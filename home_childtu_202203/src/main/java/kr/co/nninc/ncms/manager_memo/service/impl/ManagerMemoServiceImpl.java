package kr.co.nninc.ncms.manager_memo.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.manager_memo.service.ManagerMemoDTO;
import kr.co.nninc.ncms.manager_memo.service.ManagerMemoService;

@Service("managerMemoService")
public class ManagerMemoServiceImpl extends EgovAbstractServiceImpl implements ManagerMemoService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	
	/**
	 * @title : 메모 가져오기
	 * @method : view
	 */
	@Override
	public void view(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//select전용 dto
		ManagerMemoDTO selectdto = new ManagerMemoDTO();
		selectdto.setExTableName("manager_memo");
		selectdto.setExKeyColumn("mm_idx");
		selectdto.setExColumn("mm_idx, mm_content, mm_wdate");
		selectdto.setExWhereQuery("where mm_id = '"+Func.getSession(request, "ss_m_id")+"'");
		HashMap<String,String>managermemo = exdao.selectQueryRecord(selectdto);
		request.setAttribute("managermemo", managermemo);

	}

	/**
	 * @title : 메모 등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		ManagerMemoDTO param = (ManagerMemoDTO) map.get("managermemodto");
		param.setMm_id(Func.getSession(request, "ss_m_id"));
		param.setMm_wdate(Func.date("Y-m-d H:i:s"));
		param.setExTableName("manager_memo");
		
		//등록에서 제외
		param.setMm_idx(null);
		
		exdao.insert(param);
	}

	/**
	 * @title : 메모 수정처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		ManagerMemoDTO param = (ManagerMemoDTO) map.get("managermemodto");
		
		param.setMm_wdate(Func.date("Y-m-d H:i:s"));
		
		//수정에서 제외
		param.setMm_idx(null);
		param.setMm_id(null);

		param.setExTableName("manager_memo");
		param.setExWhereQuery("where mm_id = '"+Func.getSession(request, "ss_m_id")+"'");
		exdao.update(param);
		
	}

}
