package kr.co.nninc.ncms.smarteditor.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.nninc.ncms.smarteditor.service.SmartEditorService;



/**
 * 스마트에디터 파일업로드 위한 비즈니스 구현 클래스
 * 
 * @author 나눔
 * @since 2017.11.11
 * @version 1.0
 */

@Controller
public class SmartEditorController {
	
	/** SmartEditorService */
	@Resource(name="smartEditorService")
	private SmartEditorService smartEditorService;
	
	/**************  Information  ****************************************
	* @Title : 스마트에디터 파일업로드(html5)
	* @method : file_uploader_html5
	/****************************************************************/
	@RequestMapping(value = "/smarteditor/file_uploader_html5.do")
	public String file_uploader_html5(HttpServletRequest request, Model model)
			throws Exception {
		model.addAttribute("request", request);
		
		smartEditorService.fileUploaderHtml5(model);
		return "/site/smarteditor/returnPage";
	}
	
	/**************  Information  ****************************************
	* @Title : 스마트에디터 파일업로드
	* @method : fileUploader
	/****************************************************************/
	@RequestMapping(value = "/smarteditor/fileUploader.do", method = RequestMethod.POST)
	public String fileUploader(HttpServletRequest request, MultipartHttpServletRequest multi, Model model)
			throws Exception {
		model.addAttribute("request", request);
		model.addAttribute("multi", multi);
		
		String return_url = smartEditorService.fileUploader(model);
		return return_url;
	}

}
