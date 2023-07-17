package kr.co.nninc.ncms.ebook.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.ebook.service.EbookDTO;
import kr.co.nninc.ncms.ebook.service.EbookService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


/**
 * 이북 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.09.26
 * @version 1.0
 */
@Controller
public class EbookController {

	@Resource(name = "ebookService")
	private EbookService ebookService;
	
	/** 관리자 메뉴 서비스 */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ****************************************
	* @Title : 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value = "/ncms/ebook/list.do")
	public String list(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);
		
		// 이북리스트
		ebookService.list(model);
		return "/ncms/ebook/list";
	}
	
	
	/**************  Information  ****************************************
	* @Title : 이북 등록폼
	* @method : write
	/****************************************************************/
	@RequestMapping(value = "/ncms/ebook/write.do")
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);

		//수정폼
		String eb_idx = Func.nvl( request.getParameter("eb_idx") ).trim();
		if("".equals(eb_idx)) { //등록폼
			ebookService.write(model);
		}else {
			ebookService.modify(model);
		}
		return "/ncms/ebook/write";
	}
	
	
	/**************  Information  ****************************************
	* @Title : 이북 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/ebook/writeOk.do")
	public String writeOk(HttpServletRequest request, MultipartHttpServletRequest multi,
			@ModelAttribute("ebookdto") EbookDTO ebookdto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("ebookdto", ebookdto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			
			String eb_idx = Func.nvl( request.getParameter("eb_idx") ).trim();
			//수정처리
			if("".equals(eb_idx)) {
				ebookService.writeOk(model);
			}else { //등록처리
				ebookService.modifyOk(model);
			}
		}
		return "redirect:/ncms/ebook/list.do";
	}
	
	
	/**************  Information  ****************************************
	* @Title : 이북 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value="/ncms/ebook/levelOk.do")
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			ebookService.levelOk(model);
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/ebook/list.do";}
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 이북 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value="/ncms/ebook/listMove.do")
	public String listMove(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		ebookService.listMove(model);
		return "/ncms/ebook/listMove";
	}
	
	/**************  Information  ****************************************
	* @Title : 이북 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value="/ncms/ebook/listMoveOk.do")
	public String listMoveOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		ebookService.listMoveOk(model);
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "/ncms/ebook/list.do";}
		return messageService.closeRedirect(model, "", prepage);
	}
	
	/**************  Information  ****************************************
	* @Title : 이북 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value="/ncms/ebook/deleteOk.do")
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			
			ebookService.deleteOk(model);
			
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/ebook/list.do";}
			return "redirect:"+prepage;
		}
	}
	
}
