package kr.co.nninc.ncms.ebookpdf.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.ebookpdf.service.EbookpdfDTO;
import kr.co.nninc.ncms.ebookpdf.service.EbookpdfService;
import kr.co.nninc.ncms.ebookpdf.service.UserEbookpdfService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;

@Controller
public class EbookPdfController {
	
	// ebookpdf 관리자 서비스
	@Resource(name = "ebookpdfService")
	private EbookpdfService ebookpdfService;
	
	// 메뉴 서비스
	@Resource(name = "menuService")
	private MenuService menuService;
	
	// 메시지 출력 서비스
	@Resource(name = "messageService")
	private MessageService messageService;
	/**************  Information  ****************************************
	* @Title : 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value = {"/ncms/ebookpdf/list.do", "/*/ncms/ebookpdf/list.do"})
	public String list(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		// 이북리스트
		ebookpdfService.list(model);
		return "/ncms/ebookpdf/list";
	}
	
	/**************  Information  ****************************************
	* @Title : 등록
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/ncms/ebookpdf/write.do", "/*/ncms/ebookpdf/write.do"})
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		String eb_idx = Func.nvl( request.getParameter("eb_idx") ).trim();
		
		if("".equals(eb_idx)) { //등록폼
			ebookpdfService.write(model);
		}else { // 수정폼
			ebookpdfService.modify(model);
		}
		
		return "/ncms/ebookpdf/write";
	}
	
	/**************  Information  ****************************************
	* @Title : 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/ebookpdf/writeOk.do","/*/ncms/ebookpdf/writeOk.do"})
	public String writeOk(HttpServletRequest request, MultipartHttpServletRequest multi,
			@ModelAttribute("ebookpdfdto") EbookpdfDTO ebookpdfdto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("ebookpdfdto", ebookpdfdto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String eb_idx = Func.nvl( request.getParameter("eb_idx") ).trim();
			
			if("".equals(eb_idx)) { // 등록처리
				return ebookpdfService.writeOk(model);
			}else { //수정처리
				return ebookpdfService.modifyOk(model);
			}
		}
		
	}
	
	/**************  Information  ****************************************
	* @Title : 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/ebookpdf/deleteOk.do","/*/ncms/ebookpdf/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			ebookpdfService.deleteOk(model);
			
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "list.do";}
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/ebookpdf/levelOk.do","/*/ncms/ebookpdf/levelOk.do"})
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			ebookpdfService.levelOk(model);
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "list.do";}
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 순서일괄수정
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value={"/ncms/ebookpdf/listMove.do", "/*/ncms/ebookpdf/listMove.do"})
	public String listMove(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		ebookpdfService.listMove(model);
		return "/ncms/ebookpdf/listMove";
	}
	
	/**************  Information  ****************************************
	* @Title : 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/ebookpdf/listMoveOk.do", "/*/ncms/ebookpdf/listMoveOk.do"})
	public String listMoveOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			ebookpdfService.listMoveOk(model);
		
			return messageService.closeRedirect(model,"","");
		}
	}
	
	/**************  Information  ************************************
	* @Title : 첨부파일 파일다운로드
	* @method : down
	/****************************************************************/
	@RequestMapping(value={"/ncms/ebookpdf/down.do","/*/ncms/ebookpdf/down.do","/*/ebookpdf/down.do"})
	public String down(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		String rtn_url = ebookpdfService.down(model);
		return rtn_url;
	}
	
}
