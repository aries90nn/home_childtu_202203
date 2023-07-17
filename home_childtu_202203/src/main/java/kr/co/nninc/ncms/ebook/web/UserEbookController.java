package kr.co.nninc.ncms.ebook.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.ebook.service.EbookDTO;
import kr.co.nninc.ncms.ebook.service.UserEbookService;

/**
 * 이북 관리를 위한 비즈니스 구현 클래스(사용자용)
 * 
 * @author 나눔
 * @since 2017.11.03
 * @version 1.0
 */
@Controller
public class UserEbookController {

	/** UserEbookService */
	@Resource(name = "userEbookService")
	private UserEbookService userEbookService;
	
	/**************  Information  ****************************************
	* @Title : 이북컨텐츠
	* @method : index
	/****************************************************************/
	@RequestMapping(value = "/ebook/index.do")
	public String index(HttpServletRequest request, @ModelAttribute("ebookdto") EbookDTO ebookdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("ebookdto", ebookdto);

		// 이북리스트
		String rtn_url = userEbookService.index(model);
		return rtn_url;
	}
	
	/**************  Information  ****************************************
	* @Title : 이북리스트
	* @method : list
	/****************************************************************/
	@RequestMapping(value = "/ebook/list.do")
	public String list(HttpServletRequest request, @ModelAttribute("ebookdto") EbookDTO ebookdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("ebookdto", ebookdto);
		
		// 이북리스트
		userEbookService.list(model);
		return "/site/ebook/listall/list";
	}
}
