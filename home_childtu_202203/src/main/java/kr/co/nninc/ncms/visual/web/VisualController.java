package kr.co.nninc.ncms.visual.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.visual.service.VisualDTO;
import kr.co.nninc.ncms.visual.service.VisualService;

/**
 * 비쥬얼 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2018.05.28
 * @version 1.0
 */
@Controller
public class VisualController {

	/** VisualService */
	@Resource(name="visualService")
	private VisualService visualService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ***********************************
	* @Title : 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value="/ncms/visual/list.do")
	public String list(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);

		visualService.list(model);
		
		return "/ncms/visual/list";
	}
	
	/**************  Information  ***********************************
	* @Title : 비쥬얼 등록폼
	* @method : write
	/****************************************************************/
	@RequestMapping(value="/ncms/visual/write.do")
	public String write(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);
		
		String v_num = Func.nvl( request.getParameter("v_num") ).trim();
		if("".equals(v_num)) {
			visualService.write(model);
		}else {
			visualService.modify(model);
		}

		return "/ncms/visual/write";
	}
	
	/**************  Information  ***********************************
	* @Title : 비쥬얼 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value="/ncms/visual/writeOk.do", method = RequestMethod.POST)
	public String writeOk(HttpServletRequest request, MultipartHttpServletRequest multi, @ModelAttribute("visualdto") VisualDTO visualdto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("visualdto", visualdto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String v_num = Func.nvl( request.getParameter("v_num") ).trim();
			String rtn_url = "";
			//수정처리
			if("".equals(v_num)) {
				rtn_url = visualService.writeOk(model);
			}else { //등록처리
				rtn_url = visualService.modifyOk(model);
			}
			return rtn_url;
		}
	}
	
	/**************  Information  ***********************************
	* @Title : 비쥬얼 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value="/ncms/visual/deleteOk.do")
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			visualService.deleteOk(model);
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/visual/list.do";}
			return "redirect:"+prepage;
		}
	}
	
	
	/**************  Information  ***********************************
	* @Title : 비쥬얼 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value="/ncms/visual/listMove.do")
	public String listMove(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);

		visualService.listMove(model);
		return "/ncms/visual/listMove";
	}
	
	/**************  Information  ***********************************
	* @Title : 비쥬얼 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value="/ncms/visual/listMoveOk.do")
	public String listMoveOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);

		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			visualService.listMoveOk(model);
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/visual/list.do";}
			return messageService.closeRedirect(model, "", prepage);
		}
	}
	
	
	/**************  Information  ***********************************
	* @Title : 비쥬얼 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value="/ncms/visual/levelOk.do")
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			visualService.levelOk(model);
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/visual/list.do";}
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ***********************************
	* @Title : 비쥬얼 파일 다운로드
	* @method : down
	/****************************************************************/
	@RequestMapping(value="/ncms/visual/down.do")
	public String down(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		visualService.modify(model);
		return "/ncms/visual/down";
	}
	
}
