package kr.co.nninc.ncms.buseo.service;

import org.springframework.ui.Model;

/**
 * 부서를 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2021.04.19
 * @version 1.0
 */
public interface UserBuseoService {
	
	public String list(Model model) throws Exception ;
	
	public void search(Model model) throws Exception ;
	
}
