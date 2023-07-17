package kr.co.nninc.ncms.buseo.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("buseodto")
public class BuseoDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = 4602524797239170790L;

	private String ct_idx;
	private String ct_code;
	private String ct_name;
	private String ct_ref;
	private String ct_depth;
	private String ct_chk;
	private String ct_wdate;
	private String ct_codeno;
	private String ct_memo;
	private String site_dir;

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

	public String getCt_memo() {
		return ct_memo;
	}

	public void setCt_memo(String ct_memo) {
		this.ct_memo = ct_memo;
	}

	public String getSite_dir() {
		return site_dir;
	}

	public void setSite_dir(String site_dir) {
		this.site_dir = site_dir;
	}
}