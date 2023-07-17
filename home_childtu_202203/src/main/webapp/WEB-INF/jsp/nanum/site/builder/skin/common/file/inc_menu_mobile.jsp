<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--공통으로 사용될 모바일 메뉴 --%>
<c:if test="${fn:length(topMenuList) > 0}">
	<c:forEach items="${topMenuList}" var="topMenu" varStatus="no">
		<c:set var="menu_url" value="${empty topMenu.ct_menu_url ? '#nolink' : topMenu.ct_menu_url}" />
		
					<li>
						<a href="#emty" id="dep${no.count }" onclick="depth_layer('${no.count }');" onfocus="if(event.keycode==13) { depth_layer('${no.count }'); }"><span>${topMenu.ct_name}</span></a>
						<!-- href="${menu_url}" -->
						<div id="dep${no.count }_viwe" style="display:none;">
							<ul>
		<%--메뉴생성 --%>
		<c:forEach items="${topMenu.menuList}" var="subMenu" varStatus="no2">
			<c:set var="submenu_url" value="${subMenu.ct_menu_url eq '' ? '#nolink' : subMenu.ct_menu_url}" />
			<c:choose>
				<%--예외처리 --%>
				<c:when test="${subMenu.ct_chk ne 'Y'
				
				or (fn:contains(submenu_url, '/site/mylib/modify.do') and empty sessionScope.ss_m_id)
				or (fn:contains(submenu_url, '/site/mylib/memberDrop.do') and empty sessionScope.ss_m_id)
				
				}">
				</c:when>
				<c:otherwise>
								<li><a href="${submenu_url}" ${subMenu.ct_url_target eq 'Y' ? 'target="_blank"' : '' }>${subMenu.ct_name}
								<c:if test="${subMenu.ct_url_target eq 'Y'}">
										<img src="/nanum/site/img/common/new_window.png" alt="새창" />
								</c:if>
								</a></li>
								
				</c:otherwise>
			</c:choose>
		</c:forEach>
							</ul>
						</div>
					</li>
	</c:forEach>
</c:if>