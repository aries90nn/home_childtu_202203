package kr.co.nninc.ncms.board.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board.service.SkinService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;



/**
 * 통합게시판 스킨 별도처리를 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.12.10
 * @version 1.0
 */
@Service("total_boardService")
public class Total_boardServiceImpl extends EgovAbstractServiceImpl implements SkinService {

	/**
	 * @title : total_board 스킨  List 처리
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
		
		
		//불러올 필드 추가
		String s_fields = ", (select a_site_dir from board_config where a_num = a.a_num) as a_site_dir";
		skinDTO.setS_fields(s_fields);
		
		
		//통합조회할 게시판 설정
		String sh_anums_key = config.get("a_level")+".sh_anums["+config.get("a_num")+"]";
		String sh_anums = Func.nvl( CommonConfig.getBoard( sh_anums_key ) ).trim();
		System.out.println(sh_anums_key + "=" + CommonConfig.getBoard( sh_anums_key ) );
		
		
		String v_cate = exdao.filter(request.getParameter("v_cate"));
		
		String nowdate = Func.date("Y-m-d H:i");
		String is_ad_cms				= (String) request.getAttribute("is_ad_cms");
		String ss_m_id = Func.getSession(request, "ss_m_id"); // 세션아이디
		String ss_m_jumin = Func.getSession(request, "ss_m_jumin"); // 세션 식별번호
		
		// whereQuery 생성
		String whereQuery = "";
		whereQuery = "WHERE b_look='Y'";
		
		/*
		 * 공지로 등록된 글까지 조회돼야한다.(광주)
		whereQuery += " AND ( b_noticechk = ''";
		whereQuery += " 	OR (b_noticechk = 'Y'";
		whereQuery += " 		AND ( b_notice_sdate > '" + nowdate + "' OR b_notice_edate < '" + nowdate + "')";
		whereQuery += "		)";
		whereQuery += ")";
		*/

		if (!"Y".equals(is_ad_cms)) { // 관리자가아닐때 실행할것들
			// 회원제
			if ("Y".equals(config.get("a_member"))) {
				if (!"".equals(ss_m_jumin)) {
					whereQuery += " AND ( ( b_jumin = '" + ss_m_jumin + "') or ( b_id = '" + ss_m_id + "' and b_id <> '' ) )";
				} else {
					whereQuery += " AND b_id = '" + ss_m_id + "' and b_id <> ''";
				}
			}
		}

		// 답변기능을 사용하지 않을때
		String a_reply = config.get("a_reply");
		String a_replyOpt = config.get("a_replyOpt");
		if ("N".equals(a_reply) || ("Y".equals(a_reply) && "2".equals(a_replyOpt))) {
			whereQuery += " AND b_type = 'N'";
		}

		// 분류기능 사용시
		//String v_cate = exdao.filter(request.getParameter("v_cate"));
		if ("Y".equals(config.get("a_cate")) && !"".equals(v_cate)) {
			whereQuery += " AND b_cate = '" + v_cate + "'";
		}

		// 검색
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		if (!"".equals(v_keyword)) {
			whereQuery += " AND " + v_search + " like '%" + v_keyword + "%'";
		}
			
		if(!"".equals(sh_anums)){	//정해진 sh_anums 이  있으면 해당 게시판들만 조회
			whereQuery += " and a_num in ("+config.get("a_num")+","+exdao.filter(sh_anums)+") ";
		}
		
		skinDTO.setWhere_query(whereQuery);

	}

	/**
	 * @title : total_board 스킨  List 처리
	 * @method : list
	 */
	@Override
	public void view(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		//불러올 필드 추가
		String s_fields = ", (select a_site_dir from board_config where a_num = a.a_num) as a_site_dir";
		s_fields += ", (select a_tablename from board_config where a_num = a.a_num) as a_tablename";
		skinDTO.setS_fields(s_fields);

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
