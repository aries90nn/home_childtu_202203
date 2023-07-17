<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${!empty BUILDER.bs_logo }">
<c:set var="logoimg" value="<img src='/data/builder/${BUILDER.bs_logo }' alt='${BUILDER.bs_sitename }'>" />
</c:if>

<c:if test="${fn:length(closeList) > 0 or fn:length(banner3) > 0 }">

<!-- 상단팝업 -->

<!-- //상단팝업 -->
<script type="text/javascript">
//상단 팝업
$(function(){
var ck = ReadCookie( "headpopup_main" );
if( ck != "1" ){
$("#thead").show();
}
});
function todaycloseWin(){
	var objImage;
	var objLayer;
	var id_check_popup_close = document.getElementById("id_check_popup_close");

	try{
		objLayer = document.getElementById("thead");
	}
	catch(err){
		objLayer=null;
	}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		$(objLayer).slideDown();
	}
	else{
		$(objLayer).slideUp();
	}
	setCookie_toppop();
}

function setCookie_toppop(){
	var expire = new Date();
	expire.setDate(expire.getDate() + 1 );
	document.cookie = "headpopup_main=1; expires=" +  expire.toGMTString()+ "; path=/";
}
</script>

</c:if>

<!-- 상단영역 -->
<div id="head">
	<div class="wsize">
			<!-- 로고영역 -->
			<h1 class="logo"><a href="/${BUILDER_DIR }/"><img src="/data/builder/logo_2.jpg" alt="투명우산나눔활동" /></a></h1>
			<!-- //로고영역 -->
			
			<!-- 메뉴영역 -->
			<div id="menu_navi">
				<div id="menu_area">
					<ul>
						<%-- 공통으로 사용되는 상단메뉴 --%>
						<jsp:include page="inc_menu.jsp" />
					</ul>
				</div>
			</div>
			<!-- //메뉴영역 -->
			
			<!--오른쪽메뉴-->
			<ul class="aside_menu" >
				<c:if test="${empty sessionScope.ss_m_dupinfo}"><%-- 본인인증시 --%>
				<li class="in"><a href="/${BUILDER_DIR }/site/mylib/loanList.do?prepage=${nowPageEncode}" title="마이페이지">마이페이지</a></li>
				</c:if>
				<c:if test="${!empty sessionScope.ss_m_dupinfo}"><%-- 본인인증시 --%>
				<li class="lout"><a href="/${BUILDER_DIR }/member/logout.do?prepage=${nowPageEncode}" title="${sessionScope.ss_m_name }님 로그아웃">${sessionScope.ss_m_name }님 로그아웃</a></li> <%--${sessionScope.ss_m_name }님 <br />로그아웃 --%>
				<li class="in"><a href="/${BUILDER_DIR }/site/mylib/loanList.do?prepage=${nowPageEncode}" title="마이페이지">마이페이지</a></li></c:if>
				<%-- 
				<c:if test="${empty sessionScope.ss_m_id }"> <!--로그인전-->
				<li><a href="/${BUILDER_DIR }/site/member/login.do?prepage=${nowPageEncode}">로그인</a></li>
				<li><a href="/${BUILDER_DIR }/site/member/agree.do">회원가입</a></li>
				<!-- <li><a href="/${BUILDER_DIR }/member/regist.do">회원가입(자체)</a></li>-->
				</c:if>
				
				<c:if test="${!empty sessionScope.ss_m_id }"><!-- 로그인 후 -->
				<li class="lout"><a href="/${BUILDER_DIR }/member/logout.do">${sessionScope.ss_m_name }님<br />로그아웃</a></li>
				<c:if test="${sessionScope.ss_user_memberclass eq '0' }">
				<li><a href="/${BUILDER_DIR }/site/mylib/loanList.do">마이페이지</a></li>
				</c:if>
				<c:if test="${sessionScope.ss_user_memberclass eq '2' }">
				<li><a href="/${BUILDER_DIR }/site/mylib/modify.do">정보수정</a></li>
				</c:if>
				<c:if test="${empty sessionScope.ss_user_memberclass }">
				<li><a href="/${BUILDER_DIR }/member/modify.do">정보수정</a></li>
				<!-- <li><a href="/${BUILDER_DIR }/member/secedeOk.do" onclick="if(!confirm('회원탈퇴하시겠습니까?')){return false;}">회원탈퇴</a></li> -->
				</c:if>
				</c:if>
				--%>
			</ul>
			<!--오른쪽메뉴-->

		</div>
		
		<div class="header_dimmed">메뉴마스크</div>

		<script>
		$(document).ready(function(){
		var jbHeight = $( '#wrapper' ).height();
		$('#menu_navi > #menu_area > ul > li').mouseenter(function() {
		$('.header_dimmed').css('display','block');
		$('.header_dimmed').height(jbHeight);
		});
		$('#menu_navi > #menu_area > ul > li').mouseleave(function() {
		$('.header_dimmed').css('display','none');
		$('.header_dimmed').height(jbHeight);
		});
		});

		$(function(){
		$("#logo, .allmenu_btn, .tsearch, .tarea, #container").mouseover(function(){
		//$("#menu_navi .smenu_area").slideUp();
		$(".smenu_area").slideUp(250);
		$('.header_dimmed').css('display','none');
		smenu_status = false;
		});
		/*$("#logo, .allmenu_btn, .tsearch, .tarea, #container").keydown(function(){
		$("#menu_navi .smenu_area").slideUp(250);
		$('.header_dimmed').css('display','block');
		smenu_status = false;
		});*/

		});
		var fnPrint = function() {
			//document.body.innerHTML = contents_area.innerHTML;
			window.print();
		};

		</script>
		<!--// 메뉴영역 -->
	<div class="gnb_bg1"></div>

	<!-- 모바일메뉴 전체보기 -->
	<div class="viewAllMenu">
		<button type="button" class="btnViewAll">전체메뉴 열기</button><!-- 스크립트 : 클릭 시 <div class="viewAllMenu"> class="open" -->
		<div class="con">
			<nav>
				<div class="mem_log">
					<ul>
						<%--
						<c:if test="${empty sessionScope.ss_m_id }">
						<li><a href="/${BUILDER_DIR }/site/member/login.do">로그인</a></li>
						<li><a href="/${BUILDER_DIR }/site/member/agree.do">회원가입</a></li>
						</c:if>
						<c:if test="${!empty sessionScope.ss_m_id }">
						<li><a href="/${BUILDER_DIR }/member/logout.do">${sessionScope.ss_m_name }님 로그아웃</a></li>
						<li><a href="/${BUILDER_DIR }/site/mylib/loanList.do">마이페이지</a></li>
						</c:if>
						 --%>
						<c:if test="${empty sessionScope.ss_m_coinfo}"><%-- 본인인증시 --%>
							<li class="in"><a href="/${BUILDER_DIR }/site/mylib/loanList.do" title="마이페이지">마이페이지</a></li>
						</c:if>
						<c:if test="${!empty sessionScope.ss_m_coinfo}"><%-- 본인인증시 --%>
							<li class="lout"><a href="/${BUILDER_DIR }/member/logout.do" title="로그아웃">${sessionScope.ss_m_name }님 로그아웃</a></li> <%--${sessionScope.ss_m_name }님 <br />로그아웃 --%>
							<li class="in"><a href="/${BUILDER_DIR }/site/mylib/loanList.do" title="마이페이지">마이페이지</a></li>
						</c:if>
					</ul>
				</div>
				<ul>
					<c:forEach items="${topMenuList}" var="topMenu" varStatus="no">
					<c:set var="menu_url" value="${pc_version eq 'Y' ? topMenu.ct_menu_url : topMenu.ct_mobile_menu_url}" />
					<li class="jsMmenu">
						<a href="javascript:;">${topMenu.ct_name}</a>
						<c:set var="flag_submenu" value="N"/>
						<c:choose>
						<c:when test="${topMenu.ct_idx eq '6'}">
						<c:if test="${fn:length(topMenu.menuList) > 1}">
						<c:set var="flag_submenu" value="Y"/>
						</c:if>
						</c:when>
						<c:otherwise>
						<c:if test="${fn:length(topMenu.menuList) > 0}">
						<c:set var="flag_submenu" value="Y"/>
						</c:if>
						</c:otherwise>
						</c:choose>


						<div class="jsMsubMenu" style="display:none;">
							<ul>

								<c:if test="${flag_submenu eq 'Y'}">
								<c:forEach items="${topMenu.menuList}" var="subMenu" varStatus="no2">
								<c:set var="submenu_url" value="${subMenu.ct_menu_url eq '' ? '#nolink' : subMenu.ct_menu_url}" />

								<c:choose>
								<%--예외처리 --%>
								<c:when test="${subMenu.ct_chk ne 'Y'
								or (fn:contains(submenu_url, '/site/mylib/modify.do') and empty sessionScope.ss_m_id)
								or (fn:contains(submenu_url, '/site/mylib/memberDrop.do') and empty sessionScope.ss_m_id)
								or (fn:contains(submenu_url, '/site/member/idSearch.do') and !empty sessionScope.ss_m_id)
								}">
								</c:when>
								<c:otherwise>
								<li><a href="${submenu_url}" ${subMenu.ct_url_target eq 'Y' ? 'target="_blank"' : '' }>${subMenu.ct_name}</a></li>
								</c:otherwise>
								</c:choose>

								</c:forEach>

								</c:if>
							</ul>
						</div>
					</li>
					</c:forEach>
				</ul>
			</nav>
			<script type="text/javascript">
			$(function(){
			$(".jsMmenu").find("a:first").click(function(){
			var menu1 = $(this);
			var sub = $(this).next(".jsMsubMenu");
			if (sub.is(":visible")){
			sub.slideUp();
			menu1.removeClass("on");
			}else{
			$(".jsMsubMenu").slideUp();
			$(".jsMmenu").find("a:first").removeClass("on");

			sub.slideDown();
			menu1.addClass("on");
			}
			})

			$(".jsMsubMenu").find("a.depth3").click(function(){
			var ul = $(this).parent().find("ul");
			if( ul.length != 0 ){
			if(ul.css("display") == "none"){
			$(this).addClass("on");
			ul.slideDown();
			}else{
			$(this).removeClass("on");
			ul.slideUp();
			}
			return false;
			}
			});
			});
			</script>
		</div>
		<button type="button" class="btnAllClose">전체메뉴 닫기</button><!-- 스크립트 : 클릭 시 class="open" 삭제 -->
	</div>
	<div class="dimmed"></div>
	<!-- //모바일메뉴 -->
</div>
<!-- //상단영역 -->

