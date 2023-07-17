package kr.co.nninc.ncms.stats.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("menucounterdto")
public class MenuCounterDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 4340837716270682051L;

	private String idx;
	private String page_id;
	private String vDD;
	private String vHH;
	private String counter;
	private String vOut;
	private String page_codeno;
	private String mobile;
	private String site_dir;
	
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getPage_id() {
		return page_id;
	}
	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}
	public String getvDD() {
		return vDD;
	}
	public void setvDD(String vDD) {
		this.vDD = vDD;
	}
	public String getvHH() {
		return vHH;
	}
	public void setvHH(String vHH) {
		this.vHH = vHH;
	}
	public String getCounter() {
		return counter;
	}
	public void setCounter(String counter) {
		this.counter = counter;
	}
	public String getvOut() {
		return vOut;
	}
	public void setvOut(String vOut) {
		this.vOut = vOut;
	}
	public String getPage_codeno() {
		return page_codeno;
	}
	public void setPage_codeno(String page_codeno) {
		this.page_codeno = page_codeno;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSite_dir() {
		return site_dir;
	}
	public void setSite_dir(String site_dir) {
		this.site_dir = site_dir;
	}
	
}
