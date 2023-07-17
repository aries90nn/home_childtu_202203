package kr.co.nninc.ncms.common.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("clobselectdto")
public class ClobSelectDTO extends EgovAbstractMapper implements Serializable{

	private static final long serialVersionUID = 4165660958318707556L;
	
	private String exClobField;

	public String getExClobField() {
		return exClobField;
	}

	public void setExClobField(String exClobField) {
		this.exClobField = exClobField;
	}
	
	

}
