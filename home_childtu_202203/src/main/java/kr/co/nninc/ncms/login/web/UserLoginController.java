package kr.co.nninc.ncms.login.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.FuncMember;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.login.service.UserLoginService;


@Controller
public class UserLoginController {

	/** UserCmsService */
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;
	
	/** UserLoginService */
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/** 로그인 처리 */
	@RequestMapping( value={"/member/loginOk.do","/*/member/loginOk.do"} )
	public String userLoginProc(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("response", response);
		
		return userLoginService.loginOk(model);
	}
	
	/** 로그아웃 처리 */
	@RequestMapping( value={"/member/logout.do","/*/member/logout.do"} )
	public String userLogout(HttpServletRequest request, Model model) throws Exception {
		FuncMember.memgr_logout(request);
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){
			prepage = "/";
			if(!"".equals(builder_dir)){
				prepage = "/"+builder_dir+prepage;
			}
		}
		
		return "redirect:"+prepage;
	}
	
	
}
