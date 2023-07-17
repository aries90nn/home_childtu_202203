<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
				
				
				
				<div class="popupzone_wrap">
					<ul id="ul_popupzone" class="pop_list">
<c:forEach items="${banner2List}" var="banner2" varStatus="no">
	<c:set var="num_img" value="off" />
	<c:set var="display" value="display:none;" />
	<c:if test="${no.index == 0 }">
		<c:set var="num_img" value="on" />
		<c:set var="display" value="display:block;" />
	</c:if>
	
	<c:set var="w_width" value="${banner2.w_width ==0 ? 100 : banner2.w_width }" />
	<c:set var="w_height" value="${banner2.w_height ==0 ? 100 : banner2.w_height }" />
	
	<c:set var="strwin" value="" />
	<c:set var="linkurl" value="href='javascript:;'" />
	<c:if test="${banner2.b_l_win eq '1' }">
		<c:set var="strwin" value=" target='_blank' " />
	</c:if>
	<c:if test="${banner2.b_l_page eq '1' }">
		<c:set var="linkurl" value="href='${banner2.b_l_url}' ${strwin}" />
	</c:if>
	<c:if test="${banner2.b_l_page ne '1' }">
		<c:set var="linkurl">href='/banner2/view.do?b_l_num=${banner2.b_l_num}' onclick="window.open(this.href,'bd_banner${banner2.b_l_num}','scrollbars=${banner2.scrollbars},toolbar=${banner2.toolbar},menubar=${banner2.menubar},location=${banner2.locations},width=${w_width},height=${w_height},left=${banner2.w_left}, top=${banner2.w_top}');return false;"</c:set>
	</c:if>	
	
						<li>
							<p class="pop_list_btn"><a href="#pz_number" id="number_${no.count }"><img src="/nanum/site/builder/main/${BUILDER_MAIN }/img/pop_${num_img }.png" alt="${no.count }번째 팝업존" class="pz_numimg" /></a></p>
							<p class="popup_list" style="${display}"><a ${linkurl} id="div_img_${no.count }" class="popup_img"><img src="/data/banner2/${banner2.b_main_img}" style="width:330px;height:400px;" alt="${banner2.b_l_subject}" class="pz_image" /></a></p>
						</li>
</c:forEach>
					</ul>
					<div class="pop_play">
						<p class="pz_stop"><a id="a_popupzone_stop" href="#stop" onclick="pz.stop();">팝업존 슬라이드 멈춤</a></p>
						<p class="pz_play"><a id="a_popupzone_play" href="#play" onclick="pz.play();">팝업존 슬라이드 시작</a></p>
					</div>
				</div>
				
				<script type="text/javascript">
				var pz = new Popupzone("ul_popupzone");
				pz.start();
				</script>
				