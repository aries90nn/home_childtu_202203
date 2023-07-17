package kr.co.nninc.ncms.member_conf.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("memberconfdto")
public class MemberConfDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 4488466946361971310L;
	
	private String mc_idx;
	private String mc_addr;
	private String mc_nickname;
	private String mc_phone;
	private String mc_mobile;
	private String mc_email;
	private String mc_homepage;
	private String mc_joinlevel;
	private String mc_wdate;
	private String mc_mailing;
	private String mc_sms;
	private String mc_sex;
	private String mc_birth;
	private String mc_fax;
	private String mc_marry;
	private String mc_marrydate;
	private String mc_job;
	private String mc_text;
	private String mc_nickname_req;
	private String mc_jumin_req;
	private String mc_sex_req;
	private String mc_birth_req;
	private String mc_phone_req;
	private String mc_fax_req;
	private String mc_mobile_req;
	private String mc_email_req;
	private String mc_addr_req;
	private String mc_homepage_req;
	private String mc_marry_req;
	private String mc_marrydate_req;
	private String mc_job_req;
	private String mc_text_req;
	private String mc_recom;
	private String mc_recom_point;
	private String mc_new_point;
	private String mc_agree;
	private String mc_agree_str;
	private String mc_info;
	private String mc_info_str;
	private String mc_join;
	
	public String getMc_idx() {
		return mc_idx;
	}
	public void setMc_idx(String mc_idx) {
		this.mc_idx = mc_idx;
	}
	public String getMc_addr() {
		return mc_addr;
	}
	public void setMc_addr(String mc_addr) {
		this.mc_addr = mc_addr;
	}
	public String getMc_nickname() {
		return mc_nickname;
	}
	public void setMc_nickname(String mc_nickname) {
		this.mc_nickname = mc_nickname;
	}
	public String getMc_phone() {
		return mc_phone;
	}
	public void setMc_phone(String mc_phone) {
		this.mc_phone = mc_phone;
	}
	public String getMc_mobile() {
		return mc_mobile;
	}
	public void setMc_mobile(String mc_mobile) {
		this.mc_mobile = mc_mobile;
	}
	public String getMc_email() {
		return mc_email;
	}
	public void setMc_email(String mc_email) {
		this.mc_email = mc_email;
	}
	public String getMc_homepage() {
		return mc_homepage;
	}
	public void setMc_homepage(String mc_homepage) {
		this.mc_homepage = mc_homepage;
	}
	public String getMc_joinlevel() {
		return mc_joinlevel;
	}
	public void setMc_joinlevel(String mc_joinlevel) {
		this.mc_joinlevel = mc_joinlevel;
	}
	public String getMc_wdate() {
		return mc_wdate;
	}
	public void setMc_wdate(String mc_wdate) {
		this.mc_wdate = mc_wdate;
	}
	public String getMc_mailing() {
		return mc_mailing;
	}
	public void setMc_mailing(String mc_mailing) {
		this.mc_mailing = mc_mailing;
	}
	public String getMc_sms() {
		return mc_sms;
	}
	public void setMc_sms(String mc_sms) {
		this.mc_sms = mc_sms;
	}
	public String getMc_sex() {
		return mc_sex;
	}
	public void setMc_sex(String mc_sex) {
		this.mc_sex = mc_sex;
	}
	public String getMc_birth() {
		return mc_birth;
	}
	public void setMc_birth(String mc_birth) {
		this.mc_birth = mc_birth;
	}
	public String getMc_fax() {
		return mc_fax;
	}
	public void setMc_fax(String mc_fax) {
		this.mc_fax = mc_fax;
	}
	public String getMc_marry() {
		return mc_marry;
	}
	public void setMc_marry(String mc_marry) {
		this.mc_marry = mc_marry;
	}
	public String getMc_marrydate() {
		return mc_marrydate;
	}
	public void setMc_marrydate(String mc_marrydate) {
		this.mc_marrydate = mc_marrydate;
	}
	public String getMc_job() {
		return mc_job;
	}
	public void setMc_job(String mc_job) {
		this.mc_job = mc_job;
	}
	public String getMc_text() {
		return mc_text;
	}
	public void setMc_text(String mc_text) {
		this.mc_text = mc_text;
	}
	public String getMc_nickname_req() {
		return mc_nickname_req;
	}
	public void setMc_nickname_req(String mc_nickname_req) {
		this.mc_nickname_req = mc_nickname_req;
	}
	public String getMc_jumin_req() {
		return mc_jumin_req;
	}
	public void setMc_jumin_req(String mc_jumin_req) {
		this.mc_jumin_req = mc_jumin_req;
	}
	public String getMc_sex_req() {
		return mc_sex_req;
	}
	public void setMc_sex_req(String mc_sex_req) {
		this.mc_sex_req = mc_sex_req;
	}
	public String getMc_birth_req() {
		return mc_birth_req;
	}
	public void setMc_birth_req(String mc_birth_req) {
		this.mc_birth_req = mc_birth_req;
	}
	public String getMc_phone_req() {
		return mc_phone_req;
	}
	public void setMc_phone_req(String mc_phone_req) {
		this.mc_phone_req = mc_phone_req;
	}
	public String getMc_fax_req() {
		return mc_fax_req;
	}
	public void setMc_fax_req(String mc_fax_req) {
		this.mc_fax_req = mc_fax_req;
	}
	public String getMc_mobile_req() {
		return mc_mobile_req;
	}
	public void setMc_mobile_req(String mc_mobile_req) {
		this.mc_mobile_req = mc_mobile_req;
	}
	public String getMc_email_req() {
		return mc_email_req;
	}
	public void setMc_email_req(String mc_email_req) {
		this.mc_email_req = mc_email_req;
	}
	public String getMc_addr_req() {
		return mc_addr_req;
	}
	public void setMc_addr_req(String mc_addr_req) {
		this.mc_addr_req = mc_addr_req;
	}
	public String getMc_homepage_req() {
		return mc_homepage_req;
	}
	public void setMc_homepage_req(String mc_homepage_req) {
		this.mc_homepage_req = mc_homepage_req;
	}
	public String getMc_marry_req() {
		return mc_marry_req;
	}
	public void setMc_marry_req(String mc_marry_req) {
		this.mc_marry_req = mc_marry_req;
	}
	public String getMc_marrydate_req() {
		return mc_marrydate_req;
	}
	public void setMc_marrydate_req(String mc_marrydate_req) {
		this.mc_marrydate_req = mc_marrydate_req;
	}
	public String getMc_job_req() {
		return mc_job_req;
	}
	public void setMc_job_req(String mc_job_req) {
		this.mc_job_req = mc_job_req;
	}
	public String getMc_text_req() {
		return mc_text_req;
	}
	public void setMc_text_req(String mc_text_req) {
		this.mc_text_req = mc_text_req;
	}
	public String getMc_recom() {
		return mc_recom;
	}
	public void setMc_recom(String mc_recom) {
		this.mc_recom = mc_recom;
	}
	public String getMc_recom_point() {
		return mc_recom_point;
	}
	public void setMc_recom_point(String mc_recom_point) {
		this.mc_recom_point = mc_recom_point;
	}
	public String getMc_new_point() {
		return mc_new_point;
	}
	public void setMc_new_point(String mc_new_point) {
		this.mc_new_point = mc_new_point;
	}
	public String getMc_agree() {
		return mc_agree;
	}
	public void setMc_agree(String mc_agree) {
		this.mc_agree = mc_agree;
	}
	public String getMc_agree_str() {
		return mc_agree_str;
	}
	public void setMc_agree_str(String mc_agree_str) {
		this.mc_agree_str = mc_agree_str;
	}
	public String getMc_info() {
		return mc_info;
	}
	public void setMc_info(String mc_info) {
		this.mc_info = mc_info;
	}
	public String getMc_info_str() {
		return mc_info_str;
	}
	public void setMc_info_str(String mc_info_str) {
		this.mc_info_str = mc_info_str;
	}
	public String getMc_join() {
		return mc_join;
	}
	public void setMc_join(String mc_join) {
		this.mc_join = mc_join;
	}

}
