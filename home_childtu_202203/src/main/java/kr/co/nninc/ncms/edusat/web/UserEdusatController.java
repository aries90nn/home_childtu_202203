package kr.co.nninc.ncms.edusat.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.edusat.service.EdusatDTO;
import kr.co.nninc.ncms.edusat.service.EdusatRequestDTO;
import kr.co.nninc.ncms.edusat.service.EdusatService;
import kr.co.nninc.ncms.edusat.service.UserEdusatService;

/**
 * 온라인강좌 사용자
 * 
 * @author 나눔
 * @since 2017.10.13
 * @version 1.0
 */
@Controller
public class UserEdusatController {

	@Resource(name="edusatService")
	private EdusatService edusatService;
	
	@Resource(name="userEdusatService")
	private UserEdusatService userEdusatService;

	/** UserCmsService */
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	
	/**************  Information ************************************
	* @Title : 강좌 목록을 조회한다.
	* @method : list
	/****************************************************************/
	@RequestMapping(value={"/edusat/list.do","/*/edusat/list.do"})
	public String list(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);

		userEdusatService.list(model);
		return "/site/edusat/list";
	}
	
	/**************  Information ************************************
	* @Title : 강좌 상세정보
	* @method : view
	/****************************************************************/
	@RequestMapping(value={"/edusat/view.do","/*/edusat/view.do"})
	public String view(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		//edusatService.modify 를 공통으로 사용
		edusatService.modify(model);
		
		return "/site/edusat/view";
		
	}
	
	/**************  Information ************************************
	* @Title : 강좌 첨부파일 다운로드
	* @method : down
	/****************************************************************/
	@RequestMapping(value={"/edusat/down.do","/*/edusat/down.do"})
	public String down(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		
		//edusatService.modify 를 공통으로 사용
		edusatService.modify(model);
		
		return "/site/edusat/down";
	}
	
	/**************  Information ************************************
	* @Title : 강좌 신청폼
	* @method : regist
	/****************************************************************/
	@RequestMapping(value={"/edusat/regist.do","/*/edusat/regist.do"})
	public String regist(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		String url = "/site/edusat/regist";
		String es_idx = Func.nvl(request.getParameter("a")).trim();
		if("".equals(es_idx)){
			System.out.println("작성");
			url = userEdusatService.regist(model);
		}else{
			System.out.println("수정");
			url = userEdusatService.modify(model);
		}
		
		return url;
		
	}
	
	/**************  Information ************************************
	* @Title : 강좌 신청처리
	* @method : registOk
	/****************************************************************/
	@RequestMapping(value={"/edusat/registOk.do","/*/edusat/registOk.do"})
	public String registOk(HttpServletRequest request, @ModelAttribute("edusatrequestdto") EdusatRequestDTO edusatrequestdto, Model model) throws Exception{
		model.addAttribute("request", request);
		model.addAttribute("edusatrequestdto", edusatrequestdto);
		
		String es_idx = Func.nvl(request.getParameter("es_idx")).trim();
		String edu_idx = Func.nvl(request.getParameter("edu_idx")).trim();
		String url = "";
		if("".equals(es_idx) || "".equals(edu_idx)) {
			url = userEdusatService.registOk(model);	
		}else{
			url = userEdusatService.modifyOk(model);
		}
		
		return url;
	}
	
	
	/**************  Information ************************************
	* @Title : 신청확인
	* @method : user
	/****************************************************************/
	@RequestMapping(value={"/edusat/user.do","/*/edusat/user.do"})
	public String user(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);

		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		String rtn_url = userEdusatService.user(model);
		
		edusatService.config(model, 1);
		
		return rtn_url;
	}
	
	/**************  Information ************************************
	* @Title : 신청취소 처리
	* @method : cancelOk
	/****************************************************************/
	@RequestMapping(value={"/edusat/cancelOk.do","/*/edusat/cancelOk.do"})
	public String cancelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		
		String rtn_url = userEdusatService.cancelOk(model);
		return rtn_url;
	}
	
	/**************  Information ************************************
	* @Title : 본인 강좌내역 조회
	* @method : myinfo
	/****************************************************************/
	@RequestMapping(value={"/edusat/myinfo.do","/*/edusat/myinfo.do"})
	public String myinfo(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		String NOWPAGE_ENCODE = Func.urlEncode( Func.getNowPage(request) );
		// 권한체크
		String ss_m_coinfo = Func.getSession(request, "ss_m_dupinfo");
		String ss_g_num = Func.getSession(request, "ss_g_num");
		if("".equals(ss_m_coinfo) && "".equals(ss_g_num)) {
			return messageService.redirectMsg(model, "본인인증 페이지로 이동합니다.", "/main/site/member/ipin.do?prepage="+NOWPAGE_ENCODE);
		}
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		userEdusatService.myinfo(model);
		
		
		
		return "/site/edusat/myinfo";
	}
	
	/**************  Information  ****************************************
	* @Title : 게시판 첨부파일
	* @method : nfu_upload
	/****************************************************************/
	
	@RequestMapping(value={"/edusat/nfu_upload.do","/*/edusat/nfu_upload.do"}, method = RequestMethod.POST)
	public String nfu_upload(HttpServletRequest request, MultipartHttpServletRequest multi, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		System.out.println("멀티업로드");
		userEdusatService.nfuUpload(model);
		return "/site/edusat/nfu_upload";
	}
	
	/**************  Information  ****************************************
	* @Title : 게시판 일반 첨부파일
	* @method : nfu_normal_upload
	/****************************************************************/
	
	@RequestMapping(value={"/edusat/nfu_normal_upload.do","/*/edusat/nfu_normal_upload.do"})
	public String nfu_normal_upload(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		System.out.println("일반업로드");
		return "/site/edusat/nfu_normal_upload";
	}
	
	/**************  Information  ****************************************
	* @Title : 게시판 일반 첨부파일 처리
	* @method : nfu_normal_upload_ok
	/****************************************************************/
	@RequestMapping(value = "/edusat/nfu_nfu_normal_upload_ok.do", method = RequestMethod.POST)
	public String nfu_normal_upload_ok(HttpServletRequest request, MultipartHttpServletRequest multi, @ModelAttribute("edusatrequestdto") EdusatRequestDTO edusatrequestdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("edusatrequestdto", edusatrequestdto);
		userEdusatService.nfuNormalUpload(model);
		return "/site/edusat/nfu_normal_upload";
		//허용 url
		/*
		if(Func.urlChk(model,"nfu_normal_upload.do").equals("N")) {
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}else {
			
			userEdusatService.nfuNormalUpload(model);
			return "/site/edusat/nfu_normal_upload";
		}
		*/
	}
	
	/**************  Information  ************************************
	* @Title : 이미지 파일 삭제
	* @method : imgDeleteOk
	/****************************************************************/
	@RequestMapping(value={"/edusat/imgDeleteOk.do","/*/edusat/imgDeleteOk.do"})
	public String imgDeleteOk(@ModelAttribute("edusatrequestdto") EdusatRequestDTO edusatrequestdto, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("type", "down");
		model.addAttribute("edusatrequestdto", edusatrequestdto);
		
		//허용 url
		if(Func.urlChk(model,"nfu_normal_upload.do,request.do,regist.do").equals("N")){
			return messageService.backMsg(model, "잘못된 접근입니다1.");
		}
		
		return userEdusatService.imgDeleteOk(model);
	}
	
	/**************  Information  ************************************
	* @Title : 입력정보확인
	* @method : request
	/****************************************************************/
	@RequestMapping(value={"/site/mylib/request.do","/*/site/mylib/request.do"})
	public String request(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//레프트메뉴
		userCmsService.left(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		
		userEdusatService.viewRequest(model);
		return "/site/edusat/request";
	}
}
