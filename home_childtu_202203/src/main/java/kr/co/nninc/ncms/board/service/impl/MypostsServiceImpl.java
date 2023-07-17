package kr.co.nninc.ncms.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 내가쓴글 처리 구현 클래스
 * @author 나눔
 * @since 2019.06.04
 * @version 1.2
 */
@Service("mypostsService")
public class MypostsServiceImpl extends EgovAbstractServiceImpl implements SkinService {

	/**
	 * @title : 내가쓴글 목록
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
		
		String ss_m_id = Func.getSession(request, "ss_m_id");
		String ss_m_jumin = Func.getSession(request, "ss_m_jumin"); // 세션jumin (ci 또는 di 정보를 저장)
		if("".equals(ss_m_jumin)){ss_m_jumin = Func.getSession(request, "ss_m_dupinfo");}
		String nowpage = Func.getNowPage(request); // 현재url을 가져오고 동시에 jsp전송
		
		//아이디 또는 인증정보 체크
		if("".equals(ss_m_jumin) && "".equals(ss_m_id)){
			String login_url = (String)request.getAttribute("board_login_url");
			skinDTO.setBefore_msg( messageService.redirectMsg(model, "", login_url+"?prepage=" + Func.urlEncode(nowpage)) );
			return;
		}
		
		
		String builder_dir = Func.nvl((String)request.getAttribute("BUILDER_DIR")).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		//사이트내에 등록된 게시판 조회
		String a_nums = Func.nvl( CommonConfig.getBoard( config.get("a_level")+".sh_anums["+config.get("a_num")+"]" ) ).trim();
		if("".equals(a_nums)){
			selectdto.setExTableName("board_config");
			selectdto.setExColumn("a_num");
			String whereQuery = "";
			if(!"".equals(builder_dir)){
				whereQuery += " and a_site_dir = '"+exdao.filter(builder_dir)+"'";
			}
			selectdto.setExWhereQuery(whereQuery);
			List<HashMap<String, String>>anumList = exdao.selectQueryTable(selectdto);
			
			for(int i=0;i<=anumList.size()-1;i++){
				if(i > 0)a_nums += ", ";
				a_nums += anumList.get(i).get("a_num");
			}
		}
		
		//게시물 검색
		int v_page = Func.cInt(request.getParameter("v_page"));
		if(v_page == 0)v_page = 1;
		int pagesize = Func.cInt( config.get("a_displaysu") );
		if(pagesize == 0)pagesize = 10;
		
		List<HashMap<String,String>>boardList = new ArrayList<HashMap<String,String>>();
		selectdto = new SelectDTO();
		selectdto.setExTableName("board_total");
		selectdto.setExColumn("a_num, a_bbsname, a_pageurl, b_num, b_name, b_subject, b_content, b_file1, b_regdate, b_count, b_cate, b_catename");
		String whereQuery = "where a_num in ("+a_nums+")";
		if(!"".equals(ss_m_id)){
			whereQuery += " and b_id = '"+ss_m_id+"'";
		}else if(!"".equals(ss_m_jumin)){
			whereQuery += " and b_jumin = '"+ss_m_jumin+"'";
		}
		// 검색
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		if (!"".equals(v_keyword)) {
				whereQuery += " AND " + v_search + " like '%" + v_keyword + "%'";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by b_regdate desc");
		selectdto.setExRecordCount(pagesize);
		selectdto.setExPage(v_page);
		
		if(!"".equals(a_nums)){
			boardList = exdao.selectQueryPage(selectdto);
		}
		skinDTO.setBoardList(boardList);
		

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
