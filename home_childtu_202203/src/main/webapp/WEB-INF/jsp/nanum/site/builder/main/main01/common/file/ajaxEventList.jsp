<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<jsp:useBean id="now" class="java.util.Date" />
<c:set var="nowyear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
<c:set var="nowmonth"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
<c:set var="nowday"><fmt:formatDate value="${now}" pattern="dd" /></c:set>
<c:set var="nowhour"><fmt:formatDate value="${now}" pattern="HH" /></c:set>
<c:set var="nowminute"><fmt:formatDate value="${now}" pattern="mm" /></c:set>
<c:set var="nowsecond"><fmt:formatDate value="${now}" pattern="ss" /></c:set>				

<c:set var="edu_ecnt" value="${fn:length(edusatList) > 3 ? 2 : fn:length(edusatList)-1 }" />
<c:set var="edu_ecnt" value="${edu_ecnt < 0 ? 0 : edu_ecnt}" />
<c:forEach items="${edusatList }" var="edusat" varStatus="no" begin="0" end="${edu_ecnt }">
	<c:choose>
		<c:when test="${edusat.ct_idx eq '4' }">
			<c:set var="lib_class" value="lib1" />
			<c:set var="lib_name" value="독립" />
		</c:when>
		<c:when test="${edusat.ct_idx eq '32' }">
			<c:set var="lib_class" value="lib2" />
			<c:set var="lib_name" value="금호" />
		</c:when>
		<c:when test="${edusat.ct_idx eq '33' }">
			<c:set var="lib_class" value="lib3" />
			<c:set var="lib_name" value="교육" />
		</c:when>
		<c:when test="${edusat.ct_idx eq '34' }">
			<c:set var="lib_class" value="lib4" />
			<c:set var="lib_name" value="중앙" />
		</c:when>
		<c:when test="${edusat.ct_idx eq '35' }">
			<c:set var="lib_class" value="lib5" />
			<c:set var="lib_name" value="송정" />
		</c:when>
		<c:when test="${edusat.ct_idx eq '36' }">
			<c:set var="lib_class" value="lib6" />
			<c:set var="lib_name" value="석봉" />
		</c:when>
		<c:otherwise><c:set var="lib_class" value="" /></c:otherwise>
	</c:choose>

	<%-- 접수상태 생성 --%>
	<c:set var="nowdate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmm" /></c:set>
	<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}${edusat.edu_resdate_h}00' />
	<c:if test="${fn:length(edusat.edu_resdate_h) == 1}">
		<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}0${edusat.edu_resdate_h}00' />
	</c:if>
	<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}${edusat.edu_reedate_h}59' />
	<c:if test="${fn:length(edusat.edu_reedate_h) == 1}">
		<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}0${edusat.edu_reedate_h}59' />
	</c:if>
	
	
	<fmt:parseNumber value="${edusat.req_count }" pattern="0" var="req_count"></fmt:parseNumber>
	<fmt:parseNumber value="${edusat.edu_inwon }" pattern="0" var="edu_inwon"></fmt:parseNumber>
	<fmt:parseNumber value="${edusat.edu_awaiter }" pattern="0" var="edu_awaiter"></fmt:parseNumber>
	<c:set var="requestChk" value="false" />
	<c:choose>
		<c:when test="${reedate < nowdate}">
			<c:set var="str2" value='<span class="chk_ready">접수마감</span>' />
		</c:when>
		<c:when test="${resdate > nowdate}">
			<c:set var="str2" value='<span class="chk_ready">접수대기</span>' />
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${edu_inwon <= req_count}">
					<c:set var="str2" value='<span class="chk_ready">접수마감</span>' />
					<c:if test="${(edu_inwon + edu_awaiter) > req_count }">
						<c:set var="str2" value='<span class="chk_open">접수중</span>' />
						<c:set var="requestChk" value="true" />
					</c:if>
				</c:when>
				<c:otherwise>
					<c:set var="requestChk" value="true"></c:set>
					<c:set var="str2" value='<span class="chk_open">접수중</span>' />
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	
	<c:set var="req_count_str" value="<span style='color:#0000FF;font-weight:bold;'>${edusat.req_count}</span>"></c:set>
	<c:if test="${req_count >= edu_inwon}">
		<c:set var="req_count_str" value="<span style='color:#FF0000;font-weight:bold;'>${edusat.req_count}</span>"></c:set>
	</c:if>
	
	<c:if test="${edusat.end_chk eq 'T'}">
		<c:set var="str2" value='<span class="chk_ready">접수마감</span>' />
		<c:set var="requestChk" value="false"></c:set>
	</c:if>
	
	<%-- 접수상태 생성끝 --%>

					<li class="box${no.count eq 3 ? ' last' : ''}">
						<div class="${lib_class }">
							<div class="moblie_wrap">
								<p class="showlib"><span>${lib_name }</span>${str2 }</p>
							</div>
							<div class="moblie_wrap">
								<p class="event_noticetit"><a href="/${BUILDER_DIR }/edusat/view.do?edu_idx=${edusat.edu_idx }">${edusat.edu_subject}</a></p>
								<p class="event_day"><span class="web">${fn:substring(edusat.edu_resdate,0,5)}</span>${fn:substring(edusat.edu_resdate,5,10)} ~ <span class="web">${fn:substring(edusat.edu_reedate,0,5)}</span>${fn:substring(edusat.edu_reedate,5,10)}</p>
								<div class="event_location">
									<p>장소 : ${edusat.edu_temp2}</p>
									<p>대상 : ${edusat.edu_target}</p>
								</div>
							</div>
						</div>
						<ul class="btn_wrap">
							<li><a href="/${BUILDER_DIR }/edusat/view.do?edu_idx=${edusat.edu_idx }">자세히보기</a></li>
	<c:choose>
		<c:when test="${requestChk eq 'true' }">
							<li><a href="/${BUILDER_DIR }/edusat/regist.do?edu_idx=${edusat.edu_idx }">신청하기</a></li>
		</c:when>
		<c:otherwise>
							<li><a href="#close">신청불가</a></li>
		</c:otherwise>
	</c:choose>		
						</ul>
					</li>

</c:forEach>