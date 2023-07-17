package kr.co.nninc.ncms.visual.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.visual.service.VisualDTO;
import kr.co.nninc.ncms.visual.service.VisualService;

/**
 * 메인비쥬얼을 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2018.05.28
 * @version 1.0
 */
@Service("visualService")
public class VisualServiceImpl extends EgovAbstractServiceImpl implements VisualService {

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
	 * @title : 비쥬얼 목록
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String v_search = exdao.filter( request.getParameter("v_search") );
		String v_keyword = exdao.filter( request.getParameter("v_keyword") );
		String v_chk = exdao.filter( request.getParameter("v_chk") );
		String v_sdate = exdao.filter( request.getParameter("v_sdate") );
		String v_edate = exdao.filter( request.getParameter("v_edate") );
		
		SelectDTO selectdto = new SelectDTO();
		
		//비쥬얼리스트
		selectdto.setExTableName("visual");
		selectdto.setExKeyColumn("v_num");	//고유컬럼 설정 필수
		selectdto.setExColumn(new VisualDTO());
		String where_query = "";
		String andOrWhere = " where ";
		if(!"".equals(v_search) && !"".equals(v_keyword)){
			where_query += andOrWhere+" "+v_search+" like '%"+v_keyword+"%'";
			andOrWhere = " and ";
		}
		if(!"".equals(v_chk)){
			where_query += andOrWhere+" v_chk = '"+v_chk+"'";
			andOrWhere = " and ";
		}
		if(!"".equals(v_sdate)){
			where_query += andOrWhere+" v_sdate <= '"+v_sdate+"'";
			andOrWhere = " and ";
		}
		if(!"".equals(v_edate)){
			where_query += andOrWhere+" v_edate >= '"+v_edate+"'";
			andOrWhere = " and ";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("ORDER BY v_code ASC");
		List<HashMap<String,String>>visualList = exdao.selectQueryTable(selectdto);
		request.setAttribute("visualList", visualList);

		//현재url 전송
		Func.getNowPage(request);
	}

	/**
	 * @title : 비쥬얼등록 폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		// 시작 : 날짜시간
		int v_sdate_y = Func.cInt(Func.date("Y"));
		int v_sdate_m = Func.cInt(Func.date("m"));
		int v_sdate_d = Func.cInt(Func.date("d"));
		int v_sdate_h = 0;
		int v_sdate_n = 0;
		
		// 끝 : 날짜시간 (오늘 날짜 + 1달)
		int v_edate_y = Func.cInt(Func.addGetDate2(1, "yyyy"));
		int v_edate_m = Func.cInt(Func.addGetDate2(1, "MM"));
		int v_edate_d = Func.cInt(Func.addGetDate2(1, "dd"));
		int v_edate_h = 23;
		int v_edate_n = 59;
		
		request.setAttribute("v_sdate_y", v_sdate_y);
		request.setAttribute("v_sdate_m", v_sdate_m);
		request.setAttribute("v_sdate_d", v_sdate_d);
		request.setAttribute("v_sdate_h", v_sdate_h);
		request.setAttribute("v_sdate_n", v_sdate_n);

		request.setAttribute("v_edate_y", v_edate_y);
		request.setAttribute("v_edate_m", v_edate_m);
		request.setAttribute("v_edate_d", v_edate_d);
		request.setAttribute("v_edate_h", v_edate_h);
		request.setAttribute("v_edate_n", v_edate_n);
		
	}

	/**
	 * @title : 비쥬얼수정 폼
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String v_num = Func.nvl( request.getParameter("v_num") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		selectdto.setExTableName("visual");
		selectdto.setExKeyColumn("v_num");
		selectdto.setExColumn(new VisualDTO());
		selectdto.setExWhereQuery("where v_num = '"+v_num+"'");
		HashMap<String,String>visualdto = exdao.selectQueryRecord(selectdto);

		// 시작 : 날짜시간
		int v_sdate_y = Func.cInt(Func.date("Y"));
		int v_sdate_m = Func.cInt(Func.date("m"));
		int v_sdate_d = Func.cInt(Func.date("d"));
		int v_sdate_h = 0;
		int v_sdate_n = 0;
		
		String v_sdate = Func.nvl(visualdto.get("v_sdate"));
		if(!"".equals(v_sdate)) {
			String[] v_sdate_arr = v_sdate.split(" ");
			String[] v_sdate_arr2 = v_sdate_arr[0].split("-");
			v_sdate_y = Func.cInt(v_sdate_arr2[0]);
			v_sdate_m = Func.cInt(v_sdate_arr2[1]);
			v_sdate_d = Func.cInt(v_sdate_arr2[2]);
			
			String[] v_sdate_arr3	= v_sdate_arr[1].split(":");
			v_sdate_h = Func.cInt(v_sdate_arr3[0]);
			v_sdate_n = Func.cInt(v_sdate_arr3[1]);
		}
		// 끝 : 날짜시간 (오늘 날짜 + 1달)
		int v_edate_y = Func.cInt(Func.addGetDate2(1, "yyyy"));
		int v_edate_m = Func.cInt(Func.addGetDate2(1, "MM"));
		int v_edate_d = Func.cInt(Func.addGetDate2(1, "dd"));
		int v_edate_h = 23;
		int v_edate_n = 59;
				
		String v_edate = Func.nvl(visualdto.get("v_edate"));
		if(!"".equals(v_edate)) {
			String[] v_edate_arr = v_edate.split(" ");
			String[] v_edate_arr2 = v_edate_arr[0].split("-");
			v_edate_y = Func.cInt(v_edate_arr2[0]);
			v_edate_m = Func.cInt(v_edate_arr2[1]);
			v_edate_d = Func.cInt(v_edate_arr2[2]);
			
			String[] v_edate_arr3	= v_edate_arr[1].split(":");
			v_edate_h = Func.cInt(v_edate_arr3[0]);
			v_edate_n = Func.cInt(v_edate_arr3[1]);
		}
		request.setAttribute("v_sdate_y", v_sdate_y);
		request.setAttribute("v_sdate_m", v_sdate_m);
		request.setAttribute("v_sdate_d", v_sdate_d);
		request.setAttribute("v_sdate_h", v_sdate_h);
		request.setAttribute("v_sdate_n", v_sdate_n);

		request.setAttribute("v_edate_y", v_edate_y);
		request.setAttribute("v_edate_m", v_edate_m);
		request.setAttribute("v_edate_d", v_edate_d);
		request.setAttribute("v_edate_h", v_edate_h);
		request.setAttribute("v_edate_n", v_edate_n);
		request.setAttribute("filename", visualdto.get("v_img"));
		request.setAttribute("visual", visualdto);		

		//작업기록
		Func.writeManagerLog ("비쥬얼 상세보기 ["+visualdto.get("v_subject")+"]", request);
	}

	/**
	 * @title : 비쥬얼 등록처리
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		VisualDTO visualdto = (VisualDTO) Func.requestAll(map.get("visualdto"));

		String v_sdate_y = Func.nvl(request.getParameter("v_sdate_y"));
		String v_sdate_m = Func.nvl(request.getParameter("v_sdate_m"));
		String v_sdate_d = Func.nvl(request.getParameter("v_sdate_d"));
		String v_sdate_h = Func.nvl(request.getParameter("v_sdate_h"));
		String v_sdate_n = Func.nvl(request.getParameter("v_sdate_n"));
		
		String v_edate_y = Func.nvl(request.getParameter("v_edate_y"));
		String v_edate_m = Func.nvl(request.getParameter("v_edate_m"));
		String v_edate_d = Func.nvl(request.getParameter("v_edate_d"));
		String v_edate_h = Func.nvl(request.getParameter("v_edate_h"));
		String v_edate_n = Func.nvl(request.getParameter("v_edate_n"));
		
		String v_sdate = v_sdate_y + "-" + Func.zerofill(v_sdate_m, 2, "0") + "-" + Func.zerofill(v_sdate_d, 2, "0")
		+ " " + Func.zerofill(v_sdate_h, 2, "0") + ":" + Func.zerofill(v_sdate_n, 2, "0");
		String v_edate = v_edate_y + "-" + Func.zerofill(v_edate_m, 2, "0") + "-" + Func.zerofill(v_edate_d, 2, "0")
		+ " " + Func.zerofill(v_edate_h, 2, "0") + ":" + Func.zerofill(v_edate_n, 2, "0");
		
		String v_wdate = Func.date("Y-m-d H:i:s");
		String v_mdate = Func.date("Y-m-d H:i:s");
		if("".equals(visualdto.getUnlimited())) visualdto.setUnlimited("N");
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ================
		String uploaddir =  fileutil.realPath(request, "/data/visual/");
		Func.folderCreate(uploaddir);
		//==========================================================
		
		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, true, "비쥬얼이미지", "v_img_file", 10 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			visualdto.setV_img(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		//순서 업데이트
		exdao.executeQuery("UPDATE visual SET v_code = v_code + 1");
		
		// 세팅
		visualdto.setV_code("1");
		visualdto.setV_sdate(v_sdate);
		if("Y".equals(visualdto.getUnlimited())) {
			visualdto.setV_edate("");
		}else {
			visualdto.setV_edate(v_edate);
		}
		visualdto.setV_wdate(v_wdate);
		visualdto.setV_mdate(v_mdate);
		
		//insert 제외필드
		visualdto.setV_num(null);
		
		//insert
		visualdto.setExTableName("visual");
		exdao.insert(visualdto);
		
		//작업기록
		Func.writeManagerLog ("비쥬얼 생성 ["+visualdto.getV_subject()+"]", request);

		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "/ncms/visual/list.do";}
		return "redirect:"+prepage;
	}

	/**
	 * @title : 비쥬얼수정처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		VisualDTO visualdto = (VisualDTO) map.get("visualdto");
		
		String v_num = Func.nvl( request.getParameter("v_num") ).trim();

		String v_sdate_y = Func.nvl(request.getParameter("v_sdate_y"));
		String v_sdate_m = Func.nvl(request.getParameter("v_sdate_m"));
		String v_sdate_d = Func.nvl(request.getParameter("v_sdate_d"));
		String v_sdate_h = Func.nvl(request.getParameter("v_sdate_h"));
		String v_sdate_n = Func.nvl(request.getParameter("v_sdate_n"));
		
		String v_edate_y = Func.nvl(request.getParameter("v_edate_y"));
		String v_edate_m = Func.nvl(request.getParameter("v_edate_m"));
		String v_edate_d = Func.nvl(request.getParameter("v_edate_d"));
		String v_edate_h = Func.nvl(request.getParameter("v_edate_h"));
		String v_edate_n = Func.nvl(request.getParameter("v_edate_n"));
		
		visualdto.setV_memo(Func.InputValue2(request.getParameter("v_memo")));
		System.out.println(Func.InputValue2(request.getParameter("v_memo"))+">>>v_memo");
		
		//변수초기화 =================================================
		String v_sdate = v_sdate_y + "-" + Func.zerofill(v_sdate_m, 2, "0") + "-" + Func.zerofill(v_sdate_d, 2, "0")
		+ " " + Func.zerofill(v_sdate_h, 2, "0") + ":" + Func.zerofill(v_sdate_n, 2, "0");
		String v_edate = v_edate_y + "-" + Func.zerofill(v_edate_m, 2, "0") + "-" + Func.zerofill(v_edate_d, 2, "0")
		+ " " + Func.zerofill(v_edate_h, 2, "0") + ":" + Func.zerofill(v_edate_n, 2, "0");

		String v_mdate = Func.date("Y-m-d H:i:s");
		
		//무제한설정
		if("".equals(Func.nvl(visualdto.getUnlimited()).trim())) visualdto.setUnlimited("N");
		if("Y".equals(visualdto.getUnlimited())) v_edate = v_sdate;
		//변수초기화 =================================================
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ===============
		String uploaddir =  fileutil.realPath(request, "/data/visual/");
		Func.folderCreate(uploaddir);
		//==============================================
		
		// 파일 유효성 검사 및 저장
		String v_img2 = Func.nvl( request.getParameter("v_img2") ).trim();
		String v_img_del = Func.nvl( request.getParameter("v_img_del") ).trim();
		visualdto.setV_img("");
		
		boolean img_chk = false;
		if(!"".equals(v_img_del)){
			img_chk = true;
		}
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, img_chk, "비쥬얼이미지", "v_img_file", 10 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			visualdto.setV_img(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		if(!"".equals(visualdto.getV_img())){  //첨부파일이 있으면
			Func.fileDelete(uploaddir+"/"+v_img2);
		}else{
			visualdto.setV_img(v_img2);

			if(!"".equals(v_img_del)){ //첨부파일만 삭제
				Func.fileDelete(uploaddir+"/"+v_img2);
				visualdto.setV_img("");
			}
		}
		
		//필드 세팅
		visualdto.setV_sdate(v_sdate);
		if("Y".equals(visualdto.getUnlimited())) {
			visualdto.setV_edate("");
		}else {
			visualdto.setV_edate(v_edate);
		}
		visualdto.setV_mdate(v_mdate);

		//update 제외필드
		visualdto.setV_num(null);
		
		//update 
		visualdto.setExTableName("visual");
		visualdto.setExWhereQuery("where v_num = '"+v_num+"'");
		exdao.update(visualdto);
		
		//작업기록
		Func.writeManagerLog ("비쥬얼 수정 ["+visualdto.getV_subject()+"]", request);
		
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "/ncms/visual/list.do";}
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

		String status 		= Func.nvl(request.getParameter("status") ).trim();	//값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String v_num		= Func.nvl(request.getParameter("v_num") ).trim();	//단일 삭제 사용
		String uploaddir	= fileutil.realPath(request, "/data/visual/");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("visual");
		selectdto.setExColumn("v_subject, v_img");
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				selectdto.setExWhereQuery("where v_num = '"+chk[z]+"'");
				HashMap<String, String>visual = exdao.selectQueryRecord(selectdto);
				String v_subject = Func.nvl( visual.get("v_subject") );
				String v_img = Func.nvl( visual.get("v_img") );
				exdao.executeQuery("DELETE FROM visual WHERE v_num = '"+chk[z]+"'");

				Func.fileDelete(uploaddir+"/"+v_img);

				//작업기록
				Func.writeManagerLog ("비쥬얼 삭제 ["+v_subject+"]", request);
			}
		} else { //단일삭제
			selectdto.setExWhereQuery("where v_num = '"+v_num+"'");
			HashMap<String, String>visual = exdao.selectQueryRecord(selectdto);
			String v_subject = Func.nvl( visual.get("v_subject") );
			String v_img = Func.nvl( visual.get("v_img") );
			exdao.executeQuery("DELETE FROM visual WHERE v_num = '"+v_num+"'");

			Func.fileDelete(uploaddir+"/"+v_img);

			//작업기록
			Func.writeManagerLog ("비쥬얼 삭제 ["+v_subject+"]", request);
		}
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
		
		selectdto.setExTableName("visual");
		selectdto.setExColumn("v_num, v_img, v_subject, v_sdate, v_edate, unlimited");
		selectdto.setExOrderByQuery("ORDER BY v_code ASC");
		List<HashMap<String,String>>visualList = exdao.selectQueryTable(selectdto);
		request.setAttribute("visualList", visualList);
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
			String v_num = chk[z];
			String v_code = Func.cStr(z + 1);
			exdao.executeQuery("UPDATE visual SET v_code = '"+v_code+"' WHERE v_num = '"+v_num+"'");
		}
		
		//작업기록
		Func.writeManagerLog ("비쥬얼 일괄순서변경", request);
	}

	/**
	 * @title : 사용/중지
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String tot_level_chk = request.getParameter("tot_level_chk");
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("visual");
		selectdto.setExColumn("v_subject");
		for (int z = 0; z <= chk.length - 1; z++) {
			String v_num = chk[z];
			String v_chk = tot_level_chk;
			
			exdao.executeQuery("UPDATE visual SET v_chk = '"+v_chk+"' WHERE v_num = '"+v_num+"'");
			
			selectdto.setExWhereQuery("where v_num = '"+v_num+"'");
			String v_subject = exdao.selectQueryColumn(selectdto);
			
			//작업기록
			String v_chk_str = "사용";
			if("N".equals(v_chk)) v_chk_str = "중지";
			Func.writeManagerLog ("비쥬얼 상태변경 ["+v_subject+"("+v_chk_str+")]", request);
		}

	}

	@Override
	public void down(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

}
