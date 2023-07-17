package kr.co.nninc.ncms.manager_sql.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("managersqldto")
public class ManagerSqlDTO extends ExtendDTO implements Serializable{
	private static final long serialVersionUID = -2871357479808072254L;
	
	private String ms_num;
	private String ms_get;
	private String ms_post;
	
	public String getMs_num() {
		return ms_num;
	}
	public void setMs_num(String ms_num) {
		this.ms_num = ms_num;
	}
	public String getMs_get() {
		return ms_get;
	}
	public void setMs_get(String ms_get) {
		this.ms_get = ms_get;
	}
	public String getMs_post() {
		return ms_post;
	}
	public void setMs_post(String ms_post) {
		this.ms_post = ms_post;
	}
	

}
