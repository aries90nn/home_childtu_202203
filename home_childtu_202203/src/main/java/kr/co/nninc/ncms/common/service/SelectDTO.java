package kr.co.nninc.ncms.common.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
/*
 * select를 위한 전용 dummy dto
 */
@Repository("selectdto")
public class SelectDTO extends ExtendDTO implements Serializable {
	private static final long serialVersionUID = -6134241035024357605L;

}
