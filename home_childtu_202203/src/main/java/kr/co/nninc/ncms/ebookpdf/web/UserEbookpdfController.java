package kr.co.nninc.ncms.ebookpdf.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.ebookpdf.service.UserEbookpdfService;

@Controller
public class UserEbookpdfController {
	
	// User PDF Service
	@Resource(name = "userEbookpdfService")
	private UserEbookpdfService userEbookpdfService;
	
	
	// User CMS Service
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;
	
	
	/**************  Information  ****************************************
	* @Title : 뷰어
	* @method : viewer
	/****************************************************************/
	@RequestMapping(value={"/ebookpdf/viewer/index.do", "/*/ebookpdf/viewer/index.do"})
	public String viewer(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		userEbookpdfService.viewer(model);
		return "/site/ebookpdf/viewer/index";
	}
	
	
	
	/**************  Information  ****************************************
	* @Title : 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value={"/ebookpdf/list.do", "/*/ebookpdf/list.do"})
	public String list(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		userEbookpdfService.list(model);
		return "/site/ebookpdf/list";
	}

}
