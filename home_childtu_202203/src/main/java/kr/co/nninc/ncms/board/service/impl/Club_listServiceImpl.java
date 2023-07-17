package kr.co.nninc.ncms.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.BoardDTO;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board.service.SkinService;
import kr.co.nninc.ncms.board_code.service.BoardCodeDTO;
import kr.co.nninc.ncms.board_code.service.BoardCodeService;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 동아리 가입신청 스킨별 처리 구현 클래스
 * @author 나눔
 * @since 2019.06.03
 * @version 1.2
 */
@Service("club_listService")
public class Club_listServiceImpl extends EgovAbstractServiceImpl implements SkinService {

	/** BoardCodeService */
	@Resource(name="boardCodeService")
	private BoardCodeService boardCodeService;
	
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String board_a_num = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".board_a_num["+config.get("a_num")+"]")).trim();
		request.setAttribute("board_a_num", board_a_num);

	}

	@Override
	public void view(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 동아리생성시 동아리전용게시판에 분류생성
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값

		String a_num = exdao.filter( config.get("a_num") );
		String b_num = exdao.filter( request.getParameter("b_num") );
		String b_subject = exdao.filter( request.getParameter("b_subject") );
		String b_temp4 = exdao.filter( request.getParameter("b_temp4") );
		String board_a_num = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".board_a_num["+a_num+"]")).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		//마지막 순서코드 가져오기
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("max( ct_code )");
		String max_code = Func.cStr( Func.cInt( exdao.selectQueryColumn(selectdto) ) + 1 );
		
		BoardCodeDTO boardcode = new BoardCodeDTO();
		//게시판분류 저장
		boardcode.setCt_name( b_subject );
		boardcode.setA_num( board_a_num );
		boardcode.setCt_ref("0");
		boardcode.setCt_depth("1");
		boardcode.setCt_chk("Y");
		boardcode.setCt_wdate( Func.date("Y-m-d H:i:s") );
		boardcode.setCt_code(max_code);
		
		boardcode.setCt_idx(null);
		
		boardcode.setExTableName("board_code");
		exdao.insert(boardcode);
		
		//메뉴값 생성 및 적용
		String ct_codeno_ref = "C0;";
		selectdto = new SelectDTO();
		selectdto.setExTableName("board_code");
		selectdto.setExColumn("max(ct_idx)");
		String max_ct_idx = exdao.selectQueryColumn(selectdto);
		String ct_codeno = ct_codeno_ref+"C"+max_ct_idx+";";
		
		exdao.executeQuery("update board_code set ct_codeno = '"+exdao.filter(ct_codeno)+"' where ct_idx = '"+max_ct_idx+"'");
		request.setAttribute("max_ct_idx", max_ct_idx);
		
		//b_temp4에 max_ct_idx 저장하고 writeOk로 전달
		BoardDTO board = (BoardDTO) map.get("boarddto"); // 전송된 dto
		board.setB_temp4(max_ct_idx);
		skinDTO.setBoardDTO(board);
		
		String is_ad_cms = (String)request.getAttribute("is_ad_cms");
		if(!"Y".equals(is_ad_cms)){
			String prepage = Func.nvl( request.getParameter("prepage") ).trim();
			if("".equals(prepage)){prepage = "/main/site/club/clubList.do?a_num="+config.get("a_num");}
			String after_msg = messageService.redirectMsg(model, "동아리신청이 완료되었습니다.", prepage);
			skinDTO.setAfter_msg(after_msg);
		}

	}

	@Override
	public void modify(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 동아리정보수정시 동아리전용게시판에 분류수정
	 * @method : writeOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값

		String a_num = exdao.filter( config.get("a_num") );
		String b_num = exdao.filter( request.getParameter("b_num") );
		String b_subject = exdao.filter( request.getParameter("b_subject") );
		String b_temp4 = exdao.filter( request.getParameter("b_temp4") );
		String board_a_num = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".board_a_num["+a_num+"]")).trim();

		exdao.executeQuery("update board_code set ct_name = '"+b_subject+"' where ct_idx = '"+b_temp4+"'");
	}
	
	
	/**
	 * @title : 동아리명 중복검사
	 * @method : clubNameSearchCount
	 */
	public void clubNameSearchCount(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		
		String a_num = exdao.filter( config.get("a_num") );
		String sh_b_subject = exdao.filter( request.getParameter("sh_b_subject") );
		String sh_b_num = exdao.filter( request.getParameter("sh_b_num") );
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("board");
		selectdto.setExColumn("count(*)");
		String whereQuery = "where a_num = '"+a_num+"' and b_subject = '"+sh_b_subject+"'";
		if(!"".equals(sh_b_num)){
			whereQuery += " and b_num <> '"+sh_b_num+"'";
		}
		selectdto.setExWhereQuery(whereQuery);
		String cnt = exdao.selectQueryColumn(selectdto);
		request.setAttribute("cnt", cnt);
	}
	
	/**
	 * @title : 동아리 리스트(사용자페이지용)
	 * @method : clubList
	 */
	public void clubList(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		request.setAttribute("config", config);
		
		String a_num = exdao.filter( config.get("a_num") );
		
		SelectDTO selectdto = new SelectDTO();
		
		selectdto.setExTableName("board");
		selectdto.setExColumn("b_num, b_subject, b_file1, b_temp1, b_temp2, b_temp3, b_temp4, b_temp5, b_temp6, b_temp7, b_temp8");
		selectdto.setExWhereQuery("where a_num = "+a_num+" and b_temp7 = 'Y'");
		selectdto.setExOrderByQuery("order by b_num desc");
		List<HashMap<String,String>>club_list = exdao.selectQueryTable(selectdto);
		request.setAttribute("club_list", club_list);
		
		String board_a_num = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".board_a_num["+config.get("a_num")+"]")).trim();
		request.setAttribute("board_a_num", board_a_num);
	}

	/**
	 * @title : 동아리신청 게시물 삭제시 동아리전용게시판 분류 및 게시물 삭제
	 * @method : clubList
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		ExtendDAO exdao = (ExtendDAO) map.get("exdao");	//bean객체를 model로 받아 사용해야한다.
		MessageService messageService = (MessageService) map.get("messageService");	//bean객체 를 model로 받아 사용해야한다.
		HashMap<String, String>config = (HashMap<String, String>) request.getAttribute("config");	//게시판 설정값
		String a_num = exdao.filter(config.get("a_num"));
		
		if(!"Y".equals(config.get("a_garbage"))){	//휴지통기능 사용하지 않을때
			
			SelectDTO selectdto = new SelectDTO();
			selectdto.setExTableName("board");
			selectdto.setExColumn("b_temp4");
			String status = Func.nvl( request.getParameter("status") );
			if ("totdel".equals(status)) { //다중삭제
				String[] chk = request.getParameterValues("chk");
				for(int z=0;z <=chk.length-1;z++) {
					String b_num = chk[z].trim();
					
					//동아리신청게시물에서 동아리전용게시판 분류값 추출
					selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
					String b_cate = exdao.selectQueryColumn(selectdto);
					
					//동아리 전용게시판 게시물삭제
					String board_a_num = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".board_a_num["+config.get("a_num")+"]")).trim();
					exdao.executeQuery("delete from board where a_num = '"+exdao.filter(board_a_num)+"' and b_cate = '"+exdao.filter(b_cate)+"'");
					
					//동아리 전용게시판 게시판분류 삭제
					exdao.executeQuery("delete from board_code where ct_idx = '"+exdao.filter(b_cate)+"'");
				}
			}else{
				String b_num = exdao.filter(request.getParameter("b_num"));
				
				//동아리신청게시물에서 동아리전용게시판 분류값 추출
				selectdto.setExWhereQuery("where b_num = '"+b_num+"'");
				String b_cate = exdao.selectQueryColumn(selectdto);
				
				//동아리 전용게시판 게시물삭제
				String board_a_num = Func.nvl(CommonConfig.getBoard(config.get("a_level")+".board_a_num["+config.get("a_num")+"]")).trim();
				exdao.executeQuery("delete from board where a_num = '"+exdao.filter(board_a_num)+"' and b_cate = '"+exdao.filter(b_cate)+"'");
				
				//동아리 전용게시판 게시판분류 삭제
				exdao.executeQuery("delete from board_code where ct_idx = '"+exdao.filter(b_cate)+"'");
			}
		}
		
	}

}
