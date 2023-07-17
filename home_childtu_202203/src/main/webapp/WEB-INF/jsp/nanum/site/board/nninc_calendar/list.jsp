<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<!-- 리스트 -->
<div id="board" style="width:${config.a_width};">
	<!-- //분류 -->
		<div class="board_total_left">
		<c:if test="${config.a_ad_cms eq 'Y'}">
			<c:choose>
				<c:when test="${ss_m_id == null}">
					<a href="?proc_type=login&a_num=${config.a_num}&prepage=${nowPageEncode}"><img src="/nanum/site/board/nninc_simple/img/key.gif" alt="" /></a> 
				</c:when>
				<c:otherwise>
					<a href="/board/logout.do?a_num=${config.a_num}&prepage=${nowPageEncode}"><img src="/nanum/site/board/nninc_simple/img/unkey.gif" alt="" /></a> 
				</c:otherwise>
			</c:choose>
		</c:if>
		</div>
	<div class="top">
		<div class="cal_top">
			<a class="t_arrow y_pre" href='?proc_type=list&a_num=${config.a_num}&year=${prevYear-1}&month=${intThisMonth}'><span class="blind">이전 해</span></a>
			<a class="t_arrow m_pre" href='?proc_type=list&a_num=${config.a_num}&year=${prevYear}&month=${prevMonth}'><span class="blind">이전 달</span></a> 
			
			<c:set var="thisYear" value="${fn:split(intThisYear,'-')}" />
			<c:set var="thisMonth" value="${fn:split(intThisMonth2,'-')}" />
			<span class="t_date">${thisYear[0]}.&nbsp;${thisMonth[0]}</span>

			<a class="t_arrow m_next" href='?proc_type=list&a_num=${config.a_num}&year=${nextYear}&month=${nextMonth}'><span class="blind">다음 달</span></a>
			<a class="t_arrow y_next" href='?proc_type=list&a_num=${config.a_num}&year=${nextYear+1}&month=${intThisMonth}'><span class="blind">다음 해</span></a>

			<a class="t_today" href='?proc_type=list&a_num=${config.a_num}'>오늘</a>


		</div>
		<div class="cal_right">
			<jsp:include page="../code.jsp" />
		</div>
	</div>

	<c:if test="${is_ad_cms eq 'Y'}">
	<form id= "frm_list" action=""  method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" name="a_num" value="${config.a_num}" />
		<input type="hidden" id="chk_all" name="chk_all" /><!-- 전체체크 -->
		<input type="hidden" name="prepage" value="${nowPage}" />
		<input type="hidden" name="board_token" value="${board_token}" />
	</div>
	</c:if>
	
	
	<!-- 리스트 테이블 -->
	<div class="cal_blist">

		<table cellspacing="0"  summary="${title} 날짜별 정보확인 및 내용을 선택하여 상세내용을 확인">
		<caption>${title}</caption>
			<colgroup>
			<col width="14%" />
			<col width="14%" />
			<col width="14%" />
			<col width="14%" />
			<col width="14%" />
			<col width="14%" />
			<col width="14%" />
			</colgroup>
		<thead>
			<tr>
				<th scope="col" class="sun">일요일</th>
				<th scope="col">월요일</th>
				<th scope="col">화요일</th>
				<th scope="col">수요일</th>
				<th scope="col">목요일</th>
				<th scope="col">금요일</th>
				<th scope="col" class="sat">토요일</th>
			</tr>
		</thead>

		<tbody>
			<c:set var="cal" value="${fn:replace(config.a_width,'%', '') / 7}" />
			<c:set var="day" value="${-( datFirstDay ) + 1}" />
			<c:set var="day2" value="${-( datFirstDay ) + 1}" />

			<c:forEach var="ju" begin="0" end="${jucnt-1}" step="1">
			<tr>
				<c:forEach var="i" begin="0" end="6" step="1">
					<c:set var="tcolor" value="${i==0 ? 'sun':''}" />
					<c:set var="tcolor" value="${i==6 ? 'sat':tcolor}" />
					<c:set var="cday" value="${day > 0 and day <= intLastDay ? day:''}" />
					<th id="date${cday}" class="day ${tcolor}">${cday}</th>
					<c:set var="day" value="${day + 1}" />
				</c:forEach>
			</tr>

			<!-- 내용 -->
			<tr>
				<c:forEach var="c" begin="0" end="6" step="1">
					<c:set var="cday2" value="${day2 > 0 and day2 <= intLastDay ? day2:''}" />
					<c:set var="cday2" value="${cday2 < 10 ? '0'+cday2:cday2}" />
					<c:set var="thisDate" value="" />
					<c:if test="${cday2 ne '00'}">
						<fmt:formatNumber var="month_str" minIntegerDigits="2" value="${intThisMonth}" type="number"/>
						<fmt:formatNumber var="day_str" minIntegerDigits="2" value="${cday2}" type="number"/>
						<c:set var="thisDate" value="${intThisYear}-${month_str}-${day_str}" />
					</c:if>
					
					<td headers="schedule date${cday2}">
						<c:forEach items="${calendarMap[thisDate]}" var="board" varStatus="no">
							<a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}" style="${board.txt_title_style}color:#6B6B6B;">${board.txt_show}
							${b_cate_str[board.b_cate]}${board.b_subject}</a>
							
							<c:if test="${(config.a_type eq 'Y' and board.b_open eq 'N') or config.a_blind eq 'Y'}">
								<img src='/nanum/site/board/nninc_simple/img/icon_key.gif' alt='비밀글' />
							</c:if><br/>
						</c:forEach>
					</td>
					<c:set var="day2" value="${day2 + 1}" />
				</c:forEach>
			</tr>
			</c:forEach>
			
		</tbody>
		</table>

	</div>
	<!-- //리스트 테이블 -->

	<!-- 버튼 -->
	<div class="board_button">
		<div class="fr">
			<c:if test="${is_write eq 'Y'}">
			<span class="bt"><a href="?proc_type=write&a_num=${config.a_num}&prepage=${nowPageEncode}" class="cbtn cbtn_point">글쓰기</a></span>
			</c:if>
		</div>

	</div>
	<!-- //버튼 -->

	<c:if test="${is_ad_cms eq 'Y'}">
	</form>
	</c:if>

</div>
<!-- //리스트 -->
