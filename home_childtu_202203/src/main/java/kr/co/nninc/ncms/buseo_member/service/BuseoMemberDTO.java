package kr.co.nninc.ncms.buseo_member.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("buseomemberdto")
public class BuseoMemberDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = -8382044203771339748L;

	private String m_num;
	private String m_name;
	private String m_tel;
	private String m_fax;
	private String ct_idx;
	private String ct_codeno;
	private String m_work;
	private String m_code;
	private String m_wdate;
	private String m_mdate;

	public String getM_num() {
		return m_num;
	}

	public void setM_num(String m_num) {
		this.m_num = m_num;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_tel() {
		return m_tel;
	}

	public void setM_tel(String m_tel) {
		this.m_tel = m_tel;
	}

	public String getM_fax() {
		return m_fax;
	}

	public void setM_fax(String m_fax) {
		this.m_fax = m_fax;
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

	public String getM_work() {
		return m_work;
	}

	public void setM_work(String m_work) {
		this.m_work = m_work;
	}

	public String getM_code() {
		return m_code;
	}

	public void setM_code(String m_code) {
		this.m_code = m_code;
	}

	public String getM_wdate() {
		return m_wdate;
	}

	public void setM_wdate(String m_wdate) {
		this.m_wdate = m_wdate;
	}

	public String getM_mdate() {
		return m_mdate;
	}

	public void setM_mdate(String m_mdate) {
		this.m_mdate = m_mdate;
	}

}