package kr.co.nninc.ncms.member_group.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("membergroupdto")
public class MemberGroupDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = -4995373512964726484L;

	private String g_num;
	private String g_code;
	private String g_menuname;
	private String g_chk;
	private String g_hdsize;
	private String g_wdate;
	private String g_webmail;
	private String g_money;
	private String g_myhome;
	private String g_cafe;
	private String g_blog;
	private String g_sms;
	private String g_manager;
	private String g_site_dir;
	
	public String getG_num() {
		return g_num;
	}
	public void setG_num(String g_num) {
		this.g_num = g_num;
	}
	public String getG_code() {
		return g_code;
	}
	public void setG_code(String g_code) {
		this.g_code = g_code;
	}
	public String getG_menuname() {
		return g_menuname;
	}
	public void setG_menuname(String g_menuname) {
		this.g_menuname = g_menuname;
	}
	public String getG_chk() {
		return g_chk;
	}
	public void setG_chk(String g_chk) {
		this.g_chk = g_chk;
	}
	public String getG_hdsize() {
		return g_hdsize;
	}
	public void setG_hdsize(String g_hdsize) {
		this.g_hdsize = g_hdsize;
	}
	public String getG_wdate() {
		return g_wdate;
	}
	public void setG_wdate(String g_wdate) {
		this.g_wdate = g_wdate;
	}
	public String getG_webmail() {
		return g_webmail;
	}
	public void setG_webmail(String g_webmail) {
		this.g_webmail = g_webmail;
	}
	public String getG_money() {
		return g_money;
	}
	public void setG_money(String g_money) {
		this.g_money = g_money;
	}
	public String getG_myhome() {
		return g_myhome;
	}
	public void setG_myhome(String g_myhome) {
		this.g_myhome = g_myhome;
	}
	public String getG_cafe() {
		return g_cafe;
	}
	public void setG_cafe(String g_cafe) {
		this.g_cafe = g_cafe;
	}
	public String getG_blog() {
		return g_blog;
	}
	public void setG_blog(String g_blog) {
		this.g_blog = g_blog;
	}
	public String getG_sms() {
		return g_sms;
	}
	public void setG_sms(String g_sms) {
		this.g_sms = g_sms;
	}
	public String getG_manager() {
		return g_manager;
	}
	public void setG_manager(String g_manager) {
		this.g_manager = g_manager;
	}
	public String getG_site_dir() {
		return g_site_dir;
	}
	public void setG_site_dir(String g_site_dir) {
		this.g_site_dir = g_site_dir;
	}
	
	
}
