<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/WEB-INF/jsp/nanum/site/builder/skin/${BUILDER_SKIN }/common/file/header.jsp" />
<jsp:include page="/WEB-INF/jsp/nanum/site/builder/skin/${BUILDER_SKIN }/common/file/left.jsp" />
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/member.css" />
<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/${BUILDER_SKIN }/common/css/ndls.css" />
						<!-- 컨텐츠상단영역 -->
						<div id="cont_head">
							<h2>${title}</h2>
							<!-- 현재위치 -->
							<div id="location">
								<ul>
									<li><a href="/${BUILDER_DIR }/">home</a></li>
									<li>${first_menu_name}</li>
									<c:if test="${!empty two_menu_name}"><li>${two_menu_name}</li></c:if>
									<li class="now">${title}</li>
								</ul>
							</div>
							<!-- //현재위치 -->
						</div>
						<!-- //컨텐츠상단영역 -->
		
						<!-- 내용영역 -->
						<div id="cont_wrap">
						
						<%--탭메뉴 상단내용 --%>
						${ct_header }
						<%--탭메뉴 상단내용 끝 --%>
						
						<%--탭메뉴생성 --%>
						<c:if test="${fn:length(tabList) > 0}">
							<div class="con_tab tab_b">
								<ul class="no${fn:length(tabList)}"><!--갯수에 따라 숫자가 바뀝니다.-->
							<c:forEach items="${tabList}" var="tab" varStatus="no">
									<li><a href="${empty tab.ct_menu_url ? 'javascript:;' : tab.ct_menu_url}" <c:if test="${idx eq tab.ct_idx or ref_idx3 eq tab.ct_idx}">class="on"</c:if>>${tab.ct_name}</a></li>
							</c:forEach>
								</ul>
							</div>
						</c:if>
						
						<%--탭메뉴생성 --%>
						<c:if test="${fn:length(tabList2) > 0}">
							<div class="con_tab tab_b">
								<ul class="no${fn:length(tabList2)}"><!--갯수에 따라 숫자가 바뀝니다.-->
							<c:forEach items="${tabList2}" var="tab" varStatus="no">
									<li><a href="${empty tab.ct_menu_url ? 'javascript:;' : tab.ct_menu_url}" <c:if test="${idx eq tab.ct_idx or ref_idx3 eq tab.ct_idx}">class="on"</c:if>>${tab.ct_name}</a></li>
							</c:forEach>
								</ul>
							</div>
						</c:if>
				
						<decorator:body />
				
						</div>
						<!-- //내용영역 -->
		
<jsp:include page="/WEB-INF/jsp/nanum/site/builder/skin/${BUILDER_SKIN }/common/file/foot_sub.jsp" />