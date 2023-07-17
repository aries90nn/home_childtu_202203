package kr.co.nninc.ncms.board_access.service;

import java.io.Serializable;

import kr.co.nninc.ncms.common.service.ExtendDTO;

public class BoardAccessDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = 6716345097639360963L;

	private String bl_num;
	private String g_num;
	private String a_num;
	private String bl_ad_cms;
	private String bl_list;
	private String bl_read;
	private String bl_write;
	private String bl_delete;
	private String bl_reply;
	
	public String getBl_num() {
		return bl_num;
	}
	public void setBl_num(String bl_num) {
		this.bl_num = bl_num;
	}
	public String getG_num() {
		return g_num;
	}
	public void setG_num(String g_num) {
		this.g_num = g_num;
	}
	public String getA_num() {
		return a_num;
	}
	public void setA_num(String a_num) {
		this.a_num = a_num;
	}
	public String getBl_ad_cms() {
		return bl_ad_cms;
	}
	public void setBl_ad_cms(String bl_ad_cms) {
		this.bl_ad_cms = bl_ad_cms;
	}
	public String getBl_list() {
		return bl_list;
	}
	public void setBl_list(String bl_list) {
		this.bl_list = bl_list;
	}
	public String getBl_read() {
		return bl_read;
	}
	public void setBl_read(String bl_read) {
		this.bl_read = bl_read;
	}
	public String getBl_write() {
		return bl_write;
	}
	public void setBl_write(String bl_write) {
		this.bl_write = bl_write;
	}
	public String getBl_delete() {
		return bl_delete;
	}
	public void setBl_delete(String bl_delete) {
		this.bl_delete = bl_delete;
	}
	public String getBl_reply() {
		return bl_reply;
	}
	public void setBl_reply(String bl_reply) {
		this.bl_reply = bl_reply;
	}
	
	
}
