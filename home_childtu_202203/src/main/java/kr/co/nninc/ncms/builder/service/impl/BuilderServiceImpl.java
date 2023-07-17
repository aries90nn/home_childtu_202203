package kr.co.nninc.ncms.builder.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board_config.service.BoardConfigDTO;
import kr.co.nninc.ncms.builder.service.BuilderService;
import kr.co.nninc.ncms.builder.service.BuilderSiteDTO;
import kr.co.nninc.ncms.cms.service.CmsDTO;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;


/**
 * 빌더사이트를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.04.23
 * @version 1.0
 */
@Service("builderService")
public class BuilderServiceImpl extends EgovAbstractServiceImpl implements BuilderService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/** FileUtil */
	@Resource(name="fileutil")
	private FileUtil fileutil;
	
	
	/**
	 * @title : 사이트등록
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String cp_bs_num = exdao.filter( request.getParameter("cp_bs_num") );
		
		SelectDTO selectdto = new SelectDTO();
		
		//사이트복사
		if(!"".equals(cp_bs_num)){
			
			selectdto.setExTableName("builder_site");
			selectdto.setExColumn("bs_sitename, bs_main, bs_skin");
			selectdto.setExWhereQuery("where bs_num = '"+cp_bs_num+"'");
			
			HashMap<String,String>builder = exdao.selectQueryRecord(selectdto);
			builder.put("bs_sitename", "["+Func.cStr(builder.get("bs_sitename"))+"]의 복사본");
			request.setAttribute("builder", builder);
			
		}
		
		//등록된 사이트 목록
		this.list(model);
		
		
		//메인타입 조회
		ArrayList<HashMap<String,String>> main_list = new ArrayList<HashMap<String,String>>();
		String filePath = fileutil.realPath(request, "/nanum/site/builder/main");
		String[] dir = Func.openDir( filePath );
		for(int i=0;i<=dir.length-1;i++){
			String main_name = Func.fileGetContents( filePath+"/"+dir[i]+"/name.txt" );
			if(main_name == null){main_name = dir[i];}
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("type", dir[i]);
			hm.put("name", main_name);
			main_list.add(hm);
		}
		request.setAttribute("main_list", main_list);
		
		//스킨타입 조회
		ArrayList<HashMap<String,String>> skin_list = new ArrayList<HashMap<String,String>>();
		String filePath2 = fileutil.realPath(request, "/nanum/site/builder/skin");
		String[] dir2 = Func.openDir( filePath2 );
		for(int i=0;i<=dir2.length-1;i++){
			if("common".equals(dir2[i])){continue;}
			String skin_name = Func.fileGetContents( filePath2+"/"+dir2[i]+"/name.txt" );
			if(skin_name == null){skin_name = dir2[i];}
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("type", dir2[i]);
			hm.put("name", skin_name);
			skin_list.add(hm);
		}
		request.setAttribute("skin_list", skin_list);
		
		
	}

	/**
	 * @title : 사이트목록
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		request.setAttribute("v_keyword", v_keyword);
		request.setAttribute("v_search", v_search);
		
		SelectDTO selectdto = new SelectDTO();
		
		selectdto.setExTableName("builder_site");
		selectdto.setExKeyColumn("bs_num");	//고유컬럼 설정 필수
		selectdto.setExColumn("bs_num, bs_sitename, bs_directory, bs_main, bs_skin, bs_writer, bs_regdate, bs_chk");
		
		String where_query = "";
		if(!"".equals(v_search) && !"".equals(v_keyword)){
			where_query += " and "+v_search+" like '%"+v_keyword+"%'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("order by bs_code");
		
		List<HashMap<String,String>>builderList = exdao.selectQueryTable(selectdto);
		request.setAttribute("builderList", builderList);

		int recordcount = builderList.size();
		request.setAttribute("recordcount", recordcount);
		
		
		Func.getNowPage(request);
	}

	/**
	 * @title : 사이트수정
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		System.out.println("BuilderServiceImpl.modify start");
		String bs_num = exdao.filter( request.getParameter("bs_num") );
		
		SelectDTO selectdto = new SelectDTO();
		
		selectdto.setExTableName("builder_site");
		selectdto.setExKeyColumn("bs_num");
		selectdto.setExColumn(new BuilderSiteDTO());
		selectdto.setExWhereQuery("where bs_num = '"+bs_num+"'");
		
		HashMap<String,String>builder = exdao.selectQueryRecord(selectdto);
		request.setAttribute("builder", builder);
		
		
		
		//메인타입 조회
		ArrayList<HashMap<String,String>> main_list = new ArrayList<HashMap<String,String>>();
		String filePath = fileutil.realPath(request, "/nanum/site/builder/main");
		String[] dir = Func.openDir( filePath );
		for(int i=0;i<=dir.length-1;i++){
			String main_name = Func.fileGetContents( filePath+"/"+dir[i]+"/name.txt" );
			if(main_name == null){main_name = dir[i];}
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("type", dir[i]);
			hm.put("name", main_name);
			main_list.add(hm);
		}
		request.setAttribute("main_list", main_list);
		
		//스킨타입 조회
		ArrayList<HashMap<String,String>> skin_list = new ArrayList<HashMap<String,String>>();
		String filePath2 = fileutil.realPath(request, "/nanum/site/builder/skin");
		String[] dir2 = Func.openDir( filePath2 );
		for(int i=0;i<=dir2.length-1;i++){
			if("common".equals(dir2[i])){continue;}
			String skin_name = Func.fileGetContents( filePath2+"/"+dir2[i]+"/name.txt" );
			if(skin_name == null){skin_name = dir2[i];}
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("type", dir2[i]);
			hm.put("name", skin_name);
			skin_list.add(hm);
		}
		request.setAttribute("skin_list", skin_list);
		
	}

	/**
	 * @title : 사이트 등록 처리
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		BuilderSiteDTO builderdto = (BuilderSiteDTO) map.get("builderdto");
		
		
		//웹디렉토리명 중복체크
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("builder_site");
		selectdto.setExColumn("count(*)");
		selectdto.setExWhereQuery("where bs_directory = '"+exdao.filter(builderdto.getBs_directory())+"'");
		int cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
		if(cnt > 0){
			return messageService.backMsg(model, builderdto.getBs_directory()+"은(는) 이미 사용중인 디렉토리명입니다.");
		}
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ===============
		String uploaddir =  fileutil.realPath(request, "/data/builder/");
		Func.folderCreate(uploaddir);
		//==============================================
		
		// 파일 유효성 검사 및 저장
		builderdto.setBs_logo("");
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "로고이미지", "bs_logo_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			builderdto.setBs_logo(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		
		builderdto.setBs_writer( Func.getSession(request, "ss_m_id") );
		builderdto.setBs_regdate( Func.date("Y-m-d H:i:s") );
		
		
		//카피라이트 css파일삭제
		String regex = "<link[^>]*href=[\"']?([^>\"']+)[\"']?[^>]*>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(builderdto.getBs_copyright());
		String bs_copyright2 = "";
		while (matcher.find()) {
			bs_copyright2 += matcher.replaceAll("");
		}
		if (!"".equals(bs_copyright2)){
			builderdto.setBs_copyright(bs_copyright2);
		}
		
		//순서코드세팅
		int bs_code = Func.cInt(exdao.selectQueryColumn("max(bs_code)", "builder_site"));
		builderdto.setBs_code( Func.cStr(bs_code+1) );
		
		//insert 제외
		builderdto.setBs_num(null);
		
		//insert
		builderdto.setExTableName("builder_site");
		exdao.insert(builderdto);
		
		//사이트복사일경우 메뉴복사
		String cp_bs_num = exdao.filter(request.getParameter("cp_bs_num"));
		if(!"".equals(cp_bs_num)){
			//현재일자
			String nowdate = Func.date("Y-m-d H:i:s");
			
			selectdto = new SelectDTO();
			selectdto.setExTableName("builder_site");
			selectdto.setExColumn("bs_directory");
			selectdto.setExWhereQuery("where bs_num = '"+cp_bs_num+"'");
			String ct_site_dir_org = exdao.selectQueryColumn(selectdto);
			String ct_site_dir_trg = exdao.filter(builderdto.getBs_directory());
			
			String fields = exdao.getFieldNames(new CmsDTO(), "ct_idx, ct_site_dir, ct_wdate");
			String i_fields = fields+", ct_site_dir, ct_wdate";
			String s_fields = fields+", '"+ct_site_dir_trg+"', '"+nowdate+"'";
			String sql = "insert into homepage_menu("+i_fields+")";
			sql += "select "+s_fields+" from homepage_menu where ct_site_dir='"+ct_site_dir_org+"'";
			
			//원본사이트 메뉴그대로 생성
			exdao.executeQuery(sql);
			
			//게시판 테이블명을 구하기 위해 최대 board 카운트생성
			selectdto = new SelectDTO();
			selectdto.setExTableName("board_config");
			selectdto.setExColumn("a_tablename");
			selectdto.setExOrderByQuery("order by a_idx desc");
			String a_tablename = Func.nvl( exdao.selectQueryColumn(selectdto) );
			int board_count = 1;
			if(!"".equals(Func.nvl(a_tablename))) {
				String[] a_tablename_arr = a_tablename.split("_");
				board_count = Func.cInt( a_tablename_arr[1] ) + 1;
			}
			
			
			//생성된 메뉴 수정
			selectdto = new SelectDTO();
			selectdto.setExTableName("homepage_menu");
			selectdto.setExKeyColumn("ct_idx");	//고유컬럼 설정 필수
			selectdto.setExColumn("ct_idx, ct_depth, ct_codeno, ct_url, ct_pagetype, ct_anum");
			selectdto.setExWhereQuery("where ct_site_dir = '"+ct_site_dir_trg+"'");
			List<HashMap<String,String>>menu_list = exdao.selectQueryTable(selectdto);
			for(int i=0;i<=menu_list.size()-1;i++){
				HashMap<String,String>menu = (HashMap<String,String>)menu_list.get(i);
				String ct_idx = menu.get("ct_idx");
				
				//ct_codeno, ct_ref 수정
				String ct_codeno = menu.get("ct_codeno");
				String[] ct_codeno_arr = ct_codeno.split(";");
				int ct_depth_int = Func.cInt( menu.get("ct_depth") );
				String org_codeno = ct_codeno_arr[ct_depth_int];
				String org_ref = org_codeno.replaceAll("C", "");
				exdao.executeQuery("update homepage_menu set ct_codeno = replace(ct_codeno, '"+org_codeno+";', 'C"+ct_idx+";') where ct_site_dir = '"+ct_site_dir_trg+"'");
				exdao.executeQuery("update homepage_menu set ct_ref = '"+ct_idx+"' where ct_ref = '"+org_ref+"' and ct_site_dir = '"+ct_site_dir_trg+"'");
				
				
				//게시판 생성
				if("B".equals(menu.get("ct_pagetype"))){
					String a_num = Func.get_idx_add();
					selectdto = new SelectDTO();
					selectdto.setExTableName("board_config");
					selectdto.setExColumn("count(*) as cnt");
					boolean flag = true;
					while(flag){
						selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
						String a_num_cnt = exdao.selectQueryColumn(selectdto);
						if("0".equals(a_num_cnt)){
							flag = false;
						}
						a_num = Func.get_idx_add();
					}
					String new_tablename = "board_"+board_count;
					String b_fields = exdao.getFieldNames(new BoardConfigDTO(), "a_idx, a_num, a_tablename, a_site_dir, a_date, a_tablecnt, a_pageurl");
					sql = "insert into board_config(a_num, a_tablename, a_site_dir, a_date, a_tablecnt, a_pageurl, "+b_fields+")";
					sql += "select '"+a_num+"', '"+new_tablename+"', '"+ct_site_dir_trg+"', '"+nowdate+"', '"+board_count+"', '', "+b_fields+" from board_config where a_num ='"+menu.get("ct_anum")+"'";
					exdao.executeQuery(sql);
					board_count++;
					
					//메뉴 ct_anum 수정
					exdao.executeQuery("update homepage_menu set ct_anum = '"+a_num+"' where ct_idx = '"+ct_idx+"'");
				}
				
				//링크수정
				if("L".equals(menu.get("ct_pagetype"))){
					String ct_url = Func.nvl( menu.get("ct_url") );
					if(!"".equals(ct_url)){
						String ct_url_s = ct_url.toLowerCase();
						if(!( ct_url_s.contains("https://") || ct_url_s.contains("http://") )){
							String first_dir = ct_url.substring(0, ct_site_dir_org.length()+1);
							if(first_dir.equals("/"+ct_site_dir_org)){
								String change_ct_url = "/"+builderdto.getBs_directory()+ct_url.substring(ct_site_dir_org.length()+1);
								exdao.executeQuery("update homepage_menu set ct_url = '"+change_ct_url+"' where ct_idx = '"+ct_idx+"'");
							}
						}
					}
				}
			}
			
		}
		
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "/ncms/builder/write.do";}
		return "redirect:"+prepage;
	}

	/**
	 * @title : 사이트 수정 처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		BuilderSiteDTO builderdto = (BuilderSiteDTO) map.get("builderdto");
		
		String bs_num = exdao.filter( request.getParameter("bs_num") );
		
		//웹디렉토리명 중복체크
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("builder_site");
		selectdto.setExColumn("count(*)");
		selectdto.setExWhereQuery("where bs_num <> '"+bs_num+"' and bs_directory = '"+exdao.filter(builderdto.getBs_directory())+"'");
		int cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
		if(cnt > 0){
			return messageService.backMsg(model, builderdto.getBs_directory()+"은(는) 이미 사용중인 디렉토리명입니다.");
		}
		
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ===============
		String uploaddir =  fileutil.realPath(request, "/data/builder/");
		Func.folderCreate(uploaddir);
		//==============================================
		
		// 파일 유효성 검사 및 저장
		builderdto.setBs_logo("");
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "로고이미지", "bs_logo_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			builderdto.setBs_logo(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		String bs_logo2 = Func.nvl(request.getParameter("bs_logo2")).trim();
		String bs_logo_del = Func.nvl(request.getParameter("bs_logo_del")).trim();
		if(!"".equals(builderdto.getBs_logo())){  //첨부파일이 있으면
			
			Func.fileDelete(uploaddir+"/"+bs_logo2);
		}else{
			builderdto.setBs_logo(bs_logo2);

			if(!"".equals(bs_logo_del)){ //첨부파일만 삭제
				Func.fileDelete(uploaddir+"/"+bs_logo2);
				builderdto.setBs_logo("");
			}
		}
		
		//수정하기
		String bs_regdate = Func.date("Y-m-d H:i:s");
		builderdto.setBs_regdate(bs_regdate);
		builderdto.setBs_writer( Func.getSession(request, "ss_m_id") );
		
		
		//카피라이트 css파일삭제
		String regex = "<link[^>]*href=[\"']?([^>\"']+)[\"']?[^>]*>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(builderdto.getBs_copyright());
		String bs_copyright2 = "";
		while (matcher.find()) {
			bs_copyright2 += matcher.replaceAll("");
		}
		if (!"".equals(bs_copyright2)){
			builderdto.setBs_copyright(bs_copyright2);
		}
		
		
		//udpate 제외
		builderdto.setBs_num(null);
		builderdto.setBs_manager_ip(null);
		builderdto.setBs_manager_mobile(null);
		
		
		//update
		builderdto.setExTableName("builder_site");
		builderdto.setExWhereQuery("where bs_num = '"+bs_num+"'");
		exdao.update(builderdto);

		
		
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "/ncms/builder/write.do";}
		return "redirect:"+prepage;
	}

	/**
	 * @title : 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 		= Func.nvl( request.getParameter("status") ).trim(); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String bs_num		= request.getParameter("bs_num");  //단일 삭제 사용
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("builder_site");
		selectdto.setExColumn("bs_sitename, bs_directory");
		
		String strDir =  fileutil.realPath(request, "/data/cms/")+"/";
		String strDir2 =  fileutil.realPath(request, "/nanum/site/builder/dir/")+"/";
		
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				selectdto.setExWhereQuery("where bs_num = '"+chk[z]+"'");
				HashMap<String,String>builder = exdao.selectQueryRecord(selectdto);
				String bs_sitename = builder.get("bs_sitename");
				String bs_directory = builder.get("bs_directory");
				
				//게시판일괄삭제
				SelectDTO selectdto2 = new SelectDTO();
				selectdto2.setExTableName("board_config");
				selectdto2.setExColumn("a_num");
				selectdto2.setExWhereQuery("where a_site_dir = '"+bs_directory+"'");
				List<HashMap<String,String>>board_list = exdao.selectQueryTable(selectdto2);
				for(int j=0;j<=board_list.size()-1;j++){
					this.deleteBoard(request, Func.nvl(board_list.get(j).get("a_num")).trim());
				}
				
				//메뉴삭제
				exdao.executeQuery("delete from homepage_menu where ct_site_dir = '"+bs_directory+"'");

				//사이트 삭제
				exdao.executeQuery("delete from builder_site where bs_num = '"+chk[z]+"'");
				
				//메뉴파일 삭제
				Func.fileDelete(strDir+"menu_"+bs_directory+".json");
				
				//css, img 삭제
				Func.folderDelete(strDir2+bs_directory);
				
				//작업기록
				Func.writeManagerLog ("사이트 삭제 ["+bs_sitename+"]", request);
			}
		} else { //단일삭제
			selectdto.setExWhereQuery("where bs_num = '"+bs_num+"'");
			HashMap<String,String>builder = exdao.selectQueryRecord(selectdto);
			String bs_sitename = builder.get("bs_sitename");
			String bs_directory = builder.get("bs_directory");
			
			//게시판일괄삭제
			SelectDTO selectdto2 = new SelectDTO();
			selectdto2.setExTableName("board_config");
			selectdto2.setExColumn("a_num");
			selectdto2.setExWhereQuery("where a_site_dir = '"+bs_directory+"'");
			List<HashMap<String,String>>board_list = exdao.selectQueryTable(selectdto2);
			for(int j=0;j<=board_list.size()-1;j++){
				this.deleteBoard(request, Func.nvl(board_list.get(j).get("a_num")).trim());
			}
			
			//메뉴삭제
			exdao.executeQuery("delete from homepage_menu where ct_site_dir = '"+bs_directory+"'");
			
			//사이트 삭제
			exdao.executeQuery("delete from builder_site where bs_num = '"+bs_num+"'");
			
			//메뉴파일 삭제
			Func.fileDelete(strDir+"menu_"+bs_directory+".json");
			
			//css, img 삭제
			Func.folderDelete(strDir2+bs_directory);
			
			//작업기록
			Func.writeManagerLog ("사이트 삭제 ["+bs_sitename+"]", request);
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
		
		SelectDTO selectdto = new SelectDTO();
		
		String tot_level_chk = exdao.filter( request.getParameter("tot_w_chk") );
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String bs_num = chk[z];
			String bs_chk = tot_level_chk;
			
			exdao.executeQuery("UPDATE builder_site SET bs_chk = '"+bs_chk+"' WHERE bs_num = '"+bs_num+"'");
			
			//사이트명 조회
			selectdto.setExTableName("builder_site");
			selectdto.setExColumn("bs_sitename");
			selectdto.setExWhereQuery("where bs_num = '"+bs_num+"'");
			String bs_sitename = exdao.selectQueryColumn(selectdto);
			
			//작업기록
			String bs_chk_str = "사용";
			if("N".equals(bs_chk)) bs_chk_str = "중지";
			Func.writeManagerLog ("사이트 상태변경 ["+bs_sitename+"("+bs_chk_str+")]", request);
			
		}

	}
	
	/**
	 * @throws Exception 
	 * @title : 게시판 삭제 일괄처리
	 * @method : deleteBoard
	 */
	private void deleteBoard(HttpServletRequest request, String a_num) throws Exception{
		SelectDTO selectdto = new SelectDTO();
		
		String uploaddir		= fileutil.realPath(request, "/data/board/");
		
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_tablename, a_bbsname");
		selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
		HashMap<String,String>board = exdao.selectQueryRecord(selectdto);
		String a_tablename = board.get("a_tablename");
		String a_bbsname = board.get("a_bbsname");
		
		//게시물 삭제 한다.
		exdao.executeQuery("delete from board where a_num = '"+a_num+"'");
		
		//관리자 테이블에서 해당게시판 정보를 삭제한다.
		exdao.executeQuery("delete from board_config where a_num = '"+a_num+"'");
		
		//게시판 권한삭제
		exdao.executeQuery("DELETE FROM board_code WHERE a_num = '"+a_num+"'");
		
		//꼬릿글삭제
		exdao.executeQuery("DELETE FROM board_command WHERE a_num = '"+a_num+"'");
		
		//폴더삭제
		if(!"".equals(Func.nvl(a_tablename))) {
			Func.folderDelete(uploaddir+"/"+a_tablename);
		}
		
		//작업기록
		Func.writeManagerLog ("게시판 삭제 ["+a_bbsname+"]", request);
	}

	/**
	 * @title : 사이트관리 순서리스트
	 * @method : listMove
	 */
	@Override
	public void listMove(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		
		//select 전용 dto
		SelectDTO selectdto = new SelectDTO();
		
		//메뉴리스트 가져오기
		selectdto.setExTableName("builder_site");
		selectdto.setExColumn("bs_num, bs_sitename, bs_directory");
		selectdto.setExOrderByQuery("order by bs_code");
		List<HashMap<String,String>>siteList = exdao.selectQueryTable(selectdto);
		request.setAttribute("siteList", siteList);
		
	}

	/**
	 * @title : 사이트관리 순서일괄수정
	 * @method : listMoveOk
	 */
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String bs_num = chk[z];
			// 순서업데이트
			exdao.executeQuery("UPDATE builder_site SET bs_code = '"+Func.cStr(z + 1)+"' WHERE bs_num = '"+bs_num+"'");
		}
		
		
	}

}
