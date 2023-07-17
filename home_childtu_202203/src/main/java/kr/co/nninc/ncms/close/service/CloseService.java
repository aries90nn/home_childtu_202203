package kr.co.nninc.ncms.close.service;

import org.springframework.ui.Model;

public interface CloseService {

	public void close(Model model)throws Exception;
	
	public void closeOk(Model model)throws Exception;
	
}
