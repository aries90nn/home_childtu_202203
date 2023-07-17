package kr.co.nninc.ncms.poll.service;

import org.springframework.ui.Model;

/**
 * 설문조사를 위한 서비스 인터페이스 클래스(사용자)
 * @author 나눔
 * @since 2017.11.02
 * @version 1.0
 */
public interface UserPollService {

	public void list(Model model) throws Exception;

	public String poll(Model model) throws Exception;

	public String pollOk(Model model) throws Exception;

	public void view(Model model) throws Exception;

	public String result(Model model) throws Exception;

	
}
