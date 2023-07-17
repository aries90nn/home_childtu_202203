<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<div class="banner">
	<ul>
	<c:forEach items="${bannerList}" var="banner" varStatus="no">
		<li><a href="${banner.b_l_url}" target="_blank"><img src="/data/banner/${banner.b_l_img}" style="width:175px;heigth:40px;" alt="${banner.b_l_subject}" /></a></li>
	</c:forEach>
	</ul>
</div>