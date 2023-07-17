<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<!-- 리스트 -->
<div id="board" style="width:${config.a_width};">

	<div class="top">
		<div class="cal_top">
			<a class="t_arrow y_pre" href='${PATH_INFO}?a_num=${config.a_num}&year=${prevYear-1}&month=${intThisMonth}'><span class="blind">이전 해</span></a>
			<a class="t_arrow m_pre" href='${PATH_INFO}?a_num=${config.a_num}&year=${prevYear}&month=${prevMonth}'><span class="blind">이전 달</span></a> 

			<c:set var="thisYear" value="${fn:split(intThisYear,'-')}" />
			<c:set var="thisMonth" value="${fn:split(intThisMonth2,'-')}" />
			<span class="t_date">${thisYear[0]}.&nbsp;${thisMonth[0]}</span>

			<a class="t_arrow m_next" href='${PATH_INFO}?a_num=${config.a_num}&year=${nextYear}&month=${nextMonth}'><span class="blind">다음 달</span></a>
			<a class="t_arrow y_next" href='${PATH_INFO}?a_num=${config.a_num}&year=${nextYear+1}&month=${intThisMonth}'><span class="blind">다음 해</span></a>

			<a class="t_today" href='${PATH_INFO}?a_num=${config.a_num}&year=${year}&month=${month}'>오늘</a>

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
			<!-- 주간 7개 -->
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
			<!--// 주간 7개 -->

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
					<c:set var="chk_edate_data" value="" />
					<!-- 게시글 -->
					<c:forEach items="${calendarMap[thisDate]}" var="board" varStatus="no">
						<fmt:parseDate value='${thisDate}' var="thisDate2" pattern="yyyy-MM-dd" />
						<fmt:parseDate value='${board.b_sdate}' var="b_sdate" pattern="yyyy-MM-dd" />
						<fmt:parseDate value='${board.b_edate}' var="b_edate" pattern="yyyy-MM-dd" />
						<c:choose>
							<c:when test="${thisDate2 >= b_sdate and thisDate2 <= b_edate}">
								<c:set var="chk_edate_arr" value="${fn:split(chk_edate_data,',') }" />
								<c:forEach items="${chk_edate_arr }" var="chk_edate">
									<c:if test="${!empty chk_edate }">
										<fmt:parseDate value='${chk_edate}' var="chk_edate" pattern="yyyy-MM-dd" />
										<c:if test="${chk_edate >= b_sdate }">
											<a href='#none' class='none_day' style="margin-bottom:1px;">&nbsp;</a>
										</c:if>
									</c:if>
								</c:forEach>
								
								<c:if test="${board.b_sdate eq  board.b_edate}">
								<a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}" class='${board.b_sbjclr}' style="margin-bottom:1px;height:40px;overflow:hidden;" title='${b_cate_str[board.b_cate]}${board.b_subject}'>
									<c:if test="${thisDate2 eq b_sdate or week eq '0' or day_str eq '01'}">
										<span style='display:inline-block;height:100%;word-break: break-all;${board.txt_title_style}' class='${empty board.b_sbjclr ? "red_day" : board.b_sbjclr}'>${board.txt_show}
										${b_cate_str[board.b_cate]}${board.b_subject}</span>
									</c:if>
								</a>
								</c:if>
								<c:if test="${board.b_sdate ne  board.b_edate}">
									<c:choose>
										<c:when test="${c eq  '6' and (thisDate2 eq b_sdate or week eq '0' or day_str eq '01')}">
											<a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}" class='${board.b_sbjclr}' style="margin-bottom:1px;height:40px;overflow:hidden;" title='${b_cate_str[board.b_cate]}${board.b_subject}'>
												<span style='display:inline-block;height:100%;word-break: break-all;${board.txt_title_style}' class='${empty board.b_sbjclr ? "red_day" : board.b_sbjclr}'>${board.txt_show}
										</c:when>
										<c:otherwise>
											<a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}" class='${board.b_sbjclr}' style="margin-bottom:1px;" title='${b_cate_str[board.b_cate]}${board.b_subject}'>
												<span style='display:inline-block;position:absolute;height:19px;${board.txt_title_style}' class='${empty board.b_sbjclr ? "red_day" : board.b_sbjclr}'>${board.txt_show}
										</c:otherwise>
									</c:choose>
									<c:if test="${thisDate2 eq b_sdate or week eq '0' or day_str eq '01'}">
										${b_cate_str[board.b_cate]}${board.b_subject}</span>
									</c:if>
								</a>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:set var="chk_edate_data" value="${chk_edate_data },${board.b_edate }" />
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
					<!-- //게시글 -->
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
			<span class="bt"><a href="?proc_type=write&a_num=${config.a_num}&prepage=${nowPageEncode}" class="con_btn orange">글쓰기</a></span>
			</c:if>
		</div>

	</div>
	<!-- //버튼 -->

	<c:if test="${is_ad_cms eq 'Y'}">
	</form>
	</c:if>

</div>
<!-- //리스트 -->
