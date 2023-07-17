package kr.co.nninc.ncms.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController {

	/**************  Information  ***********************************
	* @Title : 에러페이지
	* @method : error
	/****************************************************************/
	@RequestMapping(value="/ncms/error.do")
	public String error(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("request",request);
		
		return "/ncms/error/errorPage";
	}

	/**************  Information  ***********************************
	* @Title : 에러페이지(관리자 접속IP 체크)
	* @method : errorIp
	/****************************************************************/
	@RequestMapping(value="/error/ip.do")
	public String iperror(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("errorMsg", "Can not connect to your IP. (YourIP:"+Func.remoteIp(request)+")");
		return "/site/common/errorPage";
	}
	
	/**************  Information  ***********************************
	* @Title : 에러페이지(아이디별 관리자 접속IP 체크)
	* @method : errorIpId
	/****************************************************************/
	@RequestMapping(value="/error/ipid.do")
	public String errorIpId(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("errorMsg", "해당 계정에 등록된 IP 가 아닙니다. (YourIP:"+Func.remoteIp(request)+")");
		return "/site/common/errorPage";
	}
	
	/**************  Information  ***********************************
	* @Title : 접근권한없을때
	* @method : errorAuth
	/****************************************************************/
	@RequestMapping(value="/error/auth.do")
	public String errorAuth(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("msg", "접근권한이 없습니다. (101)");
		return "/site/common/back_msg";
	}

	/**************  Information  ***********************************
	* @Title : 에러페이지(관리자 모바일 접속체크)
	* @method : errorMobile
	/****************************************************************/
	@RequestMapping(value="/error/mobile.do")
	public String errorMobile(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("errorMsg","Mobile devices can not connect.");
		return "/site/common/errorPage";
	}

	/**************  Information  ***********************************
	* @Title : 에러페이지(BadRequest)
	* @method : errorBadReq
	/****************************************************************/
	@RequestMapping(value="/error/badreq.do")
	public String errorBadReq(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("errorMsg", "Bad Request.");
		return "/site/common/errorPage";
	}

	/**************  Information  ***********************************
	* @Title : 에러페이지(세션)
	* @method : errorWait
	/****************************************************************/
	@RequestMapping(value="/error/wait.do")
	public String errorWait(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("request",request);
		return "/site/common/wait";
	}

	/**************  Information  ***********************************
	* @Title : 에러페이지(사용자용)
	* @method : error
	/****************************************************************/
	@RequestMapping(value="/error/error.do")
	public String error2(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("request",request);
		return "/site/common/errorPage";
	}

	/**************  Information  ***********************************
	* @Title : 에러페이지(페이지접근권한사용자쪽)
	* @method : errorAuth2
	/****************************************************************/
	@RequestMapping(value="/error/auth2.do")
	public String auth2(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("msg", "페이지 접근권한이 없습니다.");
		model.addAttribute("url", "/");
		return "/site/common/redirect_msg";
	}

	/**************  Information  ***********************************
	* @Title : 에러페이지(페이지접근권한사용자쪽)
	* @method : errorAuth3
	/****************************************************************/
	@RequestMapping(value="/error/auth3.do")
	public String auth3(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("msg", "페이지에 접근할수없습니다");
		model.addAttribute("url", "/");
		return "/site/common/redirect_msg";
	}
	
	/**************  Information  ***********************************
	* @Title : iframe에서 > 로그인 페이지로 이동
	* @method : login
	/****************************************************************/
	@RequestMapping(value="/error/parent_redirect.do")
	public String login(HttpServletRequest request,  Model model) throws Exception{
		model.addAttribute("url", "/ncms/login.do");
		return "/site/common/parent_redirect_msg";
	}
}