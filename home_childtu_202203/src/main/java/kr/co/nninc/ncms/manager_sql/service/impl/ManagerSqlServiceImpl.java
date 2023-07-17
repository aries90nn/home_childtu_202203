package kr.co.nninc.ncms.manager_sql.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.manager_sql.service.ManagerSqlDTO;
import kr.co.nninc.ncms.manager_sql.service.ManagerSqlService;

/**
 * 키워드 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.10.17
 * @version 1.0
 */
@Service("managerSqlService")
public class ManagerSqlServiceImpl extends EgovAbstractServiceImpl implements ManagerSqlService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	
	/**
	 * @title : 기본정보 설정 등록폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("manager_sql");
		selectdto.setExKeyColumn("ms_num");
		selectdto.setExColumn("ms_num, ms_get, ms_post");
		HashMap<String,String>managersql = exdao.selectQueryRecord(selectdto);
		request.setAttribute("managersql", managersql);
	}

	/**
	 * @title : 기본정보 설정 등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		ManagerSqlDTO managersqldto = (ManagerSqlDTO) Func.requestAll(map.get("managersqldto"));
		
		//insert 제외 필드
		managersqldto.setMs_num(null);
		
		//insert
		managersqldto.setExTableName("manager_sql");
		exdao.insert(managersqldto);

	}

	/**
	 * @title : 기본정보 설정 수정처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		ManagerSqlDTO managersqldto = (ManagerSqlDTO) Func.requestAll(map.get("managersqldto"));
		
		String ms_num = exdao.filter( request.getParameter("ms_num") );
		
		//update 제외 필드
		managersqldto.setMs_num(null);
		
		//update
		managersqldto.setExTableName("manager_sql");
		managersqldto.setExWhereQuery("where ms_num = '"+ms_num+"'");
		exdao.update(managersqldto);

	}

}
