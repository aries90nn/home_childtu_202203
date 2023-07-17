package kr.co.nninc.ncms.stock.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("stockcountdto")
public class StockCountDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = -8566765346915594374L;
	
	private String sc_idx;
	private String sc_count; // 재고
	private String sc_receiv; // 총 입고
	private String sc_forward; // 총 출고
	private String sc_last; // 작년 재고
	
	
	public String getSc_idx() {
		return sc_idx;
	}
	public void setSc_idx(String sc_idx) {
		this.sc_idx = sc_idx;
	}
	public String getSc_count() {
		return sc_count;
	}
	public void setSc_count(String sc_count) {
		this.sc_count = sc_count;
	}
	public String getSc_receiv() {
		return sc_receiv;
	}
	public void setSc_receiv(String sc_receiv) {
		this.sc_receiv = sc_receiv;
	}
	public String getSc_forward() {
		return sc_forward;
	}
	public void setSc_forward(String sc_forward) {
		this.sc_forward = sc_forward;
	}
	public String getSc_last() {
		return sc_last;
	}
	public void setSc_last(String sc_last) {
		this.sc_last = sc_last;
	}
	
	

}
