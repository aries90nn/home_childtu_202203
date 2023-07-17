<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<!-- 상단내용 --> 
${pollconf.poc_tophtml }

<link rel="Stylesheet" type="text/css" href="/nanum/site/poll/css/common.css" />
<script type="text/javascript" src="/nanum/site/poll/js/common.js"></script>
<!-- 리스트 -->
<div id="poll">
	<form id= "frm_list" action="" method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" id="chk_all" name="chk_all" /><!-- 전체체크 사용하려구.. -->
	</div>

	<div class="poll_total">
		<div class="poll_total_left">
			<img src="/nanum/site/poll/img/total_ic.gif" width="9" height="8" alt="" /> 전체 <strong>${recordcount}</strong>개 (페이지 <strong class="poll_orange">${v_page}</strong>/${totalpage})
		</div>
		<div class="poll_total_right">
		</div>
	</div>
	
	<!-- 리스트 테이블 -->
	<div class="table_blist">
		<fieldset>
		<legend>게시물 리스트</legend>
			<table cellspacing="0" summary="게시판명 게시글 정보를 제공하고 제목을 클릭하면 상세내용화면으로 이동됩니다.">
			<caption>리스트</caption>
				<colgroup>
				<col width="10%" />
				<col width="*" />
				<col width="*" />
				<col width="15%" />
				<col width="13%" />
				<col width="13%" />
				</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">주제</th>
					<th scope="col">설문기간</th>
					<th scope="col">참여자수</th>
					<th scope="col">상태</th>
					<th scope="col" class="th_none">결과</th>
				</tr>
			</thead>
			<tbody>
			
				<jsp:useBean id="now" class="java.util.Date" />
				<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
				<c:forEach items="${pollList}" var="poll" varStatus="no">
				
				<!-- 변수선언 -->
				<c:set var="po_chk_str2" value="<span class='orange'><strong class='finish'>종료</strong></span>" />
				<c:if test="${poll.po_sdate > today}">
					<c:set var="po_chk_str2" value="<span class='orange'><strong>준비중</strong></span>" />
				</c:if>
				<c:set var="link_str" value="javascript:alert('설문참가 기간이 아닙니다.');" />
				
				<c:if test="${poll.po_edate >= today and poll.po_sdate <= today}">
					<c:set var="po_chk_str2" value="<span class='orange'><strong>진행</strong></span>" />
					<c:set var="link_str" value="poll.do?po_pk=${poll.po_pk}&prepage=${nowPageEncode}" />
				</c:if>
				
				<tr>
					<td>${recordcount - ((v_page-1) * pagesize + no.index) }</td>
					<td class="title"><a href="${link_str}">${poll.po_subject}</a></td>
					<td>${poll.po_sdate} ~ ${poll.po_edate}</td>
					<td>${poll.po_count}명</td>
					<td>${po_chk_str2}</td>
					<td><a href="result.do?po_pk=${poll.po_pk}&prepage=${nowPageEncode}" class="look">보기</a></td>
				</tr>
				</c:forEach>
				<c:if test="${fn:length(pollList) == 0}">
				<tr>
					<td scope="row" class="center" colspan="6">데이터가 없습니다.</td>
				</tr>
				</c:if>

			</tbody>
			</table>
		</fieldset>
	</div>
	<!-- //리스트 테이블 -->
	
	</form>


	<!-- 버튼 -->
	<div class="poll_button">

	</div>
	<!-- //버튼 -->

	<!-- 페이징 -->
	<div class="poll_paginate">
		${pagingtag }
	</div>
	<!-- //페이징 -->
	
	<!-- 게시물 검색 -->
	<div style="margin:0 auto;text-align:center;">
		<div class="poll_search">
			<form id="frm_sch" action="" method="get">
				<fieldset>
				<legend>게시물 검색</legend>
				<ul>
					<li><select id="v_search" name="v_search" title="검색형태 선택" style="width:70px;" >
						<option value="po_subject"  ${"po_subject" == param.v_search ? 'selected="selected"' : '' }>제목</option>
					</select></li>
					<li><input type="text" size="25" title="검색어를 입력하세요" id="p_keyword" name="v_keyword" class="search_input" value="${param.v_keyword}" /></li>
					<li class="bt"><input type="image" src="/nanum/site/board/nninc_simple/img/search_bt.gif" id="search_bt" name="search_bt" class="search_bt" alt="검색" /></li>
				</ul>
				</fieldset>
			</form>
		</div>
	</div>
	<!-- //게시물 검색 -->
	
</div>
<!-- //리스트 -->
	

<!-- 하단내용 --> 
${pollconf.poc_btmhtml }