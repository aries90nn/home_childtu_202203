package kr.co.nninc.ncms.board.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board.service.SkinService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

@Service("movieService")
public class MovieServiceImpl extends EgovAbstractServiceImpl implements SkinService {

	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		//페이징이 없는 목록
		config.put("a_displaysu", "99");
		
		//파라미터정보 ********************************************************
		String sh_b_sdate_y			= exdao.filter( request.getParameter("sh_b_sdate_y"));
		String sh_b_sdate_m			= exdao.filter( request.getParameter("sh_b_sdate_m"));
		//****************************************************************

		if("".equals(sh_b_sdate_y))	sh_b_sdate_y = Func.date("Y");
		if("".equals(sh_b_sdate_m))	sh_b_sdate_m = Func.date("m");
		
		//select쿼리 추가 또는 수정
		String where_query = " AND year(b_sdate) = '"+sh_b_sdate_y+"' AND month(b_sdate) = '"+Func.cInt(sh_b_sdate_m)+"'";
		skinDTO.setWhere_query(where_query);
		skinDTO.setOrderby_query("order by b_sdate asc");
		
		
		// 이전, 다음 만들기 
		int prevYear				= ( Func.cInt(sh_b_sdate_y) == 1 )? ( Func.cInt(sh_b_sdate_y) - 1 ) : Func.cInt(sh_b_sdate_y); 
		int prevMonth			= ( Func.cInt(sh_b_sdate_m) == 1 )? 12 : ( Func.cInt(sh_b_sdate_m) - 1 ); 
		int nextYear				= ( Func.cInt(sh_b_sdate_m) == 12 )? ( Func.cInt(sh_b_sdate_y) + 1 ) : Func.cInt(sh_b_sdate_y); 
		int nextMonth			= ( Func.cInt(sh_b_sdate_m) == 12 )? 1 : ( Func.cInt(sh_b_sdate_m) + 1 );
		
		sh_b_sdate_m = Func.zerofill(sh_b_sdate_m, 2, "0");
		
		request.setAttribute("prevYear", prevYear);
		request.setAttribute("prevMonth", prevMonth);
		request.setAttribute("nextYear", nextYear);
		request.setAttribute("nextMonth", nextMonth);
		request.setAttribute("year", Func.date("Y"));
		request.setAttribute("month", Func.date("m"));
		request.setAttribute("sh_b_sdate_y", Func.cInt(sh_b_sdate_y));
		request.setAttribute("sh_b_sdate_m", Func.cInt(sh_b_sdate_m));

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

	@Override
	public void deleteOk(Model model) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
