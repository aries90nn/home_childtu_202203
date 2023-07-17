<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<%--동아리 목록 --%>
<c:choose>
	<c:when test="${!empty param.a_num and empty param.page_type }">
		<jsp:include flush='false' page='/board/club_list/clubList.do'><jsp:param name='a_num' value='${param.a_num }' /></jsp:include>
	</c:when>
	<c:when test="${!empty param.a_num and param.page_type eq 'write' }">
		<jsp:include flush='false' page='/board/board.do'>
			<jsp:param name='proc_type' value='write' />
			<jsp:param name='a_num' value='${param.a_num }' />
			<jsp:param name='prepage' value='${param.prepage }' />
		</jsp:include>
	</c:when>
	<c:otherwise>
		<script>
			alert("잘못된 요청입니다.");
			histtory.go(-1);
		</script>
	</c:otherwise>
</c:choose>

