<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/' : param.prepage }" />

<c:url var="ipin_url" value="/cert/kcb/ipin/ipin_popup2.jsp">
	<c:param name="prepage" value="${prepage }"></c:param>
</c:url>
<c:url var="phone_url" value="/cert/dream/mobile/dream_test02.jsp">
	<c:param name="prepage" value="${prepage }"></c:param>
</c:url>

<%
/*
session = request.getSession(true);
if(session.getAttribute("ss_m_coinfo") != null){
	out.print("<br />"+session.getAttribute("ss_m_coinfo"));
	out.print("<br />"+session.getAttribute("ss_m_dupinfo"));
	out.print("<br />"+session.getAttribute("ss_m_sex"));
	out.print("<br />"+session.getAttribute("ss_m_birth"));
	out.print("<br />"+session.getAttribute("ss_m_age"));
	out.print("<br />"+session.getAttribute("ss_m_name"));
}
*/
%>
							<div class="memberbox ipin">
								<!-- -->
								<div class="box">
									<p class="tit icon11"><strong>아이핀 인증</strong></p>
									<div class="con">
										아이핀(I-PIN)은 인터넷상의 개인식별번호를 의미하여,<br>개인 아이핀아이디와 비밀번호로 인증합니다
									</div>
									<p class="btn"><a href="${ipin_url }" onclick="window.open(this.href,'ipin','width=450, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');return false;" class="con_btn blue">아이핀 인증하기</a></p>
								</div>
								<!-- -->
								<div class="box">
									<p class="tit icon12"><strong>휴대폰 인증</strong></p>
									<div class="con">
										본인명의로 등록된 휴대폰번호로<br>인증이 가능합니다.
									</div>
									<p class="btn"><a href="${phone_url }" onclick="window.open(this.href,'phone','width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');return false;" class="con_btn blue">휴대폰 인증하기</a></p>
								</div>
								<!-- -->
							</div>