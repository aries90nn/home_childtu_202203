package kr.co.nninc.ncms.poll.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("pollresultdto")
public class PollResultDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 3916007546441139715L;

	private String por_idx;
	private String por_result;
	private String por_mid;
	private String por_wdate;
	private String po_pk;
	private String poq_idx;
	private String por_ip;
	private String por_ukey;
	private String por_name;
	
	public String getPor_name() {
		return por_name;
	}
	public void setPor_name(String por_name) {
		this.por_name = por_name;
	}
	public String getPor_idx() {
		return por_idx;
	}
	public void setPor_idx(String por_idx) {
		this.por_idx = por_idx;
	}
	public String getPor_result() {
		return por_result;
	}
	public void setPor_result(String por_result) {
		this.por_result = por_result;
	}
	public String getPor_mid() {
		return por_mid;
	}
	public void setPor_mid(String por_mid) {
		this.por_mid = por_mid;
	}
	public String getPor_wdate() {
		return por_wdate;
	}
	public void setPor_wdate(String por_wdate) {
		this.por_wdate = por_wdate;
	}
	public String getPo_pk() {
		return po_pk;
	}
	public void setPo_pk(String po_pk) {
		this.po_pk = po_pk;
	}
	public String getPoq_idx() {
		return poq_idx;
	}
	public void setPoq_idx(String poq_idx) {
		this.poq_idx = poq_idx;
	}
	public String getPor_ip() {
		return por_ip;
	}
	public void setPor_ip(String por_ip) {
		this.por_ip = por_ip;
	}
	public String getPor_ukey() {
		return por_ukey;
	}
	public void setPor_ukey(String por_ukey) {
		this.por_ukey = por_ukey;
	}
	
	
}
