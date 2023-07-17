package kr.co.nninc.ncms.edusat.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("edusatseasondto")
public class EdusatSeasonDTO  extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = 3848440885676544365L;

	private String es_num;
	private String es_libcode;
	private String es_name;
	private String es_chk;
	private String es_wid;
	private String es_regdate;
	public String getEs_num() {
		return es_num;
	}
	public void setEs_num(String es_num) {
		this.es_num = es_num;
	}
	public String getEs_libcode() {
		return es_libcode;
	}
	public void setEs_libcode(String es_libcode) {
		this.es_libcode = es_libcode;
	}
	public String getEs_name() {
		return es_name;
	}
	public void setEs_name(String es_name) {
		this.es_name = es_name;
	}
	public String getEs_chk() {
		return es_chk;
	}
	public void setEs_chk(String es_chk) {
		this.es_chk = es_chk;
	}
	public String getEs_wid() {
		return es_wid;
	}
	public void setEs_wid(String es_wid) {
		this.es_wid = es_wid;
	}
	public String getEs_regdate() {
		return es_regdate;
	}
	public void setEs_regdate(String es_regdate) {
		this.es_regdate = es_regdate;
	}
}
