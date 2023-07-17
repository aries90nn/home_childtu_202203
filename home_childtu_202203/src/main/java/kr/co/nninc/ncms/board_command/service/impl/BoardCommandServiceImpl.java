package kr.co.nninc.ncms.board_command.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board_command.service.BoardCommandDTO;
import kr.co.nninc.ncms.board_command.service.BoardCommandService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;

/**
 * 댓글을 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.01.16
 * @version 1.1
 */
@Service("boardCommandService")
public class BoardCommandServiceImpl  extends EgovAbstractServiceImpl implements BoardCommandService {

	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	
	/**
	 * @title : 댓글 등록
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		BoardCommandDTO command	= (BoardCommandDTO) map.get("boardcommanddto"); //게시판댓글
		
		String a_num = exdao.filter(request.getParameter("a_num"));
		String ss_m_id					= (String) Func.getSession(request, "ss_m_id");
		String ss_m_name				= (String) Func.getSession(request, "ss_m_name");
		String ss_m_pwd					= (String) Func.getSession(request, "ss_m_pwd");
		
		String prepage = Func.nvl( request.getParameter("prepage") );
		if("".equals(prepage)){prepage = "?proc_type=view&b_num="+Func.nvl(request.getParameter("b_num"));}
		
		//select전용 dto
		BoardCommandDTO selectdto = new BoardCommandDTO();
		
		//게시판 정보 조회
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_tablename");
		selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
		String a_tablename = exdao.selectQueryColumn(selectdto);
		
		
		String c_name = command.getC_name();
		String c_pwd = command.getC_pwd();
		if("".equals(ss_m_id)) {
			//자동가입방지 체크
			//*****************************************
			String autoimg_str		= Func.nvl( request.getParameter("autoimg_str") );
			String autoimg_chk	= "";
			String c_codechk		= Func.nvl( request.getParameter("c_codechk") );
			
			switch ( Func.cInt(autoimg_str) ){
				case 1  : autoimg_chk = "DF4PH3"; break;
				case 2  : autoimg_chk = "HS42YP"; break;
				case 3  : autoimg_chk = "S8VP3X"; break;
				case 4  : autoimg_chk = "N4CW2U"; break;
				case 5  : autoimg_chk = "9SK6GR"; break;
			}
			if( !autoimg_chk.equals( c_codechk.toUpperCase() ) ){
				return messageService.backMsg(model, "자동가입방지입력값이 동일하지 않습니다.");
			}
			//*****************************************
		}else{
			c_name = ss_m_name;
			c_pwd = ss_m_pwd;
		}
		String c_bnum			= Func.nvl(request.getParameter("b_num"));
		String c_id				= ss_m_id;
		String c_content		= Func.nvl(request.getParameter("c_content"));
		String c_regdate		= Func.date("Y-m-d H:i:s");
		
		if ("".equals(c_name) || "".equals(c_pwd) || "".equals(c_content)|| "".equals(c_bnum)){
			return messageService.backMsg(model, "비밀번호, 작성자명, 내용을 바르게 입력하세요.");
		}
		c_pwd = Func.hash(c_pwd);
		
		//댓글등록
		command.setC_bnum(c_bnum);
		command.setC_id(c_id);
		command.setC_pwd(c_pwd);
		command.setC_name(c_name);
		command.setC_content(c_content);
		command.setC_regdate(c_regdate);
		
		//insert 예외처리
		command.setC_num(null);
		
		command.setExTableName("board_command");
		exdao.insert(command);
		
		//댓글수 추가
		String sql = "UPDATE board SET b_c_count = b_c_count + 1 Where b_num = '"+c_bnum+"'";
		exdao.executeQuery(sql);
		
		return "redirect:"+prepage;
	}

	/**
	 * @title : 댓글삭제
	 * @method : deleteOk
	 */
	@Override
	public String deleteOk(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		
		String a_num = exdao.filter(request.getParameter("a_num"));
		String is_ad_cms					= (String) request.getAttribute("is_ad_cms"); //관리권한
		String c_bnum						= Func.nvl(request.getParameter("b_num"));
		String c_num						= Func.nvl(request.getParameter("c_num"));
		
		String ss_m_id						= Func.getSession(request, "ss_m_id");
		
		String prepage = Func.nvl( request.getParameter("prepage") );
		if("".equals(prepage)){prepage = "?proc_type=view&b_num="+Func.nvl(request.getParameter("b_num"));}
		
		//select전용 dto
		BoardCommandDTO selectdto = new BoardCommandDTO();
		
		//게시판 정보 조회
		selectdto.setExTableName("board_config");
		selectdto.setExColumn("a_tablename");
		selectdto.setExWhereQuery("where a_num = '"+a_num+"'");
		String a_tablename = exdao.selectQueryColumn(selectdto);
		
		
		//댓글 정보 조회
		selectdto.setExTableName("board_command");
		selectdto.setExColumn("c_id, c_pwd");
		selectdto.setExWhereQuery("where c_num = '"+c_num+"'");
		HashMap<String,String>rs_cd = exdao.selectQueryRecord(selectdto);
		
		String is_c_id = rs_cd.get("c_id");
		String is_c_pwd = rs_cd.get("c_pwd"); //삭제할 글의 비번..
		
		String c_pwd = Func.hash( Func.nvl(request.getParameter("c_pwd")) );
		
		if (c_pwd.equals(is_c_pwd) || "Y".equals(is_ad_cms) || (!"".equals(ss_m_id) && (is_c_id.equals(ss_m_id)))) { //비번이 일치, 관리자, 글쓴본인
			//댓글 삭제
			exdao.executeQuery( "DELETE FROM board_command WHERE c_num='"+c_num+"'" );
			
			//댓글수 감소
			exdao.executeQuery( "UPDATE board SET b_c_count = b_c_count - 1 Where b_num = '"+c_bnum+"'" );
			
		}else{
			return messageService.backMsg(model, "비밀번호가 일치하지 않습니다.");
		}
		
		
		return "redirect:"+prepage;
	}

}
