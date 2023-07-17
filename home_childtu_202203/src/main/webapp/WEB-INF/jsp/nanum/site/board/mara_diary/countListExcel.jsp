<%@
page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%

String filename = "";
String sh_course = request.getParameter("sh_course");
if(sh_course == null){sh_course = "";}

if("".equals(sh_course)){
	filename = "excel_down_all.xls";
}else if("21097".equals(sh_course)){
	filename = "excel_down_half.xls";
}else{
	filename = "excel_down_"+sh_course+".xls";
}

response.setContentType("application/vnd.ms-excel; charset=utf-8");
response.setHeader("Content-Disposition", "inline; filename="+filename); 
response.setHeader("Content-Description", "JSP Generated Data");

%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>엑셀리스트</title>
<body>
<table border="1">
	<tbody>
		<tr>
			<th>이름</th>
			<th>도서제목</th>
			<th>저자</th>
			<th>출판사</th>
			<th>날짜</th>
			<th>분류번호</th>
			<th>대출도서관</th>
			<th>대출도서관명</th>
			<th>읽은쪽수</th>
			<th>누적쪽수</th>
			<th>ID</th>
			<th>종목</th>
			<th>감상문</th>
		</tr>
<c:set var="sumpage" value="0" />
<c:set var="chk_b_num" value="" />
<c:forEach items="${boardList }" var="board" varStatus="no">
	<fmt:formatNumber var="b_temp1_number" value="${board.b_temp1}" pattern="#,###"/>
	<c:choose>
		<c:when test="${empty chk_b_num or chk_b_num ne board.b_num }">
			<c:set var="sumpage" value="${board.b_temp1 }" />
			<c:set var="chk_b_num" value="${board.b_num }" />
		</c:when>
		<c:otherwise>
			<c:set var="sumpage" value="${sumpage + board.b_temp1 }" />
		</c:otherwise>
	</c:choose>
	<fmt:formatNumber var="sumpage_number" value="${sumpage}" pattern="#,###"/>
	
			<td>${board.b_name }</td>
			<td>${board.b_subject }</td>
			<td>${board.b_temp4 }</td>
			<td>${board.b_temp5 }</td>
			<td>${fn:substring(board.b_regdate,0,10) }</td>
			<td>${board.b_temp3 }</td>
			<td>${board.b_temp2 }</td>
			<td>${board.b_temp6 }</td>
			<td>${b_temp1_number  }</td>
			<td>${sumpage_number }</td>
			<td>${board.b_id }</td>
			<td>${courseList[board.course] }</td>
			<td>${board.b_content }</td>
		</tr>
</c:forEach>
	</tbody>
</table>

</body>
</html>