package kr.co.nninc.ncms.board.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

/**
 * 게시물 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2019.01.14
 * @version 1.1
 */
public interface BoardService {
	
	public String setConfig(Model model) throws Exception;

	public String list(Model model) throws Exception;
	
	public String view(Model model) throws Exception;
	
	public String write(Model model) throws Exception;
	
	public String modify(Model model) throws Exception;
	
	public String reply(Model model) throws Exception;
	
	public String writeOk(Model model) throws Exception;
	
	public String modifyOk(Model model) throws Exception;
	
	public String replyOk(Model model) throws Exception;
	
	public String deleteOk(Model model) throws Exception;

	public void pwd(Model model) throws Exception;

	public String pwdOk(Model model) throws Exception;

	public String a_pwdOk(Model model) throws Exception;
	
	public String login(Model model) throws Exception;
	
	public String loginOk(Model model) throws Exception;

	public String down(Model model) throws Exception;
	
	public String imgDeleteOk(Model model) throws Exception;

	public void nfuUpload(Model model) throws Exception;

	public void nfuNormalUpload(Model model) throws Exception;
	
	public String checkToken(HttpServletRequest request) throws IOException;
	
	public void procSkinMethod(Model model, String method_name) throws Exception;
	
	public String skinProcPage(Model model) throws Exception;
	
	public String loanList(Model model) throws Exception;

	public String levelOk(Model model) throws Exception;
}
