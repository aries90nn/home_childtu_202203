package kr.co.nninc.ncms.edusat.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.View;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.ExcelView;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.edusat.service.EdusatDTO;
import kr.co.nninc.ncms.edusat.service.EdusatRequestDTO;
import kr.co.nninc.ncms.edusat.service.EdusatService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;

/**
 * 온라인수강신청 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.09.13
 * @version 1.0
 */
@Controller
public class EdusatController {

	/** EdusatService */
	@Resource(name = "edusatService")
	private EdusatService edusatService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	
	/**************  Information  ************************************
	* @Title : 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/list.do","/*/ncms/edusat/list.do"})
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
		edusatService.list(model);
		
		return "/ncms/edusat/list";
	}
	
	/**************  Information  ************************************
	* @Title : 강좌정보/신청자관리
	* @method : write
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/write.do","/*/ncms/edusat/write.do"})
	public String write(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		String edu_idx = Func.nvl(request.getParameter("edu_idx")).trim();
		if( "".equals(edu_idx) ) {
			edusatService.write(model);
		}else{
			edusatService.modify(model);
		}
		return "/ncms/edusat/write";
	}
	
	/**************  Information  ************************************
	* @Title : 강좌정보등록/수정
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/writeOk.do","/*/ncms/edusat/writeOk.do"})
	public String writeOk(HttpServletRequest request, MultipartHttpServletRequest multi, @ModelAttribute("edusatdto") EdusatDTO edusatdto, Model model) throws Exception {
		//System.out.println("edusatcontroller.writeOk");
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("edusatdto", edusatdto);

		String edu_idx = Func.nvl(request.getParameter("edu_idx")).trim();
		if( "".equals(edu_idx) ) {
			edusatService.writeOk(model);
		}else{
			edusatService.modifyOk(model);
		}
		
		String url = Func.cStr( request.getParameter("prepage") );
		if("".equals(url)){ url = "list.do"; }
		return "redirect:"+url;
		
	}
	
	/**************  Information ***********************************
	* @Title : 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/levelOk.do","/*/ncms/edusat/levelOk.do"})
	public String levelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			
			edusatService.levelOk(model);
			String url = Func.cStr( request.getParameter("prepage") );
			if("".equals(url)){ url = "list.do"; }
			return "redirect:"+url;
		}
	}
	
	/**************  Information ***********************************
	* @Title : 강좌 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/deleteOk.do","/*/ncms/edusat/deleteOk.do"})	
	public String deleteOk(HttpServletRequest request, @ModelAttribute("edusatdto") EdusatDTO edusatdto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("edusatdto", edusatdto);
		
		//허용 url
		if(Func.urlChk(model,"list.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			
			edusatService.deleteOk(model);
			String url = Func.cStr( request.getParameter("prepage") );
			if("".equals(url)){
				url = "list.do";
			}
			return "redirect:"+url;
		}
	}
	
	/**************  Information  ************************************
	* @Title : 목록
	* @method : list
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/requestList.do","/*/ncms/edusat/requestList.do"})
	public String requestList(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));

		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		edusatService.requestList(model);
		
		return "/ncms/edusat/requestList";
	}
	
	/**************  Information  ************************************
	* @Title : 신청자 엑셀다운 
	* @method : excel
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/excel.do","/*/ncms/edusat/excel.do"})
	public View excel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("edusatdto") EdusatDTO edusatdto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("edusatdto", edusatdto);
		
		edusatService.excel(model);
		return new ExcelView();
	}
	
	
	/**************  Information  ************************************
	* @Title : 신청자정보관리
	* @method : request
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/request.do","/*/ncms/edusat/request.do"})
	public String request(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		edusatService.viewRequest(model);
		return "/ncms/edusat/request";
	}
	
	/**************  Information  ************************************
	* @Title : 신청정보등록/수정
	* @method : requestOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/requestOk.do","/*/ncms/edusat/requestOk.do"})
	public String requestOk(HttpServletRequest request, @ModelAttribute("edusatrequestdto") EdusatRequestDTO edusatrequestdto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("edusatrequestdto", edusatrequestdto);

		String es_idx = Func.nvl(request.getParameter("es_idx")).trim();
		if( "".equals(es_idx ) ) {
			//edusatService.writeOk(model);
		}else{
			edusatService.modifyRequest(model);
		}
		return messageService.closeRedirect(model, "", "");
	}
	
	/**************  Information  ************************************
	* @Title : 신청정보삭제
	* @method : delete2Ok
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/delete2Ok.do","/*/ncms/edusat/delete2Ok.do"})
	public String delete2Ok(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		edusatService.deleteRequest(model);

		String url = Func.cStr( request.getParameter("prepage") );
		if("".equals(url)){ url = "/ncms/edusat/list.do"; }
		return "redirect:"+url;
		
	}
	
	
	/**************  Information  ************************************
	* @Title : 강좌신청자 추첨
	* @method : winOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/winOk.do","/*/ncms/edusat/winOk.do"})
	public String winOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);
		
		String rtn_url = edusatService.winOk(model);
		return rtn_url;
	}
	
	/**************  Information ***********************************
	* @Title : 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat/reqlevelOk.do","/*/ncms/edusat/reqlevelOk.do"})
	public String reqlevelOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"requestList.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			edusatService.levelOk2(model);
			String url = Func.cStr( request.getParameter("prepage") );
			if("".equals(url)){ url = "list.do"; }
			return "redirect:"+url;
		}
	}
}
