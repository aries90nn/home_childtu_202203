package kr.co.nninc.ncms.board_command.service;

import org.springframework.ui.Model;

public interface BoardCommandService {

	public String writeOk(Model model) throws Exception;
	
	public String deleteOk(Model model) throws Exception;
}
