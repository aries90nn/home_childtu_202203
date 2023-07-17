package kr.co.nninc.ncms.edusat_blacklist.service;

import org.springframework.ui.Model;

/**
 * 신청자경고를 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2018.01.04
 * @version 1.0
 */
public interface EdusatBlackListService {

	public void list(Model model) throws Exception;
	
	public String write(Model model) throws Exception;
	
	public String writeOk(Model model) throws Exception;
	
	public String modifyOk(Model model) throws Exception;
	
	public void deleteOk(Model model) throws Exception;
}
