package kr.co.nninc.ncms.edusat_blacklist.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.edusat_blacklist.service.EdusatBlackListDTO;
import kr.co.nninc.ncms.edusat_blacklist.service.EdusatBlackListService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


@Controller
public class EdusatBlackListController {
	
	@Resource(name="edusatBlackListService")
	private EdusatBlackListService edusatBlackListService;
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;

	
	/**************  Information  ************************************
	* @Title : 블랙리스트
	* @method : list
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat_blacklist/list.do","/*/ncms/edusat_blacklist/list.do"})
	public String list(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		edusatBlackListService.list(model);
		return "/ncms/edusat_blacklist/list";
	}
	
	/**************  Information  ************************************
	* @Title : 블랙리스트 창
	* @method : writePop
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat_blacklist/writePop.do","/*/ncms/edusat_blacklist/writePop.do"})
	public String writePop(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		String url = edusatBlackListService.write(model);
		return url;
	}
	
	/**************  Information  ************************************
	* @Title : 블랙리스트 등록
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat_blacklist/writeOk.do","/*/ncms/edusat_blacklist/writeOk.do"})
	public String writeOk(HttpServletRequest request, @ModelAttribute("edusatblacklistdto") EdusatBlackListDTO edusatblacklistdto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("edusatblacklistdto", edusatblacklistdto);
		
		String eb_num = Func.nvl( request.getParameter("eb_num") ).trim();
		String rtn_url = "";
		//수정처리
		if(!"".equals(eb_num)) {
			rtn_url = edusatBlackListService.modifyOk(model);
		}else { //등록처리
			rtn_url = edusatBlackListService.writeOk(model);
		}
		return rtn_url;
	}
	
	/**************  Information  ************************************
	* @Title : 블랙리스트 삭제
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat_blacklist/deleteOk.do","/*/ncms/edusat_blacklist/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
				
		
		//수정처리
		edusatBlackListService.deleteOk(model);
		String url = Func.cStr( request.getParameter("prepage") );
		if("".equals(url)){
			url = "/ncms/edu_blacklist/list.do";
		}
		return "redirect:"+url;
	}
}
