<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
	
<h1 class="tit">메뉴접속 통계</h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">

<script type="text/javascript">
$(function(){
	//마지막일자 세팅하기
	$( "#mm" ).change(function() {
		var dd_val = $("#dd option:selected").val();
		
		var lastDay = ( new Date($("#yy option:selected").val(), $("#mm option:selected").val(), 0) ).getDate();
		
		$("#dd").empty();
		$("#dd").append('<option value="0">전체</option>');
		for(var i=1; i<=lastDay; i++){
			$("#dd").append('<option value="'+i+'">'+i+'일</option>');
		}
		$("#dd").val("0").change();
	});
});
</script>
<form id="frm_sch" action="stat.do" method="get">

<fieldset>
	<legend>검색</legend>
	<table class="bbs_common bbs_default">
	<caption>검색 서식</caption>
	<colgroup>
	<col width="11%" />
	<col width="39%" />
	<col width="11%" />
	<col width="*" />
	</colgroup>			
		<tr>
			<th scope="row">날짜검색</th>
			<td class="left">

				<!-- 현재날짜 -->
				<jsp:useBean id="date" class="java.util.Date" />
				<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
				<fmt:formatDate value="${date}" pattern="MM" var="currentMon" />
				
				<select id="yy" name="yy" title="해당 년도 선택" class="t_search" style="width:80px;">
					<c:forEach var="i" begin="2018" end="${currentYear}">
					<option value="${i}" ${i == yy ? 'selected="selected"' : '' }>${i}년</option>
					</c:forEach>
				</select>	
				
				<select id="mm" name="mm" title="해당 월 선택" class="t_search" style="width:60px;">
					<c:forEach var="i" begin="1" end="12">
					<option value="${i}" ${i == mm ? 'selected="selected"' : '' }>${i}월</option>
					</c:forEach>
				</select>	
				
				<select id="dd" name="dd" title="해당 일 선택" class="t_search" style="width:60px;">
				<option value="0" ${0 eq dd ? 'selected="selected"' : '' }>전체</option>
				<c:forEach var="i" begin="1" end="${lastDay}">
				<option value="${i}" ${i eq dd ? 'selected="selected"' : '' }>${i}일</option>
				</c:forEach>
			</select>	

			</td>

			<th scope="row">접속환경</th>
			<td class="left">
				<select id="v_mobile" name="v_mobile" class="t_search">
					<option value="total" ${v_mobile eq 'total' ? 'selected="selected"' : '' }>PC + 모바일</option>
					<option value="N" ${v_mobile eq 'N' ? 'selected="selected"' : '' }>PC</option>
					<option value="Y" ${v_mobile eq 'Y' ? 'selected="selected"' : '' }>모바일</option>
				</select>
			</td>	
			
		</tr>
		<tr>								
			<th scope="row">검색 메뉴차수</th>
			<td class="left" colspan="3">
				<select id="v_depth" name="v_depth" class="t_search">
					<option value="0" ${v_depth eq '0' ? 'selected="selected"' : '' }>전체메뉴</option>
				<c:forEach var="z" begin="1" end="${maxdepth}" step="1">
					<option value="${z}" ${z eq v_depth ? 'selected="selected"' : '' }>${z}차 메뉴</option>
				</c:forEach>
				</select>
				&nbsp;&nbsp;
				<input type="checkbox" name="v_main" id="v_main" value="Y" ${v_main eq 'Y' ? 'checked' : '' } /><label for="v_main">메인페이지 제외</label>&nbsp;&nbsp;
				<input type="checkbox" name="v_sub1" id="v_sub1" value="Y" ${v_sub1 eq 'Y' ? 'checked' : '' } /><label for="v_sub1">1차메뉴 감추기</label>
			</td>
		</tr>
		

	</table>
</fieldset>

<div class="contoll_box" style="padding-top:5px;">
	<span><input type="submit" value="검색" class="btn_bl_default" /></span> <span><input type="button" value="검색초기화" onclick="page_go1('stat.do');" class="btn_wh_default" style="width:80px;" /></span>
	</div>

</form>

	<div style="clear:both; padding-top:30px;">

		<div class="list_count pb2">
			전체 <strong>${totalcounter}</strong> 건
		</div>

		<fieldset>
			<legend>메뉴별 방문자 통계</legend>
			<table class="bbs_common bbs_default" summary="메뉴별 방문자를 확인합니다.">
			<caption>메뉴별 방문자 통계 서식</caption>
			<colgroup>
			<col width="35%" />
			<col />
			<col width="8%" />
			</colgroup>

			<thead>
			<tr>
				<th scope="col">메뉴명</th>
				<th scope="col">통계</th>
				<th scope="col" class="stress">방문수</th>
			</tr>
			</thead>

			<tbody>

<c:choose>
	<c:when test="${v_depth > 0}">
		<jsp:include page="./table1.jsp" />
	</c:when>
	<c:otherwise>
		<jsp:include page="./table2.jsp" />
	</c:otherwise>
</c:choose>

				</tbody>
				</table>
			</fieldset>
		</div>
</div>
<!-- 내용들어가는곳 -->

<script type="text/javascript">

var max_percent = 0;
$(function(){
	$(".jsGraph").each(function(index){		
		p = parseInt($(this).data("percent"));		
		if (max_percent<p)	max_percent = p;		

		if (index>=$(".jsGraph").length-1){
			setGage();
		}
	});
});

function setGage(){
	$(".jsGraph").each(function(){
		// 최대값을 100%로 두고 게이지 재조정
		//var p = parseInt($(this).data("percent")) * 100 / max_percent;
		var p = ($(this).data("percent")) * 100 / max_percent;	// 1%이하는 그래프 표시가 안되서 수정
		$(this).animate({width: p+"%"},700);
	});
}
</script>


<link rel="Stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>
<script type="text/javascript">
$(function(){
	$('.jsCalendar').each(function(){
		$(this).helloCalendar({'selectBox':true});
	});
});
</script>