package kr.co.nninc.ncms.board.service;

import org.springframework.ui.Model;

public interface SkinService {

	public void list(Model model) throws Exception;
	public void view(Model model) throws Exception;
	public void write(Model model) throws Exception;
	public void writeOk(Model model) throws Exception;
	public void modify(Model model) throws Exception;
	public void modifyOk(Model model) throws Exception;
	public void deleteOk(Model model) throws Exception;
}
