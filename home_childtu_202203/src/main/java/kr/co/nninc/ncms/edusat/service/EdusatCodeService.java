package kr.co.nninc.ncms.edusat.service;

import org.springframework.ui.Model;


/**
 * 강좌분류를 별도 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2019.09.17
 * @version 1.0
 */
public interface EdusatCodeService {
	
	public void write(Model model) throws Exception;

	public void writeOk(Model model) throws Exception;

	public void modifyOk(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void listMoveOk(Model model) throws Exception;

	public void move(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;
}
