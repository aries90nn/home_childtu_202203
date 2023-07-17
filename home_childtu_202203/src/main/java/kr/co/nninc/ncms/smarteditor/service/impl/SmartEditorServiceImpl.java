package kr.co.nninc.ncms.smarteditor.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.MessageService;
import kr.co.nninc.ncms.smarteditor.service.SmartEditorService;


/**
 * 스마트에디터 파일업로드 관리하기 위한 서비스 구현 클래스
 * @author 나눔
 * @since 2017.11.11
 * @version 1.0
 */
@Service("smartEditorService")
public class SmartEditorServiceImpl extends EgovAbstractServiceImpl implements SmartEditorService {
	
	/** FileUtil */
	@Resource(name="fileutil")
	private FileUtil fileutil;
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;

	
	/**
	 * @title : 파일업로드(html5)
	 * @method : fileUploaderHtml5
	 */
	@Override
	public void fileUploaderHtml5(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String sFileInfo = ""; 

		//파일명 - 싱글파일업로드와 다르게 멀티파일업로드는 HEADER로 넘어옴  
		String name = request.getHeader("file-name"); 
		String ext = name.substring(name.lastIndexOf(".")+1); 

		boolean extchk = false;
		String[] ext_arr = {"jpg","gif","png","bmp"};	
		for(int i=0; i <= ext_arr.length-1;i++){
			if(ext_arr[i].toLowerCase().equals(ext.toLowerCase())){
				extchk = true;
				break;
			}
		}

		if(!extchk){	
			request.setAttribute("sFileInfo", "NOTALLOW_"+name);
		}else{
			
			String defaultPath = fileutil.realPath(request, "/data/");

			String path = defaultPath + "/userfiles" + File.separator; 

			File file = new File(path); 
			if(!file.exists()) { 
				file.mkdirs(); 
			} 

			String realname = UUID.randomUUID().toString() + "." + ext; 
			InputStream is = request.getInputStream(); 
			OutputStream os=new FileOutputStream(path + realname); 

			int numRead; 
			// 파일쓰기 
			byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))]; 
			while((numRead = is.read(b,0,b.length)) != -1){ 
				os.write(b,0,numRead); 
			} 
			if(is != null) { 
				is.close(); 
			} 
			os.flush(); 
			os.close(); 

			sFileInfo += "&bNewLine=true&sFileName="+ name+"&sFileURL=/data/userfiles/"+realname; 
			request.setAttribute("sFileInfo", sFileInfo);
		}
	}
	
	/**
	 * @title : 파일업로드
	 * @method : fileUploader
	 */
	@Override
	public String fileUploader(Model model) throws Exception {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) map.get("multi");

		MultipartFile file = (MultipartFile) multi.getFileMap().get("Filedata");
		
		String callback = Func.nvl(request.getParameter("callback"));
		String callback_func = Func.nvl(request.getParameter("callback_func"));
		String file_result = "";
		try {
			if(file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().equals("")){
			//파일이 존재하면
			String original_name = file.getOriginalFilename();
			String ext = original_name.substring(original_name.lastIndexOf(".")+1);
			String defaultPath = fileutil.realPath(request, "/data/");

			boolean extchk = false;
			String[] ext_arr = {"jpg","gif","png","bmp"};
			for(int i=0; i <= ext_arr.length-1;i++){
				if(ext_arr[i].toLowerCase().equals(ext.toLowerCase())){
					extchk = true;
					break;
				}
			}
			if(extchk){ //허용파일(210702)
				String path = defaultPath + "/userfiles" + File.separator;
				//디렉토리 존재하지 않을경우 디렉토리 생성
				Func.folderCreate(path);
				//서버에 업로드 할 파일명(한글문제로 인해 원본파일은 올리지 않는것이 좋음)
				String realname = UUID.randomUUID().toString() + "." + ext;
				///////////////// 서버에 파일쓰기 ///////////////// 
				((MultipartFile) file).transferTo(new File(path+realname));
				file_result += "&bNewLine=true&sFileName="+original_name.replaceAll(" ", "")+"&sFileURL=/data/userfiles/"+realname;
			}else{
				file_result += "&errstr=error";
			}
		} else {
			file_result += "&errstr=error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + callback + "?callback_func="+callback_func+file_result;
	}

}
