package kr.co.nninc.ncms.board_config.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("boardconfigdto")
public class BoardConfigDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 593788138520519827L;

	private String a_idx;
	private String a_num;
	private String a_bbsname;
	private String a_level;
	private String a_type;
	private String a_tablename;
	private String a_tablecnt;
	private String a_imgline;
	private String a_imgwidth;
	private String a_imgheight;
	private String a_member;
	private String a_ad_cms_id;
	private String a_ad_cms;
	private String a_cate;
	private String a_email;
	private String a_email_req;
	private String a_phone;
	private String a_phone_req;
	private String a_home;
	private String a_home_req;
	private String a_content;
	private String a_content_req;
	private String a_jumin;
	private String a_jumin_opt;
	private String a_ftemp1;
	private String a_ftemp1_type;
	private String a_ftemp1_str;
	private String a_ftemp1_val;
	private String a_ftemp1_req;
	private String a_ftemp2;
	private String a_ftemp2_type;
	private String a_ftemp2_str;
	private String a_ftemp2_val;
	private String a_ftemp2_req;
	private String a_ftemp3;
	private String a_ftemp3_type;
	private String a_ftemp3_str;
	private String a_ftemp3_val;
	private String a_ftemp3_req;
	private String a_ftemp4;
	private String a_ftemp4_type;
	private String a_ftemp4_str;
	private String a_ftemp4_val;
	private String a_ftemp4_req;
	private String a_ftemp5;
	private String a_ftemp5_type;
	private String a_ftemp5_str;
	private String a_ftemp5_val;
	private String a_ftemp5_req;
	private String a_ftemp6;
	private String a_ftemp6_type;
	private String a_ftemp6_str;
	private String a_ftemp6_val;
	private String a_ftemp6_req;
	private String a_ftemp7;
	private String a_ftemp7_type;
	private String a_ftemp7_str;
	private String a_ftemp7_val;
	private String a_ftemp7_req;
	private String a_ftemp8;
	private String a_ftemp8_type;
	private String a_ftemp8_str;
	private String a_ftemp8_val;
	private String a_ftemp8_req;
	private String a_new;
	private String a_upload;
	private String a_upload_len;
	private String a_nofilesize;
	private String a_nofile;
	private String a_reply;
	private String a_replyOpt;
	private String a_command;
	private String a_date_list;
	private String a_sdate;
	private String a_edate;
	private String a_noword;
	private String a_width;
	private String a_displaysu;
	private String a_viewType;
	private String a_lt_a;
	private String a_lt_b;
	private String a_lt_c;
	private String a_lt_e;
	private String a_lt_f;
	private String a_header;
	private String a_hd_file;
	private String a_detail;
	private String a_dt_file;
	private String a_writecontent;
	private String a_topinclude;
	private String a_btminclude;
	private String a_skin;
	private String a_date;
	private String a_edit;
	private String a_password;
	private String a_password_str;
	private String a_blind;
	private String a_code;
	private String a_group;
	private String a_sns;
	private String a_rss;
	private String a_photoview;
	private String a_multiupload;
	private String a_garbage;
	private String a_show;
	private String a_show_term;
	private String a_pageurl;
	private String a_date_change;
	private String a_site_dir;
	
	
	public String getA_idx() {
		return a_idx;
	}
	public void setA_idx(String a_idx) {
		this.a_idx = a_idx;
	}
	public String getA_num() {
		return a_num;
	}
	public void setA_num(String a_num) {
		this.a_num = a_num;
	}
	public String getA_bbsname() {
		return a_bbsname;
	}
	public void setA_bbsname(String a_bbsname) {
		this.a_bbsname = a_bbsname;
	}
	public String getA_level() {
		return a_level;
	}
	public void setA_level(String a_level) {
		this.a_level = a_level;
	}
	public String getA_type() {
		return a_type;
	}
	public void setA_type(String a_type) {
		this.a_type = a_type;
	}
	public String getA_tablename() {
		return a_tablename;
	}
	public void setA_tablename(String a_tablename) {
		this.a_tablename = a_tablename;
	}
	public String getA_tablecnt() {
		return a_tablecnt;
	}
	public void setA_tablecnt(String a_tablecnt) {
		this.a_tablecnt = a_tablecnt;
	}
	public String getA_imgline() {
		return a_imgline;
	}
	public void setA_imgline(String a_imgline) {
		this.a_imgline = a_imgline;
	}
	public String getA_imgwidth() {
		return a_imgwidth;
	}
	public void setA_imgwidth(String a_imgwidth) {
		this.a_imgwidth = a_imgwidth;
	}
	public String getA_imgheight() {
		return a_imgheight;
	}
	public void setA_imgheight(String a_imgheight) {
		this.a_imgheight = a_imgheight;
	}
	public String getA_member() {
		return a_member;
	}
	public void setA_member(String a_member) {
		this.a_member = a_member;
	}
	public String getA_ad_cms_id() {
		return a_ad_cms_id;
	}
	public void setA_ad_cms_id(String a_ad_cms_id) {
		this.a_ad_cms_id = a_ad_cms_id;
	}
	public String getA_ad_cms() {
		return a_ad_cms;
	}
	public void setA_ad_cms(String a_ad_cms) {
		this.a_ad_cms = a_ad_cms;
	}
	public String getA_cate() {
		return a_cate;
	}
	public void setA_cate(String a_cate) {
		this.a_cate = a_cate;
	}
	public String getA_email() {
		return a_email;
	}
	public void setA_email(String a_email) {
		this.a_email = a_email;
	}
	public String getA_email_req() {
		return a_email_req;
	}
	public void setA_email_req(String a_email_req) {
		this.a_email_req = a_email_req;
	}
	public String getA_phone() {
		return a_phone;
	}
	public void setA_phone(String a_phone) {
		this.a_phone = a_phone;
	}
	public String getA_phone_req() {
		return a_phone_req;
	}
	public void setA_phone_req(String a_phone_req) {
		this.a_phone_req = a_phone_req;
	}
	public String getA_home() {
		return a_home;
	}
	public void setA_home(String a_home) {
		this.a_home = a_home;
	}
	public String getA_home_req() {
		return a_home_req;
	}
	public void setA_home_req(String a_home_req) {
		this.a_home_req = a_home_req;
	}
	public String getA_content() {
		return a_content;
	}
	public void setA_content(String a_content) {
		this.a_content = a_content;
	}
	public String getA_content_req() {
		return a_content_req;
	}
	public void setA_content_req(String a_content_req) {
		this.a_content_req = a_content_req;
	}
	public String getA_jumin() {
		return a_jumin;
	}
	public void setA_jumin(String a_jumin) {
		this.a_jumin = a_jumin;
	}
	public String getA_jumin_opt() {
		return a_jumin_opt;
	}
	public void setA_jumin_opt(String a_jumin_opt) {
		this.a_jumin_opt = a_jumin_opt;
	}
	public String getA_ftemp1() {
		return a_ftemp1;
	}
	public void setA_ftemp1(String a_ftemp1) {
		this.a_ftemp1 = a_ftemp1;
	}
	public String getA_ftemp1_type() {
		return a_ftemp1_type;
	}
	public void setA_ftemp1_type(String a_ftemp1_type) {
		this.a_ftemp1_type = a_ftemp1_type;
	}
	public String getA_ftemp1_str() {
		return a_ftemp1_str;
	}
	public void setA_ftemp1_str(String a_ftemp1_str) {
		this.a_ftemp1_str = a_ftemp1_str;
	}
	public String getA_ftemp1_val() {
		return a_ftemp1_val;
	}
	public void setA_ftemp1_val(String a_ftemp1_val) {
		this.a_ftemp1_val = a_ftemp1_val;
	}
	public String getA_ftemp1_req() {
		return a_ftemp1_req;
	}
	public void setA_ftemp1_req(String a_ftemp1_req) {
		this.a_ftemp1_req = a_ftemp1_req;
	}
	public String getA_ftemp2() {
		return a_ftemp2;
	}
	public void setA_ftemp2(String a_ftemp2) {
		this.a_ftemp2 = a_ftemp2;
	}
	public String getA_ftemp2_type() {
		return a_ftemp2_type;
	}
	public void setA_ftemp2_type(String a_ftemp2_type) {
		this.a_ftemp2_type = a_ftemp2_type;
	}
	public String getA_ftemp2_str() {
		return a_ftemp2_str;
	}
	public void setA_ftemp2_str(String a_ftemp2_str) {
		this.a_ftemp2_str = a_ftemp2_str;
	}
	public String getA_ftemp2_val() {
		return a_ftemp2_val;
	}
	public void setA_ftemp2_val(String a_ftemp2_val) {
		this.a_ftemp2_val = a_ftemp2_val;
	}
	public String getA_ftemp2_req() {
		return a_ftemp2_req;
	}
	public void setA_ftemp2_req(String a_ftemp2_req) {
		this.a_ftemp2_req = a_ftemp2_req;
	}
	public String getA_ftemp3() {
		return a_ftemp3;
	}
	public void setA_ftemp3(String a_ftemp3) {
		this.a_ftemp3 = a_ftemp3;
	}
	public String getA_ftemp3_type() {
		return a_ftemp3_type;
	}
	public void setA_ftemp3_type(String a_ftemp3_type) {
		this.a_ftemp3_type = a_ftemp3_type;
	}
	public String getA_ftemp3_str() {
		return a_ftemp3_str;
	}
	public void setA_ftemp3_str(String a_ftemp3_str) {
		this.a_ftemp3_str = a_ftemp3_str;
	}
	public String getA_ftemp3_val() {
		return a_ftemp3_val;
	}
	public void setA_ftemp3_val(String a_ftemp3_val) {
		this.a_ftemp3_val = a_ftemp3_val;
	}
	public String getA_ftemp3_req() {
		return a_ftemp3_req;
	}
	public void setA_ftemp3_req(String a_ftemp3_req) {
		this.a_ftemp3_req = a_ftemp3_req;
	}
	public String getA_ftemp4() {
		return a_ftemp4;
	}
	public void setA_ftemp4(String a_ftemp4) {
		this.a_ftemp4 = a_ftemp4;
	}
	public String getA_ftemp4_type() {
		return a_ftemp4_type;
	}
	public void setA_ftemp4_type(String a_ftemp4_type) {
		this.a_ftemp4_type = a_ftemp4_type;
	}
	public String getA_ftemp4_str() {
		return a_ftemp4_str;
	}
	public void setA_ftemp4_str(String a_ftemp4_str) {
		this.a_ftemp4_str = a_ftemp4_str;
	}
	public String getA_ftemp4_val() {
		return a_ftemp4_val;
	}
	public void setA_ftemp4_val(String a_ftemp4_val) {
		this.a_ftemp4_val = a_ftemp4_val;
	}
	public String getA_ftemp4_req() {
		return a_ftemp4_req;
	}
	public void setA_ftemp4_req(String a_ftemp4_req) {
		this.a_ftemp4_req = a_ftemp4_req;
	}
	public String getA_ftemp5() {
		return a_ftemp5;
	}
	public void setA_ftemp5(String a_ftemp5) {
		this.a_ftemp5 = a_ftemp5;
	}
	public String getA_ftemp5_type() {
		return a_ftemp5_type;
	}
	public void setA_ftemp5_type(String a_ftemp5_type) {
		this.a_ftemp5_type = a_ftemp5_type;
	}
	public String getA_ftemp5_str() {
		return a_ftemp5_str;
	}
	public void setA_ftemp5_str(String a_ftemp5_str) {
		this.a_ftemp5_str = a_ftemp5_str;
	}
	public String getA_ftemp5_val() {
		return a_ftemp5_val;
	}
	public void setA_ftemp5_val(String a_ftemp5_val) {
		this.a_ftemp5_val = a_ftemp5_val;
	}
	public String getA_ftemp5_req() {
		return a_ftemp5_req;
	}
	public void setA_ftemp5_req(String a_ftemp5_req) {
		this.a_ftemp5_req = a_ftemp5_req;
	}
	public String getA_ftemp6() {
		return a_ftemp6;
	}
	public void setA_ftemp6(String a_ftemp6) {
		this.a_ftemp6 = a_ftemp6;
	}
	public String getA_ftemp6_type() {
		return a_ftemp6_type;
	}
	public void setA_ftemp6_type(String a_ftemp6_type) {
		this.a_ftemp6_type = a_ftemp6_type;
	}
	public String getA_ftemp6_str() {
		return a_ftemp6_str;
	}
	public void setA_ftemp6_str(String a_ftemp6_str) {
		this.a_ftemp6_str = a_ftemp6_str;
	}
	public String getA_ftemp6_val() {
		return a_ftemp6_val;
	}
	public void setA_ftemp6_val(String a_ftemp6_val) {
		this.a_ftemp6_val = a_ftemp6_val;
	}
	public String getA_ftemp6_req() {
		return a_ftemp6_req;
	}
	public void setA_ftemp6_req(String a_ftemp6_req) {
		this.a_ftemp6_req = a_ftemp6_req;
	}
	public String getA_ftemp7() {
		return a_ftemp7;
	}
	public void setA_ftemp7(String a_ftemp7) {
		this.a_ftemp7 = a_ftemp7;
	}
	public String getA_ftemp7_type() {
		return a_ftemp7_type;
	}
	public void setA_ftemp7_type(String a_ftemp7_type) {
		this.a_ftemp7_type = a_ftemp7_type;
	}
	public String getA_ftemp7_str() {
		return a_ftemp7_str;
	}
	public void setA_ftemp7_str(String a_ftemp7_str) {
		this.a_ftemp7_str = a_ftemp7_str;
	}
	public String getA_ftemp7_val() {
		return a_ftemp7_val;
	}
	public void setA_ftemp7_val(String a_ftemp7_val) {
		this.a_ftemp7_val = a_ftemp7_val;
	}
	public String getA_ftemp7_req() {
		return a_ftemp7_req;
	}
	public void setA_ftemp7_req(String a_ftemp7_req) {
		this.a_ftemp7_req = a_ftemp7_req;
	}
	public String getA_ftemp8() {
		return a_ftemp8;
	}
	public void setA_ftemp8(String a_ftemp8) {
		this.a_ftemp8 = a_ftemp8;
	}
	public String getA_ftemp8_type() {
		return a_ftemp8_type;
	}
	public void setA_ftemp8_type(String a_ftemp8_type) {
		this.a_ftemp8_type = a_ftemp8_type;
	}
	public String getA_ftemp8_str() {
		return a_ftemp8_str;
	}
	public void setA_ftemp8_str(String a_ftemp8_str) {
		this.a_ftemp8_str = a_ftemp8_str;
	}
	public String getA_ftemp8_val() {
		return a_ftemp8_val;
	}
	public void setA_ftemp8_val(String a_ftemp8_val) {
		this.a_ftemp8_val = a_ftemp8_val;
	}
	public String getA_ftemp8_req() {
		return a_ftemp8_req;
	}
	public void setA_ftemp8_req(String a_ftemp8_req) {
		this.a_ftemp8_req = a_ftemp8_req;
	}
	public String getA_new() {
		return a_new;
	}
	public void setA_new(String a_new) {
		this.a_new = a_new;
	}
	public String getA_upload() {
		return a_upload;
	}
	public void setA_upload(String a_upload) {
		this.a_upload = a_upload;
	}
	public String getA_upload_len() {
		return a_upload_len;
	}
	public void setA_upload_len(String a_upload_len) {
		this.a_upload_len = a_upload_len;
	}
	public String getA_nofilesize() {
		return a_nofilesize;
	}
	public void setA_nofilesize(String a_nofilesize) {
		this.a_nofilesize = a_nofilesize;
	}
	public String getA_nofile() {
		return a_nofile;
	}
	public void setA_nofile(String a_nofile) {
		this.a_nofile = a_nofile;
	}
	public String getA_reply() {
		return a_reply;
	}
	public void setA_reply(String a_reply) {
		this.a_reply = a_reply;
	}
	public String getA_replyOpt() {
		return a_replyOpt;
	}
	public void setA_replyOpt(String a_replyOpt) {
		this.a_replyOpt = a_replyOpt;
	}
	public String getA_command() {
		return a_command;
	}
	public void setA_command(String a_command) {
		this.a_command = a_command;
	}
	public String getA_date_list() {
		return a_date_list;
	}
	public void setA_date_list(String a_date_list) {
		this.a_date_list = a_date_list;
	}
	public String getA_sdate() {
		return a_sdate;
	}
	public void setA_sdate(String a_sdate) {
		this.a_sdate = a_sdate;
	}
	public String getA_edate() {
		return a_edate;
	}
	public void setA_edate(String a_edate) {
		this.a_edate = a_edate;
	}
	public String getA_noword() {
		return a_noword;
	}
	public void setA_noword(String a_noword) {
		this.a_noword = a_noword;
	}
	public String getA_width() {
		return a_width;
	}
	public void setA_width(String a_width) {
		this.a_width = a_width;
	}
	public String getA_displaysu() {
		return a_displaysu;
	}
	public void setA_displaysu(String a_displaysu) {
		this.a_displaysu = a_displaysu;
	}
	public String getA_viewType() {
		return a_viewType;
	}
	public void setA_viewType(String a_viewType) {
		this.a_viewType = a_viewType;
	}
	public String getA_lt_a() {
		return a_lt_a;
	}
	public void setA_lt_a(String a_lt_a) {
		this.a_lt_a = a_lt_a;
	}
	public String getA_lt_b() {
		return a_lt_b;
	}
	public void setA_lt_b(String a_lt_b) {
		this.a_lt_b = a_lt_b;
	}
	public String getA_lt_c() {
		return a_lt_c;
	}
	public void setA_lt_c(String a_lt_c) {
		this.a_lt_c = a_lt_c;
	}
	public String getA_lt_e() {
		return a_lt_e;
	}
	public void setA_lt_e(String a_lt_e) {
		this.a_lt_e = a_lt_e;
	}
	public String getA_lt_f() {
		return a_lt_f;
	}
	public void setA_lt_f(String a_lt_f) {
		this.a_lt_f = a_lt_f;
	}
	public String getA_header() {
		return a_header;
	}
	public void setA_header(String a_header) {
		this.a_header = a_header;
	}
	public String getA_hd_file() {
		return a_hd_file;
	}
	public void setA_hd_file(String a_hd_file) {
		this.a_hd_file = a_hd_file;
	}
	public String getA_detail() {
		return a_detail;
	}
	public void setA_detail(String a_detail) {
		this.a_detail = a_detail;
	}
	public String getA_dt_file() {
		return a_dt_file;
	}
	public void setA_dt_file(String a_dt_file) {
		this.a_dt_file = a_dt_file;
	}
	public String getA_writecontent() {
		return a_writecontent;
	}
	public void setA_writecontent(String a_writecontent) {
		this.a_writecontent = a_writecontent;
	}
	public String getA_topinclude() {
		return a_topinclude;
	}
	public void setA_topinclude(String a_topinclude) {
		this.a_topinclude = a_topinclude;
	}
	public String getA_btminclude() {
		return a_btminclude;
	}
	public void setA_btminclude(String a_btminclude) {
		this.a_btminclude = a_btminclude;
	}
	public String getA_skin() {
		return a_skin;
	}
	public void setA_skin(String a_skin) {
		this.a_skin = a_skin;
	}
	public String getA_date() {
		return a_date;
	}
	public void setA_date(String a_date) {
		this.a_date = a_date;
	}
	public String getA_edit() {
		return a_edit;
	}
	public void setA_edit(String a_edit) {
		this.a_edit = a_edit;
	}
	public String getA_password() {
		return a_password;
	}
	public void setA_password(String a_password) {
		this.a_password = a_password;
	}
	public String getA_password_str() {
		return a_password_str;
	}
	public void setA_password_str(String a_password_str) {
		this.a_password_str = a_password_str;
	}
	public String getA_blind() {
		return a_blind;
	}
	public void setA_blind(String a_blind) {
		this.a_blind = a_blind;
	}
	public String getA_code() {
		return a_code;
	}
	public void setA_code(String a_code) {
		this.a_code = a_code;
	}
	public String getA_group() {
		return a_group;
	}
	public void setA_group(String a_group) {
		this.a_group = a_group;
	}
	public String getA_sns() {
		return a_sns;
	}
	public void setA_sns(String a_sns) {
		this.a_sns = a_sns;
	}
	public String getA_rss() {
		return a_rss;
	}
	public void setA_rss(String a_rss) {
		this.a_rss = a_rss;
	}
	public String getA_photoview() {
		return a_photoview;
	}
	public void setA_photoview(String a_photoview) {
		this.a_photoview = a_photoview;
	}
	public String getA_multiupload() {
		return a_multiupload;
	}
	public void setA_multiupload(String a_multiupload) {
		this.a_multiupload = a_multiupload;
	}
	public String getA_garbage() {
		return a_garbage;
	}
	public void setA_garbage(String a_garbage) {
		this.a_garbage = a_garbage;
	}
	public String getA_show() {
		return a_show;
	}
	public void setA_show(String a_show) {
		this.a_show = a_show;
	}
	public String getA_show_term() {
		return a_show_term;
	}
	public void setA_show_term(String a_show_term) {
		this.a_show_term = a_show_term;
	}
	public String getA_pageurl() {
		return a_pageurl;
	}
	public void setA_pageurl(String a_pageurl) {
		this.a_pageurl = a_pageurl;
	}
	public String getA_date_change() {
		return a_date_change;
	}
	public void setA_date_change(String a_date_change) {
		this.a_date_change = a_date_change;
	}
	public String getA_site_dir() {
		return a_site_dir;
	}
	public void setA_site_dir(String a_site_dir) {
		this.a_site_dir = a_site_dir;
	}
	
}
