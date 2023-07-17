package kr.co.nninc.ncms.stock.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("stockdto")
public class StockDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = -1166769157521344898L;
	
	private String st_idx; // index
	private String st_code; // 순서
	private String st_regdate; // 등록일
	private String st_use; // 사용 X
	
	//private String 	st_stock; // 작업 후 갯수
	private String st_date; // 날짜
	//private String st_gubun; // 구분
	private String st_work; // 작업
	private String st_cnt; // 수량
	private String st_program; // 프로그램
	private String st_delivery; // 배송
	private String st_history; // 비고
	private String st_ext; // 비고
	
	public String getSt_idx() {
		return st_idx;
	}
	public void setSt_idx(String st_idx) {
		this.st_idx = st_idx;
	}
	public String getSt_code() {
		return st_code;
	}
	public void setSt_code(String st_code) {
		this.st_code = st_code;
	}
	public String getSt_regdate() {
		return st_regdate;
	}
	public void setSt_regdate(String st_regdate) {
		this.st_regdate = st_regdate;
	}
	public String getSt_use() {
		return st_use;
	}
	public void setSt_use(String st_use) {
		this.st_use = st_use;
	}
	public String getSt_date() {
		return st_date;
	}
	public void setSt_date(String st_date) {
		this.st_date = st_date;
	}
	public String getSt_work() {
		return st_work;
	}
	public void setSt_work(String st_work) {
		this.st_work = st_work;
	}
	public String getSt_cnt() {
		return st_cnt;
	}
	public void setSt_cnt(String st_cnt) {
		this.st_cnt = st_cnt;
	}
	public String getSt_program() {
		return st_program;
	}
	public void setSt_program(String st_program) {
		this.st_program = st_program;
	}
	public String getSt_delivery() {
		return st_delivery;
	}
	public void setSt_delivery(String st_delivery) {
		this.st_delivery = st_delivery;
	}
	public String getSt_ext() {
		return st_ext;
	}
	public void setSt_ext(String st_ext) {
		this.st_ext = st_ext;
	}
	public String getSt_history() {
		return st_history;
	}
	public void setSt_history(String st_history) {
		this.st_history = st_history;
	}
	
	
	
}
