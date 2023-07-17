package kr.co.nninc.ncms.poll.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("pollconfdto")
public class PollConfDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = 2947586893916274955L;

	private String poc_idx;
	private String poc_topinclude;
	private String poc_tophtml;
	private String poc_btminclude;
	private String poc_btmhtml;
	private String poc_wdate;
	
	public String getPoc_idx() {
		return poc_idx;
	}
	public void setPoc_idx(String poc_idx) {
		this.poc_idx = poc_idx;
	}
	public String getPoc_topinclude() {
		return poc_topinclude;
	}
	public void setPoc_topinclude(String poc_topinclude) {
		this.poc_topinclude = poc_topinclude;
	}
	public String getPoc_tophtml() {
		return poc_tophtml;
	}
	public void setPoc_tophtml(String poc_tophtml) {
		this.poc_tophtml = poc_tophtml;
	}
	public String getPoc_btminclude() {
		return poc_btminclude;
	}
	public void setPoc_btminclude(String poc_btminclude) {
		this.poc_btminclude = poc_btminclude;
	}
	public String getPoc_btmhtml() {
		return poc_btmhtml;
	}
	public void setPoc_btmhtml(String poc_btmhtml) {
		this.poc_btmhtml = poc_btmhtml;
	}
	public String getPoc_wdate() {
		return poc_wdate;
	}
	public void setPoc_wdate(String poc_wdate) {
		this.poc_wdate = poc_wdate;
	}

}
