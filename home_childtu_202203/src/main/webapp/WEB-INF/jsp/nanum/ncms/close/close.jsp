<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<c:set var="cl_category" value="${empty param.cl_category ? '' : param.cl_category }" />

<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/close.css" />
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_close.js"></script>



<h1 class="tit">휴관일 설정</h1>

		<!-- 내용들어가는곳 -->
		<div id="contents_area">
	
		
		
		<!-- 리스트 -->
<div id="board" style="width:100%;">

	<div class="top">
		<div class="cal_top">
			<a class="t_arrow y_pre" href='?year=${prevYear-1}&month=${intThisMonth}&cl_category=${cl_category}'><span class="blind">이전 해</span></a>
			<a class="t_arrow m_pre" href='?&year=${prevYear}&month=${prevMonth}&cl_category=${cl_category}'><span class="blind">이전 달</span></a> 
			
			<c:set var="thisYear" value="${fn:split(intThisYear,'-')}" />
			<c:set var="thisMonth" value="${fn:split(intThisMonth2,'-')}" />
			<span class="t_date">${thisYear[0]}.&nbsp;${thisMonth[0]}</span>

			<a class="t_arrow m_next" href='?year=${nextYear}&month=${nextMonth}&cl_category=${cl_category}'><span class="blind">다음 달</span></a>
			<a class="t_arrow y_next" href='?year=${nextYear+1}&month=${intThisMonth}&cl_category=${cl_category}'><span class="blind">다음 해</span></a>

			<a class="t_today" href='?year=${year}&month=${month}&cl_category=${cl_category}'>이달</a>


		</div>
		<div class="cal_right">
			&nbsp;
		</div>
	</div>

	
	<form id= "frm_list" action="closeOk.do"  method='post'>
	<div>
		<input type="hidden" name="prepage" value="${nowPage }" />
		<input type="hidden" id="chk_all" name="chk_all" /><!-- 전체체크 -->
		<input type="hidden" id="cl_category" name="cl_category" value="${cl_category}" /><!-- 도서관구분 -->
	</div>
	
	
	
	<!-- 리스트 테이블 -->
	<div class="cal_blist">

		<table cellspacing="0"  summary="${title} 날짜별 정보확인 및 내용을 선택하여 상세내용을 확인">
		<caption>${title}</caption>
			<colgroup>
			<col width="14%" />
			<col width="14%" />
			<col width="14%" />
			<col width="14%" />
			<col width="14%" />
			<col width="14%" />
			<col width="14%" />
			</colgroup>
		<thead>
			<tr>
				<th scope="col" class="sun"><input type="checkbox" title="해당 요일 선택" id="wchk_1" value="1" onclick="wCheck(this)" /><label for="wchk_1">일요일</label></th>
				<th scope="col"><input type="checkbox" title="해당 요일 선택" id="wchk_2" value="2" onclick="wCheck(this)" /><label for="wchk_2">월요일</label></th>
				<th scope="col"><input type="checkbox" title="해당 요일 선택" id="wchk_3" value="3" onclick="wCheck(this)" /><label for="wchk_3">화요일</label></th>
				<th scope="col"><input type="checkbox" title="해당 요일 선택" id="wchk_4" value="4" onclick="wCheck(this)" /><label for="wchk_4">수요일</label></th>
				<th scope="col"><input type="checkbox" title="해당 요일 선택" id="wchk_5" value="5" onclick="wCheck(this)" /><label for="wchk_5">목요일</label></th>
				<th scope="col"><input type="checkbox" title="해당 요일 선택" id="wchk_6" value="6" onclick="wCheck(this)" /><label for="wchk_6">금요일</label></th>
				<th scope="col" class="sat"><input type="checkbox" title="해당 요일 선택" id="wchk_7" value="7" onclick="wCheck(this)" /><label for="wchk_7">토요일</label></th>
			</tr>
		</thead>

		<tbody>
			<c:set var="day" value="${-( datFirstDay ) + 1}" />
			<c:set var="day2" value="${-( datFirstDay ) + 1}" />

			<c:forEach var="ju" begin="0" end="${jucnt-1}" step="1">
			<tr>
				<c:forEach var="i" begin="0" end="6" step="1">
					<c:set var="tcolor" value="${i==0 ? 'sun':''}" />
					<c:set var="tcolor" value="${i==6 ? 'sat':tcolor}" />
					<c:set var="cday" value="${day > 0 and day <= intLastDay ? day:''}" />
					
					<c:set var="nowday" value="${day}"></c:set>
					<c:if test="${day lt 10}">
						<c:set var="nowday" value="${'0'}${day }"></c:set>
					</c:if>
					<c:set var="nowmonth" value="${intThisMonth}"></c:set>
					<c:if test="${nowmonth lt 10}">
						<c:set var="nowmonth" value="${'0'}${nowmonth }"></c:set>
					</c:if>
					<c:set var="nowdate" value="${intThisYear}-${nowmonth}-${nowday}"/>
					<th id="date${cday}" class="day ${tcolor}">
					<c:if test="${!empty cday }">
					<input type="checkbox" id="chk_${cday}" class="w_${i+1}" name="chk" value="${nowdate }" title="해당 게시글 선택" />
					<label for="chk_${cday}">${cday}</label>
					</c:if>
					</th>
					<c:set var="day" value="${day + 1}" />
				</c:forEach>
			</tr>

			<!-- 내용 -->
			<tr>
				<c:forEach var="c" begin="0" end="6" step="1">
					<c:set var="cday2" value="${day2 > 0 and day2 <= intLastDay ? day2:''}" />
					<c:set var="cday2" value="${cday2 < 10 ? '0'+cday2:cday2}" />
					<c:set var="thisDate" value="" />
					<c:if test="${cday2 ne '00'}">
						<fmt:formatNumber var="month_str" minIntegerDigits="2" value="${intThisMonth}" type="number"/>
						<fmt:formatNumber var="day_str" minIntegerDigits="2" value="${cday2}" type="number"/>
						<c:set var="thisDate" value="${intThisYear}-${month_str}-${day_str}" />
					</c:if>
					
					<td headers="schedule date${cday2}">
						<div class='req_type'>${empty closeData[thisDate] ? '' : closeData[thisDate].cl_name }</div>
					</td>
					<c:set var="day2" value="${day2 + 1}" />
				</c:forEach>
			</tr>
			</c:forEach>
			
		</tbody>
		</table>

	</div>
	<!-- //리스트 테이블 -->

	<!-- 버튼 -->
	<div class="board_button">
		<div class="fl">
			<ul>
				<li class="pt"><a href="javascript:checkAll();" class="board_lbtn"><span>전체 선택/해제</span></a></li>
				<li class="pt">
					선택한 날짜를 
					<select id="proc_type" name="proc_type">
						<option value="1">휴관일</option>
						<option value="2">법정공휴일</option>
						<option value="0">휴관일해제</option>
					</select>
					(으)로 <a href="javascript:requestType();" class="board_lbtn"><span>변경</span></a>
				</li>
			</ul>
		</div>
		<div class="fr">
			&nbsp;
		</div>

	</div>
	<!-- //버튼 -->
	</form>


</div>
<!-- //리스트 -->

		</div>
		<!-- 내용들어가는곳 -->