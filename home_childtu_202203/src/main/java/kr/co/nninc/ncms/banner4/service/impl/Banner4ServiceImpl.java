package kr.co.nninc.ncms.banner4.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.banner4.service.Banner4DTO;
import kr.co.nninc.ncms.banner4.service.Banner4Service;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;


/**
 * 상단팝업을 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.05.16
 * @version 1.0
 */
@Service("banner4Service")
public class Banner4ServiceImpl extends EgovAbstractServiceImpl implements Banner4Service {

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
	 * @title : 상단팝업등록 폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
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

	}

	/**
	 * @title : 상단팝업목록
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
		
		
		selectdto.setExTableName("banner4");
		selectdto.setExKeyColumn("b_l_num");
		selectdto.setExColumn(new Banner4DTO());
		
		String whereQuery = "";
		if(!"".equals(v_keyword) && !"".equals(v_search)){
			whereQuery += "where "+v_search+" like '%"+v_keyword+"%' ";
		}
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		if(!"".equals(builder_dir)){
			whereQuery += " and site_dir = '"+builder_dir+"' ";
		}
		
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY b_l_code asc");
		List<HashMap<String,String>> banner4List = exdao.selectQueryTable(selectdto);
		// setAttribute
		request.setAttribute("banner4List", banner4List);
	
		//현재경로 전송
		Func.getNowPage(request);
		
	}

	/**
	 * @title : 상단팝업수정 폼
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String b_l_num = Func.nvl( request.getParameter("b_l_num") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("banner4");
		selectdto.setExKeyColumn("b_l_num");	//clob자료형을 조회하기위해서 키컬럼을 추가
		selectdto.setExColumn(new Banner4DTO());
		selectdto.setExWhereQuery("where b_l_num = '"+b_l_num+"'");
		HashMap<String,String>banner4 = exdao.selectQueryRecord(selectdto);
		
		// 시작 : 날짜시간
		int b_l_sdate_y = Func.cInt(Func.date("Y"));
		int b_l_sdate_m = Func.cInt(Func.date("m"));
		int b_l_sdate_d = Func.cInt(Func.date("d"));
		int b_l_sdate_h = 0;
		int b_l_sdate_n = 0;
		
		String b_l_sdate = Func.nvl(banner4.get("b_l_sdate"));
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
		
		String b_l_edate = Func.nvl(banner4.get("b_l_edate"));
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
		request.setAttribute("banner4", banner4);
		
		
		//작업기록
		Func.writeManagerLog ("상단팝업 상세보기 ["+banner4.get("b_l_subject")+"]", request);
		
	}

	/**
	 * @title : 상단팝업 등록처리
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		Banner4DTO banner4dto = (Banner4DTO) map.get("banner4dto");
		
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
		
		String b_l_sdate = b_l_sdate_y + "-" + Func.zerofill(b_l_sdate_m, 2, "0") + "-" + Func.zerofill(b_l_sdate_d, 2, "0")
		+ " " + Func.zerofill(b_l_sdate_h, 2, "0") + ":" + Func.zerofill(b_l_sdate_n, 2, "0");
		String b_l_edate = b_l_edate_y + "-" + Func.zerofill(b_l_edate_m, 2, "0") + "-" + Func.zerofill(b_l_edate_d, 2, "0")
		+ " " + Func.zerofill(b_l_edate_h, 2, "0") + ":" + Func.zerofill(b_l_edate_n, 2, "0");
		
		String b_l_wdate = Func.date("Y-m-d H:i:s");
		String b_l_mdate = Func.date("Y-m-d H:i:s");
		if("".equals(Func.nvl( request.getParameter("unlimited") ))) banner4dto.setUnlimited("N");
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ================
		String uploaddir =  fileutil.realPath(request, "/data/banner4/");
		Func.folderCreate(uploaddir);
		//==========================================================
		
		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, true, "상단팝업이미지", "b_l_img_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			banner4dto.setB_l_img(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		//순서업데이트
		exdao.executeQuery("UPDATE banner4 SET B_L_CODE = B_L_CODE + 1");
		
		// 세팅
		banner4dto.setB_l_code("1");
		banner4dto.setB_l_sdate(b_l_sdate);
		banner4dto.setB_l_edate(b_l_edate);
		banner4dto.setB_l_wdate(b_l_wdate);
		banner4dto.setB_l_mdate(b_l_mdate);
		
		//insert 제외 필드
		banner4dto.setB_l_num(null);
		
		//site 디렉토리 저장
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			banner4dto.setSite_dir(builder_dir);
		}
		
		//insert
		banner4dto.setExTableName("banner4");
		exdao.insert(banner4dto);
		
		//작업기록
		Func.writeManagerLog ("상단팝업 생성 ["+banner4dto.getB_l_subject()+"]", request);
		
		String prepage = Func.nvl(request.getParameter("prepage")).trim();
		if("".equals(prepage)){prepage= "/ncms/banner4/list.do";}
		return "redirect:"+prepage;
	}

	/**
	 * @title : 상단팝업수정처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		Banner4DTO banner4dto = (Banner4DTO) map.get("banner4dto");
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
		String b_l_sdate = b_l_sdate_y + "-" + Func.zerofill(b_l_sdate_m, 2, "0") + "-" + Func.zerofill(b_l_sdate_d, 2, "0")
		+ " " + Func.zerofill(b_l_sdate_h, 2, "0") + ":" + Func.zerofill(b_l_sdate_n, 2, "0");
		String b_l_edate = b_l_edate_y + "-" + Func.zerofill(b_l_edate_m, 2, "0") + "-" + Func.zerofill(b_l_edate_d, 2, "0")
		+ " " + Func.zerofill(b_l_edate_h, 2, "0") + ":" + Func.zerofill(b_l_edate_n, 2, "0");

		String b_l_mdate = Func.date("Y-m-d H:i:s");
		
		//무제한설정
		String unlimited = Func.nvl( request.getParameter("unlimited") );
		if("".equals(unlimited)) banner4dto.setUnlimited("N");
		if("Y".equals(unlimited)) b_l_edate = b_l_sdate;
		//변수초기화 =================================================
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ===============
		String uploaddir =  fileutil.realPath(request, "/data/banner4/");
		Func.folderCreate(uploaddir);
		//==============================================
		
		boolean img_chk = false;
		String b_l_img_del = Func.nvl( request.getParameter("b_l_img_del") ).trim();
		if(!"".equals(b_l_img_del)){
			img_chk = true;
		}
		// 파일 유효성 검사 및 저장
		banner4dto.setB_l_img("");
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, img_chk, "상단팝업이미지", "b_l_img_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			banner4dto.setB_l_img(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		String b_l_img2 = Func.nvl( request.getParameter("b_l_img2") );
		if(!"".equals(banner4dto.getB_l_img())){  //첨부파일이 있으면
			Func.fileDelete(uploaddir+"/"+b_l_img2);
		}else{
			banner4dto.setB_l_img(b_l_img2);

			if(!"".equals(Func.nvl(b_l_img_del))){ //첨부파일만 삭제
				Func.fileDelete(uploaddir+"/"+b_l_img2);
				banner4dto.setB_l_img("");
			}
		}
		
		//필드 세팅
		banner4dto.setB_l_sdate(b_l_sdate);
		banner4dto.setB_l_edate(b_l_edate);
		banner4dto.setB_l_mdate(b_l_mdate);
		if(banner4dto.getB_l_win() == null){banner4dto.setB_l_win("");}
		
		//수정제외 필드
		banner4dto.setB_l_num(null);
		banner4dto.setSite_dir(null);
		
		
		//수정
		banner4dto.setExTableName("banner4");
		banner4dto.setExWhereQuery("where b_l_num = '"+b_l_num+"'");
		exdao.update(banner4dto);
		
		//작업기록
		Func.writeManagerLog ("상단팝업 수정 ["+banner4dto.getB_l_subject()+"]", request);
		
		String prepage = Func.nvl(request.getParameter("prepage")).trim();
		if("".equals(prepage)){prepage= "/ncms/banner4/list.do";}
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
		String b_l_num		= request.getParameter("b_l_num");  //단일 삭제 사용
		String uploaddir	= fileutil.realPath(request, "/data/banner4/");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("banner4");
		selectdto.setExColumn("b_l_subject, b_l_img");
		
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				selectdto.setExWhereQuery("where b_l_num = '"+chk[z]+"'");
				HashMap<String,String>banner4 = exdao.selectQueryRecord(selectdto);
				Func.fileDelete(uploaddir+"/"+banner4.get("b_l_img"));
				exdao.executeQuery("delete from banner4 where b_l_num = '"+chk[z]+"'");

				//작업기록
				Func.writeManagerLog ("상단팝업 삭제 ["+banner4.get("b_l_subject")+"]", request);
			}
		} else { //단일삭제
			selectdto.setExWhereQuery("where b_l_num = '"+b_l_num+"'");
			HashMap<String,String>banner4 = exdao.selectQueryRecord(selectdto);
			
			//파일삭제
			Func.fileDelete(uploaddir+"/"+banner4.get("b_l_img"));
			exdao.executeQuery("delete from banner4 where b_l_num = '"+b_l_num+"'");
			
			//작업기록
			Func.writeManagerLog ("상단팝업 삭제 ["+banner4.get("b_l_subject")+"]", request);
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
		selectdto.setExTableName("banner4");
		selectdto.setExColumn("b_l_num, b_l_img, b_l_subject, b_l_sdate, b_l_edate, unlimited");
		selectdto.setExOrderByQuery("order by B_L_CODE ASC");
		
		String builder_dir = (String)request.getAttribute("BUILDER_DIR");
		if(!"".equals(builder_dir)){
			selectdto.setExWhereQuery("where site_dir = '"+builder_dir+"'");
		}
		
		List<HashMap<String,String>>banner4List = exdao.selectQueryTable(selectdto);
		request.setAttribute("banner4List", banner4List);
		
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
			exdao.executeQuery("UPDATE banner4 SET B_L_CODE = '"+Func.cStr(z + 1)+"' WHERE B_L_NUM = '"+b_l_num+"'");
		}
		//작업기록
		Func.writeManagerLog ("상단팝업 일괄순서변경", request);
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
			
			exdao.executeQuery("UPDATE banner4 SET B_L_CHK = '"+b_l_chk+"' WHERE B_L_NUM = '"+b_l_num+"'");
			
			//상단팝업명 조회
			selectdto.setExTableName("banner4");
			selectdto.setExColumn("b_l_subject");
			selectdto.setExWhereQuery("where b_l_num = '"+b_l_num+"'");
			String b_l_subject = exdao.selectQueryColumn(selectdto);
			
			//작업기록
			String b_l_chk_str = "사용";
			if("N".equals(b_l_chk)) b_l_chk_str = "중지";
			Func.writeManagerLog ("상단팝업 상태변경 ["+b_l_subject+"("+b_l_chk_str+")]", request);
			
		}
	}

}
