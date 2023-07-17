package kr.co.nninc.ncms.ebook_page.web;

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
import kr.co.nninc.ncms.ebook_page.service.EbookPageDTO;
import kr.co.nninc.ncms.ebook_page.service.EbookPageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


/**
 * 이북페이지 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.09.27
 * @version 1.0
 */

@Controller
public class EbookPageController {

	@Resource(name = "ebookPageService")
	private EbookPageService ebookPageService;
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	
	/**************  Information  ****************************************
	* @Title : 이북 페이지 등록폼 
	* @method : write
	/****************************************************************/
	@RequestMapping(value = "/ncms/ebook_page/write.do")
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);

		ebookPageService.write(model);

		return "/ncms/ebook_page/write";
	}
	
	/**************  Information  ****************************************
	* @Title : 이북 페이지 등록처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/ebook_page/writeOk.do")
	public String writeOk(HttpServletRequest request, @ModelAttribute("ebookpagedto") EbookPageDTO ebookpagedto, MultipartHttpServletRequest multi, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("ebookpagedto", ebookpagedto);

		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			//등록처리
			String rtn_url = ebookPageService.writeOk(model);
			return rtn_url;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 이북 첨부파일
	* @method : nfu_upload
	/****************************************************************/
	@RequestMapping(value = "/ncms/ebook_page/nfu_upload.do")
	public String nfu_upload(HttpServletRequest request, MultipartHttpServletRequest multi, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		
		ebookPageService.nfuUpload(model);
		return "/ncms/ebook_page/nfu_upload";
	}
	
	/**************  Information  ****************************************
	* @Title : 이북 일반 첨부파일
	* @method : nfu_normal_upload
	/****************************************************************/
	@RequestMapping(value = "/ncms/ebook_page/nfu_normal_upload.do")
	public String nfu_normal_upload(HttpServletRequest request, @ModelAttribute("ebookpagedto") EbookPageDTO ebookpagedto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("ebookpagedto", ebookpagedto);
		
		return "/ncms/ebook_page/nfu_normal_upload";
	}
	
	/**************  Information  ****************************************
	* @Title : 이북 페이지 수정처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/ebook_page/modifyOk.do")
	public String modifyOk(HttpServletRequest request, @ModelAttribute("ebookpagedto") EbookPageDTO ebookpagedto, MultipartHttpServletRequest multi, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("ebookpagedto", ebookpagedto);

		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			//수정처리
			String rtn_url = ebookPageService.modifyOk(model);
			return rtn_url;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 이북페이지 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value="/ncms/ebook_page/listMove.do")
	public String listMove(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		ebookPageService.listMove(model);
		return "/ncms/ebook_page/listMove";
	}
	
	/**************  Information  ****************************************
	* @Title : 이북페이지 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value="/ncms/ebook_page/listMoveOk.do")
	public String listMoveOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "/ncms/ebook/list.do";}
		String eb_pk = Func.nvl( request.getParameter("eb_pk") );
		
		ebookPageService.listMoveOk(model);
		return messageService.closeRedirect(model, "", prepage);
	}
	
	/**************  Information  ****************************************
	* @Title : 이북페이지 이미지 삭제
	* @method : delete2Ok
	/****************************************************************/
	@RequestMapping(value = "/ncms/ebook_page/delete2Ok.do")
	public String delete2Ok(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		String prepage = Func.nvl( request.getParameter("prepage") );
		if("".equals(prepage)){prepage = "/ncms/ebook/list.do";}
		String eb_pk = Func.nvl( request.getParameter("eb_pk") );

		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			//수정처리
			ebookPageService.delete2Ok(model);
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 이북페이지 삭제
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/ebook_page/deleteOk.do")
	public String deleteOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);

		String prepage = Func.nvl( request.getParameter("prepage") );
		if("".equals(prepage)){prepage = "/ncms/ebook/list.do";}
		String eb_pk = Func.nvl( request.getParameter("eb_pk") );
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			//수정처리
			ebookPageService.deleteOk(model);
			return "redirect:"+prepage;
		}
	}
	
}
