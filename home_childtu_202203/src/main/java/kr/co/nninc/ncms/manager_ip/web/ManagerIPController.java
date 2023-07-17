package kr.co.nninc.ncms.manager_ip.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_ip.service.ManagerIPDTO;
import kr.co.nninc.ncms.manager_ip.service.ManagerIPService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


/**
 * IP관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.17
 * @version 1.0
 */
@Controller
public class ManagerIPController {

	/** IpService */
	@Resource(name = "managerIPService")
	private ManagerIPService ipService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ****************************************
	* @Title : IP 리스트
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/ncms/ip/write.do","/*/ncms/ip/write.do"})
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("security_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}

		ipService.write(model);
		return "/ncms/ip/write";
	}
	
	/**************  Information  ****************************************
	* @Title : IP 등록/수정처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/ip/writeOk.do","/*/ncms/ip/writeOk.do"})
	public String writeOk(HttpServletRequest request, @ModelAttribute("manageripdto") ManagerIPDTO ipdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("ipdto", ipdto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else {
			String mi_num = Func.nvl( request.getParameter("mi_num") ).trim();

			if("".equals(mi_num)) { //등록처리
				ipService.writeOk(model);
			}else { //수정처리
				ipService.modifyOk(model);
			}
		}
		return "redirect:write.do";
	}
	
	
	/**************  Information  ************************************
	* @Title : IP 사용여부
	* @method : levelOK
	/****************************************************************/
	@RequestMapping(value={"/ncms/ip/levelOK.do","/*/ncms/ip/levelOK.do"})
	public String levelOK(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			ipService.levelOk(model);
			return "redirect:write.do";
		}
	}
	
	
	/**************  Information  ************************************
	* @Title : IP 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/ip/deleteOk.do","/*/ncms/ip/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			ipService.deleteOk(model);
			return "redirect:write.do";
		}
	}
}
