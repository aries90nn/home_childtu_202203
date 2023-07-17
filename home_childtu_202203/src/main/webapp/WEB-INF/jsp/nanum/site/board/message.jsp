<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 내용영역 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ko" />
	<meta name="robots" content="noindex, nofollow" />
	<meta http-equiv="Cache-Control" content="No-Cache">
	<meta http-equiv="Pragma" content="No-Cache">
	<title>메세지</title>
</head>

<body>


<script type="text/javascript">
<c:if test="${ok eq ''}">
	alert("${msg}");
	location.href="${prepage}";
</c:if>
<c:if test="${ok ne ''}">
	if(confirm("${msg}")){
		location.href="${ok}";
	}else{
		location.href="${no}";
	}
	</c:if>
</script>
<noscript>

${msg}
<br/>
<br/>

<c:if test="${ok eq ''}">
	<a href="${prepage}">[확인]</a>
</c:if>
<c:if test="${ok ne ''}">
	<a href="${ok}">[확인]</a>
	&nbsp;&nbsp;
	<a href="${no}">[취소]</a>
</c:if>


</noscript>

</body>
</html>