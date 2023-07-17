package kr.co.nninc.ncms.member_group.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board_access.service.BoardAccessDTO;
import kr.co.nninc.ncms.board_config.service.BoardConfigDTO;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.manager_menu_access.service.ManagerMenuAccessDTO;
import kr.co.nninc.ncms.member_group.service.MemberGroupDTO;
import kr.co.nninc.ncms.member_group.service.MemberGroupService;



/**
 * 회원그룹을 관리하기 위한 관리하기 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 2019.01.08
 * @version 1.1
 */
@Service("memberGroupService")
public class MemberGroupServiceImpl extends EgovAbstractServiceImpl implements MemberGroupService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/**
	 * @title : 회원그룹 관리
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//select 전용 dto
		MemberGroupDTO selectdto = new MemberGroupDTO();
		
		//리스트조회하기
		selectdto.setExTableName("member_group");
		String s_fields = "g_num, g_code, g_menuname, g_chk, g_hdsize, g_wdate, g_webmail, g_money, g_myhome, g_cafe, g_blog, g_sms, g_manager";
		selectdto.setExColumn(s_fields);
		
		String where_query = "";
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		if("".equals(builder_dir)){
			//총관리자페이지 - 총관리자, 사이트관리자, 도서관회원(정회원), 비회원까지만 조회 (각사이트에서 생성한 그룹은 보지말자)
			where_query += " and ( g_num in (1,2,3,4) or g_site_dir is null )";
		}else{
			//사이트관리자페이지 - 사이트관리자, 도서관회원(정회원), 비회원, 해당사이트에서 생성한 그룹만 조회
			where_query += " and ( g_num in (2,3,4) or g_site_dir = '"+builder_dir+"' )";
		}
		String g_chk = exdao.filter( request.getParameter("g_chk") );
		if(!"".equals(g_chk)){
			where_query += " and g_chk = '"+g_chk+"'";
			
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("ORDER BY g_code");
		List<HashMap<String,String>>membergroupList = exdao.selectQueryTable(selectdto);
		request.setAttribute("membergroupList", membergroupList);

		
	}

	/**
	 * @title : 등록하기 처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MemberGroupDTO membergroup = (MemberGroupDTO) map.get("membergroupdto");

		//select전용 dto
		MemberGroupDTO selectdto = new MemberGroupDTO();
		
		//마지막 순서코드 가져오기
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("max(g_code)");
		String max_code = exdao.selectQueryColumn(selectdto);
		max_code = Func.cStr( Func.cInt(max_code) + 1 );
		
		membergroup.setG_manager("N");
		membergroup.setG_wdate(Func.date("Y-m-d H:i:s"));
		membergroup.setG_code(Func.cStr(max_code));
		
		//총관리자, 사이트관리자, 도서관회원(정회원), 비회원등 기본제공그룹을 제외한 회원그룹은 생성한 사이트의 명칭이 저장
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(BUILDER_DIR)){
			membergroup.setG_site_dir(BUILDER_DIR);
		}
		
		//insert 제외 필드
		membergroup.setG_num(null);
		
		membergroup.setExTableName("member_group");
		exdao.insert(membergroup);
		
		//작업기록
		Func.writeManagerLog ("회원그룹 생성 ["+membergroup.getG_menuname()+"]", request);
	}

	/**
	 * @title : 수정하기 처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MemberGroupDTO membergroup = (MemberGroupDTO) map.get("membergroupdto");

		membergroup.setG_wdate(Func.date("Y-m-d H:i:s"));
		
		String g_num = request.getParameter("g_num");
		membergroup.setExTableName("member_group");
		membergroup.setExWhereQuery("where g_num = '"+exdao.filter(g_num)+"'");
		
		
		//총관리자, 사이트관리자, 도서관회원(정회원), 비회원등 기본제공그룹을 제외한 회원그룹은 생성한 사이트의 명칭이 저장
		String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(BUILDER_DIR)){
			membergroup.setG_site_dir(BUILDER_DIR);
		}
		
		//update 제외 필드
		membergroup.setG_num(null);
		membergroup.setG_chk(null);
		
		exdao.update(membergroup);
		
		//작업기록
		Func.writeManagerLog ("회원그룹 수정 ["+membergroup.getG_menuname()+"]", request);
	}

	/**
	 * @title : 회원권한설정 폼
	 * @method : awrite
	 */
	@Override
	public void awrite(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//select 전용 dto
		MemberGroupDTO selectdto = new MemberGroupDTO(); 
		
		//복사권한이 없을땐.. 원래 권한이 복사권한으로..
		String g_num = Func.nvl(request.getParameter("g_num"));
		String cp_g_num = Func.nvl(request.getParameter("cp_g_num"));
		if("".equals(cp_g_num)) {
			cp_g_num = g_num;
		}
		request.setAttribute("cp_g_num", cp_g_num);
		
		// 회원그룹 정보가져오기
		String g_num_str = "비회원";
		String g_manager = "";
		if(!"".equals(cp_g_num)) {
			selectdto = new MemberGroupDTO();
			selectdto.setExTableName("member_group");
			selectdto.setExColumn("g_menuname, g_manager");
			selectdto.setExWhereQuery("where g_num = '"+g_num+"'");
			HashMap<String, String>rs = exdao.selectQueryRecord(selectdto);
			if(rs.size() > 0){
				g_num_str = rs.get("g_menuname");
				g_manager = rs.get("g_manager");
			}
		}
		
		request.setAttribute("g_num", g_num);
		request.setAttribute("g_manager", g_manager);
		request.setAttribute("g_num_str", g_num_str);
		
		// 회원그룹리스트조회하기(사용인것만)
		selectdto = new MemberGroupDTO();
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_num, g_menuname");
		selectdto.setExWhereQuery("where g_chk = 'Y'");
		selectdto.setExOrderByQuery("order by g_code asc");
		List<HashMap<String,String>> membergroupList = exdao.selectQueryTable(selectdto);
		request.setAttribute("membergroupList", membergroupList);
		
		// 게시판 전체리스트
		BoardConfigDTO boardConfig = new BoardConfigDTO();
		boardConfig.setExTableName("board_config a left outer join homepage_menu b on a.a_num = b.ct_anum");
		boardConfig.setExColumn("a.a_num, a.a_bbsname, b.ct_site_dir");
		
		//사이트관리자는 해당 게시판만 조회
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			boardConfig.setExWhereQuery("where a_site_dir = '"+builder_dir+"'");
		}
		
		List<HashMap<String,String>>boardList = exdao.selectQueryTable(boardConfig);
		
		for(int i=0; i<boardList.size(); i++) {
			HashMap<String, String>board = boardList.get(i);
			
			// 게시판 권한 가져오기
			selectdto = new MemberGroupDTO();
			selectdto.setExTableName("board_access");
			selectdto.setExColumn("bl_ad_cms, bl_list, bl_read, bl_write, bl_delete, bl_reply");
			selectdto.setExWhereQuery("where g_num = '"+cp_g_num+"' and a_num = '"+board.get("a_num")+"'");
			HashMap<String,String>boardaccess = exdao.selectQueryRecord(selectdto);
			
			// 게시판 권한 데이터 담기
			if(boardaccess != null) {
				board.put("bl_ad_cms", boardaccess.get("bl_ad_cms"));
				board.put("bl_list", boardaccess.get("bl_list"));
				board.put("bl_read", boardaccess.get("bl_read"));
				board.put("bl_write", boardaccess.get("bl_write"));
				board.put("bl_delete", boardaccess.get("bl_delete"));
				board.put("bl_reply", boardaccess.get("bl_reply"));
			}
		}
		request.setAttribute("boardList", boardList);
		
		//관리메뉴 부분(대메뉴)
		selectdto = new MemberGroupDTO();
		selectdto.setExTableName("manager_menu");
		
		String s_fields = "ct_idx, ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_wdate, ct_codeno, ct_url, ct_icon";
		selectdto.setExColumn(s_fields);
		String whereQuery = "where ct_depth = 1 and ct_chk = 'Y'";
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY ct_code ASC");
		List<HashMap<String,Object>>menuList = exdao.selectQueryTableObject(selectdto);
		
		for (int i = 0; i < menuList.size(); i++) {
			HashMap<String,Object> menudto = (HashMap<String,Object>)menuList.get(i);
			
			//관리메뉴 부분(소메뉴)
			selectdto = new MemberGroupDTO();
			selectdto.setExTableName("manager_menu");
			selectdto.setExColumn(s_fields);
			whereQuery = "where ct_ref = '"+menudto.get("ct_idx")+"' and ct_depth = 2 and ct_chk = 'Y'";
			selectdto.setExWhereQuery(whereQuery);
			selectdto.setExOrderByQuery("ORDER BY ct_code ASC");
			List<HashMap<String,Object>>menuList2 = exdao.selectQueryTableObject(selectdto);
			
			for (int j = 0; j < menuList2.size(); j++) {
				HashMap<String,Object> menudto2 = (HashMap<String,Object>)menuList2.get(j);
				
				//권한체크
				selectdto = new MemberGroupDTO();
				selectdto.setExTableName("manager_menu_access");
				selectdto.setExColumn("count(*)");
				selectdto.setExWhereQuery("where g_num = '"+cp_g_num+"' and ct_idx = '"+Func.nvl(menudto2.get("ct_idx").toString())+"'");
				String checked = exdao.selectQueryColumn(selectdto);
				if(!"0".equals(checked)){
					menudto2.put("checked", "1");
				}
				
				//관리메뉴 부분(소소메뉴)
				selectdto = new MemberGroupDTO();
				selectdto.setExTableName("manager_menu");
				selectdto.setExColumn(s_fields);
				whereQuery = "where ct_ref = '"+menudto2.get("ct_idx")+"' and ct_depth = 3 and ct_chk = 'Y'";
				selectdto.setExWhereQuery(whereQuery);
				selectdto.setExOrderByQuery("ORDER BY ct_code ASC");
				List<HashMap<String,Object>>menuList3 = exdao.selectQueryTableObject(selectdto);
				/*
				 * 처리안함
				for (int k = 0; k < menuList3.size(); k++) {
					HashMap<String,Object> menudto3 = (HashMap<String,Object>)menuList3.get(k);
					
					//권한체크
					selectdto = new MemberGroupDTO();
					selectdto.setExTableName("manager_menu_access");
					selectdto.setExColumn("count(*)");
					selectdto.setExWhereQuery("where g_num = '"+g_num+"' and ct_idx = '"+menudto3.get("ct_idx")+"'");
					String checked = exdao.selectQueryColumn(selectdto);
					
					menudto3.put("checked", checked);
				}
				*/
				menudto2.put("menuList", menuList3);
				
			}
			menudto.put("menuList", menuList2);
		}
		request.setAttribute("menuList", menuList);
		
	}

	/**
	 * @title : 회원권한설정 처리
	 * @method : awriteOk
	 */
	@Override
	public void awriteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MemberGroupDTO membergroup = (MemberGroupDTO) Func.requestAll(map.get("membergroupdto"));

		int menu_cnt = Func.cInt((String) request.getParameter("menu_cnt"));
		int bbs_cnt	 = Func.cInt((String) request.getParameter("bbs_cnt"));
		
		String g_num = exdao.filter(request.getParameter("g_num"));
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		String sql = "";
		
		//게시판 권한 처리는 각사이트에서만 하자
		if(!"".equals(builder_dir)){
			//기존 데이터 초기화
			sql = "DELETE FROM board_access where g_num = '"+g_num+"' and a_num in (";
			String a_nums = "";
			for(int i=1; i<=bbs_cnt; i++) {
				String a_num		= Func.nvl(request.getParameter("a_num"+i));
				if(!"".equals(a_num)){
					if(!"".equals(a_nums)){a_nums += ",";}
					a_nums += a_num;
				}
			}
			sql += a_nums+")";
			exdao.executeQuery(sql);
			
			//게시판 권한등록
			for(int i=1; i<=bbs_cnt; i++) {
				String a_num		= Func.nvl(request.getParameter("a_num"+i));
				String bl_ad_cms	= Func.nvl(request.getParameter("bl_ad_cms"+i));
				String bl_list			= Func.nvl(request.getParameter("bl_list"+i));
				String bl_read		= Func.nvl(request.getParameter("bl_read"+i));
				String bl_write		= Func.nvl(request.getParameter("bl_write"+i));
				String bl_delete		= Func.nvl(request.getParameter("bl_delete"+i));
				String bl_reply		= Func.nvl(request.getParameter("bl_reply"+i));
				
				BoardAccessDTO boardaccess = new BoardAccessDTO();
				boardaccess.setG_num(g_num);
				boardaccess.setA_num(a_num);
				boardaccess.setBl_ad_cms(bl_ad_cms);
				boardaccess.setBl_list(bl_list);
				boardaccess.setBl_read(bl_read);
				boardaccess.setBl_write(bl_write);
				boardaccess.setBl_delete(bl_delete);
				boardaccess.setBl_reply(bl_reply);
				
				boardaccess.setExTableName("board_access");
				exdao.insert(boardaccess);
			}
		}
		
		// 접속권한 수정하기
		sql = "UPDATE member_group SET g_manager = '"+exdao.filter(membergroup.getG_manager())+"' WHERE g_num = '"+g_num+"'";
		exdao.executeQuery(sql);
		
		//관리메뉴권한 삭제하기
		sql = "DELETE FROM manager_menu_access where g_num = '"+g_num+"'";
		exdao.executeQuery(sql);
		
		for(int i=1; i<=menu_cnt; i++){
			String ct_idx		= Func.nvl( request.getParameter("ct_idx"+i));
			String ct_codeno	= Func.nvl( request.getParameter("ct_codeno"+i));
			
			ManagerMenuAccessDTO menuaccess = new ManagerMenuAccessDTO();
			menuaccess.setCt_idx(ct_idx);
			menuaccess.setCt_codeno(ct_codeno);
			menuaccess.setG_num(membergroup.getG_num());
			//등록하기
			if(!"".equals(ct_idx)){
				menuaccess.setExTableName("manager_menu_access");
				exdao.insert(menuaccess);
			}
		}
		
		//사이트관리자일경우 처리X
		if("".equals(builder_dir)){
		
			//관리메뉴권한 삭제하기
			sql = "DELETE FROM manager_menu_access where g_num = '"+g_num+"'";
			exdao.executeQuery(sql);
			
			for(int i=1; i<=menu_cnt; i++){
				String ct_idx		= Func.nvl( request.getParameter("ct_idx"+i));
				String ct_codeno	= Func.nvl( request.getParameter("ct_codeno"+i));
				
				ManagerMenuAccessDTO menuaccess = new ManagerMenuAccessDTO();
				menuaccess.setCt_idx(ct_idx);
				menuaccess.setCt_codeno(ct_codeno);
				menuaccess.setG_num(membergroup.getG_num());
				//등록하기
				if(!"".equals(ct_idx)){
					menuaccess.setExTableName("manager_menu_access");
					exdao.insert(menuaccess);
				}
			}
			
		}
		//select전용  dto
		MemberGroupDTO selectdto = new MemberGroupDTO();
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_menuname");
		selectdto.setExWhereQuery("where g_num='"+exdao.filter(membergroup.getG_num())+"'");
		String g_menuname = exdao.selectQueryColumn(selectdto);
		
		//작업기록
		Func.writeManagerLog ("회원그룹 권한설정 ["+g_menuname+"]", request);
		
	}

	
	/**
	 * @title : 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String status 		= Func.nvl( request.getParameter("status") ); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String g_num			= exdao.filter( request.getParameter("g_num") ); //단일 삭제 사용
		
		//select 전용 dto
		MemberGroupDTO selectdto = new MemberGroupDTO();
		
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_menuname");
		if (status.equals("totdel")) { //다중삭제
			
			for(int z=0;z <= chk.length-1;z++){
				g_num = chk[z];
				
				selectdto.setExWhereQuery("where g_num = '"+g_num+"'");
				String g_menuname = exdao.selectQueryColumn(selectdto);
				
				//게시판 권한 삭제
				exdao.executeQuery("delete from board_access where g_num = '"+g_num+"'");
				//관리메뉴권한 삭제
				exdao.executeQuery("delete from manager_menu_access where g_num = '"+g_num+"'");
				//삭제하기
				exdao.executeQuery("delete from member_group where g_num = '"+g_num+"'");
				//비회원으로 변경
				exdao.executeQuery("UPDATE member SET m_level = '2' WHERE m_level= '"+g_num+"'");
				//작업기록
				Func.writeManagerLog ("회원그룹 삭제 ["+g_menuname+"]", request);
				
			}
		}else{
			selectdto.setExWhereQuery("where g_num = '"+g_num+"'");
			String g_menuname = exdao.selectQueryColumn(selectdto);
			
			//게시판 권한 삭제
			exdao.executeQuery("delete from board_access where g_num = '"+g_num+"'");
			//관리메뉴권한 삭제
			exdao.executeQuery("delete from manager_menu_access where g_num = '"+g_num+"'");
			//삭제하기
			exdao.executeQuery("delete from member_group where g_num = '"+g_num+"'");
			//비회원으로 변경
			exdao.executeQuery("UPDATE member SET m_level = '2' WHERE m_level= '"+g_num+"'");
			//작업기록
			Func.writeManagerLog ("회원그룹 삭제 ["+g_menuname+"]", request);
		}
		
	}

	/**
	 * @title : 사용/중지
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String tot_level_chk = request.getParameter("tot_level_chk");
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		
		//select 전용 dto
		MemberGroupDTO selectdto = new MemberGroupDTO();
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_menuname");

		for (int z = 0; z <= chk.length - 1; z++) {
			String g_num = chk[z];
			String g_chk = tot_level_chk;
			//
			String sql = "update member_group set g_chk = '"+g_chk+"' where g_num = '"+g_num+"'";
			exdao.executeQuery(sql);
			
			//쿼리조회
			selectdto.setExWhereQuery("where g_num = '"+g_num+"'");
			String g_menuname = exdao.selectQueryColumn(selectdto);
			
			//작업기록
			String chk_str = "사용";
			if("N".equals(tot_level_chk)) chk_str = "중지";
			Func.writeManagerLog ("회원그룹 상태변경 ["+g_menuname+"("+chk_str+")]", request);
		}
	}

	/**
	 * @title : 순서변경폼
	 * @method : listMove
	 */
	@Override
	public void listMove(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//select 전용 dto
		MemberGroupDTO selectdto = new MemberGroupDTO();
		
		//리스트 조회
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_num, g_menuname");
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		String where_query = "";
		if("".equals(builder_dir)){
			//총관리자페이지 - 총관리자, 사이트관리자, 도서관회원(정회원), 비회원까지만 조회 (각사이트에서 생성한 그룹은 보지말자)
			where_query += " and ( g_num in (1,2,3,4) or g_site_dir is null )";
		}else{
			//사이트관리자페이지 - 사이트관리자, 도서관회원(정회원), 비회원, 해당사이트에서 생성한 그룹만 조회
			where_query += " and ( g_num in (2,3,4) or g_site_dir = '"+builder_dir+"' )";
		}
		String g_chk = exdao.filter( request.getParameter("g_chk") );
		if(!"".equals(g_chk)){
			where_query += " and g_chk = '"+g_chk+"'";
			
		}
		selectdto.setExWhereQuery(where_query);
		
		selectdto.setExOrderByQuery("ORDER BY g_code");
		List<HashMap<String,String>>membergroupList = exdao.selectQueryTable(selectdto);
		request.setAttribute("membergroupList", membergroupList);
	}

	/**
	 * @title : 순서변경처리
	 * @method : listMoveOk
	 */
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String g_num = chk[z];
			//
			String sql = "update member_group set g_code = '"+Func.cStr(z + 1)+"' where g_num = '"+g_num+"'";
			exdao.executeQuery(sql);
		}
	}
}
