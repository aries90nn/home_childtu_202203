<%@
page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%

String rq_b_bname = (String)request.getAttribute("rq_b_bname");
String filename = "";
if(rq_b_bname == null){
	filename = "diary_list.xls";
}else{
	rq_b_bname = new String(rq_b_bname.getBytes("EUC-KR"),"8859_1");
	filename = "diary_"+rq_b_bname+".xls";
}
response.setContentType("application/vnd.ms-excel; charset=utf-8");
response.setHeader("Content-Disposition", "inline; filename="+filename); 
response.setHeader("Content-Description", "JSP Generated Data");

%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>엑셀리스트</title>
<body>

<h2>독서이력</h2>
<table>
	<tbody>
		<tr>
<c:if test="${fn:length(rqBoardList) > 1 }">
			<td>번호</td>
</c:if>
			<td><strong>아이디</strong></td>
			<td>이름</td>
			<td>학교</td>
			<td>학년</td>
			<td><strong>참가종목</font></strong></td>
			<td><strong></strong></td>
			<td><strong>달성률</strong></td>
			<td><strong>달성일</strong></td>
		</tr>
<c:forEach items="${rqBoardList }" var="rqBoard" varStatus="no">
	<fmt:formatNumber var="b_temp7_number" value="${rqBoard.b_temp7}" pattern="#,###"/>
	<fmt:formatNumber var="b_temp9_number" value="${rqBoard.b_temp9}" pattern="#,###"/>
	<fmt:formatNumber var="calc_page" value="${rqBoard.b_temp7 - rqBoard.b_temp9}" pattern="#,###"/>
	<c:set var="b_temp7_str" value="${courseList[rqBoard.b_temp7] } (${b_temp7_number }쪽)" />
	<c:if test="${empty courseList[rqBoard.b_temp7] }">
		<c:set var="b_temp7_str" value="${rqBoard.b_temp7 }" />
	</c:if>
	
	<fmt:formatNumber var="avg" value="${(rqBoard.b_temp9*1.0 / rqBoard.b_temp7) * 100}" pattern="#.##"/>
	<c:set var="avg" value="${avg}%" />
	<c:if test="${rqBoard.b_temp10 eq '1' }">
		<c:set var="avg" value="<span style='font-weight:bold;color:#FF0000;'>${avg }</span>" />
	</c:if>
	
	<c:set var="b_re_date_str" value="${fn:replace(rqBoard.b_re_date, '-', '/') }" />
		<tr>
<c:if test="${fn:length(rqBoardList) > 1 }">
			<td>${recordcount - no.index } </td>
</c:if>
			<td>${rqBoard.b_id }</td>
			<td>${rqBoard.b_name }</td>
			<td>${rqBoard.b_temp2 }</td>
			<td>${fn:split(rqBoard.b_temp3, '-')[0] }</td>
			<td>${b_temp7_str }</td>
			<td>목표치 : ${b_temp7_number }<br>달성치 : ${b_temp9_number }</td>
			<td>${avg }</td>
			<td>${b_re_date_str }</td>
		</tr>
		<tr>
			<td colspan="9">
			
			<table border="1">
			<thead>
				<tr>
					<th>이름</th>
					<th>도서제목</th>
					<th>날짜</th>
					<th>분류번호</th>
					<th>읽은쪽수</th>
					<th>누적쪽수</th>
				</tr>
			</thead>
			<tbody>
	<c:forEach items="${rqBoard.boardList }" var="board" varStatus="no2">
		<c:set var="b_name" value="${board.b_name }" />
			<c:if test="${(config.a_type eq 'Y' and board.b_open eq 'N') or config.a_type eq 'T'}">
				<c:if test="${is_ad_cms ne 'Y' }">
					<c:set var="b_name" value="비공개" />
				</c:if>
			</c:if>
				<tr>
					<td>${board.b_name }</td>
					<td>${board.b_subject }</td>
					<td>${fn:substring(board.b_regdate,0,10) }</td>
					<td>${board.b_temp3 }</td>
					<td><fmt:formatNumber value="${board.b_temp1}" pattern="#,###"/></td>
					<td><fmt:formatNumber value="${board.sumpage }" pattern="#,###" /></td>
				</tr>
				<tr>
					<td>독서감상문</td>
					<td colspan="5">${board.b_content }</td>
				</tr>
	</c:forEach>		
			</tbody>
			</table>
			
			</td>
		</tr>
</c:forEach>
	</tbody>
</table>

</body>
</html>