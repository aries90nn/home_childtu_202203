<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${statsList}" var="stats" varStatus="no">
<tr>
	<td class="left">${stats.menuname}</td>
	<td class="left">${stats.graph}</td>
	<td class="center eng stress">${stats.counter}</td>
</tr>
</c:forEach>