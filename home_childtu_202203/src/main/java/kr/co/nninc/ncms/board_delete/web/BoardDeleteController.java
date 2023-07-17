package kr.co.nninc.ncms.board_delete.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.board_delete.service.BoardDeleteService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


/**
 * 삭제게시물을 관리하기 위한 비즈니스 구현 클래스
 * @author 나눔
 * @since 2017.11.23
 * @version 1.0
 */
@Controller
public class BoardDeleteController {

	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** BoardDeleteService */
	@Resource(name="boardDeleteService")
	private BoardDeleteService boardDeleteService;
	
	/**************  Information  ************************************
	* @Title : 삭제게시물 목록관리
	* @method : list
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_delete/list.do","/*/ncms/board_delete/list.do"})
	public String list(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		if(!"".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}

		boardDeleteService.list(model);
		boardDeleteService.pageInfo(model);
		return "/ncms/board_delete/list";
	}
	
	/**************  Information  ************************************
	* @Title : 삭제게시물 완전삭제
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_delete/deleteOk.do","/*/ncms/board_delete/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String rtn_url = boardDeleteService.deleteOk(model);
			return rtn_url;
		}
	}
	

	/**************  Information  ************************************
	* @Title : 삭제게시물 복원
	* @method : restoreOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/board_delete/restoreOk.do","/*/ncms/board_delete/restoreOk.do"})
	public String restoreOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String rtn_url = boardDeleteService.restoreOk(model);
			return rtn_url;
		}
	}
}
