package kr.co.nninc.ncms.edusat.service;

import org.springframework.ui.Model;

/**
 * 온라인수강신청 기수관리를 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2019.09.09
 * @version 1.0
 */
public interface EdusatSeasonSerivce {
	public void list(Model model) throws Exception;
	
	public void write(Model model) throws Exception;
	
	public void writeOk(Model model) throws Exception;
	
	public void modify(Model model) throws Exception;
	
	public void modifyOk(Model model) throws Exception;
	
	public void listMove(Model model) throws Exception;
	
	public void listMoveOk(Model model) throws Exception;
	
	public void deleteOk(Model model) throws Exception;
	
	public void levelOk(Model model) throws Exception;
	
	public void ajaxlistAll(Model model) throws Exception;
}
