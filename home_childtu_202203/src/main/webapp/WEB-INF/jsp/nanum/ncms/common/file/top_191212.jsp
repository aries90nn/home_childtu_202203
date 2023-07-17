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

<!-- 퀵영역 -->
<div id="quick_wrap" class="quick_wrap_default">
	<div class="area01"><a href="/ncms/index.do"><img src="/nanum/ncms/img/common/lquick_icon01.png" class="png24" alt="홈페이지 바로가기 아이콘" /><span>홈으로</span></a></div>
	<div class="area02"><a href="#" onclick="javascript: window.open('/main.do');"><span>홈페이지<br/>바로가기</span></a></div>
	<div class="area03"><a href="http://www.nninc.co.kr" target="_blank"><span>나눔<br/>아이앤씨</span></a></div>
	<div class="area04"><a href="/ncms/logout.do"><span>로그아웃</span></a></div>
	<cite><img src="/nanum/ncms/img/default/cite.gif" alt="copyright (c) nanum i&c. all rights reserved." /></cite>
</div>
<!-- //퀵영역 -->

<!-- 상단 영역-->
<div id="head_wrap">
	<div class="head_wrap_default">
		<h1 class="logo">NCMS v1.0</h1>
		<h2 class="hidden">대메뉴</h2>
		
		<ul id="top_area">
			<c:forEach items="${menuTopList}" var="menuTop" varStatus="no">
			<c:set var="flagon" value="${now_ct_code == menuTop.ct_codeno ? 'Y' : 'N'}" />
			<li><a ${flagon=='Y' ? "class='on'" : ""} href="${menuTop.ct_url}" title="${menuTop.ct_name}" onMouseOver='rollover${no.count}.src="/nanum/ncms/img/default/${menuTop.ct_icon}_on.png"' onMouseOut='rollover${no.count}.src="/nanum/ncms/img/default/${menuTop.ct_icon}${flagon=="Y" ? "_on" : ""}.png"'><img name="rollover${no.count}" src="/nanum/ncms/img/default/${menuTop.ct_icon}${flagon== 'Y' ? '_on' : ''}.png" class="png24" alt="${menuTop.ct_name}" /><span>${menuTop.ct_name}</span></a></li>
			</c:forEach>
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
	<p class="manual"><a href="/nanum/manual/index.html" target="_blank"><span>관리자 매뉴얼</span></a></p>
</div>
<!-- //상단 영역 -->