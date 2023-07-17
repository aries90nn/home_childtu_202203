package kr.co.nninc.ncms.buseo.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.nninc.ncms.buseo.service.BuseoDTO;
import kr.co.nninc.ncms.buseo.service.BuseoService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;

/**
 * 부서 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2018.11.16
 * @version 1.0
 */
@Controller
public class BuseoController {
	
	/** BuseoService */
	@Resource(name="buseoService")
	private BuseoService buseoService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	@Resource(name = "messageService")
	private MessageService messageService;

	/**************  Information  ***********************************
	* @Title : 목록
	* @method : list
	/************************************************************/
	@RequestMapping(value = {"/ncms/buseo/write.do", "/*/ncms/buseo/write.do"})
	public String list(HttpServletRequest request, @ModelAttribute("buseodto") BuseoDTO buseodto, Model model) throws Exception{
		model.addAttribute("request", request);
		model.addAttribute("buseodto", buseodto);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}

		return buseoService.write(model);
	}
	

	/**************  Information  ***********************************
	* @Title : 부서 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/buseo/writeOk.do", "/*/ncms/buseo/writeOk.do"}, method = RequestMethod.POST)
	public String writeOk(HttpServletRequest request, @ModelAttribute("buseodto") BuseoDTO buseodto
			, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("buseodto", buseodto);
		
		String ct_idx = Func.nvl(request.getParameter("ct_idx"));
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			String rtn_url = "";
			//수정처리
			if (Func.cInt(ct_idx) != 0) {
				rtn_url = buseoService.modifyOk(model);
			}else { //등록처리
				rtn_url = buseoService.writeOk(model);
			}
			return rtn_url;
		}
	}
	
	
	/**************  Information  ***********************************
	* @Title : 부서 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/buseo/deleteOk.do", "/*/ncms/buseo/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, @ModelAttribute("buseodto") BuseoDTO buseodto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("buseodto",buseodto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			return buseoService.deleteOk(model);
		}
	}

	/**************  Information  ***********************************
	* @Title : 부서 순서일괄팝업
	* @method : listMove
	/****************************************************************/
	@RequestMapping(value = {"/ncms/buseo/listMove.do", "/*/ncms/buseo/listMove.do"})
	public String listMove(HttpServletRequest request, @ModelAttribute("buseodto") BuseoDTO buseodto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("buseodto",buseodto);

		buseoService.listMove(model);
		return "/ncms/buseo/listMove";
	}

	/**************  Information  ***********************************
	* @Title : 부서 순서일괄처리
	* @method : listMoveOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/buseo/listMoveOk.do", "/*/ncms/buseo/listMoveOk.do"})
	public String listMoveOk(HttpServletRequest request, @ModelAttribute("buseodto") BuseoDTO buseodto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("buseodto",buseodto);

		//허용 url
		if(Func.urlChk(model,"listMove.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			return buseoService.listMoveOk(model);
		}
	}
	
	/**************  Information  ***********************************
	* @Title : 부서 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value = {"/ncms/buseo/levelOk.do", "/*/ncms/buseo/levelOk.do"})
	public String levelOk(HttpServletRequest request, @ModelAttribute("buseodto") BuseoDTO buseodto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("buseodto",buseodto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			return buseoService.levelOk(model);
		}
	}
	/**************  Information  ***********************************
	* @Title : 부서 상태변경
	* @method : listAjax
	/****************************************************************/
	@RequestMapping(value = {"/ncms/buseo/listAjax.do", "/*/ncms/buseo/listAjax.do"})
	public String listAjax(HttpServletRequest request, @ModelAttribute("buseodto") BuseoDTO buseodto, Model model) throws Exception{
		model.addAttribute("request",request);
		model.addAttribute("buseodto",buseodto);
		
		return buseoService.listAjax(model);
	}

}
