package kr.co.nninc.ncms.manager_memo.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.manager_memo.service.ManagerMemoDTO;
import kr.co.nninc.ncms.manager_memo.service.ManagerMemoService;

/**
 *관리자 페이지 구현 클래
 * 
 * @author 나눔
 * @since 2017.10.02
 * @version 1.0
 */

@Controller
public class ManagerMemoController {
	
	/** ManagerMemoService */
	@Resource(name = "managerMemoService")
	private ManagerMemoService managerMemoService;
	
	/**************  Information  ****************************************
	* @Title : 관리자 메모 등록/수정처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/manager_memo/writeOk.do")
	public String writeOk(HttpServletRequest request, @ModelAttribute("managermemodto") ManagerMemoDTO managermemodto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("managermemodto", managermemodto);
		
		//수정처리
		String mm_idx = Func.nvl( request.getParameter("mm_idx") ).trim();
		if("".equals(mm_idx)) {
			managerMemoService.writeOk(model);
		}else { //등록처리
			managerMemoService.modifyOk(model);
		}
		return "redirect:/ncms/index.do";
	}
}
