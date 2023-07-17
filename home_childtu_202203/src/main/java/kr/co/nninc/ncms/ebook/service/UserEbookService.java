package kr.co.nninc.ncms.ebook.service;

import org.springframework.ui.Model;

/**
 * 이북을 관리하기 위한 서비스 인터페이스 클래스(사용자용)
 * @author 나눔
 * @since 2017.11.03
 * @version 1.0
 */
public interface UserEbookService {

	public String index(Model model) throws Exception;
	
	public void list(Model model) throws Exception;
}
