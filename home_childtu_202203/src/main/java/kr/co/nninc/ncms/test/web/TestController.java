package kr.co.nninc.ncms.test.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.nninc.ncms.test.service.TestService;


@Controller
public class TestController {
	@Resource(name="testService")
	private TestService testService;

	@RequestMapping(value="/test.do")
	public String list(HttpServletRequest request, Model model) throws Exception{
		System.out.println("test controller");
		testService.test();
		return null;
	}
	
}
