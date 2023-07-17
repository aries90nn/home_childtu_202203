<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 상단팝업 -->
<c:if test="${fn:length(banner3List) > 0}">
<div id="top_head"  style="display:none;">
	<div class="area">
		<p class="close">
		<input type="checkbox" id="id_check_popup_close" name="id_check_popup_close" value="Y"> <label for="id_check_popup_close">1일간 열지 않음</label> 
		<a href="javascript:;" onclick="todaycloseWin();">닫기</a>
		</p>
		<div class="fadeOut owl-carousel owl-theme">
			<c:forEach items="${banner3List}" var="banner3" varStatus="no">
				<div class="item"><img src="/data/banner3/${banner3.b_l_img}" alt="${banner3.b_l_subject}"  style="width:1100px;height:130px" /></div>
			</c:forEach>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		var ck = ReadCookie( "headpopup_arboretum" );
		if( ck != "1" ){
			$("#top_head").show();
		}
	});
	function todaycloseWin(){ 
		var objImage;
		var objLayer;
		var id_check_popup_close = document.getElementById("id_check_popup_close");

		try{objLayer = document.getElementById("top_head");}catch(err){objLayer=null;}

		if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
			$(objLayer).slideDown();
		}else{
			$(objLayer).slideUp();
		}
		if(id_check_popup_close.checked){
			setCookie();
		}
	}

	function setCookie(){
		var expire = new Date();
		expire.setDate(expire.getDate() + 1 );
		document.cookie = "headpopup_arboretum=1; expires=" +  expire.toGMTString()+ "; path=/";
	}

	// 팝업창 띄우지 않게 하기 시작
	function ReadCookie(name){
		var nameOfCookie=name+"=";
					//alert(nameOfCookie);
		var x=0;
		while(x<=document.cookie.length)
		{
			var y=(x+nameOfCookie.length)
			if(document.cookie.substring(x,y)==nameOfCookie){
				if((endOfCookie=document.cookie.indexOf(";",y))==-1)
					endOfCookie=document.cookie.length;
				return unescape(document.cookie.substring(y,endOfCookie));
			}
			x=document.cookie.indexOf(" ",x) +1;
			if(x==0)
				break;
		}
		return "";
	}
	</script>
<script>
  jQuery(document).ready(function($) {
    $('.fadeOut').owlCarousel({
      items: 1,
      animateOut: 'fadeOut',
      loop: true,
      margin: 10,
    });
    $('.custom1').owlCarousel({
      animateOut: 'slideOutDown',
      animateIn: 'flipInX',
      items: 1,
      margin: 0,
      stagePadding: 50,
      smartSpeed: 800
    });
  });
</script>
<!-- //상단팝업 -->
</c:if>

<!-- 상단영역 -->
<div id="head">
	<div class="wsize">
		<div class="wsize_area">
			<h1 id="logo"><a href="/main.do"><img src="/data/info/${siteinfo.sc_logo}" alt="${siteinfo.sc_sitename} 로고" style="width:160px;height:82px" /></a></h1>
			<div class="head_fr">
				<c:if test="${auth_join eq 'Y'}">
					<ul class="hmamber">
						<c:choose>
							<c:when test="${empty sessionScope.ss_m_id}">
								<li><a href="${DOMAIN_HTTPS}/member/login.do?prepage=${nowPageEncode}">로그인</a></li>
								<li><a href="${DOMAIN_HTTPS}/member/join.do">회원가입</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${DOMAIN_HTTPS}/member/modify.do">정보수정</a></li>
								<li><a href="/member/logout.do">로그아웃</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</c:if>
				<!-- 날씨 -->
				<div class="tweather" id="jsTopWeather">
					<!-- div class="box">
						<p class="weater_icon icon01"></p>
						<p class="cate">백두대간</p>
						<p class="number">28.5˚C</p>
					</div -->
				</div>
				<!--날씨-->
				<div class="weather_w" id="jsTopWeather"><div style="width:114px;"></div></div>
				<script type="text/javascript" >
					$(function(){
						$.ajax({
							url: "/weather.do", 
							dataType:"html",
							async:true,
							success: function(msg){	
								$("#jsTopWeather").html(msg);
							}
						});
					});
				</script>
				<!--//날씨-->	
			</div>
		</div>
		<!-- 메뉴영역 -->
		<div id="menu_navi">
			<div class="allbtn"><a href="/sitemap.do">
				<div class="hamburger">
				  <div class="top-bun"></div>
				  <div class="meat"></div>
				  <div class="bottom-bun"></div>
				</div>
			</a></div>


			<div class="gnb_bg"></div>
			<c:if test="${fn:length(topMenuList) > 0}">
			<ul class="h_menu">
				<c:forEach items="${topMenuList}" var="topMenu" varStatus="no">
				<c:set var="menu_url" value="${topMenu.ct_menu_url eq '' ? '/' : topMenu.ct_menu_url}" />
				<li><a href="${menu_url}" ${topMenu.menuList[0].ct_url_target eq 'Y' ? 'target="_blank"' : '' } class="hvr-underline-from-center">
					<c:choose>
						<c:when test="${topMenu.ct_img_status eq 'Y' and topMenu.ct_img_off ne '' and topMenu.ct_img_off ne null}">
							<img src="/data/cms/${topMenu.ct_img_off}" alt="${topMenu.ct_name}" />
						</c:when>
						<c:otherwise>
							${topMenu.ct_name}
						</c:otherwise>
					</c:choose>
					</a></li>
				</c:forEach>
			</ul>
			<div id="h_menu_all" style="display:none;">
				<div class="hmenu_w">
					<c:forEach items="${topMenuList}" var="topMenu" varStatus="no">
					<dl>
						<dt>
						<c:set var="menu_url" value="${topMenu.ct_menu_url eq '' ? '/' : topMenu.ct_menu_url}" />
						<a href="${menu_url}">${topMenu.ct_name}</a></dt>
						<dd>
							<c:if test="${fn:length(topMenu.menuList) > 0}">
							<ul>
								<c:forEach items="${topMenu.menuList}" var="subMenu" varStatus="no2">
								<c:set var="submenu_url" value="${subMenu.ct_menu_url eq '' ? '/' : subMenu.ct_menu_url}" />
								<li><a href="${submenu_url}" ${subMenu.ct_url_target eq 'Y' ? 'target="_blank"' : '' }>${subMenu.ct_name}
									<c:if test="${subMenu.ct_url_target eq 'Y'}">
										<img src="/nanum/site/img/common/new_window.png" alt="새창" />
									</c:if>
								</a></li>
								</c:forEach>
							</ul>
							</c:if>
						</dd>
					</dl>
					</c:forEach>
				</div>
			</div>
			</c:if>
			<p class="mmobile_tit"><a href="#" onclick="m_menu();">${empty first_menu_name ? '메뉴 전체보기' : first_menu_name}</a></p>
			<div id="menu_all" style="display:none;">
				<p class="mmobile_close"><a href="#" onclick="m_menu();" onfocus="if(event .keycode==13) { m_menu(); }">${first_menu_name == null ? '메뉴 전체보기' : first_menu_name}</a></p>
				<div class="menu_cont">
					<c:forEach items="${topMenuList}" var="topMenu" varStatus="no">
					<dl>
						<dt><a href="${topMenu.ct_menu_url}">${topMenu.ct_name}</a></dt>
						<dd>
							<ul>
								<c:forEach items="${topMenu.menuList}" var="subMenu" varStatus="no2">
								<c:set var="submenu_url" value="${subMenu.ct_menu_url eq '' ? '/' : subMenu.ct_menu_url}" />
								<li><a href="${submenu_url}" ${subMenu.ct_url_target eq 'Y' ? 'target="_blank"' : '' }>${subMenu.ct_name}
									<c:if test="${subMenu.ct_url_target eq 'Y'}">
										<img src="/nanum/site/img/common/new_window.png" alt="새창" />
									</c:if>
								</a></li>
								</c:forEach>
							</ul>
						</dd>
					</dl>
					</c:forEach>
				</div>
			</div>
		</div>
		<!--// 메뉴영역 -->
	</div>
</div>
<!--// 상단영역 -->
<!-- 모바일전체보기 
<div class="mobile_menu">
	<p class="tit"><a href="#" onclick="m_menu();" onfocus="if(event .keycode==13) { m_menu(); }">한국수목원관리원 소개</a></p>
	<div class="box" id="m_menu_cont" style="display:none;">
		<dl>
			<dt>ddd</dt>
			<dd>dd</dd>
		</dl>
	</div>
</div>
// 모바일전체보기 -->


