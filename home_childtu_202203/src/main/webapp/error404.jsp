<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<%--
asdf=${requestScope["javax.servlet.forward.request_uri"] }
<br />asdf=${requestScope["javax.servlet.forward.servlet_path"] }
<br />asdf=${requestScope["javax.servlet.forward.path_info"] }
<br />asdf=${requestScope["javax.servlet.forward.query_string"] }
 --%>
 <%-- '/' 없을경우 한번더 체크 --%>
<c:set var="request_uri" value='${requestScope["javax.servlet.forward.request_uri"]}' />
<c:set var="request_uri_len" value='${fn:length(request_uri) }' />
<c:if test="${fn:substring(request_uri, request_uri_len-1, request_uri_len ) ne '/' }">

	<c:set var="request_uri_arr" value="${fn:split(request_uri, '/')}" />
	<c:set var="page_name" value="${request_uri_arr[fn:length(request_uri_arr)-1] }" />
	<c:if test="${!fn:contains(page_name, '.') }">
		<script>location.href="${request_uri}/";</script>
	</c:if>
</c:if>

<div style="text-align:center;font-family:돋움;padding-top:8%;">
	<p style="color:#d64a4a;font-weight:bold;font-size:40px;margin-bottom:35px;">404 error</p>
	<p style="color:#000;font-weight:bold;font-size:13px;margin-bottom:15px;">주소가 잘못 입력되었거나<br />원하시는 페이지를 찾을 수 없습니다.</p>
	<p style="color:#333;font-weight:normal;font-size:13px;margin-bottom:10px;">요청하신 페이지를 실행하는 데 문제가 발생했습니다.<br />링크 정보를 다시 확인해 주세요.</p>
	<div style="width:100%;display:inline-block;">
		<p style="display:inline-block;*display:inline;*zoom:1;"><a href="/" style="display:block;color:#000;font-weight:bold;padding:8px 14px;border:1px solid #dbdbdb;font-size:13px;text-decoration:none;">메인으로 이동</a></p>
		<!-- <p style="display:inline-block;*display:inline;*zoom:1;"><a href="#" style="display:block;color:#333;font-weight:normal;padding:8px 14px;border:1px solid #dbdbdb;font-size:13px;text-decoration:none;">뒤로가기</a></p> -->
	</div>
</div>
