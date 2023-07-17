package kr.co.nninc.ncms.buseo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.buseo.service.BuseoDTO;
import kr.co.nninc.ncms.buseo.service.UserBuseoService;
import kr.co.nninc.ncms.buseo_member.service.BuseoMemberDTO;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 부서를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2021.04.19
 * @version 1.0
 */
@Service("userBuseoService")
public class UserBuseoServiceImpl implements UserBuseoService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	@Resource(name = "messageService")
	private MessageService messageService;

	/** 리스트 */
	@Override
	public String list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String v_keyword 	= Func.cStr(exdao.filter(request.getParameter("v_keyword")));
		String v_tab 	= Func.cStr(exdao.filter(request.getParameter("v_tab")));
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		
		String codeno = Func.cStr(exdao.filter(request.getParameter("codeno")));
		String ct_idx_1 = "", ct_idx_2 = "", ct_idx_3 = "";
		
		if(!"".equals(Func.nvl(codeno))) {
			String[] ref_codeno_arr = codeno.split("\\;");
			ct_idx_1 = ref_codeno_arr[1].replaceAll("C","").replaceAll(";","");
			
			if(ref_codeno_arr.length >= 3){
				ct_idx_2 = ref_codeno_arr[2].replaceAll("C","").replaceAll(";","");
			}
			if(ref_codeno_arr.length >= 4){
				ct_idx_3 = ref_codeno_arr[3].replaceAll("C","").replaceAll(";","");
			}
		}
		
		//부서리스트
		String sfield = "ct_idx, ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_codeno";
		String mfield = "m_num, m_name, m_tel, m_fax, ct_idx, m_code, m_wdate, m_mdate, m_work, ct_codeno ";
		
		//검색조건 넣기
		String idx_str = "", idx_str2 = "", idx_str3 = "";
		BuseoDTO selectdto = new BuseoDTO();
		if(!"".equals(Func.nvl(v_keyword))) {
			selectdto.setExTableName("buseo");
			selectdto.setExColumn(sfield);
			selectdto.setExOrderByQuery(" ORDER BY ct_code ");
			selectdto.setExKeyColumn("ct_idx");
			String search_str2 = "  WHERE site_dir='"+builder_dir+"' AND ct_codeno in ( select ct_codeno FROM buseo_member WHERE m_name like '%"+ v_keyword +"%' or m_work like '%"+ v_keyword +"%') ";
			selectdto.setExWhereQuery(search_str2);
			List<HashMap<String, String>> buseoTmpList = exdao.selectQueryTable(selectdto);
			
			for (int i = 0; i < buseoTmpList.size(); i++) {
				HashMap<String, Object> datamap = (HashMap) buseoTmpList.get(i);
				
				String ct_codeno = (String) datamap.get("ct_codeno");
				if(!"".equals(ct_codeno)) {
					String[] ref_codeno_arr = ct_codeno.split("\\;");
					String ct_idx_tmp = ref_codeno_arr[1].replaceAll("C","").replaceAll(";","");
					String ct_idx_tmp2 = "";
					String ct_idx_tmp3 = "";
					if(ref_codeno_arr.length >2) {
						ct_idx_tmp2 = ref_codeno_arr[2].replaceAll("C","").replaceAll(";","");
					}
					if(ref_codeno_arr.length > 3) {
						ct_idx_tmp3 = ref_codeno_arr[3].replaceAll("C","").replaceAll(";","");
					}

					idx_str += "'"+ct_idx_tmp+"', ";
					idx_str2 += "'"+ct_idx_tmp2+"', ";
					idx_str3 += "'"+ct_idx_tmp3+"', ";
				}
			}
		}
		//
		String search_idx = "", search_idx2 = "", search_idx3 = "", search2 = "";
		
		//검색어있으면
		if(!"".equals(Func.nvl(v_keyword))) {
			search2 = "  AND (m_name like '%"+ v_keyword +"%' or m_work like '%"+ v_keyword +"%') ";
			if(!"".equals(Func.nvl(idx_str))) {
				search_idx = " AND ct_idx in ("+idx_str.substring(0, idx_str.length()-2)+") ";
			}else {
				search_idx = " AND 1=2 ";
			}
			if(!"".equals(Func.nvl(idx_str2))) {
				search_idx2 = " AND ct_idx in ("+idx_str2.substring(0, idx_str2.length()-2)+") ";
			}
			if(!"".equals(Func.nvl(idx_str3))) {
				search_idx3 = " AND ct_idx in ("+idx_str3.substring(0, idx_str3.length()-2)+") ";
			}
		}

		selectdto = new BuseoDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn(sfield);
		selectdto.setExOrderByQuery(" ORDER BY ct_code ");
		if(!"".equals(Func.nvl(v_keyword))) {
			selectdto.setExWhereQuery(" WHERE site_dir='"+builder_dir+"' AND ct_depth=1 "+search_idx);
		}else {
			selectdto.setExWhereQuery(" WHERE site_dir='"+builder_dir+"' AND ct_depth=1");
		}
		List<HashMap<String, String>> buseoList = exdao.selectQueryTable(selectdto);
		for (int i = 0; i < buseoList.size(); i++) {
			HashMap<String, Object> datamap = (HashMap) buseoList.get(i);

			int ct_idx = Func.cInt((String) datamap.get("ct_idx"));
			String ct_codeno = (String) datamap.get("ct_codeno");
			
			//조직원리스트 ====================================
			BuseoMemberDTO mselectdto = new BuseoMemberDTO();
			mselectdto.setExTableName("buseo_member");
			mselectdto.setExColumn(mfield);
			mselectdto.setExWhereQuery(" WHERE ct_codeno='"+ ct_codeno +"' "+search2);
			mselectdto.setExKeyColumn("m_num");	//clob데이타가 있을경우 키컬럼 지정
			mselectdto.setExOrderByQuery(" ORDER BY m_code ");
			List<HashMap<String, String>> memberList = exdao.selectQueryTable(mselectdto);
			for (int k = 0; k < memberList.size(); k++) {
				HashMap<String, Object> mdatamap = (HashMap) memberList.get(k);
				mdatamap.put("m_work2", Func.getTextmode(Func.nvl((String) mdatamap.get("m_work"))));
			}
			datamap.put("memberList", memberList);
			//조직원리스트 ====================================
			
			selectdto = new BuseoDTO();
			selectdto.setExTableName("buseo");
			selectdto.setExColumn(sfield);
			selectdto.setExOrderByQuery(" ORDER BY ct_code ");
			selectdto.setExWhereQuery(" WHERE ct_ref='"+ ct_idx +"' "+search_idx2);
			List<HashMap<String, String>> buseoList2 = exdao.selectQueryTable(selectdto);
			if(buseoList2.size() >0) {
				for (int j = 0; j < buseoList2.size(); j++) {
					HashMap<String, Object> datamap2 = (HashMap) buseoList2.get(j);
					int ct_idx2 = Func.cInt((String) datamap2.get("ct_idx"));
					
					//조직원리스트 ====================================
					mselectdto = new BuseoMemberDTO();
					mselectdto.setExTableName("buseo_member");
					mselectdto.setExColumn(mfield);
					mselectdto.setExWhereQuery(" WHERE ct_codeno='"+ (String) datamap2.get("ct_codeno") +"' "+search2);
					mselectdto.setExKeyColumn("m_num");	//clob데이타가 있을경우 키컬럼 지정
					mselectdto.setExOrderByQuery(" ORDER BY m_code ");
					List<HashMap<String, String>> memberList2 = exdao.selectQueryTable(mselectdto);
					for (int k = 0; k < memberList2.size(); k++) {
						HashMap<String, Object> mdatamap2 = (HashMap) memberList2.get(k);
						mdatamap2.put("m_work2", Func.getTextmode(Func.nvl((String) mdatamap2.get("m_work"))));
					}
					datamap2.put("memberList", memberList2);
					//조직원리스트 ====================================
					
					//하위부서리스트 ====================================
					selectdto = new BuseoDTO();
					selectdto.setExTableName("buseo");
					selectdto.setExColumn(sfield);
					selectdto.setExOrderByQuery(" ORDER BY ct_code ");
					selectdto.setExWhereQuery(" WHERE ct_ref='"+ ct_idx2 +"' "+search_idx3);
					List<HashMap<String, String>> buseoList3 = exdao.selectQueryTable(selectdto);
					if(buseoList3.size() >0) {
						for (int n = 0; n < buseoList3.size(); n++) {
							HashMap<String, Object> datamap3 = (HashMap) buseoList3.get(n);
							
							//조직원리스트 ====================================
							mselectdto = new BuseoMemberDTO();
							mselectdto.setExTableName("buseo_member");
							mselectdto.setExColumn(mfield);
							mselectdto.setExWhereQuery(" WHERE ct_codeno='"+ (String) datamap3.get("ct_codeno") +"' "+search2);
							mselectdto.setExKeyColumn("m_num");	//clob데이타가 있을경우 키컬럼 지정
							mselectdto.setExOrderByQuery(" ORDER BY m_code ");
							List<HashMap<String, String>> memberList3 = exdao.selectQueryTable(mselectdto);
							for (int o = 0; o < memberList3.size(); o++) {
								HashMap<String, Object> mdatamap3 = (HashMap) memberList3.get(o);
								mdatamap3.put("m_work2", Func.getTextmode(Func.nvl((String) mdatamap3.get("m_work"))));
							}
							datamap3.put("memberList", memberList3);
							//조직원리스트 ====================================
						}
					}
					//하위부서리스트 ====================================
					datamap2.put("buseoList", buseoList3);
				}
				datamap.put("buseoList", buseoList2);
			}
		}
		return "/site/buseo/list";
	}

	/** 검색 */
	@Override
	public void search(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();

		String firstCodenoWhere = "";
		//부서리스트=======================================
		String bfield = "ct_idx, ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_codeno";

		BuseoDTO selectdto2 = new BuseoDTO();
		selectdto2.setExTableName("buseo");
		selectdto2.setExColumn(bfield);
		selectdto2.setExOrderByQuery(" ORDER BY ct_code ");
		String search_str2 = "  WHERE ct_depth='1' AND site_dir='"+builder_dir+"' ";
		selectdto2.setExWhereQuery(search_str2);
		
		List<HashMap<String, String>> buseoList = exdao.selectQueryTable(selectdto2);
		for (int i = 0; i < buseoList.size(); i++) {
			HashMap<String, Object> datamap = (HashMap) buseoList.get(i);

			int ct_idx = Func.cInt((String) datamap.get("ct_idx"));
			String ct_codeno = (String) datamap.get("ct_codeno");
			
			if("".equals(firstCodenoWhere)) firstCodenoWhere =  " ct_codeno like '"+ct_codeno + "%' ";
			else firstCodenoWhere += " OR ct_codeno like '"+ct_codeno + "%' ";

			selectdto2 = new BuseoDTO();
			selectdto2.setExTableName("buseo");
			selectdto2.setExColumn(bfield);
			selectdto2.setExOrderByQuery(" ORDER BY ct_code ");
			String search_str3 = "  WHERE ct_ref='"+ ct_idx +"' and ct_depth='2' ";
			selectdto2.setExWhereQuery(search_str3);
			List<HashMap<String, String>> buseoList2 = exdao.selectQueryTable(selectdto2);
			if(buseoList2.size() >0) {
				for (int j = 0; j < buseoList2.size(); j++) {
					HashMap<String, Object> datamap2 = (HashMap) buseoList2.get(j);
					int ct_idx2 = Func.cInt((String) datamap2.get("ct_idx"));
					String ct_codeno2 = (String) datamap2.get("ct_codeno");
					//
					selectdto2 = new BuseoDTO();
					selectdto2.setExTableName("buseo");
					selectdto2.setExColumn(bfield);
					selectdto2.setExOrderByQuery(" ORDER BY ct_code ");
					String search_str4 = "  WHERE ct_ref='"+ ct_idx2 +"' and ct_depth='3' ";
					selectdto2.setExWhereQuery(search_str4);
					List<HashMap<String, String>> buseoList3 = exdao.selectQueryTable(selectdto2);
					if(buseoList3.size() >0) {
						for (int k = 0; k < buseoList3.size(); k++) {
							HashMap<String, Object> datamap3 = (HashMap) buseoList3.get(k);
							String ct_codeno3 = (String) datamap3.get("ct_codeno");
							datamap3.put("ct_codeno", ct_codeno3);
						}
					}
					datamap2.put("ct_codeno", ct_codeno2);
					datamap2.put("list", buseoList3);
				}
				datamap.put("ct_codeno", ct_codeno);
				datamap.put("list", buseoList2);
			}
		}
		request.setAttribute("buseoList", buseoList);
		//부서리스트========================================
		
		String sfield = "m_num, m_name, m_tel, m_fax, ct_idx, m_code, m_wdate, m_mdate, m_work, ct_codeno ";
		String codeno 		= Func.cStr(exdao.filter(request.getParameter("codeno")));
		String v_keyword 	= Func.cStr(exdao.filter(request.getParameter("v_keyword")));
		String v_search 	= Func.cStr(exdao.filter(request.getParameter("v_search"))) == null ? "m_name" : Func.cStr(exdao.filter(request.getParameter("v_search")));
		
		String ct_idx_1 = "", ct_idx_2 = "", ct_idx_3 = "";
		
		if(!"".equals(Func.nvl(codeno))) {
			String[] ref_codeno_arr = codeno.split("\\;");
			ct_idx_1 = ref_codeno_arr[1].replaceAll("C","").replaceAll(";","");
			
			if(ref_codeno_arr.length >= 3){
				ct_idx_2 = ref_codeno_arr[2].replaceAll("C","").replaceAll(";","");
			}
			if(ref_codeno_arr.length >= 4){
				ct_idx_3 = ref_codeno_arr[3].replaceAll("C","").replaceAll(";","");
			}
		}
		//검색조건
		request.setAttribute("codeno", codeno);
		request.setAttribute("v_search", v_search);
		request.setAttribute("v_keyword", v_keyword);
		//키워드없으면 리턴
		if("".equals(Func.nvl(v_keyword)) && "".equals(codeno)) {
			return;
		}
		//
		String idx_str = "";
		BuseoDTO selectdto = new BuseoDTO();
		if(!"".equals(Func.nvl(v_keyword))) {
			selectdto.setExTableName("buseo");
			selectdto.setExColumn(bfield);
			selectdto.setExOrderByQuery(" ORDER BY ct_code ");
			selectdto.setExKeyColumn("ct_idx");
			search_str2 = "  WHERE 1=1 ";
			search_str2 += " AND ct_codeno in ( select ct_codeno FROM buseo_member WHERE "+ v_search +" like '%"+ v_keyword +"%') ";
			if(!"".equals(Func.nvl(codeno))) { 
				search_str2 += " AND ct_codeno like '"+ codeno +"%' ";
			}
			if (!"".equals(firstCodenoWhere)){
				search_str2 += " AND ( " + firstCodenoWhere+" ) ";
			}
			selectdto.setExWhereQuery(search_str2);
			List<HashMap<String, String>> buseoTmpList = exdao.selectQueryTable(selectdto);
			
			for (int i = 0; i < buseoTmpList.size(); i++) {
				HashMap<String, Object> datamap = (HashMap) buseoTmpList.get(i);
				
				String ct_codeno = (String) datamap.get("ct_codeno");
				if(!"".equals(ct_codeno)) {
					String[] ref_codeno_arr = ct_codeno.split("\\;");
					String ct_idx_tmp = ref_codeno_arr[1].replaceAll("C","").replaceAll(";","");
					
					idx_str += "'"+ct_idx_tmp+"', ";
				}
			}
		}
		if(!"".equals(Func.nvl(v_keyword)) && "".equals(idx_str)) {
			return;
		}
			
		//상위정보 가져오기
		selectdto = new BuseoDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn(bfield);
		selectdto.setExOrderByQuery(" ORDER BY ct_code ");
		selectdto.setExKeyColumn("ct_idx");
		search_str2 = "  WHERE ct_depth='1' ";
		if(!"".equals(Func.nvl(idx_str))) {
			search_str2 += "  AND ct_idx in ("+idx_str.substring(0, idx_str.length()-2)+") ";
		}else {
			search_str2 += "  AND ct_idx='"+ ct_idx_1 +"' ";
		}
		selectdto.setExWhereQuery(search_str2);
		List<HashMap<String, String>> resultList = exdao.selectQueryTable(selectdto);
		for (int i = 0; i < resultList.size(); i++) {
			HashMap<String, Object> datamap = (HashMap) resultList.get(i);
			
			//하위부서
			selectdto = new BuseoDTO();
			selectdto.setExTableName("buseo");
			selectdto.setExColumn(bfield);
			selectdto.setExOrderByQuery(" ORDER BY ct_code ");
			selectdto.setExKeyColumn("ct_idx");
			//search_str2 = "  WHERE ct_depth = '3' AND ct_codeno like '"+ (String) datamap.get("ct_codeno") +"%' ";		// 1,2depth에 담당자가 배정된 경우 나오지 않아서 depth 제거
			search_str2 = "  WHERE ct_codeno like '"+ (String) datamap.get("ct_codeno") +"%' ";
			if(!"".equals(Func.nvl(v_keyword))) {
				search_str2 += " AND ct_codeno in ( select ct_codeno FROM buseo_member WHERE "+ v_search +" like '%"+ v_keyword +"%') ";
			}
			if(!"".equals(Func.nvl(codeno))) {
				search_str2 += " AND ct_codeno like '"+ codeno +"%' ";
			}
			selectdto.setExWhereQuery(search_str2);
			List<HashMap<String, String>> resultList2 = exdao.selectQueryTable(selectdto);
			for (int j = 0; j < resultList2.size(); j++) {
				HashMap<String, Object> datamap2 = (HashMap) resultList2.get(j);
				
				BuseoMemberDTO mselectdto = new BuseoMemberDTO();
				mselectdto.setExTableName("buseo_member");
				mselectdto.setExColumn(sfield);
				String where_query = " WHERE ct_codeno='"+ (String) datamap2.get("ct_codeno") +"' ";
				if(!"".equals(Func.nvl(v_keyword))) {
					where_query += " AND "+ v_search +" like '%"+ v_keyword +"%' ";
				}
				mselectdto.setExWhereQuery(where_query);
				mselectdto.setExKeyColumn("m_num");	//clob데이타가 있을경우 키컬럼 지정
				mselectdto.setExOrderByQuery(" ORDER BY m_code ");
				List<HashMap<String, String>> buseoMemberList2 = exdao.selectQueryTable(mselectdto);
				for (int k = 0; k < buseoMemberList2.size(); k++) {
					HashMap<String, Object> mdatamap = (HashMap) buseoMemberList2.get(k);
					mdatamap.put("m_work2", Func.getTextmode(Func.nvl((String) mdatamap.get("m_work"))));
				}
				datamap2.put("memberList", buseoMemberList2);
			}
			datamap.put("buseoList", resultList2);
		}
		request.setAttribute("resultList", resultList);
	}

}
