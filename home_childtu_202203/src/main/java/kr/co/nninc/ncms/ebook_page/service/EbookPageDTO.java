package kr.co.nninc.ncms.ebook_page.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("ebookpagedto")
public class EbookPageDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 7457704564645675524L;

	private String ebp_idx;
	private String ebp_subject;
	private String ebp_code;
	private String ebp_pageimg;
	private String ebp_wdate;
	private String ebp_mdate;
	private String ebp_chk;
	private String eb_pk;
	private String ebp_keyword;
	
	public String getEbp_idx() {
		return ebp_idx;
	}
	public void setEbp_idx(String ebp_idx) {
		this.ebp_idx = ebp_idx;
	}
	public String getEbp_subject() {
		return ebp_subject;
	}
	public void setEbp_subject(String ebp_subject) {
		this.ebp_subject = ebp_subject;
	}
	public String getEbp_code() {
		return ebp_code;
	}
	public void setEbp_code(String ebp_code) {
		this.ebp_code = ebp_code;
	}
	public String getEbp_pageimg() {
		return ebp_pageimg;
	}
	public void setEbp_pageimg(String ebp_pageimg) {
		this.ebp_pageimg = ebp_pageimg;
	}
	public String getEbp_wdate() {
		return ebp_wdate;
	}
	public void setEbp_wdate(String ebp_wdate) {
		this.ebp_wdate = ebp_wdate;
	}
	public String getEbp_mdate() {
		return ebp_mdate;
	}
	public void setEbp_mdate(String ebp_mdate) {
		this.ebp_mdate = ebp_mdate;
	}
	public String getEbp_chk() {
		return ebp_chk;
	}
	public void setEbp_chk(String ebp_chk) {
		this.ebp_chk = ebp_chk;
	}
	public String getEb_pk() {
		return eb_pk;
	}
	public void setEb_pk(String eb_pk) {
		this.eb_pk = eb_pk;
	}
	public String getEbp_keyword() {
		return ebp_keyword;
	}
	public void setEbp_keyword(String ebp_keyword) {
		this.ebp_keyword = ebp_keyword;
	}
	
}
