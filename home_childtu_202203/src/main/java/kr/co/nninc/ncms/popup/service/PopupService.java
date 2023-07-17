package kr.co.nninc.ncms.popup.service;

import org.springframework.ui.Model;

public interface PopupService {
	
	public void write(Model model) throws Exception ;

	public String writeOk(Model model) throws Exception;

	public void list(Model model) throws Exception;

	public void view(Model model) throws Exception;

	public void modify(Model model) throws Exception;

	public String modifyOk(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;
}
