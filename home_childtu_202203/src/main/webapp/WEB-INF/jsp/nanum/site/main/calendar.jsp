<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


		
<script>
function calendar(a_num, year, month){
	$("#div_main_calendar").load("/main_calendar.do?a_num="+a_num+"&year="+year+"&month="+month);
}
</script>

		
<fmt:formatNumber var="month_str" minIntegerDigits="2" value="${intThisMonth}" type="number"/>
<c:set var="now" value="<%=new java.util.Date()%>" />
<fmt:formatDate var="nowdate" value="${now}" pattern="yyyy-MM-dd" />

<div class="tab_list_box">
	<div class="month">
		<p class="prev"><a href="javascript:calendar('${a_num}', '${prevYear}', '${prevMonth}');"><img src="../../nanum/site/img/main/calprev_btn.gif" alt="" /></a></p>
		<p class="num">${month_str}</p>
		<p class="next"><a href="javascript:calendar('${a_num}', '${nextYear}', '${nextMonth}');"><img src="../../nanum/site/img/main/calnext_btn.gif" alt="" /></a></p>
	</div>
	<div class="lib_tab">
		<ul>
			<li><a href="javascript:calendar('38572191', '${intThisYear}', '${intThisMonth}');" <c:if test="${a_num eq '38572191'}">class="on"</c:if>>문화</a></li>
			<li><a href="javascript:calendar('93878559', '${intThisYear}', '${intThisMonth}');" <c:if test="${a_num eq '93878559'}">class="on"</c:if>>아이</a></li>
			<li><a href="javascript:calendar('88224797', '${intThisYear}', '${intThisMonth}');" <c:if test="${a_num eq '88224797'}">class="on"</c:if>>학마을</a></li>
			<li><a href="javascript:calendar('18157392', '${intThisYear}', '${intThisMonth}');" <c:if test="${a_num eq '18157392'}">class="on"</c:if>>기적</a></li>
			<li><a href="javascript:calendar('34715861', '${intThisYear}', '${intThisMonth}');" <c:if test="${a_num eq '34715861'}">class="on"</c:if>>무수골</a></li>
			<li><a href="javascript:calendar('77375767', '${intThisYear}', '${intThisMonth}');" <c:if test="${a_num eq '77375767'}">class="on"</c:if>>둘리</a></li>
			<li><a href="javascript:calendar('10359238', '${intThisYear}', '${intThisMonth}');" <c:if test="${a_num eq '10359238'}">class="on"</c:if>>작은</a></li>
		</ul>
	</div>
</div>
<div class="calander_cont">
	<table summary="도봉문화정보도서관의 이달의 행사를 월요일부터 일요일까지 달력으로 나타내어 빨강색은 휴관일 파랑색은 행사의 날로 표현하고 있다">
		<caption>도봉문화정보도서관 이달의 행사</caption>
		<colgroup>
			<col width="14.28%"/>
			<col width="14.28%"/>
			<col width="14.28%"/>
			<col width="14.28%"/>
			<col width="14.28%"/>
			<col width="14.28%"/>
			<col width="14.28%"/>
		</colgroup>
		<thead>
			<tr>
				<th>일</th>
				<th>월</th>
				<th>화</th>
				<th>수</th>
				<th>목</th>
				<th>금</th>
				<th>토</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="day" value="${-( datFirstDay ) + 1}" />
			<c:set var="day2" value="${-( datFirstDay ) + 1}" />

			<c:forEach var="ju" begin="0" end="${jucnt-1}" step="1">
			<tr>
				<c:forEach var="c" begin="0" end="6" step="1">
					<c:set var="cday2" value="${day2 > 0 and day2 <= intLastDay ? day2:''}" />
					<c:set var="cday2" value="${cday2 < 10 ? '0'+cday2:cday2}" />
					<c:set var="thisDate" value="" />
					<c:if test="${cday2 ne '00'}">
						<fmt:formatNumber var="day_str" minIntegerDigits="2" value="${cday2}" type="number"/>
						<c:set var="thisDate" value="${intThisYear}-${month_str}-${day_str}" />
					</c:if>
					<td>
						<c:if test="${thisDate ne ''}">
							
							<c:choose>
								<c:when test="${holidayMap[thisDate] != null and holidayMap[thisDate] ne '' }">
									<%//휴관일 %>
									<a href="javascript:;" class="clr1 layover" laydate="${thisDate}_1">${cday2}</a>
									<div class="lay" id="lay_${thisDate}_1" style="top:0;left:20px;border:2px solid #ff4e00;color:#666;width:40px;text-align:left;display:none;">휴관일</div>
								</c:when>
								<c:when test="${edusatMap[thisDate] != null and fn:length(edusatMap[thisDate]) > 0}">
									<%//문화행사 %>
									<c:set var="edu_idx" value="${edusatMap[thisDate][0].edu_idx}" />
									<!-- a href="/edusat/view.do?edu_idx=${edu_idx}" class="clr2 layover" laydate="${thisDate}_2" -->
									<a href="/board/list.do?a_num=${config.a_num}" class="clr2 layover" laydate="${thisDate}_2">${cday2}
										<div class="lay" id="lay_${thisDate}_2" style="top:0;left:20px;border:2px solid #37a0e9;width:150px;text-align:left;color:#666;display:none;">${edusatMap[thisDate][0].edu_subject}</div>
									</a>
								</c:when>
								<c:when test="${edusat2Map[thisDate] != null and fn:length(edusat2Map[thisDate]) > 0}">
									<%//정기강좌 %>
									<c:set var="edu_idx" value="${edusat2Map[thisDate][0].edu_idx}" />
									<a href="/board/list.do?a_num=${config.a_num}" class="clr4 layover" laydate="${thisDate}_4">${cday2}
										<div class="lay" id="lay_${thisDate}_4" style="top:0;left:20px;border:2px solid #A901DB;width:150px;text-align:left;color:#666;display:none;">${edusat2Map[thisDate][0].edu_subject}</div>
									</a>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${nowdate eq thisDate}">
											<a href="javascript:;" class="clr3">${cday2}</a>
										</c:when>
										<c:otherwise>
											${cday2}
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:if>
					</td>
					
					
					<c:set var="day2" value="${day2 + 1}" />
				</c:forEach>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>



<script>
$(".layover").mouseover(function() {
	$(".lay").css("display","none");
	
	var laydate = $(this).attr("laydate");
	$("#lay_"+laydate).css("display","inline-block");
});
</script>