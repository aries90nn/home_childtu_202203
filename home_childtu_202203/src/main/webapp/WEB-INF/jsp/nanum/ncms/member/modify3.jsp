<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_member.js"></script>
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>
<style>
.text1{color:#5C7DF8;font-size:11px}
</style>

<script type="text/javascript">
function w_chk(){
	var eForm = document.getElementById('frm');
	if( CheckSpaces(document.getElementById('frm').auth_pwd, '비밀번호') ) { return false; }

}
</script>

		<h1 class="tit"><span>비밀번호 확인</span></h1>

		<!-- 내용들어가는곳 -->
		<div id="contents_area">
<c:set var="action_url" value="${DOMAIN_HTTPS}/ncms/member/write.do" />
<c:if test="${!empty BUILDER_DIR}">
	<c:set var="action_url" value="${DOMAIN_HTTPS}/${BUILDER_DIR }/ncms/member/write.do" />
</c:if>
			<form id="frm" name="frm" method="post" action="${action_url }" onsubmit="return w_chk();">
			<div>
			<input type="hidden" name="m_num" value="${param.m_num}" /><!-- (수정일때사용) -->
			<input type="hidden" name="prepage" value="${nowPage}" />
			</div>
			
			<h2 class="tit">비밀번호 확인  <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 인증을 거친후 회원정보수정이 가능합니다.</span></h2>
		<fieldset>
		<legend>회원 기본정보 서식 작성</legend>
		<table class="bbs_common bbs_default" summary="신규 회원 기본정보를 위한 입력 양식입니다.">
		<caption>기능정보 서식</caption>
		<colgroup>
		<col width="15%" />
		<col width="*" />
		</colgroup>
		
		<tr>
			<th scope="row"><label for="m_id"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 아이디</label></th>
			<td class="left">${ss_m_id}</td>
		</tr>
		<tr>
			<th scope="row"><label for="auth_pwd"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 비밀번호</label></th>
			<td class="left"><input type="password" title="비밀번호 입력" id="auth_pwd" name="auth_pwd" class="ta_input" maxlength="30" style="width:200px;" /></td>
		</tr>
		</table>
		</fieldset>
		
				<div class="contoll_box">
					<span><input type="submit" value="등록" class="btn_bl_default" /></span>
				</div>
		
			</form>
</div>
<!-- 내용들어가는곳 -->