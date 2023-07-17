package kr.co.nninc.ncms.builder.service;

import java.util.HashMap;

import org.springframework.ui.Model;

/**
 * 기본정보 설정을 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2019.04.30
 * @version 1.0
 */
public interface BuilderInfoService {

	public void modify(Model model) throws Exception;

	public String modifyOk(Model model) throws Exception;

	public HashMap<String, Object> siteConfigOk(Model model) throws Exception;
}
