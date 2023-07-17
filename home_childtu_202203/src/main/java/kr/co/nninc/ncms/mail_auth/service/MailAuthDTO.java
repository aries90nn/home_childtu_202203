package kr.co.nninc.ncms.mail_auth.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("mailauthdto")
public class MailAuthDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = 2550565598677485787L;

	private String a_name;
	private String a_chk;
	private String a_email;
	private String a_random;
	private String a_wdate;

	public String getA_name() {
		return a_name;
	}

	public void setA_name(String a_name) {
		this.a_name = a_name;
	}

	public String getA_chk() {
		return a_chk;
	}

	public void setA_chk(String a_chk) {
		this.a_chk = a_chk;
	}

	public String getA_email() {
		return a_email;
	}

	public void setA_email(String a_email) {
		this.a_email = a_email;
	}

	public String getA_random() {
		return a_random;
	}

	public void setA_random(String a_random) {
		this.a_random = a_random;
	}

	public String getA_wdate() {
		return a_wdate;
	}

	public void setA_wdate(String a_wdate) {
		this.a_wdate = a_wdate;
	}

}