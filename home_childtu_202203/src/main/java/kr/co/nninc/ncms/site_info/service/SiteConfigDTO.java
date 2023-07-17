package kr.co.nninc.ncms.site_info.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("siteconfigdto")
public class SiteConfigDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 1501717875589774119L;

	private String sc_idx;
	private String sc_sitename;
	private String sc_sitename_en;
	private String sc_ademail;
	private String sc_url;
	private String sc_company;
	private String sc_logo;
	private String sc_uptae;
	private String sc_jongmok;
	private String sc_zipcode;
	private String sc_addr1;
	private String sc_addr2;
	private String sc_no;
	private String sc_no2;
	private String sc_ceo;
	private String sc_phone1;
	private String sc_phone2;
	private String sc_phone3;
	private String sc_fax1;
	private String sc_fax2;
	private String sc_fax3;
	private String sc_title;
	private String sc_skeyword;
	private String sc_hdate1;
	private String sc_hdate2;
	private String sc_hdd;
	private String sc_copyright;
	private String sc_logouttime;
	private String sc_wdate;
	private String sc_date_chk;
	private String sc_hdd_chk;
	private String sc_editor;
	private String sc_manager_ip;
	private String sc_manager_mobile;
	private String sc_session_time;
	private String sc_session_use;
	private String sc_session_count;
	private String sc_session_wait;
	private String sc_person;
	private String sc_group;
	private String sc_header_meta;
	
	public String getSc_idx() {
		return sc_idx;
	}
	public void setSc_idx(String sc_idx) {
		this.sc_idx = sc_idx;
	}
	public String getSc_sitename() {
		return sc_sitename;
	}
	public void setSc_sitename(String sc_sitename) {
		this.sc_sitename = sc_sitename;
	}
	public String getSc_sitename_en() {
		return sc_sitename_en;
	}
	public void setSc_sitename_en(String sc_sitename_en) {
		this.sc_sitename_en = sc_sitename_en;
	}
	public String getSc_ademail() {
		return sc_ademail;
	}
	public void setSc_ademail(String sc_ademail) {
		this.sc_ademail = sc_ademail;
	}
	public String getSc_url() {
		return sc_url;
	}
	public void setSc_url(String sc_url) {
		this.sc_url = sc_url;
	}
	public String getSc_company() {
		return sc_company;
	}
	public void setSc_company(String sc_company) {
		this.sc_company = sc_company;
	}
	public String getSc_logo() {
		return sc_logo;
	}
	public void setSc_logo(String sc_logo) {
		this.sc_logo = sc_logo;
	}
	public String getSc_uptae() {
		return sc_uptae;
	}
	public void setSc_uptae(String sc_uptae) {
		this.sc_uptae = sc_uptae;
	}
	public String getSc_jongmok() {
		return sc_jongmok;
	}
	public void setSc_jongmok(String sc_jongmok) {
		this.sc_jongmok = sc_jongmok;
	}
	public String getSc_zipcode() {
		return sc_zipcode;
	}
	public void setSc_zipcode(String sc_zipcode) {
		this.sc_zipcode = sc_zipcode;
	}
	public String getSc_addr1() {
		return sc_addr1;
	}
	public void setSc_addr1(String sc_addr1) {
		this.sc_addr1 = sc_addr1;
	}
	public String getSc_addr2() {
		return sc_addr2;
	}
	public void setSc_addr2(String sc_addr2) {
		this.sc_addr2 = sc_addr2;
	}
	public String getSc_no() {
		return sc_no;
	}
	public void setSc_no(String sc_no) {
		this.sc_no = sc_no;
	}
	public String getSc_no2() {
		return sc_no2;
	}
	public void setSc_no2(String sc_no2) {
		this.sc_no2 = sc_no2;
	}
	public String getSc_ceo() {
		return sc_ceo;
	}
	public void setSc_ceo(String sc_ceo) {
		this.sc_ceo = sc_ceo;
	}
	public String getSc_phone1() {
		return sc_phone1;
	}
	public void setSc_phone1(String sc_phone1) {
		this.sc_phone1 = sc_phone1;
	}
	public String getSc_phone2() {
		return sc_phone2;
	}
	public void setSc_phone2(String sc_phone2) {
		this.sc_phone2 = sc_phone2;
	}
	public String getSc_phone3() {
		return sc_phone3;
	}
	public void setSc_phone3(String sc_phone3) {
		this.sc_phone3 = sc_phone3;
	}
	public String getSc_fax1() {
		return sc_fax1;
	}
	public void setSc_fax1(String sc_fax1) {
		this.sc_fax1 = sc_fax1;
	}
	public String getSc_fax2() {
		return sc_fax2;
	}
	public void setSc_fax2(String sc_fax2) {
		this.sc_fax2 = sc_fax2;
	}
	public String getSc_fax3() {
		return sc_fax3;
	}
	public void setSc_fax3(String sc_fax3) {
		this.sc_fax3 = sc_fax3;
	}
	public String getSc_title() {
		return sc_title;
	}
	public void setSc_title(String sc_title) {
		this.sc_title = sc_title;
	}
	public String getSc_skeyword() {
		return sc_skeyword;
	}
	public void setSc_skeyword(String sc_skeyword) {
		this.sc_skeyword = sc_skeyword;
	}
	public String getSc_hdate1() {
		return sc_hdate1;
	}
	public void setSc_hdate1(String sc_hdate1) {
		this.sc_hdate1 = sc_hdate1;
	}
	public String getSc_hdate2() {
		return sc_hdate2;
	}
	public void setSc_hdate2(String sc_hdate2) {
		this.sc_hdate2 = sc_hdate2;
	}
	public String getSc_hdd() {
		return sc_hdd;
	}
	public void setSc_hdd(String sc_hdd) {
		this.sc_hdd = sc_hdd;
	}
	public String getSc_copyright() {
		return sc_copyright;
	}
	public void setSc_copyright(String sc_copyright) {
		this.sc_copyright = sc_copyright;
	}
	public String getSc_logouttime() {
		return sc_logouttime;
	}
	public void setSc_logouttime(String sc_logouttime) {
		this.sc_logouttime = sc_logouttime;
	}
	public String getSc_wdate() {
		return sc_wdate;
	}
	public void setSc_wdate(String sc_wdate) {
		this.sc_wdate = sc_wdate;
	}
	public String getSc_date_chk() {
		return sc_date_chk;
	}
	public void setSc_date_chk(String sc_date_chk) {
		this.sc_date_chk = sc_date_chk;
	}
	public String getSc_hdd_chk() {
		return sc_hdd_chk;
	}
	public void setSc_hdd_chk(String sc_hdd_chk) {
		this.sc_hdd_chk = sc_hdd_chk;
	}
	public String getSc_editor() {
		return sc_editor;
	}
	public void setSc_editor(String sc_editor) {
		this.sc_editor = sc_editor;
	}
	public String getSc_manager_ip() {
		return sc_manager_ip;
	}
	public void setSc_manager_ip(String sc_manager_ip) {
		this.sc_manager_ip = sc_manager_ip;
	}
	public String getSc_manager_mobile() {
		return sc_manager_mobile;
	}
	public void setSc_manager_mobile(String sc_manager_mobile) {
		this.sc_manager_mobile = sc_manager_mobile;
	}
	public String getSc_session_time() {
		return sc_session_time;
	}
	public void setSc_session_time(String sc_session_time) {
		this.sc_session_time = sc_session_time;
	}
	public String getSc_session_use() {
		return sc_session_use;
	}
	public void setSc_session_use(String sc_session_use) {
		this.sc_session_use = sc_session_use;
	}
	public String getSc_session_count() {
		return sc_session_count;
	}
	public void setSc_session_count(String sc_session_count) {
		this.sc_session_count = sc_session_count;
	}
	public String getSc_session_wait() {
		return sc_session_wait;
	}
	public void setSc_session_wait(String sc_session_wait) {
		this.sc_session_wait = sc_session_wait;
	}
	public String getSc_person() {
		return sc_person;
	}
	public void setSc_person(String sc_person) {
		this.sc_person = sc_person;
	}
	public String getSc_group() {
		return sc_group;
	}
	public void setSc_group(String sc_group) {
		this.sc_group = sc_group;
	}
	public String getSc_header_meta() {
		return sc_header_meta;
	}
	public void setSc_header_meta(String sc_header_meta) {
		this.sc_header_meta = sc_header_meta;
	}
	
	
}
