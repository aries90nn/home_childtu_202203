<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@
 taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
 taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%><%@
 taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %><%

response.setContentType("application/vnd.ms-excel; charset=utf-8");
response.setHeader("Content-Disposition", "inline; filename=locker_list.xls"); 
response.setHeader("Content-Description", "JSP Generated Data");

%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>사물함신청자 리스트</title>

</head>

<c:choose>
	<c:when test="${param.sh_lc_season eq '1' }">
		<c:set var="sh_lc_season_str" value="1월 ~ 6월" />
	</c:when>
	<c:when test="${param.sh_lc_season eq '7' }">
		<c:set var="sh_lc_season_str" value="7월 ~ 12월" />
	</c:when>
	<c:otherwise>
		<c:set var="sh_lc_season_str" value="전체" />
	</c:otherwise>
</c:choose>

<body class="blue">
<table border="1">
<thead>
<tr>
	<th colspan="7">
		도서관 : ${empty sh_libname ? '전체' : sh_libname } | 연도 : ${empty param.sh_lc_year ? '전체' : param.sh_lc_year } | 차수 : ${sh_lc_season_str }</th>
</tr>
<tr>
	<th>도서관</th>
	<th>신청기간</th>
	<th>사물함번호</th>
	<th>신청자명</th>
	<th>이용자번호</th>
	<th>연락처</th>
	<th>신청일자</th>
</tr>
</thead>
<tbody>
<c:forEach items="${lockerList }" var="locker" varStatus="no">
	
	<c:choose>
		<c:when test="${locker.lc_season eq '1' }">
			<c:set var="lc_season_str" value="1월 ~ 6월" />
		</c:when>
		<c:when test="${locker.lc_season eq '7' }">
			<c:set var="lc_season_str" value="7월 ~ 12월" />
		</c:when>
		<c:otherwise>
			<c:set var="lc_season_str" value="${locker.lc_season }" />
		</c:otherwise>
	</c:choose>


<tr>
	<td>${locker.lc_lib }</td>
	<td>${lc_season_str }</td>
	<td>&nbsp;${locker.lc_locker_num }</td>
	<td>${locker.lc_m_name }</td>
	<td>${locker.lc_m_num }</td>
	<td>${locker.lc_phone }</td>
	<td>${locker.lc_regdate }</td>
</tr>

</c:forEach>

</tbody>
</html>