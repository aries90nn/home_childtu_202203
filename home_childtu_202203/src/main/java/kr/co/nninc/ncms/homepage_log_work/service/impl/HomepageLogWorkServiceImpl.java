package kr.co.nninc.ncms.homepage_log_work.service.impl;

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
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.homepage_log_work.service.HomepageLogWorkService;


/**
 * 로그를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.10.18
 * @version 1.0
 */
@Service("logWorkService")
public class HomepageLogWorkServiceImpl extends EgovAbstractServiceImpl implements HomepageLogWorkService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	
	/**
	 * @title : 로그관리
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		
		String v_search = exdao.filter( request.getParameter("v_search") );
		String v_keyword = exdao.filter( request.getParameter("v_keyword") );
		
		String v_type = exdao.filter( request.getParameter("v_type") );
		//로그인기록만 보이게하기
		//String v_type = "4";
		
		
		String yy = exdao.filter(request.getParameter("yy"));
		String mm = exdao.filter(request.getParameter("mm"));
		String dd = exdao.filter(request.getParameter("dd"));
		if("".equals(yy)) yy = Func.date("Y");
		if("".equals(mm)) mm = Func.date("m");
		if("".equals(dd)) dd = Func.date("d");
		// setAttribute
		request.setAttribute("yy", yy);
		request.setAttribute("mm", mm);
		request.setAttribute("dd", dd);
		
		int lastDay = Func.lastDay(Func.cInt(yy), Func.cInt(mm), 1);
		request.setAttribute("lastDay", lastDay);

		//검색조건 추가 예제
		String v_ymd = "";
		if(!"all".equals(yy)){
			v_ymd = yy;
			if(!"all".equals(mm)){
				v_ymd += Func.zerofill(mm, 2, "0");
				if(!"all".equals(dd)){
					v_ymd += Func.zerofill(dd, 2, "0");
				}
			}
		}
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("homepage_log_work");
		selectdto.setExColumn("hl_idx, hl_type, hl_job, hl_id, hl_name, hl_ci, hl_ip, hl_ymd, hl_regdate");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		String where_query = "";
		if(!"".equals(v_search) && !"".equals(v_keyword)){
			where_query += " and "+v_search+" like '%"+v_keyword+"%'";
		}
		if(!"".equals(v_ymd)){
			where_query += " and hl_ymd like '"+v_ymd+"%'";
		}
		if(!"".equals(v_type)){
			where_query += " and hl_type = '"+v_type+"'";
		}
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){where_query += " and site_dir ='"+builder_dir+"'";}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("ORDER BY hl_idx DESC");
		
		List<HashMap<String,String>> logList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( logList.get(0).get("totalcount") ); // 전체레코드 수
		
		request.setAttribute("recordcount", recordcount);
		logList.remove(0);	//총검색개수행(첫번째행)을 삭제
		
		request.setAttribute("logList", logList);
		
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수
		
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = pagePerBlock;	//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring2 = paging.setQueryString(request, "v_search, v_keyword, yy, mm, dd, v_type");
		
		String pagingtag = paging.execute(querystring2);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pagesize", pagesize);
		//페이징문자열 생성 끝
	}

}
