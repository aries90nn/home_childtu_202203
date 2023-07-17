<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<h1 class="tit"><span>방문자</span> 접속위치정보</h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">
	
<script type="text/javascript">
$(function(){
	//마지막일자 세팅하기
	$( "#mm" ).change(function() {
		var dd_val = $("#dd option:selected").val();
		
		var lastDay = ( new Date($("#yy option:selected").val(), $("#mm option:selected").val(), 0) ).getDate();
		
		$("#dd").empty();
		$("#dd").append('<option value="ALL">전체</option>');
		for(var i=1; i<=lastDay; i++){
			$("#dd").append('<option value="'+i+'">'+i+'일</option>');
		}
		$("#dd").val("ALL").change();
	});
});

function portal_click(portal_val){
	document.getElementById('frm').portal_type.value = portal_val;
	document.getElementById('frm').submit();
}
</script>
	<form id="frm" action="log.do" method="get">
	<input type="hidden" name="portal_type" value="${portal_type}" />
<!-- 검색 -->
<div class="top_search_area mt10">
	<ul>
		<li class="tit"><label for="YY"><h3 class="tit">날짜별검색 :</h3></label></li>
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
				<option value="ALL">전체</option>
				<c:forEach var="i" begin="1" end="${lastDay}">
				<option value="${i}" ${i == dd ? 'selected="selected"' : '' }>${i}일</option>
				</c:forEach>
			</select>	
		</li>
		<li class="btn"><input type="submit" value="보기" class="btn_bl_default" /></li>
		<li class="btn"><input type="button" value="오늘" class="btn_gr_default" onclick="page_go1('log.do?yy=${currentYear}&mm=${currentMon}&dd=${currentDay}');" /></li>
	</ul>
</div>
<div class="stats_area">
	<ul>
		<li><a href="javascript:portal_click('');"><span style="color:#DF0101;">전체보기</span></a></li>
		<li><a href="javascript:portal_click('naver');" ${portal_type == 'naver' ? 'class="on"' : '' }><span><img src="/nanum/ncms/img/naver.gif" alt="네이버" /></span> : ${percentNaver}% / <fmt:formatNumber value="${totalNaver}" groupingUsed="true" />건</a></li>
		<li><a href="javascript:portal_click('daum');" ${portal_type == 'daum' ? 'class="on"' : '' }><span><img src="/nanum/ncms/img/daum.gif" alt="다음" /></span> : ${percentDaum}% / <fmt:formatNumber value="${totalDaum}" groupingUsed="true" />건</a></li>
		<li><a href="javascript:portal_click('brow');" ${portal_type == 'brow' ? 'class="on"' : '' }><span>즐겨찾기</span> : ${percentBrow}% / <fmt:formatNumber value="${totalBrow}" groupingUsed="true" />건</a></li>
		<li><a href="javascript:portal_click('etc');" ${portal_type == 'etc' ? 'class="on"' : '' }><span>기타</span> : ${percentEtc}% / <fmt:formatNumber value="${totalEtc}" groupingUsed="true" />건</a></li>
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
	<th>포털</th>
	<th>방문수</th>
</tr>
</thead>
<tbody>
<tr>
	<td>NAVER</td>
	<td>${arrData[0][0]}</td>
</tr>
<tr>
	<td>DAUM</td>
	<td>${arrData[1][0]}</td>
</tr>
<tr>
	<td>즐겨찾기</td>
	<td>${arrData[2][0]}</td>
</tr>
<tr>
	<td>기타</td>
	<td>${arrData[3][0]}</td>
</tr>
</tbody>
</table>
<!-- //챠트 -->

	<div>
	
<div class="list_count pb2">
	전체 <strong><span class="point_default"><fmt:formatNumber value="${totalCount}" groupingUsed="true" /></span></strong>명 방문 (페이지 <strong class="point_default">${v_page}</strong>/${totalpage})
</div>

	<fieldset>
		<legend>방문자 접속위치정보 통계</legend>
		<table class="bbs_common bbs_default" summary="방문자 접속위치정보를 확인합니다.">
		<caption>방문자 접속위치정보 통계 서식</caption>
		<colgroup>
		<col width="70" />
		<col width="" />
		<col width="25%" />
		<col width="18%" />
		</colgroup>

		<thead>
		<tr>
			<th scope="col">번호</th>
			<th scope="col">링크된페이지(REFER_URL)</th>
			<th scope="col">운영체제 / 브라우저</th>
			<th scope="col">방문시각 / 방문자 IP </th>
		</tr>
		</thead>

		<tbody>
		<c:set var="num" value="${totalCount}" />
		<c:forEach items="${statsList}" var="stats" varStatus="no">
			<tr>
				<td class="center eng">${recordcount - ((v_page-1) * pagesize + no.index) }</td>
				<td class="left">${stats.vURL_str}</td>
				<td class="center eng">${stats.vOS_str}<br />${stats.vBrowser_str}</td>
				<td class="center eng">${stats.vHH} : ${stats.vMT}<br /><a href="javascript:page_go2('infopage.do?domain_name=${stats.vIP}');" >${stats.vIP}</a></td>
			</tr>
			<c:set var="num" value="${num-1}" />
		</c:forEach>
		</tbody>
		</table>
	</fieldset>
	
	<!-- 페이징 -->
	<div class="paginate">
		${pagingtag }

	</div>
	<!-- //페이징 -->

</div>
<!-- 내용들어가는곳 -->