<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="/nanum/site/common/js/jquery-bxslider.min.js"></script>
<link rel="stylesheet" type="text/css" href="/nanum/site/common/js/jquery-bxslider.css" />
<!-- bxSlider Javascript file -->
<script type="text/javascript">

	$(document).ready(function(){
		
	 var slider = $('.popz').bxSlider({
		auto: false,
		pager:true,
		moveSlides: 1,
		slideWidth: 360,
		nextSelector: '#slider-next',
		prevSelector: '#slider-prev',
		onSliderLoad: function (currentIndex){
			$('#slide-counter .current-index').text(currentIndex + 1);
		},
		onSlideBefore: function ($slideElement, oldIndex, newIndex){
		    $('#slide-counter .current-index').text(newIndex + 1);
		}
	 });
	 
	 $('#slide-counter').append(slider.getSlideCount());

	 $('#slider-next').click(function(){
		slider.goToNextSlide();
		return false;
	});

	$('#slider-prev').click(function(){
		slider.goToPrevSlide();
		return false;
	});

	$('#slider-stop').click(function(){
		slider.stopAuto();
		$(this).css("display", "none");
		$("#slider-start").css("display", "block");
		return false;
	});

	$('#slider-start').click(function(){
		slider.startAuto();
		$(this).css("display", "none");
		$("#slider-stop").css("display", "block");
		return false;
	});

	});
</script>
<p class="controller">
	<span class="page" id="slide-counter"><strong class="current-index">1</strong>/</span>
	<span class="prev"><a href="javascript:;"><img src="../../nanum/site/img/main/popprev_btn.gif" onmouseover="this.src='../../nanum/site/img/main/popprev_btn_on.gif'" onmouseout="this.src='../../nanum/site/img/main/popprev_btn.gif'" alt="이전 팝업보기"  id="slider-prev"/></a></span>
	<span class="next"><a href="javascript:;"><img src="../../nanum/site/img/main/popnext_btn.gif" onmouseover="this.src='../../nanum/site/img/main/popnext_btn_on.gif'" onmouseout="this.src='../../nanum/site/img/main/popnext_btn.gif'" alt="다음 팝업보기"   id="slider-next"/></a></span>
</p>
<br/>
<ul class="popz">
	<c:forEach items="${banner2List}" var="banner2" varStatus="no">
	<li>
		<a href="javascript:page_go2('${banner2.b_l_url}');"><img src = "/data/banner2/${banner2.b_main_img}" alt="${banner2.b_l_subject}" style="width:360px;height:230px;"/></a>
	</li>
	</c:forEach>
</ul>


	<!-- li>
		<p>
			<a href="/content/03culture/04_01.php">
				<img src="../../nanum/site/img/main/popzone_off.gif" alt="팝업존2" />
			</a>
		</p>
		<a href="#" class="popup_img"><img src="../../nanum/site/img/main/popzone_img1.gif" alt="온마을아이맘센터 오픈되었습니다." /></a>
	</li>
	<li>
		<p>
			<a href="/content/03culture/04_01.php">
				<img src="../../nanum/site/img/main/popzone_off.gif" alt="팝업존2" />
			</a>
		</p>
		<a href="#" class="popup_img"><img src="../../nanum/site/img/main/popzone_img1.gif" alt="온마을아이맘센터 오픈되었습니다." /></a>
	</li -->
	