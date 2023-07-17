package kr.co.nninc.ncms.ebookpdf.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("ebookpdf")
public class EbookpdfDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = 1590655661417106814L;
	
	private String eb_idx;		// 인덱스
	private String eb_code;		// 순서
	private String eb_subject;	// 제목
	private String eb_pdf;		// pdf 파일
	private String eb_img;		// 메인이미지
	private String eb_chk;		// 사용여부
	private String eb_regdate;	// 작성일
	private String site_dir;	// 경로
	
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
	public String getEb_pdf() {
		return eb_pdf;
	}
	public void setEb_pdf(String eb_pdf) {
		this.eb_pdf = eb_pdf;
	}
	public String getEb_img() {
		return eb_img;
	}
	public void setEb_img(String eb_img) {
		this.eb_img = eb_img;
	}
	public String getEb_chk() {
		return eb_chk;
	}
	public void setEb_chk(String eb_chk) {
		this.eb_chk = eb_chk;
	}
	public String getEb_regdate() {
		return eb_regdate;
	}
	public void setEb_regdate(String eb_regdate) {
		this.eb_regdate = eb_regdate;
	}
	public String getSite_dir() {
		return site_dir;
	}
	public void setSite_dir(String site_dir) {
		this.site_dir = site_dir;
	}
	
	

}
