package kr.co.nninc.ncms.main.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.batch.core.resource.StepExecutionSimpleCompletionPolicy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.cms.service.UserCmsService;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.main.service.MainService;



/**
 *사용자 메인 클래스
 * 
 * @author 나눔
 * @since 2019.03.28
 * @version 1.1
 */

@Controller
public class MainController {

	/** UserCmsService */
	@Resource(name = "userCmsService")
	private UserCmsService userCmsService;
	
	/** MainService */
	@Resource(name = "mainService")
	private MainService mainService;
	

	/** 메인 페이지 */
	@RequestMapping(value = { "/main.do","/*/main.do", "/*/index.do" })
	public String main(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("request", request);
		
		//탑메뉴
		userCmsService.top(model, request);
		//하단메뉴
		userCmsService.foot(model, request);
		//메인 스킨별 처리
		mainService.procSkinMethod(model);
		
		HashMap<String,String>builder = (HashMap<String,String>)request.getAttribute("BUILDER");
		List<HashMap<String, String>>boardList = mainService.mainBoardList(model, "69394427", 4); // 공지사항
		request.setAttribute("board1", boardList);
		
		boardList = mainService.mainBoardList(model, "75543687", 5); // 활동갤러리
		request.setAttribute("board2", boardList);
		
		boardList = mainService.mainBoardList(model, "59907913", 1); // 홍보영상
		request.setAttribute("board3", boardList);
		
		boardList = mainService.mainPollList(model);
		System.out.println(boardList.size());
		request.setAttribute("poll", boardList);
		
		mainService.bannerList(model); // 배너존
		mainService.banner2List(model); // 팝업존
		mainService.banner3List(model); // 상단팝업
		mainService.banner4List(model); // 풀팝업
		//mainService.popupList(model); // 팝업
		
		if(builder == null){
			return "/site/main";
		}else{
			return "/site/builder/main/"+Func.nvl(builder.get("bs_main"))+"/main";
		}
		
	}
	
	/** 메인 별도요청 ajax 처리는 인터셉터에 예외처리되도록 /[빌더명]/main/페이지명 형식을 지킨다
	 * 새소식 */
	@RequestMapping(value = { "/*/main/*.do" })
	public String mainCommonProc(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("request", request);
		//처리 메소드 실행
		String url = Func.nvl( (String)request.getAttribute("javax.servlet.include.request_uri") ).trim();
		System.out.println(url);
		if("".equals(url)){
			url = Func.nvl( (String)request.getRequestURI() ).trim();
		}
		System.out.println(url);
		String methodName = url.substring(url.lastIndexOf('/') + 1, url.length()).replaceAll(".do", "").replaceAll(".jsp", "");
		//메인 스킨별 메소드 처리
		mainService.procSkinMethod(model, methodName);
		
		String main_skin = Func.nvl( (String)request.getAttribute("BUILDER_MAIN") ).trim();
		String ret_value = "/site/builder/main/"+main_skin+"/common/file/"+methodName;
		
		return ret_value;
	}
	
	

}
