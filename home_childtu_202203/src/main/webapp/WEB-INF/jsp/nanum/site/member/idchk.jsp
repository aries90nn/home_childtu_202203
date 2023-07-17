<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${idcnt eq '0'}">
		alert("사용할 수 있는 아이디 입니다.");
		document.getElementById("frm").idchk.value = "Y";
		document.getElementById("frm").m_pwd.focus();
	</c:when>
	<c:otherwise>
		alert("[${m_id}]은(는) 이미 사용중인 아이디 입니다.");
		document.getElementById("frm").idchk.value = "N";
		document.getElementById("frm").m_id.focus();
	</c:otherwise>
</c:choose>