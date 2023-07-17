<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.net.URLEncoder"%>

<link href="/nanum/ndls/common/css/common.css" rel="stylesheet" type="text/css">
<link href="/nanum/ndls/common/css/content.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/${BUILDER_SKIN }/common/css/ndls.css" />

<link rel="stylesheet" type="text/css" href="/nanum/site/board/nninc_photo_mobile/css/lightbox.css">
<link rel="stylesheet" type="text/css" href="/nanum/site/board/nninc_simple/css/common.css?ver=0922">
<script type="text/javascript" src="/nanum/site/board/nninc_simple/js/common.js?ver=0922"></script>
<script type="text/javascript" src="/nanum/site/board/nninc_simple/js/board.js?ver=0922"></script>
<script type="text/javascript" src="/nanum/site/board/nninc_photo_mobile/js/spica.js"></script>
<script type="text/javascript" src="/nanum/site/board/nninc_photo_mobile/js/lightbox.js"></script>

<!-- 리스트 -->
<div id="board">
	
	<div class="board_total">
		<div class="board_total_left">
			전체 <strong>${recordcount}</strong>개 (페이지 <strong class="board_orange">${v_page}</strong>/${totalpage})
		</div>
	</div>

	<!--  -->
	<div class="table_blist">
		<table cellspacing="0" summary="${title} 의 프로그램명, 운영기간, 신청현황, 신청상태를 확인">
		<caption>프로그램 신청내역</caption>
		<colgroup>
			<col width="50px" />
			<col width="25%"/>
			<col />
			<col width="20%"/>
			<col width="70px"/>
		</colgroup>
		<thead>
		<tr>
			<th scope="col">번호</th>
			<th scope="col">분류</th>
			<th scope="col">제목</th>
			<th scope="col">등록일</th>
			<th scope="col">조회수</th>
		</tr>
		</thead>
		<tbody>
		<c:if test="${fn:length(boardList) > 0 }">
		<c:forEach items="${boardList }" var="board" varStatus="no">
		<tr>
			<td>${recordcount - ((v_page-1) * pagesize + no.index) }</td>
			<td>${board.bbs_name }</td>
			<td>
				<a href="/main/contents.do?proc_type=view&a_num=${board.a_num }&b_num=${board.b_num }&prepage=${NOWPAGE}">
					${board.b_subject }
				</a>
			</td>
			<td>${fn:substring(board.b_regdate,0,10)}</td>
			<td>${board.b_count }</td>
		</tr>
		</c:forEach>
		</c:if>
		<c:if test="${fn:length(boardList) <= 0 }">
		<tr>
			<td class="center" colspan="5">등록된 내용이 없습니다.</td>
		</tr>
		</c:if>
		</tbody>
	</table>
		

	<!-- 페이징 -->
	<div class="board_paginate">
		${pagingtag }
	</div>
	<!-- //페이징 -->

</div>
</div>