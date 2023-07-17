package kr.co.nninc.ncms.visual.service;

import org.springframework.ui.Model;

/**
 * 메인비쥬얼을 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2018.05.28
 * @version 1.0
 */
public interface VisualService {
	
	public void list(Model model) throws Exception;
	
	public void write(Model model) throws Exception ;
	
	public void modify(Model model) throws Exception;
	
	public String writeOk(Model model) throws Exception;
	
	public String modifyOk(Model model) throws Exception;
	
	public void deleteOk(Model model) throws Exception;
	
	public void listMove(Model model) throws Exception;
	
	public void listMoveOk(Model model) throws Exception;
	
	public void levelOk(Model model) throws Exception;
	
	public void down(Model model) throws Exception;
}
