package kr.co.nninc.ncms.cms.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.cms.service.CmsCssHistoryDTO;
import kr.co.nninc.ncms.cms.service.CmsDTO;
import kr.co.nninc.ncms.cms.service.CmsService;
import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;

/**
 * CMS를 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2019.02.15
 * @version 1.1
 */
@Controller
public class CmsController {

	@Resource(name = "cmsService")
	private CmsService cmsService;
	
	/** UserCmsService */
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	
	/**************  Information  ****************************************
	* @Title : CMS관리 목록
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/write.do", "/*/ncms/cms/write.do"})
	public String write(HttpServletRequest request, @ModelAttribute("cmsdto") CmsDTO cmsdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("cmsdto", cmsdto);
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
		}
		// 레프트 메뉴
		cmsService.mngLeft(model);
		
		// 코드 리스트
		cmsService.write(model);
		int max_depth_option	= (Integer) request.getAttribute("max_depth_option");
		int ct_depth					= (Integer) request.getAttribute("ct_depth");
		
		//쓰기권한체크
		if(ct_depth > max_depth_option) {
			return messageService.backMsg(model, "최대 "+ max_depth_option +" 단계 까지 가능합니다.");
		}else {
			return "/ncms/cms/write";
		}
	}
	
	
	/**************  Information  ****************************************
	* @Title : CMS관리 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/writeOk.do","/*/ncms/cms/writeOk.do"})
	public String writeOk(HttpServletRequest request, @ModelAttribute("cmsdto") CmsDTO cmsdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("cmsdto", cmsdto);
		
		String prepage = Func.nvl( request.getParameter("prepage") );
		if("".equals(prepage)){prepage = "/ncms/cms/write.do";}
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else {
			//수정처리
			String ct_idx = Func.nvl( request.getParameter("ct_idx") );
			if("".equals(ct_idx)) {
				cmsService.writeOk(model);
			}else { //등록처리
				cmsService.modifyOk(model);
			}
		}
		return "redirect:"+prepage;
	}
	
	
	/**************  Information  ****************************************
	* @Title : CMS관리 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value={"/ncms/cms/listMove.do", "/*/ncms/cms/listMove.do"})
	public String listMove(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		cmsService.listMove(model);
		return "/ncms/cms/listMove";
	}
	
	
	/**************  Information  ****************************************
	* @Title : CMS관리 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/cms/listMoveOk.do","/*/ncms/cms/listMoveOk.do"})
	public String listMoveOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else{
			cmsService.listMoveOk(model);
			String url = "/ncms/cms/write.do?ct_idx="+request.getAttribute("ct_ref");
			String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
			if(!"".equals(builder_dir)){url = "/"+builder_dir+url;}
			System.out.println("listMoveOk="+builder_dir+","+url);
			return messageService.closeRedirect(model, "", url);
		}
	}
	
	
	/**************  Information  ****************************************
	* @Title : CMS관리 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/cms/levelOk.do","/*/ncms/cms/levelOk.do"})
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		String prepage = Func.nvl( request.getParameter("prepage") );
		if("".equals(prepage)){prepage = "/ncms/cms/write.do";}
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else{
			cmsService.levelOk(model);
			return "redirect:"+prepage;
		}
	}
	
	
	
	/**************  Information  ****************************************
	* @Title : CMS관리 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/cms/deleteOk.do","/*/ncms/cms/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		String prepage = Func.nvl( request.getParameter("prepage") );
		if("".equals(prepage)){prepage = "/ncms/cms/write.do";}
		
		//허용 url
		if(Func.urlChk(model,"write.do,contents.do,img.do,css.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else{
			cmsService.deleteOk(model);
			return "redirect:"+prepage;
		}
	}
	
	
	/**************  Information  ****************************************
	* @Title : CMS관리 내용관리
	* @method : contents
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/contents.do","/*/ncms/cms/contents.do"})
	public String contents(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
		}
		// 레프트 메뉴
		cmsService.mngLeft(model);
		
		String ct_idx = Func.nvl( request.getParameter("ct_idx") );
		
		if("".equals(ct_idx)) { 
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}else {
			cmsService.contents(model);
			return "/ncms/cms/contents";
		}
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 내용등록처리
	* @method : contentsOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/contentsOk.do","/*/ncms/cms/contentsOk.do"}, method = RequestMethod.POST)
	public String contentsOk(HttpServletRequest request, MultipartHttpServletRequest multi, @ModelAttribute("cmsdto") CmsDTO cmsdto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("cmsdto", cmsdto);
		
		//허용 url
		if(Func.urlChk(model,"contents.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else {
			String rtn_url = cmsService.contentsOk(model);
			return rtn_url;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 미리보기
	* @method : preview
	/****************************************************************/
	@RequestMapping(value = {"/cms/preview.do","/*/cms/preview.do"})
	public String preview(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단
		userCmsService.foot(model, request);
		
		cmsService.preview(model);
		return "/ncms/cms/preview";
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 작업내역보기
	* @method : history
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/history.do","/*/ncms/cms/history.do"})
	public String history(HttpServletRequest request, @ModelAttribute("cmsdto") CmsDTO cmsdto,
			Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("cmsdto", cmsdto);
		
		cmsService.history(model);
		return "/ncms/cms/history";
	}
	
	
	/**************  Information  ****************************************
	* @Title : CMS관리 작업내역보기
	* @method : history
	/****************************************************************/
	@RequestMapping(value = {"/cms/history_intro.do","/*/cms/history_intro.do"})
	public String history_intro(HttpServletRequest request, @ModelAttribute("cmsdto") CmsDTO cmsdto,
			Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("cmsdto", cmsdto);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단
		userCmsService.foot(model, request);
		
		cmsService.history(model);
		return "/ncms/cms/history_intro";
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 작업내역 적용하기
	* @method : historyOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/historyOk.do","/*/ncms/cms/historyOk.do"})
	public String historyOk(HttpServletRequest request, @ModelAttribute("cmsdto") CmsDTO cmsdto,
			Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("cmsdto", cmsdto);
		
		String target_type = request.getParameter("target_type");
		
		cmsService.historyOk(model);
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("pop".equals(target_type)) {
			return messageService.closeRedirect(model, "적용되었습니다.", "/"+builder_dir+"/ncms/cms/contents.do?ct_idx="+request.getAttribute("ct_idx"));
		}else {
			
			return "redirect:/"+builder_dir+"/ncms/cms/contents.do?ct_idx="+request.getAttribute("ct_idx");
		}
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 이미지폴더
	* @method : img
	/****************************************************************/
	@RequestMapping(value={"/ncms/cms/img.do","/*/ncms/cms/img.do"})
	public String img(HttpServletRequest request, @ModelAttribute("cmsdto") CmsDTO cmsdto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("cmsdto",cmsdto);
		
		String builder_dir = Func.cStr( request.getAttribute("BUILDER_DIR") );
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
		}
		// 레프트 메뉴
		cmsService.mngLeft(model);
		
		cmsService.img(model);
		return "/ncms/cms/img";
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 이미지폴더
	* @method : imgOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/cms/imgOk.do","/*/ncms/cms/imgOk.do"}, method = RequestMethod.POST)
	public String imgOk(HttpServletRequest request, MultipartHttpServletRequest multi, @ModelAttribute("cmsdto") CmsDTO cmsdto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("multi", multi);
		model.addAttribute("cmsdto",cmsdto);
		
		//허용 url
		if(Func.urlChk(model,"img.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else{
			cmsService.imgOk(model);
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){
				String builder_dir = (String)request.getAttribute("BUILDER_DIR");
				prepage = "/"+builder_dir+"/ncms/cms/img.do?ct_idx="+request.getAttribute("ct_idx");
			}
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/cms/imgDeleteOk.do","/*/ncms/cms/imgDeleteOk.do"})
	public String imgDeleteOk(HttpServletRequest request, @ModelAttribute("cmsdto") CmsDTO cmsdto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("cmsdto", cmsdto);
		
		//허용 url
		if(Func.urlChk(model,"img.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else{
			cmsService.imgDeleteOk(model);
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){
				String builder_dir = (String)request.getAttribute("BUILDER_DIR");
				prepage = "/"+builder_dir+"/ncms/cms/img.do?ct_idx="+request.getAttribute("ct_idx");
			}
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 > CSS관리
	* @method : CSS
	/****************************************************************/
	@RequestMapping(value={"/ncms/cms/css.do","/*/ncms/cms/css.do"})
	public String css(HttpServletRequest request, @ModelAttribute("cmscsshistorydto") CmsCssHistoryDTO cmscsshistorydto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("cmscsshistorydto",cmscsshistorydto);
		
		String builder_dir = Func.cStr( request.getAttribute("BUILDER_DIR") );
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
		}
		
		// 레프트 메뉴
		cmsService.mngLeft(model);
		
		//css관리
		cmsService.css(model);
		return "/ncms/cms/css";
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 > CSS 등록/수정
	* @method : cssOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/cms/cssOk.do","/*/ncms/cms/cssOk.do"})
	public String cssOk(HttpServletRequest request, @ModelAttribute("cmscsshistorydto") CmsCssHistoryDTO cmscsshistorydto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("cmscsshistorydto",cmscsshistorydto);
		
		//허용 url
		if(Func.urlChk(model,"css.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else{
			cmsService.cssOk(model);
			String prepage = Func.nvl( request.getParameter("prepage")).trim();
			if("".equals(prepage)){prepage="/ncms/cms/css.do?ct_idx="+request.getAttribute("ct_idx");}
			return "redirect:"+prepage;
		}
	}
	
	
	/**************  Information  ****************************************
	* @Title : CMS관리 이미지 첨부파일
	* @method : nfu_upload
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/nfu_upload.do","/*/ncms/cms/nfu_upload.do"})
	public String nfu_upload(HttpServletRequest request, @ModelAttribute("cmsdto") CmsDTO cmsdto, MultipartHttpServletRequest multi, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("cmsdto",cmsdto);
		
		cmsService.nfuUpload(model);
		return "/ncms/cms/nfu_upload";
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 이미지 일반 첨부파일
	* @method : nfu_normal_upload
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/nfu_normal_upload.do","/*/ncms/cms/nfu_normal_upload.do"})
	public String nfu_normal_upload(HttpServletRequest request, @ModelAttribute("cmsdto") CmsDTO cmsdto, Model model)
			throws Exception {
		//업로드 처리 폴더명
		String ct_menu_folder = Func.nvl(request.getParameter("ct_menu_folder"));
		request.setAttribute("ct_menu_folder", ct_menu_folder);
		
		return "/ncms/cms/nfu_normal_upload";
	}
	
	/**************  Information  ****************************************
	* @Title : CMS관리 이미지 일반 첨부파일 처리
	* @method : nfu_normal_upload_ok
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/nfu_normal_upload_ok.do","/*/ncms/cms/nfu_normal_upload_ok.do"}, method = RequestMethod.POST)
	public String nfu_normal_upload_ok(HttpServletRequest request, @ModelAttribute("cmsdto") CmsDTO cmsdto, MultipartHttpServletRequest multi, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("cmsdto",cmsdto);

		cmsService.nfuNormalUpload(model);
		return "/ncms/cms/nfu_normal_upload";
	}
	
	
	/**************  Information  ****************************************
	* @Title : 드래그 메뉴이동
	* @method : dragMoveOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/dragMoveOk.do","/*/ncms/cms/dragMoveOk.do"})
	@ResponseBody
	public String dragMoveOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		return cmsService.dragMoveOk(model);
	}
	
	
	/**************  Information  ****************************************
	* @Title : 동기화
	* @method : syncOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/cms/syncOk.do","/*/ncms/cms/syncOk.do"})
	public String syncOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		String prepage = Func.nvl(request.getParameter("prepage")).trim();
		if("".equals(prepage)){
			prepage = "/ncms/cms/write.do";
			String BUILDER_DIR = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
			if(!"".equals(BUILDER_DIR)){
				prepage = "/"+BUILDER_DIR+prepage;
			}
		}
		cmsService.createMenuJson(request);
		
		return "redirect:"+prepage;
	}
	
}
