package kr.co.nninc.ncms.login.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.FuncMember;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.login.service.LoginService;
import kr.co.nninc.ncms.member.service.MemberDTO;


@Service("loginService")
public class LoginServiceImpl extends EgovAbstractServiceImpl implements LoginService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/** 로그인 실패제한횟수 */
	private int failCount = 5;
	
	/** 로그인 제한시간(분) */
	private int loginLimitTime = 10;
	
	public int getFailCount() {
		return failCount;
	}

	public int getLoginLimitTime() {
		return loginLimitTime;
	}

	/**
	 * @title : 로그인 처리
	 * @method : loginOk
	 */
	@Override
	public String loginOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//아이디&비밀번호
		String m_id = Func.nvl( request.getParameter("m_id") );
		String m_pwd = Func.nvl( request.getParameter("m_pwd") );
		String prepage = Func.nvl( request.getParameter("prepage") );
		if("".equals(prepage)){
			prepage = "/ncms/builder/list.do";
			String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
			if(!"".equals(builder_dir)){
				prepage = "/"+builder_dir+"/ncms/cms/write.do";
			}
		}
		
		if (FuncMember.memgr_idpwdchk(m_id, m_pwd, "Y")) {
			
			//로그인 실패시간 확인
			String m_incorrect_time = loginFailTime(m_id);
			if(!"".equals(m_incorrect_time)){
				if(Func.cLng(Func.date("YmdHi")) - Func.cLng(m_incorrect_time) < this.loginLimitTime){
					String msg = "로그인실패 "+this.failCount+"회이상으로 "+this.loginLimitTime+"분간 로그인이 제한되었습니다.";
					return messageService.backMsg(model, msg);
				}
			}
			
			FuncMember.memgr_logincookie(request, m_id, m_pwd); //인증쿠키발생
			FuncMember.memgr_ad_cmscookie(request); //관리자페이지쿠키발생
			
			loginFailReset(m_id);		//로그인실패 초기화
			
			Func.writeManagerLoginLog("관리자페이지 로그인", request);
			return "redirect:" + prepage;
		} else {
			String failCheck = loginFailCheck(model);
			if(!"N".equals(failCheck)){
				String msg = "정보가 정확하지 않거나 접속권한이 없습니다. \\n\\n(비밀번호 "+this.failCount+"회 실패시 "+this.loginLimitTime+"분동안 로그인이 제한됩니다.)";
				return messageService.backMsg(model, msg);
			}
			
			return messageService.backMsg(model, "정보가 정확하지 않거나 접속권한이 없습니다. \\n\\n(비밀번호 "+this.failCount+"회 실패시 "+this.loginLimitTime+"분동안 로그인이 제한됩니다.)");
		}

	}

	/**
	 * @title : 관리자 로그인 실패체크
	 * @method : loginFailCheck
	 */
	@Override
	public String loginFailCheck(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String m_id = exdao.filter( request.getParameter("m_id") );
		
		MemberDTO selectdto = new MemberDTO();
		selectdto.setExColumn("m_incorrect, m_incorrect_time");
		selectdto.setExTableName("member");
		selectdto.setExWhereQuery("where m_id = '"+m_id+"'");
		HashMap<String,String>member = exdao.selectQueryRecord(selectdto);
		
		if(member.size() == 0){
			return "N";
		}else{
			int m_incorrect = Func.cInt(member.get("m_incorrect"));
			String m_incorrect_time = Func.cStr(member.get("m_incorrect_time"));
			if(m_incorrect < this.failCount){
				m_incorrect += 1;
				m_incorrect_time = Func.date("YmdHi");
				exdao.executeQuery("UPDATE member SET m_incorrect='"+m_incorrect+"', m_incorrect_time='"+m_incorrect_time+"' WHERE m_id='"+m_id+"'");
			}
			return Func.cStr( m_incorrect );
		}
	}

	/**
	 * @title : 관리자 로그인 실패 초기화
	 * @method : loginFailReset
	 */
	@Override
	public void loginFailReset(String m_id) throws Exception {
		exdao.executeQuery("update member set m_incorrect = '0', m_incorrect_time = '' where m_id = '"+exdao.filter(m_id)+"'");
	}

	@Override
	public String loginFailTime(String m_id) throws Exception {

		String m_incorrect_time = "";
		
		MemberDTO selectdto = new MemberDTO();
		selectdto.setExTableName("member");
		selectdto.setExColumn("m_incorrect_time");
		selectdto.setExWhereQuery("where m_id = '"+exdao.filter(m_id)+"' and m_incorrect >= '"+this.failCount+"'");
		
		m_incorrect_time = Func.nvl( exdao.selectQueryColumn(selectdto) );
		
		return m_incorrect_time;
	}

}
