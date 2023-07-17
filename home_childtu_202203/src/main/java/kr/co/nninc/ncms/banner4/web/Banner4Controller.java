package kr.co.nninc.ncms.banner4.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.banner4.service.Banner4Service;
import kr.co.nninc.ncms.banner4.service.Banner4DTO;
import kr.co.nninc.ncms.banner4.service.Banner4Service;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;

/**
 * 배너 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2019.05.16
 * @version 1.0
 */
@Controller
public class Banner4Controller {

	/** banner4Service */
	@Resource(name="banner4Service")
	private Banner4Service banner4Service;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ***********************************
	* @Title : 목록
	* @method : list
	/************************************************************/
	@RequestMapping(value={"/ncms/banner4/list.do","/*/ncms/banner4/list.do"})
	public String list(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		banner4Service.list(model);
		return "/ncms/banner4/list";
	}
	
	/**************  Information  ***********************************
	* @Title : 배너 등록폼
	* @method : write
	/****************************************************************/
	@RequestMapping(value={"/ncms/banner4/write.do","/*/ncms/banner4/write.do"})
	public String write(HttpServletRequest request, Model model) throws Exception{
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
		if("".equals(b_l_num)){
			banner4Service.write(model);
		}else{
			banner4Service.modify(model);
		}
		return "/ncms/banner4/write";
	}
	
	/**************  Information  ***********************************
	* @Title : 배너 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/banner4/writeOk.do","/*/ncms/banner4/writeOk.do"}, method = RequestMethod.POST)
	public String writeOk(HttpServletRequest request, MultipartHttpServletRequest multi,
			@ModelAttribute("banner4dto") Banner4DTO banner4dto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("banner4dto", banner4dto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String rtn_url = "";
			String b_l_num = Func.nvl( request.getParameter("b_l_num") ).trim();
			//수정처리
			if("".equals(b_l_num)){
				rtn_url = banner4Service.writeOk(model);
			}else{
				rtn_url = banner4Service.modifyOk(model);
			}
			return rtn_url;
		}
	}
	
	/**************  Information  ***********************************
	* @Title : 배너 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/banner4/levelOk.do","/*/ncms/banner4/levelOk.do"})
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			if(!"".equals(builder_dir)){builder_dir = "/"+builder_dir;}
			String prepage = Func.nvl( request.getParameter("prepage") );
			if("".equals(prepage)){prepage = builder_dir+"/ncms/banner4/list.do";}
			banner4Service.levelOk(model);
			return "redirect:"+prepage;
		}
	}
	
	
	/**************  Information  ***********************************
	* @Title : 배너 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value={"/ncms/banner4/listMove.do","/*/ncms/banner4/listMove.do"})
	public String listMove(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);

		banner4Service.listMove(model);
		return "/ncms/banner4/listMove";
	}
	
	/**************  Information  ***********************************
	* @Title : 배너 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/banner4/listMoveOk.do","/*/ncms/banner4/listMoveOk.do"})
	public String listMoveOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);

		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			banner4Service.listMoveOk(model);
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
			if(!"".equals(builder_dir)){
				builder_dir = "/"+builder_dir;
			}
			return messageService.closeRedirect(model, "", builder_dir+"/ncms/banner4/list.do");
		}
	}
	
	/**************  Information  ***********************************
	* @Title : 배너 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/banner4/deleteOk.do","/*/ncms/banner4/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/banner4/list.do";}
			banner4Service.deleteOk(model);
			
			return "redirect:"+prepage;
		}
	}
}
