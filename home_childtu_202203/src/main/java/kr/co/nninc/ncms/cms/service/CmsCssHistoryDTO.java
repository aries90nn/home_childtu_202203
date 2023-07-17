package kr.co.nninc.ncms.cms.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("cmscsshistorydto")
public class CmsCssHistoryDTO extends ExtendDTO  implements Serializable{

	private static final long serialVersionUID = -4977694870649661902L;

	private String c_idx;
	private String ct_idx;
	private String c_content;
	private String c_wdate;
	private String m_id;
	
	
	public String getC_idx() {
		return c_idx;
	}
	public void setC_idx(String c_idx) {
		this.c_idx = c_idx;
	}
	public String getCt_idx() {
		return ct_idx;
	}
	public void setCt_idx(String ct_idx) {
		this.ct_idx = ct_idx;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public String getC_wdate() {
		return c_wdate;
	}
	public void setC_wdate(String c_wdate) {
		this.c_wdate = c_wdate;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	
	
}
