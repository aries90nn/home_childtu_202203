package kr.co.nninc.ncms.login.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.FuncMember;
import kr.co.nninc.ncms.login.service.LoginService;

/**
 * 로그인을 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.09.21
 * @version 1.0
 */

@Controller
public class LoginController {
	
	@Resource(name = "loginService")
	private LoginService loginService;

	/** 로그인 하기 */
	@RequestMapping({ "/ncms/login.do" })
	public String index(ModelMap model, HttpServletRequest request) throws Exception {
		return "/ncms/login";
	}
	
	/** 로그인 처리 */
	@RequestMapping({ "/ncms/loginOk.do" })
	public String nnincLogin(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		return loginService.loginOk(model);
		
	}
	
	/** 로그아웃 */
	@RequestMapping({ "/ncms/logout.do" })
	public String nnincLogout(HttpServletRequest request, Model model) throws Exception {
		FuncMember.memgr_logout(request);
		return "redirect:/ncms/index.do";
	}

}
