package kr.co.nninc.ncms.banner2.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.banner2.service.Banner2DTO;
import kr.co.nninc.ncms.banner2.service.Banner2Service;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 팝업존을 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.09.26
 * @version 1.0
 */
@Service("banner2Service")
public class Banner2ServiceImpl extends EgovAbstractServiceImpl implements Banner2Service {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** FileUtil */
	@Resource(name="fileutil")
	private FileUtil fileutil;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	
	/**
	 * @title : 팝업존등록 폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		HashMap<String,String>banner2 = new HashMap<String, String>();

		// 초기화
		banner2.put("b_l_page","1");
		banner2.put("w_width","0");
		banner2.put("w_height","0");
		banner2.put("w_top","0");
		banner2.put("w_left","0");
		banner2.put("unlimited","N");
		
		// 시작 : 날짜시간
		int b_l_sdate_y = Func.cInt(Func.date("Y"));
		int b_l_sdate_m = Func.cInt(Func.date("m"));
		int b_l_sdate_d = Func.cInt(Func.date("d"));
		int b_l_sdate_h = 0;
		int b_l_sdate_n = 0;
		
		// 끝 : 날짜시간 (오늘 날짜 + 1달)
		int b_l_edate_y = Func.cInt(Func.addGetDate2(1, "yyyy"));
		int b_l_edate_m = Func.cInt(Func.addGetDate2(1, "MM"));
		int b_l_edate_d = Func.cInt(Func.addGetDate2(1, "dd"));
		int b_l_edate_h = 23;
		int b_l_edate_n = 59;
		
		request.setAttribute("b_l_sdate_y", b_l_sdate_y);
		request.setAttribute("b_l_sdate_m", b_l_sdate_m);
		request.setAttribute("b_l_sdate_d", b_l_sdate_d);
		request.setAttribute("b_l_sdate_h", b_l_sdate_h);
		request.setAttribute("b_l_sdate_n", b_l_sdate_n);

		request.setAttribute("b_l_edate_y", b_l_edate_y);
		request.setAttribute("b_l_edate_m", b_l_edate_m);
		request.setAttribute("b_l_edate_d", b_l_edate_d);
		request.setAttribute("b_l_edate_h", b_l_edate_h);
		request.setAttribute("b_l_edate_n", b_l_edate_n);
		request.setAttribute("banner2", banner2);

	}

	/**
	 * @title : 팝업존수정 폼
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String b_l_num = Func.nvl( request.getParameter("b_l_num") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		//조회
		selectdto.setExTableName("banner2");
		selectdto.setExKeyColumn("b_l_num");	//clob자료형을 조회하기위해서 키컬럼을 추가
		selectdto.setExColumn(new Banner2DTO());
		selectdto.setExWhereQuery("where b_l_num = '"+b_l_num+"'");
		HashMap<String,String>banner2dto = exdao.selectQueryRecord(selectdto);
		
		// 시작 : 날짜시간
		int b_l_sdate_y = Func.cInt(Func.date("Y"));
		int b_l_sdate_m = Func.cInt(Func.date("m"));
		int b_l_sdate_d = Func.cInt(Func.date("d"));
		int b_l_sdate_h = 0;
		int b_l_sdate_n = 0;
		
		String b_l_sdate = Func.nvl(banner2dto.get("b_l_sdate"));
		if(!"".equals(b_l_sdate)) {
			String[] b_l_sdate_arr = b_l_sdate.split(" ");
			String[] b_l_sdate_arr2 = b_l_sdate_arr[0].split("-");
			b_l_sdate_y = Func.cInt(b_l_sdate_arr2[0]);
			b_l_sdate_m = Func.cInt(b_l_sdate_arr2[1]);
			b_l_sdate_d = Func.cInt(b_l_sdate_arr2[2]);
			
			String[] b_l_sdate_arr3	= b_l_sdate_arr[1].split(":");
			b_l_sdate_h = Func.cInt(b_l_sdate_arr3[0]);
			b_l_sdate_n = Func.cInt(b_l_sdate_arr3[1]);
		}
		// 끝 : 날짜시간 (오늘 날짜 + 1달)
		
		int b_l_edate_y = Func.cInt(Func.addGetDate2(1, "yyyy"));
		int b_l_edate_m = Func.cInt(Func.addGetDate2(1, "MM"));
		int b_l_edate_d = Func.cInt(Func.addGetDate2(1, "dd"));
		int b_l_edate_h = 23;
		int b_l_edate_n = 59;
		
		String b_l_edate = Func.nvl(banner2dto.get("b_l_edate"));
		if(!"".equals(b_l_edate)) {
			String[] b_l_edate_arr = b_l_edate.split(" ");
			String[] b_l_edate_arr2 = b_l_edate_arr[0].split("-");
			b_l_edate_y = Func.cInt(b_l_edate_arr2[0]);
			b_l_edate_m = Func.cInt(b_l_edate_arr2[1]);
			b_l_edate_d = Func.cInt(b_l_edate_arr2[2]);
			
			String[] b_l_edate_arr3	= b_l_edate_arr[1].split(":");
			b_l_edate_h = Func.cInt(b_l_edate_arr3[0]);
			b_l_edate_n = Func.cInt(b_l_edate_arr3[1]);
		}
		request.setAttribute("b_l_sdate_y", b_l_sdate_y);
		request.setAttribute("b_l_sdate_m", b_l_sdate_m);
		request.setAttribute("b_l_sdate_d", b_l_sdate_d);
		request.setAttribute("b_l_sdate_h", b_l_sdate_h);
		request.setAttribute("b_l_sdate_n", b_l_sdate_n);

		request.setAttribute("b_l_edate_y", b_l_edate_y);
		request.setAttribute("b_l_edate_m", b_l_edate_m);
		request.setAttribute("b_l_edate_d", b_l_edate_d);
		request.setAttribute("b_l_edate_h", b_l_edate_h);
		request.setAttribute("b_l_edate_n", b_l_edate_n);
		request.setAttribute("banner2", banner2dto);
		
		//작업기록
		Func.writeManagerLog ("팝업존 상세보기 ["+banner2dto.get("b_l_subject")+"]", request);
	}

	/**
	 * @title : 팝업존목록
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		SelectDTO selectdto = new SelectDTO();
		
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		request.setAttribute("v_keyword", v_keyword);
		request.setAttribute("v_search", v_search);
		
		
		selectdto.setExTableName("banner2");
		selectdto.setExKeyColumn("b_l_num");
		selectdto.setExColumn(new Banner2DTO());
		
		String whereQuery = "";
		if(!"".equals(v_keyword) && !"".equals(v_search)){
			whereQuery += " where "+v_search+" like '%"+v_keyword+"%' ";
		}
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){whereQuery += " and site_dir = '"+builder_dir+"' ";}
		selectdto.setExWhereQuery(whereQuery);
		
		selectdto.setExOrderByQuery("ORDER BY b_l_code asc");
		List<HashMap<String,String>> banner2List = exdao.selectQueryTable(selectdto);
		// setAttribute
		request.setAttribute("banner2List", banner2List);
	
		//현재경로 전송
		Func.getNowPage(request);

	}

	/**
	 * @title : 팝업존등록처리
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		Banner2DTO banner2dto = (Banner2DTO) Func.requestAll(map.get("banner2dto"));

		String b_l_sdate_y = Func.nvl(request.getParameter("b_l_sdate_y"));
		String b_l_sdate_m = Func.nvl(request.getParameter("b_l_sdate_m"));
		String b_l_sdate_d = Func.nvl(request.getParameter("b_l_sdate_d"));
		String b_l_sdate_h = Func.nvl(request.getParameter("b_l_sdate_h"));
		String b_l_sdate_n = Func.nvl(request.getParameter("b_l_sdate_n"));
		
		String b_l_edate_y = Func.nvl(request.getParameter("b_l_edate_y"));
		String b_l_edate_m = Func.nvl(request.getParameter("b_l_edate_m"));
		String b_l_edate_d = Func.nvl(request.getParameter("b_l_edate_d"));
		String b_l_edate_h = Func.nvl(request.getParameter("b_l_edate_h"));
		String b_l_edate_n = Func.nvl(request.getParameter("b_l_edate_n"));
		
		//변수초기화 =================================================
		if("".equals(banner2dto.getScrollbars())) banner2dto.setScrollbars("no");
		if("".equals(banner2dto.getToolbar())) banner2dto.setToolbar("no");
		if("".equals(banner2dto.getMenubar())) banner2dto.setMenubar("no");
		if("".equals(banner2dto.getW_width())) banner2dto.setW_width("no");
		if("".equals(banner2dto.getW_height())) banner2dto.setW_height("no");
		if("".equals(banner2dto.getLocations())) banner2dto.setLocations("no");
		if(!"1".equals(banner2dto.getB_l_win())) banner2dto.setB_l_win("0");
		if("".equals(banner2dto.getB_l_page())) banner2dto.setB_l_page("1");

		String b_l_sdate = b_l_sdate_y + "-" + Func.zerofill(b_l_sdate_m, 2, "0") + "-" + Func.zerofill(b_l_sdate_d, 2, "0")
		+ " " + Func.zerofill(b_l_sdate_h, 2, "0") + ":" + Func.zerofill(b_l_sdate_n, 2, "0");
		String b_l_edate = b_l_edate_y + "-" + Func.zerofill(b_l_edate_m, 2, "0") + "-" + Func.zerofill(b_l_edate_d, 2, "0")
		+ " " + Func.zerofill(b_l_edate_h, 2, "0") + ":" + Func.zerofill(b_l_edate_n, 2, "0");

		String b_l_wdate = Func.date("Y-m-d H:i:s");
		String b_l_mdate = Func.date("Y-m-d H:i:s");
		
		//무제한설정
		if("".equals(banner2dto.getUnlimited())) banner2dto.setUnlimited("N");
		if("Y".equals(banner2dto.getUnlimited())) b_l_edate = b_l_sdate;
		//변수초기화 ========================================
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ===============
		String uploaddir =  fileutil.realPath(request, "/data/banner2/");
		Func.folderCreate(uploaddir);
		//==============================================
		
		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "팝업존이미지", "b_main_img_file", 1000 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			banner2dto.setB_main_img(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		// 순서 업데이트
		exdao.executeQuery("UPDATE banner2 SET B_L_CODE = B_L_CODE + 1");
		
		// 세팅
		banner2dto.setB_l_code("1");
		banner2dto.setB_l_sdate(b_l_sdate);
		banner2dto.setB_l_edate(b_l_edate);
		banner2dto.setB_l_wdate(b_l_wdate);
		banner2dto.setB_l_mdate(b_l_mdate);
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		if(!"".equals(builder_dir)){
			banner2dto.setSite_dir(builder_dir);
		}
		
		//insert 제외필드
		banner2dto.setB_l_num(null);
		
		//insert
		banner2dto.setExTableName("banner2");
		exdao.insert(banner2dto);
		
		//작업기록
		Func.writeManagerLog ("팝업존 등록 ["+banner2dto.getB_l_subject()+"]", request);
		
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "/ncms/banner2/list.do";}
		return "redirect:"+prepage;
	}

	/**
	 * @title : 팝업존수정처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		Banner2DTO banner2dto = (Banner2DTO) map.get("banner2dto");
		
		String b_l_num = Func.nvl( request.getParameter("b_l_num") ).trim();
		
		String b_l_sdate_y = Func.nvl(request.getParameter("b_l_sdate_y"));
		String b_l_sdate_m = Func.nvl(request.getParameter("b_l_sdate_m"));
		String b_l_sdate_d = Func.nvl(request.getParameter("b_l_sdate_d"));
		String b_l_sdate_h = Func.nvl(request.getParameter("b_l_sdate_h"));
		String b_l_sdate_n = Func.nvl(request.getParameter("b_l_sdate_n"));
		
		String b_l_edate_y = Func.nvl(request.getParameter("b_l_edate_y"));
		String b_l_edate_m = Func.nvl(request.getParameter("b_l_edate_m"));
		String b_l_edate_d = Func.nvl(request.getParameter("b_l_edate_d"));
		String b_l_edate_h = Func.nvl(request.getParameter("b_l_edate_h"));
		String b_l_edate_n = Func.nvl(request.getParameter("b_l_edate_n"));
		
		//변수초기화 =================================================
		if("".equals(banner2dto.getScrollbars())) banner2dto.setScrollbars("no");
		if("".equals(banner2dto.getToolbar())) banner2dto.setToolbar("no");
		if("".equals(banner2dto.getMenubar())) banner2dto.setMenubar("no");
		if("".equals(banner2dto.getW_width())) banner2dto.setW_width("no");
		if("".equals(banner2dto.getW_height())) banner2dto.setW_height("no");
		if("".equals(banner2dto.getLocations())) banner2dto.setLocations("no");
		if(!"1".equals(banner2dto.getB_l_win())) banner2dto.setB_l_win("0");
		if("".equals(banner2dto.getB_l_page())) banner2dto.setB_l_page("1");

		String b_l_sdate = b_l_sdate_y + "-" + Func.zerofill(b_l_sdate_m, 2, "0") + "-" + Func.zerofill(b_l_sdate_d, 2, "0")
		+ " " + Func.zerofill(b_l_sdate_h, 2, "0") + ":" + Func.zerofill(b_l_sdate_n, 2, "0");
		String b_l_edate = b_l_edate_y + "-" + Func.zerofill(b_l_edate_m, 2, "0") + "-" + Func.zerofill(b_l_edate_d, 2, "0")
		+ " " + Func.zerofill(b_l_edate_h, 2, "0") + ":" + Func.zerofill(b_l_edate_n, 2, "0");

		String b_l_mdate = Func.date("Y-m-d H:i:s");
		
		//무제한설정
		if("".equals(Func.nvl(banner2dto.getUnlimited()))) banner2dto.setUnlimited("N");
		if("Y".equals(banner2dto.getUnlimited())) b_l_edate = b_l_sdate;
		//변수초기화 =================================================
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ================
		String uploaddir =  fileutil.realPath(request, "/data/banner2/");
		Func.folderCreate(uploaddir);
		//==========================================================
		
		// 파일 유효성 검사 및 저장
		banner2dto.setB_main_img("");
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "팝업존이미지", "b_main_img_file", 1000 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			banner2dto.setB_main_img(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		String b_main_img2 = Func.nvl( request.getParameter("b_main_img2") );
		String b_main_img_del = Func.nvl( request.getParameter("b_main_img_del") );
		if(!"".equals(banner2dto.getB_main_img())){  //첨부파일이 있으면
			Func.fileDelete(uploaddir+"/"+b_main_img2);
		}else{
			banner2dto.setB_main_img(b_main_img2);

			if(!"".equals(Func.nvl(b_main_img_del))){ //첨부파일만 삭제
				Func.fileDelete(uploaddir+"/"+b_main_img2);
				banner2dto.setB_main_img("");
			}
		}
		//수정
		banner2dto.setB_l_sdate(b_l_sdate);
		banner2dto.setB_l_edate(b_l_edate);
		banner2dto.setB_l_mdate(b_l_mdate);
		
		//update 제외필드
		banner2dto.setB_l_num(null);
		banner2dto.setSite_dir(null);
		
		//update
		banner2dto.setExTableName("banner2");
		banner2dto.setExWhereQuery("where b_l_num = '"+b_l_num+"'");
		exdao.update(banner2dto);
		
		//작업기록
		Func.writeManagerLog ("팝업존 수정 ["+banner2dto.getB_l_subject()+"]", request);

		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "/ncms/banner2/write.do";}
		return "redirect:"+prepage;
	}

	/**
	 * @title : 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 		= Func.nvl( request.getParameter("status") ).trim(); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String b_l_num		= Func.nvl( request.getParameter("b_l_num") ).trim();  //단일 삭제 사용
		String uploaddir	= fileutil.realPath(request, "/data/banner/");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("banner2");
		selectdto.setExColumn("b_l_subject, b_main_img");
		
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				selectdto.setExWhereQuery("where b_l_num = '"+chk[z]+"'");
				HashMap<String,String>banner = exdao.selectQueryRecord(selectdto);
				Func.fileDelete(uploaddir+"/"+banner.get("b_main_img"));
				exdao.executeQuery("delete from banner2 where b_l_num = '"+chk[z]+"'");

				//작업기록
				Func.writeManagerLog ("팝업존 삭제 ["+banner.get("b_l_subject")+"]", request);
			}
		} else { //단일삭제
			selectdto.setExWhereQuery("where b_l_num = '"+b_l_num+"'");
			HashMap<String,String>banner = exdao.selectQueryRecord(selectdto);
			
			//파일삭제
			Func.fileDelete(uploaddir+"/"+banner.get("b_main_img"));
			exdao.executeQuery("delete from banner2 where b_l_num = '"+b_l_num+"'");
			
			//작업기록
			Func.writeManagerLog ("팝업존 삭제 ["+banner.get("b_l_subject")+"]", request);
		}

	}

	@Override
	public void move(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 순서변경폼
	 * @method : listMove
	 */
	@Override
	public void listMove(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("banner2");
		selectdto.setExColumn("b_l_num, b_l_img, b_l_subject, b_l_sdate, b_l_edate, unlimited, b_main_img");
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			selectdto.setExWhereQuery("where site_dir = '"+builder_dir+"'");
		}
		
		selectdto.setExOrderByQuery("order by B_L_CODE ASC");
		List<HashMap<String,String>>banner2List = exdao.selectQueryTable(selectdto);
		request.setAttribute("banner2List", banner2List);

	}

	/**
	 * @title : 순서변경처리
	 * @method : listMoveOk
	 */
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String b_l_num = chk[z];
			exdao.executeQuery("UPDATE banner2 SET B_L_CODE = '"+Func.cStr(z + 1)+"' WHERE B_L_NUM = '"+b_l_num+"'");
		}
		//작업기록
		Func.writeManagerLog ("팝업존일괄순서변경", request);

	}

	/**
	 * @title : 사용/중지
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		
		String tot_level_chk = request.getParameter("tot_level_chk");
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String b_l_num = chk[z];
			String b_l_chk = tot_level_chk;
			
			exdao.executeQuery("UPDATE banner2 SET B_L_CHK = '"+b_l_chk+"' WHERE B_L_NUM = '"+b_l_num+"'");
			
			//배너명 조회
			selectdto.setExTableName("banner2");
			selectdto.setExColumn("b_l_subject");
			selectdto.setExWhereQuery("where b_l_num = '"+b_l_num+"'");
			String b_l_subject = exdao.selectQueryColumn(selectdto);
			
			//작업기록
			String b_l_chk_str = "사용";
			if("N".equals(b_l_chk)) b_l_chk_str = "중지";
			Func.writeManagerLog ("팝업존상태변경 ["+b_l_subject+"("+b_l_chk_str+")]", request);
			
		}

	}

}
