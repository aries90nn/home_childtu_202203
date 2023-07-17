<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>관리자페이지 : 설문조사환경설정 </title>
	<jsp:include page="../common/file/head_script.jsp" />
	<script type="text/javascript" src="/nanum/ncms/common/js/ncms_poll.js"></script>
</head>

<body id="wrap">
<!-- 상단 영역-->
<jsp:include page="../common/file/top.jsp" />

<!-- 사이드영역 -->
<jsp:include page="../common/file/left.jsp" />

<!-- 중간 영역 -->
<div id="content_wrap">

	<!-- 내용영역 -->
	<div id="contents">

		<h1 class="tit"><span>설문조사</span> 환경설정</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

	<form id="frm" method="post" action="confOk.do" onsubmit="return confw_chk();">
	<div>
	<input type="hidden" name="poc_idx" value="${conf.poc_idx}" />
	
	</div>


		<fieldset>
			<legend>설문조사 환경설정 작성</legend>
			<table class="bbs_common bbs_default" summary="설문조사 환경설정을 위한 입력 양식입니다.">
			<caption>설문조사 환경설정 </caption>
			<colgroup>
			<col width="140" />
			<col width="580" />
			</colgroup>

			<tr>
				<th scope="row"><label for="poc_tophtml">상단 HTML 정보</label></th>
				<td class="left"><textarea rows="10" class="ta_textarea w9" id="poc_tophtml" name="poc_tophtml" title="상단 HTML 입력" onfocus="focus_on1_default(this);" onblur="focus_off1(this);" >${conf.poc_tophtml}</textarea><br /><span class="point fs11">* HTML태그사용은 가능하나, &lt; script &gt; 는 사용불가합니다.</span></td>
			</tr>

			<tr>
				<th scope="row"><label for="poc_btmhtml">하단 HTML 정보</label></th>
				<td class="left"><textarea rows="10" class="ta_textarea w9" id="poc_btmhtml" name="poc_btmhtml" title="하단 HTML 입력" onfocus="focus_on1_default(this);" onblur="focus_off1(this);" >${conf.poc_btmhtml}</textarea><br /><span class="point fs11">* HTML태그사용은 가능하나, &lt; script &gt; 는 사용불가합니다.</span></td>
			</tr>

			</table>
		</fieldset>

		<div class="contoll_box">
			<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="취소" class="btn_wh_default" onclick="page_go1('conf.do');" /></span>
		</div>

	</form>

</div>
<!-- 내용들어가는곳 -->


		</div>
		<!-- 내용들어가는곳 -->

	</div>
	<!-- //내용영역 -->

</div>

<!-- 하단영역-->
<jsp:include page="../common/file/bottom.jsp" />

</body>
</html>