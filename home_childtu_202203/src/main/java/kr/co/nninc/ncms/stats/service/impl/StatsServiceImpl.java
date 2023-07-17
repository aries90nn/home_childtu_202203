package kr.co.nninc.ncms.stats.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.cms.service.CmsService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.stats.service.StatsService;
import kr.co.nninc.ncms.stats.service.VisitCounterDTO;

/**
 * 통계를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.10.10
 * @version 1.0
 */
@Service("statsService")
public class StatsServiceImpl extends EgovAbstractServiceImpl implements StatsService{

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** cms */
	@Resource(name = "cmsService")
	private CmsService cmsService;
	
	/**
	 * @title : 시간별 통계
	 * @method : hour
	 */
	@Override
	public void hour(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		
		//1. 파라미터정보(초기화)
		String yy = Func.nvl( request.getParameter("yy") );
		String mm = Func.nvl( request.getParameter("mm") );
		String dd = Func.nvl( request.getParameter("dd") );
		String tt = Func.nvl( request.getParameter("tt") );
		String os = Func.nvl(request.getParameter("os"));
		if(yy.equals("") && mm.equals("") && dd.equals("") && tt.equals("")){
			yy		= Func.date("Y");
			mm		= Func.date("m");
			dd		= Func.date("d");
		}
		
		int lastDay = Func.lastDay(Func.cInt(yy), Func.cInt(mm), 1);
		request.setAttribute("lastDay", lastDay);
		
		
		//2. 배열 초기화
		int totalCount = 0;
		int curCount[] = new int[25];
		String arrData[][] = new String[32][3];
		for(int i=1; i<= 24; i++) {
			curCount[i] = 0;
		}
		
		//3. 카운터테이블명 생성
		String count_table = "visit_counter_"+yy+Func.zerofill(mm, 2, "0");
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			count_table = "visit_counter_site_"+yy+Func.zerofill(mm, 2, "0");
		}
		
		//4. 카운터테이블 확인
		int cnt = exdao.searchTableCount(count_table);
		float sumCount = 0;
		if( cnt > 0 ){
			//카운터 수의 합을 구한다
			selectdto.setExTableName(count_table);
			selectdto.setExColumn("count(*)");
			if(!"".equals(dd)){
				selectdto.setExWhereQuery("where vDD = '"+dd+"'");
			}
			if(!"".equals(builder_dir)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vSite_dir = '"+builder_dir+"'" );
			}
			if("PC".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vOS not like '%Android%' and vOS not like '%iPhone%' and vOS not like '%iPad%'" );
			}else if("MOBILE".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and ( vOS like '%Android%' or vOS like '%iPhone%' or vOS like '%iPad%')" );
			}
			totalCount = Func.cInt( exdao.selectQueryColumn(selectdto) );
			
			//시간별 카운터를 구한다
			selectdto = new SelectDTO();
			selectdto.setExTableName(count_table);
			selectdto.setExColumn("vHH, count(vHH) as vCount");
			if(!"".equals(dd)){
				selectdto.setExWhereQuery("where vDD = '"+dd+"'");
			}
			if(!"".equals(builder_dir)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vSite_dir = '"+builder_dir+"'" );
			}
			if("PC".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vOS not like '%Android%' and vOS not like '%iPhone%' and vOS not like '%iPad%'" );
			}else if("MOBILE".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and ( vOS like '%Android%' or vOS like '%iPhone%' or vOS like '%iPad%')" );
			}
			selectdto.setExGroupByQuery("GROUP BY vHH");
			selectdto.setExOrderByQuery("ORDER BY vHH");
			List<HashMap<String,String>>list = exdao.selectQueryTable(selectdto);
			if(list.size() > 0) {
				for(int i=0; i<list.size(); i++) {
					HashMap<String,String>stats = list.get(i);
					String  vC = stats.get("vCount");
					String  vHH = stats.get("vHH");
					
					curCount[Func.cInt(vHH)] = Func.cInt(vC);
					sumCount = sumCount + Func.cInt(vC);
				}
			}
		}
		
		//5. 최대값/최소값 구하기
		int maxCount = 0;
		int minCount = 100000;
		
		float curSize = 0;
		float curPer = 0;
		for (int i = 0; i<24; i++) {
			
			if( sumCount > 0 ) {
				curSize	= Func.formatDecimal2( Double.valueOf(((curCount[i]/sumCount) * 540)).floatValue(), 2);
				curPer	= Func.formatDecimal2( Double.valueOf(((curCount[i]/sumCount) * 100)*10 / 10.0).floatValue(), 2);
			} else {
				curSize	= 0;
				curPer	= 0;
			}
			if (maxCount < curCount[i] ) maxCount = curCount[i];
			if (minCount > curCount[i] ) minCount = curCount[i];
			
			arrData[i][0] = Func.cStr(curCount[i]);
			arrData[i][1] = Func.cStr(curSize);
			arrData[i][2] = Func.cStr(curPer);

			if ("0".equals(arrData[i][0]) || "0.0".equals(arrData[i][0])) {
				arrData[i][0] = "";
			}
		
			if ("0".equals(arrData[i][2]) || "0.0".equals(arrData[i][2])) {
				arrData[i][2] = "";
			}

		}
		request.setAttribute("yy", yy);
		request.setAttribute("mm", mm);
		request.setAttribute("dd", dd);
		request.setAttribute("arrData", arrData);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("minCount", minCount);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("sumCount", sumCount);
	}

	/**
	 * @title : 일별 통계
	 * @method : day
	 */
	@Override
	public void day(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		
		//1. 파라미터정보(초기화)
		String yy = Func.nvl(request.getParameter("yy"));
		String mm = Func.nvl(request.getParameter("mm"));
		String dd = Func.nvl(request.getParameter("dd"));
		String os = Func.nvl(request.getParameter("os"));
		if(yy.equals("") && mm.equals("") && dd.equals("")){
			yy	= Func.date("Y");
			mm	= Func.date("m");
		}
		
		//2. 마지막날 구하기
		int dataLength  = 0;
		if (!yy.equals("") && !mm.equals("")) {
			dataLength = Func.lastDay(Func.cInt(yy), Func.cInt(mm), 1);
		} else {
			dataLength = 31;
		}
		
		//3. 배열 초기화
		int arrValue[] = new int[dataLength+1];
		for(int i=1; i<=dataLength;i++) {
			arrValue[i] = 0;
		}
		int totalCount = 0;
		int curCount[] = new int[25];
		String arrData[][] = new String[32][3];
		
		for(int i=1; i<= 24; i++) {
			curCount[i] = 0;
		}
		
		//4. 카운터테이블명 생성
		String count_table = "visit_counter_"+yy+Func.zerofill(mm, 2, "0");
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			count_table = "visit_counter_site_"+yy+Func.zerofill(mm, 2, "0");
		}
				
		//5. 카운터테이블 확인
		int cnt = exdao.searchTableCount(count_table);
		if( cnt > 0 ){
			
			//카운터 수의 합을 구한다
			selectdto.setExTableName(count_table);
			selectdto.setExColumn("count(*)");
			if(!"".equals(builder_dir)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vSite_dir = '"+builder_dir+"'" );
			}
			if("PC".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vOS not like '%Android%' and vOS not like '%iPhone%' and vOS not like '%iPad%'" );
			}else if("MOBILE".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and ( vOS like '%Android%' or vOS like '%iPhone%' or vOS like '%iPad%')" );
			}
			totalCount = Func.cInt( exdao.selectQueryColumn(selectdto) );
			
			//일별 카운터를 구한다
			selectdto = new SelectDTO();
			selectdto.setExTableName(count_table);
			selectdto.setExColumn("vDD, count(vDD) as vCount");
			if(!"".equals(builder_dir)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vSite_dir = '"+builder_dir+"'" );
			}
			if("PC".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vOS not like '%Android%' and vOS not like '%iPhone%' and vOS not like '%iPad%'" );
			}else if("MOBILE".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and ( vOS like '%Android%' or vOS like '%iPhone%' or vOS like '%iPad%')" );
			}
			selectdto.setExGroupByQuery("GROUP BY vDD");
			selectdto.setExOrderByQuery("ORDER BY vDD");
			List<HashMap<String,String>>list = exdao.selectQueryTable(selectdto);
			if(list.size() > 0) {
				for(int i=0; i<list.size(); i++) {
					HashMap<String,String>stats = list.get(i);
					String  vCount	= stats.get("vCount");
					String  vDD		= stats.get("vDD");
					
					arrValue[Func.cInt(vDD)] = Func.cInt(vCount);
				}
			}
		}
		
		//6. 최대값/최소값 구하기
		int maxCount = 0;
		int minCount = 100000;
		
		for(int j=1; j<= dataLength; j++) { 
			arrData[j][0] = Func.cStr(arrValue[j]); //카운터

			if(totalCount > 1) {
				arrData[j][1] = Func.getPer(totalCount, arrValue[j]);
				arrData[j][2] = Func.cStr( ( arrValue[j]/totalCount ) * 550 );
			} else {
				arrData[j][1] = "0";
				arrData[j][2] = "0"; 
			}
			
			if(minCount > arrValue[j]) minCount = arrValue[j];
			if(maxCount < arrValue[j]) maxCount = arrValue[j];	

			if ("0".equals(arrData[j][0])) {
				arrData[j][0] = "";
			}
			if ("0".equals(arrData[j][1])) {
				arrData[j][1] = "";
			}
			if ("0".equals(arrData[j][2])) {
				arrData[j][2] = "";
			}
		}
		
		request.setAttribute("yy", yy);
		request.setAttribute("mm", mm);
		request.setAttribute("arrData", arrData);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("minCount", minCount);
		request.setAttribute("totalCount", totalCount);
	}

	/**
	 * @title : 월별 통계
	 * @method : month
	 */
	@Override
	public void month(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SelectDTO selectdto = new SelectDTO();
		
		//1. 파라미터정보(초기화)
		String yy = Func.nvl(request.getParameter("yy"));
		String mm = Func.nvl(request.getParameter("mm"));
		String dd = Func.nvl(request.getParameter("dd"));
		String os = Func.nvl(request.getParameter("os"));
		if(yy.equals("")){
			yy		= Func.date("Y");
		}
		
		//2. 배열 초기화
		int totalCount = 0;
		float sumCount = 0;
		String arrData[][] = new String[32][4];
		int curCount[] = new int[13];
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		for(int i=1; i<= 12; i++) {
			curCount[i] = 0;
			
			//3. 카운터테이블명 생성
			String count_table = "visit_counter_"+yy+Func.zerofill(Func.cStr(i), 2, "0");
			if(!"".equals(builder_dir)){
				count_table = "visit_counter_site_"+yy+Func.zerofill(Func.cStr(i), 2, "0");
			}
					
			//4. 카운터테이블 확인
			int cnt = exdao.searchTableCount(count_table);
			if( cnt > 0 ){
				
				//5. 월별 카운터를 구한다
				selectdto.setExTableName(count_table);
				selectdto.setExColumn("count(*)");
				if(!"".equals(builder_dir)){
					selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vSite_dir = '"+builder_dir+"'" );
				}
				if("PC".equals(os)){
					selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vOS not like '%Android%' and vOS not like '%iPhone%' and vOS not like '%iPad%'" );
				}else if("MOBILE".equals(os)){
					selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and ( vOS like '%Android%' or vOS like '%iPhone%' or vOS like '%iPad%')" );
				}
				
				int month_cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
				curCount[i] =  month_cnt;
				sumCount = sumCount + month_cnt;
				
			}
		}
		totalCount = (int) sumCount;
		
		//6. 최대값/최소값 구하기
		int maxCount = 0;
		int minCount = 100000;

		float curSize = 0;
		float curPer = 0;

		for (int i = 1 ; i<= curCount.length-1; i++) {
			if( sumCount > 0 ) {
				curSize	= Func.formatDecimal2( ((curCount[i]/sumCount) * 540), 2 );
				curPer	= Func.formatDecimal2( ((curCount[i]/sumCount) * 100)*10 / 10.0, 2 );
			} else {
				curSize	= 0;
				curPer	= 0;
			}
			
			if (maxCount < curCount[i] ) {
				maxCount = curCount[i];
			}
			if (minCount > curCount[i] ) {
				minCount = curCount[i]; 
			}
			arrData[i][0] = Func.cStr(curCount[i]);
			arrData[i][1] = Func.cStr(curSize);
			arrData[i][2] = Func.cStr(curPer);

			if ("0".equals(arrData[i][0])) {
				arrData[i][0] = "";
			}
			if ("0".equals(arrData[i][2]) || "0.0".equals(arrData[i][2])) {
				arrData[i][2] = "";
			}
		}
		
		request.setAttribute("yy", yy);
		request.setAttribute("mm", mm);
		request.setAttribute("arrData", arrData);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("minCount", minCount);
		request.setAttribute("totalCount", totalCount);
	}

	/**
	 * @title : 요일 통계
	 * @method : week
	 */
	@Override
	public void week(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		
		//1. 파라미터정보(초기화)
		String yy = Func.nvl(request.getParameter("yy"));
		String mm = Func.nvl(request.getParameter("mm"));
		String dd = Func.nvl(request.getParameter("dd"));
		String os = Func.nvl(request.getParameter("os"));
		if(yy.equals("") && mm.equals("") && dd.equals("")){
			yy	= Func.date("Y");
			mm	= Func.date("m");
		}
		
		//2. 배열 초기화
		String arrData[][] = new String[32][4];
		int curCount[] = new int[8];
		for(int i=0; i<= 7; i++) {
			curCount[i] = 0;
		}
		int totalCount = 0;
		float sumCount = 0;
		
		//3. 카운터테이블명 생성
		String count_table = "visit_counter_"+yy+Func.zerofill(mm, 2, "0");
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			count_table = "visit_counter_site_"+yy+Func.zerofill(mm, 2, "0");
		}
						
		//4. 카운터테이블 확인
		int cnt = exdao.searchTableCount(count_table);
		if( cnt > 0 ){
			//카운터 수의 합을 구한다
			selectdto.setExTableName(count_table);
			selectdto.setExColumn("count(*)");
			if(!"".equals(builder_dir)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vSite_dir = '"+builder_dir+"'" );
			}
			if("PC".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vOS not like '%Android%' and vOS not like '%iPhone%' and vOS not like '%iPad%'" );
			}else if("MOBILE".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and ( vOS like '%Android%' or vOS like '%iPhone%' or vOS like '%iPad%')" );
			}
			totalCount = Func.cInt( exdao.selectQueryColumn(selectdto) );
			
			//요일별 카운터를 구한다
			selectdto = new SelectDTO();
			selectdto.setExTableName(count_table);
			selectdto.setExColumn("vDW, count(vDW) as vCount");
			if(!"".equals(builder_dir)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vSite_dir = '"+builder_dir+"'" );
			}
			if("PC".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and vOS not like '%Android%' and vOS not like '%iPhone%' and vOS not like '%iPad%'" );
			}else if("MOBILE".equals(os)){
				selectdto.setExWhereQuery( Func.nvl(selectdto.getExWhereQuery())+" and ( vOS like '%Android%' or vOS like '%iPhone%' or vOS like '%iPad%')" );
			}
			selectdto.setExGroupByQuery("GROUP BY vDW");
			selectdto.setExOrderByQuery("ORDER BY vDW");
			List<HashMap<String,String>>list = exdao.selectQueryTable(selectdto);
			if(list.size() > 0) {
				for(int i=0; i<list.size(); i++) {
					HashMap<String, String> stats = (HashMap<String, String>)list.get(i);
					String  vCount	= stats.get("vCount");
					String  vDW		= stats.get("vDW");
					
					curCount[Func.cInt(vDW)] =  Func.cInt(vCount);
					sumCount = sumCount + Func.cInt(vCount);
				}
			}
		}

		//6. 최대값/최소값 구하기
		int maxCount = 0;
		int minCount = 100000;
		
		float curSize = 0;
		float curPer = 0;
		
		for (int i = 1 ; i<=7; i++) {
			if( sumCount > 0 ) {
				curSize		= Func.formatDecimal2( ((curCount[i]/sumCount) * 540), 2 );
				curPer		= Func.formatDecimal2( ((curCount[i]/sumCount) * 100)*10 / 10.0, 2 );
			} else {
				curSize	= 0;
				curPer	= 0;
			}
			if (maxCount < curCount[i] ) {
				maxCount = curCount[i];
			}
			if (minCount > curCount[i] ) {
				minCount = curCount[i];
			}
			arrData[i][0] = Func.cStr(curCount[i]);
			arrData[i][1] = Func.cStr(curSize);
			arrData[i][2] = Func.cStr(curPer);
			arrData[i][3] = Func.SetWeek(i-1);
		
			if ("0".equals(arrData[i][0])) {
				arrData[i][0] = "";
			}
		
			if ("0".equals(arrData[i][2]) || "0.0".equals(arrData[i][2])) {
				arrData[i][2] = "";
			}
		}
		request.setAttribute("yy", yy);
		request.setAttribute("mm", mm);
		request.setAttribute("arrData", arrData);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("minCount", minCount);
		request.setAttribute("totalCount", totalCount);
	}

	/**
	 * @title : 방문자기록
	 * @method : log
	 */
	@Override
	public void log(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		SelectDTO selectdto = new SelectDTO();
		
		//파라미터정보 ****************************
		String yy = Func.nvl(request.getParameter("yy"));
		String mm = Func.nvl(request.getParameter("mm"));
		String dd = Func.nvl(request.getParameter("dd"));
		String portal_type = Func.nvl(request.getParameter("portal_type"));
		
		if(yy.equals("")){ yy = Func.date("Y");}
		if(mm.equals("")){ mm = Func.date("m");}
		if(dd.equals("")){ dd = Func.date("d");}

		if (yy.equals("ALL")){ yy = ""; mm = ""; dd = "";}
		if (mm.equals("ALL")){ mm = ""; dd = ""; }
		if (dd.equals("ALL")){ dd = ""; }
		//*************************************
		
		//배열&필드 초기화
		int totalCount = 0, totalCount_All = 0;
		int totalNaver = 0, totalDaum = 0, totalBrow = 0, totalEtc = 0;
		String percentNaver= "0", percentDaum= "0", percentBrow= "0", percentEtc = "0";
		String arrData[][] = new String[32][3];
		int curCount[] = new int[5];
		for(int i=0; i<= 4; i++) {
			curCount[i] = 0;
		}

		//카운터테이블명 있는지 확인
		String count_table = "visit_counter_"+yy+Func.zerofill(mm, 2, "0");
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			count_table = "visit_counter_site_"+yy+Func.zerofill(mm, 2, "0");
		}
		
		int table_cnt = exdao.searchTableCount(count_table);
		List<HashMap<String,String>> statsList = new ArrayList<HashMap<String,String>>();
		if(table_cnt > 0) {
			
			// 총 개수(포털검색조건있기전)
			selectdto.setExTableName(count_table);
			selectdto.setExColumn("count(*)");
			String whereQuery = "";
			if(!"".equals(dd)){
				whereQuery += " and vDD = '"+dd+"'";
			}
			if(!"".equals(builder_dir)){
				whereQuery += " and vSite_dir = '"+builder_dir+"'";
			}
			selectdto.setExWhereQuery(whereQuery);
			totalCount_All = Func.cInt(exdao.selectQueryColumn(selectdto));
			
			//-- 네이버 검색 카운트하기 --------------------
			selectdto.setExWhereQuery(whereQuery+" and LOWER(vUrl) like LOWER('%naver%')");
			totalNaver = Func.cInt(exdao.selectQueryColumn(selectdto));
			curCount[0] = totalNaver;
			
			if(totalCount_All > 0){
				percentNaver = Func.getPer(totalCount_All, totalNaver);
			}
			//-- 다음 검색 카운트하기 --------------------
			selectdto.setExWhereQuery(whereQuery+" and LOWER(vUrl) like LOWER('%daum%')");
			totalDaum = Func.cInt(exdao.selectQueryColumn(selectdto));
			curCount[1] = totalDaum;
			
			if(totalCount_All > 0){
				percentDaum = Func.getPer(totalCount_All, totalDaum);
			}
			//-- 즐겨찾기 검색 카운트하기 --------------------
			selectdto.setExWhereQuery(whereQuery+" AND (vURL='' or vURL IS NULL or LOWER(vUrl)='null')");
			totalBrow = Func.cInt(exdao.selectQueryColumn(selectdto));
			curCount[2] = totalBrow;
			
			if(totalCount_All > 0){
				percentBrow = Func.getPer(totalCount_All, totalBrow);
			}
			//-- 기타 검색 카운트하기 --------------------
			selectdto.setExWhereQuery(whereQuery+" AND vURL<>'' AND LOWER(vUrl) not like LOWER('%naver%') AND LOWER(vUrl) not like LOWER('%daum%') AND LOWER(vUrl)<>'null'");
			totalEtc = Func.cInt(exdao.selectQueryColumn(selectdto));
			curCount[3] = totalEtc;
			
			if(totalCount_All > 0){
				percentEtc = Func.getPer(totalCount_All, totalEtc);
			}
			
			// 포털사이트 검색조건 별 총 개수
			String whereQuery2 = whereQuery;
			if("naver".equals(portal_type)){
				whereQuery2 += " and LOWER(vUrl) like LOWER('%naver%')";
			}else if("daum".equals(portal_type)){
				whereQuery2 += " and LOWER(vUrl) like LOWER('%daum%')";
			}else if("brow".equals(portal_type)){
				whereQuery2 += " AND (vURL='' or vURL IS NULL or LOWER(vUrl)='null')";
			}else if("etc".equals(portal_type)){
				whereQuery2 += " AND vURL<>'' AND LOWER(vUrl) not like LOWER('%naver%') AND LOWER(vUrl) not like LOWER('%daum%') AND LOWER(vUrl)<>'null'";				
			}
			selectdto.setExWhereQuery(whereQuery2);
			totalCount = Func.cInt(exdao.selectQueryColumn(selectdto));

			
			// 방문자 리스트
			// pageConf
			int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
			int pagesize = 20;
			int pagePerBlock = 10;
			int recordcount = 0; // 전체레코드 수
			request.setAttribute("pagesize", pagesize);
			
			selectdto = new SelectDTO();
			selectdto.setExTableName(count_table);
			selectdto.setExColumn("vNum, vIP, vDD, vHH, vMT, vSeason, vDW, vBrowser, vOS, vURL, vDomain");
			selectdto.setExRecordCount(pagesize);
			selectdto.setExPage(v_page);
			selectdto.setExWhereQuery(whereQuery2);
			statsList = exdao.selectQueryPage(selectdto);
			recordcount = Func.cInt( statsList.get(0).get("totalcount") ); // 전체레코드 수
			
			request.setAttribute("recordcount", recordcount);
			statsList.remove(0);	//총검색개수행(첫번째행)을 삭제
			
			int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수
			//페이징문자열 생성
			Paging paging = new Paging();
			paging.pageKeyword = "v_page";	//페이지파라미터명
			paging.page = v_page;			//현재페이지
			paging.block = pagePerBlock;	//페이지링크 갯수
			paging.totalpage = totalpage;	//총페이지 갯수
			String querystring2 = paging.setQueryString(request, "portal_type, yy, mm, dd");
			
			String pagingtag = paging.execute(querystring2);
			request.setAttribute("pagingtag", pagingtag);
			request.setAttribute("v_page", v_page);
			request.setAttribute("totalpage", totalpage);
			//페이징문자열 생성 끝
			
			
			for(int i=0; i<statsList.size(); i++) {
				HashMap<String,String> stats = statsList.get(i);
				String vURL			= stats.get("vURL");
				String vHH			= stats.get("vHH");
				String vMT			= stats.get("vMT");
				String vOS			= stats.get("vOS");
				String vBrowser		= stats.get("vBrowser");
				
				String vURL_str = "";
				if(vURL.equals("") || vURL.equals("null")){
					vURL_str = "주소직접입력 또는 즐겨찾기를 이용한 방문";
				}else{
					vURL = vURL.replaceAll("&", "&amp;");
					vURL_str = "<a href=\""+vURL+"\" title='"+vURL+"' target='_blank'>"+Func.StringToHTML(vURL, 60, false )+"</a>";
				}
				if(vHH.length() == 1){
					vHH = "0"+vHH;
				}
				if(vMT.length() == 1){
					vMT = "0"+vMT;
				}
				stats.put("vHH", vHH);
				stats.put("vMT", vMT);
				stats.put("vURL", vURL);
				stats.put("vURL_str", vURL_str);
				stats.put("vOS_str", Func.SetOS(vOS));
				stats.put("vBrowser_str", Func.SetBrowser(vBrowser));
			}
		}
		int maxCount = 0;
		int minCount = 100000;
		for (int i = 0 ; i<= 3; i++) {
			if (maxCount < curCount[i] ) maxCount = curCount[i];
			if (minCount > curCount[i] ) minCount = curCount[i];

			arrData[i][0] = Func.cStr(curCount[i]);

			if ("0".equals(arrData[i][0])) {
				arrData[i][0] = "";
			}
		}
		
		int lastDay = Func.lastDay(Func.cInt(yy), Func.cInt(mm), 1);
		request.setAttribute("lastDay", lastDay);
		request.setAttribute("yy", yy);
		request.setAttribute("mm", mm);
		request.setAttribute("dd", dd);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("statsList", statsList);
		request.setAttribute("percentNaver", percentNaver);
		request.setAttribute("percentDaum", percentDaum);
		request.setAttribute("percentBrow", percentBrow);
		request.setAttribute("percentEtc", percentEtc);
		request.setAttribute("totalNaver", totalNaver);
		request.setAttribute("totalDaum", totalDaum);
		request.setAttribute("totalBrow", totalBrow);
		request.setAttribute("totalEtc", totalEtc);
		request.setAttribute("arrData", arrData);
		request.setAttribute("portal_type", portal_type);
	}

	/**
	 * @title : 메뉴접속 통계
	 * @method : stats
	 */
	@Override
	public void stat(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		
		DecimalFormat df = new DecimalFormat("###,###");
		
		//파라미터정보 *************************************************************************************
		String v_mobile	= Func.InputValue(request.getParameter("v_mobile"));
		String v_depth	= Func.InputValue(request.getParameter("v_depth"));
		String v_main	= Func.InputValue(request.getParameter("v_main"));
		String v_sub1	= Func.InputValue(request.getParameter("v_sub1"));
		String yy			= Func.InputValue(request.getParameter("yy"));
		String mm			= Func.InputValue(request.getParameter("mm"));
		String dd			= Func.InputValue(request.getParameter("dd"));
		//**************************************************************************************************
		if("".equals(v_mobile))	v_mobile = "total";
		if("".equals(v_depth))	v_depth = "2";
		
		if(yy.equals("") && mm.equals("")){
			yy		= Func.date("Y");
			mm	= Func.date("m");
		}
		if(dd.equals("")) {
			dd	= Func.date("d");
		}
		
		int lastDay = Func.lastDay(Func.cInt(yy), Func.cInt(mm), 1);
		request.setAttribute("lastDay", lastDay);
		
		String count_table = "menu_counter_"+yy+Func.zerofill(mm, 2, "0");
		int cnt = exdao.searchTableCount(count_table);
		
		
		// 최대 메뉴 차수
		selectdto.setExTableName("homepage_menu");
		selectdto.setExColumn("max( ct_depth ) as maxdepth");
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			selectdto.setExWhereQuery("where ct_site_dir = '"+builder_dir+"'");
		}
		int maxdepth = Func.cInt( exdao.selectQueryColumn(selectdto) );
		
		// 검색 전체
		long totalcounter = 0;
		
		if(cnt > 0){
			selectdto = new SelectDTO();
			selectdto.setExTableName(count_table);
			selectdto.setExColumn("sum(counter) as cnt");
			String where_query = "";
			//PC, 모바일검색조건
			if (!"total".equals(v_mobile)){ where_query += " and mobile = '"+v_mobile+"'"; }
			//메인검색 조건
			if ("Y".equals(v_main)){ where_query += " and page_id > 0"; }
			//일자검색
			if (!"".equals(dd) && !"0".equals(dd)){ where_query += " and vDD = '"+dd+"'"; }
			if(!"".equals(builder_dir)){where_query += " and site_dir = '"+builder_dir+"'";}
			selectdto.setExWhereQuery(where_query);
			// 검색 전체
			totalcounter = Func.cLng( exdao.selectQueryColumn(selectdto) );
			
			if(Func.cInt(v_depth)>0) {
				// 메인 및 기타 페이지
				if (!"Y".equals(v_main)){
					selectdto.setExWhereQuery( where_query+" and page_id=-1" );
					long cnt_m = Func.cLng( exdao.selectQueryColumn(selectdto) );
					request.setAttribute("cnt_m_graph", Func.drawGraph(cnt_m, totalcounter, 1));
					request.setAttribute("cnt_m", cnt_m);
				}
				
				//1차메뉴 - JSON메뉴파일 사용 재귀함수 사용
				JSONArray menuList = cmsService.getMenuJson(request);
				//menuList = this.setMenuCountGraph(menuList, count_table, where_query, Func.cInt(v_depth), totalcounter);
				menuList = this.setMenuCountGraph(menuList, count_table, where_query, 1, Func.cInt(v_depth), totalcounter);
				request.setAttribute("menuList", menuList);
			}else{
				selectdto = new SelectDTO();
				selectdto.setExTableName(count_table);
				selectdto.setExColumn("page_id, sum(counter) as counter");
				selectdto.setExWhereQuery(where_query);
				selectdto.setExGroupByQuery("GROUP BY page_id");
				selectdto.setExOrderByQuery("ORDER BY counter desc");
				List<HashMap<String,String>>statsList = exdao.selectQueryTable(selectdto);
				for(int i=0;i<statsList.size();i++){
					HashMap<String,String>stats = statsList.get(i);
					
					//카운트&그래프
					long counter = Func.cLng(stats.get("counter"));
					
					String graph = Func.drawGraph(counter, totalcounter, 1);
					stats.put("graph",graph);
					stats.put("counter",df.format(counter));
					
					//메뉴명 조회하기 *********************************
					String menuname = "";
					selectdto = new SelectDTO();
					selectdto.setExTableName("homepage_menu");
					selectdto.setExColumn("ct_codeno");
					selectdto.setExWhereQuery("where ct_idx = '"+Func.nvl( stats.get("page_id") )+"'");
					String ct_codeno = Func.nvl(exdao.selectQueryColumn(selectdto)).trim();
					if(!"".equals(ct_codeno)){
						String[] ct_code_arr = ct_codeno.split("\\;");
						String ct_code_value = ct_code_arr[0]+";";
						for(int j = 1;j <=ct_code_arr.length-1;j++){
							ct_code_value = ct_code_value+ct_code_arr[j]+";";
							//메뉴명칭조회
							selectdto.setExColumn("ct_name");
							selectdto.setExWhereQuery("where ct_codeno = '"+ct_code_value+"'");
							String ct_name = exdao.selectQueryColumn(selectdto);
							if(j > 1){menuname = menuname+" > ";}
							menuname = menuname+ct_name;
						}
					}
					if ("".equals(menuname))	menuname = "메인페이지 및 기타";
					stats.put("menuname", menuname);
				}
				//메뉴명 조회하기 *********************************
				request.setAttribute("statsList", statsList);
			}
		}
		request.setAttribute("yy", yy);
		request.setAttribute("mm", mm);
		request.setAttribute("dd", dd);
		request.setAttribute("v_mobile", v_mobile);
		request.setAttribute("v_depth", v_depth);
		request.setAttribute("v_main", v_main);
		request.setAttribute("v_sub1", v_sub1);
		request.setAttribute("maxdepth", maxdepth);
		request.setAttribute("totalcounter", df.format(totalcounter));
	}

	@Override
	public void list(Model model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @title : 관리자 인덱스페이지에 쓰임 
	 * @method : ncmsIndex
	 */
	@Override
	public void ncmsIndex(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//select 전용 dto
		VisitCounterDTO selectdto = new VisitCounterDTO();
		
		// 어제의 방문자
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, -1);
		
		int year = cal.get(Calendar.YEAR);
		int month = (cal.get(Calendar.MONTH) + 1);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		// 이달의 방문자
		String count_table = "visit_counter_"+Func.date("Ym");
		selectdto.setExTableName(count_table);
		selectdto.setExColumn("count(*) as total_cnt");
		int month_cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
		request.setAttribute("month_cnt", month_cnt);
		
		//어제방문자수 조회
		int yesterday_cnt = 0;
		//테이블조회
		String dbname = CommonConfig.get("jdbc.dbname");
		String tablename = "visit_counter_"+year+Func.zerofill(month, 2, "0");
		int table_cnt = exdao.searchTableCount(dbname, tablename);
		if(table_cnt > 0){

			selectdto = new VisitCounterDTO();
			selectdto.setExTableName("visit_counter_"+year+Func.zerofill(month, 2, "0"));
			selectdto.setExColumn("count(*) as total_cnt");
			selectdto.setExWhereQuery("where vDD = '"+exdao.filter(Func.cStr(day))+"'");
			yesterday_cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
		}
		request.setAttribute("yesterday_cnt", yesterday_cnt);
		
		//총 방문자
		selectdto = new VisitCounterDTO();
		selectdto.setExTableName("total_counter");
		selectdto.setExColumn("count_value");
		int total_cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
		request.setAttribute("total_cnt", total_cnt);
		
		//오늘의 방문자수
		selectdto = new VisitCounterDTO();
		selectdto.setExTableName( "visit_counter_"+Func.date("Ym") );
		selectdto.setExColumn("count(*) as total_cnt");
		selectdto.setExWhereQuery("where vDD = '"+Func.date("d")+"'");
		int today_cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
		request.setAttribute("today_cnt", today_cnt);
	}


	/**
	 * @throws Exception 
	 * @title : menuList에 카운트, 그래프값 세팅 
	 * @method : setMenuCountGraph
	 */
	JSONArray setMenuCountGraph(JSONArray menuList, String count_table, String where_query, int depth, int max_depth, long totalcounter) throws Exception{
		
		SelectDTO selectdto = new SelectDTO();
		DecimalFormat df = new DecimalFormat("###,###");
		
		if(menuList.size() > 0){
			for(int i=0; i<menuList.size(); i++) {
				JSONObject cms1 = (JSONObject)menuList.get(i); 
				
				//카운트&그래프
				selectdto = new SelectDTO();
				selectdto.setExTableName(count_table);
				selectdto.setExColumn("sum(counter) as cnt");
				String ct_codeno = Func.cStr( cms1.get("ct_codeno") ).trim();
				String ct_menu_url = Func.cStr( cms1.get("ct_menu_url") ).trim();
				String where_query2 = where_query;
				where_query2 += " and page_codeno like '"+ct_codeno+"%'";
				/*
				if("".equals(ct_menu_url)){
					where_query2 += " and page_codeno = '"+ct_codeno+"'";
				}else{
					where_query2 += " and page_codeno in (select ct_codeno from homepage_menu where ct_menu_url = '"+ct_menu_url+"')";
				}
				*/
				System.out.println(depth+", where_query2="+where_query2);
				selectdto.setExWhereQuery(where_query2);
				long cnt_1 = Func.cLng( exdao.selectQueryColumn(selectdto) );
				String graph_1 = Func.drawGraph(cnt_1, totalcounter, depth);
				
				cms1.put("cnt", df.format(cnt_1));
				cms1.put("graph", graph_1);
				String ct_depth = Func.cStr( cms1.get("ct_depth") );
				if(max_depth > Func.cInt(ct_depth)){
					JSONArray menuList2 = (JSONArray) cms1.get("menuList");
					cms1.put("menuList", this.setMenuCountGraph(menuList2, count_table, where_query, depth+1, max_depth, totalcounter));
					//menuList = this.setMenuCountGraph(menuList2, count_table, where_query, v_depth, totalcounter);
				}
			}
		}
		return menuList;
	}
}
