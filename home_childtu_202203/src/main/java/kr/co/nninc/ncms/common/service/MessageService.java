package kr.co.nninc.ncms.common.service;

import org.springframework.ui.Model;

/**
 * 공통으로 사용해해야 하는 서비스를 정의하기 위한 서비스 인터페이스
 * @author 나눔
 * @since 2017.08.29
 * @version 1.0
 */

public interface MessageService {

	String redirectMsg(Model model, String msg, String url) throws Exception;
	
	String backMsg(Model model, String msg) throws Exception;
	
	String closeRedirect(Model model, String msg, String url) throws Exception;
	
	String closeMsg(Model model, String msg) throws Exception;
	
	String confirmMsg(Model model, String msg, String url, String url2) throws Exception;
	
}
