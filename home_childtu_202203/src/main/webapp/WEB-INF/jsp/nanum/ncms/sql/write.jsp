<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>

<h1 class="tit"><span>키워드</span> 관리</h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">
	
<h2 class="tit">인젝션 삽입공격으로 <strong style='color:#AA2222;'>디비쿼리변조(SQL Injection), 크로스사이트스크립팅(XSS)</strong>이 가능한 키워드를 <strong style='color:#AA2222;'>차단</strong>합니다.</h2>
<span class="point_default fs11">* 사이트관리에서 생성된 사이트전체에 반영됩니다.</span>
<fieldset>
	<legend>SQL인젝션 작성/수정</legend>

	<form id="frm" name="frm" action="/ncms/sql/writeOk.do" method="post">
	<input type="hidden" name="ms_num" value="${managersql.ms_num}" />
<table class="bbs_common bbs_default" summary="사이트 기본정보를 위한 입력/수정 양식입니다.">
<caption>SQL인젝션</caption>
<colgroup>
<col width="150px" />
<col />
</colgroup>
<tr>
	<th scope="row">GET</th>
	<td class="left">
		ex) keyword1, keyword2...
		<textarea rows="5" id="ms_get" name="ms_get" class="ta_textarea eng w9">${managersql.ms_get}</textarea>
	</td>
</tr>	
<tr>
	<th scope="row">POST</th>
	<td class="left">
		ex) keyword1, keyword2...
		<textarea rows="5" id="ms_post" name="ms_post" class="ta_textarea eng w9">${managersql.ms_post}</textarea>
		</td>
	</tr>
	</table>

<div class="contoll_box">
	<span><input type="submit" value="등록" class="btn_bl_default"/></span> <span><input type="button" value="취소" onclick="location.href='write.do'"  class="btn_wh_default" /></span>
</div>
</form>
</fieldset>

<p style="clear:both; height:40px;"/>

</div>
<!-- 내용들어가는곳 -->