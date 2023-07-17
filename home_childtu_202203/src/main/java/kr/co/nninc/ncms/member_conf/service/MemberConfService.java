package kr.co.nninc.ncms.member_conf.service;

import org.springframework.ui.Model;

/**
 * 회원환경설정을 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.10.12
 * @version 1.0
 */
public interface MemberConfService {

	public void write(Model model) throws Exception ;

	public void writeOk(Model model) throws Exception;

	public void modifyOk(Model model) throws Exception;
}
