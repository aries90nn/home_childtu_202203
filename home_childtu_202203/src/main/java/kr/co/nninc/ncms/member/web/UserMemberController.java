package kr.co.nninc.ncms.member.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.member.service.MemberDTO;
import kr.co.nninc.ncms.member.service.UserMemberService;

/**
 * 회원 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2020.03.02
 * @version 1.0
 */
@Controller
public class UserMemberController {
	
	/** 회원관리 서비스 */
	@Resource(name = "userMemberService")
	private UserMemberService userMemberService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/** UserCmsService */
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;

	
	/**************  Information  ****************************************
	* @Title : 회원 등록폼
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/site/member/regist.do","/*/site/member/regist.do"})
	public String regist(HttpServletRequest request, @ModelAttribute("memberdto") MemberDTO memberdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("memberdto", memberdto);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		String rtn_url = userMemberService.regist(model);
		
		return rtn_url;
	}
	
	/**************  Information  ****************************************
	* @Title : 회원 등록처리
	* @method : registOk
	/****************************************************************/
	@RequestMapping(value = {"/member/registOk.do","/*/member/registOk.do"})
	public String registOk(HttpServletRequest request, @ModelAttribute("memberdto") MemberDTO memberdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("memberdto", memberdto);
		
		String m_num = Func.getSession(request, "ss_m_num").trim();
		if("".equals(m_num)){
			return userMemberService.registOk(model);
		}else{
			return userMemberService.modifyOk(model);
		}
	}
	
	
	/**************  Information  ****************************************
	* @Title : 회원 아이디 중복 체크
	* @method : ajaxIdCheck
	/****************************************************************/
	@RequestMapping(value = {"/member/ajaxIdCheck.do","/*/member/ajaxIdCheck.do"})
	@ResponseBody
	public String ajaxIdCheck(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		String cnt = Func.cStr( userMemberService.idCheck(model) );
		return cnt;
	}
	
	/**************  Information  ****************************************
	* @Title : 회원 이메일 중복 체크
	* @method : ajaxIdCheck
	/****************************************************************/
	@RequestMapping(value = {"/member/ajaxEmailCheck.do","/*/member/ajaxEmailCheck.do"})
	@ResponseBody
	public String ajaxajaxEmailCheckCheck(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		String cnt = Func.cStr( userMemberService.EmailCheck(model) );
		return cnt;
	}
	
	/**************  Information  ****************************************
	* @Title : 회원 수정폼
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/member/modify.do","/*/member/modify.do"})
	public String modify(HttpServletRequest request, @ModelAttribute("memberdto") MemberDTO memberdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("memberdto", memberdto);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		String rtn_url = userMemberService.modify(model);
		return rtn_url;
	}
	
	
	/**************  Information  ****************************************
	* @Title : 비밀번호변경폼
	* @method : pwdChange
	/****************************************************************/
	@RequestMapping(value = {"/member/pwdChange.do","/*/member/pwdChange.do"})
	public String pwdChange(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		//비번인증
		String ps_value = Func.nvl( request.getParameter("ps_value") ).trim();
		if("".equals(ps_value)){ps_value = Func.getSession(request, "ss_ps_value");}
		if("".equals(ps_value)){
			String action_url = Func.getNowPage(request);
			request.setAttribute("action_url", action_url);
			return "/site/member/pwdConfirm";
		}else if(!ps_value.equals(Func.getSession(request, "ss_m_pwd"))){
			return messageService.backMsg(model, "비밀번호를 다시 확인하세요.");
		}
		//세션변수는 1회용으로 사용
		Func.setSession(request, "ss_ps_value", "");
		
		
		
		return "/site/member/pwdChange";
	}
	
	/**************  Information  ****************************************
	* @Title : 비밀번호변경 처리
	* @method : pwdChangeOk
	/****************************************************************/
	@RequestMapping(value = {"/member/pwdChangeOk.do","/*/member/pwdChangeOk.do"})
	public String pwdChangeOk(HttpServletRequest request, Model model) throws Exception {
		
		String m_id = Func.getSession(request, "ss_m_id");
		String m_pwd = Func.nvl( request.getParameter("m_pwd") ).trim();
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){
			prepage = "/";
			String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			if(!"".equals(BUILDER_DIR)){
				prepage = "/"+BUILDER_DIR+prepage;
			}
			
		}
		
		String result = userMemberService.pwdChangeOk(m_id, m_pwd);
		
		if(!"ok".equals(result)){
			//return messageService.backMsg(model, "비밀번호변경 실패");
			//1회용 세션변수 - 다시 비번인증으로 돌아가지 않기 위해
			Func.setSession(request, "ss_ps_value", Func.getSession(request, "ss_m_pwd"));
			return messageService.redirectMsg(model, result, prepage);
		}
		
		Func.setSession(request, "ss_m_pwd", m_pwd);
		
		//1회용 세션변수
		Func.setSession(request, "ss_ps_value", m_pwd);
		
		return messageService.redirectMsg(model, "비밀번호가 변경되었습니다.", prepage);
	}
	
	
	/**************  Information  ****************************************
	* @Title : 아이디찾기
	* @method : idFind
	/****************************************************************/
	@RequestMapping(value = {"/member/idFind.do","/*/member/idFind.do"})
	public String idFind(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		return userMemberService.idFind(model);
	}
	
	/**************  Information  ****************************************
	* @Title : 비밀번호찾기
	* @method : pwdFind
	/****************************************************************/
	@RequestMapping(value = {"/member/pwdFind.do","/*/member/pwdFind.do"})
	public String pwdFind(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		System.out.println("여기 오지?");
		return userMemberService.pwdFind(model);
	}
	
	/**************  Information  ****************************************
	* @Title : 회원탈퇴
	* @method : secedeOk
	/****************************************************************/
	@RequestMapping(value = {"/member/secedeOk.do","/*/member/secedeOk.do"})
	public String secedeOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		return userMemberService.secedeOk(model);
	}

}
