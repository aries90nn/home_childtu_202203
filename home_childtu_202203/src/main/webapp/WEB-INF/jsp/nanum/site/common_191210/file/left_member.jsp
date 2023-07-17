<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	
	<!-- 중간영역 -->
	<div id="container">
		<div class="wsize">
		<!-- 사이드영역 -->
		<div id="sidebar">
			<h2>${first_menu_name}</h2>
			<div id="sidemenu">
				<p class="tit jsMMenuText"><a href="#empty" class="jsMMenuBtn">${first_menu_name}</a></p>
				<ul>
					<c:choose>
						<c:when test="${empty sessionScope.ss_m_id}">
							<li <c:if test="${type eq '1'}">class="on"</c:if>>
								<a href="${DOMAIN_HTTPS}/member/login.do">로그인</a>
							</li>
							<li <c:if test="${type eq '2'}">class="on"</c:if>>
								<a href="${DOMAIN_HTTPS}/member/join.do">회원가입</a>
							</li>
							<li <c:if test="${type eq '3'}">class="on"</c:if>>
								<a href="${DOMAIN_HTTPS}/member/idpw.do">아이디/비밀번호 찾기</a>
							</li>
						</c:when>
						<c:otherwise>
							<li <c:if test="${type eq '4'}">class="on"</c:if>>
								<a href="${DOMAIN_HTTPS}/member/modify.do">정보수정</a>
							</li>
							<li <c:if test="${type eq '5'}">class="on"</c:if>>
								<a href="${DOMAIN_HTTPS}/member/logout.do">로그아웃</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
				<jsp:include page="../file/sub_banner.jsp" />
			</div>
		</div>
		<!-- //사이드영역 -->
		<!-- 컨텐츠영역 -->
		<div id="contents">
			<!-- 인쇄영역 -->
			<div id="print_wrap">