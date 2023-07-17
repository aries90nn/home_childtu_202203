<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="notice_area">
	<ul>
		<c:forEach items="${boardMainList}" var="board" varStatus="no">
		<c:set var="category" value='<span class="category type7">통합</span>' />
		<c:if test="${fn:contains(board.b_catename, '기적')}">
			<c:set var="category" value='<span class="category type4">기적</span>' />
		</c:if>
		<c:if test="${fn:contains(board.b_catename, '문화')}">
			<c:set var="category" value='<span class="category type1">문화</span>' />
		</c:if>
		<c:if test="${fn:contains(board.b_catename, '학마을')}">
			<c:set var="category" value='<span class="category type3">학마을</span>' />
		</c:if>
		<c:if test="${fn:contains(board.b_catename, '무수골')}">
			<c:set var="category" value='<span class="category type5">무수골</span>' />
		</c:if>
		<c:if test="${fn:contains(board.b_catename, '둘리')}">
			<c:set var="category" value='<span class="category type2">둘리</span>' />
		</c:if>
		<c:if test="${fn:contains(board.b_catename, '아이')}">
			<c:set var="category" value='<span class="category type6">아이</span>' />
		</c:if>
		<c:if test="${fn:contains(board.b_catename, '작은')}">
			<c:set var="category" value='<span class="category type8">작은</span>' />
		</c:if>
		<li>${category}<a href="/board/view.do?a_num=24874869&b_num=${board.b_num}">${board.b_subject}</a>
		<span class="date">${fn:substring(board.b_regdate, 5, 10)}</span></li>
		</c:forEach>
	</ul>
</div>
<p class="more"><a href="/board/list.do?a_num=24874869&v_cate=${param.v_cate}"><img src="../../nanum/site/img/main/more_btn.gif" alt="도서관소식 더보기" /></a></p>