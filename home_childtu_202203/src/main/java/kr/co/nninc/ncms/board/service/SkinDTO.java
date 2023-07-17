package kr.co.nninc.ncms.board.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("skindto")
public class SkinDTO extends EgovAbstractMapper implements Serializable{

	private static final long serialVersionUID = 1177591584354133505L;

	private String s_fields; 			//조회 컬럼(컬럼명부터 시작하면  교체수정, ','로 시작하면 추가)
	private String where_query; 		//조건문('where'로 시작하면 교체수정, 'and'로 시작하면 추가 )
	private String orderby_query; 		//정렬문('order by'로 시작하면 교체수정 , ','로 시작하면 추가)
	private List<HashMap<String,String>> boardList;	//List에만 사용, 목록데이타 전체를 생성해서   저장
	private HashMap<String, String> boardMap = new HashMap<String, String>(); 		//view, write, modify 에만 사용, board Map을 생성해서 저장
	private BoardDTO boardDTO; 			//writeOk, modifyOk 에만 사용, model안에  boarddto를  불러와서  수정후 boardDTO로  저장 
	private String paging_query;		//List에만 사용, 페이징에 사용될 파라미터('&'로 시작하면  추가, 파라미터명부터 시작하면 교체수정. 단, 페이지 파라미터와 a_num은 유지됨 ex:v_page, a_num)
	private String before_msg;			//처리전 리디렉션 메세지 - 권한, 필수값 체크
	private String after_msg;			//처리후 리디렉션 메세지 - 완료메세지등...
	
	
	public String getS_fields() {
		return s_fields;
	}
	public void setS_fields(String s_fields) {
		this.s_fields = s_fields;
	}
	public String getWhere_query() {
		return where_query;
	}
	public void setWhere_query(String where_query) {
		this.where_query = where_query;
	}
	public String getOrderby_query() {
		return orderby_query;
	}
	public void setOrderby_query(String orderby_query) {
		this.orderby_query = orderby_query;
	}
	public List<HashMap<String, String>> getBoardList() {
		return boardList;
	}
	public void setBoardList(List<HashMap<String, String>> boardList) {
		this.boardList = boardList;
	}
	public HashMap<String, String> getBoardMap() {
		return boardMap;
	}
	public void setBoardMap(HashMap<String, String> boardMap) {
		this.boardMap = boardMap;
	}
	public BoardDTO getBoardDTO() {
		return boardDTO;
	}
	public void setBoardDTO(BoardDTO boardDTO) {
		this.boardDTO = boardDTO;
	}
	public String getPaging_query() {
		return paging_query;
	}
	public void setPaging_query(String paging_query) {
		this.paging_query = paging_query;
	}
	public String getBefore_msg() {
		return before_msg;
	}
	public void setBefore_msg(String before_msg) {
		this.before_msg = before_msg;
	}
	public String getAfter_msg() {
		return after_msg;
	}
	public void setAfter_msg(String after_msg) {
		this.after_msg = after_msg;
	}

	
	
}
