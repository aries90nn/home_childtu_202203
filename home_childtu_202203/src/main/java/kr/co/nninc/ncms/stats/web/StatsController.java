package kr.co.nninc.ncms.stats.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.stats.service.StatsService;

/**
 * 통계 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.18
 * @version 1.0
 */
@Controller
public class StatsController {

	/** StatsService */
	@Resource(name = "statsService")
	private StatsService statsService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/**************  Information  ****************************************
	* @Title : 시간별 통계
	* @method : hour
	/****************************************************************/
	@RequestMapping(value = {"/ncms/stats/h.do","/*/ncms/stats/h.do"})
	public String hour(HttpServletRequest request, Model model)
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

		statsService.hour(model);

		return "/ncms/stats/h";
	}
	
	/**************  Information  ****************************************
	* @Title : 일별 통계
	* @method : day
	/****************************************************************/
	@RequestMapping(value = {"/ncms/stats/d.do","/*/ncms/stats/d.do"})
	public String day(HttpServletRequest request, Model model)
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
		statsService.day(model);

		return "/ncms/stats/d";
	}
	
	/**************  Information  ****************************************
	* @Title : 요일별 통계
	* @method : week
	/****************************************************************/
	@RequestMapping(value = {"/ncms/stats/w.do","/*/ncms/stats/w.do"})
	public String week(HttpServletRequest request, Model model)
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

		statsService.week(model);

		return "/ncms/stats/w";
	}
	
	/**************  Information  ****************************************
	* @Title : 월별 통계
	* @method : month
	/****************************************************************/
	@RequestMapping(value = {"/ncms/stats/m.do","/*/ncms/stats/m.do"})
	public String month(HttpServletRequest request, Model model)
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

		statsService.month(model);

		return "/ncms/stats/m";
	}
	
	/**************  Information  ****************************************
	* @Title : 방문자 리스트
	* @method : month
	/****************************************************************/
	@RequestMapping(value = {"/ncms/stats/log.do","/*/ncms/stats/log.do"})
	public String log(HttpServletRequest request, Model model)
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

		statsService.log(model);

		return "/ncms/stats/log";
	}
	
	/**************  Information  ****************************************
	* @Title : 메뉴접속통계
	* @method : stat
	/****************************************************************/
	@RequestMapping(value = {"/ncms/stats_menu/stat.do","/*/ncms/stats_menu/stat.do"})
	public String stat(HttpServletRequest request, Model model)
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

		statsService.stat(model);
		
		return "/ncms/stats_menu/stat";
	}
}
