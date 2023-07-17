package kr.co.nninc.ncms.common.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.service.MessageService;

/**
 * 공통으로 사용해해야 하는 서비스를 정의하기 위한 서비스 인터페이스
 * 
 * @author 나눔 개발팀
 * @since 2017.08.29
 * @version 1.0
 */
@Service("messageService")
public class MessageServiceImpl extends EgovAbstractServiceImpl implements MessageService {
	/**
	 * 메시지를 출력하고 해당 URL로 리턴한다.
	 * 
	 * @param ModelMap, String, String
	 * @return String
	 * @throws Exception
	 */
	public String redirectMsg(Model model, String msg, String url) throws Exception {
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		return "site/common/redirect_msg";
	}

	/**
	 * 메시지를 출력하고 history.back 한다.
	 * @param model
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public String backMsg(Model model, String msg) throws Exception {
		model.addAttribute("msg", msg);
		return "site/common/back_msg";
	}

	/**
	 * 새창을 닫고 해당 URL로 리턴한다.
	 * @param model
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public String closeMsg(Model model, String msg) throws Exception {
		model.addAttribute("msg", msg);
		return "site/common/close_msg";
	}
	
	public String closeRedirect(Model model, String msg, String url) throws Exception {
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		return "site/common/close_redirect";
	}
	
	/**
	 * 확인창묻고 해당 URL로 리턴한다.
	 * @param model
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	@Override
	public String confirmMsg(Model model, String msg, String url, String url2) throws Exception {
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		model.addAttribute("url2", url2);
		return "site/common/confirm_msg";
	}
}
