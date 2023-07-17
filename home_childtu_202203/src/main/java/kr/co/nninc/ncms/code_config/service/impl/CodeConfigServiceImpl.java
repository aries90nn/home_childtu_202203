package kr.co.nninc.ncms.code_config.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.code_config.service.CodeConfigDTO;
import kr.co.nninc.ncms.code_config.service.CodeConfigService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 코드 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.10.02
 * @version 1.0
 */
@Service("codeService")
public class CodeConfigServiceImpl extends EgovAbstractServiceImpl implements CodeConfigService {

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

		SelectDTO selectdto = new SelectDTO();
		
		//변수초기화
		int ct_idx = Func.cInt((String) request.getParameter("ct_idx")) ;
		int ct_ref = 0;
		int ct_depth = 1;
		String ct_codeno_ref = "C0;";
		//int max_depth_option = Func.cInt(CommonConfig.get("code.max_depth_option"));
		int max_depth_option = 3;
		
		//상위 idx값을 구한다.
		if(ct_idx != 0) {
			selectdto.setExTableName("code_config");
			selectdto.setExColumn("ct_idx, ct_depth, ct_codeno");
			selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
			HashMap<String,String>codedto = exdao.selectQueryRecord(selectdto);
			
			ct_ref				= Func.cInt(codedto.get("ct_idx"));
			ct_depth			= Func.cInt(codedto.get("ct_depth")) + 1;
			ct_codeno_ref		= codedto.get("ct_codeno");
		}
		
		//현재 메뉴경로
		List<HashMap> refCodeList = new ArrayList<HashMap>();
		if(!"".equals(ct_codeno_ref)) {
			String[] ref_codeno_arr = ct_codeno_ref.split("\\;");
			for(int z=1; z<=ref_codeno_arr.length-1; z++) {
				String ct_idx_str = ref_codeno_arr[z].replaceAll("C","");
				ct_idx_str = ct_idx_str.replaceAll(";","");
				
				if(!"".equals(ct_idx_str)) {
					selectdto = new SelectDTO();
					selectdto.setExTableName("code_config");
					selectdto.setExColumn("ct_idx, ct_name, ct_depth, ct_codeno");
					selectdto.setExWhereQuery("where ct_idx = '"+ct_idx_str+"'");
					HashMap<String,String>menudto2 = exdao.selectQueryRecord(selectdto);
					if(menudto2.size() > 0){
						HashMap hm = new HashMap();
						hm.put("ct_idx", ct_idx_str);
						hm.put("ct_name", menudto2.get("ct_name"));
						refCodeList.add(hm);
					}
				}
			}
		}
		request.setAttribute("refCodeList", refCodeList);
		
		//코드리스트 가져오기
		selectdto = new SelectDTO();
		selectdto.setExTableName("code_config");
		selectdto.setExColumn( new CodeConfigDTO() );
		selectdto.setExWhereQuery("where ct_ref = '"+ct_ref+"'");
		selectdto.setExOrderByQuery("order by ct_code");
		List<HashMap<String,String>>codeList = exdao.selectQueryTable(selectdto);
		
		//값리턴하기
		request.setAttribute("ct_idx", ct_idx);
		request.setAttribute("ct_ref", ct_ref);
		request.setAttribute("ct_depth", ct_depth);
		request.setAttribute("ct_codeno_ref", ct_codeno_ref);
		request.setAttribute("max_depth_option", max_depth_option);
		request.setAttribute("codeList", codeList);
		
		//도서관(사이트)분류라면 사이트목록을불러온다
		if(ct_idx == 1){
			selectdto = new SelectDTO();
			selectdto.setExTableName("builder_site");
			selectdto.setExColumn("bs_num, bs_sitename, bs_directory");
			selectdto.setExWhereQuery("where bs_chk = 'Y'");
			List<HashMap<String,String>>siteList = exdao.selectQueryTable(selectdto);
			request.setAttribute("siteList", siteList);
		}
		
		Func.getNowPage(request);
	}

	/**
	 * @title : 코드관리 등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		CodeConfigDTO codedto = (CodeConfigDTO) map.get("codedto");

		SelectDTO selectdto = new SelectDTO();
		
		//1. 마지막 순서코드 가져오기
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("max(ct_code) as ct_code");
		int max_code = Func.cInt( exdao.selectQueryColumn(selectdto) )+1;
		
		
		//2. 등록처리
		String ct_wdate = Func.date("Y-m-d H:i:s");
		codedto.setCt_code(Func.cStr(max_code));
		codedto.setCt_wdate(ct_wdate);
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			codedto.setCt_site_dir(builder_dir);
		}
		
		//insert 제외
		codedto.setCt_idx(null);
		
		//insert
		codedto.setExTableName("code_config");
		exdao.insert(codedto);
		
		//3. 최대 일련번호 가져오기
		selectdto = new SelectDTO();
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("max(ct_idx) as cnt");
		int max_ct_idx = Func.cInt( exdao.selectQueryColumn(selectdto) );
		
		//4. 메뉴값 적용
		String ct_codeno_ref = Func.nvl( request.getParameter("ct_codeno_ref") ).trim();
		String ct_codeno = ct_codeno_ref+"C"+max_ct_idx+";";
		exdao.executeQuery("UPDATE code_config SET ct_codeno = '"+ct_codeno+"' WHERE ct_idx = '"+max_ct_idx+"'");
		
		//5. 리턴파라미터
		String ct_ref = codedto.getCt_ref();
		if("".equals(Func.nvl(codedto.getCt_ref())) || "0".equals(codedto.getCt_ref())) { ct_ref = "";}
		request.setAttribute("ct_ref", ct_ref);
		
	}

	
	/**
	 * @title : 코드관리 수정처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		CodeConfigDTO codedto = (CodeConfigDTO) map.get("codedto");

		String ct_idx = exdao.filter( request.getParameter("ct_idx") );
		
		//수정처리
		String ct_wdate = Func.date("Y-m-d H:i:s");
		codedto.setCt_wdate(ct_wdate);
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			codedto.setCt_site_dir(builder_dir);
		}
		
		//update 제외
		codedto.setCt_idx(null);
		codedto.setCt_chk(null);
		
		//update
		codedto.setExTableName("code_config");
		codedto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		exdao.update(codedto);
		
		//리턴파라미터
		String ct_ref = codedto.getCt_ref();
		if("".equals(Func.nvl(codedto.getCt_ref())) || "0".equals(codedto.getCt_ref())) { ct_ref = "";}
		request.setAttribute("ct_ref", ct_ref);
	}

	/**
	 * @title : 코드관리 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 		= exdao.filter( request.getParameter("status") ); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String ct_idx		= exdao.filter( request.getParameter("ct_idx") );  //단일 삭제 사용

		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("ct_codeno");
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				selectdto.setExWhereQuery("where ct_idx = '"+exdao.filter(chk[z])+"'");
				String ct_codeno = exdao.selectQueryColumn(selectdto);
				exdao.executeQuery("DELETE FROM code_config WHERE ct_codeno LIKE '"+exdao.filter(ct_codeno)+"%'");
				
			}
		} else { //단일삭제
			selectdto.setExWhereQuery("where ct_idx = '"+exdao.filter(ct_idx)+"'");
			String ct_codeno = exdao.selectQueryColumn(selectdto);
			exdao.executeQuery("DELETE FROM code_config WHERE ct_codeno LIKE '"+exdao.filter(ct_codeno)+"%'");
		}
	}

	
	/**
	 * @title : 코드관리 순서일괄수정
	 * @method : listMoveOk
	 */
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String ct_idx = exdao.filter( chk[z] );
			// 순서업데이트
			exdao.executeQuery("UPDATE code_config SET ct_code = '"+Func.cStr(z + 1)+"' WHERE ct_idx = '"+ct_idx+"'");
			
			//리턴파라미터
			String ct_ref = Func.nvl(request.getParameter("ct_ref")).trim();
			if("0".equals(ct_ref)) { ct_ref = "";}
			request.setAttribute("ct_ref", ct_ref);
		}
	}

	@Override
	public void move(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 코드사용여부변경
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String tot_level_chk	= exdao.filter( request.getParameter("tot_level_chk") );
		String[] chk 			= request.getParameterValues("chk");

		for (int z = 0; z <= chk.length - 1; z++) {
			String ct_idx = exdao.filter( chk[z] );
			String ct_chk = tot_level_chk;
			//
			exdao.executeQuery("update code_config set ct_chk = '"+ct_chk+"' where ct_idx = '"+ct_idx+"'");
			
			//리턴파라미터
			String ct_ref = Func.nvl( request.getParameter("ct_ref") ).trim();
			if("0".equals(ct_ref)) { ct_ref = "";}
			request.setAttribute("ct_ref", ct_ref);
		}
		
	}
	

}
