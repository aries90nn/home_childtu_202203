package kr.co.nninc.ncms.manager_sql.web;

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
import kr.co.nninc.ncms.manager_sql.service.ManagerSqlDTO;
import kr.co.nninc.ncms.manager_sql.service.ManagerSqlService;


/**
 * 키워드 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.17
 * @version 1.0
 */
@Controller
public class ManagerSqlController {

	/** ManagerSqlService */
	@Resource(name = "managerSqlService")
	private ManagerSqlService managerSqlService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**************  Information  ****************************************
	* @Title : 키워드 관리
	* @method : write
	/****************************************************************/
	@RequestMapping(value = "/ncms/sql/write.do")
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("security_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);

		managerSqlService.write(model);

		return "/ncms/sql/write";
	}
	
	/**************  Information  ****************************************
	* @Title : 키워드 관리 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/sql/writeOk.do")
	public String writeOk(HttpServletRequest request, @ModelAttribute("managersqldto") ManagerSqlDTO managersqldto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("managersqldto", managersqldto);

		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else {
			String ms_num = Func.nvl( request.getParameter("ms_num") ).trim();
			
			if("".equals(ms_num)) {
				managerSqlService.writeOk(model);
			}else {
				managerSqlService.modifyOk(model);
			}
		}
		return "redirect:/ncms/sql/write.do";
	}
}
