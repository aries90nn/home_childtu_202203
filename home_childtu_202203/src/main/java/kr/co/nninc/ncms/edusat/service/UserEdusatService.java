package kr.co.nninc.ncms.edusat.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

/**
 * 온라인강좌 서비스 인터페이스 클래스(사용자용)
 * @author 나눔
 * @since 2017.11.13
 * @version 1.0
 */
public interface UserEdusatService {
	
	public void list(Model model) throws Exception;
	
	public void view(Model model) throws Exception;
	
	public String regist(Model model) throws Exception;
	
	public String registOk(Model model) throws Exception;
	
	public String cancel(Model model) throws Exception;
	
	public String cancelOk(Model model) throws Exception;
	
	public String user(Model model) throws Exception;
	
	public void user_chk(Model model) throws Exception;
	
	public void myinfo(Model model) throws Exception;
	
	public String requestCheck(HttpServletRequest request, HashMap<String,String> edusatdto) throws Exception;
	
	public void nfuUpload(Model model) throws Exception;
	public void nfuNormalUpload(Model model) throws Exception;
	public String imgDeleteOk(Model model) throws Exception;
	public String modify(Model model) throws Exception;
	public String modifyOk(Model model) throws Exception;
	public void viewRequest(Model model) throws Exception;

}
