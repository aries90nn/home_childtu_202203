package kr.co.nninc.ncms.common;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import egovframework.rte.fdl.cryptography.impl.EgovARIACryptoServiceImpl;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.crypto.service.CryptoARIAService;
import kr.co.nninc.ncms.member.service.MemberDTO;

/**
 * 회원함수모음
 * 
 * @author 나눔
 * @since 2017.11.16
 * @version 1.0
 */
@Component("FuncMember")
public class FuncMember {
	
	/** 동적dao */
	private static ExtendDAO exdao;

	/**ARIA 암호화*/
	//@Resource(name="cryptoService")
	private static CryptoARIAService cryptoService;
	
	@Autowired
	private FuncMember(ExtendDAO exdao, CryptoARIAService cryptoService) {
		this.exdao = exdao;
		this.cryptoService = cryptoService;
	}
	
	

	/**
	 * @throws Exception 
	 * @title : 아이디패스워드검사
	 * @method : memgr_idpwdchk
	 */
	public static boolean memgr_idpwdchk(String m_id, String m_pwd) throws Exception {
		return memgr_idpwdchk(m_id, m_pwd, "");
	}
	public static boolean memgr_idpwdchk(String m_id, String m_pwd, String m_manager) throws Exception {

		boolean rtn_val = false;
		
		m_manager = Func.nvl(m_manager);
		m_pwd = Func.hash(m_pwd);
		
		//select 전용 dto
		MemberDTO selectdto = new MemberDTO();
		
		selectdto.setExTableName("member_view");
		selectdto.setExColumn("count(*)");
		String where_query = "WHERE m_id = '"+exdao.filter(m_id)+"' and m_pwd = '"+exdao.filter(m_pwd)+"'";
		if(!"".equals(m_manager)){
			where_query += " and g_manager = 'Y'";
		}
		selectdto.setExWhereQuery(where_query);
		
		String cnt = exdao.selectQueryColumn(selectdto);
		if(!"0".equals(cnt)){
			rtn_val = true;
		}
		
		return rtn_val;
	}
	
	
	/**
	 * @throws Exception 
	 * @title : 일반 이용자 아이디패스워드검사
	 * @method : memgr_idpwdchk2
	 */
	public static boolean memgr_idpwdchk2(String m_id, String m_pwd) throws Exception {
		
		return memgr_idpwdchk(m_id, m_pwd, "");
	}
	
	
	/**
	 * @throws Exception 
	 * @title : 로긴시 쿠키생성
	 * @method : memgr_logincookie
	 */
	public static void memgr_logincookie(HttpServletRequest request, String m_id, String m_pwd) throws Exception {
		
		
		MemberDTO selectdto = new MemberDTO();
		selectdto.setExTableName("member");
		selectdto.setExColumn("m_num, m_id, m_name, m_level, m_lastdate, m_ip, m_site");
		selectdto.setExWhereQuery("where m_id='"+exdao.filter(m_id)+"' and m_pwd = '"+Func.hash(m_pwd)+"'");
		HashMap<String,String>member = exdao.selectQueryRecord(selectdto);
		
		if(member.size() > 0){
			// 세션생성
			Func.setSession(request, "ss_m_num", member.get("m_num"));
			Func.setSession(request, "ss_m_id", member.get("m_id"));
			if(!member.get("m_id").equals("nninc")) { // ARIA
				Func.setSession(request, "ss_m_name", cryptoService.decryptData(member.get("m_name")));	
			}else{
				Func.setSession(request, "ss_m_name", member.get("m_name"));
			}
			Func.setSession(request, "ss_g_num", member.get("m_level"));
			Func.setSession(request, "ss_m_pwd", m_pwd);
			Func.setSession(request, "ss_m_site", member.get("m_site"));
			
			//이전접속기록
			Func.setSession(request, "ss_m_lastdate", member.get("m_lastdate"));
			Func.setSession(request, "ss_m_ip", member.get("m_ip"));
	
			//최근접속일 등록
			MemberDTO paramMember = new MemberDTO();
			paramMember.setM_lastdate(Func.date("Y-m-d H:i:s"));
			paramMember.setM_ip(Func.remoteIp(request));
			paramMember.setM_id(m_id);
			
			paramMember.setExTableName("member");
			paramMember.setExWhereQuery("where m_id = '"+exdao.filter(m_id)+"'");
			exdao.update(paramMember);
			
		}
	}
	
	
	
	/**
	 * @title : 관리자페이지 쿠키생성
	 * @method : memgr_ad_cmscookie
	 */
	public static void memgr_ad_cmscookie(HttpServletRequest request) {
		Func.setSession(request, "ss_security_ad_cms", "ok");
	}
	
	/**
	 * @title : 로그아웃
	 * @method : memgr_logout
	 */	
	public static void memgr_logout(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.invalidate();
	}
}
