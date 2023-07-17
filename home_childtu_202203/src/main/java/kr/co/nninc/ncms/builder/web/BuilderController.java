package kr.co.nninc.ncms.builder.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.builder.service.BuilderService;
import kr.co.nninc.ncms.builder.service.BuilderSiteDTO;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;

/**
 * 배너 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2019.04.23
 * @version 1.0
 */
@Controller
public class BuilderController {
	
	/** builderService */
	@Resource(name="builderService")
	private BuilderService builderService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ***********************************
	* @Title : 목록
	* @method : list
	/************************************************************/
	@RequestMapping(value="/ncms/builder/list.do")
	public String list(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);
		
		builderService.list(model);
		
		return "/ncms/builder/list";
	}
	
	/**************  Information  ***********************************
	* @Title : 등록/수정
	* @method : write
	/************************************************************/
	@RequestMapping(value="/ncms/builder/write.do")
	public String write(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);
		
		String bs_num = Func.nvl( request.getParameter("bs_num") ).trim();
		if("".equals(bs_num)){
			builderService.write(model);
		}else{
			builderService.modify(model);
		}
		
		return "/ncms/builder/write";
	}
	
	/**************  Information  ****************************************
	* @Title : 사이트 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/builder/writeOk.do")
	public String writeOk(HttpServletRequest request, MultipartHttpServletRequest multi, @ModelAttribute("buildersitedto") BuilderSiteDTO builderdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("builderdto",builderdto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else {
			//수정처리
			String bs_num = Func.nvl( request.getParameter("bs_num") ).trim();
			if("".equals(bs_num)) {
				return builderService.writeOk(model);
			}else { //등록처리
				return builderService.modifyOk(model);
			}
		}
	}
	
	
	/**************  Information  ***********************************
	* @Title : 사이트 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value="/ncms/builder/levelOk.do")
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			String prepage = Func.nvl( request.getParameter("prepage") );
			if("".equals(prepage)){prepage = "/ncms/builder/list.do";}
			builderService.levelOk(model);
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ***********************************
	* @Title : 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value="/ncms/builder/deleteOk.do")
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/builder/list.do";}
			builderService.deleteOk(model);
			
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 사이트관리 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value="/ncms/builder/listMove.do")
	public String listMove(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		builderService.listMove(model);
		return "/ncms/builder/listMove";
	}
	
	
	/**************  Information  ****************************************
	* @Title : 사이트관리 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value="/ncms/builder/listMoveOk.do")
	public String listMoveOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else{
			builderService.listMoveOk(model);
			String url = "/ncms/builder/list.do";
			return messageService.closeRedirect(model, "", url);
		}
	}

}
