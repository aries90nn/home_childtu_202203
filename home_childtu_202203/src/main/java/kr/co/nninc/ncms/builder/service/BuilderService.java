package kr.co.nninc.ncms.builder.service;

import org.springframework.ui.Model;

/**
 * 빌더 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2019.04.23
 * @version 1.0
 */
public interface BuilderService {

	public void write(Model model) throws Exception ;
	
	public void list(Model model) throws Exception;
	
	public void modify(Model model) throws Exception;
	
	public String writeOk(Model model) throws Exception;
	
	public String modifyOk(Model model) throws Exception;
	
	public void deleteOk(Model model) throws Exception;
	
	public void levelOk(Model model) throws Exception;
	
	public void listMove(Model model) throws Exception;
	
	public void listMoveOk(Model model) throws Exception;
}
