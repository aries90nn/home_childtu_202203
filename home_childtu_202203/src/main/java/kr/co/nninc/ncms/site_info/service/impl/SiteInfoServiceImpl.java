package kr.co.nninc.ncms.site_info.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.site_info.service.SiteConfigDTO;
import kr.co.nninc.ncms.site_info.service.SiteInfoService;

/**
 * 기본정보 설정을 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.09.27
 * @version 1.0
 */
@Service("siteInfoService")
public class SiteInfoServiceImpl extends EgovAbstractServiceImpl implements SiteInfoService {

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
	 * @title : 기본정보 설정 등록폼
	 * @method : write
	 */
	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		SelectDTO selectdto = new SelectDTO();
		
		selectdto.setExTableName("site_config");
		selectdto.setExKeyColumn("sc_idx");
		selectdto.setExColumn(new SiteConfigDTO());
		selectdto.setExOrderByQuery("order by sc_idx desc");
		
		HashMap<String,String>info = exdao.selectQueryRecord(selectdto);
		if("".equals(Func.nvl(info.get("sc_hdate1")))) {
			info.put("sc_hdate1_y",Func.date("Y"));
			info.put("sc_hdate1_m",Func.date("m"));
			info.put("sc_hdate1_d",Func.date("d"));
		}else{
			String[] arr_hdate1 = Func.nvl(info.get("sc_hdate1")).split("\\-");
			info.put("sc_hdate1_y",arr_hdate1[0]);
			info.put("sc_hdate1_m",arr_hdate1[1]);
			info.put("sc_hdate1_d",arr_hdate1[2]);
		}
		
		//
		if("".equals( Func.nvl(info.get("sc_hdate2")) )) {
			info.put("sc_hdate2_y", Func.addGetDate2(2, "yyyy"));
			info.put("sc_hdate2_m", Func.addGetDate2(2, "MM"));
			info.put("sc_hdate2_d", Func.addGetDate2(2, "dd"));
		}else {
			String[] arr_hdate2 = info.get("sc_hdate2").split("\\-");
			info.put("sc_hdate2_y",arr_hdate2[0]);
			info.put("sc_hdate2_m",arr_hdate2[1]);
			info.put("sc_hdate2_d",arr_hdate2[2]);
		}
		request.setAttribute("info", info);
	}

	/**
	 * @title : 기본정보 설정 등록 처리
	 * @method : writeOk
	 */
	@Override
	public String writeOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		SiteConfigDTO infodto = (SiteConfigDTO)map.get("infodto");
		
		//데이터비우기
		exdao.executeQuery("truncate table site_config");
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ================
		String uploaddir =  fileutil.realPath(request, "/data/info/");
		Func.folderCreate(uploaddir);
		//==========================================================
		
		// 파일 유효성 검사 및 저장
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "로고이미지", "sc_logo_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			infodto.setSc_logo(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		//등록하기
		String sc_wdate = Func.date("Y-m-d H:i:s");
		infodto.setSc_wdate(sc_wdate);
		infodto.setSc_zipcode( Func.nvl(request.getParameter("b_zip1")) );
		infodto.setSc_addr1( Func.nvl(request.getParameter("b_addr1")) );
		infodto.setSc_addr2( Func.nvl(request.getParameter("b_addr2")) );
		//css파일삭제
		String regex = "<link[^>]*href=[\"']?([^>\"']+)[\"']?[^>]*>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(infodto.getSc_copyright());
		String sc_copyright = "";
		while (matcher.find()) {
			sc_copyright += matcher.replaceAll("");
		}
		infodto.setSc_copyright(sc_copyright);
		
		//insert 제외 필드
		infodto.setSc_idx(null);
		
		//insert
		infodto.setExTableName("site_config");
		exdao.insert(infodto);
		
		return "redirect:/ncms/info/write.do";
	}

	
	/**
	 * @title : 기본정보 설정 수정 처리
	 * @method : modifyOk
	 */
	@Override
	public String modifyOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");
		SiteConfigDTO infodto = (SiteConfigDTO)map.get("infodto");
		
		String sc_idx = Func.nvl( request.getParameter("sc_idx") ).trim();
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ===============
		String uploaddir =  fileutil.realPath(request, "/data/info/");
		Func.folderCreate(uploaddir);
		//==============================================
		
		// 파일 유효성 검사 및 저장
		infodto.setSc_logo("");
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "로고이미지", "sc_logo_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			infodto.setSc_logo(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		String sc_logo2 = Func.nvl(request.getParameter("sc_logo2")).trim();
		String sc_logo_del = Func.nvl(request.getParameter("sc_logo_del")).trim();
		if(!"".equals(infodto.getSc_logo())){  //첨부파일이 있으면
			
			Func.fileDelete(uploaddir+"/"+sc_logo2);
		}else{
			infodto.setSc_logo(sc_logo2);

			if(!"".equals(sc_logo_del)){ //첨부파일만 삭제
				Func.fileDelete(uploaddir+"/"+sc_logo2);
				infodto.setSc_logo("");
			}
		}
		
		//수정하기
		String sc_wdate = Func.date("Y-m-d H:i:s");
		infodto.setSc_wdate(sc_wdate);
		infodto.setSc_zipcode( Func.nvl(request.getParameter("b_zip1")) );
		infodto.setSc_addr1( Func.nvl(request.getParameter("b_addr1")) );
		infodto.setSc_addr2( Func.nvl(request.getParameter("b_addr2")) );
		
		//css파일삭제
		String regex = "<link[^>]*href=[\"']?([^>\"']+)[\"']?[^>]*>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(infodto.getSc_copyright());
		String sc_copyright = "";
		while (matcher.find()) {
			sc_copyright += matcher.replaceAll("");
		}
		infodto.setSc_copyright(sc_copyright);
		
		//update 제외 필드
		infodto.setSc_idx(null);
		
		//update 처리
		infodto.setExTableName("site_config");
		infodto.setExWhereQuery("where sc_idx = '"+sc_idx+"'");
		exdao.update(infodto);
		
		return "redirect:/ncms/info/write.do";
	}

	/**
	 * @title : 관리자 인덱스페이지에 쓰임 
	 * @method : ncmsIndex
	 */
	@Override
	public void ncmsIndex(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//select 전용 dto
		SiteConfigDTO selectdto = new SiteConfigDTO();
		selectdto.setExTableName("site_config");
		selectdto.setExColumn("sc_hdate1, sc_hdate2, sc_hdd");
		
		HashMap<String,String>info = exdao.selectQueryRecord(selectdto);
		
		//호스팅 만료
		long remain_date = Func.diffOfDate(info.get("sc_hdate1"), info.get("sc_hdate2"), "yyyy-MM-DD");

		String str_date = info.get("sc_hdate1")+" ~ "+ info.get("sc_hdate2") +" ";
		request.setAttribute("str_date", str_date);
		request.setAttribute("str_date2", remain_date);
		
		//현재 용량
		try{
			String dir = CommonConfig.get("DIR")+CommonConfig.get("FOLDER");
			long now_hdd = FileUtils.sizeOfDirectory(new File(dir));
			
			DecimalFormat df = new DecimalFormat("#,###");
			request.setAttribute("str_hdd", df.format(now_hdd/1024/1024) + " MB");
		}catch(Exception e){
			request.setAttribute("str_hdd", "-");
		}
	}

	/**
	 * @title : 보안설정에서 IP접근제한,모바일기기접속 정보가져오기
	 * @method : siteConfigOk
	 */
	@Override
	public HashMap<String, Object> siteConfigOk(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String proc_type		= Func.nvl(request.getParameter("proc_type"));
		String field_name		= exdao.filter(request.getParameter("fname"));
		String field_val			= exdao.filter(request.getParameter("val"));
		String result_value = "N";
		String msg = "";
		boolean flag_proc = true;
		
		SelectDTO selectdto = new SelectDTO();
		
		if("check".equals(proc_type)) {
			selectdto.setExTableName("site_config");
			selectdto.setExColumn("sc_manager_ip, sc_manager_mobile");
			selectdto.setExOrderByQuery("order by sc_idx desc");
			HashMap<String,String>info = exdao.selectQueryRecord(selectdto);
			
			// pc 접속가능여부
			if("sc_manager_ip".equals(field_name)) {
				result_value = info.get("sc_manager_ip");
			}
			// 모바일 접속가능여부
			if("sc_manager_mobile".equals(field_name)) {
				result_value = info.get("sc_manager_mobile");
			}
		// 접근제한,모바일기기접속 수정하기
		}else if ("update".equals(proc_type) && !"".equals(field_val)) {
			if("sc_manager_ip".equals(field_name) && "Y".equals(field_val)) {
				selectdto = new SelectDTO();
				selectdto.setExTableName("manager_ip");
				selectdto.setExColumn("COUNT(*) AS CNT");
				selectdto.setExWhereQuery("WHERE mi_chk='Y'");
				int ipCount = Func.cInt( exdao.selectQueryColumn(selectdto) );
				
				if(ipCount < 1) {
					flag_proc = false;
				}
				
				//본인 아이피 등록된게 없으면 처리하지말기
				String nowdate = Func.date("Y-m-d");
				String nowip = Func.remoteIp(request);
				selectdto = new SelectDTO();
				selectdto.setExTableName("manager_ip");
				selectdto.setExColumn("count(*) as cnt");
				String where_query = "where mi_chk='Y'";
				where_query += " and mi_sdate <> '' AND mi_sdate is not null";
				where_query += " and mi_sdate <= '"+nowdate+"'";
				where_query += " and (mi_edate = '' or mi_edate is null or mi_edate >= '"+nowdate+"')";
				where_query += " and mi_ip = '"+Func.remoteIp(request)+"'";
				selectdto.setExWhereQuery(where_query);
				int cnt = Func.cInt( exdao.selectQueryColumn(selectdto) );
				if(cnt == 0){
					flag_proc = false;
					msg = "현재 접속중인 IP가 등록되어있지 않습니다. \n\nIP를 등록해주세요. (IP주소 : "+nowip+")";
				}
			}
			if (flag_proc) {
				exdao.executeQuery("UPDATE site_config SET sc_wdate = '"+Func.date("Y-m-d H:i:s")+"', "+field_name+" = '"+field_val+"'");
			}
		}
		
		
		HashMap<String, Object> hm = new HashMap();
		hm.put("result_value", result_value);
		hm.put("field_val", field_val);
		hm.put("flag_proc", flag_proc);
		hm.put("msg", msg);
		return hm;
	}

}
