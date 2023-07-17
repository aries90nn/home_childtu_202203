package kr.co.nninc.ncms.member.service;

import org.springframework.ui.Model;

/**
 * 회원정보를 관리하기 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.12
 * @version 1.0
 */
public interface MemberService {

	public void list(Model model) throws Exception;
	
	public String write(Model model) throws Exception ;

	public String writeOk(Model model) throws Exception;
	
	public String modify(Model model) throws Exception ;

	public String modifyOk(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;
}
