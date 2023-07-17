package kr.co.nninc.ncms.board_code.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.board_code.service.BoardCodeDTO;
import kr.co.nninc.ncms.board_code.service.BoardCodeService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;

/**
 * 게시판 분류 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.20
 * @version 1.0
 */
@Controller
public class BoardCodeController {

	/** BoardCodeService */
	@Resource(name="boardCodeService")
	private BoardCodeService boardCodeService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ****************************************
	* @Title : 게시판 분류관리 목록
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/ncms/board_code/write.do","/*/ncms/board_code/write.do"})
	public String write(HttpServletRequest request, @ModelAttribute("boardcodedto") BoardCodeDTO boardcodedto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("boardcodedto", boardcodedto);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);
		
		String a_num = Func.nvl( request.getParameter("a_num") ).trim();
		if("".equals(a_num)) {
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}else {
			// 게시판 분류 리스트
			boardCodeService.write(model);
			return "/ncms/board_code/write";
		}
	}
	
	/**************  Information  ************************************
	* @Title : 게시판 분류 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/board_code/writeOk.do","/*/ncms/board_code/writeOk.do"}, method = RequestMethod.POST)
	public String writeOk(HttpServletRequest request, MultipartHttpServletRequest multi, @ModelAttribute("boardcodedto") BoardCodeDTO boardcodedto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("boardcodedto", boardcodedto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String rtn_url = "";
			//수정처리
			String ct_idx = Func.nvl( request.getParameter("ct_idx") ).trim();
			if("".equals(ct_idx)) {
				rtn_url = boardCodeService.writeOk(model);
			}else { //등록처리
				rtn_url = boardCodeService.modifyOk(model);
			}
			return rtn_url;
		}
	}
	
	/**************  Information  ************************************
	* @Title : 게시판 분류 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_code/deleteOk.do","/*/ncms/board_code/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, @ModelAttribute("boardcodedto") BoardCodeDTO boardcodedto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("boardcodedto", boardcodedto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			boardCodeService.deleteOk(model);
			String a_num = Func.nvl( request.getParameter("a_num") ).trim();
			return "redirect:write.do?a_num="+a_num;
		}
	}
	
	
	/**************  Information  ***********************************
	* @Title : 게시판 분류 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_code/levelOk.do","/*/ncms/board_code/levelOk.do"})
	public String levelOk(HttpServletRequest request, @ModelAttribute("boardcodedto") BoardCodeDTO boardcodedto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("boardcodedto", boardcodedto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			boardCodeService.levelOk(model);
			String a_num = Func.nvl( request.getParameter("a_num") ).trim();
			return "redirect:write.do?a_num="+a_num;
		}
	}
	
	/**************  Information  ***********************************
	* @Title : 게시판 분류 순서변경
	* @method : move
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_code/move.do","/*/ncms/board_code/move.do"})
	public String move(HttpServletRequest request, @ModelAttribute("boardcodedto") BoardCodeDTO boardcodedto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("boardcodedto", boardcodedto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			boardCodeService.move(model);
			String a_num = Func.nvl( request.getParameter("a_num") ).trim();
			return "redirect:write.do?a_num="+a_num;
		}
	}
	
	/**************  Information  ***********************************
	* @Title : 게시판 분류 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_code/listMove.do","/*/ncms/board_code/listMove.do"})
	public String listMove(HttpServletRequest request, @ModelAttribute("boardcodedto") BoardCodeDTO boardcodedto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("boardcodedto",boardcodedto);
		
		boardCodeService.listMove(model);
		return "/ncms/board_code/listMove";
	}
	
	/**************  Information  ***********************************
	* @Title : 게시판 분류 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_code/listMoveOk.do","/*/ncms/board_code/listMoveOk.do"})
	public String listMoveOk(HttpServletRequest request, @ModelAttribute("boardcodedto") BoardCodeDTO boardcodedto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("boardcodedto",boardcodedto);

		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			boardCodeService.listMoveOk(model);
			String a_num = Func.nvl( request.getParameter("a_num") ).trim();
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			System.out.println("builder_dir="+builder_dir);
			if(!"".equals(builder_dir)){builder_dir = "/"+builder_dir;}
			return messageService.closeRedirect(model, "", builder_dir+"/ncms/board_code/write.do?a_num="+a_num);
		}
	}
}
