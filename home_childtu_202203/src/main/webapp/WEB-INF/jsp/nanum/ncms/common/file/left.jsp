<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!-- 사이드영역 열기/닫기 -->
<div class="side_close"><a href="javascript: SetMngLeft('N');" onMouseOver='rollover7.src="/nanum/ncms/img/default/side_close_on.png"' onMouseOut='rollover7.src="/nanum/ncms/img/default/side_close.png"'><img name="rollover7" src="/nanum/ncms/img/default/side_close.png" class="png24" alt="사이드 영역 숨기기" /></a></div>
<div class="side_open disnone"><a href="javascript: SetMngLeft('Y');" onMouseOver='rollover7_.src="/nanum/ncms/img/default/side_open_on.png"' onMouseOut='rollover7_.src="/nanum/ncms/img/default/side_open.png"'><img name="rollover7_" src="/nanum/ncms/img/default/side_open.png" class="png24" alt="사이드 영역 열기" /></a></div>
<!-- <div id="side_alert" style="display:none;position:absolute;left:58px;top:44px;z-index:999;"><img src="/nanum/ncms/img/common/side_open_point.png" class="png24"/></div> -->
<!-- //사이드영역 열기/닫기 -->

<!-- 사이드영역 -->
<div id="side_wrap">

	<!-- 상단 -->
	
	<div class="top_area">
		<h2><span>${ss_m_name}</span>님
		<p class="ip_default">IP_${pageContext.request.remoteHost}</p>
		<p class="ip_chic mt10">마지막 로그인<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${ss_m_lastdate}</p>
		<p class="ip_chic">마지막 로그인IP<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${ss_m_ip}</p>
		</h2>
		<br/>
	</div>
	<!-- //상단 -->

	<!-- 카테고리 -->
	<h2 class="hidden">회원/권한관리</h2>
	
	<div id="sidemenu" class="menu_area">
		<c:forEach items="${menuLeftList}" var="parent" varStatus="no">
			<c:if test="${parent.menu_chk eq 'Y'}">
			<ul>
				<li id="menu18" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />${parent.ct_name}</a>
					<c:if test= "${fn:length(menuLeftList) > 0}">
						<ul class="depth2">
							<c:forEach items="${parent.menuList}" var="child" varStatus="no2"> 
								<c:if test="${child.menu_chk eq 'Y'}">
									<li id="menu1-${child.ct_idx}" class="none"><a href="${child.ct_url}"  class="category dot">${child.ct_name}</a></li>
								</c:if>
							</c:forEach>
						</ul>
					</c:if>
				</li>
			</ul>
			</c:if>
		</c:forEach>
	</div>

</div>
<!-- //사이드영역 -->