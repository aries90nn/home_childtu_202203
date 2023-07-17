package kr.co.nninc.ncms.ebook.service.impl;

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
import kr.co.nninc.ncms.ebook.service.EbookDTO;
import kr.co.nninc.ncms.ebook.service.UserEbookService;
import kr.co.nninc.ncms.ebook_page.service.EbookPageDTO;


/**
 * 이북을 관리하기 위한 서비스 구현 클래스(사용자용)
 * 
 * @author 나눔
 * @since 2017.11.03
 * @version 1.0
 */
@Service("userEbookService")
public class UserEbookServiceImpl extends EgovAbstractServiceImpl implements UserEbookService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**
	 * @title : 이북
	 * @method : index
	 */
	@Override
	public String index(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//검색어
		String v_search = exdao.filter( request.getParameter("v_search") );
		String v_keyword = exdao.filter( request.getParameter("v_keyword") );
		request.setAttribute("v_search", v_search);
		request.setAttribute("v_keyword", v_keyword);
		
		String eb_pk = Func.nvl(request.getParameter("eb_pk")).trim();
		String is_size = Func.nvl(request.getParameter("is_size")).trim();
		if(!"".equals(is_size)) {
			request.getSession().setAttribute("is_size", is_size);
		}
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("ebook");
		//없으면 하나조회하기
		if("".equals(eb_pk)) {
			selectdto.setExColumn("eb_pk");
			selectdto.setExOrderByQuery("ORDER BY eb_code ASC");
			eb_pk = exdao.selectQueryColumn(selectdto);
			if(eb_pk == null || "".equals(eb_pk)) {
				return messageService.backMsg(model, "등록된 데이터가 없습니다.");
			}
		}
		//이북조회하기
		selectdto = new SelectDTO();
		selectdto.setExTableName("ebook");
		selectdto.setExColumn(new EbookDTO());
		selectdto.setExWhereQuery("where eb_pk = '"+eb_pk+"'");
		HashMap<String, String>ebook_map = exdao.selectQueryRecord(selectdto);
		
		//이북리스트
		selectdto = new SelectDTO();
		selectdto.setExTableName("ebook");
		selectdto.setExColumn(new EbookDTO());
		String whereQuery = "where eb_chk='Y' ";
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY eb_code ASC");
		List<HashMap<String,String>>ebookList = exdao.selectQueryTable(selectdto);

		String flippingBook_pages = "";
		String flippingBook_contents = "";
		
		//이북페이지
		SelectDTO selectdto2 = new SelectDTO();
		selectdto2.setExTableName("ebook_page");
		selectdto2.setExColumn(new EbookPageDTO());
		whereQuery = "where eb_pk='"+eb_pk+"' ";
		if(!"".equals(v_keyword)){
			whereQuery = whereQuery + " and ebp_subject like '%"+v_keyword+"%'";
		}
		selectdto2.setExWhereQuery(whereQuery);
		selectdto2.setExOrderByQuery("ORDER BY ebp_code ASC");
		List<HashMap<String,String>>ebookPageList = exdao.selectQueryTable(selectdto2);
		for(int i=0;  i<ebookPageList.size(); i++) {
			HashMap<String,String> ebookPage = ebookPageList.get(i);
			
			if(i > 0){flippingBook_pages = flippingBook_pages+", ";}
			if("".equals(Func.nvl(ebookPage.get("ebp_pageimg")))){
				flippingBook_pages = flippingBook_pages+"''";
			}else{
				flippingBook_pages = flippingBook_pages+"'/data/ebook/"+ebookPage.get("eb_pk")+"/middle/"+ebookPage.get("ebp_pageimg")+"' ";
			}
			if(i > 0){flippingBook_contents = flippingBook_contents+", ";}
			flippingBook_contents = flippingBook_contents+" [ '"+ebookPage.get("ebp_subject")+"', "+(i+1)+" ]  ";
		}
		
		request.setAttribute("ebook", ebook_map);
		request.setAttribute("ebookList", ebookList);
		request.setAttribute("flippingBook_pages", flippingBook_pages);
		request.setAttribute("flippingBook_contents", flippingBook_contents);
		return "/site/ebook/skin"+ebook_map.get("eb_skin")+"/index";
	}

	/**
	 * @title : 이북리스트
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//검색어
		String v_search = exdao.filter( request.getParameter("v_search") );
		String v_keyword = exdao.filter( request.getParameter("v_keyword") );
		request.setAttribute("v_search", v_search);
		request.setAttribute("v_keyword", v_keyword);
	
		//현재 페이지
		int v_page = Func.cInt(request.getParameter("v_page"));
		if(v_page == 0){v_page = 1;}
		//목록갯수
		int pagesize = 10;
		
		//select 전용dto
		EbookDTO selectdto = new EbookDTO();
		//목록조회
		selectdto.setExTableName("ebook a");
		String s_fields = "eb_idx, eb_subject, eb_code, eb_skin, eb_logoimg, eb_wdate, eb_mdate, eb_chk, eb_pk, eb_viewtype, eb_width, eb_height, eb_width2, eb_height2";
		s_fields += ", (select count(*) from ebook_page b where b.eb_pk = a.eb_pk) as ebp_cnt";
		selectdto.setExColumn(s_fields);
		String whereQuery = " where eb_chk='Y' ";
		if(!"".equals(v_keyword)){
			whereQuery = "and "+v_search+" like '%"+v_keyword+"%'";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY eb_code ASC");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		List<HashMap<String,String>>ebookList = exdao.selectQueryPage(selectdto);
		int recordcount = Func.cInt( ebookList.get(0).get("totalcount") );	//총검색개수 추출
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수 
		ebookList.remove(0);	//총검색개수행(첫번째행)을 삭제
		request.setAttribute("recordcount", recordcount);
		request.setAttribute("ebookList", ebookList);
		
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = 10;				//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring = paging.setQueryString(request, "v_search, v_keyword");
		/*
		 * Paging.setQueryString 함수는 아래와 같은 값을 반환단다.
		querystring = "v_search="+Func.urlEncode(v_search);
		querystring += "&v_keyword="+Func.urlEncode(v_keyword);
		*/
		String pagingtag = paging.execute(querystring);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		
		//현재URL 전송
		Func.getNowPage(request);
		
	}

}
