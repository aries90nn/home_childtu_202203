package kr.co.nninc.ncms.board_config.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board_access.service.BoardAccessDTO;
import kr.co.nninc.ncms.board_config.service.BoardConfigDTO;
import kr.co.nninc.ncms.board_config.service.BoardConfigService;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 게시판 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.01.09
 * @version 1.1
 */
@Service("boardConfigService")
public class BoardConfigServiceImpl extends EgovAbstractServiceImpl implements BoardConfigService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** FileUtil */
	@Resource(name="fileutil")
	private FileUtil fileutil;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**
	 * @title : 게시판리스트
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//select 전용 dto
		SelectDTO selectdto = new  SelectDTO();
		
		String v_keyword = Func.nvl( request.getParameter("v_keyword") );
		String v_search = Func.nvl( request.getParameter("v_search") );
		String whereQuery = "";
		if(!"".equals(v_keyword)){
			whereQuery = "where "+v_search+" like '%"+exdao.filter(v_keyword)+"%' ";
		}
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			whereQuery += " and a_site_dir = '"+builder_dir+"'";
		}
		
		selectdto.setExTableName("board_config");
		selectdto.setExKeyColumn("a_idx");	//고유컬럼 설정 필수
		String s_fields = "a_num, a_bbsname, a_level, a_type, a_tablename, a_ad_cms_id, a_ad_cms, a_cate";
		s_fields += ", a_upload, a_upload_len, a_nofilesize, a_nofile";
		selectdto.setExColumn(s_fields);
		selectdto.setExOrderByQuery("ORDER BY a_code, a_bbsname ASC");
		selectdto.setExWhereQuery(whereQuery);
		List<HashMap<String, String>>boardList = exdao.selectQueryTable(selectdto);
		
		// 리스트
		selectdto.setExOrderByQuery("");
		for(int i=0;i<boardList.size();i++) {
			HashMap<String, String>datamap = (HashMap<String, String>) boardList.get(i);
			selectdto = new SelectDTO();
			selectdto.setExTableName("board");
			selectdto.setExColumn("count(*) as cnt");
			selectdto.setExWhereQuery("where a_num = '"+datamap.get("a_num")+"'");
			
			// 해당 게시판 전체 게시글수
			String tot_count = exdao.selectQueryColumn(selectdto);
			datamap.put("tot_count", tot_count);
			
			// 해당 게시판 오늘 게시글수
			selectdto.setExWhereQuery(" where a_num = '"+datamap.get("a_num")+"' and b_regdate like '"+Func.date("Y-m-d")+"%' ");
			String today_cnt = exdao.selectQueryColumn(selectdto);
			datamap.put("today_cnt", today_cnt);
		}
		
		
		request.setAttribute("boardList", boardList);
		request.setAttribute("v_keyword", v_keyword);
		request.setAttribute("v_search", v_search);
		request.setAttribute("boardList", boardList);
		//현재페이지 URL전송
		Func.getNowPage(request);
		
	}
	
	/**
	 * @title : 게시판 생성 폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		BoardConfigDTO board = (BoardConfigDTO) Func.requestAll(map.get("boarddto"));
		
		//select전용 dto
		BoardConfigDTO selectdto = new BoardConfigDTO();
		
		//디렉토리 위치
		ArrayList<HashMap<String,String>> dir_list = new ArrayList<HashMap<String,String>>();
		String filePath = fileutil.realPath(request, "/nanum/site/board");
		String[] dir = Func.openDir( filePath );
		for(int i=0;i<=dir.length-1;i++){
			if("_".equals( dir[i].substring(0, 1) )){continue;}
			String skin_name = Func.fileGetContents( filePath+"/"+dir[i]+"/skinname.txt" );
			if(skin_name == null){skin_name = dir[i];}
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("dir", dir[i]);
			hm.put("skin_name", skin_name);
			dir_list.add(hm);
		}
		request.setAttribute("dir_list", dir_list);
		
		// 게시판 리스트
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_tablename, a_bbsname");
		selectdto.setExOrderByQuery("ORDER BY a_code, a_bbsname ASC");
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
		request.setAttribute("boardList", boardList);
		
		
		//기본값 설정
		board.setA_level("nninc_simple");
		board.setA_type("N");
		board.setA_imgline("4");
		board.setA_imgwidth("130");
		board.setA_member("N");
		board.setA_ad_cms("N");
		board.setA_cate("N");
		board.setA_email("N");
		board.setA_email_req("N");
		board.setA_phone("N");
		board.setA_phone_req("N");
		board.setA_home("N");
		board.setA_home_req("N");
		board.setA_content("Y");
		board.setA_content_req("Y");
		board.setA_jumin("N");
		board.setA_new("1");
		board.setA_upload("Y");
		board.setA_upload_len("2");
		board.setA_nofile("hwp,doc,ppt,xls,txt,gif,jpg,docx,pptx,xlsx,zip,alz,rar,pdf");
		board.setA_nofilesize("5");
		board.setA_reply("N");
		board.setA_replyOpt("2");
		board.setA_command("N");
		board.setA_date_list("N");
		board.setA_width("100%");
		board.setA_displaysu("10");
		board.setA_viewType("1");
		board.setA_lt_a("Y");
		board.setA_lt_b("Y");
		board.setA_lt_c("Y");
		board.setA_lt_e("Y");
		board.setA_lt_f("N");
		board.setA_skin("1");
		board.setA_edit("Y");
		board.setA_password("N");
		board.setA_blind("N");
		board.setA_rss("N");
		board.setA_sns("N");
		board.setA_photoview("N");
		board.setA_multiupload("N");
		board.setA_garbage("N");
		board.setA_show("N");
		board.setA_show_term("12");
		board.setA_date_change("N");
		board.setA_ftemp1("N");
		board.setA_ftemp1_type("T");
		board.setA_ftemp1_req("N");
		board.setA_ftemp2("N");
		board.setA_ftemp2_type("T");
		board.setA_ftemp2_req("N");
		board.setA_ftemp3("N");
		board.setA_ftemp3_type("T");
		board.setA_ftemp3_req("N");
		board.setA_ftemp4("N");
		board.setA_ftemp4_type("T");
		board.setA_ftemp4_req("N");
		board.setA_ftemp5("N");
		board.setA_ftemp5_type("T");
		board.setA_ftemp5_req("N");
		board.setA_ftemp6("N");
		board.setA_ftemp6_type("T");
		board.setA_ftemp6_req("N");
		board.setA_ftemp7("N");
		board.setA_ftemp7_type("T");
		board.setA_ftemp7_req("N");
		board.setA_ftemp8("N");
		board.setA_ftemp8_type("T");
		board.setA_ftemp8_req("N");
		
		board.setA_sdate(Func.date("Y-m-d"));
		board.setA_edate(Func.date("Y-m-d"));
		request.setAttribute("board", board);
		
		//게시판고유번호
		request.setAttribute("a_num_str", Func.get_idx_add());
		
		//처리타입
		request.setAttribute("prc_type", "write");
				
		//현재 URL 전송
		Func.getNowPage(request);

	}



	/**
	 * @title : 게시판 등록하기
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		BoardConfigDTO board = (BoardConfigDTO) map.get("boarddto");

		//select 전용 dto
		BoardConfigDTO selectdto = new BoardConfigDTO();
		
		//사용기간 - 사용안함
		board.setA_sdate("--");
		board.setA_edate("--");
		
		if( !"Y".equals(board.getA_email()) ){ board.setA_email_req("N"); }
		if( !"Y".equals(board.getA_phone()) ){ board.setA_phone_req("N"); }
		if( !"Y".equals(board.getA_home()) ){ board.setA_home_req("N"); }
		if( !"Y".equals(board.getA_content()) ){ board.setA_content_req("N"); }
		
		//게시판 고유번호
		String a_num = board.getA_num();
		if("".equals(a_num) || a_num == null) {
			// a_num 생성, 중복검사
			selectdto.setExTableName("board_config");
			selectdto.setExColumn("count(*) as cnt");
			
			boolean flag = true;
			while(flag){
				a_num = Func.get_idx_add();
				selectdto.setExWhereQuery("where a_num = '"+exdao.filter(a_num)+"'");
				String cnt = exdao.selectQueryColumn(selectdto);
				if("0".equals(cnt)){
					flag = false;
				}
			}
			board.setA_num(a_num);
		}
		
		//테이블 명을 구한다.
		selectdto = new BoardConfigDTO();	//select 전용 dto 초기화
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_tablecnt");
		selectdto.setExOrderByQuery("order by a_tablecnt desc");
		int a_tablecnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
		String a_tablename_str = "";
		if(a_tablecnt == 0) {
			a_tablecnt++;
			a_tablename_str = "board_1";
		} else {
			a_tablecnt++;
			a_tablename_str = "board_" + a_tablecnt;
		}
		
		//테이블명 중복체크
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		selectdto = new BoardConfigDTO();	//select 전용 dto 초기화
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("count(*)");
		selectdto.setExWhereQuery("where a_tablename = '"+a_tablename_str+"'");
		String dupli_table_cnt = exdao.selectQueryColumn(selectdto);
		if(!"0".equals(dupli_table_cnt)){
			return messageService.backMsg(model, "같은이름의 게시판이 이미 존재합니다.");
		}
		board.setA_tablename(a_tablename_str);
		board.setA_tablecnt(Func.cStr(a_tablecnt));
		
		
		// 순서 (10 단위로 증가되도록)
		selectdto = new BoardConfigDTO();	//select 전용 dto 초기화
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("max(a_code)");
		int a_code = Func.cInt( exdao.selectQueryColumn(selectdto) );
		a_code = a_code + 10;
		a_code = (int)Math.floor(a_code/10) * 10;	//10단위로 만들어 주자 10,20,30...
		board.setA_code(Func.cStr(a_code));
		
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ================
		String uploaddir =  fileutil.realPath(request, "/data/");
		Func.folderCreate(uploaddir+"/board");
		Func.folderCreate(uploaddir+"/board/"+a_tablename_str+"/");
		//==============================================
		
		//a_group 기본값
		if("".equals(board.getA_group())) board.setA_group("0");
		
		//게시판생성일자
		String a_date = Func.date("Y-m-d H:i:s");
		board.setA_date(a_date);

		board.setA_tablename(a_tablename_str);
		

		
		board.setA_site_dir(builder_dir);
		
		
		//board_config insert
		board.setExTableName("board_config");
		exdao.insert(board);
		
		//게시판 테이블 생성
		//exdao.createBoard(a_tablename_str);
		
		//뷰생성
		//viewCreate();
		
		// 회원그룹별 기본 권한 주기 (목록보기, 읽기)
		BoardAccessDTO boardaccess = new BoardAccessDTO();
		boardaccess.setG_num("2");
		boardaccess.setA_num(board.getA_num());
		boardaccess.setBl_ad_cms("");
		boardaccess.setBl_list("Y");
		boardaccess.setBl_read("Y");
		boardaccess.setBl_write("");
		boardaccess.setBl_delete("");
		boardaccess.setBl_reply("");
		
		boardaccess.setExTableName("board_access");
		exdao.insert(boardaccess);
		boardaccess.setG_num("3");	//정회원(도서관회원)
		exdao.insert(boardaccess);
		boardaccess.setG_num("4");	//사이트관리자
		boardaccess.setBl_ad_cms("Y");
		exdao.insert(boardaccess);
		
		
		//작업기록
		Func.writeManagerLog ("게시판 생성 ["+board.getA_bbsname()+"]", request);

		
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "list.do";}
		return "redirect:"+prepage;
	}

	/**
	 * @title : 게시판 수정 폼
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//디렉토리 위치
		ArrayList<HashMap<String,String>> dir_list = new ArrayList<HashMap<String,String>>();
		String filePath = fileutil.realPath(request, "/nanum/site/board");
		String[] dir = Func.openDir( filePath );
		for(int i=0;i<=dir.length-1;i++){
			String skin_name = Func.fileGetContents( filePath+"/"+dir[i]+"/skinname.txt" );
			if(skin_name == null){skin_name = dir[i];}
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("dir", dir[i]);
			hm.put("skin_name", skin_name);
			dir_list.add(hm);
		}
		request.setAttribute("dir_list", dir_list);
		
		//select 전용 dto 생성
		BoardConfigDTO selectdto = new BoardConfigDTO();
		
		// 게시판 리스트
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_tablename, a_bbsname");
		selectdto.setExOrderByQuery("ORDER BY a_code, a_bbsname ASC");
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
		request.setAttribute("boardList", boardList);
		
		//상세보기
		selectdto = new BoardConfigDTO();	//select 전용 dto 초기화
		selectdto.setExTableName("board_config");
		selectdto.setExKeyColumn("a_idx");	//PK 키컬럼 정의
		selectdto.setExColumn( new BoardConfigDTO() );
		String a_num = exdao.filter( request.getParameter("a_num") );
		selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
		HashMap<String, String>board_map = exdao.selectQueryRecord(selectdto);
		if(board_map.size() > 0){
			if("".equals( Func.nvl( board_map.get("a_sdate") ) )){
				board_map.put("a_sdate", Func.date("Y-m-d"));
			}
			if("".equals( Func.nvl( board_map.get("a_edate") ) )){
				board_map.put("a_edate", Func.date("Y-m-d"));
			}
		}
		request.setAttribute("board", board_map);
		
		//처리타입
		request.setAttribute("prc_type", "edit");

		//현재 URL 전송
		Func.getNowPage(request);
		
	}
	
	/**
	 * @title : 게시판 수정하기
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		BoardConfigDTO board = (BoardConfigDTO) map.get("boarddto");
		
		//사용기간 - 사용안함
		board.setA_sdate("--");
		board.setA_edate("--");
		
		if( !"Y".equals(board.getA_email_req()) ){ board.setA_email_req("N"); }
		if( !"Y".equals(board.getA_phone_req()) ){ board.setA_phone_req("N"); }
		if( !"Y".equals(board.getA_home_req()) ){ board.setA_home_req("N"); }
		if( !"Y".equals(board.getA_content_req()) ){ board.setA_content_req("N"); }
		if( !"Y".equals(board.getA_reply()) ) { board.setA_reply("N"); }
		if( "".equals(board.getA_group()) ) board.setA_group("0");
		
		//update 처리	
		board.setExTableName("board_config");

		//수정처리에서 제외할 필드
		board.setA_num(null);
		board.setA_tablename(null);
		board.setA_code(null);
		board.setA_date(null);
		board.setA_tablecnt(null);
		
		String a_num = exdao.filter( request.getParameter("a_num") );
		board.setExWhereQuery("where a_num = '"+a_num+"'");
		exdao.update(board);
		
		//작업기록
		Func.writeManagerLog ("게시판 수정 ["+board.getA_bbsname()+"]", request);

		if(request.getHeader("referer").contains("write_popup")) {
			return "redirect:write_popup.do?a_num="+board.getA_num()+"&page_type=popup";
		}else {
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "list.do";}
			return "redirect:"+prepage;
		}
	}

	/**
	 * @title : 게시판 삭제하기
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 			= Func.nvl(request.getParameter("status")); //값:totdel (다중삭제시 사용)
		String[] chk 			= request.getParameterValues("chk"); //선택 체크 값
		String a_num			= exdao.filter( request.getParameter("a_num") );  //단일 삭제 사용
		String uploaddir		= fileutil.realPath(request, "/data/board/");

		//select 전용 dto 생성
		BoardConfigDTO selectdto = new BoardConfigDTO();
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_tablename, a_bbsname");
		
		if (status.equals("totdel")) { //다중삭제
			
			for(int z=0;z <= chk.length-1;z++){
				String a_num_chk = exdao.filter(chk[z]);
				selectdto.setExWhereQuery("where a_num = '"+a_num_chk+"'");
				HashMap<String,String>board = exdao.selectQueryRecord(selectdto);
				String a_tablename = board.get("a_tablename");
				String a_bbsname = board.get("a_bbsname");
				
				//게시물 삭제 한다.
				exdao.executeQuery("delete from board where a_num = '"+a_num_chk+"'");
				
				//관리자 테이블에서 해당게시판 정보를 삭제한다.
				exdao.executeQuery("delete from board_config where a_num = '"+a_num_chk+"'");
				
				//게시판 권한삭제
				exdao.executeQuery("DELETE FROM board_code WHERE a_num = '"+a_num_chk+"'");
				
				//꼬릿글삭제
				exdao.executeQuery("DELETE FROM board_command WHERE a_num = '"+a_num_chk+"'");
				
				//폴더삭제
				if(!"".equals(Func.nvl(a_tablename))) {
					Func.folderDelete(uploaddir+"/"+a_tablename);
				}
				
				//작업기록
				Func.writeManagerLog ("게시판 삭제 ["+a_bbsname+"]", request);
				
			}
		}else{
			
			selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
			HashMap<String,String>board = exdao.selectQueryRecord(selectdto);
			String a_tablename = board.get("a_tablename");
			String a_bbsname = board.get("a_bbsname");
			
			//게시물 삭제 한다.
			exdao.executeQuery("delete from board where a_num = '"+a_num+"'");
			
			//관리자 테이블에서 해당게시판 정보를 삭제한다.
			exdao.executeQuery("delete from board_config where a_num = '"+a_num+"'");
			
			//게시판 권한삭제
			exdao.executeQuery("DELETE FROM board_code WHERE a_num = '"+a_num+"'");
			
			//꼬릿글삭제
			exdao.executeQuery("DELETE FROM board_command WHERE a_num = '"+a_num+"'");
			
			//폴더삭제
			if(!"".equals(Func.nvl(a_tablename))) {
				Func.folderDelete(uploaddir+"/"+a_tablename);
			}
			
			//작업기록
			Func.writeManagerLog ("게시판 삭제 ["+a_bbsname+"]", request);
			
		}
		
		//뷰생성
		//viewCreate();
	}

	@Override
	public void move(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveAll(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 순서일괄수정 리스트
	 * @method : listMove
	 */
	@Override
	public void listMove(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		SelectDTO selectdto = new SelectDTO();
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		//리스트조회하기
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_num, a_tablename, a_bbsname");
		selectdto.setExWhereQuery("where a_site_dir = '"+builder_dir+"'");
		selectdto.setExOrderByQuery("order by a_code");
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
		request.setAttribute("boardList", boardList);

	}

	/**
	 * @title : 순서일괄수정
	 * @method : listMoveOk
	 */
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String a_num = chk[z];
			exdao.executeQuery("update board_config set a_code = '"+(z+1)+"' where a_num = '"+a_num+"'");
		}
	}

	/**
	 * @title : 관리자 인덱스페이지에 쓰임 
	 * @method : ncmsIndex
	 */
	@Override
	public void ncmsIndex(Model model) throws Exception {
		//사용하지 않고 기본값만 전송
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int board_cnt = 0;
		int now_cnt = 0;
		int all_cnt = 0;
		
		request.setAttribute("board_cnt", board_cnt);
		request.setAttribute("now_cnt", now_cnt);
		request.setAttribute("all_cnt", all_cnt);
	}

	/**
	 * @title : 관리자 메인 > 게시판리스트
	 * @method : mainList
	 */
	@Override
	public void mainList(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//select 전용 dto
		BoardConfigDTO selectdto = new BoardConfigDTO();
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 10;
		int pagePerBlock = 10;
		selectdto.setExTableName("board_total_main");
		selectdto.setExColumn("count(*)");
		int recordcount = 0; // 전체레코드 수
		
		selectdto = new BoardConfigDTO();
		selectdto.setExTableName("board_total_main");
		selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		selectdto.setExColumn("a_num, a_bbsname, b_num, b_subject, b_content, b_regdate");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		List<HashMap<String,String>>boardList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( boardList.get(0).get("totalcount") ); // 전체레코드 수 재설정
		boardList.remove(0);	//총검색개수행(첫번째행)을 삭제
		
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수 
		
		for(int i=0; i <boardList.size(); i++) {
			HashMap<String,String>board = (HashMap<String,String>)boardList.get(i);
			
			if(Func.nvl(board.get("b_subject")).length() > 60 ) board.put("b_subject",Func.strCut(Func.nvl(board.get("b_subject")), null, 60, 0, true, true));
			if(Func.nvl(board.get("b_content")).length() > 100 ) board.put("b_content",Func.strCut(Func.nvl(board.get("b_content")), null, 100, 0, true, true));
		}
		
		// setAttribute
		request.setAttribute("boardList", boardList);

	}

	@Override
	public void memberSearch(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String pageInfo(Model model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @title : view board_total 생성
	 * @method : viewCreate
	 */
	@Override
	public void viewCreate() throws Exception {
		//select 전용 dto 생성
		BoardConfigDTO selectdto = new BoardConfigDTO();
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_num, a_tablename, a_bbsname");
		selectdto.setExOrderByQuery("order by a_code, a_bbsname ASC");
		
		List<HashMap<String,String>> boardList = exdao.selectQueryTable(selectdto);
		exdao.createBoardView(boardList);

	}

	@Override
	public void createTable(String a_tablename_str) throws Exception {
		// TODO Auto-generated method stub

	}

}
