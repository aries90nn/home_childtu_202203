package kr.co.nninc.ncms.poll.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;

import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.crypto.service.CryptoARIAService;
import kr.co.nninc.ncms.poll.service.PollDTO;
import kr.co.nninc.ncms.poll.service.PollQuestionDTO;
import kr.co.nninc.ncms.poll.service.PollResultDTO;
import kr.co.nninc.ncms.poll.service.UserPollService;


/**
 * 설문조사를 위한 서비스 구현 클래스(사용자)
 * @author 나눔
 * @since 2019.03.29
 * @version 1.1
 */
@Service("userPollService")
public class UserPollServiceImpl extends EgovAbstractServiceImpl implements UserPollService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**ARIA*/
	@Resource(name="cryptoService")
	private CryptoARIAService cryptoService;
	
	
	/**
	 * @title : 설문조사목록
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 10;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		
		PollDTO selectdto = new PollDTO();
		
		selectdto.setExTableName("poll");
		selectdto.setExKeyColumn("po_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new PollDTO());
		selectdto.setExRecordCount(pagesize);
		
		String where_query = "where po_chk = 'Y'";
		String v_search = Func.nvl( request.getParameter("v_search") ).trim();
		String v_keyword = Func.nvl( request.getParameter("v_keyword") ).trim();
		if(!"".equals(v_keyword) && !"".equals(v_search)){
			where_query += " and "+v_search+" like '%"+v_keyword+"%'";
		}
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			where_query += " and site_dir = '"+builder_dir+"'";
		}
		selectdto.setExWhereQuery(where_query);
		selectdto.setExOrderByQuery("ORDER BY po_wdate DESC");
		selectdto.setExPage(v_page);
		
		List<HashMap<String,String>> pollList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( pollList.get(0).get("totalcount") ); // 전체레코드 수
		request.setAttribute("recordcount", recordcount);
		pollList.remove(0);	//총검색개수행(첫번째행)을 삭제
		request.setAttribute("pollList", pollList);
		
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수
		
		
		
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = pagePerBlock;	//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring2 = paging.setQueryString(request, "v_search, v_keyword, v_gnum");
		
		String pagingtag = paging.execute(querystring2);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		//페이징문자열 생성 끝
		
		//현재페이지 URL
		Func.getNowPage(request);
		
		
		//설문조사 환경설정
		selectdto = new PollDTO();
		selectdto.setExTableName("poll_conf");
		selectdto.setExColumn("poc_idx, poc_topinclude, poc_tophtml, poc_btminclude, poc_btmhtml, poc_wdate");
		selectdto.setExRecordCount(1);
		selectdto.setExOrderByQuery("order by poc_idx desc");
		HashMap<String,String>pollconf = exdao.selectQueryRecord(selectdto);
		request.setAttribute("pollconf", pollconf);
		
	}

	/**
	 * @title : 설문조사 투표하기 폼
	 * @method : poll
	 */
	@Override
	public String poll(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String po_pk = Func.nvl( request.getParameter("po_pk") ).trim();
		
		SelectDTO selectdto = new SelectDTO();
		
		selectdto.setExTableName("poll_result");
		selectdto.setExColumn("count(*)");
		String por_mid = Func.getSession(request, "ss_m_id");
		if("".equals(por_mid)) por_mid = Func.getSession(request, "ss_m_dupinfo");
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"' and por_ip = '"+Func.remoteIp(request)+"' and por_mid = '"+por_mid+"'");
		int chk_count = Func.cInt(exdao.selectQueryColumn(selectdto));
		if(chk_count > 0) return messageService.backMsg(model, "이미 참가한 설문조사 입니다.");
		
		selectdto = new SelectDTO();
		selectdto.setExTableName("poll");
		selectdto.setExColumn("po_idx, po_subject, po_group, po_addid, po_sdate, po_edate, po_chk, po_code, po_pk, po_wdate, po_mdate, po_count");
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		HashMap<String,String>poll = exdao.selectQueryRecord(selectdto);
		
		//등급별 권한설정
		String g_num = "2";
		if(!"".equals(Func.getSession(request, "ss_m_name")) && !"".equals(Func.getSession(request, "ss_m_dupinfo")) && !"".equals(Func.getSession(request, "ss_m_coinfo"))) {
			g_num = "5";
		}
		String ss_g_num = Func.getSession(request, "ss_g_num");
		if(!"".equals(ss_g_num)) {
			g_num = ss_g_num;
		}
		System.out.println("po_group="+poll.get("po_group")+" | "+"["+g_num+"]");
		if(ss_g_num != "1" && !Func.nvl(poll.get("po_group")).contains("["+g_num+"]")) {
			return messageService.backMsg(model, "참가권한이 없습니다.");
		}
		
		if((ss_g_num != "1" && "".equals(Func.getSession(request, "ss_m_name")) && "".equals(Func.getSession(request, "ss_m_dupinfo")) && "".equals(Func.getSession(request, "ss_m_coinfo")))) {
			String NOWPAGE_ENCODE = Func.urlEncode( Func.getNowPage(request) );
			String url = "/site/member/login.do?prepage="+NOWPAGE_ENCODE;
			String url2 = "/site/member/ipin.do?prepage="+NOWPAGE_ENCODE;
			String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			if(!"".equals(BUILDER_DIR)){
				url = "/"+BUILDER_DIR+url;
				url2 = "/"+BUILDER_DIR+url2;
			}
			String prepage = request.getParameter("prepage");
			return messageService.confirmMsg(model, "본인인증을 해주셔야 설문 참여가 가능합니다.\\n확인 시 본인인증페이지로 이동합니다.", url2, prepage);
			//return messageService.backMsg(model, "로그인 또는 본인인증 후 참가가 가능합니다.");
		}
		
		//시작일자
		String po_sdate = Func.nvl( poll.get("po_sdate") );
		String po_sdate_y = Func.date("Y");
		String po_sdate_m = Func.date("m");
		String po_sdate_d = Func.date("d");
		if(!"".equals(po_sdate)){
			String[] po_sdate_arr	= po_sdate.split("-");
			po_sdate_y 		= po_sdate_arr[0];
			po_sdate_m 	= po_sdate_arr[1];
			po_sdate_d 		= po_sdate_arr[2];
		}
		request.setAttribute("po_sdate_y", po_sdate_y);
		request.setAttribute("po_sdate_m", po_sdate_m);
		request.setAttribute("po_sdate_d", po_sdate_d);
		//종료일자
		String po_edate = Func.nvl( poll.get("po_edate") );
		String po_edate_y = Func.date("Y");
		String po_edate_m = Func.addGetDate2(1, "m");
		String po_edate_d = Func.date("d");
		if(!"".equals(po_edate)){
			String[] po_edate_arr	= po_edate.split("-");
			po_edate_y 		= po_edate_arr[0];
			po_edate_m 	= po_edate_arr[1];
			po_edate_d 		= po_edate_arr[2];
		}
		request.setAttribute("po_edate_y", po_edate_y);
		request.setAttribute("po_edate_m", po_edate_m);
		request.setAttribute("po_edate_d", po_edate_d);
		request.setAttribute("poll", poll);
		
		//설문조사 환경설정
		//필요한가?
		
		
		//설문 항목
		selectdto = new SelectDTO();
		selectdto.setExTableName("poll_question");
		selectdto.setExKeyColumn("poq_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new PollQuestionDTO());
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		selectdto.setExOrderByQuery("ORDER BY poq_code ASC");
		List<HashMap<String,String>>questionList = exdao.selectQueryTable(selectdto);
		request.setAttribute("questionList", questionList);
		return "/site/poll/poll";
	}

	
	/**
	 * @title : 설문조사 투표하기
	 * @method : pollOk
	 */
	@Override
	public String pollOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpServletResponse response = (HttpServletResponse) map.get("response");
		PollDTO param = (PollDTO) map.get("polldto");
		
		String po_pk		= Func.nvl(request.getParameter("po_pk")).trim();
		String por_mid		= Func.nvl(request.getParameter("por_mid"));
		if("".equals(por_mid)) por_mid = Func.getSession(request, "ss_m_dupinfo");
		String por_name 	= Func.getSession(request, "ss_m_name");
		por_name = cryptoService.encryptData(por_name);
		String por_ip		= Func.remoteIp(request);
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		
		// 객관식 공란체크 2014-07-21
		for(int i=0; i<= chk.length-1; i++){
			String poq_type = Func.nvl(request.getParameter("poq_type_"+chk[i]));
			
			if (poq_type.equals("1")){
				String por_result[] = request.getParameterValues("q_"+chk[i]);
				boolean value_chk = false;
				if(por_result != null) {
					for(int j=0;j<=por_result.length-1;j++){
						if(!Func.nvl(por_result[j]).equals("")){
							value_chk = true;
							break;
						}
					}
				}
				if(!value_chk){
					return messageService.backMsg(model, "모든 객관식 문항에 체크해주세요.");
				}
			}
		}
		
		//투표했는지 체크
		if(Func.getCookie(request, "poll_ck"+po_pk).equals("")){
			//쿠키저장
			Func.setCookie(request, response, "poll_ck"+po_pk,"checked");
			
			String por_wdate = Func.date("Y-m-d H:i:s");
			
			//결과저장
			PollResultDTO pollresult = new PollResultDTO();
			pollresult.setPor_mid(por_mid);
			pollresult.setPor_wdate(por_wdate);
			pollresult.setPo_pk(po_pk);
			pollresult.setPor_ip(por_ip);
			pollresult.setExTableName("poll_result");
			pollresult.setPor_name(por_name);
			pollresult.setPor_ukey(Func.get_idx_add());	//이용자별 고유키
			
			for(int z=0;z <= chk.length-1;z++){
				String poq_multi = Func.nvl(request.getParameter("poq_multi_"+chk[z]));
				pollresult.setPoq_idx(chk[z]);

				if (poq_multi.equals("Y")){
					String[] por_result = request.getParameterValues("q_"+chk[z]);
					for(int q=0; q < por_result.length;q++){
						pollresult.setPor_result(por_result[q]);
						exdao.insert(pollresult);
					}
				}else{
					String por_result = Func.nvl(request.getParameter("q_"+chk[z]));
					pollresult.setPor_result(por_result);
					exdao.insert(pollresult);
				}
			}
			//참여자 추가
			exdao.executeQuery("UPDATE poll SET po_count = po_count + 1 Where po_pk = '"+po_pk+"'");
		}else{
			return messageService.backMsg(model, "이미 참가하셨습니다.");
		}
		String prepage = Func.nvl( request.getParameter("prepage") ).trim();
		if("".equals(prepage)){prepage = "list.do";}
		return messageService.redirectMsg(model, "설문조사에 참여해주셔서 감사합니다", prepage);
		//return "redirect:"+prepage;
	}

	/**
	 * @title : 설문조사 주관식내용보기
	 * @method : view
	 */
	@Override
	public void view(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String poq_idx	= Func.nvl(request.getParameter("poq_idx"));
		
		PollQuestionDTO selectdto = new PollQuestionDTO();
		
		//설문문항
		selectdto.setExTableName("poll_question");
		String s_fields = "poq_idx, po_pk, poq_code, poq_topmemo, poq_question, poq_type, poq_multi, poq_chk";
		for(int i=1;i<=10;i++){
			s_fields += ", poq_bogi"+i;
		}
		s_fields += ", poq_wdate, poq_mdate";
		selectdto.setExColumn(s_fields);
		selectdto.setExWhereQuery("where poq_idx = '"+poq_idx+"'");
		HashMap<String,String> question = exdao.selectQueryRecord(selectdto);
		request.setAttribute("question", question);
		
		//결과보기
		selectdto = new PollQuestionDTO();
		selectdto.setExTableName("poll_result");
		selectdto.setExKeyColumn("por_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn("por_idx, por_result, por_mid, por_wdate, po_pk, poq_idx, por_ip");
		selectdto.setExWhereQuery("where po_pk = '"+Func.cStr(question.get("po_pk"))+"' and poq_idx = '"+Func.cStr(question.get("poq_idx"))+"'");
		selectdto.setExOrderByQuery("order by por_wdate DESC");
		List<HashMap<String,String>>resultList = exdao.selectQueryTable(selectdto);
		request.setAttribute("resultList", resultList);

	}

	/**
	 * @title : 결과보기
	 * @method : result
	 */
	@Override
	public String result(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String po_pk = Func.nvl(request.getParameter("po_pk")).trim();
		
		PollDTO selectdto = new PollDTO();
		selectdto.setExTableName("poll");
		String s_fields = "po_idx, po_subject, po_group, po_addid, po_sdate, po_edate, po_chk, po_code, po_pk, po_wdate, po_mdate, po_count";
		selectdto.setExColumn(s_fields);
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		HashMap<String,String>poll = exdao.selectQueryRecord(selectdto);
		
		//등급별 권한설정
		String g_num = "2";
		String ss_g_num = Func.getSession(request, "ss_g_num");
		if(!"".equals(ss_g_num)) {
			g_num = ss_g_num;
		}
		if(!ss_g_num.equals("1") && !poll.get("po_addid").contains("["+g_num+"]")) {
			return messageService.backMsg(model, "결과확인권한이 없습니다.");
		}
		if((ss_g_num != "1" && "".equals(Func.getSession(request, "ss_m_name")) && "".equals(Func.getSession(request, "ss_m_dupinfo")) && "".equals(Func.getSession(request, "ss_m_coinfo")))) {
			String NOWPAGE_ENCODE = Func.urlEncode( Func.getNowPage(request) );
			String url = "/site/member/login.do?prepage="+NOWPAGE_ENCODE;
			String url2 = "/site/member/ipin.do?prepage="+NOWPAGE_ENCODE;
			String BUILDER_DIR = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
			if(!"".equals(BUILDER_DIR)){
				url = "/"+BUILDER_DIR+url;
				url2 = "/"+BUILDER_DIR+url2;
			}
			String prepage = request.getParameter("prepage");
			return messageService.confirmMsg(model, "본인인증을 해주셔야 설문 참여가 가능합니다.\\n확인 시 본인인증페이지로 이동합니다.", url2, prepage);
			//return messageService.backMsg(model, "로그인 또는 본인인증 후 참가가 가능합니다.");
		}
		
		//시작일자
		String po_sdate = poll.get("po_sdate");
		String po_sdate_y = Func.date("Y");
		String po_sdate_m = Func.date("m");
		String po_sdate_d = Func.date("d");
		if(!"".equals(po_sdate)){
			String[] po_sdate_arr	= po_sdate.split("-");
			po_sdate_y 		= po_sdate_arr[0];
			po_sdate_m 	= po_sdate_arr[1];
			po_sdate_d 		= po_sdate_arr[2];
		}
		request.setAttribute("po_sdate_y", po_sdate_y);
		request.setAttribute("po_sdate_m", po_sdate_m);
		request.setAttribute("po_sdate_d", po_sdate_d);
		//종료일자
		String po_edate = poll.get("po_edate");
		String po_edate_y = Func.date("Y");
		String po_edate_m = Func.addGetDate2(1, "m");
		String po_edate_d = Func.date("d");
		if(!"".equals(po_edate)){
			String[] po_edate_arr	= po_edate.split("-");
			po_edate_y 		= po_edate_arr[0];
			po_edate_m 	= po_edate_arr[1];
			po_edate_d 		= po_edate_arr[2];
		}
		request.setAttribute("po_edate_y", po_edate_y);
		request.setAttribute("po_edate_m", po_edate_m);
		request.setAttribute("po_edate_d", po_edate_d);
		request.setAttribute("poll", poll);
		
		//설문 항목
		selectdto = new PollDTO();
		selectdto.setExTableName("poll_question");
		selectdto.setExKeyColumn("poq_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new PollQuestionDTO());
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"' and poq_chk = 'Y'");
		selectdto.setExOrderByQuery("order by poq_code ASC");
		List<HashMap<String,Object>>questionList = exdao.selectQueryTableObject(selectdto);
		for(int i=0; i<questionList.size(); i++) {
			HashMap<String,Object>question = (HashMap<String,Object>)questionList.get(i);
			
			//문제 참여자수
			selectdto = new PollDTO();
			selectdto.setExTableName("poll_result");
			selectdto.setExColumn("count(*) as cnt");
			selectdto.setExWhereQuery("where po_pk = '"+po_pk+"' and poq_idx = '"+Func.cStr(question.get("poq_idx"))+"'");
			int por_totcnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
			question.put("por_totcnt", Func.cStr(por_totcnt));
			
			HashMap<String, String> cntMap = new HashMap<String, String>();
			for (int z=1; z<=10; z++) {
				
				String poq_bogi = Func.cStr( question.get("poq_bogi"+z) );
				if(!"".equals(poq_bogi)) {
					//보기별 참여자수
					selectdto = new PollDTO();
					selectdto.setExTableName("poll_result");
					selectdto.setExColumn("count(*) as cnt");
					String where_query = "where po_pk = '"+po_pk+"' and poq_idx = '"+Func.cStr(question.get("poq_idx"))+"'";
					where_query += " and por_result = '"+z+"'";
					selectdto.setExWhereQuery(where_query);
					int por_cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
					cntMap.put("por_cnt"+z, Func.cStr(por_cnt));
					
					//선택한보기 백분율
					String v_percent = "0";
					if(por_totcnt != 0 && por_cnt != 0){ //참가인원이 있으면
						v_percent		= Func.formatDecimal( por_cnt / (por_totcnt*1.0) * 100, 1 ); //선택한보기 백분율
					}
					String v_percent_str	 = "1";
					if(!v_percent.equals("0")){
						v_percent_str	 = v_percent;
					}
					cntMap.put("v_percent"+z, v_percent);
					cntMap.put("v_percent_str"+z, v_percent_str);
				}
			}
			question.put("por_mp", cntMap);
		}
		request.setAttribute("questionList", questionList);
		
		
		//설문조사 환경설정
		selectdto = new PollDTO();
		selectdto.setExTableName("poll_conf");
		selectdto.setExColumn("poc_idx, poc_topinclude, poc_tophtml, poc_btminclude, poc_btmhtml, poc_wdate");
		selectdto.setExRecordCount(1);
		selectdto.setExOrderByQuery("order by poc_idx desc");
		HashMap<String,String>pollconf = exdao.selectQueryRecord(selectdto);
		request.setAttribute("pollconf", pollconf);
		
		return "/site/poll/result";
	}

}
