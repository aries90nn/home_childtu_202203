package kr.co.nninc.ncms.homepage_log_work.service;

import org.springframework.ui.Model;

/**
 * 로그 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.10.18
 * @version 1.0
 */
public interface HomepageLogWorkService {

	public void list(Model model) throws Exception ;
}
