package kr.co.nninc.ncms.ebookpdf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.ebookpdf.service.EbookpdfDTO;
import kr.co.nninc.ncms.ebookpdf.service.EbookpdfService;

@Service("ebookpdfService")
public class EbookPdfServiceImpl implements EbookpdfService{

	// 동적DAO
	@Resource(name="extendDAO")
	private ExtendDAO exdao;
	
	// Fileutil
	@Resource(name="fileutil")
	private FileUtil fileutil;
	
	// 메시지 출력
	@Resource(name="messageService")
	private MessageService messageService;
	
	// 소식지 리스트
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
		EbookpdfDTO selectdto = new EbookpdfDTO();
		
		//목록조회
		selectdto.setExTableName("ebookpdf");
		String s_fileds = exdao.getFieldNames(new EbookpdfDTO());
		selectdto.setExColumn(s_fileds);
		String whereQuery = "";
		if(!"".equals(v_keyword)){
			whereQuery += " AND "+v_search+" like '%"+v_keyword+"%'";
		}

		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY eb_code desc");
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
		
		String pagingtag = paging.execute(querystring);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("querystring", querystring);
	}
	
	// 소식지 등록
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		EbookpdfDTO ebook = new EbookpdfDTO();
		ebook.setEb_chk("Y");
		
		request.setAttribute("ebook", ebook);
	}
	
	// 소식지 등록 처리
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		EbookpdfDTO ebook = (EbookpdfDTO) map.get("ebookpdfdto");
		
		// 저장폴더 생성
		String uploaddir =  fileutil.realPath(request, "/data/ebookpdf/");
		Func.folderCreate(uploaddir);
		
		// 파일 유효성 검사 및 저장
		String[] pdf_type = { "pdf" };
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "소식지pdf", "eb_pdf_file", 1000 * 1024 * 1024, pdf_type);
		if( !"".equals(Func.nvl(file.getError_msg())) ){
			return messageService.backMsg(model, file.getError_msg());
		}
		String eb_pdf = Func.nvl(file.getFile_name());
		ebook.setEb_pdf(eb_pdf);
		
		//메인이미지
		file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "메인이미지", "eb_img_file", 10 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if( !"".equals(Func.nvl(file.getError_msg())) ){
			return messageService.backMsg(model, file.getError_msg());
		}
		String eb_img = Func.nvl(file.getFile_name());
		ebook.setEb_img(eb_img);
		
		// 순서
		String eb_code = exdao.selectQueryColumn("ifnull(max(eb_code),0)+1", "ebookpdf");
		ebook.setEb_code(eb_code);
		
		//이북등록
		ebook.setEb_regdate(Func.date("Y-m-d H:i:s"));
		String BIILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(BIILDER_DIR)){
			ebook.setSite_dir(BIILDER_DIR);
		}
		
		//insert제외
		ebook.setEb_idx(null);
		
		//insert
		ebook.setExTableName("ebookpdf");
		exdao.insert(ebook);
		
		//작업기록
		Func.writeManagerLog ("소식지 등록 ["+ebook.getEb_subject()+"]", request);

		String url = Func.cStr( request.getParameter("prepage") );
		if("".equals(url)){ url = "list.do"; }
		return "redirect:"+url;
	}
	
	// 소식지 수정
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//상세보기
		EbookpdfDTO selectdto = new EbookpdfDTO();	//select 전용 dto 초기화
		selectdto.setExTableName("ebookpdf");
		selectdto.setExColumn(new EbookpdfDTO());
		String eb_idx = exdao.filter( request.getParameter("eb_idx") );
		selectdto.setExWhereQuery("where eb_idx = '"+eb_idx+"'");
		HashMap<String, String>ebook_map = exdao.selectQueryRecord(selectdto);
		
		request.setAttribute("ebook", ebook_map);	
	}

	// 소식지 수정 처리
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		EbookpdfDTO ebook = (EbookpdfDTO) map.get("ebookpdfdto");
		String prepage		= exdao.filter( request.getParameter("prepage") );
		if("".equals(prepage)) prepage = "list.do";
		
		// 저장공간인 생성
		String uploaddir =  fileutil.realPath(request, "/data/ebookpdf/");
		Func.folderCreate(uploaddir);
		
		// 파일 유효성 검사 및 저장
		String[] pdf_type = { "pdf" };
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "소식지"
				+ "pdf", "eb_pdf_file", 1000 * 1024 * 1024, pdf_type);
		if( !"".equals(Func.nvl(file.getError_msg())) ){
			return messageService.backMsg(model, file.getError_msg());
		}
		String eb_pdf = Func.nvl(file.getFile_name());
		
		String eb_pdf_org = Func.nvl(request.getParameter("eb_pdf_org"));
		if ("".equals(eb_pdf)){
			String eb_pdf_del = Func.nvl(request.getParameter("eb_pdf_del"));
			if ("Y".equals(eb_pdf_del)){
				Func.fileDelete(uploaddir + "/" + eb_pdf_org); // 파일삭제
				ebook.setEb_pdf("");
			}else{
				ebook.setEb_pdf(eb_pdf_org);
			}
		}else{
			ebook.setEb_pdf(eb_pdf);
			if (!"".equals(eb_pdf_org)){
				Func.fileDelete(uploaddir + "/" + eb_pdf_org); // 파일삭제
			}
		}
		
		file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "메인이미지", "eb_img_file", 10 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if( !"".equals(Func.nvl(file.getError_msg())) ){
			return messageService.backMsg(model, file.getError_msg());
		}
		String eb_img = Func.nvl(file.getFile_name());
		
		String eb_img_org = Func.nvl(request.getParameter("eb_img_org"));
		if ("".equals(eb_img)){
			String eb_img_del = Func.nvl(request.getParameter("eb_img_del"));
			if ("Y".equals(eb_img_del)){
				Func.fileDelete(uploaddir + "/" + eb_img_org); // 파일삭제
				ebook.setEb_img("");
			}else{
				ebook.setEb_img(eb_img_org);
			}
		}else{
			ebook.setEb_img(eb_img);
			if (!"".equals(eb_img_org)){
				Func.fileDelete(uploaddir + "/" + eb_img_org); // 파일삭제
			}
		}
		
		
		String eb_idx = exdao.filter( request.getParameter("eb_idx") );
		ebook.setExTableName("ebookpdf");
		ebook.setExWhereQuery("where eb_idx = '"+eb_idx+"'");
		exdao.update(ebook);
		
		//작업기록
		Func.writeManagerLog ("소식지 수정 ["+ebook.getEb_subject()+"]", request);

		String url = Func.cStr( request.getParameter("prepage") );
		if("".equals(url)){ url = "list.do"; }
		return "redirect:"+url;
	}
	
	// 삭제 처리
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status = Func.nvl(request.getParameter("status"));
		
		if ("totdel".equals(status)){
			String[] arr_chk = request.getParameterValues("chk");
			for (int z = 0; z < arr_chk.length; z++) {
				deleteProc(request, arr_chk[z]);
			}
		}else{
			String eb_idx = Func.nvl(request.getParameter("idx"));
			deleteProc(request, eb_idx);
		}
	}
	
	// 소식지 삭제처리
	private void deleteProc(HttpServletRequest request, String eb_idx) throws Exception{		

		SelectDTO sltdto = new SelectDTO();
		sltdto.setExColumn("eb_subject, eb_pdf, eb_img");
		sltdto.setExTableName("ebookpdf");
		sltdto.setExWhereQuery("WHERE eb_idx='"+eb_idx+"'");
		
		HashMap<String, String> ebook = exdao.selectQueryRecord(sltdto);
		String eb_subject = Func.cStr(ebook.get("eb_subject"));
		String eb_pdf = Func.cStr(ebook.get("eb_pdf"));
		String eb_img = Func.cStr(ebook.get("eb_img"));
		
		exdao.executeQuery("DELETE FROM ebookpdf WHERE eb_idx='"+eb_idx+"'");
		if (!"".equals(eb_pdf)){
			String uploaddir = fileutil.realPath(request, "/data/ebookpdf/");
			Func.fileDelete(uploaddir + "/" + eb_pdf); // pdf삭제
			Func.fileDelete(uploaddir + "/" + eb_img); // 이미지삭제
		}
		
		//작업기록
		Func.writeManagerLog ("소식지 삭제["+eb_subject+"]", request);
		
	}

	// 사용/중지 처리
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
			exdao.executeQuery("update ebookpdf set eb_chk = '"+eb_chk+"' where eb_idx ='"+eb_idx+"'");
			
			//제목조회
			EbookpdfDTO selectdto = new EbookpdfDTO();		//select 전용 dto
			selectdto.setExTableName("ebookpdf");
			selectdto.setExColumn("eb_subject");
			selectdto.setExWhereQuery("where eb_idx='"+eb_idx+"'");
			String eb_subject = exdao.selectQueryColumn(selectdto);
			
			//작업기록
			String eb_chk_str = "사용";
			if("N".equals(eb_chk)) eb_chk_str = "중지";
			Func.writeManagerLog ("소식자 사용 상태변경 ["+eb_subject+"("+eb_chk_str+")]", request);
		}
	}

	// 순서일괄수정
	@Override
	public void listMove(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//리스트조회하기
		EbookpdfDTO selectdto = new EbookpdfDTO();		//select 전용 dto
		selectdto.setExTableName("ebookpdf");
		selectdto.setExColumn("eb_idx, eb_subject, eb_chk, eb_code");
		selectdto.setExOrderByQuery("ORDER BY eb_code desc");

		List<HashMap<String, String>>ebookList = exdao.selectQueryTable(selectdto);
		request.setAttribute("ebookList", ebookList);
	}

	// 순서일괄처리
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String[] arr_sort = request.getParameterValues("eb_code");
		String[] arr_idx = request.getParameterValues("idx");
		
		for (int z = 0; z < arr_idx.length; z++) {
			String idx = arr_idx[z];
			String sort = arr_sort[z];
			exdao.executeQuery("UPDATE ebookpdf SET eb_code = '"+sort+"' WHERE eb_idx = '"+idx+"'");
		}
		
		//작업기록
		Func.writeManagerLog ("소식지 순서일괄처리", request);
	}

	@Override
	public String down(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		String eb_idx = exdao.filter(request.getParameter("eb_idx"));
		
		if("".equals(eb_idx)){
			return messageService.backMsg(model, "잘못된 요청입니다.");
		}
		
		String filename = exdao.selectQueryColumn("eb_pdf", "ebookpdf", "where eb_idx = '"+eb_idx+"'");
		request.setAttribute("filename", filename);
		
		return "/ncms/ebookpdf/down";
	}
	
}
