package kr.co.nninc.ncms.buseo_member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.buseo.service.BuseoDTO;
import kr.co.nninc.ncms.buseo_member.service.BuseoMemberDTO;
import kr.co.nninc.ncms.buseo_member.service.BuseoMemberService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 조직원을 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2018.11.16
 * @version 1.0
 */
@Service("buseoMemberService")
public class BuseoMemberServiceImpl implements BuseoMemberService {
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
		
		String sfield = "m_num, m_name, m_tel, m_fax, ct_idx, m_code, m_wdate, m_mdate, m_work, ct_codeno ";
		String codeno = Func.cStr(exdao.filter(request.getParameter("codeno")));
		

		String codeno_where = " WHERE 1=1 ";
		String tmp_codeno_where = "";
		
		BuseoMemberDTO selectdto = new BuseoMemberDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn("ct_codeno, ct_name, ct_idx, ct_ref");
		selectdto.setExOrderByQuery(" ORDER BY ct_code ");
		selectdto.setExWhereQuery("WHERE ct_ref='0' AND site_dir='"+builder_dir+"' ");
		selectdto.setExKeyColumn("ct_idx");	//clob데이타가 있을경우 키컬럼 지정
		List<HashMap<String, String>> bdnaBuseoList = exdao.selectQueryTable(selectdto);
		for(int i=0; i<=bdnaBuseoList.size()-1; i++){
			HashMap<String, String> bdnaBuseo = bdnaBuseoList.get(i);
			
			String ct_codeno = bdnaBuseo.get("ct_codeno");
			tmp_codeno_where += " OR ct_codeno LIKE '"+ ct_codeno +"%'";
		}
		if(!"".equals(tmp_codeno_where)){
			codeno_where += " AND ( "+ tmp_codeno_where.substring(3) + " )";
		}
		selectdto = new BuseoMemberDTO();
		selectdto.setExTableName("buseo_member");
		selectdto.setExColumn(sfield);
			
		if(!"".equals(Func.nvl(codeno))) {
			codeno_where += " AND ct_codeno like '"+ codeno +"%'  ";
		}
		selectdto.setExWhereQuery(codeno_where);
		selectdto.setExKeyColumn("m_num");	//clob데이타가 있을경우 키컬럼 지정
		selectdto.setExOrderByQuery(" ORDER BY m_code ");
		List<HashMap<String, String>> buseoMemberList = exdao.selectQueryTable(selectdto);
		for (int i = 0; i < buseoMemberList.size(); i++) {
			HashMap<String, Object> datamap = (HashMap) buseoMemberList.get(i);
			
			selectdto = new BuseoMemberDTO();
			selectdto.setExTableName("buseo");
			selectdto.setExColumn("ct_name");
			selectdto.setExWhereQuery("WHERE ct_codeno ='" + datamap.get("ct_codeno") + "'");
			String ct_name = exdao.selectQueryColumn(selectdto);
			if(ct_name != null) {
				datamap.put("ct_name", ct_name);
			}
			datamap.put("m_work2", Func.getTextmode(Func.nvl((String) datamap.get("m_work"))));
		}
		request.setAttribute("buseoMemberList", buseoMemberList);
		
		//부서리스트
		String bfield = "ct_idx, ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_codeno";

		BuseoDTO selectdto2 = new BuseoDTO();
		selectdto2.setExTableName("buseo");
		selectdto2.setExColumn(bfield);
		selectdto2.setExOrderByQuery(" ORDER BY ct_code ");

		String search_str2 = "  WHERE ct_depth='1' ";
		String tmp_idx_where = "";
		for(int i=0; i<=bdnaBuseoList.size()-1; i++){
			HashMap<String, String> bdnaBuseo = bdnaBuseoList.get(i);
			
			String ct_idx = bdnaBuseo.get("ct_idx");
			tmp_idx_where += ", '"+ ct_idx +"' ";
		}
		if(!"".equals(tmp_idx_where)){
			search_str2 += " AND ct_idx in ("+tmp_idx_where.substring(2)+") ";
		}
		selectdto2.setExWhereQuery(search_str2);
		
		List<HashMap<String, String>> buseoList = exdao.selectQueryTable(selectdto2);
		for (int i = 0; i < buseoList.size(); i++) {
			HashMap<String, Object> datamap = (HashMap) buseoList.get(i);

			int ct_idx = Func.cInt((String) datamap.get("ct_idx"));
			String ct_codeno = (String) datamap.get("ct_codeno");

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
		request.setAttribute("codeno", codeno);
		
		Func.getNowPage(request);
		return "/ncms/buseo_member/write";
	}
	
	/** 등록 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		BuseoMemberDTO memberdto = (BuseoMemberDTO) Func.requestAll(map.get("buseomemberdto"));

		String codeno = Func.cStr(exdao.filter(request.getParameter("codeno")));
		if(codeno == null) return messageService.backMsg(model, "잘못된 접근입니다.");

		BuseoMemberDTO selectdto = new BuseoMemberDTO();
		selectdto.setExTableName("buseo_member");
		selectdto.setExColumn("max(m_code)");
		if (!"".equals(Func.nvl(codeno))) {
			selectdto.setExWhereQuery(" WHERE ct_codeno='"+codeno+"' ");
		}
		int m_code = Func.cInt(exdao.selectQueryColumn(selectdto))+1;
		
		//부서코드
		selectdto = new BuseoMemberDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn("ct_idx");
		selectdto.setExWhereQuery(" WHERE ct_codeno='"+codeno+"' ");
		int ct_idx = Func.cInt(exdao.selectQueryColumn(selectdto));
		
		memberdto.setCt_idx(Func.cStr(ct_idx));
		memberdto.setM_code(Func.cStr(m_code));
		memberdto.setM_wdate(Func.date("Y-m-d H:i:s"));
		memberdto.setM_work(Func.InputValueXSS(request.getParameter("m_work")));
		memberdto.setM_num(null);
		memberdto.setExTableName("buseo_member");
		exdao.insert(memberdto);

		Func.writeManagerLog("부서조직원 등록[" + memberdto.getM_name() + "]", request);
		
		codeno =memberdto.getCt_codeno();
		String prepage = "/ncms/buseo_member/write.do?codeno="+Func.urlEncode(codeno);
		if ("".equals(Func.nvl(codeno))) {
			prepage = "/ncms/buseo_member/write.do";
		}
		//폴더명 붙이기
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		if(!"".equals(builder_dir)){
			prepage = "/" + builder_dir + prepage;
		}
		request.setAttribute("codeno", codeno);
		return "redirect:" + prepage;
	}

	/** 수정 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		BuseoMemberDTO memberdto = (BuseoMemberDTO) Func.requestAll(map.get("buseomemberdto"));

		String m_num = exdao.filter(request.getParameter("m_num"));
		String codeno = Func.cStr(exdao.filter(request.getParameter("codeno")));
		
		//부서코드
		BuseoMemberDTO selectdto = new BuseoMemberDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn("ct_idx");
		selectdto.setExWhereQuery(" WHERE ct_codeno='"+codeno+"' ");
		int ct_idx = Func.cInt(exdao.selectQueryColumn(selectdto));
		
		memberdto.setCt_codeno(codeno);
		memberdto.setCt_idx(Func.cStr(ct_idx));
		memberdto.setM_num(null);
		memberdto.setM_code(null);
		memberdto.setM_wdate(Func.date("Y-m-d H:i:s"));
		memberdto.setM_work(Func.InputValueXSS(request.getParameter("m_work")));
		memberdto.setExTableName("buseo_member");
		memberdto.setExWhereQuery("WHERE m_num='" + m_num + "'");
		exdao.update(memberdto);

		Func.writeManagerLog("부서조직원 수정[" + memberdto.getM_name() + "]", request);
		
		
		//폴더명 붙이기
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		String prepage = "/ncms/buseo_member/write.do?codeno="+Func.urlEncode(codeno);
		if ("".equals(Func.nvl(codeno))) {
			prepage = "/ncms/buseo_member/write.do";
		}
		if(!"".equals(builder_dir)){
			prepage = "/" + builder_dir + prepage;
		}
		request.setAttribute("codeno", codeno);
		return "redirect:" + prepage;
	}

	/** 삭제 */
	@Override
	public String deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String codeno = Func.cStr(exdao.filter(request.getParameter("codeno")));
		String prepage = "/ncms/buseo_member/write.do?codeno="+Func.urlEncode(codeno);
		if ("".equals(Func.nvl(codeno))) {
			prepage = "/ncms/buseo_member/write.do";
		}
		//폴더명 붙이기
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		if(!"".equals(builder_dir)){
			prepage = "/" + builder_dir + prepage;
		}
		String[] arr_chk = request.getParameterValues("chk");
		String m_num = exdao.filter(request.getParameter("m_num"));
		String status = exdao.filter(request.getParameter("status"));
		if ("totdel".equals(status)) {
			for (int z = 0; z < arr_chk.length; z++) {
				deleteProc(request, arr_chk[z]);
			}
		} else {
			deleteProc(request, m_num);
		}
		request.setAttribute("codeno", codeno);
		return "redirect:" + prepage;
	}
	
	public void deleteProc(HttpServletRequest request, String m_num) throws Exception {
		BuseoMemberDTO selectdto = new BuseoMemberDTO();
		selectdto.setExTableName("buseo_member");
		selectdto.setExColumn("m_name");
		selectdto.setExWhereQuery("WHERE m_num='" + m_num + "'");
		HashMap<String, String> edusat = exdao.selectQueryRecord(selectdto);
		if (edusat.size() > 0) {
			exdao.executeQuery("DELETE FROM buseo_member WHERE m_num='" +m_num + "'");

			Func.writeManagerLog("부서조직원 삭제[" + (String) edusat.get("m_name") + "]", request);
		}
	}

	/** 순서리스트 */
	@Override
	public void listMove(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		
		String sfield = "m_num, m_name, m_tel, m_fax, ct_idx, m_code, m_wdate, m_mdate, m_work, ct_codeno ";
		String codeno = Func.cStr(exdao.filter(request.getParameter("codeno")));
		
		String codeno_where = " WHERE 1=1 ";
		String tmp_codeno_where = "";
		
		BuseoMemberDTO selectdto = new BuseoMemberDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn("ct_codeno, ct_name, ct_idx, ct_ref");
		selectdto.setExOrderByQuery(" ORDER BY ct_code ");
		selectdto.setExWhereQuery("WHERE ct_ref='0' AND site_dir='"+builder_dir+"' ");
		selectdto.setExKeyColumn("ct_idx");	//clob데이타가 있을경우 키컬럼 지정
		List<HashMap<String, String>> buseoList = exdao.selectQueryTable(selectdto);
		for(int i=0; i<=buseoList.size()-1; i++){
			HashMap<String, String> bdnaBuseo = buseoList.get(i);
			
			String ct_codeno = bdnaBuseo.get("ct_codeno");
			tmp_codeno_where += " OR ct_codeno LIKE '"+ ct_codeno +"%'";
		}
		if(!"".equals(tmp_codeno_where)){
			codeno_where += " AND ( "+ tmp_codeno_where.substring(3) + " )";
		}
		selectdto = new BuseoMemberDTO();
		selectdto.setExTableName("buseo_member");
		selectdto.setExColumn(sfield);
			
		if(!"".equals(Func.nvl(codeno))) {
			codeno_where += " AND ct_codeno like '"+ codeno +"%' ";
		}
		selectdto.setExWhereQuery(codeno_where);
		selectdto.setExKeyColumn("m_num");	//clob데이타가 있을경우 키컬럼 지정
		selectdto.setExOrderByQuery(" ORDER BY m_code ");
		List<HashMap<String, String>> buseoMemberList = exdao.selectQueryTable(selectdto);
		request.setAttribute("buseoMemberList", buseoMemberList);
		request.setAttribute("codeno", codeno);
		
		selectdto = new BuseoMemberDTO();
		selectdto.setExTableName("buseo");
		selectdto.setExColumn("ct_name");
		selectdto.setExWhereQuery("WHERE ct_codeno ='" + codeno + "'");
		String ct_name = exdao.selectQueryColumn(selectdto);
		request.setAttribute("ct_name", ct_name);
	}

	/** 순서일괄저장 */
	@Override
	public String listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String codeno = Func.cStr(exdao.filter(request.getParameter("codeno")));
		
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String m_num = chk[z];
			
			//순서수정
			exdao.executeQuery("UPDATE buseo_member SET m_code='"+(z + 1)+"' WHERE m_num='" + m_num + "'");
		}
		//작업기록
		Func.writeManagerLog ("부서조직원 일괄순서변경", request);
		
		String prepage = "/ncms/buseo_member/write.do?codeno="+Func.urlEncode(codeno);
		if ("".equals(Func.nvl(codeno))) {
			prepage = "/ncms/buseo_member/write.do";
		}
		//폴더명 붙이기
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		if(!"".equals(builder_dir)){
			prepage = "/" + builder_dir + prepage;
		}
		return messageService.closeRedirect(model, "", prepage);
	}
}
