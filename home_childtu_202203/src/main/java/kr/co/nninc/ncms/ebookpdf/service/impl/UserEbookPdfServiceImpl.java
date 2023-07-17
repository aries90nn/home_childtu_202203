package kr.co.nninc.ncms.ebookpdf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.ebookpdf.service.EbookpdfDTO;
import kr.co.nninc.ncms.ebookpdf.service.UserEbookpdfService;

@Service("userEbookpdfService")
public class UserEbookPdfServiceImpl implements UserEbookpdfService{

	// 동적DAO
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_search = exdao.filter(request.getParameter("v_search"));

		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 6;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("ebookpdf");
		String s_fileds = exdao.getFieldNames(new EbookpdfDTO());
		selectdto.setExColumn(s_fileds);
		
		String whereQuery = "where eb_chk = 'Y'";
		if(!"".equals(v_keyword) && !"".equals(v_search)){
			whereQuery +=" and "+v_search+" like '%"+v_keyword+"%' ";
		}
		
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		if(!"".equals(builder_dir)){
			whereQuery += " and site_dir = '"+builder_dir+"' ";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY eb_code desc");
		
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		
		List<HashMap<String,String>> ebookpdfList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( ebookpdfList.get(0).get("totalcount") ); // 전체레코드 수
		request.setAttribute("recordcount", recordcount);
		ebookpdfList.remove(0);	//총검색개수행(첫번째행)을 삭제
		request.setAttribute("ebookpdfList", ebookpdfList);
		
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수
		
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = pagePerBlock;	//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring2 = paging.setQueryString(request, "v_search, v_keyword");
		
		String pagingtag = paging.execute(querystring2);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pagesize", pagesize);
		//페이징문자열 생성 끝
	}

	@Override
	public void viewer(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String eb_idx = exdao.filter(request.getParameter("eb_idx"));
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("ebookpdf");
		selectdto.setExColumn(new EbookpdfDTO());
		selectdto.setExWhereQuery("where eb_idx = '"+eb_idx+"'");
		HashMap<String,String>ebookpdf = exdao.selectQueryRecord(selectdto);
		
		request.setAttribute("ebookpdf", ebookpdf);
	}

}
