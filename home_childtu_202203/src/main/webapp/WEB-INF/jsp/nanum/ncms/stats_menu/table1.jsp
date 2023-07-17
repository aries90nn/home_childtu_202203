<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<fmt:parseNumber var="v_depth" type="number" value="${v_depth}" />

<c:if test="${v_main ne 'Y'}">
<tr>
	<td class="left">메인페이지 및 기타</td>
	<td class="left">${cnt_m_graph}</td>
	<td class="center eng stress">${cnt_m}</td>
</tr>
</c:if>
							

<c:forEach items="${menuList}" var="menu1" varStatus="no">
	<tr ${v_sub1 == 'Y' ? ' style="display:none;"' : '' }>
		<td class="left">${menu1.ct_name}</td>
		<td class="left">${menu1.graph}</td>
		<td class="center eng stress">${menu1.cnt}</td>
	</tr>
	
	<c:if test="${v_depth > 1}">
		<c:forEach items="${menu1.menuList}" var="menu2" varStatus="no">
			<tr>
				<td class="left" style="padding-left:50px;">${menu2.ct_name}</td>
				<td class="left">${menu2.graph}</td>
				<td class="center eng stress">${menu2.cnt}</td>
			</tr>
			<c:if test="${v_depth > 2}">
				<c:forEach items="${menu2.menuList}" var="menu3" varStatus="no">
				<tr>
					<td class="left" style="padding-left:100px;">${menu3.ct_name}</td>
					<td class="left">${menu3.graph}</td>
					<td class="center eng stress">${menu3.cnt}</td>
				</tr>
					<c:if test="${v_depth > 3}">
						<c:forEach items="${menu3.menuList}" var="menu4" varStatus="no">
						<tr>
							<td class="left" style="padding-left:150px;">${menu4.ct_name}</td>
							<td class="left">${menu4.graph}</td>
							<td class="center eng stress">${menu4.cnt}</td>
						</tr>
						</c:forEach>
					</c:if>
			
				</c:forEach>
			</c:if>
		</c:forEach>
	</c:if>

</c:forEach>