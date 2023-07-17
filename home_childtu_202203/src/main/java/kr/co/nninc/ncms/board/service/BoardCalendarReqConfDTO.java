package kr.co.nninc.ncms.board.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("calendarreqconfdto")
public class BoardCalendarReqConfDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = 5561337737383311157L;
	
	private String be_num;
	private String be_a_num;
	private String be_rtype;
	private String be_date;
	private String be_type;
	private String be_m_id;
	private String be_regdate;
	private String be_time1;
	private String be_count1;
	private String be_time2;
	private String be_count2;
	private String be_time3;
	private String be_count3;
	private String be_time4;
	private String be_count4;
	
	
	public String getBe_num() {
		return be_num;
	}
	public void setBe_num(String be_num) {
		this.be_num = be_num;
	}
	public String getBe_a_num() {
		return be_a_num;
	}
	public void setBe_a_num(String be_a_num) {
		this.be_a_num = be_a_num;
	}
	public String getBe_rtype() {
		return be_rtype;
	}
	public void setBe_rtype(String be_rtype) {
		this.be_rtype = be_rtype;
	}
	public String getBe_date() {
		return be_date;
	}
	public void setBe_date(String be_date) {
		this.be_date = be_date;
	}
	public String getBe_type() {
		return be_type;
	}
	public void setBe_type(String be_type) {
		this.be_type = be_type;
	}
	public String getBe_m_id() {
		return be_m_id;
	}
	public void setBe_m_id(String be_m_id) {
		this.be_m_id = be_m_id;
	}
	public String getBe_regdate() {
		return be_regdate;
	}
	public void setBe_regdate(String be_regdate) {
		this.be_regdate = be_regdate;
	}
	public String getBe_time1() {
		return be_time1;
	}
	public void setBe_time1(String be_time1) {
		this.be_time1 = be_time1;
	}
	public String getBe_count1() {
		return be_count1;
	}
	public void setBe_count1(String be_count1) {
		this.be_count1 = be_count1;
	}
	public String getBe_time2() {
		return be_time2;
	}
	public void setBe_time2(String be_time2) {
		this.be_time2 = be_time2;
	}
	public String getBe_count2() {
		return be_count2;
	}
	public void setBe_count2(String be_count2) {
		this.be_count2 = be_count2;
	}
	public String getBe_time3() {
		return be_time3;
	}
	public void setBe_time3(String be_time3) {
		this.be_time3 = be_time3;
	}
	public String getBe_count3() {
		return be_count3;
	}
	public void setBe_count3(String be_count3) {
		this.be_count3 = be_count3;
	}
	public String getBe_time4() {
		return be_time4;
	}
	public void setBe_time4(String be_time4) {
		this.be_time4 = be_time4;
	}
	public String getBe_count4() {
		return be_count4;
	}
	public void setBe_count4(String be_count4) {
		this.be_count4 = be_count4;
	}
	
	

}
