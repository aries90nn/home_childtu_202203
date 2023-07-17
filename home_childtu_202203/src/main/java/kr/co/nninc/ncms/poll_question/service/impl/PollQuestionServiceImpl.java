package kr.co.nninc.ncms.poll_question.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.poll.service.PollDTO;
import kr.co.nninc.ncms.poll_question.service.PollQuestionDTO;
import kr.co.nninc.ncms.poll_question.service.PollQuestionService;


/**
 * 설문페이지를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.09.27
 * @version 1.0
 */
@Service("pollQuestionService")
public class PollQuestionServiceImpl extends EgovAbstractServiceImpl implements PollQuestionService {

	
	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**
	 * @title : 설문페이지 등록폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String po_pk = Func.nvl( request.getParameter("po_pk") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		
		//설문조회하기
		selectdto.setExTableName("poll");
		selectdto.setExKeyColumn("po_idx");
		selectdto.setExColumn(new PollDTO());
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		HashMap<String,String>poll = exdao.selectQueryRecord(selectdto);
		request.setAttribute("poll", poll);
		
		//설문항목 조회
		selectdto.setExTableName("poll_question");
		selectdto.setExKeyColumn("poq_idx");
		selectdto.setExColumn(new PollQuestionDTO());
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		selectdto.setExOrderByQuery("order by poq_code ASC");
		List<HashMap<String,String>>questionList = exdao.selectQueryTable(selectdto);
		request.setAttribute("questionList", questionList);
		
		//현제 URL
		Func.getNowPage(request);
	}

	/**
	 * @title : 설문페이지 등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		PollQuestionDTO pollquestion = (PollQuestionDTO) map.get("pollquestiondto");
		
		String po_pk = Func.nvl( request.getParameter("po_pk") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		// 마지막 순서코드가져오기
		selectdto.setExTableName("poll_question");
		selectdto.setExColumn("max(poq_code)");
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		String poq_code = exdao.selectQueryColumn(selectdto);
		if(poq_code == null){poq_code = "0";}
		poq_code = Func.cStr( Func.cInt(poq_code) + 1 );
		pollquestion.setPoq_code(Func.cStr(poq_code));
		
		// 설문페이지등록
		String poq_wdate = Func.date("Y-m-d H:i:s");
		String poq_mdate = Func.date("Y-m-d H:i:s");
		pollquestion.setPoq_wdate(poq_wdate);
		pollquestion.setPoq_mdate(poq_mdate);
		pollquestion.setPoq_chk("Y");
		
		//insert 제외필드
		pollquestion.setPoq_idx(null);
		
		//insert
		pollquestion.setExTableName("poll_question");
		exdao.insert(pollquestion);
		
		
		//설문조사명
		selectdto = new SelectDTO();
		selectdto.setExTableName("poll");
		selectdto.setExColumn("po_subject");
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		String po_subject = exdao.selectQueryColumn(selectdto);
		
		//작업기록
		Func.writeManagerLog ("설문조사 문항 등록 ["+po_subject+"]", request);
	}

	/**
	 * @title : 수정하기
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		PollQuestionDTO pollquestion = (PollQuestionDTO) map.get("pollquestiondto");
		
		String po_pk = Func.nvl( request.getParameter("po_pk") ).trim();
		String poq_idx = Func.nvl( request.getParameter("poq_idx") ).trim();
		
		//설문조회하기
		String poq_topmemo			= Func.nvl(request.getParameter("poq_topmemo_"+poq_idx));
		String poq_question			= Func.nvl(request.getParameter("poq_question_"+poq_idx));
		String poq_type				= Func.nvl(request.getParameter("poq_type_"+poq_idx));
		String poq_multi			= Func.nvl(request.getParameter("poq_multi_"+poq_idx));
		String poq_bogi1			= Func.nvl(request.getParameter("poq_bogi1_"+poq_idx));
		String poq_bogi2			= Func.nvl(request.getParameter("poq_bogi2_"+poq_idx));
		String poq_bogi3			= Func.nvl(request.getParameter("poq_bogi3_"+poq_idx));
		String poq_bogi4			= Func.nvl(request.getParameter("poq_bogi4_"+poq_idx));
		String poq_bogi5			= Func.nvl(request.getParameter("poq_bogi5_"+poq_idx));
		String poq_bogi6			= Func.nvl(request.getParameter("poq_bogi6_"+poq_idx));
		String poq_bogi7			= Func.nvl(request.getParameter("poq_bogi7_"+poq_idx));
		String poq_bogi8			= Func.nvl(request.getParameter("poq_bogi8_"+poq_idx));
		String poq_bogi9			= Func.nvl(request.getParameter("poq_bogi9_"+poq_idx));
		String poq_bogi10			= Func.nvl(request.getParameter("poq_bogi10_"+poq_idx));
		
		//설문페이지수정
		String poq_mdate = Func.date("Y-m-d H:i:s");
		pollquestion.setPoq_mdate(poq_mdate);
		pollquestion.setPoq_topmemo(poq_topmemo);
		pollquestion.setPoq_question(poq_question);
		pollquestion.setPoq_type(poq_type);
		pollquestion.setPoq_multi(poq_multi);
		pollquestion.setPoq_bogi1(poq_bogi1);
		pollquestion.setPoq_bogi2(poq_bogi2);
		pollquestion.setPoq_bogi3(poq_bogi3);
		pollquestion.setPoq_bogi4(poq_bogi4);
		pollquestion.setPoq_bogi5(poq_bogi5);
		pollquestion.setPoq_bogi6(poq_bogi6);
		pollquestion.setPoq_bogi7(poq_bogi7);
		pollquestion.setPoq_bogi8(poq_bogi8);
		pollquestion.setPoq_bogi9(poq_bogi9);
		pollquestion.setPoq_bogi10(poq_bogi10);
		
		//update 제외필드
		pollquestion.setPoq_idx(null);
		
		//update
		pollquestion.setExTableName("poll_question");
		pollquestion.setExWhereQuery("where poq_idx = '"+poq_idx+"'");
		exdao.update(pollquestion);
		
		
		//설문조사명 조회
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("poll");
		selectdto.setExColumn("po_subject");
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		String po_subject = exdao.selectQueryColumn(selectdto);
		
		//작업기록
		Func.writeManagerLog ("설문조사 문항 수정 ["+po_subject+"]", request);
	}

	/**
	 * @title : 삭제
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 		= Func.nvl( request.getParameter("status") ).trim(); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String poq_idx		= Func.nvl( request.getParameter("poq_idx") ).trim();  //단일 삭제 사용
		String po_pk		= Func.nvl( request.getParameter("po_pk") ).trim();
		String poq_mdate = Func.date("Y-m-d H:i:s");
		
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				exdao.executeQuery("DELETE FROM poll_question where poq_idx='"+exdao.filter(chk[z])+"'");
			}
			
		} else { //단일삭제
			exdao.executeQuery("DELETE FROM poll_question where poq_idx='"+poq_idx+"'");
		}
		
		//설문조사명 조회
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("poll");
		selectdto.setExColumn("po_subject");
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		String po_subject = exdao.selectQueryColumn(selectdto);
		
		//작업기록
		Func.writeManagerLog ("설문조사 문항 삭제 ["+po_subject+"]", request);
	}

	/**
	 * @title : 순서변경폼
	 * @method : listMove
	 */
	@Override
	public void listMove(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String po_pk = Func.nvl( request.getParameter("po_pk") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		//설문문항
		selectdto.setExTableName("poll_question");
		selectdto.setExColumn("po_pk, poq_question, poq_idx");
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		selectdto.setExOrderByQuery("ORDER BY poq_code ASC");
		List<HashMap<String,String>>questionList = exdao.selectQueryTable(selectdto);
		request.setAttribute("questionList", questionList);
		
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
			String poq_idx = chk[z];
			//순서수정
			exdao.executeQuery("UPDATE poll_question SET poq_code = '"+Func.cStr(z + 1)+"' WHERE poq_idx = '"+poq_idx+"'");
		}
	}

	/**
	 * @title : 순서변경처리
	 * @method : listMoveOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String tot_level_chk	= Func.nvl( request.getParameter("tot_level_chk") ).trim();
		String[] chk 			= request.getParameterValues("chk");
		String po_pk			= Func.nvl( request.getParameter("po_pk") ).trim();
		
		for (int z = 0; z <= chk.length - 1; z++) {
			String poq_idx = exdao.filter( chk[z] );
			String poq_chk = exdao.filter( tot_level_chk );
			//
			exdao.executeQuery("UPDATE poll_question SET poq_chk = '"+poq_chk+"' WHERE poq_idx = '"+poq_idx+"'");
		}
		
		//설문조사명 조회
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("poll");
		selectdto.setExColumn("po_subject");
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		String po_subject = exdao.selectQueryColumn(selectdto);
		
		//작업기록
		String poq_chk_str = "사용";
		if("N".equals(tot_level_chk)) poq_chk_str = "중지";
		Func.writeManagerLog ("설문조사 문항 상태변경 ["+po_subject+"("+poq_chk_str+")]", request);
	}

}
