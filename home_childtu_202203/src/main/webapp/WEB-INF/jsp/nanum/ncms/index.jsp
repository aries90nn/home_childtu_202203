<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />

	<meta name="language" content="ko" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>관리자페이지</title>
	<jsp:include page="./common/file/head_script.jsp" />
</head>

<c:if test= "${ss_m_id == null}">
	<script>
	location.href="/ncms/login.do";
	</script>
</c:if>

<c:choose>
	<c:when test="${mngleft_ck == 'Y'}">
	<body id="wrap">
	</c:when>
	<c:otherwise>
	<body id="wide">
	</c:otherwise>
</c:choose>

<!-- 상단 영역-->
<jsp:include page="./common/file/top.jsp" />

<!-- 사이드영역 -->

<!-- 사이드영역 -->
<div id="side_wrap"<c:if test= "${mngleft_ck != 'Y'}"> class='disnone'</c:if>>

	<!-- 상단 -->
	<jsp:include page="./common/file/side_top.jsp" />
	<!-- //상단 -->

	<!-- 카테고리 -->
	<h2 class="hidden">관리정보·이용정보·게시판·방문자 정보</h2>
	<div class="menu_area">
		<dl>
			<dt style="padding-right:10px;">도메인</dt>
			<dd class="url" style="width:133px;">${pageContext.request.serverName}</dd>
			<dt>용<span></span>량</dt>
			<dd>${str_hdd}</dd>
			<dt>버<span></span>전</dt>
			<dd>NCMS v1.0</dd>
		</dl>
		<dl class="none">
			<dt>어제의 방문자</dt>
			<dd><fmt:formatNumber type = "number" pattern="#,###" value = "${yesterday_cnt}" /><span>명</span></dd>
			<dt>이달의 방문자</dt>
			<dd><fmt:formatNumber type = "number" pattern="#,###" value = "${month_cnt}" /><span>명</span></dd>
			<dt>전체 방문자</dt>
			<dd><fmt:formatNumber type = "number" pattern="#,###" value = "${total_cnt}" /><span>명</span></dd>
		</dl>
		<div class="today"><fmt:formatNumber type = "number" pattern="#,###" value = "${today_cnt}" /><span>오늘의 방문자</span></div>
	</div>
	<!-- //카테고리 -->
</div>
<!-- //사이드영역 -->



<!-- 중간 영역 -->
<div id="content_wrap">

	<!-- 내용 -->
	<div id="contents">
		<div id="m_new_notice">
			<h1 class="m_tit">최근게시물</h1>
			<ul>

			<!-- 게시글 -->

			</ul>
			<p class="more"><a class="btn_bl_" style="width:100%;">더보기&nbsp;&nbsp;<span style="font-size:8pt; color:#737373;"> 1-<span id="sp_nowCnt"></span> / <span id="sp_totCnt"></span>건</span></a></p>
		</div>

		<!-- 메모장 -->
		<div id="note_wrap">
			<form id="reg" method="post" action="/ncms/manager_memo/writeOk.do" >
			<div>
				<input type="hidden" name="mm_idx" value="${managermemo.mm_idx}" />
				<input type="hidden" name="mm_id" value="${managermemo.mm_id}" />
			</div>
			<h2>간편메모장</h2>
			<div class="area"><label for="mm_content" style="display: block;">간단한 메모를 저장할 수 있는<br/>간편메모장입니다.</label><textarea id="mm_content" name="mm_content" title="간편메모 입력" class="memo autoInput" accesskey="m">${managermemo.mm_content}</textarea></div>
			<div class="bottom">
				<p class="st"><strong>${managermemo.mm_wdate}</strong></p>
				<p class="bt"><a href="javascript:document.getElementById('reg').submit();" class="btn_wh" style="width:70%;">저장하기</a></p>
			</div>
			</form>

		</div>
		<!-- //메모장 -->



	</div>
	<!-- //내용 -->	 



<!-- 하단영역-->
<jsp:include page="./common/file/sub_bottom.jsp" />

<script type="text/javascript" >

var totalCnt = ${all_cnt};
var listpage = 1;
var pagesize = 10;


$(document).ready(function(){ 
	getList();

	$("#m_new_notice").find(".more").click(function(){
		getList();
	});


});

function getList(){
	$.ajax({
		type: "POST",
		url: "/ncms/board_config/mainList.do",
		data: "v_page="+listpage+"&pagesize="+pagesize,
		dataType: "html",
		async: true,
		success: function(data) {
			$("#m_new_notice>ul").append(data);
			listpage = listpage + 1;
		},error: function( jqXHR, textStatus, errorThrown ) {
			alert( jqXHR.responseText, true );
		}, beforeSend: function( str1, str2 ) {
//			$("#loding").show();
//			$("#more").hide();
		},complete: function (xhr, textStatus){
//			$("#loding").hide();
//			$("#more").show();
		}
	});

	if ( totalCnt <= $("#m_new_notice>ul>li").length ){
		$("#m_new_notice").find(".more").hide();
	}

	setListCount();
}

// 더보기 옆에 글 갯수표시
function setListCount(){
	$("#sp_nowCnt").text($("#m_new_notice>ul>li").length);
	$("#sp_totCnt").text(totalCnt);
}


</script>