<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>
<%--a_level을 지정해서 property에 선언한 a_num을 사용 --%>
<jsp:include flush='false' page='/board/board.do'>
	<jsp:param name='proc_type' value='write' />
	<jsp:param name='a_level' value='mara_request' />
</jsp:include>

