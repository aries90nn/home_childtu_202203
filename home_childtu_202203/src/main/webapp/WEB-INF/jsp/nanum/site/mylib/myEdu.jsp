<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="url" value="/edusat/myinfo.do" />
<c:if test="${!empty BUILDER_DIR }">
	<c:set var="url" value="/${BUILDER_DIR }/edusat/myinfo.do" />
</c:if>

<jsp:include flush="false" page="${url }" />