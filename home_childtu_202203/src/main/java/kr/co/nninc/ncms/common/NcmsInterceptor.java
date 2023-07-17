package kr.co.nninc.ncms.common;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.site_info.service.SiteConfigDTO;

public class NcmsInterceptor extends HandlerInterceptorAdapter {
	protected Log log = LogFactory.getLog(NcmsInterceptor.class);

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;

	@Override
	// 컨트롤러 전처리
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("NcmsInterceptor.preHandle start");
		HttpSession session = request.getSession();
		
		//select전용 dto
		SiteConfigDTO selectdto = new SiteConfigDTO();
		
		//관리자 접속IP 체크
		selectdto.setExTableName("site_config");
		selectdto.setExColumn("sc_manager_ip, sc_manager_mobile");
		HashMap<String,String>infodto = exdao.selectQueryRecord(selectdto);
		
		if("Y".equals(infodto.get("sc_manager_ip"))) { //IP 체크
			selectdto = new SiteConfigDTO();
			selectdto.setExTableName("manager_ip");
			selectdto.setExColumn("count(*)");
			String where_query = "WHERE mi_chk='Y' AND mi_sdate <> '' AND mi_sdate is not null";
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
		if("N".equals(infodto.get("sc_manager_mobile"))){
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
		
		if ("/ncms/login.do".equals(request.getRequestURI()) || "/ncms/loginOk.do".equals(request.getRequestURI())) {
			return super.preHandle(request, response, handler);
		} else {
			if (!"ok".equals(Func.getSession(request, "ss_security_ad_cms")) || Func.getSession(request, "ss_security_ad_cms") == null) {
				if ("/ncms/board_config/member_search.do".equals(request.getRequestURI())) {
					response.sendRedirect("/error/parent_redirect.do");
					return false;
				}else {
					response.sendRedirect("/ncms/login.do");
					return false;
				}
			}else {
				//현재페이지 정보값(권한있는지 체크하기)
				String ss_g_num = Func.getSession(request, "ss_g_num");
				String url = Func.nvl(request.getRequestURI());
				if(!"".equals(url) && !"1".equals(ss_g_num) && !"/ncms/info/site_config_ok.do".equals(url) && !"/ncms/manager_memo/writeOk.do".equals(url)) {
					String[] urlArr = url.split("\\/");
					if(urlArr.length > 3) {
						selectdto = new SiteConfigDTO();
						selectdto.setExTableName("manager_menu_access");
						selectdto.setExColumn("count(*) as checked");
						String where_query = "where g_num = '"+exdao.filter(ss_g_num)+"'";
						where_query += " and ct_idx in (SELECT ct_idx FROM manager_menu WHERE ct_folder LIKE '%"+exdao.filter(urlArr[2])+",%')";
						selectdto.setExWhereQuery(where_query);
						int chk = Func.cInt( exdao.selectQueryColumn(selectdto) );
						if(chk == 0) {
							response.sendRedirect("/error/auth.do");
							return false;
						}
					}
				}
			}
			
			//그냥 총관리자페이지는 g_num:1만 접속시키자	20200815
			if(!"1".equals(Func.getSession(request, "ss_g_num"))){
				response.sendRedirect("/error/auth.do");
				return false;
			}
			
			request.setAttribute("mngleft_ck", "Y");
			System.out.println("NcmsInterceptor.preHandle start");
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
