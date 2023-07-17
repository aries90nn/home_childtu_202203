<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
							
<%--공통으로 사용될 레프트 메뉴 --%>
					<c:forEach items="${leftMenuList}" var="leftMenu" varStatus="no">
						<c:set var="menu_url" value="${leftMenu.ct_menu_url eq '' ? 'javascript:;' : leftMenu.ct_menu_url}" />
						<c:choose>
							<%--예외처리 --%>
							<c:when test="${(fn:contains(menu_url, '/member/login') and !empty sessionScope.ss_m_id)
								or (fn:contains(menu_url, '/site/member/idSearch.do') and !empty sessionScope.ss_m_id)
								or (fn:contains(menu_url, '/contents.do?idx=745') and !empty sessionScope.ss_m_id)
								or (fn:contains(menu_url, '/site/member/agree.do') and !empty sessionScope.ss_m_id)
								or (fn:contains(menu_url, '/site/member/join.do') and !empty sessionScope.ss_m_id)
								or (fn:contains(menu_url, '/member/idFind.do') and !empty sessionScope.ss_m_id)
								or (fn:contains(menu_url, '/member/pwdFind.do') and !empty sessionScope.ss_m_id)

								
								or (fn:contains(menu_url, '/site/mylib/modify.do') and empty sessionScope.ss_m_id)
								or (fn:contains(menu_url, '/site/member/reagree.do') and empty sessionScope.ss_m_id)
								or (fn:contains(menu_url, '/site/mylib/memberDrop.do') and empty sessionScope.ss_m_id)
								
								
								or (fn:contains(menu_url, '/member/modify.do') and empty sessionScope.ss_m_id)
								or (fn:contains(menu_url, '/member/pwdChange.do') and empty sessionScope.ss_m_id)
								
								or (fn:contains(menu_url, '/site/mylib/mobileCard.do') and pc_version eq 'Y')
								or leftMenu.ct_chk ne 'Y'
								}">
							</c:when>
							<c:otherwise>

								<li <c:if test="${idx eq leftMenu.ct_idx or ref_idx2 eq  leftMenu.ct_idx or ref_idx3 eq leftMenu.ct_idx}">class="on"</c:if>>
									<a href="${menu_url}" ${leftMenu.ct_url_target eq 'Y' ? 'target="_blank" class="newwin"' : '' }>${leftMenu.ct_name}
									<c:if test="${leftMenu.ct_url_target eq 'Y'}">
										<img src="/nanum/site/img/common/new_window.png" alt="새창" />
									</c:if>
									</a>
									<%--3차메뉴 --%>
									<c:set var="sub_cnt" value="0" />
									<c:forEach items="${leftMenu.menuList}" var="subLeftMenu">
										<c:if test="${subLeftMenu.ct_chk eq 'Y'}">
											<c:set var="sub_cnt" value="${sub_cnt+1}" />
										</c:if>
									</c:forEach>
									<c:if test="${fn:length(leftMenu.menuList) > 0 and leftMenu.ct_tab ne 'Y' and sub_cnt > 0}">
									<div>
										<ul <c:if test="${idx ne leftMenu.ct_idx and ref_idx2 ne leftMenu.ct_idx}">style="display:none !important"</c:if>>
										
										<c:forEach items="${leftMenu.menuList}" var="subLeftMenu" varStatus="no2">
											<c:set var="submenu_url" value="${subLeftMenu.ct_menu_url eq '' ? 'javascript:;' : subLeftMenu.ct_menu_url}" />
											<c:choose>
												<%-- 예외 메뉴 처리 --%>
												<c:when test="${subLeftMenu.ct_chk ne 'Y'}">
												</c:when>
												<c:otherwise>
										
											<li <c:if test="${idx eq subLeftMenu.ct_idx and leftMenu.ct_idx eq subLeftMenu.ct_ref}">class="on"</c:if>>
												<a href="${submenu_url}" ${subLeftMenu.ct_url_target=='Y' ? 'target="_blank"' : '' }>${subLeftMenu.ct_name}
												<c:if test="${subLeftMenu.ct_url_target eq 'Y'}">
													<img src="/nanum/site/img/common/new_window.png" alt="새창" />
												</c:if>
												</a>
											</li>
										
												</c:otherwise>
											</c:choose>
										</c:forEach>
										
										</ul>
									</div>
									</c:if>
								</li>
								
								
							</c:otherwise>
						</c:choose>
					</c:forEach>
