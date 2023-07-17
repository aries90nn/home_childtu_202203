<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html lang="ko">
<head>
<title>${BUILDER.bs_sitename }</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ko" />
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />

	<script type="text/javascript" src="/nanum/site/common/js/jquery-2.2.4.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/jquery.easing.1.3.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_design.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/swiper.min.js"></script>

	<!--슬라이드효과-->
	<script type="text/javascript" src="/nanum/site/common/js/swiper.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/swiper.min.css" />
	<!--//슬라이드효과-->

	<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/${BUILDER_SKIN }/common/css/all.css" />
<%
String rq_request_uri = (String)request.getRequestURI();
if(rq_request_uri.contains("/main.jsp")){
%>
	<link rel="stylesheet" type="text/css" href="/nanum/site/builder/main/${BUILDER.bs_main }/common/css/main_layout.css" />
<%
}else{
%>
	<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/${BUILDER_SKIN }/common/css/sub_layout.css" />
	<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/${BUILDER_SKIN }/common/css/sub_design.css" />
	<!-- 메뉴마다 필요CSS -->
<c:if test="${head_css ne ''}">
	<link rel="stylesheet" type="text/css" href="${head_css}" />
</c:if>
<%
}
%>
<script type="text/javascript">
    jQuery.browser = {};
    (function () {
        jQuery.browser.msie = false;
        jQuery.browser.version = 0;
        if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
            jQuery.browser.msie = true;
            jQuery.browser.version = RegExp.$1;
        }
    })();
</script>

	<!-- 모바일메뉴 -->
	<script type="text/javascript" src="/nanum/site/common/js/sj_submenu.js"></script>
	<!-- //모바일메뉴 -->

</head>
<%if(rq_request_uri.contains("/main.jsp")){%>
<body class="main">
<%}else{%>
<body class="sub">
<%}%>
	<!-- 스킵네비게이션 -->
	<div>
		<a class="sknavi" href="#contents">본문으로 바로가기</a>
		<a class="sknavi" href="#menu_navi">대메뉴로 바로가기</a>
		<%if(rq_request_uri.contains("/main.jsp")){%>
		<a class="sknavi" href="#sidebar">왼쪽메뉴 바로가기</a>
		<%}%>
	</div>
	<!-- //스킵네비게이션 -->
	<div id="wrapper">
		<jsp:include page="../../builder/skin/${BUILDER_SKIN }/common/file/menu.jsp" />
