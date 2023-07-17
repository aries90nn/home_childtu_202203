<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<script type="text/javascript">
$(function(){
	//마지막일자 세팅하기
	$( "#mm" ).change(function() {
		var dd_val = $("#dd option:selected").val();
		
		var lastDay = ( new Date($("#yy option:selected").val(), $("#mm option:selected").val(), 0) ).getDate();
		
		$("#dd").empty();
		$("#dd").append('<option value="">전체</option>');
		for(var i=1; i<=lastDay; i++){
			$("#dd").append('<option value="'+i+'">'+i+'일</option>');
		}
		$("#dd").val("").change();
	});
});
</script>
<h1 class="tit"><span>시간별</span> 통계</h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<form id="frm" action="h.do" method="get">
	<!-- 검색 -->
<div class="top_search_area mt10">
	<ul>
		<li class="tit"><label for="YY"><h3 class="tit">시간별검색 :</h3></label></li>
		<li class="sel">
			<!-- 현재날짜 -->
			<jsp:useBean id="date" class="java.util.Date" />
			<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
			<fmt:formatDate value="${date}" pattern="MM" var="currentMon" />
			<fmt:formatDate value="${date}" pattern="dd" var="currentDay" />
			
			<select id="yy" name="yy" title="해당 년도 선택" class="t_search" style="width:80px;">
				<c:forEach var="i" begin="2018" end="${currentYear}">
				<option value="${i}" ${i == yy ? 'selected="selected"' : '' }>${i}년</option>
				</c:forEach>
			</select>	
		</li>
		<li class="sel">
			<select id="mm" name="mm" title="해당 월 선택" class="t_search" style="width:60px;">
				<c:forEach var="i" begin="1" end="12">
				<option value="${i}" ${i == mm ? 'selected="selected"' : '' }>${i}월</option>
				</c:forEach>
			</select>	
		</li>
		<li class="sel">
			<select id="dd" name="dd" title="해당 일 선택" class="t_search" style="width:60px;">
				<option value="">전체</option>
				<c:forEach var="i" begin="1" end="${lastDay}">
				<option value="${i}" ${i == dd ? 'selected="selected"' : '' }>${i}일</option>
				</c:forEach>
			</select>	
		</li>
		<li class="sel">
			<select id="os" name="os" title="접속환경" class="t_search" style="width:120px;">
				<option value="">PC+MOBILE</option>
				<option value="PC" ${param.os eq 'PC' ? 'selected="selected"' : '' }>PC</option>
				<option value="MOBILE" ${param.os eq 'MOBILE' ? 'selected="selected"' : '' }>MOBILE</option>
			</select>	
		</li>
		<li class="btn"><input type="submit" value="보기" class="btn_bl_default" /></li>
		<li class="btn"><input type="button" value="오늘" class="btn_gr_default" onclick="page_go1('h.do?yy=${currentYear}&mm=${currentMon}&dd=${currentDay}');" /></li>
	</ul>
</div>
<!-- //검색 -->
</form>


<!-- 챠트 -->
<script type="text/javascript" src="/nanum/site/common/highcharts/highcharts.js"></script>
<script type="text/javascript" src="/nanum/site/common/highcharts/modules-data.js"></script>
<script type="text/javascript" src="/nanum/ncms/common/js/chart.js"></script>

<div id="chartContainer" style="min-width: 600px; height: 250px; margin: 0 auto" data-labelstep="1" ></div>
<table id="chartDatatable" style="display:none;">
<thead>
<tr>
	<th>시간</th>
	<th>방문수</th>
</tr>
</thead>
<tbody>
<c:forEach var="z" begin="0" end="23" step="1">
<tr>
	<td>${z}시</td>
	<td>${arrData[z][0]}</td>
</tr>
</c:forEach>

</tbody>
</table>
<!-- //챠트 -->

			<div>

				<div class="list_count mt10 mb5">
					전체 <strong><span class="point_default"><fmt:formatNumber value="${totalCount}" groupingUsed="true" /></span></strong>명 방문 (최대 <fmt:formatNumber value="${maxCount}" groupingUsed="true" />명, 최소 <fmt:formatNumber value="${minCount}" groupingUsed="true" />명)
				</div>

				<fieldset>
					<legend>시간별 방문자 통계</legend>
					<table class="bbs_common bbs_default" summary="시간별 방문자를 확인합니다.">
					<caption>시간별 방문자 통계 서식</caption>
					<colgroup>
					<col width="30%" />
					<col />
					<col width="30%" />
					</colgroup>

					<thead>
					<tr>
						<th scope="col">시간</th>
						<th scope="col">방문수</th>
						<th scope="col">비율(%)</th>
					</tr>
					</thead>

					<tbody>
					<c:forEach var="z" begin="0" end="23" step="1">
					<tr>
						<td class="center">${z}시</td>
						<td class="center rline">
							<c:choose>
								<c:when test="${arrData[z][0] eq maxCount and maxCount != '0'}">
									<strong><fmt:formatNumber value="${arrData[z][0]}" groupingUsed="true" /></strong>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${arrData[z][0]}" groupingUsed="true" />
								</c:otherwise>
							</c:choose>
						</td>
						<td class="center">${arrData[z][2]}</td>
					</tr>
					</c:forEach>
					</tbody>
					</table>
				</fieldset>

			</div>

</div>
<!-- 내용들어가는곳 -->