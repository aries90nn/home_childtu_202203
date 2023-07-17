package kr.co.nninc.ncms.homepage_log_work.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.homepage_log_work.service.HomepageLogWorkService;
import kr.co.nninc.ncms.manager_menu.service.MenuService;


/**
 * 로그관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.18
 * @version 1.0
 */
@Controller
public class HomepageLogWorkController {

	/** LogService */
	@Resource(name = "logWorkService")
	private HomepageLogWorkService logService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/**************  Information  ****************************************
	* @Title : 홈페이지 작업기록
	* @method : list
	/****************************************************************/
	@RequestMapping(value = {"/ncms/log/list.do","/*/ncms/log/list.do"})
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("stats_top"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){
			//관리자 탑메뉴 리스트
			menuService.mngTopList(model);
			// 관리자 레프트메뉴 리스트
			menuService.mngLeftList(model);
		}
		
		logService.list(model);
		return "/ncms/log/list";
	}
	
}
