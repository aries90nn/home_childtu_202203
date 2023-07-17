package kr.co.nninc.ncms.banner2.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.banner2.service.Banner2DTO;
import kr.co.nninc.ncms.banner2.service.Banner2Service;

/**
 * 팝업존 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2018.03.29
 * @version 1.0
 */

@Controller
public class UserBanner2Controller {
	@Resource(name = "banner2Service")
	private Banner2Service banner2Service;
	
	/**************  Information  ************************************
	* @Title : 팝업존 등록폼
	* @method : view
	/****************************************************************/
	@RequestMapping(value = "/banner2/view.do")
	public String view(HttpServletRequest request, @ModelAttribute("banner2dto") Banner2DTO banner2dto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("banner2dto", banner2dto);
		
		banner2Service.modify(model);
		return "/ncms/banner2/view";
	}
}
