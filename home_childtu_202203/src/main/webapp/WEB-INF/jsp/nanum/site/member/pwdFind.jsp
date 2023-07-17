<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:if test="${!empty sessionScope.ss_m_id }">
	<script>
		location.href="/${BUILDER_DIR}/";
	</script>
</c:if>

<%-- 본인인증 연동할 경우 활성화 --%>
<c:if test="${empty sessionScope.ss_m_dupinfo }">
	<c:url var="auth_url" value="/${BUILDER_DIR }/site/member/ipin.do">
		<c:param name="prepage" value="${nowPage }" />
	</c:url>
	<script>
		alert("비밀번호찾기를 위해 본인인증 페이지로 이동합니다.");
		location.href="${auth_url}";
	</script>

</c:if>
<%-- //본인인증 연동할 경우 활성화 --%>

<link href="/nanum/site/member/css/common.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/member.css" />
<script src="/nanum/ndls/common/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="/nanum/ndls/common/js/jquery-jqtransform.js"></script>



<c:if test="${empty member.m_id }">

<script type="text/javascript">
//<![CADTA[
function formChk( eForm ){
	if( CheckSpaces(eForm.m_name, '이름') ) { return false; }
	if( CheckSpaces(eForm.m_email, '이메일') ) { return false; }
	if( CheckSpaces(eForm.m_id, '아이디') ) { return false; }
}
//]]>
</script>
	
	<form id="form_idsearch" name="form_pwdsearch" method="post" action="${DOMAIN_HTTPS}/${BUILDER_DIR }/member/pwdFind.do" onsubmit="return formChk(this);">
	<div class="member_id">
		<span class="bg idpw"></span>
		<p class="tit">회원정보 <strong>비밀번호 찾기</strong></p>
		<p class="txt">잊어버린 나의 정보를 비밀번호 찾기를 통해 찾을 수 있습니다.</p>
		<div class="area">
			<p><input type="text" title="이름 입력" id="m_name" name="m_name" value="" class="label_put" /><label for="m_name">이름을 입력하세요.</label></p>
			<p><input type="text" id="m_email"  title="이메일 입력" name="m_email" class="label_put" /><label for="m_email">가입시 등록한 이메일을 입력하세요.</label></p>
			<p><input type="text" id="m_id"  title="아이디 입력" name="m_id" class="label_put" /><label for="m_id">가입시 등록한 아이디를 입력하세요.</label></p>
			<input type="submit" id="" name="" class="inp_btn" title="정보찾기" value="정보찾기" />
		</div>
	</div>
	</form>
</c:if>

<c:if test="${!empty member.m_id }">
<script type="text/javascript">

function formChk(eForm){
	if( CheckSpaces(eForm.m_pwd, '비밀번호') ) { return false; }
	if (document.getElementById('frm').m_pwd.value!=""){
		if(!passCheck() ) {return false; }
		if(CheckSpaces(eForm.m_pwdchk, '비밀번호확인') ) { return false; }
		if(CheckEqual(eForm.m_pwd,eForm.m_pwdchk,'비밀번호')) { return false; }
	}
}
</script>
	
	<form id="frm" name="frm" method="post" action="${DOMAIN_HTTPS}/${BUILDER_DIR }/member/pwdFind.do" onsubmit="return formChk(this);">
	<input type="hidden" name="m_num" value="${member.m_num }" />
	<input type="hidden" name="m_name" value="${member.m_name }" />
	<input type="hidden" name="m_email" value="${member.m_email }" />
	<input type="hidden" name="m_id" value="${member.m_id }" />
	<div class="member_id">
		<span class="bg idpw"></span>
		<p class="tit">회원정보 <strong>비밀번호 변경</strong></p>
		<p class="txt">로그인에 사용할 비밀번호를 입력하시기 바랍니다.</p>
		<div class="area">
			<p><input type="password" id="m_pwd"  title="변경할 비밀번호 입력" name="m_pwd" class="label_put" /><label for="m_pwd">변경할 비밀번호를 입력하세요.</label></p>
			<p><input type="password" id="m_pwdchk"  title="변경할 비밀번호 재입력" name="m_pwdchk" class="label_put" /><label for="m_pwdchk">변경할 비밀번호를 다시 입력하세요.</label></p>
			<input type="submit" id="" name="" class="inp_btn" title="비밀번호 변경" value="비밀번호 변경" />
		</div>
	</div>
	</form>
</c:if>



<!-- 모듈하단 -->
