package kr.co.nninc.ncms.mail_auth.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.nninc.ncms.mail_auth.service.MailAuthService;

/**
 * 메일인증 컨트롤러
 * 
 * @author 나눔
 * @since 2021.04.06
 * @version 1.0
 */
@Controller
public class MailAuthController {
	
	/** MailAuthService */
	@Resource(name = "mailAuthService")
	private MailAuthService mailAuthService;
	
	/** 메일 인증번호 전송 */
	@RequestMapping(value = {"/mail_auth/send.do","/*/mail_auth/send.do"})
	@ResponseBody
	public Map<String, String> send(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("request", request);
		
		return mailAuthService.send(model, request);
	}
	
	/** 메일 인증번호 확인 */
	@RequestMapping(value = {"/mail_auth/end.do","/*/mail_auth/end.do"})
	@ResponseBody
	public Map<String, String> end(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("request", request);
		
		return mailAuthService.end(model, request);
	}
}
