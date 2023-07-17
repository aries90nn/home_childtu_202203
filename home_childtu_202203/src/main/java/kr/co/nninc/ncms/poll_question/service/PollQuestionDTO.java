package kr.co.nninc.ncms.poll_question.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("pollquestiondto")
public class PollQuestionDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = -6800796865331781595L;

	private String poq_idx;
	private String po_pk;
	private String poq_code;
	private String poq_topmemo;
	private String poq_question;
	private String poq_type;
	private String poq_multi;
	private String poq_chk;
	private String poq_bogi1;
	private String poq_bogi2;
	private String poq_bogi3;
	private String poq_bogi4;
	private String poq_bogi5;
	private String poq_bogi6;
	private String poq_bogi7;
	private String poq_bogi8;
	private String poq_bogi9;
	private String poq_bogi10;
	private String poq_wdate;
	private String poq_mdate;
	
	public String getPoq_idx() {
		return poq_idx;
	}
	public void setPoq_idx(String poq_idx) {
		this.poq_idx = poq_idx;
	}
	public String getPo_pk() {
		return po_pk;
	}
	public void setPo_pk(String po_pk) {
		this.po_pk = po_pk;
	}
	public String getPoq_code() {
		return poq_code;
	}
	public void setPoq_code(String poq_code) {
		this.poq_code = poq_code;
	}
	public String getPoq_topmemo() {
		return poq_topmemo;
	}
	public void setPoq_topmemo(String poq_topmemo) {
		this.poq_topmemo = poq_topmemo;
	}
	public String getPoq_question() {
		return poq_question;
	}
	public void setPoq_question(String poq_question) {
		this.poq_question = poq_question;
	}
	public String getPoq_type() {
		return poq_type;
	}
	public void setPoq_type(String poq_type) {
		this.poq_type = poq_type;
	}
	public String getPoq_multi() {
		return poq_multi;
	}
	public void setPoq_multi(String poq_multi) {
		this.poq_multi = poq_multi;
	}
	public String getPoq_chk() {
		return poq_chk;
	}
	public void setPoq_chk(String poq_chk) {
		this.poq_chk = poq_chk;
	}
	public String getPoq_bogi1() {
		return poq_bogi1;
	}
	public void setPoq_bogi1(String poq_bogi1) {
		this.poq_bogi1 = poq_bogi1;
	}
	public String getPoq_bogi2() {
		return poq_bogi2;
	}
	public void setPoq_bogi2(String poq_bogi2) {
		this.poq_bogi2 = poq_bogi2;
	}
	public String getPoq_bogi3() {
		return poq_bogi3;
	}
	public void setPoq_bogi3(String poq_bogi3) {
		this.poq_bogi3 = poq_bogi3;
	}
	public String getPoq_bogi4() {
		return poq_bogi4;
	}
	public void setPoq_bogi4(String poq_bogi4) {
		this.poq_bogi4 = poq_bogi4;
	}
	public String getPoq_bogi5() {
		return poq_bogi5;
	}
	public void setPoq_bogi5(String poq_bogi5) {
		this.poq_bogi5 = poq_bogi5;
	}
	public String getPoq_bogi6() {
		return poq_bogi6;
	}
	public void setPoq_bogi6(String poq_bogi6) {
		this.poq_bogi6 = poq_bogi6;
	}
	public String getPoq_bogi7() {
		return poq_bogi7;
	}
	public void setPoq_bogi7(String poq_bogi7) {
		this.poq_bogi7 = poq_bogi7;
	}
	public String getPoq_bogi8() {
		return poq_bogi8;
	}
	public void setPoq_bogi8(String poq_bogi8) {
		this.poq_bogi8 = poq_bogi8;
	}
	public String getPoq_bogi9() {
		return poq_bogi9;
	}
	public void setPoq_bogi9(String poq_bogi9) {
		this.poq_bogi9 = poq_bogi9;
	}
	public String getPoq_bogi10() {
		return poq_bogi10;
	}
	public void setPoq_bogi10(String poq_bogi10) {
		this.poq_bogi10 = poq_bogi10;
	}
	public String getPoq_wdate() {
		return poq_wdate;
	}
	public void setPoq_wdate(String poq_wdate) {
		this.poq_wdate = poq_wdate;
	}
	public String getPoq_mdate() {
		return poq_mdate;
	}
	public void setPoq_mdate(String poq_mdate) {
		this.poq_mdate = poq_mdate;
	}
}
