package kr.co.nninc.ncms.ebookpdf.service;

import org.springframework.ui.Model;

public interface EbookpdfService {

	public void list(Model model) throws Exception;
	
	public void write(Model model) throws Exception ;
	
	public String writeOk(Model model) throws Exception;
	
	public void modify(Model model) throws Exception ;
	
	public String modifyOk(Model model) throws Exception;
	
	public void deleteOk(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;

	public void listMove(Model model) throws Exception;

	public void listMoveOk(Model model) throws Exception;
	
	public String down(Model model) throws Exception;
}
