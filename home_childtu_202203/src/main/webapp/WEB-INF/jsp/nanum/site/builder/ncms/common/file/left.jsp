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
		<span class="lib_names">${BUILDER.bs_sitename }</span>
		<h2><span>${ss_m_name}</span>님
		<p class="ip_default">IP_${pageContext.request.remoteHost}</p>
		<p class="ip_chic mt10">마지막 로그인<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${ss_m_lastdate}</p>
		<p class="ip_chic">마지막 로그인IP<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${ss_m_ip}</p>
		<p class="ip_chic">남은문자 : ${smsCount } 건<br/>건당요금 20원(vat별도)</p>
		</h2>
		<br/>
	</div>
	<!-- //상단 -->

	<!-- 카테고리 -->
	<h2 class="hidden">사이트관리자메뉴</h2>
	
	<div id="sidemenu" class="menu_area">

	<c:if test="${!empty mng_menu_group.key }">
		<c:choose>
			<c:when test="${mng_menu_group.key eq '01' }">
				<c:set var="leftmenu" value="./left${mng_menu_group.key }_${BUILDER_DIR }.jsp" />
			</c:when>
			<c:otherwise>
				<c:set var="leftmenu" value="./left${mng_menu_group.key }.jsp" />
			</c:otherwise>
		</c:choose>
		
	</c:if>
	
	<c:catch var="incException">
		<jsp:include page="${leftmenu }" />
	</c:catch>
	<c:if test="${incException != null }">
		<ul>
			<li id="menu4" class="on">
				<a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />(${leftmenu }) 생성하세요.</a>
			</li>
		</ul>
	</c:if>
		
	</div>

</div>
<!-- //사이드영역 -->