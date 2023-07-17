package kr.co.nninc.ncms.site_info.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.site_info.service.SiteConfigDTO;
import kr.co.nninc.ncms.site_info.service.SiteInfoService;

/**
 * 환경설정 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.09.27
 * @version 1.0
 */
@Controller
public class SiteInfoController {
	
	/** InfoService */
	@Resource(name = "siteInfoService")
	private SiteInfoService siteInfoService;
	
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;

	/**************  Information  ****************************************
	* @Title : 기본정보 설정
	* @method : write
	/****************************************************************/
	@RequestMapping(value = "/ncms/info/write.do")
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("mng_left_cd", CommonConfig.get("basic_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);

		siteInfoService.write(model);

		return "/ncms/info/write";
	}
	
	/**************  Information  ****************************************
	* @Title : 기본정보 설정
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/info/writeOk.do", method = RequestMethod.POST)
	public String writeOk(HttpServletRequest request, MultipartHttpServletRequest multi, @ModelAttribute("siteconfigdto") SiteConfigDTO infodto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("infodto", infodto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else {
			String rtn_url = "";
			String sc_idx = Func.nvl( request.getParameter("sc_idx") ).trim();
			if("".equals(sc_idx)) {
				rtn_url = siteInfoService.writeOk(model);
			}else {
				rtn_url = siteInfoService.modifyOk(model);
			}
			return rtn_url;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 관리자페이지 접근관리
	* @method : site_config_ok
	/****************************************************************/
	@RequestMapping(value = "/ncms/info/site_config_ok.do")
	@ResponseBody
	public HashMap<String, Object> site_config_ok(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);

		HashMap<String, Object> hm =siteInfoService.siteConfigOk(model);
		return hm;
	}
}
