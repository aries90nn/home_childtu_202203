package kr.co.nninc.ncms.close.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.close.service.CloseDTO;
import kr.co.nninc.ncms.close.service.CloseService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

@Service("closeService")
public class CloseServiceImpl extends EgovAbstractServiceImpl implements CloseService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/**
	 * @title : 휴관일설정
	 * @method : close
	 */
	@Override
	public void close(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String year = Func.nvl( request.getParameter("year") );
		String month = Func.nvl( request.getParameter("month") );

		int intThisYear  = Func.cInt( Func.date("Y") );
		int intThisMonth  = Func.cInt( Func.date("m") );

		if ( !year.equals("") ) { intThisYear = Func.cInt( year); }
		if ( !month.equals("") ) { intThisMonth = Func.cInt( month ); }


		String intThisMonth2 =  Func.cStr( intThisMonth );
		if(intThisMonth < 10){intThisMonth2 = "0"+intThisMonth2;}

		int gday		= Func.cInt( Func.date("d") ); //오늘날짜
		int datFirstDay	= Func.cInt( Func.date("w", Func.dateSerial( intThisYear, intThisMonth, 1 )) ); //요일 구하기 (num)
		int intLastDay	= Func.cInt( Func.date("d", Func.dateSerial( intThisYear, intThisMonth+1, 1-1 )) ); //마지막날 구하기

		//php 처리로직에 맞추기위해 요일값을 변경
		datFirstDay--;
		int jucnt			= Func.cInt( Func.formatDecimal( Math.ceil( (datFirstDay + intLastDay)/7.0 ), 0) );


		// 이전, 다음 만들기 
		int prevYear		= ( intThisMonth == 1 )? ( intThisYear - 1 ) : intThisYear; 
		int prevMonth		= ( intThisMonth == 1 )? 12 : ( intThisMonth - 1 ); 

		int nextYear		= ( intThisMonth == 12 )? ( intThisYear + 1 ) : intThisYear; 
		int nextMonth		= ( intThisMonth == 12 )? 1 : ( intThisMonth + 1 ); 
		
		//System.out.println("w="+Func.date("w"));
		
		request.setAttribute("datFirstDay", datFirstDay);
		request.setAttribute("intLastDay", intLastDay);
		request.setAttribute("jucnt", jucnt);
		request.setAttribute("prevYear", prevYear);
		request.setAttribute("prevMonth", prevMonth);
		request.setAttribute("nextYear", nextYear);
		request.setAttribute("nextMonth", nextMonth);
		request.setAttribute("intThisYear", intThisYear);
		request.setAttribute("intThisMonth", intThisMonth);
		request.setAttribute("intThisMonth2", intThisMonth2);
		request.setAttribute("year", Func.date("Y"));
		request.setAttribute("month", Func.date("m"));
		
		//월별 휴관일검색
		String cl_category = Func.nvl( request.getParameter("cl_category") );
		String sh_ym = Func.cStr(intThisYear)+"-"+intThisMonth2;
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("close");
		selectdto.setExColumn(new CloseDTO());
		String where_query = "where cl_date like '"+sh_ym+"%'";
		if(!"".equals(cl_category)){
			where_query += " and cl_category = '"+cl_category+"'";
		}
		selectdto.setExWhereQuery(where_query);
		List<HashMap<String,String>>closeList = exdao.selectQueryTable(selectdto);
		
		HashMap<String, HashMap<String,String>> closeData = new HashMap<String, HashMap<String,String>>();
		for(int i=0;i<=closeList.size()-1;i++){
			HashMap<String,String>close = closeList.get(i);
			closeData.put(close.get("cl_date"), close);
		}
		request.setAttribute("closeData", closeData);
	}

	/**
	 * @title : 휴관일설정처리
	 * @method : closeOk
	 */
	@Override
	public void closeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		CloseDTO closedto = (CloseDTO) Func.requestAll(map.get("closedto"));
		
		String proc_type = Func.nvl( request.getParameter("proc_type") );
		String[] chk = request.getParameterValues("chk");
		String cl_category = exdao.filter(request.getParameter("cl_category"));
		
		String cl_name = "";
		if("1".equals(proc_type)){	//휴관일
			cl_name = "휴관일";
		}else if("2".equals(proc_type)){	//법정공휴일
			cl_name = "법정공휴일";
		}else{
			cl_name = "휴관일";
		}
		
		closedto.setExTableName("close");
		closedto.setCl_name(cl_name);
		closedto.setCl_idx(null);
		for(int i=0;i<=chk.length-1;i++){
			exdao.executeQuery("delete from close where cl_date = '"+chk[i]+"' and cl_category = '"+cl_category+"'");
			if(!"0".equals(proc_type)){
				closedto.setCl_date(chk[i]);
				exdao.insert(closedto);
			}
		}

	}

}
