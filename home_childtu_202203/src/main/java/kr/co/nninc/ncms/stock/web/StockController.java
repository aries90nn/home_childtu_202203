package kr.co.nninc.ncms.stock.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.buseo_member.service.BuseoMemberDTO;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.edusat.service.EdusatRequestDTO;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.stock.service.StockDTO;
import kr.co.nninc.ncms.stock.service.StockService;

/**
 * 투명우산 재고관리
 * 
 * @author 나눔
 * @since 2022.06.15
 * @version 1.0
 */
@Controller
public class StockController {
	
	/** stockService */
	@Resource(name="stockService")
	private StockService stockService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	
	/**************  Information  ***********************************
	* @Title : 목록
	* @method : write
	/************************************************************/
	@RequestMapping(value={"/ncms/stock/write.do","/*/ncms/stock/write.do"})
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
		return stockService.write(model);
		//return "/ncms/stock/write";
	}
	
	/**************  Information  ***********************************
	* @Title : 등록
	* @method : writeOk
	/************************************************************/
	@RequestMapping(value={"/ncms/stock/writeOk.do","/*/ncms/stock/writeOk.do"})
	public String writeOk(HttpServletRequest request, @ModelAttribute("stockdto") StockDTO stockdto, Model model) throws Exception{
		model.addAttribute("request", request);
		model.addAttribute("stockdto", stockdto);
		model.addAttribute("mng_left_cd", CommonConfig.get("other_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		return stockService.writeOk(model);
		//return "/ncms/stock/write";
	}
	
	/**************  Information  ***********************************
	* @Title : 재고로그 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/stock/deleteOk.do","/*/ncms/stock/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else{
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/ncms/stock/write.do";}
			stockService.deleteOk(model);
			
			return "redirect:"+prepage;
		}
	}
	
	/**************  Information  ***********************************
	* @Title : 엑셀업로드 팝업 창
	* @method : popWrite
	/****************************************************************/
	@RequestMapping(value = {"/ncms/stock/excel.do", "/*/ncms/stock/excel.do"})
	public String excel(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request",request);
		//System.out.println("여기오나?");
		stockService.excel(model);
		
		return "/ncms/stock/excel";
	}

}
