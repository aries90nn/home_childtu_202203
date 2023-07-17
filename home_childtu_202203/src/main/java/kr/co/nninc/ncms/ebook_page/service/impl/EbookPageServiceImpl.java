package kr.co.nninc.ncms.ebook_page.service.impl;

import java.util.Arrays;
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
import kr.co.nninc.ncms.common.FuncThumb;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.ebook.service.EbookDTO;
import kr.co.nninc.ncms.ebook_page.service.EbookPageDTO;
import kr.co.nninc.ncms.ebook_page.service.EbookPageService;


/**
 * 이북페이지를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.04.17
 * @version 1.1
 */
@Service("ebookPageService")
public class EbookPageServiceImpl extends EgovAbstractServiceImpl implements EbookPageService {

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
	 * @title : 이북페이지 등록폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//이북조회하기
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("ebook");
		String s_fields = "eb_idx, eb_subject, eb_code, eb_skin, eb_logoimg, eb_wdate, eb_mdate, eb_chk, eb_pk, eb_viewtype, eb_width, eb_height, eb_width2, eb_height2";
		selectdto.setExColumn(s_fields);
		String eb_pk = exdao.filter( request.getParameter("eb_pk") );
		selectdto.setExWhereQuery("where eb_pk = '"+eb_pk+"'");
		HashMap<String, String>ebook_map = exdao.selectQueryRecord(selectdto);
		request.setAttribute("ebookdto", ebook_map);

		//이북페이지 조회하기
		selectdto = new SelectDTO();
		selectdto.setExTableName("ebook_page");
		selectdto.setExColumn(new EbookPageDTO());
		String whereQuery = "where eb_pk='"+eb_pk+"' ";
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY ebp_code ASC");
		List<HashMap<String,String>>ebookPageList = exdao.selectQueryTable(selectdto);
		request.setAttribute("ebookpageList", ebookPageList);
		
		Func.getNowPage(request);
	}

	/**
	 * @title : 이북페이지 등록처리
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		EbookPageDTO ebookpage = (EbookPageDTO) map.get("ebookpagedto");
		String prepage		= Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)) prepage = "/ncms/ebook/list.do";
		
		SelectDTO selectdto = new SelectDTO();
		
		//이북조회하기
		selectdto.setExTableName("ebook");
		String s_fields = "eb_idx, eb_subject, eb_code, eb_skin, eb_logoimg, eb_wdate, eb_mdate, eb_chk, eb_pk, eb_viewtype, eb_width, eb_height, eb_width2, eb_height2";
		selectdto.setExColumn(s_fields);
		String eb_pk = exdao.filter( request.getParameter("eb_pk") );
		selectdto.setExWhereQuery("where eb_pk = '"+eb_pk+"'");
		HashMap<String, String>ebook_map = exdao.selectQueryRecord(selectdto);
		request.setAttribute("ebook", ebook_map);
		
		// 마지막 순서코드가져오기
		selectdto = new SelectDTO();
		selectdto.setExTableName("ebook_page");
		selectdto.setExColumn("max(ebp_code)");
		selectdto.setExWhereQuery("where eb_pk = '"+eb_pk+"'");
		int ebp_code = Func.cInt( exdao.selectQueryColumn(selectdto) )+1;
		
		// 필드세팅
		String ebp_wdate = Func.date("Y-m-d H:i:s");
		String ebp_mdate = Func.date("Y-m-d H:i:s");
		ebookpage.setEbp_wdate(ebp_wdate);
		ebookpage.setEbp_mdate(ebp_mdate);
		ebookpage.setEbp_chk("Y");
		
		//------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
		String realpath =  fileutil.realPath(request, "/data/");
		String strDir = realpath+"/ebook/"+eb_pk+"/";
		String strDir2 = realpath+"/ebook/"+eb_pk+"/middle/";
		String strDir3 = realpath+"/ebook/"+eb_pk+"/small/";
		Func.folderCreate(strDir);
		Func.folderCreate(strDir2);
		Func.folderCreate(strDir3);
		//------ 저장공간인 [DATA]폴더가 없다면 생성 한다. ------------------------
		
		String[] ebp_pageimg_data = request.getParameterValues("ebp_pageimg_data");
		if(ebp_pageimg_data != null){
			Arrays.sort( ebp_pageimg_data );
			
			for(int i=0;i<=ebp_pageimg_data.length-1;i++){
				String ebp_pageimg = ebp_pageimg_data[i];
				if (!"".equals(Func.nvl(ebp_pageimg))) {
					//썸네일1
					FuncThumb.GD2_make_thumb(Func.cInt(ebook_map.get("eb_width2")), Func.cInt(ebook_map.get("eb_height2")), "/middle/", strDir, ebp_pageimg);
					//썸네일2
					FuncThumb.GD2_make_thumb(50,71,"/small/", strDir, ebp_pageimg);
					
					String ebp_subject = "page"+ebp_code;
					ebookpage.setEbp_subject(ebp_subject);
					ebookpage.setEbp_code(Func.cStr(ebp_code));
					ebookpage.setEbp_pageimg(ebp_pageimg);
					
					//insert 제외필드
					ebookpage.setEbp_idx(null);
					
					//insert
					ebookpage.setExTableName("ebook_page");
					exdao.insert(ebookpage);
					
					ebp_code = ebp_code + 1;
				}
			}
		}
		
		
		//작업기록
		Func.writeManagerLog ("이북 페이지 등록 ["+ebook_map.get("ebp_subject") +"]", request);
		return "redirect:/ncms/ebook_page/write.do?eb_pk="+eb_pk+"&prepage="+Func.urlEncode(prepage);
	}

	/**
	 * @title : 이북 페이지 수정
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		EbookPageDTO ebookpage = (EbookPageDTO) map.get("ebookpagedto");
		String prepage		= Func.nvl( ( request.getParameter("prepage") ) ).trim();
		if("".equals(prepage)) prepage = "/ncms/ebook/list.do";
		
		String eb_pk = exdao.filter( request.getParameter("eb_pk") ).trim();
		
		//------------------- 저장공간인 [DATA]폴더가 없다면 생성 한다. -------------------
		String realpath =  fileutil.realPath(request, "/data/");
		String strDir = realpath+"/ebook/"+eb_pk+"/";
		String strDir2 = realpath+"/ebook/"+eb_pk+"/middle/";
		String strDir3 = realpath+"/ebook/"+eb_pk+"/small/";
		Func.folderCreate(strDir);
		Func.folderCreate(strDir2);
		Func.folderCreate(strDir3);
		//------------------------------------------------------------------------------
		
		//이북조회하기
		EbookDTO selectdto = new EbookDTO();
		selectdto.setExTableName("ebook");
		String s_fields = "eb_idx, eb_subject, eb_code, eb_skin, eb_logoimg, eb_wdate, eb_mdate, eb_chk, eb_pk, eb_viewtype, eb_width, eb_height, eb_width2, eb_height2";
		selectdto.setExColumn(s_fields);
		selectdto.setExWhereQuery("where eb_pk = '"+eb_pk+"'");
		HashMap<String, String>ebook_map = exdao.selectQueryRecord(selectdto);
		
		String ebp_idx					= ebookpage.getEbp_idx();
		String ebp_subject				= request.getParameter("ebp_subject_"+ebp_idx);
		String ebp_pageimg			= "";
		String ebp_pageimg_org	= Func.nvl(request.getParameter("ebp_pageimg_org_"+ebp_idx));
		String ebp_pageimg_del		= Func.nvl(request.getParameter("ebp_pageimg_del_"+ebp_idx));
		
		//--- 이미지파일저장 ----------------------------------
		int sizeLimit = 10 * 1024 * 1024 ; // 10M
		
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), strDir, false, "이북페이지이미지", "ebp_pageimg_file_"+ebp_idx, sizeLimit, fileutil.FILE_TYPE_IMAGE);
		if(file != null) {
			ebp_pageimg = Func.nvl(file.getFile_name());
			ebookpage.setEbp_pageimg(ebp_pageimg);
		}
		//--- 이미지파일저장 ----------------------------------

		//체크하면 첨부파일 삭제
		if (!ebp_pageimg_del.equals("")){ //첨부파일만 삭제
			Func.fileDelete( strDir + ebp_pageimg_org );
			Func.fileDelete( strDir2 + ebp_pageimg_org );
			Func.fileDelete( strDir3 + ebp_pageimg_org );
			ebp_pageimg = "";
			ebp_pageimg_org = "";
		}

		if (!ebp_pageimg.equals("")){ //첨부파일이 있으면
			if(!ebp_pageimg_org.equals("")){
				Func.fileDelete( strDir + ebp_pageimg_org );
				Func.fileDelete( strDir2 + ebp_pageimg_org );
				Func.fileDelete( strDir3 + ebp_pageimg_org );
			}
			//썸네일1
			FuncThumb.GD2_make_thumb(Func.cInt(ebook_map.get("eb_width2")), Func.cInt(ebook_map.get("eb_height2")),"/middle/", strDir, ebp_pageimg);
			//썸네일2
			FuncThumb.GD2_make_thumb(50,71,"/small/", strDir, ebp_pageimg);
		}else{
			ebp_pageimg = ebp_pageimg_org;
		}
		
		//수정처리에서 제외할 필드
		ebookpage.setEbp_code(null);
		ebookpage.setEbp_wdate(null);
		ebookpage.setEbp_chk(null);
		
		//이북페이지수정
		String ebp_mdate = Func.date("Y-m-d H:i:s");
		ebookpage.setEbp_mdate(ebp_mdate);
		ebookpage.setEbp_subject(ebp_subject);
		ebookpage.setEbp_pageimg(ebp_pageimg);
		ebookpage.setExTableName("ebook_page");
		
		
		//update 제외필드
		ebookpage.setEbp_idx(null);
		
		//update
		ebookpage.setExWhereQuery("where ebp_idx = '"+ebp_idx+"'");
		exdao.update(ebookpage);
		
		//작업기록
		Func.writeManagerLog ("이북 페이지 수정 ["+ebp_subject +"]", request);
		
		return "redirect:"+prepage;
	}

	/**
	 * @title : 이북페이지 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 			= Func.nvl(request.getParameter("status")); //값:totdel (다중삭제시 사용)
		String[] chk 			= request.getParameterValues("chk"); //선택 체크 값
		String ebp_idx		= exdao.filter( request.getParameter("ebp_idx") );  //단일 삭제 사용
		String eb_pk			= exdao.filter( request.getParameter("eb_pk") );  //단일 삭제 사용
		String ebp_mdate = Func.date("Y-m-d H:i:s");
		
		String realpath =  fileutil.realPath(request, "/data/");
		String strDir = realpath+"/ebook/"+eb_pk+"/";
		String strDir2 = realpath+"/ebook/"+eb_pk+"/middle/";
		String strDir3 = realpath+"/ebook/"+eb_pk+"/small/";
		
		if (status.equals("totdel")) { //다중삭제
			//select 전용 dto 생성
			SelectDTO selectdto = new SelectDTO();
			selectdto.setExTableName("ebook_page");
			selectdto.setExColumn("ebp_subject, ebp_pageimg, eb_pk");
			for(int z=0;z <= chk.length-1;z++){
				String ebp_idx_chk = exdao.filter(chk[z]);
				selectdto.setExWhereQuery("WHERE ebp_idx = '"+ebp_idx_chk+"' ");
				HashMap<String, String> ebookpage = exdao.selectQueryRecord(selectdto);
				String ebp_pageimg = Func.nvl(ebookpage.get("ebp_pageimg"));
				
				if(!"".equals(ebp_pageimg)) {
					Func.fileDelete( strDir + ebp_pageimg );
					Func.fileDelete( strDir2 + ebp_pageimg );
					Func.fileDelete( strDir3 + ebp_pageimg );
				}
				exdao.executeQuery("DELETE FROM ebook_page where ebp_idx='"+ebp_idx_chk+"' ");
				
				//작업기록
				Func.writeManagerLog ("이북 페이지 삭제 ["+ebookpage.get("ebp_subject")+"]", request);
			}
			
		} else { //단일삭제
			SelectDTO selectdto = new SelectDTO();
			selectdto.setExTableName("ebook_page");
			selectdto.setExColumn("ebp_subject, ebp_pageimg, eb_pk");
			selectdto.setExWhereQuery("WHERE ebp_idx = '"+ebp_idx+"' ");
			HashMap<String, String> ebookpage = exdao.selectQueryRecord(selectdto);
			String ebp_pageimg = Func.nvl(ebookpage.get("ebp_pageimg"));
			
			if(!"".equals(ebp_pageimg)) {
				Func.fileDelete( strDir + ebp_pageimg );
				Func.fileDelete( strDir2 + ebp_pageimg );
				Func.fileDelete( strDir3 + ebp_pageimg );
			}
			exdao.executeQuery("DELETE FROM ebook_page where ebp_idx='"+ebp_idx+"' ");
			
			//작업기록
			Func.writeManagerLog ("이북 페이지 삭제 ["+ebookpage.get("ebp_subject")+"]", request);
		}
	}

	/**
	 * @title : 이미지만 삭제처리
	 * @method : delete2Ok
	 */
	@Override
	public void delete2Ok(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String ebp_idx		= exdao.filter( request.getParameter("ebp_idx") );  //단일 삭제 사용
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("ebook_page");
		selectdto.setExColumn("ebp_subject, ebp_pageimg, eb_pk");
		selectdto.setExWhereQuery("WHERE ebp_idx = '"+ebp_idx+"' ");
		HashMap<String, String> ebookpage = exdao.selectQueryRecord(selectdto);
		String ebp_pageimg = Func.nvl(ebookpage.get("ebp_pageimg"));
		String eb_pk = Func.nvl(ebookpage.get("eb_pk"));
		String ebp_mdate = Func.date("Y-m-d H:i:s");
		
		//------------------- 파일을 삭제한다. -------------------
		if(!"".equals(ebp_pageimg)) {
			String realpath =  fileutil.realPath(request, "/data/");
			String strDir = realpath+"/ebook/"+eb_pk+"/";
			String strDir2 = realpath+"/ebook/"+eb_pk+"/middle/";
			String strDir3 = realpath+"/ebook/"+eb_pk+"/small/";
			
			Func.fileDelete( strDir + ebp_pageimg );
			Func.fileDelete( strDir2 + ebp_pageimg );
			Func.fileDelete( strDir3 + ebp_pageimg );
			//------------------------------------------------------------------------------
			
			//이미지만 초기화
			exdao.executeQuery("update ebook_page set ebp_pageimg = '', ebp_mdate='"+ebp_mdate+"' where ebp_idx ='"+ebp_idx+"'");
			
			//작업기록
			Func.writeManagerLog ("이북 페이지 이미지 삭제 ["+ebookpage.get("ebp_subject")+"]", request);
		}
	}

	/**
	 * @title : 순서일괄수정
	 * @method : listMove(Model model)
	 */
	@Override
	public void listMove(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String eb_pk		= exdao.filter( request.getParameter("eb_pk") );  //단일 삭제 사용
		
		//리스트조회하기
		EbookPageDTO selectdto = new EbookPageDTO();		//select 전용 dto
		selectdto.setExTableName("ebook_page");
		selectdto.setExColumn("ebp_idx, ebp_subject, ebp_chk");
		selectdto.setExWhereQuery("where eb_pk='"+eb_pk+"'");
		selectdto.setExOrderByQuery("ORDER BY ebp_code ASC");
		
		//이북페이지 조회하기
		List<HashMap<String, String>>ebookpageList = exdao.selectQueryTable(selectdto);
		request.setAttribute("ebookpageList", ebookpageList);
		request.setAttribute("eb_pk", eb_pk);
		
	}

	/**
	 * @title : 순서일괄수정 처리
	 * @method : listMoveOk(Model model)
	 */
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String ebp_idx = exdao.filter(chk[z]);
			//순서변경
			exdao.executeQuery("update ebook_page set ebp_code = '"+Func.cStr((z+1))+"' where ebp_idx ='"+ebp_idx+"'");
		}
	}

	@Override
	public void levelOk(Model model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * @title : 이북 페이지 멀티업로드
	 * @method : nfuUpload
	 */
	@Override
	public void nfuUpload(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		String eb_pk		= Func.nvl(request.getParameter("eb_pk"));
		eb_pk				= eb_pk.replaceAll("\\.", "");
		eb_pk				= eb_pk.replaceAll("/", "");
		
		String uploaddir 		=  fileutil.realPath(request, "/data/ebook/"+eb_pk+"/");
		int sizeLimit				= 1000 * 1024 * 1024;
		String target_name	= "";
		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "이북로고이미지", "NFU_add_file", sizeLimit, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(file.getFile_name())) {
			target_name = file.getFile_name();
		}
		request.setAttribute("target_name", target_name);
		
	}

	/**
	 * @title : 이북 페이지 일반업로드
	 * @method : nfuNormalUpload
	 */
	@Override
	public void nfuNormalUpload(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		
		String eb_pk		= Func.nvl(request.getParameter("eb_pk"));
		eb_pk				= eb_pk.replaceAll("\\.", "");
		eb_pk				= eb_pk.replaceAll("/", "");
		
		String init = request.getParameter("init");
		String uploadchk = "";
		String file_names = "";
		String uploaddir =  fileutil.realPath(request, "/data/ebook/"+eb_pk+"/");
	
		uploadchk = request.getParameter("uploadchk");
		String maxFileSize = request.getParameter("maxFileSize");
		String fileFilter = request.getParameter("fileFilter");
		String fileFilterType = request.getParameter("fileFilterType");
	
		if("Y".equals(uploadchk)){
			// 파일수
			String filecount	= request.getParameter("filecount");
			
			for(int i=1;i<=Func.cInt(filecount);i++){
				String filename = fileutil.fileNormalUpload(multi.getFileMap(), uploaddir, "NFU_add_file_"+i, Func.cLng(maxFileSize), fileFilter, fileFilterType);
				if(!"".equals(Func.nvl(filename))) {
					file_names += filename+"|";
				}
			}
		}
		request.setAttribute("uploadchk", uploadchk);
		request.setAttribute("file_names", file_names);
		request.setAttribute("eb_pk", eb_pk);
		
	}


}
