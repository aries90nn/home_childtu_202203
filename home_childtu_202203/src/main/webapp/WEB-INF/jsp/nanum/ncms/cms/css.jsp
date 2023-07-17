<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_cms.js"></script>
<script src="/nanum/site/common/js/jquery-linenumbers.min.js"></script>

<h1 class="tit"><span>CSS </span> 관리</h1>
		
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
		
	<h2 class="tit">css 경로<span class="loc" style="font-size:13px">: ${ct_name} ( <strong class="point_default">${css_file}</strong> )</span></h2>
	
<script type="text/javascript">
//<![CDATA[
function view_history(){
	var cth_idx = document.getElementById("frm").c_idx.value;

	if(cth_idx == ""){
		alert("작업내역을 선택해주세요.");
		return false;
	}else{
		document.getElementById("frm").action="css.do?ct_idx=${ct_idx}";
		document.getElementById("frm").submit();
	}
}
//]]>
</script>	
	<form id="frm" method="post" action="./cssOk.do?ct_idx=${ct_idx}">
	<input type="hidden" name="prepage" value="${nowPage }" />
	<fieldset>
		<legend>CSS 설정</legend>
		<table class="bbs_common bbs_default" summary="설문조사 환경설정을 위한 입력 양식입니다.">
		<caption>CSS 설정</caption>
		<colgroup>
		<col width="10%" />
		<col width="*" />
		</colgroup>
		<tbody>
		
		<c:if test="${fn:length(cssList) > 0}">
		<tr>
			<th scope="row">작업내역</th>
			<td class="left">
				
				<select name="c_idx" id="c_idx" class="ta_select" style="width:300px">
					<option value="">선택</option>
					<c:forEach items="${cssList}" var="history" varStatus="no">
					<option value="${history.c_idx}" ${history.c_idx == c_idx ? 'selected="selected"' : '' }>${no.count}. ${history.c_wdate}</option>
					</c:forEach>
				</select>
				<input type="button" value="보기" class="btn_gr_zipcode" onclick="javascript:view_history();"/>&nbsp;
			</td>
		</tr>
		</c:if>
		
		<tr>
			<th scope="row"><label for="content">CSS 소스</label></th>
			<td class="left"><textarea name="c_content" id="c_content" class="w99" style="height:650px;">${c_content}</textarea></td>
		</tr>
		</tbody>
		</table>
	</fieldset>

		<div class="contoll_box">
			<span><input type="submit" value="등록" class="btn_bl_default" /></span>
		</div>
	</form>

	</div>
	<!-- 내용들어가는곳 -->

<script>
$(function() {
	$("#c_content").linenumbers({col_width:'25px'});
});
</script>