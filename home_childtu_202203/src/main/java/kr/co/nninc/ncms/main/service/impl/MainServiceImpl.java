package kr.co.nninc.ncms.main.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.banner2.service.Banner2DTO;
import kr.co.nninc.ncms.banner3.service.Banner3DTO;
import kr.co.nninc.ncms.banner4.service.Banner4DTO;
import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.edusat.service.EdusatDTO;
import kr.co.nninc.ncms.main.service.MainService;
import kr.co.nninc.ncms.popup.service.PopupDTO;


@Service("mainService")
public class MainServiceImpl extends EgovAbstractServiceImpl
	implements MainService
{

	@Resource(name="extendDAO")
	private ExtendDAO exdao;

	@Resource(name="messageService")
	private MessageService messageService;

	
	/**
	 * @title : 메인스킨별 메소드 처리
	 * @method : procSkinMethod
	 */
	public void procSkinMethod(Model model) throws Exception {
		procSkinMethod(model, "main");
	}
	public void procSkinMethod(Model model, String methodName) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");

		//String method_name = Func.nvl((String)request.getAttribute("BUILDER_MAIN"));
		
		String className = Func.nvl((String)request.getAttribute("BUILDER_MAIN")).trim();
		className = className.substring(0, 1).toUpperCase() + className.substring(1);
		if(className.equals("Main01") && methodName.equals("ajaxCalendar")) className = "Main";
		
		System.out.println(className + " 메인스킨 - "+methodName+" 처리 시작");

		model.addAttribute("exdao", this.exdao);
		model.addAttribute("messageService", this.messageService);
		try
		{
			Class cls = Class.forName("kr.co.nninc.ncms.main.service.impl."+className+"ServiceImpl");
			Object obj = cls.newInstance();
			Class[] typePara = { Model.class };
			try
			{
				Method method = cls.getMethod(methodName, typePara);
				method.invoke(obj, new Object[] { model });
				System.out.println(className+"ServiceImpl."+methodName+" 스킨처리 성공!!");
			} catch (IllegalAccessException e) {
				System.out.println(className+"ServiceImpl."+methodName+" 메소드 처리 실패!!");
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				System.out.println(className+"ServiceImpl."+methodName+" 메소드 처리 실패!!");
				e.getTargetException().printStackTrace(); //getTargetException
			} catch (Exception e) {
				System.out.println(className+"ServiceImpl."+methodName+" 메소드 조회 실패!!");
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println(className+" 클래스 조회 실패!!");
		}

		System.out.println(className + " 메인스킨 - "+methodName+" 처리 종료");
	}

	/**
	 * @title : 배너
	 * @method : bannerList
	 */
	public void bannerList(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		SelectDTO selectdto = new SelectDTO();

		String nowdate = Func.date("Y-m-d H:i:s");

		selectdto.setExTableName("banner");
		selectdto.setExKeyColumn("b_l_num");	//고유컬럼 설정 필수
		selectdto.setExColumn("b_l_num, info_url, b_l_code, b_l_img, b_l_url, b_l_sdate, b_l_edate, b_l_subject, b_l_memo, b_l_wdate, b_l_mdate");
		String where_query = "where b_l_sdate <= '" + nowdate + "'";
		where_query = where_query + " and ( b_l_edate >= '" + nowdate + "' or unlimited='Y' )";
		where_query = where_query + " and b_l_chk = 'Y'";
		String builder_dir = this.exdao.filter((String)request.getAttribute("BUILDER_DIR"));
		if (!"".equals(builder_dir)) {
			where_query = where_query + " and site_dir = '" + builder_dir + "'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("ORDER BY B_L_CODE ASC");
		List bannerList = this.exdao.selectQueryTable(selectdto);
		request.setAttribute("bannerList", bannerList);
	}

	/**
	 * @title : 팝업
	 * @method : popupList
	 */
	public void popupList(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		SelectDTO selectdto = new SelectDTO();

		String nowdate = Func.date("Y-m-d H:i:s");

		selectdto.setExTableName("popup");
		selectdto.setExKeyColumn("idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new PopupDTO());
		String where_query = "where (w_chk = 'Y' or w_chk = 'L' )";
		where_query = where_query + " and sdate <= '" + nowdate + "'";
		where_query = where_query + " and ( edate >= '" + nowdate + "' or unlimited='Y' )";
		String builder_dir = this.exdao.filter((String)request.getAttribute("BUILDER_DIR"));
		if (!"".equals(builder_dir)) {
			where_query = where_query + " and site_dir = '" + builder_dir + "'";
		}
		if("Y".equals(Func.getSession(request, "pc_version"))){
			where_query += " and (pos = 'A' or pos = 'P')";
		}else{
			where_query += " and (pos = 'A' or pos = 'M')";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("ORDER BY sdate ASC");
		List<HashMap<String, String>> popupList = this.exdao.selectQueryTable(selectdto);
		
		for (HashMap<String, String> hashMap : popupList) {
			String content = hashMap.get("content");
			Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
			Matcher matcher = pattern.matcher(content);
			int z = 1;
			while (matcher.find()) {
				hashMap.put("img_src", matcher.group(1));
			}
		}
		request.setAttribute("popupList", popupList);
	}

	/**
	 * @title : 게시물 리스트
	 * @method : boardList
	 */
	public List<HashMap<String, String>> boardList(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}
		String a_num = this.exdao.filter(request.getParameter("a_num"));
		int pagecount = Func.cInt( this.exdao.filter(request.getParameter("pagecount")) );
		return this.boardList(model, a_num, pagecount);
	}
	public List<HashMap<String, String>> boardList(Model model, int pagecount) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}
		String a_num = this.exdao.filter(request.getParameter("a_num"));
		return this.boardList(model, a_num, pagecount);
	}
	public List<HashMap<String, String>> boardList(Model model, String a_num, int pagecount) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		String b_cate = this.exdao.filter(request.getParameter("b_cate"));
		if(pagecount == 0){pagecount = 5;}
		String v_page = this.exdao.filter(request.getParameter("v_page"));
		if ("".equals(v_page)) v_page = "1";

		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board_total");
		selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		selectdto.setExColumn(exdao.getFieldNames(new BoardDTO())+", a_site_dir, a_bbsname, a_tablename");
		selectdto.setExRecordCount(pagecount);
		selectdto.setExPage(Func.cInt(v_page));
		String whereQuery = "where b_look = 'Y' and b_open = 'Y'";
		if (!"".equals(a_num)) {
			whereQuery = whereQuery + " and a_num in (" + a_num + ")";
		}
		if (!"".equals(b_cate)) {
			whereQuery = whereQuery + " and b_cate = '" + b_cate + "'";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by b_regdate desc");
		List boardList = this.exdao.selectQueryPage(selectdto);
		boardList.remove(0);

		return boardList;
	}
	public List<HashMap<String, String>> mainBoardList(Model model, String a_num, int pagecount) {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board_total");
		selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		selectdto.setExColumn(exdao.getFieldNames(new BoardDTO())+", a_site_dir, a_bbsname, a_tablename");
		selectdto.setExRecordCount(pagecount);
		selectdto.setExPage(Func.cInt(0));
		String whereQuery = "where b_look = 'Y' and b_open = 'Y'";
		if(a_num.equals("69394427")) whereQuery = whereQuery + " and b_temp10 <> 'N' ";
		if (!"".equals(a_num)) {
			whereQuery = whereQuery + " and a_num in (" + a_num + ")";
		}

		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by b_regdate desc");
		List boardList = this.exdao.selectQueryPage(selectdto);
		boardList.remove(0);

		return boardList;
	}
	// 설문조사
	public List<HashMap<String, String>> mainPollList(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		String nowdate = Func.date("Y-m-d H:i:s");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("poll");
		selectdto.setExKeyColumn("po_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn("po_subject, po_idx, po_wdate, po_pk, po_sdate, po_edate");
		String whereQuery = "where po_chk = 'Y' and po_sdate <'"+nowdate+"' and po_edate > '"+nowdate+"'";

		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by po_wdate desc limit 1");
		List<HashMap<String, String>> boardList = this.exdao.selectQueryTable(selectdto);

		return boardList;
	}

	
	/**
	 * @title : 팝업존
	 * @method : banner2List
	 */
	public void banner2List(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		SelectDTO selectdto = new SelectDTO();

		String nowdate = Func.date("Y-m-d H:i:s");

		selectdto.setExTableName("banner2");
		selectdto.setExKeyColumn("b_l_num");	//고유컬럼 설정 필수
		selectdto.setExColumn(new Banner2DTO());
		String where_query = "where b_l_sdate <= '" + nowdate + "'";
		where_query = where_query + " and ( b_l_edate >= '" + nowdate + "' or unlimited='Y' )";
		where_query = where_query + " and b_l_chk = 'Y'";
		String builder_dir = this.exdao.filter((String)request.getAttribute("BUILDER_DIR"));
		if (!"".equals(builder_dir)) {
			where_query = where_query + " and site_dir = '" + builder_dir + "'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("ORDER BY B_L_CODE ASC");
		List<HashMap<String,String>>banner2List = exdao.selectQueryTable(selectdto);
		request.setAttribute("banner2List", banner2List);
	}

	/**
	 * @title : 상단팝업
	 * @method : banner3List
	 */
	public void banner3List(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		SelectDTO selectdto = new SelectDTO();

		String nowdate = Func.date("Y-m-d H:i:s");

		selectdto.setExTableName("banner3");
		selectdto.setExKeyColumn("b_l_num");	//PK 키 설정 필수
		selectdto.setExColumn(new Banner3DTO());
		String where_query = "where b_l_sdate <= '" + nowdate + "'";
		where_query = where_query + " and ( b_l_edate >= '" + nowdate + "' or unlimited='Y' )";
		where_query = where_query + " and b_l_chk = 'Y'";
		String builder_dir = this.exdao.filter((String)request.getAttribute("BUILDER_DIR"));
		if (!"".equals(builder_dir)) {
			where_query = where_query + " and site_dir = '" + builder_dir + "'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("ORDER BY B_L_CODE ASC");
		List<HashMap<String, String>> banner3List = exdao.selectQueryTable(selectdto);
		request.setAttribute("banner3List", banner3List);
	}
	
	/**
	 * @title : 풀팝업
	 * @method : banner3List
	 */
	public void banner4List(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		SelectDTO selectdto = new SelectDTO();

		String nowdate = Func.date("Y-m-d H:i:s");

		selectdto.setExTableName("banner4");
		selectdto.setExKeyColumn("b_l_num");	//PK 키 설정 필수
		selectdto.setExColumn(new Banner4DTO());
		String where_query = "where b_l_sdate <= '" + nowdate + "'";
		where_query = where_query + " and ( b_l_edate >= '" + nowdate + "' or unlimited='Y' )";
		where_query = where_query + " and b_l_chk = 'Y'";
		String builder_dir = this.exdao.filter((String)request.getAttribute("BUILDER_DIR"));
		if (!"".equals(builder_dir)) {
			where_query = where_query + " and site_dir = '" + builder_dir + "'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("ORDER BY B_L_CODE ASC");
		List<HashMap<String, String>> banner4List = exdao.selectQueryTable(selectdto);
		request.setAttribute("banner4List", banner4List);
	}

	/**
	 * @title : 상단 휴관일
	 * @method : closeList
	 */
	public void closeList(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("close");
		selectdto.setExColumn("cl_category, cl_name");
		selectdto.setExWhereQuery("where cl_date = '" + Func.date("Y-m-d") + "'");
		selectdto.setExOrderByQuery("order by cl_category");
		List closeList = this.exdao.selectQueryTable(selectdto);
		request.setAttribute("closeList", closeList);
	}

	/**
	 * @title : 메인 도서관별 월별 휴관일
	 * @method : closeListMain
	 */
	public String closeListMain(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		String cl_category = this.exdao.filter(request.getParameter("cl_category"));

		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("close");
		selectdto.setExColumn("cl_date, cl_name");
		selectdto.setExWhereQuery("where cl_date like '" + Func.date("Y-m") + "%' and cl_category = '" + cl_category + "'");
		selectdto.setExOrderByQuery("order by cl_date");
		List closeList = this.exdao.selectQueryTable(selectdto);
		request.setAttribute("closeList", closeList);

		String days = "";
		for (int i = 0; i <= closeList.size() - 1; i++) {
			if (i > 0) days = days + ", ";
			String[] cl_date_arr = ((String)((HashMap)closeList.get(i)).get("cl_date")).split("-");
			days = days + cl_date_arr[(cl_date_arr.length - 1)];
		}

		return days;
	}
	
	
	
	/**
	 * @title : 이달의 행사
	 * @method : monthEvent
	 */
	public void monthEvent(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		SelectDTO selectdto = new SelectDTO();
		String nowym = Func.date("Y-m");
		String nowsdate = Func.date("Y-m-01");
		String nowedate = Func.dateSerial(Func.cInt(Func.date("Y")), Func.cInt(Func.date("m"))+1, 0);
		
		String builder_dir = Func.getNowPage(request).split("/")[1];
		String a_num = exdao.filter(request.getParameter("a_num"));
		
		//등록된 행사일정
		selectdto.setExTableName("board_total");
		selectdto.setExColumn("'event' as event_type, a_num, b_num, b_subject, b_sdate, b_edate");
		String whereQuery = "where b_sdate <= '"+nowedate+"' and b_edate >= '"+nowsdate+"' and b_open = 'Y' and b_look = 'Y'";
		if("".equals(a_num)){
			//whereQuery += " and a_level = 'lib_event' and a_site_dir = '"+builder_dir+"')";	//개별사이트내 통합
			whereQuery += " and a_level = 'lib_event'";		//전체 통합
		}else{
			whereQuery += " and a_num = '"+a_num+"' ";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by b_sdate");
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
		request.setAttribute("boardList", boardList);
		
		/*
		//등록된 영화상영
		selectdto = new SelectDTO();
		selectdto.setExTableName("board_total");
		selectdto.setExColumn("'movie' as event_type, a_num, b_num, b_subject, b_sdate, b_edate");
		whereQuery = "where b_sdate like '"+nowym+"%' and b_open = 'Y' and b_look = 'Y'";
		if("".equals(a_num)){
			whereQuery += " and a_level = 'movie' and a_num in (select ct_anum from homepage_menu where ct_site_dir = '"+builder_dir+"' and ct_anum <> '')";
		}else{
			String movie_anum = CommonConfig.getBoard("lib_event.movie_a_num["+a_num+"]");
			whereQuery += " and a_num = '"+movie_anum+"' ";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by b_sdate");
		List<HashMap<String,String>>movieList = exdao.selectQueryTable(selectdto);
		request.setAttribute("movieList", movieList);
		*/
		
		//등록된 강좌
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn("'edu' as event_type, "+exdao.getFieldNames(new EdusatDTO()));
		whereQuery = "where edu_resdate <= '"+nowedate+"' and edu_reedate >= '"+nowsdate+"' and end_chk = 'Y'";
		if("".equals(a_num)){
			whereQuery += "";
		}else{
			String edusat_ct_idx = CommonConfig.getBoard("lib_event.edusat_ct_idx["+a_num+"]");
			whereQuery += " and ct_idx = '"+edusat_ct_idx+"' ";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by edu_resdate");
		List<HashMap<String,String>>edusatList = exdao.selectQueryTable(selectdto);
		
		for(int i=0; i<edusatList.size();i++) {
			HashMap<String,String>edusat = edusatList.get(i);
			int req_count = getReqCount(model, edusat.get("edu_idx"), edusat.get("edu_ptcp_yn"));
			edusat.put("req_count", Func.cStr(req_count));	//신청인원
		}
		request.setAttribute("edusatList", edusatList);
	}
	public int getReqCount(Model model, String edu_idx, String edu_ptcp_yn) throws Exception {
		Map map = model.asMap();
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request");
		
		if("Y".equals(edu_ptcp_yn)){
			selectdto.setExColumn("sum(es_ptcp_cnt)");
		}else{
			selectdto.setExColumn("count(*)");
		}
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"' and es_status <> 9");
		int count = Func.cInt( exdao.selectQueryColumn(selectdto) );
		
		return count;
	}

	
	/**
	 * @title : 메인강좌 리스트
	 * @method : monthEvent
	 */
	public void eduList(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		String nowym = Func.date("Y-m");
		String nowsdate = Func.date("Y-m-01");
		String nowedate = Func.dateSerial(Func.cInt(Func.date("Y")), Func.cInt(Func.date("m"))+1, 0);
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn("'edu' as event_type, "+exdao.getFieldNames(new EdusatDTO()));
		String whereQuery = "where  end_chk = 'Y' and edu_resdate <= '"+nowedate+"' and edu_reedate >= '"+nowsdate+"'";
		if(!"".equals(builder_dir)){
			whereQuery += " and ct_idx in (select ct_idx from code_config where ct_ref=1 and ct_site_dir = '"+builder_dir+"') ";
		}
		
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by edu_idx desc");
		List<HashMap<String,String>>eduList = exdao.selectQueryTable(selectdto);
		
		for(int i=0; i<eduList.size();i++) {
			HashMap<String,String>edusat = eduList.get(i);
			int req_count = getReqCount(model, edusat.get("edu_idx"), edusat.get("edu_ptcp_yn"));
			edusat.put("req_count", Func.cStr(req_count));	//신청인원
		}
		request.setAttribute("eduList", eduList);

	}
	
	
	/**
	 * @title : 영화상영
	 * @method : monthEvent
	 */
	public void movieList(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		String builder_dir = Func.getNowPage(request).split("/")[1];
		String nowymd = Func.date("Y-m-d");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board_total");
		selectdto.setExKeyColumn("b_num");	//PK 키 설정 필수
		selectdto.setExColumn("a_num, b_num, b_subject, b_sdate, b_file1, b_keyword, b_temp1, b_temp2");
		String whereQuery = "where a_level = 'movie' and b_sdate >= '"+nowymd+"' and b_open = 'Y' and b_look = 'Y'";
		whereQuery += " and a_num in (select ct_anum from homepage_menu where ct_site_dir = '"+builder_dir+"' and ct_anum <> '')";
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by b_sdate");
		HashMap<String,String>movie = exdao.selectQueryRecord(selectdto);
		if(movie.size() > 0){
			String b_sdate_str = Func.date("m.d(q)", movie.get("b_sdate"));
			movie.put("b_sdate_str", b_sdate_str);
		}
		request.setAttribute("movie", movie);
		
	}
	
	/**
	 * @title : 추천도서
	 * @method : recomBookList
	 */
	public void recomBookList(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		if (this.exdao == null) {
			this.exdao = ((ExtendDAO)map.get("exdao"));
		}

		String builder_dir = Func.getNowPage(request).split("/")[1];
		String nowym = Func.date("Y-m");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board_total");
		selectdto.setExColumn("a_num, b_num, b_subject, b_sdate, b_file1, b_keyword, b_temp1, b_temp2, b_temp3");
		String whereQuery = "where a_level = 'recommend_book' and b_temp1 <= '"+nowym+"' and b_open = 'Y' and b_look = 'Y'";
		whereQuery += " and a_num in (select ct_anum from homepage_menu where ct_site_dir = '"+builder_dir+"' and ct_anum <> '')";
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by b_num desc");
		selectdto.setExRecordCount(3);
		List<HashMap<String,String>>recommBookList = exdao.selectQueryTable(selectdto);
		request.setAttribute("recommBookList", recommBookList);
		
	}
	
	
	/**
	 * @title : 이달의 행사 달력
	 * @method : monthEventCalendar
	 */
	public void monthEventCalendar(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		ExtendDAO exdao = ((ExtendDAO)map.get("exdao"));
		
		
		String year = Func.nvl( request.getParameter("year") );
		String month = Func.nvl( request.getParameter("month") );

		int intThisYear  = Func.cInt( Func.date("Y") );
		int intThisMonth  = Func.cInt( Func.date("m") );

		if ( !year.equals("") ) { intThisYear = Func.cInt( year); }
		if ( !month.equals("") ) { intThisMonth = Func.cInt( month ); }


		String intThisMonth2 =  Func.cStr( intThisMonth );
		if(intThisMonth < 10){intThisMonth2 = "0"+intThisMonth2;}

		int gday		= Func.cInt( Func.date("d") ); //오늘날짜
		int datFirstDay	= Func.cInt( Func.date("w", Func.dateSerial( intThisYear, intThisMonth, 1 )) ); //요일 구하기 (num)
		int intLastDay	= Func.cInt( Func.date("d", Func.dateSerial( intThisYear, intThisMonth+1, 1-1 )) ); //마지막날 구하기

		//php 처리로직에 맞추기위해 요일값을 변경
		datFirstDay--;
		int jucnt			= Func.cInt( Func.formatDecimal( Math.ceil( (datFirstDay + intLastDay)/7.0 ), 0) );

		// 이전, 다음 만들기 
		int prevYear		= ( intThisMonth == 1 )? ( intThisYear - 1 ) : intThisYear; 
		int prevMonth		= ( intThisMonth == 1 )? 12 : ( intThisMonth - 1 ); 

		int nextYear		= ( intThisMonth == 12 )? ( intThisYear + 1 ) : intThisYear; 
		int nextMonth		= ( intThisMonth == 12 )? 1 : ( intThisMonth + 1 ); 
		
		//System.out.println("w="+Func.date("w"));
		
		request.setAttribute("datFirstDay", datFirstDay);
		request.setAttribute("intLastDay", intLastDay);
		request.setAttribute("jucnt", jucnt);
		request.setAttribute("prevYear", prevYear);
		request.setAttribute("prevMonth", prevMonth);
		request.setAttribute("nextYear", nextYear);
		request.setAttribute("nextMonth", nextMonth);
		request.setAttribute("intThisYear", intThisYear);
		request.setAttribute("intThisMonth", intThisMonth);
		request.setAttribute("intThisMonth2", intThisMonth2);
		request.setAttribute("year", Func.date("Y"));
		request.setAttribute("month", Func.date("m"));

		
		String a_num = exdao.filter( request.getParameter("a_num") );
		SelectDTO selectdto = new SelectDTO();
		
		//도서관일정별 연동할 자료설정
		String cl_category = Func.nvl(CommonConfig.getBoard("lib_event.cl_category["+a_num+"]")).trim();
		String movie_a_num = Func.nvl(CommonConfig.getBoard("lib_event.movie_a_num["+a_num+"]")).trim();
		String edusat_ct_idx = Func.nvl(CommonConfig.getBoard("lib_event.edusat_ct_idx["+a_num+"]")).trim();
		String thisYm = Func.dateSerial(intThisYear, intThisMonth, 1).substring(0, 7);
		String where_query = "";
		
		//이번달에 행사기간이 포함된 행사조회
		HashMap<String, List> eventBoardMap = new HashMap();	//문화행사 리스트
		selectdto = new SelectDTO();
		selectdto.setExTableName("board_total");
		selectdto.setExColumn("a_num, a_site_dir, a_bbsname, b_num, b_subject, b_sdate, b_edate");
		where_query = "where b_look = 'Y' and b_open = 'Y'";
		if("".equals(a_num)){	//개별도서관 지정
			where_query += " and a_level = 'lib_event'";
		}else{
			where_query += " and a_num in ("+a_num+")";
		}
		where_query += " and (b_sdate <= '"+thisYm+"-31' and b_edate >= '"+thisYm+"-01')";
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by b_num desc");
		List<HashMap<String,String>>tmpEventBoardList = exdao.selectQueryTable(selectdto);
		for(int i=1;i<=intLastDay;i++){
			String thisDate = Func.dateSerial(intThisYear, intThisMonth, i);
			String thisweek = Func.date("w", thisDate);
			thisweek = Func.cStr( Func.cInt(thisweek)-1 );
			int intThisDate = Func.cInt( thisDate.replaceAll("-", "") );
			List<HashMap<String,String>>eventBoardList = new ArrayList<HashMap<String,String>>();
			
			for(int j=0;j<=tmpEventBoardList.size()-1;j++){
				HashMap<String,String>eventBoard = tmpEventBoardList.get(j);
				
				int intB_sdate = Func.cInt( eventBoard.get("b_sdate").replaceAll("-", "") );
				int intB_edate = Func.cInt( eventBoard.get("b_edate").replaceAll("-", "") );
				//이날짜가 행사기간에 포함되어 있다면
				if(intThisDate >= intB_sdate && intThisDate <= intB_edate){
					eventBoardList.add(eventBoard);
				}
			}
			eventBoardMap.put(thisDate, eventBoardList);
		}
		request.setAttribute("eventBoardMap", eventBoardMap);
		
		
		
	}
	
	/**
	 * @title : 메인달력 ajax 처리
	 * @method : ajaxBoardList
	 */
	public void ajaxCalendar(Model model) throws Exception {
		monthEventCalendar(model);
	}
	
	
	
}