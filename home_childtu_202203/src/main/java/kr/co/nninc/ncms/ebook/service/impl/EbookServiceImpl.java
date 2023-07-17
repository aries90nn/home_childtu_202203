package kr.co.nninc.ncms.ebook.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.ebook.service.EbookDTO;
import kr.co.nninc.ncms.ebook.service.EbookService;


/**
 * 이북을 관리하기 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.09.26
 * @version 1.0
 */
@Service("ebookService")
public class EbookServiceImpl extends EgovAbstractServiceImpl implements EbookService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** FileUtil */
	@Resource(name="fileutil")
	private FileUtil fileutil;
	
	
	/**
	 * @title : 이북수정 폼
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//상세보기
		EbookDTO selectdto = new EbookDTO();	//select 전용 dto 초기화
		selectdto.setExTableName("ebook");
		selectdto.setExColumn(new EbookDTO());
		String eb_idx = exdao.filter( request.getParameter("eb_idx") );
		selectdto.setExWhereQuery("where eb_idx = '"+eb_idx+"'");
		HashMap<String, String>ebook_map = exdao.selectQueryRecord(selectdto);
		
		//초기화시키기
		if(Func.cInt(ebook_map.get("eb_idx")) == 0) {
			ebook_map.put("eb_chk", "Y");
			ebook_map.put("eb_skin", "1");
			ebook_map.put("eb_viewtype", "1");
		}
		if("".equals(Func.nvl(ebook_map.get("eb_width"))) || "0".equals(ebook_map.get("eb_width"))) ebook_map.put("eb_width", "990");
		if("".equals(Func.nvl(ebook_map.get("eb_height"))) || "0".equals(ebook_map.get("eb_height"))) ebook_map.put("eb_height", "1403");
		if("".equals(Func.nvl(ebook_map.get("eb_width2"))) || "0".equals(ebook_map.get("eb_width2"))) ebook_map.put("eb_width2", "494");
		if("".equals(Func.nvl(ebook_map.get("eb_height2"))) || "0".equals(ebook_map.get("eb_height2"))) ebook_map.put("eb_height2", "700");
		
		request.setAttribute("ebook", ebook_map);
		
		//작업기록
		Func.writeManagerLog ("이북 상세보기 ["+ebook_map.get("eb_subject")+"]", request);

	}

	
	/**
	 * @title : 이북수정처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		EbookDTO ebook = (EbookDTO) map.get("ebookdto");
		String prepage		= exdao.filter( request.getParameter("prepage") );
		if("".equals(prepage)) prepage = "list.do";
		
		//------------------- 저장공간인 [DATA]폴더가 없다면 생성 한다. -------------------
		String uploaddir =  fileutil.realPath(request, "/data/");
		Func.folderCreate(uploaddir+"/ebook");
		Func.folderCreate(uploaddir+"/ebook/logo/");
		Func.folderCreate(uploaddir+"/ebook/"+ebook.getEb_pk()+"/");
		Func.folderCreate(uploaddir+"/ebook/"+ebook.getEb_pk()+"/small/");
		Func.folderCreate(uploaddir+"/ebook/"+ebook.getEb_pk()+"/middle/");
		//------------------------------------------------------------------------------
		uploaddir =  fileutil.realPath(request, "/data/ebook/logo/");
		
		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "이북로고이미지", "eb_logoimg_file", 1000 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(file == null){
			ebook.setEb_logoimg("");
		}else{
			ebook.setEb_logoimg(Func.nvl(file.getFile_name()));
		}

		String eb_logoimg_org = Func.nvl(request.getParameter("eb_logoimg_org"));
		String eb_logoimg_del = Func.nvl(request.getParameter("eb_logoimg_del"));
		if(!"".equals(ebook.getEb_logoimg())){  //첨부파일이 있으면
			fileutil.FolderDelete(uploaddir+"/"+eb_logoimg_org);
		}else{
			ebook.setEb_logoimg(eb_logoimg_org);

			if(!"".equals(Func.nvl(eb_logoimg_del))){ //첨부파일만 삭제
				fileutil.FolderDelete(uploaddir+"/"+eb_logoimg_org);
				ebook.setEb_logoimg("");
			}
		}
		
		//수정처리에서 제외할 필드
		ebook.setEb_code(null);
		ebook.setEb_wdate(null);
		
		//수정
		String eb_mdate = Func.date("Y-m-d H:i:s");
		ebook.setEb_mdate(eb_mdate);
		
		String eb_idx = exdao.filter( request.getParameter("eb_idx") );
		ebook.setExTableName("ebook");
		ebook.setExWhereQuery("where eb_idx = '"+eb_idx+"'");
		exdao.update(ebook);
		
		//작업기록
		Func.writeManagerLog ("이북 수정 ["+ebook.getEb_subject()+"]", request);

	}

	/**
	 * @title : 이북등록 폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String eb_idx = Func.nvl( request.getParameter("eb_idx") ).trim();

		EbookDTO ebook = new EbookDTO();
		//초기화시키기
		if("".equals(eb_idx)) {
			ebook.setEb_chk("Y");
			ebook.setEb_skin("1");
			ebook.setEb_viewtype("1");
		}
		if("".equals(Func.nvl(ebook.getEb_width())) || "0".equals(ebook.getEb_width())) ebook.setEb_width("990");
		if("".equals(Func.nvl(ebook.getEb_height())) || "0".equals(ebook.getEb_height())) ebook.setEb_height("1403");
		if("".equals(Func.nvl(ebook.getEb_width2())) || "0".equals(ebook.getEb_width2())) ebook.setEb_width2("494");
		if("".equals(Func.nvl(ebook.getEb_height2())) || "0".equals(ebook.getEb_height2())) ebook.setEb_height2("700");
		
		request.setAttribute("ebook", ebook);

	}

	/**
	 * @title : 이북등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		EbookDTO ebook = (EbookDTO) map.get("ebookdto");
		
		String eb_pk = Func.get_idx_add();
		ebook.setEb_pk(eb_pk);
		
		//------------------- 저장공간인 [DATA]폴더가 없다면 생성 한다. -------------------
		String uploaddir =  fileutil.realPath(request, "/data/");
		Func.folderCreate(uploaddir+"/ebook");
		Func.folderCreate(uploaddir+"/ebook/logo/");
		Func.folderCreate(uploaddir+"/ebook/"+eb_pk+"/");
		Func.folderCreate(uploaddir+"/ebook/"+eb_pk+"/small/");
		Func.folderCreate(uploaddir+"/ebook/"+eb_pk+"/middle/");
		//------------------------------------------------------------------------------
		uploaddir =  fileutil.realPath(request, "/data/ebook/logo/");
		
		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "이북로고이미지", "eb_logoimg_file", 1000 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(file != null) {
			ebook.setEb_logoimg(Func.nvl(file.getFile_name()));
		}
		// 마지막 순서코드가져오기
		EbookDTO selectdto = new EbookDTO();
		selectdto.setExTableName("ebook");
		selectdto.setExColumn("max(eb_code)");
		int eb_code = Func.cInt( exdao.selectQueryColumn(selectdto) )+1;
		ebook.setEb_code(Func.cStr(eb_code));
		
		//이북등록
		String eb_wdate = Func.date("Y-m-d H:i:s");
		String eb_mdate = Func.date("Y-m-d H:i:s");
		ebook.setEb_wdate(eb_wdate);
		ebook.setEb_mdate(eb_mdate);
		
		//insert제외
		ebook.setEb_idx(null);
		
		//insert
		ebook.setExTableName("ebook");
		exdao.insert(ebook);
		
		//작업기록
		Func.writeManagerLog ("이북 등록 ["+ebook.getEb_subject()+"]", request);

	}

	/**
	 * @title : 이북목록
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//검색어
		String v_search = exdao.filter( request.getParameter("v_search") );
		String v_keyword = exdao.filter( request.getParameter("v_keyword") );
		request.setAttribute("v_search", v_search);
		request.setAttribute("v_keyword", v_keyword);

		//현재 페이지
		int v_page = Func.cInt(request.getParameter("v_page"));
		if(v_page == 0){v_page = 1;}
		//목록갯수
		int pagesize = 10;
		
		//select 전용dto
		EbookDTO selectdto = new EbookDTO();
		//목록조회
		selectdto.setExTableName("ebook");
		selectdto.setExColumn(new EbookDTO());
		String whereQuery = "";
		if(!"".equals(v_keyword)){
			whereQuery = "where "+v_search+" like '%"+v_keyword+"%'";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY eb_code ASC");
		selectdto.setExPage(v_page);
		selectdto.setExRecordCount(pagesize);
		List<HashMap<String,String>>ebookList = exdao.selectQueryPage(selectdto);
		int recordcount = Func.cInt( ebookList.get(0).get("totalcount") );	//총검색개수 추출
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수 
		ebookList.remove(0);	//총검색개수행(첫번째행)을 삭제
		request.setAttribute("recordcount", recordcount);
		request.setAttribute("ebookList", ebookList);
		
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = 10;				//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring = paging.setQueryString(request, "v_search, v_keyword");
		/*
		 * Paging.setQueryString 함수는 아래와 같은 값을 반환단다.
		querystring = "v_search="+Func.urlEncode(v_search);
		querystring += "&v_keyword="+Func.urlEncode(v_keyword);
		*/
		String pagingtag = paging.execute(querystring);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		
		//현재URL 전송
		Func.getNowPage(request);

	}

	/**
	 * @title : 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 			= Func.nvl(request.getParameter("status")); //값:totdel (다중삭제시 사용)
		String[] chk 			= request.getParameterValues("chk"); //선택 체크 값
		String eb_idx		= exdao.filter( request.getParameter("eb_idx") );  //단일 삭제 사용
		
		if (status.equals("totdel")) { //다중삭제
			//select 전용 dto 생성
			EbookDTO selectdto = new EbookDTO();
			selectdto.setExTableName("ebook");
			selectdto.setExColumn("eb_logoimg, eb_pk, eb_subject");
			for(int z=0;z <= chk.length-1;z++){
				String eb_idx_chk = exdao.filter(chk[z]);
				selectdto.setExWhereQuery("WHERE eb_idx = '"+eb_idx_chk+"' ");
				HashMap<String, String> ebook = exdao.selectQueryRecord(selectdto);
				
				//폴더삭제
				String uploaddir =  fileutil.realPath(request, "/data/ebook/");
				String uploaddir_logo =  fileutil.realPath(request, "/data/ebook/logo/");
				if(!"".equals(Func.nvl(ebook.get("eb_logoimg")))) Func.fileDelete(uploaddir_logo+"/"+ebook.get("eb_logoimg"));
				Func.folderDelete(uploaddir+"/"+ebook.get("eb_pk")+"/");

				//삭제
				exdao.executeQuery("delete from ebook where eb_pk='"+ebook.get("eb_pk")+"' ");
				
				//작업기록
				Func.writeManagerLog ("이북 삭제 ["+ebook.get("eb_subject")+"]", request);
			}
		} else { //단일삭제
			//파일삭제
			EbookDTO selectdto = new EbookDTO();
			selectdto.setExTableName("ebook");
			selectdto.setExColumn("eb_logoimg, eb_pk, eb_subject");
			selectdto.setExWhereQuery("WHERE eb_idx = '"+eb_idx+"' ");
			HashMap<String, String> ebook = exdao.selectQueryRecord(selectdto);
			
			String uploaddir =  fileutil.realPath(request, "/data/ebook/");
			String uploaddir_logo =  fileutil.realPath(request, "/data/ebook/logo/");
			if(!"".equals(Func.nvl(ebook.get("eb_logoimg")))) Func.fileDelete(uploaddir_logo+"/"+ebook.get("eb_logoimg"));
			Func.folderDelete(uploaddir+"/"+ebook.get("eb_pk")+"/");

			//삭제
			exdao.executeQuery("delete from ebook where eb_pk='"+ebook.get("eb_pk")+"' ");
			
			//작업기록
			Func.writeManagerLog ("이북 삭제 ["+ebook.get("eb_subject")+"]", request);
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
		
		for (int z = 0; z <= chk.length - 1; z++) {
			String eb_idx = chk[z];
			String eb_chk = tot_level_chk;
			//상태변경
			exdao.executeQuery("update ebook set eb_chk = '"+eb_chk+"' where eb_idx ='"+eb_idx+"'");
			
			//제목조회
			EbookDTO selectdto = new EbookDTO();		//select 전용 dto
			selectdto.setExTableName("ebook");
			selectdto.setExColumn("eb_subject");
			selectdto.setExWhereQuery("where eb_idx='"+eb_idx+"'");
			String eb_subject = exdao.selectQueryColumn(selectdto);
			
			//작업기록
			String eb_chk_str = "사용";
			if("N".equals(eb_chk)) eb_chk_str = "중지";
			Func.writeManagerLog ("이북 상태변경 ["+eb_subject+"("+eb_chk_str+")]", request);
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
		
		//리스트조회하기
		EbookDTO selectdto = new EbookDTO();		//select 전용 dto
		selectdto.setExTableName("ebook");
		selectdto.setExColumn("eb_idx, eb_subject, eb_chk, eb_code");
		selectdto.setExOrderByQuery("ORDER BY eb_code ASC");

		List<HashMap<String, String>>ebookList = exdao.selectQueryTable(selectdto);
		request.setAttribute("ebookList", ebookList);

	}

	/**
	 * @title : 순서일괄수정처리
	 * @method : listMoveOk
	 */
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String eb_idx = chk[z];
			//순서변경
			exdao.executeQuery("update ebook set eb_code = '"+Func.cStr((z+1))+"' where eb_idx ='"+eb_idx+"'");
		}
		
		//작업기록
		Func.writeManagerLog ("이북 순서일괄처리", request);

	}

	@Override
	public void move(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void conf(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void confOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

}
