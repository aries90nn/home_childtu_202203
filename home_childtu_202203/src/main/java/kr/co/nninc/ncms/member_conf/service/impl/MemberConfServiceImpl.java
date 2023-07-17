package kr.co.nninc.ncms.member_conf.service.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.member_conf.service.MemberConfDTO;
import kr.co.nninc.ncms.member_conf.service.MemberConfService;

/**
 * 회원환경설정을 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.01.08
 * @version 1.1
 */
@Service("memberconfService")
public class MemberConfServiceImpl extends EgovAbstractServiceImpl implements MemberConfService {

	/** 동적dao */
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;
	
	/**
	 * @title : 회원환경설정 폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//select 전용 dto
		MemberConfDTO selectdto = new MemberConfDTO();
		selectdto.setExTableName("member_config");
		selectdto.setExKeyColumn("mc_idx");
		selectdto.setExColumn(new MemberConfDTO());
		HashMap<String,String>memberconf = exdao.selectQueryRecord(selectdto);

		// 파라미터 초기데이터 집어넣기
		if(memberconf.size() == 0){
			MemberConfDTO memberconfdto = new MemberConfDTO();
			try {
				Object obj = memberconfdto;
				for (Field field : obj.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if (!"serialVersionUID".equals(field.getName())
							&& !"mc_idx".equals(field.getName())
							&& !"mc_agree_str".equals(field.getName())
							&& !"mc_info_str".equals(field.getName())) {
						//초기화할값넣기
						field.set(obj, "N");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if("".equals(memberconfdto.getMc_joinlevel())) memberconfdto.setMc_joinlevel("0");
			request.setAttribute("memberconf", memberconfdto);
		}else{
			if("".equals(Func.nvl(memberconf.get("mc_joinlevel")))){
				memberconf.put("mc_joinlevel", "0");
			}
			request.setAttribute("memberconf", memberconf);
		}
		
		// 회원그룹리스트조회하기(사용인것만)
		selectdto = new MemberConfDTO();
		selectdto.setExTableName("member_group");
		selectdto.setExColumn("g_num, g_menuname");
		selectdto.setExWhereQuery("where g_chk = 'Y'");
		selectdto.setExOrderByQuery("order by g_code asc");
		List<HashMap<String,String>> membergroupList = exdao.selectQueryTable(selectdto);
		request.setAttribute("membergroupList", membergroupList);

	}

	/**
	 * @title : 회원환경설정 등록처리
	 * @method : writeOk
	 */
	@Override
	public void writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MemberConfDTO memberconfdto = (MemberConfDTO) map.get("memberconfdto");
		
		//비우기
		exdao.executeQuery("truncate table member_config");
		
		String mc_wdate = Func.date("Y-m-d H:i:s");
		memberconfdto.setMc_wdate(mc_wdate);
		if("".equals(Func.nvl(memberconfdto.getMc_new_point()))) { memberconfdto.setMc_new_point("0");}
		if("".equals(Func.nvl(memberconfdto.getMc_recom_point()))) { memberconfdto.setMc_recom_point("0");}

		memberconfdto.setExTableName("member_config");
		//insert 제외 필드
		memberconfdto.setMc_idx(null);
		exdao.insert(memberconfdto);
		
		//작업기록
		Func.writeManagerLog ("회원가입 환경설정 생성", request);
		
	}

	/**
	 * @title : 회원환경설정 수정처리
	 * @method : modifyOk
	 */
	@Override
	public void modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MemberConfDTO memberconfdto = (MemberConfDTO) map.get("memberconfdto");
		
		memberconfdto = (MemberConfDTO)Func.requestAll(memberconfdto);	//체크박스값이 null 일경우 공백으로 변환하자

		String mc_wdate = Func.date("Y-m-d H:i:s");
		memberconfdto.setMc_wdate(mc_wdate);
		if("".equals(Func.nvl(memberconfdto.getMc_new_point()))) { memberconfdto.setMc_new_point("0");}
		if("".equals(Func.nvl(memberconfdto.getMc_recom_point()))) { memberconfdto.setMc_recom_point("0");}

		String mc_idx = request.getParameter("mc_idx");
		memberconfdto.setExTableName("member_config");
		memberconfdto.setExWhereQuery("where mc_idx = '"+exdao.filter(mc_idx)+"'");
		
		//update 에서 제외할 필드
		memberconfdto.setMc_idx(null);
		exdao.update(memberconfdto);
		
		//작업기록
		Func.writeManagerLog ("회원가입 환경설정 수정", request);
	}
}
