<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/all.css" />
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/main_layout.css" />
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/sub_layout.css" />
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/sub_design.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>

<script type="text/javascript">
//<![CDATA[
//복원하기
function submit_history(){
	if (confirm('정말 적용하시겠습니까?'))	{
		document.getElementById("frm_history").action = "/${BUILDER_DIR}/ncms/cms/historyOk.do";
		document.getElementById("frm_history").submit();
	}
}
//]]>
</script>
	<br/><br/>
	<c:if test="${history.ct_pagetype eq 'L'}">
		<center><span style="display:inline-block;border:1px solid #dfdfdf;border-radius:3px;padding:8px 10px;"><a href="${history.ct_url}" target="_new"style="display:inline-block;border:1px solid #dfdfdf;border-radius:3px;padding:8px 10px;">새창으로 링크보기</a></span></center>
	</c:if>
	<c:if test="${history.ct_pagetype ne 'L'}">
		<iframe src="/${BUILDER_DIR}/cms/history_intro.do?cth_idx=${history.cth_idx}&ct_idx=${history.ct_idx}&idx=${history.ct_idx}" style="width:98%;height:680px;border:1px solid #F2F2F2;padding: 10px 10px"></iframe>
	</c:if>
	

	<div style="margin-top:10px; text-align:center; width:99%">
		<input type="button" value="복원하기" onclick="javascript:submit_history();"  class="cbtn cbtn_point"/>
		<input type="button" value="창닫기" onclick="self.close();" class="cbtn cbtn_g"/>
	</div>


	<form name="frm_history" id="frm_history" action="" method="post">
		<input type="hidden" name="ct_idx" value="${history.ct_idx}" />
		<input type="hidden" name="cth_idx" value="${history.cth_idx}" />
		<input type="hidden" name="target_type" value="pop" />
	</form>
