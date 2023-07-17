package kr.co.nninc.ncms.poll.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.poll.service.PollDTO;
import kr.co.nninc.ncms.poll.service.PollService;

/**
 * 설문조사 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.11.01
 * @version 1.0
 */

@Controller
public class PollController {

	/** PollService */
	@Resource(name = "pollService")
	private PollService pollService;
	
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
	@RequestMapping(value = {"/ncms/poll/list.do","/*/ncms/poll/list.do"})
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
		
		// 설문조사리스트
		pollService.list(model);

		return "/ncms/poll/list";
	}
	
	/**************  Information  ****************************************
	* @Title : 설문조사 등록폼
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/ncms/poll/write.do","/*/ncms/poll/write.do"})
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

		//수정폼
		String po_idx = Func.nvl(request.getParameter("po_idx")).trim();
		if("".equals(po_idx)){
			pollService.write(model);
		}else{
			pollService.modify(model);
		}
		return "/ncms/poll/write";
	}
	
	/**************  Information  ****************************************
	* @Title : 설문조사 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/poll/writeOk.do","/*/ncms/poll/writeOk.do"})
	public String writeOk(HttpServletRequest request, @ModelAttribute("polldto") PollDTO polldto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("polldto", polldto);
		
		String prepage = Func.nvl( request.getParameter("prepage") );
		if("".equals(prepage)){prepage = "/ncms/poll/list.do";}
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String po_idx = Func.nvl( request.getParameter("po_idx") ).trim();
			
			//수정처리
			if("".equals(po_idx)){
				pollService.writeOk(model);
			}else{
				pollService.modifyOk(model);
			}
		}
		return "redirect:"+prepage;
	}
	
	/**************  Information  ****************************************
	* @Title : 설문조사 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/poll/levelOk.do","/*/ncms/poll/levelOk.do"})
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			String prepage = Func.nvl( request.getParameter("prepage") );
			if("".equals(prepage)){prepage = "/ncms/poll/list.do";}
			pollService.levelOk(model);
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 설문조사 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/poll/deleteOk.do","/*/ncms/poll/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			String prepage = Func.nvl( request.getParameter("prepage") );
			if("".equals(prepage)){prepage = "/ncms/poll/list.do";}
			pollService.deleteOk(model);
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 설문조사결과 엑셀
	* @method : resultExcel
	/****************************************************************/
	@RequestMapping(value={"/ncms/poll/resultExcel.do","/*/ncms/poll/resultExcel.do"})
	public String resultExcel(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);

		//관리자로그인 체크
		if(!"ok".equals(Func.getSession(request, "ss_security_ad_cms"))){
			return messageService.backMsg(model, "접근권한이 없습니다.");
		}
		
		pollService.resultList(model);
		
		return "/ncms/poll/resultExcel";
	}
}
