<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<h1 class="tit"><span>관리자 접속기록</span></h1>

<script type="text/javascript">
$(function(){
	//마지막일자 세팅하기
	$( "#mm" ).change(function() {
		var dd_val = $("#dd option:selected").val();
		
		var lastDay = ( new Date($("#yy option:selected").val(), $("#mm option:selected").val(), 0) ).getDate();
		
		$("#dd").empty();
		$("#dd").append('<option value="all">전체</option>');
		for(var i=1; i<=lastDay; i++){
			$("#dd").append('<option value="'+i+'">'+i+'일</option>');
		}
		$("#dd").val("all").change();
	});
});
</script>
<!-- 내용들어가는곳 -->
	<div id="contents_area">

<form id="frm_sch" action="list.do" method="get">
<!-- 검색 -->
<div class="top_search_area mt10">
	<ul>
		<li class="tit"><label for="ct_name_i"><h3 class="tit">일자검색 :</h3></label></li>
		<li class="sel">
				<!-- 현재날짜 -->
			<jsp:useBean id="date" class="java.util.Date" />
			<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
			
			<select id="yy" name="yy" title="해당 년도 선택" class="t_search" style="width:80px;">
				<!-- <option value="all">전체</option>-->
				<c:forEach var="i" begin="2018" end="${currentYear}">
				<option value="${i}" ${i == yy ? 'selected="selected"' : '' }>${i}년</option>
				</c:forEach>
			</select>	
		</li>
		<li class="sel">
			<select id="mm" name="mm" title="해당 월 선택" class="t_search" style="width:60px;">
				<option value="all">전체</option>
				<c:forEach var="i" begin="1" end="12">
					<c:set var="mm_str" value="${mm == 'all' ? '0' : mm }" /><!-- 변수 다시세팅 -->
					<option value="${i}" ${i == mm_str ? 'selected="selected"' : '' }>${i}월</option>
				</c:forEach>
			</select>	
		</li>
		<li class="sel">
			<select id="dd" name="dd" title="해당 월 선택" class="t_search" style="width:60px;">
				<option value="all">전체</option>
				<c:forEach var="i" begin="1" end="${lastDay}">
					<c:set var="dd_str" value="${dd == 'all' ? '0' : dd }" /><!-- 변수 다시세팅 -->
					<option value="${i}" ${i == dd_str ? 'selected="selected"' : '' }>${i}일</option>
				</c:forEach>
			</select>	
		</li>
		<li class="sel">
			<select id="v_type" name="v_type" title="검색형태 선택" class="t_search" style="width:80px;">
				<option value="">전체영역</option>
				<option value="1" ${v_type == '1' ? 'selected="selected"' : '' }>관리기능</option>
				<option value="2" ${v_type == '2' ? 'selected="selected"' : '' }>게시판</option>
				<option value="3" ${v_type == '3' ? 'selected="selected"' : '' }>회원</option>
				<option value="3" ${v_type == '4' ? 'selected="selected"' : '' }>로그인</option>
			</select>
		</li>

		<li class="tit" style="padding-left:10px;"><label for="p_search"><h3 class="tit">검색 :</h3></label></li>
		<li class="sel">
			<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
				<option value="hl_job" ${v_search == 'hl_job' ? 'selected="selected"' : '' }>내역</option>
				<option value="hl_id" ${v_search == 'hl_id' ? 'selected="selected"' : '' }>아이디</option>
				<option value="hl_name" ${v_search == 'hl_name' ? 'selected="selected"' : '' }>이름</option>
				<option value="hl_ip" ${v_search == 'hl_ip' ? 'selected="selected"' : '' }>IP</option>
			</select>
		</li>
		<li class="search">
			<label for="p_keyword">검색어를 입력하세요</label><input type="text" id="v_keyword" name="v_keyword" class="search_input autoInput" value="${v_keyword}" />
		</li>
		<li class="btn"><input type="submit" value="검색" class="btn_bl_default" /></li>
		<li class="btn"><input type="button" value="초기화" class="btn_wh_default" onclick="page_go1('list.do');" /></li>
	</ul>
</div>
<!-- //검색 -->
</form>


<fieldset>
	<legend>홈페이지 작업기록</legend>
	<table class="bbs_common bbs_default" summary="홈페이지 작업기록+">
	<caption>홈페이지 작업기록 서식</caption>
<colgroup>
<col width="50" />
<col />
<col width="150" />
<col width="120" />
<col width="140" />
</colgroup>

<thead>
<tr>
	<th scope="col">번호</th>
	<th scope="col">내역</th>
	<th scope="col" class="stress">작업자</th>
	<th scope="col">IP</th>
	<th scope="col" class="stress">일자</th>
</tr>
</thead>

<tbody>
	<c:forEach items="${logList}" var="log" varStatus="no">
	<tr>
		<td class="center eng">
		${recordcount - ((v_page-1) * pagesize + no.index) }
		</td>
		<td class="left">${log.hl_job}</td>
		<td class="center  stress">${log.hl_name} <c:if test="${log.hl_id != ''}">(${log.hl_id})</c:if></td>
		<td class="center eng">${log.hl_ip}</td>
		<td class="center eng  stress">${log.hl_regdate}</td>
	</tr>
	</c:forEach>
	<c:if test="${fn:length(logList) == 0}">
	<tr>
		<td class="center" colspan="6">
			데이터가 없습니다.
		</td>
	</tr>
	</c:if>

	</tbody>
	</table>
</fieldset>

<!-- 페이징 -->
<div class="paginate">
	${pagingtag}

</div>
<!-- //페이징 -->
</form>
	
</div>
<!-- 내용들어가는곳 -->