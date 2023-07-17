package kr.co.nninc.ncms.common;

import java.util.Enumeration;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.stats.service.MenuCounterDTO;
import kr.co.nninc.ncms.stats.service.VisitCounterSiteDTO;

public class BuilderInterceptor  extends HandlerInterceptorAdapter{

protected Log log = LogFactory.getLog(BuilderInterceptor.class);
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	@Override
	// 컨트롤러 전처리
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("BuilderInterceptor.preHandle start");
		
		HttpSession session = request.getSession();
		
		//----------- 세션타임아웃 -------------
		SelectDTO selectdto = new SelectDTO();	//초기화해서 재사용
		selectdto.setExTableName("site_config");
		selectdto.setExColumn("sc_session_use, sc_session_time");
		HashMap<String,String>session_info = exdao.selectQueryRecord(selectdto);
		//InfoDTO info = infoDAO.view();
		if(session_info.size() > 0) {
			if(Func.cInt(session_info.get("sc_session_time")) > 0) {
				session.setMaxInactiveInterval(Func.cInt(session_info.get("sc_session_time")) * 60);
			}
		}
		System.out.println("-- session timeout check ok");
		//----------- 세션타임아웃끝 -------------
		
		//동시접속제한
		if("Y".equals(session_info.get("sc_session_use"))) {
			String session_id = session.getId();
			HashMap<String, ?> rs_ss = exdao.procSessionWait(session_id);
			String session_status = (String) rs_ss.get("status");
			long wait_time = rs_ss.get("wait_time") == null ? 0 : (Long) rs_ss.get("wait_time");
			if("N".equals(session_status)) {
				String nowpage_ss = (String) request.getRequestURI();
				String querystring_ss = (String) request.getQueryString();
				if(querystring_ss != null){nowpage_ss += "?"+querystring_ss;}
				//
				String query_string_ss2 = "?wait_time="+java.net.URLEncoder.encode(Func.cStr(wait_time),"UTF-8");
				query_string_ss2 += "&gopage="+java.net.URLEncoder.encode(nowpage_ss,"UTF-8");
				String url_ss = "/error/wait.do"+query_string_ss2;
				response.sendRedirect(url_ss);
				return false;
			}
		}
		System.out.println("-- connting limit check ok");
		
		
		//----------- 인젝션 필터 -------------
		selectdto = new SelectDTO();	//초기화해서 재사용
		selectdto.setExTableName("manager_sql");
		selectdto.setExKeyColumn("ms_num");
		selectdto.setExColumn("ms_get, ms_post");
		HashMap<String,String>managerSql = exdao.selectQueryRecord(selectdto);
		
		String sms_count = exdao.filter(Func.getSms());
		sms_count = sms_count.substring(sms_count.indexOf("<strong>")+8, sms_count.indexOf("</strong>"));
		request.setAttribute("smsCount", sms_count);
		//ManagerSqlDTO managerSql = managerSqlDAO.view();
		if(managerSql != null) {
			String ms_get = Func.nvl(managerSql.get("ms_get"));
			if(ms_get.equals("")){ms_get = "', script, iframe";}	//최소한의 필터링
			String ms_post = Func.nvl(managerSql.get("ms_post"));
			//ms_get = ms_get.replaceAll(" ", "");
			//ms_post = ms_post.replaceAll(" ", "");
			if(("GET".equals(request.getMethod()) && !"".equals(ms_get)) || ("POST".equals(request.getMethod()) && !"".equals(ms_post))) {
				String[] sql_pattern = null;
				Enumeration eNames = request.getParameterNames();
				if (eNames.hasMoreElements()) {
					if( "GET".equals(request.getMethod()) ){
						sql_pattern = ms_get.split(",");
					}else{
						sql_pattern = ms_post.split(",");
					}
					//현재페이지 정보값(관리자메뉴 > 키워드관리는 예외처리)
					String thisPageName = request.getRequestURI().toLowerCase();
					if(thisPageName != null && !thisPageName.contains("/ncms/sql/write") && !thisPageName.contains("/ncms/cms/cssOk")) {
						while (eNames.hasMoreElements()) {
							String name = (String) eNames.nextElement();
							String values = request.getParameter(name);
							values = values.toLowerCase();
							for(int i=0;i<=sql_pattern.length-1;i++){
								if( values.indexOf( sql_pattern[i] ) >= 0 ){
									//System.out.println("k=["+sql_pattern[i]+"]");
									//System.out.println("v="+values);
									response.sendRedirect("/error/badreq.do");
									return false;
								}
							}
						}
					}
				}
			}
		}
		System.out.println("-- injection check ok");
		//----------- 인젝션 필터끝 -------------
		
		//----------- 빌더정보 -------------
		String requestURI = Func.getNowPage(request).split("[?]")[0];
		if(requestURI == null){//현재 경로값이 정상이 아니라면
			response.sendRedirect("/error/badreq.do");
			return false;
		}
		
		//빌더네임 추출
		String[] path_arr = requestURI.split("/");
		String builder_dir = path_arr[1];
		
		//등록된 사이트 체크
		selectdto = new SelectDTO();
		selectdto.setExTableName("builder_site");
		selectdto.setExKeyColumn("bs_num");
		selectdto.setExColumn("bs_num, bs_sitename, bs_directory, bs_main, bs_skin, bs_writer, bs_logo, bs_header_meta, bs_copyright");
		String where_query = "where bs_directory = '"+builder_dir+"'";
		if(!"1".equals(Func.getSession(request, "ss_g_num"))){
			where_query += " and bs_chk = 'Y'";
		}
		selectdto.setExWhereQuery(where_query);
		HashMap<String,String>builder = exdao.selectQueryRecord(selectdto);
		if(builder.size() == 0){
			response.sendRedirect("/error/error.do");
			return false;
		}
		request.setAttribute("BUILDER", builder);
		request.setAttribute("BUILDER_DIR", builder_dir);
		request.setAttribute("BUILDER_SKIN", builder.get("bs_skin"));
		request.setAttribute("BUILDER_MAIN", builder.get("bs_main"));
		System.out.println("-- builder check ok");
		//----------- 빌더정보끝 -------------
		
		
		//----------- 접속통계 -------------
		String dbname = CommonConfig.get("jdbc.dbname");
		String is_cooke_chk = Func.getCookie(request, "VCounter_Cookie_"+builder_dir);
		if(is_cooke_chk.equals("") && !request.getRequestURI().contains("/error/")){
			Func.setCookie(request, response, "VCounter_Cookie_"+builder_dir, "checked");
			
			String info				= request.getHeader("User-Agent");
			String vIp				= Func.remoteIp(request);
			int vMM				= Func.cInt(Func.date("m"));
			String vDD				= Func.date("d");
			String vHH				= Func.date("H");
			String vMT				= Func.date("i");
			String vDw			= Func.date("w");
			String vBrowser		= "-";
			String vOs				= "-";
			String vURL			= request.getHeader("referer");
			String vDomain		= "";

			if(info != null){
				UserAgent agent = UserAgent.parseUserAgentString(info);
				Browser browser = agent.getBrowser(); //브라우저정보
				OperatingSystem os = agent.getOperatingSystem(); //os정보
				vBrowser = browser.getName();
				vOs = os.getName();
				if("Robot/Spider".equals(vBrowser)) {
					vOs = "-";
					vBrowser = "-";
				}
			}
			int vSeason			= 0;

			switch(vMM) {
				case 3  :	vSeason = 1; break;
				case 4  :	vSeason = 1; break;
				case 5  :	vSeason = 1; break;
				case 6  :	vSeason = 2; break;
				case 7  :	vSeason = 2; break;
				case 8  :	vSeason = 2; break;
				case 9  :	vSeason = 3; break;
				case 10 :	vSeason = 3; break;
				case 11 :	vSeason = 3; break;
				case 12 :	vSeason = 4; break;
				case 1  :	vSeason = 4; break;
				case 2  :	vSeason = 4; break;
			}
			
			String count_table = "visit_counter_site_"+Func.date("Ym");
			
			
			//테이블 조회
			int table_cnt = exdao.searchTableCount(dbname, count_table);
			if(table_cnt == 0){
				//테이블생성
				exdao.createBuilderSiteVisitTable(count_table, "vcountersite"+Func.date("Ym"));
			}
			
			//데이터등록
			VisitCounterSiteDTO visitCounter = new VisitCounterSiteDTO();
			visitCounter.setvIP(vIp);
			visitCounter.setvDD(vDD);
			visitCounter.setvHH(vHH);
			visitCounter.setvMT(vMT);
			visitCounter.setvSeason(Func.cStr(vSeason));
			visitCounter.setvDW(vDw);
			visitCounter.setvBrowser(Func.nvl(vBrowser));
			visitCounter.setvOS(Func.nvl(vOs));
			visitCounter.setvURL(Func.nvl(vURL));
			visitCounter.setvDomain(Func.nvl(vDomain));
			visitCounter.setvSite_dir(builder_dir);
			
			visitCounter.setExTableName(count_table);
			exdao.insert(visitCounter);
			
			
		}
		System.out.println("-- visit_counter check ok");
		//----------- 접속통계 끝 -------------
		
		
		//----------- 메뉴통계(관리자아니면) -------------
		//모바일 체크
		boolean FLAG_MOBILE = false;
		
		String[] tmp_arr_mobile = {"android","iphone"};
		for(int m=0; m<tmp_arr_mobile.length; m++){
			String tmp_agent_str = Func.cStr(request.getHeader("User-Agent")).toLowerCase();
			System.out.println(tmp_arr_mobile[m]+"="+tmp_agent_str.indexOf(tmp_arr_mobile[m]));
			if(tmp_agent_str.indexOf(tmp_arr_mobile[m]) != -1){
				FLAG_MOBILE = true;
				break;
			}
		}
		
		if ("Y".equals(request.getParameter("mobile"))){
			FLAG_MOBILE = true;
		}
		//모바일세션생성
		if (FLAG_MOBILE) {
			Func.setSession(request, "pc_version","N" );
		}else {
			Func.setSession(request, "pc_version","Y" );
		}
		
		if (!request.getRequestURI().contains("/ncms/")) {
			//String page = request.getQueryString();
			String page = Func.getNowPage(request);
			if(page == null) page = Func.getNowPage(request);
			if(page == null){page = "";}
			String page_id = "-1";
			String page_codeno = "";
			
			String vDD				= Func.date("d");
			String vHH				= Func.date("H");
			String vIp				= Func.remoteIp(request);
			String vOut = "Y";
			if ("".equals(vIp)){	// 내부 아이피 설정
				vOut = "N";
			}
			String mobile = "N";
			if (FLAG_MOBILE) {
				mobile = "Y";
			}
			
			String count_table = "menu_counter_"+Func.date("Ym");
			//테이블생성
			int table_cnt = exdao.searchTableCount(dbname, count_table);
			if(table_cnt == 0){
				HashMap<String, String> counter = new HashMap<String, String>();
				counter.put("count_table", count_table);
				exdao.createMenuTable(count_table);
			}
			
			
			if ("/".equals(page) || "".equals(page) || "/main.do".equals(page) ){
			}else{
				selectdto = new SelectDTO();
				selectdto.setExTableName("homepage_menu");
				selectdto.setExColumn("ct_idx, ct_codeno");
				selectdto.setExRecordCount(1);
				//selectdto.setExWhereQuery("WHERE ct_menu_url LIKE '%"+page+"%'");
				if(page.contains("/contents.do?idx=")){
					selectdto.setExWhereQuery("WHERE ct_menu_url = '"+page+"'");
				}else{
					selectdto.setExWhereQuery("WHERE ct_menu_url like '%"+page+"%'");
				}
				selectdto.setExOrderByQuery("order by ct_depth desc");
				HashMap<String,String>cms = exdao.selectQueryRecord(selectdto);
				if(cms.size() > 0){
					page_id = Func.cStr(cms.get("ct_idx"));
					page_codeno = Func.cStr(cms.get("ct_codeno"));
				}
			}
			//데이터 있는지 조회
			selectdto = new SelectDTO();
			selectdto.setExTableName(count_table);
			selectdto.setExColumn("idx");
			where_query = "WHERE page_id='"+exdao.filter(page_id)+"'";
			where_query += " and vDD = '"+exdao.filter(vDD)+"'";
			where_query += " and vHH = '"+exdao.filter(vHH)+"'";
			where_query += " and vOut = '"+exdao.filter(vOut)+"'";
			where_query += " and mobile = '"+exdao.filter(mobile)+"'";
			where_query += " and site_dir = '"+exdao.filter(builder_dir)+"'";
			selectdto.setExWhereQuery(where_query);
			String idx = exdao.selectQueryColumn(selectdto);
			MenuCounterDTO menuCounter = new MenuCounterDTO();
			if(idx == null){
				menuCounter.setExTableName(count_table);
				
				menuCounter.setPage_id(page_id);
				menuCounter.setvDD(vDD);
				menuCounter.setvHH(vHH);
				menuCounter.setCounter("1");
				menuCounter.setvOut(vOut);
				menuCounter.setMobile(mobile);
				menuCounter.setPage_codeno(page_codeno);
				menuCounter.setSite_dir(builder_dir);
				exdao.insert(menuCounter);
			}else{
				exdao.executeQuery("update "+count_table+" set counter = counter + 1 where idx = '"+exdao.filter(idx)+"'");
			}

		}
		
		System.out.println("-- menu_counter check ok");
		
		//리턴페이지&공통파라미터 ================================
		String DOMAIN_HTTP = Func.nvl( CommonConfig.get("DOMAIN_HTTP") );
		request.setAttribute("DOMAIN_HTTP", DOMAIN_HTTP);
		String DOMAIN_HTTPS = Func.nvl( CommonConfig.get("DOMAIN_HTTPS") );
		request.setAttribute("DOMAIN_HTTPS", DOMAIN_HTTPS);
		//리턴페이지&공통파라미터 ================================
		
		//현재 url
		Func.getNowPage(request);
		
		System.out.println("BuilderInterceptor.preHandle finish");
		return super.preHandle(request, response, handler);
	}
	
	
	@Override
	// 컨트롤러 후처리
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
		}
	}
}
