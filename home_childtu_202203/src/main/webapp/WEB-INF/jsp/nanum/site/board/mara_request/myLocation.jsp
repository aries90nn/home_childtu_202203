<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 내용영역 -->
<link rel="stylesheet" type="text/css" href="/nanum/site/board/${config.a_level }/css/common.css">

<!-- 쓰기 -->
<div id="board" style="width:${config.a_width};">
	<!-- 쓰기 테이블 -->
	<div class="table_bwrite">

			<table cellspacing="0" summary="${title}의 이름, 비밀번호, 내용을 입력">
			<caption>${title}</caption>
				<colgroup>
				<col width="130" />
				<col width="" />
				</colgroup>
			<thead>
				<tr>
					<th scope="col" class="th_end" colspan="2">참가정보</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row">참가자명</th>
					<td>
						${board.b_name }
					</td>
				</tr>
				<tr>
					<th scope="row">참가종목</th>
					<td>
						${courseList[board.b_temp7] } (<fmt:formatNumber value="${board.b_temp7 }" pattern="#,###" />)쪽
					</td>
				</tr>
				<tr>
					<th scope="row">개인누적현황</th>
					<td>
						<c:set var="total_page" value="${empty board.b_temp9 ? '0' : board.b_temp9 }" />
						<span style="font-weight:bold;"><fmt:formatNumber value="${total_page }" pattern="#,###" /></span>쪽
					</td>
				</tr>
			</tbody>
			</table><br /><br />
</div>

<c:choose>
	<c:when test="${board.b_temp7 eq '3000' }">
		<c:set var="point1" value="600" />
		<c:set var="point2" value="1200" />
		<c:set var="point3" value="1800" />
		<c:set var="point4" value="2400" />
		<c:set var="point5" value="3000" />
	</c:when>
	<c:when test="${board.b_temp7 eq '5000' }">
		<c:set var="point1" value="1000" />
		<c:set var="point2" value="2000" />
		<c:set var="point3" value="3000" />
		<c:set var="point4" value="4000" />
		<c:set var="point5" value="5000" />
	</c:when>
	<c:when test="${board.b_temp7 eq '10000' }">
		<c:set var="point1" value="2000" />
		<c:set var="point2" value="4000" />
		<c:set var="point3" value="6000" />
		<c:set var="point4" value="8000" />
		<c:set var="point5" value="10000" />
	</c:when>
	<c:when test="${board.b_temp7 eq '21097' }">
		<c:set var="point1" value="4200" />
		<c:set var="point2" value="8400" />
		<c:set var="point3" value="12600" />
		<c:set var="point4" value="16800" />
		<c:set var="point5" value="21097" />
	</c:when>
</c:choose>

<div class="mara_check">
    <div class="user" style="left:${avg}%"><img src="/nanum/site/board/${config.a_level }/img/${board.b_temp5 eq '여' ? 'bookpeo.png' : 'bookman02.png' }" alt="독서마라토너" /></div>
	<div class="finish"><img src="/nanum/site/board/${config.a_level }/img/finish.gif" alt="finish" /></div>
	<div class="mara_course">
		<div class="bar_bg">
			<span class="pink_bar" style="width:${avg}%"> </span>
		</div>
     </div>
     <div class="number">
		<em class="spot1">${point1 }km</em>
		<em class="spot2">${point2 }km</em>
		<em class="spot3">${point3 }km</em>
		<em class="spot4">${point4 }km</em>
		<em class="spot5">${point5 }km</em>
	</div>
</div>
<!-- //내용영역 -->