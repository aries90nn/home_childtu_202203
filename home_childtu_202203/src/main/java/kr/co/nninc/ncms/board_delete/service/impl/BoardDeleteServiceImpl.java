package kr.co.nninc.ncms.board_delete.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.board_delete.service.BoardDeleteService;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 게시물 삭제관리를 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.11.23
 * @version 1.0
 */
@Service("boardDeleteService")
public class BoardDeleteServiceImpl extends EgovAbstractServiceImpl implements BoardDeleteService {

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
	 * @title : 삭제 게시글 리스트
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//검색어
		String v_search = exdao.filter( request.getParameter("v_search") );
		String v_keyword = exdao.filter( request.getParameter("v_keyword") );
		request.setAttribute("v_search", v_search);
		request.setAttribute("v_keyword", v_keyword);
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		//현재 페이지
		int v_page = Func.cInt(request.getParameter("v_page"));
		if(v_page == 0){v_page = 1;}
		//목록갯수
		int pagesize = 10;
		
		//select 전용dto
		BoardDTO selectdto = new BoardDTO();
		
		//목록조회
		selectdto.setExTableName("board_total_delete");
		selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		selectdto.setExColumn("a_num, a_bbsname, b_num, b_name, b_subject, b_content, b_regdate, b_keyword, b_look");
		String whereQuery = "";
		if(!"".equals(v_keyword)){
			whereQuery = " and "+v_search+" like '%"+v_keyword+"%'";
		}
		if(!"".equals(builder_dir)){
			whereQuery += " and a_site_dir = '"+builder_dir+"'";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY b_regdate DESC");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		List<HashMap<String,String>>boardDeleteList = exdao.selectQueryPage(selectdto);
		int recordcount = Func.cInt( boardDeleteList.get(0).get("totalcount") );	//총검색개수 추출
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수 
		boardDeleteList.remove(0);	//총검색개수행(첫번째행)을 삭제
		request.setAttribute("recordcount", recordcount);
		request.setAttribute("boardDeleteList", boardDeleteList);
		
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = 10;				//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring = paging.setQueryString(request, "v_search, v_keyword");
		/*
		 * Paging.setQueryString 함수는 아래와 같은 값을 반환단다.
		querystring = "v_search="+Func.urlEncode(v_search);
		querystring += "&v_keyword="+Func.urlEncode(v_keyword);
		*/
		String pagingtag = paging.execute(querystring);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		
		//현재URL 전송
		Func.getNowPage(request);

	}
	
	/**
	 * @title : 삭제 게시글 완전삭제
	 * @method : deleteOk
	 */
	@Override
	public String deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status = Func.nvl(request.getParameter("status"));
		String[] chk = request.getParameterValues("chk"); //선택 체크 값
		
		if("totdel".equals(status)){	//다중삭제
			for(int z=0;z <=chk.length-1;z++) {
				BoardDTO selectdto = new BoardDTO();	//select 전용 dto
				String[] num_arr = chk[z].split("\\^");
				String a_num = num_arr[0];
				String b_num = num_arr[1];
				b_num = exdao.filter(b_num);
				a_num = exdao.filter(a_num);
				
				String a_tablename = "";
				String a_bbsname = "";
				
				if(!"".equals(b_num) && !"".equals(a_num)){
					selectdto.setExTableName("board_config");
					selectdto.setExColumn("a_tablename, a_bbsname");
					selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
					HashMap<String,String> boardConfig = exdao.selectQueryRecord(selectdto);
					if(boardConfig.size() == 0){
						return messageService.backMsg(model, "존재하지 않는 게시판입니다.");
					}else{
						a_tablename = Func.nvl( boardConfig.get("a_tablename") );
						a_bbsname = Func.nvl( boardConfig.get("a_bbsname") );
					}
				}
				String realpath =  fileutil.realPath(request, "/data/");
				String strDir = realpath+"/board/"+a_tablename+"/";
				String strDir2 = realpath+"/board/"+a_tablename+"/thum/";
				
				selectdto = new BoardDTO();	//select 전용 dto
				selectdto.setExTableName("board");
				selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
				selectdto.setExColumn("b_subject, b_step, b_level");
				HashMap<String,String>board = exdao.selectQueryRecord(selectdto);
				
				if(board.size() > 0){
					int b_step		= Func.cInt(board.get("b_step"));
					int b_level		= Func.cInt(board.get("b_level"));
					
					//select 전용 dto
					selectdto = new BoardDTO();
					selectdto.setExTableName("board");
					String s_fields = "b_file1, b_file2, b_file3, b_file4, b_file5";
					s_fields += ", b_file6, b_file7, b_file8, b_file9, b_file10";
					selectdto.setExColumn(s_fields);
					
					//일반글일때
					if(b_step == 0 && b_level == 0) {
						//이미지 삭제
						selectdto.setExWhereQuery("where b_ref = '"+b_num+"'");
						List<HashMap<String, String>>boardList = exdao.selectQueryTable(selectdto);
						
						for(int ii=0;ii<=boardList.size()-1;ii++){	//답변글까지 모두 삭제처리
							HashMap<String, String>boardData = (HashMap<String, String>)boardList.get(ii);
							for(int f=1;f<=10;f++){	//파일갯수만큼 반복처리
								String b_file = boardData.get("b_file"+f);
								if(!"".equals(Func.nvl(b_file))) { Func.fileDelete(strDir+b_file); Func.fileDelete(strDir2+b_file); } //삭제
							}
						}
						
						//삭제하기
						exdao.executeQuery("delete from board where b_ref = '"+b_num+"' ");
					} else {  //답변글 일때(해당 목록만 삭제)
						selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
						HashMap<String, String>boardData = exdao.selectQueryRecord(selectdto);
						if(boardData.size() > 0){
							for(int f=1;f<=10;f++){	//파일갯수만큼 반복처리
								String b_file = boardData.get("b_file"+f);
								if(!"".equals(Func.nvl(b_file))) { Func.fileDelete(strDir+b_file); Func.fileDelete(strDir2+b_file); } //삭제
							}
							//삭제하기
							exdao.executeQuery("delete from board where b_num = '"+b_num+"' ");
						}
					}
					//작업기록
					Func.writeManagerLog ("게시물 영구삭제 ["+a_bbsname + " : "+ board.get("b_subject") +"]", request);
				}
				
			}
		}else{	//일반삭제
			BoardDTO selectdto = new BoardDTO();	//select 전용 dto
			
			String a_num = exdao.filter(request.getParameter("a_num"));
			String b_num = exdao.filter(request.getParameter("b_num"));
			String a_tablename = "";
			String a_bbsname = "";
			
			if(!"".equals(b_num) && !"".equals(a_num)){
				selectdto.setExTableName("board_config");
				selectdto.setExColumn("a_tablename, a_bbsname");
				selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
				HashMap<String,String> boardConfig = exdao.selectQueryRecord(selectdto);
				if(boardConfig.size() == 0){
					return messageService.backMsg(model, "존재하지 않는 게시판입니다.");
				}else{
					a_tablename = Func.nvl( boardConfig.get("a_tablename") );
					a_bbsname = Func.nvl( boardConfig.get("a_bbsname") );
				}
			}
			String realpath =  fileutil.realPath(request, "/data/");
			String strDir = realpath+"/board/"+a_tablename+"/";
			String strDir2 = realpath+"/board/"+a_tablename+"/thum/";
			
			//이미지 삭제
			selectdto = new BoardDTO();
			selectdto.setExTableName("board");
			String s_fields = "b_file1, b_file2, b_file3, b_file4, b_file5";
			s_fields += ", b_file6, b_file7, b_file8, b_file9, b_file10";
			s_fields += ", b_subject";
			selectdto.setExColumn(s_fields);
			selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
			HashMap<String, String>boardData = exdao.selectQueryRecord(selectdto);
			if(boardData.size() > 0){
				for(int f=1;f<=10;f++){	//파일갯수만큼 반복처리
					String b_file = boardData.get("b_file"+f);
					if(!"".equals(Func.nvl(b_file))) { Func.fileDelete(strDir+b_file); Func.fileDelete(strDir2+b_file); } //삭제
				}
				//삭제하기
				exdao.executeQuery("delete from board where b_num = '"+b_num+"' ");
				
				//작업기록
				String b_subject = Func.nvl( boardData.get("b_subject") );
				Func.writeManagerLog ("게시물 영구삭제 ["+a_bbsname + " : "+ b_subject +"]", request);
			}
			
		}
		
		
		String prepage = Func.nvl(request.getParameter("prepage"));
		if("".equals(prepage)){
			prepage = "list.do";
		}
		return "redirect:"+prepage;
	}

	/**
	 * @title : 삭제 게시글 복원
	 * @method : restoreOk
	 */
	@Override
	public String restoreOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status = Func.nvl(request.getParameter("status"));
		String[] chk = request.getParameterValues("chk"); //선택 체크 값
		
		if("totdel".equals(status)){	//다중삭제
			for(int z=0;z <=chk.length-1;z++) {
				BoardDTO selectdto = new BoardDTO();	//select 전용 dto
				String[] num_arr = chk[z].split("\\^");
				String a_num = num_arr[0];
				String b_num = num_arr[1];
				b_num = exdao.filter(b_num);
				a_num = exdao.filter(a_num);
				
				String a_tablename = "";
				String a_bbsname = "";
				
				if(!"".equals(b_num) && !"".equals(a_num)){
					selectdto.setExTableName("board_config");
					selectdto.setExColumn("a_tablename, a_bbsname");
					selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
					HashMap<String,String> boardConfig = exdao.selectQueryRecord(selectdto);
					if(boardConfig.size() == 0){
						return messageService.backMsg(model, "존재하지 않는 게시판입니다.");
					}else{
						a_tablename = Func.nvl( boardConfig.get("a_tablename") );
						a_bbsname = Func.nvl( boardConfig.get("a_bbsname") );
					}
				}
				
				selectdto = new BoardDTO();	//select 전용 dto
				selectdto.setExTableName("board");
				selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
				selectdto.setExColumn("b_subject, b_step, b_level");
				HashMap<String,String>board = exdao.selectQueryRecord(selectdto);
				
				if(board.size() > 0){
					int b_step		= Func.cInt(board.get("b_step"));
					int b_level		= Func.cInt(board.get("b_level"));
					
					//일반글일때
					if(b_step == 0 && b_level == 0) {
						//복원하기
						exdao.executeQuery("update board SET b_look = 'Y', b_keyword = '' where b_ref = '"+b_num+"' ");
					} else {  //답변글 일때(해당 목록만 삭제)
						//복원하기
						exdao.executeQuery("update board SET b_look = 'Y', b_keyword = '' where b_num = '"+b_num+"' ");
					}
					//작업기록
					Func.writeManagerLog ("게시물 복원 ["+a_bbsname + " : "+ board.get("b_subject") +"]", request);
				}
				
			}
		}else{	//일반삭제
			BoardDTO selectdto = new BoardDTO();	//select 전용 dto
			
			String a_num = exdao.filter(request.getParameter("a_num"));
			String b_num = exdao.filter(request.getParameter("b_num"));
			String a_tablename = "";
			String a_bbsname = "";
			
			if(!"".equals(b_num) && !"".equals(a_num)){
				selectdto.setExTableName("board_config");
				selectdto.setExColumn("a_tablename, a_bbsname");
				selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
				HashMap<String,String> boardConfig = exdao.selectQueryRecord(selectdto);
				if(boardConfig.size() == 0){
					return messageService.backMsg(model, "존재하지 않는 게시판입니다.");
				}else{
					a_tablename = Func.nvl( boardConfig.get("a_tablename") );
					a_bbsname = Func.nvl( boardConfig.get("a_bbsname") );
				}
			}
			
			//제목조회
			selectdto = new BoardDTO();
			selectdto.setExTableName("board");
			selectdto.setExColumn("b_subject");
			selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
			
			String b_subject = exdao.selectQueryColumn(selectdto);
			if(b_subject != null){
				
				//복원하기
				exdao.executeQuery("update board SET b_look = 'Y', b_keyword = '' where b_num = '"+b_num+"' ");
				
				//작업기록
				Func.writeManagerLog ("게시물 복원 ["+a_bbsname + " : "+ b_subject +"]", request);
			}
			
		}
		
		String prepage = Func.nvl(request.getParameter("prepage"));
		if("".equals(prepage)){
			prepage = "list.do";
		}
		return "redirect:"+prepage;
		
	}

	@Override
	public String pageInfo(Model model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
