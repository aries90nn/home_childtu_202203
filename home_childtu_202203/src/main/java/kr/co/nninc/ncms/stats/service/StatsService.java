package kr.co.nninc.ncms.stats.service;

import org.springframework.ui.Model;

/**
 * 통계를 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.10.10
 * @version 1.0
 */
public interface StatsService {
	
	public void hour(Model model) throws Exception;

	public void day(Model model) throws Exception;

	public void month(Model model) throws Exception;

	public void week(Model model) throws Exception;

	public void log(Model model) throws Exception;

	public void stat(Model model) throws Exception;

	public void list(Model model) throws Exception;

	public void ncmsIndex(Model model) throws Exception;

}
