package kr.co.nninc.ncms.ebook.service;

import org.springframework.ui.Model;

/**
 * 이북을 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.09.26
 * @version 1.0
 */
public interface EbookService {

	public void modify(Model model) throws Exception ;

	public void modifyOk(Model model) throws Exception;
	
	public void write(Model model) throws Exception ;

	public void writeOk(Model model) throws Exception;

	public void list(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;

	public void listMove(Model model) throws Exception;

	public void listMoveOk(Model model) throws Exception;

	public void move(Model model) throws Exception;

	public void conf(Model model) throws Exception;

	public void confOk(Model model) throws Exception;
}
