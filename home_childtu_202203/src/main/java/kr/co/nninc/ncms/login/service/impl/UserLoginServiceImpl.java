package kr.co.nninc.ncms.login.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.FuncMember;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.login.service.UserLoginService;

@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {

	/** 메시지 출력할때 사용 */
	@Resource(name = "loginService")
	private LoginServiceImpl loginService;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	
	/** 로그인 실패제한횟수 */
	private int failCount = 0;
	
	/** 로그인 제한시간(분) */
	private int loginLimitTime = 0;
	
	/**
	 * @title : 로그인 처리
	 * @method : loginOk
	 */
	@Override
	public String loginOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpServletResponse response = (HttpServletResponse) map.get("response");
		
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		String DOMAIN_HTTP = Func.nvl( CommonConfig.get("DOMAIN_HTTP") ).trim();
		
		//loginService 처리를 재사용
		this.failCount = loginService.getFailCount();
		this.loginLimitTime = loginService.getLoginLimitTime();
		
		//아이디&비밀번호
		String m_id = Func.nvl( request.getParameter("m_id") ).trim();
		String m_pwd = Func.nvl( request.getParameter("m_pwd") ).trim();
		String prepage = Func.nvl( request.getParameter("prepage") );
		if("".equals(prepage)){
			prepage = "/";
			if(!"".equals(builder_dir)){
				prepage = "/"+builder_dir+"/";
			}
		}
		
		String idsave = Func.nvl( request.getParameter("idsave") );
		if("Y".equals(idsave)){
			Func.setCookie(request, response, "ck_idsave", "Y", 60*60*24*30);
			Func.setCookie(request, response, "ck_m_id", m_id, 60*60*24*30);
		}else{
			Func.setCookie(request, response, "ck_idsave", "", 0);
			Func.setCookie(request, response, "ck_m_id", "", 0);
		}
		
		//세션 초기화
		request.getSession().invalidate();
		
		//본인인증정보는 초기화
		Func.setSession(request, "ss_m_name", "");
		Func.setSession(request, "ss_m_dupinfo", "");
		Func.setSession(request, "ss_m_coinfo", "");
		Func.setSession(request, "ss_m_sex", "");
		Func.setSession(request, "ss_m_age", "");
		Func.setSession(request, "ss_m_birth", "");
		
		//로그인 처리
		boolean status = false;
		
		//관리자(홈페이지 회원) 로그인
		if (FuncMember.memgr_idpwdchk(m_id, m_pwd)) {
			
			//로그인 실패시간 확인
			String m_incorrect_time = loginService.loginFailTime(m_id);
			if(!"".equals(m_incorrect_time)){
				if(Func.cLng(Func.date("YmdHi")) - Func.cLng(m_incorrect_time) < loginLimitTime){
					String msg = "로그인실패 "+failCount+"회이상으로 "+loginLimitTime+"분간 로그인이 제한되었습니다.";
					return messageService.backMsg(model, msg);
				}
			}
			FuncMember.memgr_logincookie(request, m_id, m_pwd); //인증쿠키발생
			loginService.loginFailReset(m_id);		//로그인실패 초기화
			status = true;
			
			//작업기록
			Func.writeMemberLog ("홈페이지 로그인", request);
		}else{
			String failCheck = loginService.loginFailCheck(model);
			if(!"N".equals(failCheck)){
				String msg = "정보가 정확하지 않거나 접속권한이 없습니다. \\n\\n(비밀번호 "+failCount+"회 실패시 "+loginLimitTime+"분동안 로그인이 제한됩니다.)";
				return messageService.backMsg(model, msg);
			}
			//return messageService.backMsg(model, "정보가 정확하지 않거나 접속권한이 없습니다. \\n\\n(비밀번호 5회 실패시 10분동안 로그인이 제한됩니다.)");
		}
		
		
		
		if(status){
			return "redirect:" + DOMAIN_HTTP+prepage;
		}else{
			return messageService.backMsg(model, "정보가 정확하지 않거나 접속권한이 없습니다.");
		}
	}

}
