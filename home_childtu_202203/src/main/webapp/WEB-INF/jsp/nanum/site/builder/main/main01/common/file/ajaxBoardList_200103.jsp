<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach items="${noticeList }" var="notice" varStatus="no">
	<c:choose>
		<c:when test="${notice.a_site_dir eq 'main' }">
			<c:set var="lib_class" value="lib5" />
			<c:set var="lib_name" value="통합" />
		</c:when>
		<c:when test="${notice.a_site_dir eq 'student' }">
			<c:set var="lib_class" value="lib1" />
			<c:set var="lib_name" value="독립" />
		</c:when>
		<c:when test="${notice.a_site_dir eq 'geumho' }">
			<c:set var="lib_class" value="lib2" />
			<c:set var="lib_name" value="금호" />
		</c:when>
		<c:when test="${notice.a_site_dir eq 'gecs' }">
			<c:set var="lib_class" value="lib3" />
			<c:set var="lib_name" value="교육" />
		</c:when>
		<c:when test="${notice.a_site_dir eq 'jungang' }">
			<c:set var="lib_class" value="lib4" />
			<c:set var="lib_name" value="중앙" />
		</c:when>
		<c:when test="${notice.a_site_dir eq 'songjung' }">
			<c:set var="lib_class" value="lib5" />
			<c:set var="lib_name" value="송정" />
		</c:when>
		<c:when test="${notice.a_site_dir eq 'seokbong' }">
			<c:set var="lib_class" value="lib6" />
			<c:set var="lib_name" value="석봉" />
		</c:when>
	</c:choose>

					<li class="box${no.count eq 3 ? ' last' : ''}">
						<div class="${lib_class }">
							<p class="showlib"><span>${lib_name }</span></p>
							<p class="event_noticetit"><a href="/main/contents.do?proc_type=view&a_num=48342159&b_num=${notice.b_num }">${notice.b_subject }</a></p>
							<p class="event_day">${fn:substring(notice.b_regdate,0,10) }</p>
							<div class="event_location noticetxt">
								<p><c:out value='${notice.b_content.replaceAll("\\\<.*?\\\>","")}' escapeXml="false" /></p>
							</div>
						</div>
					</li>


</c:forEach>


<script>
	$(document).ready(function(){
		
		var idx1=$('.main_noticewrap ul li.box > div.lib1').parent().css("border-bottom","solid 3px #55b400");
		var idx2=$('.main_noticewrap ul li.box > div.lib2').parent().css("border-bottom","solid 3px #55b400");
		var idx3=$('.main_noticewrap ul li.box > div.lib3').parent().css("border-bottom","solid 3px #55b400");
		var idx4=$('.main_noticewrap ul li.box > div.lib4').parent().css("border-bottom","solid 3px #55b400");
		var idx5=$('.main_noticewrap ul li.box > div.lib5').parent().css("border-bottom","solid 3px #eb5650");
		var idx6=$('.main_noticewrap ul li.box > div.lib6').parent().css("border-bottom","solid 3px #feb506");
		try{//메인로딩시 자바스크립트오류로 예외처리
			idx1();
			idx2();
			idx3();
			idx4();
			idx5();
			idx6();
		}catch(e){console.log(e);}
	})
</script>