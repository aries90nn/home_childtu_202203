package kr.co.nninc.ncms.stock.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.buseo.service.BuseoDTO;
import kr.co.nninc.ncms.buseo_member.service.BuseoMemberDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.stock.service.StockCountDTO;
import kr.co.nninc.ncms.stock.service.StockDTO;
import kr.co.nninc.ncms.stock.service.StockService;

@Service("stockService")
public class StockServiceImpl extends EgovAbstractServiceImpl implements StockService {
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** FileUtil */
	@Resource(name="fileutil")
	private FileUtil fileutil;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;

	@Override
	public String write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		
		String where = " WHERE 1=1 ";
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		StockDTO selectdto = new StockDTO();
		selectdto.setExTableName("stock");
		selectdto.setExColumn(new StockDTO());
		List<HashMap<String, String>> countList = exdao.selectQueryTable(selectdto);
		
		String v_search = exdao.filter(request.getParameter("v_search"));
		request.setAttribute("v_search", v_search);
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		request.setAttribute("v_keyword", v_keyword);
		if(v_search != "" && v_keyword != "") {
			where += " AND "+v_search+" like '%"+ v_keyword +"%'  ";
		}
		
		// 페이징 설정
		selectdto.setExRecordCount(pagesize);
		selectdto.setExPage(v_page);
				
		selectdto.setExWhereQuery(where);
		selectdto.setExKeyColumn("st_idx");	//clob데이타가 있을경우 키컬럼 지정
		selectdto.setExOrderByQuery("ORDER BY st_date DESC, st_regdate DESC");
		List<HashMap<String, String>> stockList = exdao.selectQueryPage(selectdto);
				
		recordcount = Func.cInt( stockList.get(0).get("totalcount") ); // 전체레코드 수
		request.setAttribute("recordcount", recordcount);
		stockList.remove(0);	//총검색개수행(첫번째행)을 삭제
				
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
		/*
		for (HashMap<String, String> hashMap : stockList) {
			//String gubun = hashMap.get("st_gubun");
			String work = hashMap.get("st_work");
			String program = hashMap.get("st_program");
			String delivery = hashMap.get("st_delivery");
			
			//if(gubun != null) {
				//gubun = gubun.trim().equals("m") ? "모비스" : "재단";
				//hashMap.put("st_gubun", gubun);	
			//}
			
			
			if(work != null) {
				work = work.trim().equals("add") ? "입고" : "출고";
				hashMap.put("st_work", work);	
			}
			if(program != null) {
				switch (program) {
					case "1": program = "사연신청"; break;
					case "2": program = "학교신청"; break;
					case "3": program = "임직원나눔"; break;
					case "4": program = "대리점/협력사나눔"; break;
					case "5": program = "기타나눔"; break;
					default: program = ""; break;
				}
				hashMap.put("st_program", program);	
			}
			if(delivery != null) {
				delivery = (delivery.trim().equals("p") ? "택배배송" : (delivery.trim().equals("f") ? "화물배송" : ""));
				hashMap.put("st_delivery", delivery);	
			}
			
		}*/
		
		request.setAttribute("stockList", stockList);
		
		int sc_count = 0;
		int sc_receiv = 0;
		int sc_forward = 0;
		String sc_last = "0";
		
		for (HashMap<String, String> hashMap : countList) {
			String work = hashMap.get("st_work");
			int cnt = Func.cInt(hashMap.get("st_cnt"));
			if(work.equals("입고")) {
				sc_count += cnt;
				sc_receiv += cnt;
			}else if(work.equals("출고")) {
				sc_count -= cnt;
				sc_forward += cnt;
			}
		}
		
		selectdto = new StockDTO();
		selectdto.setExTableName("stock_count");
		//selectdto.setExColumn(new StockCountDTO());
		selectdto.setExColumn("sc_last");
		selectdto.setExWhereQuery("WHERE 1=1 AND sc_idx = '1' ");
		String rs_sc_last = exdao.selectQueryColumn(selectdto);
		if(rs_sc_last != null && !rs_sc_last.equals("")) sc_last = rs_sc_last;
		//HashMap<String, String> sc = exdao.selectQueryRecord(selectdto);
		/*if(sc != null) {
			sc_count = sc.get("sc_count");
			sc_receiv = sc.get("sc_receiv");
			sc_forward = sc.get("sc_forward");
			sc_last = sc.get("sc_last");
		}*/
		
		request.setAttribute("sc_count", sc_count);
		request.setAttribute("sc_receiv", sc_receiv);
		request.setAttribute("sc_forward", sc_forward);
		request.setAttribute("sc_last", sc_last);
		
		String prepage = exdao.filter(request.getParameter("prepage"));
		Func.getNowPage(request);
		
		return "/ncms/stock/write";
	}

	// 재고 등록
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		StockDTO stockdto = (StockDTO) map.get("stockdto");
		
		int stock_cnt = Func.cInt(exdao.filter(request.getParameter("stock_cnt")));
		// 독립적인 재고 수량 관리
		/*StockCountDTO stockCountDTO = new StockCountDTO();
		stockCountDTO.setExTableName("stock_count");
		stockCountDTO.setExColumn(new StockCountDTO());
		stockCountDTO.setExWhereQuery(" where sc_idx = '1' ");
		HashMap<String, String>sc = exdao.selectQueryRecord(stockCountDTO);
		
		long sc_receiv = 0;
		long sc_forward = 0;
		int sc_count = 0;
		
		if(sc != null) {
			sc_receiv = Func.cLng(sc.get("sc_receiv"));
			sc_forward = Func.cLng(sc.get("sc_forward"));
			sc_count = Func.cInt(sc.get("sc_count"));
		}*/
		
		//String st_gubun = exdao.filter(stockdto.getSt_gubun());
		String st_date = exdao.filter(stockdto.getSt_date());
		String st_work = exdao.filter(stockdto.getSt_work());
		int st_cnt = Func.cInt(exdao.filter(stockdto.getSt_cnt()));
		String st_program = exdao.filter(stockdto.getSt_program());
		String st_delivery = exdao.filter(stockdto.getSt_delivery());
		String st_ext = exdao.filter(stockdto.getSt_ext());
		String st_history = exdao.filter(stockdto.getSt_history());

		if(st_date == null || st_date.equals("")) return messageService.backMsg(model, "날짜를 선택해주세요.");
		else if(st_work == null || st_work.equals("")) return messageService.backMsg(model, "재고 작업을 선택해주세요.");
		else if(st_cnt == 0 || st_cnt < 0) return messageService.backMsg(model, "수량을 입력해주세요.");
		else if(!st_work.equals("입고")) {
			if(st_program == null || st_program.equals("")) return messageService.backMsg(model, "프로그램 구분을 선택해주세요.");
			else if(st_delivery == null || st_delivery.equals("")) return messageService.backMsg(model, "배송정보를 선택해주세요.");
		}
		if(st_work.equals("출고") && (st_cnt > stock_cnt)) return messageService.backMsg(model, "출고수량이 재고수량보다 많습니다.");
		
		//DecimalFormat df = new DecimalFormat("###,###");
		//String st_cnt_str = df.format(st_cnt);
		/*if(st_work.equals("입고")) {
			sc_count += st_cnt;
			sc_receiv += st_cnt;
		}else if(st_work.equals("출고")) {
			if(st_cnt > sc_count) return messageService.backMsg(model, "출고수량이 재고수량보다 많습니다.");
			sc_count -= st_cnt;
			sc_forward += st_cnt;
		}else {
			return messageService.backMsg(model, "잘못된 접근입니다.");
		}*/
		
		//순서업데이트
		exdao.executeQuery("UPDATE stock SET st_code = st_code + 1");
				
		StockDTO stockDTO = new StockDTO();
		
		//insert 제외 필드
		stockDTO.setSt_idx(null);
		
		// 세팅
		stockDTO.setSt_code("1");
		stockDTO.setSt_regdate(Func.date("Y-m-d H:i:s"));
		stockDTO.setSt_use("Y");
		
		//stockDTO.setSt_stock(Func.cStr(sc_count));
		//stockDTO.setSt_gubun(st_gubun);
		stockDTO.setSt_date(st_date);
		stockDTO.setSt_work(st_work);
		stockDTO.setSt_cnt(Func.cStr(st_cnt));
		stockDTO.setSt_program(st_program);
		stockDTO.setSt_delivery(st_delivery);
		stockDTO.setSt_ext(st_ext);
		stockDTO.setSt_history(st_history);
		
		//insert
		stockDTO.setExTableName("stock");
		stockDTO.setExColumn("st_idx");
		exdao.insert(stockDTO);
		
		// insert
		/*if( sc == null ) {
			stockCountDTO = new StockCountDTO();
			stockCountDTO.setSc_idx(null);
			stockCountDTO.setSc_count(Func.cStr(sc_count));
			stockCountDTO.setSc_count(Func.cStr(sc_receiv));
			stockCountDTO.setSc_count(Func.cStr(sc_forward));
			stockCountDTO.setExTableName("stock_count");	
			exdao.insert(stockCountDTO);
		}else{
			if(st_work.equals("입고")) {
				exdao.executeQuery("UPDATE stock_count SET sc_count = '"+Func.cStr(sc_count)+"', sc_receiv = '"+Func.cStr(sc_receiv)+"' where sc_idx = '1' ");
			} else if(st_work.equals("출고")) {
				exdao.executeQuery("UPDATE stock_count SET sc_count = '"+Func.cStr(sc_count)+"', sc_forward = '"+Func.cStr(sc_forward)+"' where sc_idx = '1' ");	
			}
		}*/
		
		//작업기록
		Func.writeManagerLog("투명우산 재고 작업", request);
		
		String prepage = Func.nvl(request.getParameter("prepage")).trim();
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		if("".equals(prepage)){prepage= "/main/ncms/stock/write.do";}
		/*if(!"".equals(builder_dir)){
			prepage = "/" + builder_dir + prepage;
		}*/
		return "redirect:"+prepage;
	}

	@Override
	public void excel(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		String where = " WHERE 1=1 ";
		
		String v_search = exdao.filter(request.getParameter("v_search"));
		request.setAttribute("v_search", v_search);
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		request.setAttribute("v_keyword", v_keyword);
		if(v_search != "" && v_keyword != "") {
			where += " AND "+v_search+" like '%"+ v_keyword +"%'  ";
		}
		
		// 게시판필드		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("stock");
		selectdto.setExKeyColumn("st_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new StockDTO());
		selectdto.setExWhereQuery("");
		selectdto.setExOrderByQuery("ORDER BY st_date DESC, st_regdate DESC");
		selectdto.setExWhereQuery(where);
		List<HashMap<String,String>>stockList = exdao.selectQueryTable(selectdto);
		/*
		for (HashMap<String, String> hashMap : stockList) {			
			//String gubun = hashMap.get("st_gubun");
			String work = hashMap.get("st_work");
			String program = hashMap.get("st_program");
			String delivery = hashMap.get("st_delivery");
			
			//if(gubun != null) {
				//gubun = gubun.trim().equals("m") ? "모비스" : "재단";
				//hashMap.put("st_gubun", gubun);	
			//}
			
			if(work != null) {
				work = work.trim().equals("add") ? "입고" : "출고";
				hashMap.put("st_work", work);	
			}
			if(program != null) {
				switch (program) {
					case "1": program = "사연신청"; break;
					case "2": program = "학교신청"; break;
					case "3": program = "임직원나눔"; break;
					case "4": program = "대리점/협력사나눔"; break;
					case "5": program = "기타나눔"; break;
					default: program = ""; break;
				}
				hashMap.put("st_program", program);	
			}
			if(delivery != null) {
				delivery = (delivery.trim().equals("p") ? "택배배송" : (delivery.trim().equals("f") ? "화물배송" : ""));
				hashMap.put("st_delivery", delivery);	
			}
		}*/
		
		request.setAttribute("stockList", stockList);
	}
	
	/**
	 * @title : 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 		= Func.nvl( request.getParameter("status") ).trim(); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String st_idx 		= request.getParameter("st_idx");
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("stock");
		selectdto.setExColumn("st_regdate");
		
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				selectdto.setExWhereQuery("where st_idx = '"+chk[z]+"'");
				HashMap<String,String>stock = exdao.selectQueryRecord(selectdto);

				exdao.executeQuery("delete from stock where st_idx = '"+chk[z]+"'");

				//작업기록
				Func.writeManagerLog ("투명우산 재고 로그 삭제 ["+stock.get("st_regdate")+"]", request);
			}
		} else { //단일삭제
			selectdto.setExWhereQuery("where st_idx = '"+st_idx+"'");
			HashMap<String,String>stock = exdao.selectQueryRecord(selectdto);
			
			exdao.executeQuery("delete from stock where st_idx = '"+st_idx+"'");
			
			//작업기록
			Func.writeManagerLog ("투명우산 재고 로그 삭제 ["+stock.get("st_regdate")+"]", request);
		}
		
	}
	
}
