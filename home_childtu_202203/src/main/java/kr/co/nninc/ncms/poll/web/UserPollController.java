package kr.co.nninc.ncms.poll.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.poll.service.PollDTO;
import kr.co.nninc.ncms.poll.service.UserPollService;

/**
 * 설문조사를 위한 비즈니스 구현 클래스(사용자용)
 * 
 * @author 나눔
 * @since 2017.11.02
 * @version 1.0
 */
@Controller
public class UserPollController {
	
	/** UserPollService */
	@Resource(name = "userPollService")
	private UserPollService userPollService;
	
	/** UserCmsService */
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;
	
	/**************  Information  ****************************************
	* @Title : 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value = {"/poll/list.do","/*/poll/list.do"})
	public String list(HttpServletRequest request, Model model )throws Exception {
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		// 설문조사리스트
		userPollService.list(model);
		
		return "/site/poll/list";
	}
	
	/**************  Information  ****************************************
	* @Title : 결과보기
	* @method : result
	/****************************************************************/
	@RequestMapping(value = {"/poll/result.do","/*/poll/result.do"})
	public String result(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		String rtn_url = userPollService.result(model);
		return rtn_url;
		
	}
	
	/**************  Information  ****************************************
	* @Title : 주관식내용보기
	* @method : result
	/****************************************************************/
	@RequestMapping(value = {"/poll/view.do","/*/poll/view.do"})
	public String view(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		userPollService.view(model);
		return "/site/poll/view";
	}
	
	/**************  Information  ****************************************
	* @Title : 설문조사하기
	* @method : list
	/****************************************************************/
	@RequestMapping(value = {"/poll/poll.do","/*/poll/poll.do"})
	public String poll(HttpServletRequest request, @ModelAttribute("polldto") PollDTO polldto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("polldto", polldto);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		// 설문조사하기
		String rtn_url = userPollService.poll(model);
		return rtn_url;
	}
	
	/**************  Information  ****************************************
	* @Title : 투표하기
	* @method : list
	/****************************************************************/
	@RequestMapping(value = {"/poll/pollOk.do","/*/poll/pollOk.do"})
	public String pollOk(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("response", response);
		
		//허용 url
		if(Func.urlChk(model,"poll.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			//탑메뉴
			userCmsService.top(model, request);
			//레프트메뉴
			userCmsService.left(model, request);
			//하단메뉴
			userCmsService.foot(model, request);
			
			//String rtn_url = userPollService.pollOk(model);
			//return rtn_url;
			return userPollService.pollOk(model);
		}
	}

}
