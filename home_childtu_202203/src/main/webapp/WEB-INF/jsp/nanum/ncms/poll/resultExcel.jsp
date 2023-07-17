<%@
page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%

response.setContentType("application/vnd.ms-excel; charset=utf-8");
response.setHeader("Content-Disposition", "inline; filename=resultlist.xls"); 
response.setHeader("Content-Description", "JSP Generated Data");

%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>엑셀리스트</title>
<body>
<table border="1">
<thead>
	<tr>
		<th>번호</th>
		<th>성함</th>
<c:forEach items="${poqList }" var="poq" varStatus="no">
		<th>${no.count}. ${poq.poq_question }</th>

</c:forEach>
	</tr>
</thead>
<tbody>
<c:forEach items="${userList }" var="user" varStatus="no">
	<tr>
		<td>
			${no.index+1}
		</td>
		<td>
			${user.por_name }
		</td>
	<c:forEach items="${poqList }" var="poq" varStatus="no2">
		<td>
			${user[poq.poq_idx] }
		</td>
	</c:forEach>
	</tr>
</c:forEach>
</tbody>
</table>
</body>
</html>