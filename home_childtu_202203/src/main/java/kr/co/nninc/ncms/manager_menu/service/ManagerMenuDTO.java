package kr.co.nninc.ncms.manager_menu.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;



/**
 * @Class Name : MenuDTO.java
 * @Description : 메뉴 필드
 * @author 나눔
 * @since 2017.09.12
 * @version 1.0
 */
@Repository("managermenudto")
public class ManagerMenuDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = -7538983032977914265L;

	private String ct_idx;
	private String ct_code;
	private String ct_name;
	private String ct_ref;
	private String ct_depth;
	private String ct_chk;
	private String ct_wdate;
	private String ct_codeno;
	private String ct_url;
	private String ct_icon;
	private String ct_folder;
	
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
	public String getCt_url() {
		return ct_url;
	}
	public void setCt_url(String ct_url) {
		this.ct_url = ct_url;
	}
	public String getCt_icon() {
		return ct_icon;
	}
	public void setCt_icon(String ct_icon) {
		this.ct_icon = ct_icon;
	}
	public String getCt_folder() {
		return ct_folder;
	}
	public void setCt_folder(String ct_folder) {
		this.ct_folder = ct_folder;
	}
	
}
