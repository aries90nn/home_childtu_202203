package kr.co.nninc.ncms.mail_auth.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

/**
 * 메일인증 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2020.04.07
 * @version 1.0
 */
public interface MailAuthService {

	public Map<String, String> send(Model model, HttpServletRequest request) throws Exception;

	public Map<String, String> end(Model model, HttpServletRequest request) throws Exception;

}