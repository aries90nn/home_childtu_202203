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
import kr.co.nninc.ncms.cms.service.CmsDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.stats.service.VisitCounterDTO;

public class VisitCountInterceptor extends HandlerInterceptorAdapter {
	protected Log log = LogFactory.getLog(VisitCountInterceptor.class);
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	@Override
	// 컨트롤러 전처리
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		
		//select전용으로 사용할 dto 여러 테이블에 재사용
		CmsDTO selectdto = new CmsDTO();
		
		//에러페이지에서는 인젝션검사&카운트 처리안되게(두번처리됨)
		if (!request.getRequestURI().contains("/error/")) {
			String is_cooke_chk = Func.getCookie(request, "VCounter_Cookie");
			if(is_cooke_chk.equals("")){
				Func.setCookie(request, response, "VCounter_Cookie", "checked");
				//
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
				
				String count_table = "visit_counter_"+Func.date("Ym");
				
				//테이블 조회
				String dbname = CommonConfig.get("jdbc.dbname");
				int table_cnt = exdao.searchTableCount(dbname, count_table);
				if(table_cnt == 0){
					//테이블생성
					exdao.createVisitTable(count_table);
				}
				
				

				//데이터등록
				VisitCounterDTO visitCounter = new VisitCounterDTO();
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
				
				visitCounter.setExTableName(count_table);
				exdao.insert(visitCounter);
				
				
				//총카운트 업데이트
				exdao.executeQuery("UPDATE total_counter SET count_value = count_value + 1");
			}
			
			
			//카피라이터 , 로고 , 사이트명 가져오기
			selectdto = new CmsDTO();	//초기화해서 재사용
			selectdto.setExTableName("site_config");
			selectdto.setExKeyColumn("sc_idx");
			selectdto.setExColumn("sc_sitename, sc_sitename_en, sc_company, sc_logo, sc_copyright, sc_header_meta");
			selectdto.setExOrderByQuery("order by sc_idx desc");
			HashMap<String,String>siteinfo = exdao.selectQueryRecord(selectdto);
			request.setAttribute("siteinfo", siteinfo);
			
			//----------- 인젝션 필터 -------------
			selectdto = new CmsDTO();	//초기화해서 재사용
			selectdto.setExTableName("manager_sql");

			selectdto.setExColumn("ms_get, ms_post");
			HashMap<String,String>managerSql = exdao.selectQueryRecord(selectdto);
			//ManagerSqlDTO managerSql = managerSqlDAO.view();
			if(managerSql != null) {
				String ms_get = Func.nvl(managerSql.get("ms_get"));
				if(ms_get.equals("")){ms_get = "', script, iframe";}	//최소한의 필터링
				String ms_post = Func.nvl(managerSql.get("ms_post"));
				ms_get = ms_get.replaceAll(" ", "");
				ms_post = ms_post.replaceAll(" ", "");
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
										response.sendRedirect("/error/badreq.do");
										return false;
									}
								}
							}
						}
					}
				}
			}
			//----------- 인젝션 필터끝 -------------
	
			//----------- 세션타임아웃 -------------
			selectdto = new CmsDTO();	//초기화해서 재사용
			selectdto.setExTableName("site_config");
			selectdto.setExColumn("sc_session_use, sc_session_time");
			HashMap<String,String>info = exdao.selectQueryRecord(selectdto);
			//InfoDTO info = infoDAO.view();
			if(info != null) {
				if(Func.cInt(info.get("sc_session_time")) > 0) {
					session.setMaxInactiveInterval(Func.cInt(info.get("sc_session_time")) * 60);
				}
			}
			//----------- 세션타임아웃끝 -------------
			//인젝션 필터
			if("Y".equals(info.get("sc_session_use"))) {
				String session_id = session.getId();
				//HashMap<String, ?> rs_ss = infoDAO.procSessionWait(session_id);
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
			
			//회원제 사용여부
			selectdto = new CmsDTO();	//초기화해서 재사용
			selectdto.setExTableName("member_config");
			selectdto.setExColumn("mc_join");
			String mc_join = exdao.selectQueryColumn(selectdto);
			request.setAttribute("auth_join", mc_join);
			
			//리턴페이지&공통파라미터 ================================
			String PATH_INFO = request.getRequestURI();
			String NOWPAGE = request.getRequestURI();
			String PATH_QUERY		= request.getQueryString();
			if(PATH_QUERY != null){
				NOWPAGE += "?"+PATH_QUERY;
			}
			String SERVER_NAME = (String) request.getServerName().toString();
			String USER_PORT		= Func.cStr(request.getServerPort());			//사용자포트
			if(!USER_PORT.equals("80")){
				SERVER_NAME += ":"+USER_PORT;
			}
			request.setAttribute("PATH_INFO", PATH_INFO);
			request.setAttribute("PATH_INFO_ENCODE", Func.urlEncode(PATH_INFO));
			request.setAttribute("SERVER_NAME", SERVER_NAME);
			request.setAttribute("NOWPAGE", NOWPAGE);
			request.setAttribute("NOWPAGE_ENCODE", Func.urlEncode(NOWPAGE));
			//
			String DOMAIN_HTTP = Func.nvl( CommonConfig.get("DOMAIN_HTTP") );
			request.setAttribute("DOMAIN_HTTP", DOMAIN_HTTP);
			String DOMAIN_HTTPS = Func.nvl( CommonConfig.get("DOMAIN_HTTPS") );
			request.setAttribute("DOMAIN_HTTPS", DOMAIN_HTTPS);
			
			Func.getNowPage(request);
			//리턴페이지&공통파라미터 ================================
			return super.preHandle(request, response, handler);
		}else {
			return super.preHandle(request, response, handler);
		}
	}

	@Override
	// 컨트롤러 후처리
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
		}
	}
}
