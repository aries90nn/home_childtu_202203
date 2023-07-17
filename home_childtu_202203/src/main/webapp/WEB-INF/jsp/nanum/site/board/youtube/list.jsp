<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
		<input type="hidden" name="status" value=""/>
		<input type="hidden" name="a_num" value="${config.a_num}" />
		<input type="hidden" name="chk_all" id="chk_all" />		<!-- 전체체크 -->
		<input type="hidden" name="prepage" value="${nowPage}" />
		<input type="hidden" name="board_token" value="${board_token}" />
	</div>
	</c:if>

	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="now_date" />
	<!-- 리스트 테이블 -->
	<div class="photo_list">
		<ul>
		
		<c:choose>
		<c:when test="${fn:length(boardList) > 0}">
			<c:set var="vCnt" value="${fn:length(boardList)}" />
			<c:forEach items="${boardList}" var="board" varStatus="no">
			<li>
				<c:choose>
					<c:when test="${board.b_file1 ne '' }">
					<p class="img"><a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}"><img src="/board/get_img.do?a_num=${config.a_num}&b_num=${board.b_num}&f_num=1&type=img" class="photo" alt="${board.b_subject} 관련사진" /></a></p>
					</c:when>
					<c:otherwise>
					<p class="img"><a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}"><img src="https://img.youtube.com/vi/${board.b_temp1}/maxresdefault.jpg" class="photo" alt="${board.b_subject} 관련사진" /></a></p>
					</c:otherwise>
				</c:choose>

				
				<div class="subject" style="${board.txt_title_style}">
					<c:if test="${is_ad_cms eq 'Y'}">
						<input type="checkbox" name="chk" value="${board.b_num}" title="해당 게시글 선택" />
					</c:if>
					${board.txt_show}
					<c:if test="${board.b_type eq 'N'}"><span class='category_color'>${b_cate_str[board.b_cate]}</span></c:if>
					<a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}">
						<c:if test="${board.newIcon}"><img src='/nanum/site/board/nninc_simple/img/icon_new.gif' alt='NEW(새글)' /></c:if>${board.b_subject}</a>
					<c:if test="${config.a_command eq 'Y' and board.b_c_count ne '0'}"><span class="reply_count">(${board.b_c_count})</span></c:if>
					
					<c:if test="${(config.a_type eq 'Y' and board.b_open eq 'N') or config.a_type eq 'T'}">
						<img src='/nanum/site/board/nninc_simple/img/icon_key.gif' alt='비밀글' />
					</c:if>
				</div>
				<!-- <p class="date">${fn:replace(fn:substring(board.b_regdate,0,10),"-", "-")}</p> -->
			</li>
			</c:forEach>
			</c:when>
			<c:otherwise>
					<li style="width:100%;text-align:center;">등록된 내용이 없습니다.</li>
			</c:otherwise>
	</c:choose>
	
			</ul>
	</div>

	<!-- 버튼 -->
	<div class="board_button">
		<c:if test="${is_ad_cms eq 'Y'}">
		<div class="fl">
			<ul>
				<li class="pt"><a href="javascript:checkAll();" class="board_lbtn"><span>전체 선택/해제</span></a></li>
				<li class="pt"><a href="javascript:delete2();" class="board_lbtn"><span>선택 게시글삭제</span></a></li>
			</ul>
		</div>
		</c:if>
		<div class="fr">
			<c:if test="${is_write eq 'Y'}">
			<span class="bt"><a href="?proc_type=write&a_num=${config.a_num}&prepage=${nowPageEncode}" class="con_btn orange">글쓰기</a></span>
			</c:if>
			<span class="bt"><a href="?proc_type=list&a_num=${config.a_num}" class="con_btn blue">목록</a></span>
		</div>

	</div>
	<!-- //버튼 -->


	<!-- //리스트 테이블 -->
	<c:if test="${is_ad_cms eq 'Y'}">
	</form>
	</c:if>

	<!-- 페이징 -->
	<div class="board_paginate">
		${pagingtag }
	</div>
	<!-- //페이징 -->
	
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
						<option value="b_subject" ${"b_subject" == param.v_search ? 'selected="selected"' : '' }>제목</option>
						<option value="b_content" ${"b_content" == param.v_search ? 'selected="selected"' : '' }>내용</option>
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
	
	