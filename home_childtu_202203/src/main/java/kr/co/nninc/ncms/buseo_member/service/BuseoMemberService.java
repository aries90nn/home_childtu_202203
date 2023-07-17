package kr.co.nninc.ncms.buseo_member.service;

import org.springframework.ui.Model;

/**
 * 조직원을 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2018.11.16
 * @version 1.0
 */
public interface BuseoMemberService {
	
	public String write(Model model) throws Exception ;
	
	public String writeOk(Model model) throws Exception;
	
	public String modifyOk(Model model) throws Exception;
	
	public String deleteOk(Model model) throws Exception;
	
	public void listMove(Model model) throws Exception;
	
	public String listMoveOk(Model model) throws Exception;
}
