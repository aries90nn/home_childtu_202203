<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<jsp:include page="/WEB-INF/jsp/nanum/site/builder/skin/${BUILDER_SKIN }/common/file/header.jsp" />

<!-- 내용영역 -->
<div id="container" style="min-height:800px;">

	<!-- 메인비주얼 -->
	<div id="mvisual">
		<div class="wsize">
			<div class="mtxt">
				<p class="t1">투명한 세상! <span>안전한 교통!</span></p>
				<p class="t2">투명우산을 펼치면 안전한 세상이 열려요</p>
				<p class="t3">여러분의 소중한 사연으로 어린이들에게 교통안전을 선물하세요</p>
			</div>
			<div class="main_img">
				<img src="/nanum/site/builder/main/main01/img/mvisual.png" alt="" />
			</div>
		</div>
	</div>
	<!--//메인비주얼 -->

	<div class="wsize">
		<!--바로가기 -->
		<div class="qbtn">
			<ul>
				<li><a href="/main/contents.do?idx=2588" title="사업소개 바로가기"><i><img src="/nanum/site/builder/main/main01/img/micon01.png" class="rotate" alt=""></i><span>사업소개</span></a></li>
				<li><a href="/main/contents.do?idx=2590" title="사연신청 (개인나눔) 바로가기"><i><img src="/nanum/site/builder/main/main01/img/micon03.png" class="rotate" alt=""></i><span>사연신청 (개인나눔)</span></a></li>
				<li><a href="/main/contents.do?idx=2591" title="교통안전교육 (학교나눔) 바로가기"><i><img src="/nanum/site/builder/main/main01/img/micon06.png" class="rotate" alt=""></i><span>교통안전교육 (학교나눔)</span></a></li>
				<li><a href="/main/edusat/list.do?gubun=47" title="프로그램 신청 바로가기"><i><img src="/nanum/site/builder/main/main01/img/micon02.png" class="rotate" alt=""></i><span>프로그램 신청</span></a></li>
				<li><a href="/main/contents.do?a_num=51268418" title="참여후기 바로가기"><i><img src="/nanum/site/builder/main/main01/img/micon05.png" class="rotate" alt=""></i><span>참여후기</span></a></li>
			</ul>
		</div>
		<!-- //바로가기 -->


		<!-- msection1 -->
		<div class="msection1">
			<!-- 활동갤러리 -->
			<div class="bx">
				<p class="tit">활동갤러리</p>
				
				<div class="swiper gallery">
					<div class="swiper-wrapper">
						<c:forEach var="gal" items="${board2 }">
						<div class="swiper-slide">
							<a href="contents.do?proc_type=view&a_num=${gal.a_num }&b_num=${gal.b_num }&prepage=${NOWPAGE }" title="${gal.b_subject }" style="width:100%;height:100%;">
							<div class="mask"><img src="/nanum/site/builder/main/main01/img/gallery_bg.png" alt="" /></div>
							<img src="/data/board/${gal.a_tablename }/${gal.b_file1}" alt="${gal.b_subject }" />
							<div class="cont">
								<p>${gal.b_subject }</p>
								<span>${fn:substring(gal.b_regdate,0, 10) }</span>
							</div>
							</a>
						</div>
						</c:forEach>
					</div>
					<div class="swiper-button-next"></div>
				   <div class="swiper-button-prev"></div>
				</div>
				<a href="contents.do?a_num=75543687" class="more" title="활동갤러리 더보기">활동갤러리 더보기</a>
			</div>
			<!-- Initialize Swiper -->
			<script>
			  var swiper = new Swiper(".gallery", {
				autoplay : {
					delay : 3000,
					disableOnInteraction : true,
				},
				navigation: {
				  nextEl: ".swiper-button-next",
				  prevEl: ".swiper-button-prev",
				},
			  });
			</script>
			<!-- //활동갤러리 -->

			<!-- 새소식 -->
			<div class="bx">
				<p class="tit">새소식</p>
				<div class="news">
					<ul>
						<c:forEach var="news" items="${board1 }" varStatus="no">
						<li ${no.index % 2 != 0 ? 'class="right"' : ''}>
							<a href="contents.do?proc_type=view&a_num=${news.a_num }&b_num=${news.b_num }&prepage=${NOWPAGE }" title="${news.b_subject }">
							<p>${news.b_subject }</p>
							<span>${fn:substring(news.b_regdate,0, 10) }</span>
							</a>
						</li>
						</c:forEach>
					</ul>
					<a href="contents.do?a_num=69394427" class="more" title="새소식 더보기">새소식 더보기</a>
				</div>
			</div>
			<!-- //새소식 -->
		</div>
		<!-- //msection1 -->

		<!-- msection2 -->
		<div class="msection2">
			<!--홍보영상-->
			<div class="bx">
				<p class="tit">홍보영상</p>
				<div class=" yline">
					<c:forEach var="video" items="${board3 }">
					<a href="contents.do?proc_type=view&a_num=${video.a_num }&b_num=${video.b_num }&prepage=${NOWPAGE}" class="youtube_img" title="${video.b_subject }">
						<img src="${video.b_temp2 }" alt="${video.b_subject }" />
						<div class="btn_mplay"><img src="/nanum/site/builder/main/main01/img/btn_mplay.png" alt="홍보영상 바로보기" /></div>
					</a>
					<div class="ytxt">
						<a href="contents.do?proc_type=view&a_num=${video.a_num }&b_num=${video.b_num }&prepage=${NOWPAGE}" class="cont" title="${video.b_subject }">
							<p>${video.b_subject }</p>
							<span>${fn:substring(video.b_regdate,0, 10) }</span>
						</a>
					</div>
					</c:forEach>
					<a href="contents.do?idx=2616" class="more" title="홍보영상 더보기">홍보영상 더보기</a>
				</div>
			</div>
			<!--//홍보영상-->
			<c:set var="ran"><%= java.lang.Math.round((java.lang.Math.random() * 2)+1) %></c:set>
			<!--설문조사-->
			<div class="bx">
				<p class="tit">설문조사</p>
				<div class=" yline">
					<a href="#" class="youtube_img" title="어린이 교통안전 설문조사">
						<img src="/nanum/site/builder/main/main01/img/sulmun_img${ran }.jpg" alt="어린이 교통안전 설문조사" />
					</a>
					<div class="ytxt">
						<c:forEach items="${poll }" var="poll">
						<a href="poll/poll.do?po_pk=${poll.po_pk }&prepage=${NOWPAGE}"  class="cont" title="${poll.po_subject }">
							<p>${poll.po_subject }</p>
							<span>${poll.po_sdate } ~ ${poll.po_edate }</span>
						</a>
						</c:forEach>
					</div>
					<a href="poll/list.do" class="more" title="설문조사 더보기">설문조사 더보기</a>
				</div>
			</div>
			<!--//설문조사-->
		</div>
		<!-- //msection2 -->
		</div>
		
		<!-- 팝업존 -->
		<div class="popzone">
			<div class="swiper popswiper">
				<div class="swiper-wrapper">
					<c:forEach var="popzone" items="${banner2List }">
					<a href="${popzone.b_l_url }" target="${popzone.b_l_win eq '1' ? '_blank' : ''}">
					<div class="swiper-slide"><img src="/data/banner2/${popzone.b_main_img }" alt="${popzone.b_l_subject }" /></div>
					</a>
					</c:forEach>
				</div>
				<div class="swiper-button-next"></div>
				<div class="swiper-button-prev"></div>
			</div>
		</div>
		<!-- Initialize Swiper -->
	    <script>
	      var swiper = new Swiper(".popswiper", {
    	  autoplay: {
  			delay: 3000,
  			disableOnInteraction: false
  		},
	        navigation: {
	          nextEl: ".swiper-button-next",
	          prevEl: ".swiper-button-prev",
	        },
	      });
	    </script>
		<!-- //팝업존 -->
		
		<!--참여기관-->
		<div class="wsize">
			<div class="banner">
				<ul>
					<li><a href="https://www.mobis.co.kr/kr/index.do" target="_blank" title="현대모비스"><img src="/nanum/site/builder/main/main01/img/banner01.gif" alt="현대모비스" /></a></li>
					<li><a href="https://childsafe.or.kr/" target="_blank" title="한국어린이안전재단"><img src="/nanum/site/builder/main/main01/img/banner02.gif" alt="한국어린이안전재단" /></a></li>
					<li><a href="http://www.koroad.or.kr/" target="_blank" title="도로교통공단"><img src="/nanum/site/builder/main/main01/img/banner03.gif" alt="도로교통공단" /></a></li>
				</ul>
			</div>
		</div>
		<!--//참여기관-->
		
	
</div>

<!-- //내용영역 -->

<jsp:include page="/WEB-INF/jsp/nanum/site/builder/skin/${BUILDER_SKIN }/common/file/foot.jsp" />
<!-- <jsp:include page="/WEB-INF/jsp/nanum/site/common/file/popup.jsp" /> -->
<jsp:include page="/WEB-INF/jsp/nanum/site/common/file/popup2.jsp" />
