package kr.co.nninc.ncms.poll.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;


@Repository("polldto")
public class PollDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = -3029952739114949668L;
	private String po_idx;
	private String po_subject;
	private String po_group;
	private String po_addid;
	private String po_sdate;
	private String po_edate;
	private String po_chk;
	private String po_code;
	private String po_pk;
	private String po_wdate;
	private String po_mdate;
	private String po_count;
	private String site_dir;
	
	public String getPo_idx() {
		return po_idx;
	}
	public void setPo_idx(String po_idx) {
		this.po_idx = po_idx;
	}
	public String getPo_subject() {
		return po_subject;
	}
	public void setPo_subject(String po_subject) {
		this.po_subject = po_subject;
	}
	public String getPo_group() {
		return po_group;
	}
	public void setPo_group(String po_group) {
		this.po_group = po_group;
	}
	public String getPo_addid() {
		return po_addid;
	}
	public void setPo_addid(String po_addid) {
		this.po_addid = po_addid;
	}
	public String getPo_sdate() {
		return po_sdate;
	}
	public void setPo_sdate(String po_sdate) {
		this.po_sdate = po_sdate;
	}
	public String getPo_edate() {
		return po_edate;
	}
	public void setPo_edate(String po_edate) {
		this.po_edate = po_edate;
	}
	public String getPo_chk() {
		return po_chk;
	}
	public void setPo_chk(String po_chk) {
		this.po_chk = po_chk;
	}
	public String getPo_code() {
		return po_code;
	}
	public void setPo_code(String po_code) {
		this.po_code = po_code;
	}
	public String getPo_pk() {
		return po_pk;
	}
	public void setPo_pk(String po_pk) {
		this.po_pk = po_pk;
	}
	public String getPo_wdate() {
		return po_wdate;
	}
	public void setPo_wdate(String po_wdate) {
		this.po_wdate = po_wdate;
	}
	public String getPo_mdate() {
		return po_mdate;
	}
	public void setPo_mdate(String po_mdate) {
		this.po_mdate = po_mdate;
	}
	public String getPo_count() {
		return po_count;
	}
	public void setPo_count(String po_count) {
		this.po_count = po_count;
	}
	public String getSite_dir() {
		return site_dir;
	}
	public void setSite_dir(String site_dir) {
		this.site_dir = site_dir;
	}

	
}

