package kr.co.nninc.ncms.board_config.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.nninc.ncms.board_config.service.BoardConfigDTO;
import kr.co.nninc.ncms.board_config.service.BoardConfigService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


/**
 * 게시판 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.10
 * @version 1.0
 */
@Controller
public class BoardConfigController {
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** BoardService */
	@Resource(name = "boardConfigService")
	private BoardConfigService boardConfigService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;


	/**************  Information  ****************************************
	* @Title : 게시판 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value = {"/ncms/board_config/list.do", "/*/ncms/board_config/list.do"})
	public String list(HttpServletRequest request, @ModelAttribute("boarddto") BoardConfigDTO boarddto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		boardConfigService.list(model);
		
		return "/ncms/board_config/list";
	}
	
	/**************  Information  ****************************************
	* @Title : 게시판 목록
	* @method : list_a
	/****************************************************************/
	@RequestMapping(value = {"/ncms/board_config/list_a.do","/*/ncms/board_config/list_a.do"})
	public String list_a(HttpServletRequest request, @ModelAttribute("boarddto") BoardConfigDTO boarddto, Model model)
			throws Exception {
		
		list(request, boarddto, model);
		
		return "/ncms/board_config/list_a";
	}
	
	/**************  Information  ****************************************
	* @Title : 게시판 생성/수정 폼
	* @method : write
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_config/write.do","/*/ncms/board_config/write.do"})
	public String write(HttpServletRequest request, @ModelAttribute("boarddto") BoardConfigDTO boarddto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("boarddto", boarddto);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		String a_num = Func.nvl( request.getParameter("a_num") ).trim();
		if("".equals(a_num)) {
			boardConfigService.write(model);
		}else {
			boardConfigService.modify(model);
		}
		boardConfigService.pageInfo(model);
		return "/ncms/board_config/write";
	}
	
	
	/**************  Information  ************************************
	* @Title : 게시판 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/board_config/writeOk.do","/*/ncms/board_config/writeOk.do"}, method = RequestMethod.POST)
	public String writeOk(HttpServletRequest request, @ModelAttribute("boarddto") BoardConfigDTO boarddto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("boarddto", boarddto);
		
		//허용 url
		if(Func.urlChk(model,"write.do,write_popup.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String prc_type = Func.nvl(request.getParameter("prc_type"));
			String rtn_url = "";
			//수정처리
			if("edit".equals(prc_type)) {
				rtn_url = boardConfigService.modifyOk(model);
			}else { //등록처리
				rtn_url = boardConfigService.writeOk(model);
			}
			boardConfigService.pageInfo(model);
			return rtn_url;
		}
	}
	
	
	/**************  Information  ************************************
	* @Title : 게시판 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_config/deleteOk.do","/*/ncms/board_config/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, @ModelAttribute("boarddto") BoardConfigDTO boarddto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("boarddto",boarddto);
		String prepage = Func.nvl(request.getParameter("prepage"));
		if("".equals(prepage)){prepage = "/ncms/board_config/list.do";}
		
		//허용 url
		if(Func.urlChk(model,"list_a.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			boardConfigService.deleteOk(model);
			return "redirect:"+prepage;
		}
	}
	
	
	
	/**************  Information  ****************************************
	* @Title : 관리자 메인페이지 게시판리스트
	* @method : mainList
	/****************************************************************/
	@RequestMapping(value = {"/ncms/board_config/mainList.do","/*/ncms/board_config/mainList.do"})
	public String mainList(HttpServletRequest request, @ModelAttribute("boarddto") BoardConfigDTO boarddto, Model model)
			throws Exception {
		model.addAttribute("request", request);

		boardConfigService.mainList(model);

		return "/ncms/board_config/mainList";
	}
	
	
	/**************  Information  ****************************************
	* @Title : 관리자 게시판 보기
	* @method : board
	/****************************************************************/
	@RequestMapping(value = {"/ncms/board_config/board.do","/*/ncms/board_config/board.do"})
	public String board(HttpServletRequest request, @ModelAttribute("boarddto") BoardConfigDTO boarddto, Model model)
			throws Exception {
		model.addAttribute("request", request);

		return "/ncms/board_config/board";
	}
	
	
	/**************  Information  ************************************
	* @Title : 게시판 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_config/listMove.do","/*/ncms/board_config/listMove.do"})
	public String listMove(HttpServletRequest request, @ModelAttribute("boarddto") BoardConfigDTO boarddto, Model model)
			throws Exception {
		model.addAttribute("request",request);
		model.addAttribute("boarddto",boarddto);
		
		boardConfigService.listMove(model);
		return "/ncms/board_config/listMove";
	}
	
	/**************  Information  ************************************
	* @Title : 게시판 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_config/listMoveOk.do","/*/ncms/board_config/listMoveOk.do"})
	public String listMoveOk(HttpServletRequest request, @ModelAttribute("boarddto") BoardConfigDTO boarddto, Model model)
			throws Exception {
		model.addAttribute("request",request);
		model.addAttribute("boarddto",boarddto);

		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) {
			return "redirect:/error/error.do";
		}else{
			boardConfigService.listMoveOk(model);
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			if(!"".equals(builder_dir)){
				builder_dir = "/"+builder_dir;
			}
			return messageService.closeRedirect(model, "", builder_dir+"/ncms/board_config/list.do");
		}
	}
}
