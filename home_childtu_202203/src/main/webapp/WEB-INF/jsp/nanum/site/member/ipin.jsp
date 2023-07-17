<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/' : param.prepage }" />
<%--
<c:url var="ipin_url" value="/cert/kcb/ipin/ipin_popup2.jsp">
	<c:param name="prepage" value="${prepage }"></c:param>
</c:url>
<c:url var="phone_url" value="/cert/dream/mobile/dream_test02.jsp">
	<c:param name="prepage" value="${prepage }"></c:param>
</c:url>
--%>

<c:url var="ipin_url" value="/cert/nice/ipin/ipin_main.jsp">
	<c:param name="prepage" value="${prepage }"></c:param>
</c:url>
<c:url var="phone_url" value="/cert/nice/mobile/checkplus_main.jsp">
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
							<div class="cash_box">
								<p><a href="/${BUILDER_DIR }/site/member/cash_delete_popup.do" onclick="window.open(this.href, '', 'width=425px,height=475px,top=0,left=0, scrollbars=no'); return false;">모바일에서 본인인증이 안될 경우 [해결방법]</a></p>
							</div>
							
							<div class="gray_box mb30">
								<ul class="list">
									<!-- <li>${prepage }</li>-->
									<li>신규 아이핀을 발급 받고자 하시는 분은 민간아이핀 홈페이지를 이용하거나, 본인 신분증을 가지고 가까운 주민센터를 방문하여 발급 받을 수 있습니다. </li>
									<li>14세 미만의 경우 홈페이지와 주민센터 방문신청을 통해 민간아이핀사의 아이핀 발급이 가능해지며, 법정대리인의 주민센터 방문을 통한 발급도 가능합니다.</li>
									<li>기타 아이핀 관련 사항은 공공아이핀 홈페이지<span class="eng2">(www.gpin.go.kr)</span>를 참조하시기 바랍니다</li>
									<li>민간 아이핀 발급<br /> 
										<a href="http://www.niceipin.co.kr/niceipin/index.asp" target="_blank" class="con_sbtn blue_l  mt10">나이스 아이핀 발급</a></span>
										<a href="http://www.siren24.com/" target="_blank"  class="con_sbtn blue_l  mt10">sci평가정보 Ssriend</a></span>
										<a href="http://www.allcredit.co.kr/ADFCommonSvl?SCRN_ID=s05050522678" target="_blank"  class="con_sbtn blue_l mt10">코리아크레딧뷰로 </a></span>								
									</li>
								</ul>
							</div>
							
							
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