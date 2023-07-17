<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--공통으로 사용될 상단메뉴 --%>
<c:if test="${fn:length(topMenuList) > 0}">
	<c:forEach items="${topMenuList}" var="topMenu" varStatus="no">
		
	
				<c:set var="menu_url" value="${empty topMenu.ct_menu_url ? '#nolink' : topMenu.ct_menu_url}" />
						<li class="menu${no.count }"><a href="${menu_url}" ${topMenu.menuList[0].ct_url_target eq 'Y' ? 'target="_blank"' : '' }>
						<c:choose>
							<c:when test="${topMenu.ct_img_status eq 'Y' and topMenu.ct_img_off ne '' and topMenu.ct_img_off ne null}">
								<img src="/data/cms/${topMenu.ct_img_off}" alt="${topMenu.ct_name}" />
							</c:when>
							<c:otherwise>
								${topMenu.ct_name}
							</c:otherwise>
						</c:choose></a>
							<div class="two_depth">
								<p class="bmenu_tit">${topMenu.ct_name}</p>
								<ul>
						<c:forEach items="${topMenu.menuList}" var="subMenu" varStatus="no2">
							<c:set var="submenu_url" value="${subMenu.ct_menu_url eq '' ? '#nolink' : subMenu.ct_menu_url}" />
							<c:choose>
								<%--예외처리 --%>
								<c:when test="${subMenu.ct_chk ne 'Y'
								
								or (fn:contains(submenu_url, '/site/mylib/modify.do') and empty sessionScope.ss_m_id)
								or (fn:contains(submenu_url, '/site/mylib/memberDrop.do') and empty sessionScope.ss_m_id)
								
								or (fn:contains(submenu_url, '/site/member/idSearch.do') and !empty sessionScope.ss_m_id)
								
								or (fn:contains(submenu_url, '/site/mylib/mobileCard.do') and pc_version eq 'Y')
								}">
								</c:when>
								<c:otherwise>
									<li><a href="${submenu_url}" ${subMenu.ct_url_target eq 'Y' ? 'target="_blank"' : '' }><span>${subMenu.ct_name}
									<c:if test="${subMenu.ct_url_target eq 'Y'}">
										<img src="/nanum/site/img/common/new_window.png" alt="새창" />
									</c:if>
									</span></a></li>
									
								</c:otherwise>
							</c:choose>	
									
						</c:forEach>
								</ul>
							</div>
						</li>
			
	</c:forEach>
</c:if>