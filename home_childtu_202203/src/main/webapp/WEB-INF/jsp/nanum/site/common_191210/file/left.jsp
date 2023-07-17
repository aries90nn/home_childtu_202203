<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	
	<!-- 중간영역 -->
	<div id="container">
		<div class="wsize">
		<!-- 사이드영역 -->
		<div id="sidebar">
			<c:if test="${ref_idx1 eq '14'}">
				<h2><span>한국수목원관리원</span><br />소개</h2>
			</c:if>
			<c:if test="${ref_idx1 ne '14'}">
				<h2>${first_menu_name}</h2>
			</c:if>
			<div id="sidemenu">
				<p class="tit jsMMenuText"><a href="#empty" class="jsMMenuBtn">${title}</a></p>
				<ul>
					<c:forEach items="${leftMenuList}" var="leftMenu" varStatus="no">
					<c:set var="menu_url" value="${leftMenu.ct_menu_url eq '' ? 'javascript:;' : leftMenu.ct_menu_url}" />
					<li <c:if test="${idx eq leftMenu.ct_idx or ref_idx2 eq  leftMenu.ct_idx or ref_idx3 eq leftMenu.ct_idx}">class="on"</c:if>>
					
						<c:choose>
							<%-- 예외 메뉴 처리 --%>
							<c:when test="${leftMenu.ct_chk ne 'Y'}">
							</c:when>
							<c:otherwise>
							
								<a href="${menu_url}" ${leftMenu.ct_url_target eq 'Y' ? 'target="_blank"' : '' }>${leftMenu.ct_name}
									<c:if test="${leftMenu.ct_url_target eq 'Y'}">
										<img src="/nanum/site/img/common/new_window.png" alt="새창" />
									</c:if>
								</a>
								
								<!--3차메뉴-->
								<c:if test="${fn:length(leftMenu.menuList) > 0 and leftMenu.ct_tab ne 'Y'}">
									<ul <c:if test="${idx ne leftMenu.ct_idx and ref_idx2 ne leftMenu.ct_idx}">style="display:none !important"</c:if>>
										<c:forEach items="${leftMenu.menuList}" var="subLeftMenu" varStatus="no2">
											<c:set var="submenu_url" value="${subLeftMenu.ct_menu_url eq '' ? 'javascript:;' : subLeftMenu.ct_menu_url}" />
											<c:choose>
												<%-- 예외 메뉴 처리 --%>
												<c:when test="${subLeftMenu.ct_chk ne 'Y'}">
												</c:when>
												<c:otherwise>
											
											<li <c:if test="${idx eq subLeftMenu.ct_idx and leftMenu.ct_idx eq subLeftMenu.ct_ref}">class="on"</c:if>>
												<a href="${submenu_url}" ${subLeftMenu.ct_url_target=='Y' ? 'target="_blank"' : '' }>${subLeftMenu.ct_name}</a>
											</li>
											
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</ul>
								</c:if>
								<!--//3차메뉴-->
							
							</c:otherwise>
						</c:choose>
						
					</li>
					</c:forEach>
					<c:if test="${fn:length(leftMenuList) == 0}">
						<li>&nbsp;</li>
					</c:if>
				</ul>
				<jsp:include page="../file/sub_banner.jsp" />
			</div>
		</div>
		<!-- //사이드영역 -->
		<!-- 컨텐츠영역 -->
		<div id="contents">
			<!-- 인쇄영역 -->
			<div id="print_wrap">