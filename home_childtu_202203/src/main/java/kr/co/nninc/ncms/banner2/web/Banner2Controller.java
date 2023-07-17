package kr.co.nninc.ncms.banner2.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.banner2.service.Banner2DTO;
import kr.co.nninc.ncms.banner2.service.Banner2Service;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


/**
 * 팝업존 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.09.13
 * @version 1.0
 */

@Controller
public class Banner2Controller {
	
	@Resource(name = "banner2Service")
	private Banner2Service banner2Service;

	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ************************************
	* @Title : 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value = {"/ncms/banner2/list.do","/*/ncms/banner2/list.do"})
	public String list(HttpServletRequest request, @ModelAttribute("banner2dto") Banner2DTO banner2dto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("banner2dto", banner2dto);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}

		banner2Service.list(model);
		return "/ncms/banner2/list";
	}
	
	/**************  Information  ************************************
	* @Title : 팝업존 등록폼
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/ncms/banner2/write.do","/*/ncms/banner2/write.do"})
	public String write(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		String b_l_num = Func.nvl(request.getParameter("b_l_num")).trim();
		if("".equals(b_l_num)) {
			banner2Service.write(model);
		}else {
			banner2Service.modify(model);
		}
		return "/ncms/banner2/write";
	}
	
	/**************  Information  ************************************
	* @Title : 팝업존 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/banner2/writeOk.do","/*/ncms/banner2/writeOk.do"}, method = RequestMethod.POST)
	public String writeOk(HttpServletRequest request, MultipartHttpServletRequest multi,
			@ModelAttribute("banner2dto") Banner2DTO banner2dto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("banner2dto", banner2dto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String b_l_num = Func.nvl( request.getParameter("b_l_num") ).trim();
			String rtn_url = "";
			//수정처리
			if("".equals(b_l_num)) { //등록처리
				rtn_url = banner2Service.writeOk(model);
			}else {
				rtn_url = banner2Service.modifyOk(model);
			}
			System.out.println("b_l_num="+b_l_num);
			System.out.println("rtn_url="+rtn_url);
			return rtn_url;
		}
	}
	
	/**************  Information  ***********************************
	* @Title : 팝업존 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/banner2/levelOk.do","/*/ncms/banner2/levelOk.do"})
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			banner2Service.levelOk(model);
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/banner2/list.do";}
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ************************************
	* @Title : 팝업존 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value={"/ncms/banner2/listMove.do","/*/ncms/banner2/listMove.do"})
	public String listMove(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		banner2Service.listMove(model);
		return "/ncms/banner2/listMove";
	}
	
	/**************  Information  ************************************
	* @Title : 팝업존 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/banner2/listMoveOk.do","/*/ncms/banner2/listMoveOk.do"})
	public String listMoveOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			banner2Service.listMoveOk(model);
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
			if(!"".equals(builder_dir)){
				builder_dir = "/"+builder_dir;
			}
			return messageService.closeRedirect(model, "", builder_dir+"/ncms/banner2/list.do");
		}
	}
	
	/**************  Information  ************************************
	* @Title : 팝업존 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/banner2/deleteOk.do","/*/ncms/banner2/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
				
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			banner2Service.deleteOk(model);
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/banner2/list.do";}
			return "redirect:"+prepage;
		}
	}
}
