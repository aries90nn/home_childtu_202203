package kr.co.nninc.ncms.ebook.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("ebookdto")
public class EbookDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = -2503167301753649285L;

	private String eb_idx;
	private String eb_subject;
	private String eb_code;
	private String eb_skin;
	private String eb_logoimg;
	private String eb_wdate;
	private String eb_mdate;
	private String eb_chk;
	private String eb_pk;
	private String eb_viewtype;
	private String eb_width;
	private String eb_height;
	private String eb_width2;
	private String eb_height2;
	
	public String getEb_idx() {
		return eb_idx;
	}
	public void setEb_idx(String eb_idx) {
		this.eb_idx = eb_idx;
	}
	public String getEb_subject() {
		return eb_subject;
	}
	public void setEb_subject(String eb_subject) {
		this.eb_subject = eb_subject;
	}
	public String getEb_code() {
		return eb_code;
	}
	public void setEb_code(String eb_code) {
		this.eb_code = eb_code;
	}
	public String getEb_skin() {
		return eb_skin;
	}
	public void setEb_skin(String eb_skin) {
		this.eb_skin = eb_skin;
	}
	public String getEb_logoimg() {
		return eb_logoimg;
	}
	public void setEb_logoimg(String eb_logoimg) {
		this.eb_logoimg = eb_logoimg;
	}
	public String getEb_wdate() {
		return eb_wdate;
	}
	public void setEb_wdate(String eb_wdate) {
		this.eb_wdate = eb_wdate;
	}
	public String getEb_mdate() {
		return eb_mdate;
	}
	public void setEb_mdate(String eb_mdate) {
		this.eb_mdate = eb_mdate;
	}
	public String getEb_chk() {
		return eb_chk;
	}
	public void setEb_chk(String eb_chk) {
		this.eb_chk = eb_chk;
	}
	public String getEb_pk() {
		return eb_pk;
	}
	public void setEb_pk(String eb_pk) {
		this.eb_pk = eb_pk;
	}
	public String getEb_viewtype() {
		return eb_viewtype;
	}
	public void setEb_viewtype(String eb_viewtype) {
		this.eb_viewtype = eb_viewtype;
	}
	public String getEb_width() {
		return eb_width;
	}
	public void setEb_width(String eb_width) {
		this.eb_width = eb_width;
	}
	public String getEb_height() {
		return eb_height;
	}
	public void setEb_height(String eb_height) {
		this.eb_height = eb_height;
	}
	public String getEb_width2() {
		return eb_width2;
	}
	public void setEb_width2(String eb_width2) {
		this.eb_width2 = eb_width2;
	}
	public String getEb_height2() {
		return eb_height2;
	}
	public void setEb_height2(String eb_height2) {
		this.eb_height2 = eb_height2;
	}
	
}
