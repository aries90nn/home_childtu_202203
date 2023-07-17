package kr.co.nninc.ncms.cms.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

/**
 * cms 사용자 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.11.28
 * @version 1.0
 */
public interface UserCmsService {

	public void top(Model model, HttpServletRequest request) throws Exception;

	public void left(Model model, HttpServletRequest request) throws Exception;

	public void foot(Model model, HttpServletRequest request) throws Exception;
}
