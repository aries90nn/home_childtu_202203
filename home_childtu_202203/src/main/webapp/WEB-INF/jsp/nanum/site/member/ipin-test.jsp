<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/' : param.prepage }" />
<%
//test용

session.setAttribute("ss_m_name", "최경수");
session.setAttribute("ss_m_coinfo", "hjJibWaIC3o6fM3Vyqi8FmSQSfbQAcrHFWIKMAU36AcjqLq+QHotXYGVL1FXuCBWRpQg6x3tXCgFUwEglc9f8A==");
session.setAttribute("ss_m_dupinfo", "MC0GCCqGSIb3DQIJAyEAsW3tzqa1NRlG9bgylQep3R4CFn2Kv7T3Kz0cDcAAAAA==");
session.setAttribute("ss_m_sex", "0");
session.setAttribute("ss_m_birth", "19790903");
session.setAttribute("ss_m_age", "41");


/*
session.setAttribute("ss_m_name", "김경수");
session.setAttribute("ss_m_coinfo", "hjJibWaIC3o6fM3Vyqi8FmSQSfbQAcrHFWIKMAU36AcjqLq+QHotXYGVL1FXuCBWRpQg6x3tXCgFUwEglc9f8B==");
session.setAttribute("ss_m_dupinfo", "MC0GCCqGSIb3DQIJAyEAsW3tzqa1NRlG9bgylQep3R4CFn2Kv7T3Kz0cDcAAAAB==");
session.setAttribute("ss_m_sex", "0");
session.setAttribute("ss_m_birth", "19790903");
session.setAttribute("ss_m_age", "40");
*/

/*
//미성년자
session.setAttribute("ss_m_name", "김미성");
session.setAttribute("ss_m_coinfo", "hjJibWaIC3o6fM3Vyqi8FmSQSfbQAcrHFWIKMAU36AcjqLq+QHotXYGVL1FXuCBWRpQg6x3tXCgFUwEglc9f8C==");
session.setAttribute("ss_m_dupinfo", "MC0GCCqGSIb3DQIJAyEAsW3tzqa1NRlG9bgylQep3R4CFn2Kv7T3Kz0cDcAAAAC==");
session.setAttribute("ss_m_sex", "1");
session.setAttribute("ss_m_birth", "20060903");
session.setAttribute("ss_m_age", "13");
*/

%>
<script type="text/javascript">
//<![CDATA[
function test(){
	alert("준비중입니다.");
	location.href="${prepage}";
}
//]]>
</script>

<c:url var="ipin_url" value="/sci_ipin/ipin_request_seed.jsp">
	<c:param name="prepage" value="${prepage }"></c:param>
</c:url>
<c:url var="phone_url" value="/nice_check/checkplus_main.jsp">
	<c:param name="prepage" value="${prepage }"></c:param>
</c:url>
							<div class="memberbox ipin">
								<!-- -->
								<div class="box">
									<p class="tit icon11"><strong>아이핀 인증</strong></p>
									<div class="con">
										아이핀(I-PIN)은 인터넷상의 개인식별번호를 의미하여,<br>개인 아이핀아이디와 비밀번호로 인증합니다
									</div>
									<p class="btn"><a href="${ipin_url }" onclick="test();return false;" class="con_btn">아이핀 인증하기</a></p>
								</div>
								<!-- -->
								<div class="box">
									<p class="tit icon12"><strong>휴대폰 인증</strong></p>
									<div class="con">
										본인명의로 등록된 휴대폰번호로<br>인증이 가능합니다.
									</div>
									<p class="btn"><a href="${phone_url }" onclick="test();return false;" class="con_btn">휴대폰 인증하기</a></p>
								</div>
								<!-- -->
							</div>