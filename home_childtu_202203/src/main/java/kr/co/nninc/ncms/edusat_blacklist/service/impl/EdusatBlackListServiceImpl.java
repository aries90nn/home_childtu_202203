package kr.co.nninc.ncms.edusat_blacklist.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.edusat.service.EdusatDTO;
import kr.co.nninc.ncms.edusat.service.EdusatRequestDTO;
import kr.co.nninc.ncms.edusat_blacklist.service.EdusatBlackListDTO;
import kr.co.nninc.ncms.edusat_blacklist.service.EdusatBlackListService;

@Service("edusatBlackListService")
public class EdusatBlackListServiceImpl extends EgovAbstractServiceImpl implements EdusatBlackListService {
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	

	/**
	 * @title : 신청자경고 리스트
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String ss_g_num = Func.getSession(request, "ss_g_num");
		String sh_ct_idx = Func.nvl(request.getParameter("sh_ct_idx"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		
		SelectDTO selectdto = new SelectDTO();
		
		//도서관 코드 가져오기
		selectdto.setExTableName("code_config");
		selectdto.setExColumn("ct_idx, ct_name, ct_codeno");
		String where_query = "where ct_ref=1 and ct_chk ='Y'";
		if(!"1".equals(ss_g_num) && !"4".equals(ss_g_num)){//총관리자아니면 해당 도서관만
			where_query += " and ct_g_num = '"+exdao.filter(ss_g_num)+"'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by ct_code");
		List<HashMap<String,String>>libList = exdao.selectQueryTable(selectdto);
		request.setAttribute("libList", libList);
		
		
		//경고리스트
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_blacklist");
		selectdto.setExColumn(new EdusatBlackListDTO());
		
		String whereQuery = "";
		//자기도서관해당하는것만 보기
		if(!"1".equals(ss_g_num) && !"4".equals(ss_g_num) && "".equals(sh_ct_idx)) {
			if(libList.size() > 0) {
				sh_ct_idx = libList.get(0).get("ct_idx");
			}
		}
		if(!"".equals(sh_ct_idx)){ whereQuery += " and ct_idx ='"+sh_ct_idx+"'"; }
		if(!"".equals(v_search) && !"".equals(v_keyword)){ whereQuery += " and "+v_search+" like '%"+v_keyword+"%'"; }
		selectdto.setExWhereQuery(whereQuery);
		
		selectdto.setExOrderByQuery("order by eb_num desc");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		
		List<HashMap<String,String>> edusatBlackList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( edusatBlackList.get(0).get("totalcount") ); // 전체레코드 수
		
		request.setAttribute("recordcount", recordcount);
		edusatBlackList.remove(0);	//총검색개수행(첫번째행)을 삭제
		// setAttribute
		request.setAttribute("edusatBlackList", edusatBlackList);
		
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수
		
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = pagePerBlock;	//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring2 = paging.setQueryString(request, "v_search, v_keyword, sh_ct_idx");
		
		String pagingtag = paging.execute(querystring2);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pagesize", pagesize);
		//페이징문자열 생성 끝
		
		
	}

	/**
	 * @title : 신청자경고 팝업창
	 * @method : write
	 */
	@Override
	public String write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String es_idx = exdao.filter( request.getParameter("es_idx") );
		
		if("".equals(es_idx)) {
			return messageService.closeRedirect(model, "잘못된 요청입니다.", "");
		}
		
		//신청자 정보
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_request");
		selectdto.setExKeyColumn("es_idx");
		selectdto.setExColumn(new EdusatRequestDTO());
		selectdto.setExWhereQuery("where es_idx = '"+es_idx+"'");
		HashMap<String,String>edusatrequest = exdao.selectQueryRecord(selectdto);
		
		//강좌정보
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat");
		selectdto.setExKeyColumn("edu_idx");
		selectdto.setExColumn(new EdusatDTO());
		selectdto.setExWhereQuery("where edu_idx = '"+edusatrequest.get("edu_idx")+"'");
		HashMap<String,String>edusat = exdao.selectQueryRecord(selectdto);
		
		//데이터담기
		HashMap<String,String>edublacklistdto = new HashMap<String,String>();
		edublacklistdto.put("edu_idx",edusatrequest.get("edu_idx"));
		edublacklistdto.put("es_name",edusatrequest.get("es_name"));
		edublacklistdto.put("es_bphone1",edusatrequest.get("es_bphone1"));
		edublacklistdto.put("es_bphone2",edusatrequest.get("es_bphone2"));
		edublacklistdto.put("es_bphone3",edusatrequest.get("es_bphone3"));
		edublacklistdto.put("es_wdate",edusatrequest.get("es_wdate"));
		edublacklistdto.put("es_id",edusatrequest.get("edu_m_id"));
		edublacklistdto.put("edu_lib",edusat.get("edu_lib"));
		edublacklistdto.put("edu_gubun",edusat.get("edu_gubun"));
		edublacklistdto.put("edu_subject",edusat.get("edu_subject"));
		edublacklistdto.put("edu_resdate",edusat.get("edu_resdate"));
		edublacklistdto.put("edu_reedate",edusat.get("edu_reedate"));
		edublacklistdto.put("edu_sdate",edusat.get("edu_sdate"));
		edublacklistdto.put("edu_edate",edusat.get("edu_edate"));
		edublacklistdto.put("ct_idx",edusat.get("ct_idx"));
		edublacklistdto.put("ct_idx2",edusat.get("ct_idx2"));
		edublacklistdto.put("edu_ci",edusatrequest.get("edu_ci"));
		request.setAttribute("edublacklist", edublacklistdto);
		
		//누적경고
		selectdto = new SelectDTO();
		selectdto.setExTableName("edusat_blacklist");
		selectdto.setExColumn("COUNT(*)");
		String whereQuery = "where (es_id = '"+edusatrequest.get("edu_m_id")+"' and edu_ci = '"+edusatrequest.get("edu_ci")+"')";
		whereQuery += "or (es_name = '"+edusatrequest.get("es_name")+"' and es_bphone1 = '"+edusatrequest.get("es_bphone1")+"' and es_bphone2 = '"+edusatrequest.get("es_bphone2")+"' and es_bphone3 = '"+edusatrequest.get("es_bphone3")+"')";
		selectdto.setExWhereQuery(whereQuery);
		int alert_count = Func.cInt( exdao.selectQueryColumn(selectdto) );
		request.setAttribute("alert_count", alert_count);
		
		return "/ncms/edusat_blacklist/write";
	}

	/**
	 * @title : 신청자경고 등록처리
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		EdusatBlackListDTO edusatblacklistdto = (EdusatBlackListDTO) map.get("edusatblacklistdto");
		
		edusatblacklistdto.setEb_w_id(Func.getSession(request, "ss_m_id"));
		edusatblacklistdto.setEb_wdate(Func.date("Y-m-d H:i:s"));
		
		//insert 제외
		edusatblacklistdto.setEb_num(null);
		
		//insert
		edusatblacklistdto.setExTableName("edusat_blacklist");
		exdao.insert(edusatblacklistdto);
		
		return messageService.closeRedirect(model, "해당 신청자를 경고처리 하였습니다.", "list.do");
	}

	/**
	 * @title : 신청자경고 수정처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		EdusatBlackListDTO edusatblacklistdto = (EdusatBlackListDTO) map.get("edusatblacklistdto");
		
		//update 제외
		edusatblacklistdto.setEb_num(null);
		
		//update
		edusatblacklistdto.setExTableName("edusat_blacklist");
		exdao.update(edusatblacklistdto);
		
		return messageService.closeRedirect(model, "해당 신청자를 경고처리 하였습니다.", "list.do");
	}

	
	/**
	 * @title : 신청자경고 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status = Func.nvl( request.getParameter("status") ).trim();
		String[] chk = request.getParameterValues("chk");
		String eb_num = exdao.filter(request.getParameter("eb_num"));
		
		if("totdel".equals(status)) {
			for(int z=0;z <= chk.length-1; z++){
				exdao.executeQuery("delete from edusat_blacklist where eb_num = '"+exdao.filter(chk[z])+"'");
			}
		}else {
			exdao.executeQuery("delete from edusat_blacklist where eb_num = '"+eb_num+"'");
		}

	}

}
