<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.net.URLEncoder"%>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="default_prepage" value="list.do?sh_ct_idx=${edusat.ct_idx }" />
<c:set var="prepage" value="${empty param.prepage ? default_prepage : param.prepage}" />

<link rel="Stylesheet" type="text/css" href="/nanum/site/edusat/css/common.css" />
<script type="text/javascript" src="/nanum/site/edusat/js/common.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>

<jsp:include page="./config.jsp" />
<jsp:include page="./tophtml.jsp" />


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
		<c:set var="edu_week_str" value="${edu_week_str} 요일"></c:set>
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
<c:set var="edu_resdate_h_str" value="${edusat.edu_resdate_h }" />
<c:if test="${fn:length(edusat.edu_resdate_h) == 1}">
	<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}0${edusat.edu_resdate_h}00' />
	<c:set var="edu_resdate_h_str" value="0${edusat.edu_resdate_h }" />
</c:if>


<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}${edusat.edu_reedate_h}00' />
<c:set var="edu_reedate_h_str" value="${edusat.edu_reedate_h }" />
<c:if test="${fn:length(edusat.edu_reedate_h) == 1}">
	<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}0${edusat.edu_reedate_h}00' />
	<c:set var="edu_reedate_h_str" value="0${edusat.edu_reedate_h }" />
</c:if>

<fmt:parseNumber value="${edusat.req_count }" pattern="0" var="req_count"></fmt:parseNumber>
<fmt:parseNumber value="${edusat.edu_inwon }" pattern="0" var="edu_inwon"></fmt:parseNumber>
<fmt:parseNumber value="${edusat.edu_awaiter }" pattern="0" var="edu_awaiter"></fmt:parseNumber>
<c:set var="requestChk" value="false" />
<c:set var="colorCss" value="" />
		<c:set var="colorCss2" value="color:#1679d3" />
		<c:choose>
			<c:when test="${reedate <= nowdate}">
				<c:set var="str2" value='<a href="#javascript:;" class="btn_sm btn_close">기간종료</a>' />
				<c:set var="colorCss" value="color:#BDBDBD" />
				<c:set var="colorCss2" value="color:#848484" />
			</c:when>
			<c:when test="${resdate > nowdate}">
				<c:set var="str2" value='<a href="#javascript:;" class="btn_sm btn_prepare">신청준비</a>' />
			</c:when>
			<c:otherwise>
				<c:set var="str2" value='<a href="#javascript:;" class="btn_sm btn_receipt">신청중</a>' />
				<c:set var="requestChk" value="true" />
				<c:set var="colorCss" value="" />
				<c:set var="colorCss2" value="color:#1679d3" />
			</c:otherwise>
		</c:choose>
		
		<c:set var="req_count_str" value="<span style='color:#0000FF;font-weight:bold;'>${edusat.req_count}</span>"></c:set>
		<c:if test="${req_count >= edu_inwon}">
			<c:set var="req_count_str" value="<span style='color:#FF0000;font-weight:bold;'>${edusat.req_count}</span>"></c:set>
		</c:if>
		
		<c:if test="${edusat.end_chk eq 'T'}">
			<c:set var="str2" value='<a href="#javascript:;" class="btn_sm btn_end">신청마감</a>' />
			<c:set var="requestChk" value="false"></c:set>
		</c:if>
		
		
		<c:set var="receive_str" value=""></c:set>
		<c:set var="br" value=""></c:set>
		
		<c:if test="${fn:contains(edusat.edu_receive_type, '0') }">
			<c:set var="receive_str" value="${receive_str }${empty receive_str ? '' : ',' }방문"></c:set>
		</c:if>
		<c:if test="${fn:contains(edusat.edu_receive_type, '1') }">
			<c:set var="receive_str" value="${receive_str }${empty receive_str ? '' : ',' }전화"></c:set>
		</c:if>
		<c:set var="receive_str" value="${receive_str }접수" />


<c:set var="receive_str" value=""></c:set>
<c:set var="br" value=""></c:set>
<%--
<c:choose>
	<c:when test="${fn:contains(edusat.edu_receive_type, '0')}">
		<c:set var="receive_str" value="${receive_str}${br}<img src='/nanum/site/edusat/img/eduicon01.gif' alt='방문신청' />"></c:set>
		<c:set var="br" value=""></c:set>
	</c:when>
	<c:when test="${fn:contains(edusat.edu_receive_type, '1')}">
		<c:set var="receive_str" value="${receive_str}${br}<img src='/nanum/site/edusat/img/eduicon02.gif' alt='전화신청' />"></c:set>
		<c:set var="br" value=""></c:set>
	</c:when>
	<c:when test="${fn:contains(edusat.edu_receive_type, '2')}">
		<c:set var="receive_str" value="${receive_str}${br}<img src='/nanum/site/edusat/img/eduicon03.gif' alt='인터넷신청' />"></c:set>
		<c:set var="br" value=""></c:set>
		<c:choose>
			<c:when test="${edusat.edu_login eq 'Y'}">
				<c:set var="receive_str" value="${receive_str}${br}<img src='/nanum/site/edusat/img/eduicon05.gif' alt='회원신청' />"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="receive_str" value="${receive_str}${br}<img src='/nanum/site/edusat/img/eduicon04.gif' alt='비회원신청' />"></c:set>
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>
 --%>

<c:if test="${fn:contains(edusat.edu_receive_type, '0') }">
	<c:set var="receive_str" value="${receive_str }${empty receive_str ? '' : ',' }방문"></c:set>
</c:if>
<c:if test="${fn:contains(edusat.edu_receive_type, '1') }">
	<c:set var="receive_str" value="${receive_str }${empty receive_str ? '' : ',' }전화"></c:set>
</c:if>
<c:if test="${fn:contains(edusat.edu_receive_type, '2') }">
	<c:set var="receive_str" value="${receive_str }${empty receive_str ? '' : ',' }인터넷"></c:set>
</c:if>
<c:set var="receive_str" value="${receive_str }접수" />


<div id="board" style="width:100%;">
	
	<!-- 보기 테이블 -->
	<div class="table_bview">
			<table cellspacing="0" summary="의 작성일, 작성자, 조회수 및 내용" >
			<caption></caption>
				<colgroup>
				<col width="" />
				</colgroup>
			<thead>
				<tr>
					<th scope="col" class="th_none">${edusat.edu_subject} ${str2}</th>
				</tr>
			</thead>
			<tbody>
			<tr>
				<td>
					<dl class="info">
						<dt>구분</dt>
						<dd class="eng">${edusat.edu_gubun}</dd>
					</dl>
				</td>
			</tr>
			<!-- 
			<tr>
				<td>
					<dl class="info">
						<dt>강좌기간</dt>
						<dd class="eng">${edusat.edu_sdate} ~ ${edusat.edu_edate}</dd>
					</dl>
				</td>
			</tr>
			<tr>
				<td>
					<dl class="info">
						<dt>강좌시간</dt>
						<dd class="eng">${edusat.edu_time}&nbsp;(${edu_week_str})</dd>
					</dl>
				</td>
			</tr>
			 -->
			 <tr>
				<td>
					<dl class="info">
						<dt>신청기간</dt>
						<dd class="eng">
							<%--${edusat.edu_resdate} ${edu_resdate_h_str }:00 ~ ${edusat.edu_reedate} ${edu_reedate_h_str }:59&nbsp; --%>
							${edusat.edu_resdate} ~ ${edusat.edu_reedate}&nbsp;
						</dd>
					</dl>
				</td>
			</tr>
			<tr>
				<td>
					<dl class="info">
						<dt>신청방법</dt>
						<dd class="eng">
							${receive_str }
						</dd>
					</dl>
				</td>
			</tr>
			<!-- 
			<c:if test="${edusat.edu_target ne '' }">
			<tr>
				<td>
					<dl class="info">
						<dt>수강대상</dt>
						<dd>${edusat.edu_target}&nbsp;</dd>
					</dl>
				</td>
			</tr>
			</c:if>
			<tr>
				<td>
					<dl class="info">
						<dt>모집인원</dt>
						<dd class="eng">
<c:if test="${fn:contains(edusat.edu_receive_type, '2')}">
							<strong>${req_count_str}</strong> / ${edusat.edu_inwon} 명&nbsp;
						<c:if test="${edusat.edu_lot_type eq '1'}">
							(추첨)
						</c:if>
						<c:if test="${edusat.edu_lot_type eq '2'}">
							(선착순)
						</c:if>
</c:if>
<c:if test="${!fn:contains(edusat.edu_receive_type, '2')}">
						${edusat.edu_inwon} 명
</c:if>
						</dd>
					</dl>
				</td>
			</tr>
			 -->
			
		<!-- 
		<c:if test="${!empty edusat.edu_temp2}">
			<tr>
				<td>
					<dl class="info">
						<dt>강의실</dt>
						<dd>${edusat.edu_temp2}&nbsp;</dd>
					</dl>
				</td>
			</tr>
		</c:if>
		
		<c:if test="${!empty edusat.edu_money}">
			<tr>
				<td>
					<dl class="info">
						<dt>참가비</dt>
						<dd class="eng">${edusat.edu_money}&nbsp;</dd>
					</dl>
				</td>
			</tr>
		</c:if>
		
		<c:if test="${!empty edusat.edu_teacher}">
			<tr>
				<td>
					<dl class="info">
						<dt>강사</dt>
						<dd>${edusat.edu_teacher}&nbsp;</dd>
					</dl>
				</td>
			</tr>
		</c:if>
			<c:if test="${edusat.edu_temp3 ne '' }">
			<tr>
				<td>
					<dl class="info">
						<dt>첨부파일</dt>
						<dd><a href="down.do?edu_idx=${edusat.edu_idx}">
								<img src="/nanum/site/board/nninc_simple/img/file/${edu_temp3_icon}" alt="" />&nbsp;${edusat.edu_temp3}</a>
							<span class="size">(${edu_temp3_size} byte)</span>
						</dd>
					</dl>
				</td>
			</tr>
			</c:if>
		 -->
			<tr>
				<td class="content">
				<!-- 
				<c:if test="${edusat.edu_temp3 ne '' }">
					<img src="/data/edusat/${edusat.edu_temp3 }" alt="${edusat.edu_subject }">
				</c:if>
				 -->
				${edusat.edu_content}
				
				</td>
			</tr>
			</tbody>
			</table>
	</div>
	<!-- //보기 테이블 -->
 
	<!-- 버튼 -->
 
 
	<div class="btn_w">

		<c:if test="${requestChk eq 'true' }">
			<c:if test="${fn:contains(edusat.edu_receive_type, '2')}">
			<a href="regist.do?edu_idx=${edusat.edu_idx }&prepage=${nowPageEncode}" class="con_btn btn_receipt">신청</a>
			</c:if>
		</c:if>
			<a href="${prepage }" class="con_btn gray" >목록</a>
	</div>
	<!-- //버튼 -->
 
 
 
 
	<!-- 댓글 -->
		<!-- //댓글 -->
 
 
 
	
</div>
<!-- //보기 -->
				
<jsp:include page="./bthtml.jsp" />
