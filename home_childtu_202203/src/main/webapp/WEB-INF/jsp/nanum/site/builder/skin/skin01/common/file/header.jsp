<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<!DOCTYPE html>
<html lang="ko">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ko" />
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	${BUILDER.bs_header_meta }

	<c:set var="header_title" value="" />
	<c:if test="${!empty title}">
	<c:set var="header_title" value="${header_title}${title} > " />
	</c:if>
	<c:if test="${two_menu_name ne '' and two_menu_name ne null}">
	<c:set var="header_title" value="${header_title}${two_menu_name} > " />
	</c:if>
	<c:if test="${first_menu_name ne '' and first_menu_name ne null}">
	<c:set var="header_title" value="${header_title}${first_menu_name} > " />
	</c:if>
	<title><c:if test="${header_title != null and header_title ne ''}">${fn:substring(header_title,0,fn:length(header_title)-2)} |</c:if> ${BUILDER.bs_sitename }</title>


	<script type="text/javascript" src="/nanum/site/common/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/jquery.easing.1.3.min.js"></script><%--자바스크립트 오류 --%>
	<script type="text/javascript" src="/nanum/site/builder/main/${BUILDER_MAIN }/common/js/common_design.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/swiper.min.js"></script>

	<!--슬라이드효과-->
	<script type="text/javascript" src="/nanum/site/common/js/swiper.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/swiper.min.css" />
	<!--//슬라이드효과-->

	<!-- 퀵효과 -->
	<script type="text/javascript" src="/nanum/site/common/js/ui_common.js"></script>
	<!--// 퀵효과 -->

	<!-- 파비콘 -->
	<link rel="shortcut icon" type="image/x-icon" href="/nanum/site/builder/skin/${BUILDER_MAIN }/img/common/favicon_gen.ico" />
	<!--//파비콘  -->

	<!-- 모바일메뉴 -->
	<script type="text/javascript" src="/nanum/site/common/js/mobile_submenu.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/libs.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common.js"></script>
	<!-- //모바일메뉴 -->

	<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/${BUILDER_SKIN }/common/css/all.css" />
	<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/common/css/mtop_head.css" />
	<%
	String rq_request_uri = (String)request.getRequestURI();
	if(rq_request_uri.contains("/main.jsp")){	//메인페이지
	%>
	<link rel="stylesheet" type="text/css" href="/nanum/site/builder/main/${BUILDER.bs_main }/common/css/main_layout.css" />
	<script type="text/javascript" src="/nanum/site/builder/main/${BUILDER.bs_main }/common/js/main.js"></script>
	<%
	}else{	//서브페이지
	%>
	<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/${BUILDER_SKIN }/common/css/sub_layout.css" />
	<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/${BUILDER_SKIN }/common/css/sub_design.css" />
	<!-- 메뉴마다 필요CSS -->
	<c:if test="${!empty head_css}">
	<link rel="stylesheet" type="text/css" href="${head_css}" />
	</c:if>
	<%
	}
	%>
	<script type="text/javascript">
	jQuery.browser = {
	};
	(function () {
	jQuery.browser.msie = false;
	jQuery.browser.version = 0;
	if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
	jQuery.browser.msie = true;
	jQuery.browser.version = RegExp.$1;
	}
	})();
	</script>

	<c:set var="nowURL" value="${pageContext.request.requestURL }" />
	<c:if test="${fn:substring(nowURL,0,5) eq 'https'}">
	<script>
	location.href="${DOMAIN_HTTP}${nowPage}";
	</script>
	</c:if>

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
			<%if(!rq_request_uri.contains("/main.jsp")){%>
			<a class="sknavi" href="#sidebar">왼쪽메뉴 바로가기</a>
			<%}%>
		</div>
		<!-- //스킵네비게이션 -->
		<div id="wrapper">
			<jsp:include page="./toppopup.jsp" />
			<jsp:include page="./menu.jsp" />
