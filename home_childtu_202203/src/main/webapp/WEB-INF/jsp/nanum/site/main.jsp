<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="./common/file/header.jsp" />


<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/swiper.min.css" />
<script type="text/javascript" src="/nanum/site/common/js/swiper.min.js"></script>


<!-- 중간영역 -->
<div id="container">


	<!-- 메인비주얼-->
	<div id="mimg" class="swiper-container">
		<div class="area swiper-wrapper">
			<c:forEach items="${visualList}" var="visual" varStatus="no">
			<div class="box swiper-slide" style="background:url('/data/visual/${visual.v_img}') no-repeat top center;background-size:1590px 600px;">
				<div class="txt_b">
					<p class="txt1">지속 가능한 산림생물자원의 가치를 창출하는 글로벌 수목원 선도기관</p>
					<p class="txt2">한국수목원관리원</p>
					<p class="txt3">${visual.v_memo}</p>
				</div>
			</div>
			</c:forEach>
		</div>
		<div class="control">
			<div class="box"> 
				<p class="swiper-button-prev1"></p>
				<p class="swiper-button-next1"></p>
			</div>
		</div>
		<script>
		var swiper = new Swiper('.swiper-container', {
		  autoHeight: true, //enable auto height
		  spaceBetween: 20,
		  autoplay: {
	        delay: 4000,
	        disableOnInteraction: false,
	      },
		  pagination: {
			el: '.swiper-pagination',
			clickable: true,
		  },
		 loop: true,
		 effect: 'fade',
		  navigation: {
			nextEl: '.swiper-button-next1',
			prevEl: '.swiper-button-prev1',
		  },
		});
	  </script>
	</div>
	<!--//메인비주얼-->

<!-- bxSlider Javascript file -->
<script type="text/javascript" src="/nanum/site/common/js/jquery.bxslider.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
	 var slider = $('.bxslider').bxSlider({
		auto: false,
		pager:false,
		mode: 'vertical',
		nextSelector: '#pronext',
		prevSelector: '#proprev',
		
		prevText: '',   
		nextText: ''
	 });

	 $('#pronext').click(function(){
		slider.goToNextSlide();
		 return false;
	});

	$('#proprev').click(function(){
	  slider.goToPrevSlide();
	  return false;
	});

	});
</script>
	<!--소속기관바로가기-->
	<div class="attached">
		<ul class="bxslider" >
			<li>
				<div class="cont">
					<dl>	
						<dt>국립백두대간수목원</dt>
						<dd>국토균형발전위원회의결정으로<br />
						백두대간을 체계적으로 보호</dd>
					</dl>
					<p class="btn"><a href="http://www.bdna.or.kr/" target="_blank">바로가기</a></p>
				</div>
				<div class="pic"><img src="/nanum/site/img/main/attached_img01.jpg" alt="백두대간수목원" /></div>
				<div class="picm"><a href="http://www.bdna.or.kr/" target="_blank"><img src="/nanum/site/img/main/attached_img01.jpg" alt="백두대간수목원" /></a></div>
			</li>
			<li>
				<div class="cont">
					<dl>	
						<dt>국립세종수목원</dt>
						<dd>수집·전시·보전과 기후변화 취약 식물종<br />
						모니터링과 연구 기능 등을 수행</dd>
					</dl>
					<p class="btn"><a href="/contents.do?idx=40">바로가기</a></p>
				</div>
				<div class="pic"><img src="/nanum/site/img/main/attached_img02.jpg" alt="국립세종수목원" /></div>
				<div class="picm"><a href="/contents.do?idx=40"><img src="/nanum/site/img/main/attached_img02.jpg" alt="국립세종수목원" /></a></div>
			</li>
			<li>
				<div class="cont">
					<dl>	
						<dt>국립새만금수목원</dt>
						<dd>간척지를 고려한 내염 방풍 수종 개발<br />
						숲 조성, 염생식물의 관리</dd>
					</dl>
					<p class="btn"><a href="/contents.do?idx=41">바로가기</a></p>
				</div>
				<div class="pic"><img src="/nanum/site/img/main/attached_img03.jpg" alt="국립새만금수목원" /></div>
				<div class="picm"><a href="/contents.do?idx=41"><img src="/nanum/site/img/main/attached_img03.jpg" alt="국립새만금수목원" /></a></div>
			</li>
		</ul>
		<div class="control"style="z-index:999 !important;">
			<p class="up"><img src="/nanum/site/img/main/up_btn.png" alt="이전"  id="proprev" style="cursor:pointer;"/></p>
			<p class="down" ><img src="/nanum/site/img/main/down_btn.png" alt="다음"   id="pronext" style="cursor:pointer;"/></p>
		</div>
	</div>
	<!--//소속기관바로가기-->

	<!--메뉴바로가기-->
	<div class="gomenu">
		<div class="box">
			<div class="tit_box">
				<a href="/contents.do?idx=106">
					<p class="stxt">한국수목원관리원</p>
					<p class="btxt">정보공개</p>
				</a>
			</div>
		</div>
		<div class="box">			
			<div class="tit_box">
				<a href="/contents.do?idx=28">
					<p class="stxt">한국수목원관리원</p>
					<p class="btxt">기관소개</p>
				</a>
			</div>
			
		</div>
	</div>
	<!--//메뉴바로가기-->


	<!--게시판-->
	<div class="mboard">
		
			
		
		<div class="area">

			<ul>
				<li><a href="javascript:;" class="on" id="tab1" onmouseover="firTab();return false;" onfocus="firTab();return false;">공지사항</a>
					<div class="cont" id="hlist1">

						<div class="swiper-container swiper_name1">
							<div class="swiper-wrapper">
							<c:forEach items="${noticeList}" var="notice" varStatus="no">
							<div class="swiper-slide">
								<div class="box">
									<p class="date">${fn:substring(notice.b_regdate,0,10)}</p>
									<p class="title">${notice.b_subject}</p>
									<p class="txt">${notice.b_content}</p>
									<p class="btn"><a href="/contents.do?proc_type=view&a_num=${notice.a_num }&b_num=${notice.b_num }">자세히보기</a></p>
								</div>
							</div>
							</c:forEach>
							</div>
						 </div>
						 <c:if test="${fn:length(noticeList) > 0}">
						<!-- 화살표버튼 -->
						<div class="control">
							<div class="swiper-button-next"></div>
							<div class="swiper-button-prev"></div>
						</div>
						<!-- //화살표버튼 -->
						</c:if>
					</div>
				</li>
				<li><a href="javascript:;" id="tab2" onmouseover="twoTab();return false;" onfocus="twoTab();return false;">채용정보</a>
					<div class="cont" id="hlist2" style="display:none;">

						<div class="swiper-container swiper_name2">
							<div class="swiper-wrapper">
							<c:forEach items="${boardList}" var="board" varStatus="no">
								<div class="swiper-slide">
									<div class="box">
										<p class="date">${fn:substring(board.b_regdate,0,10)}</p>
										<p class="title">${board.b_subject}</p>
										<p class="txt">${board.b_content}</p>
										<p class="btn"><a href="/contents.do?proc_type=view&a_num=${board.a_num }&b_num=${board.b_num}">자세히보기</a></p>
									</div>
								</div>
								</c:forEach>
							</div>
						 </div>
						 <c:if test="${fn:length(boardList) > 0}">
						<!-- 화살표버튼 -->
						<div class="control">
							<div class="swiper-button-next2"></div>
							<div class="swiper-button-prev2"></div>
						</div>
						<!-- //화살표버튼 -->
						</c:if>
					</div>
				</li>
			</ul>
			<script>
			var swiper;
			var init=false;
			var init2 =false;
			function firTab(){
				$("#hlist1").css("display", "");
				$("#tab1").addClass("on");
				
				$("#hlist2").css("display", "none");
				$("#tab2").removeClass("on");
				
				if(!init){
					//공지사항 슬라이드
					swiper = new Swiper('.swiper_name1', {
					slidesPerView: 3,
					spaceBetween: 55,
					breakpoints: {
						736: {
						  slidesPerView: 1,
						  spaceBetween: 30,
						}
					},
					initialSlide: 0,
					centeredSlides: true,
					loop: true,
					navigation: {
						nextEl: '.swiper-button-next',
						prevEl: '.swiper-button-prev',
					},
					});
				}else{
					swiper.destroy();
					//공지사항 슬라이드
					swiper = new Swiper('.swiper_name1', {
					slidesPerView: 3,
					spaceBetween: 55,
					breakpoints: {
						736: {
						  slidesPerView: 1,
						  spaceBetween: 30,
						}
					},
					initialSlide : 0,
					centeredSlides: true,
					loop: true,
					navigation: {
						nextEl: '.swiper-button-next',
						prevEl: '.swiper-button-prev',
					},
					});
					//swiper.setWrapperTranslate(0);
					swiper.slideTo(1,0,false);
				}
				init = true;
				
			}

			var swiper2;
			function twoTab(){
				$("#hlist1").css("display", "none");
				$("#tab1").removeClass("on");
				
				$("#hlist2").css("display", "");
				$("#tab2").addClass("on");
				
				if(!init2){
					//채용공고 슬라이드
					swiper2 = new Swiper('.swiper_name2', {
					slidesPerView: 3,
					spaceBetween: 55,
					breakpoints: {
						736: {
						  slidesPerView: 1,
						  spaceBetween: 30,
						}
					},
					initialSlide: 0,
					centeredSlides: true,
					loop: true,
					navigation: {
						nextEl: '.swiper-button-next2',
						prevEl: '.swiper-button-prev2',
					},
					});
				}else{
					swiper2.destroy();
					//채용공고 슬라이드
					swiper2 = new Swiper('.swiper_name2', {
					slidesPerView: 3,
					spaceBetween: 55,
					breakpoints: {
						736: {
						  slidesPerView: 1,
						  spaceBetween: 30,
						}
					},
					initialSlide : 0,
					centeredSlides: true,
					loop: true,
					navigation: {
						nextEl: '.swiper-button-next2',
						prevEl: '.swiper-button-prev2',
					},
					});
					swiper2.setWrapperTranslate(0);
					swiper2.slideTo(1,0,false);
				}
				init2 = true;
			}
			//처음초기화
			firTab();
			$(document).ready(function($) {
				$(window).on("resize", function(event){
					if($("#hlist1").css("display") != "none"){
						firTab();
					}else{
						twoTab();
					}
				});
			});
			</script>


		</div>
	</div>
	<!--//게시판-->

	<!--배너모음-->
	<div id="banner">
		<div id="RollingBanner">
			<c:set var="bannerCnt" value="${fn:length(bannerList)}" />
			<c:forEach items="${bannerList}" var="banner" varStatus="no">
			<c:if test="${(no.count%5)==1}"><ul  class="rolling"></c:if>
			<li>
			<c:if test="${banner.b_l_url eq '' or banner.b_l_url eq null}">
				<a href="javascript:;">
			</c:if>
			<c:if test="${banner.b_l_url ne ''}">
				<a href="${banner.b_l_url == '' or banner.b_l_url == null ? 'javascript:;' : banner.b_l_url}" target="_blank">
			</c:if>
			<img src="/data/banner/${banner.b_l_img}" style="width:200px;heigth:60px;" alt="${banner.b_l_subject}" /></a></li>
			<c:if test="${((no.count%5)==0) or (no.count==bannerCnt)}"></ul></c:if>
			</c:forEach>
		</div>
		<div class="control">
			<p class="up"><a href="#bprev" onclick="RollingBanner.prev(); return false;"><img src="/nanum/site/img/main/up_btn.png" alt="이전" /></a></p>
			<p class="down"><a href="#bnext" onclick="RollingBanner.next(); return false;"><img src="/nanum/site/img/main/down_btn.png" alt="다음" /></a></p>
		</div>
		
		<script type="text/javascript" src="/nanum/site/common/js/bannerzone.js"></script>
		<script type="text/javascript">
			var RollingBanner=new rotatecontents('RollingBanner');
			RollingBanner.rotatemethod='vertical';
			RollingBanner.initialize();
			function bannerStop(){
				RollingBanner.stop();
				document.getElementById("a_bn_stop").style.display = "none";
				document.getElementById("a_bn_plsy").style.display = "block";
			}
			function bannerStart(){
				RollingBanner.play();
				document.getElementById("a_bn_stop").style.display = "block";
				document.getElementById("a_bn_plsy").style.display = "none";
			}
		</script>
	</div>
	<!--//배너모음-->

	<!--하단 사이트맵-->
	<div class="foot_sitemap">
		<div class="area">
			<div class="fl">
				<div class="title">
					<p class="stxt">한국수목원관리원</p>
					<p class="btxt">서비스 바로가기</p>
				</div>
				<div class="select">
					<div class="box"><a href="#empt" onclick="site_w();" onfocus="if(event .keycode==13) { site_w(); }">관련사이트</a></div>
					<div id="site_all" class="selct_cnt" style="display:none;">
						<ul>
							<li><a href="http://www.forest.go.kr/" target="_blank">산림청</a></li>
							<li><a href="https://www.kofpi.or.kr/" target="_blank">한국임업진흥원</a></li>
							<li><a href="http://www.fowi.or.kr/" target="_blank">한국산림복지진흥원</a></li>
							<li><a href="http://www.forest.go.kr/" target="_blank">국립수목원</a></li>
							<li><a href="http://daslim.fowi.or.kr/" target="_blank">국립산림치유원</a></li>
							<li><a href="http://www.ipet.re.kr/" target="_blank">농림식품기술기획평가원</a></li>
							<li><a href="http://www.rda.go.kr/" target="_blank">농촌진흥청</a></li>
							<li><a href="http://www.nature.go.kr/" target="_blank">국가생물종지식정보서비스</a></li>
							<li><a href="http://www.nature.go.kr/kpni/index.do" target="_blank">국가표준식물목록</a></li>
							<li><a href="http://gis.kofpi.or.kr/gis/main.do" target="_blank">산림정보 다드림</a></li>
							<li><a href="http://www.forest.go.kr/newkfsweb/kfs/idx/SubIndex.do?orgId=fgis&mn=KFS_02_04#link" target="_blank">산림공간정보서비스</a></li>
							<li><a href="https://ftis.forest.go.kr/ftisWebApp/main/main.do" target="_blank">산림과학기술서비스</a></li>
						</ul>
					</div>
					<script type="text/javascript"> 
						function site_w(){		 
							var objImage;
							var objLayer;
					 
							try{objImage = document.getElementById("site_w");}catch(err){objImage=null;}
							try{objLayer = document.getElementById("site_all");}catch(err){objLayer=null;}
					 
							if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
								$(objLayer).slideDown();
							}else{
								$(objLayer).slideUp();
							}		
						}
					</script>
				</div>
			</div>
			<div class="sitemap">
				<c:forEach items="${topMenuList}" var="topMenu" varStatus="no">
				<dl>
					<c:if test="${topMenu.ct_idx eq '14'}">
						<dt>한국수목원관리원</dt>
					</c:if>
					<c:if test="${topMenu.ct_idx ne '14'}">
						<dt>${topMenu.ct_name}</dt>
					</c:if>
					<c:forEach items="${topMenu.menuList}" var="subMenu" varStatus="no2">
					<c:set var="submenu_url" value="${subMenu.ct_menu_url}" />
						<dd><a href="${submenu_url}" ${subMenu.ct_url_target eq 'Y' ? 'target="_blank"' : '' }>${subMenu.ct_name}
						<c:if test="${subMenu.ct_url_target eq 'Y'}"><img src="/nanum/site/img/common/new_window2.png" alt="새창" /></c:if>
						</a></dd>
					</c:forEach>
				</dl>
				</c:forEach>
			</div>
		</div>
	</div>
	<!--//하단 사이트맵-->

</div>
<!--// 중간영역 -->
		

<jsp:include page="./common/file/foot.jsp" />
	
<jsp:include page="./common/file/popup.jsp" />