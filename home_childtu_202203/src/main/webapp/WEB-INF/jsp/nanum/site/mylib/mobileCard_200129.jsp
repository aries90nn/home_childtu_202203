<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/member.css" />
</head>


<c:if test="${empty sessionScope.ss_user_num }">
	<c:set var="builder_dir" value="${BUILDER_DIR }" />
	<c:if test="${!empty builder_dir }"><c:set var="builder_dir" value="/${BUILDER_DIR }" /></c:if>
	<c:url var="login_url" value="${builder_dir }/site/member/login.do">
		<c:param name="prepage" value="${nowPage }"></c:param>
	</c:url>
<script type="text/javascript">
//<![CDATA[
alert("개인정보 제공 재동의 및 본인인증 처리 후에 도서관 방문하셔서 정회원으로 전환 후에 이용하실 수 있습니다.");
<c:choose>
	<c:when test="${empty sessionScope.ss_user_id}">
		location.href="${login_url}";
	</c:when>
	<c:otherwise>
		location.href="/";
	</c:otherwise>
</c:choose>
//]]>
</script>
</c:if>


						<div class="membership">
							<div class="memberimg">
								<div class="hatch"><img class="hatch" src="/nanum/site/builder/skin/common/img/membership_icon.gif" alt=""></div>
							</div>
							<dl>
								<dt>${sessionScope.ss_user_name } 님</dt>
								<dd>${sessionScope.ss_user_num }</dd>
							</dl>
							<div class="member_code"><p class="member_code" id="bcTarget1" style="margin:0 auto;"></p></div>
<c:choose>
	<c:when test="${BUILDER_DIR eq 'seokbong'}">

							<p class="member_info">도서의 대출시에는 이 증을 제시하십시오.<br />이 회원증은 본인만 사용 가능하며,1인당 5권, 15일간 (대출일 포함, 연장불가)대출 가능합니다.</p>
	</c:when>
	<c:otherwise>
							<p class="member_info">도서의 대출시에는 이 증을 제시하십시오.<br />이 회원증은 본인만 사용 가능하며,1인당 10권, 15일간 (대출일 포함, 연장불가)대출 가능합니다.</p>
	</c:otherwise>
</c:choose>

						</div>


<script type="text/javascript" src="/nanum/ndls/common/js/jquery-barcode.js"></script>
<script type="text/javascript">
$("#bcTarget1").barcode("${sessionScope.ss_user_num }", "code128", {barWidth:2, barHeight:45, showHRI:false});
</script>