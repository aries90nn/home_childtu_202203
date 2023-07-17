package kr.co.nninc.ncms.board_code.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board_code.service.BoardCodeDTO;
import kr.co.nninc.ncms.board_code.service.BoardCodeService;
import kr.co.nninc.ncms.board_config.service.BoardConfigDTO;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;


/**
 * 게시판 분류를 관리하기 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.10.20
 * @version 1.1
 */
@Service("boardCodeService")
public class BoardCodeServiceImpl  extends EgovAbstractServiceImpl implements BoardCodeService{

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** FileUtil */
	@Resource(name="fileutil")
	private FileUtil fileutil;

	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**
	 * @title : 분류목록,등록
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
	
		String a_num = exdao.filter(request.getParameter("a_num"));
		request.setAttribute("a_num", a_num);
		
		//리스트 가져오기
		BoardCodeDTO selectdto = new BoardCodeDTO();
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("ct_idx, ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_wdate, ct_codeno, a_num, ct_file");
		selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
		selectdto.setExOrderByQuery("order by ct_code");
		List<HashMap<String,String>>boardcodeList = exdao.selectQueryTable(selectdto);
		request.setAttribute("boardcodeList", boardcodeList);
		
		//게시판 정보
		selectdto = new BoardCodeDTO();
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_bbsname");
		selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
		String a_bbsname = exdao.selectQueryColumn(selectdto);
		request.setAttribute("a_bbsname", a_bbsname);
		
	}

	/**
	 * @title : 게시판 분류 등록처리
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		BoardCodeDTO boardcode = (BoardCodeDTO) map.get("boardcodedto");
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ===============
		String uploaddir =  fileutil.realPath(request, "/data/board_code/");
		Func.folderCreate(uploaddir);
		//==============================================
		
		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "분류파일이미지", "ct_file_file", 1000 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			boardcode.setCt_file(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		//select전용 dto
		BoardCodeDTO selectdto = new BoardCodeDTO();
		
		//마지막 순서코드 가져오기
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("max( ct_code )");
		String max_code = Func.cStr( Func.cInt( exdao.selectQueryColumn(selectdto) ) + 1 );
		
		//게시판분류 저장
		boardcode.setCt_wdate( Func.date("Y-m-d H:i:s") );
		boardcode.setCt_code(max_code);
		boardcode.setExTableName("board_code");
		exdao.insert(boardcode);
		
		//메뉴값 생성 및 적용
		String ct_codeno_ref = exdao.filter(multi.getParameter("ct_codeno_ref"));
		selectdto = new BoardCodeDTO();
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("max(ct_idx)");
		int max_ct_idx = Func.cInt( exdao.selectQueryColumn(selectdto) );
		String ct_codeno = ct_codeno_ref+"C"+max_ct_idx+";";
		
		exdao.executeQuery("update board_code set ct_codeno = '"+exdao.filter(ct_codeno)+"' where ct_idx = '"+max_ct_idx+"'");
		request.setAttribute("max_ct_idx", max_ct_idx);
		
		String a_num = Func.nvl(multi.getParameter("a_num"));
		return "redirect:write.do?a_num="+a_num;
	}

	/**
	 * @title : 게시판 분류 수정처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		BoardCodeDTO boardcode = (BoardCodeDTO) map.get("boardcodedto");
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ===============
		String uploaddir =  fileutil.realPath(request, "/data/board_code/");
		Func.folderCreate(uploaddir);
		//==============================================
		
		String ct_idx		= exdao.filter(request.getParameter("ct_idx"));
		String ct_name 		= request.getParameter("ct_name_"+ct_idx);
		String ct_file 		= request.getParameter("ct_file_"+ct_idx);
		String ct_file_org 	= request.getParameter("ct_file_org_"+ct_idx);
		String ct_file_del 	= request.getParameter("ct_file_del_"+ct_idx);
		
		// 파일 유효성 검사 및 저장
		boardcode.setCt_file("");
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "분류파일이미지", "ct_file_file_"+ct_idx, 1000 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			boardcode.setCt_file(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		if(!"".equals(boardcode.getCt_file())){  //첨부파일이 있으면
			Func.fileDelete(uploaddir+"/"+ct_file_org);
		}else{
			boardcode.setCt_file(ct_file_org);

			if(!"".equals(Func.nvl(ct_file_del))){ //첨부파일만 삭제
				Func.fileDelete(uploaddir+"/"+ct_file_org);
				boardcode.setCt_file("");
			}
		}
		
		boardcode.setCt_wdate(Func.date("Y-m-d H:i:s"));
		boardcode.setCt_name(ct_name);
		
		//제외필드처리
		boardcode.setCt_idx(null);
		boardcode.setA_num(null);
		boardcode.setCt_code(null);
		boardcode.setCt_ref(null);
		boardcode.setCt_depth(null);
		boardcode.setCt_chk(null);
		boardcode.setCt_codeno(null);
		
		//수정
		boardcode.setExTableName("board_code");
		boardcode.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
		exdao.update(boardcode);
		
		String a_num = request.getParameter("a_num");
		return "redirect:write.do?a_num="+a_num;
	}

	/**
	 * @title : 게시판 분류 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 		= Func.nvl(request.getParameter("status")); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String ct_idx		= Func.nvl(request.getParameter("ct_idx"));  //단일 삭제 사용
		String uploaddir	= fileutil.realPath(request, "/data/board_code/");
		
		BoardCodeDTO selectdto = new BoardCodeDTO();	//select 전용 dto
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("ct_codeno, ct_file");
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				String ct_idx_chk = exdao.filter(chk[z]);
				selectdto.setExWhereQuery("where ct_idx = '"+ct_idx_chk+"'");
				HashMap<String, String>boardcode = exdao.selectQueryRecord(selectdto);
				
				// 파일삭제
				String ct_file = Func.nvl( boardcode.get("ct_file") );
				if(!"".equals(ct_file)) {
					Func.fileDelete(uploaddir+"/"+ct_file);
				}
				
				// 삭제하기
				exdao.executeQuery("delete from board_code where ct_idx = '"+ct_idx_chk+"'");
			}
		}else{ //단일삭제
			selectdto.setExWhereQuery("where ct_idx = '"+ct_idx+"'");
			HashMap<String, String>boardcode = exdao.selectQueryRecord(selectdto);
			
			// 파일삭제
			String ct_file = Func.nvl( boardcode.get("ct_file") );
			if(!"".equals(ct_file)) {
				Func.fileDelete(uploaddir+"/"+ct_file);
			}
			
			// 삭제하기
			exdao.executeQuery("delete from board_code where ct_idx = '"+ct_idx+"'");
		}
		
	}

	/**
	 * @title : 게시판 분류 상태변경
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String tot_level_chk = exdao.filter(request.getParameter("tot_level_chk"));
		String[] chk = request.getParameterValues("chk");
		
		for (int z = 0; z <= chk.length - 1; z++) {
			String ct_idx = exdao.filter( chk[z] );
			String ct_chk = tot_level_chk;
			
			exdao.executeQuery("update board_code set ct_chk = '"+ct_chk+"' where ct_idx = '"+ct_idx+"'");
		}
		
	}

	/**
	 * @title : 게시판 분류 순서변경
	 * @method : move
	 */
	@Override
	public void move(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String ct_idx	= exdao.filter( request.getParameter("ct_idx") ); //--일련번호
		String ct_code	= exdao.filter( request.getParameter("ct_code") ); //--정렬번호
		String ct_ref	= exdao.filter( request.getParameter("ct_ref") ); //--참조키
		String move		= exdao.filter( request.getParameter("move") ); //--처리구분
		
		BoardConfigDTO selectdto = new BoardConfigDTO();
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("ct_idx, ct_code");
		if("up".equals(move)){
			selectdto.setExWhereQuery("WHERE ct_code < '"+ct_code+"' and ct_ref ='"+ct_ref+"'");
			selectdto.setExOrderByQuery("ORDER BY ct_code desc");
		}else if("down".equals(move)){
			selectdto.setExWhereQuery("WHERE ct_code > '"+ct_code+"' and ct_ref ='"+ct_ref+"'");
			selectdto.setExOrderByQuery("ORDER BY ct_code");
		}
		
		HashMap<String, String>rs = exdao.selectQueryRecord(selectdto);
		if(rs.size() > 0){
			String v_ct_idx  = Func.nvl( rs.get("ct_idx") );  //-- 대체할 디비번호
			String v_ct_code = Func.nvl( rs.get("ct_code") ); //-- 대체할 값
			
			//순서변경1
			exdao.executeQuery("update board_code set ct_code = '"+ct_code+"' where ct_idx ='"+v_ct_idx+"'");
			
			//순서변경2
			exdao.executeQuery("update board_code set ct_code = '"+v_ct_code+"' where ct_idx ='"+ct_idx+"'");
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
		
		String a_num = exdao.filter(request.getParameter("a_num"));
		request.setAttribute("a_num", a_num);
		
		//select 전용 dto
		BoardCodeDTO selectdto = new BoardCodeDTO();
		
		//리스트가져오기
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("ct_idx, ct_code, ct_name");
		selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
		selectdto.setExOrderByQuery("order by ct_code");
		List<HashMap<String,String>>boardcodeList = exdao.selectQueryTable(selectdto);
		request.setAttribute("boardcodeList", boardcodeList);
		
		// 게시판정보
		selectdto = new BoardCodeDTO();
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_bbsname");
		selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
		String a_bbsname = exdao.selectQueryColumn(selectdto);
		request.setAttribute("a_bbsname", a_bbsname);
		
	}

	/**
	 * @title : 순서변경처리
	 * @method : listMoveOk
	 */
	@Override
	public void listMoveOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String a_num = exdao.filter( request.getParameter("a_num") );
		
		//select 전용 dto
		BoardCodeDTO selectdto = new BoardCodeDTO();
		
		// 게시판정보
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_bbsname");
		selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
		String a_bbsname = exdao.selectQueryColumn(selectdto);
		
		String[] chk = request.getParameterValues("chk"); // 선택 체크 값
		for (int z = 0; z <= chk.length - 1; z++) {
			String ct_idx = chk[z];
			
			exdao.executeQuery("UPDATE board_code SET ct_code = '"+Func.cStr(z + 1)+"' WHERE ct_idx = '"+ct_idx+"'");
		}
		
		//작업기록
		Func.writeManagerLog ("게시판 분류 일괄순서변경("+a_bbsname+")", request);
		
	}

}
