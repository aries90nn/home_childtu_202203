package kr.co.nninc.ncms.banner2.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("banner2dto")
public class Banner2DTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 213658689155473824L;

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
	private String b_main_img;
	private String b_l_win;
	private String b_l_page;
	private String w_width;
	private String w_height;
	private String w_top;
	private String w_left;
	private String scrollbars;
	private String toolbar;
	private String menubar;
	private String locations;
	private String content;
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
	public String getB_main_img() {
		return b_main_img;
	}
	public void setB_main_img(String b_main_img) {
		this.b_main_img = b_main_img;
	}
	public String getB_l_win() {
		return b_l_win;
	}
	public void setB_l_win(String b_l_win) {
		this.b_l_win = b_l_win;
	}
	public String getB_l_page() {
		return b_l_page;
	}
	public void setB_l_page(String b_l_page) {
		this.b_l_page = b_l_page;
	}
	public String getW_width() {
		return w_width;
	}
	public void setW_width(String w_width) {
		this.w_width = w_width;
	}
	public String getW_height() {
		return w_height;
	}
	public void setW_height(String w_height) {
		this.w_height = w_height;
	}
	public String getW_top() {
		return w_top;
	}
	public void setW_top(String w_top) {
		this.w_top = w_top;
	}
	public String getW_left() {
		return w_left;
	}
	public void setW_left(String w_left) {
		this.w_left = w_left;
	}
	public String getScrollbars() {
		return scrollbars;
	}
	public void setScrollbars(String scrollbars) {
		this.scrollbars = scrollbars;
	}
	public String getToolbar() {
		return toolbar;
	}
	public void setToolbar(String toolbar) {
		this.toolbar = toolbar;
	}
	public String getMenubar() {
		return menubar;
	}
	public void setMenubar(String menubar) {
		this.menubar = menubar;
	}
	public String getLocations() {
		return locations;
	}
	public void setLocations(String locations) {
		this.locations = locations;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
}
