<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
request.setCharacterEncoding("UTF-8");
response.setContentType("application/vnd.ms-excel; charset=UTF-8;");
response.setHeader("Content-Disposition", "inline; filename=list.xls"); 
response.setHeader("Content-Description", "JSP Generated Data");
%>

<html>
<head>
<title>엑셀리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<table>
<thead>
	<tr>
		<th>번호</th>
		<th>날짜</th>
		<th>작업</th>
		<th>수량</th>
		<th>프로그램구분</th>
		<th>배송정보</th>
		<th>내역</th>
		<th>비고</th>
		<th>등록날짜</th>
	</tr>
</thead>
<tbody>

<c:forEach items="${stockList}" var="stock" varStatus="no">
	<tr>
		<td>${no.count}</td>
		<td>${stock.st_date }</td>
		
		<td>${stock.st_work }</td>
		<td><fmt:formatNumber value="${stock.st_cnt }" pattern="#,###" /></td>
		<td>${stock.st_program }</td>
		<td>${stock.st_delivery }</td>
		<td>${stock.st_history }</td>
		<td>${stock.st_ext }</td>
		<td>${stock.st_regdate }</td>
	</tr>

</c:forEach>
</tbody>
</table>
</body>
</html>
