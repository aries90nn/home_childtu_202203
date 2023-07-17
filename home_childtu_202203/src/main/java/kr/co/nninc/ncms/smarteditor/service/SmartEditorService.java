package kr.co.nninc.ncms.smarteditor.service;

import org.springframework.ui.Model;

/**
 * 스마트에디터 파일업로드 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.11.11
 * @version 1.0
 */
public interface SmartEditorService {
	
	public void fileUploaderHtml5(Model model) throws Exception ;
	
	public String fileUploader(Model model) throws Exception ;
}