package kr.co.nninc.ncms.manager_menu_access.service;

import java.io.Serializable;

import kr.co.nninc.ncms.common.service.ExtendDTO;

public class ManagerMenuAccessDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = -2588138193154152415L;

	private String num;
	private String g_num;
	private String ct_idx;
	private String ct_codeno;
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getG_num() {
		return g_num;
	}
	public void setG_num(String g_num) {
		this.g_num = g_num;
	}
	public String getCt_idx() {
		return ct_idx;
	}
	public void setCt_idx(String ct_idx) {
		this.ct_idx = ct_idx;
	}
	public String getCt_codeno() {
		return ct_codeno;
	}
	public void setCt_codeno(String ct_codeno) {
		this.ct_codeno = ct_codeno;
	}
	
	
}
