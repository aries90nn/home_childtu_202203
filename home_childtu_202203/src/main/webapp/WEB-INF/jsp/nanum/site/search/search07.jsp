<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${decorator ne '' ? decorator : 'user_content'}" />
</head>


<jsp:include flush="false" page="/ndls/bookSearch/nonBookSearch.do">
	<jsp:param name="sh_PL" value="C001,H501,G401,F301" />
</jsp:include>