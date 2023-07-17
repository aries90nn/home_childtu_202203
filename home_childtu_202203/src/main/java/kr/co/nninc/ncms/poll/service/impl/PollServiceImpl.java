package kr.co.nninc.ncms.poll.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.Paging;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.crypto.service.CryptoARIAService;
import kr.co.nninc.ncms.poll.service.PollDTO;
import kr.co.nninc.ncms.poll.service.PollService;
import kr.co.nninc.ncms.poll_question.service.PollQuestionDTO;


/**
 * 설문조사를 관리하기 위한 서비스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.11.01
 * @version 1.0
 */
@Service("pollService")
public class PollServiceImpl extends EgovAbstractServiceImpl implements PollService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/**ARIA*/
	@Resource(name="cryptoService")
	private CryptoARIAService cryptoService;
	
	
	/**
	 * @title : 설문조사수정 폼
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String po_idx = Func.nvl( request.getParameter("po_idx") ).trim();
		
		// 회원그룹리스트조회하기(사용인것만)
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_num, g_menuname");
		selectdto.setExWhereQuery("where g_chk = 'Y' and g_num != '1'");
		selectdto.setExOrderByQuery("order by g_code");
		List<HashMap<String,String>>membergroupList = exdao.selectQueryTable(selectdto);
		request.setAttribute("membergroupList", membergroupList);
		
		//수정폼
		selectdto = new SelectDTO();
		selectdto.setExTableName("poll");
		selectdto.setExKeyColumn("po_idx");
		selectdto.setExColumn(new PollDTO());
		selectdto.setExWhereQuery("where po_idx = '"+po_idx+"'");
		HashMap<String,String>polldto = exdao.selectQueryRecord(selectdto);
		
		//시작일자
		String po_sdate = polldto.get("po_sdate");
		String po_sdate_y = Func.date("Y");
		String po_sdate_m = Func.date("m");
		String po_sdate_d = Func.date("d");
		if(!"".equals(po_sdate)){
			String[] po_sdate_arr	= po_sdate.split("-");
			po_sdate_y 		= po_sdate_arr[0];
			po_sdate_m 	= po_sdate_arr[1];
			po_sdate_d 		= po_sdate_arr[2];
		}
		polldto.put("po_sdate_y",po_sdate_y);
		polldto.put("po_sdate_m",po_sdate_m);
		polldto.put("po_sdate_d",po_sdate_d);
		
		//종료일자
		String po_edate = polldto.get("po_edate");
		String po_edate_y = Func.date("Y");
		String po_edate_m = Func.addGetDate2(1, "m");
		String po_edate_d = Func.date("d");
		if(!"".equals(po_edate)){
			String[] po_edate_arr	= po_edate.split("-");
			po_edate_y 		= po_edate_arr[0];
			po_edate_m 	= po_edate_arr[1];
			po_edate_d 		= po_edate_arr[2];
		}
		polldto.put("po_edate_y",po_edate_y);
		polldto.put("po_edate_m",po_edate_m);
		polldto.put("po_edate_d",po_edate_d);
		request.setAttribute("poll", polldto);
		request.setAttribute("po_idx", polldto.get("po_idx"));
		
		//작업기록
		Func.writeManagerLog ("설문조사 상세보기 ["+polldto.get("po_subject")+"]", request);
	}

	/**
	 * @title : 설문조사등록처리
	 * @method : writeOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		PollDTO polldto = (PollDTO) map.get("polldto");

		//설문그룹
		String[] po_group = request.getParameterValues( "po_group" );
		String po_group_str = "[][1]";
		if(po_group != null){
			for(int z=0;z <= po_group.length-1;z++){
				if(po_group[z].equals("4")) { po_group_str += "[5]"; }
				po_group_str += "["+po_group[z]+"]";
			}
		}
		polldto.setPo_group(po_group_str);
		
		//결과확인그룹
		String[] po_addid = request.getParameterValues( "po_addid" );
		String po_addid_str = "[][1]";
		if(po_addid != null){
			for(int z=0;z <= po_addid.length-1;z++){
				if(po_addid[z].equals("4")) { po_addid_str += "[5]";}
				po_addid_str += "["+po_addid[z]+"]";
			}
		}
		polldto.setPo_addid(po_addid_str);
		
		//기간날짜 넣기
		String po_sdate_y = request.getParameter("po_sdate_y");
		String po_sdate_m = request.getParameter("po_sdate_m");
		String po_sdate_d = request.getParameter("po_sdate_d");
		String po_sdate = Func.dateSerial(Func.cInt(po_sdate_y), Func.cInt(po_sdate_m), Func.cInt(po_sdate_d));
		polldto.setPo_sdate(po_sdate);
		
		String po_edate_y = request.getParameter("po_edate_y");
		String po_edate_m = request.getParameter("po_edate_m");
		String po_edate_d = request.getParameter("po_edate_d");
		String po_edate = Func.dateSerial(Func.cInt(po_edate_y), Func.cInt(po_edate_m), Func.cInt(po_edate_d));
		polldto.setPo_edate(po_edate);

		//수정일자
		String po_mdate = Func.date("Y-m-d H:i:s");
		polldto.setPo_mdate(po_mdate);
		
		//수정제외 필드
		polldto.setPo_idx(null);
		polldto.setSite_dir(null);
		
		//update
		String po_idx = Func.nvl( request.getParameter("po_idx") ).trim();
		polldto.setExTableName("poll");
		polldto.setExWhereQuery("where po_idx = '"+po_idx+"'");
		exdao.update(polldto);
		
		//작업기록
		Func.writeManagerLog ("설문조사 수정 ["+polldto.getPo_subject()+"]", request);
	}

	/**
	 * @title : 설문조사등록 폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		
		// 회원그룹리스트조회하기(사용인것만)
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_num, g_menuname");
		String whereQuery = "where g_chk = 'Y' and g_num != '1' ";
		if(!"".equals(builder_dir)){
			whereQuery += " and (g_num in (1,2,3,4) or g_site_dir = '"+builder_dir+"')";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("order by g_code");
		List<HashMap<String,String>>membergroupList = exdao.selectQueryTable(selectdto);
		request.setAttribute("membergroupList", membergroupList);
		
		//초기화시키기
		HashMap<String,String>poll = new HashMap<String,String>();
		String nowyear = Func.date("Y");
		String nowmonth = Func.date("m");
		String nowday = Func.date("d");
		String edate = Func.dateSerial(Func.cInt(nowyear), Func.cInt(nowmonth)+1, Func.cInt(nowday));
		poll.put("po_chk","N");
		poll.put("po_sdate_y", nowyear);
		poll.put("po_sdate_m", nowmonth);
		poll.put("po_sdate_d", nowday);
		poll.put("po_edate_y", edate.substring(0,4));
		poll.put("po_edate_m", edate.substring(5,7));
		poll.put("po_edate_d", edate.substring(8,10));
		request.setAttribute("poll", poll);
		request.setAttribute("po_idx", Func.nvl(request.getParameter("po_idx")));
	}

	/**
	 * @title : 설문조사등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		PollDTO polldto = (PollDTO) map.get("polldto");

		SelectDTO selectdto = new SelectDTO();
		
		String po_pk = Func.get_idx_add();
		polldto.setPo_pk(po_pk);
		
		// 마지막 순서코드가져오기
		selectdto.setExTableName("poll");
		selectdto.setExColumn("max(po_code)");
		int po_code = Func.cInt( exdao.selectQueryColumn(selectdto) ) + 1;
		polldto.setPo_code(Func.cStr(po_code));
		
		//설문그룹
		String[] po_group = request.getParameterValues( "po_group" );
		String po_group_str = "[][1]";
		if(po_group != null){
			for(int z=0;z <= po_group.length-1;z++){
				if(po_group[z].equals("4")) { po_group_str += "[5]";}
				po_group_str += "["+po_group[z]+"]";
			}
		}
		polldto.setPo_group(po_group_str);
		
		//결과확인그룹
		String[] po_addid = request.getParameterValues( "po_addid" );
		String po_addid_str = "[][1]";
		if(po_addid != null){
			for(int z=0;z <= po_addid.length-1;z++){
				if(po_addid[z].equals("4")) { po_addid_str += "[5]";}
				po_addid_str += "["+po_addid[z]+"]";
			}
		}
		polldto.setPo_addid(po_addid_str);
		
		//기간날짜 넣기
		String po_sdate_y = request.getParameter("po_sdate_y");
		String po_sdate_m = request.getParameter("po_sdate_m");
		String po_sdate_d = request.getParameter("po_sdate_d");
		String po_sdate = Func.dateSerial(Func.cInt(po_sdate_y), Func.cInt(po_sdate_m), Func.cInt(po_sdate_d));
		
		String po_edate_y = request.getParameter("po_edate_y");
		String po_edate_m = request.getParameter("po_edate_m");
		String po_edate_d = request.getParameter("po_edate_d");
		String po_edate = Func.dateSerial(Func.cInt(po_edate_y), Func.cInt(po_edate_m), Func.cInt(po_edate_d));
		
		polldto.setPo_sdate(po_sdate);
		polldto.setPo_edate(po_edate);
		
		
		//설문조사등록
		String po_wdate = Func.date("Y-m-d H:i:s");
		String po_mdate = Func.date("Y-m-d H:i:s");
		polldto.setPo_wdate(po_wdate);
		polldto.setPo_mdate(po_mdate);
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			polldto.setSite_dir(builder_dir);
		}
		
		//insert 제외필드
		polldto.setPo_idx(null);
		
		//insert
		polldto.setExTableName("poll");
		exdao.insert(polldto);
		
		//작업기록
		Func.writeManagerLog ("설문조사 생성 ["+polldto.getPo_subject()+"]", request);
	}

	/**
	 * @title : 설문조사목록
	 * @method : list
	 */
	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		SelectDTO selectdto = new SelectDTO();
		
		String v_keyword = exdao.filter(request.getParameter("v_keyword"));
		String v_search = exdao.filter(request.getParameter("v_search"));
		request.setAttribute("v_keyword", v_keyword);
		request.setAttribute("v_search", v_search);
		
		// pageConf
		int v_page = Func.cInt(request.getParameter("v_page")) == 0 ? 1 : Func.cInt(request.getParameter("v_page"));
		int pagesize = 20;
		int pagePerBlock = 10;
		int recordcount = 0; // 전체레코드 수
		
		selectdto.setExTableName("poll");
		selectdto.setExKeyColumn("po_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new PollDTO());
		selectdto.setExRecordCount(pagesize);
		selectdto.setExPage(v_page);

		String whereQuery = "";
		if(!"".equals(v_keyword) && !"".equals(v_search)){
			whereQuery += " where "+v_search+" like '%"+v_keyword+"%' ";
		}
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if(!"".equals(builder_dir)){
			whereQuery += " and site_dir = '"+builder_dir+"'";
		}
		selectdto.setExWhereQuery(whereQuery);
		selectdto.setExOrderByQuery("ORDER BY po_idx DESC");
		List<HashMap<String,String>> pollList = exdao.selectQueryPage(selectdto);
		recordcount = Func.cInt( pollList.get(0).get("totalcount") ); // 전체레코드 수
		
		request.setAttribute("recordcount", recordcount);
		pollList.remove(0);	//총검색개수행(첫번째행)을 삭제
		// setAttribute
		request.setAttribute("pollList", pollList);
		
		
		int totalpage = (int)Math.ceil( ((recordcount-1)/pagesize)+1);		//'전체덩어리갯수
		
		//페이징문자열 생성
		Paging paging = new Paging();
		paging.pageKeyword = "v_page";	//페이지파라미터명
		paging.page = v_page;			//현재페이지
		paging.block = pagePerBlock;	//페이지링크 갯수
		paging.totalpage = totalpage;	//총페이지 갯수
		String querystring2 = paging.setQueryString(request, "v_search, v_keyword");
		
		String pagingtag = paging.execute(querystring2);
		request.setAttribute("pagingtag", pagingtag);
		request.setAttribute("v_page", v_page);
		request.setAttribute("totalpage", totalpage);
		//페이징문자열 생성 끝
		
	
		//현재경로 전송
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
		
		String status 		= Func.nvl( request.getParameter("status") ).trim(); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String po_idx		= Func.nvl( request.getParameter("po_idx") ).trim();  //단일 삭제 사용
		
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("poll");
		selectdto.setExColumn("po_pk, po_subject");
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				selectdto.setExWhereQuery("where po_idx = '"+Func.nvl(chk[z])+"'");
				HashMap<String,String>poll = exdao.selectQueryRecord(selectdto);
				String po_subject = Func.nvl( poll.get("po_subject") );
				String po_pk = Func.nvl( poll.get("po_pk") );
				
				//poll 삭제
				exdao.executeQuery("delete from poll where po_pk = '"+po_pk+"'");
				
				//poll_question 삭제
				exdao.executeQuery("delete from poll_question where po_pk = '"+po_pk+"'");
				
				//poll_result 삭제
				exdao.executeQuery("delete from poll_result where po_pk = '"+po_pk+"'");

				//작업기록
				Func.writeManagerLog ("설문조사 삭제 ["+po_subject+"]", request);
			}
		} else { //단일삭제
			selectdto.setExWhereQuery("where po_idx = '"+po_idx+"'");
			HashMap<String,String>poll = exdao.selectQueryRecord(selectdto);
			String po_subject = Func.nvl( poll.get("po_subject") );
			String po_pk = Func.nvl( poll.get("po_pk") );
			
			//poll 삭제
			exdao.executeQuery("delete from poll where po_pk = '"+po_pk+"'");
			
			//poll_question 삭제
			exdao.executeQuery("delete from poll_question where po_pk = '"+po_pk+"'");
			
			//poll_result 삭제
			exdao.executeQuery("delete from poll_result where po_pk = '"+po_pk+"'");

			//작업기록
			Func.writeManagerLog ("설문조사 삭제 ["+po_subject+"]", request);
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

		String status 			= Func.nvl( request.getParameter("status") ).trim(); //값:totdel (다중삭제시 사용)
		String tot_level_chk	= Func.nvl( request.getParameter("tot_level_chk") ).trim();
		String[] chk 			= request.getParameterValues("chk");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("poll");
		selectdto.setExColumn("po_subject");
		for (int z = 0; z <= chk.length - 1; z++) {
			String po_idx = chk[z];
			String po_chk = tot_level_chk;
			
			//사용여부변경
			exdao.executeQuery("UPDATE poll SET po_chk = '"+po_chk+"' WHERE po_idx = '"+po_idx+"'");
			
			selectdto.setExWhereQuery("where po_idx = '"+po_idx+"'");
			String po_subject = exdao.selectQueryColumn(selectdto);
			
			//작업기록
			String po_chk_str = "사용";
			if("N".equals(po_chk)) po_chk_str = "중지";
			Func.writeManagerLog ("설문조사 상태변경 ["+po_subject+"("+po_chk_str+")]", request);
		}
	}

	@Override
	public void conf(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void confOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @title : 설문조사결과목록
	 * @method : resultList
	 */
	@Override
	public void resultList(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		
		
		//설문조사키
		String po_pk = exdao.filter(request.getParameter("po_pk"));
		
		//문제목록
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("poll_question");
		selectdto.setExKeyColumn("poq_idx");	//고유컬럼 설정 필수
		selectdto.setExColumn(new PollQuestionDTO());
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		selectdto.setExOrderByQuery("order by poq_code");
		List<HashMap<String,String>>poqList = exdao.selectQueryTable(selectdto);
		request.setAttribute("poqList", poqList);
		
		//참가자목록
		selectdto = new SelectDTO();
		selectdto.setExTableName("poll_result");
		selectdto.setExColumn("por_mid, por_ip, por_ukey, por_name");
		selectdto.setExWhereQuery("where po_pk = '"+po_pk+"'");
		selectdto.setExGroupByQuery("group by por_mid, por_ip, por_ukey");
		List<HashMap<String,String>>userList = exdao.selectQueryTable(selectdto);
		
		//참가자목록에 결과 추가
		for(int i=0;i<=userList.size()-1;i++){
			HashMap<String,String>user = (HashMap<String,String>)userList.get(i);
			
			//이용자별 작성결과
			selectdto = new SelectDTO();
			selectdto.setExTableName("poll_result");
			selectdto.setExKeyColumn("por_idx");	//고유컬럼 설정 필수
			selectdto.setExColumn("por_result, poq_idx");
			selectdto.setExWhereQuery("where po_pk = '"+po_pk+"' and por_mid = '"+user.get("por_mid")+"' and por_ip = '"+user.get("por_ip")+"' and por_ukey = '"+user.get("por_ukey")+"'");
			List<HashMap<String,String>>resultList = exdao.selectQueryTable(selectdto);
			for(int j=0;j<=resultList.size()-1;j++){
				HashMap<String,String>result = (HashMap<String,String>)resultList.get(j);
				
				String poq_idx = Func.nvl( result.get("poq_idx") ).trim();
				String por_result = Func.nvl(result.get("por_result")).trim();
				
				//문제map에서 데이타 추출
				HashMap<String,String>poq = new HashMap<String,String>();
				for(int z=0;z<=poqList.size()-1;z++){
					if(poq_idx.equals( poqList.get(z).get("poq_idx") )){
						poq = poqList.get(z);
						break;
					}
				}
				
				String resultValue = "";
				if("1".equals(poq.get("poq_type"))){	//객관식
					resultValue = poq.get("poq_bogi"+por_result);
				}else{
					resultValue = por_result;
				}
				
				user.put(result.get("poq_idx"), resultValue);
			}
			// aria
			user.put("por_name", cryptoService.decryptData(user.get("por_name")));
		}
		request.setAttribute("userList", userList);
		
	}

}
