<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.net.URLEncoder"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<link rel="Stylesheet" type="text/css" href="/nanum/site/edusat/css/common.css" />
<script type="text/javascript" src="/nanum/site/edusat/js/common.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>


<div class="con_tab tab_b">
<c:set var="class_nm" value="no${fn:length(libList)}" />
<c:set var="class_nm" value="${fn:length(libList) > 7 ? 'no5' : class_nm}" />
<ul class="${class_nm}">
	<c:forEach items="${libList}" var="lib" varStatus="no">
	<li><a href="?sh_ct_idx=${lib.ct_idx}" ${sh_ct_idx eq lib.ct_idx ? 'class="on"' : '' }>${lib.ct_name}</a></option>
	</c:forEach>
</ul>
</div>


<c:choose>
	<c:when test="${libConf != null and libConf.ec_tophtml ne '' }">
		${libConf.ec_tophtml}
	</c:when>
	<c:otherwise>
		${ec_tophtml}
	</c:otherwise>
</c:choose>

<!--<div class="icon_box">
	<div class="area icon9">
		<p class="bx_tit"><strong>프로그램 신청</strong> 전 꼭 읽어보세요<span class="oblique">!</span></p>
		<ul class="list">
			<li>프로그램명과 프로그램 상태를 확인하신 후 신청해주세요.</li>
			<li>프로그램 대상(연령)을 확인해주세요.<br />
			(해당연령이 아닐 경우 임의로 취소될 수 있습니다.)</li>
			<li>선착순 신청이므로 정원 초과될 경우 대기자로 신청됩니다</li>
			<li>프로그램 신청 취소는 개강 3일전까지 반드시 연락주세요.</li>
		</ul>
	</div>
</div>-->

<!-- 검색영역 -->
<div class="search_tit mt50">
	<h3 class="tit">프로그램 목록</h3>
	<div class="search_box_fr">
<form name="frm_edu" method="get" action="list.do">
<input type="hidden" name="sh_ct_idx" value="${sh_ct_idx}" />
		<p class="select_style">
			<select name="v_search" class="select_styleselect">
				<option value="all" ${v_search eq '' ? "selected" : ""}>전체</option>
				<option value="edu_content" ${v_search eq 'edu_content' ? "selected" : ""}>내용</option>
				<option value="edu_subject" ${v_search eq 'edu_subject' ? "selected" : ""}>제목</option>
			</select>			
		</p>
		<p class="txtbox"><label for="program_search">검색어를 입력해주세요</label><input type="text" id="program_search" name="v_keyword" class="txt_input" value="${v_keyword}"  /></p>
		<p class="bt"><input type="submit" value="검색하기" class="con_btn blue" /></p>
</form>
	</div>
</div>
<!--// 검색영역 -->

<!--총 <strong>${recordcount }</strong>개의 프로그램이 등록되어 있습니다.-->

<!-- 프로그램리스트 -->
<div class="program_list">
	<div class="section">

<c:forEach items="${edusatList}" var="edusat" varStatus="no">
	
		<c:set var="edu_week_str" value="${fn:replace(edusat.edu_week, '0', '일') }"></c:set>
		<c:set var="edu_week_str" value="${fn:replace(edu_week_str, '1', '월') }"></c:set>
		<c:set var="edu_week_str" value="${fn:replace(edu_week_str, '2', '화') }"></c:set>
		<c:set var="edu_week_str" value="${fn:replace(edu_week_str, '3', '수') }"></c:set>
		<c:set var="edu_week_str" value="${fn:replace(edu_week_str, '4', '목') }"></c:set>
		<c:set var="edu_week_str" value="${fn:replace(edu_week_str, '5', '금') }"></c:set>
		<c:set var="edu_week_str" value="${fn:replace(edu_week_str, '6', '토') }"></c:set>
		
		<c:choose>
			<c:when test="${fn:contains(edusat.edu_week, '9')}">
				<c:set var="edu_week_str" value="매일"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="edu_week_str" value="${edu_week_str}요일"></c:set>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${edusat.edu_lot_type eq '1'}">
				<c:set var="edu_lot_type_str" value="[추첨]"></c:set>
			</c:when>
			<c:when test="${edusat.edu_lot_type eq '2'}">
				<c:set var="edu_lot_type_str" value="[선착순]"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="edu_lot_type_str" value="${edusat.edu_lot_type}"></c:set>
			</c:otherwise>
		</c:choose>
		
		<jsp:useBean id="now" class="java.util.Date" />
		<c:set var="nowdate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" /></c:set>
	
		<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}${edusat.edu_resdate_h}00' />
		<c:if test="${fn:length(edusat.edu_resdate_h) == 1}">
			<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}0${edusat.edu_resdate_h}00' />
		</c:if>
		
		<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}${edusat.edu_reedate_h}59' />
		<c:if test="${fn:length(edusat.edu_reedate_h) == 1}">
			<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}0${edusat.edu_reedate_h}59' />
		</c:if>
		
		
		<c:set var="requestChk" value="false" />
		<c:set var="colorCss" value="" />		
		<c:choose>
			<c:when test="${reedate < nowdate}">
				<c:set var="str2" value='<span class="btn state3">기간마감</span>' />
				<c:set var="colorCss" value="none" />
			</c:when>
			<c:when test="${resdate > nowdate}">
				<c:set var="str2" value='<span class="btn state1">신청준비</span>' />
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${edusat.edu_inwon <= edusat.req_count}">
						<c:set var="str2" value='<span class="btn state2">신청마감</span>' />
						
						<c:set var="tmp_cnt1" value="${edusat.edu_inwon }" />
						<c:set var="tmp_cnt2" value="${edusat.req_count - edusat.edu_inwon }" />

						<c:if test="${(edusat.edu_inwon + edusat.edu_awaiter) > edusat.req_count }">
							<c:set var="requestChk" value="true" />
						</c:if>
					</c:when>
					<c:otherwise>
						<c:set var="requestChk" value="true"></c:set>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		
		<fmt:parseNumber value="${edusat.req_count }" pattern="0" var="req_count"></fmt:parseNumber>
		<fmt:parseNumber value="${edusat.edu_inwon }" pattern="0" var="edu_inwon"></fmt:parseNumber>
		<c:set var="req_count_str" value="<span style='color:#0000FF;font-weight:bold;'>${edusat.req_count}</span>"></c:set>
		<c:if test="${req_count >= edu_inwon}">
			<c:set var="req_count_str" value="<span style='color:#FF0000;font-weight:bold;'>${edusat.req_count}</span>"></c:set>
		</c:if>
		
		<c:if test="${edusat.end_chk eq 'T'}">
			<c:set var="str2" value='<span class="btn state2">신청마감</span>' />
			<c:set var="requestChk" value="false"></c:set>
		</c:if>
		
		
		<c:set var="receive_str" value=""></c:set>
		<c:set var="br" value=""></c:set>
		<c:choose>
			<c:when test="${fn:contains(edusat.edu_receive_type, '0')}">
				<c:set var="receive_str" value="${receive_str}${br}<img src='/nanum/site/edusat/img/eduicon01.gif' alt='방문접수' />"></c:set>
				<c:set var="br" value=""></c:set>
			</c:when>
			<c:when test="${fn:contains(edusat.edu_receive_type, '1')}">
				<c:set var="receive_str" value="${receive_str}${br}<img src='/nanum/site/edusat/img/eduicon02.gif' alt='전화접수' />"></c:set>
				<c:set var="br" value=""></c:set>
			</c:when>
			<c:when test="${fn:contains(edusat.edu_receive_type, '2')}">
				<c:set var="receive_str" value="${receive_str}${br}<img src='/nanum/site/edusat/img/eduicon03.gif' alt='인터넷접수' />"></c:set>
				<c:set var="br" value=""></c:set>
				<c:choose>
					<c:when test="${edusat.edu_login eq 'Y'}">
						<c:set var="receive_str" value="${receive_str}${br}<img src='/nanum/site/edusat/img/eduicon05.gif' alt='회원접수' />"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="receive_str" value="${receive_str}${br}<img src='/nanum/site/edusat/img/eduicon04.gif' alt='비회원접수' />"></c:set>
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>
		
		<c:set var="tmp_cnt1" value="${req_count}" />
		<c:set var="tmp_cnt2" value="0" />
		<c:if test="${edu_inwon <= req_count}">				
			<c:set var="tmp_cnt1" value="${edu_inwon }" />
			<c:set var="tmp_cnt2" value="${req_count - edu_inwon }" />
		</c:if>
		

		
		<!-- . -->
		<div class="area ${colorCss}">
			<div class="box">
				<p class="lib">${edusat.edu_lib}<c:if test="${edusat.edu_gubun2 ne  '' and edusat.edu_gubun2 != null}"> > ${edusat.edu_gubun2}</c:if></p>
				<p class="pro_title"><a href="view.do?edu_idx=${edusat.edu_idx }&prepage=${nowPageEncode}"><c:if test="${edusat.edu_gubun ne  '' and edusat.edu_gubun != null}"> [${edusat.edu_gubun}]</c:if> ${edusat.edu_subject}</a></p>
				<div class="date">
					<dl>
						<dt>신청기간</dt>
						<dd>${edusat.edu_resdate} ~ ${edusat.edu_reedate}</dd>
					</dl>
					<dl>
						<dt>운영기간</dt>
						<dd>${edusat.edu_sdate} ~ ${edusat.edu_edate} (${edu_week_str})</dd>
					</dl>
				</div>
			</div>
			<div class="box2">
				<div class="info">
					<dl>
						<dt>대상</dt>
						<dd>${edusat.edu_target}</dd>
					</dl>
					<dl>
						<dt>모집현황(대기자)</dt>
						<dd><span class="eng">${tmp_cnt1}(${tmp_cnt2})</span></dd>
					</dl>
				</div>
				<div class="btn_box">
					<c:choose>
						<c:when test="${requestChk eq 'true' }">
							<a href="regist.do?edu_idx=${edusat.edu_idx }&prepage=${nowPageEncode}" class="btn state1">신청하기</a>
						</c:when>
						<c:otherwise>
							${str2}
						</c:otherwise>
					</c:choose>
					<a href="user.do?edu_idx=${edusat.edu_idx }&prepage=${nowPageEncode}" class="btn confirm">등록확인</a>
				</div>
			</div>
		</div>
		<!-- //. -->
</c:forEach>

<c:if test="${fn:length(edusatList) == 0}">
		<div>
			<p style='text-align:center'>강좌가 없습니다.</p>
		</div>
</c:if>
	</div>

	<!-- 페이징 -->
	<div class="board_paginate">
		<c:if test="${prevPage > 0 }">
		<a href="?v_page=${prevPage}&${pageInfo}&${query_string}" class='pre'>이전</a>
		</c:if>
		<c:forEach var="i" begin="${firstPage}" end="${lastPage}">
		<c:choose>
			<c:when test="${v_page == i }">
				<strong>${i}</strong>
			</c:when>
			<c:otherwise>
				<a href="?v_page=${i}&${pageInfo}&${query_string}">${i}</a>
			</c:otherwise>
		</c:choose>
		</c:forEach>
		<c:if test="${nextPage > 0 }">
			<a href="?v_page=${nextPage}&${pageInfo}&${query_string}" class='next'>다음</a>
		</c:if>
	</div>
	<!-- //페이징 -->


</div>

<c:choose>
	<c:when test="${libConf != null and libConf.ec_btmhtml ne '' }">
		${libConf.ec_btmhtml}
	</c:when>
	<c:otherwise>
		${ec_btmhtml}
	</c:otherwise>
</c:choose>
