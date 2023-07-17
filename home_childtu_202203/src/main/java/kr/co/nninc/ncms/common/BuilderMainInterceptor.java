package kr.co.nninc.ncms.common;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

public class BuilderMainInterceptor  extends HandlerInterceptorAdapter{

protected Log log = LogFactory.getLog(BuilderMainInterceptor.class);
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	@Override
	// 컨트롤러 전처리
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("BuilderMainInterceptor.preHandle start");
		
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
		SelectDTO selectdto = new SelectDTO();
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
		//----------- 빌더정보끝 -------------
		
		
		//리턴페이지&공통파라미터 ================================
		String DOMAIN_HTTP = Func.nvl( CommonConfig.get("DOMAIN_HTTP") );
		request.setAttribute("DOMAIN_HTTP", DOMAIN_HTTP);
		String DOMAIN_HTTPS = Func.nvl( CommonConfig.get("DOMAIN_HTTPS") );
		request.setAttribute("DOMAIN_HTTPS", DOMAIN_HTTPS);
		//리턴페이지&공통파라미터 ================================
		
		//현재 url
		Func.getNowPage(request);
		
		System.out.println("BuilderMainInterceptor.preHandle finish");
		return super.preHandle(request, response, handler);
	}
	
	
	@Override
	// 컨트롤러 후처리
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
		}
	}
}
