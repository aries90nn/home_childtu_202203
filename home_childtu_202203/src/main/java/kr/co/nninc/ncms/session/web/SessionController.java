package kr.co.nninc.ncms.session.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.session.service.SessionService;

/**
 * 세션 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.18
 * @version 1.0
 */
@Controller
public class SessionController {

	/** SessionService */
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	
	/**************  Information  ****************************************
	* @Title : 세션관리
	* @method : write
	/****************************************************************/
	@RequestMapping(value = "/ncms/session/write.do")
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("security_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);

		sessionService.write(model);

		return "/ncms/session/write";
	}
	
	
	/**************  Information  ****************************************
	* @Title : 키워드 관리 등록 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/session/writeOk.do")
	public String writeOk(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);

		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else {
			sessionService.writeOk(model);
			return "redirect:/ncms/session/write.do";
		}
	}
}
