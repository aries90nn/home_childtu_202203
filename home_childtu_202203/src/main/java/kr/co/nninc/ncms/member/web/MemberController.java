package kr.co.nninc.ncms.member.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.member.service.MemberDTO;
import kr.co.nninc.ncms.member.service.MemberService;


/**
 * 회원 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.12
 * @version 1.0
 */
@Controller
public class MemberController {

	/** 회원관리 서비스 */
	@Resource(name = "memberService")
	private MemberService memberService;
	
	/** 관리자 메뉴 서비스 */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ****************************************
	* @Title : 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value = {"/ncms/member/list.do", "/*/ncms/member/list.do"})
	public String list(HttpServletRequest request, @ModelAttribute("memberdto") MemberDTO memberdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("member_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		memberService.list(model);
		
		return "/ncms/member/list";
	}
	
	/**************  Information  ****************************************
	* @Title : 회원 등록폼
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/ncms/member/write.do","/*/ncms/member/write.do"})
	public String write(HttpServletRequest request, @ModelAttribute("memberdto") MemberDTO memberdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("memberdto", memberdto);
		model.addAttribute("mng_left_cd", CommonConfig.get("member_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		System.out.println("builder_dir="+builder_dir);
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		
		String rtn_url = "";
		String m_num = Func.nvl( request.getParameter("m_num") );
		
		//수정폼
		if(!"".equals(m_num)) {
			rtn_url = memberService.modify(model);
		}else { //등록폼
			rtn_url = memberService.write(model);
		}
		return rtn_url;
	}
	
	/**************  Information  ****************************************
	* @Title : 비밀번호 확인
	* @method : modify3
	/****************************************************************/
	@RequestMapping(value = {"/ncms/member/modify3.do","/*/ncms/member/modify3.do"})
	public String modify3(HttpServletRequest request, @ModelAttribute("memberdto") MemberDTO memberdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("memberdto", memberdto);
		model.addAttribute("mng_left_cd", CommonConfig.get("member_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		
		Func.getNowPage(request);
		
		return "/ncms/member/modify3";
	}
	
	/**************  Information  ****************************************
	* @Title :회원 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/member/writeOk.do","/*/ncms/member/writeOk.do"}, method = RequestMethod.POST)
	public String writeOk(HttpServletRequest request, @ModelAttribute("memberdto") MemberDTO memberdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("memberdto", memberdto);

		String rtn_url = "";
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			//수정처리
			String m_num = Func.nvl( request.getParameter("m_num") ).trim();
			if("".equals(m_num)) {
				rtn_url = memberService.writeOk(model);
			}else { //등록처리
				rtn_url = memberService.modifyOk(model);
			}
		}

		return rtn_url;
	}
	
	/**************  Information  ****************************************
	* @Title : 회원 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/member/deleteOk.do","/*/ncms/member/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, @ModelAttribute("memberdto") MemberDTO memberdto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("memberdto", memberdto);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			memberService.deleteOk(model);
			String prepage = Func.nvl(request.getParameter("prepage"));
			if("".equals(prepage)){prepage= "/ncms/member/list.do";}
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/member/levelOk.do","/*/ncms/member/levelOk.do"})
	public String levelOk(HttpServletRequest request, @ModelAttribute("memberdto") MemberDTO memberdto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("memberdto", memberdto);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			memberService.levelOk(model);
			String prepage = Func.nvl(request.getParameter("prepage"));
			if("".equals(prepage)){prepage= "/ncms/member/list.do";}
			return "redirect:"+prepage;
		}
	}
}
