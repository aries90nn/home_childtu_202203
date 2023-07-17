package kr.co.nninc.ncms.page.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.manager_menu.service.MenuService;

/**
 * 페이지를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.11.30
 * @version 1.0
 */
@Controller
public class PageController {

	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** UserCmsService */
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;
	
	/**************  Information  ****************************************
	* @Title : 디자이너용
	/****************************************************************/
	@RequestMapping(value = {"/site/**/*.do","/*/site/**/*.do"})
	public String hour(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		String url = request.getRequestURI().replaceAll("\\.do", "");
		System.out.println("1.page.request_url="+url);
		//빌더 사이트에서 접속
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			url = url.replaceFirst("/"+builder_dir, "");
		}
		System.out.println("2.page.page_url="+url);
		return url;
	}
	
	/**************  Information  ****************************************
	* @Title : ncms 추가 jsp
	/****************************************************************/
	@RequestMapping(value = {"/ncms/site/**/*.do","/*/ncms/site/**/*.do"})
	public String ncmspage(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		String url = request.getRequestURI().replaceAll("\\.do", "");
		System.out.println("1.page.request_url="+url);
		//빌더 사이트에서 접속
		if(!"".equals(builder_dir)){
			url = url.replaceFirst("/"+builder_dir, "");
		}
		System.out.println("2.page.page_url="+url);
		return url;
	}
}
