package kr.co.nninc.ncms.board.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.board.service.BoardService;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board_config.service.BoardConfigDTO;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.FuncMember;
import kr.co.nninc.ncms.common.FuncThumb;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.login.service.LoginService;

/**
 * 게시물을 관리하기 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 2019.01.14
 * @version 1.1
 */
@Service("boardService")
class BoardServiceImpl  extends EgovAbstractServiceImpl implements BoardService {

	private final Logger log = Logger.getLogger(this.getClass().getName());

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;

	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;

	/** FileUtil */
	@Resource(name = "fileutil")
	private FileUtil fileutil;
	
	/** loginService */
	@Resource(name = "loginService")
	private LoginService loginService;
	
	

	/** 게시판설정값 */
	private HashMap<String, String> config = new HashMap<String, String>();
	private String is_ad_cms = "N";
	private String is_list = "N";
	private String is_read = "N";
	private String is_write = "N";
	private String is_delete = "N";
	private String is_reply = "N";
	private HashMap<String, String> b_cate_str = new HashMap<String, String>();
	private HashMap<String, String> b_cate_str2 = new HashMap<String, String>();

	/**
	 * @title : 게시물목록
	 * @method : list
	 */
	@Override
	public String list(Model model) throws Exception {
		
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}

		/////////// 스킨처리 ////////////
		this.procSkinMethod(model, "list");
		/////////// 스킨처리 ////////////

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리전 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String before_msg = Func.nvl(skinDTO.getBefore_msg());
		if (!"".equals(before_msg)) {
			return before_msg;
		}

		String a_num = Func.nvl(config.get("a_num")); // 게시판넘버
		String ss_m_id = Func.getSession(request, "ss_m_id"); // 세션아이디
		String ss_m_jumin = Func.getSession(request, "ss_m_jumin"); // 세션 식별번호
		if("".equals(ss_m_jumin)){ss_m_jumin = Func.getSession(request, "ss_m_dupinfo");}
		String nowpage = Func.getNowPage(request); // 현재url을 가져오고 동시에 jsp전송

		
		// 권한체크
		if (!"Y".equals(is_list)) {
			if ("".equals(ss_m_id)) {
				String login_url = (String)request.getAttribute("board_login_url");
				return messageService.redirectMsg(model, "", login_url+"?prepage=" + Func.urlEncode(nowpage));
			} else {
				return messageService.backMsg(model, "접근권한이 없습니다.");
			}
		}
		
		// 요청페이지 변조방지값
		String board_token = this.getBoardToken(request);
		request.setAttribute("board_token", board_token);

		// select 전용 dto
		BoardDTO selectdto = new BoardDTO(); // select전용 dto

		// 게시판필드
		String s_fields = exdao.getFieldNames( new BoardDTO(), "b_c_count, b_content" );
		s_fields += ", (select count(*) from board_command WHERE a_num = '" + a_num
				+ "' AND c_bnum = a.b_num ) as b_c_count";
		///////////////////////////////////////////
		/////////// 스킨처리 - s_fields 확인 ////////////
		///////////////////////////////////////////
		s_fields = this.getSkinQuery(skinDTO, "s_fields", s_fields);

		// ----- 공지사항 조회 -------
		String nowdateNotice = Func.date("Y-m-d H:i");
		selectdto.setExTableName("board a");
		selectdto.setExKeyColumn("b_num");
		selectdto.setExColumn(s_fields);

		// 검색조건
		String whereQueryNotice = "WHERE a_num = '"+a_num+"' and b_look = 'Y' AND b_noticechk='Y'";
		whereQueryNotice += " AND b_notice_sdate <= '" + nowdateNotice + "' AND b_notice_edate >= '" + nowdateNotice
				+ "'";
		if (!"Y".equals(is_ad_cms)) { // 관리자 아닐때 실행
			whereQueryNotice += " AND ( b_show_sdate = '' or b_show_sdate is null";
			whereQueryNotice += " OR (b_show_sdate <> '' and b_show_sdate <= '" + nowdateNotice
					+ "' and b_show_edate  >= '" + nowdateNotice + "'))";
		}
		//분류기능
		String v_cate = exdao.filter(request.getParameter("v_cate"));
		if(!"".equals(v_cate)){
			whereQueryNotice += " and b_cate = '"+v_cate+"'";
		}
		
		// 공지사항, 보이기 감추기
		String g_num = Func.getSession(request, "ss_g_num");
		if(a_num.equals("69394427") && !g_num.equals("1") && !g_num.equals("4")) {
			whereQueryNotice += " and b_temp10 <> 'N'";
		}
		
		selectdto.setExWhereQuery(whereQueryNotice);

		// 정렬
		String orderByQueryNotice = "ORDER BY b_regdate desc, b_num desc";
		if ("Y".equals(config.get("a_reply"))) { // 답변기능
			orderByQueryNotice = "ORDER BY b_ref desc, b_step asc";
		}
		selectdto.setExOrderByQuery(orderByQueryNotice);
		List<HashMap<String, String>> noticeList = exdao.selectQueryTable(selectdto);

		// new아이콘 표시, 첨부파일 정보설
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/board/" + config.get("a_tablename") + "/";
		for (int i = 0; i < noticeList.size(); i++) {
			HashMap<String, String> board_new = (HashMap<String, String>) noticeList.get(i);
			boolean newIcon = Func.get_newimg(board_new.get("b_regdate"), Func.cInt(config.get("a_new")));
			if (newIcon) {
				board_new.put("newIcon", "true");
			} else {
				board_new.put("newIcon", "false");
			}

			// 기간설정에 따른 줄처리
			String title_style = "";
			String show_str = "";
			String b_show_sdate_str = "";
			String b_show_edate_str = "";
			if ("Y".equals(config.get("a_show"))
					&& (is_ad_cms.equals("Y") || (ss_m_id.equals(board_new.get("b_id")) && !ss_m_id.equals("")))) {
				String b_show_sdate = Func.nvl(board_new.get("b_show_sdate"));
				String b_show_edate = Func.nvl(board_new.get("b_show_edate"));
				if (!"".equals(b_show_sdate) && !"".equals(b_show_sdate)) {
					b_show_sdate_str = b_show_sdate.substring(2, 10).replaceAll("-", ".");
					b_show_edate_str = b_show_sdate.substring(2, 10).replaceAll("-", ".");

					if (b_show_sdate.substring(0, 10).compareTo(Func.date("Y-m-d ")) > 0
							|| b_show_edate.substring(0, 10).compareTo(Func.date("Y-m-d")) < 0) {
						title_style = "text-decoration:line-through;";
						show_str = "<span style='font-size:8pt;' class='" + Func.nvl(board_new.get("b_sbjclr")) + "'>("
								+ b_show_sdate_str + "~" + b_show_edate_str + ")</span>&nbsp;&nbsp;";
					}
				}
			}
			board_new.put("txt_title_style", title_style);
			board_new.put("txt_show", show_str);
			
			//첨부파일정보설정
			for (int j = 1; j <= 10; j++) {
				String b_file = Func.nvl(board_new.get("b_file" + j));
				if (!"".equals(b_file)) {
					File file = new File(strDir + b_file);
					if (file.exists()) {
						// 파일용량담기
						board_new.put("b_file" + j + "_size", Func.byteConvert(Func.cStr(file.length())));
					}
				}
				// 파일이미지아이콘담기
				board_new.put("b_file" + j + "_icon", Func.get_FileName(b_file));
			}
		}
		request.setAttribute("noticeList", noticeList);
		// ----- 공지사항 조회끝 -------

		// ----- 게시물 조회 시작 -------

		// 페이징
		int v_page = Func.cInt(request.getParameter("v_page"));
		if (v_page == 0) {
			v_page = 1;
		}
		request.setAttribute("v_page", v_page);

		int pagesize = Func.cInt(config.get("a_displaysu"));
		request.setAttribute("pagesize", pagesize);

		///////////////////////////////////////////
		/////////// 스킨처리 - List 데이타 확인 ////////////
		///////////////////////////////////////////
		List<HashMap<String, String>> boardList = skinDTO.getBoardList();

		if (boardList == null) { // 스킨에서 전달되는 List가 없으면 기본처리
			selectdto = new BoardDTO(); // select전용 dto
			String nowdate = Func.date("Y-m-d H:i");
			selectdto.setExTableName("board a");
			selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
			selectdto.setExColumn(s_fields);

			// whereQuery 생성
			String whereQuery = "WHERE a_num = '"+a_num+"' and b_look='Y'";
			whereQuery += " AND ( b_noticechk = ''";
			whereQuery += " 	OR (b_noticechk = 'Y'";
			whereQuery += " 		AND ( b_notice_sdate <= '" + nowdate + "' OR b_notice_edate >= '" + nowdate + "')";
			whereQuery += "		)";
			whereQuery += ")";

			if (!"Y".equals(is_ad_cms)) { // 관리자가아닐때 실행할것들
				// 회원제
				if ("Y".equals(config.get("a_member"))) {
					if (!"".equals(ss_m_jumin)) {
						whereQuery += " AND ( ( b_jumin = '" + ss_m_jumin + "') or ( b_id = '" + ss_m_id + "' and (b_id <> '' or b_id is not null) ) )";
					} else {
						whereQuery += " AND b_id = '" + ss_m_id + "' and (b_id <> '' or b_id is not null)";
					}
				}
			}

			// 답변기능을 사용하지 않을때
			String a_reply = config.get("a_reply");
			String a_replyOpt = config.get("a_replyOpt");
			if ("N".equals(a_reply) || ("Y".equals(a_reply) && "2".equals(a_replyOpt))) {
				whereQuery += " AND b_type = 'N'";
			}

			// 분류기능 사용시
			//String v_cate = exdao.filter(request.getParameter("v_cate"));
			if ("Y".equals(config.get("a_cate")) && !"".equals(v_cate)) {
				whereQuery += " AND b_cate = '" + v_cate + "'";
			}

			// 검색
			String v_keyword = exdao.filter(request.getParameter("v_keyword"));
			String v_search = exdao.filter(request.getParameter("v_search"));
			if (!"".equals(v_keyword)) {
				whereQuery += " AND " + v_search + " like '%" + v_keyword + "%'";
			}
			///////////////////////////////////////////
			/////////// 스킨처리 - where_query 확인 /////////
			///////////////////////////////////////////
			whereQuery = this.getSkinQuery(skinDTO, "where_query", whereQuery);
			
			// 공지사항, 보이기 감추기
			if(a_num.equals("69394427") && !g_num.equals("1") && !g_num.equals("4")) {
				whereQuery += " and b_temp10 <> 'N'";
			}
			
			selectdto.setExWhereQuery(whereQuery);

			// 정렬
			///////////////////////////////////////////
			/////////// 스킨처리 - orderby_query 확인 /////////
			///////////////////////////////////////////
			orderByQueryNotice = this.getSkinQuery(skinDTO, "orderby_query", orderByQueryNotice);
			selectdto.setExOrderByQuery(orderByQueryNotice); // 공지목록 정렬조건을 같이쓰자

			// 페이징 설정
			selectdto.setExPage(v_page); // 현재페이지
			selectdto.setExRecordCount(pagesize); // 목록개수

			// 게시물 목록 List 생성
			boardList = exdao.selectQueryPage(selectdto);
		}

		// List가 페이징 타입일 경우
		if (boardList.size() > 0 && boardList.get(0).get("totalcount") != null) {
			int recordcount = Func.cInt(boardList.get(0).get("totalcount")); // 전체레코드
																				// 수
			request.setAttribute("recordcount", recordcount);
			boardList.remove(0); // 총검색개수행(첫번째행)을 삭제

			int totalpage = (int) Math.ceil(((recordcount - 1) / pagesize) + 1); // '전체덩어리갯수
			request.setAttribute("totalpage", totalpage);

			// 페이징문자열 생성
			Paging paging = new Paging();
			paging.pageKeyword = "v_page"; // 페이지파라미터명
			paging.page = v_page; // 현재페이지
			paging.block = 10; // 페이지링크 갯수
			paging.totalpage = totalpage; // 총페이지 갯수
			String querystring2 = paging.setQueryString(request, "v_search, v_keyword, v_cate");
			///////////////////////////////////////////
			/////////// 스킨처리 - paging_query 확인 /////////
			///////////////////////////////////////////
			querystring2 = this.getSkinQuery(skinDTO, "paging_query", querystring2);
			querystring2 += "&a_num=" + a_num;
			String pagingtag = paging.execute(querystring2);
			request.setAttribute("pagingtag", pagingtag);
		}

		// new아이콘 표시, 첨부파일아이콘
		for (int i = 0; i < boardList.size(); i++) {
			HashMap<String, String> board_new = (HashMap<String, String>) boardList.get(i);
			boolean newIcon = Func.get_newimg(board_new.get("b_regdate"), Func.cInt(config.get("a_new")));
			if (newIcon) {
				board_new.put("newIcon", "true");
			} else {
				board_new.put("newIcon", "false");
			}

			// 기간설정에 따른 줄처리
			String title_style = "";
			String show_str = "";
			String b_show_sdate_str = "";
			String b_show_edate_str = "";
			if ("Y".equals(config.get("a_show"))
					&& (is_ad_cms.equals("Y") || (ss_m_id.equals(board_new.get("b_id")) && !ss_m_id.equals("")))) {
				String b_show_sdate = Func.nvl(board_new.get("b_show_sdate"));
				String b_show_edate = Func.nvl(board_new.get("b_show_edate"));
				if (!"".equals(b_show_sdate) && !"".equals(b_show_sdate)) {
					b_show_sdate_str = b_show_sdate.substring(2, 10).replaceAll("-", ".");
					b_show_edate_str = b_show_sdate.substring(2, 10).replaceAll("-", ".");

					if (b_show_sdate.substring(0, 10).compareTo(Func.date("Y-m-d ")) > 0
							|| b_show_edate.substring(0, 10).compareTo(Func.date("Y-m-d")) < 0) {
						title_style = "text-decoration:line-through;";
						show_str = "<span style='font-size:8pt;' class='" + Func.nvl(board_new.get("b_sbjclr")) + "'>("
								+ b_show_sdate_str + "~" + b_show_edate_str + ")</span>&nbsp;&nbsp;";
					}
				}
			}
			board_new.put("txt_title_style", title_style);
			board_new.put("txt_show", show_str);

			
			//첨부파일정보설정
			for (int j = 1; j <= 10; j++) {
				String b_file = Func.nvl(board_new.get("b_file" + j));
				if (!"".equals(b_file)) {
					File file = new File(strDir + b_file);
					if (file.exists()) {
						// 파일용량담기
						board_new.put("b_file" + j + "_size", Func.byteConvert(Func.cStr(file.length())));
					}
				}
				// 파일이미지아이콘담기
				board_new.put("b_file" + j + "_icon", Func.get_FileName(b_file));
			}
		}
		request.setAttribute("boardList", boardList);
		// ----- 게시물 조회끝 -------

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리후 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String after_msg = Func.nvl(skinDTO.getAfter_msg());
		if (!"".equals(after_msg)) {
			return after_msg;
		}

		return "/site/board/" + Func.nvl(config.get("a_level")) + "/list";
	}

	/**
	 * @title : 게시물상세보기
	 * @method : view
	 */
	@Override
	public String view(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}

		/////////// 스킨처리 ////////////
		this.procSkinMethod(model, "view");
		/////////// 스킨처리 ////////////

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리전 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String before_msg = Func.nvl(skinDTO.getBefore_msg());
		if (!"".equals(before_msg)) {
			return before_msg;
		}

		// select 전용 dto
		BoardDTO selectdto = new BoardDTO();

		String ss_m_id = Func.getSession(request, "ss_m_id"); // 세션아이디
		String ss_m_jumin = Func.getSession(request, "ss_m_jumin"); // 본인인증정보
		if("".equals(ss_m_jumin)){ss_m_jumin = Func.getSession(request, "ss_m_dupinfo");}	// (di or ci)
		System.out.println(Func.getSession(request, "ss_m_dupinfo"));
		String nowpage = Func.getNowPage(request); // 현재url

		// 게시판넘버
		String a_num = Func.nvl(config.get("a_num"));

		// 테이블명
		String a_tablename = Func.nvl(config.get("a_tablename"));

		// 파라미터정보
		// *************************************************************************************
		String b_num = Func.nvl(request.getParameter("b_num"));
		String prepage = Func.nvl(request.getParameter("prepage"));
		if ("".equals(prepage))
			prepage = "?a_num=" + a_num;
		// **********************************************************************************************

		// 세션정보
		if (!is_read.equals("Y")) {
			if ("".equals(ss_m_id)) {
				String login_url = (String)request.getAttribute("board_login_url");
				return messageService.redirectMsg(model, "",login_url+"?prepage=" + Func.urlEncode(nowpage));
			} else {
				return messageService.backMsg(model, "접근권한이 없습니다.");
			}
		}

		// 조회수 추가
		exdao.executeQuery("update board set b_count = b_count + 1 where b_num ='" + b_num + "' and a_num = '"+a_num+"' ");

		// 게시물정보조회 시작
		///////////////////////////////////////////
		/////////// 스킨처리 - View 데이타 확인 ////////////
		///////////////////////////////////////////
		HashMap<String, String> board = skinDTO.getBoardMap();

		if (board.size() == 0) { // 스킨에서 전달되는 map이 없으면 기본처리
			selectdto.setExTableName("board a");

			// 게시판필드
			String s_fields = exdao.getFieldNames(new BoardDTO());
			
			///////////////////////////////////////////
			/////////// 스킨처리 - s_fields 확인 ////////////
			///////////////////////////////////////////
			s_fields = this.getSkinQuery(skinDTO, "s_fields", s_fields);
			selectdto.setExColumn(s_fields);

			String whereQuery = "where b_num = '" + b_num + "' and a_num = '"+a_num+"' ";
			if (!"Y".equals(is_ad_cms)) {
				whereQuery += " and b_look = 'Y'";
			}
			String b_type = exdao.filter(request.getParameter("b_type"));
			if (!"".equals(b_type)) {
				whereQuery += " and b_type = '" + b_type + "'";
			}
			String b_look = exdao.filter(request.getParameter("b_look"));
			if (!"".equals(b_look)) {
				whereQuery += " and b_look = '" + b_look + "'";
			}
			// 공지사항, 보이기 감추기
			String g_num = Func.getSession(request, "ss_g_num");
			if(a_num.equals("69394427") && !g_num.equals("1") && !g_num.equals("4")) {
				whereQuery += " and b_temp10 <> 'N'";
			}
			///////////////////////////////////////////
			/////////// 스킨처리 - where_query 확인 /////////
			///////////////////////////////////////////
			whereQuery = this.getSkinQuery(skinDTO, "where_query", whereQuery);
			selectdto.setExWhereQuery(whereQuery);

			// 게시물정보 map 생성
			selectdto.setExKeyColumn("b_num");	//clob자료형을 조회하기위해서 키컬럼을 추가
			board = exdao.selectQueryRecord(selectdto);
		}
		// 게시물정보조회 끝

		if (board.size() == 0) {
			return messageService.backMsg(model, "존재하지 않는 글입니다.");
		}

		if (!"Y".equals(is_ad_cms)) { // 관리자가아닐때 체크
			
			
			if (ss_m_id.equals(board.get("b_id")) && !"".equals(ss_m_id)) { // 로그인했고 본인글이라면
				Func.setSession(request, "pwd_chk_" + a_num + "_" + b_num, "ok");	//조회
				Func.setSession(request, "pwd_chk2_" + a_num + "_" + b_num, "ok");	//수정, 삭제처리
			} else if (ss_m_jumin.equals(board.get("b_jumin")) && !"".equals(ss_m_jumin)) { // 본인인증했고  본인글이라면
				Func.setSession(request, "pwd_chk_" + a_num + "_" + b_num, "ok");
				Func.setSession(request, "pwd_chk2_" + a_num + "_" + b_num, "ok");	//수정, 삭제처리
			} else if ("R".equals(board.get("b_type"))){	//답변이라면 문의글이 내글인지
				selectdto = new BoardDTO();
				selectdto.setExTableName("board");
				selectdto.setExColumn("b_id, b_jumin");
				selectdto.setExWhereQuery("where b_num = '"+exdao.filter(board.get("b_ref"))+"' and a_num = '"+a_num+"' ");
				HashMap<String,String>board_ref = exdao.selectQueryRecord(selectdto);
				if (ss_m_id.equals(board_ref.get("b_id")) && !"".equals(ss_m_id)) { // 로그인했고 본인글이라면
					Func.setSession(request, "pwd_chk_" + a_num + "_" + b_num, "ok");	//조회
					Func.setSession(request, "pwd_chk2_" + a_num + "_" + b_num, "ok");	//수정, 삭제처리
				} else if (ss_m_jumin.equals(board_ref.get("b_jumin")) && !"".equals(ss_m_jumin)) { // 본인인증했고  본인글이라면
					Func.setSession(request, "pwd_chk_" + a_num + "_" + b_num, "ok");
					Func.setSession(request, "pwd_chk2_" + a_num + "_" + b_num, "ok");	//수정, 삭제처리
				}
			
			}
			String pwd_chk = Func.getSession(request, "pwd_chk_" + a_num + "_" + b_num);
			// 공개/비공개
			// 체크*************************************************************************************
			boolean go_pwd = false;
			if ("Y".equals(config.get("a_type"))) { // 공개/비공개 선택
				if ("N".equals(board.get("b_open"))) { // 비공개일때
					if (!"ok".equals(pwd_chk)) {
						go_pwd = true;
					}
				}
			} else if ("T".equals(config.get("a_type"))) { // 비공개
				if (!"ok".equals(pwd_chk)) {
					go_pwd = true;
				}
				if("Y".equals(board.get("b_noticechk"))){	//공지글이면 공개처리
					go_pwd = false;
				}
			}
			if (go_pwd) {
				// 조회수 차감
				exdao.executeQuery("update board set b_count = b_count - 1 where b_num ='" + b_num + "' and a_num = '"+a_num+"' ");
				if("".equals(Func.nvl(board.get("b_id"))) && "".equals(Func.getSession(request, "ss_m_dupinfo"))){
					//String pwd_url = "?proc_type=pwd&a_num=" + a_num + "&b_num=" + b_num + "&prepage=" + Func.urlEncode(prepage)+"&gopage="+Func.urlEncode(nowpage);
					//return messageService.redirectMsg(model, "", pwd_url);
					return messageService.redirectMsg(model, "본인인증 페이지로 이동합니다.", "/main/site/member/ipin.do?prepage=" + Func.urlEncode(nowpage));
					
				}else{
					if("".equals(Func.getSession(request, "ss_m_id")) && "".equals(Func.getSession(request, "ss_m_dupinfo"))){
						//String login_url = (String)request.getAttribute("board_login_url");
						//return messageService.redirectMsg(model, "",login_url+"?prepage=" + Func.urlEncode(nowpage));
						return messageService.redirectMsg(model, "본인인증 페이지로 이동합니다.", "/main/site/member/ipin.do?prepage=" + Func.urlEncode(nowpage));
					}else{
						return messageService.redirectMsg(model, "본인이 작성한 글이 아닙니다.", prepage);
					}
				}
			}
			// **************************************************************************************************
		}
		
		

		// 사이즈&파일이미지
		if(!"".equals(Func.nvl( board.get("a_tablename") ))){
			a_tablename = Func.nvl(board.get("a_tablename"));
		}
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/board/" + a_tablename + "/";
		for (int i = 1; i <= 10; i++) {
			String b_file = Func.nvl(board.get("b_file" + i));
			if (!"".equals(b_file)) {
				File file = new File(strDir + b_file);
				if (file.exists()) {
					// 파일용량담기
					board.put("b_file" + i + "_size", Func.byteConvert(Func.cStr(file.length())));
				}
			}
			// 파일이미지아이콘담기
			board.put("b_file" + i + "_icon", Func.get_FileName(b_file));
		}

		// 데이타 추가 가공
		String b_content = Func.nvl(board.get("b_content"));
		String b_re_content = Func.nvl(board.get("b_re_content"));
		if ("N".equals(config.get("a_edit"))) {// 에디터 사용하지 않을때...
			board.put("b_content", Func.getTextmode(b_content));
			board.put("b_re_content", Func.getTextmode(b_re_content));
		}
		board.put("a_reply", config.get("a_reply"));

		// 게시물상세정보 전송
		request.setAttribute("board", board);

		// 이전, 다음글 조회
		String b_ref = Func.nvl(board.get("b_ref"));
		String b_step = Func.nvl(board.get("b_step"));
		// 이전글
		// ------------------------------------------------------------------------------------------------
		selectdto = new BoardDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_num, b_subject");
		String whereQueryPrev = "WHERE a_num = '"+a_num+"' and b_look = 'Y' AND b_num <> '" + b_num + "' AND b_ref <= '" + b_ref
				+ "' AND b_step >= '" + b_step + "'";
		if ("N".equals(config.get("a_reply"))) {
			whereQueryPrev += " AND b_type='N'";
		}
		selectdto.setExWhereQuery(whereQueryPrev);
		selectdto.setExOrderByQuery("ORDER BY b_ref DESC, b_step ASC");
		HashMap<String, String> prevBoard = exdao.selectQueryRecord(selectdto);
		if (prevBoard.size() > 0) {
			request.setAttribute("prev_b_num", Func.nvl(prevBoard.get("b_num")));
			request.setAttribute("prev_b_subject", Func.nvl(prevBoard.get("b_subject")));
		}
		// 이전글끝
		// ----------------------------------------------------------------------------------------------
		// 다음글
		// ------------------------------------------------------------------------------------------------
		selectdto = new BoardDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_num, b_subject");
		String whereQueryNext = "WHERE a_num = '"+a_num+"' and b_look = 'Y' AND b_num <> '" + b_num + "' AND b_ref >= '" + b_ref
				+ "' AND b_step <= '" + b_step + "'";
		if ("N".equals(config.get("a_reply"))) {
			whereQueryNext += " AND b_type='N'";
		}
		selectdto.setExWhereQuery(whereQueryNext);
		selectdto.setExOrderByQuery("ORDER BY b_ref ASC, b_step DESC");
		HashMap<String, String> nextBoard = exdao.selectQueryRecord(selectdto);
		String next_b_num = "";
		String next_b_subject = "";
		if (nextBoard.size() > 0) {
			request.setAttribute("next_b_num", Func.nvl(nextBoard.get("b_num")));
			request.setAttribute("next_b_subject", Func.nvl(nextBoard.get("b_subject")));
		}

		// 다음글끝
		// ----------------------------------------------------------------------------------------------

		// 댓글 자동입력방지 문자
		Random rand = new Random();
		String autoimg_str = Func.cStr(rand.nextInt(4) + 1);
		request.setAttribute("autoimg_str", autoimg_str);

		// 댓글리스트
		selectdto = new BoardDTO();
		selectdto.setExTableName("board_command");
		selectdto.setExKeyColumn("c_num");
		selectdto.setExColumn("c_num, a_num, c_bnum, c_id, c_pwd, c_name, c_content, c_regdate");
		selectdto.setExWhereQuery("WHERE a_num = '" + a_num + "' AND c_bnum = '" + b_num + "'");
		List<HashMap<String, String>> commandList = exdao.selectQueryTable(selectdto);
		for (int i = 0; i < commandList.size(); i++) {
			String c_content = Func.nvl(commandList.get(i).get("c_content"));
			commandList.get(i).put("c_content", Func.getTextmode(c_content));
		}
		request.setAttribute("commandList", commandList);

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리후 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String after_msg = Func.nvl(skinDTO.getAfter_msg());
		if (!"".equals(after_msg)) {
			return after_msg;
		}
		
		// 요청페이지 변조방지값
		String board_token = this.getBoardToken(request);
		request.setAttribute("board_token", board_token);

		return "/site/board/" + Func.nvl(config.get("a_level")) + "/view";
	}

	/**
	 * @title : 게시물 글쓰기폼
	 * @method : write
	 */
	@Override
	public String write(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}

		/////////// 스킨처리 ////////////
		this.procSkinMethod(model, "write");
		/////////// 스킨처리 ////////////

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리전 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String before_msg = Func.nvl(skinDTO.getBefore_msg());
		if (!"".equals(before_msg)) {
			return before_msg;
		}

		String nowpage = Func.getNowPage(request);
		String ss_m_id = Func.getSession(request, "ss_m_id"); // 세션아이디
		String ss_m_name = Func.getSession(request, "ss_m_name"); // 세션이름
		String ss_m_jumin = Func.getSession(request, "ss_m_jumin"); // 세션jumin
		String ss_m_pwd = Func.getSession(request, "ss_m_pwd"); // 세션비밀번호
		String a_num = Func.nvl(config.get("a_num"));// 게시판넘버

		if (!"Y".equals(is_write)) {
			if ("".equals(ss_m_id)) {
				String login_url = (String)request.getAttribute("board_login_url");
				return messageService.redirectMsg(model, "", login_url +"?prepage=" + Func.urlEncode(nowpage));
			} else {
				return messageService.backMsg(model, "접근권한이 없습니다.");
			}
		}

		// 실명인증사용이고 관리자가 아닐때
		if ("Y".equals(config.get("a_jumin")) && !"Y".equals(is_ad_cms)) {
			if ("".equals(ss_m_name) && "".equals(ss_m_jumin)) {
				return messageService.redirectMsg(model, "", "?proc_type=name&a_num="+a_num+"&prepage=" + Func.urlEncode(nowpage));
			}
		}

		// 로그인한 회원은 비번을 입력하지 않는다..
		String b_name = ss_m_name;
		String b_pwd = ss_m_pwd;
		String b_jumin = "";

		///////////////////////////////////////////
		/////////// 스킨처리 - Write 맵데이타 확인 //////////
		///////////////////////////////////////////
		// 기본값 전송할 map
		HashMap<String, String> board = skinDTO.getBoardMap();
		// 정보넣기
		Func.mapCheckPut(board, "b_name", b_name);
		Func.mapCheckPut(board, "b_pwd", b_pwd);
		Func.mapCheckPut(board, "b_jumin", b_jumin);
		Func.mapCheckPut(board, "b_type", "N");
		Func.mapCheckPut(board, "b_content", config.get("a_writecontent"));
		Func.mapCheckPut(board, "b_sdate", Func.date("Y-m-d"));
		Func.mapCheckPut(board, "b_edate", Func.date("Y-m-d"));
		if ("T".equals(config.get("a_type"))) { // 비공개 게시판
			Func.mapCheckPut(board, "b_open", "N");
		} else {
			Func.mapCheckPut(board, "b_open", "Y");
		}
		
		// 공지기간
		String b_notice_sdate = Func.date("Y-m-d");
		String b_notice_sdate_time = "00:00";
		String b_notice_edate = Func.date("Y-m-d");
		String b_notice_edate_time = "23:59";

		// jsp에서 사용하기 위해 날짜,시간을 잘러서 전송
		Func.mapCheckPut(board, "b_notice_sdate", b_notice_sdate);
		Func.mapCheckPut(board, "b_notice_sdate_time", b_notice_sdate_time);
		// board.put("b_notice_edate", b_notice_edate); //시작일자 선택이 불편해서
		// 종료일자는 비워둔다
		// board.put("b_notice_edate_time", b_notice_edate_time);
		
		//분류값 있을경우
		Func.mapCheckPut(board, "b_cate", exdao.filter(request.getParameter("v_cate")));
		
		// 기본값 전송
		request.setAttribute("board", board);

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리후 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String after_msg = Func.nvl(skinDTO.getAfter_msg());
		if (!"".equals(after_msg)) {
			return after_msg;
		}

		// 요청페이지 변조방지값
		String board_token = this.getBoardToken(request);
		request.setAttribute("board_token", board_token);

		return "/site/board/" + Func.nvl(config.get("a_level")) + "/write";
	}

	/**
	 * @title : 게시물 등록
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}

		/////////// 스킨처리 ////////////
		this.procSkinMethod(model, "writeOk");
		/////////// 스킨처리 ////////////

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리전 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String before_msg = Func.nvl(skinDTO.getBefore_msg());
		if (!"".equals(before_msg)) {
			return before_msg;
		}

		String ss_m_id = Func.getSession(request, "ss_m_id"); // 세션아이디
		String ss_m_jumin = Func.getSession(request, "ss_m_jumin"); // 세션jumin
		if("".equals(ss_m_jumin)){ss_m_jumin = Func.getSession(request, "ss_m_dupinfo");}
		String ss_m_pwd = Func.getSession(request, "ss_m_pwd"); // 비밀번호
		// 게시판넘버
		String a_num = Func.nvl(config.get("a_num"));
		String a_tablename = Func.nvl(config.get("a_tablename"));

		///////////////////////////////////////////
		/////////// 스킨처리 - WriteOk dto 확인 //////////
		///////////////////////////////////////////
		BoardDTO board = skinDTO.getBoardDTO();
		if (board == null) { // 스킨처리가 없다면 기본 처리
			board = (BoardDTO) map.get("boarddto"); // 전송된 dto
		}

		// xss 처리
		board.setB_content( Func.InputValueXSS(request.getParameter("b_content")) );
		board.setB_subject(Func.InputValue(board.getB_subject()));
		board.setB_name(Func.InputValue(board.getB_name()));

		// 비밀번호 세팅
		String b_pwd = Func.nvl(board.getB_pwd());
		if ("".equals(b_pwd))
			b_pwd = ss_m_pwd;
		board.setB_pwd(b_pwd);

		// 등록일자 세팅
		String b_regdate = Func.date("Y-m-d H:i:s");
		board.setB_regdate(b_regdate);

		// 공지여부,기간
		String b_notice_sdate = "";
		String b_notice_edate = "";
		if ("Y".equals(is_ad_cms)) {
			if ("Y".equals(board.getB_noticechk())) {
				String notice_nolimit = Func.nvl(request.getParameter("notice_nolimit"));
				if ("Y".equals(notice_nolimit)) {
					b_notice_sdate = "1900-01-01 00:00";
					b_notice_edate = "2099-12-31 23:59";
				} else {
					String b_notice_sdate_time = Func.nvl(request.getParameter("b_notice_sdate_time"));
					String b_notice_edate_time = Func.nvl(request.getParameter("b_notice_edate_time"));
					b_notice_sdate = Func.nvl(board.getB_notice_sdate()) + " " + b_notice_sdate_time;
					b_notice_edate = Func.nvl(board.getB_notice_edate()) + " " + b_notice_edate_time;
				}
			} else {
				b_notice_sdate = "";
				b_notice_edate = "";
			}
		}
		board.setB_notice_sdate(b_notice_sdate);
		board.setB_notice_edate(b_notice_edate);


		// ■■■■■ 필드체크 시작 ■■■■■
		String chk_str = valueChk(board.getB_name(), "이름");
		if(Func.getSession(request, "ss_m_dupinfo").equals("")) {
			chk_str += valueChk(board.getB_pwd(), "비밀번호");	
		}
		
		chk_str += valueChk(board.getB_subject(), "제목");

		if ("Y".equals(config.get("a_phone")) && "Y".equals(config.get("a_phone_req"))) {
			chk_str += valueChk(board.getB_phone1(), "연락처1");
			chk_str += valueChk(board.getB_phone2(), "연락처2");
			chk_str += valueChk(board.getB_phone3(), "연락처3");
		}
		if ("Y".equals(config.get("a_email")) && "Y".equals(config.get("a_email_req"))) {// 이메일
			chk_str += valueChk(board.getB_email(), "이메일");
		}
		if ("Y".equals(config.get("a_cate")) && !"R".equals(board.getB_type())) { // 분류
			//String b_cate_tot = Func.nvl(request.getParameter("b_cate_tot"));
			//chk_str += valueChk(b_cate_tot, "분류");
		}
		if ("Y".equals(config.get("a_home")) && "Y".equals(config.get("a_home_req"))) {// 주소
			chk_str += valueChk(board.getB_zip1(), "우편번호1");
			chk_str += valueChk(board.getB_addr1(), "주소");
		}
		if ("Y".equals(config.get("a_content")) && "Y".equals(config.get("a_content_req"))) {
			chk_str += valueChk(board.getB_content(), "내용");
		}
		if (!"".equals(chk_str)) {
			return messageService.backMsg(model, chk_str);
		}

		// ■■■■■ 금지단어 체크 ■■■■■
		if (!"".equals(Func.nvl( config.get("a_noword") ))) {
			String[] a_noword = config.get("a_noword").replaceAll(" ", "").split("\\,"); // 제한파일..
			for (int z = 0; z <= a_noword.length - 1; z++) {
				String b_content = Func.nvl(board.getB_content());
				String b_subject = Func.nvl(board.getB_subject());

				int iscontent = b_content.indexOf(a_noword[z]); // 내용에서 검색
				int issubject = b_subject.indexOf(a_noword[z]); // 글제목에서 검색..

				if (iscontent >= 0 || issubject >= 0) {
					return messageService.backMsg(model, a_noword[z] + "은(는) 사용금지단어입니다. \\n단어를 삭제하세요.");
				}
			}
		}
		// ■■■■■ 금지단어 체크 끝 ■■■■■

		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/board/" + a_tablename + "/";
		String strDir2 = realpath + "/board/" + a_tablename + "/thum/";
		Func.folderCreate(strDir);
		Func.folderCreate(strDir2);
		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------

		// 업로드 허용 확장자
		String ext_str = Func.nvl(config.get("a_nofile"));
		String a_upload_len = Func.nvl(config.get("a_upload_len"));

		// 업로드를 미리처리하자.
		if ("Y".equals(config.get("a_upload"))) { // 첨부파일 사용일때
			for (int z = 1; z <= Func.cInt(a_upload_len); z++) {
				// 가변변수 파일명 가져오기
				String b_file = Func.getValue(board, "b_file" + z);
				if (!"".equals(b_file)) {
					// 썸네일
					FuncThumb.GD2_make_thumb(640, 480, "/thum/", strDir, b_file);
				}
			}
		}

		// ref,step,level 체크
		int b_step = Func.cInt(board.getB_step());
		int b_ref = Func.cInt(board.getB_ref());
		int b_level = Func.cInt(board.getB_level());
		if ("R".equals(board.getB_type())) { // 답변이면
			exdao.executeQuery("update board set b_step = b_step + 1 WHERE b_ref = '" + b_ref + "'  AND b_step > '" + b_step + "'");
			b_step = b_step + 1;
			b_level = b_level + 1;
		}

		String b_look = "Y"; // 사용/중지
		String b_id = ss_m_id;
		String b_ip = Func.remoteIp(request);
		String b_cate = "0";
		String b_catename = "";
		if ("Y".equals(config.get("a_cate"))) {
			if ("R".equals(board.getB_type())) { // 답변이면
				// 상위분류값을 넣는다..
				BoardDTO selectdto = new BoardDTO();
				selectdto.setExTableName("board");
				selectdto.setExColumn("b_cate, b_catename");
				selectdto.setExWhereQuery("WHERE b_num = '" + b_ref + "'");
				HashMap<String, String> rs_cate = exdao.selectQueryRecord(selectdto);
				b_cate = rs_cate.get("b_cate");
				b_catename = rs_cate.get("b_catename");
			} else {
				String b_cate_tot = Func.nvl(request.getParameter("b_cate_tot"));
				if(!"".equals(b_cate_tot)){
					String[] b_cate_str = b_cate_tot.split("\\,");
					b_cate = b_cate_str[0].trim();
					b_catename = b_cate_str[1].trim();
				}
			}
		}

		if (!"Y".equals(is_ad_cms)) {
			board.setB_notice_sdate(null);
			board.setB_notice_edate(null);
		}
		board.setB_count("0");
		board.setB_chuchun("0");
		board.setB_c_count("0");
		board.setB_look(b_look);
		board.setB_ip(b_ip);
		board.setB_id(b_id);
		board.setB_cate(b_cate);
		board.setB_catename(b_catename);
		board.setB_step(Func.cStr(b_step));
		board.setB_ref(Func.cStr(b_ref));
		board.setB_level(Func.cStr(b_level));
		board.setB_pwd(Func.hash(board.getB_pwd()));
		board.setB_jumin(ss_m_jumin);

		// 제외처리
		board.setB_num(null);

		// 공지체크
		if (board.getB_noticechk() == null) {
			board.setB_noticechk("");
		}

		// 등록하기
		board.setExTableName("board");
		exdao.insert(board);

		BoardDTO selectdto = new BoardDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("max(b_num)");
		int max_b_num = Func.cInt(exdao.selectQueryColumn(selectdto));

		if ("N".equals(board.getB_type())) {
			exdao.executeQuery(
					"UPDATE board SET b_ref = '" + max_b_num + "' WHERE b_num = '" + max_b_num + "'");
		}

		// 작업기록
		Func.writeBoardLog("게시물 등록 [" + config.get("a_bbsname") + " : " + board.getB_subject() + "]", request);

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리후 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String after_msg = Func.nvl(skinDTO.getAfter_msg());
		if (!"".equals(after_msg)) {
			return after_msg;
		}
		
		String prepage = Func.nvl(request.getParameter("prepage"));
		if ("".equals(prepage)) {
			prepage = "?proc_type=list&a_num="+a_num;
		}
		return "redirect:" + prepage;

	}

	/**
	 * @title : 게시물 수정폼
	 * @method : modify
	 */
	@Override
	public String modify(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}

		/////////// 스킨처리 ////////////
		this.procSkinMethod(model, "modify");
		/////////// 스킨처리 ////////////

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리전 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String before_msg = Func.nvl(skinDTO.getBefore_msg());
		if (!"".equals(before_msg)) {
			return before_msg;
		}

		String ss_m_id = Func.getSession(request, "ss_m_id"); // 세션아이디
		String ss_m_name = Func.getSession(request, "ss_m_name"); // 세션이름
		String ss_m_pwd = Func.getSession(request, "ss_m_pwd"); // 세션빕번
		String ss_m_jumin = Func.getSession(request, "ss_m_jumin"); // 세션jumin
		if("".equals(ss_m_jumin)){ss_m_jumin = Func.getSession(request, "ss_m_dupinfo");}
		String a_num = config.get("a_num");// 게시판넘버
		String a_tablename = config.get("a_tablename");
		String b_num = Func.nvl(request.getParameter("b_num"));
		String nowpage = Func.getNowPage(request);
		
		// 요청페이지 변조방지값
		String board_token = this.getBoardToken(request);
		request.setAttribute("board_token", board_token);

		if (!"Y".equals(is_write)) {
			if ("".equals(ss_m_id)) {
				String login_url = (String)request.getAttribute("board_login_url");
				return messageService.redirectMsg(model, "", login_url+"?prepage=" + Func.urlEncode(nowpage));
			} else {
				return messageService.backMsg(model, "접근권한이 없습니다.");
			}
		}

		// 실명인증사용이고 관리자가 아닐때
		if ("Y".equals(config.get("a_jumin")) && !"Y".equals(is_ad_cms)) {
			if ("".equals(ss_m_name) && "".equals(ss_m_jumin)) {
				return messageService.redirectMsg(model, "", "?proc_type=name&a_num="+a_num+"&b_num="+b_num+"&prepage="+Func.urlEncode(nowpage)+"&board_token="+Func.urlEncode(board_token));
			}
		}

		// 관리자일때 비번을 입력하지 않는다..
		String b_name = ss_m_name;
		String b_pwd = ss_m_pwd;

		///////////////////////////////////////////
		/////////// 스킨처리 - View 맵데이타 확인 //////////
		///////////////////////////////////////////
		// 기본값 전송할 map
		HashMap<String, String> board = skinDTO.getBoardMap();
		if (board.size() == 0) { // 스킨에서 전달되는 map이 없으면 기본처리

			String b_type = exdao.filter(request.getParameter("b_type"));
			String b_look = exdao.filter(request.getParameter("b_look"));

			BoardDTO selectdto = new BoardDTO(); // select전용 dto
			if ("R".equals(b_type)) { // 답변글쓰기 일때
				selectdto.setExTableName("board");
				selectdto.setExKeyColumn("b_num");	//clob자료형을 조회하기위해서 키컬럼을 추가
				selectdto.setExColumn("b_subject, b_content, b_ref, b_step, b_level, b_open");
				selectdto.setExWhereQuery("WHERE b_num = '" + exdao.filter(b_num) + "' and a_num = '"+a_num+"' ");

				board = exdao.selectQueryRecord(selectdto);
				request.setAttribute("w_mode", "WR");
				board.put("b_type", "R");
				board.put("b_subject", "RE:" + Func.nvl(board.get("b_subject")));
				board.put("b_content",
						"<br /><br /><br /><br />------------------ 원문 -------------------------<br /><br />"
								+ Func.nvl(board.get("b_content")));
				board.put("b_name", b_name);
				board.put("b_pwd", b_pwd);
			} else { // 그냥 수정
				selectdto.setExTableName("board");
				selectdto.setExKeyColumn("b_num");	//clob자료형을 조회하기위해서 키컬럼을 추가
				// 게시판필드
				String s_fields = exdao.getFieldNames(new BoardDTO());
				///////////////////////////////////////////
				/////////// 스킨처리 - s_fields 확인 ////////////
				///////////////////////////////////////////
				s_fields = this.getSkinQuery(skinDTO, "s_fields", s_fields);
				selectdto.setExColumn(s_fields);

				String whereQuery = "WHERE b_num = '" + b_num + "' and a_num = '"+a_num+"' ";

				if (!"Y".equals(is_ad_cms)) { // 관리자가 아닐때
					whereQuery += " AND b_look='Y'";
				}
				if (!"".equals(b_look)) {
					whereQuery += " AND b_look='" + b_look + "'";
				}
				if (!"".equals(b_type)) {
					whereQuery += " AND b_type='" + b_type + "'";
				}
				///////////////////////////////////////////
				/////////// 스킨처리 - where_query 확인 /////////
				///////////////////////////////////////////
				whereQuery = this.getSkinQuery(skinDTO, "where_query", whereQuery);
				selectdto.setExWhereQuery(whereQuery);
				board = exdao.selectQueryRecord(selectdto);
				if (board.size() == 0) {
					return messageService.backMsg(model, "존재하지 않는 글입니다.");
				}
			}
		}
		request.setAttribute("w_mode", "M");
		board.put("w_mode", "M"); // map에도 추가해버리자..
		// 관리자가아닐때
		if (!"Y".equals(is_ad_cms)) {
			
			if(ss_m_id.equals(board.get("b_id")) && !"".equals(board.get("b_id"))){	//내글이라면
				
				Func.setSession(request, "pwd_chk2_" + a_num + "_" + b_num, "ok");
			}else{
				if (!"ok".equals(Func.getSession(request, "pwd_chk2_" + a_num + "_" + b_num))) { // 체크통과 안했으면..
					String prepage = Func.nvl(request.getParameter("prepage"));	//되돌아갈 페이지
					String gopage = nowpage;	//비번인증후 이동할 페이지
					return messageService.redirectMsg(model, "", "?proc_type=pwd&pwd_type=chk2&a_num=" + a_num + "&b_num=" + b_num
							+ "&prepage=" + Func.urlEncode(prepage)+"&gopage="+Func.urlEncode(gopage));
				}
			}
		}

		// 공지기간
		String b_notice_sdate = Func.nvl(board.get("b_notice_sdate")).trim();
		String b_notice_sdate_time = "";
		if ("".equals(b_notice_sdate)) {
			b_notice_sdate = Func.date("Y-m-d");
			b_notice_sdate_time = "00:00";
		} else {
			b_notice_sdate_time = b_notice_sdate.substring(10, b_notice_sdate.length());
			b_notice_sdate = b_notice_sdate.substring(0, 10);
		}
		String b_notice_edate = Func.nvl(board.get("b_notice_edate")).trim();
		String b_notice_edate_time = "";
		if ("".equals(b_notice_edate)) {
			b_notice_edate = Func.date("Y-m-d");
			b_notice_edate_time = "23:59";
		} else {
			b_notice_edate_time = b_notice_edate.substring(11, b_notice_edate.length());
			b_notice_edate = b_notice_edate.substring(0, 10);
		}
		System.out.println("notice_edate="+b_notice_edate);
		System.out.println("b_notice_edate_time="+b_notice_edate_time);
		board.put("b_notice_sdate", b_notice_sdate);
		board.put("b_notice_sdate_time", b_notice_sdate_time);
		board.put("b_notice_edate", b_notice_edate); //시작일자입력이 불편하여 종료일자 설정안함
		board.put("b_notice_edate_time", b_notice_edate_time);

		String b_sdate = Func.nvl(board.get("b_sdate"));
		String b_edate = Func.nvl(board.get("b_edate"));
		if ("".equals(b_sdate))
			board.put("b_sdate", Func.date("Y-m-d"));
		if ("".equals(b_edate))
			board.put("b_edate", Func.date("Y-m-d"));

		request.setAttribute("b_zip1", board.get("b_zip1"));
		request.setAttribute("b_addr1", board.get("b_addr1"));
		request.setAttribute("b_addr2", board.get("b_addr2"));
		request.setAttribute("board", board);

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리후 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String after_msg = Func.nvl(skinDTO.getAfter_msg());
		if (!"".equals(after_msg)) {
			return after_msg;
		}

		
		return "/site/board/" + Func.nvl(config.get("a_level")) + "/write";
	}

	
	/**
	 * @title : 게시물 수정처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}

		/////////// 스킨처리 ////////////
		this.procSkinMethod(model, "modifyOk");
		/////////// 스킨처리 ////////////

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리전 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String before_msg = Func.nvl(skinDTO.getBefore_msg());
		if (!"".equals(before_msg)) {
			return before_msg;
		}
		
		String ss_m_pwd					= Func.getSession(request, "ss_m_pwd"); //비밀번호
		String b_num = exdao.filter( request.getParameter("b_num") );

		///////////////////////////////////////////
		/////////// 스킨처리 - WriteOk dto 확인 //////////
		///////////////////////////////////////////
		BoardDTO board = skinDTO.getBoardDTO();
		if (board == null) { // 스킨처리가 없다면 기본 처리
			board = (BoardDTO) map.get("boarddto"); // 전송된 dto
		}
			
		//xss 처리
		board.setB_subject(Func.InputValue(board.getB_subject()));		
		board.setB_name(Func.InputValue(board.getB_name()));
		board.setB_content(Func.InputValueXSS(request.getParameter("b_content")));
		
		//비밀번호세팅
		String b_pwd	=	board.getB_pwd();
		if ("".equals(b_pwd)) b_pwd = ss_m_pwd;
		board.setB_pwd(b_pwd);
		
		// 공지여부,기간
		String b_notice_sdate = "";
		String b_notice_edate = "";
		if("Y".equals(is_ad_cms)) {
			if ("Y".equals(board.getB_noticechk())){
				String notice_nolimit = Func.nvl( request.getParameter("notice_nolimit") );
				if("Y".equals(notice_nolimit)){
					b_notice_sdate="1900-01-01 00:00";
					b_notice_edate="2099-12-31 23:59";
				}else{
					String b_notice_sdate_time = Func.nvl( request.getParameter("b_notice_sdate_time") );
					String b_notice_edate_time = Func.nvl( request.getParameter("b_notice_edate_time") );
					b_notice_sdate = board.getB_notice_sdate()+" "+b_notice_sdate_time;
					b_notice_edate = board.getB_notice_edate()+" "+b_notice_edate_time;
				}
			}else{
				b_notice_sdate="";
				b_notice_edate="";
			}
		}
		board.setB_notice_sdate(b_notice_sdate);
		board.setB_notice_edate(b_notice_edate);
		
		//■■■■■ 필드체크 시작 ■■■■■
		String chk_str = valueChk(board.getB_name(), "이름");
		chk_str += valueChk(board.getB_subject(), "제목");
		
		if("Y".equals(config.get("a_phone")) && "Y".equals(config.get("a_phone_req"))){
			chk_str += valueChk(board.getB_phone1(), "연락처1");
			chk_str += valueChk(board.getB_phone2(), "연락처2");
			chk_str += valueChk(board.getB_phone3(), "연락처3");
		}
		if("Y".equals(config.get("a_email")) && "Y".equals(config.get("a_email_req"))){//이메일
			chk_str += valueChk(board.getB_email(), "이메일");
		}
		if("Y".equals(config.get("a_cate")) && !"R".equals(board.getB_type())) { //분류
			//String b_cate_tot = Func.nvl( request.getParameter("b_cate_tot") );
			//chk_str += valueChk(b_cate_tot, "분류");
		}
		if("Y".equals(config.get("a_home")) && "Y".equals(config.get("a_home_req"))){//주소
			chk_str += valueChk(board.getB_zip1(), "우편번호1");
			chk_str += valueChk(board.getB_addr1(), "주소");
		}
		if ("Y".equals(config.get("a_content")) && "Y".equals(config.get("a_content_req"))) {
			chk_str += valueChk(board.getB_content(), "내용");
		}
		if (!"".equals(chk_str)) {
			return messageService.backMsg(model, chk_str);
		}
		
		// ■■■■■ 금지단어 체크 ■■■■■
		if (!"".equals(Func.nvl(config.get("a_noword")))) {
			String[] a_noword = config.get("a_noword").replaceAll(" ", "").split("\\,"); // 제한파일..
			for (int z = 0; z <= a_noword.length - 1; z++) {
				String b_content = Func.nvl(board.getB_content());
				String b_subject = Func.nvl(board.getB_subject());

				int iscontent = b_content.indexOf(a_noword[z]); // 내용에서 검색
				int issubject = b_subject.indexOf(a_noword[z]); // 글제목에서 검색..

				if (iscontent >= 0 || issubject >= 0) {
					return messageService.backMsg(model, a_noword[z] + "은(는) 사용금지단어입니다. \\n단어를 삭제하세요.");
				}
			}
		}
		// ■■■■■ 금지단어 체크 끝 ■■■■■
		
		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/board/" + config.get("a_tablename") + "/";
		String strDir2 = realpath + "/board/" + config.get("a_tablename") + "/thum/";
		Func.folderCreate(strDir);
		Func.folderCreate(strDir2);
		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
		
		// 업로드 허용 확장자
		String ext_str = Func.nvl(config.get("a_nofile"));
		String a_upload_len = Func.nvl(config.get("a_upload_len"));
		
		// 업로드를 미리처리하자.
		if ("Y".equals(config.get("a_upload"))) { // 첨부파일 사용일때
			for (int z = 1; z <= Func.cInt(a_upload_len); z++) {
				// 가변변수 파일명 가져오기
				String b_file = Func.getValue(board, "b_file" + z);
				if (!"".equals(b_file)) {
					// 썸네일
					FuncThumb.GD2_make_thumb(640, 480, "/thum/", strDir, b_file);
				}
			}
		}
		
		//수정처리
		if("Y".equals(config.get("a_upload"))) {	 //첨부파일 사용일때
			for(int z=1;z <= Func.cInt(config.get("a_upload_len"));z++) {
			
				String b_file = Func.getValue(board, "b_file"+z);
				String b_file_chk = Func.nvl(request.getParameter("b_file_chk"+z));
				String b_file_org = Func.nvl(request.getParameter("b_file_org"+z));
			
				if(!"".equals(b_file)) { //첨부파일 있으면
					if(!"".equals(b_file_org)){
						Func.fileDelete(strDir+b_file_org);
						Func.fileDelete(strDir2+b_file_org);
					}
				}else if (!"".equals(b_file_chk)) { //체크하면 파일삭제, 디비삭제
					Func.fileDelete(strDir+b_file_org);
					Func.fileDelete(strDir2+b_file_org);
					
					//값세팅
					Func.setValue(board, "b_file"+z, "");
				}else if(!"".equals(b_file_org)){	//첨부파일 없으면 기존파일명유지
					//값세팅
					Func.setValue(board, "b_file"+z, b_file_org);
				}
			}
		}
		
		String b_cate		= "0";
		String b_catename	= "";
		if("Y".equals(config.get("a_cate"))){
			if("R".equals(board.getB_type())) { //답변이면 
				//상위분류값을 넣는다..
				BoardDTO selectdto = new BoardDTO();
				selectdto.setExTableName("board");
				selectdto.setExColumn("b_cate, b_catename");
				selectdto.setExWhereQuery("WHERE b_num = '"+Func.nvl(board.getB_ref())+"' and a_num = '"+config.get("a_num")+"' ");
				HashMap<String,String>rs_cate = exdao.selectQueryRecord(selectdto);
				b_cate = rs_cate.get("b_cate");
				b_catename = rs_cate.get("b_catename");
				
			}else{
				String b_cate_tot 	= Func.nvl(request.getParameter("b_cate_tot"));
				if(!"".equals(b_cate_tot)){
					String[] b_cate_str	= b_cate_tot.split("\\,");
					b_cate				= b_cate_str[0].trim();
					b_catename			= b_cate_str[1].trim();
				}
			}
		}
		
		// 필드세팅
		// update 제외 처리
		board.setB_num(null);
		board.setA_num(null);
		
		//board.setIs_ad_cms(is_ad_cms);
		if(!"Y".equals(is_ad_cms)){
			board.setB_noticechk(null);
			board.setB_notice_sdate(null);
			board.setB_notice_edate(null);
		}
		//board.setA_home(config.getA_home());
		if(!"Y".equals(config.get("a_home"))){
			board.setB_zip1(null);
			board.setB_zip2(null);
			board.setB_addr1(null);
			board.setB_addr2(null);
		}
		//board.setA_date_change(config.getA_date_change());
		//board.setA_reply(config.getA_reply());
		if(!"Y".equals(config.get("a_date_change")) || !"Y".equals(is_ad_cms) || "Y".equals(config.get("a_reply"))){
			board.setB_regdate(null);
		}
		
		board.setB_cate(b_cate);
		board.setB_catename(b_catename);
		
		board.setExTableName("board");
		board.setExWhereQuery("where b_num = '"+b_num+"' and a_num = '"+config.get("a_num")+"' ");
		exdao.update(board);

		//작업기록
		Func.writeBoardLog ("게시물 수정 ["+config.get("a_bbsname")+" : "+board.getB_subject()+"]", request);
		
	
		////////////////////////////////////////////
		/////////// 스킨처리 - 처리후 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String after_msg = Func.nvl(skinDTO.getAfter_msg());
		if (!"".equals(after_msg)) {
			return after_msg;
		}

		String prepage = Func.nvl(request.getParameter("prepage"));
		if ("".equals(prepage)) {
			prepage = "?proc_type=list&a_num="+config.get("a_num");
		}
		return "redirect:" + prepage;
		
	}
	
	
	/**
	 * @title : 게시물 답변폼
	 * @method : reply
	 */
	@Override
	public String reply(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}
		
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		String ss_m_id						= Func.getSession(request, "ss_m_id"); //세션아이디
		String a_num 						= Func.nvl(config.get("a_num"));//게시판넘버
		String a_tablename 					= Func.nvl(config.get("a_tablename"));
		String prepage 						= Func.nvl(request.getParameter("prepage"));
		if("".equals(prepage)){prepage = "?proc_type=list&a_num="+a_num;}
		String nowpage = Func.getNowPage(request);
		
		String b_num = exdao.filter((request.getParameter("b_num")));
		
		if(!"Y".equals(is_reply)){
			return messageService.backMsg(model, "접근권한이 없습니다.");
		}
		
		BoardDTO selectdto = new BoardDTO();
		selectdto.setExTableName("board");
		selectdto.setExKeyColumn("b_num");	//clob자료형을 조회하기위해서 키컬럼을 추가
		
		String s_fields = exdao.getFieldNames(new BoardDTO());
		selectdto.setExColumn(s_fields);
		
		String whereQuery = "WHERE b_num = '"+b_num+"'";
		if(!"Y".equals(is_ad_cms)){	//관리자가 아닐때
			whereQuery += " AND b_look='Y'";
		}
		selectdto.setExWhereQuery(whereQuery);
		
		HashMap<String,String>board = exdao.selectQueryRecord(selectdto);
		
		if(board.size() == 0){
			return messageService.backMsg(model, "존재하지 않는 글입니다.");
		}
		
		//관리자가아닐때
		if(!"Y".equals(is_ad_cms)) {
			String b_id = Func.nvl( board.get("b_id") );
			if ("".equals(b_id) || (!b_id.equals(ss_m_id) ) ){	// 내글아닐때
				if(!"ok".equals(Func.getSession(request, "pwd_chk2_"+a_num+"_"+b_num))) { //체크통과 안했으면..
					String gopage = nowpage;	//비번인증후 이동할 페이지
					return messageService.redirectMsg(model, "", "?proc_type=pwd&pwd_type=chk2&a_num="+a_num+"&b_num="+b_num+"&prepage="+Func.urlEncode(prepage)+"&gopage="+Func.urlEncode(gopage));
				}
			}
		}
		
		String b_re_date = Func.nvl(board.get("b_re_date"));
		if("".equals(b_re_date)) {
			board.put("b_re_date_y", Func.date("Y"));
			board.put("b_re_date_m", Func.date("m"));
			board.put("b_re_date_d", Func.date("d"));
		}else {
			String[] b_re_date_arr = b_re_date.split("\\-");
			board.put("b_re_date_y", b_re_date_arr[0]);
			board.put("b_re_date_m", b_re_date_arr[1]);
			board.put("b_re_date_d", b_re_date_arr[2]);
		}
		
		request.setAttribute("board", board);
		
		//세션소멸
		Func.setSession(request, "pwd_chk2_"+a_num+"_"+b_num, "");
		
		//토큰생성
		String board_token = this.getBoardToken(request);
		request.setAttribute("board_token", board_token);
		
		return "/site/board/"+config.get("a_level")+"/reply";
	}

	
	/**
	 * @title : 게시물 답변처리
	 * @method : replyOk
	 */
	@Override
	public String replyOk(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String prepage 						= Func.nvl(request.getParameter("prepage"));
		if("".equals(prepage)){prepage = "?proc_type=list&a_num="+config.get("a_num");}
		
		BoardDTO board = new BoardDTO();
		
		//xss 처리
		board.setB_re_buseo( Func.InputValueXSS(request.getParameter("b_re_buseo")) );
		board.setB_re_content( Func.InputValueXSS(request.getParameter("b_re_content")) );
		board.setB_re_state( Func.InputValueXSS(request.getParameter("b_re_state")) );
		
		//처리일자 설정
		String b_re_date_y = Func.nvl(request.getParameter("b_re_date_y"));
		String b_re_date_m = Func.nvl(request.getParameter("b_re_date_m"));
		b_re_date_m = Func.zerofill(b_re_date_m, 2, "0");
		String b_re_date_d = Func.nvl(request.getParameter("b_re_date_d"));
		b_re_date_d = Func.zerofill(b_re_date_d, 2, "0");
		String b_re_date = b_re_date_y + "-"+ b_re_date_m + "-"+ b_re_date_d;
		board.setB_re_date(b_re_date);
				
		
		board.setExTableName("board");
		board.setExWhereQuery("where b_num = '"+exdao.filter(request.getParameter("b_num"))+"'");
		
		//update
		exdao.update(board);
		
		
		
		return "redirect:"+prepage;
	}

	
	
	/**
	 * @title : 게시물 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public String deleteOk(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}
		/////////// 스킨처리 ////////////
		this.procSkinMethod(model, "deleteOk");
		/////////// 스킨처리 ////////////

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리전 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String before_msg = Func.nvl(skinDTO.getBefore_msg());
		if (!"".equals(before_msg)) {
			return before_msg;
		}
		
		String a_num = Func.nvl( config.get("a_num") );
		String b_num = exdao.filter(request.getParameter("b_num"));
		String d_page = Func.nvl(request.getParameter("d_page"));	//기본페이지
		if("".equals(d_page)){d_page = "/";}
		
		String prepage = Func.nvl(request.getParameter("prepage"));	//되돌아갈 페이지
		if("".equals(prepage)){prepage = d_page+"?proc_type=list&a_num="+a_num;}
		
		String ss_m_id = Func.getSession(request, "ss_m_id");
		String ss_m_jumin = Func.getSession(request, "ss_m_dupinfo");
		String status = Func.nvl( request.getParameter("status") );
		
		// 세션정보
		if (!is_delete.equals("Y")) {
			if ("".equals(ss_m_id)) {
				String login_url = (String)request.getAttribute("board_login_url");
				return messageService.redirectMsg(model, "",login_url+"?prepage=" + Func.urlEncode(prepage));
			} else {
				return messageService.backMsg(model, "접근권한이 없습니다.");
			}
		}
		
		
		if(!"Y".equals(is_ad_cms)) { //관리자가아닐때 체크
			if(!"ok".equals(Func.getSession(request, "pwd_chk2_"+a_num+"_"+b_num))) {
				String gopage = Func.getNowPage(request);	//비번인증후 이동할 페이지
				return messageService.redirectMsg(model, "", d_page+"?proc_type=pwd&pwd_type=chk2&a_num=" + a_num + "&b_num=" + b_num
						+ "&prepage=" + Func.urlEncode(prepage)+"&gopage="+Func.urlEncode(gopage));
			}
		}
		
		if("Y".equals(config.get("a_garbage"))){	//휴지통기능
			delete_garbage(model);
		}else{
			delete_real(model);
		}
		
		
		////////////////////////////////////////////
		/////////// 스킨처리 - 처리후 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String after_msg = Func.nvl(skinDTO.getAfter_msg());
		if (!"".equals(after_msg)) {
			return after_msg;
		}
		
		//세션소멸
		Func.setSession(request, "pwd_chk_"+a_num+"_"+b_num, "");
		if ("totdel".equals(status)) { //목록에서 다중삭제할 경우
			return "redirect:"+prepage;
		}else{
			return "redirect:"+d_page+"?proc_type=list&a_num="+a_num+"&v_cate="+Func.nvl(request.getParameter("v_cate")).trim();
		}
	}

	/**
	 * @title : 비밀번호확인
	 * @method : pwd
	 */
	@Override
	public void pwd(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return;
		}
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String a_num = Func.nvl(config.get("a_num"));
		String b_num = Func.nvl(request.getParameter("b_num"));
		request.setAttribute("a_num", a_num);
		request.setAttribute("b_num", b_num);
		
		String board_token = this.getBoardToken(request);
		request.setAttribute("board_token", board_token);
	}

	/**
	 * @title : 비밀번호확인 처리
	 * @method : pwdOk
	 */
	@Override
	public String pwdOk(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//파라미터정보 *************************************************************************************
		String b_num		= Func.nvl(request.getParameter("b_num"));
		String b_pwd_chk	= Func.nvl(request.getParameter("b_pwd_chk"));
		String gopage		= Func.nvl(request.getParameter("gopage"));
		if("".equals(gopage)){gopage = "?proc_type=list&a_num="+config.get("a_num");}
		
		String pwd_type		= Func.nvl(request.getParameter("pwd_type"));
		//**********************************************************************************************
		System.out.println(Func.getSession(request, "ss_m_dupinfo"));
		if("".equals(b_pwd_chk) && Func.getSession(request, "ss_m_dupinfo").equals("")){ //수정
			return messageService.backMsg(model, "비밀번호를 입력해주세요.");
		}
		
		//비밀번호 암호화
		String b_pwd_chk_org = b_pwd_chk;
		b_pwd_chk = Func.hash(b_pwd_chk);

		//게시판넘버
		String a_num = Func.nvl( config.get("a_num") );
		
		String b_pwd = ""; String b_ref = ""; String b_pwd2 = ""; String b_type = "";
		BoardDTO selectdto = new BoardDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_pwd, b_ref, b_type");
		selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
		HashMap<String, String>board = exdao.selectQueryRecord(selectdto);
		if(board.size() > 0){
			b_pwd = Func.nvl( board.get("b_pwd") );
			b_ref = Func.nvl( board.get("b_ref") );
			b_type = Func.nvl( board.get("b_type") );
		}
		//비번타입이 chk2가 아니고 답변(내용보기)일때..사용
		if(!"chk2".equals(pwd_type) && "Y".equals(config.get("a_reply")) && "R".equals(b_type)){
			selectdto = new BoardDTO();
			selectdto.setExTableName("board");
			selectdto.setExColumn("b_pwd");
			selectdto.setExWhereQuery("where b_num = '"+b_ref+"'");
			b_pwd2 = exdao.selectQueryColumn(selectdto);
		}
		
		if(b_pwd_chk.equals(b_pwd) || b_pwd_chk.equals(b_pwd2)) { //비밀번호가 일치하면
			//세션발생
			Func.setSession(request, "pwd_chk_"+a_num+"_"+b_num, "ok");
			if("Y".equals(config.get("a_reply")) && "R".equals(b_type)){//답변(내용보기)일때..사용
				Func.setSession(request, "pwd_chk_"+config.get("a_num")+"_"+b_ref, "ok");
			}
			if("chk2".equals(pwd_type)){	//수정, 삭제에 사용됨
				Func.setSession(request, "pwd_chk2_"+a_num+"_"+b_num, "ok");
			}
			return "redirect:"+gopage;
		} else { //수정
			return messageService.backMsg(model, "비밀번호가 일치하지 않습니다.다시 확인해주세요.");
		}
	}

	@Override
	public String a_pwdOk(Model model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	/**
	 * @title : 로그인폼
	 * @method : login
	 */
	@Override
	public String login(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		
		// 요청페이지 변조방지값
		String board_token = this.getBoardToken(request);
		request.setAttribute("board_token", board_token);
		
		return "/site/member/ipin";
	}
	
	
	
	/**
	 * @title : 로그인처리
	 * @method : managerOk
	 */
	@Override
	public String loginOk(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//파라미터정보 *******************************************************************
		String m_id			 = Func.nvl(request.getParameter("m_id"));
		String m_pwd		 = Func.nvl(request.getParameter("m_pwd"));
		String prepage		 = Func.nvl(request.getParameter("prepage"));
		if("".equals(prepage)){prepage = "?proc_type=list&a_num="+config.get("a_num");}
		//파라미터정보 *******************************************************************
		
		if (FuncMember.memgr_idpwdchk(m_id,m_pwd, null)) {
			
			//로그인 실패시간 확인
			String m_incorrect_time = loginService.loginFailTime(m_id);
			if(!"".equals(m_incorrect_time)){
				if(Func.cLng(Func.date("YmdHi")) - Func.cLng(m_incorrect_time) < 10){
					String msg = "로그인실패 5회이상으로 10분간 로그인이 제한되었습니다.";
					return messageService.backMsg(model, msg);
				}
			}
			FuncMember.memgr_logincookie(request, m_id, m_pwd); //인증쿠키발생
			loginService.loginFailReset(m_id);		//로그인실패 초기화
			
			
			FuncMember.memgr_logincookie(request, m_id,m_pwd); //인증쿠키발생
		} else {	
			String failCheck = loginService.loginFailCheck(model);
			if(!"N".equals(failCheck)){
				String msg = "정보가 정확하지 않거나 접속권한이 없습니다. \\n\\n(비밀번호 5회 실패시 10분동안 로그인이 제한됩니다.)";
				return messageService.backMsg(model, msg);
			}else {
				return messageService.backMsg(model, "정보가 정확하지 않거나 접속권한이 없습니다. \\n\\n(비밀번호 5회 실패시 10분동안 로그인이 제한됩니다.)");
			}
		}
		
		
		return "redirect:"+prepage;
	}

	/**
	 * @title : 파일다운로드
	 * @method : down
	 */
	@Override
	public String down(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}
		
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		String type					= (String) map.get("type");
		
		String ss_m_id						= Func.getSession(request, "ss_m_id"); //세션아이디
		String ss_m_jumin					= Func.getSession(request, "ss_m_jumin"); //세션 본인인증
		if("".equals(ss_m_jumin)){ss_m_jumin = Func.getSession(request, "ss_m_dupinfo");}
		String a_num						= Func.nvl(request.getParameter("a_num"));
		String b_num						= Func.nvl(request.getParameter("b_num"));
		String f_num						= Func.nvl(request.getParameter("f_num"));
		String thum							= Func.nvl(request.getParameter("thum"));
		String a_tablename 					= Func.nvl(config.get("a_tablename"));
		String nowpage = Func.getNowPage(request);
		
		if("".equals(a_num) || Func.cInt(a_num) == 0 || "".equals(b_num) || Func.cInt(b_num) == 0 || "".equals(f_num) || Func.cInt(f_num) == 0 ){
			return messageService.redirectMsg(model, "잘못된 파라미터입니다.", "/");
		}
		if(!"Y".equals(is_read)){
			return messageService.redirectMsg(model, "접근권한이 없습니다.", "/");
		}
		
		//게시글 정보 확인
		BoardDTO selectdto = new BoardDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_id, b_open, b_noticechk, b_file"+f_num);
		selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
		HashMap<String, String>board = exdao.selectQueryRecord(selectdto);
		if(board.size() == 0) {
			return messageService.backMsg(model, "존재하지 않는 글입니다.");
		}
		
		if(!"Y".equals(is_ad_cms)) { //관리자가아닐때 체크
			
			if (ss_m_id.equals(board.get("b_id")) && !"".equals(ss_m_id)) { // 로그인했고 본인글이라면
				Func.setSession(request, "pwd_chk_" + a_num + "_" + b_num, "ok");
			} else if (ss_m_jumin.equals(board.get("b_jumin")) && !"".equals(ss_m_jumin)) { // 본인인증했고  본인글이라면
				Func.setSession(request, "pwd_chk_" + a_num + "_" + b_num, "ok");
			}
			String pwd_chk = Func.getSession(request, "pwd_chk_"+a_num+"_"+b_num);
			//공개/비공개 체크*************************************************************************************
			if ("Y".equals(config.get("a_type"))) { //공개여부 사용일때
				if("N".equals(board.get("b_open"))){ //비공개일때
					if(!"ok".equals(pwd_chk)) {
						return messageService.backMsg(model, "접근권한이 없습니다.");
					}
				}
			}
			if("T".equals(config.get("a_type"))){	//비공개게시판
				if("Y".equals(board.get("b_noticechk"))){	//공지글이면 공개처리
					pwd_chk = "ok";
				}
				if(!"ok".equals(pwd_chk)) {
					return messageService.backMsg(model, "접근권한이 없습니다.");
				}
			}
			//**************************************************************************************************
		}
		//파일네임
		String filename = board.get("b_file"+f_num);
		System.out.println("filename="+filename);
		
		request.setAttribute("filename", filename);
		request.setAttribute("a_tablename", a_tablename);
		request.setAttribute("thum", thum);
		if("img".equals(type)) {
			return "/site/board/get_img";
		}else {
			return "/site/board/down";
		}
	}

	
	/**
	 * @title : 이미지파일 삭제
	 * @method : imgDeleteOk
	 */
	@Override
	public String imgDeleteOk(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//파라미터정보 *************************************************************************************
		String b_num				= Func.nvl( request.getParameter("b_num") );
		String a_num				= config.get("a_num");
		String b_file				= "b_file"+Func.nvl(request.getParameter("b_file"));
		String prepage				= Func.nvl(request.getParameter("prepage"));
		//**************************************************************************************************
		
		String a_tablename = Func.nvl( config.get("a_tablename") );
		
		BoardDTO selectdto = new BoardDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn(b_file);
		selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
		
		String b_filename = Func.nvl( exdao.selectQueryColumn(selectdto) );
		
		if(!"".equals(b_filename)){
			String realpath =  fileutil.realPath(request, "/data/");
			String strDir = realpath+"/board/"+a_tablename+"/";
			String strDir2 = realpath+"/board/"+a_tablename+"/thum/";
			//삭제
			Func.fileDelete(strDir+b_filename);
			Func.fileDelete(strDir2+b_filename);
			
			//업데이트
			exdao.executeQuery("update board set "+b_file+"='' where b_num = '"+b_num+"'");
		}
		return "redirect:"+prepage;
	}

	/**
	 * @title : 게시판 멀티업로드
	 * @method : nfuUpload
	 */
	@Override
	public void nfuUpload(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return;
		}

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");

		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/board/" + config.get("a_tablename") + "/";
		String strDir2 = realpath + "/board/" + config.get("a_tablename") + "/thum/";
		Func.folderCreate(strDir);
		Func.folderCreate(strDir2);
		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------

		String uploaddir = fileutil.realPath(request, "/data/board/" + config.get("a_tablename") + "/");
		int sizeLimit = Func.cInt(config.get("a_nofilesize")) * 1024 * 1024;
		String target_name = "";

		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "게시판파일", "NFU_add_file", sizeLimit,
				config.get("a_nofile").split(","));
		if (!"".equals(file.getFile_name())) {
			target_name = file.getFile_name();
		}
		request.setAttribute("target_name", target_name);

	}

	/**
	 * @title : 게시판 일반업로드
	 * @method : nfuNormalUpload
	 */
	@Override
	public void nfuNormalUpload(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return;
		}

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");

		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/board/" + config.get("a_tablename") + "/";
		String strDir2 = realpath + "/board/" + config.get("a_tablename") + "/thum/";
		Func.folderCreate(strDir);
		Func.folderCreate(strDir2);
		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------

		String init = request.getParameter("init");
		String uploadchk = "";
		String file_names = "";
		String uploaddir = fileutil.realPath(request, "/data/board/" + config.get("a_tablename") + "/");

		uploadchk = request.getParameter("uploadchk");
		request.setAttribute("uploadchk", uploadchk);

		String maxFileSize = request.getParameter("maxFileSize");
		String fileFilter = request.getParameter("fileFilter");
		String fileFilterType = request.getParameter("fileFilterType");

		if ("Y".equals(uploadchk)) {
			// 파일수
			String filecount = request.getParameter("filecount");

			for (int i = 1; i <= Func.cInt(filecount); i++) {
				String filename = fileutil.fileNormalUpload(multi.getFileMap(), uploaddir, "NFU_add_file_" + i,
						Func.cLng(maxFileSize), fileFilter, fileFilterType);
				if (!"".equals(Func.nvl(filename))) {
					file_names += filename + "|";
				}
			}
		}
		request.setAttribute("file_names", file_names);

	}

	/**
	 * @title : 게시판 설정조회
	 * @method : config
	 */
	@Override
	public String setConfig(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		is_ad_cms = "N";
		is_list = "N";
		is_read = "N";
		is_write = "N";
		is_delete = "N";
		is_reply = "N";

		String a_num = exdao.filter(request.getParameter("a_num"));
		if("".equals(a_num)){
			a_num = Func.nvl( (String)request.getAttribute("a_num") ).trim();
		}
		
		String proc_type = Func.nvl( request.getParameter("proc_type") ).trim();
		if("list".equals(proc_type) || "view".equals(proc_type) || "write".equals(proc_type)){
			String now_uri = Func.getNowPage(request).split("[?]")[0];
			if(!now_uri.contains("/ncms/")){
				exdao.executeQuery("update board_config set a_pageurl = '"+exdao.filter(now_uri)+"' where a_num = '"+a_num+"'");
			}
		}

		// select 전용 dto
		BoardConfigDTO selectdto = new BoardConfigDTO();
		selectdto.setExTableName("board_config");
		selectdto.setExKeyColumn("a_idx");	//clob자료형을 조회하기위해서 키컬럼을 추가
		selectdto.setExColumn( new BoardConfigDTO() );
		selectdto.setExWhereQuery("where a_num = '" + a_num + "'");

		config = exdao.selectQueryRecord(selectdto);
		config = Func.requestAllMap(config);	//map안에 null제거
		if("<p>&nbsp;</p>".equals(config.get("a_writecontent"))){
			config.put("a_writecontent", "");
		}
		request.setAttribute("config", config);

		if (config.size() == 0) {
			return messageService.backMsg(model, "잘못된 요청입니다.");
		} else {
			// 그룹권한 확인 설정
			String ss_g_num = Func.getSession(request, "ss_g_num");
			String ss_m_id = Func.getSession(request, "ss_m_id");

			if ("1".equals(ss_g_num)) {
				// 총관리자 ****************
				is_ad_cms = "Y";
				is_list = "Y";
				is_read = "Y";
				is_write = "Y";
				is_delete = "Y";
				is_reply = "Y";
				// ***********************
			} else {
				int g_num = 2; // 비회원 권한으로 초기화

				// 등급별 권한설정....
				if (!"".equals(Func.nvl(ss_g_num))) {
					g_num = Func.cInt(ss_g_num); // 로그인되어 있다면 해당그룹으로 설정
				}

				// 게시판 권한 조회
				selectdto = new BoardConfigDTO();
				selectdto.setExTableName("board_access");
				selectdto.setExColumn("bl_num, g_num, a_num, bl_ad_cms, bl_list, bl_read, bl_write, bl_delete, bl_reply");
				selectdto.setExWhereQuery("where a_num = '" + a_num + "' and g_num = '" + g_num + "'");
				HashMap<String, String> boardAccess = exdao.selectQueryRecord(selectdto);
				if (boardAccess.size() > 0) {
					is_ad_cms = Func.nvl(boardAccess.get("bl_ad_cms"));
					is_list = Func.nvl(boardAccess.get("bl_list"));
					is_read = Func.nvl(boardAccess.get("bl_read"));
					is_write = Func.nvl(boardAccess.get("bl_write"));
					is_delete = Func.nvl(boardAccess.get("bl_delete"));
					is_reply = Func.nvl(boardAccess.get("bl_reply"));
				}

				// 게시판설정에 등록된 관리자체크
				if (!"".equals(Func.nvl(config.get("a_ad_cms_id")))) {
					String[] a_ad_cms_id = config.get("a_ad_cms_id").split("\\,"); // 게시판관리자

					for (int i = 0; i < a_ad_cms_id.length; i++) {
						String m_id = ss_m_id;
						if (!"".equals(m_id) && m_id.toLowerCase().equals(a_ad_cms_id[i].trim())) {
							is_ad_cms = "Y";
							break;
						}
					}
				}

				// 최종적으로 is_ad_cms가 Y이면 모든권한 설정
				if ("Y".equals(is_ad_cms)) {
					is_list = "Y";
					is_read = "Y";
					is_write = "Y";
					is_delete = "Y";
					is_reply = "Y";
				}
			}

			request.setAttribute("is_list", is_list);
			request.setAttribute("is_read", is_read);
			request.setAttribute("is_write", is_write);
			request.setAttribute("is_delete", is_delete);
			request.setAttribute("is_ad_cms", is_ad_cms);
			request.setAttribute("is_reply", is_reply);

			// 분류
			if ("Y".equals(Func.nvl(config.get("a_cate")))) {
				selectdto = new BoardConfigDTO(); // select전용 dto 초기화
				selectdto.setExTableName("board_code");
				selectdto.setExColumn("ct_idx, ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_wdate, ct_codeno, a_num, ct_file");
				selectdto.setExWhereQuery("where a_num = '" + a_num + "' and ct_chk = 'Y'");
				selectdto.setExOrderByQuery("order by ct_code");
				List<HashMap<String, String>> codeList = exdao.selectQueryTable(selectdto);
				for (int i = 0; i < codeList.size(); i++) {
					String ct_idx = Func.nvl(codeList.get(i).get("ct_idx"));
					String ct_name = Func.nvl(codeList.get(i).get("ct_name"));
					String ct_file = Func.nvl(codeList.get(i).get("ct_file"));

					String b_cate_temp_cnf = "";
					String b_cate_temp_cnf2 = "";
					if (!ct_file.equals("")) { // 이미지가 잇으면
						b_cate_temp_cnf = "<img src='/data/board_code/" + ct_file + "' alt='" + ct_name + "'/>";
						b_cate_temp_cnf2 = b_cate_temp_cnf;
					} else {
						b_cate_temp_cnf = "[" + ct_name + "]";
						b_cate_temp_cnf2 = ct_name;
					}
					b_cate_str.put(ct_idx, b_cate_temp_cnf);
					b_cate_str2.put(ct_idx, b_cate_temp_cnf2);
				}
				request.setAttribute("codeList", codeList);
			}
			request.setAttribute("b_cate_str", b_cate_str);
			request.setAttribute("b_cate_str2", b_cate_str2);

		}

		// 서버 접속정보
		String SERVER_NAME = (String) request.getServerName().toString();
		String USER_PORT = Func.cStr(request.getServerPort()); // 사용자포트
		if (!USER_PORT.equals("80")) {
			SERVER_NAME += ":" + USER_PORT;
		}
		request.setAttribute("SERVER_NAME", SERVER_NAME);

		//로그인URL 설정
		String board_login_url = "/site/member/ipin.do";

		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			if("marathon".equals(builder_dir)){
				builder_dir = "main";
			}
			board_login_url = "/"+builder_dir+board_login_url;
		}
		request.setAttribute("board_login_url", board_login_url);
		
		
		//현재페이지 (querystring 제외)
		String[] nowpage_arr = Func.getNowPage(request).split("[?]");
		request.setAttribute("nowURI", nowpage_arr[0]);
		
		return "OK";
	}

	/**
	 * @throws IOException
	 * @throws Exception
	 * @title : 토큰비교
	 * @method : checkToken
	 */
	@Override
	public String checkToken(HttpServletRequest request) throws IOException {
		String board_token = Func.nvl(request.getParameter("board_token")).trim();
		if("".equals(board_token)){
			return "N";
		}
		String board_token_decode = this.boardTokenDecode(board_token);

		String referer = Func.nvl(request.getHeader("referer")).trim();
		System.out.println(referer+" , "+request.getHeader("referer"));
		if (referer.contains(board_token_decode)) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * @throws Exception
	 * @title : 스킨별 처리
	 * @method : procSkinMethod
	 */
	@Override
	public void procSkinMethod(Model model, String method_name) throws Exception {
		System.out.println(config.get("a_level") + " " + method_name + " 스킨처리 조회 시작");
		
		// 동적 메소드 실행시 사용할 빈객체 exdao, messageService
		model.addAttribute("exdao", exdao);
		model.addAttribute("messageService", messageService);
		
		String className = config.get("a_level");
		className = className.substring(0, 1).toUpperCase() + className.substring(1);
		try{
			Class cls = Class.forName("kr.co.nninc.ncms.board.service.impl."+className+"ServiceImpl");
			Object obj = cls.newInstance();
			Class[] typePara = new Class[] { Model.class };
			
			try {
				Method method = cls.getMethod(method_name, typePara);
				method.invoke(obj, new Object[] { model });
				System.out.println(className + "ServiceImpl." + method_name + " 스킨처리  성공!!");
			} catch (Exception e) {
				System.out.println(className + "ServiceImpl." + method_name + " 메소드 조회 실패!!");
				//e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println(className + "ServiceImpl 클래스 조회 실패!!");
			//e.printStackTrace();
		}

		System.out.println(config.get("a_level") + " " + method_name + " 스킨처리 조회 종료");
	}

	/**
	 * @throws Exception
	 * @title : 스킨처리에서 전달되는 쿼리 조합
	 * @method : getSkinQuery
	 */
	private String getSkinQuery(SkinDTO skinDTO, String type, String query) throws Exception {

		String skin_query = "";
		if ("s_fields".equals(type) || "orderby_query".equals(type)) {
			if ("s_fields".equals(type)) {
				skin_query = skinDTO.getS_fields();
			} else if ("orderby_query".equals(type)) {
				skin_query = skinDTO.getOrderby_query();
			}
			skin_query = Func.nvl(skin_query);
			if (!"".equals(skin_query)) {
				if (skin_query.trim().substring(0, 1).equals(",")) {
					query += skin_query;
				} else {
					query = skin_query;
				}
			}
		} else if ("where_query".equals(type)) {
			skin_query = Func.nvl(skinDTO.getWhere_query());
			if (!"".equals(skin_query)) {
				skin_query = skin_query.replaceAll("^\\s+","");
				if (skin_query.trim().substring(0, 5).toLowerCase().equals("where")) {
					query = skin_query;
				} else {
					query += " " + skin_query;
				}
			}
		} else if ("paging_query".equals(type)) {
			skin_query = Func.nvl(skinDTO.getPaging_query());
			if (!"".equals(skin_query)) {
				if (skin_query.trim().substring(0, 1).equals("&")) {
					query += skin_query;
				} else {
					query = skin_query;
				}
			}
		}
		return query;
	}

	/**
	 * @throws IOException
	 * @throws Exception
	 * @title : 요청페이지 체크값 생성
	 * @method : getBoardToken
	 */
	private String getBoardToken(HttpServletRequest request) throws IOException {

		String rchr1 = String.valueOf((char) getRandomNumber());
		String rchr2 = String.valueOf((char) getRandomNumber());
		String rchr3 = String.valueOf((char) getRandomNumber());
		String rchr4 = String.valueOf((char) getRandomNumber());
		String rchr5 = String.valueOf((char) getRandomNumber());
		
		String path_info = rchr1 + rchr2 + Func.getNowPage(request).split("[?]")[0] + rchr3 + rchr4;
		String ret_value = rchr5 + Func.base64Encode(path_info);

		return ret_value;
	}
	private int getRandomNumber(){
		Random rnd = new Random();
		int cnt = 0;
		while (true) {
			cnt = rnd.nextInt(58) + 65;
			if (cnt > 90 && cnt < 97) {
				continue;
			}
			break;
		}
		return cnt;
	}
	
	/**
	 * @throws IOException
	 * @throws Exception
	 * @title : 요청페이지 체크값 복호화
	 * @method : getBoardToken
	 */
	private String boardTokenDecode(String value) throws IOException {
		if(value == null){value = "";}
		try{
			value = value.substring(1);
			value = Func.base64Decode(value);
			value = value.substring(2, value.length() - 2);
		}catch(Exception e){}

		return value;
	}

	// 입력값 체크함수
	public String valueChk(String value, String val_name) {
		String ret_value = "";
		if (value == null) {
			value = "";
		}
		if (value.trim().equals("")) {
			ret_value = val_name + "을(를) 입력하세요.\\n";
		}
		return ret_value;
	}
	
	
	
	
	
	
	/**
	 * @title : 휴지통기능
	 * @method : delete_garbage
	 */
	public void delete_garbage(Model model) throws Exception {
		// 게시판 설정세팅
		String a_num = config.get("a_num");
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return;
		}
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String ss_m_id						= Func.getSession(request, "ss_m_id"); //세션아이디
		String user_ip						= Func.remoteIp(request);
		
		String status = Func.nvl( request.getParameter("status") );
		
		//테이블명칭
		String a_tablename = Func.nvl( config.get("a_tablename") );
		
		System.out.println("delete_garbage.status="+status);
		if("totdel".equals(status)) { //다중삭제
			String[] chk = request.getParameterValues("chk");
			for(int z=0;z <= chk.length-1;z++) {
				String b_num = chk[z].trim();
				BoardDTO selectdto = new BoardDTO();
				selectdto.setExTableName("board");
				selectdto.setExColumn("b_subject, b_step, b_level");
				selectdto.setExWhereQuery("where b_num = '"+b_num+"' and a_num = '"+a_num+"' ");
				HashMap<String, String>board = exdao.selectQueryRecord(selectdto);
				int b_step		= Func.cInt(board.get("b_step"));
				int b_level		= Func.cInt(board.get("b_level"));
				String b_subject = Func.nvl(board.get("b_subject"));
				
				String b_keyword = Func.date("Y-m-d H:i:s")+"^"+ss_m_id+"^"+user_ip;
				String sql = "";
				if(b_step == 0 && b_level == 0) {//일반글일때
					sql = "update board set b_look = 'N', b_keyword = '"+b_keyword+"' where b_ref = '"+b_num+"' and a_num = '"+a_num+"' ";
				} else {  //답변글 일때(해당 목록만 삭제)
					sql = "update board set b_look = 'N', b_keyword = '"+b_keyword+"' where b_num = '"+b_num+"' and a_num = '"+a_num+"' ";
				}
				exdao.executeQuery(sql);
				//작업기록
				Func.writeBoardLog ("게시물 휴지통보관 ["+a_tablename+" : "+b_subject+"]", request);
			}
		}else{ //일반삭제
			String b_num = exdao.filter(request.getParameter("b_num"));
			
			BoardDTO selectdto = new BoardDTO();
			selectdto.setExTableName("board");
			selectdto.setExColumn("b_subject, b_step, b_level");
			selectdto.setExWhereQuery("where b_num = '"+b_num+"' and a_num = '"+a_num+"' ");
			HashMap<String, String>board = exdao.selectQueryRecord(selectdto);

			int b_step		= Func.cInt(board.get("b_step"));
			int b_level		= Func.cInt(board.get("b_level"));
			String b_subject = Func.nvl(board.get("b_subject"));
			String b_keyword = Func.date("Y-m-d H:i:s")+"^"+ss_m_id+"^"+user_ip;
			
			String sql = "";
			if(b_step == 0 && b_level == 0) {//일반글일때
				sql = "update board set b_look = 'N', b_keyword = '"+b_keyword+"' where b_ref = '"+b_num+"' and a_num = '"+a_num+"' ";
			} else {  //답변글 일때(해당 목록만 삭제)
				sql = "update board set b_look = 'N', b_keyword = '"+b_keyword+"' where b_num = '"+b_num+"' and a_num = '"+a_num+"' ";
			}
			exdao.executeQuery(sql);
			
			//작업기록
			Func.writeBoardLog ("게시물 휴지통보관 ["+a_tablename+" : "+b_subject+"]", request);
		}
	}
	
	
	
	/**
	 * @title : 게시물 완전삭제
	 * @method : delete_real
	 */
	public void delete_real(Model model) throws Exception {
		// 게시판 설정세팅
		String a_num = config.get("a_num");
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return;
		}
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//테이블명
		String a_tablename = Func.nvl(config.get("a_tablename"));
		
		String realpath =  fileutil.realPath(request, "/data/");
		String strDir = realpath+"/board/"+a_tablename+"/";
		String strDir2 = realpath+"/board/"+a_tablename+"/thum/";
		
		String status = Func.nvl( request.getParameter("status") );
		
		if ("totdel".equals(status)) { //다중삭제
			String[] chk = request.getParameterValues("chk");
			for(int z=0;z <=chk.length-1;z++) {
				String b_num = chk[z].trim();
				BoardDTO selectdto = new BoardDTO();
				selectdto.setExTableName("board");
				String s_fields = "b_subject, b_step, b_level";
				s_fields += ", b_file1, b_file2, b_file3, b_file4, b_file5, b_file6, b_file7, b_file9, b_file10";
				selectdto.setExColumn(s_fields);
				selectdto.setExWhereQuery("where b_num = '"+b_num+"' and a_num = '"+a_num+"' ");
				HashMap<String, String>board = exdao.selectQueryRecord(selectdto);
				if(board.size() > 0){
					int b_step		= Func.cInt(board.get("b_step"));
					int b_level		= Func.cInt(board.get("b_level"));
					String b_subject = Func.nvl(board.get("b_subject"));
					
					if(b_step == 0 && b_level == 0) {//일반글일때
						//이미지 삭제
						selectdto = new BoardDTO();
						selectdto.setExTableName("board");
						s_fields = "b_file1, b_file2, b_file3, b_file4, b_file5, b_file6, b_file7, b_file9, b_file10";
						selectdto.setExColumn(s_fields);
						selectdto.setExWhereQuery("where b_ref = '"+b_num+"' and a_num = '"+a_num+"' ");
						List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
						for(int ii=0;ii<=boardList.size()-1;ii++){
							HashMap<String, String>boardData = (HashMap<String, String>)boardList.get(ii);
							for(int f=1;f<=10;f++){	//파일갯수만큼 반복처리
								String b_file = boardData.get("b_file"+f);
								if(!"".equals(Func.nvl(b_file))) { Func.fileDelete(strDir+b_file); Func.fileDelete(strDir2+b_file); } //삭제
							}
						}
						//삭제하기
						exdao.executeQuery("delete from board where b_ref = '"+b_num+"' and a_num = '"+a_num+"' ");
					} else {  //답변글 일때(해당 목록만 삭제)
						for(int f=1;f<=10;f++){	//파일갯수만큼 반복처리
							String b_file = Func.nvl( board.get("b_file"+f) );
							if(!"".equals(Func.nvl(b_file))) { Func.fileDelete(strDir+b_file); Func.fileDelete(strDir2+b_file); } //삭제
						}
						//삭제하기
						exdao.executeQuery("delete from board where b_num = '"+b_num+"' and a_num = '"+a_num+"' ");
					}
					//작업기록
					Func.writeBoardLog ("게시물 삭제 ["+a_tablename+" : "+b_subject+"]", request);
				}
			}
		} else { //일반삭제
			String b_num = Func.nvl(request.getParameter("b_num"));
			BoardDTO selectdto = new BoardDTO();
			selectdto.setExTableName("board");
			String s_fields = "b_subject, b_step, b_level";
			s_fields += ", b_file1, b_file2, b_file3, b_file4, b_file5, b_file6, b_file7, b_file9, b_file10";
			selectdto.setExColumn(s_fields);
			selectdto.setExWhereQuery("where b_num = '"+b_num+"' and a_num = '"+a_num+"' ");
			HashMap<String, String>board = exdao.selectQueryRecord(selectdto);
			
			if(board.size() > 0){
				String b_subject = Func.nvl( board.get("b_subject") );
				for(int f=1;f<=10;f++){	//파일갯수만큼 반복처리
					String b_file = Func.nvl( board.get("b_file"+f) );
					if(!"".equals(Func.nvl(b_file))) { Func.fileDelete(strDir+b_file); Func.fileDelete(strDir2+b_file); } //삭제
				}
				//삭제하기
				exdao.executeQuery("delete from board where b_num = '"+b_num+"' and a_num = '"+a_num+"' ");
				
				//작업기록
				Func.writeBoardLog ("게시물 삭제 ["+a_tablename+" : "+b_subject+"]", request);
			}
		}
	}

	/**
	 * @title : 스킨별 별도처리페이지
	 * @method : skinProcPage
	 */
	@Override
	public String skinProcPage(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SkinDTO skinDTO = (SkinDTO)map.get("skinDTO");
		
		//a_level이 넘어온다면 property에 선언된 a_num이 있는지 체크
		String a_level = Func.nvl( request.getParameter("a_level") ).trim();
		String a_num = "";
		if(!"".equals(a_level)){
			a_num = Func.nvl( CommonConfig.getBoard(a_level+"[a_num]") ).trim();
			if(!"".equals(a_num))request.setAttribute("a_num", a_num);
		}
		
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String url = Func.nvl( (String)request.getAttribute("javax.servlet.include.request_uri") ).trim();
		if("".equals(url)){
			url = Func.nvl( (String)request.getRequestURI() ).trim();
		}
		System.out.println("skinProcPage.url - "+url);
		
		//처리 메소드 실행
		String methodName = url.substring(url.lastIndexOf('/') + 1, url.length()).replaceAll(".do", "").replaceAll(".jsp", "");
		//기본처리로 바로 접근시도 방지
		if("list".equals(methodName) || "view".equals(methodName) || "write".equals(methodName) || "modify".equals(methodName)
		|| "writeOk".equals(methodName) || "deleteOk".equals(methodName)
		){
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}
		this.procSkinMethod(model, methodName);
		
		//skinDTO 담긴 처리메세지가 있을경우
		String before_msg = Func.nvl(skinDTO.getBefore_msg());
		if (!"".equals(before_msg)) { return before_msg; }
		String after_msg = Func.nvl(skinDTO.getAfter_msg());
		if (!"".equals(after_msg)) { return after_msg; }
		
		
		//prepage가 있으면 redirect 하고 없으면 해당 메소드.jsp 실행
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){
			prepage = "/site/board/"+config.get("a_level")+"/"+methodName;
		}else{
			prepage = "redirect:"+prepage;
		}
		
		return prepage;
	}

	/**
	 * @title : 게시물목록
	 * @method : list
	 */
	@Override
	public String loanList(Model model) throws Exception {
		// 게시판 설정세팅
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		
		String ss_m_id = Func.getSession(request, "ss_m_id"); // 세션아이디
		String ss_m_jumin = Func.getSession(request, "ss_m_jumin"); // 세션 식별번호
		if("".equals(ss_m_jumin)){ss_m_jumin = Func.getSession(request, "ss_m_dupinfo");}
		String nowpage = Func.getNowPage(request); // 현재url을 가져오고 동시에 jsp전송
		String ss_g_num = Func.getSession(request, "ss_g_num");
		// 권한체크
		if("".equals(ss_m_jumin) && ss_g_num.equals("")) {
			//return messageService.backMsg(model, "접근권한이 없습니다.");
			return messageService.redirectMsg(model, "본인인증 페이지로 이동합니다.", "/main/site/member/ipin.do?prepage=" + Func.urlEncode(nowpage));
		}
		
		// 요청페이지 변조방지값
		String board_token = this.getBoardToken(request);
		request.setAttribute("board_token", board_token);
		
		// select 전용 dto
		BoardDTO selectdto = new BoardDTO(); // select전용 dto
		
		// 게시판필드
		String s_fields = exdao.getFieldNames( new BoardDTO(), "b_c_count, b_content" );
		if(!"".equals(ss_m_jumin)) {
			s_fields += ", (select count(*) from board_command WHERE b_jumin = '" + ss_m_jumin
					+ "' AND c_bnum = a.b_num ) as b_c_count";	
		}else{
			s_fields += ", (select count(*) from board_command WHERE b_jumin = '" + ss_m_jumin
					+ "' AND c_bnum = a.b_num ) as b_c_count";
		}
		

		// ----- 공지사항 조회 -------
		String nowdateNotice = Func.date("Y-m-d H:i");
		selectdto.setExTableName("board a");
		selectdto.setExKeyColumn("b_num");
		selectdto.setExColumn(s_fields);

		// 검색조건
		String whereQueryNotice =  "";
		if(!"".equals(ss_m_jumin)) {
			whereQueryNotice = "WHERE b_jumin = '"+ss_m_jumin+"' and b_look = 'Y' AND b_noticechk='Y'";	
		}else{
			whereQueryNotice = "WHERE b_id = '"+ss_m_id+"' and b_look = 'Y' AND b_noticechk='Y'";
		}
		whereQueryNotice += " AND b_notice_sdate <= '" + nowdateNotice + "' AND b_notice_edate >= '" + nowdateNotice
				+ "'";
		if (!"Y".equals(is_ad_cms)) { // 관리자 아닐때 실행
			whereQueryNotice += " AND ( b_show_sdate = '' or b_show_sdate is null";
			whereQueryNotice += " OR (b_show_sdate <> '' and b_show_sdate <= '" + nowdateNotice
					+ "' and b_show_edate  >= '" + nowdateNotice + "'))";
		}
		//분류기능
		String v_cate = exdao.filter(request.getParameter("v_cate"));
		if(!"".equals(v_cate)){
			whereQueryNotice += " and b_cate = '"+v_cate+"'";
		}
		selectdto.setExWhereQuery(whereQueryNotice);

		// 정렬
		String orderByQueryNotice = "ORDER BY b_regdate desc, b_num desc";
		if ("Y".equals(config.get("a_reply"))) { // 답변기능
			orderByQueryNotice = "ORDER BY b_ref desc, b_step asc";
		}
		selectdto.setExOrderByQuery(orderByQueryNotice);
		List<HashMap<String, String>> noticeList = exdao.selectQueryTable(selectdto);
		// new아이콘 표시, 첨부파일 정보설
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/board/" + config.get("a_tablename") + "/";
		

		// ----- 게시물 조회 시작 -------

		// 페이징
		int v_page = Func.cInt(request.getParameter("v_page"));
		if (v_page == 0) {
			v_page = 1;
		}
		request.setAttribute("v_page", v_page);

		int pagesize = 20;
		request.setAttribute("pagesize", pagesize);

		/////////// 스킨처리 - List 데이타 확인 ////////////
		List<HashMap<String, String>> boardList = null;
		if (boardList == null) { // 스킨에서 전달되는 List가 없으면 기본처리
			selectdto = new BoardDTO(); // select전용 dto
			String nowdate = Func.date("Y-m-d H:i");
			selectdto.setExTableName("board a");
			selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
			selectdto.setExColumn(s_fields);
			// whereQuery 생성
			String whereQuery = "";
			if(!"".equals(ss_m_jumin)) {
				whereQuery = "WHERE b_jumin = '"+ss_m_jumin+"' and b_look='Y'";
			}else{
				whereQuery = "WHERE b_id = '"+ss_m_id+"' and b_look='Y'";
			}
			whereQuery += " AND ( b_noticechk = ''";
			whereQuery += " 	OR (b_noticechk = 'Y'";
			whereQuery += " 		AND ( b_notice_sdate <= '" + nowdate + "' OR b_notice_edate >= '" + nowdate + "')";
			whereQuery += "		)";
			whereQuery += ")";
			if (!"Y".equals(is_ad_cms)) { // 관리자가아닐때 실행할것들
				// 회원제
				if ("Y".equals(config.get("a_member"))) {
					if (!"".equals(ss_m_jumin)) {
						whereQuery += " AND ( ( b_jumin = '" + ss_m_jumin + "') or ( b_id = '" + ss_m_id + "' and (b_id <> '' or b_id is not null) ) )";
					} else {
						whereQuery += " AND b_id = '" + ss_m_id + "' and (b_id <> '' or b_id is not null)";
					}
				}
			}
			// 답변기능을 사용하지 않을때
			String a_reply = config.get("a_reply");
			String a_replyOpt = config.get("a_replyOpt");
			if ("N".equals(a_reply) || ("Y".equals(a_reply) && "2".equals(a_replyOpt))) {
				whereQuery += " AND b_type = 'N'";
			}
			// 분류기능 사용시
			//String v_cate = exdao.filter(request.getParameter("v_cate"));
			if ("Y".equals(config.get("a_cate")) && !"".equals(v_cate)) {
				whereQuery += " AND b_cate = '" + v_cate + "'";
			}
			// 검색
			String v_keyword = exdao.filter(request.getParameter("v_keyword"));
			String v_search = exdao.filter(request.getParameter("v_search"));
			if (!"".equals(v_keyword)) {
				whereQuery += " AND " + v_search + " like '%" + v_keyword + "%'";
			}
			selectdto.setExWhereQuery(whereQuery);

			// 정렬
			selectdto.setExOrderByQuery(orderByQueryNotice); // 공지목록 정렬조건을 같이쓰자

			// 페이징 설정
			selectdto.setExPage(v_page); // 현재페이지
			selectdto.setExRecordCount(pagesize); // 목록개수
			// 게시물 목록 List 생성
			boardList = exdao.selectQueryPage(selectdto);
		}
		
		// List가 페이징 타입일 경우
		if (boardList.size() > 0 && boardList.get(0).get("totalcount") != null) {
			int recordcount = Func.cInt(boardList.get(0).get("totalcount")); // 전체레코드수
			request.setAttribute("recordcount", recordcount);
			boardList.remove(0); // 총검색개수행(첫번째행)을 삭제
			int totalpage = (int) Math.ceil(((recordcount - 1) / pagesize) + 1); // '전체덩어리갯수
			request.setAttribute("totalpage", totalpage);
			// 페이징문자열 생성
			Paging paging = new Paging();
			paging.pageKeyword = "v_page"; // 페이지파라미터명
			paging.page = v_page; // 현재페이지
			paging.block = 10; // 페이지링크 갯수
			paging.totalpage = totalpage; // 총페이지 갯수
			String querystring2 = paging.setQueryString(request, "v_search, v_keyword, v_cate");
			
			querystring2 += "&b_jumin=" + ss_m_jumin;
			String pagingtag = paging.execute(querystring2);
			request.setAttribute("pagingtag", pagingtag);
		}

		// new아이콘 표시, 첨부파일아이콘, 분류
		for (int i = 0; i < boardList.size(); i++) {
			HashMap<String, String> board_new = (HashMap<String, String>) boardList.get(i);
			
			SelectDTO selectDTO2 = new SelectDTO();
			selectDTO2.setExTableName("board_config");
			selectDTO2.setExColumn("a_bbsname");
			selectDTO2.setExWhereQuery("where a_num = '"+board_new.get("a_num")+"'");
			String bbsname = exdao.selectQueryColumn(selectDTO2);
			board_new.put("bbs_name", bbsname);
			
			boolean newIcon = Func.get_newimg(board_new.get("b_regdate"), Func.cInt(config.get("a_new")));
			if (newIcon) {
				board_new.put("newIcon", "true");
			} else {
				board_new.put("newIcon", "false");
			}

			// 기간설정에 따른 줄처리
			String title_style = "";
			String show_str = "";
			String b_show_sdate_str = "";
			String b_show_edate_str = "";
			if ("Y".equals(config.get("a_show"))
					&& (is_ad_cms.equals("Y") || (ss_m_id.equals(board_new.get("b_id")) && !ss_m_id.equals("")))) {
				String b_show_sdate = Func.nvl(board_new.get("b_show_sdate"));
				String b_show_edate = Func.nvl(board_new.get("b_show_edate"));
				if (!"".equals(b_show_sdate) && !"".equals(b_show_sdate)) {
					b_show_sdate_str = b_show_sdate.substring(2, 10).replaceAll("-", ".");
					b_show_edate_str = b_show_sdate.substring(2, 10).replaceAll("-", ".");
					if (b_show_sdate.substring(0, 10).compareTo(Func.date("Y-m-d ")) > 0
							|| b_show_edate.substring(0, 10).compareTo(Func.date("Y-m-d")) < 0) {
						title_style = "text-decoration:line-through;";
						show_str = "<span style='font-size:8pt;' class='" + Func.nvl(board_new.get("b_sbjclr")) + "'>("
								+ b_show_sdate_str + "~" + b_show_edate_str + ")</span>&nbsp;&nbsp;";
					}
				}
			}
			board_new.put("txt_title_style", title_style);
			board_new.put("txt_show", show_str);

			//첨부파일정보설정
			for (int j = 1; j <= 10; j++) {
				String b_file = Func.nvl(board_new.get("b_file" + j));
				if (!"".equals(b_file)) {
					File file = new File(strDir + b_file);
					if (file.exists()) {
						// 파일용량담기
						board_new.put("b_file" + j + "_size", Func.byteConvert(Func.cStr(file.length())));
					}
				}
				// 파일이미지아이콘담기
				board_new.put("b_file" + j + "_icon", Func.get_FileName(b_file));
			}
		}
		request.setAttribute("boardList", boardList);
		// ----- 게시물 조회끝 -------

		return "/site/board/myinfo";
	}
	
	/**
	 * @title : 게시물 삭제처리
	 * @method : levelOk
	 */
	@Override
	public String levelOk(Model model) throws Exception {
		// 게시판 설정세팅
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}
		/////////// 스킨처리 ////////////
		this.procSkinMethod(model, "levelOk");
		/////////// 스킨처리 ////////////

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");

		////////////////////////////////////////////
		/////////// 스킨처리 - 처리전 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String before_msg = Func.nvl(skinDTO.getBefore_msg());
		if (!"".equals(before_msg)) {
			return before_msg;
		}
		
		String a_num = Func.nvl( config.get("a_num") );
		String d_page = Func.nvl(request.getParameter("d_page"));	//기본페이지
		if("".equals(d_page)){d_page = "/";}
		
		String prepage = Func.nvl(request.getParameter("prepage"));	//되돌아갈 페이지
		if("".equals(prepage)){prepage = d_page+"?proc_type=list&a_num="+a_num;}
		
		String ss_m_id = Func.getSession(request, "ss_m_id");
		String ss_m_jumin = Func.getSession(request, "ss_m_dupinfo");
		String status = Func.nvl( request.getParameter("status") );
		String tot_level_chk = Func.nvl(request.getParameter("tot_level_chk"));
		
		
		//테이블명
		String a_tablename = Func.nvl(config.get("a_tablename"));
						
		if ("totlevel".equals(status)) { //다중상태변경
			String[] chk = request.getParameterValues("chk");
			for(int z=0;z <=chk.length-1;z++) {
				String b_num = chk[z].trim();
				BoardDTO selectdto = new BoardDTO();
				selectdto.setExTableName("board");
				String s_fields = "b_subject, b_step, b_level";
				s_fields += ", b_file1, b_file2, b_file3, b_file4, b_file5, b_file6, b_file7, b_file9, b_file10";
				selectdto.setExColumn(s_fields);
				selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
				HashMap<String, String>board = exdao.selectQueryRecord(selectdto);
				if(board.size() > 0){
					int b_step		= Func.cInt(board.get("b_step"));
					int b_level		= Func.cInt(board.get("b_level"));
					String b_subject = Func.nvl(board.get("b_subject"));
							
					if(b_step == 0 && b_level == 0) {//일반글일때
						//수정하기
						exdao.executeQuery("update board set b_temp10 = '"+tot_level_chk+"' where b_ref = '"+b_num+"' ");
					} else {  //답변글 일때(해당 목록만 삭제)
						//수정하기
						exdao.executeQuery("update board set b_temp10 = '"+tot_level_chk+"'  where b_num = '"+b_num+"' ");
					}
					//작업기록
					Func.writeBoardLog ("게시물 삭제 ["+a_tablename+" : "+b_subject+"]", request);
				}
			}
		}
		
		////////////////////////////////////////////
		/////////// 스킨처리 - 처리후 리디렉션 메세지 ////////////
		////////////////////////////////////////////
		String after_msg = Func.nvl(skinDTO.getAfter_msg());
		if (!"".equals(after_msg)) {
			return after_msg;
		}
		
		
		return "redirect:"+prepage;
	}
}
