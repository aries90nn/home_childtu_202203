<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@
taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today_ymd" />

<!-- 일정 달력 -->
	<div class="mevent_w" id="div_calendar">
		<div class="event_bg">
			<div class="topw">
				<c:set var="thisYear" value="${fn:split(intThisYear,'-')}" />
				<c:set var="thisMonth" value="${fn:split(intThisMonth2,'-')}" />
				<p>${thisYear[0]}.${thisMonth[0]}</p>
				<div class="btn_w mon">
					<a href="/${BUILDER_DIR }/main/ajaxCalendar.do?a_num=${param.a_num }&year=${prevYear}&month=${prevMonth}" class="prev">이전월 일정 보기</a>
					<a href="/${BUILDER_DIR }/main/ajaxCalendar.do?a_num=${param.a_num }&year=${nextYear}&month=${nextMonth}" class="next">다음월 일정 보기</a>
				</div>
				<ul>
					<li class="blue"><span></span>오늘</li>
					<li class="green"><span></span>일정</li>
					<%--<li class="red"><span></span>휴관일</li> --%>
				</ul>
			</div>
			<ul class="day_w">
				<li>일</li>
				<li>월</li>
				<li>화</li>
				<li>수</li>
				<li>목</li>
				<li>금</li>
				<li>토</li>
			</ul>
		</div>
		<div class="cal_eventw">
			<ul>
				<c:set var="day" value="${-( datFirstDay ) + 1}" />
				<c:set var="day2" value="${-( datFirstDay ) + 1}" />
				<c:forEach var="ju" begin="0" end="${jucnt-1}" step="1">
					<c:forEach var="i" begin="0" end="6" step="1">
						<c:set var="tcolor" value="${i==0 ? 'sun':''}" />
						<c:set var="tcolor" value="${i==6 ? 'sat':tcolor}" />
						<c:set var="cday" value="${day > 0 and day <= intLastDay ? day:''}" />
							
						<c:set var="nowday" value="${day}"></c:set>
						<c:if test="${day lt 10}">
							<c:set var="nowday" value="${'0'}${day }"></c:set>
						</c:if>
						<c:set var="nowmonth" value="${intThisMonth}"></c:set>
						<c:if test="${nowmonth lt 10}">
							<c:set var="nowmonth" value="${'0'}${nowmonth }"></c:set>
						</c:if>
						<c:set var="nowdate" value="${intThisYear}-${nowmonth}-${nowday}"/>
							
						<c:choose>
							<c:when test="${!empty cday }">
								<fmt:parseDate var="nowdate_fmt" value="${nowdate }" pattern="yyyy-MM-dd" />
								<fmt:formatDate value="${nowdate_fmt}" pattern="E" var="nowweek" />
								<c:choose>
									<c:when test="${nowweek eq '일' }"><c:set var="nowweek" value="sun" /></c:when>
									<c:when test="${nowweek eq '월' }"><c:set var="nowweek" value="mon" /></c:when>
									<c:when test="${nowweek eq '화' }"><c:set var="nowweek" value="tue" /></c:when>
									<c:when test="${nowweek eq '수' }"><c:set var="nowweek" value="wed" /></c:when>
									<c:when test="${nowweek eq '목' }"><c:set var="nowweek" value="thu" /></c:when>
									<c:when test="${nowweek eq '금' }"><c:set var="nowweek" value="fri" /></c:when>
									<c:when test="${nowweek eq '토' }"><c:set var="nowweek" value="sat" /></c:when>
								</c:choose>
								<fmt:formatDate value="${nowdate_fmt}" pattern="W" var="nowrow" />
											
								<%--오늘 체크 --%>
								<c:set var="today_yn" value="N" />
								<c:if test="${today_ymd eq nowdate }"><c:set var="today_yn" value="Y" /></c:if>
								<%--일자별 레이어 내용 문자열 생성 --%>
								<c:set var="layer_content_txt" value="" />
								<c:set var="li_class" value="" />
								<c:choose>
									<%--강좌, 행사체크 --%>
									<c:when test="${!empty eventBoardMap[nowdate] }">
										<%--일자 표시 
										<c:set var="span_class" value="event" />
										<c:if test="${today_yn eq 'Y' }">
											<span class="today front">${cday}</span>
											<span class="${span_class } back"></span>
										</c:if>
										<c:if test="${today_yn ne 'Y' }">
											<span class="${span_class }">${cday}</span>
										</c:if>
										--%>			
										<%--레이어 내용 추가 --%>
										<c:forEach items="${eventBoardMap[nowdate] }" var="board">
											<c:set var="b_sdate" value="${fn:replace(fn:substring(board.b_sdate,5,10),'-', '.') }" />
											<c:set var="b_edate" value="${fn:replace(fn:substring(board.b_edate,5,10),'-', '.') }" />
											<c:url var="view_url" value="/${BUILDER_DIR }/contents.do">
												<c:param name="proc_type" value="view" />
												<c:param name="a_num" value="${param.a_num }" />
												<c:param name="b_num" value="${board.b_num }" />
												<c:param name="prepage" value="?proc_type=list&a_num=${param.a_num }&year=${thisYear[0] }&month=${thisMonth[0] }" />
											</c:url>
											<c:set var="layer_content_txt">${layer_content_txt }<li style="width:100%"><a style="display:inline" href="${view_url }">${board.b_subject }</a><span class="date">${b_sdate } - ${b_edate }</span></li></c:set>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:if test="${today_yn eq 'Y' }">
											<span class="today">${cday}</span>
										</c:if>
										<c:if test="${today_yn ne 'Y' }">
											${cday}
										</c:if>
									</c:otherwise>
								</c:choose>
								<c:if test="${!empty layer_content_txt }"><c:set var="li_class" value="green" /></c:if>
								<c:if test="${today_yn eq 'Y' }"><c:set var="li_class" value="blue" /></c:if>
								<li class="${li_class }"><a href="#calendar" onclick="showLayerText('div_layer_${cday }');">${cday }</a></li>
								<%--일자별 레이어 문자열 마무리 --%>
								<c:if test="${!empty layer_content_txt }">
									<c:set var="layer_txt">${layer_txt }<div id="div_layer_${cday }" class="bubble_box ${nowweek } row0${nowrow }" style="display:none"><div class="scroll_wrap"><ul>${layer_content_txt }</ul></div></div></c:set>
								</c:if>
										
							</c:when>
							<c:otherwise>
								<li>&nbsp;</li>
							</c:otherwise>
						</c:choose>
						<c:set var="day" value="${day + 1}" />
					</c:forEach>
				</c:forEach>
			</ul>
			${layer_txt }
		</div>
		<script type="text/javascript">
			//<![CDATA[
			$(function(){
				$("#div_calendar .mon a").click(function(){
					var gopage = $(this).attr("href");
					$("#div_calendar").load(gopage);
					return false;
				});
			});
			//]]>
		</script>
	</div>
<!-- //일정 달력 -->
			
<script type="text/javascript">
//<![CDATA[
	
	$(function(){
		$("#ul_year a").click(function(){
			var gopage = $(this).attr("href");
			$("#div_calender").load(gopage);
			return false;
		});
	});
	
	function showLayerText(id){
		if($("#"+id).css("display") == "none"){
			$("div.bubble_box").css("display","none");
			$("#"+id).css("display", "");
		}else{
			$("div.bubble_box").css("display","none");
			$("#"+id).css("display", "none");
		}
	}
	
//]]>
</script>