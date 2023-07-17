package kr.co.nninc.ncms.manager_ip.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.manager_ip.service.ManagerIPDTO;
import kr.co.nninc.ncms.manager_ip.service.ManagerIPService;


/**
 * IP를 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.10.17
 * @version 1.0
 */
@Service("managerIPService")
public class ManagerIPServiceImpl extends EgovAbstractServiceImpl implements ManagerIPService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	
	/**
	 * @title : IP 리스트
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("manager_ip");
		selectdto.setExColumn("mi_num, mi_ip, mi_memo, mi_chk, mi_regdate, mi_modidate, mi_sdate, mi_edate");
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){builder_dir = "ncms";}
		selectdto.setExWhereQuery("where site_dir = '"+builder_dir+"'");
		selectdto.setExOrderByQuery("ORDER BY mi_num ASC");
		List<HashMap<String,String>>ipList = exdao.selectQueryTable(selectdto);
		request.setAttribute("ipList", ipList);
		
		selectdto = new SelectDTO();
		selectdto.setExTableName("manager_ip");
		selectdto.setExColumn("count(*) as cnt");
		selectdto.setExWhereQuery("WHERE mi_chk='Y'");
		String IpCount = exdao.selectQueryColumn(selectdto);
		request.setAttribute("IpCount", IpCount);

	}

	/**
	 * @title : IP 등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		ManagerIPDTO ipdto = (ManagerIPDTO) map.get("ipdto");
		
		String mi_regdate = Func.date("Y-m-d H:i:s");
		String mi_modidate = Func.date("Y-m-d H:i:s");
		ipdto.setMi_regdate(mi_regdate);
		ipdto.setMi_modidate(mi_modidate);
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") ).trim();
		if("".equals(builder_dir)){builder_dir = "ncms";}
		ipdto.setSite_dir(builder_dir);
		
		//insert 제외필드
		ipdto.setMi_num(null);
		
		//insert
		ipdto.setExTableName("manager_ip");
		exdao.insert(ipdto);
		
		//작업기록
		Func.writeManagerLog ("관리자페이지 접근IP 추가 ["+ipdto.getMi_ip()+"]", request);
	}

	/**
	 * @title : IP 수정처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		ManagerIPDTO ipdto = (ManagerIPDTO) map.get("ipdto");

		String mi_num = exdao.filter( request.getParameter("mi_num") );
		
		String mi_modidate = Func.date("Y-m-d H:i:s");
		ipdto.setMi_modidate(mi_modidate);
		
		//update 제외필드
		ipdto.setMi_num(null);
		ipdto.setSite_dir(null);
		
		//update
		ipdto.setExTableName("manager_ip");
		ipdto.setExWhereQuery("where mi_num = '"+mi_num+"'");
		exdao.update(ipdto);
		
		//작업기록
		Func.writeManagerLog ("관리자페이지 접근IP 수정 ["+ipdto.getMi_ip()+"]", request);
	}

	/**
	 * @title : IP 삭제처리
	 * @method : deleteOk
	 */
	@Override
	public void deleteOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String status 		= exdao.filter( request.getParameter("status") ); //값:totdel (다중삭제시 사용)
		String[] chk 		= request.getParameterValues("chk"); //선택 체크 값
		String mi_num		= exdao.filter( request.getParameter("mi_num") );  //단일 삭제 사용
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("manager_ip");
		selectdto.setExColumn("mi_ip");
		if (status.equals("totdel")) { //다중삭제
			for(int z=0;z <= chk.length-1;z++){
				//쿼리조회
				selectdto.setExWhereQuery("where mi_num='"+chk[z]+"'");
				String mi_ip = exdao.selectQueryColumn(selectdto);
				
				exdao.executeQuery("DELETE FROM manager_ip WHERE mi_num = '"+chk[z]+"'");

				//작업기록
				Func.writeManagerLog ("관리자페이지 접근IP 삭제 ["+mi_ip+"]", request);
			}
		} else { //단일삭제
			//쿼리조회
			selectdto.setExWhereQuery("where mi_num='"+mi_num+"'");
			String mi_ip = exdao.selectQueryColumn(selectdto);
			
			exdao.executeQuery("DELETE FROM manager_ip WHERE mi_num = '"+mi_num+"'");

			//작업기록
			Func.writeManagerLog ("관리자페이지 접근IP 삭제 ["+mi_ip+"]", request);
		}
		
	}

	/**
	 * @title : IP 사용여부변경
	 * @method : levelOk
	 */
	@Override
	public void levelOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String tot_level_chk	= exdao.filter( request.getParameter("tot_level_chk") );
		String[] chk			= request.getParameterValues("chk"); // 선택 체크 값
		
		SelectDTO selectdto = new SelectDTO();
		selectdto.setExTableName("manager_ip");
		selectdto.setExColumn("mi_ip");
		for (int z = 0; z <= chk.length-1; z++) {
			String mi_num = exdao.filter( chk[z] );
			String mi_chk = tot_level_chk;
			//
			exdao.executeQuery("UPDATE manager_ip SET mi_chk = '"+mi_chk+"' WHERE mi_num = '"+mi_num+"'");
			
			//쿼리조회
			selectdto.setExWhereQuery("where mi_num='"+Func.cInt(mi_num)+"'");
			String mi_ip = exdao.selectQueryColumn(selectdto);
			//작업기록
			String mi_chk_str = "사용";
			if("N".equals(mi_chk)) mi_chk_str = "중지";
			Func.writeManagerLog ("관리자페이지 접근IP 상태변경 ["+mi_ip+"("+mi_chk_str+")]", request);
		}
		
	}

}
