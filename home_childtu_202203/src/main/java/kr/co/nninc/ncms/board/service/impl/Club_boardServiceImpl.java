package kr.co.nninc.ncms.board.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board.service.SkinService;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

public class Club_boardServiceImpl extends EgovAbstractServiceImpl implements SkinService {

	/**
	 * @title : 목록처리
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String v_cate = exdao.filter( request.getParameter("v_cate") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		if("".equals(v_cate)){
			skinDTO.setBefore_msg( messageService.backMsg(model, "동아리 게시판 정보가 없습니다.") );
			return;
		}
		selectdto = new SelectDTO();
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("ct_name");
		selectdto.setExWhereQuery("where ct_idx = '"+exdao.filter(v_cate)+"'");
		String club_board_name = exdao.selectQueryColumn(selectdto);
		request.setAttribute("club_board_name", club_board_name);
		
	}

	/**
	 * @title : 상세보기
	 * @method : view
	 */
	@Override
	public void view(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String v_cate = exdao.filter( request.getParameter("v_cate") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		if("".equals(v_cate)){
			skinDTO.setBefore_msg( messageService.backMsg(model, "동아리 게시판 정보가 없습니다.") );
			return;
		}
		selectdto = new SelectDTO();
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("ct_name");
		selectdto.setExWhereQuery("where ct_idx = '"+exdao.filter(v_cate)+"'");
		String club_board_name = exdao.selectQueryColumn(selectdto);
		request.setAttribute("club_board_name", club_board_name);
		
	}

	/**
	 * @title : 글등록
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String v_cate = exdao.filter( request.getParameter("v_cate") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		if("".equals(v_cate)){
			skinDTO.setBefore_msg( messageService.backMsg(model, "동아리 게시판 정보가 없습니다.") );
			return;
		}
		selectdto = new SelectDTO();
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("ct_name");
		selectdto.setExWhereQuery("where ct_idx = '"+exdao.filter(v_cate)+"'");
		String club_board_name = exdao.selectQueryColumn(selectdto);
		request.setAttribute("club_board_name", club_board_name);
		
	}

	@Override
	public void writeOk(Model model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @title : 글수정
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String v_cate = exdao.filter( request.getParameter("v_cate") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		if("".equals(v_cate)){
			skinDTO.setBefore_msg( messageService.backMsg(model, "동아리 게시판 정보가 없습니다.") );
			return;
		}
		selectdto = new SelectDTO();
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("ct_name");
		selectdto.setExWhereQuery("where ct_idx = '"+exdao.filter(v_cate)+"'");
		String club_board_name = exdao.selectQueryColumn(selectdto);
		request.setAttribute("club_board_name", club_board_name);
		
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
