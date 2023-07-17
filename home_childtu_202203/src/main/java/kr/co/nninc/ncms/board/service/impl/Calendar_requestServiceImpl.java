package kr.co.nninc.ncms.board.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import kr.co.nninc.ncms.board.service.BoardCalendarReqConfDTO;
import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board.service.SkinService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

@Service("calendar_requestService")
public class Calendar_requestServiceImpl extends EgovAbstractServiceImpl implements SkinService {

	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		
		SelectDTO selectdto = new SelectDTO();
		
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
		
		
		
		String thisYm = Func.dateSerial(intThisYear, intThisMonth, 1).substring(0, 7);
		
		HashMap<String, String> brTypeMap = new HashMap(); //견학신청유무
		
		//일자별 휴관일검색
		String cl_category = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".cl_category["+config.get("a_num")+"]")).trim();
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
		
		String req_month = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".req_month["+config.get("a_num")+"]")).trim();
		String req_date = Func.addGetDate2(Func.cInt(req_month), "yyyy-MM");
		request.setAttribute("req_date", req_date);
		
		String default_week = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".default_week["+config.get("a_num")+"]")).trim();
		
		//1일 부터 말일까지 루프
		HashMap<String, List> todayMap = new HashMap();
		for(int i=1;i<=intLastDay;i++){
			String cday2 = Func.cStr(i);
			String thisDate = "";
			
			//날짜계산
			if(Func.cInt(cday2) < 10) cday2 = "0"+cday2; //1~9숫자 앞에 0붙이기
			if(!cday2.equals("00")) thisDate = Func.dateSerial( intThisYear, intThisMonth, Func.cInt(cday2) );
			
			//0:신청불가, 1:신청가능, 2:신청마감
			String br_type = "0";
			
			//기본신청가능 요일
			if(!"".equals(default_week)){
				String this_week = Func.date("w", thisDate);
				this_week = Func.cStr( Func.cInt(this_week)-1 );	//달력의 요일과 값을 맞추기 위해 -1
				if(default_week.contains(this_week)){
					br_type = "1";
				}
			}
			System.out.println("week="+thisDate+" | "+br_type);
			
			//관리자 설정조회
			selectdto = new SelectDTO();
			selectdto.setExTableName("board_calendar_req_conf");
			selectdto.setExColumn(new BoardCalendarReqConfDTO());
			selectdto.setExWhereQuery("where be_a_num = '"+config.get("a_num")+"' and be_date = '"+exdao.filter(thisDate)+"'");
			HashMap<String,String>conf = exdao.selectQueryRecord(selectdto);
			if(conf.size() > 0){
				br_type = Func.nvl( conf.get("be_rtype") ).trim();
			}
			if("".equals(br_type)){br_type = "0";}
			System.out.println("conf="+thisDate+" | "+br_type);
			
			//신청제한 일자확인(N개월 후 제한)
			if("1".equals(br_type)){
				SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
				Date day1 = null;
				Date day2 = null;
				try {
					day1 = format.parse( thisDate );
					day2 = format.parse( Func.date("Y-m-d") );
				} catch (ParseException e) {
					e.printStackTrace();
				}
				int compare = day1.compareTo( day2 );
				// day1 < day2 or day1 = day2
				// day1 < day2 중앙도서관의 요청으로 당일까지 신청되도록 200108
				//if(compare < 0 || compare == 0){
				if(compare < 0){
					br_type = "2";
				}else if(compare == 0){
					if(Func.cInt(Func.date("H")) >= 17){
						br_type = "2";
					}
				}
				if(!"Y".equals(is_ad_cms)){
					SimpleDateFormat format2 = new SimpleDateFormat( "yyyy-MM" );
					Date day3 = null;
					Date day4 = null;
					try {
						day3 = format2.parse( thisDate.substring(0, 7) );
						day4 = format2.parse( req_date );
					} catch (ParseException e) {
						//e.printStackTrace();
					}
					// day3 > day4
					int compare2 = day3.compareTo( day4 );
					if(compare2 > 0){
						br_type = "0";
					}
				}
			}
			System.out.println("month="+thisDate+" | "+br_type);
			
			//신청마감확인
			if("1".equals(br_type)) {
				
				br_type = "2";
				
				selectdto = new SelectDTO();
				selectdto.setExTableName("board");
				selectdto.setExColumn("count(*)");
				//방문시간별 신청확인
				for(int j=1; j<=4; j++) {
					//관리자 설정 조회
					String be_time = Func.nvl( conf.get("be_time"+j) ).trim();
					String be_count = Func.nvl( conf.get("be_count"+j) ).trim();
					
					//기본요일별 설정 조회
					String this_week = Func.date("w", thisDate);
					this_week = Func.cStr( Func.cInt(this_week)-1 );	//달력의 요일과 값을 맞추기 위해 -1
					if("".equals(be_time)){be_time = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".w"+this_week+".be_time"+j+"["+config.get("a_num")+"]")).trim();}
					if("".equals(be_count)){be_count = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".w"+this_week+".be_count"+j+"["+config.get("a_num")+"]")).trim();}
					
					//기본 설정 조회
					if("".equals(be_time)){be_time = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".be_time"+j+"["+config.get("a_num")+"]")).trim();}
					if("".equals(be_count)){be_count = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".be_count"+j+"["+config.get("a_num")+"]")).trim();}
					
					if(!"".equals(be_time) && !"".equals(be_count)){
						String where_query = "where a_num = '"+config.get("a_num")+"'";
						where_query += " and b_sdate = '"+exdao.filter(thisDate)+"'";
						where_query += " and b_temp2 = '"+be_time+"'";
						where_query += " and b_temp8 <> '예약취소'";
						selectdto.setExWhereQuery(where_query);
						int cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
						if(cnt < Func.cInt(be_count)){
							br_type = "1";
							break;
						}
					}
				}
			}
			System.out.println("req="+thisDate+" | "+br_type);
			
			//휴관일 : 신청불가상태가 아니고 관리자설정이 없을경우
			if(!"0".equals(br_type) && conf.size() == 0) {
				if(closeData.get(thisDate).size() > 0){
					br_type = "0";
				}
			}
			System.out.println("close="+thisDate+" | "+br_type);
			
			
			
			brTypeMap.put(thisDate, br_type);
			
			
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
		request.setAttribute("brTypeMap", brTypeMap);
	}

	@Override
	public void view(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String b_num = exdao.filter(request.getParameter("b_num"));
		String b_sdate = Func.nvl(request.getParameter("b_sdate"));
		
		SelectDTO selectdto = new SelectDTO();
		HashMap<String,String>board = new HashMap<String,String>();
		
		//신청일자 설정
		if("".equals(b_num)){
			if("".equals(b_sdate)){b_sdate = Func.date("Y-m-d");}
			board.put("b_sdate",b_sdate);
		}
		
		//방문시간설정
		List<String> time_config = new ArrayList<String>();
		List<String> max_count = new ArrayList<String>();
		List<String> cnt_array = new ArrayList<String>();
		selectdto.setExTableName("board_calendar_req_conf");
		selectdto.setExColumn(new BoardCalendarReqConfDTO());
		selectdto.setExWhereQuery("where be_a_num = '"+config.get("a_num")+"' and be_date = '"+exdao.filter(b_sdate)+"'");
		HashMap<String,String>conf = exdao.selectQueryRecord(selectdto);
		for(int j=1; j<=4; j++) {
			
			//관리자 일별설정 조회
			String be_time = Func.nvl( conf.get("be_time"+j) ).trim();
			String be_count = Func.nvl( conf.get("be_count"+j) ).trim();
			
			//기본요일별 설정 조회
			String this_week = Func.date("w", b_sdate);
			this_week = Func.cStr( Func.cInt(this_week)-1 );	//달력의 요일과 값을 맞추기 위해 -1
			if("".equals(be_time)){ be_time = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".w"+this_week+".be_time"+j+"["+config.get("a_num")+"]")).trim(); }
			if("".equals(be_count)){ be_count = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".w"+this_week+".be_count"+j+"["+config.get("a_num")+"]")).trim(); }
			
			//기본설정 조회
			if("".equals(be_time)){be_time = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".be_time"+j+"["+config.get("a_num")+"]")).trim();}
			if("".equals(be_count)){be_count = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".be_count"+j+"["+config.get("a_num")+"]")).trim();}
			if(!"".equals(be_time) && !"".equals(be_count)){
				time_config.add(be_time);
				max_count.add(be_count);
			}
		}
		
		
		//방문시간별 신청확인
		selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("count(*)");
		for(int j=0;j<=time_config.size()-1;j++){
			String where_query = "where a_num = '"+config.get("a_num")+"'";
			where_query += " and b_sdate = '"+exdao.filter(b_sdate)+"'";
			where_query += " and b_temp2 = '"+time_config.get(j)+"'";
			where_query += " and b_temp8 <> '예약취소'";
			selectdto.setExWhereQuery(where_query);
			String cnt = Func.nvl( exdao.selectQueryColumn(selectdto) ).trim();
			cnt_array.add(cnt);
		}
		if(time_config.size() == 1){	//신청시간이 하나일 경우 b_temp2에 저장
			board.put("b_temp2", time_config.get(0));
		}
		
		//기본값 설정한 맵을 스킨처리에 적용
		skinDTO.setBoardMap(board);
		
		request.setAttribute("cnt_array", cnt_array);
		request.setAttribute("max_count", max_count);
		request.setAttribute("time_config", time_config);

	}

	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		//writeOk 처리로 전달할 dto 생성
		BoardDTO board = (BoardDTO) map.get("boarddto");

		//파라미터 체크
		String chk_str = "";
		
		//희망시간 체크
		String[] b_temp_arr = board.getB_temp2().split("\\^");
		if(b_temp_arr.length > 0) {
			board.setB_temp2(b_temp_arr[0]);
			//
			boolean ret_type = false;
			
			SelectDTO selectdto = new SelectDTO();
			selectdto.setExTableName("board");
			selectdto.setExColumn("count(*)");
			String whereQuery = "where a_num = '"+config.get("a_num")+"'";
			whereQuery += " and b_sdate = '"+exdao.filter( board.getB_sdate() )+"'";
			whereQuery += " and b_temp2 = '"+exdao.filter( board.getB_temp2() )+"'";
			whereQuery += " and b_temp8 <> '예약취소'";
			selectdto.setExWhereQuery(whereQuery);
			int cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
			if(cnt < Func.cInt(b_temp_arr[1])){
				ret_type = true;
			}
			if(!ret_type){
				chk_str += "해당일자는 신청이 마감되었습니다.";
			}
		}
		//에러메시지
		if(!"".equals(chk_str)){
			skinDTO.setBefore_msg( messageService.backMsg(model, chk_str) );
			return;
		}
		
		
		//월제한 건수 체크
		String month_limit = Func.nvl( CommonConfig.getBoard(config.get("a_level")+".month_limit["+config.get("a_num")+"]") ).trim();
		if(!"".equals(month_limit) && !"0".equals(month_limit)){
			
			SelectDTO selectdto = new SelectDTO();
			selectdto.setExTableName("board");
			selectdto.setExColumn("count(*)");
			String whereQuery = "where a_num = '"+config.get("a_num")+"'";
			whereQuery += " and b_sdate like '"+exdao.filter( board.getB_sdate() ).substring(0,7)+"%'";
			
			if("".equals(Func.getSession(request, "ss_m_id"))){
				whereQuery += " and b_name = '"+exdao.filter(board.getB_name())+"'";
				whereQuery += " and b_phone1 = '"+exdao.filter(board.getB_phone1())+"'";
				whereQuery += " and b_phone2 = '"+exdao.filter(board.getB_phone2())+"'";
				whereQuery += " and b_phone3 = '"+exdao.filter(board.getB_phone3())+"'";
			}else{
				whereQuery += " and b_id = '"+exdao.filter(Func.getSession(request, "ss_m_id"))+"'";
			}
			
			whereQuery += " and b_temp8 <> '예약취소'";
			selectdto.setExWhereQuery(whereQuery);
			int cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
			
			if(cnt >= Func.cInt(month_limit)){
				chk_str += "이번달에 "+month_limit+"회 이상 신청하셨습니다.";
			}
		}
		
		//에러메시지
		if(!"".equals(chk_str)){
			skinDTO.setBefore_msg( messageService.backMsg(model, chk_str) );
			return;
		}

		//boardDTO writeOk로 전달
		skinDTO.setBoardDTO(board);

	}

	@Override
	public void modify(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		//writeOk 처리로 전달할 dto 생성
		BoardDTO board = (BoardDTO) map.get("boarddto");
		
		if("".equals(board.getB_temp8())) board.setB_temp8("대기");
		
		//update 제외처리
		board.setB_temp2(null);

		//boardDTO writeOk로 전달
		skinDTO.setBoardDTO(board);

	}

	@Override
	public void deleteOk(Model model) throws Exception {
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
		orderQuery = "ORDER BY b_temp2 asc";
		
		if(!"Y".equals(is_ad_cms)){
			whereQuery += " and b_look = 'Y'";
			whereQuery += " and b_temp8 <> '예약취소'";
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
		s_fields += ", b_show_sdate, b_show_edate, b_id, b_sdate, b_edate";
		s_fields += ", b_temp2, b_temp8";
		selectdto.setExColumn(s_fields);
		
		//정렬
		selectdto.setExOrderByQuery(orderQuery);
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
		
		return boardList;
	}
	
	/**
	 * @title : calendar_request 스킨 dateConf 처리 (도서관견학설정)
	 * @method : dateConf
	 */
	public void dateConf(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String is_ad_cms = (String) request.getAttribute("is_ad_cms"); //관리권한
		if(!"Y".equals(is_ad_cms)){
			String before_msg = messageService.closeMsg(model, "접근권한이 없습니다.");
			skinDTO.setBefore_msg(before_msg);
			return;
		}
		
		SelectDTO selectdto = new SelectDTO();
		
		//날짜
		String be_date = exdao.filter(request.getParameter("be_date"));
		String be_rtype = exdao.filter(request.getParameter("be_rtype"));
		
		selectdto.setExTableName("board_calendar_req_conf");
		selectdto.setExColumn(new BoardCalendarReqConfDTO());
		selectdto.setExWhereQuery("where be_a_num = '"+config.get("a_num")+"' and be_date = '"+be_date+"'");
		HashMap<String,String>excursconf = exdao.selectQueryRecord(selectdto);
		if(excursconf.size() == 0){
			excursconf.put("be_date", be_date);
			excursconf.put("be_rtype", be_rtype);

			for(int i=1;i<=4;i++){
				
				String be_time = "";
				String be_count = "";
				
				//기본요일별 설정 조회
				String this_week = Func.date("w", be_date);
				this_week = Func.cStr( Func.cInt(this_week)-1 );	//달력의 요일과 값을 맞추기 위해 -1
				be_time = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".w"+this_week+".be_time"+i+"["+config.get("a_num")+"]")).trim();
				be_count = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".w"+this_week+".be_count"+i+"["+config.get("a_num")+"]")).trim();
				
				//기본 설정 조회
				if("".equals(be_time)){be_time = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".be_time"+i+"["+config.get("a_num")+"]")).trim();}
				if("".equals(be_count)){be_count = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".be_count"+i+"["+config.get("a_num")+"]")).trim();}
				
				if(!"".equals(be_time)){excursconf.put("be_time"+i, be_time);}
				if(!"".equals(be_count)){excursconf.put("be_count"+i, be_count);}
			}
		}
		request.setAttribute("excursconf", excursconf);
		
	}
	
	/**
	 * @title : calendar_request 스킨 dateConfOk 처리 (도서관견학설정처리)
	 * @method : dateConfOk
	 */
	public void dateConfOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String is_ad_cms = (String) request.getAttribute("is_ad_cms"); //관리권한
		String ss_m_id = Func.getSession(request, "ss_m_id"); //세션아이디
		
		if(!"Y".equals(is_ad_cms)){
			String before_msg = messageService.closeMsg(model, "접근권한이 없습니다.");
			skinDTO.setBefore_msg(before_msg);
			return;
		}
		
		String be_num = exdao.filter(request.getParameter("be_num"));
		
		BoardCalendarReqConfDTO btrcdto = new BoardCalendarReqConfDTO();
		btrcdto.setBe_regdate(Func.date("Y-m-d H:i:s"));
		btrcdto.setBe_m_id(ss_m_id);
		btrcdto.setBe_a_num(config.get("a_num"));
		
		btrcdto.setBe_date( exdao.filter(request.getParameter("be_date")) );
		btrcdto.setBe_rtype( exdao.filter(request.getParameter("be_rtype")) );
		btrcdto.setBe_type( exdao.filter(request.getParameter("be_type")) );
		
		btrcdto.setBe_count1( Func.cStr(Func.cInt(request.getParameter("be_count1"))) );
		btrcdto.setBe_time1( request.getParameter("be_time1") );
		btrcdto.setBe_count2( Func.cStr(Func.cInt(request.getParameter("be_count2"))) );
		btrcdto.setBe_time2( request.getParameter("be_time2") );
		btrcdto.setBe_count3( Func.cStr(Func.cInt(request.getParameter("be_count3"))) );
		btrcdto.setBe_time3( request.getParameter("be_time3") );
		btrcdto.setBe_count4( Func.cStr(Func.cInt(request.getParameter("be_count4"))) );
		btrcdto.setBe_time4( request.getParameter("be_time4") );
		
		//insert, update 제외
		btrcdto.setBe_num(null);
		
		btrcdto.setExTableName("board_calendar_req_conf");
		if("".equals(be_num)){	//등록
			exdao.insert(btrcdto);
		}else{
			btrcdto.setExWhereQuery("where be_num = '"+be_num+"'");
			exdao.update(btrcdto);
		}
		
		String after_msg = messageService.closeRedirect(model, "등록되었습니다.", "");
		skinDTO.setAfter_msg( after_msg );
		
	}
	
	
	/**
	 * @title : calendar_request 스킨 dateConfReset 처리 (도서관견학설정초기화)
	 * @method : dateConfReset
	 */
	public void dateConfReset(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String is_ad_cms = (String) request.getAttribute("is_ad_cms"); //관리권한
		if(!"Y".equals(is_ad_cms)){
			String before_msg = messageService.closeMsg(model, "접근권한이 없습니다.");
			skinDTO.setBefore_msg(before_msg);
			return;
		}
		
		String be_num = exdao.filter( request.getParameter("be_num") );
		if(!"".equals(be_num)){
			exdao.executeQuery("delete from board_calendar_req_conf where be_num = '"+be_num+"'");
		}
		
		String after_msg = messageService.closeRedirect(model, "초기화 되었습니다.", "");
		skinDTO.setAfter_msg( after_msg );
		
	}
	
	
	
	/**
	 * @title : calendar_request 스킨 simpleList 처리 (내가신청한 봉사)
	 * @method : simpleList
	 */
	public void simpleList(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		
		String ss_m_id = exdao.filter(Func.getSession(request, "ss_m_id"));
		String ss_m_jumin = exdao.filter(Func.getSession(request, "ss_m_dupinfo"));
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board_total");
		selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		selectdto.setExColumn(exdao.getFieldNames(new BoardDTO())+", a_site_dir, a_bbsname, a_tablename, a_pageurl, a_level");
		String whereQuery = "where a_level = '"+config.get("a_level")+"'";	//봉사게시판전체 조회(홈페이지 메뉴구성에 따라 조정필요)
		//whereQuery += " and a_num = '"+config.get("a_num")+"'";	//개별사이트에서 조회시
		if(!"".equals(ss_m_id)){
			whereQuery += " and b_id = '"+ss_m_id+"'";
		}else if(!"".equals(ss_m_jumin)){
			whereQuery += " and b_jumin = '"+ss_m_jumin+"'";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by b_sdate desc, b_num desc");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		
		List<HashMap<String,String>>boardList = new ArrayList<HashMap<String,String>>();
		if(!"".equals(ss_m_id) || !"".equals(ss_m_jumin)){
			boardList = exdao.selectQueryPage(selectdto);
			recordcount = Func.cInt( boardList.get(0).get("totalcount") ); // 전체레코드 수
			boardList.remove(0);	//총검색개수행(첫번째행)을 삭제
			
			selectdto = new SelectDTO();
			selectdto.setExTableName("code_config");
			selectdto.setExColumn("ct_name");
			for(int i=0;i<=boardList.size()-1;i++){
				HashMap<String,String>board = boardList.get(i);
				
				//도서관명 추출
				selectdto.setExWhereQuery("where ct_ref = '1' and ct_site_dir = (select a_site_dir from board_config where a_num = '"+board.get("a_num")+"')");
				String libname = exdao.selectQueryColumn(selectdto);
				board.put("libname", libname);
			}
		}
		request.setAttribute("recordcount", recordcount);
		request.setAttribute("boardList", boardList);
		
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수
		
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = pagePerBlock;	//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring2 = paging.setQueryString(request, "v_search, v_keyword, v_gnum");
		
		String pagingtag = paging.execute(querystring2);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pagesize", pagesize);
		//페이징문자열 생성 끝
		
	}
	
	
	/**
	 * @title : calendar_request 스킨 excel 처리 (월별 엑셀출력)
	 * @method : excel
	 */
	public void excel(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String sh_b_sdate_y = exdao.filter( request.getParameter("sh_b_sdate_y") );
		String sh_b_sdate_m = exdao.filter( request.getParameter("sh_b_sdate_m") );
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExKeyColumn("b_num");	//고유컬럼 설정 필수
		selectdto.setExColumn(new BoardDTO());
		selectdto.setExWhereQuery("where a_num = '"+exdao.filter(config.get("a_num"))+"' and b_sdate like '"+sh_b_sdate_y+"-"+sh_b_sdate_m+"%'");
		selectdto.setExOrderByQuery("order by b_sdate");
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(selectdto);
		request.setAttribute("boardList", boardList);
		
	}

}
