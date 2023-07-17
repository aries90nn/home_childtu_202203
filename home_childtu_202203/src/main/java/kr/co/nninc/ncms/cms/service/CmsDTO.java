package kr.co.nninc.ncms.cms.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("cmsdto")
public class CmsDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = -4562535404939041663L;

	private String ct_idx;
	private String ct_code;
	private String ct_name;
	private String ct_name_eng;
	private String ct_ref;
	private String ct_depth;
	private String ct_chk;
	private String ct_wdate;
	private String ct_mdate;
	private String ct_codeno;
	private String ct_url;
	private String ct_url_target;
	private String ct_pagetype;
	private String ct_content;
	private String ct_anum;
	private String ct_header;
	private String ct_footer;
	private String ct_program;
	private String ct_top_url;
	private String ct_bottom_url;
	private String ct_img_on;
	private String ct_img_off;
	private String ct_img_status;
	private String ct_popup_w;
	private String ct_popup_h;
	private String ct_point;
	private String ct_manage_num;
	private String ct_layout;
	private String ct_img_on2;
	private String ct_img_off2;
	private String ct_menu_url;
	private String ct_tab;
	private String ct_board_header;
	private String ct_board_footer;
	private String ct_group;
	private String ct_site_dir;
	
	
	public String getCt_idx() {
		return ct_idx;
	}
	public void setCt_idx(String ct_idx) {
		this.ct_idx = ct_idx;
	}
	public String getCt_code() {
		return ct_code;
	}
	public void setCt_code(String ct_code) {
		this.ct_code = ct_code;
	}
	public String getCt_name() {
		return ct_name;
	}
	public void setCt_name(String ct_name) {
		this.ct_name = ct_name;
	}
	public String getCt_name_eng() {
		return ct_name_eng;
	}
	public void setCt_name_eng(String ct_name_eng) {
		this.ct_name_eng = ct_name_eng;
	}
	public String getCt_ref() {
		return ct_ref;
	}
	public void setCt_ref(String ct_ref) {
		this.ct_ref = ct_ref;
	}
	public String getCt_depth() {
		return ct_depth;
	}
	public void setCt_depth(String ct_depth) {
		this.ct_depth = ct_depth;
	}
	public String getCt_chk() {
		return ct_chk;
	}
	public void setCt_chk(String ct_chk) {
		this.ct_chk = ct_chk;
	}
	public String getCt_wdate() {
		return ct_wdate;
	}
	public void setCt_wdate(String ct_wdate) {
		this.ct_wdate = ct_wdate;
	}
	public String getCt_mdate() {
		return ct_mdate;
	}
	public void setCt_mdate(String ct_mdate) {
		this.ct_mdate = ct_mdate;
	}
	public String getCt_codeno() {
		return ct_codeno;
	}
	public void setCt_codeno(String ct_codeno) {
		this.ct_codeno = ct_codeno;
	}
	public String getCt_url() {
		return ct_url;
	}
	public void setCt_url(String ct_url) {
		this.ct_url = ct_url;
	}
	public String getCt_url_target() {
		return ct_url_target;
	}
	public void setCt_url_target(String ct_url_target) {
		this.ct_url_target = ct_url_target;
	}
	public String getCt_pagetype() {
		return ct_pagetype;
	}
	public void setCt_pagetype(String ct_pagetype) {
		this.ct_pagetype = ct_pagetype;
	}
	public String getCt_content() {
		return ct_content;
	}
	public void setCt_content(String ct_content) {
		this.ct_content = ct_content;
	}
	public String getCt_anum() {
		return ct_anum;
	}
	public void setCt_anum(String ct_anum) {
		this.ct_anum = ct_anum;
	}
	public String getCt_header() {
		return ct_header;
	}
	public void setCt_header(String ct_header) {
		this.ct_header = ct_header;
	}
	public String getCt_footer() {
		return ct_footer;
	}
	public void setCt_footer(String ct_footer) {
		this.ct_footer = ct_footer;
	}
	public String getCt_program() {
		return ct_program;
	}
	public void setCt_program(String ct_program) {
		this.ct_program = ct_program;
	}
	public String getCt_top_url() {
		return ct_top_url;
	}
	public void setCt_top_url(String ct_top_url) {
		this.ct_top_url = ct_top_url;
	}
	public String getCt_bottom_url() {
		return ct_bottom_url;
	}
	public void setCt_bottom_url(String ct_bottom_url) {
		this.ct_bottom_url = ct_bottom_url;
	}
	public String getCt_img_on() {
		return ct_img_on;
	}
	public void setCt_img_on(String ct_img_on) {
		this.ct_img_on = ct_img_on;
	}
	public String getCt_img_off() {
		return ct_img_off;
	}
	public void setCt_img_off(String ct_img_off) {
		this.ct_img_off = ct_img_off;
	}
	public String getCt_img_status() {
		return ct_img_status;
	}
	public void setCt_img_status(String ct_img_status) {
		this.ct_img_status = ct_img_status;
	}
	public String getCt_popup_w() {
		return ct_popup_w;
	}
	public void setCt_popup_w(String ct_popup_w) {
		this.ct_popup_w = ct_popup_w;
	}
	public String getCt_popup_h() {
		return ct_popup_h;
	}
	public void setCt_popup_h(String ct_popup_h) {
		this.ct_popup_h = ct_popup_h;
	}
	public String getCt_point() {
		return ct_point;
	}
	public void setCt_point(String ct_point) {
		this.ct_point = ct_point;
	}
	public String getCt_manage_num() {
		return ct_manage_num;
	}
	public void setCt_manage_num(String ct_manage_num) {
		this.ct_manage_num = ct_manage_num;
	}
	public String getCt_layout() {
		return ct_layout;
	}
	public void setCt_layout(String ct_layout) {
		this.ct_layout = ct_layout;
	}
	public String getCt_img_on2() {
		return ct_img_on2;
	}
	public void setCt_img_on2(String ct_img_on2) {
		this.ct_img_on2 = ct_img_on2;
	}
	public String getCt_img_off2() {
		return ct_img_off2;
	}
	public void setCt_img_off2(String ct_img_off2) {
		this.ct_img_off2 = ct_img_off2;
	}
	public String getCt_menu_url() {
		return ct_menu_url;
	}
	public void setCt_menu_url(String ct_menu_url) {
		this.ct_menu_url = ct_menu_url;
	}
	public String getCt_tab() {
		return ct_tab;
	}
	public void setCt_tab(String ct_tab) {
		this.ct_tab = ct_tab;
	}
	public String getCt_board_header() {
		return ct_board_header;
	}
	public void setCt_board_header(String ct_board_header) {
		this.ct_board_header = ct_board_header;
	}
	public String getCt_board_footer() {
		return ct_board_footer;
	}
	public void setCt_board_footer(String ct_board_footer) {
		this.ct_board_footer = ct_board_footer;
	}
	public String getCt_group() {
		return ct_group;
	}
	public void setCt_group(String ct_group) {
		this.ct_group = ct_group;
	}
	public String getCt_site_dir() {
		return ct_site_dir;
	}
	public void setCt_site_dir(String ct_site_dir) {
		this.ct_site_dir = ct_site_dir;
	}
	
}
