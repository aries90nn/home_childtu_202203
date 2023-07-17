package kr.co.nninc.ncms.builder.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.builder.service.BuilderInfoService;
import kr.co.nninc.ncms.builder.service.BuilderSiteDTO;
import kr.co.nninc.ncms.common.FileDTO;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.common.service.SelectDTO;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;


/**
 * 기본정보 설정을 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2019.04.30
 * @version 1.0
 */
@Service("builderInfoService")
public class BuilderInfoServiceImpl extends EgovAbstractServiceImpl implements BuilderInfoService {


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
	 * @title : 사이트기본정보 설정 수정폼
	 * @method : modify
	 */
	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		//메인타입 조회
		ArrayList<HashMap<String,String>> main_list = new ArrayList<HashMap<String,String>>();
		String filePath = fileutil.realPath(request, "/nanum/site/builder/main");
		String[] dir = Func.openDir( filePath );
		for(int i=0;i<=dir.length-1;i++){
			String main_name = Func.fileGetContents( filePath+"/"+dir[i]+"/name.txt" );
			if(main_name == null){main_name = dir[i];}
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("type", dir[i]);
			hm.put("name", main_name);
			main_list.add(hm);
		}
		request.setAttribute("main_list", main_list);
		
		//스킨타입 조회
		ArrayList<HashMap<String,String>> skin_list = new ArrayList<HashMap<String,String>>();
		String filePath2 = fileutil.realPath(request, "/nanum/site/builder/skin");
		String[] dir2 = Func.openDir( filePath2 );
		for(int i=0;i<=dir2.length-1;i++){
			String skin_name = Func.fileGetContents( filePath2+"/"+dir2[i]+"/name.txt" );
			if(skin_name == null){skin_name = dir2[i];}
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("type", dir2[i]);
			hm.put("name", skin_name);
			skin_list.add(hm);
		}
		request.setAttribute("skin_list", skin_list);
		
		
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
		BuilderSiteDTO infodto = (BuilderSiteDTO)map.get("infodto");
		
		String bs_num = exdao.filter( request.getParameter("bs_num") );
		
		//======= 저장공간인 [DATA]폴더가 없다면 생성 한다. ===============
		String uploaddir =  fileutil.realPath(request, "/data/builder/");
		Func.folderCreate(uploaddir);
		//==============================================
		
		// 파일 유효성 검사 및 저장
		infodto.setBs_logo("");
		FileDTO file = fileutil.wfile(model, multi.getFileMap(), uploaddir, false, "로고이미지", "bs_logo_file", 5 * 1024 * 1024, fileutil.FILE_TYPE_IMAGE);
		if(!"".equals(Func.nvl(file.getFile_name()))) {
			infodto.setBs_logo(Func.nvl(file.getFile_name()));
		}else if(!"".equals(Func.nvl(file.getError_msg()))){
			return messageService.backMsg(model, file.getError_msg());
		}
		
		String bs_logo2 = Func.nvl(request.getParameter("bs_logo2")).trim();
		String bs_logo_del = Func.nvl(request.getParameter("bs_logo_del")).trim();
		if(!"".equals(infodto.getBs_logo())){  //첨부파일이 있으면
			
			Func.fileDelete(uploaddir+"/"+bs_logo2);
		}else{
			infodto.setBs_logo(bs_logo2);

			if(!"".equals(bs_logo_del)){ //첨부파일만 삭제
				Func.fileDelete(uploaddir+"/"+bs_logo2);
				infodto.setBs_logo("");
			}
		}
		
		//수정하기
		String bs_regdate = Func.date("Y-m-d H:i:s");
		infodto.setBs_regdate(bs_regdate);
		
		//css파일삭제
		String regex = "<link[^>]*href=[\"']?([^>\"']+)[\"']?[^>]*>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(infodto.getBs_copyright());
		String bs_copyright = "";
		while (matcher.find()) {
			bs_copyright += matcher.replaceAll("");
		}
		infodto.setBs_copyright(bs_copyright);
		
		//update 제외필드
		infodto.setBs_num(null);
		infodto.setBs_directory(null);
		
		//update 처리
		infodto.setExTableName("builder_site");
		infodto.setExWhereQuery("where bs_num = '"+bs_num+"'");
		exdao.update(infodto);
		
		String builder_dir = (String)request.getAttribute("BUILDER_DIR");
		return "redirect:/"+builder_dir+"/ncms/builder_info/write.do";
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
		
		String builder_dir = Func.nvl( (String)request.getAttribute("BUILDER_DIR") );
		
		SelectDTO selectdto = new SelectDTO();
		
		if("check".equals(proc_type)) {
			selectdto.setExTableName("builder_site");
			selectdto.setExColumn("bs_manager_ip as sc_manager_ip, bs_manager_mobile as sc_manager_mobile");
			selectdto.setExWhereQuery("where bs_directory = '"+builder_dir+"'");
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
				selectdto.setExWhereQuery("WHERE mi_chk='Y' and site_dir = '"+builder_dir+"'");
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
				String where_query = "where mi_chk='Y' and site_dir = '"+builder_dir+"'";
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
				field_name = field_name.replaceAll("sc_", "bs_");
				exdao.executeQuery("UPDATE builder_site SET "+field_name+" = '"+field_val+"' where bs_directory = '"+builder_dir+"'");
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
