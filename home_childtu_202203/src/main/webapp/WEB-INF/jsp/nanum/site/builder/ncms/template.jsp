<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%

//메뉴그룹화
HashMap<String,String>mng_menu_group = new HashMap<String,String>();
String rq_request_uri = (String)request.getAttribute("rq_request_uri");
if(rq_request_uri.contains("/edusat/")
		|| rq_request_uri.contains("/edusat_blacklist/")
		|| rq_request_uri.contains("/edusat_code/")
		|| rq_request_uri.contains("/edusat_season/")
		|| rq_request_uri.contains("/close/")
		|| rq_request_uri.contains("/site/board/")
		|| rq_request_uri.contains("/site/marathon/")
		|| rq_request_uri.contains("/locker/")
		|| rq_request_uri.contains("/locker_season/")
		|| rq_request_uri.contains("/lib_stats/")
		|| rq_request_uri.contains("/buseo/")
		|| rq_request_uri.contains("/buseo_member/")
		|| rq_request_uri.contains("/ebookpdf/")
		|| rq_request_uri.contains("/poll/")
		|| rq_request_uri.contains("/poll_question/")
		|| rq_request_uri.contains("/stock/")
	){
	mng_menu_group.put("key", "01");
	mng_menu_group.put("menu01_img", "_on");
}else if(rq_request_uri.contains("/cms/")){
	mng_menu_group.put("key", "02");
	mng_menu_group.put("menu02_img", "_on");
}else if(rq_request_uri.contains("/member/") || rq_request_uri.contains("/member_group/")){
	mng_menu_group.put("key", "03");
	mng_menu_group.put("menu03_img", "_on");
}else if(rq_request_uri.contains("/board_config/")
		|| rq_request_uri.contains("/board_delete/")
		|| rq_request_uri.contains("/board_code/")
		|| rq_request_uri.contains("/popup/")
		|| rq_request_uri.contains("/banner/")
		|| rq_request_uri.contains("/banner2/")
		|| rq_request_uri.contains("/banner3/")
		|| rq_request_uri.contains("/banner4/")
	){
	mng_menu_group.put("key", "04");
	mng_menu_group.put("menu04_img", "_on");
}else if(rq_request_uri.contains("/stats/")
		|| rq_request_uri.contains("/stats_menu/")
		|| rq_request_uri.contains("/log/")
	){
	mng_menu_group.put("key", "05");
	mng_menu_group.put("menu05_img", "_on");
}else if(rq_request_uri.contains("/ip/")
		|| rq_request_uri.contains("/sql/")
		|| rq_request_uri.contains("/session/")
	){
	mng_menu_group.put("key", "06");
	mng_menu_group.put("menu06_img", "_on");
}
request.setAttribute("mng_menu_group", mng_menu_group);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />

	<meta name="language" content="ko" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>관리자페이지 <c:if test="${title_name != null and title_name ne ''}">:</c:if> ${title_name} </title>
	<!-- css, script-->
	<jsp:include page="./common/file/head_script.jsp" />
</head>


<body id="wrap">
<!-- 상단 영역-->
<jsp:include page="./common/file/top.jsp" />

<!-- 사이드영역 -->
<jsp:include page="./common/file/left.jsp" />


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
<jsp:include page="./common/file/bottom.jsp" />

</body>
</html>