package kr.co.nninc.ncms.manager_ip.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("manageripdto")
public class ManagerIPDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = -5762655377782462567L;

	private String mi_num;
	private String mi_ip;
	private String mi_memo;
	private String mi_chk;
	private String mi_regdate;
	private String mi_modidate;
	private String mi_sdate;
	private String mi_edate;
	private String site_dir;
	
	public String getMi_num() {
		return mi_num;
	}
	public void setMi_num(String mi_num) {
		this.mi_num = mi_num;
	}
	public String getMi_ip() {
		return mi_ip;
	}
	public void setMi_ip(String mi_ip) {
		this.mi_ip = mi_ip;
	}
	public String getMi_memo() {
		return mi_memo;
	}
	public void setMi_memo(String mi_memo) {
		this.mi_memo = mi_memo;
	}
	public String getMi_chk() {
		return mi_chk;
	}
	public void setMi_chk(String mi_chk) {
		this.mi_chk = mi_chk;
	}
	public String getMi_regdate() {
		return mi_regdate;
	}
	public void setMi_regdate(String mi_regdate) {
		this.mi_regdate = mi_regdate;
	}
	public String getMi_modidate() {
		return mi_modidate;
	}
	public void setMi_modidate(String mi_modidate) {
		this.mi_modidate = mi_modidate;
	}
	public String getMi_sdate() {
		return mi_sdate;
	}
	public void setMi_sdate(String mi_sdate) {
		this.mi_sdate = mi_sdate;
	}
	public String getMi_edate() {
		return mi_edate;
	}
	public void setMi_edate(String mi_edate) {
		this.mi_edate = mi_edate;
	}
	public String getSite_dir() {
		return site_dir;
	}
	public void setSite_dir(String site_dir) {
		this.site_dir = site_dir;
	}
	
	
}
