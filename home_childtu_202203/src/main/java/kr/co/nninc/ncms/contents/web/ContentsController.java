package kr.co.nninc.ncms.contents.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.cms.service.CmsService;
import kr.co.nninc.ncms.cms.service.UserCmsService;

/**
 * 컨텐츠 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.31
 * @version 1.0
 */
@Controller
public class ContentsController {

	/** CmsService */
	@Resource(name = "cmsService")
	private CmsService cmsService;
	
	/** UserCmsService */
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;
	
	/**************  Information  ************************************
	* @Title : 컨텐츠내용
	* @method : contents
	/****************************************************************/
	@RequestMapping({"/contents.do", "/*/contents.do"})
	public String contents(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단배너리스트
		userCmsService.foot(model, request);
		
		//사용자페이지
		String rtn_url = cmsService.user_contents(model);
		return rtn_url;
	}
	
}
