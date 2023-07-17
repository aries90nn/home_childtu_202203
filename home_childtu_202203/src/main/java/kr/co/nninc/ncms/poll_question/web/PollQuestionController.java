package kr.co.nninc.ncms.poll_question.web;

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
import kr.co.nninc.ncms.poll_question.service.PollQuestionDTO;
import kr.co.nninc.ncms.poll_question.service.PollQuestionService;

/**
 * 설문항목 관리를 위한 비즈니스 구현 클래스
 * @author 나눔
 * @since 2017.11.01
 * @version 1.0
 */
@Controller
public class PollQuestionController {
	/** PollQuestionService */
	@Resource(name = "pollQuestionService")
	private PollQuestionService pollQuestionService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ****************************************
	* @Title : 설문항목 등록폼 
	* @method : write
	/****************************************************************/
	@RequestMapping(value = {"/ncms/poll_question/write.do","/*/ncms/poll_question/write.do"})
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);

		pollQuestionService.write(model);

		return "/ncms/poll_question/write";
	}
	
	/**************  Information  ****************************************
	* @Title : 설문항목 등록처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/poll_question/writeOk.do","/*/ncms/poll_question/writeOk.do"})
	public String writeOk(HttpServletRequest request, @ModelAttribute("pollquestiondto") PollQuestionDTO pollquestiondto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("pollquestiondto", pollquestiondto);

		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			//등록처리
			String poq_idx = Func.nvl( request.getParameter("poq_idx") ).trim();
			if("".equals(poq_idx)) {
				pollQuestionService.writeOk(model);
			}else {
				//수정처리
				pollQuestionService.modifyOk(model);
			}
		}
		String prepage = Func.nvl(request.getParameter("prepage")).trim();
		if("".equals(prepage)){prepage = "/ncms/poll/list.do";}
		return "redirect:"+prepage;
	}
	
	
	/**************  Information  ***********************************
	* @Title : 설문항목 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/poll_question/levelOk.do","/*/ncms/poll_question/levelOk.do"})
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/poll/list.do";}
			pollQuestionService.levelOk(model);
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ************************************
	* @Title : 설문항목 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value={"/ncms/poll_question/listMove.do","/*/ncms/poll_question/listMove.do"})
	public String listMove(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		pollQuestionService.listMove(model);
		return "/ncms/poll_question/listMove";
	}
	
	/**************  Information  ************************************
	* @Title : 설문항목 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/poll_question/listMoveOk.do","/*/ncms/poll_question/listMoveOk.do"})
	public String listMoveOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			pollQuestionService.listMoveOk(model);
			String po_pk = Func.nvl( request.getParameter("po_pk") ).trim();
			String prepage = Func.nvl( request.getParameter("prepage") );
			if("".equals(prepage)){prepage = "/ncms/poll_question/write.do?po_pk="+po_pk;}
			return messageService.closeRedirect(model, "", prepage);
					
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 설문항목 삭제
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/poll_question/deleteOk.do","/*/ncms/poll_question/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);

		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			//수정처리
			pollQuestionService.deleteOk(model);
			
			String po_pk = Func.nvl( request.getParameter("po_pk") ).trim();
			String prepage = Func.nvl( request.getParameter("prepage") );
			if("".equals(prepage)){prepage = "/ncms/poll_question/write.do?po_pk="+po_pk;}
			return "redirect:"+prepage;
		}
	}
}
