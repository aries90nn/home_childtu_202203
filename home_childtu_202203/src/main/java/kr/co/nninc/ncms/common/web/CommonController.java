package kr.co.nninc.ncms.common.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.nninc.ncms.common.FileUtil;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.PropertyUtil;

/**
 * 공용처리 콘트롤러 클래스
 * 
 * @author 나눔
 * @since 2020.02.06
 * @version 1.0
 */
@Controller("ncmsCommonController")
public class CommonController {

	
	/**************  Information  ***********************************
	* @Title : property 로딩
	* @method : prop
	/************************************************************/
	@ResponseBody
	@RequestMapping(value="/prop/apply.do")
	public String prop(HttpServletRequest request, Model model) throws Exception{
		model.addAttribute("request", request);
		
		String pname = Func.nvl( request.getParameter("pname") ).trim();
		
		FileUtil fileUtil = new FileUtil();
		String path = "";
		String result = "";
		
		path = fileUtil.realPath(request, "/WEB-INF/classes/kr/co/nninc/spring/"+pname+"-config.properties");
		//path = "C:/dev/eGovFrameDev-3.7.0-32bit/workspace/home_builder_202002/src/main/resources/kr/co/nninc/spring/nninc-config.properties";
		PropertyUtil pu = PropertyUtil.getInstance(path);
		if(pu == null){
			result = pname+"-config.properties failed.";
			return result;
		}
		
		result = pname+"-config.properties ok.";
		System.out.println("path="+path);
		
		return result;
	}
}
