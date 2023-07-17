package kr.co.nninc.ncms.buseo_member.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.nninc.ncms.buseo_member.service.BuseoMemberDTO;
import kr.co.nninc.ncms.buseo_member.service.BuseoMemberService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;

/**
 * 조직원 구성원 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2018.11.16
 * @version 1.0
 */
@Controller
public class BuseoMemberController {
	
	/** BuseoMemberService */
	@Resource(name="buseoMemberService")
	private BuseoMemberService buseoMemberService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	@Resource(name = "messageService")
	private MessageService messageService;

	/**************  Information  ***********************************
	* @Title : 목록
	* @method : write
	/************************************************************/
	@RequestMapping(value = {"/ncms/buseo_member/write.do", "/*/ncms/buseo_member/write.do"})
	public String write(HttpServletRequest request, @ModelAttribute("buseomemberdto") BuseoMemberDTO buseomemberdto, Model model) throws Exception{
		model.addAttribute("request", request);
		model.addAttribute("buseomemberdto", buseomemberdto);
		model.addAttribute("mng_left_cd", CommonConfig.get("business_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}

		buseoMemberService.write(model);
		return "/ncms/buseo_member/write";
	}
	

	/**************  Information  ***********************************
	* @Title : 조직원 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/buseo_member/writeOk.do", "/*/ncms/buseo_member/writeOk.do"}, method = RequestMethod.POST)
	public String writeOk(HttpServletRequest request, @ModelAttribute("buseomemberdto") BuseoMemberDTO buseomemberdto
			, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("buseomemberdto", buseomemberdto);
		
		String m_num = Func.nvl(request.getParameter("m_num"));
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String rtn_url = "";
			//수정처리
			if (Func.cInt(m_num) != 0) {
				rtn_url = buseoMemberService.modifyOk(model);
			}else { //등록처리
				rtn_url = buseoMemberService.writeOk(model);
			}
			return rtn_url;
		}
	}
	
	
	/**************  Information  ***********************************
	* @Title : 조직원 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/buseo_member/deleteOk.do", "/*/ncms/buseo_member/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, @ModelAttribute("buseomemberdto") BuseoMemberDTO buseomemberdto
			, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("buseomemberdto", buseomemberdto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			return buseoMemberService.deleteOk(model);
		}
	}

	/**************  Information  ***********************************
	* @Title : 조직원 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value = {"/ncms/buseo_member/listMove.do", "/*/ncms/buseo_member/listMove.do"})
	public String listMove(HttpServletRequest request, @ModelAttribute("buseomemberdto") BuseoMemberDTO buseomemberdto
			, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("buseomemberdto", buseomemberdto);

		buseoMemberService.listMove(model);
		return "/ncms/buseo_member/listMove";
	}

	/**************  Information  ***********************************
	* @Title : 조직원 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/buseo_member/listMoveOk.do", "/*/ncms/buseo_member/listMoveOk.do"})
	public String listMoveOk(HttpServletRequest request, @ModelAttribute("buseomemberdto") BuseoMemberDTO buseomemberdto
			, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("buseomemberdto",buseomemberdto);

		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			return buseoMemberService.listMoveOk(model);
		}
	}

}
