package kr.co.nninc.ncms.board_delete.service;

import org.springframework.ui.Model;

/**
 * 게시물 삭제를 관리하기 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.11.23
 * @version 1.0
 */
public interface BoardDeleteService {

	public void list(Model model) throws Exception;

	public String deleteOk(Model model) throws Exception;

	public String restoreOk(Model model) throws Exception;

	public String pageInfo(Model model) throws Exception;

	
}
