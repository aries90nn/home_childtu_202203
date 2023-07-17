package kr.co.nninc.ncms.board_command.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.board_command.service.BoardCommandDTO;
import kr.co.nninc.ncms.board_command.service.BoardCommandService;

/**
 * 게시판 댓글을 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.11.07
 * @version 1.0
 */
@Controller
public class BoardCommandController {

	/** BoardCommandService */
	@Resource(name="boardCommandService")
	private BoardCommandService boardCommandService;
	
	/**************  Information  ************************************
	* @Title : 댓글 등록
	* @method : writeok
	/****************************************************************/
	@RequestMapping(value="/board/command/writeOk.do")
	public String writeOk(@ModelAttribute("boardcommanddto") BoardCommandDTO boardcommanddto, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("boardcommanddto", boardcommanddto);
		
		String url = boardCommandService.writeOk(model);
		return url;
	}
	
	/**************  Information  ************************************
	* @Title : 댓글 삭제
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value="/board/command/deleteOk.do")
	public String deleteOk(@ModelAttribute("boardcommanddto") BoardCommandDTO boardcommanddto, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("boardcommanddto", boardcommanddto);
		
		String url = boardCommandService.deleteOk(model);
		return url;
	}
}
