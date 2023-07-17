<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

							<div class="etcbox">
								<div class="area">
								
<c:forEach items="${topMenuList}" var="topMenu" varStatus="no">
									<!-- -->
									<div class="box box0${no.count }">
										<p class="tit">${topMenu.ct_name}</p>
										<div class="con">
											<ul>
	<c:forEach items="${topMenu.menuList}" var="subMenu" varStatus="no2">
		<c:set var="menu_url" value="${subMenu.ct_menu_url eq '' ? 'javascript:;' : subMenu.ct_menu_url}" />
		<c:choose>
			<%--예외처리 --%>
			<c:when test="${(fn:contains(menu_url, '/site/mylib/modify.do') and empty sessionScope.ss_m_id)
			or (fn:contains(menu_url, '/site/member/reagree.do') and empty sessionScope.ss_m_id)
			or (fn:contains(menu_url, '/site/mylib/memberDrop.do') and empty sessionScope.ss_m_id)
			
			or subMenu.ct_chk ne 'Y'
				}">
			</c:when>
			<c:otherwise>
	
												<li><a href="${subMenu.ct_menu_url}"  ${subMenu.ct_url_target eq 'Y' ? 'target="_blank"' : '' }>${subMenu.ct_name}</a>
			<%-- 도서관  소개에서만 3차메뉴 까지표현 --%>								
			<c:if test="${no.count == 6 and fn:length(subMenu.menuList) > 0 }">
													<ul>
				<c:forEach items="${subMenu.menuList}" var="subMenu2" varStatus="no3">
														<li><a href="${subMenu2.ct_menu_url}" ${subMenu2.ct_url_target eq 'Y' ? 'target="_blank"' : '' }>${subMenu2.ct_name}</a></li>
				</c:forEach>
													</ul>
			</c:if>
												</li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
											</ul>
										</div>
									</div>
</c:forEach>
								</div>
							</div>
