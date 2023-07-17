package kr.co.nninc.ncms.manager_ip.service;

import org.springframework.ui.Model;

/**
 * IP를 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.10.17
 * @version 1.0
 */
public interface ManagerIPService {

	public void write(Model model) throws Exception;

	public void writeOk(Model model) throws Exception;

	public void modifyOk(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;
}
