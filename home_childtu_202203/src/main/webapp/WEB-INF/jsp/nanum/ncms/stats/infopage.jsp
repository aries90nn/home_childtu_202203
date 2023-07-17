<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
	<meta name="robots" content="noindex, nofollow" />
	<title>Home Manager</title>
</head>
<body >
<form id="levefrm" method="post">
	<div>
		<input type="hidden" name="query" value="${domain_name}">
	</div>
</form>


<script type='text/javascript'>
function ip_info(){
	document.getElementById('levefrm').action = "http://whois.nic.or.kr/kor/whois.jsp";
	document.getElementById('levefrm').submit();
}

ip_info();
</script>


</body>
</html>