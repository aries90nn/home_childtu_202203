<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<%-- 본인인증 연동할 경우 활성화 --%>
<c:if test="${empty sessionScope.ss_m_dupinfo }">
	<c:url var="auth_url" value="/${BUILDER_DIR }/site/member/ipin.do">
		<c:param name="prepage" value="${nowPage }" />
	</c:url>
	<script>
		alert("아이디찾기를 위해 본인인증 페이지로 이동합니다.");
		location.href="${auth_url}";
	</script>

</c:if>
<%-- //본인인증 연동할 경우 활성화 --%>

<link href="/nanum/site/member/css/common.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/member.css" />
<script src="/nanum/ndls/common/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="/nanum/ndls/common/js/jquery-jqtransform.js"></script>

<script type="text/javascript">
//<![CADTA[
function formChk( eForm ){
	
	if( CheckSpaces(eForm.m_name, '이름') ) { return false; }
	if( CheckSpaces(eForm.m_email, '이메일') ) { return false; }
}
//]]>
</script>

<c:if test="${empty member.m_id }">	
	<form id="form_idsearch" name="form_idsearch" method="post" action="${DOMAIN_HTTPS}/${BUILDER_DIR }/member/idFind.do" onsubmit="return formChk(this);">
	<div class="member_id">
		<span class="bg idpw"></span>
		<p class="tit">회원정보 <strong>아이디 찾기</strong></p>
		<p class="txt">잊어버린 나의 정보를 아이디 찾기를 통해 찾을 수 있습니다.</p>
		<div class="area">
			<p><input type="text" title="이름 입력" id="m_name" name="m_name" value="" class="label_put" /><label for="m_name">이름을 입력하세요.</label></p>
			<p><input type="text" id="m_email"  title="이메일 입력" name="m_email" class="label_put" /><label for="m_email">가입시 등록한 이메일을 입력하세요.</label></p>
			<input type="submit" id="" name="" class="inp_btn" title="정보찾기" value="정보찾기" />
		</div>
	</div>
	</form>
</c:if>

<c:if test="${!empty member.m_id }">
	<div class="member_id">
		<span class="bg idpw"></span>
		<p class="tit">회원정보 <strong>아이디  찾기</strong></p>
		<p class="txt">기입하신 정보로 찾고자 하시는 정보를 전달합니다.</p>
		<div class="area">
			<p class="idpw_reserv"><strong>${member.m_name }</strong>님의 아이디는 <span class="eng">${member.m_id }</span> 입니다.</p>
			<a href="/${BUILDER_DIR }/site/member/login.do" class="inp_btn">로그인 하러가기</a>
		</div>
	</div>
</c:if>



<!-- 모듈하단 -->
