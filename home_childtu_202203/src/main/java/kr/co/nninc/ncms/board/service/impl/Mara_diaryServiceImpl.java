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
 * 마라톤일지작성 스킨 별도처리를 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.06.07
 * @version 1.2
 */
@Service("mara_diaryService")
public class Mara_diaryServiceImpl implements SkinService {

	/**
	 * @title : mara_diary 스킨  list 처리
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
		
		String ss_m_id = Func.getSession(request, "ss_m_id");
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		
		//로그인확인
		if("".equals(ss_m_id)){
			String login_url = (String)request.getAttribute("board_login_url");
			String nowpage = Func.getNowPage(request);
			skinDTO.setBefore_msg( messageService.redirectMsg(model, "로그인후 이용 가능합니다.\\n\\n달서 독서마라톤대회 회원이 아니시면 가입후 이용해 주세요.", login_url +"?prepage=" + Func.urlEncode(nowpage)) );
			return;
		}
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board a");
		selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		selectdto.setExColumn(exdao.getFieldNames(new BoardDTO())+", (select sum(b_temp1) from board where a_num = '"+config.get("a_num")+"' and b_id = '"+ss_m_id+"' and b_num <= a.b_num) as sumpage");
		
		String whereQuery = "where a_num = '"+config.get("a_num")+"'";
		if (!"".equals(v_search) && !"".equals(v_keyword)) {
			whereQuery += " AND " + v_search + " like '%" + v_keyword + "%'";
		}
		//관리자가 아니면 내글만 조회
		/*
		String is_ad_cms = Func.nvl( (String)request.getAttribute("is_ad_cms") ).trim();
		if(!"Y".equals(is_ad_cms)){
			whereQuery += " and b_id = '"+ss_m_id+"'";
		}
		*/
		whereQuery += " and b_id = '"+ss_m_id+"'";
		selectdto.setExWhereQuery(whereQuery);
		
		int v_page = Func.cInt(request.getParameter("v_page"));
		if (v_page == 0) {
			v_page = 1;
		}
		selectdto.setExPage(v_page);
		int pagesize = Func.cInt(config.get("a_displaysu"));
		selectdto.setExRecordCount(pagesize);
		
		selectdto.setExOrderByQuery("order by b_regdate desc, b_num desc");
		List<HashMap<String,String>>boardList = exdao.selectQueryPage(selectdto);
		skinDTO.setBoardList(boardList);

	}

	@Override
	public void view(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : mara_diary 스킨  write 처리
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
		
		
		//독서마라톤 설정정보
		LinkedHashMap<String,String> mara_rq_cfg = CommonConfig.getBoardMap("mara_request");
		int sdate = Func.cInt( mara_rq_cfg.get("sdate") );
		int edate = Func.cInt( mara_rq_cfg.get("edate") );
		int nowdate = Func.cInt( Func.date("YmdH") );
		if(sdate > nowdate){
			skinDTO.setBefore_msg( messageService.backMsg(model, "독서마라톤 대회 시작전입니다.") );
			return;
		}
		if(edate < nowdate){
			skinDTO.setBefore_msg( messageService.backMsg(model, "독서마라톤 대회가 종료되었습니다.") );
			return;
		}
		
		
		String ss_m_id = Func.getSession(request, "ss_m_id");
		//로그인확인
		if("".equals(ss_m_id)){
			String login_url = (String)request.getAttribute("board_login_url");
			String nowpage = Func.getNowPage(request);
			skinDTO.setBefore_msg( messageService.redirectMsg(model, "달서 독서마라톤대회 홈페이지에서 로그인후 이용 가능합니다.\\n\\n달서 독서마라톤대회 회원이 아니시면 가입후 이용해 주세요.", login_url +"?prepage=" + Func.urlEncode(nowpage)) );
			return;
		}
		
		SelectDTO selectdto = new SelectDTO();
		
		//코스
		LinkedHashMap<String,String> courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);
		
		//독서마라톤 참가정보 조회
		String mara_rq_a_num = Func.nvl( mara_rq_cfg.get("a_num") ).trim();
		if("".equals(mara_rq_a_num)){
			skinDTO.setBefore_msg( messageService.backMsg(model, "독서마라톤 정보조회 실패") );
			return;
		}
		selectdto.setExTableName("board");
		selectdto.setExKeyColumn("b_num");	//PK 키컬럼 정의
		selectdto.setExColumn(new BoardDTO());
		selectdto.setExWhereQuery("where a_num = '"+mara_rq_a_num+"' and b_id = '"+ss_m_id+"'");
		HashMap<String,String>mara_rq_board = exdao.selectQueryRecord(selectdto);
		if(mara_rq_board.size() == 0){
			skinDTO.setBefore_msg( messageService.backMsg(model, "참가신청이 되어 있지 않습니다.") );
			return;
		}
		request.setAttribute("mara_rq_board", mara_rq_board);
		
		//오늘하루 작성한 쪽수
		selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("sum( b_temp1 )");
		String whereQuery = "where a_num = '"+exdao.filter(config.get("a_num"))+"'";
		whereQuery += " and b_id = '"+ss_m_id+"'";
		whereQuery += " and b_regdate like '"+Func.date("Y-m-d")+"%'";
		selectdto.setExWhereQuery(whereQuery);
		int today_page = Func.cInt( exdao.selectQueryColumn(selectdto) );
		request.setAttribute("today_page", today_page);

	}

	/**
	 * @title : mara_diary 스킨  write 처리
	 * @method : write
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
		
		//독서마라톤 설정정보
		LinkedHashMap<String,String> mara_rq_cfg = CommonConfig.getBoardMap("mara_request");
		int sdate = Func.cInt( mara_rq_cfg.get("sdate") );
		int edate = Func.cInt( mara_rq_cfg.get("edate") );
		int nowdate = Func.cInt( Func.date("YmdH") );
		if(sdate > nowdate){
			skinDTO.setBefore_msg( messageService.backMsg(model, "독서마라톤 대회 시작전입니다.") );
			return;
		}
		if(edate < nowdate){
			skinDTO.setBefore_msg( messageService.backMsg(model, "독서마라톤 대회가 종료되었습니다.") );
			return;
		}
		
		//로그인확인
		String ss_m_id = Func.getSession(request, "ss_m_id");
		if("".equals(ss_m_id)){
			skinDTO.setBefore_msg( messageService.backMsg(model, "로그인세션이 만료되었습니다.") );
			return;
		}
		
		
		SelectDTO selectdto = new SelectDTO();
		
		//누적기록 업데이트
		int b_temp1 = Func.cInt( request.getParameter("b_temp1") );
		selectdto.setExTableName("board");
		selectdto.setExColumn("sum(b_temp1) as sumpage");
		selectdto.setExWhereQuery("where a_num = '"+config.get("a_num")+"' and b_id = '"+ss_m_id+"'");
		int sumpage = Func.cInt( exdao.selectQueryColumn(selectdto) );
		sumpage += b_temp1;
		
		//최종누적기록
		selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_temp7");
		selectdto.setExWhereQuery("where a_num = '"+mara_rq_cfg.get("a_num")+"' and b_id = '"+ss_m_id+"'");
		int rq_course = Func.cInt( exdao.selectQueryColumn(selectdto) );
		
		String mara_rq_a_num = mara_rq_cfg.get("a_num");
		String sql = "update board set b_temp9 = '"+sumpage+"'";
		System.out.println("mara_diaryServiceImpl.writeOk.sumpage="+sumpage+","+rq_course);
		sql += " where a_num = '"+mara_rq_a_num+"' and b_id = '"+ss_m_id+"'";
		exdao.executeQuery(sql);
		
		//목표달성
		if(sumpage >= rq_course){
			sql = "update board set b_temp10 = 1, b_re_date = '"+Func.date("Y-m-d H:i:s")+"'";
			sql += " where a_num = '"+mara_rq_a_num+"' and b_id = '"+ss_m_id+"' and b_temp10 <> 1";
			exdao.executeQuery(sql);
		}
		
	}

	/**
	 * @title : mara_diary 스킨  modify 처리
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
		
		String b_num = exdao.filter( request.getParameter("b_num") );
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_id");
		selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
		String b_id = Func.nvl( exdao.selectQueryColumn(selectdto) ).trim();
		
		//독서마라톤 설정정보
		LinkedHashMap<String,String> mara_rq_cfg = CommonConfig.getBoardMap("mara_request");

		int sdate = Func.cInt( mara_rq_cfg.get("sdate") );
		int edate = Func.cInt( mara_rq_cfg.get("edate") );
		int nowdate = Func.cInt( Func.date("YmdH") );
		String marathon_close = "N";
		if(edate < nowdate){
			marathon_close = "Y";
		}
		request.setAttribute("marathon_close", marathon_close);
		
		
		//코스
		LinkedHashMap<String,String> courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);
		
		//독서마라톤 참가정보 조회
		String mara_rq_a_num = Func.nvl( mara_rq_cfg.get("a_num") ).trim();
		if("".equals(mara_rq_a_num)){
			skinDTO.setBefore_msg( messageService.backMsg(model, "독서마라톤 정보조회 실패") );
			return;
		}
		selectdto.setExTableName("board");
		selectdto.setExKeyColumn("b_num");	//PK 키컬럼 정의
		selectdto.setExColumn(new BoardDTO());
		selectdto.setExWhereQuery("where a_num = '"+mara_rq_a_num+"' and b_id = '"+b_id+"'");
		HashMap<String,String>mara_rq_board = exdao.selectQueryRecord(selectdto);
		if(mara_rq_board.size() == 0){
			skinDTO.setBefore_msg( messageService.backMsg(model, "참가신청이 되어 있지 않습니다.") );
			return;
		}
		request.setAttribute("mara_rq_board", mara_rq_board);

	}

	/**
	 * @title : mara_diary 스킨  modifyOk 처리
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

		String b_num = exdao.filter(request.getParameter("b_num"));
		
		//독서마라톤 설정정보
		LinkedHashMap<String,String> mara_rq_cfg = CommonConfig.getBoardMap("mara_request");
		int sdate = Func.cInt( mara_rq_cfg.get("sdate") );
		int edate = Func.cInt( mara_rq_cfg.get("edate") );
		int nowdate = Func.cInt( Func.date("YmdH") );
		if(sdate > nowdate){
			skinDTO.setBefore_msg( messageService.backMsg(model, "독서마라톤 대회 시작전입니다.") );
			return;
		}
		if(edate < nowdate){
			skinDTO.setBefore_msg( messageService.backMsg(model, "독서마라톤 대회가 종료되어 일지를 수정할 수 없습니다.") );
			return;
		}
		
		SelectDTO selectdto = new SelectDTO();
		
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_id");
		selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
		String b_id = exdao.filter( exdao.selectQueryColumn(selectdto) );
		
		//누적기록 업데이트
		int b_temp1 = Func.cInt( request.getParameter("b_temp1") );
		selectdto.setExTableName("board");
		selectdto.setExColumn("sum(b_temp1) as sumpage");
		selectdto.setExWhereQuery("where a_num = '"+config.get("a_num")+"' and b_id = '"+b_id+"' and b_num <> '"+b_num+"'");
		int sumpage = Func.cInt( exdao.selectQueryColumn(selectdto) );
		sumpage += b_temp1;
		
		//최종누적기록
		selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_temp7");
		selectdto.setExWhereQuery("where a_num = '"+mara_rq_cfg.get("a_num")+"' and b_id = '"+b_id+"'");
		int rq_course = Func.cInt( exdao.selectQueryColumn(selectdto) );
		
		String mara_rq_a_num = mara_rq_cfg.get("a_num");
		String sql = "update board set b_temp9 = '"+sumpage+"'";
		System.out.println("mara_diaryServiceImpl.writeOk.sumpage="+sumpage+","+rq_course);
		sql += " where a_num = '"+mara_rq_a_num+"' and b_id = '"+b_id+"'";
		exdao.executeQuery(sql);

		//목표달성
		if(sumpage >= rq_course){
			sql = "update board set b_temp10 = 1, b_re_date = '"+Func.date("Y-m-d H:i:s")+"'";
			sql += " where a_num = '"+mara_rq_a_num+"' and b_id = '"+b_id+"' and b_temp10 <> 1";
			exdao.executeQuery(sql);
		}else{
			sql = "update board set b_temp10 = 0, b_re_date = ''";
			sql += " where a_num = '"+mara_rq_a_num+"' and b_id = '"+b_id+"'";
			exdao.executeQuery(sql);
		}
		
	}

	/**
	 * @title : mara_diary 스킨  write 처리
	 * @method : write
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		// 세션정보
		String is_delete = Func.nvl( (String)request.getAttribute("is_delete") ).trim();
		if (!is_delete.equals("Y")) {
			skinDTO.setBefore_msg( messageService.backMsg(model, "접근권한이 없습니다.") );
			return;
		}
		
		String status = Func.nvl( request.getParameter("status") );
		
		//독서마라톤 설정정보
		LinkedHashMap<String,String> mara_rq_cfg = CommonConfig.getBoardMap("mara_request");
		String mara_rq_a_num = Func.nvl(mara_rq_cfg.get("a_num")).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_id, b_temp1");
		String whereQuery = "where a_num = '"+config.get("a_num")+"'";
		if ("totdel".equals(status)) { //다중삭제
			String[] chk = request.getParameterValues("chk");
			for(int z=0;z <=chk.length-1;z++) {
				String b_num = chk[z].trim();
				whereQuery += " and b_num = '"+b_num+"'";
				selectdto.setExWhereQuery(whereQuery);
				HashMap<String,String>board = exdao.selectQueryRecord(selectdto);
				String b_id = exdao.filter( board.get("b_id") );
				
				//누적기록 업데이트
				SelectDTO selectdto2 = new SelectDTO();
				selectdto2.setExTableName("board");
				selectdto2.setExColumn("sum(b_temp1) as sumpage");
				selectdto2.setExWhereQuery("where a_num = '"+config.get("a_num")+"' and b_id = '"+b_id+"' and b_num <> '"+b_num+"'");
				int sumpage = Func.cInt( exdao.selectQueryColumn(selectdto2) );
				
				//최종누적기록
				selectdto2 = new SelectDTO();
				selectdto2.setExTableName("board");
				selectdto2.setExColumn("b_temp7");
				selectdto2.setExWhereQuery("where a_num = '"+mara_rq_cfg.get("a_num")+"' and b_id = '"+b_id+"'");
				int rq_course = Func.cInt( exdao.selectQueryColumn(selectdto2) );
				
				String sql = "update board set b_temp9 = '"+sumpage+"'";
				System.out.println("mara_diaryServiceImpl.writeOk.sumpage="+sumpage+","+rq_course);
				if(sumpage >= rq_course){	//목표달성
					sql += ", b_temp10 = 1, b_re_date = '"+Func.date("Y-m-d H:i:s")+"'";
				}else{
					sql += ", b_temp10 = 0, b_re_date = ''";	//수정시 목표달성치보다 낮을경우 다시 상태변경
				}
				sql += " where a_num = '"+mara_rq_a_num+"' and b_id = '"+b_id+"'";
				exdao.executeQuery(sql);
				
			}
		}else{
			String b_num = exdao.filter( request.getParameter("b_num") ).trim();
			whereQuery += " and b_num = '"+b_num+"'";
			selectdto.setExWhereQuery(whereQuery);
			HashMap<String,String>board = exdao.selectQueryRecord(selectdto);
			String b_id = exdao.filter( board.get("b_id") );
			
			//누적기록 업데이트
			SelectDTO selectdto2 = new SelectDTO();
			selectdto2.setExTableName("board");
			selectdto2.setExColumn("sum(b_temp1) as sumpage");
			selectdto2.setExWhereQuery("where a_num = '"+config.get("a_num")+"' and b_id = '"+b_id+"' and b_num <> '"+b_num+"'");
			int sumpage = Func.cInt( exdao.selectQueryColumn(selectdto2) );
			
			//최종누적기록
			selectdto2 = new SelectDTO();
			selectdto2.setExTableName("board");
			selectdto2.setExColumn("b_temp7");
			selectdto2.setExWhereQuery("where a_num = '"+mara_rq_cfg.get("a_num")+"' and b_id = '"+b_id+"'");
			int rq_course = Func.cInt( exdao.selectQueryColumn(selectdto2) );
			
			String sql = "update board set b_temp9 = '"+sumpage+"'";
			System.out.println("mara_diaryServiceImpl.writeOk.sumpage="+sumpage+","+rq_course);
			if(sumpage >= rq_course){	//목표달성
				sql += ", b_temp10 = 1, b_re_date = '"+Func.date("Y-m-d H:i:s")+"'";
			}else{
				sql += ", b_temp10 = 0, b_re_date = ''";	//수정시 목표달성치보다 낮을경우 다시 상태변경
			}
			sql += " where a_num = '"+mara_rq_a_num+"' and b_id = '"+b_id+"'";
			exdao.executeQuery(sql);
		}

	}

	/**
	 * @title : mara_diary 스킨  totalList 처리
	 * @method : totalList
	 */
	public void totalList(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값

		String b_id = exdao.filter(request.getParameter("b_id"));
		
		//관리자, 본인 확인
		String is_ad_cms = Func.nvl( (String)request.getAttribute("is_ad_cms") ).trim();
		if(!"Y".equals(is_ad_cms) && !b_id.equals(Func.getSession(request, "ss_m_id"))){
			skinDTO.setBefore_msg( messageService.closeMsg(model, "권한이 없습니다.") );
			return;
		}
		
		if("".equals(b_id)){
			skinDTO.setBefore_msg( messageService.closeMsg(model, "신청자 정보가 없습니다.") );
			return;
		}
		
		SelectDTO selectdto = new SelectDTO();
		
		//신청정보
		LinkedHashMap<String, String>mara_rq_cfg = CommonConfig.getBoardMap("mara_request");
		String mara_rq_a_num = Func.nvl( mara_rq_cfg.get("a_num") ).trim();
		if("".equals(mara_rq_a_num)){
			skinDTO.setBefore_msg( messageService.closeMsg(model, "독서마라톤게시판정보 조회실패.") );
			return;
		}
		selectdto.setExTableName("board");
		selectdto.setExKeyColumn("b_num");	//PK 키컬럼 정의
		selectdto.setExColumn(new BoardDTO());
		selectdto.setExWhereQuery("where a_num = '"+mara_rq_a_num+"' and b_id = '"+b_id+"'");
		HashMap<String,String>mara_rq_board = exdao.selectQueryRecord(selectdto);
		mara_rq_board.put("b_content", Func.getTextmode(mara_rq_board.get("b_content")));
		request.setAttribute("mara_rq_board", mara_rq_board);
		
		//코스정보
		LinkedHashMap<String,String> courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);
		
		
		//마라톤일지 목록
		selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		selectdto.setExColumn(new BoardDTO());
		selectdto.setExWhereQuery("where a_num = '"+config.get("a_num")+"' and b_id = '"+b_id+"'");
		selectdto.setExOrderByQuery("order by b_regdate");
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
		int sumpage = 0;
		for(int i= 0;i<=boardList.size()-1;i++){
			HashMap<String,String>board = boardList.get(i);
			sumpage += Func.cInt( board.get("b_temp1") );
			board.put("sumpage", Func.cStr(sumpage));
			board.put("b_content", Func.getTextmode(board.get("b_content")));
		}
		request.setAttribute("boardList", boardList);
		
		int recordcount = boardList.size();
		request.setAttribute("recordcount", recordcount);
	}
	
	
	/**
	 * @title : mara_diary 스킨  totalListExcel 처리
	 * @method : totalListExcel
	 */
	public void totalListExcel(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값

		//관리자 확인
		String is_ad_cms = Func.nvl( (String)request.getAttribute("is_ad_cms") ).trim();
		if(!"Y".equals(is_ad_cms)){
			skinDTO.setBefore_msg( messageService.closeMsg(model, "권한이 없습니다.") );
			return;
		}
		
		SelectDTO selectdto = new SelectDTO();
		String b_id = exdao.filter(request.getParameter("b_id"));
		String sh_course = exdao.filter(request.getParameter("sh_course"));
		String complete = exdao.filter(request.getParameter("complete"));
		
		//신청정보
		LinkedHashMap<String, String>mara_rq_cfg = CommonConfig.getBoardMap("mara_request");
		String mara_rq_a_num = Func.nvl( mara_rq_cfg.get("a_num") ).trim();
		if("".equals(mara_rq_a_num)){
			skinDTO.setBefore_msg( messageService.closeMsg(model, "독서마라톤게시판정보 조회실패.") );
			return;
		}
		selectdto.setExTableName("board");
		selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		selectdto.setExColumn(new BoardDTO());
		String whereQuery = "where a_num = '"+mara_rq_a_num+"'";
		if(!"".equals(b_id)){
			whereQuery += " and b_id = '"+b_id+"'";
		}
		if(!"".equals(sh_course)){
			whereQuery += " and b_temp7  = '"+sh_course+"'";
		}
		if("Y".equals(complete)){
			whereQuery += " and b_temp10 = '1'";
		}
		selectdto.setExWhereQuery(whereQuery);
		List<HashMap<String,Object>>rqBoardList = exdao.selectQueryTableObject(selectdto);
		if(rqBoardList.size() == 1 && !"".equals(b_id)){	//회원별 조회면 회원명 전송
			String rq_b_bname = Func.cStr( rqBoardList.get(0).get("b_name") );
			request.setAttribute("rq_b_bname", rq_b_bname);
		}
		for(int i=0;i<=rqBoardList.size()-1;i++){
			String rq_b_id = Func.cStr( rqBoardList.get(i).get("b_id") );
			//마라톤일지 목록
			selectdto = new SelectDTO();
			selectdto.setExTableName("board");
			selectdto.setExColumn(new BoardDTO());
			selectdto.setExWhereQuery("where a_num = '"+config.get("a_num")+"' and b_id = '"+rq_b_id+"'");
			selectdto.setExOrderByQuery("order by b_regdate");
			List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
			int sumpage = 0;
			for(int j= 0;j<=boardList.size()-1;j++){
				HashMap<String,String>board = boardList.get(j);
				sumpage += Func.cInt( board.get("b_temp1") );
				board.put("sumpage", Func.cStr(sumpage));
				board.put("b_content", Func.getTextmode(board.get("b_content")));
			}
			rqBoardList.get(i).put("boardList", boardList);
		}
		request.setAttribute("rqBoardList", rqBoardList);
		
		//코스정보
		LinkedHashMap<String,String> courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);
		
		int recordcount = rqBoardList.size();
		request.setAttribute("recordcount", recordcount);
	}
	
	/**
	 * @title : mara_diary 스킨  countListExcel 처리
	 * @method : countListExcel
	 */
	public void countListExcel(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값

		//관리자 확인
		String is_ad_cms = Func.nvl( (String)request.getAttribute("is_ad_cms") ).trim();
		if(!"Y".equals(is_ad_cms)){
			skinDTO.setBefore_msg( messageService.closeMsg(model, "권한이 없습니다.") );
			return;
		}
		
		String sh_course = exdao.filter(request.getParameter("sh_course"));
		
		//마라톤 신청정보
		LinkedHashMap<String, String>mara_rq_cfg = CommonConfig.getBoardMap("mara_request");
		String mara_rq_a_num = mara_rq_cfg.get("a_num");
		
		//코스정보
		LinkedHashMap<String, String>courseList = CommonConfig.getBoardMap("mara_request.course");
		request.setAttribute("courseList", courseList);
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board a LEFT OUTER JOIN board b ON a.b_id = b.b_id");
		selectdto.setExKeyColumn("a.b_num");	//고유컬럼 설정 필수
		String s_fields = "a.b_id, a.b_name, a.b_subject, a.b_content, a.b_regdate, a.b_temp1, a.b_temp2, a.b_temp3, a.b_temp4, a.b_temp5, a.b_temp6";
		s_fields += ", b.b_num, b.b_temp7 as course";
		selectdto.setExColumn(s_fields);
		String whereQuery = "where a.a_num ='"+exdao.filter(config.get("a_num"))+"' AND b.a_num = '"+mara_rq_a_num+"'";
		if(!"".equals(sh_course)){
			whereQuery += " and b.b_temp7 = '"+sh_course+"'";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY b.b_num DESC, a.b_regdate");
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
		request.setAttribute("boardList", boardList);
		
	}
}
