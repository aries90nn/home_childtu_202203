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

<jsp:useBean id="now" class="java.util.Date" />
<c:set var="nowyear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
<c:set var="nowmonth"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
<c:set var="nowday"><fmt:formatDate value="${now}" pattern="dd" /></c:set>
<c:set var="nowhour"><fmt:formatDate value="${now}" pattern="HH" /></c:set>
<c:set var="nowminute"><fmt:formatDate value="${now}" pattern="mm" /></c:set>
<c:set var="nowsecond"><fmt:formatDate value="${now}" pattern="ss" /></c:set>


<link rel="Stylesheet" type="text/css" href="/nanum/site/edusat/css/common.css" />
<script type="text/javascript" src="/nanum/site/edusat/js/common.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>

<jsp:include page="./config.jsp" />
<jsp:include page="./tophtml.jsp" />

<c:if test="${BUILDER_DIR eq 'main' }">
</c:if>
 
<jsp:include page="./tophtml2.jsp" />


<script type="text/javascript">
//<![CDATA[
/*
var date = new Date(${nowyear},${nowmonth}-1,${nowday},${nowhour},${nowminute},${nowsecond});
onload = function(){
	printDate();
}
function printDate(){
		var obj    = document.getElementById("lecture-clock");
		var year   = parseInt(date.getYear());
		if(parseInt(date.getMonth())==0) var month = "1";
		else var month  = "0" + parseInt(date.getMonth()+1);
		var day    = "0" + parseInt(date.getDate());
		var hour   = "0" + date.getHours();
		var minute = "0" + date.getMinutes();
		var sec    = "0" + date.getSeconds();
		month      = month.substr(month.length - 2, 2);
		day        = day.substr(day.length - 2, 2);
		hour       = hour.substr(hour.length - 2, 2);
		minute     = minute.substr(minute.length - 2, 2);
		sec        = sec.substr(sec.length - 2, 2);
//		var szTime = month + "월 " + day + "일 " + hour + "시 " + minute + "분 " + sec + "초";
		var szTime = "<strong>" + month + "</strong><span>월</span> " + "<strong>" + day + "</strong><span>일</span> " + "<strong>" + hour + "</strong><span>시</span> " + "<strong>" + minute + "</strong><span>분</span> " + "<strong>" + sec + "</strong><span>초</span>";
		
		obj.innerHTML = szTime;

		
		//if(obj.firstChild && obj.firstChild.nodeType == 3){
			//obj.firstChild.nodeValue = szTime;
		//}else{
			//var objText = document.createTextNode(szTime);
			//obj.appendChild(objText);
		//}
		
		setTimeout("date.setTime(date.getTime()+1000);printDate();", 1000);
}
*/
//]]>
</script>



<div id="board" style="width:100%;">

	<div class="search_tit">
		<!--<h3 class="tit">프로그램 목록</h3>-->
		<div class="search_box_fr2 medium">
		<form name="frm_edu" method="get" action="list.do">
		<input type="hidden" name="sh_ct_idx" value="${param.sh_ct_idx}" />
			<ul>
				<li class="select_style medium">
					<select name="v_search" title="검색분류">
						<option value="" ${empty param.v_search ? "selected" : ""}>검색항목</option>
						<option value="edu_subject" ${param.v_search eq 'edu_subject' ? "selected" : ""}>프로그램명</option>
						<option value="edu_content" ${param.v_search eq 'edu_content' ? "selected" : ""}>내용</option>
					</select>
				</li>
				<li class="long">
					<input type="text" id="v_keyword" name="v_keyword" title="검색어 입력" value="${param.v_keyword}" class="txt_input" />
				</li>
				<li>
					<input type="submit" title="검색" value="검색" class="con_btn green" />
				</li>
			</ul>
		</form>
		</div>
	</div>
	
	<div class="board_total">
		<div class="board_total_left">
			총 <strong class="eng">${recordcount }</strong>개의 프로그램이 등록되어 있습니다.
		</div>
		<!-- 
		<div class="board_total_right">
			현재시간 : <span style="color:#ff4d4d;" id="lecture-clock"></span>
		</div>
		 -->
	</div>
	<!-- 리스트 테이블 -->
	<div class="lesson no_top">
		<ul>
	<c:forEach items="${edusatList}" var="edusat" varStatus="no">		
		<c:set var="nowdate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" /></c:set>
	
		<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}${edusat.edu_resdate_h}00' />
		<c:if test="${fn:length(edusat.edu_resdate_h) == 1}">
			<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}0${edusat.edu_resdate_h}00' />
		</c:if>
		
		<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}${edusat.edu_reedate_h}00' />
		<c:if test="${fn:length(edusat.edu_reedate_h) == 1}">
			<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}0${edusat.edu_reedate_h}00' />
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
		
		<c:set var="edu_resdate_h" value="${edusat.edu_resdate_h }" />
		<c:if test="${edu_resdate_h < 10 }"><c:set var="edu_resdate_h" value="0${edusat.edu_resdate_h }" /></c:if>
		<c:set var="edu_reedate_h" value="${edusat.edu_reedate_h }" />
		<c:if test="${edu_reedate_h < 10 }"><c:set var="edu_reedate_h" value="0${edusat.edu_reedate_h }" /></c:if>
		
		<c:choose>
			<c:when test="${edusat.edu_temp3 ne ''}">
				<c:set var="img_src" value='/data/edusat/${edusat.edu_temp3 }' />
				<c:set var="img_class" value="class=''" />
			</c:when>
			<c:otherwise>
				<c:set var="img_src" value='/nanum/site/board/movie/img/no.gif' />
				<c:set var="img_class" value="class='no_img'" />
			</c:otherwise>
		</c:choose>
		
		<li ${img_class }>
			<c:if test="${edusat.edu_temp3 ne ''}">
				<img  class="edu_img" src="${img_src }" alt="${edusat.edu_subject }" />
			</c:if>
			<div class="cont">
				<p class="tit"><a href="view.do?gubun=${param.gubun }&edu_idx=${edusat.edu_idx }&prepage=${nowPageEncode}" style="${colorCss}">
					<!-- <c:if test="${edusat.edu_gubun ne  '' and edusat.edu_gubun != null}"> [${edusat.edu_gubun}]</c:if> --> ${edusat.edu_subject}</a>
				</p>
				<div class="sm_box">
					<dl>
						<dt>신청기간</dt>
						<dd class="eng">${edusat.edu_resdate} ${edu_resdate_h}:00 ~ ${edusat.edu_reedate} ${edu_reedate_h}:00</dd>
					</dl>
				</div>
				
			</div>
			<div class="btn_box">
				<c:choose>
					<c:when test="${requestChk eq 'true' }">
						<c:if test="${fn:contains(edusat.edu_receive_type, '2')}">
							<a href="regist.do?gubun=${param.gubun }&edu_idx=${edusat.edu_idx }&prepage=${nowPageEncode}" class="btn_sm btn_ing">신청하기</a>
						</c:if>
						<c:if test="${!fn:contains(edusat.edu_receive_type, '2')}">
							<a href="#norequest" class="btn_sm btn_ing">${receive_str }</a>
						</c:if>
					</c:when>
					<c:otherwise>
						${str2}
					</c:otherwise>
				</c:choose>
				<c:if test="${fn:contains(edusat.edu_receive_type, '2')}">
				<a href="/main/site/mylib/myEdu.do?prepage=${nowPageEncode}" class="btn_sm btn_check">신청확인</a>
				</c:if>
				<c:if test="${!fn:contains(edusat.edu_receive_type, '2')}">
				<a href="#nocheck" class="btn_sm btn_check">등록확인불가</a>
				</c:if>
			</div>
		</li>
</c:forEach>

<c:if test="${fn:length(edusatList) == 0}">
		<li style="min-height:auto" class="no_program">
			<p style='text-align:center'>프로그램 준비중입니다.</p>
		</li>
</c:if>
</ul>

	</div>
	<!-- //리스트 테이블 -->
	<!-- 버튼 -->
	<div class="board_button">
		&nbsp;
	</div>
	<!-- //버튼 -->
 
	<!-- 페이징 -->
	<div class="board_paginate">
		${pagingtag }
	</div>
	<!-- //페이징 -->
</div>

<jsp:include page="./bthtml.jsp" />
