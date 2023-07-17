package kr.co.nninc.ncms.ebookpdf.service;

import org.springframework.ui.Model;

public interface UserEbookpdfService {

	public void list(Model model) throws Exception;
	
	public void viewer(Model model) throws Exception;
}
