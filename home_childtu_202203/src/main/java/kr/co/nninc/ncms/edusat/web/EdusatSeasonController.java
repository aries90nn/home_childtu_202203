package kr.co.nninc.ncms.edusat.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.edusat.service.EdusatSeasonDTO;
import kr.co.nninc.ncms.edusat.service.EdusatSeasonSerivce;
import kr.co.nninc.ncms.manager_menu.service.MenuService;

/**
 * 온라인수강신청 기수관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2019.09.09
 * @version 1.0
 */
@Controller
public class EdusatSeasonController {
	/** EdusatSeasonService */
	@Resource(name = "edusatSeasonService")
	private EdusatSeasonSerivce edusatSeasonService;
	
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
	@RequestMapping(value={"/ncms/edusat_season/list.do","/*/ncms/edusat_season/list.do"})
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
		edusatSeasonService.list(model);
		
		return "/ncms/edusat_season/list";
	}
	
	/**************  Information  ************************************
	* @Title : 등록
	* @method : write
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat_season/write.do","/*/ncms/edusat_season/write.do"})
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
		
		String es_num = Func.nvl( request.getParameter("es_num") ).trim();
		if("".equals(es_num)){
			edusatSeasonService.write(model);
		}else{
			edusatSeasonService.modify(model);
		}
		
		return "/ncms/edusat_season/write";
	}
	
	/**************  Information  ************************************
	* @Title : 등록처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat_season/writeOk.do","/*/ncms/edusat_season/writeOk.do"})
	public String writeOk(HttpServletRequest request, Model model, @ModelAttribute("edusatseasondto")EdusatSeasonDTO edusatseasondto) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("edusatseasondto", edusatseasondto);
		
		String es_num = Func.nvl( request.getParameter("es_num") ).trim();
		if("".equals(es_num)){
			edusatSeasonService.writeOk(model);
		}else{
			edusatSeasonService.modifyOk(model);
		}
		
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){
			prepage = "/ncms/edusat_season/list.do";
			
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			if(!"".equals(builder_dir)){
				prepage = "/"+builder_dir+prepage;
			}
		}
		
		return "redirect:"+prepage;
	}
	
	/**************  Information  ************************************
	* @Title : 삭제처리
	* @method : deleteOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat_season/deleteOk.do","/*/ncms/edusat_season/deleteOk.do"})
	public String deleteOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);

		edusatSeasonService.deleteOk(model);
		
		
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){
			prepage = "/ncms/edusat_season/list.do";
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			if(!"".equals(builder_dir)){
				prepage = "/"+builder_dir+prepage;
			}
		}
		
		return "redirect:"+prepage;
	}
	
	/**************  Information  ************************************
	* @Title : 상태변경
	* @method : levelOk
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat_season/levelOk.do","/*/ncms/edusat_season/levelOk.do"})
	public String levelOk(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);

		edusatSeasonService.levelOk(model);
		
		
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){
			prepage = "/ncms/edusat_season/list.do";
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			if(!"".equals(builder_dir)){
				prepage = "/"+builder_dir+prepage;
			}
		}
		
		return "redirect:"+prepage;
	}
	
	
	/**************  Information  ************************************
	* @Title : 전체목록
	* @method : ajaxListAll
	/****************************************************************/
	@RequestMapping(value={"/ncms/edusat_season/ajaxListAll.do","/*/ncms/edusat_season/ajaxListAll.do"})
	public String ajaxListAll(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("request", request);

		edusatSeasonService.ajaxlistAll(model);
		
		return "/ncms/edusat_season/ajaxListAll";
	}
}
