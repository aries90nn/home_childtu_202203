package kr.co.nninc.ncms.board_config.service;

import org.springframework.ui.Model;

/**
 * 게시판 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.10.10
 * @version 1.0
 */
public interface BoardConfigService {

	public void list(Model model) throws Exception;

	public void write(Model model) throws Exception;
	
	public void modify(Model model) throws Exception;

	public String writeOk(Model model) throws Exception;

	public String modifyOk(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;
	
	public void move(Model model) throws Exception;
	
	public void moveAll(Model model) throws Exception;
	
	public void listMove(Model model) throws Exception;
	
	public void listMoveOk(Model model) throws Exception;

	public void ncmsIndex(Model model) throws Exception;

	public void mainList(Model model) throws Exception;

	public void memberSearch(Model model) throws Exception;

	public String pageInfo(Model model) throws Exception;

	public void viewCreate() throws Exception;
	
	public void createTable(String a_tablename_str) throws Exception;
}
