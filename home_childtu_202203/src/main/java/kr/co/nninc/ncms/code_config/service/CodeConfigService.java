package kr.co.nninc.ncms.code_config.service;

import org.springframework.ui.Model;

/**
 * 팝업존을 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.10.02
 * @version 1.0
 */
public interface CodeConfigService {

	public void write(Model model) throws Exception;

	public void writeOk(Model model) throws Exception;

	public void modifyOk(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void listMoveOk(Model model) throws Exception;

	public void move(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;
}
