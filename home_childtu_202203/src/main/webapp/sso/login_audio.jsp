<%@
page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@
page import ="kr.co.nninc.ncms.common.Func"%><%

response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);

//세션초기화
request.getSession().invalidate();

String m_id = Func.nvl( request.getParameter("user_id") ).trim();
String m_pwd = Func.nvl( request.getParameter("user_pw") ).trim();
String code = "-2";
String message = "unknown user ID";
boolean status = false;
if(!"".equals(m_id) && !"".equals(m_pwd)){

	if(status){
		if("2".equals(Func.getSession(request, "ss_user_class"))){
			code = "-5";
			message = "Blocked or expired user";
		}else if("".equals(Func.getSession(request, "ss_user_num"))){
			code = "-6";
			message = "Not KOLAS member (only web member)";
		}else{
			code = "1";
			message = "OK";
		}
	}
	
}

if("1".equals(code)){
	out.print(code);
}else{
	out.print("0");
}


//세션초기화
request.getSession().invalidate();
%>