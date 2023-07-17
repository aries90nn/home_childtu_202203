package kr.co.nninc.ncms.board_command.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("boardcommanddto")
public class BoardCommandDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = -8957722691831709691L;

	private String c_num;
	private String a_num;
	private String c_bnum;
	private String c_id;
	private String c_pwd;
	private String c_name;
	private String c_content;
	private String c_regdate;
	
	public String getC_num() {
		return c_num;
	}
	public void setC_num(String c_num) {
		this.c_num = c_num;
	}
	public String getA_num() {
		return a_num;
	}
	public void setA_num(String a_num) {
		this.a_num = a_num;
	}
	public String getC_bnum() {
		return c_bnum;
	}
	public void setC_bnum(String c_bnum) {
		this.c_bnum = c_bnum;
	}
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public String getC_pwd() {
		return c_pwd;
	}
	public void setC_pwd(String c_pwd) {
		this.c_pwd = c_pwd;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public String getC_regdate() {
		return c_regdate;
	}
	public void setC_regdate(String c_regdate) {
		this.c_regdate = c_regdate;
	}

	
}
