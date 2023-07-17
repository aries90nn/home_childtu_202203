package kr.co.nninc.ncms.banner3.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("banner3dto")
public class Banner3DTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 3100554428784496377L;
	private String b_l_num;
	private String info_url;
	private String b_l_code;
	private String b_l_img;
	private String b_l_url;
	private String b_l_chk;
	private String b_l_sdate;
	private String b_l_edate;
	private String b_l_subject;
	private String b_l_memo;
	private String b_l_wdate;
	private String b_l_mdate;
	private String b_l_win;
	private String unlimited;
	private String site_dir;
	
	public String getB_l_num() {
		return b_l_num;
	}
	public void setB_l_num(String b_l_num) {
		this.b_l_num = b_l_num;
	}
	public String getInfo_url() {
		return info_url;
	}
	public void setInfo_url(String info_url) {
		this.info_url = info_url;
	}
	public String getB_l_code() {
		return b_l_code;
	}
	public void setB_l_code(String b_l_code) {
		this.b_l_code = b_l_code;
	}
	public String getB_l_img() {
		return b_l_img;
	}
	public void setB_l_img(String b_l_img) {
		this.b_l_img = b_l_img;
	}
	public String getB_l_url() {
		return b_l_url;
	}
	public void setB_l_url(String b_l_url) {
		this.b_l_url = b_l_url;
	}
	public String getB_l_chk() {
		return b_l_chk;
	}
	public void setB_l_chk(String b_l_chk) {
		this.b_l_chk = b_l_chk;
	}
	public String getB_l_sdate() {
		return b_l_sdate;
	}
	public void setB_l_sdate(String b_l_sdate) {
		this.b_l_sdate = b_l_sdate;
	}
	public String getB_l_edate() {
		return b_l_edate;
	}
	public void setB_l_edate(String b_l_edate) {
		this.b_l_edate = b_l_edate;
	}
	public String getB_l_subject() {
		return b_l_subject;
	}
	public void setB_l_subject(String b_l_subject) {
		this.b_l_subject = b_l_subject;
	}
	public String getB_l_memo() {
		return b_l_memo;
	}
	public void setB_l_memo(String b_l_memo) {
		this.b_l_memo = b_l_memo;
	}
	public String getB_l_wdate() {
		return b_l_wdate;
	}
	public void setB_l_wdate(String b_l_wdate) {
		this.b_l_wdate = b_l_wdate;
	}
	public String getB_l_mdate() {
		return b_l_mdate;
	}
	public void setB_l_mdate(String b_l_mdate) {
		this.b_l_mdate = b_l_mdate;
	}
	public String getUnlimited() {
		return unlimited;
	}
	public void setUnlimited(String unlimited) {
		this.unlimited = unlimited;
	}
	public String getSite_dir() {
		return site_dir;
	}
	public void setSite_dir(String site_dir) {
		this.site_dir = site_dir;
	}
	public String getB_l_win() {
		return b_l_win;
	}
	public void setB_l_win(String b_l_win) {
		this.b_l_win = b_l_win;
	}

}
