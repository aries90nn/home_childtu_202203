package kr.co.nninc.ncms.manager_menu.service;

import org.springframework.ui.Model;

/**
 * 관리자 메뉴를 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.09.12
 * @version 1.0
 */
public interface MenuService {

	public void mngTopList(Model model) throws Exception;
	
	public void mngLeftList(Model model) throws Exception;

	public boolean getMenuChk(String mct_codeno,String g_num) throws Exception;

	public String getNowCode(String url) throws Exception;

	public void write(Model model) throws Exception;

	public void writeOk(Model model) throws Exception;

	public void modifyOk(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void move(Model model) throws Exception;

	public void listMoveOk(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;
}
