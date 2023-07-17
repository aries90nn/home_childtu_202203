package kr.co.nninc.ncms.index.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.board_config.service.BoardConfigService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.login.service.LoginService;
import kr.co.nninc.ncms.manager_memo.service.ManagerMemoService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.site_info.service.SiteInfoService;
import kr.co.nninc.ncms.stats.service.StatsService;

/**
 *관리자 페이지 구현 클래
 * 
 * @author 나눔
 * @since 2017.10.02
 * @version 1.0
 */

@Controller
public class IndexController {

	@Resource(name = "loginService")
	private LoginService loginService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;

	@Resource(name = "menuService")
	private MenuService menuService;
	
	@Resource(name = "siteInfoService")
	private SiteInfoService siteInfoService;
	
	@Resource(name = "boardConfigService")
	private BoardConfigService boardConfigService;
	
	@Resource(name = "statsService")
	private StatsService statsService;
	
	/** ManagerMemoService */
	@Resource(name = "managerMemoService")
	private ManagerMemoService managerMemoService;
	
	/** 메인 페이지 */
	@RequestMapping(value = { "/ncms/index.do", "/ncms.do", "/ncms/", "/ncms" })
	public String index(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("request", request);
		
		String ss_m_id						= (String) Func.getSession(request, "ss_m_id");
		if("".equals(ss_m_id)) {
			return "redirect:/ncms/login.do";
		}else {
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			
			//사이트 정보
			siteInfoService.ncmsIndex(model);
			
			//게시판 정보
			boardConfigService.ncmsIndex(model);
			
			//통계 정보
			statsService.ncmsIndex(model);
			
			//관리자메모
			managerMemoService.view(model);
			
			return "/ncms/index";
		}
	}

}
