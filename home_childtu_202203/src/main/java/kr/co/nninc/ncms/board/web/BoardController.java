package kr.co.nninc.ncms.board.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.board.service.BoardService;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board_config.service.BoardConfigDTO;
import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.FuncMember;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.edusat.service.UserEdusatService;

/**
 * 게시물을 관리하기 위한 비즈니스 구현 클래스
 * @author 나눔
 * @since 2019.01.14
 * @version 1.1
 */
@Controller
public class BoardController {
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;

	
	/** BoardService */
	@Resource(name="boardService")
	private BoardService boardService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;

	/** UserCmsService */
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;
	
	
	/**************  Information  ************************************
	* @Title : 게시물의 목록을 조회한다.
	* @method : list
	/****************************************************************/
	@RequestMapping("/board/list.do")
	public String list(HttpServletRequest request, Model model) throws Exception {
		SkinDTO skinDTO = new SkinDTO();
		model.addAttribute("request", request);
		model.addAttribute("skinDTO", skinDTO);
		
		
		
		return boardService.list(model);
	}
	
	/**************  Information  ************************************
	* @Title : 컨텐츠내용
	* @method : contents
	/****************************************************************/
	@RequestMapping({"/viewCalendar.do", "/*/viewCalendar.do"})
	public String viewCalendar(HttpServletRequest request, Model model) throws Exception{
		System.out.println("여기오냐?");
		SkinDTO skinDTO = new SkinDTO();
		model.addAttribute("request", request);
		model.addAttribute("skinDTO", skinDTO);
		
		//사용자페이지
		//return boardService.list(model);
		return "/site/builder/main/main01/calendar";
	}

	/**************  Information  ************************************
	* @Title : 게시물 상세보기
	* @method : view
	/****************************************************************/
	@RequestMapping("/board/view.do")
	public String view(HttpServletRequest request, Model model) throws Exception {
		SkinDTO skinDTO = new SkinDTO();
		model.addAttribute("request", request);
		model.addAttribute("skinDTO", skinDTO);
		
		return boardService.view(model);
	}
	
	/**************  Information  ************************************
	* @Title : 게시물의 글쓰기 페이지
	* @method : write
	/****************************************************************/
	@RequestMapping("/board/write.do")
	public String write(@ModelAttribute("boarddto") BoardDTO boarddto, HttpServletRequest request, Model model) throws Exception {
		SkinDTO skinDTO = new SkinDTO();
		model.addAttribute("request", request);
		model.addAttribute("boarddto", boarddto);
		model.addAttribute("skinDTO", skinDTO);
		
		if("".equals(Func.nvl( request.getParameter("b_num") ))) {
			return boardService.write(model);
		}else {
			return boardService.modify(model);
		}

	}
	
	
	/**************  Information  ****************************************
	* @Title :게시물 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping("/board/writeOk.do")
	public String writeOk(@ModelAttribute("boarddto") BoardDTO boarddto, HttpServletRequest request, MultipartHttpServletRequest multi, Model model)
			throws Exception {
		SkinDTO skinDTO = new SkinDTO();
		model.addAttribute("request", request);
		model.addAttribute("boarddto", boarddto);
		model.addAttribute("multi", multi);
		model.addAttribute("skinDTO", skinDTO);
		
		//허용 url
		if(boardService.checkToken(request).equals("N")){
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}
		String b_num = Func.nvl( request.getParameter("b_num") );
		if("".equals(b_num)){
			return boardService.writeOk(model);
		}else{
			return boardService.modifyOk(model);
		}
		
	}
	
	
	/**************  Information  ************************************
	* @Title : 게시물의 비밀번호 체크
	* @method : pwd
	/****************************************************************/
	@RequestMapping("/board/pwd.do")
	public String pwd(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);

		boardService.pwd(model);
		return "/site/board/pwd";
	}
	
	
	/**************  Information  ************************************
	* @Title : 게시물의 비밀번호 확인 처리
	* @method : pwdOk
	/****************************************************************/
	@RequestMapping("/board/pwdOk.do")
	public String pwdOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		//허용 url
		if(boardService.checkToken(request).equals("N")){
			return "redirect:/error.do";
		}
		
		return boardService.pwdOk(model);

	}
	
	
	/**************  Information  ************************************
	* @Title : 게시물삭제처리
	* @method : delete
	/****************************************************************/
	@RequestMapping("/board/deleteOk.do")
	public String deleteOk(HttpServletRequest request, Model model) throws Exception {
		SkinDTO skinDTO = new SkinDTO();
		model.addAttribute("request", request);
		model.addAttribute("skinDTO", skinDTO);
		//허용 url
		if(boardService.checkToken(request).equals("N")){
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}

		return boardService.deleteOk(model);
	}
	
	
	
	
	/**************  Information  ************************************
	* @Title : 게시물 이미지보기
	* @method : get_img
	/****************************************************************/
	@RequestMapping("/board/get_img.do")
	public String get_img(@ModelAttribute("boarddto") BoardDTO boarddto, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("type", "img");
		model.addAttribute("boarddto", boarddto);
		
		String rtn_url = boardService.down(model);
		return rtn_url;
	}
	
	/**************  Information  ************************************
	* @Title : 첨부파일 파일다운로드
	* @method : down
	/****************************************************************/
	@RequestMapping("/board/down.do")
	public String down(@ModelAttribute("boarddto") BoardDTO boarddto, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("boarddto", boarddto);
		
		String rtn_url = boardService.down(model);
		return rtn_url;
	}

	
	
	/**************  Information  ****************************************
	* @Title : 게시판 첨부파일
	* @method : nfu_upload
	/****************************************************************/
	@RequestMapping(value = "/board/nfu_upload.do", method = RequestMethod.POST)
	public String nfu_upload(HttpServletRequest request, MultipartHttpServletRequest multi, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		
		boardService.nfuUpload(model);
		return "/site/board/nfu_upload";
	}
	
	/**************  Information  ****************************************
	* @Title : 게시판 일반 첨부파일
	* @method : nfu_normal_upload
	/****************************************************************/
	@RequestMapping("/board/nfu_normal_upload.do")
	public String nfu_normal_upload(@ModelAttribute("boarddto") BoardDTO boarddto, HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("boarddto", boarddto);
		
		return "/site/board/nfu_normal_upload";
	}
	
	/**************  Information  ****************************************
	* @Title : 게시판 일반 첨부파일 처리
	* @method : nfu_normal_upload_ok
	/****************************************************************/
	@RequestMapping(value = "/board/nfu_normal_upload_ok.do", method = RequestMethod.POST)
	public String nfu_normal_upload_ok(HttpServletRequest request, MultipartHttpServletRequest multi, @ModelAttribute("boarddto") BoardDTO boarddto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("boarddto", boarddto);
		
		//허용 url
		if(Func.urlChk(model,"nfu_normal_upload.do").equals("N")) {
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}else {
			boardService.nfuNormalUpload(model);
			return "/site/board/nfu_normal_upload";
		}
	}
	
	/**************  Information  ************************************
	* @Title : 이미지 파일 삭제
	* @method : imgDeleteOk
	/****************************************************************/
	@RequestMapping("/board/imgDeleteOk.do")
	public String imgDeleteOk(@ModelAttribute("boarddto") BoardDTO boarddto, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("type", "down");
		model.addAttribute("boarddto", boarddto);
		
		//허용 url
		if(boardService.checkToken(request).equals("N")){
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}
		
		return boardService.imgDeleteOk(model);
	}
	
	
	/**************  Information  ************************************
	* @Title : 게시판 로그인
	* @method : login
	/****************************************************************/
	@RequestMapping("/board/login.do")
	public String login(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		return boardService.login(model);
	}
	
	
	/**************  Information  ************************************
	* @Title : 게시판 로그인 처리
	* @method : manager
	/****************************************************************/
	@RequestMapping("/board/loginOk.do")
	public String loginOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		//허용 url
		if(boardService.checkToken(request).equals("N")){
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}else {
			return boardService.loginOk(model);
		}
	}
	
	/**************  Information  ************************************
	* @Title : 로그아웃
	* @method : logout
	/****************************************************************/
	@RequestMapping("/board/logout.do")
	public String logout(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
			
		String prepage = Func.nvl(request.getParameter("prepage"));
		if("".equals(prepage)) prepage = "/";
		//로그아웃
		FuncMember.memgr_logout(request);
		return messageService.redirectMsg(model, "로그아웃 되었습니다", prepage);
	}
	
	
	
	
	/**************  Information  ************************************
	* @Title : 게시물의 답변 페이지
	* @method : reply
	/****************************************************************/
	@RequestMapping("/board/reply.do")
	public String reply(@ModelAttribute("boarddto") BoardDTO boarddto, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("boarddto", boarddto);

		//답변폼
		return boardService.reply(model);
	}
	
	/**************  Information  ************************************
	* @Title : 게시물의 답변 처리
	* @method : reply
	/****************************************************************/
	@RequestMapping("/board/replyOk.do")
	public String replyOk(@ModelAttribute("boarddto") BoardDTO boarddto, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("boarddto", boarddto);
		
		//허용 url
		if(boardService.checkToken(request).equals("N")){
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}else {
			//답변처리
			return boardService.replyOk(model);
		}
	}
	
	
	
	
	/**************  Information  ************************************
	* @Title : 게시물의 모듈 호출
	* @method : board
	/****************************************************************/
	@RequestMapping("/board/board.do")
	public String board(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		//a_level이 넘어온다면 property에 선언된 a_num이 있는지 체크
		String a_level = Func.nvl( request.getParameter("a_level") ).trim();
		String a_num = "";
		if(!"".equals(a_level)){
			a_num = Func.nvl( CommonConfig.getBoard(a_level+"[a_num]") ).trim();
			if(!"".equals(a_num))request.setAttribute("a_num", a_num);
		}
		System.out.println("board.a_num="+a_num+", "+a_level);
		Func.getNowPage(request);
		
		return "/site/board/board";
	}
	
	
	/**************  Information  ************************************
	* @Title : 스킨별 별도처리페이지
	* @method : board
	/****************************************************************/
	@RequestMapping(value="/board/*/*.do")
	public String skinProcPage(HttpServletRequest request, Model model) throws Exception {
		SkinDTO skinDTO = new SkinDTO();
		model.addAttribute("request", request);
		model.addAttribute("skinDTO", skinDTO);

		return boardService.skinProcPage(model);
	}
	
	/**************  Information  ****************************************
	* @Title : 게시글 작성 내역
	* @method : loanList
	/****************************************************************/
	@RequestMapping(value={"/board/myinfo.do","/*/board/myinfo.do"})
	public String loanList(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
				
		model.addAttribute("request", request);
		//boardService.nfuUpload(model);
		return boardService.loanList(model);
	}
	
	/**************  Information  ****************************************
	* @Title :게시물 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/*/*/board/levelOk.do","/*/*/*/board/levelOk.do","/levelOk.do","/*/levelOk.do"})
	public String levelOk(HttpServletRequest request, Model model) throws Exception {
		SkinDTO skinDTO = new SkinDTO();
		model.addAttribute("request", request);
		model.addAttribute("skinDTO", skinDTO);
		//허용 url
		if(boardService.checkToken(request).equals("N")){
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}

		return boardService.levelOk(model);
	}
	
	
}
