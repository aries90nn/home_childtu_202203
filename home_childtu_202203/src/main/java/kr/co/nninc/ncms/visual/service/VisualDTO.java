package kr.co.nninc.ncms.visual.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("visualdto")
public class VisualDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = 2932464483083075572L;

	private String v_num;
	private String info_url;
	private String v_code;
	private String v_img;
	private String v_url;
	private String v_chk;
	private String v_sdate;
	private String v_edate;
	private String v_subject;
	private String v_memo;
	private String v_wdate;
	private String v_mdate;
	private String unlimited;
	
	public String getV_num() {
		return v_num;
	}
	public void setV_num(String v_num) {
		this.v_num = v_num;
	}
	public String getInfo_url() {
		return info_url;
	}
	public void setInfo_url(String info_url) {
		this.info_url = info_url;
	}
	public String getV_code() {
		return v_code;
	}
	public void setV_code(String v_code) {
		this.v_code = v_code;
	}
	public String getV_img() {
		return v_img;
	}
	public void setV_img(String v_img) {
		this.v_img = v_img;
	}
	public String getV_url() {
		return v_url;
	}
	public void setV_url(String v_url) {
		this.v_url = v_url;
	}
	public String getV_chk() {
		return v_chk;
	}
	public void setV_chk(String v_chk) {
		this.v_chk = v_chk;
	}
	public String getV_sdate() {
		return v_sdate;
	}
	public void setV_sdate(String v_sdate) {
		this.v_sdate = v_sdate;
	}
	public String getV_edate() {
		return v_edate;
	}
	public void setV_edate(String v_edate) {
		this.v_edate = v_edate;
	}
	public String getV_subject() {
		return v_subject;
	}
	public void setV_subject(String v_subject) {
		this.v_subject = v_subject;
	}
	public String getV_memo() {
		return v_memo;
	}
	public void setV_memo(String v_memo) {
		this.v_memo = v_memo;
	}
	public String getV_wdate() {
		return v_wdate;
	}
	public void setV_wdate(String v_wdate) {
		this.v_wdate = v_wdate;
	}
	public String getV_mdate() {
		return v_mdate;
	}
	public void setV_mdate(String v_mdate) {
		this.v_mdate = v_mdate;
	}
	public String getUnlimited() {
		return unlimited;
	}
	public void setUnlimited(String unlimited) {
		this.unlimited = unlimited;
	}
	
}
