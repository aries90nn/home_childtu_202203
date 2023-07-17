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
	<div class="mscroll_guide"><span>모바일로 확인하실 경우</span> 표를 좌우로 움직여 내용을 확인 하실 수 있습니다.</div>
	<div class="mscroll">
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
					<th id="date${cday}" class="day ${tcolor}">
					<c:if test="${!empty cday}">
						<fmt:formatNumber var="day_str" minIntegerDigits="2" value="${cday}" type="number"/>
						<fmt:formatNumber var="month_str" minIntegerDigits="2" value="${intThisMonth}" type="number"/>
						<c:set var="nowdate" value="${intThisYear}-${month_str}-${day_str}" />
						<c:choose>
							<c:when test="${is_ad_cms eq 'Y'}">
								<c:set var="tcolor" value="${i==0 ? 'sun':''}" />
								<c:url var="dateconf_url" value="/board/${config.a_level }/dateConf.do">
									<c:param name="a_num" value="${config.a_num}" />
									<c:param name="be_date" value="${nowdate}" />
									<c:param name="be_rtype" value="${brTypeMap[nowdate]}" />
								</c:url>
								<a href="${dateconf_url }" onclick="window.open(this.href,'date_conf','width=500,height=600');return false;" style='text-decoration:underline;'>
								${cday}
								</a>
							</c:when>
							<c:otherwise>
								${cday}
							</c:otherwise>
						</c:choose>
					
					
					</c:if>
					</th>
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
						<%//강좌신청가능버튼 %>
						<div class='req_type'>
							<c:if test="${brTypeMap[thisDate] eq '0'}">
								<a href="javascript:;" class="cal_smbtn red">신청불가</a>
							</c:if>
							<c:if test="${brTypeMap[thisDate] eq '1'}">
								<c:url var="write_url" value="">
									<c:param name="proc_type" value="write" />
									<c:param name="a_num" value="${config.a_num}" />
									<c:param name="b_sdate" value="${thisDate }" />
									<c:param name="prepage" value="${nowPage }" />
								</c:url>
								<a href='${write_url }' class="cal_smbtn blue">신청</a>
							</c:if>
							<c:if test="${brTypeMap[thisDate] eq '2'}">
								<a href="javascript:;" class="cal_smbtn gray">마감</a>
							</c:if>
						</div>
					
						<c:forEach items="${calendarMap[thisDate]}" var="board" varStatus="no">
							<c:choose>
								<c:when test="${board.b_temp8 eq '승인'}"><c:set var="b_temp8_str" value="<span style='color:#0000FF'>[신청완료]</span>" /></c:when>
								<c:when test="${board.b_temp8 eq '예약취소'}"><c:set var="b_temp8_str" value="<span style='color:#FF0000'>[예약취소]</span>" /></c:when>
								<c:otherwise>
									<c:set var="b_temp8_str" value="<span>${board.b_temp8}</span>" />
								</c:otherwise>
							</c:choose>
							<c:set var="b_name" value="${board.b_name}" />
							<c:if test="${is_ad_cms ne 'Y'}">
								<c:set var="b_name" value="${fn:substring(b_name,0,1)}*${fn:substring(b_name,2, fn:length(b_name))}" />
							</c:if>
							
							<c:if test="${board.b_temp8 eq '대기'}">
								<c:set var="b_temp8_str" value="<span style='color:#1C1C1C'>대기</span>" />
							</c:if>
							<c:if test="${board.b_temp8 eq '승인'}">
								<c:set var="b_temp8_str" value="<span style='color:#0000FF'>승인</span>" />
							</c:if>
							<c:if test="${board.b_temp8 eq '보류'}">
								<c:set var="b_temp8_str" value="<span style='color:#DF0101'>보류</span>" />
							</c:if>
							<c:if test="${board.b_temp8 eq '예약취소'}">
								<c:set var="b_temp8_str" value="<span style='color:#FF0000'>취소</span>" />
							</c:if>
							
							
							<p><a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}" style="${board.b_temp8 eq '예약취소' ? 'text-decoration:line-through;' : ''}color:#6B6B6B;">${board.txt_show}
							${b_cate_str[board.b_cate]}${b_name}[${b_temp8_str }]<!-- <br />${board.b_temp2 } --></a>
							<c:if test="${(config.a_type eq 'Y' and board.b_open eq 'N') or config.a_blind eq 'Y'}">
								<img src='/nanum/site/board/nninc_simple/img/icon_key.gif' alt='비밀글' />
							</c:if></p>
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
		<div class="fl" style="text-align:left;">
		<p class="bot_info_red">
		※ 신청자를 클릭해서 신청상태를 확인할 수 있습니다.
		</p>
		<c:if test="${is_ad_cms eq 'Y'}">
		<p class="bot_info_red">
		※ 일반 사용자는 ${req_date}월(현재월+2) 이후부터는 신청불가로 보여집니다.
		</p>
		</c:if>
		</div>
		<div class="fr">
		<c:if test="${is_ad_cms eq 'Y'}">
			<p class="bot_info_green">
			<a href="/board/${config.a_level }/excel.do?a_num=${config.a_num}&sh_b_sdate_y=${thisYear[0]}&sh_b_sdate_m=${thisMonth[0]}" style="color:#006600;">엑셀자료받기</a>
			</p>
		</c:if>
		</div>

	</div>
	<!-- //버튼 -->

	<c:if test="${is_ad_cms eq 'Y'}">
	</form>
	</c:if>
	</div>

</div>
<!-- //리스트 -->
