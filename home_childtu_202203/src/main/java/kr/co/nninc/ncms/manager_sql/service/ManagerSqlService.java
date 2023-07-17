package kr.co.nninc.ncms.manager_sql.service;

import org.springframework.ui.Model;

/**
 * 키워드관리를 하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.10.17
 * @version 1.0
 */
public interface ManagerSqlService {

	public void write(Model model) throws Exception;

	public void writeOk(Model model) throws Exception;

	public void modifyOk(Model model) throws Exception;
}
