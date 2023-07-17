package kr.co.nninc.ncms.homepage_log_work.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("logworkdto")
public class HomepageLogWorkDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = -4934544841683989131L;
	
	private String hl_idx;
	private String hl_type;
	private String hl_job;
	private String hl_id;
	private String hl_name;
	private String hl_ci;
	private String hl_ip;
	private String hl_ymd;
	private String hl_regdate;
	private String site_dir;
	
	public String getHl_idx() {
		return hl_idx;
	}
	public void setHl_idx(String hl_idx) {
		this.hl_idx = hl_idx;
	}
	public String getHl_type() {
		return hl_type;
	}
	public void setHl_type(String hl_type) {
		this.hl_type = hl_type;
	}
	public String getHl_job() {
		return hl_job;
	}
	public void setHl_job(String hl_job) {
		this.hl_job = hl_job;
	}
	public String getHl_id() {
		return hl_id;
	}
	public void setHl_id(String hl_id) {
		this.hl_id = hl_id;
	}
	public String getHl_name() {
		return hl_name;
	}
	public void setHl_name(String hl_name) {
		this.hl_name = hl_name;
	}
	public String getHl_ci() {
		return hl_ci;
	}
	public void setHl_ci(String hl_ci) {
		this.hl_ci = hl_ci;
	}
	public String getHl_ip() {
		return hl_ip;
	}
	public void setHl_ip(String hl_ip) {
		this.hl_ip = hl_ip;
	}
	public String getHl_ymd() {
		return hl_ymd;
	}
	public void setHl_ymd(String hl_ymd) {
		this.hl_ymd = hl_ymd;
	}
	public String getHl_regdate() {
		return hl_regdate;
	}
	public void setHl_regdate(String hl_regdate) {
		this.hl_regdate = hl_regdate;
	}
	public String getSite_dir() {
		return site_dir;
	}
	public void setSite_dir(String site_dir) {
		this.site_dir = site_dir;
	}
	
}
