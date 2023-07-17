package kr.co.nninc.ncms.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board.service.SkinService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

@Service("lib_eventService")
public class Lib_eventServiceImpl extends EgovAbstractServiceImpl implements SkinService {

	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		
		//게시물목록이 필요없기때문에 초기화
		List<HashMap<String,String>> dummyList = new ArrayList<HashMap<String,String>>();
		skinDTO.setBoardList(dummyList);
		
		String ss_m_id					= Func.getSession(request, "ss_m_id"); //세션아이디
		String is_ad_cms				= (String) request.getAttribute("is_ad_cms");
		
		String year						= Func.nvl( request.getParameter("year") );
		String month					= Func.nvl( request.getParameter("month") );
		if("".equals(year)){year = Func.date("Y");}
		if("".equals(month)){month = Func.date("m");}
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		
		int intThisYear						= Func.cInt( year );
		int intThisMonth					= Func.cInt( month );
		
		String intThisMonth2 = Func.cStr( intThisMonth );
		if(intThisMonth < 10){intThisMonth2 = "0"+intThisMonth2;}
		
		int gday						= Func.cInt( Func.date("d") ); //오늘날짜
		int datFirstDay					= Func.cInt( Func.date("w", Func.dateSerial( intThisYear, intThisMonth, 1 )) ); //요일 구하기 (num)
		int intLastDay					= Func.cInt( Func.date("d", Func.dateSerial( intThisYear, intThisMonth+1, 1-1 )) ); //마지막날 구하기
		//php 처리로직에 맞추기위해 요일값을 변경
		datFirstDay--;
		int jucnt						= Func.cInt( Func.formatDecimal( Math.ceil( (datFirstDay + intLastDay)/7.0 ), 0) );
		
		// 이전, 다음 만들기 
		int prevYear					= ( intThisMonth == 1 )? ( intThisYear - 1 ) : intThisYear; 
		int prevMonth					= ( intThisMonth == 1 )? 12 : ( intThisMonth - 1 ); 
		int nextYear					= ( intThisMonth == 12 )? ( intThisYear + 1 ) : intThisYear; 
		int nextMonth					= ( intThisMonth == 12 )? 1 : ( intThisMonth + 1 );
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
		
		//도서관일정별 연동할 자료설정
		String cl_category = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".cl_category["+config.get("a_num")+"]")).trim();
		String movie_a_num = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".movie_a_num["+config.get("a_num")+"]")).trim();
		String edusat_ct_idx = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".edusat_ct_idx["+config.get("a_num")+"]")).trim();
		
		//1일 부터 말일까지 루프
		SelectDTO selectdto = new SelectDTO();
		HashMap<String, List> todayMap = new HashMap();		//일정에등록된게시물
		for(int i=1;i<=intLastDay;i++){
			String cday2 = Func.cStr(i);
			String thisDate = "";
			
			//날짜계산
			if(Func.cInt(cday2) < 10) cday2 = "0"+cday2; //1~9숫자 앞에 0붙이기
			if(!cday2.equals("00")) thisDate = Func.dateSerial( intThisYear, intThisMonth, Func.cInt(cday2) );
			//일자별 게시물조회
			List<HashMap<String,String>> boardList = this.todayList(model, thisDate);
			for(int j=0; j <=boardList.size()-1; j++) {
				HashMap<String,String> board_new = boardList.get(j);

				String title_style = "";
				String show_str = "";
				String b_show_sdate_str = "";
				String b_show_edate_str = "";
				
				if("Y".equals(config.get("a_show")) && (is_ad_cms.equals("Y") || ( board_new.get("b_id").equals( ss_m_id ) && !ss_m_id.equals("") ) ) ){
					if(!board_new.get("b_show_sdate").equals("") && !board_new.get("b_show_edate").equals("")){
						b_show_sdate_str = board_new.get("b_show_sdate").substring(2, 10).replaceAll("-",".");
						b_show_edate_str = board_new.get("b_show_edate").substring(2, 10).replaceAll("-",".");
						
						show_str = "<span style='font-size:8pt;color:#667dcc;'>("+b_show_sdate_str+"~"+b_show_edate_str+")</span>&nbsp;&nbsp;";
						if( board_new.get("b_show_sdate").substring(0, 10).compareTo( Func.date("Y-m-d") ) > 0 || board_new.get("b_show_edate").substring(0, 10).compareTo(Func.date("Y-m-d")) < 0 ){
							title_style = "text-decoration:line-through;";
						}
					}
				}
				board_new.put("txt_title_style", title_style);
				board_new.put("txt_show", show_str);
			}
			todayMap.put(thisDate, boardList);
		}
		request.setAttribute("calendarMap", todayMap);
		
		String thisYm = Func.dateSerial(intThisYear, intThisMonth, 1).substring(0, 7);
		
		//일자별 휴관일검색
		HashMap<String, List> closeData = new HashMap();	//휴관일
		if(!"".equals(cl_category)){
			selectdto = new SelectDTO();
			selectdto.setExTableName("close");
			selectdto.setExColumn("cl_name, cl_date");
			selectdto.setExWhereQuery("where cl_date like '"+thisYm+"%' and cl_category = '"+cl_category+"'");
			List<HashMap<String,String>>tmpcloseList = exdao.selectQueryTable(selectdto);
			for(int i=1;i<=intLastDay;i++){
				String thisDate = Func.dateSerial(intThisYear, intThisMonth, i);
				List<HashMap<String,String>>closeList = new ArrayList<HashMap<String,String>>();
				for(int j=0;j<=tmpcloseList.size()-1;j++){
					HashMap<String,String>close = tmpcloseList.get(j);
					if(thisDate.equals(close.get("cl_date"))){
						closeList.add(close);
					}
				}
				closeData.put(thisDate, closeList);
			}
			
		}
		request.setAttribute("closeData", closeData);
		
		//일자별 영화상영
		HashMap<String, List> movieData = new HashMap();	//영화상영
		if(!"".equals(movie_a_num)){
			selectdto = new SelectDTO();
			selectdto.setExTableName("board");
			selectdto.setExColumn("a_num, b_num, b_subject, b_sdate");
			selectdto.setExWhereQuery("where a_num = '"+movie_a_num+"' and b_sdate like '"+thisYm+"%' and b_open = 'Y' and b_look = 'Y'");
			selectdto.setExOrderByQuery("order by b_temp1");
			List<HashMap<String,String>>tmpMovieList = exdao.selectQueryTable(selectdto);
			for(int i=1;i<=intLastDay;i++){
				String thisDate = Func.dateSerial(intThisYear, intThisMonth, i);
				List<HashMap<String,String>>movieList = new ArrayList<HashMap<String,String>>();
				for(int j=0;j<=tmpMovieList.size()-1;j++){
					HashMap<String,String>movie = tmpMovieList.get(j);
					if(thisDate.equals(movie.get("b_sdate"))){
						movieList.add(movie);
					}
				}
				movieData.put(thisDate, movieList);
			}
		}
		request.setAttribute("movieData", movieData);
		
		//일자별 문화강좌
		HashMap<String, List> edusatMap = new HashMap();	//문화행사 리스트
		if(!"".equals(edusat_ct_idx)){
			//이번달에 강의기간이 포함된 강좌조회
			selectdto = new SelectDTO();
			selectdto.setExTableName("edusat");
			selectdto.setExColumn("edu_idx, ct_idx, edu_subject, edu_sdate, edu_edate, edu_week");
			String where_query = "where ct_idx = '"+edusat_ct_idx+"' and end_chk = 'Y'";
			where_query += " and (edu_sdate <= '"+thisYm+"-31' and edu_edate >= '"+thisYm+"-01')";
			selectdto.setExWhereQuery(where_query);
			selectdto.setExOrderByQuery("order by edu_code");
			List<HashMap<String,String>>tmpEdusatList = exdao.selectQueryTable(selectdto);
			for(int i=1;i<=intLastDay;i++){
				String thisDate = Func.dateSerial(intThisYear, intThisMonth, i);
				String thisweek = Func.date("w", thisDate);
				thisweek = Func.cStr( Func.cInt(thisweek)-1 );
				int intThisDate = Func.cInt( thisDate.replaceAll("-", "") );
				List<HashMap<String,String>>edusatList = new ArrayList<HashMap<String,String>>();
				
				for(int j=0;j<=tmpEdusatList.size()-1;j++){
					HashMap<String,String>edusat = tmpEdusatList.get(j);
					String edu_week = edusat.get("edu_week");
					int intEdu_sdate = Func.cInt( edusat.get("edu_sdate").replaceAll("-", "") );
					int intEdu_edate = Func.cInt( edusat.get("edu_edate").replaceAll("-", "") );
					//이날짜가 강의기간에 포함되어 있고 요일도 포함되어있다면
					if(intThisDate >= intEdu_sdate && intThisDate <= intEdu_edate){
						if("9".equals(edu_week) || edu_week.contains(thisweek)){
							edusatList.add(edusat);
						}
					}
				}
				edusatMap.put(thisDate, edusatList);
			}
		}
		request.setAttribute("edusatMap", edusatMap);

	}

	@Override
	public void view(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void modify(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * @title : 일자별 게시물목록 조회 - 달력형스킨에 공통으로 쓰일 수 있다.
	 * @method : todayList
	 */
	public List<HashMap<String,String>> todayList(Model model, String thisDate) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String ss_m_id = Func.getSession(request, "ss_m_id"); // 세션아이디
		String ss_m_jumin = Func.getSession(request, "ss_m_jumin"); // 세션 식별번호
		if("".equals(ss_m_jumin)){ss_m_jumin = Func.getSession(request, "ss_m_dupinfo");}
		
		String is_ad_cms = (String) request.getAttribute("is_ad_cms"); //관리권한
		
		BoardDTO selectdto = new BoardDTO();	//select전용 dto
		selectdto.setExTableName("board");
		selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		String whereQuery = "";
		String orderQuery = "";
		
		whereQuery = " where a_num = '"+config.get("a_num")+"' and ((b_sdate <= '"+thisDate+"' AND b_edate >= '"+thisDate+"') or b_sdate = '"+thisDate+"')";
		orderQuery = "ORDER BY b_num asc";
		
		if(!"Y".equals(is_ad_cms)){
			whereQuery += " and b_look = 'Y'";
		}
		
		// 회원제
		if ("Y".equals(config.get("a_member"))) {
			if (!"".equals(ss_m_jumin)) {
				whereQuery += " AND ( ( b_jumin = '" + ss_m_jumin + "' and b_jumin <> '' ) or ( b_id = '"
						+ ss_m_id + "' and b_id <> '' ) )";
			} else {
				whereQuery += " AND b_id = '" + ss_m_id + "' and b_id <> ''";
			}
		}
		
		if("Y".equals(config.get("a_cate"))){	//분류기능
			String v_cate = exdao.filter(request.getParameter("v_cate"));
			if(!"".equals(v_cate)){
				whereQuery += " AND b_cate = '"+v_cate+"'";
			}
		}
		//검색조건 적용
		selectdto.setExWhereQuery(whereQuery);
		
		//조회컬럼
		String s_fields = "b_num, b_name, b_subject, b_content, b_catename, b_open, b_sbjclr, b_cate";
		s_fields += ", b_show_sdate, b_show_edate, b_id, b_temp8, b_sdate, b_edate";
		selectdto.setExColumn(s_fields);
		
		//정렬
		selectdto.setExOrderByQuery(orderQuery);
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
		
		return boardList;
	}

	@Override
	public void deleteOk(Model model) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
