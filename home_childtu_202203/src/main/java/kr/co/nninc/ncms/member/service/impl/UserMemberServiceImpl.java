package kr.co.nninc.ncms.member.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.FuncMember;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.crypto.service.CryptoARIAService;
import kr.co.nninc.ncms.member.service.MemberDTO;
import kr.co.nninc.ncms.member.service.UserMemberService;
import kr.co.nninc.ncms.member_conf.service.MemberConfDTO;


/**
 * 회원가입, 정보수정, 탈퇴 처리 클래스
 * 
 * @author 나눔
 * @since 2017.08.29
 * @version 1.0
 */
@Service("userMemberService")
public class UserMemberServiceImpl implements UserMemberService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**ARIA 암호화*/
	@Resource(name="cryptoService")
	private CryptoARIAService cryptoService;
	
	/** FileUtil */
	@Resource(name = "fileutil")
	private FileUtil fileutil;
	
	/**
	 * @title : 회원정보수정폼
	 * @method : modify
	 */
	@Override
	public String modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String m_num = Func.getSession(request, "ss_m_num");
		
		String cancel_url = "/";
		String login_url = "/site/member/login.do?prepage="+Func.urlEncode( Func.getNowPage(request) );
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(BUILDER_DIR)){
			cancel_url = "/"+BUILDER_DIR+cancel_url;
			login_url = "/"+BUILDER_DIR+login_url;
		}
		if("".equals(m_num)){
			return messageService.redirectMsg(model, "로그인후 사용하세요.", login_url);
		}
		
		
		//비번인증
		String ps_value = Func.nvl( request.getParameter("ps_value") ).trim();
		if("".equals(ps_value)){ps_value = Func.getSession(request, "ss_ps_value");}
		if("".equals(ps_value)){
			String action_url = Func.getNowPage(request);
			request.setAttribute("action_url", action_url);
			return "/site/member/pwdConfirm";
		}else if(!ps_value.equals(Func.getSession(request, "ss_m_pwd"))){
			return messageService.backMsg(model, "비밀번호를 다시 확인하세요.");
		}
		//세션변수는 1회용으로 사용
		Func.setSession(request, "ss_ps_value", "");
		
		
		SelectDTO selectdto = new SelectDTO();
		
		selectdto.setExTableName("member");
		selectdto.setExKeyColumn("m_num");
		selectdto.setExColumn(new MemberDTO());
		selectdto.setExWhereQuery("where m_num = '"+m_num+"'");
		HashMap<String,String>member = exdao.selectQueryRecord(selectdto);
		if(member.size() == 0){
			return messageService.backMsg(model, "회원정보 조회 실패");
		}
		
		//ARIA
		if(!Func.getSession(request, "ss_m_id").equals("nninc")) {
			String decrypText = "";
			String aria_str = "m_email,m_zipcode,m_addr1,m_addr2,m_job";
			String[] aria_arr = aria_str.replaceAll(" ", "").split(",");
			for (String string : aria_arr) {
				if(member.get(string) != null && !"".equals(member.get(string))) {
					decrypText = cryptoService.decryptData(member.get(string));
					member.put(string, decrypText);
				}
			}	
		}
		
		request.setAttribute("member", member);
		
		//회원환경설정
		selectdto = new SelectDTO();
		selectdto.setExTableName("member_config");
		selectdto.setExColumn(new MemberConfDTO());
		HashMap<String, String>memberconf = exdao.selectQueryRecord(selectdto);
		request.setAttribute("memberconf", memberconf);
		
		
		return "/site/member/regist";
	}

	/**
	 * @title : 회원정보수정 처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MemberDTO member = (MemberDTO) Func.requestAll(map.get("memberdto"));
		
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		String prepage = Func.nvl(request.getParameter("prepage")).trim();
		if("".equals(prepage)){
			prepage = "/";
			if(!"".equals(BUILDER_DIR)){
				prepage = "/"+BUILDER_DIR+prepage;
			}
		}
		
		
		String m_num = Func.getSession(request, "ss_m_num");
		
		String cancel_url = "/";
		if(!"".equals(BUILDER_DIR)){
			cancel_url = "/"+BUILDER_DIR+cancel_url;
		}
		if("".equals(m_num)){
			return messageService.redirectMsg(model, "로그인후 사용하세요.", cancel_url);
		}
		
		
		//동일메일주소 체크
		//*****************************************
		String m_email = exdao.filter( request.getParameter("m_email") );
		if(!"".equals(m_email)){
			int mail_cnt = Func.cInt( exdao.selectQueryColumn("count(*)", "member", "where m_id <> '"+Func.getSession(request, "ss_m_id")+"' and m_email = '"+m_email+"'") );
			if(mail_cnt > 0){
				return messageService.backMsg(model, "동일한 이메일주소가 존재합니다.");
			}
		}
		//*****************************************
		
		
		member.setM_modymate(Func.date("Y-m-d H:i:s"));
		
		//수정제외 처리
		member = (MemberDTO)exdao.emptyToNull(member);
		if(member.getM_homepage() == null){member.setM_homepage("");}
		if(member.getM_job() == null){member.setM_job("");}
		if(member.getM_nickname() == null){member.setM_nickname("");}
		if(member.getM_text() == null){member.setM_text("");}
		member.setM_dupinfo(null);
		member.setM_coinfo(null);
		member.setM_id(null);
		member.setM_num(null);
		member.setM_name(null);
		member.setM_email(null);
		// ARIA
		if(!Func.getSession(request, "ss_m_id").equals("nninc")) {
			String encrypText = "";
			encrypText = cryptoService.encryptData(member.getM_zipcode());
			member.setM_zipcode(encrypText);
			encrypText = cryptoService.encryptData(member.getM_addr1());
			member.setM_addr1(encrypText);
			encrypText = cryptoService.encryptData(member.getM_addr2());
			member.setM_addr2(encrypText);
			if(member.getM_job() != null && !member.getM_job().equals("")) {
				encrypText = cryptoService.encryptData(member.getM_job());
				member.setM_job(encrypText);
			}
		}
		
		//수정처리
		member.setExTableName("member");
		member.setExWhereQuery("where m_num = '"+m_num+"'");
		exdao.update(member);
		
		//1회용 세션변수 생성
		Func.setSession(request, "ss_ps_value", Func.getSession(request, "ss_m_pwd"));
		
		//작업기록
		Func.writeMemberLog("회원정보수정  ["+member.getM_name()+"("+member.getM_id()+")]", request);
		
		return messageService.redirectMsg(model, "회원정보가 수정되었습니다.", prepage);
	}

	
	/**
	 * @title : 회원탈퇴 처리
	 * @method : secedeOk
	 */
	@Override
	public String secedeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String m_secede_reason = exdao.filter(request.getParameter("m_secede_reason"));
		String m_id = Func.getSession(request, "ss_m_id");
		
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		String main_url = "/";
		if(!"".equals(BUILDER_DIR)){
			main_url = "/"+BUILDER_DIR+main_url;
		}
		
		if("".equals(m_id)){
			return messageService.redirectMsg(model, "로그인후 사용하세요.", main_url);
		}
		//$sql = " UPDATE member SET m_pwd='', m_nickname='', m_jumin='', m_zipcode='', m_addr1='', m_addr2='', m_phone1='', m_phone2='' ";
		//$sql = $sql . " , m_phone3='', m_email='', m_mobile1='', m_mobile2='', m_mobile3='', m_fax1='', m_fax2='', m_fax3='' ";
		//$sql = $sql . " , m_homepage='', m_temp1='N', m_temp2='N', m_point='0', m_level ='2' ";
		//$sql = $sql . " , m_secede_reason='".$secede_reason."' , m_secede_date='".date("Y-m-d H:i:s")."'  ";

		MemberDTO member = new MemberDTO();
		member.setM_pwd("");
		member.setM_nickname("");
		member.setM_zipcode("");
		member.setM_addr1("");
		member.setM_addr2("");
		member.setM_phone1("");
		member.setM_phone2("");
		member.setM_phone3("");
		member.setM_email("");
		member.setM_mobile1("");
		member.setM_mobile2("");
		member.setM_mobile3("");
		member.setM_fax1("");
		member.setM_fax2("");
		member.setM_fax3("");
		member.setM_level("2");
		member.setM_secede_reason(m_secede_reason);
		member.setM_secede_date(Func.date("Y-m-d H:i:s"));
		
		member.setExTableName("member");
		member.setExWhereQuery("where m_id = '"+m_id+"'");
		exdao.update(member);
		
		FuncMember.memgr_logout(request);
		
		return messageService.redirectMsg(model, "정상적으로 탈퇴 처리 되었습니다.", main_url);
		
	}

	
	/**
	 * @title : 비밀번호변경 처리
	 * @method : pwdChangeOk
	 */
	@Override
	public String pwdChangeOk(String m_id, String m_pwd) throws Exception {
		
		String result = pwdRule(m_pwd);
		if(!"ok".equals(result)){
			return result;
		}
		
		int cnt = Func.cInt( exdao.selectQueryColumn("count(*) as cnt", "member", "where m_id = '"+exdao.filter(m_id)+"'") );
		if(cnt == 0){
			return "nodata";
		}
		
		String m_pwd_enc = Func.hash(m_pwd);
		String sql = "update member set m_pwd = '"+m_pwd_enc+"' where m_id = '"+exdao.filter(m_id)+"'";
		
		try{
			exdao.executeQuery(sql);
		}catch(Exception e){
			result = "비밀번호 저장중 오류가 발생하였습니다.";
		}
		
		return result;
	}

	/**
	 * @title : 회원 등록폼
	 * @method : regist
	 */
	@Override
	public String regist(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		//MemberDTO member = (MemberDTO) Func.requestAll(map.get("memberdto"));
		HashMap<String,String>member = new HashMap<String,String>();
		
		if(!"".equals(Func.getSession(request, "ss_m_id"))){
			return messageService.redirectMsg(model, "", "./modify.do");
		}

		SelectDTO selectdto = new SelectDTO();
		
		if(!"".equals( Func.getSession(request, "ss_m_dupinfo") )){
			//본인인증정보 맵생성
			HashMap<String, String> certMap = Func.createCertMap(request);
			request.setAttribute("certMap", certMap);
			
			//중복확인코드체크
			int mcnt = Func.cInt( exdao.selectQueryColumn("count(*)", "member", "where m_dupinfo = '"+Func.getSession(request, "ss_m_dupinfo")+"'") );
			if(mcnt > 0){
				return messageService.backMsg(model, "동일한 본인인증정보가 존재합니다.");
			}
		}
				
				
		
		
		Random rand = new Random();
		String autoimg_str = Func.cStr( rand.nextInt(4) + 1 );
		
		member.put("m_sex", "1");
		member.put("m_mailing", "Y");
		member.put("m_sms", "Y");
		member.put("m_marry", "N");
		member.put("m_num", "");
		member.put("autoimg_str", autoimg_str);
		request.setAttribute("member", member);
		
		//회원환경설정
		selectdto = new SelectDTO();
		selectdto.setExKeyColumn("mc_idx");
		selectdto.setExTableName("member_config");
		selectdto.setExColumn(new MemberConfDTO());
		HashMap<String, String>memberconf = exdao.selectQueryRecord(selectdto);
		request.setAttribute("memberconf", memberconf);
		
		
		//현재 URL 전송
		Func.getNowPage(request);
		return "/site/member/regist";
	}

	/**
	 * @title : 회원 등록처리
	 * @method : regist
	 */
	@Override
	public String registOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MemberDTO member = (MemberDTO) Func.requestAll(map.get("memberdto"));
		
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		String prepage = Func.nvl(request.getParameter("prepage")).trim();
		if("".equals(prepage)){
			prepage = "/";
			if(!"".equals(BUILDER_DIR)){
				prepage = "/"+BUILDER_DIR+prepage;
			}
		}
		
		SelectDTO selectdto = new SelectDTO();
		
		//본인인증정보
		//*****************************************
		String m_dupinfo = Func.nvl( request.getParameter("m_dupinfo") ).trim();
		if(!"".equals( m_dupinfo )){
			//중복확인코드체크
			int mcnt = Func.cInt( exdao.selectQueryColumn("count(*)", "member", "where m_dupinfo = '"+m_dupinfo+"'") );
			if(mcnt > 0){
				return messageService.backMsg(model, "동일한 본인인증정보가 존재합니다.");
			}
		}
		//*****************************************
		
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
			System.out.println("auto_chk="+autoimg_chk+","+m_autoimg_str.toUpperCase());
			return messageService.backMsg(model, "자동가입방지입력값이 동일하지 않습니다.");
		}
		//*****************************************
		
		//동일아이디 체크
		//*****************************************
		int rs_id_chk = this.idCheck(model);
		if (rs_id_chk > 0) {
			return messageService.backMsg(model, "동일한 아이디가 존재합니다.");
		}
		//*****************************************
		
		//동일메일주소 체크
		//*****************************************
		String m_id = exdao.filter( request.getParameter("m_id") );
		String m_email = exdao.filter( request.getParameter("m_email") );
		if(!"".equals(m_email)){
			int mail_cnt = Func.cInt( exdao.selectQueryColumn("count(*)", "member", "where m_email = '"+m_email+"'") );
			if(mail_cnt > 0){
				return messageService.backMsg(model, "동일한 이메일주소가 존재합니다.");
			}
		}
		//*****************************************
		
		
		//m_site 세팅
		HashMap<String, String>builder = (HashMap<String, String>)request.getAttribute("BUILDER");
		if(builder != null && builder.size() > 0){	//사이트관리자에서 신규등록시 사이트키를 저장
			member.setM_site( builder.get("bs_num") );
		}
		if(member.getM_site() != null){	//사이트별 회원검색을 위한 ','처리
			member.setM_site( member.getM_site()+"," );
		}
		
		
		//회원환경설정(회원쿼리처리하는거때문에 다시담음)
		selectdto.setExTableName("member_config");
		selectdto.setExColumn(new MemberConfDTO());
		HashMap<String, String>memberconf = exdao.selectQueryRecord(selectdto);
		
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
		
		// ARIA 암호화(이름, 이메일, 휴대폰, 주소, 직업), encrypText
		String encrypText = "";
		encrypText = cryptoService.encryptData(member.getM_name());
		member.setM_name(encrypText);
		encrypText = cryptoService.encryptData(member.getM_email());
		member.setM_email(encrypText);
		encrypText = cryptoService.encryptData(member.getM_phone1());
		member.setM_phone1(encrypText);
		encrypText = cryptoService.encryptData(member.getM_phone2());
		member.setM_phone2(encrypText);
		encrypText = cryptoService.encryptData(member.getM_phone3());
		member.setM_phone3(encrypText);
		encrypText = cryptoService.encryptData(member.getM_zipcode());
		member.setM_zipcode(encrypText);
		encrypText = cryptoService.encryptData(member.getM_addr1());
		member.setM_addr1(encrypText);
		encrypText = cryptoService.encryptData(member.getM_addr2());
		member.setM_addr2(encrypText);
		if(member.getM_job() != null && !member.getM_job().equals("")) {
			encrypText = cryptoService.encryptData(member.getM_job());
			member.setM_job(encrypText);
		}
		
		
		//insert 제외
		member.setM_num(null);	//insert시 m_num은 처리하지 않는다(자동생성)
		
		//등록하기
		member.setExTableName("member");
		exdao.insert(member);
		
		//작업기록
		Func.writeMemberLog("회원가입  ["+member.getM_name()+"("+member.getM_id()+")]", request);
		
		return messageService.redirectMsg(model, "회원가입이 완료되었습니다.", prepage);
	}

	
	/**
	 * @title : 아이디 체크
	 * @method : idCheck
	 */
	@Override
	public int idCheck(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String m_id = exdao.filter(request.getParameter("m_id"));
		
		int count = Func.cInt( exdao.selectQueryColumn("count(*) as cnt", "member", "where m_id = '"+m_id+"'") );
		
		return count;
	}
	
	/**
	 * @title : 이메일 체크
	 * @method : EmailCheck
	 */
	@Override
	public int EmailCheck(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String m_email = cryptoService.encryptData(exdao.filter(request.getParameter("m_email")));
		
		int count = Func.cInt( exdao.selectQueryColumn("count(*) as cnt", "member", "where m_email = '"+m_email+"'") );
		
		return count;
	}

	
	/**
	 * @title : 아이디 찾기
	 * @method : idFind
	 */
	@Override
	public String idFind(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String m_name = cryptoService.encryptData(exdao.filter( request.getParameter("m_name") ));
		String m_email = cryptoService.encryptData(exdao.filter( request.getParameter("m_email") ));
		
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(Func.getSession(request, "ss_m_id"))){
			String main_url = "/";
			if(!"".equals(BUILDER_DIR)){
				main_url = "/"+BUILDER_DIR+main_url;
				
			}
			return messageService.redirectMsg(model, "", main_url);
		}
		
		
		SelectDTO selectdto = new SelectDTO();
		
		if(!"".equals(m_name) && !"".equals(m_email)){	//이름과 이메일로 정보조회
			selectdto.setExTableName("member");
			selectdto.setExColumn("m_name, m_id");
			selectdto.setExWhereQuery("where m_name = '"+m_name+"' and m_email = '"+m_email+"'");
			HashMap<String,String>member = exdao.selectQueryRecord(selectdto);
			if(member.size() == 0){
				m_name = cryptoService.decryptData(m_name);
				return messageService.backMsg(model, m_name+"님의 회원정보가 없습니다. 이름과 이메일을 다시 확인하세요.");
			}else{
				member.put("m_name", cryptoService.decryptData(member.get("m_name")));
				request.setAttribute("member", member);
			}
		}else if(!"".equals( Func.getSession(request, "ss_m_dupinfo") )){
			//본인인증정보 맵생성
			HashMap<String, String> certMap = Func.createCertMap(request);
			request.setAttribute("certMap", certMap);
			
			//본인인증 정보로 회원정보조회
			selectdto.setExTableName("member");
			selectdto.setExColumn("m_name, m_id");
			selectdto.setExWhereQuery("where m_dupinfo = '"+Func.getSession(request, "ss_m_dupinfo")+"'");
			HashMap<String,String>member = exdao.selectQueryRecord(selectdto);
			if(member.size() == 0){
				m_name = cryptoService.decryptData(m_name);
				return messageService.backMsg(model, m_name+"님의 회원정보가 없습니다. 본인인증정보를 다시 확인하세요.");
			}else{
				member.put("m_name", cryptoService.decryptData(member.get("m_name")));
				request.setAttribute("member", member);
			}
		}
		
		
		return "/site/member/idFind";
	}

	
	/**
	 * @title : 비밀번호 찾기
	 * @method : pwdFind
	 */
	@Override
	public String pwdFind(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String m_id = exdao.filter( request.getParameter("m_id") );
		
		String m_name = cryptoService.encryptData(exdao.filter( request.getParameter("m_name") ));
		String m_email = cryptoService.encryptData(exdao.filter( request.getParameter("m_email") ));
		String m_pwd = exdao.filter( request.getParameter("m_pwd") );
		
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		//로그인중이면 메인으로
		if(!"".equals(Func.getSession(request, "ss_m_id"))){
			String main_url = "/";
			if(!"".equals(BUILDER_DIR)){
				main_url = "/"+BUILDER_DIR+main_url;
			}
			return messageService.redirectMsg(model, "", main_url);
		}
		
		SelectDTO selectdto = new SelectDTO();
		
		HashMap<String,String>member = new HashMap<String,String>();
		
		//아이디, 이름, 이메일로 회원정보 조회
		if(!"".equals(m_id) && !"".equals(m_name) && !"".equals(m_email)){
			selectdto.setExTableName("member");
			selectdto.setExColumn("m_num, m_name, m_id, m_email");
			selectdto.setExWhereQuery("where m_id = '"+m_id+"' and m_name = '"+m_name+"' and m_email = '"+m_email+"'");
			member = exdao.selectQueryRecord(selectdto);
			if(member.size() == 0){
				m_name = cryptoService.decryptData(m_name);
				return messageService.backMsg(model, m_name+"님의 회원정보가 없습니다. 입력한 정보를 다시 확인하세요.1");
			}
		}else if(!"".equals( Func.getSession(request, "ss_m_dupinfo") )){
			//본인인증정보 맵생성
			HashMap<String, String> certMap = Func.createCertMap(request);
			request.setAttribute("certMap", certMap);
			
			//본인인증 정보로 회원정보조회
			selectdto.setExTableName("member");
			selectdto.setExColumn("m_num, m_name, m_id, m_email");
			selectdto.setExWhereQuery("where m_dupinfo = '"+Func.getSession(request, "ss_m_dupinfo")+"'");
			member = exdao.selectQueryRecord(selectdto);
			if(member.size() == 0){
				m_name = cryptoService.decryptData(m_name);
				return messageService.backMsg(model, m_name+"님의 회원정보가 없습니다. 본인인증정보를 다시 확인하세요.2");
			}
		}
		
		
		if("".equals(m_pwd)){
			member.put("m_name", cryptoService.decryptData(member.get("m_name")));
			member.put("m_email", cryptoService.decryptData(member.get("m_email")));
			request.setAttribute("member", member);
		}else{
			
			//비밀번호 변경
			String result = this.pwdChangeOk(m_id, m_pwd);
			if(!"ok".equals(result)){
				return messageService.backMsg(model, "비밀번호 변경실패");
			}
			
			//세션변수 변경
			Func.setSession(request, "ss_m_pwd", m_pwd);
			
			String login_url = "/site/member/login.do";
			if(!"".equals(BUILDER_DIR)){
				login_url = "/"+BUILDER_DIR+login_url;
			}
			return messageService.redirectMsg(model, "비밀번호가 변경되었습니다. 다시 로그인 하시기 바랍니다.", login_url);
		}
		
		
		
		return "/site/member/pwdFind";
	}
	
	
	private String pwdRule(String m_pwd){
		String msg = "비밀번호는 숫자, 영문, 특수문자 조합으로 10자리이상 사용해야 합니다.";
		
		m_pwd = Func.nvl(m_pwd).trim();
		
		//길이
		if(m_pwd.length() < 10 || m_pwd.length() > 30){
			return msg;
		}
		
		//숫자체크
		if(!m_pwd.matches(".*[0-9].*")){
			return msg;
		}
		
		//영어체크
		if(!m_pwd.matches(".*[a-z|A-Z].*")){
			return msg;
		}
		
		//특수문자 체크
		if(!m_pwd.matches(".*[!@#$%^&*()?_~].*")){
			return msg;
		}

		return "ok";
	}

}
