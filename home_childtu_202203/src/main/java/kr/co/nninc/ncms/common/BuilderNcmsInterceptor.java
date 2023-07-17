package kr.co.nninc.ncms.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.nninc.ncms.cms.service.CmsDTO;
import kr.co.nninc.ncms.cms.service.CmsService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

public class BuilderNcmsInterceptor extends HandlerInterceptorAdapter{
	protected Log log = LogFactory.getLog(BuilderNcmsInterceptor.class);
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	@Resource(name = "cmsService")
	private CmsService cmsService;
	
	@Override
	// 컨트롤러 전처리
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("BuilderNcmsInterceptor.preHandle start");
		
		//사이트관리자 데코레이터 선언
		request.setAttribute("ncms_decorator", "bs_ncms_content");
		
		//사이트 웹디렉토리
		HashMap<String,String>builder = (HashMap<String,String>)request.getAttribute("BUILDER");
		String builder_dir = (String)request.getAttribute("BUILDER_DIR");
		
		//select 전용dto
		SelectDTO selectdto = new SelectDTO();
		
		//메뉴복구;;;;;
		//JSONArray menuList = cmsService.getMenuJson(request);
		//this.jsonToDb(request, menuList);
		//메뉴복구처리끝;;;;;
		
		
		//현재 uri - left에서 메뉴그룹화에 사용됨
		String rq_request_uri = (String)request.getRequestURI();
		request.setAttribute("rq_request_uri", rq_request_uri);
		System.out.println("BuilderNcmsInterceptor.rq_request_uri = "+rq_request_uri);
		
		
		//관리자 접속IP 체크
		selectdto.setExTableName("builder_site");
		selectdto.setExColumn("bs_manager_ip, bs_manager_mobile");
		selectdto.setExWhereQuery("where bs_directory = '"+builder_dir+"'");
		HashMap<String,String>infodto = exdao.selectQueryRecord(selectdto);
		
		if("Y".equals(infodto.get("bs_manager_ip"))) { //IP 체크
			selectdto = new SelectDTO();
			selectdto.setExTableName("manager_ip");
			selectdto.setExColumn("count(*)");
			String where_query = "WHERE site_dir = '"+builder_dir+"' and mi_chk='Y' AND mi_sdate <> '' AND mi_sdate is not null";
			where_query += " and mi_sdate <= '"+Func.date("Y-m-d")+"'";
			where_query += " AND (mi_edate = '' or mi_edate is null or mi_edate >= '"+Func.date("Y-m-d")+"')";
			where_query += " and mi_ip = '"+Func.remoteIp(request)+"'";
			selectdto.setExWhereQuery(where_query);
			int cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
			
			if(cnt == 0){
				response.sendRedirect("/error/ip.do");
				return false;
			}
		}
		
		//관리자 모바일 접속체크
		if("N".equals(infodto.get("bs_manager_mobile"))){
			boolean mobile_chk = false;
			String[] tmp_arr_mobile = {"android","iphone"};
			for(int m=0; m<tmp_arr_mobile.length; m++){
				String tmp_agent_str = Func.cStr(request.getHeader("User-Agent")).toLowerCase();
				if(tmp_agent_str.indexOf(tmp_arr_mobile[m]) != -1){
					mobile_chk = true;
					break;
				}
			}
			if(mobile_chk){
				response.sendRedirect("/error/mobile.do");
				return false;
			}
		}
		
		
		//관리자메뉴 상단 사이트목록
		selectdto = new SelectDTO();
		selectdto.setExTableName("builder_site");
		selectdto.setExColumn("bs_num, bs_sitename, bs_directory");
		//권한있는 사이트만 불러오자
		if(!"1".equals(Func.getSession(request, "ss_g_num"))){	//총관리자가 아니라면
			String ss_m_site = Func.getSession(request, "ss_m_site").trim();
			if(!"".equals(ss_m_site) && !",".equals(ss_m_site)){
				if(",".equals(ss_m_site.substring(ss_m_site.length()-1, ss_m_site.length()))){
					ss_m_site = ss_m_site.substring(0, ss_m_site.length()-1);
				}
				selectdto.setExWhereQuery("where bs_num in ("+ss_m_site+")");
			}
		}
		selectdto.setExOrderByQuery("order by bs_code");
		List<HashMap<String,String>>siteList = exdao.selectQueryTable(selectdto);
		request.setAttribute("SITE_LIST", siteList);
		
		//관리자 세션체크
		builder_dir = "/"+builder_dir;
		if ((builder_dir+"/ncms/login.do").equals(request.getRequestURI()) || (builder_dir+"/ncms/loginOk.do").equals(request.getRequestURI())) {
			return super.preHandle(request, response, handler);
		}else{
			if (!"ok".equals(Func.getSession(request, "ss_security_ad_cms"))) {
				if ((builder_dir+"/ncms/board_config/member_search.do").equals(request.getRequestURI())) {
					response.sendRedirect("/error/parent_redirect.do");
					return false;
				}else {
					response.sendRedirect(builder_dir+"/ncms/login.do");
					return false;
				}
			}else{
				//현재페이지 정보값(권한있는지 체크하기)
				if(!"1".equals(Func.getSession(request, "ss_g_num"))){	//총관리자가 아니라면
					selectdto = new SelectDTO();
					selectdto.setExTableName("member");
					selectdto.setExColumn("m_site");
					selectdto.setExWhereQuery("where m_num = '"+exdao.filter(Func.getSession(request, "ss_m_num"))+"'");
					String m_site = Func.nvl( exdao.selectQueryColumn(selectdto) ).replaceAll(" ", "");
					request.setAttribute("user_m_site", m_site);
					String[] m_site_arr = m_site.split(",");
					boolean auth_chk = false;
					for(int i=0;i<=m_site_arr.length-1;i++){
						if(m_site_arr[i].equals(builder.get("bs_num"))){
							auth_chk = true;
							break;
						}
					}
					if(!auth_chk){
						FuncMember.memgr_logout(request);	//로그아웃처리
						response.sendRedirect("/error/auth.do");
						return false;
					}
				}
				//return false;
			}
			System.out.println("BuilderNcmsInterceptor.preHandle finish");
			return super.preHandle(request, response, handler);
		}
		
	}
	
	@Override
	// 컨트롤러 후처리
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
		}
	}
	
	//json메뉴파일이 있을경우 디비로 복구
	private void jsonToDb(HttpServletRequest request, JSONArray menuList) throws Exception{
		for(int i=0;i<=menuList.size()-1;i++){
			JSONObject menu = (JSONObject)menuList.get(i);
			CmsDTO cms = new CmsDTO();
			for (Field field : cms.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if( "serialVersionUID".equals(field.getName()) ){continue;}
				if(menu.get(field.getName()) != null){
					field.set(cms, Func.cStr( menu.get(field.getName()) ));
				}
			}
			cms.setExTableName("homepage_menu");
			exdao.insert(cms);
			
			JSONArray menuList2 = (JSONArray)menu.get("menuList");
			if(menuList2.size() >0){
				this.jsonToDb(request, menuList2);
			}
		}
	}
}
