package kr.co.nninc.ncms.session.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.session.service.SessionService;
import kr.co.nninc.ncms.site_info.service.SiteConfigDTO;


/**
 * 세션 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.10.18
 * @version 1.0
 */
@Service("sessionService")
public class SessionServiceImpl extends EgovAbstractServiceImpl implements SessionService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/**
	 * @title : 세션 등록폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("site_config");
		selectdto.setExColumn("sc_session_time, sc_session_use, sc_session_count, sc_session_wait");
		selectdto.setExOrderByQuery("order by sc_idx desc");
		HashMap<String,String>info = exdao.selectQueryRecord(selectdto);
		request.setAttribute("info", info);

	}

	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String sc_session_time = exdao.filter( request.getParameter("sc_session_time") );
		String sc_session_count = exdao.filter( request.getParameter("sc_session_count") );
		String sc_session_wait = exdao.filter( request.getParameter("sc_session_wait") );
		
		SiteConfigDTO infodto = new SiteConfigDTO();
		infodto.setSc_session_time(sc_session_time);
		infodto.setSc_session_count(sc_session_count);
		infodto.setSc_session_wait(sc_session_wait);
		
		//update
		infodto.setExTableName("site_config");
		exdao.update(infodto);

	}

}
