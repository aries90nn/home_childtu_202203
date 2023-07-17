<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ko" />
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>설문조사 결과보기</title>
	
	<link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/skin01/common/css/all.css" />
	<link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/skin01/common/css/head_layout.css" />
	<link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/skin01/common/css/foot_layout.css" />
	<link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/skin01/common/css/sub_design.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/site/poll/css/common.css" />

	<script type="text/javascript" src="/nanum/site/poll/js/common.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
</head>

<body>
<div style="padding:10px 10px 10px 10px">
<!-- 리스트 -->
<div id="poll_popup">
	<!-- 타이틀 -->
	<div class="table_bview">
		<div class="count"><span>${param.sun}</span></div>
		<div class="subject">${question.poq_question}</div>
	</div>
	<!-- //타이틀 -->
	
	
	<!-- 리스트 테이블 -->
	<div class="table_blist">
		<fieldset>
		<legend>게시물 리스트</legend>
			<table cellspacing="0">
			<caption>리스트</caption>
				<colgroup>
				<col width="10%" />
				<col width="" />
				<col width="20%" />
				</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">내용</th>
					<th scope="col">등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="num" value="${fn:length(resultList)}" />
				<c:forEach items="${resultList}" var="result" varStatus="no">
				<tr>
					<td>${num}</td>
					<td class="title">${result.por_result}</td>
					<td class="eng">${fn:substring(result.por_wdate,0,10)}</td>
				</tr>
				<c:set var="num" value="${num-1}" />
				</c:forEach>
				<c:if test="${fn:length(resultList) == 0}">
				<tr>
					<td></td>
					<td class="title"></td>
					<td></td>
				</tr>
				</c:if>

			</tbody>
			</table>
		</fieldset>
	</div>
	<!-- //리스트 테이블 -->



	<!-- 버튼 -->
	<div class="poll_button2">
		<a href="javascript:;" onclick="javascript:window.close();" title="창닫기" class="con_btn blue" >창닫기</a>
	</div>
	<!-- //버튼 -->



</div>
<!-- //리스트 -->
</div>
</body>
</html>
