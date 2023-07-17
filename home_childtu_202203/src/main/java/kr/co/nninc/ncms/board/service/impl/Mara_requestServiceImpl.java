package kr.co.nninc.ncms.board.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board.service.SkinService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 마라톤신청 스킨 별도처리를 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.06.05
 * @version 1.2
 */
@Service("mara_requestService")
public class Mara_requestServiceImpl implements SkinService {

	/**
	 * @title : mara_request 스킨  List 처리
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		//관리자가 아닐경우 내글만 조회
		String is_ad_cms = Func.nvl( (String)request.getAttribute("is_ad_cms") ).trim();
		String ss_m_id = Func.getSession(request, "ss_m_id");
		String ss_m_dupinfo = Func.getSession(request, "ss_m_dupinfo");
		String whereQuery = "";
		if(!"Y".equals(is_ad_cms)){
			if(!"".equals(ss_m_id)){
				whereQuery += " and b_id = '"+ss_m_id+"'";
			}else if(!"".equals(ss_m_dupinfo)){
				whereQuery += " and b_jumin = '"+ss_m_dupinfo+"'";
			}
			if("".equals(whereQuery)){
				String login_url = (String)request.getAttribute("board_login_url");
				String nowpage = Func.getNowPage(request);
				skinDTO.setBefore_msg( messageService.redirectMsg(model, "", login_url +"?prepage=" + Func.urlEncode(nowpage)) );
				return;
			}
		}
		//코스별 검색파라미터 추가
		String sh_course = exdao.filter(request.getParameter("sh_course"));
		if(!"".equals(sh_course)){
			whereQuery += " and b_temp7 = '"+sh_course+"'";
		}
		skinDTO.setWhere_query(whereQuery);
		
		//페이징쿼리추가
		skinDTO.setPaging_query("&sh_course="+sh_course);
		
		//마라톤일지게시판정보
		LinkedHashMap<String,String> mara_diary_cfg = CommonConfig.getBoardMap("mara_diary");
		request.setAttribute("mara_diary_cfg", mara_diary_cfg);
		
		//코스정보
		LinkedHashMap<String,String> courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);

	}

	/**
	 * @title : mara_request 스킨 view 처리
	 * @method : view
	 */
	@Override
	public void view(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		//코스정보
		LinkedHashMap<String,String> courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);

	}

	/**
	 * @title : mara_request 스킨 write 처리
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		
		String ss_m_id = Func.getSession(request, "ss_m_id");
		
		//로그인확인
		if("".equals(ss_m_id)){
			String login_url = (String)request.getAttribute("board_login_url");
			String nowpage = Func.getNowPage(request);
			skinDTO.setBefore_msg( messageService.redirectMsg(model, "달서 독서마라톤대회 홈페이지에서 로그인후 이용 가능합니다.\\n\\n달서 독서마라톤대회 회원이 아니시면 가입후 이용해 주세요.", login_url +"?prepage=" + Func.urlEncode(nowpage)) );
			return;
		}
		
		//접수일자 체크
		LinkedHashMap<String, String>confMap = CommonConfig.getBoardMap(config.get("a_level"));
		String rsdate = Func.nvl( confMap.get("rsdate") ).trim();
		String redate = Func.nvl( confMap.get("redate") ).trim();
		String nowdate = Func.date("YmdH");
		if(Func.cInt( nowdate ) < Func.cInt(rsdate)){
			String rsdate_str = rsdate.substring(0, 4)+"년 "+rsdate.substring(4, 6)+"월 "+rsdate.substring(6, 8)+"일 "+rsdate.substring(8, 10)+"시";
			String before_msg = messageService.backMsg(model, "독서마라톤 대회 접수 시작일은 "+rsdate_str+" 부터입니다.");
			skinDTO.setBefore_msg(before_msg);
			return;
		}else if(Func.cInt( nowdate ) > Func.cInt(redate)){
			skinDTO.setBefore_msg(messageService.backMsg(model, "독서마라톤 대회 접수가 마감되었습니다."));
			return;
		}
		
		//신청중인지 체크
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("count(*)");
		selectdto.setExWhereQuery("where a_num = '"+exdao.filter(config.get("a_num"))+"' and b_id = '"+exdao.filter(ss_m_id)+"'");
		String cnt = exdao.selectQueryColumn(selectdto);
		if(!"0".equals(cnt)){
			String status_page = Func.nvl( confMap.get("status_page") ).trim();
			if("".equals(status_page)){
				skinDTO.setBefore_msg(messageService.backMsg(model, "이미 참가 신청을 완료하셨습니다."));
			}else{
				skinDTO.setBefore_msg(messageService.redirectMsg(model, "이미 참가 신청을 완료하셨습니다.\\n\\n참가 확인 페이지로 이동합니다.", status_page));
			}
			return;
		}
		
		//코스정보
		LinkedHashMap<String,String> courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);
		
		//기본정보 세팅
		HashMap<String, String> board = new HashMap<String,String>();
		//주소
		board.put("b_zip1", Func.getSession(request, "ss_user_zipcode"));
		if(!"".equals(Func.getSession(request, "ss_user_addr1"))){
			String b_addr1 = "";
			String[] addr_arr = Func.getSession(request, "ss_user_addr1").trim().split(" ");
			for(int i=0;i<=addr_arr.length-2;i++){
				if(i>0){b_addr1 += " ";}
				b_addr1 += addr_arr[i];
			}
			board.put("b_addr1", b_addr1);
			board.put("b_addr2", addr_arr[addr_arr.length-1]);
		}
		//휴대전화
		if(!"".equals(Func.getSession(request, "ss_user_mobile"))){
			board.put("b_temp4", Func.getSession(request, "ss_user_mobile").trim());
		}
		//집전화
		if(!"".equals(Func.getSession(request, "ss_user_phone"))){
			String[] phone_arr = Func.getSession(request, "ss_user_phone").trim().split("-");
			if(phone_arr.length >= 3){
				board.put("b_phone1", phone_arr[0]);
				board.put("b_phone2", phone_arr[1]);
				board.put("b_phone3", phone_arr[2]);
			}
		}
		//생년월일
		if(!"".equals(Func.getSession(request, "ss_user_birthday"))){
			board.put("b_temp6", Func.getSession(request, "ss_user_birthday").replaceAll("/", "-"));
		}
		//성별
		if("0".equals(Func.getSession(request, "ss_user_sex"))){
			board.put("b_temp5", "남");
		}else if("1".equals(Func.getSession(request, "ss_user_sex"))){
			board.put("b_temp5", "여");
		}
		
		skinDTO.setBoardMap(board);
		

	}

	/**
	 * @title : mara_request 스킨 writeOk 처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		BoardDTO board = (BoardDTO) map.get("boarddto"); // 전송된 dto
		
		//학년 반
		String b_temp3_1 = exdao.filter(request.getParameter("b_temp3_1"));
		String b_temp3_2 = exdao.filter(request.getParameter("b_temp3_2"));
		board.setB_temp3(b_temp3_1+"-"+b_temp3_2);
		
		//휴대전화
		String b_temp4_1 = exdao.filter(request.getParameter("b_temp4_1"));
		String b_temp4_2 = exdao.filter(request.getParameter("b_temp4_2"));
		String b_temp4_3 = exdao.filter(request.getParameter("b_temp4_3"));
		board.setB_temp4(b_temp4_1+"-"+b_temp4_2+"-"+b_temp4_3);
		
		//생년월일
		String b_temp6_1 = exdao.filter(request.getParameter("b_temp6_1"));
		String b_temp6_2 = exdao.filter(request.getParameter("b_temp6_2"));
		String b_temp6_3 = exdao.filter(request.getParameter("b_temp6_3"));
		board.setB_temp6(b_temp6_1+"-"+b_temp6_2+"-"+b_temp6_3);
		
		//제목
		String b_name = exdao.filter(request.getParameter("b_name"));
		board.setB_subject(b_name+" 참가신청");
		
		//누적페이지 기본값0 세팅
		board.setB_temp9("0");
		//완주상태
		board.setB_temp10("0");
		
		skinDTO.setBoardDTO(board);
		
		
		String prepage = Func.nvl(request.getParameter("prepage"));
		if ("".equals(prepage)) {
			prepage = "?proc_type=list&a_num="+config.get("a_num");
		}
		
		LinkedHashMap<String, String>confMap = CommonConfig.getBoardMap(config.get("a_level"));
		String status_page = Func.nvl( confMap.get("status_page") ).trim();
		if("".equals(status_page)){
			skinDTO.setAfter_msg(messageService.redirectMsg(model, "참가 신청이 접수 되었습니다.", prepage));
		}else{
			skinDTO.setAfter_msg(messageService.redirectMsg(model, "참가 신청이 접수 되었습니다. \\n\\n참가 신청 현황 페이지로 이동합니다.", status_page));
		}
		
		
	}

	/**
	 * @title : mara_request 스킨 modify 처리
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		BoardDTO board = (BoardDTO) map.get("boarddto"); // 전송된 dto
		
		String is_ad_cms = Func.nvl( (String)request.getAttribute("is_ad_cms") );
		
		LinkedHashMap<String, String>confMap = CommonConfig.getBoardMap(config.get("a_level"));
		String edate = Func.nvl( confMap.get("edate") ).trim();
		String nowdate = Func.date("YmdH");
		if(!"".equals(edate) && !"Y".equals(is_ad_cms)){
		//if(!"".equals(edate)){
			int year = Func.cInt( edate.substring(0, 4) );
			int month = Func.cInt( edate.substring(4, 6) );
			int day = Func.cInt( edate.substring(6, 8) );
			String hour = edate.substring(8, 10);
			String edate_chk = Func.dateSerial(year, month, day-1).replaceAll("-", "")+hour;
			if(Func.cInt(nowdate) > Func.cInt(edate_chk)){
				skinDTO.setBefore_msg(messageService.backMsg(model, "독서마라톤 마감이 임박하여 정보수정이 불가합니다."));
				return;
			}
		}
		
		//코스정보
		LinkedHashMap<String,String> courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);

	}

	/**
	 * @title : mara_request 스킨 modifyOk 처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		BoardDTO board = (BoardDTO) map.get("boarddto"); // 전송된 dto
		
		//학년 반
		String b_temp3_1 = exdao.filter(request.getParameter("b_temp3_1"));
		String b_temp3_2 = exdao.filter(request.getParameter("b_temp3_2"));
		board.setB_temp3(b_temp3_1+"-"+b_temp3_2);
		
		//휴대전화
		String b_temp4_1 = exdao.filter(request.getParameter("b_temp4_1"));
		String b_temp4_2 = exdao.filter(request.getParameter("b_temp4_2"));
		String b_temp4_3 = exdao.filter(request.getParameter("b_temp4_3"));
		board.setB_temp4(b_temp4_1+"-"+b_temp4_2+"-"+b_temp4_3);
		
		//생년월일
		String b_temp6_1 = exdao.filter(request.getParameter("b_temp6_1"));
		String b_temp6_2 = exdao.filter(request.getParameter("b_temp6_2"));
		String b_temp6_3 = exdao.filter(request.getParameter("b_temp6_3"));
		board.setB_temp6(b_temp6_1+"-"+b_temp6_2+"-"+b_temp6_3);
		
		//제목
		String b_name = exdao.filter(request.getParameter("b_name"));
		board.setB_subject(b_name+" 참가신청");
		
		//수정되면 안되는 값
		board.setB_temp1(null);
		board.setB_temp7(null);
		board.setB_temp9(null);
		board.setB_temp10(null);
		
		skinDTO.setBoardDTO(board);

	}

	@Override
	public void deleteOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * @title : mara_request 스킨 myLocation 처리
	 * @method : myLocation
	 */
	public void myLocation(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String ss_m_id = Func.getSession(request, "ss_m_id");
		
		//로그인확인
		if("".equals(ss_m_id)){
			String login_url = (String)request.getAttribute("board_login_url");
			String nowpage = Func.getNowPage(request);
			skinDTO.setBefore_msg( messageService.redirectMsg(model, "달서 독서마라톤대회 홈페이지에서 로그인후 이용 가능합니다.\\n\\n달서 독서마라톤대회 회원이 아니시면 가입후 이용해 주세요.", login_url +"?prepage=" + Func.urlEncode(nowpage)) );
			return;
		}
		
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExKeyColumn("b_num");	//PK 키컬럼 정의
		selectdto.setExColumn(new BoardDTO());
		selectdto.setExWhereQuery("where a_num = '"+config.get("a_num")+"' and b_id = '"+ss_m_id+"'");
		HashMap<String,String>board = exdao.selectQueryRecord(selectdto);
		request.setAttribute("board", board);
		if(board.size() > 0){
			int b_temp7 = Func.cInt( board.get("b_temp7") );
			int b_temp9 = Func.cInt( board.get("b_temp9") );
			double avg = ((b_temp9*1.0)/b_temp7)*100;
			if(avg > 100){avg = 100;}
			request.setAttribute("avg", avg);
		}
		//코스
		LinkedHashMap<String,String> courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);

	}
	
	/**
	 * @title : mara_request 스킨  completePaper 처리
	 * @method : completePaper
	 */
	public void completePaper(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String b_id = exdao.filter(request.getParameter("b_id"));
		if("".equals(b_id)){
			skinDTO.setBefore_msg( messageService.closeMsg(model, "회원정보가 없습니다.") );
			return;
		}
		
		LinkedHashMap<String, String>mara_rq_cfg = CommonConfig.getBoardMap("mara_request");
		request.setAttribute("mara_rq_cfg", mara_rq_cfg);
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExKeyColumn("b_num");	//PK 키컬럼 정의
		selectdto.setExColumn(new BoardDTO());
		selectdto.setExWhereQuery("where a_num = '"+config.get("a_num")+"' and b_id ='"+b_id+"'");
		HashMap<String,String>board = exdao.selectQueryRecord(selectdto);
		request.setAttribute("board", board);
		
		//코스정보
		LinkedHashMap<String, String> courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);
	}
	
	/**
	 * @title : mara_request 스킨  statusOk 처리
	 * @method : statusOk
	 */
	public void statusOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String is_ad_cms = Func.nvl( (String)request.getAttribute("is_ad_cms") ).trim();
		if(!"Y".equals(is_ad_cms)){
			skinDTO.setBefore_msg( messageService.backMsg(model, "권한이 없습니다.") );
		}
		
		String tot_level_chk = exdao.filter( request.getParameter("tot_level_chk"));
		String[] chk = request.getParameterValues("chk");
		for(int i=0;i<=chk.length-1;i++){
			exdao.executeQuery("update board set b_temp10 = '"+tot_level_chk+"' where b_num = '"+exdao.filter(chk[i])+"'");
		}
		String prepage = Func.nvl(request.getParameter("prepage")).trim();
		skinDTO.setAfter_msg( messageService.redirectMsg(model, "", prepage) );
		
	}
	
	
	/**
	 * @title : mara_request 스킨  courseStatus 처리
	 * @method : courseStatus
	 */
	public void courseStatus(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		SelectDTO selectdto = new SelectDTO();
		
		//참여현황
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_addr1, b_addr2, b_temp7");
		selectdto.setExWhereQuery("where a_num = '"+config.get("a_num")+"'");
		List<HashMap<String,String>>reqList = exdao.selectQueryTable(selectdto);
		int totalcount = reqList.size();
		if(totalcount > 0){
			int course1 = 0;
			int course2 = 0;
			int course3 = 0;
			int course4 = 0;
			for(int i =0;i<=totalcount-1;i++){
				String b_temp7 = Func.nvl( reqList.get(i).get("b_temp7") ).trim();
				boolean course_chk = false;
				
				//코스별 참여
				if("3000".equals(b_temp7)){
					course1++;
				}else if("5000".equals(b_temp7)){
					course2++;
				}else if("10000".equals(b_temp7)){
					course3++;
				}else if("21097".equals(b_temp7)){
					course4++;
				}
			}
			
			double course_avg1 = ((course1*1.0)/totalcount)*100;
			course_avg1 = Double.parseDouble(String.format("%.2f",course_avg1));
			request.setAttribute("course1", course1);
			request.setAttribute("course_avg1", course_avg1);
			
			double course_avg2 = ((course2*1.0)/totalcount)*100;
			course_avg2 = Double.parseDouble(String.format("%.2f",course_avg2));
			request.setAttribute("course2", course2);
			request.setAttribute("course_avg2", course_avg2);
			
			double course_avg3 = ((course3*1.0)/totalcount)*100;
			course_avg3 = Double.parseDouble(String.format("%.2f",course_avg3));
			request.setAttribute("course3", course3);
			request.setAttribute("course_avg3", course_avg3);
			
			double course_avg4 = ((course4*1.0)/totalcount)*100;
			course_avg4 = Double.parseDouble(String.format("%.2f",course_avg4));
			request.setAttribute("course4", course4);
			request.setAttribute("course_avg4", course_avg4);
		}
	}

}
