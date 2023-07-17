package kr.co.nninc.ncms.builder.web;

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

import kr.co.nninc.ncms.builder.service.BuilderInfoService;
import kr.co.nninc.ncms.builder.service.BuilderSiteDTO;
import kr.co.nninc.ncms.common.Func;

/**
 * 환경설정 관리를 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2019.04.30
 * @version 1.0
 */
@Controller
public class BuilderInfoController {

	/** BuilderInfoService */
	@Resource(name = "builderInfoService")
	private BuilderInfoService builderInfoService;
	
	
	/**************  Information  ****************************************
	* @Title : 기본정보 설정
	* @method : write
	/****************************************************************/
	@RequestMapping(value = "/*/ncms/builder_info/write.do")
	public String write(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
	
		builderInfoService.modify(model);

		return "/ncms/builder_info/write";
	}
	
	/**************  Information  ****************************************
	* @Title : 기본정보 설정
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/*/ncms/builder_info/writeOk.do", method = RequestMethod.POST)
	public String writeOk(HttpServletRequest request, MultipartHttpServletRequest multi, @ModelAttribute("buildersitedto") BuilderSiteDTO infodto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		model.addAttribute("infodto", infodto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) { 
			return "redirect:/ncms/error.do";
		}else {
			String rtn_url = builderInfoService.modifyOk(model);
			return rtn_url;
		}
	}
	
	/**************  Information  ****************************************
	* @Title : 관리자페이지 접근관리
	* @method : site_config_ok
	/****************************************************************/
	@RequestMapping(value = "/*/ncms/info/site_config_ok.do")
	@ResponseBody
	public HashMap<String, Object> site_config_ok(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		
		HashMap<String, Object> hm = builderInfoService.siteConfigOk(model);
		return hm;
	}
}
