package kr.co.nninc.ncms.ebook_page.service;

import org.springframework.ui.Model;

/**
 * 이북페이지를 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.09.27
 * @version 1.0
 */
public interface EbookPageService {

	public void write(Model model) throws Exception;

	public String writeOk(Model model) throws Exception;

	public String modifyOk(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void delete2Ok(Model model) throws Exception;

	public void listMove(Model model) throws Exception;

	public void listMoveOk(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;

	public void nfuUpload(Model model) throws Exception;

	public void nfuNormalUpload(Model model) throws Exception;
}
