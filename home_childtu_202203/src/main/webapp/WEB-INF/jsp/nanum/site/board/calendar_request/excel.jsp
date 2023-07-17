<%@
page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%

response.setContentType("application/vnd.ms-excel; charset=utf-8");
response.setHeader("Content-Disposition", "inline; filename=list.xls"); 
response.setHeader("Content-Description", "JSP Generated Data");


String is_ad_cms = String.valueOf( request.getAttribute("is_ad_cms") );
if(!"Y".equals(is_ad_cms)){
	out.print("Bad Request.");
	return;
}

%><html>
<head>
<title>엑셀리스트</title>
<body>
<table>
<thead>
	<tr>
		<th>신청기관명</th>
		<th>신청자명</th>
		<th>연락처</th>
		<th>방문인원</th>
		<th>방문일자</th>
		<th>방문시간대</th>
		<th>상태</th>
		<th>등록일</th>
	</tr>
</thead>
<tbody>

<c:forEach items="${boardList}" var="board">
	<tr>
		<td>${board.b_subject }</td>
		<td>${board.b_name }</td>
		<td>${board.b_phone1 }-${board.b_phone2 }-${board.b_phone3 }</td>
		<td>${board.b_temp1 }</td>
		<td>${board.b_sdate }</td>
		<td>${board.b_temp2 }</td>
		<td>${board.b_temp8 }</td>
		<td>${fn:substring(board.b_regdate, 0, 10) }</td>
	</tr>

</c:forEach>
</tbody>
</table>
</body>
</html>
