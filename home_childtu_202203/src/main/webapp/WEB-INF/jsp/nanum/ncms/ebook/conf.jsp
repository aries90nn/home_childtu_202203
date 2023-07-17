<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>관리자페이지 : 이북관리</title>
	<!-- css, script-->
	<jsp:include page="../common/file/head_script.jsp" />
	<script type="text/javascript" src="/nanum/ncms/common/js/ncms_ebook.js"></script>
</head>

<body id="wrap">
<!-- 상단 영역-->
<jsp:include page="../common/file/top.jsp" />

<!-- 사이드영역 -->
<jsp:include page="../common/file/left.jsp" />


<div id="content_wrap">

	<!-- 내용영역 -->
	<div id="contents">
	
		<h1 class="tit"><span>이북</span> 환경설정</h1>
		
		
<!-- 내용들어가는곳 -->
<div id="contents_area">

	<form id="frm" method="post" action="confOk.do">
	<div>
	<input type="hidden" name="ebc_idx" value="${ebookconf.ebc_idx}" />
	
	</div>

		<div style="padding-bottom:5px;"><a href="/nanum/site/ebook/list.do" target="_blank"><strong>[리스트보기]</strong></a></div>


		<fieldset>
			<legend>이북 환경설정 작성</legend>
			<table class="bbs_common bbs_default" summary="이북 환경설정을 위한 입력 양식입니다.">
			<caption>이북 환경설정 </caption>
			<colgroup>
			<col width="120" />
			<col width="600" />
			</colgroup>

			<tr>
				<th scope="row"><label for="ebc_tophtml">상단 HTML 정보</label></th>
				<td class="left"><textarea rows="10" class="ta_textarea w9" id="ebc_tophtml" name="ebc_tophtml" >${ebookconf.ebc_tophtml}</textarea><br /><span class="point fs11">* HTML태그사용은 가능하나, &lt; script &gt; 는 사용불가합니다.</span></td>
			</tr>
			
			<tr>
				<th scope="row"><label for="ebc_btmhtml">하단 HTML 정보</label></th>
				<td class="left"><textarea rows="10" class="ta_textarea w9" id="ebc_btmhtml" name="ebc_btmhtml" >${ebookconf.ebc_btmhtml}</textarea><br /><span class="point fs11">* HTML태그사용은 가능하나, &lt; script &gt; 는 사용불가합니다.</span></td>
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
	<!-- //내용영역 -->

</div>



<!-- 하단영역-->
<jsp:include page="../common/file/bottom.jsp" />

</body>
</html>