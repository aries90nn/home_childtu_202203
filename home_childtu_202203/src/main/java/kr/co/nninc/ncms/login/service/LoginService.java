package kr.co.nninc.ncms.login.service;

import org.springframework.ui.Model;

public interface LoginService {

	public String loginOk(Model model) throws Exception;
	
	public String loginFailCheck(Model model) throws Exception;
	
	public void loginFailReset(String m_id) throws Exception;
	
	public String loginFailTime(String m_id) throws Exception;
	
	
}
