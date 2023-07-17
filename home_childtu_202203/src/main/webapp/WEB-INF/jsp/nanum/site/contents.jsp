<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.HashMap" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>
<body>

<%
//탭메뉴 상단내용 request객체 저장
HashMap<String,String>cms = (HashMap<String,String>)request.getAttribute("cms");
request.setAttribute("ct_header", cms.get("ct_header"));
%>

<c:choose>
	<c:when test="${cms.ct_pagetype eq 'T' }">
		<c:out value="${cms.ct_content}" escapeXml="false" />
	</c:when>
	<c:when test="${cms.ct_pagetype eq 'B' }">
		${cms.ct_board_header }
		<jsp:include flush='false' page='/board/board.do'><jsp:param name='a_num' value='${cms.ct_anum }' /></jsp:include>
		${cms.ct_board_footer }
	</c:when>
	<c:when test="${!empty param.a_num and fn:length(clubList) > 0 }">
		<c:set var="loop" value="1" />
		<c:forEach items="${clubList}" var="entry" varStatus="no">
			<c:if test="${loop eq '1' }">
				<c:if test="${entry.value eq param.a_num}">
					<jsp:include flush='false' page='/board/board.do'><jsp:param name='a_num' value='${param.a_num }' /></jsp:include>
					<c:set var="loop" value="0" />
				</c:if>
			</c:if>
		</c:forEach>
	</c:when>
	<c:otherwise>
		요청한 페이지 정보가 없습니다.
	
	</c:otherwise>
</c:choose>


</body>
</html>