package kr.co.nninc.ncms.code_config.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("codeconfigdto")
public class CodeConfigDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 9016819145469466190L;

	private String ct_idx;
	private String ct_code;
	private String ct_name;
	private String ct_ref;
	private String ct_depth;
	private String ct_chk;
	private String ct_wdate;
	private String ct_codeno;
	private String ct_codeno2;
	private String ct_edusat1;
	private String ct_edusat2;
	private String ct_g_num;
	private String ct_site_dir;
	
	public String getCt_idx() {
		return ct_idx;
	}
	public void setCt_idx(String ct_idx) {
		this.ct_idx = ct_idx;
	}
	public String getCt_code() {
		return ct_code;
	}
	public void setCt_code(String ct_code) {
		this.ct_code = ct_code;
	}
	public String getCt_name() {
		return ct_name;
	}
	public void setCt_name(String ct_name) {
		this.ct_name = ct_name;
	}
	public String getCt_ref() {
		return ct_ref;
	}
	public void setCt_ref(String ct_ref) {
		this.ct_ref = ct_ref;
	}
	public String getCt_depth() {
		return ct_depth;
	}
	public void setCt_depth(String ct_depth) {
		this.ct_depth = ct_depth;
	}
	public String getCt_chk() {
		return ct_chk;
	}
	public void setCt_chk(String ct_chk) {
		this.ct_chk = ct_chk;
	}
	public String getCt_wdate() {
		return ct_wdate;
	}
	public void setCt_wdate(String ct_wdate) {
		this.ct_wdate = ct_wdate;
	}
	public String getCt_codeno() {
		return ct_codeno;
	}
	public void setCt_codeno(String ct_codeno) {
		this.ct_codeno = ct_codeno;
	}
	public String getCt_codeno2() {
		return ct_codeno2;
	}
	public void setCt_codeno2(String ct_codeno2) {
		this.ct_codeno2 = ct_codeno2;
	}
	public String getCt_edusat1() {
		return ct_edusat1;
	}
	public void setCt_edusat1(String ct_edusat1) {
		this.ct_edusat1 = ct_edusat1;
	}
	public String getCt_edusat2() {
		return ct_edusat2;
	}
	public void setCt_edusat2(String ct_edusat2) {
		this.ct_edusat2 = ct_edusat2;
	}
	public String getCt_g_num() {
		return ct_g_num;
	}
	public void setCt_g_num(String ct_g_num) {
		this.ct_g_num = ct_g_num;
	}
	public String getCt_site_dir() {
		return ct_site_dir;
	}
	public void setCt_site_dir(String ct_site_dir) {
		this.ct_site_dir = ct_site_dir;
	}
	
}
