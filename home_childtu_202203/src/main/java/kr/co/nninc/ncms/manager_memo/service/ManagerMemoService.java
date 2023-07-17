package kr.co.nninc.ncms.manager_memo.service;

import org.springframework.ui.Model;

public interface ManagerMemoService {

	public void view(Model model) throws Exception;

	public void writeOk(Model model) throws Exception;

	public void modifyOk(Model model) throws Exception;
	
}
