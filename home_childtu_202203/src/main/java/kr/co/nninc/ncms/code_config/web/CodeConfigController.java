package kr.co.nninc.ncms.code_config.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.code_config.service.CodeConfigDTO;
import kr.co.nninc.ncms.code_config.service.CodeConfigService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


/**
 * 코드 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.02
 * @version 1.0
 */
@Controller
public class CodeConfigController {

	@Resource(name = "codeService")
	private CodeConfigService codeService;
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	
	/**************  Information  ****************************************
	* @Title : 코드관리 목록
	* @method : write
	/****************************************************************/
	@RequestMapping(value = "/ncms/code/write.do")
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);
			
		// 코드 리스트
		codeService.write(model);
		int max_depth_option	= (Integer) request.getAttribute("max_depth_option");
		int ct_depth			= (Integer) request.getAttribute("ct_depth");
		
		//쓰기권한체크
		if(ct_depth > max_depth_option) {
			return messageService.backMsg(model, "최대 "+ max_depth_option +" 단계 까지 가능합니다.");
		}else {
			return "/ncms/code/write";
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 코드관리 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/code/writeOk.do")
	public String writeOk(HttpServletRequest request, @ModelAttribute("codeconfigdto") CodeConfigDTO codedto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("codedto",codedto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else {
			//수정처리
			String ct_idx = Func.nvl( request.getParameter("ct_idx") ).trim();
			if("".equals(ct_idx)) {
				codeService.writeOk(model);
			}else { //등록처리
				codeService.modifyOk(model);
			}
		}
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "/ncms/code/write.do";}
		return "redirect:"+prepage;
	}
	
	/**************  Information  ****************************************
	* @Title : 코드관리 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value="/ncms/code/levelOk.do")
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else{
			codeService.levelOk(model);
			
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage= "/ncms/code/write.do";}
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 코드관리 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value="/ncms/code/listMove.do")
	public String listMove(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		codeService.write(model);
		return "/ncms/code/listMove";
	}
	
	/**************  Information  ****************************************
	* @Title : 코드관리 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value="/ncms/code/listMoveOk.do")
	public String listMoveOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else{
			codeService.listMoveOk(model);
			
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/code/write.do";}
			return messageService.closeRedirect(model, "", prepage);
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 코드관리 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value="/ncms/code/deleteOk.do")
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else{
			codeService.deleteOk(model);
			
			String prepage = Func.nvl(request.getParameter("prepage")).trim();
			if("".equals(prepage)){prepage = "/ncms/code/write.do";}
			return "redirect:"+prepage;
		}
	}
	
}
