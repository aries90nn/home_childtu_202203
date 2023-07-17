<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><c:set var="a_num" value="${param.a}" /><c:set var="pageurl" value="${param.u}" /><rss version='2.0' xmlns:dc='http://purl.org/dc/elements/1.1/'>
<channel>
<title>${application_name}</title>
<link>${application_domain}</link>
<description>RSS version 2.0</description>
<dc:language>ko</dc:language>

<c:forEach items="${boardList}" var="board" varStatus="no">
	<item>
	<title><![CDATA[${board.b_subject}]]></title>
	<link><![CDATA[${application_domain}/board/view.do?a_num=${a_num}&b_num=${board.b_num}]]></link>
	<description>
	<![CDATA[${board.b_content}]]>
	</description>
	<dc:date><![CDATA[${board.b_regdate}]]></dc:date>
	<dc:subject><![CDATA[${a_bbsname}]]></dc:subject>
	</item>
</c:forEach>
</channel>
</rss>
