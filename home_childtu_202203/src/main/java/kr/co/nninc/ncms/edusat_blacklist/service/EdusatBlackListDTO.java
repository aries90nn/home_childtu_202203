package kr.co.nninc.ncms.edusat_blacklist.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("edusatblacklistdto")
public class EdusatBlackListDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = -4929532140878581159L;
	private String eb_num;
	private String edu_idx;
	private String ct_idx;
	private String ct_idx2;
	private String edu_lib;
	private String edu_gubun;
	private String edu_subject;
	private String edu_resdate;
	private String edu_reedate;
	private String edu_sdate;
	private String edu_edate;
	private String edu_ci;
	private String es_name;
	private String es_id;
	private String es_bphone1;
	private String es_bphone2;
	private String es_bphone3;
	private String es_wdate;
	private String eb_comment;
	private String eb_w_id;
	private String eb_wdate;
	
	public String getEb_num() {
		return eb_num;
	}
	public void setEb_num(String eb_num) {
		this.eb_num = eb_num;
	}
	public String getEdu_idx() {
		return edu_idx;
	}
	public void setEdu_idx(String edu_idx) {
		this.edu_idx = edu_idx;
	}
	public String getCt_idx() {
		return ct_idx;
	}
	public void setCt_idx(String ct_idx) {
		this.ct_idx = ct_idx;
	}
	public String getCt_idx2() {
		return ct_idx2;
	}
	public void setCt_idx2(String ct_idx2) {
		this.ct_idx2 = ct_idx2;
	}
	public String getEdu_lib() {
		return edu_lib;
	}
	public void setEdu_lib(String edu_lib) {
		this.edu_lib = edu_lib;
	}
	public String getEdu_gubun() {
		return edu_gubun;
	}
	public void setEdu_gubun(String edu_gubun) {
		this.edu_gubun = edu_gubun;
	}
	public String getEdu_subject() {
		return edu_subject;
	}
	public void setEdu_subject(String edu_subject) {
		this.edu_subject = edu_subject;
	}
	public String getEdu_resdate() {
		return edu_resdate;
	}
	public void setEdu_resdate(String edu_resdate) {
		this.edu_resdate = edu_resdate;
	}
	public String getEdu_reedate() {
		return edu_reedate;
	}
	public void setEdu_reedate(String edu_reedate) {
		this.edu_reedate = edu_reedate;
	}
	public String getEdu_sdate() {
		return edu_sdate;
	}
	public void setEdu_sdate(String edu_sdate) {
		this.edu_sdate = edu_sdate;
	}
	public String getEdu_edate() {
		return edu_edate;
	}
	public void setEdu_edate(String edu_edate) {
		this.edu_edate = edu_edate;
	}
	public String getEdu_ci() {
		return edu_ci;
	}
	public void setEdu_ci(String edu_ci) {
		this.edu_ci = edu_ci;
	}
	public String getEs_name() {
		return es_name;
	}
	public void setEs_name(String es_name) {
		this.es_name = es_name;
	}
	public String getEs_id() {
		return es_id;
	}
	public void setEs_id(String es_id) {
		this.es_id = es_id;
	}
	public String getEs_bphone1() {
		return es_bphone1;
	}
	public void setEs_bphone1(String es_bphone1) {
		this.es_bphone1 = es_bphone1;
	}
	public String getEs_bphone2() {
		return es_bphone2;
	}
	public void setEs_bphone2(String es_bphone2) {
		this.es_bphone2 = es_bphone2;
	}
	public String getEs_bphone3() {
		return es_bphone3;
	}
	public void setEs_bphone3(String es_bphone3) {
		this.es_bphone3 = es_bphone3;
	}
	public String getEs_wdate() {
		return es_wdate;
	}
	public void setEs_wdate(String es_wdate) {
		this.es_wdate = es_wdate;
	}
	public String getEb_comment() {
		return eb_comment;
	}
	public void setEb_comment(String eb_comment) {
		this.eb_comment = eb_comment;
	}
	public String getEb_w_id() {
		return eb_w_id;
	}
	public void setEb_w_id(String eb_w_id) {
		this.eb_w_id = eb_w_id;
	}
	public String getEb_wdate() {
		return eb_wdate;
	}
	public void setEb_wdate(String eb_wdate) {
		this.eb_wdate = eb_wdate;
	}

}
