package kr.co.nninc.ncms.member_conf.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.manager_menu.service.MenuService;
import kr.co.nninc.ncms.member_conf.service.MemberConfDTO;
import kr.co.nninc.ncms.member_conf.service.MemberConfService;

/**
 * 회원환경설정을 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.12
 * @version 1.0
 */
@Controller
public class MemberConfController {

	/** MemberConfService */
	@Resource(name = "memberconfService")
	private MemberConfService memberconfService;
	
	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/**************  Information  ****************************************
	* @Title : 회원환경설정 등록
	* @method : write
	/****************************************************************/
	@RequestMapping(value = "/ncms/member_conf/write.do")
	public String write(HttpServletRequest request, @ModelAttribute("memberconfdto") MemberConfDTO memberconfdto, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("memberconfdto", memberconfdto);
		model.addAttribute("mng_left_cd", CommonConfig.get("member_top"));
		
		//관리자 탑메뉴 리스트
		menuService.mngTopList(model);
		// 관리자 레프트메뉴 리스트
		menuService.mngLeftList(model);
		
		memberconfService.write(model);
		return "/ncms/member_conf/write";
	}
	
	/**************  Information  ****************************************
	* @Title :회원환경설정 등록/수정 처리
	* @method : writeOk
	/****************************************************************/
	@RequestMapping(value = "/ncms/member_conf/writeOk.do")
	public String writeOk(HttpServletRequest request, @ModelAttribute("memberconfdto") MemberConfDTO memberconfdto, Model model) throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("memberconfdto", memberconfdto);
		
		//허용 url
		if(Func.urlChk(model,"write.do").equals("N")) {
			return "redirect:/ncms/error.do";
		}else {
			//수정처리
			String mc_idx = Func.nvl(request.getParameter("mc_idx"));
			if(!"".equals(mc_idx)) {
				memberconfService.modifyOk(model);
			}else { //등록처리
				memberconfService.writeOk(model);
			}
		}
		return "redirect:/ncms/member_conf/write.do";
	}
	
}
