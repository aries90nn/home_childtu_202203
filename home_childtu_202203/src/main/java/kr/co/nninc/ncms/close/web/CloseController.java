package kr.co.nninc.ncms.close.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.close.service.CloseDTO;
import kr.co.nninc.ncms.close.service.CloseService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


@Controller
public class CloseController {

	@Resource(name="closeService")
	private CloseService closeService;
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/**************  Information  ************************************
	* @Title : 휴관일설정
	* @method : close
	/****************************************************************/
	@RequestMapping(value={"/ncms/close/close.do","/*/ncms/close/close.do"})
	public String close(HttpServletRequest request, @ModelAttribute("closedto") CloseDTO closedto, Model model)throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("closedto", closedto);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		closeService.close(model);
		
		return "/ncms/close/close";
	}
	
	/**************  Information  ************************************
	* @Title : 휴관일설정처리
	* @method : closeOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/close/closeOk.do","/*/ncms/close/closeOk.do"})
	public String closeOk(HttpServletRequest request, @ModelAttribute("closedto") CloseDTO closedto, Model model)throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("closedto", closedto);
		
		closeService.closeOk(model);
		
		String url = Func.cStr( request.getParameter("prepage") );
		return "redirect:"+url;
	}
	
}
