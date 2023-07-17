package kr.co.nninc.ncms.buseo.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.buseo.service.BuseoDTO;
import kr.co.nninc.ncms.buseo.service.UserBuseoService;
import kr.co.nninc.ncms.cms.service.UserCmsService;

/**
 * 부서 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2021.04.19
 * @version 1.0
 */
@Controller
public class UserBuseoController {
	
	/** UserBuseoService */
	@Resource(name="userBuseoService")
	private UserBuseoService userBuseoService;
	
	/** UserCmsService */
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;
	
	/**************  Information  ****************************************
	* @Title : 부서 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value = {"/buseo/list.do","/*/buseo/list.do"})
	public String list(HttpServletRequest request, @ModelAttribute("buseodto") BuseoDTO buseodto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("buseodto", buseodto);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
				
		// 부서리스트
		return userBuseoService.list(model);
	}
	
	/**************  Information  ****************************************
	* @Title : 검색
	* @method : search
	/****************************************************************/
	@RequestMapping(value = {"/buseo/search.do","/*/buseo/search.do"})
	public String search(HttpServletRequest request, @ModelAttribute("buseodto") BuseoDTO buseodto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("buseodto", buseodto);
				
		// 부서리스트
		userBuseoService.search(model);
		return "/site/buseo/search";
	}

}
