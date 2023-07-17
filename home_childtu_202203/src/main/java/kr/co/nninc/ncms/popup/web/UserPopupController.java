package kr.co.nninc.ncms.popup.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.popup.service.PopupService;

/**
 * 팝업을 보기위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.11.29
 * @version 1.0
 */
@Controller
public class UserPopupController {

	@Resource(name="popupService")
	private PopupService popupService;
	
	/**************  Information  ************************************
	* @Title : 상세보기
	* @method : view
	/***************************************************************/
	@RequestMapping(value="/popup/view.do")
	public String view(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		popupService.view(model);
		return "/ncms/popup/view";
	}
	
	/**************  Information  ************************************
	* @Title : 상세보기(레이어)
	* @method : viewLayer
	/***************************************************************/
	@RequestMapping(value="/popup/view_layer.do")
	public String view_layer(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		popupService.view(model);
		return "/ncms/popup/view_layer";
	}
}
