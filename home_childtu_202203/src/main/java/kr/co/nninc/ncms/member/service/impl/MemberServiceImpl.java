package kr.co.nninc.ncms.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.FuncMember;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.crypto.service.CryptoARIAService;
import kr.co.nninc.ncms.member.service.MemberDTO;
import kr.co.nninc.ncms.member.service.MemberService;
import kr.co.nninc.ncms.member_conf.service.MemberConfDTO;

/**
 * 회원정보를 관리하기 위한 관리하기 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.08.29
 * @version 1.0
 */
@Service("memberService")
public class MemberServiceImpl extends EgovAbstractServiceImpl implements MemberService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**ARIA**/
	@Resource(name="cryptoService")
	private CryptoARIAService cryptoService;
	
	/**
	 * @title : 회원리스트
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//select전용 dto
		MemberDTO selectdto = new MemberDTO();
		
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		String v_gnum = exdao.filter(request.getParameter("v_gnum"));
		request.setAttribute("v_keyword", v_keyword);
		request.setAttribute("v_search", v_search);
		request.setAttribute("v_gnum", v_gnum);
		
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수

		selectdto.setExTableName("member_view");
		selectdto.setExColumn("lvlname, g_manager, m_num, m_id, m_pwd, m_name, m_lastdate, m_level, m_date, m_site, m_secede_date");
		String whereQuery = "";
		if(!"".equals(v_keyword) && !"".equals(v_search)){
			whereQuery +=" and "+v_search+" like '%"+v_keyword+"%' ";
		}
		if(!"".equals(v_gnum)){
			whereQuery += " and m_level = '"+v_gnum+"'";
		}
		
		HashMap<String,String>builder = (HashMap<String,String>)request.getAttribute("BUILDER");
		if(builder != null && builder.size() > 0){	//빌더정보가 있다면(빌더사이트접속이라면)
			whereQuery += " and m_level <> '1' and m_site like '%"+builder.get("bs_num")+",%'";
		}else{
			//총관리자 메뉴라면 총관리자, 사이트관리자, 정회원(도서관), 비회원, 사이트 지정안된 그룹만 조회
			whereQuery += " and (m_level in (1, 2, 3, 4) or m_level in (select g_num from member_group where g_site_dir is null))";
		}
		
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY m_num DESC");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		
		List<HashMap<String,String>> memberList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( memberList.get(0).get("totalcount") ); // 전체레코드 수
		try{
			for (HashMap<String, String> hashMap : memberList) {
				String encrypText = hashMap.get("m_name");
				if(encrypText != null && !"".equals(encrypText)) {
					encrypText = cryptoService.decryptData(encrypText);
					hashMap.put("m_name", encrypText);
				}
				
			}	
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		request.setAttribute("recordcount", recordcount);
		memberList.remove(0);	//총검색개수행(첫번째행)을 삭제
		// setAttribute
		request.setAttribute("memberList", memberList);
		
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
		
		// 회원그룹리스트조회하기(사용인것만)
		selectdto = new MemberDTO();
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_num, g_menuname");
		whereQuery = "where g_chk = 'Y'";
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(BUILDER_DIR)){	//빌더정보가 있다면(빌더사이트접속이라면)
			whereQuery += " and g_num <> 1 and ( g_num in (2, 3, 4) or g_site_dir = '"+BUILDER_DIR+"' )";
		}else{
			whereQuery += " and ( g_num in (1,2,3,4) or g_site_dir is null )";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by g_code asc");
		List<HashMap<String,String>> membergroupList = exdao.selectQueryTable(selectdto);
		
		request.setAttribute("membergroupList", membergroupList);
		
		
		//회원정보조회시 인증세션초기화
		Func.setSession(request, "ss_member_auth", "");
		
		//현재 URL 전송
		Func.getNowPage(request);
	}

	/**
	 * @title : 회원 등록폼
	 * @method : write
	 */
	@Override
	public String write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		//MemberDTO member = (MemberDTO) Func.requestAll(map.get("memberdto"));
		HashMap<String,String>member = new HashMap<String,String>();
				
		Random rand = new Random();
		String autoimg_str = Func.cStr( rand.nextInt(4) + 1 );
		
		member.put("m_sex", "1");
		member.put("m_mailing", "Y");
		member.put("m_sms", "Y");
		member.put("m_marry", "N");
		member.put("m_num", "");
		member.put("autoimg_str", autoimg_str);
		request.setAttribute("member", member);
		
		
		// 회원그룹리스트조회하기(사용인것만)
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_num, g_menuname");
		
		String where_query = "where g_chk = 'Y'";
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(BUILDER_DIR)){	//빌더정보가 있다면(빌더사이트접속이라면)
			where_query += " and g_num <> 1 and ( g_num in (2, 3, 4) or g_site_dir = '"+BUILDER_DIR+"' )";
		}else{
			where_query += " and ( g_num in (1,2,3,4) or g_site_dir is null )";
		}
		
		
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by g_code asc");
		List<HashMap<String,String>> membergroupList = exdao.selectQueryTable(selectdto);
		request.setAttribute("membergroupList", membergroupList);
		
		//회원환경설정
		selectdto = new SelectDTO();
		selectdto.setExTableName("member_config");
		selectdto.setExColumn(new MemberConfDTO());
		HashMap<String, String>memberconf = exdao.selectQueryRecord(selectdto);
		request.setAttribute("memberconf", memberconf);
		
		
		//등록된 사이트
		selectdto = new SelectDTO();
		selectdto.setExTableName("builder_site");
		selectdto.setExColumn("bs_num, bs_sitename, bs_directory, bs_main, bs_skin, bs_writer, bs_regdate, bs_chk");
		selectdto.setExOrderByQuery("order by bs_code");
		List<HashMap<String,String>>builderList = exdao.selectQueryTable(selectdto);
		request.setAttribute("builderList", builderList);
		
		//현재 URL
		Func.getNowPage(request);

		return "/ncms/member/write";
	}

	/**
	 * @return 
	 * @title : 회원 등록처리
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MemberDTO member = (MemberDTO) Func.requestAll(map.get("memberdto"));
		
		//회원환경설정(회원쿼리처리하는거때문에 다시담음)
		MemberDTO selectdto = new MemberDTO();
		selectdto.setExTableName("member_config");
		selectdto.setExColumn(new MemberConfDTO());
		selectdto.setExRecordCount(1);
		HashMap<String, String>memberconf = exdao.selectQueryRecord(selectdto);
		
		
		//파라미터 초기화
		member.setM_zipcode( Func.nvl(request.getParameter("b_zip1")) );
		member.setM_addr1( Func.nvl(request.getParameter("b_addr1")) );
		member.setM_addr2( Func.nvl(request.getParameter("b_addr2")) );
		//그룹 없으면 초기화
		if("".equals( Func.nvl(request.getParameter("m_level")) )) {
			member.setM_level(memberconf.get("mc_joinlevel"));
		}
		String m_pwd_noenc	= Func.nvl(request.getParameter("m_pwd"));	// 암호화전 비번
		String m_pwd		= Func.hash(m_pwd_noenc);
		member.setM_pwd(m_pwd);
		member.setM_date(Func.date("Y-m-d H:i:s"));
		member.setM_modymate(Func.date("Y-m-d H:i:s"));
		member.setM_ip(Func.remoteIp(request));
		member.setM_chuchun("0");
		member.setM_incorrect("0");
		member.setM_point("0");
		
		//사용하지 않는 필드는 insert 하지 않는다
		if(!"Y".equals(memberconf.get("mc_nickname"))){member.setM_nickname(null);}
		if(!"Y".equals(memberconf.get("mc_addr"))){
			member.setM_zipcode(null);
			member.setM_addr1(null);
			member.setM_addr2(null);
		}
		if(!"Y".equals(memberconf.get("mc_phone"))){
			member.setM_phone1(null);
			member.setM_phone2(null);
			member.setM_phone3(null);
		}
		if(!"Y".equals(memberconf.get("mc_email"))){member.setM_email(null);}
		if(!"Y".equals(memberconf.get("mc_mobile"))){
			member.setM_mobile1(null);
			member.setM_mobile2(null);
			member.setM_mobile3(null);
		}
		if(!"Y".equals(memberconf.get("mc_fax"))){
			member.setM_fax1(null);
			member.setM_fax2(null);
			member.setM_fax3(null);
		}
		if(!"Y".equals(memberconf.get("mc_sex"))){member.setM_sex(null);}
		if(!"Y".equals(memberconf.get("mc_birth"))){member.setM_birth(null);}
		if(!"Y".equals(memberconf.get("mc_sms"))){member.setM_sms(null);}
		if(!"Y".equals(memberconf.get("mc_mailing"))){member.setM_mailing(null);}
		if(!"Y".equals(memberconf.get("mc_marry"))){member.setM_marry(null);}
		if(!"Y".equals(memberconf.get("mc_marrydate"))){member.setM_marrydate(null);}
		if(!"Y".equals(memberconf.get("mc_job"))){member.setM_job(null);}
		if(!"Y".equals(memberconf.get("mc_text"))){member.setM_text(null);}
		if(!"Y".equals(memberconf.get("mc_homepage"))){member.setM_homepage(null);}
		
		member.setM_num(null);	//insert시 m_num은 처리하지 않는다(자동생성)
		
		
		//자동가입방지 체크
		//*****************************************
		String autoimg_str		= Func.nvl(request.getParameter("autoimg_str"));
		String autoimg_chk		= "";
		String m_autoimg_str	= Func.nvl(request.getParameter("m_autoimg_str"));
		
		switch ( Func.cInt(autoimg_str) ){
			case 1  : autoimg_chk = "DF4PH3"; break;
			case 2  : autoimg_chk = "HS42YP"; break;
			case 3  : autoimg_chk = "S8VP3X"; break;
			case 4  : autoimg_chk = "N4CW2U"; break;
			case 5  : autoimg_chk = "9SK6GR"; break;
		}
		if( !autoimg_chk.equals( m_autoimg_str.toUpperCase() ) ){
			return messageService.backMsg(model, "자동가입방지입력값이 동일하지 않습니다.");
		}
		//*****************************************
		//동일아이디 체크
		//*****************************************
		String m_id = member.getM_id();
		selectdto = new MemberDTO();
		selectdto.setExTableName("member");
		selectdto.setExColumn("count(*)");
		selectdto.setExWhereQuery("where m_id = '"+exdao.filter( m_id )+"'");
		int rs_id_chk = Func.cInt( exdao.selectQueryColumn(selectdto) );
		if (rs_id_chk > 0) {
			return messageService.backMsg(model, "사용할 수 없는 아이디입니다.");
		}
		//*****************************************
		
		
		//m_site 세팅
		HashMap<String, String>builder = (HashMap<String, String>)request.getAttribute("BUILDER");
		if(builder != null && builder.size() > 0){	//사이트관리자에서 신규등록시 사이트키를 저장
			member.setM_site( builder.get("bs_num") );
		}
		if(member.getM_site() != null){	//사이트별 회원검색을 위한 처리
			member.setM_site( member.getM_site()+"," );
		}
		
		// ARIA 암호화(이름, 이메일, 휴대폰, 주소, 직업), encrypText
		String encrypText = "";
		if(member.getM_name() != null && !"".equals(member.getM_name())) {
			encrypText = cryptoService.encryptData(member.getM_name());
			member.setM_name(encrypText);
		}
		if(member.getM_email() != null && !"".equals(member.getM_email())) {
			encrypText = cryptoService.encryptData(member.getM_email());
			member.setM_email(encrypText);	
		}
		
		if(member.getM_mobile1() != null && !"".equals(member.getM_mobile1())) {
			encrypText = cryptoService.encryptData(member.getM_mobile1());
			member.setM_mobile1(encrypText);
			encrypText = cryptoService.encryptData(member.getM_mobile2());
			member.setM_mobile2(encrypText);
			encrypText = cryptoService.encryptData(member.getM_mobile3());
			member.setM_mobile3(encrypText);	
		}
		
		if(member.getM_zipcode() != null && !"".equals(member.getM_zipcode())) {
			encrypText = cryptoService.encryptData(member.getM_zipcode());
			member.setM_zipcode(encrypText);
			encrypText = cryptoService.encryptData(member.getM_addr1());
			member.setM_addr1(encrypText);
			encrypText = cryptoService.encryptData(member.getM_addr2());
			member.setM_addr2(encrypText);
		}
		
		if(member.getM_job() != null && !member.getM_job().equals("")) {
			encrypText = cryptoService.encryptData(member.getM_job());
			member.setM_job(encrypText);
		}
		
		
		//등록하기
		member.setExTableName("member");
		exdao.insert(member);
		
		
		//작업기록
		Func.writeManagerLog ("회원등록  ["+member.getM_name()+"("+member.getM_id()+")]", request);
		return "redirect:list.do";
	}

	/**
	 * @return 
	 * @title : 회원 정보수정
	 * @method : modify
	 */
	@Override
	public String modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String user = (String) map.get("user");
		
		String m_num = exdao.filter(request.getParameter("m_num"));
		if("Y".equals(user)) {
			m_num = Func.getSession(request, "ss_m_num");
		}else {
			//관리자였을때
			String auth_pwd = Func.nvl(request.getParameter("auth_pwd"));
			//if(!"Y".equals(Func.getSession(request, "ss_member_auth_"+m_num))) {
			if(!"Y".equals(Func.getSession(request, "ss_member_auth"))) {
				if ("".equals(auth_pwd)){
					return messageService.redirectMsg(model, "", "./modify3.do?m_num="+m_num);
				}else {
					if (FuncMember.memgr_idpwdchk2(Func.getSession(request, "ss_m_id"), auth_pwd)) {	//관리자 로그인
						//Func.setSession(request, "ss_member_auth_"+m_num, "Y");
						Func.setSession(request, "ss_member_auth", "Y");
					}else {
						return messageService.backMsg(model, "비밀번호가 틀렸습니다. 재인증해주세요.");
					}
				}
			}
		}
		
		// 회원상세보기
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("member");
		selectdto.setExKeyColumn("m_num");
		selectdto.setExColumn(new MemberDTO());
		selectdto.setExWhereQuery("where m_num = '"+m_num+"'");
		HashMap<String, String>member = exdao.selectQueryRecord(selectdto);

		// Aria
		String encrypText = "";
		/*
		encrypText = member.get("m_name");
		encrypText = cryptoService.decryptData(encrypText);
		member.put("m_name", encrypText);
		encrypText = member.get("m_email");
		encrypText = cryptoService.decryptData(encrypText);
		member.put("m_email", encrypText);
		encrypText = member.get("m_zipcode");
		encrypText = cryptoService.decryptData(encrypText);
		member.put("m_zipcode", encrypText);
		encrypText = member.get("m_addr1");
		encrypText = cryptoService.decryptData(encrypText);
		member.put("m_addr1", encrypText);
		encrypText = member.get("m_addr2");
		encrypText = cryptoService.decryptData(encrypText);
		member.put("m_addr2", encrypText);
		encrypText = member.get("m_job");
		if(encrypText != null && !"".equals(encrypText)) {
			encrypText = cryptoService.decryptData(encrypText);
			member.put("m_job", encrypText);	
		}
		encrypText = member.get("m_mobile1");
		if(encrypText != null && !"".equals(encrypText)) {
			encrypText = cryptoService.decryptData(encrypText);
			member.put("m_mobile1", encrypText);	
		}
		encrypText = member.get("m_mobile2");
		if(encrypText != null && !"".equals(encrypText)) {
			encrypText = cryptoService.decryptData(encrypText);
			member.put("m_mobile2", encrypText);	
		}
		encrypText = member.get("m_mobile3");
		if(encrypText != null && !"".equals(encrypText)) {
			encrypText = cryptoService.decryptData(encrypText);
			member.put("m_mobile3", encrypText);	
		}
		*/
		
		String aria_str = "m_name,m_email,m_zipcode,m_addr1,m_addr2,m_job,m_mobile1,m_mobile2,m_mobile3";
		String[] aria_arr = aria_str.replaceAll(" ", "").split(",");
		for (String string : aria_arr) {
			if(member.get(string) != null && !"".equals(member.get(string))) {
				//System.out.println(string);
				encrypText = cryptoService.decryptData(member.get(string));
				member.put(string, encrypText);
			}	
		}
		
		
		request.setAttribute("member", member);
		
		// 회원그룹리스트조회하기(사용인것만)
		selectdto = new SelectDTO();
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_num, g_menuname");
		String where_query = "where g_chk = 'Y'";
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(BUILDER_DIR)){	//빌더정보가 있다면(빌더사이트접속이라면)
			where_query += " and g_num <> 1 and ( g_num in (2, 3, 4) or g_site_dir = '"+BUILDER_DIR+"' )";
		}else{
			where_query += " and ( g_num in (1,2,3,4) or g_site_dir is null )";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by g_code asc");
		List<HashMap<String,String>> membergroupList = exdao.selectQueryTable(selectdto);
		request.setAttribute("membergroupList", membergroupList);
		
		//회원환경설정(회원쿼리처리하는거때문에 다시담음)
		selectdto = new SelectDTO();
		selectdto.setExTableName("member_config");
		selectdto.setExColumn(new MemberConfDTO());
		HashMap<String, String>memberconf = exdao.selectQueryRecord(selectdto);
		request.setAttribute("memberconf", memberconf);
		
		
		//등록된 사이트
		selectdto = new SelectDTO();
		selectdto.setExTableName("builder_site");
		selectdto.setExColumn("bs_num, bs_sitename, bs_directory, bs_main, bs_skin, bs_writer, bs_regdate, bs_chk");
		selectdto.setExOrderByQuery("order by bs_code");
		List<HashMap<String,String>>builderList = exdao.selectQueryTable(selectdto);
		
		//체크된 사이트
		String m_site = Func.nvl(member.get("m_site")).trim();
		if(!"".equals(m_site)){
			m_site = m_site.replaceAll(" ", "");
			String[] m_site_arr = m_site.split(",");
			for(int i=0;i<=m_site_arr.length-1;i++){
				String m_site_str = m_site_arr[i];
				
				for(int j=0;j<=builderList.size()-1;j++){
					HashMap<String,String>builder = builderList.get(j);
					if(m_site_str.equals( builder.get("bs_num") )){
						builder.put("checked", "checked");
						break;
					}
				}
			}
		}
		request.setAttribute("builderList", builderList);
		
		
		//작업기록
		Func.writeManagerLog ("회원정보 조회 ["+member.get("m_name")+"("+member.get("m_id")+")]", request);
				
		//현재 URL
		Func.getNowPage(request);
	
		
		if("Y".equals(user)) {
			return "/site/member/modify";
		}else {
			return "/ncms/member/write";
		}
	}

	/**
	 * @title : 회원 수정처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MemberDTO member = (MemberDTO) map.get("memberdto");
		String user = (String) map.get("user");
		
		String m_num = exdao.filter(request.getParameter("m_num"));
		String prepage = Func.nvl(request.getParameter("prepage"));
		if("".equals(prepage)){prepage = "/ncms/member/write.do?m_num="+m_num;}
		
		//select 전용 dto
		MemberDTO selectdto = new MemberDTO();
		
		//비밀번호 확인
		if("Y".equals(user)) {
			String m_pwd_now = Func.nvl(request.getParameter("m_pwd_now"));
			if("".equals(m_pwd_now)) {
				return messageService.backMsg(model, "현재 비밀번호를 입력해주세요.");
			}
			//m_pwd_now
			selectdto.setExTableName("member_view");
			selectdto.setExColumn("count(*)");
			selectdto.setExWhereQuery("where m_num = '"+m_num+"' and m_pwd = '"+Func.hash(m_pwd_now)+"'");
			int cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
			if(cnt == 0) {
				return messageService.backMsg(model, "현재 비밀번호가 일치하지 않습니다");
			}
			String m_pwd = Func.nvl(request.getParameter("m_pwd"));
			String m_pwdchk = Func.nvl(request.getParameter("m_pwdchk"));
			if(!"".equals(m_pwd) && !m_pwd.equals(m_pwdchk)) {
				return messageService.backMsg(model, "비밀번호를 다시 확인하세요");
			}
		}else {
			//Func.setSession(request, "ss_member_auth_"+m_num, "");
		}
		
		//회원환경설정(회원쿼리처리하는거때문에 다시담음)
		selectdto = new MemberDTO();
		selectdto.setExTableName("member_config");
		selectdto.setExColumn(new MemberConfDTO());
		HashMap<String, String>memberconf = exdao.selectQueryRecord(selectdto);
		
		//파라미터 초기화
		member.setM_zipcode( Func.nvl(request.getParameter("b_zip1")) );
		member.setM_addr1( Func.nvl(request.getParameter("b_addr1")) );
		member.setM_addr2( Func.nvl(request.getParameter("b_addr2")) );

		String m_pwd_noenc = "";
		String m_pwd = "";
		if("".equals( member.getM_pwd() )){
			member.setM_pwd(null);
		}else{
			m_pwd_noenc	= member.getM_pwd();	// 암호화전 비번
			m_pwd		= Func.hash(m_pwd_noenc);
			member.setM_pwd(m_pwd);
		}
		member.setM_modymate(Func.date("Y-m-d H:i:s"));
		member.setM_ip(Func.remoteIp(request));
		member.setM_incorrect("0");
		
		//사용하지 않는 필드는 insert 하지 않는다
		if(!"Y".equals(memberconf.get("mc_nickname"))){member.setM_nickname(null);}
		if(!"Y".equals(memberconf.get("mc_addr"))){
			member.setM_zipcode(null);
			member.setM_addr1(null);
			member.setM_addr2(null);
		}
		if(!"Y".equals(memberconf.get("mc_phone"))){
			member.setM_phone1(null);
			member.setM_phone2(null);
			member.setM_phone3(null);
		}
		if(!"Y".equals(memberconf.get("mc_email"))){member.setM_email(null);}
		if(!"Y".equals(memberconf.get("mc_mobile"))){
			member.setM_mobile1(null);
			member.setM_mobile2(null);
			member.setM_mobile3(null);
		}
		if(!"Y".equals(memberconf.get("mc_fax"))){
			member.setM_fax1(null);
			member.setM_fax2(null);
			member.setM_fax3(null);
		}
		if(!"Y".equals(memberconf.get("mc_sex"))){member.setM_sex(null);}
		if(!"Y".equals(memberconf.get("mc_birth"))){member.setM_birth(null);}
		if(!"Y".equals(memberconf.get("mc_sms"))){member.setM_sms(null);}
		if(!"Y".equals(memberconf.get("mc_mailing"))){member.setM_mailing(null);}
		if(!"Y".equals(memberconf.get("mc_marry"))){member.setM_marry(null);}
		if(!"Y".equals(memberconf.get("mc_marrydate"))){member.setM_marrydate(null);}
		if(!"Y".equals(memberconf.get("mc_job"))){member.setM_job(null);}
		if(!"Y".equals(memberconf.get("mc_text"))){member.setM_text(null);}
		if(!"Y".equals(memberconf.get("mc_homepage"))){member.setM_homepage(null);}
		
		member.setM_num(null);	//update시 m_num은 처리하지 않는다
		
		//m_site 세팅
		if(member.getM_site() != null){	//사이트별 회원조회를 위한 처리
			member.setM_site( member.getM_site()+"," );
		}
		
		
		/************ 처리하기전 수정항목 체크 *********************/
		//수정항목체크위해 기존회원디비 조회
		selectdto = new MemberDTO();
		selectdto.setExTableName("member");
		selectdto.setExKeyColumn("m_num");
		selectdto.setExColumn(new MemberDTO());
		selectdto.setExWhereQuery("where m_num = '"+m_num+"'");
		HashMap<String,String>memberchk = exdao.selectQueryRecord(selectdto);
		String mod_field = "";
		if(!Func.nvl(member.getM_pwd()).equals("") && !Func.nvl(member.getM_pwd()).equals(Func.hash(memberchk.get("m_pwd"))) ){
			mod_field += ", 비밀번호";
		}
		if(!Func.nvl(member.getM_name()).equals("") && !Func.nvl(member.getM_name()).equals(memberchk.get("m_name")) ){
			mod_field += ", 이름";
		}
		if(!Func.nvl(member.getM_zipcode()).equals("") && !Func.nvl(member.getM_zipcode()).equals(memberchk.get("m_zipcode")) ){
			mod_field += ", 주소";
		}
		if(!Func.nvl(member.getM_phone1()).equals("") && !Func.nvl(member.getM_phone1()).equals(memberchk.get("m_phone1")) ){
			mod_field += ", 전화번호(국번)";
		}
		if(!Func.nvl(member.getM_phone2()).equals("") && !Func.nvl(member.getM_phone2()).equals(memberchk.get("m_phone2")) ){
			mod_field += ", 전화번호(두번째자리)";
		}
		if(!Func.nvl(member.getM_phone3()).equals("") && !Func.nvl(member.getM_phone3()).equals(memberchk.get("m_phone3")) ){
			mod_field += ", 전화번호(세번째자리)";
		}
		if(!Func.nvl(member.getM_email()).equals("") && !Func.nvl(member.getM_email()).equals(memberchk.get("m_email")) ){
			mod_field += ", 이메일";
		}
		if(!Func.nvl(member.getM_mobile1()).equals("") && !Func.nvl(member.getM_mobile1()).equals(memberchk.get("m_mobile1")) ){
			mod_field += ", 휴대전화(국번)";
		}
		if(!Func.nvl(member.getM_mobile2()).equals("") && !Func.nvl(member.getM_mobile2()).equals(memberchk.get("m_mobile2")) ){
			mod_field += ", 휴대전화(두번째자리)";
		}
		if(!Func.nvl(member.getM_mobile3()).equals("") && !Func.nvl(member.getM_mobile3()).equals(memberchk.get("m_mobile3")) ){
			mod_field += ", 휴대전화(세번째자리)";
		}
		if(!Func.nvl(member.getM_sex()).equals("") && !Func.nvl(member.getM_sex()).equals(memberchk.get("m_sex")) ){
			mod_field += ", 성별";
		}
		if(!Func.nvl(member.getM_birth()).equals("") && !Func.nvl(member.getM_birth()).equals(memberchk.get("m_birth")) ){
			mod_field += ", 생년월일";
		}
		if(!Func.nvl(member.getM_sms()).equals("") && !Func.nvl(member.getM_sms()).equals(memberchk.get("m_sms")) ){
			mod_field += ", SMS수신여부("+member.getM_sms()+")";
		}
		if(!Func.nvl(member.getM_mailing()).equals("") && !Func.nvl(member.getM_mailing()).equals(memberchk.get("m_mailing")) ){
			mod_field += ", 메일수신여부("+member.getM_mailing()+")";
		}
		if(!Func.nvl(member.getM_level()).equals("") && !Func.nvl(member.getM_level()).equals(memberchk.get("m_level")) ){
			String m_levelnm1 = exdao.selectQueryColumn("g_menuname", "member_group", "where g_num = '"+memberchk.get("m_level")+"'");
			String m_levelnm2 = exdao.selectQueryColumn("g_menuname", "member_group", "where g_num = '"+member.getM_level()+"'");
			mod_field += ", 회원그룹("+m_levelnm1+"&gt;"+m_levelnm2+")";
		}
		
		/************ 처리하기전 수정항목 체크 끝 *********************/
		
		// ARIA 암호화(이름, 이메일, 휴대폰, 주소, 직업), encrypText
		
		String encrypText = "";
		encrypText = member.getM_name();
		if(encrypText != null && !"".equals(encrypText)) {
			member.setM_name(cryptoService.encryptData(encrypText));
		}
		encrypText = member.getM_email();
		if(encrypText != null && !"".equals(encrypText)) {
			member.setM_email(cryptoService.encryptData(encrypText));
		}
		encrypText = member.getM_phone1();
		if(encrypText != null && !"".equals(encrypText)) {
			member.setM_phone1(cryptoService.encryptData(encrypText));
			encrypText = member.getM_phone2();
			member.setM_phone2(cryptoService.encryptData(encrypText));
			encrypText = member.getM_phone3();
			member.setM_phone3(cryptoService.encryptData(encrypText));
		}
		encrypText = member.getM_zipcode();
		if(encrypText != null && !"".equals(encrypText)) {
			member.setM_zipcode(cryptoService.encryptData(encrypText));
			encrypText = member.getM_addr1();
			member.setM_addr1(cryptoService.encryptData(encrypText));
			encrypText = member.getM_addr2();
			member.setM_addr2(cryptoService.encryptData(encrypText));
		}
		if(member.getM_job() != null && !member.getM_job().equals("")) {
			encrypText = cryptoService.encryptData(member.getM_job());
			member.setM_job(encrypText);
		}
		
		//수정하기
		member.setExTableName("member");
		member.setExWhereQuery("where m_num = '"+m_num+"'");
		exdao.update(member);
		
		//작업기록
		if(!"".equals(mod_field)){
			if(",".equals(mod_field.substring(0, 1))){
				mod_field = " - "+mod_field.substring(1, mod_field.length());
			}
		}
		Func.writeManagerLog ("회원정보 수정 ["+memberchk.get("m_name")+"("+memberchk.get("m_id")+")"+mod_field+"]", request);
		
		return messageService.redirectMsg(model, "수정 되었습니다.", prepage);
	}

	/**
	 * @title : 회원 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 		= Func.nvl( request.getParameter("status") ); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		
		String m_num		= Func.nvl( request.getParameter("m_num") );  //단일 삭제 사용
		
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				MemberDTO selectdto = new MemberDTO();
				selectdto.setExTableName("member");
				selectdto.setExColumn("m_name, m_id");
				selectdto.setExWhereQuery("where m_num = '"+exdao.filter(chk[z])+"'");
				HashMap<String,String>rs = exdao.selectQueryRecord(selectdto);
				if(rs.size() > 0){
					
					String sql = "DELETE FROM member WHERE m_num= '"+exdao.filter(chk[z])+"'";
					exdao.executeQuery(sql);
					
					String m_name = rs.get("m_name");
					String m_id = rs.get("m_id");
					
					//작업기록
					Func.writeManagerLog ("회원정보 삭제 ["+m_name+"("+m_id+")]", request);
				}
			}
		}else{
			MemberDTO selectdto = new MemberDTO();
			selectdto.setExTableName("member");
			selectdto.setExColumn("m_name, m_id");
			selectdto.setExWhereQuery("where m_num = '"+m_num+"'");
			HashMap<String,String>rs = exdao.selectQueryRecord(selectdto);
			if(rs.size() > 0){
				
				String sql = "DELETE FROM member WHERE m_num= '"+exdao.filter(m_num)+"'";
				exdao.executeQuery(sql);
				
				String m_name = rs.get("m_name");
				String m_id = rs.get("m_id");
				
				//작업기록
				Func.writeManagerLog ("회원정보 삭제 ["+m_name+"("+m_id+")]", request);
			}
			
		}

	}

	/**
	 * @title : 회원그룹변경
	 * @method : levelOk
	 **/
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 		= Func.nvl( request.getParameter("status") ); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		
		String tot_m_level = exdao.filter(request.getParameter("tot_m_level"));
		if (status.equals("totlevel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				String sql = "UPDATE member SET m_level = '"+tot_m_level+"' WHERE m_num='"+exdao.filter(chk[z])+"'";
				exdao.executeQuery(sql);
			}
		}

	}

}
