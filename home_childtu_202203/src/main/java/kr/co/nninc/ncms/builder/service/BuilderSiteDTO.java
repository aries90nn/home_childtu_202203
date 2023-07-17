package kr.co.nninc.ncms.builder.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("buildersitedto")
public class BuilderSiteDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = -1270004200032684764L;

	private String bs_num;
	private String bs_sitename;
	private String bs_directory;
	private String bs_main;
	private String bs_skin;
	private String bs_writer;
	private String bs_regdate;
	private String bs_chk;
	private String bs_logo;
	private String bs_header_meta;
	private String bs_copyright;
	private String bs_manager_ip;
	private String bs_manager_mobile;
	private String bs_code;
	
	public String getBs_chk() {
		return bs_chk;
	}
	public void setBs_chk(String bs_chk) {
		this.bs_chk = bs_chk;
	}
	public String getBs_num() {
		return bs_num;
	}
	public void setBs_num(String bs_num) {
		this.bs_num = bs_num;
	}
	public String getBs_sitename() {
		return bs_sitename;
	}
	public void setBs_sitename(String bs_sitename) {
		this.bs_sitename = bs_sitename;
	}
	public String getBs_directory() {
		return bs_directory;
	}
	public void setBs_directory(String bs_directory) {
		this.bs_directory = bs_directory;
	}
	public String getBs_main() {
		return bs_main;
	}
	public void setBs_main(String bs_main) {
		this.bs_main = bs_main;
	}
	public String getBs_skin() {
		return bs_skin;
	}
	public void setBs_skin(String bs_skin) {
		this.bs_skin = bs_skin;
	}
	public String getBs_writer() {
		return bs_writer;
	}
	public void setBs_writer(String bs_writer) {
		this.bs_writer = bs_writer;
	}
	public String getBs_regdate() {
		return bs_regdate;
	}
	public void setBs_regdate(String bs_regdate) {
		this.bs_regdate = bs_regdate;
	}
	public String getBs_logo() {
		return bs_logo;
	}
	public void setBs_logo(String bs_logo) {
		this.bs_logo = bs_logo;
	}
	public String getBs_header_meta() {
		return bs_header_meta;
	}
	public void setBs_header_meta(String bs_header_meta) {
		this.bs_header_meta = bs_header_meta;
	}
	public String getBs_copyright() {
		return bs_copyright;
	}
	public void setBs_copyright(String bs_copyright) {
		this.bs_copyright = bs_copyright;
	}
	public String getBs_manager_ip() {
		return bs_manager_ip;
	}
	public void setBs_manager_ip(String bs_manager_ip) {
		this.bs_manager_ip = bs_manager_ip;
	}
	public String getBs_manager_mobile() {
		return bs_manager_mobile;
	}
	public void setBs_manager_mobile(String bs_manager_mobile) {
		this.bs_manager_mobile = bs_manager_mobile;
	}
	public String getBs_code() {
		return bs_code;
	}
	public void setBs_code(String bs_code) {
		this.bs_code = bs_code;
	}
	
}
