<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>대기페이지</title>
	<script type="text/javascript">
	//<![CDATA[
	onload = function(){
		setTimeout("chkTime()", 1000);
	}


	function chkTime(){
		var span_obj = document.getElementById("span_time");
		var time = eval( span_obj.innerText );
		time--;
		if(time == 0){
			location.href="${param.gopage}";
		}else{
			span_obj.innerText = time;
			setTimeout("chkTime()", 1000);
		}
	}

	//]]>
	</script>
</head>
<body id="wrap">
접속자 폭주로 인하여 접속이 지연되고 있습니다. 대기시간이 만료되면 자동으로 재접속됩니다.<br />
time : <span id="span_time">${param.wait_time}</span>
</body>
</html>