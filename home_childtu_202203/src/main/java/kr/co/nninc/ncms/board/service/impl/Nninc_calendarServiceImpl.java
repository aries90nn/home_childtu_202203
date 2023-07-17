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
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

@Service("nninc_calendarService")
public class Nninc_calendarServiceImpl extends EgovAbstractServiceImpl implements SkinService {

	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		
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
		
		
		//1일 부터 말일까지 루프
		HashMap<String, List> todayMap = new HashMap();
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
