package kr.co.nninc.ncms.login.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.FuncMember;
import kr.co.nninc.ncms.login.service.LoginService;

/**
 * 로그인을 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2019.04.29
 * @version 1.0
 */

@Controller
public class BuilderNcmsLoginController {

	@Resource(name = "loginService")
	private LoginService loginService;
	
	/** 로그인 하기 */
	@RequestMapping(value={ "/**/ncms/login.do", "/**/ncms/index.do", "/**/ncms/", "/**/ncms" })
	public String index(ModelMap model, HttpServletRequest request) throws Exception {
		System.out.println("BuilderNcmsLoginController.index");
		if("".equals(Func.getSession(request, "ss_m_id")) || !"ok".equals(Func.getSession(request, "ss_security_ad_cms"))){
			return "/site/builder/ncms/login";
		}else{
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			return "redirect:/"+builder_dir+"/ncms/cms/write.do";
		}
	}
	
	/** 로그인 처리 */
	@RequestMapping({ "/*/ncms/loginOk.do" })
	public String nnincLogin(HttpServletRequest request, Model model) throws Exception {
		System.out.println("BuilderNcmsLoginController.nnincLogin");
		model.addAttribute("request", request);
		return loginService.loginOk(model);
		
	}
	
	/** 로그아웃 */
	@RequestMapping({ "/*/ncms/logout.do" })
	public String nnincLogout(HttpServletRequest request, Model model) throws Exception {
		FuncMember.memgr_logout(request);
		String builder_dir = (String)request.getAttribute("BUILDER_DIR");
		return "redirect:/"+builder_dir+"/ncms/login.do";
	}
}
