package kr.co.nninc.ncms.close.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("closedto")
public class CloseDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 2617272422710656684L;
	private String cl_idx;
	private String cl_category;
	private String cl_name;
	private String cl_date;
	public String getCl_idx() {
		return cl_idx;
	}
	public void setCl_idx(String cl_idx) {
		this.cl_idx = cl_idx;
	}
	public String getCl_category() {
		return cl_category;
	}
	public void setCl_category(String cl_category) {
		this.cl_category = cl_category;
	}
	public String getCl_name() {
		return cl_name;
	}
	public void setCl_name(String cl_name) {
		this.cl_name = cl_name;
	}
	public String getCl_date() {
		return cl_date;
	}
	public void setCl_date(String cl_date) {
		this.cl_date = cl_date;
	}
	
}
