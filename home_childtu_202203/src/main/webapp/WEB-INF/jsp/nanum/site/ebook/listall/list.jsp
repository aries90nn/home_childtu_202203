<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<link rel="Stylesheet" type="text/css" href="/nanum/site/ebook/listall/css/common.css" />
<script type="text/javascript" src="/nanum/site/ebook/listall/js/common.js"></script>


<!-- 리스트 -->
<div id="poll">

	<div class="poll_total">
		<div class="poll_total_left">
			<img src="/nanum/site/ebook/listall/img/total_ic.gif" width="9" height="8" alt="" /> 전체 <strong>${recordcount}</strong>개 (페이지 <strong class="poll_orange">${v_page}</strong>/${totalpage})
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
				<col width="70" />
				<col width="" />
				<col width="80" />
				<col width="100" />
				</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">제목</th>
					<th scope="col">페이지수</th>
					<th scope="col" class="th_none">등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ebookList}" var="ebook" varStatus="no">
				<tr>
					<td>${recordcount - ((v_page-1) * pagesize + no.index) }</td>
					<td class="title"><a href="/ebook/index.do?eb_size=max&eb_pk=${ebook.eb_pk}" onclick="window.open(this.href,'ebook','width=1000,height=650');return false;" title="새창으로 열림">${ebook.eb_subject}</a></td>
					<td>${ebook.ebp_cnt}</td>
					<td>${fn:substring(ebook.eb_wdate, 0, 10)}</td>
				</tr>
				</c:forEach>
				<c:if test="${fn:length(ebookList) == 0}">
				<tr>
					<td></td>
					<td class="title"></td>
					<td></td>
					<td></td>
				</tr>
				</c:if>
			</tbody>
			</table>
		</fieldset>
	</div>
	<!-- //리스트 테이블 -->
	
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
					<li><select id="v_search" name="v_search" title="검색형태 선택" class="nor" style="width:70px;" >
					<option value="eb_subject"  ${"eb_subject" == v_search ? 'selected="selected"' : '' }>제목</option>
					</select></li>
					<li><input type="text" size="25" title="검색어를 입력하세요" id="p_keyword" name="v_keyword" class="search_input" value="${v_keyword}" /></li>
					<li><input type="image" src="/nanum/site/board/nninc_simple/img/search_bt.gif" id="search_bt" name="search_bt" class="search_bt" alt="검색" /></li>
				</ul>
				</fieldset>
			</form>
		</div>
	</div>
	<!-- //게시물 검색 -->
	


</div>
<!-- //리스트 -->
