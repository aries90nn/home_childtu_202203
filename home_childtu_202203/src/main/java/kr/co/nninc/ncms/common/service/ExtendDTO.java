package kr.co.nninc.ncms.common.service;

import java.io.Serializable;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

public class ExtendDTO extends EgovAbstractMapper implements Serializable{

	private static final long serialVersionUID = 2259581147981853759L;

	private String exTableName;		//테이블명
	private String exWhereQuery;	//검색조건
	private String exOrderByQuery;	//정렬기준
	private String exGroupByQuery;	//그룹화
	private String exColumn;		//조회컬럼명(다수는 ,로 구분)
	private String exKeyColumn;		//키컬럼(mssql 페이징에 사용된다, clob조회시에도 반드시 선언필요)
	private int exRecordCount;		//조회할 레코드수
	private int exPage;				//조회할 페이지(페이징)
	private int exStartCount;		//시작레코드카운트(페이징:mysql, 오라클등..) // dao에서 자동으로 생성됨
	private int exEndCount;			//마지막레코드카운트 (페이징:오라클등..) // dao에서 자동으로 생성됨
	
	public int getExPage() {
		return exPage;
	}
	public void setExPage(int exPage) {
		this.exPage = exPage;
	}
	public int getExEndCount() {
		return exEndCount;
	}
	public void setExEndCount(int exEndCount) {
		this.exEndCount = exEndCount;
	}
	public int getExStartCount() {
		return exStartCount;
	}
	public void setExStartCount(int exStartCount) {
		this.exStartCount = exStartCount;
	}
	public int getExRecordCount() {
		return exRecordCount;
	}
	public void setExRecordCount(int exRecordCount) {
		this.exRecordCount = exRecordCount;
	}
	public String getExTableName() {
		return exTableName;
	}
	public void setExTableName(String exTableName) {
		this.exTableName = exTableName;
	}
	public String getExWhereQuery() {
		return exWhereQuery;
	}
	public void setExWhereQuery(String exWhereQuery) {
		this.exWhereQuery = exWhereQuery;
	}
	public String getExColumn() {
		return exColumn;
	}
	public void setExColumn(String exColumn) {
		this.exColumn = exColumn;
	}
	public void setExColumn( Object dto ) {
		ExtendDAO exdao = new ExtendDAO();
		this.exColumn = exdao.getFieldNames(dto);
	}
	public void setExColumn( Object dto, String removefields ) {
		ExtendDAO exdao = new ExtendDAO();
		this.exColumn = exdao.getFieldNames(dto, removefields);
	}
	public String getExOrderByQuery() {
		return exOrderByQuery;
	}
	public void setExOrderByQuery(String exOrderByQuery) {
		this.exOrderByQuery = exOrderByQuery;
	}
	public String getExKeyColumn() {
		return exKeyColumn;
	}
	public void setExKeyColumn(String exKeyColumn) {
		this.exKeyColumn = exKeyColumn;
	}
	
	public String getExGroupByQuery() {
		return exGroupByQuery;
	}
	public void setExGroupByQuery(String exGroupByQuery) {
		this.exGroupByQuery = exGroupByQuery;
	}
}
