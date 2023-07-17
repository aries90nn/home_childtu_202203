<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>
	
<script type="text/javascript">
$(function(){
	$("p.app_view_onoff a").click(function(){
		getConfig( $(this) );
	});
});

function getConfig( aobj ){
	var field = aobj.data("field");
	var value = aobj.data("value");
	$.post("/ncms/info/site_config_ok.do", {
		proc_type:"update",
		fname:field,
		val:value
		}, function(data, status){
			//alert("Data: " + data + "\nStatus: " + status);
			$("a[data-field='sc_session_use']").hide();
			$("a[data-field='sc_session_use'][data-now='"+value+"']").show();
	});
}

$(document).ready(function(){
	//숫자만 입력
	$('.only_number').keyup(function () { 
		this.value = this.value.replace(/[^0-9]/g,'');
	});
});
</script>
		<h1 class="tit"><span>세션관리</span> </h1>
		
		<!-- 내용들어가는곳 -->
		<div id="contents_area">
		
	<form id="frm" name="frm" action="writeOk.do" method="post">
	<fieldset>
		<h2 class="tit">로그인 유지시간 설정</h2> <span class="point_default fs11">* 사이트관리에서 생성된 사이트전체에 반영됩니다.</span>
		<legend>세션유지시간 작성/수정</legend>

		<table class="bbs_common bbs_default" summary="사이트 기본정보를 위한 입력/수정 양식입니다.">
		<caption>세션관리</caption>
		<colgroup>
		<col width="150px" />
		<col />
		</colgroup>
		<tr>
			<th scope="row">세션유지시간</th>
			<td class="left"><input type="text" name="sc_session_time" value="${info.sc_session_time}" maxlength="3" onblur="SetNum(this);" class="ta_input only_number" style="width:50px;text-align:right;" /> 분
			&nbsp;&nbsp;※ 숫자 3자리 이내로 입력해주세요.
			</td>
		</tr>
		</table>
	</fieldset>
	<br /><br />

	<fieldset>
		<h2 class="tit">동시접속제한 설정</h2> <span class="point_default fs11">* 사이트관리에서 생성된 사이트전체에 반영됩니다.</span>
		<legend>동시접속제한 설정 작성/수정</legend>

		<table class="bbs_common bbs_default" summary="사이트 기본정보를 위한 입력/수정 양식입니다.">
		<caption>세션관리</caption>
		<colgroup>
		<col width="150px" />
		<col />
		</colgroup>
		<tr>
			<th scope="row">접속제한 사용유무</th>
			<td class="left">
				<p class="app_view_onoff jsOnOff">
					<a href="#tmp" class="<c:if test= "${info.sc_session_use == 'N'}"> disnone</c:if>" data-field="sc_session_use" data-now="Y" data-value="N"><span class="on">사용</span></a>
					<a href="#tmp" class="<c:if test= "${info.sc_session_use == 'Y'}">  disnone</c:if>" data-field="sc_session_use" data-now="N" data-value="Y"><span class="off">중지</span></a>
					&nbsp;&nbsp;※ 동시접속폭주로 인한 웹서버장애를 방지합니다.
				</p>

			</td>
		</tr>
		<tr>
			<th scope="row">접속허용 개수</th>
			<td class="left">
				<select name="sc_session_count" class="t_search" style="width:60px;">
					<option value="2" ${info.sc_session_count == '2' ? 'selected="selected"' : '' }>2</option>
					<option value="10" ${info.sc_session_count == '10' ? 'selected="selected"' : '' }>10</option>
					<option value="20" ${info.sc_session_count == '20' ? 'selected="selected"' : '' }>20</option>
					<option value="30" ${info.sc_session_count == '30' ? 'selected="selected"' : '' }>30</option>
					<option value="50" ${info.sc_session_count == '50' ? 'selected="selected"' : '' }>50</option>
					<option value="100" ${info.sc_session_count == '100' ? 'selected="selected"' : '' }>100</option>
				</select> 접속
				&nbsp;&nbsp;※ 웹서버 사양, 처리용량에 따라 <span style='font-weight:bold;color:#AA2222;'>최소한의 수치</span>를 지정해야 웹서버장애를 방지할 수 있습니다.
			</td>
		</tr>
		<tr>
			<th scope="row">대기시간</th>
			<td class="left">
				<select name="sc_session_wait" class="t_search" style="width:60px;">
					<option value="10" ${info.sc_session_wait == '10' ? 'selected="selected"' : '' }>10</option>
					<option value="20" ${info.sc_session_wait == '20' ? 'selected="selected"' : '' }>20</option>
					<option value="30" ${info.sc_session_wait == '30' ? 'selected="selected"' : '' }>30</option>
					<option value="60" ${info.sc_session_wait == '60' ? 'selected="selected"' : '' }>60</option>
					<option value="120" ${info.sc_session_wait == '120' ? 'selected="selected"' : '' }>120</option>
				</select> 초
			&nbsp;&nbsp;※ 연결허용수가 초과되면 접속대기페이지가 로딩되며 설정된 시간만큼 대기합니다.
			</td>
		</tr>
		</table>
	</fieldset>
	<div class="contoll_box">
		<span><input type="submit" value="등록" class="btn_bl_default"/></span> <span><input type="button" value="취소" onclick="location.href='write.do'" class="btn_wh_default" /></span>
	</div>
	</form>
	

	<p style="clear:both; height:40px;"/>

		
		</div>
		<!-- 내용들어가는곳 -->