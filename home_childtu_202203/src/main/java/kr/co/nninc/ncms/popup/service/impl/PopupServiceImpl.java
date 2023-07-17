package kr.co.nninc.ncms.popup.service.impl;

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
import kr.co.nninc.ncms.popup.service.PopupDTO;
import kr.co.nninc.ncms.popup.service.PopupService;

@Service("popupService")
public class PopupServiceImpl extends EgovAbstractServiceImpl implements PopupService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/**
	 * @title : 팝업등록 폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int sdate_y = Integer.parseInt(Func.date("Y"));
		int sdate_m = Integer.parseInt(Func.date("m"));
		int sdate_d = Integer.parseInt(Func.date("d"));
		int sdate_h = 0;
		int sdate_n = 0;
		
		int sdate_y_begin = sdate_y;
		int sdate_y_end = sdate_y_begin+2;
		
		//한달 뒤
		String edate = Func.addGetDate2(1, "yyyy-MM-dd");
		
		String[] edate_arr	= edate.split(" ");
		String[] edate_arr2	= edate_arr[0].split("-");
		
		int edate_y = Integer.parseInt(edate_arr2[0]);
		int edate_m = Integer.parseInt(edate_arr2[1]);
		int edate_d = Integer.parseInt(edate_arr2[2]);
		int edate_h = 23;
		int edate_n = 59;
		
		int edate_y_begin = sdate_y_begin;
		int edate_y_end = sdate_y_end;

		request.setAttribute("sdate_y", sdate_y);
		request.setAttribute("sdate_m", sdate_m);
		request.setAttribute("sdate_d", sdate_d);
		request.setAttribute("sdate_h", sdate_h);
		request.setAttribute("sdate_n", sdate_n);
		request.setAttribute("sdate_y_begin", sdate_y_begin);
		request.setAttribute("sdate_y_end", sdate_y_end);
		request.setAttribute("edate_y", edate_y);
		request.setAttribute("edate_m", edate_m);
		request.setAttribute("edate_d", edate_d);
		request.setAttribute("edate_h", edate_h);
		request.setAttribute("edate_n", edate_n);
		request.setAttribute("edate_y_begin", edate_y_begin);
		request.setAttribute("edate_y_end", edate_y_end);
		
		//빈맵 전송
		HashMap<String,String>popup = new HashMap<String,String>();
		request.setAttribute("popdto", popup);
		
		//현재URL 전송
		Func.getNowPage(request);

	}

	/**
	 * @title : 팝업등록처리
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		PopupDTO popupdto = (PopupDTO) map.get("popupdto");
		
		String sdate_y 		= Func.nvl(request.getParameter("sdate_y"));
		String sdate_m 		= Func.nvl(request.getParameter("sdate_m"));
		String sdate_d 		= Func.nvl(request.getParameter("sdate_d"));
		String sdate_h 		= Func.nvl(request.getParameter("sdate_h"));
		String sdate_n 		= Func.nvl(request.getParameter("sdate_n"));
		String edate_y 		= Func.nvl(request.getParameter("edate_y"));
		String edate_m 		= Func.nvl(request.getParameter("edate_m"));
		String edate_d 		= Func.nvl(request.getParameter("edate_d"));
		String edate_h 		= Func.nvl(request.getParameter("edate_h"));
		String edate_n 		= Func.nvl(request.getParameter("edate_n"));
	
		//변수초기화 =================================================
		if("".equals(popupdto.getScrollbars())) popupdto.setScrollbars("no");
		if("".equals(popupdto.getToolbar())) popupdto.setToolbar("no");
		if("".equals(popupdto.getMenubar())) popupdto.setMenubar("no");
		if("".equals(popupdto.getLocations())) popupdto.setLocations("no");
		if("".equals(popupdto.getW_width())) popupdto.setW_width("0");
		if("".equals(popupdto.getW_height())) popupdto.setW_height("0");
		if("".equals(popupdto.getW_top())) popupdto.setW_top("0");
		if("".equals(popupdto.getW_left())) popupdto.setW_left("0");
		if("".equals(popupdto.getCk_val())) popupdto.setCk_val("0");
		
		String wdate = Func.date("Y-m-d H:i:s");
		String mdate = Func.date("Y-m-d H:i:s");
		
		String sdate = sdate_y + "-" + Func.zerofill(sdate_m, 2, "0") + "-" + Func.zerofill(sdate_d, 2, "0")
		+ " " + Func.zerofill(sdate_h, 2, "0") + ":" + Func.zerofill(sdate_n, 2, "0");
		
		String edate = edate_y + "-" + Func.zerofill(edate_m, 2, "0") + "-" + Func.zerofill(edate_d, 2, "0")
		+ " " + Func.zerofill(edate_h, 2, "0") + ":" + Func.zerofill(edate_n, 2, "0");
		//변수초기화 =================================================
		
		popupdto.setSdate(sdate);
		if("Y".equals(popupdto.getUnlimited())) {
			popupdto.setEdate("");
		}else {
			popupdto.setEdate(edate);
		}
		popupdto.setWdate(wdate);
		popupdto.setMdate(mdate);
		
		//insert 제외 필드
		popupdto.setIdx(null);
		
		//사이트디렉토리등록
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			popupdto.setSite_dir(builder_dir);
		}
		
		//insert
		popupdto.setExTableName("popup");
		exdao.insert(popupdto);
		
		//작업기록
		Func.writeManagerLog ("팝업 생성 ["+popupdto.getSubject()+"]", request);
		
		
		if(!"".equals(builder_dir)){builder_dir = "/"+builder_dir;}
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = builder_dir+"/ncms/popup/list.do";}
		//System.out.println("writeOk prepage="+prepage);
		return "redirect:"+prepage;
	}

	/**
	 * @title : 팝업목록
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		SelectDTO selectdto = new SelectDTO();
		
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		request.setAttribute("v_keyword", v_keyword);
		request.setAttribute("v_search", v_search);
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		selectdto.setExTableName("popup");
		selectdto.setExKeyColumn("idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new PopupDTO());
		selectdto.setExRecordCount(pagesize);
		selectdto.setExPage(v_page);

		String whereQuery = "";
		if(!"".equals(v_keyword) && !"".equals(v_search)){
			whereQuery += "where "+v_search+" like '%"+v_keyword+"%' ";
		}
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			whereQuery += " and site_dir = '"+builder_dir+"'";
		}
		
		
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY idx DESC");
		List<HashMap<String,String>> popupList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( popupList.get(0).get("totalcount") ); // 전체레코드 수
		
		request.setAttribute("recordcount", recordcount);
		popupList.remove(0);	//총검색개수행(첫번째행)을 삭제
		// setAttribute
		request.setAttribute("popupList", popupList);
		
		
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
		//페이징문자열 생성 끝
		
	
		//현재경로 전송
		Func.getNowPage(request);
	}

	/**
	 * @title : 팝업보기
	 * @method : view
	 */
	@Override
	public void view(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String idx = Func.nvl( request.getParameter("idx") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("popup");
		selectdto.setExKeyColumn("idx");
		selectdto.setExColumn("idx, w_width, w_height, ck_chk, subject, ck_val, scrollbars, content");
		selectdto.setExWhereQuery("where idx = '"+idx+"'");
		HashMap<String,String>popup = exdao.selectQueryRecord(selectdto);
		request.setAttribute("popup", popup);
	}

	/**
	 * @title : 팝업수정 폼
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String idx = Func.nvl( request.getParameter("idx") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		selectdto.setExTableName("popup");
		selectdto.setExKeyColumn("idx");
		selectdto.setExColumn(new PopupDTO());
		
		selectdto.setExWhereQuery("where idx = '"+idx+"'");
		
		HashMap<String,String>popup = exdao.selectQueryRecord(selectdto);
		
		String sdate = Func.nvl(popup.get("sdate"));
		String sdate_y = Func.date("Y");
		String sdate_m = Func.date("m");
		String sdate_d = Func.date("d");
		String sdate_h = "0";
		String sdate_n = "0";
		if(!sdate.equals("")){
			String[] sdate_arr	= sdate.split(" ");
			String[] sdate_arr2	= sdate_arr[0].split("-");
			sdate_y = sdate_arr2[0];
			sdate_m = sdate_arr2[1];
			sdate_d = sdate_arr2[2];
			//
			sdate_arr2	= sdate_arr[1].split(":");
			sdate_h = sdate_arr2[0];
			sdate_n = sdate_arr2[1];
		}
		int sdate_y_begin =  Integer.parseInt(sdate_y);
		int sdate_y_end = sdate_y_begin+2;

		String edate = Func.nvl(popup.get("edate"));
		String edate_y = Func.date("Y");
		String edate_m = Func.date("m");
		String edate_d = Func.date("d");
		String edate_h = "23";
		String edate_n = "59";
		
		if(!edate.equals("")){
			String[] edate_arr	= edate.split(" ");
			String[] edate_arr2	= edate_arr[0].split("-");
			edate_y = edate_arr2[0];
			edate_m = edate_arr2[1];
			edate_d = edate_arr2[2];
			//
			edate_arr2	= edate_arr[1].split(":");
			edate_h = edate_arr2[0];
			edate_n = edate_arr2[1];
		}
		int edate_y_begin = sdate_y_begin;
		int edate_y_end = sdate_y_end;
		
		request.setAttribute("popdto", popup);
		
		
		request.setAttribute("sdate_y_begin", sdate_y);
		request.setAttribute("sdate_y_end", sdate_y);
		request.setAttribute("sdate_y", sdate_y);
		request.setAttribute("sdate_m", sdate_m);
		request.setAttribute("sdate_d", sdate_d);
		request.setAttribute("sdate_h", sdate_h);
		request.setAttribute("sdate_n", sdate_n);
		
		request.setAttribute("edate_y_begin", sdate_y);
		request.setAttribute("edate_y_end", sdate_y);
		request.setAttribute("edate_y", edate_y);
		request.setAttribute("edate_m", edate_m);
		request.setAttribute("edate_d", edate_d);
		request.setAttribute("edate_h", edate_h);
		request.setAttribute("edate_n", edate_n);
				
		//작업기록
		Func.writeManagerLog ("팝업 상세보기 ["+popup.get("subject")+"]", request);
	}

	/**
	 * @title : 팝업수정처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		PopupDTO popupdto = (PopupDTO) Func.requestAll( map.get("popupdto") );
		
		String idx = Func.nvl(request.getParameter("idx")).trim();
		
		String sdate_y 		= Func.nvl(request.getParameter("sdate_y"));
		String sdate_m 		= Func.nvl(request.getParameter("sdate_m"));
		String sdate_d 		= Func.nvl(request.getParameter("sdate_d"));
		String sdate_h 		= Func.nvl(request.getParameter("sdate_h"));
		String sdate_n 		= Func.nvl(request.getParameter("sdate_n"));
		String edate_y 		= Func.nvl(request.getParameter("edate_y"));
		String edate_m 		= Func.nvl(request.getParameter("edate_m"));
		String edate_d 		= Func.nvl(request.getParameter("edate_d"));
		String edate_h 		= Func.nvl(request.getParameter("edate_h"));
		String edate_n 		= Func.nvl(request.getParameter("edate_n"));
	
		//변수초기화 =================================================
		if("".equals(popupdto.getScrollbars())) popupdto.setScrollbars("no");
		if("".equals(popupdto.getToolbar())) popupdto.setToolbar("no");
		if("".equals(popupdto.getMenubar())) popupdto.setMenubar("no");
		if("".equals(popupdto.getLocations())) popupdto.setLocations("no");
		if("".equals(popupdto.getW_width())) popupdto.setW_width("0");
		if("".equals(popupdto.getW_height())) popupdto.setW_height("0");
		if("".equals(popupdto.getW_top())) popupdto.setW_top("0");
		if("".equals(popupdto.getW_left())) popupdto.setW_left("0");
		if("".equals(popupdto.getCk_val())) popupdto.setCk_val("0");
		
		String wdate = Func.date("Y-m-d H:i:s");
		String mdate = Func.date("Y-m-d H:i:s");
		
		String sdate = sdate_y + "-" + Func.zerofill(sdate_m, 2, "0") + "-" + Func.zerofill(sdate_d, 2, "0")
		+ " " + Func.zerofill(sdate_h, 2, "0") + ":" + Func.zerofill(sdate_n, 2, "0");
		
		String edate = edate_y + "-" + Func.zerofill(edate_m, 2, "0") + "-" + Func.zerofill(edate_d, 2, "0")
		+ " " + Func.zerofill(edate_h, 2, "0") + ":" + Func.zerofill(edate_n, 2, "0");
		//변수초기화 =================================================
		
		popupdto.setSdate(sdate);
		if("Y".equals(popupdto.getUnlimited())) {
			popupdto.setEdate("");
		}else {
			popupdto.setEdate(edate);
		}
		popupdto.setWdate(wdate);
		popupdto.setMdate(mdate);
		
		//수정제외 필드
		popupdto.setIdx(null);
		popupdto.setSite_dir(null);
		
		//수정처리
		popupdto.setExTableName("popup");
		popupdto.setExWhereQuery("where idx = '"+idx+"'");
		exdao.update(popupdto);
		
		//작업기록
		Func.writeManagerLog ("팝업 수정 ["+popupdto.getSubject()+"]", request);
		
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "/ncms/popup/list.do";}
		
		return "redirect:"+prepage;
	}

	/**
	 * @title : 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String status 		= Func.nvl(request.getParameter("status")).trim(); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String idx			= request.getParameter("idx");  //단일 삭제 사용
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("popup");
		selectdto.setExColumn("subject");
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				//쿼리조회
				selectdto.setExWhereQuery("where idx = '"+exdao.filter(chk[z])+"'");
				String subject = exdao.selectQueryColumn(selectdto);
				
				String sql = "DELETE FROM popup WHERE idx = '"+exdao.filter(chk[z])+"'";
				exdao.executeQuery(sql);
				
				//작업기록
				Func.writeManagerLog ("팝업 삭제 ["+subject+"]", request);
			}
		}else{
			//쿼리조회
			selectdto.setExWhereQuery("where idx = '"+exdao.filter(idx)+"'");
			String subject = exdao.selectQueryColumn(selectdto);
			
			String sql = "DELETE FROM popup WHERE idx = '"+exdao.filter(idx)+"'";
			exdao.executeQuery(sql);
			
			//작업기록
			Func.writeManagerLog ("팝업 삭제 ["+subject+"]", request);
		}
		
		
	}

	/**
	 * @title : 사용/중지
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String tot_w_chk = request.getParameter("tot_w_chk");
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("popup");
		selectdto.setExColumn("subject");
		for (int z = 0; z <= chk.length - 1; z++) {
			String sql = "UPDATE popup SET w_chk = '"+exdao.filter(tot_w_chk)+"' WHERE idx = '"+exdao.filter(chk[z])+"'";
			exdao.executeQuery(sql);
			
			selectdto.setExWhereQuery("where idx = '"+exdao.filter(chk[z])+"'");
			String subject = exdao.selectQueryColumn(selectdto);
			
			//작업기록
			String chk_str = "사용";
			if("N".equals(tot_w_chk)) chk_str = "중지";
			if("L".equals(tot_w_chk)) chk_str = "사용(레이어팝업)";
			Func.writeManagerLog ("팝업 상태변경 ["+subject+"("+chk_str+")]", request);
		}
	}

}
