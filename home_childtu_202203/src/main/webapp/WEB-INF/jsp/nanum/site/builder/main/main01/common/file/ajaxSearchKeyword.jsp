<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

				<ul>
<c:set var="keyword_list" value="${requestScope['json_data'].LIST_DATA }"></c:set>
<c:forEach items="${keyword_list }" begin="1" end="3" var="keyword" varStatus="no">
	<c:url var="search_url" value="/${BUILDER_DIR }/site/search/search00.do">
		<c:param name="cmd_name" value="bookandnonbooksearch" />
		<c:param name="search_type" value="detail" />
		<c:param name="use_facet" value="N" />
		<c:param name="search_item" value="search_title" />
		<c:param name="search_txt" value="${keyword.SEARCH_WORD }" />
	</c:url>
					<li><a href="${search_url }">#${keyword.SEARCH_WORD }</a></li>
</c:forEach>
				</ul>