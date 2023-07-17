<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<div class="movieinfo">
	<p class="monthbg"><?=(int)$sh_b_sdate_m?></p>
</div>

<!-- 리스트movie -->
<div id="board" style="width:100%;">

	<div class="top ${FLAG_MOBILE == false ? 'onlypc' : ''}">
		<div class="cal_top">
			<div class="month_select">
				<a class="t_arrow m_pre" href='?proc_type=list&a_num=${config.a_num}&sh_b_sdate_y=${prevYear}&sh_b_sdate_m=${prevMonth}'><span class="blind">이전 달</span></a> 
				
				<c:set var="thisYear" value="${fn:split(sh_b_sdate_y,'-')}" />
				<c:set var="thisMonth" value="${fn:split(sh_b_sdate_m,'-')}" />
				<span class="t_date">${thisYear[0]}${thisYear[1]}${thisYear[2]}${thisYear[3]}.&nbsp;<strong>${thisMonth[0]}${thisMonth[1]}</strong></span>
	
				<a class="t_arrow m_next" href='?proc_type=list&a_num=${config.a_num}&sh_b_sdate_y=${nextYear}&sh_b_sdate_m=${nextMonth}'><span class="blind">다음 달</span></a>
			</div>
		
			<a class="t_today" href='?proc_type=list&a_num=${config.a_num}&sh_b_sdate_y=${year}&sh_b_sdate_m=${month}'>이번달</a>


		</div>
		<div class="cal_right">
			<jsp:include page="../code.jsp" />
		</div>
	</div>

	<!-- 리스트 테이블 -->
	<div class="table_blist">
	<ul class="blist_ul">
		<c:set var="zz" value="0" />
		<c:forEach items="${boardList}" var="board" varStatus="no">
			<c:set var="img_url" value="${board.b_keyword}" />
			<c:if test="${board.b_file1 ne ''}">
				<c:set var="img_url" value="/board/get_img.do?a_num=${config.a_num}&b_num=${board.b_num}&f_num=1&type=img" />
			</c:if>
			<c:if test="${zz%2 eq 0 and zz > 0}">
				</tr><tr>
			</c:if>
			<li>
				<div class="basicinfo">
					<p class="thum">
						<span>
							<a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}"><img src="${img_url}" width="110" height="160" class="photo" alt="${board.b_subject} 관련사진" /></a>
						</span>
					</p>
					<dl>
						<dt>
							<a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}">${b_cate_str[board.b_cate]}${fn:length(board.b_subject) > 15 ? fn:substring(board.b_subject, 0, 15) : board.b_subject}</a>
						</dt>
						<dd>
							<ul>
								<c:set var="b_sdate_str" value="" />
								<c:if test='${board.b_sdate ne ""}'>
									<c:set var="b_sdate_str" value="${fn:substring(board.b_sdate, 0, 4)}년 ${fn:substring(board.b_sdate, 5, 7)}월 ${fn:substring(board.b_sdate, 8, 10)}일" />
									<fmt:parseDate value="${board.b_sdate}" pattern="yyyy-MM-dd" var="sdate" />
									<fmt:formatDate value="${sdate}" pattern="E" var="week"/>
								</c:if>
								<li><span>일시</span>${b_sdate_str} (${week})</li>
								<li><span>감독</span>${board.b_temp3}</li>
								<li><span>장르</span>${board.b_temp5}</li>
								<li><span>시간</span>${board.b_temp7}</li>
							</ul>
						</dd>
						<!-- <dd>${board.b_content}</dd> -->
					</dl>
				</div>
			</li>
			<c:set var="zz" value="${zz+1}" />
		</c:forEach>
		<c:if test="${zz%2>0}">
			<c:set var="loopcnt" value="${2-(zz%2)}" />
			<c:forEach var="t" begin="1" end="${loopcnt}" step="1">
				<td>&nbsp;</td>
			</c:forEach>
		</c:if>
		<c:if test="${fn:length(boardList) == 0}">
			<li>
				<div class="basicinfo">
					<p class="thum">
						<span>&nbsp;</span>
					</p>
					<dl>
						<dt>
							등록자료가 없습니다.
						</dt>
						<dd>
							&nbsp;
						</dd>
					</dl>
				</div>
			</li>
		</c:if>
		</ul>
	</div>
	<!-- //리스트 테이블 -->

	
	<!-- 버튼 -->
	<div class="board_button">
		<c:if test="${is_ad_cms eq 'Y'}">
		
		</c:if>
		<div class="fr">
			<c:if test="${is_write eq 'Y'}">
			<span class="bt"><a href="?proc_type=write&a_num=${config.a_num}&prepage=${nowPageEncode}" class="ndls_btn blue">등록</a></span>
			</c:if>
		</div>
	</div>
	<!-- //버튼 -->

	<!-- 페이징 -->
	<div class="board_paginate">&nbsp;</div>
	<!-- //페이징 -->

	<!-- 게시물 검색 -->
	<div style="margin:0 auto;text-align:center;">
	</div>
	<!-- //게시물 검색 -->

</div>
<!-- //리스트 -->
