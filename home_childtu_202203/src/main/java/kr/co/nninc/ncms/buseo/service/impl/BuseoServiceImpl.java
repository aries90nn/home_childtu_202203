package kr.co.nninc.ncms.buseo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.buseo.service.BuseoDTO;
import kr.co.nninc.ncms.buseo.service.BuseoService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 부서를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2018.11.16
 * @version 1.0
 */
@Service("buseoService")
public class BuseoServiceImpl implements BuseoService {
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/** 목록 */
	@Override
	public String write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		
		String ct_idx = exdao.filter(request.getParameter("ct_idx"));
		String ct_ref = "";
		String ct_depth = "";
		String ct_codeno_ref = "C0;";
		if("".equals(ct_idx)){ //없으면 1단계
			ct_ref			= "0";
			ct_depth		= "1";
			ct_codeno_ref	= "C0;";
		}else{
			
			BuseoDTO selectdto = new BuseoDTO();
			selectdto.setExTableName("buseo");
			selectdto.setExColumn("ct_depth, ct_codeno");
			selectdto.setExWhereQuery("WHERE ct_idx='" + ct_idx + "'");
			HashMap<String, String> buseo = exdao.selectQueryRecord(selectdto);
			if(buseo.size() > 0) {
				String ref_depth	= (String) buseo.get("ct_depth");
				String ref_codeno	= (String) buseo.get("ct_codeno");
				//
				ct_ref				= ct_idx;
				ct_depth			= Func.cStr(Func.cInt(ref_depth) + 1); //상위단계 + 1
				ct_codeno_ref	= ref_codeno;
				
				if(!"".equals(Func.nvl(ref_codeno))) {
					String[] ref_codeno_arr = ref_codeno.split("\\;");

					List<BuseoDTO> refList = new ArrayList<BuseoDTO>();
					for(int z=1;z<=ref_codeno_arr.length-1;z++){
						
						String ct_idx_str =ref_codeno_arr[z].replaceAll("C", "");
						ct_idx_str = ct_idx_str.replaceAll(";", "");
						
						if(!"".equals(ct_idx_str)) {
							selectdto = new BuseoDTO();
							selectdto.setExTableName("buseo");
							selectdto.setExColumn("ct_name");
							selectdto.setExWhereQuery(" WHERE ct_idx = '"+ct_idx_str+"' ");
							String ct_name = exdao.selectQueryColumn(selectdto);
							
							BuseoDTO refbuseo = new BuseoDTO();
							refbuseo.setCt_idx(ct_idx_str);
							refbuseo.setCt_name(ct_name);
							refList.add(refbuseo);
						}
					}
					request.setAttribute("refList", refList);
				}
			}
		}
		
		int max_depth_option = 3;
		//쓰기권한체크*************************************************************************************
		if(Func.cInt(ct_depth) > max_depth_option){
			return messageService.backMsg(model, "최대 3단계 까지 가능합니다.");
		}
		//*********************************************************************************************
		
		request.setAttribute("ct_idx", ct_idx);
		request.setAttribute("ct_ref", ct_ref);
		request.setAttribute("ct_depth", ct_depth);
		request.setAttribute("ct_codeno_ref", ct_codeno_ref);
		request.setAttribute("max_depth_option", Func.cStr(max_depth_option));
		
		//부서리스트
		String sfield = "ct_idx, ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_wdate, ct_codeno, ct_memo";

		BuseoDTO selectdto = new BuseoDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn(sfield);
		selectdto.setExOrderByQuery(" ORDER BY ct_code ");
		String search_str2 = " WHERE site_dir='"+builder_dir+"' ";
		if(!"".equals(ct_idx) ){
			search_str2 += " AND ct_ref='"+ct_idx+"' ";
		}else{
			search_str2 += " AND ct_ref='0' ";
		}
		selectdto.setExWhereQuery(search_str2);
		selectdto.setExKeyColumn("ct_idx");	//clob데이타가 있을경우 키컬럼 지정
		List<HashMap<String, String>> buseoList = exdao.selectQueryTable(selectdto);
		request.setAttribute("buseoList", buseoList);

		Func.getNowPage(request);
		return "/ncms/buseo/write";
	}
	
	/** 등록 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		BuseoDTO buseodto = (BuseoDTO) Func.requestAll(map.get("buseodto"));
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();

		int ct_ref = Func.cInt(exdao.filter(request.getParameter("ct_ref")));
		String ct_codeno_ref = exdao.filter(request.getParameter("ct_codeno_ref"));

		BuseoDTO selectdto = new BuseoDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn("max(ct_code)");
		selectdto.setExWhereQuery(" WHERE ct_ref = '"+ct_ref+"' ");
		int ct_code = Func.cInt(exdao.selectQueryColumn(selectdto))+1;

		buseodto.setSite_dir(builder_dir);
		buseodto.setCt_code(Func.cStr(ct_code));
		buseodto.setCt_wdate(Func.date("Y-m-d H:i:s"));
		buseodto.setCt_memo(Func.InputValueXSS(request.getParameter("ct_memo")));
		buseodto.setExTableName("buseo");

		buseodto.setCt_codeno(null);
		buseodto.setCt_idx(null);
		exdao.insert(buseodto);
		
		//코드값 적용
		selectdto = new BuseoDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn("max(ct_idx)");
		int max_ct_idx = Func.cInt(exdao.selectQueryColumn(selectdto));
		
		String ct_codeno	= ct_codeno_ref+"C"+max_ct_idx+";";
		exdao.executeQuery("UPDATE buseo SET ct_codeno='"+ct_codeno+"' WHERE ct_idx = '"+max_ct_idx+"' ");

		Func.writeManagerLog("부서 등록[" + buseodto.getCt_name() + "]", request);
		
		String prepage = "/ncms/buseo/write.do?ct_idx="+ct_ref;
		if (ct_ref == 0) {
			prepage = "/ncms/buseo/write.do";
		}
		//폴더명 붙이기
		if(!"".equals(builder_dir)){
			prepage = "/" + builder_dir + prepage;
		}
		return "redirect:" + prepage;
	}

	/** 수정 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		BuseoDTO buseodto = (BuseoDTO) Func.requestAll(map.get("buseodto"));

		int ct_ref = Func.cInt(exdao.filter(request.getParameter("ct_ref")));
		String ct_idx = exdao.filter(request.getParameter("ct_idx"));
		
		buseodto.setCt_wdate(Func.date("Y-m-d H:i:s"));
		buseodto.setCt_idx(null);
		buseodto.setCt_code(null);
		buseodto.setCt_ref(null);
		buseodto.setCt_codeno(null);
		buseodto.setCt_chk(null);
		buseodto.setCt_depth(null);
		buseodto.setSite_dir(null);
		buseodto.setCt_memo(Func.InputValueXSS(request.getParameter("ct_memo")));
		buseodto.setCt_wdate(Func.date("Y-m-d H:i:s"));
		buseodto.setExTableName("buseo");
		buseodto.setExWhereQuery("WHERE ct_idx='" + ct_idx + "'");
		exdao.update(buseodto);

		Func.writeManagerLog("부서 수정[" + buseodto.getCt_name() + "]", request);

		String prepage = "/ncms/buseo/write.do?ct_idx="+ct_ref;
		if (ct_ref == 0) {
			prepage = "/ncms/buseo/write.do";
		}
		//폴더명 붙이기
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		if(!"".equals(builder_dir)){
			prepage = "/" + builder_dir + prepage;
		}
		return "redirect:" + prepage;
	}

	/** 삭제 */
	@Override
	public String deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		int ct_ref = Func.cInt(exdao.filter(request.getParameter("ct_ref")));
		String[] arr_chk = request.getParameterValues("chk");
		String ct_idx = exdao.filter(request.getParameter("ct_idx"));
		String status = exdao.filter(request.getParameter("status"));
		if ("totdel".equals(status)) {
			for (int z = 0; z < arr_chk.length; z++) {
				deleteProc(request, arr_chk[z]);
			}
		} else {
			deleteProc(request, ct_idx);
		}
		String prepage = "/ncms/buseo/write.do?ct_idx="+ct_ref;
		if (ct_ref == 0) {
			prepage = "/ncms/buseo/write.do";
		}
		//폴더명 붙이기
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		if(!"".equals(builder_dir)){
			prepage = "/" + builder_dir + prepage;
		}
		return "redirect:" + prepage;
	}
	
	public void deleteProc(HttpServletRequest request, String ct_idx) throws Exception {
		BuseoDTO selectdto = new BuseoDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn("ct_name");
		selectdto.setExWhereQuery("WHERE ct_idx='" + ct_idx + "'");
		HashMap<String, String> edusat = exdao.selectQueryRecord(selectdto);
		if (edusat.size() > 0) {
			exdao.executeQuery("DELETE FROM buseo WHERE ct_idx='" + ct_idx + "'");

			Func.writeManagerLog("부서 삭제[" + (String) edusat.get("ct_name") + "]", request);
		}
	}

	/** 순서리스트 */
	@Override
	public void listMove(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String ct_idx = exdao.filter(request.getParameter("ct_idx"));
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		
		String sfield = "ct_idx, ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_wdate, ct_codeno";

		BuseoDTO selectdto = new BuseoDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn(sfield);
		String search_where = " WHERE site_dir='"+builder_dir+"' ";
		if(!"".equals(ct_idx) ){
			search_where += " AND ct_ref = '"+ct_idx+"' ";
		} else {
			search_where += " AND ct_ref = '0' ";
		}
		selectdto.setExWhereQuery(search_where);
		selectdto.setExOrderByQuery(" ORDER BY ct_code ");
		List<HashMap<String, String>> buseoList = exdao.selectQueryTable(selectdto);
		request.setAttribute("buseoList", buseoList);
		request.setAttribute("ct_ref", ct_idx);
	}

	/** 순서일괄저장 */
	@Override
	public String listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String ct_idx = chk[z];
			
			//순서수정
			exdao.executeQuery("UPDATE buseo SET ct_code='"+(z + 1)+"' WHERE ct_idx='" + ct_idx + "'");
		}
		//작업기록
		Func.writeManagerLog ("부서 일괄순서변경", request);

		int ct_ref = Func.cInt(exdao.filter(request.getParameter("ct_ref")));
		String prepage = "/ncms/buseo/write.do?ct_idx="+ct_ref;
		if (ct_ref == 0) {
			prepage = "/ncms/buseo/write.do";
		}
		//폴더명 붙이기
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		if(!"".equals(builder_dir)){
			prepage = "/" + builder_dir + prepage;
		}
		return messageService.closeRedirect(model, "", prepage);
	}
	
	/** 사용/중지 */
	@Override
	public String levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String tot_level_chk = exdao.filter(request.getParameter("tot_level_chk"));
		String[] chk = request.getParameterValues("chk");
		for (int z = 0; z <= chk.length - 1; z++) {
			String ct_idx = exdao.filter(chk[z]);
			if (!"".equals(ct_idx)) {
				exdao.executeQuery("UPDATE buseo SET ct_chk='" + tot_level_chk + "' WHERE ct_idx='" + ct_idx + "'");

				BuseoDTO selectdto2 = new BuseoDTO();
				selectdto2.setExTableName("buseo");
				selectdto2.setExWhereQuery("WHERE ct_idx='" + ct_idx + "' ");
				selectdto2.setExColumn("ct_name");
				String ct_name = exdao.selectQueryColumn(selectdto2);

				String end_chk_str = "사용";
				if ("N".equals(tot_level_chk)) {
					end_chk_str = "중지";
				}
				Func.writeManagerLog("부서 상태변경 [" + ct_name + "(" + end_chk_str + ")]", request);
			}
		}
		int ct_ref = Func.cInt(exdao.filter(request.getParameter("ct_ref")));
		String prepage = "/ncms/buseo/write.do?ct_idx="+ct_ref;
		if (ct_ref == 0) {
			prepage = "/ncms/buseo/write.do";
		}
		//폴더명 붙이기
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		if(!"".equals(builder_dir)){
			prepage = "/" + builder_dir + prepage;
		}
		return "redirect:" + prepage;
	}

	/** ajax처리 */
	@Override
	public String listAjax(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String sel_type = exdao.filter(request.getParameter("sel_type"));
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		
		//부서리스트
		String sfield = "ct_idx, ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_codeno";

		BuseoDTO selectdto = new BuseoDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn(sfield);
		selectdto.setExOrderByQuery(" ORDER BY ct_code ");
		selectdto.setExWhereQuery(" WHERE site_dir = '"+builder_dir+"' ");
		
		List<HashMap<String, String>> buseoList = exdao.selectQueryTable(selectdto);
		for (int i = 0; i < buseoList.size(); i++) {
			HashMap<String, Object> datamap = (HashMap) buseoList.get(i);

			int ct_depth = Func.cInt((String) datamap.get("ct_depth"));
			int ct_idx = Func.cInt((String) datamap.get("ct_idx"));

			if(ct_depth == 1) {
				selectdto = new BuseoDTO();
				selectdto.setExTableName("buseo");
				selectdto.setExColumn(sfield);
				selectdto.setExOrderByQuery(" ORDER BY ct_code ");
				String search_str3 = "  WHERE ct_ref='"+ ct_idx +"' and ct_depth='2' ";
				selectdto.setExWhereQuery(search_str3);
				List<HashMap<String, String>> buseoList2 = exdao.selectQueryTable(selectdto);
				if(buseoList2.size() >0) {
					for (int j = 0; j < buseoList2.size(); j++) {
						HashMap<String, Object> datamap2 = (HashMap) buseoList2.get(j);
						int ct_idx2 = Func.cInt((String) datamap2.get("ct_idx"));
						//
						selectdto = new BuseoDTO();
						selectdto.setExTableName("buseo");
						selectdto.setExColumn(sfield);
						selectdto.setExOrderByQuery(" ORDER BY ct_code ");
						String search_str4 = "  WHERE ct_ref='"+ ct_idx2 +"' and ct_depth='3' ";
						selectdto.setExWhereQuery(search_str4);
						List<HashMap<String, String>> buseoList3 = exdao.selectQueryTable(selectdto);
						if(buseoList3.size() >0) {
							datamap2.put("list", buseoList3);
						}
					}
					datamap.put("list", buseoList2);
				}
			}
		}
		request.setAttribute("buseoList", buseoList);
		request.setAttribute("sel_type", sel_type);
		return "/ncms/buseo/listAjax";
	}
}
