package kr.co.nninc.ncms.edusat.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.View;

/**
 * 온라인수강신청을 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2017.11.07
 * @version 1.0
 */
public interface EdusatService {
	
	public void config(Model model, int ec_idx) throws Exception;
	
	public void list(Model model) throws Exception;
	
	public void write(Model model) throws Exception;
	
	public void writeOk(Model model) throws Exception;
	
	public void modify(Model model) throws Exception;
	
	public void modifyOk(Model model) throws Exception;
	
	public void move(Model model) throws Exception;
	
	public void listMove(Model model) throws Exception;
	
	public void listMoveOk(Model model) throws Exception;
	
	public void deleteOk(Model model) throws Exception;
	
	public void levelOk(Model model) throws Exception;
	
	public String winOk(Model model) throws Exception;
	
	public void viewRequest(Model model) throws Exception;
	
	public void modifyRequest(Model model) throws Exception;
	
	public void deleteRequest(Model model) throws Exception;
	
	public void edusatConf(Model model) throws Exception;
	
	public void edusatConfOk(Model model) throws Exception;
	
	public void edusatLibConf(Model model) throws Exception;
	
	public void edusatLibConfOk(Model model) throws Exception;
	
	public void mainList(Model model) throws Exception;
	
	public View excel(Model model) throws Exception;
	
	public void requestList(Model model) throws Exception;
	
	public int getReqCount(String edu_idx) throws Exception;
	
	public int getReqCount(String edu_idx, String edu_ptcp_yn) throws Exception;
	
	public void levelOk2(Model model) throws Exception;
}
