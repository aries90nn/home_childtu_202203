package kr.co.nninc.ncms.manager_menu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.manager_menu.service.ManagerMenuDTO;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


@Service("menuService")
public class MenuServiceImpl extends EgovAbstractServiceImpl implements MenuService {

	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/**
	 * 관리자 상단 메뉴 목록을 조회한다.
	 * @param MenuDTO
	 * @return List<MenuDTO>
	 * @throws Exception
	 */
	@Override
	public void mngTopList(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//select 전용 dto
		ManagerMenuDTO selectdto = new ManagerMenuDTO();
		
		//1. 세션정보가져오기
		String ss_g_num = Func.getSession(request, "ss_g_num");
		
		//2. 현재URL정보 담기
		String[] urlArr = request.getRequestURI().split("\\/");
		String url = "";
		if(urlArr.length > 3) {
			url = "/"+urlArr[1]+"/"+urlArr[2]+"/";
			String url2 = url;
			
			//cms 분류
			String ct_group = Func.nvl( request.getParameter("ct_group") );
			if("".equals(ct_group)){ct_group = Func.getSession(request, "ss_ct_group");}
			if(!"".equals( ct_group )){
				Func.setSession(request, "ss_ct_group", ct_group);
				url2 = "/ncms/cms/write.do?ct_group="+ct_group;
			}
			
			//코드가져오기
			selectdto.setExTableName("manager_menu");
			selectdto.setExColumn("ct_codeno");
			selectdto.setExWhereQuery("WHERE ct_chk='Y' and ct_url like '%"+exdao.filter(url2)+"%'");
			selectdto.setExOrderByQuery("ORDER BY ct_code");
			
			String code = exdao.selectQueryColumn(selectdto);
			if(code != null) {
				String[] codeArr = code.split("\\;");
				String now_ct_code = codeArr[0]+";"+codeArr[1]+";";
				request.setAttribute("now_ct_code", now_ct_code);
			}
		}
		//3. 게시판 파라미터 가져와서 타이틀이름 조회하기
		String thisPageName = request.getRequestURI().toLowerCase();
		if(thisPageName != null && thisPageName.contains("/ncms/board/")) {
			String a_num = request.getParameter("a_num");
			
			selectdto = new ManagerMenuDTO();
			selectdto.setExTableName("manager_menu");
			selectdto.setExColumn("ct_name");
			selectdto.setExWhereQuery("where ct_url like '%?a_num="+exdao.filter(a_num)+"%'");
			selectdto.setExOrderByQuery("ORDER BY ct_code ASC");
			String ct_name = exdao.selectQueryColumn(selectdto);

			if(ct_name != null) {
				request.setAttribute("title_name", ct_name);
			}
		}else if(urlArr.length > 3){
			//타이틀네임 조회하기
			url += urlArr[3];
			
			selectdto = new ManagerMenuDTO();
			selectdto.setExTableName("manager_menu");
			selectdto.setExColumn("ct_name");
			selectdto.setExWhereQuery("where ct_url like '%?a_num="+exdao.filter(url)+"%'");
			selectdto.setExOrderByQuery("ORDER BY ct_code ASC");
			String ct_name = exdao.selectQueryColumn(selectdto);
			
			if(ct_name != null) {
				request.setAttribute("title_name", ct_name);
			}
		}
		
		//4. 첫번째 메뉴 리스트
		selectdto = new ManagerMenuDTO();
		selectdto.setExTableName("manager_menu");
		selectdto.setExColumn("ct_name, ct_codeno, ct_url, ct_icon");
		selectdto.setExWhereQuery("where ct_depth = 1 and ct_chk = 'Y'");
		selectdto.setExOrderByQuery("ORDER BY ct_code ASC");
		List<HashMap<String,String>>topTmpList = exdao.selectQueryTable(selectdto);
		List<HashMap<String,String>> menuTopList = new ArrayList();
		for (int i = 0; i < topTmpList.size(); i++) {
			HashMap<String, String>menudto = topTmpList.get(i);
			//MenuDTO menudto = topTmpList.get(i);
			
			String ct_codeno = getMenu2Url(menudto.get("ct_codeno"), ss_g_num);
			//5. 정보있으면 하위 URL가져오기
			String ct_url = "";
			if(!"".equals(ct_codeno)) { 
				ct_url = getMenu3Url(ct_codeno, ss_g_num);
			}
			//url 있으면 담기
			if(!"".equals(ct_url)) {
				menudto.put("ct_url",ct_url);
				menuTopList.add(menudto);
			}
		}
		request.setAttribute("decorator", "ncms_content");
		request.setAttribute("menuTopList", menuTopList);

	}

	/**
	 * 관리자 레프트 메뉴 목록을 조회한다.
	 * @param MenuDTO
	 * @return List<MenuDTO>
	 * @throws Exception
	 */
	@Override
	public void mngLeftList(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String ss_g_num = Func.getSession(request, "ss_g_num");
		int ct_ref = Func.cInt((String) map.get("mng_left_cd")) ;

		//select 전용 dto
		ManagerMenuDTO selectdto = new ManagerMenuDTO();
		
		// 게시판은 따로 레프트메뉴 조회
		String thisPageName = request.getRequestURI().toLowerCase();
		if(thisPageName != null && thisPageName.contains("/ncms/board/")) {
			String a_num = request.getParameter("a_num");
			selectdto.setExTableName("manager_menu");
			selectdto.setExColumn("ct_codeno");
			selectdto.setExWhereQuery("where ct_url like '%"+"?a_num="+exdao.filter(a_num)+"%'");
			String ct_codeno = exdao.selectQueryColumn(selectdto);
			if(ct_codeno != null){
				String[] ct_codeno_arr = ct_codeno.split("\\;");
				ct_ref = Func.cInt(ct_codeno_arr[1].replaceAll("C", ""));
			}
		}
		
		//1. 두번째 메뉴리스트 가져오기
		selectdto = new ManagerMenuDTO();
		selectdto.setExTableName("manager_menu");
		selectdto.setExColumn("ct_idx, ct_codeno, ct_url, ct_name");
		selectdto.setExWhereQuery("where ct_ref = '"+Func.cStr(ct_ref)+"' and ct_depth = 2 and ct_chk = 'Y'");
		selectdto.setExOrderByQuery("order by ct_code");
		
		//하위맵을 계속 추가해야 하므로 object형 리스트 생성
		List<HashMap<String,Object>> menuLeftList = exdao.selectQueryTableObject(selectdto);
		for (int i = 0; i < menuLeftList.size(); i++) {
			HashMap<String, Object>form = (HashMap<String, Object>)menuLeftList.get(i);
			
			//2. 권한체크하기
			String menu_chk = null;
			if ("1".equals(ss_g_num)) {
				menu_chk = "Y";
			}else{
				if(getMenuChk((String)form.get("ct_codeno"), ss_g_num)){
					menu_chk = "Y";
				}
			}
			form.put("menu_chk", menu_chk);
			
			//3. 세번째 메뉴리스트 가져오기
			selectdto = new ManagerMenuDTO();
			selectdto.setExTableName("manager_menu");
			selectdto.setExColumn("ct_idx, ct_codeno, ct_url, ct_name");
			selectdto.setExWhereQuery("where ct_ref = '"+form.get("ct_idx")+"' and ct_depth = 3 and ct_chk = 'Y'");
			selectdto.setExOrderByQuery("order by ct_code");
			
			//4. 하위메뉴리스트 담기
			List<HashMap<String,String>> menuList2 = exdao.selectQueryTable(selectdto);
			if (menuList2 != null) {
				for (int j = 0; j < menuList2.size(); j++) {
					HashMap<String,String>subform = menuList2.get(j);
					subform.put("menu_chk", "Y");
				}
				form.put("menuList", menuList2);
			}
			
			
		}
		
		logger.error("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.error("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ 메뉴출력 끝 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.error("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		request.setAttribute("menuLeftList", menuLeftList);
	}

	/**
	 * @title : 사용메뉴가능여부 체크
	 * @method : getMenuChk
	 */
	@Override
	public boolean getMenuChk(String mct_codeno, String g_num) throws Exception {
		ManagerMenuDTO selectdto = new ManagerMenuDTO();
		selectdto.setExTableName("manager_menu_chk_view");
		selectdto.setExColumn("count(*)");
		selectdto.setExWhereQuery("WHERE g_num = '"+exdao.filter(g_num)+"' and ct_codeno LIKE '"+exdao.filter(mct_codeno)+"%'");
		int cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
		if(cnt == 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public String getNowCode(String url) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @title : 메뉴관리 등록폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		
		//1. 등록/수정시 현재 상위pk(idx)값, 현재단계
		int ct_idx = Func.cInt((String) request.getParameter("ct_idx")) ;
		int ct_ref = 0;
		int ct_depth = 1;
		String ct_codeno_ref = "C0;";
		//int max_depth_option = Func.cInt(CommonConfig.get("menu.max_depth_option"));
		int max_depth_option = 3;

		//2. 상위값을 구한다.
		if(ct_idx != 0) {
			selectdto.setExTableName("manager_menu");
			selectdto.setExColumn("ct_idx, ct_depth, ct_codeno");
			selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
			HashMap<String,String>menudto = exdao.selectQueryRecord(selectdto);
			ct_ref						= Func.cInt(menudto.get("ct_idx"));
			ct_depth					= Func.cInt(menudto.get("ct_depth")) + 1;
			ct_codeno_ref			= menudto.get("ct_codeno");
		}
		
		//3. 현재 메뉴경로
		List<HashMap> refMenuList = new ArrayList<HashMap>();
		if(!"".equals(ct_codeno_ref)) {
			String[] ref_codeno_arr = ct_codeno_ref.split("\\;");
			for(int z=1; z<=ref_codeno_arr.length-1; z++) {
				String ct_idx_str = ref_codeno_arr[z].replaceAll("C","");
				ct_idx_str = ct_idx_str.replaceAll(";","");
				
				if(!"".equals(ct_idx_str)) {
					selectdto = new SelectDTO();
					selectdto.setExTableName("manager_menu");
					selectdto.setExColumn("ct_name");
					selectdto.setExWhereQuery("where ct_idx = '"+ct_idx_str+"'");
					String ct_name = exdao.selectQueryColumn(selectdto);
					if(ct_name != null) {
						HashMap hm = new HashMap();
						hm.put("ct_idx", ct_idx_str);
						hm.put("ct_name", ct_name);
						refMenuList.add(hm);
					}
				}
			}
		}
		request.setAttribute("refMenuList", refMenuList);
		
		//4. 메뉴리스트 가져오기
		selectdto = new SelectDTO();
		selectdto.setExTableName("manager_menu");
		selectdto.setExColumn("ct_idx, ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_wdate, ct_codeno, ct_url, ct_icon, ct_folder");
		selectdto.setExWhereQuery("where ct_ref = '"+ct_ref+"'");
		selectdto.setExOrderByQuery("ORDER BY ct_code ASC");
		List<HashMap<String,String>>menuList = exdao.selectQueryTable(selectdto);
		request.setAttribute("menuList", menuList);
		
		//request
		request.setAttribute("ct_idx", ct_idx);
		request.setAttribute("ct_ref", ct_ref);
		request.setAttribute("ct_depth", ct_depth);
		request.setAttribute("ct_codeno_ref", ct_codeno_ref);
		request.setAttribute("max_depth_option", max_depth_option);
		
		//현재url 전송
		Func.getNowPage(request);
	}

	/**
	 * @title : 메뉴관리 등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		ManagerMenuDTO menudto = (ManagerMenuDTO) map.get("menudto");

		SelectDTO selectdto = new SelectDTO();
		
		//1. 마지막 순서코드 가져오기
		selectdto.setExTableName("manager_menu");
		selectdto.setExColumn("max(ct_code)");
		int max_code = Func.cInt( exdao.selectQueryColumn(selectdto) ) + 1;
		
		//2. 등록처리
		menudto.setCt_code(Func.cStr(max_code));
		menudto.setCt_wdate(Func.date("Y-m-d H:i:s"));
		if (menudto.getCt_url()==null)	menudto.setCt_url("");
		
		//끝에 쉼표 붙이기
		if(!"".equals(Func.nvl( menudto.getCt_folder() ))) {
			if(!",".equals(menudto.getCt_folder().substring(menudto.getCt_folder().length()-1, menudto.getCt_folder().length()))) {
				menudto.setCt_folder(menudto.getCt_folder()+",");
			}
		}
		
		//insert 제외 필드
		menudto.setCt_idx(null);
		
		//insert
		menudto.setExTableName("manager_menu");
		exdao.insert(menudto);
		
		//3. 메뉴값 적용
		selectdto = new SelectDTO();
		selectdto.setExTableName("manager_menu");
		selectdto.setExColumn("max(ct_idx)");
		String max_ct_idx = exdao.selectQueryColumn(selectdto);
		if(max_ct_idx != null){
			String ct_codeno_ref = Func.nvl( request.getParameter("ct_codeno_ref") ).trim();
			String ct_codeno = ct_codeno_ref+"C"+max_ct_idx+";";
			exdao.executeQuery("update manager_menu set ct_codeno = '"+ct_codeno+"' where ct_idx = '"+max_ct_idx+"'");
		}
		
		//4. 리턴파라미터
		String ct_ref = menudto.getCt_ref();
		if("".equals(Func.nvl(menudto.getCt_ref())) || "0".equals(menudto.getCt_ref())) { ct_ref = "";}
		request.setAttribute("ct_ref", ct_ref);
		
	}

	/**
	 * @title : 메뉴관리 수정처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		ManagerMenuDTO menudto = (ManagerMenuDTO) map.get("menudto");
		
		String ct_idx = Func.nvl( request.getParameter("ct_idx") ).trim();
		
		//끝에 쉼표 붙이기
		if(!"".equals(menudto.getCt_folder())) {
			if(!",".equals(menudto.getCt_folder().substring(menudto.getCt_folder().length()-1, menudto.getCt_folder().length()))) {
				menudto.setCt_folder(menudto.getCt_folder()+",");
			}
		}
		//1. 수정처리
		menudto.setCt_wdate(Func.date("Y-m-d H:i:s"));
		
		//update 제외필드
		menudto.setCt_idx(null);
		menudto.setCt_chk(null);
		
		//update
		menudto.setExTableName("manager_menu");
		menudto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		exdao.update(menudto);
		
		//2. 리턴파라미터
		String ct_ref = menudto.getCt_ref();
		if("".equals(Func.nvl(menudto.getCt_ref())) || "0".equals(menudto.getCt_ref())) { ct_ref = "";}
		request.setAttribute("ct_ref", ct_ref);

	}

	/**
	 * @title : 메뉴관리 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String status 		= Func.nvl( request.getParameter("status") ).trim(); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String ct_idx		= Func.nvl( request.getParameter("ct_idx") ).trim();  //단일 삭제 사용
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("manager_menu");
		selectdto.setExColumn("ct_codeno");
		
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				selectdto.setExWhereQuery("where ct_idx = '"+ exdao.filter(chk[z]) +"'");
				String ct_codeno = Func.nvl( exdao.selectQueryColumn(selectdto) );
				if(!"".equals(ct_codeno)){
					exdao.executeQuery("DELETE FROM manager_menu WHERE ct_codeno LIKE '"+ct_codeno+"%'");
				}
			}
		}else{		//단일삭제
			selectdto.setExWhereQuery("where ct_idx = '"+ exdao.filter(ct_idx) +"'");
			String ct_codeno = Func.nvl( exdao.selectQueryColumn(selectdto) );
			if(!"".equals(ct_codeno)){
				exdao.executeQuery("DELETE FROM manager_menu WHERE ct_codeno LIKE '"+ct_codeno+"%'");
			}
		}
		
		//리턴파라미터
		String ct_ref = Func.nvl( request.getParameter("ct_ref") ).trim();
		if("0".equals(ct_ref)) { ct_ref = "";}
		request.setAttribute("ct_ref", ct_ref);
		
	}

	@Override
	public void move(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 순서일괄수정처리
	 * @method : listMoveOk
	 */
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String ct_idx = chk[z];
			String ct_code = Func.cStr(z + 1);
			exdao.executeQuery("update manager_menu set ct_code = '"+ct_code+"' where ct_idx = '"+ct_idx+"'");
		}
		
		//리턴파라미터
		String ct_ref = Func.nvl( request.getParameter("ct_ref") ).trim();
		if("0".equals(ct_ref)) { ct_ref = "";}
		request.setAttribute("ct_ref", ct_ref);
	}

	/**
	 * @title : 메뉴사용여부변경
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String tot_level_chk	= Func.nvl( request.getParameter("tot_level_chk") ).trim();
		String[] chk 			= request.getParameterValues("chk");
		
		for (int z = 0; z <= chk.length - 1; z++) {
			String ct_idx = chk[z];
			String ct_chk = exdao.filter( tot_level_chk );
			
			//상태변경
			exdao.executeQuery("update manager_menu set ct_chk = '"+ct_chk+"' where ct_idx = '"+ct_idx+"'");
		}
		//리턴파라미터
		String ct_ref = Func.nvl( request.getParameter("ct_ref") ).trim();
		if("0".equals(ct_ref)) { ct_ref = "";}
		request.setAttribute("ct_ref", ct_ref);

	}
	
	
	/**
	 * 2단계 사용메뉴가능여부
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public String getMenu2Url(String mct_codeno, String g_num) throws Exception {
		System.out.println("getMenu2Url 실행");
		String ct_codeno = "";
		
		//select 전용 dto
		ManagerMenuDTO selectdto = new ManagerMenuDTO();
		selectdto.setExTableName("manager_menu");
		selectdto.setExColumn("ct_codeno");
		selectdto.setExOrderByQuery("ORDER BY ct_code ASC");
		String where_query = "where ct_depth = '2' and ct_chk = 'Y' and ct_codeno LIKE '"+exdao.filter(mct_codeno)+"%'";
		
		if ("1".equals(g_num)) {
			selectdto.setExWhereQuery(where_query);
			String ct_codeno2 = exdao.selectQueryColumn(selectdto);
			if(ct_codeno2 != null){ct_codeno = ct_codeno2;}
		}else{
			where_query += " and ct_idx in (";
			where_query += "SELECT ct_idx FROM manager_menu_chk_view";
			where_query += " WHERE g_num = '"+exdao.filter(g_num)+"'";
			where_query += ")";
			selectdto.setExWhereQuery(where_query);
			ct_codeno = exdao.selectQueryColumn(selectdto);
		}
		ct_codeno = Func.nvl(ct_codeno);

		return ct_codeno;
	}
	
	
	/**
	 * 3단계 사용메뉴 > URL
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public String getMenu3Url(String mct_codeno, String g_num) throws Exception {
		String ct_url = "";
		System.out.println("getMenu3Url 실행");
		//select 전용 dto
		ManagerMenuDTO selectdto = new ManagerMenuDTO();
		selectdto.setExTableName("manager_menu");
		selectdto.setExColumn("ct_url");
		String where_query = "where ct_depth = 3 and ct_codeno LIKE '"+exdao.filter(mct_codeno)+"%'";
		selectdto.setExOrderByQuery("ORDER BY ct_code ASC");
		//총관리자일경우
		if ("1".equals(g_num)) {
			selectdto.setExWhereQuery(where_query);
			String ct_url2 = exdao.selectQueryColumn(selectdto);
			if(ct_url2 != null){ct_url = ct_url2;}
		}else{
			where_query += " and ct_ref in (";
			where_query += "SELECT ct_idx FROM manager_menu_chk_view";
			where_query += " WHERE g_num = '"+exdao.filter(g_num)+"'";
			where_query += ")";
			
			selectdto.setExWhereQuery(where_query);
			ct_url = exdao.selectQueryColumn(selectdto);
		}
		ct_url = Func.nvl(ct_url);

		return ct_url;
	}

}
