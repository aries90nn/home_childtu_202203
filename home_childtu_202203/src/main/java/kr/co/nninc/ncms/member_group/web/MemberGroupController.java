package kr.co.nninc.ncms.member_group.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.member_group.service.MemberGroupDTO;
import kr.co.nninc.ncms.member_group.service.MemberGroupService;


/**
 * 회원그룹설정을 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.12
 * @version 1.0
 */
@Controller
public class MemberGroupController {
	@Resource(name = "memberGroupService")
	private MemberGroupService memberGroupService;
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ****************************************
	* @Title : 회원그룹 관리
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/ncms/member_group/write.do","/*/ncms/member_group/write.do"})
	public String write(HttpServletRequest request, @ModelAttribute("membergroupdto") MemberGroupDTO membergroupdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("membergroupdto", membergroupdto);
		model.addAttribute("mng_left_cd", CommonConfig.get("member_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		memberGroupService.write(model);
		return "/ncms/member_group/write";
	}
	
	/**************  Information  ****************************************
	* @Title : 회원그룹 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/member_group/writeOk.do","/*/ncms/member_group/writeOk.do"})
	public String writeOk(HttpServletRequest request, @ModelAttribute("membergroupdto") MemberGroupDTO membergroupdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("membergroupdto", membergroupdto);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String g_num = Func.nvl( request.getParameter("g_num") );
			//수정처리
			if(!"".equals(g_num)) {
				memberGroupService.modifyOk(model);
			}else { //등록처리
				memberGroupService.writeOk(model);
			}
		}
		return "redirect:write.do";
	}
	
	/**************  Information  ****************************************
	* @Title : 회원그룹 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/member_group/deleteOk.do","/*/ncms/member_group/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, @ModelAttribute("membergroupdto") MemberGroupDTO membergroupdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("membergroupdto", membergroupdto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			memberGroupService.deleteOk(model);
		}
		return "redirect:write.do";
	}
	

	/**************  Information  ***********************************
	* @Title : 회원그룹 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/member_group/levelOk.do","/*/ncms/member_group/levelOk.do"})
	public String levelOk(HttpServletRequest request, @ModelAttribute("membergroupdto") MemberGroupDTO membergroupdto, Model model)
			throws Exception {
		model.addAttribute("request",request);
		model.addAttribute("membergroupdto", membergroupdto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			memberGroupService.levelOk(model);
			return "redirect:write.do";
		}
	}
	
	/**************  Information  ************************************
	* @Title : 회원그룹 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value={"/ncms/member_group/listMove.do","/*/ncms/member_group/listMove.do"})
	public String listMove(HttpServletRequest request, @ModelAttribute("membergroupdto") MemberGroupDTO membergroupdto, Model model)
			throws Exception {
		model.addAttribute("request",request);
		
		memberGroupService.listMove(model);
		return "/ncms/member_group/listMove";
	}
	
	/**************  Information  ************************************
	* @Title : 회원그룹 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/member_group/listMoveOk.do","/*/ncms/member_group/listMoveOk.do"})
	public String listMoveOk(HttpServletRequest request, @ModelAttribute("membergroupdto") MemberGroupDTO membergroupdto, Model model)
			throws Exception {
		model.addAttribute("request",request);
		model.addAttribute("membergroupdto", membergroupdto);
		
		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			memberGroupService.listMoveOk(model);
			return messageService.closeRedirect(model, "", "./write.do");
		}
	}

	/**************  Information  ****************************************
	* @Title : 회원그룹 권한관리
	* @method : awrite
	/****************************************************************/
	@RequestMapping(value = {"/ncms/member_group/awrite.do","/*/ncms/member_group/awrite.do"})
	public String awrite(HttpServletRequest request, @ModelAttribute("membergroupdto") MemberGroupDTO membergroupdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("membergroupdto", membergroupdto);
		model.addAttribute("mng_left_cd", CommonConfig.get("member_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		memberGroupService.awrite(model);
		return "/ncms/member_group/awrite";
	}
	
	/**************  Information  ****************************************
	* @Title : 회원그룹 권한관리 처리
	* @method : awriteOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/member_group/awriteOk.do","/*/ncms/member_group/awriteOk.do"})
	public String awriteOk(HttpServletRequest request, @ModelAttribute("membergroupdto") MemberGroupDTO membergroupdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("membergroupdto", membergroupdto);
		
		//허용 url
		if(Func.urlChk(model,"awrite.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			memberGroupService.awriteOk(model);
			String g_num = Func.nvl( request.getParameter("g_num") ).trim();
			return "redirect:awrite.do?g_num="+g_num;
		}
	}
	
}
