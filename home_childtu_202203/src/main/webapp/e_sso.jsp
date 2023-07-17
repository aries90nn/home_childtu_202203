<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="lib_code" value="127001" />
<c:set var="user_id" value="${sessionScope.ss_m_id }" />
<c:set var="user_pwd" value="${sessionScope.ss_m_pwd }" />
<c:set var="name" value="${sessionScope.ss_m_name }" />
<c:set var="manage_code" value="${sessionScope.ss_user_mgcode }" />
<c:set var="ss_user_class" value="${empty sessionScope.ss_user_class ? param.ss_user_class : sessionScope.ss_user_class }" />
<c:set var="mobile" value="" />
<c:set var="next" value="${empty param.next ? 'default' : param.next }" />
<c:set var="type" value="${param.type }" />



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>이동페이지</title>
	
	
	<script type="text/javascript">
	//<![CDATA[
	var next = "${next}";
	onload = function(){

<c:choose>
	<c:when test="${empty user_id}">
	
		alert("도서관 정회원으로 로그인 후 사용하세요.");
		window.opener = 'nothing';
		window.open('','_parent','');
		window.close();
	
	</c:when>
	<c:when test="${ss_user_memberclass ne '0'}">
	
		alert("대출가능상태가 아닙니다. 확인하시기 바랍니다.");
		window.opener = 'nothing';
		window.open('','_parent','');
		window.close();
	
	</c:when>
	<c:otherwise>

		
		<c:choose>
			<c:when test="${ss_user_class eq'0'}">
		
			document.getElementById("goEbookTest").submit();
		
			</c:when>
			<c:otherwise>
			
			alert("서비스 이용이 제한되었습니다.\n\n회원상태가 대출정지중인지 확인하시기 바랍니다.");
			window.opener = 'nothing';
			window.open('','_parent','');
			window.close();
			
			</c:otherwise>
		</c:choose>

	
	</c:otherwise>
</c:choose>
		
		
	}
	
	function emulAcceptCharset(form) {
		if (form.canHaveHTML) { // detect IE
			document.charset = form.acceptCharset;
		}
		form.submit();
		//return true;
	}
	
	
	</script>
</head>
<body>

<c:choose>
	
	<%--오디오북--%>
	<c:when test="${type eq 'audio' }">
	<FORM id='goEbookTest' ACTION="http://aspservice.audien.com/inticube/front/NeoAspMain.do" METHOD="get">
		<input type="hidden" name="paId" value="350e406294b20cb2add9" />
		<input type="hidden" name="userId" value="${user_id }" />
		<input type="submit"  value="전송" style="display:none;" />
	</FORM>
	</c:when>
	
	<%--대구전자도서관--%>
	<c:otherwise>
	<form id='goEbookTest' action="http://libebook.gen.go.kr/Login_Check.asp" method="post" > 
	<input type="hidden" name="user_id" value="${user_id }" />
	<input type="hidden" name="user_name" value="${name }" />
	<input type="hidden" name="user_type" value="${manage_code }" />
	<input type="submit"  value="전송" style="display:none;" />
	</form>
	</c:otherwise>
	
</c:choose>





</body>
</html>