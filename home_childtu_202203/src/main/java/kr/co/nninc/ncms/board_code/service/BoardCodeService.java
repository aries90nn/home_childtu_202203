package kr.co.nninc.ncms.board_code.service;

import org.springframework.ui.Model;

/**
 * 게시판 분류를 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.10.20
 * @version 1.0
 */
public interface BoardCodeService {

	public void write(Model model) throws Exception ;

	public String writeOk(Model model) throws Exception;

	public String modifyOk(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;

	public void move(Model model) throws Exception;

	public void listMove(Model model) throws Exception;

	public void listMoveOk(Model model) throws Exception;
}
