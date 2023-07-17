package kr.co.nninc.ncms.board.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board.service.SkinService;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 스킨 별도처리를 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.05.31
 * @version 1.2
 */
@Service("nninc_simpleService")
public class Nninc_simpleServiceImpl extends EgovAbstractServiceImpl implements SkinService {
	/*
	 * 기본 스킨처리 메소드 종류 - 각 스킨별 list, view, write, writeOk, modify, modifyOk
	 * skinDTO에 필드 종류 get,set사용
	 * -> String s_fields : 조회 컬럼(컬럼명부터 시작하면  교체수정, ','로 시작하면 추가)
	 * -> String where_query : 조건문('where'로 시작하면 교체수정, 'and'로 시작하면 추가 )
	 * -> String orderby_query : 정렬문('order by'로 시작하면 교체수정 , ','로 시작하면 추가)
	 * -> List<HashMap<String,String> boardList : list에만 사용, 목록데이타 전체를 생성해서  저장
	 * -> HashMap<String, String> boardMap : view, write, modify 에만 사용, board Map을  저장 (b_temp필드 조작시 사용)
	 * -> BoardDTO boardDTO : writeOk, modifyOk 에만 사용, model안에  boarddto를  불러와서  수정후 boardDTO로  저장  (b_temp필드 조작시 사용)
	 * -> String paging_query : List에만 사용, 페이징에 사용될 파라미터('&'로 시작하면  추가, 파라미터명부터 시작하면 교체수정. 단, 페이지번호와 a_num은 유지됨 ex:v_page, a_num)
	 * -> String before_msg : 처리전 리디렉션 메세지 - 권한, 필수값 체크
	 * -> String after_msg : 처리후 리디렉션 메세지 - 완료메세지등...
	 * 이외에 게시판 config, 목록/조회/쓰기/삭제 권한도 model내에 request객체를 통해 조회/수정할 수 있다.
	 * 추가 -> 기본페이지(list,write,view....)외에 페이지접속 및 처리가 필요할 경우 처리명으로 메소드생성 (예:bookSearchResult)
	 */
	
	/**
	 * @title : nninc_simple 스킨  List 처리 (내용삭제하지말고 예제(복사)용으로 사용)
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		//처리전 리디렉션
		//if(true){
		//	skinDTO.setBefore_msg( messageService.backMsg(model, "권한이 없습니다.") );
		//	return;
		//}
		
		//게시물 목록 데이타 생성
		//SelectDTO selectdto = new SelectDTO();
		//selectdto.setExTableName("board");
		//selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		//selectdto.setExColumn("b_num, b_subject");
		//List<HashMap<String,String>> boardList = exdao.selectQueryTable(selectdto);
		//skinDTO.setBoardList(boardList);
		
		//select쿼리 추가 또는 수정
		//skinDTO.setS_fields("b_temp1");
		//skinDTO.setWhere_query("and 1=1");
		//skinDTO.setOrderby_query("order by b_subject asc");
		
		
		//페이징 쿼리 추가 또는 수정
		//skinDTO.setPaging_query("sh_month=1");

		
		//게시판 권한 조회 및 수정
		//request.setAttribute("is_ad_cms", "Y");
		//request.setAttribute("is_list", "Y");
		//request.setAttribute("is_read", "Y");
		//request.setAttribute("is_write", "Y");
		//request.setAttribute("is_delete", "Y");
		//request.setAttribute("is_reply", "Y");
		
		//처리후 리디렉션
		//skinDTO.setAfter_msg( messageService.redirectMsg(model, "처리완료 되었습니다.", "/ncms") );

	}

	@Override
	public void view(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void modify(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteOk(Model model) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
