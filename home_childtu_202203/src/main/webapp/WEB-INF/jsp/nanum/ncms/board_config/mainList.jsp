<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:choose>
	<c:when test="${!empty boardList}">
	<c:forEach items="${boardList}" var="boardList" varStatus="no">
	<c:set var = "b_day" value = "${fn:substring(boardList.b_regdate, 8, 10)}" />
	<c:set var = "b_regdate" value = "${fn:substring(boardList.b_regdate, 0, 7)}" />
	<li>
		<div class="date"><p>${b_day}</p><span>일</span><br/><p class="small">${b_regdate}</p></div>
		<div class="cont"><span class="subj subj_default"><a href="javascript:page_go2('/board/view.do?a_num=${boardList.a_num}&b_num=${boardList.b_num}');" >${boardList.b_subject}</a></span><span class="detail">${boardList.b_content}</span><span class="location">[${boardList.a_bbsname}]</span></div>
		
		<p class="view"><a href="javascript:page_go2('/board/view.do?a_num=${boardList.a_num}&b_num=${boardList.b_num}');" >내용보기</a></p>
	</li>
	</c:forEach>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>