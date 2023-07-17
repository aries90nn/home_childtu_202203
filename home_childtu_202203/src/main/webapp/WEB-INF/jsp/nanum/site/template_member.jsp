<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="./common/file/header.jsp" />

<link rel="stylesheet" href="/nanum/site/common/css/member.css">

		<jsp:include page="./common/file/left_member.jsp" />
			<!-- 제목영역 -->
			<div id="cont_head">
				<div id="location">
					<ul>
						<li><img src="/nanum/site/img/common/home_ic.gif" alt="home" /></li>
						<li class="now">${title}</li>
					</ul>
				</div>
				<h2>${title}</h2>
			</div>
			<!--// 제목영역 -->
			<!-- 내용영역 -->
			<div id="cont_wrap">
				<decorator:body />
			</div>
			<!--//내용영역-->
		
		<jsp:include page="./common/file/foot_sub.jsp" />