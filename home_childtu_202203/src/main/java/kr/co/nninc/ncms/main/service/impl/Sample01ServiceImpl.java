package kr.co.nninc.ncms.main.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.main.service.MainSkinService;

@Service("sample01Service")
public class Sample01ServiceImpl extends MainServiceImpl implements MainSkinService {

	
	/**
	 * @title : 메인처리
	 * @method : main
	 */
	@Override
	public void main(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		bannerList(model);

		popupList(model);

		banner2List(model);
		
		monthEvent(model);
		
		List<HashMap<String,String>>noticeList = boardList(model, "47471516,72205222,76634026,11547698,19948620,65499793", 3);
		request.setAttribute("noticeList", noticeList);

		//ajaxBestBookList(model);
	}
	
	
	/**
	 * @title : 메인휴관일 ajax 처리
	 * @method : ajaxCloseList
	 */
	public void ajaxCloseList(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");

		String days = closeListMain(model);
		request.setAttribute("days", days);

	}
	
	/**
	 * @title : 메인행사 ajax 처리
	 * @method : ajaxCloseList
	 */
	public void ajaxEventList(Model model) throws Exception {
		
		monthEvent(model);

	}
	
	/**
	 * @title : 메인공지 ajax 처리
	 * @method : ajaxBoardList
	 */
	public void ajaxBoardList(Model model) throws Exception {
		Map map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		List<HashMap<String,String>>noticeList = boardList(model, 3);
		request.setAttribute("noticeList", noticeList);

	}

}
