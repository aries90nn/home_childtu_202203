package kr.co.nninc.ncms.manager_memo.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

/**
 * 메모관리
 * 
 * @author 나눔
 * @since 2018.01.27
 * @version 1.0
 */
@Repository("managermemodto")
public class ManagerMemoDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = -7648918407660637635L;

	private String mm_idx;
	private String mm_content;
	private String mm_wdate;
	private String mm_id;
	
	public String getMm_idx() {
		return mm_idx;
	}
	public void setMm_idx(String mm_idx) {
		this.mm_idx = mm_idx;
	}
	public String getMm_content() {
		return mm_content;
	}
	public void setMm_content(String mm_content) {
		this.mm_content = mm_content;
	}
	public String getMm_wdate() {
		return mm_wdate;
	}
	public void setMm_wdate(String mm_wdate) {
		this.mm_wdate = mm_wdate;
	}
	public String getMm_id() {
		return mm_id;
	}
	public void setMm_id(String mm_id) {
		this.mm_id = mm_id;
	}
	
	
}
