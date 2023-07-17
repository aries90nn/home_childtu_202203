package kr.co.nninc.ncms.edusat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.edusat.service.EdusatSeasonDTO;
import kr.co.nninc.ncms.edusat.service.EdusatSeasonSerivce;


/**
 * 온라인수강신청 기수관리를 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.09.09
 * @version 1.0
 */
@Service("edusatSeasonService")
public class EdusatSeasonServiceImpl extends EgovAbstractServiceImpl implements EdusatSeasonSerivce {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;

	/**
	 * @title : 신청차수 목록
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String ss_g_num = Func.getSession(request, "ss_g_num");
		String sh_es_libcode = exdao.filter(request.getParameter("sh_es_libcode"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		//도서관 코드 가져오기
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("ct_idx, ct_name, ct_codeno");
		String where_query = "where ct_ref=1 and ct_chk ='Y'";
		if(!"".equals(builder_dir)){	//해당 도서관만
			where_query += " and ct_site_dir = '"+exdao.filter(builder_dir)+"'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>libList = exdao.selectQueryTable(selectdto);
		request.setAttribute("libList", libList);
		
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_season a left outer join code_config b on a.es_libcode = b.ct_idx");
		selectdto.setExKeyColumn("a.es_num");	//고유컬럼 설정 필수
		selectdto.setExColumn(exdao.getFieldNames(new EdusatSeasonDTO())+", b.ct_name as libname");
		
		String whereQuery = "";
		//자기도서관해당하는것만 보기
		if(!"".equals(builder_dir) && "".equals(sh_es_libcode)){
			if(libList.size() > 0) {
				sh_es_libcode = libList.get(0).get("ct_idx");
			}
		}
		if(!"".equals(v_search) && !"".equals(v_keyword)){ whereQuery += " and "+v_search+" like '%"+v_keyword+"%'"; }
		if(!"".equals(sh_es_libcode)){ whereQuery += " and es_libcode ='"+sh_es_libcode+"'"; }
		
		
		selectdto.setExWhereQuery(whereQuery);
		
		selectdto.setExOrderByQuery("order by es_num desc");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		
		List<HashMap<String,String>> seasonList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( seasonList.get(0).get("totalcount") ); // 전체레코드 수
		
		request.setAttribute("recordcount", recordcount);
		seasonList.remove(0);	//총검색개수행(첫번째행)을 삭제

		// setAttribute
		request.setAttribute("seasonList", seasonList);
		
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = pagePerBlock;	//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring2 = paging.setQueryString(request, "v_search, v_keyword, sh_es_libcode");
		
		String pagingtag = paging.execute(querystring2);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pagesize", pagesize);
		//페이징문자열 생성 끝


	}

	/**
	 * @title : 신청차수 등록
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String ss_g_num = Func.getSession(request, "ss_g_num");
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		//도서관 코드 가져오기
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("ct_idx, ct_name, ct_codeno");
		String where_query = "where ct_ref=1 and ct_chk ='Y'";
		
		if(!"".equals(builder_dir)){ //해당 도서관만
			where_query += " and ct_site_dir = '"+builder_dir+"'";
		}
		
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>libList = exdao.selectQueryTable(selectdto);
		request.setAttribute("libList", libList);

	}

	/**
	 * @title : 신청차수 등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		EdusatSeasonDTO seasondto = (EdusatSeasonDTO) map.get("edusatseasondto");
		
		seasondto.setEs_wid(Func.getSession(request, "ss_m_id"));
		seasondto.setEs_regdate(Func.date("Y-m-d H:i:s"));
		
		//insert 제외
		seasondto.setEs_num(null);
		
		//insert 처리
		seasondto.setExTableName("edusat_season");
		exdao.insert(seasondto);
		
		//작업기록
		Func.writeManagerLog ("온라인프로그램 기수 등록["+seasondto.getEs_name()+"]", request);
		
	}

	/**
	 * @title : 신청차수 수정
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String es_num = exdao.filter(request.getParameter("es_num"));
		String ss_g_num = Func.getSession(request, "ss_g_num");
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		//도서관 코드 가져오기
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("ct_idx, ct_name, ct_codeno");
		String where_query = "where ct_ref=1 and ct_chk ='Y'";
		
		if(!"".equals(builder_dir)){ //해당 도서관만
			where_query += " and ct_site_dir = '"+builder_dir+"'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>libList = exdao.selectQueryTable(selectdto);
		request.setAttribute("libList", libList);
		
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_season");
		selectdto.setExColumn(new EdusatSeasonDTO());
		selectdto.setExWhereQuery("where es_num = '"+es_num+"'");
		HashMap<String,String>season = exdao.selectQueryRecord(selectdto);
		request.setAttribute("season", season);

	}

	/**
	 * @title : 신청차수 수정처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		EdusatSeasonDTO seasondto = (EdusatSeasonDTO) map.get("edusatseasondto");
		
		String es_num = exdao.filter(request.getParameter("es_num"));
		
		seasondto.setEs_wid(Func.getSession(request, "ss_m_id"));
		
		//update 제외
		seasondto.setEs_num(null);
		
		//update 처리
		seasondto.setExTableName("edusat_season");
		seasondto.setExWhereQuery("where es_num = '"+es_num+"'");
		exdao.update(seasondto);
		
		//작업기록
		Func.writeManagerLog ("온라인프로그램 기수 수정["+seasondto.getEs_name()+"]", request);

	}

	@Override
	public void listMove(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void listMoveOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 신청차수 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String status = exdao.filter( request.getParameter("status") ); // 값:totdel (다중삭제시 사용)
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		String es_num = exdao.filter( request.getParameter("es_num") ); // 단일 삭제 사용
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_season");
		selectdto.setExColumn("es_name");
		if (status.equals("totdel")) { // 다중삭제
			for (int z = 0; z <= chk.length - 1; z++) {
				selectdto.setExWhereQuery("where es_num = '"+exdao.filter( chk[z] )+"'");
				String es_name = exdao.selectQueryColumn(selectdto);
				exdao.executeQuery("delete from edusat_season where es_num = '"+exdao.filter( chk[z] )+"'");

				//작업기록
				Func.writeManagerLog ("온라인프로그램 기수 삭제["+es_name+"]", request);
			}

		} else {// 단일삭제
			selectdto.setExWhereQuery("where es_num = '"+es_num+"'");
			String es_name = exdao.selectQueryColumn(selectdto);
			exdao.executeQuery("delete from edusat_season where es_num = '"+es_num+"'");

			//작업기록
			Func.writeManagerLog ("온라인프로그램 기수 삭제["+es_name+"]", request);
		}

	}

	/**
	 * @title : 신청차수 상태변경처리
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String status = exdao.filter( request.getParameter("status") ); // 값:totdel (다중삭제시 사용)
		String tot_level_chk = exdao.filter( request.getParameter("tot_level_chk") ); // 상태값
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_season");
		selectdto.setExColumn("es_name");
		if (status.equals("totlevel")) { // 다중삭제
			for (int z = 0; z <= chk.length - 1; z++) {
				selectdto.setExWhereQuery("where es_num = '"+exdao.filter( chk[z] )+"'");
				String es_name = exdao.selectQueryColumn(selectdto);
				exdao.executeQuery("update edusat_season set es_chk = '"+tot_level_chk+"' where es_num = '"+exdao.filter( chk[z] )+"'");

				//작업기록
				String es_chk_str = "중지";
				if("Y".equals(tot_level_chk)){es_chk_str = "사용";}
				
				Func.writeManagerLog ("온라인프로그램 상태변경["+es_name+"] - "+es_chk_str, request);
			}

		}
	}

	/**
	 * @title : 기수리스트
	 * @method : ajaxlistAll
	 */
	@Override
	public void ajaxlistAll(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String sh_es_libcode = exdao.filter(request.getParameter("sh_es_libcode"));
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_season");
		selectdto.setExColumn(new EdusatSeasonDTO());
		String whereQuery = "where es_chk = 'Y' ";
		if(!"".equals(sh_es_libcode)){
			whereQuery += " and es_libcode = '"+sh_es_libcode+"'";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by es_num desc");
		List<HashMap<String,String>>seasonList = exdao.selectQueryTable(selectdto);
		request.setAttribute("seasonList", seasonList);
		
	}


}
