<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ko" />
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>완주증서</title>

	<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/skin02/common/css/all.css" />
	<link rel="stylesheet" type="text/css" href="/nanum/site/board/mara_request/css/common.css" />

	<script type="text/javascript" src="/nanum/site/common/js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/jquery-cookie.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/jquery-rightClick.js"></script>
	
	<script type="text/javascript" src="/nanum/ncms/common/js/design_default.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>

	<script type="text/javascript">
	$(function(){
		window.print();
	});
	
	</script>
</head>

<body>

<%--코스별 쪽수 문자열 --%>
<c:if test="${!empty board.b_temp7 }">
	<fmt:formatNumber var="pagecnt" value="${board.b_temp7 }" pattern="#,###" />
</c:if>
<c:set var="b_temp7_str" value="${courseList[board.b_temp7] } (${pagecnt }쪽)" />
<c:if test="${empty courseList[board.b_temp7] }">
	<c:set var="b_temp7_str" value="${board.b_temp7 }" />
</c:if>

<c:if test="${!empty board.b_temp9 }">
	<fmt:formatNumber var="b_temp9_str" value="${board.b_temp9 }" pattern="#,###" />
</c:if>


<div class="complete_paper">
<div class="content">
	<div class="top">
		<img src="/nanum/site/board/mara_request/img/com_logo.png" class="img1" alt="" />
		<img src="/nanum/site/board/mara_request/img/com_text.png" class="img2" alt="" />
	</div>
	<h1>완주증서</h1>
	<ul>
		<li><span>성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명&nbsp;&nbsp;&nbsp;${board.b_name }</span></li>
<c:if test="${!empty board.b_temp2}">		
		<li><span>학교(학년)&nbsp;&nbsp;&nbsp;${board.b_temp2 }&nbsp;&nbsp;${fn:substring(board.b_temp3, 0, 1) }학년</span></li>
</c:if>
		<li><span>부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;문&nbsp;&nbsp;&nbsp;${b_temp7_str }</span></li>
		<li><span>기&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;록&nbsp;&nbsp;&nbsp;${b_temp9_str}쪽</span></li>
	</ul>
	<div class="center">
위의 학생(분)은 ${mara_rq_cfg.title }<br>
 상기종목에 참가하여 성실한 독서활동으로<br>
 완주하였기에 이 증서를 드립니다.
	</div>
	<div class="bottom">
		2019년&nbsp;&nbsp;9월&nbsp;12일
	</div>
	<div class="sign">
		대구광역시 달서구청장&nbsp;&nbsp;
	</div>
	
</div>
</div>
</body>
</html>
