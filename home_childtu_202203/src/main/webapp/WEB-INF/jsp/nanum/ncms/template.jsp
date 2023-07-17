<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />

	<meta name="language" content="ko" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>관리자페이지 <c:if test="${title_name != null and title_name ne ''}">:</c:if> ${title_name} </title>
	<!-- css, script-->
	<jsp:include page="../ncms/common/file/head_script.jsp" />
</head>

<body id="wrap">
<!-- 상단 영역-->
<jsp:include page="../ncms/common/file/top.jsp" />

<!-- 사이드영역 -->
<jsp:include page="../ncms/common/file/left.jsp" />


<!-- 중간 영역 -->
<div id="content_wrap">

	<!-- 내용영역 -->
	<div id="contents">
<%//게시판용 %>
<c:set var="currentUrl" value="${pageContext.request.requestURL}" />
<c:if test="${fn:contains(currentUrl, '/ncms/board/') == true and param.a_num != null and param.a_num ne ''}">
<h1 class="tit"><span>${title_name}</span></h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">
</c:if>
		<!-- 내용들어가는곳 -->
		<decorator:body />

<%//게시판용 %>
<c:if test="${fn:contains(currentUrl, '/ncms/board/') == true and param.a_num != null and param.a_num ne ''}">
</div>
</c:if>
	</div>
	<!-- //내용영역 -->
</div>


<!-- 하단영역-->
<jsp:include page="../ncms/common/file/bottom.jsp" />

</body>
</html>