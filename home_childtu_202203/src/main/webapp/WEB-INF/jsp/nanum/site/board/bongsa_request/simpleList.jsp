<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<!-- 리스트 -->
<div id="board" style="width:${config.a_width};">
	<div class="board_total">
		<div class="board_total_left">
		<c:choose>
			<c:when test="${config.a_ad_cms eq 'Y'}">
				<c:choose>
					<c:when test="${ss_m_id == null}">
						<a href="?proc_type=login&a_num=${config.a_num}&prepage=${nowPageEncode}"><img src="/nanum/site/board/nninc_simple/img/key.gif" alt="" /></a> 
					</c:when>
					<c:otherwise>
						<a href="/board/logout.do?a_num=${config.a_num}&prepage=${nowPageEncode}"><img src="/nanum/site/board/nninc_simple/img/unkey.gif" alt="" /></a> 
					</c:otherwise>
				</c:choose>
				전체 <strong>${recordcount}</strong>개 (페이지 <strong class="board_orange">${v_page}</strong>/${totalpage})
			</c:when>
			<c:otherwise>
				<img src="/nanum/site/board/nninc_simple/img/total_ic.gif" alt="" /> 전체 <strong>${recordcount}</strong>개 (페이지 <strong class="board_orange">${v_page}</strong>/${totalpage})
			</c:otherwise>
		</c:choose>

		</div>
		<div class="board_total_right">
			<jsp:include page="../code.jsp" />
		</div>
	</div>
	
	<c:if test="${is_ad_cms eq 'Y'}">
	<form id= "frm_list" action="${path_info }" method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" name="a_num" value="${config.a_num}" />
		<input type="hidden" name="chk_all" id="chk_all" />		<!-- 전체체크 -->
		<input type="hidden" name="prepage" value="${nowPage}" />
		<input type="hidden" name="board_token" value="${board_token}" />
	</div>
	</c:if>
	
	<c:set var="colCnt" value="7" />
	
	<!-- 리스트 테이블 -->
	<div class="table_blist">
	<table cellspacing="0" summary="${title} 의 번호, 제목, 글쓴이, 등록일, 조회수를 확인">
	<caption>${title}</caption>
		<colgroup>
		<col width="50" />
		<col width="" />
		<col width="" />
		<col width="" />
		<col width="" />
		<col width="" />
		<col width="" />
		<%//PC일때만 %>
		<c:if test="${pc_version eq 'Y'}">
			<col width="60" />
			<c:set var= "colCnt" value="${colCnt+1}"/>
		</c:if>
		</colgroup>
	<thead>
		<tr>
			<th scope="col">번호</th>
			<th scope="col">신청자명</th>
			<th scope="col">도서관</th>
			<th scope="col">희망일</th>
			<th scope="col">희망시간대</th>
			<th scope="col">봉사활동시간</th>
			<th scope="col">상태</th>
			<%//PC일때만 %>
			<c:if test="${pc_version eq 'Y'}">
			<th scope="col" class="th_none">신청일</th>
			</c:if>
		</tr>
	</thead>
	<tbody>
		<jsp:useBean id="now" class="java.util.Date" />
		<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="now_date" />
	
	<c:forEach items="${boardList}" var="board" varStatus="no">
		<c:set var="b_name" value="${board.b_name }" />
		<c:if test="${(config.a_type eq 'Y' and board.b_open eq 'N') or config.a_type eq 'T'}">
			<c:if test="${is_ad_cms ne 'Y' }">
				<c:set var="b_name" value="비공개" />
			</c:if>
		</c:if>
		<c:set var="view_url" value="/contents.do?proc_type=view&a_num=${board.a_num }&b_num=${board.b_num }" />
		<c:if test="${!empty board.a_site_dir }">
			<c:set var="view_url" value="/${board.a_site_dir }${view_url }" />
		</c:if>
	<tr>
		<td>${recordcount - ((v_page-1) * pagesize + no.index) }</td>
		<td style="${board.txt_title_style}">
			<a href="${view_url }" target="_blank">${board.b_subject  }</a>
		</td>
		<td><a href="${view_url }" target="_blank">${board.libname }</a></td>
		<td>${fn:replace(fn:substring(board.b_sdate,0,10),"-", ".")}</td>
		<td>${board.b_temp2 }</td>
		<td>${board.b_temp5 }</td>
		<td>${board.b_temp8 }</td>
		
		<%//PC일때만 %>
		<c:if test="${pc_version eq 'Y'}">
		<td>${fn:replace(fn:substring(board.b_regdate,0,10),"-", ".")}</td>
		</c:if>

		
	</tr>
	</c:forEach>
	<c:if test="${fn:length(boardList) == 0}">
		<tr>
			<td class="center" colspan="${colCnt}">등록된 내용이 없습니다.</td>
		</tr>
	</c:if>

			</tbody>
			</table>
	</div>
	<!-- //리스트 테이블 -->
	
	<c:if test="${is_ad_cms eq 'Y'}">
	</form>
	</c:if>

	<!-- 버튼 -->
	<div class="board_button">
		<c:if test="${is_ad_cms eq 'Y'}">
		<div class="fl">
			&nbsp;
		</div>
		</c:if>
		<div class="fr">
			&nbsp;
		</div>

	</div>
	<!-- //버튼 -->

	<!-- 페이징 -->
	<div class="board_paginate">
		${pagingtag }
	</div>
	<!-- //페이징 -->

<%--
	<!-- 게시물 검색 -->
	<div style="margin:0 auto;text-align:center;">
		<div class="board_search">
			<form id="frm_sch" action="${path_info }" method="get">
			<input type="hidden" name="a_num" value="${config.a_num}" />
			<input type="hidden" name="v_cate" value="${param.v_cate}" />
				<fieldset>
				<legend>게시물 검색</legend>
				<ul>
					<li><select id="v_search" name="v_search" title="검색형태 선택" style="width:70px;" >
						<option value="b_name" ${"" == v_search ? 'selected="selected"' : '' }>제목</option>
						<option value="b_content" ${"b_content" == v_search ? 'selected="selected"' : '' }>내용</option>
					</select></li>
					<li><input type="text" size="25" title="검색어를 입력하세요" id="p_keyword" name="v_keyword" class="search_input" value="${param.v_keyword}" /></li>
					<li><input type="image" src="/nanum/site/board/nninc_simple/img/search_bt.gif" id="search_bt" name="search_bt" class="search_bt" alt="검색" /></li>
				</ul>
				</fieldset>
			</form>
		</div>
	</div>
	<!-- //게시물 검색 -->
 --%>

</div>
<!-- //리스트 -->