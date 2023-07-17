<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
	#layer_pop_wrap {position:fixed; z-index:999; top: 50%;  left: 50%; }
	#layer_pop {position: absolute; width:550px; height:580px; background:#fff; z-index:2; border-radius:14px; overflow:hidden;top: 50%;  left: 50%; transform: translate(-50%,-50%);}
	.layer_pop_con {width:550px; height:500px;  overflow:hidden;}
	.layer_pop_con .pop_Swiper {width:100%; height:100%;}
	.layer_pop_con .pop_Swiper .swiper-slide {width:100%;}
	.layer_pop_con .pop_Swiper .swiper-slide img {width:100%; height:100%;}
	.dim {position: fixed; top: 0;  left: 0;  width: 100%;  height: 100%;  background: rgba(0,0,0,0.6); z-index:1;}
	.layer_pop_con .pop_Swiper .swiper-pagination {position:absolute; bottom:55px; left:0;}
	.layer_pop_con .pop_Swiper .swiper-pagination .swiper-pagination-bullet {width:12px; height:12px; opacity:1; cursor:pointer;border: 1px solid #aaa; background: transparent; font-size: 0; box-sizing: border-box;  padding: 0;}
	.layer_pop_con .pop_Swiper .swiper-pagination .swiper-pagination-bullet-active {background:#aaa;}
	
	.layer_bottom {position:absolute; bottom:10px; left:0; font-size:14px; letter-spacing:-0.05em; width: 100%;text-align: right; padding-right: 20px;box-sizing: border-box;}
	
	.layer_bottom form {margin-bottom:0;}
	.layer_bottom #check {display:inline-block; line-height:28px;}
	.layer_bottom  .close_pop {display:inline-block; border:0; background:#222; padding:6px 10px; border-radius:4px; margin-left:10px;} 
	.layer_bottom  .close_pop a {color:#fff;  font-size:12px; text-decoration:none;}


	@media all and (max-width:600px){
		#layer_pop_wrap {top:0;}
		#layer_pop {position: absolute; width:320px; height:430px; background:#fff; z-index:2; border-radius:14px; overflow:hidden;top: 40px; left: 50%; margin-left:-160px; transform: none;}
		.layer_pop_con {width:320px; height:340px;  overflow:hidden;}
		.layer_pop_con .pop_Swiper {width:100%; height:340px;}		
	}
</style>
<c:if test="${fn:length(banner4List) > 0}">
 <div id="layer_pop_wrap" style="visibility: visible;">
	<div id="layer_pop">
		<div class="layer_pop_con">
			<div class="swiper pop_Swiper">
			  <div class="swiper-wrapper">
			  
				<c:forEach items="${banner4List}" var="banner4" varStatus="no">
					<div class="swiper-slide">
						<c:if test="${banner4.b_l_url ne '' }">
						<a href="${banner4.b_l_url }" ${banner4.b_l_win eq '1' ? 'target="_blank"' : '' }>
						</c:if>
						<img src="/data/banner4/${banner4.b_l_img }" title="${banner4.b_l_subject }" alt="${banner4.b_l_subject }" />
						<c:if test="${banner4.b_l_url ne '' }">
						</a>
						</c:if>
					</div> 
				</c:forEach>

			  </div>
			  <div class="swiper-pagination"></div>
			</div>			
		</div>
		<div class="layer_bottom">
			<form name="pop_form">
			<div id="check" ><input type="checkbox" name="chkbox" value="checkbox" id='chkbox' >
			<label for="chkbox">오늘 하루동안 보지 않기</label></div>
			<div id="close" class="close_pop"><a href="javascript:closePop();">닫기</a></div>    
			</form>
		</div>
	</div>
	<div class="dim"></div>
</div>	
</c:if>

<script language="JavaScript">
//head 태그 안에 스크립트 선언
	function setCookie( name, value, expiredays ) {
		var todayDate = new Date();
		todayDate.setDate( todayDate.getDate() + expiredays ); 
		document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
	}
	function closePop() {
		if ( document.pop_form.chkbox.checked ){
			setCookie( "main_popup_layer", "done" , 1 );
		}
		document.all['layer_pop_wrap'].style.visibility = "hidden";
	}

    cookiedata = document.cookie;   
    if ( cookiedata.indexOf("main_popup_layer=done") < 0 ){     
        document.all['layer_pop_wrap'].style.visibility = "visible";

		var pop_Swiper = new Swiper(".pop_Swiper", {
		pagination: {
		  el: ".swiper-pagination",
		  clickable : 'true',
		},
		autoplay: {delay: 6000, disableOnInteraction: false,},
	  });


    }
    else {
        document.all['layer_pop_wrap'].style.visibility = "hidden";
    }

	$("#layer_pop_wrap").on("click",".dim",function(e) {
		closePop();
	});
	
</script>
