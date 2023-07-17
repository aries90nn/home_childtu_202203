package kr.co.nninc.ncms.popup.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.popup.service.PopupDTO;
import kr.co.nninc.ncms.popup.service.PopupService;

/**
 * 팝업 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2019.04.02
 * @version 1.1
 */
@Controller
public class PopupController {

	@Resource(name="popupService")
	private PopupService popupService;
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/**************  Information  ************************************
	* @Title : 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value={"/ncms/popup/list.do","/*/ncms/popup/list.do"})
	public String list(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		popupService.list(model);
		//System.out.println("list= "+Func.getNowPage(request));
		return "/ncms/popup/list";
	}
	
	/**************  Information  ************************************
	* @Title : 팝업 등록폼
	* @method : write
	/****************************************************************/
	@RequestMapping(value={"/ncms/popup/write.do","/*/ncms/popup/write.do"})
	public String write(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		//수정처리
		String idx = Func.nvl(request.getParameter("idx")).trim();
		if("".equals(idx)) {
			popupService.write(model);
		}else { //등록처리
			popupService.modify(model);
		}
		return "/ncms/popup/write";
	}
	
	/**************  Information  ************************************
	* @Title : 팝업 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/popup/writeOk.do","/*/ncms/popup/writeOk.do"})
	public String writeOk(HttpServletRequest request, @ModelAttribute("popupdto") PopupDTO popupdto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("popupdto", popupdto);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String rtn_url = "";
			//수정처리
			String idx = Func.nvl(request.getParameter("idx")).trim();
			if("".equals(idx)) {
				rtn_url = popupService.writeOk(model);
			}else { //등록처리
				rtn_url = popupService.modifyOk(model);
			}
			return rtn_url;
		}
	}
	
	
	/**************  Information  ************************************
	* @Title : 팝업 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/popup/deleteOk.do","/*/ncms/popup/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			String prepage = Func.nvl(request.getParameter("prepage")).trim();
			if("".equals(prepage)){prepage = "/ncms/popup/list.do";}
			popupService.deleteOk(model);
			return "redirect:"+prepage;
		}
	}
	
	
	/**************  Information  ***********************************
	* @Title : 팝업 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/popup/levelOk.do","/*/ncms/popup/levelOk.do"})
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		

		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			String prepage = Func.nvl(request.getParameter("prepage")).trim();
			if("".equals(prepage)){prepage = "/ncms/popup/list.do";}
			popupService.levelOk(model);
			return "redirect:"+prepage;
		}
	}
	
}
