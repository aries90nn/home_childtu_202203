package kr.co.nninc.ncms.cms.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.cms.service.CmsDTO;
import kr.co.nninc.ncms.cms.service.CmsService;
import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.main.service.MainService;

/**
 * cms를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.02.22
 * @version 1.1
 */
@Service("userCmsService")
public class UserCmsServiceImpl extends EgovAbstractServiceImpl implements UserCmsService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	@Resource(name = "cmsService")
	private CmsService cmsService;
	
	@Resource(name = "mainService")
	private MainService mainService;
	
	/** FileUtil */
	@Resource(name="fileutil")
	private FileUtil fileutil;
	
	/**
	 * @title : 탑메뉴(사용자용)
	 * @method : top
	 */
	@Override
	public void top(Model model, HttpServletRequest request) throws Exception {
		JSONArray allMenuList = cmsService.getMenuJson(request);
		//중지메뉴빼기
		String strDir =  fileutil.realPath(request, "/data/cms/");
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		//css 파일있는지 체크하기
		String idx = Func.nvl( request.getParameter("idx") ).trim();
		if(!"".equals(idx)){
			String ct_codeno = cmsService.getMenuJsonByIdx(request, idx, "ct_codeno", allMenuList);
			ct_codeno = Func.nvl(ct_codeno).trim();
			if(!"".equals(ct_codeno)){
				String[] ref_codeno_arr = ct_codeno.split("\\;");
				String first_ct_idx = ref_codeno_arr[1].replaceAll("C","").replaceAll(";","");
				String css_file = "/nanum/site/builder/dir/"+builder_dir+"/common/css/head_"+first_ct_idx+".css";
				String filePath = fileutil.realPath(request, css_file);
				if(fileutil.isExistsFile(filePath)) {
					request.setAttribute("head_css", css_file);
				}
			}
		}
		
		
		JSONArray topMenuList = new JSONArray();
		for(int i=0;i<allMenuList.size();i++){
			JSONObject cms = (JSONObject)allMenuList.get(i);

			//파일 있는지 체크
			if("Y".equals(cms.get("ct_img_status")) && !"".equals(cms.get("ct_img_off")) && cms.get("ct_img_off") != null) {
				File file = new File( strDir+"/"+cms.get("ct_img_off") );
				if(!file.exists()){
					cms.put("ct_img_off","");
				}
			}
			if("Y".equals(cms.get("ct_chk"))) {
				topMenuList.add(cms);
			}
			
		}
		request.setAttribute("topMenuList", topMenuList);
		
		
		//상단팝업, 휴관일 안내
		mainService.banner3List(model);
		mainService.closeList(model);
	}

	/**
	 * @title : 레프트메뉴(사용자용)
	 * @method : left
	 */
	@Override
	public void left(Model model, HttpServletRequest request) throws Exception {
		
		CmsDTO selectdto = new CmsDTO();
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		//전체 리스트
		JSONArray allMenuList = cmsService.getMenuJson(request);
		
		//1. 메뉴 파라미터
		String idx = exdao.filter( request.getParameter("idx"));

		if("".equals(idx) && request.getAttribute("idx") != null) {
			idx = Func.nvl( (String)request.getAttribute("idx") ).trim();
		}
		
		//2. 없으면 URL로 조회하기 ***********************
		if("".equals(idx)) {
			HashMap<String,String> cms = currentUrl(model);
			if(cms.size() > 0){idx = cms.get("ct_idx");}
		}
		
		//****************************************
		
		//3. a_num으로 idx 추출
		String gubun = Func.nvl(request.getParameter("gubun")).trim();
		if(gubun != null && !"".equals(gubun.trim())) {
			idx = "";
			gubun = Func.nvl(gubun.trim());
		}
		
		if("".equals(idx)) {
			//3-1. a_num으로 idx 추출
			String a_num = Func.nvl(request.getParameter("a_num")).trim();
			if(!"".equals(a_num)){
				selectdto.setExTableName("homepage_menu");
				selectdto.setExColumn("ct_idx");
				selectdto.setExRecordCount(1);
				selectdto.setExWhereQuery("where ct_anum = '"+a_num+"'");
				idx = Func.nvl( exdao.selectQueryColumn(selectdto) ).trim();
			}
			//3-2. 동아리신청(전용) 게시판이면 신청게시판의 a_num으로 메뉴를 추출
			if("".equals(idx)) {
				LinkedHashMap<String, String> clubList = CommonConfig.getBoardMap("club_list");
				System.out.println("left.clubList.size="+clubList.size());
				for (Map.Entry<String, String> entry: clubList.entrySet()) {
					System.out.println("club_a_num = "+a_num + " : " + entry.getValue()); 
					if(a_num.equals(entry.getKey()) || a_num.equals(entry.getValue())){	//동아리신청 또는 전용 게시판
						selectdto = new CmsDTO();
						selectdto.setExTableName("homepage_menu");
						selectdto.setExColumn("ct_idx");
						selectdto.setExRecordCount(1);
						selectdto.setExWhereQuery("where ct_url = '/"+builder_dir+"/site/club/clubList.do?a_num="+Func.nvl( entry.getKey() )+"'");
						idx = Func.nvl( exdao.selectQueryColumn(selectdto) ).trim();
						break;
					}
					
				}
			}
			
			//3-3. gubun
			if(!"".equals(gubun)) {
				selectdto.setExTableName("homepage_menu");
				selectdto.setExColumn("ct_idx");
				selectdto.setExRecordCount(1);
				selectdto.setExWhereQuery("where ct_url like '%gubun="+gubun+"'");
				idx = Func.nvl( exdao.selectQueryColumn(selectdto) ).trim();
			}
		}
		
		
		request.setAttribute("idx", idx);
		
		//현재페이지 URL 전송
		String nowpage = Func.getNowPage(request);
		if(nowpage.indexOf("/member/ipin.do") > -1) {
			nowpage = "";
		}
		JSONArray leftMenuList = new JSONArray();
		JSONObject cms = cmsService.getMenuJsonByIdx(request, Func.cStr(idx), allMenuList);
		if(cms.size() > 0) {

			String[] ref_codeno_arr = Func.cStr(cms.get("ct_codeno")).split("\\;");
			if(ref_codeno_arr.length > 1) {
				//3. 상위 메뉴 코드(최상위코드)
				String ct_idx_str = ref_codeno_arr[1].replaceAll("C","").replaceAll(";","");
				//
				JSONObject nowmenu = cmsService.getMenuJsonByIdx(request, ct_idx_str, allMenuList);
				String first_menu_name = Func.cStr( nowmenu.get("ct_name") );
				request.setAttribute("first_menu_name", first_menu_name);
				if(nowmenu.get("ct_name_eng") != null){
					String first_menu_name_eng = Func.cStr( nowmenu.get("ct_name_eng") );
					request.setAttribute("first_menu_name_eng", first_menu_name_eng);
				}
				//4. css 파일있는지 체크하기
				String css_file = "/nanum/site/builder/dir/"+builder_dir+"/common/css/head_"+Func.zerofill(ct_idx_str, 2, "0")+".css";

				String filePath = fileutil.realPath(request, css_file);
				if(fileutil.isExistsFile(filePath)) {
					request.setAttribute("head_css", css_file);
				}
				
				//5. 레프트메뉴 리스트(2뎁스)
				//JSONArray allMenuList = cmsService.getMenuJson(request);
				leftMenuList = (JSONArray)nowmenu.get("menuList");
				leftMenuList = this.changeNowPage( leftMenuList, request );
				/*
				for(int i=0;i<allMenuList.size();i++){
					JSONObject menu_1 = (JSONObject)allMenuList.get(i);
					if(ct_idx_str.equals( Func.cStr(menu_1.get("ct_idx")) )){	//2뎁스 추출
						leftMenuList = (JSONArray)menu_1.get("menuList");
						//${nowpage} -> 치환(로그인할때이용)
						leftMenuList = this.changeNowPage( leftMenuList, request );
						
						//기존소스는 여기서 부터 3뎁스메뉴를 조회한다. 현재는 마지막뎁스까지 json에 이미 있으므로 처리할 필요없다.
						
						break;
					}
				}
				*/
				
				String ct_idx_2 = ""; String ct_idx_3 = "";
				if(ref_codeno_arr.length >= 3){
					ct_idx_2 = ref_codeno_arr[2].replaceAll("C","").replaceAll(";","");
				}
				if(ref_codeno_arr.length >= 4){
					ct_idx_3 = ref_codeno_arr[3].replaceAll("C","").replaceAll(";","");
				}
				
				//6. 2뎁쓰는 상위 메뉴 코드 조회하기
				request.setAttribute("ref_idx1", ct_idx_str);
				//7. 3뎁쓰는 상위 메뉴 코드 조회하기
				if(Func.cInt(cms.get("ct_depth")) >= 3) {
					request.setAttribute("ref_idx2", ct_idx_2);
					//
					String ct_name2 = cmsService.getMenuJsonByIdx(request, ct_idx_2, "ct_name", allMenuList);
					request.setAttribute("two_menu_name", ct_name2);
				}
				//8. 4뎁쓰는 상위 메뉴 코드 조회하기
				if(Func.cInt(cms.get("ct_depth")) >= 4) {
					request.setAttribute("ref_idx3", ct_idx_3);
				}
				request.setAttribute("title", cms.get("ct_name"));
				//9. 탭리스트조회후 setAttribute
				//객체명은 tabList(2뎁스), tabList2(3뎁스), tabList3(4뎁스)... 이런식으로 마지막뎁스까지 처리됨
				this.selectTabMenu(leftMenuList, request);
				
			}
			request.setAttribute("board_header", Func.cStr( cms.get("ct_board_header") ));
			request.setAttribute("board_footer", Func.cStr( cms.get("ct_board_footer") ));
		}
		
		request.setAttribute("leftMenuList", leftMenuList);

	}
	
	/**
	 * @title : 현재 URL 로 조회하기
	 * @method : currentUrl
	 */
	public HashMap<String,String> currentUrl(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		// 2-1. /poll/list -> 에서 /poll/ 로 조회하기
		String url = request.getRequestURI();
		String[] url_arr = url.split("\\/");
		String url_str = url_arr[0];
		if("".equals(url_str)) { url_str = url_arr[1]; }
		if(url_str.indexOf(".do") > -1) {
			url_str = "/"+ url_str;
		}else {
			url_str = "/"+ url_str + "/";
		}
		
		// 2-2. /site/search/search00.do 로 검색시
		if(url_arr.length > 3) { 
			url_str =  url_str + url_arr[2] + "/";
			if("site".equals(url_arr[1])) {
				url_str = url_str + url_arr[3];
			}else if("site".equals(url_arr[2]) && url_arr.length <= 4){
				url_str = url_str + url_arr[3];
			}else if("member".equals(url_arr[1]) || "member".equals(url_arr[2])){
				url_str = url;
			}else if(url_arr.length > 4){
				url_str = url_str + url_arr[3]+ "/"+url_arr[4];
			}
		}
		
		System.out.println("currentUrl.url="+url);
		System.out.println("currentUrl.url_str="+url_str);
		String chk_url = "/contents.do";
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(BUILDER_DIR)){
			chk_url = "/"+BUILDER_DIR+chk_url;
		}
		//idx값 없이 contents.do 이면 게시판이므로 빈맵을 리턴
		if(chk_url.equals(url)){
			return new HashMap<String, String>();
		}
		
		CmsDTO selectdto = new CmsDTO();
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("ct_idx, ct_codeno, ct_chk, ct_group");
		selectdto.setExRecordCount(1);
		selectdto.setExWhereQuery("WHERE ct_menu_url LIKE '%"+url_str+"%' and (ct_url_target is null or ct_url_target = '')");
		selectdto.setExOrderByQuery("order by ct_depth desc");
		HashMap<String, String>cms = exdao.selectQueryRecord(selectdto);
		
		return cms;
	}
	

	/**
	 * @title : 레프트메뉴(하단)
	 * @method : foot
	 */
	@Override
	public void foot(Model model, HttpServletRequest request) throws Exception {
		
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★ 메뉴 END ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

	}

	
	/**
	 * @title : ${nowpage} -> 치환(로그인할때이용)
	 * @method : changeNowPage
	 */
	private JSONArray changeNowPage(JSONArray menuList, HttpServletRequest request) throws Exception {
		
		String nowpage = Func.getNowPage(request);
		for(int i=0;i<menuList.size();i++){
			JSONObject menu = (JSONObject)menuList.get(i);
			String ct_menu_url = Func.cStr( menu.get("ct_menu_url") );
			
			ct_menu_url = ct_menu_url.replaceAll("\\$\\{nowpage\\}", Func.urlEncode(nowpage));
			menu.put("ct_menu_url", ct_menu_url);
			
			//하위메뉴체크
			JSONArray menuList2 = (JSONArray)menu.get("menuList");
			if(menuList2.size() > 0){
				menuList2 = this.changeNowPage(menuList2, request);
				menu.put("menuList", menuList2);
			}
		}

		return menuList;
	}
	
	/**
	 * @title : 탭메뉴 조회후 setAttribute
	 * @method : selectTabMenu
	 */
	private void selectTabMenu(JSONArray menuList, HttpServletRequest request) throws Exception{
		String ct_idx = Func.nvl( request.getParameter("idx") ).trim();
		if("".equals(ct_idx)){ct_idx = Func.nvl((String)request.getAttribute("idx")).trim();}
		boolean loop = true;
		for(int i=0;i<menuList.size();i++){
			JSONObject menu = (JSONObject)menuList.get(i);
			String ct_depth = Func.cStr( menu.get("ct_depth") );
			String ct_tab = Func.cStr( menu.get("ct_tab") );
			
			JSONArray menuList2 = (JSONArray)menu.get("menuList");
			
			if("Y".equals(ct_tab)){	//하위메뉴를 탭으로 구성한다면
				int depth = Func.cInt(ct_depth) - 1;
				String tablist = "tabList";
				if(depth > 1){
					tablist = tablist+depth;
				}
				
				for(int j=0;j<menuList2.size();j++){
					JSONObject menu2 = (JSONObject)menuList2.get(j);
					if(ct_idx.equals( Func.cStr(menu2.get("ct_idx")) )){	//현재메뉴idx와 같다면
						
						//탭메뉴를 jsp에 전달
						request.setAttribute(tablist, menuList2);
						loop = false;
						break;
					}
				}
			}
			
			if(!loop){
				break;
			}
			
			if(menuList2.size() > 0){	//하위 메뉴 체크
				this.selectTabMenu(menuList2, request);
			}
		}
	}
	
}
