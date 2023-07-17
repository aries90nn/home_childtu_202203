<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 스킵네비게이션 -->
<div id="skipnavigation">
<h1>스킵 네비게이션</h1>
	<ul>
		<li><a href="#contents" title="본문으로 바로가기" >본문으로 바로가기</a></li>
		<li><a href="#big_menu" title="대메뉴로 바로가기" >대메뉴로 바로가기</a></li>
		<li><a href="#sidebar" title="서브메뉴로 바로가기" >서브메뉴로 바로가기</a></li>
	</ul>
</div>
<!-- //스킵네비게이션 -->
<hr />

<%--사이트별 첫번째 업무메뉴링크 선언 --%>
<%@ include file="./inc_first_url.jsp" %>

<c:if test="${fn:length(SITE_LIST) > 1 }">

<style>
ul.sitemenu{overflow:hidden;}
ul.sitemenu li {float:left;}
ul.sitemenu li a{display:block; font-weight:bold; padding:7px 24px; color:#fff; background-color:#826873;}
ul.sitemenu li a.on{background-color:#990033;}
</style>
<div style="width:100%;background-color:#826873;">
	<ul class="sitemenu">
<c:forEach items="${SITE_LIST }" var="site">
	<c:set var="href" value="${firstMenuMap[site.bs_directory] }" />
	<c:if test="${empty href }">
		<c:set var="href" value="/${site.bs_directory }/ncms/" />
	</c:if>
		<li><a href="${href }" ${BUILDER_DIR eq site.bs_directory ? 'class="on"' : '' }>${site.bs_sitename }</a></li>

</c:forEach>
	</ul>
</div>

</c:if>

<!-- 퀵영역 -->
<div id="quick_wrap" class="quick_wrap_default">
	<div class="area01"><a href="/${BUILDER_DIR }/ncms/index.do"><img src="/nanum/ncms/img/common/lquick_icon01.png" class="png24" alt="홈페이지 바로가기 아이콘" /><span>홈으로</span></a></div>
	<div class="area02"><a href="#" onclick="javascript: window.open('/${BUILDER_DIR }/main.do');"><span>홈페이지<br/>바로가기</span></a></div>
	<div class="area03"><a href="http://www.nninc.co.kr" target="_blank"><span>나눔<br/>아이앤씨</span></a></div>
	<div class="area04"><a href="/${BUILDER_DIR }/ncms/logout.do"><span>로그아웃</span></a></div>
	<cite><img src="/nanum/ncms/img/default/cite.gif" alt="copyright (c) nanum i&c. all rights reserved." /></cite>
</div>
<!-- //퀵영역 -->

<!-- 상단 영역-->
<div id="head_wrap">
	<div class="head_wrap_default">
		<h1 class="logo">NCMS v1.0</h1>
		<h2 class="hidden">대메뉴</h2>
		
		<ul id="top_area">

<c:if test="${!empty first_menu_url }">
			<li><a ${mng_menu_group.key eq '01' ? 'class="on"' : '' } href="${first_menu_url }" title="사이트관리" onMouseOver='rollover1.src="/nanum/ncms/img/default/top_icon01_on.png"' onMouseOut='rollover1.src="/nanum/ncms/img/default/top_icon01${mng_menu_group.menu01_img}.png"'><img name="rollover1" src="/nanum/ncms/img/default/top_icon01${mng_menu_group.menu01_img}.png" class="png24" alt="업무관리" /><span>업무관리</span></a></li>
</c:if>
<c:if test="${sessionScope.ss_g_num eq '1' or sessionScope.ss_g_num eq '4'}">
			<li><a ${mng_menu_group.key eq '02' ? 'class="on"' : '' } href="/${BUILDER_DIR }/ncms/cms/write.do" title="메뉴/페이지" onMouseOver='rollover2.src="/nanum/ncms/img/default/top_icon02_on.png"' onMouseOut='rollover2.src="/nanum/ncms/img/default/top_icon02${mng_menu_group.menu02_img}.png"'><img name="rollover2" src="/nanum/ncms/img/default/top_icon02${mng_menu_group.menu02_img}.png" class="png24" alt="메뉴/페이지" /><span>메뉴/페이지</span></a></li>
			<li><a ${mng_menu_group.key eq '03' ? 'class="on"' : '' } href="/${BUILDER_DIR }/ncms/member/list.do" title="메뉴/페이지" onMouseOver='rollover3.src="/nanum/ncms/img/default/top_icon03_on.png"' onMouseOut='rollover3.src="/nanum/ncms/img/default/top_icon03${mng_menu_group.menu03_img}.png"'><img name="rollover3" src="/nanum/ncms/img/default/top_icon03${mng_menu_group.menu03_img}.png" class="png24" alt="메뉴/페이지" /><span>회원/권한관리</span></a></li>

</c:if>
			<li><a ${mng_menu_group.key eq '04' ? 'class="on"' : '' } href="/${BUILDER_DIR }/ncms/board_config/list.do" title="메뉴/페이지" onMouseOver='rollover4.src="/nanum/ncms/img/default/top_icon04_on.png"' onMouseOut='rollover4.src="/nanum/ncms/img/default/top_icon04${mng_menu_group.menu04_img}.png"'><img name="rollover4" src="/nanum/ncms/img/default/top_icon04${mng_menu_group.menu04_img}.png" class="png24" alt="부가기능" /><span>부가기능</span></a></li>
			<li><a ${mng_menu_group.key eq '05' ? 'class="on"' : '' } href="/${BUILDER_DIR }/ncms/stats/h.do" title="메뉴/페이지" onMouseOver='rollover5.src="/nanum/ncms/img/default/top_icon05_on.png"' onMouseOut='rollover5.src="/nanum/ncms/img/default/top_icon05${mng_menu_group.menu05_img}.png"'><img name="rollover5" src="/nanum/ncms/img/default/top_icon05${mng_menu_group.menu04_img}.png" class="png24" alt="통계자료" /><span>통계자료</span></a></li>
<c:if test="${sessionScope.ss_g_num eq '1' or sessionScope.ss_g_num eq '4'}">
			<li><a ${mng_menu_group.key eq '06' ? 'class="on"' : '' } href="/${BUILDER_DIR }/ncms/ip/write.do" title="메뉴/페이지" onMouseOver='rollover6.src="/nanum/ncms/img/default/top_icon06_on.png"' onMouseOut='rollover6.src="/nanum/ncms/img/default/top_icon06${mng_menu_group.menu06_img}.png"'><img name="rollover6" src="/nanum/ncms/img/default/top_icon06${mng_menu_group.menu06_img}.png" class="png24" alt="통계자료" /><span>보안설정</span></a></li>
</c:if>
		</ul>
	</div>
	<!-- div id="skinset">
		<h2 class="hidden">스킨설정</h2>
		<!-- 스킨설정
		<div class="nation_btn">
			<div onmouseover="nationBlock('pic01');" onmouseout="nationNone('pic01');">
				<div class="tit_default" style="cursor:pointer;">스킨설정</div>		
				<div id="pic01" class="list disnone">
					<p class="tit">스킨설정</p>
					<ul>
						<li class="on"><a href="javascript:SetSkin('default');"><span class="default"></span>기본</a></li>
						<li><a href="javascript:SetSkin('casual');"><span class="casual"></span>캐주얼</a></li>
						<li><a href="javascript:SetSkin('chic');"><span class="chic"></span>시크</a></li>
						<li><a href="javascript:SetSkin('dandy');"><span class="dandy"></span>댄디</a></li>
						<li><a href="javascript:SetSkin('marine');"><span class="marine"></span>마린</a></li>
					</ul>
				</div>								
			</div>
		</div>
		//스킨설정
	</div -->
	<!-- <p class="manual"><a href="/nanum/manual/index.html" target="_blank"><span>관리자 매뉴얼</span></a></p> -->
</div>
<!-- //상단 영역 -->