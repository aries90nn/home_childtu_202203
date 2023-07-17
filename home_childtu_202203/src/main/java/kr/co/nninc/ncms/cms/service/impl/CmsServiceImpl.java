package kr.co.nninc.ncms.cms.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board_config.service.BoardConfigDTO;
import kr.co.nninc.ncms.board_config.service.BoardConfigService;
import kr.co.nninc.ncms.cms.service.CmsCssHistoryDTO;
import kr.co.nninc.ncms.cms.service.CmsDTO;
import kr.co.nninc.ncms.cms.service.CmsHistoryDTO;
import kr.co.nninc.ncms.cms.service.CmsService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.site_info.service.SiteConfigDTO;


/**
 * cms를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.02.15
 * @version 1.1
 */
@Service("cmsService")
public class CmsServiceImpl extends EgovAbstractServiceImpl implements CmsService {
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/** 게시판관리서비스 */
	@Resource(name = "boardConfigService")
	private BoardConfigService boardConfigService;
	
	/** FileUtil */
	@Resource(name="fileutil")
	private FileUtil fileutil;
	
	/**
	 * @title : 코드 등록/수정 폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//변수초기화
		int ct_idx = Func.cInt((String) request.getParameter("ct_idx")) ;
		int ct_ref = 0;
		int ct_depth = 1;
		String ct_codeno_ref = "C0;";
		int max_depth_option = 5;
		
		//select전용 dto
		CmsDTO selectdto = new CmsDTO();
		
		//상위 idx값을 구한다.
		if(ct_idx != 0) {
			selectdto.setExTableName("homepage_menu");
			selectdto.setExColumn("ct_idx, ct_depth, ct_codeno");
			selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
			HashMap<String,String>rs = exdao.selectQueryRecord(selectdto);
			ct_ref				= Func.cInt(rs.get("ct_idx"));
			ct_depth			= Func.cInt(rs.get("ct_depth")) + 1;
			ct_codeno_ref		= rs.get("ct_codeno");
		}
		//현재 메뉴경로
		List<HashMap> refMenuList = new ArrayList<HashMap>();
		if(!"".equals(ct_codeno_ref)) {
			String[] ref_codeno_arr = ct_codeno_ref.split("\\;");
			for(int z=1; z<=ref_codeno_arr.length-1; z++) {
				String ct_idx_str = ref_codeno_arr[z].replaceAll("C","");
				ct_idx_str = ct_idx_str.replaceAll(";","");
				
				if(!"".equals(ct_idx_str)) {
					selectdto = new CmsDTO();
					selectdto.setExTableName("homepage_menu");
					selectdto.setExColumn("ct_idx, ct_name");
					selectdto.setExWhereQuery("where ct_idx = '"+ct_idx_str+"'");
					HashMap<String,String>rs = exdao.selectQueryRecord(selectdto);
					if(rs.size() > 0){
						HashMap hm = new HashMap();
						hm.put("ct_idx", ct_idx_str);
						hm.put("ct_name", Func.cStr(rs.get("ct_name")) );
						refMenuList.add(hm);
					}
				}
			}
		}
		request.setAttribute("refMenuList", refMenuList);
		
		//메뉴리스트 가져오기
		String builder_dir = (String)request.getAttribute("BUILDER_DIR");
		List<HashMap<String,Object>>menuList = this.getMenuList(Func.cStr(ct_ref), ct_depth, ct_depth, builder_dir);
		
		
		//값리턴하기
		request.setAttribute("ct_idx", ct_idx);
		request.setAttribute("ct_ref", ct_ref);
		request.setAttribute("ct_depth", ct_depth);
		request.setAttribute("ct_codeno_ref", ct_codeno_ref);
		request.setAttribute("max_depth_option", max_depth_option);
		request.setAttribute("menuList", menuList);
		
		//현재 url
		Func.getNowPage(request);
	}

	/**
	 * @title : CMS관리 등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		CmsDTO cms = (CmsDTO) exdao.emptyToNull( map.get("cmsdto") );
		
		//select 전용 dto 
		CmsDTO selectdto = new CmsDTO();
		
		//1. 마지막 순서코드 가져오기
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("max(ct_code)");
		int max_code = Func.cInt( exdao.selectQueryColumn(selectdto) ) + 1;
		
		//2. 등록처리
		String ct_tab = Func.nvl(cms.getCt_tab());
		String ct_wdate = Func.date("Y-m-d H:i:s");
		cms.setCt_code(Func.cStr(max_code));
		cms.setCt_wdate(ct_wdate);
		if("".equals(ct_tab)) cms.setCt_tab("N");
		
		//3. 빌더사이트 디렉토리 저장
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		if(!"".equals(builder_dir)){
			cms.setCt_site_dir(builder_dir);
		}
		
		cms.setExTableName("homepage_menu");
		exdao.insert(cms);
		
		//3. 최대 일련번호 가져오기
		selectdto = new CmsDTO();
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("max(ct_idx)");
		int max_ct_idx = Func.cInt( exdao.selectQueryColumn(selectdto) );
		
		//4. 메뉴값 적용
		String ct_codeno = Func.nvl(request.getParameter("ct_codeno_ref"))+"C"+max_ct_idx+";";
		String sql = "update homepage_menu set ct_codeno = '"+ct_codeno+"' where ct_idx = '"+max_ct_idx+"'";
		exdao.executeQuery(sql);
		
		//5. json 메뉴파일 생성
		this.createMenuJson(request);
		
		//6. 실제 URL 다시 업데이트
		String ct_ref = Func.nvl(cms.getCt_ref()).equals("0") ? "" : Func.nvl(cms.getCt_ref());
		request.setAttribute("ct_ref", ct_ref);
		
	}

	/**
	 * @title : CMS관리 수정처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		CmsDTO cms = (CmsDTO) exdao.emptyToNull(map.get("cmsdto"));

		String ct_idx = Func.nvl( request.getParameter("ct_idx") );
		
		//1. 수정처리
		String ct_mdate = Func.date("Y-m-d H:i:s");
		cms.setCt_mdate(ct_mdate);
		cms.setExTableName("homepage_menu");
		cms.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		exdao.update(cms);
		
		//2. json메뉴 생성
		this.createMenuJson(request);
		
		//3. 실제 URL 다시 업데이트
		String ct_ref = Func.nvl(cms.getCt_ref()).equals("0") ? "" : Func.nvl(cms.getCt_ref());
		request.setAttribute("ct_ref", ct_ref);
	}

	/**
	 * @title : CMS관리 내용가져오기(사용자용)
	 * @method : user_contents
	 */
	@Override
	public String user_contents(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		int idx = request.getParameter("idx") == null ? 0 : Integer.parseInt(request.getParameter("idx"));
		String ss_g_num = Func.getSession(request, "ss_g_num"); //세션 없으면 비회원
		if("".equals(Func.nvl(ss_g_num)) || ss_g_num == null) ss_g_num = "2";
		
		String a_num = Func.nvl( request.getParameter("a_num") ).trim();
		
		CmsDTO selectdto = new CmsDTO();
		
		// 상세내용보기
		selectdto.setExTableName("homepage_menu");
		selectdto.setExKeyColumn("ct_idx");
		selectdto.setExColumn("ct_pagetype, ct_content, ct_anum, ct_board_header, ct_board_footer, ct_header, ct_footer");
		if(!"".equals(a_num)){
			selectdto.setExWhereQuery("where ct_anum = '"+a_num+"'");
		}else{
			selectdto.setExWhereQuery("where ct_idx = '"+idx+"'");
		}
		HashMap<String, String>cms = exdao.selectQueryRecord(selectdto);
		request.setAttribute("cms", cms);
		
		//동아리게시판 정보전달
		LinkedHashMap<String, String> clubList = CommonConfig.getBoardMap("club_list");
		if(clubList != null){
			request.setAttribute("clubList", clubList);
		}
		
		return "/site/contents";
	}

	/**
	 * @title : CMS관리 내용관리
	 * @method : contents
	 */
	@Override
	public void contents(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String ct_idx = Func.nvl( request.getParameter("ct_idx") );
		
		//select 전용 dto
		CmsDTO selectdto = new CmsDTO();
		
		// 상세내용보기
		selectdto.setExTableName("homepage_menu");
		selectdto.setExKeyColumn("ct_idx");	//pk 키 설정
		selectdto.setExColumn(new CmsDTO());
		selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		HashMap<String,String>cms = exdao.selectQueryRecord(selectdto);
		request.setAttribute("cms", cms);
		
		//부모메뉴가 탭메뉴사용중인지
		selectdto = new CmsDTO();
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("ct_tab");
		selectdto.setExWhereQuery("where ct_idx = '"+exdao.filter(cms.get("ct_ref"))+"'");
		String ct_tab_ref = exdao.selectQueryColumn(selectdto);
		request.setAttribute("ct_tab_ref", ct_tab_ref);
		
		//게시판리스트
		selectdto = new CmsDTO();
		selectdto.setExTableName("board_config a LEFT OUTER JOIN homepage_menu b ON a.a_num=b.ct_anum");
		selectdto.setExColumn("a.a_num as a_num, a.a_bbsname as a_bbsname, a.a_tablename as a_tablename, b.ct_name as ct_name");
		selectdto.setExOrderByQuery("ORDER BY a_code, a.a_bbsname");
		
		//사이트관리자는 해당 게시판만 조회
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			selectdto.setExWhereQuery("where a.a_site_dir = '"+builder_dir+"'");
		}
		
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
		request.setAttribute("boardList", boardList);
		
		// 작업내역 리스트
		selectdto = new CmsDTO();
		selectdto.setExTableName("homepage_menu_history");
		selectdto.setExColumn("cth_idx, ct_pagetype, ct_wdate");
		selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		selectdto.setExOrderByQuery("ORDER BY ct_wdate DESC");
		List<HashMap<String,String>>historyList = exdao.selectQueryTable(selectdto);
		request.setAttribute("historyList", historyList);
		
		//회원그룹 조회하기
		selectdto = new CmsDTO();
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_num, g_menuname");
		selectdto.setExOrderByQuery("ORDER BY g_code");
		List<HashMap<String,String>>membergroupList = exdao.selectQueryTable(selectdto);
		request.setAttribute("membergroupList", membergroupList);
		
		
	}

	
	/**
	 * @title : CMS관리 내용등록처리
	 * @method : contentsOk
	 */
	@Override
	public String contentsOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		//CmsDTO cms = (CmsDTO) exdao.emptyToNull( map.get("cmsdto") );
		CmsDTO cms = (CmsDTO) map.get("cmsdto");
		String ss_m_id						= (String) Func.getSession(request, "ss_m_id");
		
		String ct_idx = Func.nvl( request.getParameter("ct_idx") );
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ===============
		String uploaddir =  fileutil.realPath(request, "/data/cms/");
		Func.folderCreate(uploaddir);
		//==============================================
		
		// 1. 파일 유효성 검사 및 저장
		String file_name = "";
		String error_msg = "";
		cms.setCt_img_on("");
		cms.setCt_img_off("");
		cms.setCt_img_on2("");
		cms.setCt_img_off2("");
		if(cms.getCt_img_status() == null){
			cms.setCt_img_status("");
		}
		
		// 2. 상단 마우스오버 이미지
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "상단 마우스오버 이미지", "ct_img_on_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		file_name = Func.nvl(file.getFile_name());
		error_msg = Func.nvl(file.getError_msg());
		
		if(!"".equals(file_name)) {
			cms.setCt_img_on(file_name);
		}else if(!"".equals(error_msg)){
			return messageService.backMsg(model, error_msg);
		}
		
		// 3. 상단 마우스아웃 이미지
		FileDTO file2 = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "상단 마우스아웃 이미지", "ct_img_off_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		file_name = Func.nvl(file2.getFile_name());
		error_msg = Func.nvl(file2.getError_msg());
		
		if(!"".equals(file_name)) {
			cms.setCt_img_off(file_name);
		}else if(!"".equals(error_msg)){
			return messageService.backMsg(model, error_msg);
		}
		
		// 4. 레프트 마우스오버 이미지
		FileDTO file3 = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "레프트 마우스오버 이미지", "ct_img_on2_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		file_name = Func.nvl(file3.getFile_name());
		error_msg = Func.nvl(file3.getError_msg());
		
		if(!"".equals(file_name)) {
			cms.setCt_img_on2(file_name);
		}else if(!"".equals(error_msg)){
			return messageService.backMsg(model, error_msg);
		}
		
		// 5. 레프트 마우스아웃 이미지
		FileDTO file4 = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "레프트 마우스아웃 이미지", "ct_img_off2_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		file_name = Func.nvl(file4.getFile_name());
		error_msg = Func.nvl(file4.getError_msg());
		
		if(!"".equals(file_name)) {
			cms.setCt_img_off2(file_name);
		}else if(!"".equals(error_msg)){
			return messageService.backMsg(model, error_msg);
		}
		
		// 6. 이미지수정확인
		String ct_img_on = cms.getCt_img_on();
		String ct_img_on_del = Func.nvl( request.getParameter("ct_img_on_del") );
		String ct_img_on_org = Func.nvl( request.getParameter("ct_img_on_org") );
		if("".equals(ct_img_on)){
			if("".equals(ct_img_on_del)){
				ct_img_on = ct_img_on_org;
			}else{
				Func.fileDelete(uploaddir+"/"+ct_img_on_org);
			}
		}else{
			if(!"".equals(ct_img_on_org)){
				Func.fileDelete(uploaddir+"/"+ct_img_on_org);
			}
		}
		String ct_img_off = cms.getCt_img_off();
		String ct_img_off_del = Func.nvl( request.getParameter("ct_img_off_del") );
		String ct_img_off_org = Func.nvl( request.getParameter("ct_img_off_org") );
		if("".equals(ct_img_off)){
			if("".equals(ct_img_off_del)){
				ct_img_off = ct_img_off_org;
			}else{
				Func.fileDelete(uploaddir+"/"+ct_img_off_org);
			}
		}else{
			if(!"".equals(ct_img_off_org)){
				Func.fileDelete(uploaddir+"/"+ct_img_off_org);
			}
		}
		String ct_img_on2 = cms.getCt_img_on2();
		String ct_img_on2_del = Func.nvl( request.getParameter("ct_img_on2_del") );
		String ct_img_on2_org = Func.nvl( request.getParameter("ct_img_on2_org") );
		if("".equals(ct_img_on2)){
			if("".equals(ct_img_on2_del)){
				ct_img_on2 = ct_img_on2_org;
			}else{
				Func.fileDelete(uploaddir+"/"+ct_img_on2_org);
			}
		}else{
			if(!"".equals(ct_img_on2_org)){
				Func.fileDelete(uploaddir+"/"+ct_img_on2_org);
			}
		}
		String ct_img_off2 = cms.getCt_img_off2();
		String ct_img_off2_del = Func.nvl( request.getParameter("ct_img_off2_del") );
		String ct_img_off2_org = Func.nvl( request.getParameter("ct_img_off2_org") );
		if("".equals(ct_img_off2)){
			if("".equals(ct_img_off2_del)){
				ct_img_off2 = ct_img_off2_org;
			}else{
				Func.fileDelete(uploaddir+"/"+ct_img_off2_org);
			}
		}else{
			if(!"".equals(ct_img_off2_org)){
				Func.fileDelete(uploaddir+"/"+ct_img_off2_org);
			}
		}
		cms.setCt_img_on(ct_img_on);
		cms.setCt_img_off(ct_img_off);
		cms.setCt_img_on2(ct_img_on2);
		cms.setCt_img_off2(ct_img_off2);
		
		// 7. 게시판생성
		String board_type = Func.nvl( request.getParameter("board_type") );
		if("B".equals(cms.getCt_pagetype())) {
			// 7-1. 새로생성
			if("new".equals(board_type)) { 
				String ct_anum = this.make_board(model, cms.getCt_idx());
				cms.setCt_anum(ct_anum);
			}
			//링크URL
			String menu_url = "/board/list.do?a_num="+cms.getCt_anum();
			cms.setCt_menu_url(menu_url); //실제 URL
			
			String ct_header_b = Func.nvl(request.getParameter("ct_header_b")).trim();
			cms.setCt_header(ct_header_b);
		// 8. 링크
		}else if ("L".equals(cms.getCt_pagetype())) {
			cms.setCt_anum("");
			if("Y".equals(cms.getCt_url_target())) {
				cms.setCt_menu_url(cms.getCt_url()); //실제 URL
			}else {
				String menu_url = cms.getCt_url();
				cms.setCt_menu_url(menu_url); //실제 URL
			}
			cms.setCt_board_header("");
			cms.setCt_board_footer("");
		// 9. 컨텐츠내용
		}else if ("T".equals(cms.getCt_pagetype())) {
			//접근제한그룹
			String[] gChk 		= request.getParameterValues("gChk");
			String ct_group = "";
			if(gChk != null) {
				for(int j=0; j<=gChk.length-1; j++) {
					ct_group += gChk[j]+",";
				}
			}
			cms.setCt_group(ct_group);
			
			//링크URL
			String menu_url = "/contents.do?idx="+cms.getCt_idx();
			cms.setCt_anum("");
			cms.setCt_menu_url(menu_url); //실제 URL
			cms.setCt_board_header("");
			cms.setCt_board_footer("");
		}else {
			cms.setCt_pagetype("X");
			cms.setCt_anum("");
			cms.setCt_board_header("");
			cms.setCt_board_footer("");
		}
		cms.setCt_mdate(Func.date("Y-m-d H:i:s"));
		cms.setCt_layout("0");
		
		//update 제외 필드
		cms.setCt_idx(null);
		
		//수정하기
		cms.setExTableName("homepage_menu");
		cms.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		exdao.update(cms);
		
		
		//작업내역 저장하기
		if(!"X".equals(cms.getCt_pagetype())) {
			this.cmsHistory(cms, ct_idx);
		}
		
		//json 생성
		this.createMenuJson(request);
		
		String prepage = Func.nvl(request.getParameter("prepage")).trim();
		if("".equals(prepage)){
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			prepage = "/"+builder_dir+"/ncms/cms/contents.do?ct_idx="+ct_idx;
		}
		return "redirect:"+prepage;
	}

	
	/**
	 * @title : CMS관리 미리보기
	 * @method : preview
	 */
	@Override
	public void preview(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//카피라이터 , 로고 , 사이트명 가져오기
		SiteConfigDTO selectdto = new SiteConfigDTO();
		selectdto.setExTableName("site_config");
		selectdto.setExKeyColumn("sc_idx");	//pk 키 설정
		selectdto.setExColumn("sc_sitename, sc_logo, sc_copyright, sc_header_meta");
		selectdto.setExRecordCount(1);
		selectdto.setExOrderByQuery("order by sc_idx desc");
		HashMap<String,Object> info = exdao.selectQueryRecordObject(selectdto);
		request.setAttribute("siteinfo", info);
	}
	
	/**
	 * @title : CMS관리 작업내역보기
	 * @method : history
	 */
	@Override
	public void history(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String cth_idx = Func.nvl(request.getParameter("cth_idx"));
		if("".equals(cth_idx)){cth_idx = "0";}
		
		CmsHistoryDTO selectdto = new CmsHistoryDTO();
		selectdto.setExTableName("homepage_menu_history");
		selectdto.setExKeyColumn("cth_idx");	//pk 키 설정
		selectdto.setExColumn(new CmsHistoryDTO());
		selectdto.setExWhereQuery("where cth_idx = '"+exdao.filter(cth_idx)+"'");
		HashMap<String,Object>history = exdao.selectQueryRecordObject(selectdto);
		request.setAttribute("history", history);
		
		//카피라이터 , 로고 , 사이트명 가져오기
		selectdto = new CmsHistoryDTO();
		selectdto.setExTableName("site_config");
		selectdto.setExKeyColumn("sc_idx");
		selectdto.setExColumn("sc_sitename, sc_logo, sc_copyright, sc_header_meta");
		selectdto.setExOrderByQuery("order by sc_idx desc");
		HashMap<String,Object> info = exdao.selectQueryRecordObject(selectdto);
		request.setAttribute("siteinfo", info);
		
	}

	/**
	 * @title : CMS관리 작업내역 적용하기
	 * @method : historyOk
	 */
	@Override
	public void historyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String cth_idx = Func.nvl( request.getParameter("cth_idx") );
		if("".equals(cth_idx)){cth_idx = "0";}
		
		CmsHistoryDTO selectdto = new CmsHistoryDTO();
		
		selectdto.setExTableName("homepage_menu_history");
		selectdto.setExKeyColumn("cth_idx");
		String s_fields = "cth_idx, ct_idx, ct_name, ct_chk, ct_url, ct_url_target, ct_pagetype, ct_content";
		s_fields += ", ct_anum, ct_header, ct_footer, ct_program, ct_top_url, ct_bottom_url";
		s_fields += ", ct_img_on, ct_img_off, ct_img_status, ct_popup_w, ct_popup_h, ct_point";
		s_fields += ", ct_manage_num, ct_layout, ct_img_on2, ct_img_off2, ct_menu_url, ct_mobile_menu_url";
		s_fields += ", m_id, ct_board_header, ct_board_footer, ct_group";
		selectdto.setExColumn(s_fields);
		selectdto.setExWhereQuery("where cth_idx = '"+exdao.filter(cth_idx)+"'");
		HashMap<String,String>history = exdao.selectQueryRecord(selectdto);
		request.setAttribute("ct_idx", history.get("ct_idx"));
		
		//작업내역을 적용
		this.cmsRestore(history);

		//json 생성
		this.createMenuJson(request);
	}

	/**
	 * @title : CMS관리 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String status 		= Func.nvl(request.getParameter("status")); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		int ct_idx			= Func.cInt( request.getParameter("ct_idx") );  //단일 삭제 사용
		
		//select 전용 dto
		CmsDTO selectdto = new CmsDTO();
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("ct_idx, ct_depth, ct_codeno");
		
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				selectdto.setExWhereQuery("where ct_idx = '"+chk[z]+"'");
				HashMap<String,String>cmsdto = exdao.selectQueryRecord(selectdto);
				
				//1. 이미지폴더 삭제(1뎁스에만 해당)
				if("1".equals(cmsdto.get("ct_depth"))) {
					String uploaddir =  fileutil.realPath(request, "/nanum/site/img/");
					if(fileutil.isExistsFile(uploaddir+"/menu"+Func.zerofill(cmsdto.get("ct_idx"), 2, "0")+"/")) {
						Func.folderDelete(uploaddir+"/menu"+Func.zerofill(cmsdto.get("ct_idx"), 2, "0")+"/");
					}
					//2. CSS 파일 삭제
					String css_file = fileutil.realPath(request, "/nanum/site/common/css/head_"+Func.zerofill(chk[z], 2, "0")+".css");
					String filePath = fileutil.realPath(request, css_file);
					if(fileutil.isExistsFile(filePath)) {
						Func.fileDelete(filePath);
					}
				}
				String ct_codeno = Func.nvl( cmsdto.get("ct_codeno") );
				if(!"".equals(ct_codeno)){
					exdao.executeQuery("DELETE FROM homepage_menu WHERE ct_codeno LIKE '"+ct_codeno+"%'");
				}
			}
		}else{ //단일삭제
			selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
			HashMap<String,String>cmsdto = exdao.selectQueryRecord(selectdto);
			
			//1. 이미지폴더 삭제(1뎁스에만 해당)
			if("1".equals(cmsdto.get("ct_depth"))) {
				String uploaddir =  fileutil.realPath(request, "/nanum/site/img/");
				if(fileutil.isExistsFile(uploaddir+"/menu"+Func.zerofill(cmsdto.get("ct_idx"), 2, "0")+"/")) {
					Func.folderDelete(uploaddir+"/menu"+Func.zerofill(cmsdto.get("ct_idx"), 2, "0")+"/");
				}
				//2. CSS 파일 삭제
				String css_file = fileutil.realPath(request, "/nanum/site/common/css/head_"+Func.zerofill(ct_idx, 2, "0")+".css");
				String filePath = fileutil.realPath(request, css_file);
				if(fileutil.isExistsFile(filePath)) {
					Func.fileDelete(filePath);
				}
			}
			String ct_codeno = Func.nvl( cmsdto.get("ct_codeno") );
			if(!"".equals(ct_codeno)){
				exdao.executeQuery("DELETE FROM homepage_menu WHERE ct_codeno LIKE '"+ct_codeno+"%'");
			}
		}
		
		//json 생성
		this.createMenuJson(request);
		
	}

	/**
	 * @title : CMS관리 순서리스트
	 * @method : listMove
	 */
	@Override
	public void listMove(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//변수초기화
		String ct_idx = Func.nvl((String) request.getParameter("ct_idx")) ;
		String ct_ref = "0";
		
		//select 전용 dto
		CmsDTO selectdto = new CmsDTO();
		
		//상위 idx값을 구한다.
		if(!"".equals(ct_idx)) {
			ct_ref = ct_idx;
		}
		request.setAttribute("ct_ref", ct_ref);
		
		//메뉴리스트 가져오기
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("ct_idx, ct_ref, ct_name");

		String where_query = "where ct_ref = '"+ct_ref+"'";
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(BUILDER_DIR)){
			where_query += " and ct_site_dir = '"+BUILDER_DIR+"'";
		}
		selectdto.setExWhereQuery(where_query);
		
		selectdto.setExOrderByQuery("order by ct_code");
		List<HashMap<String,String>>menuList = exdao.selectQueryTable(selectdto);
		request.setAttribute("menuList", menuList);
	}

	
	/**
	 * @title : CMS관리 순서일괄수정
	 * @method : listMoveOk
	 */
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String ct_ref = Func.nvl( request.getParameter("ct_ref") );
		if("0".equals(ct_ref)){ct_ref = "";}

		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String ct_idx = chk[z];
			// 순서업데이트
			exdao.executeQuery("UPDATE homepage_menu SET ct_code = '"+Func.cStr(z + 1)+"' WHERE ct_idx = '"+ct_idx+"'");
		}
		
		//json 생성
		this.createMenuJson(request);
		request.setAttribute("ct_ref", ct_ref);
	}

	@Override
	public void move(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 메뉴표시여부변경
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String tot_level_chk	= Func.nvl( request.getParameter("tot_level_chk") );
		String[] chk 			= request.getParameterValues("chk");
		
		for (int z = 0; z <= chk.length - 1; z++) {
			String ct_idx = chk[z];
			String ct_chk = tot_level_chk;
			//메뉴표시여부변경
			exdao.executeQuery("UPDATE homepage_menu SET ct_chk = '"+ct_chk+"' WHERE ct_idx = '"+ct_idx+"'");
		}
		//json 생성
		this.createMenuJson(request);
	}

	/**
	 * @title : 관리자 레프트메뉴
	 * @method : mngLeft
	 */
	@Override
	public void mngLeft(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		
		//최대하위메뉴카운트를 가지고 디비조회
		//int max_depth_option = 5;		//cms관리자 하위메뉴갯수
		//List<HashMap<String,Object>>leftList = this.getMenuList("", 1, max_depth_option);
		
		//이미생성된 json 로딩
		JSONArray leftList = this.getMenuJson(request);
		
		//값리턴하기
		request.setAttribute("leftList", leftList);
	}

	@Override
	public void initMenuUrl() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLwprtMenuUrl(CmsDTO cms) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @title : 이미지폴더
	 * @method : img
	 */
	@Override
	public void img(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		ArrayList files = new ArrayList();
		
		CmsDTO selectdto = new CmsDTO();
		
		//1. 파라미터
		int ct_idx = Func.cInt((String) request.getParameter("ct_idx"));
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("ct_name, ct_depth");
		selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		HashMap<String,String>rs = exdao.selectQueryRecord(selectdto);
		String ct_name = Func.cStr( rs.get("ct_name") );
		String ct_depth = Func.cStr( rs.get("ct_depth") );
		if("1".equals(ct_depth)) {
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
			//2. 이미지 폴더 생성시킨다.
			String uploaddir =  fileutil.realPath(request, "/nanum/site/builder/dir/"+builder_dir+"/img/menu"+Func.zerofill(ct_idx, 2, "0")+"/");
			Func.folderCreate(uploaddir);
			//3. 파일리스트
			File dir = new File(uploaddir);
			//4. 파일을 탐색한다.
			files = FileUtil.visitAllFiles(files, dir);

			//5. 파일명으로 정렬한다. 
			Collections.sort(files);
		}
		request.setAttribute("files", files);
		request.setAttribute("ct_idx", ct_idx);
		request.setAttribute("ct_name", ct_name);
		request.setAttribute("ct_menu_folder", "menu"+Func.zerofill(ct_idx, 2, "0"));

	}

	/**
	 * @title : 이미지파일 처리
	 * @method : imgOk
	 */
	@Override
	public void imgOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		CmsDTO cmsdto = (CmsDTO) Func.requestAll(map.get("cmsdto"));
		String ct_menu_folder = request.getParameter("ct_menu_folder");

		//------------------- 저장공간인 [DATA]폴더가 없다면 생성 한다. -------------------
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		String realpath =  fileutil.realPath(request, "/");
		String strDir = realpath+"/nanum/site/builder/dir/"+builder_dir+"/img/"+ct_menu_folder+"/";
		Func.folderCreate(strDir);
		//------------------------------------------------------------------------------
		String num		= request.getParameter("num");
		String img			= "";
		String img_org	= Func.nvl(request.getParameter("img_org_"+num));
		
		// 이미지파일저장
		int sizeLimit = 100 * 1024 * 1024 ; // 100M
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), strDir, false, "이미지파일", "img_file_"+num, sizeLimit, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(file.getFile_name())) {
			img = Func.nvl(file.getFile_name());
		}
		
		if (!img.equals("")){ //첨부파일이 있으면
			if(!img_org.equals("")){
				Func.fileDelete( strDir + img_org );
			}
		}
		request.setAttribute("ct_idx", cmsdto.getCt_idx());
	}

	/**
	 * @title : 이미지파일 삭제
	 * @method : imgDeleteOk
	 */
	@Override
	public void imgDeleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		CmsDTO cmsdto = (CmsDTO) Func.requestAll(map.get("cmsdto"));
		
		String status 			= Func.cStr(request.getParameter("status")); //값:totdel (다중삭제시 사용)
		String[] chk 			= request.getParameterValues("chk"); //선택 체크 값
		String ct_menu_folder = Func.cStr(request.getParameter("ct_menu_folder"));
		String ebp_mdate = Func.date("Y-m-d H:i:s");
		
		String buidler_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		String realpath =  fileutil.realPath(request, "/");
		String strDir = realpath+"/nanum/site/builder/dir/"+buidler_dir+"/img/"+ct_menu_folder+"/";
		
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				String img_file = Func.nvl(chk[z]);
				if(!"".equals(img_file)) {
					Func.fileDelete( strDir + img_file );
				}
			}
		} else { //단일삭제
			String img_file = Func.nvl(request.getParameter("img_file"));
			if(!"".equals(img_file)) {
				Func.fileDelete( strDir + img_file );
			}
		}
		request.setAttribute("ct_idx", cmsdto.getCt_idx());

	}

	/**
	 * @title : css 관리
	 * @method : css
	 */
	@Override
	public void css(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//select 전용 dto
		CmsDTO selectdto = new CmsDTO();
		
		//1. 파라미터
		String ct_idx = Func.cStr( request.getParameter("ct_idx") );
		String c_idx = Func.cStr( request.getParameter("c_idx") );
		
		//------------------- 저장공간인 [DATA]폴더가 없다면 생성 한다. -------------------
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		String realpath =  fileutil.realPath(request, "/");
		String strDir = realpath+"nanum/site/builder/dir/"+builder_dir+"/common/css/";
		Func.folderCreate(strDir);
		//-------------------------------------------------------------------------
		
		//2. 셀렉트박스로 선택한게 없으면 css파일 읽어서 보내기
		String css_file = strDir+"head_"+Func.zerofill(ct_idx, 2, "0")+".css";
		String c_content = "";
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("ct_name, ct_depth");
		selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		HashMap<String,String>cms = exdao.selectQueryRecord(selectdto);
		if("1".equals(cms.get("ct_depth"))) {
			if("".equals(c_idx)) {
				//2. css 파일이 있는지 체크하고 있으면 내용뿌리기
				//String filePath = fileutil.realPath(request, css_file);
				String filePath = css_file;
				if(fileutil.isExistsFile(filePath)) {
					c_content = fileutil.getFileContents(filePath);
				}
				System.out.println("filePath="+filePath);
				System.out.println("aaa="+fileutil.isExistsFile(filePath));
			}else{
				//3. 작업이력 CSS 뿌리기
				selectdto = new CmsDTO();
				selectdto.setExTableName("css_history");
				selectdto.setExColumn("c_content");
				selectdto.setExWhereQuery("where c_idx = '"+c_idx+"'");
				c_content = exdao.selectQueryColumn(selectdto);
			}
		}
		
		selectdto = new CmsDTO();
		selectdto.setExTableName("css_history");
		selectdto.setExKeyColumn("c_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn("c_idx, ct_idx, c_content, c_wdate, m_id");
		selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		selectdto.setExOrderByQuery("ORDER BY c_wdate DESC");
		List<HashMap<String,String>>cssList = exdao.selectQueryTable(selectdto);
		request.setAttribute("cssList", cssList);
		request.setAttribute("ct_idx", ct_idx);
		request.setAttribute("c_idx", c_idx);
		request.setAttribute("css_file", css_file);
		request.setAttribute("c_content", c_content);
		request.setAttribute("ct_name", cms.get("ct_name"));
		
		
		Func.getNowPage(request);
	}

	/**
	 * @title : css 등록
	 * @method : cssOk
	 */
	@Override
	public void cssOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		CmsCssHistoryDTO cmscss = (CmsCssHistoryDTO)map.get("cmscsshistorydto");
		String ss_m_id			= Func.getSession(request, "ss_m_id");

		CmsDTO selectdto = new CmsDTO();
		
		//1. 파라미터
		String ct_idx = Func.cStr( request.getParameter("ct_idx") );
		String c_content = Func.nvl(cmscss.getC_content());
		String css_file = "";
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("ct_depth");
		selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		String ct_depth = exdao.selectQueryColumn(selectdto);
		if("1".equals(ct_depth)) {
			
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
			//2. css 파일이 있는지 체크한다.
			css_file = fileutil.realPath(request, "/nanum/site/builder/dir/"+builder_dir+"/common/css/head_"+Func.zerofill(ct_idx, 2, "0")+".css");
			//3. 파일에 덮어쓴다.
			fileutil.saveFile(c_content, css_file);
		}
		//4. 히스토리 남기기
		cmscss.setM_id(ss_m_id);
		cmscss.setC_wdate(Func.date("Y-m-d H:i:s"));
		
		cmscss.setC_idx(null);	//불필요한 컬럼 null처리
		cmscss.setExTableName("css_history");
		exdao.insert(cmscss);
		request.setAttribute("ct_idx", ct_idx);
		
	}

	/**
	 * @title : CMS관리 이미지 페이지 멀티업로드
	 * @method : nfuUpload
	 */
	@Override
	public void nfuUpload(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		String ct_menu_folder		= Func.nvl(request.getParameter("ct_menu_folder"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		String uploaddir 		=  fileutil.realPath(request, "/nanum/site/builder/dir/"+builder_dir+"/img/"+ct_menu_folder+"/");
		int sizeLimit				= 1000 * 1024 * 1024;
		String target_name	= "";
		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "CMS관리 이미지", "NFU_add_file", sizeLimit, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(file.getFile_name())) {
			target_name = file.getFile_name();
		}
		request.setAttribute("target_name", target_name);

	}

	/**
	 * @title : CMS관리 이미지 페이지 일반업로드
	 * @method : nfuNormalUpload
	 */
	@Override
	public void nfuNormalUpload(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		String ct_menu_folder		= Func.nvl(request.getParameter("ct_menu_folder"));
		
		String init = request.getParameter("init");
		String uploadchk = "";
		String file_names = "";
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		String uploaddir =  fileutil.realPath(request, "/nanum/site/builder/dir/"+builder_dir+"/img/"+ct_menu_folder+"/");
	
		uploadchk = request.getParameter("uploadchk");
		String maxFileSize = request.getParameter("maxFileSize");
		String fileFilter = request.getParameter("fileFilter");
		String fileFilterType = request.getParameter("fileFilterType");
	
		if("Y".equals(uploadchk)){
			// 파일수
			String filecount	= request.getParameter("filecount");
			
			for(int i=1;i<=Func.cInt(filecount);i++){
				String filename = fileutil.fileNormalUpload(multi.getFileMap(), uploaddir, "NFU_add_file_"+i, Func.cLng(maxFileSize), fileFilter, fileFilterType);
				if(!"".equals(Func.nvl(filename))) {
					file_names += filename+"|";
				}
			}
		}
		request.setAttribute("uploadchk", uploadchk);
		request.setAttribute("file_names", file_names);
		request.setAttribute("ct_menu_folder", ct_menu_folder);

	}

	/**
	 * @title : 메뉴파일 로딩
	 * @method : getMenuJson
	 */
	@Override
	public JSONArray getMenuJson(HttpServletRequest request) throws Exception {
		
		//this.createMenuJson(request);
		
		String realpath = fileutil.realPath(request, "/data/cms");
		String filepath = realpath+"/menu.json";
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		if(!"".equals(builder_dir)){
			filepath = realpath+"/menu_"+builder_dir+".json";
		}
		
		//json 파일 없을 경우 생성시킨다 
		if(!fileutil.isExistsFile(filepath)){
			this.createMenuJson(request);
		}
		
		JSONParser jsParser = new JSONParser();
		
		String menu_str = "[]";
		try{
			menu_str = Func.fileGetContents( filepath );
			menu_str = menu_str.trim();
		}catch(Exception e){e.printStackTrace();}

		JSONArray json = (JSONArray) jsParser.parse( menu_str );
		//System.out.println("json="+json);
		
		return json;
	}
	
	

	/**
	 * @title : 메뉴파일 생성
	 * @method : createMenuJson
	 */
	@Override
	public void createMenuJson(HttpServletRequest request) throws Exception {
		
		int max_depth_option = 5;
		
		List<HashMap<String,Object>>menuList = new ArrayList<HashMap<String,Object>>();
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		if("".equals(builder_dir)){
			menuList = this.getMenuList("", 1, max_depth_option);
		}else{
			menuList = this.getMenuList("", 1, max_depth_option, builder_dir);
		}
		JSONArray menuArray = this.mapToJson(menuList);
		String json_data = menuArray.toJSONString();
		
		//메뉴파일 생성
		String realpath = fileutil.realPath(request, "/data/cms");
		String filepath = realpath+"/menu.json";
		if(!"".equals(builder_dir)){
			filepath = realpath+"/menu_"+builder_dir+".json";
		}

		//기존파일삭제
		//fileutil.FolderDelete(filepath);
		
		try{
			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			//BufferedWriter fw = new BufferedWriter(new FileWriter(filepath, true));	//이어쓰기
			BufferedWriter fw = new BufferedWriter(new FileWriter(filepath));	//덮어쓰기

			// 파일안에 문자열 쓰기
			fw.write(json_data);
			fw.flush();
			
			// 객체 닫기
			fw.close();
			System.out.println("메뉴파일 생성!!");
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	/**
	 * @title : 메뉴로딩
	 * @method : getMenuList
	 */
	@Override
	public List<HashMap<String,Object>> getMenuList(String ct_ref, int ct_depth, int max_depth_option, String site_dir) throws Exception {
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("homepage_menu");
		selectdto.setExKeyColumn("ct_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new CmsDTO(), "ct_content");
		
		String where_query = "where ct_depth = '"+ct_depth+"'";
		site_dir = exdao.filter(site_dir);
		String web_dir = "";
		if(!"".equals(site_dir)){
			web_dir = "/"+site_dir;
			where_query += " and ct_site_dir = '"+site_dir+"'";
		}
		if(ct_depth != 1){	//1뎁스가 아니라면 상위메뉴를 따름
			where_query += " and ct_ref = '"+ct_ref+"'";
		}
		selectdto.setExWhereQuery( where_query );
		selectdto.setExOrderByQuery("order by ct_code");
		
		List<HashMap<String,Object>>menuList = exdao.selectQueryTableObject(selectdto);
		if(ct_depth < max_depth_option){
			for(int i=0;i<=menuList.size()-1;i++){
				HashMap<String,Object>menu = (HashMap<String,Object>)menuList.get(i);
				
				String ct_idx = Func.cStr( menu.get("ct_idx") );
				
				//하위메뉴 체크
				List<HashMap<String,Object>> subMenuList = this.getMenuList(ct_idx, ct_depth+1, max_depth_option, site_dir);
				menu.put("menuList", subMenuList);
				
				//ct_menu_url 생성
				String ct_menu_url = "";	//예전처럼 db에서 가져오지 않고 메뉴생성시 같이 처리
				String ct_pagetype = Func.cStr(menu.get("ct_pagetype"));
				if("T".equals(ct_pagetype)){	//직접입력
					ct_menu_url = web_dir+"/contents.do?idx="+ct_idx;
				}else if("B".equals(ct_pagetype)){	//게시판
					ct_menu_url = web_dir+"/contents.do?a_num="+Func.cStr(menu.get("ct_anum"));
				}else if("L".equals(ct_pagetype)){	//링크
					ct_menu_url = Func.cStr(menu.get("ct_url"));
				}else{
					if(subMenuList.size() > 0){
						ct_menu_url = Func.cStr( subMenuList.get(0).get("ct_menu_url") );
					}
				}
				menu.put("ct_menu_url", ct_menu_url);
				
				exdao.executeQuery("update homepage_menu set ct_menu_url = '"+ct_menu_url+"' where ct_idx = '"+ct_idx+"'");
				
				// 내용설정안된건 컨텐츠보기 버튼 비활성화 하기위함 (cms관리자용)
				String nocon="N";
				if ("".equals(Func.nvl(ct_pagetype)) || "X".equals(ct_pagetype)) nocon="Y";
				menu.put("nocon", nocon);
			}
		}
		return menuList;
	}
	

	/**
	 * @title : 메뉴로딩
	 * @method : getMenuList
	 */
	@Override
	public List<HashMap<String,Object>> getMenuList(String ct_ref, int ct_depth, int max_depth_option) throws Exception {

		return this.getMenuList(ct_ref, ct_depth, max_depth_option, "");
	}

	
	/**
	 * @title : map을 json으로 변환
	 * @method : mapToJson
	 */
	@Override
	public JSONArray mapToJson(List<HashMap<String, Object>> menuList) throws Exception {

		JSONArray menuArray = new JSONArray();
		for(int i=0;i<=menuList.size()-1;i++){
			HashMap<String, Object> menu = menuList.get(i);
			JSONObject menuObject = new JSONObject();
			
			//map을 json으로 저장
			for( Map.Entry<String, Object> entry : menu.entrySet() ) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if(!"menuList".equals(key)){	//menuList는 별도처리
					menuObject.put(key, value);
				}
			}
			
			//하위메뉴 체크
			JSONArray subMenuArray = new JSONArray();
			List<HashMap<String, Object>> subMenuList = (List<HashMap<String, Object>>)menu.get("menuList");
			if(subMenuList.size() > 0){
				subMenuArray = mapToJson(subMenuList);
			}
			menuObject.put("menuList", subMenuArray);
			
			menuArray.add(menuObject);
		}
		
		return menuArray;
	}

	
	/**
	 * @title : 게시판생성하기
	 * @method : make_board
	 */
	public String make_board(Model model, String ct_idx) throws Exception {
		
		//select 전용 dto
		BoardConfigDTO selectdto = new BoardConfigDTO();
		
		//게시판생성 dto
		BoardConfigDTO board = new BoardConfigDTO();
		
		// a_num 생성, 중복검사
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("count(*) as cnt");
		
		String a_num = "";
		boolean flag = true;
		while(flag){
			a_num = Func.get_idx_add();
			selectdto.setExWhereQuery("where a_num = '"+exdao.filter(a_num)+"'");
			String cnt = exdao.selectQueryColumn(selectdto);
			if("0".equals(cnt)){
				flag = false;
			}
		}
		board.setA_num(a_num);
		
		// 게시판명 생성
		String a_bbsname = "";
		selectdto = new BoardConfigDTO();
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("ct_codeno");
		selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		String ct_codeno = exdao.selectQueryColumn(selectdto);
		String[] arr_codeno = ct_codeno.split(";");
		
		for (int z=1;z < arr_codeno.length; z++){
			String ct_idx_str = arr_codeno[z].replaceAll("C","");
			ct_idx_str = ct_idx_str.replaceAll(";","");
			
			if(!"".equals(ct_idx_str)) {
				selectdto.setExColumn("ct_name");
				selectdto.setExWhereQuery("where ct_idx = '"+ct_idx_str+"'");
				String ct_name = exdao.selectQueryColumn(selectdto);

				if (a_bbsname!="")	a_bbsname = a_bbsname+" &gt; ";
				a_bbsname = a_bbsname+ct_name;
			}
		}
		
		
		// 기본값 정의
		board.setA_bbsname(a_bbsname);
		board.setA_group("0");
		board.setA_level("nninc_simple");
		board.setA_type("N");
		//board.setA_tablename(a_tablename_str);
		board.setA_imgline("");
		board.setA_imgwidth("0");
		board.setA_imgheight("0");
		board.setA_member("N");
		board.setA_ad_cms_id("");
		board.setA_ad_cms("N");
		board.setA_cate("N");
		board.setA_email("N");
		board.setA_email_req("N");
		board.setA_phone("N");
		board.setA_phone_req("N");
		board.setA_home("N");
		board.setA_home_req("N");
		board.setA_content("Y");
		board.setA_content_req("N");
		board.setA_jumin("N");
		board.setA_jumin_opt("");
		
		board.setA_new("1");
		board.setA_upload("Y");
		board.setA_upload_len("2");
		board.setA_nofile("hwp,doc,ppt,xls,txt,gif,jpg,docx,pptx,xlsx,zip,alz,rar,pdf");
		board.setA_nofilesize("5");
		board.setA_reply("N");
		board.setA_replyOpt("2");
		board.setA_command("N");
		board.setA_noword("");
		board.setA_width("100%");
		board.setA_displaysu("10");

		board.setA_writecontent("");
		board.setA_edit("Y");
		board.setA_blind("N");
		board.setA_rss("N");
		board.setA_sns("N");
		board.setA_photoview("N");
		board.setA_multiupload("N");
		board.setA_garbage("N");
		board.setA_show("N");
		board.setA_show_term("12");
		board.setA_date_change("N");
		board.setA_pageurl("");
		String a_date = Func.date("Y-m-d H:i:s");
		board.setA_date(a_date);

		model.addAttribute("boarddto", board);
		
		//게시판 생성처리
		boardConfigService.writeOk(model);
		
		return a_num;
	}
	
	
	/**
	 * @title : 작업내용 저장
	 * @method : cmsHistory
	 */
	public void cmsHistory(CmsDTO cms, String ct_idx) throws Exception {
		CmsHistoryDTO cmsHistory = new CmsHistoryDTO();
		
		cmsHistory.setCt_anum( cms.getCt_anum() );
		cmsHistory.setCt_board_footer( cms.getCt_board_footer() );
		cmsHistory.setCt_board_header( cms.getCt_board_header() );
		cmsHistory.setCt_bottom_url( cms.getCt_bottom_url() );
		cmsHistory.setCt_chk( cms.getCt_chk() );
		cmsHistory.setCt_content( cms.getCt_content() );
		cmsHistory.setCt_footer( cms.getCt_footer() );
		cmsHistory.setCt_group( cms.getCt_group() );
		cmsHistory.setCt_header( cms.getCt_header() );
		cmsHistory.setCt_idx( ct_idx );
		cmsHistory.setCt_img_off( cms.getCt_img_off() );
		cmsHistory.setCt_img_off2( cms.getCt_img_off2() );
		cmsHistory.setCt_img_on( cms.getCt_img_on() );
		cmsHistory.setCt_img_on2( cms.getCt_img_on2() );
		cmsHistory.setCt_img_status( cms.getCt_img_status() );
		cmsHistory.setCt_layout( cms.getCt_layout() );
		cmsHistory.setCt_manage_num( cms.getCt_manage_num() );
		cmsHistory.setCt_mdate( Func.date("Y-m-d H:i:s") );
		cmsHistory.setCt_menu_url( cms.getCt_menu_url() );
		cmsHistory.setCt_mobile_menu_url( "" );
		cmsHistory.setCt_name( cms.getCt_name() );
		cmsHistory.setCt_pagetype( cms.getCt_pagetype() );
		cmsHistory.setCt_point( cms.getCt_point() );
		cmsHistory.setCt_popup_h( cms.getCt_popup_h() );
		cmsHistory.setCt_popup_w( cms.getCt_popup_w() );
		cmsHistory.setCt_program( cms.getCt_program() );
		cmsHistory.setCt_top_url( cms.getCt_top_url() );
		cmsHistory.setCt_url( cms.getCt_url() );
		cmsHistory.setCt_url_target( cms.getCt_url_target() );
		cmsHistory.setCt_wdate( Func.date("Y-m-d H:i:s") );
		
		exdao.emptyToNull(cmsHistory);	//오라클에서 빈값을 null로 입력하는것을 방지
		
		cmsHistory.setExTableName("homepage_menu_history");
		
		exdao.insert(cmsHistory);
		
	}
	
	
	/**
	 * @title : 작업내용 적용
	 * @method : cmsRestore
	 */
	public void cmsRestore(HashMap<String,String>history) throws Exception {
		CmsDTO cms = new CmsDTO();
		cms.setCt_url( history.get("ct_url") );
		cms.setCt_url_target( history.get("ct_url_target") );
		cms.setCt_pagetype( history.get("ct_pagetype") );
		cms.setCt_anum( history.get("ct_anum") );
		cms.setCt_mdate( history.get("ct_mdate") );
		cms.setCt_name( history.get("ct_name") );
		cms.setCt_chk( history.get("ct_chk") );
		cms.setCt_layout( history.get("ct_layout") );
		cms.setCt_img_status( history.get("ct_img_status") );
		cms.setCt_img_on( history.get("ct_img_on") );
		cms.setCt_img_off( history.get("ct_img_off") );
		cms.setCt_img_on2( history.get("ct_img_on2") );
		cms.setCt_img_off2( history.get("ct_img_off2") );
		cms.setCt_content( history.get("ct_content") );
		cms.setCt_menu_url( history.get("ct_menu_url") );
		cms.setCt_header( history.get("ct_header") );
		cms.setCt_footer( history.get("ct_footer") );
		cms.setCt_tab( history.get("ct_tab") );
		cms.setCt_board_header( history.get("ct_board_header") );
		cms.setCt_board_footer( history.get("ct_board_footer") );
		cms.setCt_group( history.get("ct_group") );
		
		cms.setExTableName("homepage_menu");
		cms.setExWhereQuery("where ct_idx = '"+exdao.filter( history.get("ct_idx") )+"'");
		
		exdao.update(cms);

	}

	
	/**
	 * @title : idx로 JSONObject 조회
	 * @method : getMenuJsonByIdx
	 */
	@Override
	public String getMenuJsonByIdx(HttpServletRequest request, String idx, String field) throws Exception {
		
		JSONArray allMenuList = this.getMenuJson(request);
		String ret_value = this.getMenuJsonByIdx(request, idx, field, allMenuList);
		
		return ret_value;
	}
	

	/**
	 * @title : idx로 JSONObject 조회
	 * @method : getMenuJsonByIdx
	 */
	@Override
	public String getMenuJsonByIdx(HttpServletRequest request, String idx, String field, JSONArray menuList) throws Exception {
		
		String ret_value = null;
		JSONObject menu = this.getMenuJsonByIdx(request, idx, menuList);
		if(menu.size() > 0){
			ret_value = Func.cStr(menu.get(field));
		}
		return ret_value;
	}
	
	
	/**
	 * @title : idx로 JSONObject 조회
	 * @method : getMenuJsonByIdx
	 */
	@Override
	public JSONObject getMenuJsonByIdx(HttpServletRequest request, String idx) throws Exception {
		JSONArray menuList = this.getMenuJson(request);
		
		JSONObject menu = this.getMenuJsonByIdx(request, idx, menuList);
		
		return menu;
	}

	/**
	 * @title : idx로 JSONObject 조회
	 * @method : getMenuJsonByIdx
	 */
	@Override
	public JSONObject getMenuJsonByIdx(HttpServletRequest request, String idx, JSONArray menuList) throws Exception {
		JSONObject menu = new JSONObject(); 
		boolean loop = true;
		
		for(int i=0;i<menuList.size();i++){
			menu = (JSONObject)menuList.get(i);
			if(idx.equals( Func.cStr(menu.get("ct_idx")) )){	//현재메뉴 체크
				loop = false;
				break;
			}
			
			JSONArray menuList2 = (JSONArray)menu.get("menuList");
			if(menuList2.size() > 0){
				menu = this.getMenuJsonByIdx(request, idx, menuList2);	//하위메뉴 체크
				if(menu.size() > 0){
					loop = false;
					break;
				}
			}
		}
		
		if(loop){
			return new JSONObject();
		}else{
			return menu;
		}
		
	}

	/**
	 * @title : 드래그로 메뉴이동
	 * @method : dragMoveOk
	 */
	@Override
	public String dragMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String parent_idx = exdao.filter(request.getParameter("parent_idx"));	//부모 ct_idx
		String target_idx = exdao.filter(request.getParameter("target_idx"));	//이동할 위치에 있던 ct_idx
		String this_idx = exdao.filter(request.getParameter("this_idx"));		//선택한 메뉴 ct_idx
		
		//0:성공, 1:필요변수누락, 2:메뉴데이터 없음
		if("".equals(parent_idx) || "".equals(target_idx) || "".equals(this_idx)){
			return "1";
		}
		
		SelectDTO selectdto = new SelectDTO();
		String prt_ct_codeno = "C0;";
		if(!"0".equals(parent_idx)){
			selectdto.setExTableName("homepage_menu");
			selectdto.setExColumn("ct_codeno");
			selectdto.setExWhereQuery("where ct_idx = '"+parent_idx+"'");
			prt_ct_codeno = Func.nvl( exdao.selectQueryColumn(selectdto) ).trim();
		}
		
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("ct_codeno, ct_code, ct_depth");
		selectdto.setExWhereQuery("where ct_idx = '"+target_idx+"'");
		HashMap<String,String>menu = exdao.selectQueryRecord(selectdto);
		String trg_ct_codeno = "";
		String trg_ct_code = "";
		String trg_ct_depth = "";
		if(menu.size() > 0){
			trg_ct_codeno = menu.get("ct_codeno");
			trg_ct_code = menu.get("ct_code");
			trg_ct_depth = menu.get("ct_depth");
		}
		
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("ct_codeno, ct_code, ct_depth");
		selectdto.setExWhereQuery("where ct_idx = '"+this_idx+"'");
		menu = exdao.selectQueryRecord(selectdto);
		String this_ct_codeno = "";
		String this_ct_code = "";
		String this_ct_depth = "";
		if(menu.size() > 0){
			this_ct_codeno = menu.get("ct_codeno");
			this_ct_code = menu.get("ct_code");
			this_ct_depth = menu.get("ct_depth");
		}
		
		if("".equals(prt_ct_codeno)
				|| "".equals(trg_ct_codeno) || "".equals(trg_ct_code) || "".equals(trg_ct_depth)
				|| "".equals(this_ct_codeno) || "".equals(this_ct_code) || "".equals(this_ct_depth)
				){
			return "2";
		}
		
		
		String sql = "";
		
		//이동할 위치 메뉴들 순서조정
		sql = "update homepage_menu set ct_code = ct_code + 1";
		sql += " where ct_ref = '"+parent_idx+"' and ct_code >= '"+trg_ct_code+"'";
		exdao.executeQuery(sql);
		
		
		//메뉴이동 처리
		String change_codeno = prt_ct_codeno+"C"+this_idx+";";
		String change_ct_code = trg_ct_code;
		String change_ct_depth = trg_ct_depth;
		sql = "update homepage_menu set ct_ref = '"+parent_idx+"'";
		sql += ", ct_codeno = '"+change_codeno+"'";
		sql += ", ct_code = '"+change_ct_code+"'";
		sql += ", ct_depth  = '"+change_ct_depth+"'";
		sql += " where ct_idx = '"+this_idx+"'";
		exdao.executeQuery(sql);
		
		//하위메뉴이동 처리
		int calc_depth = Func.cInt(trg_ct_depth) - Func.cInt(this_ct_depth);
		sql = "update homepage_menu set ct_codeno = replace(ct_codeno, '"+this_ct_codeno+"', '"+change_codeno+"')";
		if(calc_depth > 0){
			sql += " , ct_depth = ct_depth +"+calc_depth;
		}else if(calc_depth < 0){
			sql += " , ct_depth = ct_depth -"+(calc_depth*-1);
		}
		sql += " where ct_ref = '"+this_idx+"'";
		exdao.executeQuery(sql);
		
		//json 파일 생성
		this.createMenuJson(request);
		
		return "0";
	}

	

}
