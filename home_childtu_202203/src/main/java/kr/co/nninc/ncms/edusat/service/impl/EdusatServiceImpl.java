package kr.co.nninc.ncms.edusat.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.View;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.common.ExcelView;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.FuncThumb;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.crypto.service.CryptoARIAService;
import kr.co.nninc.ncms.edusat.service.EdusatDTO;
import kr.co.nninc.ncms.edusat.service.EdusatRequestDTO;
import kr.co.nninc.ncms.edusat.service.EdusatService;

/**
 * 온라인수강신청을 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.09.26
 * @version 1.1
 */
@Service("edusatService")
public class EdusatServiceImpl extends EgovAbstractServiceImpl implements EdusatService {
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** FileUtil */
	@Resource(name="fileutil")
	private FileUtil fileutil;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	// Aria
	@Resource(name="cryptoService")
	private CryptoARIAService cryptoService;

	@Override
	public void config(Model model, int ec_idx) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 강좌목록
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String ss_g_num = Func.getSession(request, "ss_g_num");
		String sh_ct_idx = exdao.filter(request.getParameter("sh_ct_idx"));
		String sh_ct_idx2 = exdao.filter(request.getParameter("sh_ct_idx2"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		
		SelectDTO selectdto = new SelectDTO();
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		//도서관 코드 가져오기
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("ct_idx, ct_name, ct_codeno");
		String where_query = "where ct_ref=1 and ct_chk ='Y'";
		if(!"".equals(builder_dir)){
			where_query += " and ct_site_dir = '"+builder_dir+"'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>libList = exdao.selectQueryTable(selectdto);
		request.setAttribute("libList", libList);
		
		//강좌분류 가져오기
		where_query = "where ct_ref=2 and ct_chk ='Y'";
		if(!"".equals(builder_dir)){
			where_query += " and ct_site_dir = '"+builder_dir+"'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>cateList = exdao.selectQueryTable(selectdto);
		request.setAttribute("cateList", cateList);
		
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(exdao.getFieldNames(new EdusatDTO()));
		
		String whereQuery = "";
		//자기도서관해당하는것만 보기
		if(!"".equals(builder_dir)){
			if(libList.size() > 0) {
				sh_ct_idx = libList.get(0).get("ct_idx");
			}
		}
		if(!"".equals(v_search) && !"".equals(v_keyword)){ whereQuery += " and "+v_search+" like '%"+v_keyword+"%'"; }
		if(!"".equals(sh_ct_idx)){ whereQuery += " and ct_idx ='"+sh_ct_idx+"'"; }
		if(!"".equals(sh_ct_idx2)){ whereQuery += " and ct_idx2 ='"+sh_ct_idx2+"'"; }
		
		
		selectdto.setExWhereQuery(whereQuery);
		
		selectdto.setExOrderByQuery("order by edu_idx desc");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		
		List<HashMap<String,String>> edusatList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( edusatList.get(0).get("totalcount") ); // 전체레코드 수
		
		request.setAttribute("recordcount", recordcount);
		edusatList.remove(0);	//총검색개수행(첫번째행)을 삭제

		for(int i=0;i<=edusatList.size()-1;i++){
			String edu_idx = edusatList.get(i).get("edu_idx");
			String edu_ptcp_yn = edusatList.get(i).get("edu_ptcp_yn");
			String req_count = Func.cStr( this.getReqCount(edu_idx, edu_ptcp_yn) );
			edusatList.get(i).put("req_count", req_count);
		}
		// setAttribute
		request.setAttribute("edusatList", edusatList);
		
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = pagePerBlock;	//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring2 = paging.setQueryString(request, "v_search, v_keyword, sh_ct_idx, sh_ct_idx2");
		
		String pagingtag = paging.execute(querystring2);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pagesize", pagesize);
		//페이징문자열 생성 끝

	}

	/**
	 * @title : 강좌정보등록폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String ss_g_num = Func.getSession(request, "ss_g_num");
		String copy_edu_idx = exdao.filter( request.getParameter("copy_edu_idx") );
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		//도서관 코드 가져오기
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("ct_idx, ct_name, ct_codeno, ct_g_num, ct_site_dir");
		String where_query = "where ct_ref=1 and ct_chk ='Y'";
		if(!"".equals(builder_dir)){	//해당 도서관만
			where_query += " and ct_site_dir = '"+builder_dir+"' ";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>libList = exdao.selectQueryTable(selectdto);
		request.setAttribute("libList", libList);
		//강좌분류 가져오기
		where_query = "where ct_ref=2";
		if(!"".equals(builder_dir)){	//해당 도서관만
			where_query += " and ct_site_dir = '"+builder_dir+"' ";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>cateList = exdao.selectQueryTable(selectdto);
		request.setAttribute("cateList", cateList);
		
		//기존강좌 목록
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExColumn("edu_idx, edu_gubun, edu_subject");
		if(!"".equals(builder_dir)){	//해당 도서관만
			where_query = "where ct_idx in (select ct_idx from code_config where ct_ref=1 and ct_chk ='Y' and ct_site_dir = '"+builder_dir+"')";
			selectdto.setExWhereQuery(where_query);
		}
		selectdto.setExOrderByQuery("order by edu_idx asc");
		List<HashMap<String,String>>srcEdusatList = exdao.selectQueryTable(selectdto);
		request.setAttribute("srcEdusatList", srcEdusatList);
		
		//복사할 기존강좌 정보 
		if(!"".equals(copy_edu_idx)){
			selectdto = new SelectDTO();
			selectdto.setExTableName("edusat");
			selectdto.setExKeyColumn("edu_idx");
			selectdto.setExColumn(new EdusatDTO(), "edu_idx, edu_temp3");
			selectdto.setExWhereQuery("where edu_idx = '"+copy_edu_idx+"'");
			HashMap<String,String>edusatdto = exdao.selectQueryRecord(selectdto);
			edusatdto.put("edu_subject", "[복사]"+Func.nvl(edusatdto.get("edu_subject")));
			request.setAttribute("edusat", edusatdto);
			
			// 신청기간
			String edu_resdate = Func.nvl(edusatdto.get("edu_resdate"));
			String edu_reedate = Func.nvl(edusatdto.get("edu_reedate"));

			// 신청일자 분리
			String edu_resdate_y = "";
			String edu_resdate_m = "";
			String edu_resdate_d = "";
			String edu_reedate_y = "";
			String edu_reedate_m = "";
			String edu_reedate_d = "";
			if (!"".equals(edu_resdate)) {
				String[] edu_resdate_arr = edu_resdate.split("-");
				edu_resdate_y = edu_resdate_arr[0];
				edu_resdate_m = edu_resdate_arr[1];
				edu_resdate_d = edu_resdate_arr[2];
			}
			if (!"".equals(edu_reedate)) {
				String[] edu_reedate_arr = edu_reedate.split("-");
				edu_reedate_y = edu_reedate_arr[0];
				edu_reedate_m = edu_reedate_arr[1];
				edu_reedate_d = edu_reedate_arr[2];
			}
			request.setAttribute("edu_resdate_y", edu_resdate_y);
			request.setAttribute("edu_resdate_m", edu_resdate_m);
			request.setAttribute("edu_resdate_d", edu_resdate_d);
			request.setAttribute("edu_reedate_y", edu_reedate_y);
			request.setAttribute("edu_reedate_m", edu_reedate_m);
			request.setAttribute("edu_reedate_d", edu_reedate_d);
			
			// 강좌기간
			String edu_sdate = Func.nvl(edusatdto.get("edu_sdate"));
			String edu_edate = Func.nvl(edusatdto.get("edu_edate"));
			// 강좌기간 일자분리
			String edu_sdate_y = "";
			String edu_sdate_m = "";
			String edu_sdate_d = "";
			String edu_edate_y = "";
			String edu_edate_m = "";
			String edu_edate_d = "";
			if (!"".equals(edu_sdate)) {
				String[] edu_sdate_arr = edu_sdate.split("-");
				edu_sdate_y = edu_sdate_arr[0];
				edu_sdate_m = edu_sdate_arr[1];
				edu_sdate_d = edu_sdate_arr[2];
			}
			if (!"".equals(edu_edate)) {
				String[] edu_edate_arr = edu_edate.split("-");
				edu_edate_y = edu_edate_arr[0];
				edu_edate_m = edu_edate_arr[1];
				edu_edate_d = edu_edate_arr[2];
			}
			request.setAttribute("edu_sdate_y", edu_sdate_y);
			request.setAttribute("edu_sdate_m", edu_sdate_m);
			request.setAttribute("edu_sdate_d", edu_sdate_d);
			request.setAttribute("edu_edate_y", edu_edate_y);
			request.setAttribute("edu_edate_m", edu_edate_m);
			request.setAttribute("edu_edate_d", edu_edate_d);
			
			//신청대상제한
			String edu_temp5 = Func.nvl( edusatdto.get("edu_temp5") );
			if(!"".equals(edu_temp5) && !"|".equals(edu_temp5)){
				String[] edu_temp5_arr = edu_temp5.split("[|]");
				if(edu_temp5_arr.length >=2){
					if(edu_temp5_arr[0].length() >= 8){
						String edu_temp5_s_y = edu_temp5_arr[0].substring(0, 4);
						String edu_temp5_s_m = edu_temp5_arr[0].substring(4, 6);
						String edu_temp5_s_d = edu_temp5_arr[0].substring(6, 8);
						request.setAttribute("edu_temp5_s_y", edu_temp5_s_y);
						request.setAttribute("edu_temp5_s_m", edu_temp5_s_m);
						request.setAttribute("edu_temp5_s_d", edu_temp5_s_d);
					}
					if(edu_temp5_arr[1].length() >= 8){
						String edu_temp5_e_y = edu_temp5_arr[1].substring(0, 4);
						String edu_temp5_e_m = edu_temp5_arr[1].substring(4, 6);
						String edu_temp5_e_d = edu_temp5_arr[1].substring(6, 8);
						request.setAttribute("edu_temp5_e_y", edu_temp5_e_y);
						request.setAttribute("edu_temp5_e_m", edu_temp5_e_m);
						request.setAttribute("edu_temp5_e_d", edu_temp5_e_d);
					}
				}
			}
			
		}else{
			//빈 맵생성(jsp에서 사용)
			HashMap<String,String>edusat = new HashMap<String,String>();
			request.setAttribute("edusat", edusat);
		}
	}

	/**
	 * @title : 강좌정보등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		EdusatDTO edusatdto = (EdusatDTO) map.get("edusatdto");

		// ------------------- 저장공간인 [DATA]폴더가 없다면 생성 한다. -------------------
		String uploaddir = fileutil.realPath(request, "/data/edusat/");
		Func.folderCreate(uploaddir);
		// -----------------------------------------------------------------------------
		// 파일 유효성 검사 및 저장

		// 파일저장 및 파일명추출
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "강과관련이미지", "edu_temp3_file",
				10 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		String edu_temp3 = Func.nvl(file.getFile_name());
		edusatdto.setEdu_temp3(edu_temp3);
		
		//로그인필수여부
		if(edusatdto.getEdu_login()==null){edusatdto.setEdu_login("N");}
		
		String edu_resdate_y = Func.nvl(request.getParameter("edu_resdate_y"));
		String edu_resdate_m = Func.nvl(request.getParameter("edu_resdate_m"));
		String edu_resdate_d = Func.nvl(request.getParameter("edu_resdate_d"));
		String edu_reedate_y = Func.nvl(request.getParameter("edu_reedate_y"));
		String edu_reedate_m = Func.nvl(request.getParameter("edu_reedate_m"));
		String edu_reedate_d = Func.nvl(request.getParameter("edu_reedate_d"));

		String edu_sdate_y = Func.nvl(request.getParameter("edu_sdate_y"));
		String edu_sdate_m = Func.nvl(request.getParameter("edu_sdate_m"));
		String edu_sdate_d = Func.nvl(request.getParameter("edu_sdate_d"));
		String edu_edate_y = Func.nvl(request.getParameter("edu_edate_y"));
		String edu_edate_m = Func.nvl(request.getParameter("edu_edate_m"));
		String edu_edate_d = Func.nvl(request.getParameter("edu_edate_d"));

		edusatdto.setEdu_reedate(edu_reedate_y + '-' + edu_reedate_m + '-' + edu_reedate_d);
		edusatdto.setEdu_resdate(edu_resdate_y + '-' + edu_resdate_m + '-' + edu_resdate_d);

		edusatdto.setEdu_sdate(edu_sdate_y + '-' + edu_sdate_m + '-' + edu_sdate_d);
		edusatdto.setEdu_edate(edu_edate_y + '-' + edu_edate_m + '-' + edu_edate_d);

		edusatdto.setEdu_wdate(Func.date("Y-m-d H:i:s"));
		edusatdto.setEdu_ip(Func.remoteIp(request));
		
		//신청대상
		String edu_temp4 = Func.nvl(request.getParameter("edu_temp4"));
		if("".equals(edu_temp4)){edusatdto.setEdu_temp4("N");}
		String edu_temp5_s_y = Func.nvl(request.getParameter("edu_temp5_s_y"));
		String edu_temp5_s_m = Func.nvl(request.getParameter("edu_temp5_s_m"));
		String edu_temp5_s_d = Func.nvl(request.getParameter("edu_temp5_s_d"));
		String edu_temp5_e_y = Func.nvl(request.getParameter("edu_temp5_e_y"));
		String edu_temp5_e_m = Func.nvl(request.getParameter("edu_temp5_e_m"));
		String edu_temp5_e_d = Func.nvl(request.getParameter("edu_temp5_e_d"));
		String edu_temp5 = edu_temp5_s_y+edu_temp5_s_m+edu_temp5_s_d+"|"+edu_temp5_e_y+edu_temp5_e_m+edu_temp5_e_d;
		edusatdto.setEdu_temp5(edu_temp5);

		// 순서코드 업데이트
		exdao.executeQuery("update edusat set edu_code = edu_code + 1");
		edusatdto.setEdu_code("1");

		if ("".equals(Func.nvl(edusatdto.getEdu_lot_type()))) {
			edusatdto.setEdu_lot_type("0");
		}
		if ("".equals(Func.nvl(edusatdto.getEdu_awaiter()))) {
			edusatdto.setEdu_awaiter("0");
		}
		if ("".equals(Func.nvl(edusatdto.getEdu_resdate_h()))) {
			edusatdto.setEdu_resdate_h("0");
		}
		if ("".equals(Func.nvl(edusatdto.getEdu_reedate_h()))) {
			edusatdto.setEdu_reedate_h("0");
		}
		if ("".equals(Func.nvl(edusatdto.getEdu_inwon()))) {
			edusatdto.setEdu_inwon("0");
		}
		//도서관 정보 등록하기
		if (!"".equals(Func.nvl(edusatdto.getCt_idx()))) {
			String[] ct_idx_arr = edusatdto.getCt_idx().split(";");
			edusatdto.setCt_idx(ct_idx_arr[0]);
			edusatdto.setEdu_lib(ct_idx_arr[1]);
		}
		//강좌분류 등록하기
		if (!"".equals(Func.nvl(edusatdto.getCt_idx2()))) {
			String[] ct_idx2_arr = edusatdto.getCt_idx2().split(";");
			edusatdto.setCt_idx2(ct_idx2_arr[0]);
			edusatdto.setEdu_gubun(ct_idx2_arr[1]);
		}
		
		if (!"Y".equals(edusatdto.getEdu_ptcp_yn())) {
			edusatdto.setEdu_ptcp_cnt("1");			
		}		
		
		if(edusatdto.getEdu_season() == null || "".equals(edusatdto.getEdu_season())){
			edusatdto.setEdu_season("0");
		}
		
		//insert 제외
		edusatdto.setEdu_idx(null);
		edusatdto.setEdu_receive_type("2");
		
		//insert
		edusatdto.setExTableName("edusat");
		exdao.insert(edusatdto);

		
		//작업기록
		Func.writeManagerLog ("문화프로그램 강좌 등록["+edusatdto.getEdu_subject()+"]", request);
	}

	/**
	 * @title : 강좌정보수정/신청자관리
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String ss_g_num = Func.getSession(request, "ss_g_num");
		String edu_idx = exdao.filter( request.getParameter("edu_idx") );
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		//도서관 코드 가져오기
		/*
		
		String where_query = "where ct_ref=1 and ct_chk ='Y'";
		if(!"".equals(builder_dir)){	//해당 도서관만
			where_query += " and ct_site_dir = '"+builder_dir+"' ";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>libList = exdao.selectQueryTable(selectdto);
		request.setAttribute("libList", libList);
		*/
		//강좌분류 가져오기
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("ct_idx, ct_name, ct_codeno, ct_g_num, ct_site_dir");
		selectdto.setExWhereQuery("where ct_ref=2");
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>cateList = exdao.selectQueryTable(selectdto);
		request.setAttribute("cateList", cateList);
		
		
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");
		selectdto.setExColumn(new EdusatDTO());
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		HashMap<String,String>edusatdto = exdao.selectQueryRecord(selectdto);
		
		// 신청기간
		String edu_resdate = Func.nvl(edusatdto.get("edu_resdate"));
		String edu_reedate = Func.nvl(edusatdto.get("edu_reedate"));

		// 신청일자 분리
		String edu_resdate_y = "";
		String edu_resdate_m = "";
		String edu_resdate_d = "";
		String edu_reedate_y = "";
		String edu_reedate_m = "";
		String edu_reedate_d = "";
		if (!"".equals(edu_resdate)) {
			String[] edu_resdate_arr = edu_resdate.split("-");
			edu_resdate_y = edu_resdate_arr[0];
			edu_resdate_m = edu_resdate_arr[1];
			edu_resdate_d = edu_resdate_arr[2];
		}
		if (!"".equals(edu_reedate)) {
			String[] edu_reedate_arr = edu_reedate.split("-");
			edu_reedate_y = edu_reedate_arr[0];
			edu_reedate_m = edu_reedate_arr[1];
			edu_reedate_d = edu_reedate_arr[2];
		}
		request.setAttribute("edu_resdate_y", edu_resdate_y);
		request.setAttribute("edu_resdate_m", edu_resdate_m);
		request.setAttribute("edu_resdate_d", edu_resdate_d);
		request.setAttribute("edu_reedate_y", edu_reedate_y);
		request.setAttribute("edu_reedate_m", edu_reedate_m);
		request.setAttribute("edu_reedate_d", edu_reedate_d);
		
		// 강좌기간
		//String edu_sdate = Func.nvl(edusatdto.get("edu_sdate"));
		//String edu_edate = Func.nvl(edusatdto.get("edu_edate"));
		// 강좌기간 일자분리
		/*
		String edu_sdate_y = "";
		String edu_sdate_m = "";
		String edu_sdate_d = "";
		String edu_edate_y = "";
		String edu_edate_m = "";
		String edu_edate_d = "";
		if (!"".equals(edu_sdate)) {
			String[] edu_sdate_arr = edu_sdate.split("-");
			edu_sdate_y = edu_sdate_arr[0];
			edu_sdate_m = edu_sdate_arr[1];
			edu_sdate_d = edu_sdate_arr[2];
		}
		if (!"".equals(edu_edate)) {
			String[] edu_edate_arr = edu_edate.split("-");
			edu_edate_y = edu_edate_arr[0];
			edu_edate_m = edu_edate_arr[1];
			edu_edate_d = edu_edate_arr[2];
		}
		request.setAttribute("edu_sdate_y", edu_sdate_y);
		request.setAttribute("edu_sdate_m", edu_sdate_m);
		request.setAttribute("edu_sdate_d", edu_sdate_d);
		request.setAttribute("edu_edate_y", edu_edate_y);
		request.setAttribute("edu_edate_m", edu_edate_m);
		request.setAttribute("edu_edate_d", edu_edate_d);
		*/
		//신청대상제한
		/*
		String edu_temp5 = Func.nvl( edusatdto.get("edu_temp5") );
		if(!"".equals(edu_temp5) && !"|".equals(edu_temp5)){
			String[] edu_temp5_arr = edu_temp5.split("[|]");
			if(edu_temp5_arr.length >=2){
				if(edu_temp5_arr[0].length() >= 8){
					String edu_temp5_s_y = edu_temp5_arr[0].substring(0, 4);
					String edu_temp5_s_m = edu_temp5_arr[0].substring(4, 6);
					String edu_temp5_s_d = edu_temp5_arr[0].substring(6, 8);
					request.setAttribute("edu_temp5_s_y", edu_temp5_s_y);
					request.setAttribute("edu_temp5_s_m", edu_temp5_s_m);
					request.setAttribute("edu_temp5_s_d", edu_temp5_s_d);
				}
				if(edu_temp5_arr[1].length() >= 8){
					String edu_temp5_e_y = edu_temp5_arr[1].substring(0, 4);
					String edu_temp5_e_m = edu_temp5_arr[1].substring(4, 6);
					String edu_temp5_e_d = edu_temp5_arr[1].substring(6, 8);
					request.setAttribute("edu_temp5_e_y", edu_temp5_e_y);
					request.setAttribute("edu_temp5_e_m", edu_temp5_e_m);
					request.setAttribute("edu_temp5_e_d", edu_temp5_e_d);
				}
			}
		}
		*/
		System.out.println("abc");
		
		// 사이즈&파일이미지
		String realpath =  fileutil.realPath(request, "/data/");
		String strDir = realpath+"/edusat/";
		if(!"".equals(Func.nvl(edusatdto.get("edu_temp3")))) {
			File file = new File( strDir+edusatdto.get("edu_temp3") );
			if(file.exists()){
				//파일용량담기
				request.setAttribute("edu_temp3_size", Func.byteConvert( Func.cStr( file.length())));
				
				//파일이미지아이콘담기
				request.setAttribute("edu_temp3_icon", Func.get_FileName(edusatdto.get("edu_temp3")));
			}
		}
		
		//신청인원 가져오기
		edusatdto.put("req_count", Func.cStr(this.getReqCount(edu_idx, edusatdto.get("edu_ptcp_yn"))) );
		
		//강좌정보 전송
		request.setAttribute("edusat", edusatdto);
	}

	
	/**
	 * @title : 강좌수정
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		EdusatDTO edusatdto = (EdusatDTO) map.get("edusatdto");

		String edu_idx = exdao.filter(request.getParameter("edu_idx"));
		
		// ------------------- 저장공간인 [DATA]폴더가 없다면 생성 한다. -------------------
		String uploaddir = fileutil.realPath(request, "/data/edusat/");
		Func.folderCreate(uploaddir);
		// -----------------------------------------------------------------------------
		// 파일 유효성 검사 및 저장

		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "강좌관련이미지", "edu_temp3_file",
				10 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);

		String edu_temp3_2 = Func.nvl(request.getParameter("edu_temp3_2"));
		String edu_temp3 = Func.nvl(file.getFile_name());
		if ("".equals(edu_temp3)) {
			String edu_temp3_chk = Func.nvl(request.getParameter("edu_temp3_chk"));

			if ("".equals(edu_temp3_chk)) {
				edusatdto.setEdu_temp3(edu_temp3_2); // 기존파일명 적용
			} else {
				Func.fileDelete(uploaddir + "/" + edu_temp3_2); // 파일삭제
				edusatdto.setEdu_temp3(""); // 파일명 삭제
			}
		} else {
			edusatdto.setEdu_temp3(edu_temp3);
			if (!"".equals(edu_temp3_2)) {
				Func.fileDelete(uploaddir + "/" + edu_temp3_2); // 파일삭제
			}
		}

		//로그인필수여부
		if(edusatdto.getEdu_login()==null){edusatdto.setEdu_login("N");}
		
		String edu_resdate_y = Func.nvl(request.getParameter("edu_resdate_y"));
		String edu_resdate_m = Func.nvl(request.getParameter("edu_resdate_m"));
		String edu_resdate_d = Func.nvl(request.getParameter("edu_resdate_d"));
		String edu_reedate_y = Func.nvl(request.getParameter("edu_reedate_y"));
		String edu_reedate_m = Func.nvl(request.getParameter("edu_reedate_m"));
		String edu_reedate_d = Func.nvl(request.getParameter("edu_reedate_d"));

		String edu_sdate_y = Func.nvl(request.getParameter("edu_sdate_y"));
		String edu_sdate_m = Func.nvl(request.getParameter("edu_sdate_m"));
		String edu_sdate_d = Func.nvl(request.getParameter("edu_sdate_d"));
		String edu_edate_y = Func.nvl(request.getParameter("edu_edate_y"));
		String edu_edate_m = Func.nvl(request.getParameter("edu_edate_m"));
		String edu_edate_d = Func.nvl(request.getParameter("edu_edate_d"));

		edusatdto.setEdu_reedate(edu_reedate_y + '-' + edu_reedate_m + '-' + edu_reedate_d);
		edusatdto.setEdu_resdate(edu_resdate_y + '-' + edu_resdate_m + '-' + edu_resdate_d);

		edusatdto.setEdu_sdate(edu_sdate_y + '-' + edu_sdate_m + '-' + edu_sdate_d);
		edusatdto.setEdu_edate(edu_edate_y + '-' + edu_edate_m + '-' + edu_edate_d);

		edusatdto.setEdu_mdate(Func.date("Y-m-d H:i:s"));
		edusatdto.setEdu_ip(Func.remoteIp(request));

		if ("".equals(Func.nvl(edusatdto.getEdu_lot_type()))) {
			edusatdto.setEdu_lot_type("0");
		}
		if ("".equals(Func.nvl(edusatdto.getEdu_awaiter()))) {
			edusatdto.setEdu_awaiter("0");
		}
		if ("".equals(Func.nvl(edusatdto.getEdu_resdate_h()))) {
			edusatdto.setEdu_resdate_h("0");
		}
		if ("".equals(Func.nvl(edusatdto.getEdu_reedate_h()))) {
			edusatdto.setEdu_reedate_h("0");
		}
		if ("".equals(Func.nvl(edusatdto.getEdu_inwon()))) {
			edusatdto.setEdu_inwon("0");
		}
		
		
		//신청대상
		String edu_temp4 = Func.nvl(request.getParameter("edu_temp4"));
		if("".equals(edu_temp4)){edusatdto.setEdu_temp4("N");}
		String edu_temp5_s_y = Func.nvl(request.getParameter("edu_temp5_s_y"));
		String edu_temp5_s_m = Func.nvl(request.getParameter("edu_temp5_s_m"));
		String edu_temp5_s_d = Func.nvl(request.getParameter("edu_temp5_s_d"));
		String edu_temp5_e_y = Func.nvl(request.getParameter("edu_temp5_e_y"));
		String edu_temp5_e_m = Func.nvl(request.getParameter("edu_temp5_e_m"));
		String edu_temp5_e_d = Func.nvl(request.getParameter("edu_temp5_e_d"));
		String edu_temp5 = edu_temp5_s_y+edu_temp5_s_m+edu_temp5_s_d+"|"+edu_temp5_e_y+edu_temp5_e_m+edu_temp5_e_d;
		edusatdto.setEdu_temp5(edu_temp5);
		
		
		//도서관 정보 등록하기
		/*
		if (!"".equals(Func.nvl(edusatdto.getCt_idx()))) {
			String[] ct_idx_arr = edusatdto.getCt_idx().split(";");
			edusatdto.setCt_idx(ct_idx_arr[0]);
			edusatdto.setEdu_lib(ct_idx_arr[1]);
		}
		*/
		//강좌분류 등록하기
		if (!"".equals(Func.nvl(edusatdto.getCt_idx2()))) {
			String[] ct_idx2_arr = edusatdto.getCt_idx2().split(";");
			edusatdto.setCt_idx2(ct_idx2_arr[0]);
			edusatdto.setEdu_gubun(ct_idx2_arr[1]);
		}
		
		if (!"Y".equals(edusatdto.getEdu_ptcp_yn())) {
			edusatdto.setEdu_ptcp_cnt("1");			
		}
		
		if(edusatdto.getEdu_season() == null || "".equals(edusatdto.getEdu_season())){
			edusatdto.setEdu_season("0");
		}
		
		//update 제외
		edusatdto.setEdu_idx(null);
		edusatdto.setEdu_receive_type("2");
		
		//update
		edusatdto.setExTableName("edusat");
		edusatdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		exdao.update(edusatdto);
		
		//작업기록
		Func.writeManagerLog ("프로그램 강좌 수정["+edusatdto.getEdu_subject()+"]", request);
	}

	@Override
	public void move(Model model) throws Exception {
		// TODO Auto-generated method stub

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
	 * @title : 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String status = exdao.filter( request.getParameter("status") ); // 값:totdel (다중삭제시 사용)
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		String edu_idx = exdao.filter( request.getParameter("edu_idx") ); // 단일 삭제 사용
		
		String realpath =  fileutil.realPath(request, "/data/");
		String strDir = realpath+"/edusat/";
		String strDir2 = realpath+"/edusat/thum/";
		
		if (status.equals("totdel")) { // 다중삭제
			for (int z = 0; z <= chk.length - 1; z++) {
				SelectDTO selectdto = new SelectDTO();
				selectdto.setExTableName("edusat");
				selectdto.setExColumn("edu_subject, edu_temp3");
				selectdto.setExWhereQuery("where edu_idx = '"+exdao.filter( chk[z] )+"'");
				HashMap<String, String>rs = exdao.selectQueryRecord(selectdto);
				String edu_subject = rs.get("edu_subject");
				String edu_temp3 =  rs.get("edu_temp3");
				
				// 프로그램 이미지 삭제
				if(!"".equals(Func.nvl(edu_temp3))) { Func.fileDelete(strDir+edu_temp3); Func.fileDelete(strDir2+edu_temp3); } //삭제
				
				// 신청자 삭제 및 이미지 삭제
				selectdto = new SelectDTO();
				selectdto.setExTableName("edusat_request");
				selectdto.setExColumn("es_idx, es_name, es_file1, es_file2, es_file3, es_file4, es_file5");
				selectdto.setExWhereQuery("where edu_idx = '"+exdao.filter( chk[z] )+"'");
				List<HashMap<String, String>> table = exdao.selectQueryTable(selectdto);
				for(int zz = 0; zz < table.size(); zz++) {
					//이미지 삭제
					HashMap<String, String>requestData = (HashMap<String, String>)table.get(zz);
					for(int f=1;f<=5;f++){	//파일갯수만큼 반복처리
						String es_file = requestData.get("es_file"+f);
						if(!"".equals(Func.nvl(es_file))) { Func.fileDelete(strDir+es_file); Func.fileDelete(strDir2+es_file); } //삭제
					}
					exdao.executeQuery("delete from edusat_request where es_idx = '"+requestData.get("es_idx")+"'");
					//작업기록
					Func.writeManagerLog ("프로그램 신청자 삭제["+requestData.get("es_name")+"]", request);
				}
				
				
				exdao.executeQuery("delete from edusat where edu_idx = '"+exdao.filter( chk[z] )+"'");

				//작업기록
				Func.writeManagerLog ("프로그램 삭제["+edu_subject+"]", request);
			}

		} else {// 단일삭제
			SelectDTO selectdto = new SelectDTO();
			selectdto.setExTableName("edusat");
			selectdto.setExColumn("edu_subject, edu_temp3");
			selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
			HashMap<String, String>rs = exdao.selectQueryRecord(selectdto);
			String edu_subject = rs.get("edu_subject");
			String edu_temp3 =  rs.get("edu_temp3");
			
			// 프로그램 이미지 삭제
			if(!"".equals(Func.nvl(edu_temp3))) { Func.fileDelete(strDir+edu_temp3); Func.fileDelete(strDir2+edu_temp3); } //삭제
			
			// 신청자 삭제 및 이미지 삭제
			selectdto = new SelectDTO();
			selectdto.setExTableName("edusat_request");
			selectdto.setExColumn("es_idx, es_name, es_file1, es_file2, es_file3, es_file4, es_file5");
			selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
			List<HashMap<String, String>> table = exdao.selectQueryTable(selectdto);
			for(int zz = 0; zz < table.size(); zz++) {
				//이미지 삭제
				HashMap<String, String>requestData = (HashMap<String, String>)table.get(zz);
				for(int f=1;f<=5;f++){	//파일갯수만큼 반복처리
					String es_file = requestData.get("es_file"+f);
					if(!"".equals(Func.nvl(es_file))) { Func.fileDelete(strDir+es_file); Func.fileDelete(strDir2+es_file); } //삭제
				}
				exdao.executeQuery("delete from edusat_request where es_idx = '"+requestData.get("es_idx")+"'");
				//작업기록
				Func.writeManagerLog ("프로그램 신청자 삭제["+requestData.get("es_name")+"]", request);
			}
			
			exdao.executeQuery("delete from edusat where edu_idx = '"+edu_idx+"'");

			//작업기록
			Func.writeManagerLog ("프로그램 삭제["+edu_subject+"]", request);
		}
		
	}

	/**
	 * @title : 상태변경
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String tot_level_chk = exdao.filter(request.getParameter("tot_level_chk"));
		String[] chk = request.getParameterValues("chk");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExColumn("edu_subject");
		for (int z = 0; z <= chk.length - 1; z++) {
			String edu_idx = exdao.filter( chk[z] );
			String end_chk = tot_level_chk;
			// 사용여부변경
			exdao.executeQuery("update edusat set end_chk = '"+end_chk+"' where edu_idx = '"+edu_idx+"'");
			
			//쿼리조회
			selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
			String edu_subject = exdao.selectQueryColumn(selectdto);
			//작업기록
			String end_chk_str = "사용";
			if("N".equals(end_chk)) end_chk_str = "중지";
			Func.writeManagerLog ("독서문화프로그램 상태변경 ["+edu_subject+"("+end_chk_str+")]", request);
		}
	}

	
	/**
	 * @title : 강좌신청자 추첨
	 * @method : winOk
	 */
	@Override
	public String winOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		String edu_idx 						= exdao.filter(request.getParameter("edu_idx"));
		String p_type						= Func.nvl(request.getParameter("p_type"));
		String prepage						= Func.nvl(request.getParameter("prepage"));
		String msg = "";
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExColumn("edu_inwon, edu_subject");
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		HashMap<String,String>edusat = exdao.selectQueryRecord(selectdto);
		
		if("win".equals(p_type)) {
			//신청자정보 상태 변경(낙첨으로 변경)
			exdao.executeQuery("update edusat_request set es_status = 2 where edu_idx = '"+edu_idx+"' and es_status <> 9");
			
			//신청자 랜덤리스트
			selectdto = new SelectDTO();
			selectdto.setExTableName("edusat_request");
			selectdto.setExColumn("es_idx");
			selectdto.setExRecordCount( Func.cInt(edusat.get("edu_inwon")) );
			selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"' and es_status <> 9");
			selectdto.setExOrderByQuery("random");
			List<HashMap<String,String>>randomList = exdao.selectQueryTable(selectdto);
			
			String es_idx_value = "";
			for(int i=0;i<=randomList.size()-1;i++){
				if(i>0){es_idx_value = es_idx_value+",";}
				es_idx_value += randomList.get(i).get("es_idx");
			}
			//추첨하기
			if(!"".equals(es_idx_value)){
				exdao.executeQuery("update edusat_request set es_status = 1 where es_idx in ("+es_idx_value+")");
				msg = edusat.get("edu_inwon")+"명 추첨 완료";
			}else{
				msg = "신청자가 없습니다.";
			}
			//작업기록
			Func.writeManagerLog("독서문화프로그램 강좌 신청자 추첨 ["+edusat.get("edu_subject")+"]", request);
			
		}else{
			//신청자정보 상태 변경
			exdao.executeQuery("update edusat_request set es_status = 0 where edu_idx = '"+edu_idx+"' and es_status <> 9");
			
			//작업기록
			Func.writeManagerLog("독서문화프로그램 강좌 신청자 추첨결과 초기화 ["+edusat.get("edu_subject")+"]", request);
		}
		
		
		return messageService.redirectMsg(model, msg, prepage);
	}

	/**
	 * @title : 신청자정보보기
	 * @method : levelOk
	 */
	@Override
	public void viewRequest(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String es_idx = exdao.filter( request.getParameter("es_idx") );
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request");
		selectdto.setExKeyColumn("es_idx");
		selectdto.setExColumn(new EdusatRequestDTO());
		selectdto.setExWhereQuery("where es_idx = '"+es_idx+"'");
		HashMap<String,String>edusatreq = exdao.selectQueryRecord(selectdto);
		
		// 특수블럭 제거
		String xml10pattern = "[^" + "\u0009\r\n" + "\u0020-\uD7FF" + "\uE000-\uFFFD" + "\ud800\udc00-\udbff\udfff" + "]";
		String xml11pattern = "[^" + "\u0001-\uD7FF" + "\uE000-\uFFFD" + "\ud800\udc00-\udbff\udfff" + "]+";
		
		//ARIA
		String encrypText = "";
		String aria_str = "es_name,es_name2,es_bphone1,es_bphone2,es_bphone3,es_phone1,es_phone2,es_phone3,es_zipcode,es_addr1,es_addr2,es_email,es_temp2,es_temp7,es_temp8,es_temp9";
		String[] aria_arr = aria_str.replaceAll(" ", "").split(",");
		
		for (String string : aria_arr) {
			if(edusatreq.get(string) != null && !"".equals(edusatreq.get(string))) {
				encrypText = cryptoService.decryptData(edusatreq.get(string));
				encrypText = encrypText.replaceAll(xml10pattern, "");
				edusatreq.put(string, encrypText);
			}	
		}
		
		String temp_9 = edusatreq.get("es_temp9");
		if(temp_9 != null && !temp_9.equals("")) {
			String[] temp_9_arr = temp_9.split("-");
			for(int j = 0; j<temp_9_arr.length; j++) {
				edusatreq.put("es_temp9_"+(j+1), temp_9_arr[j]);
			}	
		}
		
		request.setAttribute("edusatreq", edusatreq);
		
		
		//강좌정보
		String edu_idx = exdao.filter(request.getParameter("edu_idx"));
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExColumn("edu_ptcp_yn, edu_ptcp_cnt, edu_field1_yn, edu_field2_yn, edu_field3_yn, edu_field4_yn, edu_field5_yn, edu_gubun, edu_temp1");
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		HashMap<String,String>edusatdto = exdao.selectQueryRecord(selectdto);
		request.setAttribute("edusat", edusatdto);
		String gubun = Func.nvl(edusatdto.get("edu_gubun"));
		if(gubun.contains("사연")) gubun = "0";
		else if (gubun.contains("학교")) gubun = "1";
		else gubun = "2";
		request.setAttribute("gubun_num", gubun);
		// 추가 : 신청자 정보 받을지 여부
		String edu_ptcp_yn = Func.nvl(edusatdto.get("edu_ptcp_yn"));
		String edu_ptcp_cnt = Func.nvl(edusatdto.get("edu_ptcp_cnt"));
		request.setAttribute("edu_ptcp_yn", edu_ptcp_yn);
		request.setAttribute("edu_ptcp_cnt", edu_ptcp_cnt);

	}

	/**
	 * @title : 신청정보변경
	 * @method : modifyRequest
	 */
	@Override
	public void modifyRequest(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		EdusatRequestDTO edusatrequestdto = (EdusatRequestDTO) map.get("edusatrequestdto");

		String es_idx = exdao.filter(request.getParameter("es_idx"));
		String edu_idx = exdao.filter(request.getParameter("edu_idx"));
		
		//update 제외필드
		edusatrequestdto.setEs_idx(null);
		edusatrequestdto.setEdu_idx(null);
		edusatrequestdto.setEdu_m_id(null);
		edusatrequestdto.setEdu_ci(null);
		
		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/edusat/";
		String strDir2 = realpath + "/edusat/thum/";
		Func.folderCreate(strDir);
		Func.folderCreate(strDir2);
		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
				
		// 업로드 허용 확장자
		String ext_str = "gif,jpg,png,jpeg,bmp";
		String a_upload_len = "5";
				
		// 업로드를 미리처리하자.
		for (int z = 1; z <= Func.cInt(a_upload_len); z++) {
			// 가변변수 파일명 가져오기
			String es_file = Func.getValue(edusatrequestdto, "b_file" + z);
			if (!"".equals(es_file)) {
				// 썸네일
				FuncThumb.GD2_make_thumb(640, 480, "/thum/", strDir, es_file);
			}
		}
		
				
		//수정처리
		for(int z=1;z <= 5;z++) {
			String es_file = Func.getValue(edusatrequestdto, "es_file"+z);
			String es_file_chk = Func.nvl(request.getParameter("es_file_chk"+z));
			String es_file_org = Func.nvl(request.getParameter("es_file_org"+z));
			
			if(!"".equals(es_file)) { //첨부파일 있으면
				if(!"".equals(es_file_org)){
					Func.fileDelete(strDir+es_file_org);
					Func.fileDelete(strDir2+es_file_org);
				}
			}else if (!"".equals(es_file_chk)) { //체크하면 파일삭제, 디비삭제
				Func.fileDelete(strDir+es_file_org);
				Func.fileDelete(strDir2+es_file_org);
				
				//값세팅
				Func.setValue(edusatrequestdto, "es_file"+z, "");
			}else if(!"".equals(es_file_org)){	//첨부파일 없으면 기존파일명유지
				//값세팅
				Func.setValue(edusatrequestdto, "es_file"+z, es_file_org);
			}
		}
		
		String smstel = "";
		
		String temp9 = request.getParameter("es_temp9_1")+"-"+request.getParameter("es_temp9_2")+"-"+request.getParameter("es_temp9_3");
		edusatrequestdto.setEs_temp9(temp9);
		// ARIA
		String encrypText = "";
		if(edusatrequestdto.getEs_name() != null && !"".equals(edusatrequestdto.getEs_name())) {
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_name());
			edusatrequestdto.setEs_name(encrypText);
		}
		if(edusatrequestdto.getEs_name2() != null && !"".equals(edusatrequestdto.getEs_name2())) {
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_name2());
			edusatrequestdto.setEs_name2(encrypText);
		}
		if(edusatrequestdto.getEs_bphone1() != null && !"".equals(edusatrequestdto.getEs_bphone1())) {
			smstel = edusatrequestdto.getEs_bphone1()+"-"+edusatrequestdto.getEs_bphone2()+"-"+edusatrequestdto.getEs_bphone3();
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_bphone1());
			edusatrequestdto.setEs_bphone1(encrypText);
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_bphone2());
			edusatrequestdto.setEs_bphone2(encrypText);
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_bphone3());
			edusatrequestdto.setEs_bphone3(encrypText);
		}
		if(edusatrequestdto.getEs_phone1() != null && !"".equals(edusatrequestdto.getEs_phone1())) {
			smstel = edusatrequestdto.getEs_phone1()+"-"+edusatrequestdto.getEs_phone2()+"-"+edusatrequestdto.getEs_phone3();
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_phone1());
			edusatrequestdto.setEs_phone1(encrypText);
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_phone2());
			edusatrequestdto.setEs_phone2(encrypText);
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_phone3());
			edusatrequestdto.setEs_phone3(encrypText);
		}
		if(edusatrequestdto.getEs_zipcode() != null && !"".equals(edusatrequestdto.getEs_zipcode())) {
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_zipcode());
			edusatrequestdto.setEs_zipcode(encrypText);
		}
		if(edusatrequestdto.getEs_addr1() != null && !"".equals(edusatrequestdto.getEs_addr1())) {
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_addr1());
			edusatrequestdto.setEs_addr1(encrypText);
		}
		if(edusatrequestdto.getEs_addr2() != null && !"".equals(edusatrequestdto.getEs_addr2())) {
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_addr2().replace(" ",""));
			edusatrequestdto.setEs_addr2(encrypText);
		}
		if(edusatrequestdto.getEs_email() != null && !"".equals(edusatrequestdto.getEs_email())) {
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_email());
			edusatrequestdto.setEs_email(encrypText);
		}
		if(edusatrequestdto.getEs_temp2() != null && !"".equals(edusatrequestdto.getEs_temp2())) {
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_temp2());
			edusatrequestdto.setEs_temp2(encrypText);
		}
		if(edusatrequestdto.getEs_temp7() != null && !"".equals(edusatrequestdto.getEs_temp7())) {
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_temp7());
			edusatrequestdto.setEs_temp7(encrypText);
		}
		if(edusatrequestdto.getEs_temp8() != null && !"".equals(edusatrequestdto.getEs_temp8())) {
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_temp8());
			edusatrequestdto.setEs_temp8(encrypText);
		}
		if(edusatrequestdto.getEs_temp9() != null && !"".equals(edusatrequestdto.getEs_temp9())) {
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_temp9());
			edusatrequestdto.setEs_temp9(encrypText);
		}
		
		//update
		edusatrequestdto.setExTableName("edusat_request");
		edusatrequestdto.setExWhereQuery("where es_idx = '"+es_idx+"'");
		exdao.update(edusatrequestdto);
		
		//강좌정보
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExColumn("edu_subject, edu_gubun, edu_lib");
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		HashMap<String, String>edusat = exdao.selectQueryRecord(selectdto);
		String edu_subject = edusat.get("edu_subject");
		String edu_gubun = edusat.get("edu_gubun").trim();
		
		boolean sms_chk = false;
		if(edu_gubun.trim().contains("학교") || edu_gubun.trim().contains("교통안전교육")) {
			sms_chk = true;
		}
		
		if(sms_chk && edusatrequestdto.getEs_status().trim().equals("3")) {
			String smsmsg = edusat.get("edu_lib").trim().equals("") ? "신청하신 프로그램에 선정되셨습니다" : edusat.get("edu_lib");
			Func.sendSms(smsmsg, smstel);
		}
		
		Func.writeManagerLog ("프로그램 수정 ["+edu_subject+" - "+edusatrequestdto.getEs_name()+")]", request);
		
		
	}
	
	/**
	 * @title : 신청정보삭제
	 * @method : deleteRequest
	 */
	@Override
	public void deleteRequest(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status = exdao.filter( request.getParameter("status") ); // 값:totdel (다중삭제시 사용)
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		String edu_idx = exdao.filter( request.getParameter("edu_idx") );
		String es_idx = exdao.filter( request.getParameter("es_idx") );
		if (status.equals("totdel")) { // 다중삭제
			for (int z = 0; z <= chk.length - 1; z++) {
				SelectDTO selectdto = new SelectDTO();
				selectdto.setExTableName("edusat");
				selectdto.setExColumn("edu_subject");
				selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
				String edu_subject = exdao.selectQueryColumn(selectdto);
				
				selectdto = new SelectDTO();
				selectdto.setExTableName("edusat_request");
				selectdto.setExColumn("es_name");
				selectdto.setExWhereQuery("where es_idx = '"+exdao.filter( chk[z] )+"'");
				String es_name = exdao.selectQueryColumn(selectdto);
				
				//삭제
				exdao.executeQuery("delete from edusat_request where es_idx = '"+exdao.filter( chk[z] )+"'");

				//작업기록
				Func.writeManagerLog ("프로그램 삭제["+edu_subject+"]", request);
			}

		} else {// 단일삭제
			SelectDTO selectdto = new SelectDTO();
			selectdto.setExTableName("edusat");
			selectdto.setExColumn("edu_subject");
			selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
			String edu_subject = exdao.selectQueryColumn(selectdto);
			
			selectdto = new SelectDTO();
			selectdto.setExTableName("edusat_request");
			selectdto.setExColumn("es_name");
			selectdto.setExWhereQuery("where es_idx = '"+es_idx+"'");
			String es_name = exdao.selectQueryColumn(selectdto);
			
			//삭제
			exdao.executeQuery("delete from edusat_request where es_idx = '"+es_idx+"'");

			//작업기록
			Func.writeManagerLog ("프로그램 강좌신청자 삭제 ["+edu_subject+" - "+es_name+")]", request);
		}
	}

	@Override
	public void edusatConf(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void edusatConfOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void edusatLibConf(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void edusatLibConfOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void mainList(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 신청자 엑셀리스트
	 * @method : excel
	 */
	@Override
	public View excel(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String edu_idx = exdao.filter( request.getParameter("edu_idx") );
		
		String v_search2 = exdao.filter( request.getParameter("v_search2") );
		String v_keyword2 = exdao.filter( request.getParameter("v_keyword2") );
		if(v_search2.trim().equals("es_name")) {
			v_keyword2 = cryptoService.encryptData(v_keyword2);
		}
		String v_status = exdao.filter(request.getParameter("v_status"));
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExColumn("edu_lot_type, edu_inwon, edu_gubun");
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		HashMap<String,String>edusatdto = exdao.selectQueryRecord(selectdto);
		
		int accnum = 0;
		
		// 강좌신청자목록
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request");
		selectdto.setExKeyColumn("es_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new EdusatRequestDTO());
		String where_query = "where edu_idx = '"+edu_idx+"'";
		if(!"".equals(v_search2) && !"".equals(v_keyword2)){
			where_query += " and "+v_search2+" like '%"+v_keyword2+"%'";
		}
		if(!"".equals(v_status)) {
			where_query += " and es_status = '"+v_status+"'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by es_idx");
		List<HashMap<String,String>>edusatRequestList = exdao.selectQueryTable(selectdto);
		String encrypText = "";
		for(int i=0;i<=edusatRequestList.size()-1;i++){
			HashMap<String,String>edusatRequest = edusatRequestList.get(i);
			
			String es_status_str = "";
			
			if (!"9".equals(edusatRequest.get("es_status"))) {
				accnum = accnum + Func.cInt(edusatRequest.get("es_ptcp_cnt"));
			} 

			if ("9".equals(edusatRequest.get("es_status"))) {
				es_status_str = "취소";
			} else if ("1".equals(edusatRequest.get("es_status"))) {
				es_status_str = "당첨";
			} else if ("2".equals(edusatRequest.get("es_status"))) {
				es_status_str = "낙첨";
			} else if ("3".equals(edusatRequest.get("es_status"))) {
				es_status_str = "접수완료";
			} else {
				if ("2".equals(edusatdto.get("edu_lot_type"))) { // 선착순
					if (accnum > Func.cInt(edusatdto.get("edu_inwon"))) {
						es_status_str = "대기자";
					} else {
						es_status_str = "신청완료";
					}
				} else { // 추첨식
					es_status_str = "신청완료";
				}
			}
			edusatRequest.put("es_status_str", es_status_str);
		}
		List<String> colName = new ArrayList<String>();
		colName.add("번호");
		colName.add("성명");
		
		if(!edusatdto.get("edu_gubun").contains("임직원")) {
			colName.add("신청자구분"); // es_temp1
			colName.add("기관명(학교명)"); // es_name2 -> ARIA
			colName.add("수령자명"); // es_name2 -> ARIA
			colName.add("유선전화");
			colName.add("휴대전화");
			colName.add("이메일");
			colName.add("신청수량"); // es_etc
			colName.add("우편번호");
			colName.add("주소");
		}
		if(edusatdto.get("edu_gubun").contains("학교")) {
			/*
			colName.add("1학년 인원");
			colName.add("1학년 반 수");
			colName.add("2학년 인원");
			colName.add("2학년 반 수");
			*/
			colName.add("1~6학년 교육영상신청");
			colName.add("교통사고(1년 이내)");
			colName.add("교통사고 건수");
		}
		if(edusatdto.get("edu_gubun").contains("임직원")) {
			colName.add("사번");
			colName.add("직급");
			colName.add("팀명");
			colName.add("휴대전화");
			colName.add("이메일");
			colName.add("수령자명");
			colName.add("전교생인원");
			colName.add("우편번호");
			colName.add("주소");
			colName.add("학교와의 관계");
			colName.add("자녀의성명");
			colName.add("담당교사의 성명");
			colName.add("담당교사의 연락처");
			colName.add("나눔식 가능여부");
		}
		
		colName.add("신청사연");
		colName.add("신청일");
		colName.add("신청상태");
		
		
		int zz = 1;
		// 특수블럭 제거
		String xml10pattern = "[^" + "\u0009\r\n" + "\u0020-\uD7FF" + "\uE000-\uFFFD" + "\ud800\udc00-\udbff\udfff" + "]";
		String xml11pattern = "[^" + "\u0001-\uD7FF" + "\uE000-\uFFFD" + "\ud800\udc00-\udbff\udfff" + "]+";
		List<String[]> colValue = new ArrayList<String[]>();
		for(int i=0;i<=edusatRequestList.size()-1;i++){
			HashMap<String,String>edusatRequest = edusatRequestList.get(i);
			
			//ARIA
			String aria_str = "es_name,es_name2,es_bphone1,es_bphone2,es_bphone3,es_phone1,es_phone2,es_phone3,es_zipcode,es_addr1,es_addr2,es_email,es_temp2";
			String[] aria_arr = aria_str.replaceAll(" ", "").split(",");
			for (String string : aria_arr) {
				if(edusatRequest.get(string) != null && !"".equals(edusatRequest.get(string))) {
					//System.out.println(string);
					encrypText = cryptoService.decryptData(edusatRequest.get(string));
					encrypText = encrypText.replaceAll(xml10pattern, "");
					edusatRequest.put(string, encrypText);
				}	
			}
			
			if(edusatdto.get("edu_gubun").contains("임직원")) {
				//ARIA
				aria_str = "es_temp7,es_temp8, es_temp9";
				aria_arr = aria_str.replaceAll(" ", "").split(",");
				for (String string : aria_arr) {
					if(edusatRequest.get(string) != null && !"".equals(edusatRequest.get(string))) {
						//System.out.println(string);
						encrypText = cryptoService.decryptData(edusatRequest.get(string));
						encrypText = encrypText.replaceAll(xml10pattern, "");
						edusatRequest.put(string, encrypText);
					}	
				}
			}
			
			String es_temp1_str = edusatRequest.get("es_temp1");
			if(es_temp1_str.equals("i")) es_temp1_str = "유치원";
			//if(es_temp1_str.equals("i")) es_temp1_str = "초등학교";
			else if(es_temp1_str.equals("a")) es_temp1_str = "어린이집";
			else if(es_temp1_str.equals("s")) es_temp1_str = "기타";
			else if(es_temp1_str.equals("c")) es_temp1_str = "지역아동센터";
			
			
			String[] arr1 = new String[colName.size()];
			int index_num = 0;
			arr1[index_num++] = Func.cStr(i+1);
			arr1[index_num++] = edusatRequest.get("es_name");
			
			if(!edusatdto.get("edu_gubun").contains("임직원")) {
				arr1[index_num++] = es_temp1_str;
				arr1[index_num++] = edusatRequest.get("es_name2");
				arr1[index_num++] = edusatRequest.get("es_temp2");
				arr1[index_num++] = edusatRequest.get("es_bphone1")+"-"+edusatRequest.get("es_bphone2")+"-"+edusatRequest.get("es_bphone3");
				arr1[index_num++] = edusatRequest.get("es_phone1")+"-"+edusatRequest.get("es_phone2")+"-"+edusatRequest.get("es_phone3");
				arr1[index_num++] = edusatRequest.get("es_email");
				arr1[index_num++] = edusatRequest.get("es_etc");
				arr1[index_num++] = edusatRequest.get("es_zipcode");
				arr1[index_num++] = edusatRequest.get("es_addr1")+"("+edusatRequest.get("es_addr2")+")";
			}
			if(edusatdto.get("edu_gubun").contains("학교")) {
				/*
				arr1[index_num++] = edusatRequest.get("es_fgrade");
				arr1[index_num++] = edusatRequest.get("es_fgrade_class");
				arr1[index_num++] = edusatRequest.get("es_sgrade");
				arr1[index_num++] = edusatRequest.get("es_sgrade_class");
				*/
				arr1[index_num++] = (edusatRequest.get("es_video") != null && edusatRequest.get("es_video").equals("Y")) ? "신청" : "미신청";
				arr1[index_num++] = (edusatRequest.get("es_problem_yn") != null && edusatRequest.get("es_problem_yn").equals("Y")) ? "예" : "아니오";
				arr1[index_num++] = edusatRequest.get("es_problem");
			}
			if(edusatdto.get("edu_gubun").contains("임직원")) {
				arr1[index_num++] = edusatRequest.get("es_temp3");
				arr1[index_num++] = edusatRequest.get("es_temp4");
				arr1[index_num++] = edusatRequest.get("es_temp5");
				arr1[index_num++] = edusatRequest.get("es_phone1")+"-"+edusatRequest.get("es_phone2")+"-"+edusatRequest.get("es_phone3");
				arr1[index_num++] = edusatRequest.get("es_email");
				arr1[index_num++] = edusatRequest.get("es_temp2");
				arr1[index_num++] = edusatRequest.get("es_etc");
				arr1[index_num++] = edusatRequest.get("es_zipcode");
				arr1[index_num++] = edusatRequest.get("es_addr1")+"("+edusatRequest.get("es_addr2")+")";
				String temp6_str = "";
				switch (edusatRequest.get("es_temp6")) {
					case "1": temp6_str = "자녀의 학교"; break;
					case "2": temp6_str = "친척 및 지인자녀의 학교"; break;
					case "3": temp6_str = "본인의 모교"; break;
					case "4": temp6_str = "기타"; break;
				}
				arr1[index_num++] = temp6_str;
				arr1[index_num++] = edusatRequest.get("es_temp7");
				arr1[index_num++] = edusatRequest.get("es_temp8");
				arr1[index_num++] = edusatRequest.get("es_temp9");
				arr1[index_num++] = (edusatRequest.get("es_temp10") != null && edusatRequest.get("es_temp10").equals("1")) ? "가능" : "불가능";
			}
			arr1[index_num++] = edusatRequest.get("es_memo");
			arr1[index_num++] = edusatRequest.get("es_wdate");
			arr1[index_num++] = edusatRequest.get("es_status_str");
			
			
			colValue.add(arr1);
			zz = zz + 1;
		}
		
		map.put("excelName", "EdusatExcel");
		map.put("colName", colName);
		map.put("colValue", colValue);
		
		return new ExcelView();
	}
	
	/**
	 * @title : 신청자 카운트
	 * @method : getReqCount(String edu_idx)
	 */
	@Override
	public int getReqCount(String edu_idx) throws Exception {
		
		int count = this.getReqCount(edu_idx, "N");
		return count;
	}
	
	/**
	 * @title : 강좌정보수정/신청자관리
	 * @method : getReqCount(String edu_idx, String edu_ptcp_yn)
	 */
	@Override
	public int getReqCount(String edu_idx, String edu_ptcp_yn) throws Exception {
		
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
	 * @title : 신청자 목록
	 * @method : requestList
	 */
	@Override
	public void requestList(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String edu_idx = exdao.filter( request.getParameter("edu_idx") );
		
		String v_search2 = exdao.filter( request.getParameter("v_search2") );
		String v_keyword2 = exdao.filter( request.getParameter("v_keyword2") );
		if(v_search2.trim().equals("es_name")) {
			v_keyword2 = cryptoService.encryptData(v_keyword2);
		}
		String v_status = exdao.filter(request.getParameter("v_status"));
		
		//강좌정보
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");
		selectdto.setExColumn(new EdusatDTO());
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		HashMap<String,String>edusat = exdao.selectQueryRecord(selectdto);
		String req_count = Func.cStr( this.getReqCount(edu_idx, edusat.get("edu_ptcp_yn")) );
		edusat.put("req_count", req_count);
		request.setAttribute("edusat", edusat);
		
		//신청자 리스트
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request");
		selectdto.setExKeyColumn("es_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new EdusatRequestDTO());
		String where_query = "where edu_idx = '"+edu_idx+"'";
		if(!"".equals(v_search2) && !"".equals(v_keyword2)){
			where_query += " and "+v_search2+" like '%"+v_keyword2+"%'";
		}
		if(!"".equals(v_status)) {
			where_query += " and es_status = '"+v_status+"'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by es_idx");
		List<HashMap<String,String>>edusatRequestList = exdao.selectQueryTable(selectdto);
		
		// 특수블럭 제거
		String xml10pattern = "[^" + "\u0009\r\n" + "\u0020-\uD7FF" + "\uE000-\uFFFD" + "\ud800\udc00-\udbff\udfff" + "]";
		String xml11pattern = "[^" + "\u0001-\uD7FF" + "\uE000-\uFFFD" + "\ud800\udc00-\udbff\udfff" + "]+";
		
		//ARIA
		String aria_str = "es_name,es_name2,es_bphone1,es_bphone2,es_bphone3,es_phone1,es_phone2,es_phone3,m_mobile3,es_temp2";
		String[] aria_arr = aria_str.replaceAll(" ", "").split(",");
		String encrypText = "";
		for (HashMap<String, String> hashMap : edusatRequestList) {
			for (String string : aria_arr) {
				
				if(hashMap.get(string) != null && !"".equals(hashMap.get(string))) {
					//System.out.println(string);
					encrypText = cryptoService.decryptData(hashMap.get(string));
					encrypText = encrypText.replaceAll(xml10pattern, "");
					hashMap.put(string, encrypText);
				}
				
			}
		}
		/*
		for (HashMap<String, String> hashMap : edusatRequestList) {
			String plainText = "";
			plainText = hashMap.get("es_name");
			if(plainText != null && !"".equals(plainText)) {
				plainText = cryptoService.decryptData(plainText);
				hashMap.put("es_name", plainText);
			}
			plainText = hashMap.get("es_bphone1");
			if(plainText != null && !"".equals(plainText)) {
				plainText = cryptoService.decryptData(plainText);
				hashMap.put("es_bphone1", plainText);
				plainText = hashMap.get("es_bphone2");
				plainText = cryptoService.decryptData(plainText);
				hashMap.put("es_bphone2", plainText);
				plainText = hashMap.get("es_bphone3");
				plainText = cryptoService.decryptData(plainText);
				hashMap.put("es_bphone3", plainText);
			}
			plainText = hashMap.get("es_phone1");
			if(plainText != null && !"".equals(plainText)) {
				plainText = cryptoService.decryptData(plainText);
				hashMap.put("es_phone1", plainText);
				plainText = hashMap.get("es_phone2");
				plainText = cryptoService.decryptData(plainText);
				hashMap.put("es_phone2", plainText);
				plainText = hashMap.get("es_phone3");
				plainText = cryptoService.decryptData(plainText);
				hashMap.put("es_phone3", plainText);
			}
			
		}
		*/
		
		request.setAttribute("edusatRequestList", edusatRequestList);
		
	}

	/**
	 * @title : 신청상태변경
	 * @method : levelOk2
	 */
	@Override
	public void levelOk2(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		//Func.sendSms("문자테스트", "010-9485-8055");

		String tot_level_chk = exdao.filter(request.getParameter("tot_level_chk"));
		String[] chk = request.getParameterValues("chk");
		String edu_idx = exdao.filter(request.getParameter("edu_idx"));
		
		SelectDTO selectdto = new SelectDTO();
		//강좌정보
		selectdto.setExTableName("edusat");
		selectdto.setExColumn("edu_gubun, edu_lib");
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		HashMap<String, String>edusat = exdao.selectQueryRecord(selectdto);
		String edu_gubun = edusat.get("edu_gubun");
		String smsmsg = edusat.get("edu_lib").trim().equals("") ? "신청하신 프로그램에 선정되었습니다" : edusat.get("edu_lib");
		boolean sms_chk = false;
		if(edu_gubun.trim().contains("학교") || edu_gubun.trim().contains("교통안전교육")) {
			sms_chk = true;
		}
		
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request");
		selectdto.setExColumn("es_name,es_phone1,es_phone2,es_phone3");
		for (int z = 0; z <= chk.length - 1; z++) {
			String es_idx = exdao.filter( chk[z] );
			String end_chk = tot_level_chk;
			// 사용여부변경
			exdao.executeQuery("update edusat_request set es_status = '"+end_chk+"' where es_idx = '"+es_idx+"' and edu_idx = '"+edu_idx+"' ");
			
			//쿼리조회
			selectdto.setExWhereQuery("where es_idx = '"+es_idx+"' and edu_idx = '"+edu_idx+"'");
			HashMap<String, String> edu_request = exdao.selectQueryRecord(selectdto);
			String es_name = cryptoService.decryptData(edu_request.get("es_name"));
			String es_phone1 = cryptoService.decryptData(edu_request.get("es_phone1"));
			String es_phone2 = cryptoService.decryptData(edu_request.get("es_phone2"));
			String es_phone3 = cryptoService.decryptData(edu_request.get("es_phone3"));
			
			if("3".equals(end_chk) && sms_chk && (!es_phone1.equals("") && !es_phone2.equals("") && !es_phone3.equals(""))) {
				String smstel =  es_phone1+"-"+es_phone2+"-"+es_phone3;
				//System.out.println("sms 발송");
				Func.sendSms(smsmsg, smstel);
			}
			
			//작업기록
			String end_chk_str = "신청완료";
			if("9".equals(end_chk)) end_chk_str = "신청취소";
			if("3".equals(end_chk)) end_chk_str = "선정완료";
			if("4".equals(end_chk)) end_chk_str = "선정완료";
			Func.writeManagerLog ("신청자 상태변경 ["+es_name+"("+end_chk_str+")]", request);
		}
	}
}
