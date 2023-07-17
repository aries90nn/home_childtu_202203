package kr.co.nninc.ncms.edusat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.code_config.service.CodeConfigDTO;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.edusat.service.EdusatCodeService;


/**
 * 온라인강좌 분류를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.09.17
 * @version 1.01
 */
@Service("edusatCodeService")
public class EdusatCodeServiceImpl implements EdusatCodeService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/**
	 * @title : 코드 등록/수정 폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//변수초기화
		int ct_idx = Func.cInt((String) request.getParameter("ct_idx")) ;
		if(ct_idx== 0){ct_idx = 2;}
		int ct_ref = ct_idx;
		int ct_depth = 2;
		String ct_codeno_ref = "C0;C2;";
		//int max_depth_option = Func.cInt(CommonConfig.get("code.max_depth_option"));
		int max_depth_option = 3;
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		//코드리스트 가져오기
		selectdto = new SelectDTO();
		selectdto.setExTableName("code_config");
		selectdto.setExColumn( new CodeConfigDTO() );
		String whereQuery = "where ct_ref = '"+ct_ref+"'";
		if(!"".equals(builder_dir)){
			whereQuery += " and ct_site_dir = '"+builder_dir+"'";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by ct_code");
		List<HashMap<String,String>>codeList = exdao.selectQueryTable(selectdto);

		request.setAttribute("codeList", codeList);
		
		//값리턴하기
		request.setAttribute("ct_idx", ct_idx);
		request.setAttribute("ct_ref", ct_ref);
		request.setAttribute("ct_depth", ct_depth);
		request.setAttribute("ct_codeno_ref", ct_codeno_ref);
		request.setAttribute("max_depth_option", max_depth_option);

	}

	@Override
	public void writeOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void listMoveOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void levelOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

}
