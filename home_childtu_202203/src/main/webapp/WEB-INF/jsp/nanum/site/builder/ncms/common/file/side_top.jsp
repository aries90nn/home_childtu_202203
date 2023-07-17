<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="top_area">
	<h2><span>${ss_m_name}</span>님
	<p class="ip_default">IP_${pageContext.request.remoteHost}</p>
	<p class="ip_chic mt10">마지막 로그인<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${ss_m_lastdate}</p>
	<p class="ip_chic">마지막 로그인IP<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${ss_m_ip}</p>
	</h2>
	<br/>
</div>