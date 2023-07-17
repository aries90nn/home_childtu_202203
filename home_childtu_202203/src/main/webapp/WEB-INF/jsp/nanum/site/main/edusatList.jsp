<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="notice_area">
<ul>
	<c:forEach items="${edusatMainList}" var="edusat" varStatus="no">
	<c:set var="type_class" value="${edusat.status eq '마감' ? 'type2':'type1'}" />
	<li><span class="category ${type_class}">${edusat.status}</span><a href="/edusat/view.do?edu_idx=${edusat.edu_idx}">${edusat.edu_subject }</a>
	<span class="date">${edusat.edu_wdate}</span></li>
	</c:forEach>
	<c:if test="${fn:length(edusatMainList) == 0}">
	<li>등록된 행사가 없습니다.</li>
	</c:if>
</ul>
<p class="more"><a href="/edusat/list.do?sh_edu_lib=${sh_edu_lib}"><img src="../../nanum/site/img/main/more_btn.gif" alt="더보기" /></a></p>
</div>