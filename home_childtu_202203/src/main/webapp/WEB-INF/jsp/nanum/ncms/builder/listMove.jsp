<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>관리자페이지 : 코드관리 (순서변경) </title>
	<!-- css, script-->
	<jsp:include page="../common/file/head_script.jsp" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/sortable.css" />
	
</head>

<body id="popup">

<!-- 중간 영역 -->
<div id="content_wrap" style="min-width:0px;">

	<!-- 내용영역 -->
	<div id="contents">


<style>
.sortable li {height:25px;}
</style>

<h1 class="tit"><span>사이트</span> 순서 일괄수정</h1>

	
<form id= "frm_list" action="listMoveOk.do" method="post" onsubmit="return check_submit();">

<div class="section_card">

	<ul class="sortable" id="sortable">

			<c:forEach items="${siteList}" var="site" varStatus="no">
			<li>
				<p class="text" style="width:100%;">
					<strong class="jsNum">${no.count}</strong>. ${site.bs_sitename}(${site.bs_directory })</br>

				<div class="ds-buttons" style="z-index:99999;display:none;">
					<a href="#" class="btn_top _moveTop" title="최상단"><span class="blind">최상단</span></a>
					<a href="#" class="btn_up _moveUp" title="위로"><span class="blind">위로</span></a>
					<a href="#" class="btn_dn _moveDown" title="아래로"><span class="blind">아래로</span></a>
					<a href="#" class="btn_bottom _moveBottom" title="최하단"><span class="blind">최하단</span></a>
				</div>

				<input type="hidden" name="chk" value="${site.bs_num}" />
			</li>
			</c:forEach>

	</ul>

</div>


<div style="margin-top:10px; text-align:center;">
	<span style="margin-top:10px;text-align:center;"><input type="submit" value="등록" class="btn_bl_default" /></span>
</div>

</form>
	</div>
</div>

</body>
</html>

<script type="text/javascript" src="/nanum/ncms/common/js/sortable.js"></script>

<script type="text/javascript">
function check_submit(){
	if (!confirm('적용하시겠습니까?')){
		return false;
	}
}
</script>