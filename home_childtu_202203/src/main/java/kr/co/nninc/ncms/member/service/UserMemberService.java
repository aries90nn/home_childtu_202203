package kr.co.nninc.ncms.member.service;

import org.springframework.ui.Model;

/**
 * 회원정보를 관리하기 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 20120.03.02
 * @version 1.0
 */
public interface UserMemberService {
	
	public String regist(Model model) throws Exception ;

	public String registOk(Model model) throws Exception;
	
	public String modify(Model model) throws Exception ;

	public String modifyOk(Model model) throws Exception;
	
	public String pwdChangeOk(String m_id, String m_pwd) throws Exception;

	public String secedeOk(Model model) throws Exception;
	
	public int idCheck(Model model) throws Exception;
	
	public String idFind(Model model) throws Exception;
	
	public String pwdFind(Model model) throws Exception;

	public int EmailCheck(Model model) throws Exception;
	
}
