package kr.co.nninc.ncms.edusat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
/**
 * 온라인강좌 서비스 구현 클래스(사용자용)
 * 
 * @author 나눔
 * @since 2017.11.13
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kcb.module.v3.crypto.symmetric.ARIA;
import kr.co.nninc.ncms.board.service.BoardDTO;
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
import kr.co.nninc.ncms.edusat.service.UserEdusatService;

/**
 * 온라인강좌 서비스 구현 클래스(사용자용)
 * 
 * @author 나눔
 * @since 2017.11.13
 * @version 1.0
 */
@Service("userEdusatService")
public class UserEdusatServiceImpl extends EgovAbstractServiceImpl implements UserEdusatService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/** EdusatService */
	@Resource(name = "edusatService")
	private EdusatService edusatService;
	
	/** FileUtil */
	@Resource(name = "fileutil")
	private FileUtil fileutil;
	
	/**ARIA*/
	@Resource(name="cryptoService")
	private CryptoARIAService cryptoService;
	
	
	/**
	 * @title : 강좌목록
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//검색값
		String sh_ct_idx = Func.nvl( request.getParameter("sh_ct_idx") );
		String sh_ct_idx2 = exdao.filter(request.getParameter("sh_ct_idx2"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_gubun = exdao.filter(request.getParameter("gubun"));
		v_gubun = v_gubun.equals("") ? "47" : v_gubun;
		
		SelectDTO selectdto = new SelectDTO();
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(sh_ct_idx) && !"".equals(builder_dir)){
			selectdto.setExTableName("code_config");
			selectdto.setExColumn("ct_idx");
			selectdto.setExWhereQuery("where ct_ref = '1' and ct_chk='Y' and ct_site_dir='"+builder_dir+"'");
			sh_ct_idx = Func.nvl( exdao.selectQueryColumn(selectdto) );
		}
		// 분류코등갖고오기
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("ct_name");
		String where_query = "where ct_ref=2 and ct_chk ='Y' and ct_idx = '"+v_gubun+"'";
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		String ct_name = exdao.selectQueryColumn(selectdto);
		
		//도서관 코드 가져오기	
		/*
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("ct_idx, ct_name, ct_codeno");
		String where_query = "where ct_ref=1 and ct_chk ='Y'";
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>libList = exdao.selectQueryTable(selectdto);
		request.setAttribute("libList", libList);
		
		
		//강좌분류 가져오기
		where_query = "where ct_ref=2 and ct_chk ='Y'";
		if(!"".equals(sh_ct_idx) && !"".equals(builder_dir)){
			where_query += " and (ct_site_dir='"+builder_dir+"' or ct_site_dir = (select ct_site_dir from code_config where ct_idx ='"+sh_ct_idx+"'))";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code asc");
		List<HashMap<String,String>>cateList = exdao.selectQueryTable(selectdto);
		request.setAttribute("cateList", cateList);
		*/
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(exdao.getFieldNames(new EdusatDTO()));
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		
		String whereQuery = "where end_chk <> 'N'";
		if(!"".equals(v_search) && !"".equals(v_keyword)){ whereQuery += " and "+v_search+" like '%"+v_keyword+"%'"; }
		if(!"".equals(v_gubun) && !"".equals(ct_name)){ whereQuery += " and edu_gubun = '"+ct_name+"'"; }
		if(!"".equals(sh_ct_idx)){ whereQuery += " and ct_idx ='"+sh_ct_idx+"'"; }
		if(!"".equals(sh_ct_idx2)){ whereQuery += " and ct_idx2 ='"+sh_ct_idx2+"'"; }
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by edu_idx desc");
		
		List<HashMap<String,String>> edusatList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( edusatList.get(0).get("totalcount") ); // 전체레코드 수
		
		request.setAttribute("recordcount", recordcount);
		edusatList.remove(0);	//총검색개수행(첫번째행)을 삭제
		
		for(int i=0; i<edusatList.size();i++) {
			HashMap<String,String>edusat = edusatList.get(i);
			int req_count = edusatService.getReqCount(edusat.get("edu_idx"), edusat.get("edu_ptcp_yn"));
			edusat.put("req_count", Func.cStr(req_count));	//신청인원
		}
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
	 * @title : 강좌상세정보
	 * @method : view
	 */
	@Override
	public void view(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 강좌신청
	 * @method : regist
	 */
	@Override
	public String regist(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String edu_m_id = Func.getSession(request, "ss_m_id").trim();
		String edu_ci = Func.getSession(request, "ss_m_dupinfo").trim();	//ci말고 di를 저장하자
		String edu_idx = exdao.filter(request.getParameter("edu_idx"));
		request.setAttribute("edu_idx", edu_idx);
		
		SelectDTO selectdto = new SelectDTO();
		
		//중복신청확인
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request");
		selectdto.setExColumn("count(*)");
		String where_qry = "";
		where_qry = "where es_status <> 9";
		where_qry += " and edu_idx = '"+edu_idx+"'";
		//ARIA필요 - es_name
		String es_name = Func.getSession(request, "ss_m_name");
		es_name = cryptoService.encryptData(es_name);
		where_qry += " and es_name = '"+es_name+"'";
		if(!"".equals(edu_m_id)){
			where_qry += " and edu_m_id = '"+exdao.filter(edu_m_id)+"'";
		}else if(!"".equals(edu_ci.trim())){
			where_qry += " and (edu_ci = '"+exdao.filter(edu_ci)+"' or (edu_ci = '' and edu_m_id = ''))";			
		}
		
		selectdto.setExWhereQuery(where_qry);
		int du_cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
		if(du_cnt > 0){
			//return messageService.backMsg(model, "해당강좌 또는 같은 기수의 다른 강좌를 이미 신청중입니다.");
			return messageService.backMsg(model, "중복신청 하실 수 없습니다.");
		}
		
		//강좌정보
		
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");
		selectdto.setExColumn(new EdusatDTO());
		selectdto.setExWhereQuery("where edu_idx= '"+edu_idx+"'");
		HashMap<String,String> edusat = exdao.selectQueryRecord(selectdto);
		String edu_subject = Func.nvl( edusat.get("edu_subject") );
		String gubun = Func.nvl(edusat.get("edu_gubun"));
		if(gubun.contains("사연")) gubun = "0";
		else if (gubun.contains("학교")) gubun = "1";
		else gubun = "2";
		
		request.setAttribute("edusat", edusat);
		request.setAttribute("edu_subject", edu_subject);
		request.setAttribute("gubun_num", gubun);
		
		
		// 추가 : 신청자 정보 받을지 여부
		String edu_ptcp_yn = Func.nvl(edusat.get("edu_ptcp_yn"));
		String edu_ptcp_cnt = Func.nvl(edusat.get("edu_ptcp_cnt"));
		request.setAttribute("edu_ptcp_yn", edu_ptcp_yn);
		request.setAttribute("edu_ptcp_cnt", edu_ptcp_cnt);
		
		//신청인원 추가
		edusat.put( "req_count", Func.cStr(edusatService.getReqCount( edu_idx )) );
		
		//강좌신청가능여부 0:가능, 1:신청기간아님, 2:인원초과, 3:로그인필요, 4: 비회원&본인인증필요
		String result = requestCheck(request, edusat);
		System.out.println("result="+result);
		if("1".equals(result)){
			return messageService.backMsg(model, "신청기간이 아닙니다.");
		}else if("2".equals(result)){
			return messageService.backMsg(model, "신청인원이 초과되었습니다.");
		}else if("3".equals(result)){
			String login_url = "/site/member/ipin.do";
			String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
			if(!"".equals(builder_dir)){
				login_url = "/"+builder_dir+login_url;
			}
			login_url += "?prepage="+Func.urlEncode( Func.getNowPage(request) );
			return messageService.redirectMsg(model, "본인인증이 필요합니다.", login_url);
		}else if("4".equals(result)){ //비회원신청인데 로그인 안되어있으면 본인인증
			String NOWPAGE_ENCODE = Func.urlEncode( Func.getNowPage(request) );
			String url = "/site/member/login.do?prepage="+NOWPAGE_ENCODE;
			String url2 = "/site/member/ipin.do?prepage="+NOWPAGE_ENCODE;
			String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			if(!"".equals(BUILDER_DIR)){
				url = "/"+BUILDER_DIR+url;
				url2 = "/"+BUILDER_DIR+url2;
			}
			String prepage = request.getParameter("prepage");
			return messageService.confirmMsg(model, "본인인증시 신청이가능합니다.\\n본인인증페이지로 이동합니다.", url2, prepage);
		}
		
		//경고횟수
		int alert_count = 0;
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_blacklist");
		selectdto.setExColumn("count(*)");
		String where_query = "";
		if(!"".equals(edu_m_id)){
			where_query += " and es_id = '"+edu_m_id+"' ";
		}else if(!"".equals(edu_ci)){
			where_query += " and edu_ci = '"+edu_ci+"' ";
		}
		if(!"".equals(where_query)){
			selectdto.setExWhereQuery(where_query);
			alert_count = Func.cInt( exdao.selectQueryColumn(selectdto) );
			
		}
		if(alert_count >= 3){
			return messageService.backMsg(model, "신청이 제한되었습니다.\\n투명우산 나눔활동에 문의해주세요.");
		}
		
		
		String gubun_str = "";
		switch (gubun) {
			case "0": gubun_str = "사연"; break;
			case "1": gubun_str = "학교"; break;
			case "2": gubun_str = "나눔"; break;
		}
		
		SelectDTO selectDTO2 = new SelectDTO();
		selectDTO2.setExTableName("edusat");
		selectDTO2.setExColumn("edu_idx");
		selectDTO2.setExWhereQuery("where edu_gubun like '%"+gubun_str+"%' and (edu_wdate like '"+Func.date("Y")+"%' or edu_wdate like '"+(Func.cInt(Func.date("Y")) -1)+"%')");
		List<HashMap<String, String>>edu_idxs = exdao.selectQueryTable(selectDTO2);
		String edu_str = "";
		if(edu_idxs.size() > 0) {
			for (HashMap<String, String> hashMap : edu_idxs) {
				edu_str += hashMap.get("edu_idx")+",";
			}
			edu_str = edu_str.substring(0, edu_str.length() - 1);
		}
		if(!"".equals(edu_str.trim())) {
			String where_query1 = "WHERE edu_ci = '"+edu_ci+"' AND es_status = '3'";
			if(!"".equals(edu_str.trim())) where_query1 += " AND edu_idx IN ("+edu_str+")";
			selectDTO2 = new SelectDTO();
			selectDTO2.setExTableName("edusat_request");
			selectDTO2.setExColumn("count(*)");
			selectDTO2.setExWhereQuery(where_query1);
			int ctx = Func.cInt(exdao.selectQueryColumn(selectDTO2));
			if(ctx > 0) return messageService.backMsg(model, "올해 또는 작년 선정자는 신청하실 수 없습니다.");	
		}
		
		//나이제한
		String ss_g_num = Func.getSession(request, "ss_g_num");
		if(!"1".equals(ss_g_num) && !"4".equals(ss_g_num) && !"".equals(ss_g_num)){	//총관리자, 사이트관리자가 아니면
			if("Y".equals(edusat.get("edu_temp4"))){
				String prepage = Func.nvl(request.getParameter("prepage")).trim();
				if("".equals(prepage)){
					prepage = "/edusat/list.do";
					String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
					if(!"".equals(BUILDER_DIR)){
						prepage = "/"+BUILDER_DIR+"/edusat/list.do";
					}
				}
				
				String ss_user_birthday = Func.getSession(request, "ss_user_birthday");
				if("".equals(ss_user_birthday)){
					return messageService.redirectMsg(model, "참가대상이 아닙니다.\\n도서관에 등록된 생년월일을 확인하세요.", prepage);
				}

				String edu_temp5 = Func.nvl( edusat.get("edu_temp5") );
				if(!"".equals(edu_temp5) && !"|".equals(edu_temp5)){
					String[] edu_temp5_arr = edu_temp5.split("[|]");
					if(edu_temp5_arr.length >=2){
						int user_ymd = Func.cInt( ss_user_birthday.replaceAll("/", "") );
						int chk_ymd_s = Func.cInt( edu_temp5_arr[0] );
						int chk_ymd_e = Func.cInt( edu_temp5_arr[1] );
						if(chk_ymd_s > user_ymd || chk_ymd_e < user_ymd){
							return messageService.redirectMsg(model, "참가대상이 아닙니다.", prepage);
						}
					}
				}
			}
		}
		
		return "/site/edusat/regist";
	}
	
	/**
	 * @title : 강좌신청 수정
	 * @method : regist
	 */
	@Override
	public String modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String edu_m_id = Func.getSession(request, "ss_m_id").trim();
		String edu_ci = Func.getSession(request, "ss_m_dupinfo").trim();	//ci말고 di를 저장하자
		
		String es_idx = exdao.filter(request.getParameter("a")); 
		String edu_idx = exdao.filter(request.getParameter("b"));
		request.setAttribute("edu_idx", edu_idx);
		String es_tel = exdao.filter(request.getParameter("c"));
		String es_name = exdao.filter(request.getParameter("d"));
		String es_pwd = exdao.filter(request.getParameter("e"));

		if(es_pwd.equals("")) es_pwd = Func.hash(Func.getSession(request, "ss_m_pwd"));
		if(es_name.equals("")) es_name = Func.getSession(request, "ss_m_name");
		
		SelectDTO selectdto = new SelectDTO();
		
		//강좌정보
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");  
		
		selectdto.setExColumn(new EdusatDTO());
		selectdto.setExWhereQuery("where edu_idx= '"+edu_idx+"'");
		HashMap<String,String> edusat = exdao.selectQueryRecord(selectdto);
		String edu_subject = Func.nvl( edusat.get("edu_subject") );
		String gubun = Func.nvl(edusat.get("edu_gubun"));
		if(gubun.contains("사연")) gubun = "0";
		else if (gubun.contains("학교")) gubun = "1";
		else gubun = "2";
		
		request.setAttribute("edusat", edusat);
		request.setAttribute("edu_subject", edu_subject);
		request.setAttribute("gubun_num", gubun);
		
		//신청인원 추가
		edusat.put( "req_count", Func.cStr(edusatService.getReqCount( edu_idx )) );
		
		//강좌신청가능여부 0:가능, 1:신청기간아님, 2:인원초과, 3:로그인필요, 4: 비회원&본인인증필요
		String result = requestCheck(request, edusat);
		System.out.println("result="+result);
		if("1".equals(result)){
			return messageService.backMsg(model, "신청기간이 아닙니다.");
		}else if("2".equals(result)){
			return messageService.backMsg(model, "신청인원이 초과되었습니다.");
		}else if("3".equals(result)&& ("".equals(es_name) || "".equals(es_tel) || "".equals(es_pwd)) ){
			String login_url = "/site/member/ipin.do";
			String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
			if(!"".equals(builder_dir)){
				login_url = "/"+builder_dir+login_url;
			}
			login_url += "?prepage="+Func.urlEncode( Func.getNowPage(request) );
			return messageService.redirectMsg(model, "본인인증이 필요합니다.", login_url);
		}else if("4".equals(result) && ("".equals(es_name) || "".equals(es_tel) || "".equals(es_pwd)) ){ //비회원신청인데 로그인 안되어있으면 본인인증
			String NOWPAGE_ENCODE = Func.urlEncode( Func.getNowPage(request) );
			String url = "/site/member/login.do?prepage="+NOWPAGE_ENCODE;
			String url2 = "/site/member/ipin.do?prepage="+NOWPAGE_ENCODE;
			String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			if(!"".equals(BUILDER_DIR)){
				url = "/"+BUILDER_DIR+url;
				url2 = "/"+BUILDER_DIR+url2;
			}
			return messageService.redirectMsg(model, "프로그램신청을 위하여 본인인증페이지로 이동합니다", url2);
		}
		
		//경고횟수
		int alert_count = 0;
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_blacklist");
		selectdto.setExColumn("count(*)");
		String where_query = "";
		if(!"".equals(edu_m_id)){
			where_query += " and es_id = '"+edu_m_id+"' ";
		}else if(!"".equals(edu_ci)){
			where_query += " and edu_ci = '"+edu_ci+"' ";
		}
		if(!"".equals(where_query)){
			selectdto.setExWhereQuery(where_query);
			alert_count = Func.cInt( exdao.selectQueryColumn(selectdto) );
		}
		if(alert_count >= 3){
			return messageService.backMsg(model, "신청이 제한되었습니다.\\n투명우산에 문의해주세요.");
		}
		
		//나이제한
		String ss_g_num = Func.getSession(request, "ss_g_num");
		if(!"1".equals(ss_g_num) && !"4".equals(ss_g_num) && !"".equals(ss_g_num)){	//총관리자, 사이트관리자가 아니면
			if("Y".equals(edusat.get("edu_temp4"))){
				String prepage = Func.nvl(request.getParameter("prepage")).trim();
				if("".equals(prepage)){
					prepage = "/edusat/list.do";
					String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
					if(!"".equals(BUILDER_DIR)){
						prepage = "/"+BUILDER_DIR+"/edusat/list.do";
					}
				}
				
				String ss_user_birthday = Func.getSession(request, "ss_user_birthday");
				if("".equals(ss_user_birthday)){
					return messageService.redirectMsg(model, "참가대상이 아닙니다.\\n투명우산에 등록된 생년월일을 확인하세요.", prepage);
				}

				String edu_temp5 = Func.nvl( edusat.get("edu_temp5") );
				if(!"".equals(edu_temp5) && !"|".equals(edu_temp5)){
					String[] edu_temp5_arr = edu_temp5.split("[|]");
					if(edu_temp5_arr.length >=2){
						int user_ymd = Func.cInt( ss_user_birthday.replaceAll("/", "") );
						int chk_ymd_s = Func.cInt( edu_temp5_arr[0] );
						int chk_ymd_e = Func.cInt( edu_temp5_arr[1] );
						if(chk_ymd_s > user_ymd || chk_ymd_e < user_ymd){
							return messageService.redirectMsg(model, "참가대상이 아닙니다.", prepage);
						}
					}
				}
			}
		}
		
		//강좌정보
		selectdto.setExTableName("edusat_request");
		selectdto.setExKeyColumn("es_idx");
		selectdto.setExColumn(new EdusatRequestDTO());
		
		//ARIA
		if(es_name != null && !"".equals(es_name)) {
			es_name = cryptoService.encryptData(es_name);
		}
		where_query = "";
		where_query = "where es_idx= '"+es_idx+"' and edu_idx = '"+edu_idx+"'";
		if(edu_ci != null && !"".equals(edu_ci)) {
			es_name = cryptoService.encryptData(Func.getSession(request, "ss_m_name"));
			where_query += "  and es_name = '"+es_name+"' and edu_ci = '"+edu_ci+"'";
			es_pwd = "";
		}else{
			where_query += "  and es_name = '"+es_name+"' and es_pwd = '"+es_pwd+"'";
		}
		
		selectdto.setExWhereQuery(where_query);
		HashMap<String,String> edurequest = exdao.selectQueryRecord(selectdto);
		request.setAttribute("es_idx", es_idx);
		
		// 특수블럭 제거
		String xml10pattern = "[^" + "\u0009\r\n" + "\u0020-\uD7FF" + "\uE000-\uFFFD" + "\ud800\udc00-\udbff\udfff" + "]";
		String xml11pattern = "[^" + "\u0001-\uD7FF" + "\uE000-\uFFFD" + "\ud800\udc00-\udbff\udfff" + "]+";
		
		//ARIA
		String encrypText = "";
		String aria_str = "es_name,es_name2,es_bphone1,es_bphone2,es_bphone3,es_phone1,es_phone2,es_phone3,es_zipcode,es_addr1,es_addr2,es_email,es_temp2,es_temp7,es_temp8,es_temp9";
		String[] aria_arr = aria_str.replaceAll(" ", "").split(",");
		for (String string : aria_arr) {
			if(edurequest.get(string) != null && !"".equals(edurequest.get(string))) {
				encrypText = cryptoService.decryptData(edurequest.get(string));
				encrypText = encrypText.replaceAll(xml10pattern, "");
				edurequest.put(string, encrypText);
			}	
		}
		String temp_9 = edurequest.get("es_temp9");
		String[] temp_9_arr = temp_9.split("-");
		for(int j = 0; j<temp_9_arr.length; j++) {
			edurequest.put("es_temp9_"+(j+1), temp_9_arr[j]);
		}

		request.setAttribute("edurequest", edurequest);
		
		return "/site/edusat/regist";
	}

	/**
	 * @title : 강좌신청처리
	 * @method : registOk
	 */
	@Override
	public String registOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		EdusatRequestDTO edusatrequestdto = (EdusatRequestDTO) map.get("edusatrequestdto");
		
		String edu_m_id = Func.getSession(request, "ss_m_id").trim();
		String edu_ci = Func.getSession(request, "ss_m_dupinfo").trim();	//di를 사용하자
		String edu_idx = exdao.filter(request.getParameter("edu_idx"));

		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		
		String es_name = exdao.filter(edusatrequestdto.getEs_name());
		String es_bphone1 = exdao.filter(edusatrequestdto.getEs_bphone1());
		String es_bphone2 = exdao.filter(edusatrequestdto.getEs_bphone2());
		String es_bphone3 = exdao.filter(edusatrequestdto.getEs_bphone3());
		String es_jumin = exdao.filter(edusatrequestdto.getEs_jumin());
		
		SelectDTO selectdto = new SelectDTO();
		
		//경고횟수
		int alert_count = 0;
		selectdto.setExTableName("edusat_blacklist");
		selectdto.setExColumn("count(*)");
		String where_query = "";
		if(!"".equals(edu_m_id)){
			where_query += " and es_id = '"+edu_m_id+"' ";
		}else if(!"".equals(edu_ci)){
			where_query += " and edu_ci = '"+edu_ci+"' ";
		}else{
			where_query += " and es_name = '"+es_name+"'";
			where_query += " and es_bphone1 = '"+es_bphone1+"'";
			where_query += " and es_bphone2 = '"+es_bphone2+"'";
			where_query += " and es_bphone3 = '"+es_bphone3+"'";
		}
		selectdto.setExWhereQuery(where_query);
		alert_count = Func.cInt( exdao.selectQueryColumn(selectdto) );
		if(alert_count >= 3){
			return messageService.backMsg(model, "신청이 제한되었습니다.\\n담당자에게 문의해주세요.");
		}
		
		//강좌정보
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");
		selectdto.setExColumn(new EdusatDTO());
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		HashMap<String,String>edusat = exdao.selectQueryRecord(selectdto);
		int edu_inwon = Func.cInt( edusat.get("edu_inwon") );
		String edu_season = Func.nvl( edusat.get("edu_season") ).trim();
		
		
		edusatrequestdto.setEdu_m_id(edu_m_id);
		edusatrequestdto.setEdu_ci(edu_ci);
		edusatrequestdto.setEs_status("0");	//신청상태 기본설정
		edusatrequestdto.setEs_wdate( Func.date("Y-m-d H:i:s") );
		
		//중복신청확인
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request");
		selectdto.setExColumn("count(*)");
		where_query = "where es_status <> 9";
		/*if("0".equals(edu_season) || "".equals(edu_season)){	//기수가 설정된 강좌는 모두 포함
			where_query += " and edu_idx = '"+edu_idx+"'";
		}else{
			where_query += " and edu_idx in (select edu_idx from edusat where edu_season = '"+edu_season+"')";
		}*/
		
		where_query += " and edu_idx = '"+edu_idx+"'";
		String temp9 = request.getParameter("es_temp9_1")+"-"+request.getParameter("es_temp9_2")+"-"+request.getParameter("es_temp9_3");
		edusatrequestdto.setEs_temp9(temp9);
		//ARIA필요 - es_name
		es_name = cryptoService.encryptData(es_name);
		where_query += " and es_name = '"+es_name+"'";
		
		if(!"".equals(edu_m_id)){
			where_query += " and edu_m_id = '"+exdao.filter(edu_m_id)+"'";
		}else if(!"".equals(edu_ci)){
			where_query += " and (edu_ci = '"+exdao.filter(edu_ci)+"' or (edu_m_id = '' and edu_ci = '') ) ";			
		}else if(!"".equals(es_bphone1) && !"".equals(es_bphone2) && !"".equals(es_bphone3)){
			where_query += " and es_bphone1 = '"+es_bphone1+"' and es_bphone2 = '"+es_bphone2+"' and es_bphone3 = '"+es_bphone3+"'";
			if(!"".equals(es_jumin)){ where_query += " and es_jumin = '"+es_jumin+"'"; }
		}
		
		selectdto.setExWhereQuery(where_query);
		int du_cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
		if(du_cnt > 0){
			//return messageService.backMsg(model, "해당강좌 또는 같은 기수의 다른 강좌를 이미 신청중입니다.");
			return messageService.backMsg(model, "중복신청 하실 수 없습니다.");
		}
		
		//강좌신청가능여부 0:가능, 1:신청기간아님, 2:인원초과, 3:로그인필요
		String result = requestCheck(request, edusat);
		if("1".equals(result)){
			return messageService.backMsg(model, "신청기간이 아닙니다.");
		}else if("2".equals(result)){
			return messageService.backMsg(model, "신청인원이 초과되었습니다.");
		}else if("3".equals(result)){
			String login_url = "/site/member/ipin.do";
			String regist_url = "/edusat/regist.do?edu_idx="+edu_idx;
			
			if(!"".equals(builder_dir)){
				login_url += "/"+builder_dir+login_url;
				regist_url += "/"+builder_dir+regist_url;
			}
			login_url += "?prepage="+Func.urlEncode( regist_url );
			return messageService.redirectMsg(model, "본인인증이 필요합니다.", login_url);
		}else if("4".equals(result)){ //비회원신청인데 로그인 안되어있으면 본인인증
			/*
			String url = "/site/member/login.do?prepage=/edusat/regist.do?edu_idx="+edu_idx;
			String url2 = "/site/member/ipin.do?prepage=/edusat/regist.do?edu_idx="+edu_idx;
			return messageService.confirmMsg(model, "홈페이지 회원이십니까? \\n(확인은 로그인화면으로, 취소는 본인인증페이지로 이동합니다.)", url, url2);
			*/
		}
		// 업로드를 미리처리하자.
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/edusat/";
		String strDir2 = realpath + "/edusat/thum/";
		Func.folderCreate(strDir);
		Func.folderCreate(strDir2);
		for (int z = 1; z <= 5; z++) {
			// 가변변수 파일명 가져오기
			String es_file = Func.getValue(edusatrequestdto, "es_file" + z);
			if (!"".equals(es_file)) {
				// 썸네일
				//FuncThumb.GD2_make_thumb(640, 480, "/thum/", strDir, es_file);
			}
		}
				
		//insert 제외
		edusatrequestdto.setEs_idx(null);
		
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
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_bphone1());
			edusatrequestdto.setEs_bphone1(encrypText);
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_bphone2());
			edusatrequestdto.setEs_bphone2(encrypText);
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_bphone3());
			edusatrequestdto.setEs_bphone3(encrypText);
		}
		if(edusatrequestdto.getEs_phone1() != null && !"".equals(edusatrequestdto.getEs_phone1())) {
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
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_addr2());
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
		
		
		// 비밀번호
		String es_pwd = "";
		if(edu_m_id.equals("")) es_pwd = request.getParameter("es_pwd");
		else es_pwd = Func.getSession(request, "ss_m_pwd");
		edusatrequestdto.setEs_pwd(Func.hash(es_pwd));
		
		//insert
		if(edu_ci.trim().equals("") && edu_m_id.trim().equals("")) return messageService.backMsg(model, "본인인증을 확인해주세요.");
		else if (edusatrequestdto.getEs_phone1() == null || edusatrequestdto.getEs_phone1().trim().equals("")
				|| edusatrequestdto.getEs_phone2() == null || edusatrequestdto.getEs_phone2().trim().equals("")
				|| edusatrequestdto.getEs_phone3() == null || edusatrequestdto.getEs_phone3().trim().equals("")
				|| edusatrequestdto.getEs_email() == null || edusatrequestdto.getEs_email().trim().equals("")) {
			return messageService.backMsg(model, "입력정보를 확인해주세요.");
		} else {
			edusatrequestdto.setExTableName("edusat_request");
			exdao.insert(edusatrequestdto);	
		}
		
		String prepage = Func.nvl( request.getParameter("prepage") ); 
		if("".equals(prepage)){
			prepage = "/edusat/list.do";
			if(!"".equals(builder_dir)){prepage = "/"+builder_dir+prepage;}
		}
		
		String msg = "신청되었습니다.";
		//int req_count = edusatService.getReqCount(edu_idx);
		//if(edu_inwon < req_count){
			//msg = "정원이 초과되어 대기자로 신청되었습니다.";
		//}
		return messageService.redirectMsg(model, msg, prepage);
	}
	
	/**
	 * @title : 강좌신청처리
	 * @method : registOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		EdusatRequestDTO edusatrequestdto = (EdusatRequestDTO) map.get("edusatrequestdto");
		
		String edu_m_id = Func.getSession(request, "ss_m_id").trim();
		String edu_ci = Func.getSession(request, "ss_m_dupinfo").trim();	//di를 사용하자
		
		String edu_idx = exdao.filter(request.getParameter("edu_idx"));
		String es_idx = exdao.filter(request.getParameter("es_idx"));

		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		
		String es_name = exdao.filter(edusatrequestdto.getEs_name());
		String es_bphone1 = exdao.filter(edusatrequestdto.getEs_bphone1());
		String es_bphone2 = exdao.filter(edusatrequestdto.getEs_bphone2());
		String es_bphone3 = exdao.filter(edusatrequestdto.getEs_bphone3());
		String es_phone1 = exdao.filter(edusatrequestdto.getEs_phone1());
		String es_phone2 = exdao.filter(edusatrequestdto.getEs_phone2());
		String es_phone3 = exdao.filter(edusatrequestdto.getEs_phone3());
		String es_jumin = exdao.filter(edusatrequestdto.getEs_jumin());
		
		SelectDTO selectdto = new SelectDTO();
		
		//경고횟수
		int alert_count = 0;
		selectdto.setExTableName("edusat_blacklist");
		selectdto.setExColumn("count(*)");
		String where_query = "where 1= 1 ";
		String where_query2 = "where 1= 1 ";
		
		if(!"".equals(edu_m_id)){
			System.out.println("조건1");
			where_query += " and es_id = '"+edu_m_id+"' ";
			where_query2 += " and edu_m_id = '"+edu_m_id+"' ";
			//cryptoService.encryptData(plainText)
		}else if(!"".equals(edu_ci)){
			System.out.println("조건2");
			where_query += " and edu_ci = '"+edu_ci+"' ";
			where_query2 += " and edu_ci = '"+edu_ci+"' ";
		}else{
			System.out.println("조건3");
			where_query += " and es_name = '"+es_name+"'";
			where_query2 += " and es_name = '"+es_name+"'";
			
			if(es_bphone1 != "") {
				String str_1 = cryptoService.encryptData(es_bphone1);
				String str_2 = cryptoService.encryptData(es_bphone2);
				String str_3 = cryptoService.encryptData(es_bphone3);
				where_query += " and es_bphone1 = '"+str_1+"'";
				where_query += " and es_bphone2 = '"+str_2+"'";
				where_query += " and es_bphone3 = '"+str_3+"'";
				
				where_query2 += " and es_bphone1 = '"+str_1+"'";
				where_query2 += " and es_bphone2 = '"+str_2+"'";
				where_query2 += " and es_bphone3 = '"+str_3+"'";
			}else{
				String str_1 = cryptoService.encryptData(es_phone1);
				String str_2 = cryptoService.encryptData(es_phone2);
				String str_3 = cryptoService.encryptData(es_phone3);
				where_query += " and es_phone1 = '"+str_1+"'";
				where_query += " and es_phone2 = '"+str_2+"'";
				where_query += " and es_phone3 = '"+str_3+"'";
				
				where_query2 += " and es_phone1 = '"+str_1+"'";
				where_query2 += " and es_phone2 = '"+str_2+"'";
				where_query2 += " and es_phone3 = '"+str_3+"'";
			}
			
		}
		System.out.println("조건결과");
		System.out.println(where_query);
		selectdto.setExWhereQuery(where_query);
		
		alert_count = Func.cInt( exdao.selectQueryColumn(selectdto) );
		if(alert_count >= 3){
			return messageService.backMsg(model, "신청이 제한되었습니다.\\n담당자에게 문의해주세요.");
		}
		
		//강좌정보
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");
		selectdto.setExColumn(new EdusatDTO());
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		HashMap<String,String>edusat = exdao.selectQueryRecord(selectdto);
		int edu_inwon = Func.cInt( edusat.get("edu_inwon") );
		String edu_season = Func.nvl( edusat.get("edu_season") ).trim();
		
		edusatrequestdto.setEdu_m_id(edu_m_id);
		edusatrequestdto.setEdu_ci(edu_ci);
		edusatrequestdto.setEs_status("0");	//신청상태 기본설정
		edusatrequestdto.setEs_wdate( Func.date("Y-m-d H:i:s") );
		
		//강좌신청가능여부 0:가능, 1:신청기간아님, 2:인원초과, 3:로그인필요
		String result = requestCheck(request, edusat);
		if("1".equals(result)){
			return messageService.backMsg(model, "신청기간이 아닙니다.");
		}else if("2".equals(result)){
			return messageService.backMsg(model, "신청인원이 초과되었습니다.");
		}else if("3".equals(result) && (edu_idx.equals("") || es_idx.equals(""))){
			String login_url = "/site/member/ipin.do";
			String regist_url = "/edusat/regist.do?edu_idx="+edu_idx;
			
			if(!"".equals(builder_dir)){
				login_url += "/"+builder_dir+login_url;
				regist_url += "/"+builder_dir+regist_url;
			}
			login_url += "?prepage="+Func.urlEncode( regist_url );
			return messageService.redirectMsg(model, "본인인증이 필요합니다.", login_url);
		}else if("4".equals(result)&& (edu_idx.equals("") || es_idx.equals(""))){ //비회원신청인데 로그인 안되어있으면 본인인증
			/*
			String url = "/site/member/login.do?prepage=/edusat/regist.do?edu_idx="+edu_idx;
			String url2 = "/site/member/ipin.do?prepage=/edusat/regist.do?edu_idx="+edu_idx;
			return messageService.confirmMsg(model, "홈페이지 회원이십니까? \\n(확인은 로그인화면으로, 취소는 본인인증페이지로 이동합니다.)", url, url2);
			*/
		}
		
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
			String es_file = Func.getValue(edusatrequestdto, "es_file" + z);
			if (!"".equals(es_file)) {
				// 썸네일
				//FuncThumb.GD2_make_thumb(640, 480, "/thum/", strDir, b_file);
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
				
		//update 제외
		edusatrequestdto.setEs_idx(null);
		edusatrequestdto.setEdu_idx(null);
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
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_bphone1());
			edusatrequestdto.setEs_bphone1(encrypText);
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_bphone2());
			edusatrequestdto.setEs_bphone2(encrypText);
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_bphone3());
			edusatrequestdto.setEs_bphone3(encrypText);
		}
		if(edusatrequestdto.getEs_phone1() != null && !"".equals(edusatrequestdto.getEs_phone1())) {
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
			encrypText = cryptoService.encryptData(edusatrequestdto.getEs_addr2());
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
		
		// 비밀번호
		String es_pwd = "";
		if(edu_m_id.equals("")) es_pwd = request.getParameter("es_pwd");
		else es_pwd = Func.getSession(request, "ss_m_pwd");
		edusatrequestdto.setEs_pwd(Func.hash(es_pwd));
		
		//update
		edusatrequestdto.setExTableName("edusat_request");
		edusatrequestdto.setExWhereQuery(where_query2);
		exdao.update(edusatrequestdto);
		
		
		String prepage = Func.nvl( request.getParameter("prepage") ); 
		if("".equals(prepage)){
			prepage = "/edusat/list.do";
			if(!"".equals(builder_dir)){prepage = "/"+builder_dir+prepage;}
		}
		
		//작업기록
		Func.writeBoardLog ("프로그램 신청자 신청 정보 수정 ["+es_idx+" : "+es_name+"]", request);
		
		String msg = "수정되었습니다.";
		//int req_count = edusatService.getReqCount(edu_idx);
		//if(edu_inwon < req_count){
			//msg = "정원이 초과되어 대기자로 신청되었습니다.";
		//}
		return messageService.redirectMsg(model, msg, prepage);
	}

	@Override
	public String cancel(Model model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @title : 신청취소 처리
	 * @method : cancelOk
	 */
	@Override
	public String cancelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String prepage = Func.nvl(request.getParameter("prepage")).trim();
		if("".equals(prepage)){prepage = "list.do";}
		
		String es_idx = exdao.filter( request.getParameter("es_idx") ).trim();
		//exdao.executeQuery("update edusat_request set es_status = '9' where es_idx = '"+es_idx+"'");
		exdao.executeQuery("delete from edusat_request where es_idx = '"+es_idx+"'");
		
		return messageService.redirectMsg(model, "취소되었습니다.", prepage);
	}

	/**
	 * @title : 신청자목록
	 * @method : user
	 */
	@Override
	public String user(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int edu_idx = Func.cInt( exdao.filter( request.getParameter("edu_idx") ) );
		
		//세션정보
		String ss_m_id			= Func.getSession(request, "ss_m_id");
		String ss_m_coinfo	= Func.getSession(request, "ss_m_dupinfo");	//di를 사용하자
		
		//String es_name = exdao.filter(request.getParameter("es_name"));
		//String es_bphone1 = exdao.filter(request.getParameter("es_bphone1"));
		//String es_bphone2 = exdao.filter(request.getParameter("es_bphone2"));
		//String es_bphone3 = exdao.filter(request.getParameter("es_bphone3"));
		String es_name = cryptoService.encryptData(exdao.filter(request.getParameter("es_name")));
		String es_bphone1 = cryptoService.encryptData(exdao.filter(request.getParameter("es_bphone1")));
		String es_bphone2 = cryptoService.encryptData(exdao.filter(request.getParameter("es_bphone2")));
		String es_bphone3 = cryptoService.encryptData(exdao.filter(request.getParameter("es_bphone3")));
		String es_pwd = exdao.filter(request.getParameter("es_pwd"));
		if(ss_m_id != null && !"".equals(ss_m_id)) {
			request.setAttribute("code", Func.hash(Func.getSession(request, "ss_m_pwd")));	
		}else {
			request.setAttribute("code", Func.hash(es_pwd));
		}
		
		
		//이용자 정보 체크
		if("".equals(ss_m_id) && "".equals(ss_m_coinfo)){
			if("".equals(es_name) && "".equals(es_bphone1) && "".equals(es_bphone2) && "".equals(es_bphone3) && "".equals(es_pwd)){
				return "/site/edusat/user_chk";
			}
		}
		
		System.out.println("1?");
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request");
		selectdto.setExKeyColumn("es_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new EdusatRequestDTO());
		
		String where_query = "where edu_idx = '"+edu_idx+"'";
		
		if(!"".equals(ss_m_id)){
			where_query += " and edu_m_id = '"+exdao.filter(ss_m_id)+"'";
		}else if(!"".equals(ss_m_coinfo)){
			where_query += " and edu_ci = '"+exdao.filter(ss_m_coinfo)+"'";
		}else{
			where_query += " and es_name = '"+es_name+"'";
			where_query += " and es_bphone1 = '"+es_bphone1+"' and es_bphone2 = '"+es_bphone2+"' and es_bphone3 = '"+es_bphone3+"'";
			where_query += " and es_pwd = '"+Func.hash(es_pwd)+"'";
		}
		selectdto.setExWhereQuery(where_query);
		System.out.println("2?");
		List<HashMap<String,String>>edusatRequestList = exdao.selectQueryTable(selectdto);
		System.out.println("3?");
		
		
		//강좌정보, 신청자수
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");
		selectdto.setExColumn(new EdusatDTO());
		selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"'");
		HashMap<String,String>edusat = exdao.selectQueryRecord(selectdto);
		if(edusat.size() == 0){
			return messageService.backMsg(model, "강좌정보가 없습니다.");
		}
		System.out.println("4?");
		int req_count = edusatService.getReqCount(Func.cStr(edu_idx), edusat.get("edu_ptcp_yn"));
		edusat.put("req_count", Func.cStr(req_count));
		System.out.println("5?");
		//나보다 먼저신청한 인원수
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request");
		selectdto.setExColumn("count(*)");
		for(int i=0;i<=edusatRequestList.size()-1;i++){
			HashMap<String,String>edusatRequest = edusatRequestList.get(i);
			
			String es_idx = exdao.filter( edusatRequest.get("es_idx") );
			selectdto.setExWhereQuery("where edu_idx = '"+edu_idx+"' and es_status <> 9 and es_idx < '"+es_idx+"'");
			String count_value = exdao.selectQueryColumn(selectdto);
			edusatRequest.put("count_value", count_value);
			
			String plainText = "";
			plainText = cryptoService.decryptData(edusatRequest.get("es_name"));
			edusatRequest.put("es_name", plainText);
			plainText = cryptoService.decryptData(edusatRequest.get("es_phone1"));
			edusatRequest.put("es_phone1", plainText);
			plainText = cryptoService.decryptData(edusatRequest.get("es_phone2"));
			edusatRequest.put("es_phone2", plainText);
			plainText = cryptoService.decryptData(edusatRequest.get("es_phone3"));
			edusatRequest.put("es_phone3", plainText);
			
		}
		System.out.println("6?");
		//강좌정보 전송
		request.setAttribute("edusatdto", edusat);
		
		//신청자정보 전송
		request.setAttribute("edusatreq", edusatRequestList);
		
		return "/site/edusat/user";
	}

	@Override
	public void user_chk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 강좌 신청내역조회
	 * @method : myinfo
	 */
	@Override
	public void myinfo(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//검색값
		String sh_ct_idx = Func.nvl( request.getParameter("sh_ct_idx") );
		String v_search = exdao.filter(request.getParameter("v_search"));
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		
		//세션정보담기
		String ss_m_id = Func.getSession(request, "ss_m_id");
		String ss_m_coinfo = Func.getSession(request, "ss_m_dupinfo");	//ci말고 di를 사용하자
				
		SelectDTO selectdto = new SelectDTO();

		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request a left outer join edusat b on a.edu_idx = b.edu_idx");
		//selectdto.setExKeyColumn("a.es_idx");	//고유컬럼 설정 필수
		String edusat_request_fields = "a.edu_idx, es_idx, es_fgrade, es_name, es_wdate, es_status, es_ptcp_cnt, es_ptcp_name";
		String edusat_fields = exdao.getFieldNames(new EdusatDTO(), "edu_idx");
		selectdto.setExColumn(edusat_request_fields+","+edusat_fields);
		String whereQuery = "";
		if(!"".equals(ss_m_id)){
			whereQuery += " and edu_m_id = '"+ss_m_id+"'";
		}else if(!"".equals(ss_m_coinfo)){
			whereQuery += " and edu_ci = '"+ss_m_coinfo+"'";
		}
		if(!"".equals(sh_ct_idx)){
			whereQuery += " and b.ct_idx = '"+sh_ct_idx+"'";
		}
		if(!"".equals(v_search)&&!"".equals(v_keyword)){
			whereQuery += " and "+v_search+" like '%"+v_keyword+"%'";
		}
		whereQuery += " and b.edu_subject is not null and b.edu_subject <> ''";
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY es_idx DESC");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		selectdto.setExWhereQuery(whereQuery);

		List<HashMap<String,String>>edusatList = new ArrayList<HashMap<String,String>>();
		if(!"".equals(ss_m_id) || !"".equals(ss_m_coinfo)){
			edusatList = exdao.selectQueryPage(selectdto);
			recordcount = Func.cInt( edusatList.get(0).get("totalcount") ); // 전체레코드 수
			edusatList.remove(0);	//총검색개수행(첫번째행)을 삭제
		}
	
		for(int i=0;i<=edusatList.size()-1;i++){
			HashMap<String,String>edusat = edusatList.get(i);
			int req_count = edusatService.getReqCount(edusat.get("edu_idx"), edusat.get("edu_ptcp_yn"));
			edusat.put("req_count", Func.cStr(req_count));	//신청인원
			
			//나보다 먼저신청한 인원수
			selectdto = new SelectDTO();
			selectdto.setExTableName("edusat_request");
			selectdto.setExColumn("count(*)");
			selectdto.setExWhereQuery("where edu_idx = '"+edusat.get("edu_idx")+"' and es_status <> 9 and es_idx < '"+edusat.get("es_idx")+"'");
			String count_value = exdao.selectQueryColumn(selectdto);
			edusat.put("count_value", Func.cStr(count_value));
			
		}
		
		request.setAttribute("edusatList", edusatList);
		
		request.setAttribute("recordcount", recordcount);
		
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수
		
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = pagePerBlock;	//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		
		String querystring2 = paging.setQueryString(request, "v_search, v_keyword, v_gnum");
	
		String pagingtag = paging.execute(querystring2);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pagesize", pagesize);
		//페이징문자열 생성 끝
		
		Func.getNowPage(request);
		
	}

	/**
	 * @title : 강좌신청가능여부
	 * @method : requestCheck
	 * @result : 0:가능, 1:신청기간아님, 2:인원초과, 3:로그인필요, 4:비회원 본인인증 필요
	 */
	@Override
	public String requestCheck(HttpServletRequest request, HashMap<String,String> edusatdto) throws Exception {
		String edu_login = Func.nvl( edusatdto.get("edu_login") );
		String edu_resdate = Func.nvl(edusatdto.get("edu_resdate"));
		String edu_reedate = Func.nvl(edusatdto.get("edu_reedate"));
		
		String edu_resdate_h = Func.nvl( edusatdto.get("edu_resdate_h") );
		String edu_reedate_h = Func.nvl( edusatdto.get("edu_reedate_h") );
		if(edu_resdate_h.length() == 1){edu_resdate_h = "0"+edu_resdate_h;}
		if(edu_reedate_h.length() == 1){edu_reedate_h = "0"+edu_reedate_h;}
		
		String edu_lot_type = Func.nvl( edusatdto.get("edu_lot_type") );
		String end_chk = Func.nvl( edusatdto.get("end_chk") );
		int edu_inwon = Func.cInt( edusatdto.get("edu_inwon") );
		int edu_awaiter = Func.cInt( edusatdto.get("edu_awaiter") );
		int req_count = edusatService.getReqCount( edusatdto.get("edu_idx") );
		
		//세션정보
		String m_id			= Func.getSession(request, "ss_m_id").trim();
		String edu_ci		= Func.getSession(request, "ss_m_dupinfo").trim();	//dupinfo를 사용하자
		if("Y".equals(edu_login)){
			if("".equals(m_id)){
				return "3";	//로그인필요
			}
		}else if("".equals(Func.nvl(edu_ci)) && "".equals(Func.nvl(m_id))) {
			//비로그인시 본인인증할경우만 활성화 하자 
			return "4";	//비회원 본인인증 필요
		}
		
		//신청가능유무
		long now = Func.cLng( Func.date("YmdHi") );
		long resdate = Func.cLng( edu_resdate.replaceAll("-","") + edu_resdate_h + "00");
		long reedate = Func.cLng( edu_reedate.replaceAll("-","") + edu_reedate_h + "00" );
		if("1".equals(edu_lot_type)){	//추첨제
			if( !("Y".equals(end_chk) && resdate <= now && reedate > now)){
				return "1";	//신청기간아님
			}
			
		}else if("2".equals(edu_lot_type)){	//선착순
			if( (edu_inwon+edu_awaiter) <= req_count ){
				return "2";	//신청인원초과
			}
			if( !("Y".equals(end_chk) && resdate <= now && reedate > now)){
				return "1";	//신청기간아님
			}
		}
		return "0";
	}
	
	/**
	 * @title : 게시판 멀티업로드
	 * @method : nfuUpload
	 */
	@Override
	public void nfuUpload(Model model) throws Exception {
		// 게시판 설정세팅
		/*
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return;
		}
		*/

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");

		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/edusat/";
		String strDir2 = realpath + "/edusat/thum/";
		Func.folderCreate(strDir);
		Func.folderCreate(strDir2);
		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------

		String uploaddir = fileutil.realPath(request, "/data/edusat/");
		int sizeLimit = Func.cInt("5") * 1024 * 1024;
		String target_name = "";
		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "프로그램신청이미지파일", "NFU_add_file", sizeLimit,
				"gif,jpg,png,jpeg,bmp".split(","));
		if (!"".equals(file.getFile_name())) {
			target_name = file.getFile_name();
		}
		request.setAttribute("target_name", target_name);
		System.out.println(target_name);
	}

	/**
	 * @title : 게시판 일반업로드
	 * @method : nfuNormalUpload
	 */
	@Override
	public void nfuNormalUpload(Model model) throws Exception {
		// 게시판 설정세팅
		/*
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return;
		}
		*/

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");

		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
		String realpath = fileutil.realPath(request, "/data/");
		String strDir = realpath + "/edusat/";
		String strDir2 = realpath + "/edusat/thum/";
		Func.folderCreate(strDir);
		Func.folderCreate(strDir2);
		// ------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------

		String init = request.getParameter("init");
		String uploadchk = "";
		String file_names = "";
		String uploaddir = fileutil.realPath(request, "/data/edusat/");

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
	 * @title : 이미지파일 삭제
	 * @method : imgDeleteOk
	 */
	@Override
	public String imgDeleteOk(Model model) throws Exception {
		// 게시판 설정세팅
		/*
		String conf_result = this.setConfig(model);
		if (!"OK".equals(conf_result)) {
			return conf_result;
		}
		*/
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//파라미터정보 *************************************************************************************
		String es_idx				= Func.nvl( request.getParameter("es_idx") );
		String edu_idx				= Func.nvl( request.getParameter("edu_idx") );
		String es_file				= "es_file"+Func.nvl(request.getParameter("es_file"));
		String prepage				= Func.nvl(request.getParameter("prepage"));
		//**************************************************************************************************
		
		//String a_tablename = Func.nvl( config.get("a_tablename") );
		
		EdusatRequestDTO selectdto = new EdusatRequestDTO();
		selectdto.setExTableName("edusat_request");
		selectdto.setExColumn(es_file);
		selectdto.setExWhereQuery("where es_idx = '"+es_idx+"'");
		
		String b_filename = Func.nvl( exdao.selectQueryColumn(selectdto) );
		
		if(!"".equals(b_filename)){
			String realpath =  fileutil.realPath(request, "/data/");
			String strDir = realpath + "/edusat/";
			String strDir2 = realpath + "/edusat/thum/";
			//삭제
			Func.fileDelete(strDir+b_filename);
			Func.fileDelete(strDir2+b_filename);
			
			//업데이트
			exdao.executeQuery("update edusat_request set "+es_file+"='' where es_idx = '"+es_idx+"'");
		}
		return "redirect:"+prepage;
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
		
		
		request.setAttribute("edusatreq", edusatreq);
		
		
		//강좌정보
		String edu_idx = exdao.filter(request.getParameter("edu_idx"));
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExColumn("edu_subject, edu_ptcp_yn, edu_ptcp_cnt, edu_field1_yn, edu_field2_yn, edu_field3_yn, edu_field4_yn, edu_field5_yn, edu_gubun, edu_temp1");
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

}
