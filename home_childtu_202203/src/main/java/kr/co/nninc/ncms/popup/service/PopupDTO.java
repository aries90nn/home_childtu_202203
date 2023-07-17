package kr.co.nninc.ncms.popup.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("popupdto")
public class PopupDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = -654437092397479348L;
	
	private String idx;
	private String w_width;
	private String w_height;
	private String w_top;
	private String w_left;
	private String ck_chk;
	private String ck_val;
	private String scrollbars;
	private String toolbar;
	private String menubar;
	private String locations;
	private String subject;
	private String content;
	private String w_chk;
	private String wdate;
	private String mdate;
	private String sdate;
	private String edate;
	private String tot_chk;
	private String ck_layer;
	private String unlimited;
	private String site_dir;
	private String pos;
	
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
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
	public String getCk_chk() {
		return ck_chk;
	}
	public void setCk_chk(String ck_chk) {
		this.ck_chk = ck_chk;
	}
	public String getCk_val() {
		return ck_val;
	}
	public void setCk_val(String ck_val) {
		this.ck_val = ck_val;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getW_chk() {
		return w_chk;
	}
	public void setW_chk(String w_chk) {
		this.w_chk = w_chk;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public String getMdate() {
		return mdate;
	}
	public void setMdate(String mdate) {
		this.mdate = mdate;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getTot_chk() {
		return tot_chk;
	}
	public void setTot_chk(String tot_chk) {
		this.tot_chk = tot_chk;
	}
	public String getCk_layer() {
		return ck_layer;
	}
	public void setCk_layer(String ck_layer) {
		this.ck_layer = ck_layer;
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
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}

}
