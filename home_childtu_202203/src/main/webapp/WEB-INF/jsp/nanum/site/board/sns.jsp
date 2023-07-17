<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="title" content="${board.b_subject}" />
<meta name="description" content="${board.b_content}" />

<c:set var="tp_dir" value="/data/board/${a_tablename}/" />
<c:forEach var="t" begin="1" end="5" step="1">
	<!-- 가변변수 담기.. -->
	<c:set var="b_file">b_file${t}</c:set>
	<c:set var="b_file_name">${board[b_file]}</c:set>
	<c:set var="filename" value="${fn:split(b_file_name,'.')}" />
	<c:set var="fileend" value="${fn:toLowerCase(fn:trim(filename[ fn:length(filename)-1 ]))}" />
	<c:set var="imgsrc" />
	
	<c:if test="${fileend eq 'gif'}">
		<c:set var="imgsrc" value="${tp_dir}${b_file_name}" />
	</c:if>
	<c:if test="${fileend eq 'jpg'}">
		<c:set var="imgsrc" value="${tp_dir}thum/${b_file_name}" />
	</c:if>
	
	<c:if test="${imgsrc ne ''}">
		<link rel="image_src" href="${SERVER_NAME}${imgsrc}" />
	</c:if>
</c:forEach>
<script type="text/javascript">
function redirectPage() {
	var url = "${linkurl}";
	location.href = url;
}
redirectPage();
</script>
</head>
<body>
</body>
</html>