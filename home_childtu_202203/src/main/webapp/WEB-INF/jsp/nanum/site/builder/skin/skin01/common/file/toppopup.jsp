<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 상단팝업 -->
<c:if test="${fn:length(banner3List) > 0}">
<div id="thead_pop" style="display:none;">
	<div class="cont">
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<c:forEach items="${banner3List}" var="banner3" varStatus="no">
				<div class="swiper-slide"><a href="${banner3.b_l_url}" ${banner3.b_l_win eq '1' ? 'target="_blank"' : '' }><img src="/data/banner3/${banner3.b_l_img}" alt="${banner3.b_l_subject}" style="width:1200px;" /></a></div>
				</c:forEach>
			</div>
		</div>
		<div class="close">
			<input type="checkbox" id="id_check_popup_close" name="id_check_popup_close" value="Y" class="chk_cls" />
			<label for="id_check_popup_close"><span>오늘하루 열지않기</span></label>
			<a href="javascript:;" onclick="todaycloseWin();"><img src="/nanum/site/builder/skin/skin01/img/common/headtop_cls.png" alt="닫기"></a>
		</div>
		<!-- 페이징 -->
		<!-- <div class="wsize"><div class="swiper-pagination"></div></div> -->
	</div>
</div>
<script type="text/javascript">
$(function(){
	var ck = ReadCookie_toppop( "toppop_medicity" );
	if( ck != "1" ){
		$("#thead_pop").show();
		
		
		$("#thead_pop").slideDown("fast", function(){
			var pop_swiper = new Swiper('#thead_pop .swiper-container', {
				slidesPerView: 1,
				autoplay: { delay: 5500, },
				pagination: {
					el: '#thead_pop .swiper-pagination',
					clickable: true,
				},	
			});
		});
		
	}
});
function todaycloseWin(){
	var objImage;
	var objLayer;
	var id_check_popup_close = document.getElementById("id_check_popup_close");

	try{objLayer = document.getElementById("thead_pop");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		$(objLayer).slideDown();
	}else{
		$(objLayer).slideUp();
	}
	if(id_check_popup_close.checked){
		setCookie_toppop();
	}
}

function setCookie_toppop(){
	var expire = new Date();
	expire.setDate(expire.getDate() + 1 );
	document.cookie = "toppop_medicity=1; expires=" +  expire.toGMTString()+ "; path=/";
}

// 팝업창 띄우지 않게 하기 시작
function ReadCookie_toppop(name){
	var nameOfCookie=name+"=";
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
<!-- //상단팝업 -->
</c:if>