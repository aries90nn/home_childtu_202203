package kr.co.nninc.ncms.main.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.main.service.MainSkinService;

@Service("sample02Service")
public class Sample02ServiceImpl extends MainServiceImpl implements MainSkinService {

	/**
	 * @title : 메인처리
	 * @method : main
	 */
	@Override
	public void main(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");

		popupList(model);

		banner2List(model);
		
		monthEvent(model);
		
		eduList(model);
		
		bannerList(model);
		
		List<HashMap<String,String>>noticeList = boardList(model, "47471516", 2);
		request.setAttribute("noticeList", noticeList);
		
		/*
		request.setAttribute("sh_days", "7");
		request.setAttribute("manage_code", "MA");
		bookService.bestBookMain(model);
		*/
	}


	/**
	 * @title : 메인달력 ajax 처리
	 * @method : ajaxBoardList
	 */
	public void ajaxCalendar(Model model) throws Exception {
		monthEventCalendar(model);
		
	}
	
	
	
}

