package kr.co.nninc.ncms.poll.service;

import org.springframework.ui.Model;

/**
 * 설문조사를 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.11.01
 * @version 1.0
 */
public interface PollService {

	public void modify(Model model) throws Exception ;

	public void modifyOk(Model model) throws Exception;
	
	public void write(Model model) throws Exception ;

	public void writeOk(Model model) throws Exception;

	public void list(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;

	public void conf(Model model) throws Exception;

	public void confOk(Model model) throws Exception;
	
	public void resultList(Model model) throws Exception;
}
