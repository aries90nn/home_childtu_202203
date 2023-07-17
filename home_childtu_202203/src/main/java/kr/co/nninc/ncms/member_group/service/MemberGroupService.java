package kr.co.nninc.ncms.member_group.service;

import org.springframework.ui.Model;

/**
 * 회원그룹을 관리하기 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.12
 * @version 1.0
 */
public interface MemberGroupService {

	public void write(Model model) throws Exception ;

	public void writeOk(Model model) throws Exception;

	public void modifyOk(Model model) throws Exception;
	
	public void awrite(Model model) throws Exception ;
	
	public void awriteOk(Model model) throws Exception ;

	public void deleteOk(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;

	public void listMove(Model model) throws Exception;

	public void listMoveOk(Model model) throws Exception;
}
