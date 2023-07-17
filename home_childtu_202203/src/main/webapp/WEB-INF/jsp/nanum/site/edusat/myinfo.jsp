<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.net.URLEncoder"%>

<link href="/nanum/ndls/common/css/common.css" rel="stylesheet" type="text/css">
<link href="/nanum/ndls/common/css/content.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/nanum/site/builder/skin/${BUILDER_SKIN }/common/css/ndls.css" />


<script type="text/javascript">
//<![CDATA[

function deleteOk(edu_idx, es_idx){
	if(confirm("신청을 취소하시겠습니까?")){
		location.href="/${BUILDER_DIR}/edusat/cancelOk.do?edu_idx="+edu_idx+"&es_idx="+es_idx+"&prepage=${nowPageEncode}";
	}
}
function modifyOk(a, b) {
	alert('신청 마감 3일전까지 수정이 가능합니다.\n\n이후 수정을 원할경우 투명우산나눔활동 담당자에게 연락해주세요.');
	var reform = document.getElementById('reform');
	reform.a.value = b;
	reform.b.value = a;
	reform.submit();
}
//]]>
</script>

<link rel="stylesheet" type="text/css" href="/nanum/site/board/nninc_photo_mobile/css/lightbox.css">
<link rel="stylesheet" type="text/css" href="/nanum/site/board/nninc_simple/css/common.css?ver=0922">
<script type="text/javascript" src="/nanum/site/board/nninc_simple/js/common.js?ver=0922"></script>
<script type="text/javascript" src="/nanum/site/board/nninc_simple/js/board.js?ver=0922"></script>
<script type="text/javascript" src="/nanum/site/board/nninc_photo_mobile/js/spica.js"></script>
<script type="text/javascript" src="/nanum/site/board/nninc_photo_mobile/js/lightbox.js"></script>

<!-- 리스트 -->
<div id="board">
	
	<div class="board_total">
		<div class="board_total_left">
			전체 <strong>${recordcount}</strong>개 (페이지 <strong class="board_orange">${v_page}</strong>/${totalpage})
		</div>
	</div>

	<p class="mscroll_guide mt20"><span>모바일로 확인하실 경우</span> 좌우로 움직여 내용을 확인 하실 수 있습니다.</p>
	<div class="mscroll">

	<!--  -->
	<div class="table_blist">
		<table cellspacing="0" summary="${title} 의 프로그램명, 운영기간, 신청현황, 신청상태를 확인">
		<caption>프로그램 신청내역</caption>
		<colgroup>
			<col width="50px" />
			<col />
			<%-- <col  /> --%>
			<col width="70px"/>
			<col width="70px"/>
			<col width="70px"/>
			<col width="90px"/>
		</colgroup>
		<thead>
		<tr>
			<th scope="col">번호</th>
			<th scope="col">프로그램정보</th>
			<%--<th scope="col">참가인원</th> --%>
			<th scope="col">진행상태</th>
			<th scope="col" class="th_none">입력정보</th>
			<th scope="col" class="th_none">수정</th>
			<th scope="col" class="th_none">취소</th>
		</tr>
		</thead>
		<tbody>
		<jsp:useBean id="now" class="java.util.Date" />
		<c:set var="nowdate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" /></c:set>
		
		<c:forEach items="${edusatList}" var="edusat" varStatus="no">
		
		<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}${edusat.edu_resdate_h}00' />
		<c:if test="${fn:length(edusat.edu_resdate_h) == 1}">
			<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}0${edusat.edu_resdate_h}00' />
		</c:if>
		
		<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}${edusat.edu_reedate_h}59' />
		<c:if test="${fn:length(edusat.edu_reedate_h) == 1}">
			<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}0${edusat.edu_reedate_h}59' />
		</c:if>
		
		<c:set var="requestChk" value="false"></c:set>
		<c:choose>
			<c:when test="${reedate < nowdate}">
				<c:set var="str2" value='기간종료' />
			</c:when>
			<c:when test="${resdate > nowdate}">
				<c:set var="str2" value='신청준비' />
			</c:when>
			<c:otherwise>
				<c:set var="str2" value='신청중' />
				<c:set var="requestChk" value="true"></c:set>
			</c:otherwise>
		</c:choose>
				
		<%//신청상태 %>
		<c:set var="es_status_str" value="" />
		<c:set var="wait_count" value="" />
		<c:set var="es_status_col" value="#7c7c7c" /> 
		<c:choose>
			<c:when test="${edusat.edu_lot_type eq '2' }">
				<c:set var="es_status_str" value="신청완료"></c:set>
				<c:if test="${edusat.count_value+0 >= edusat.edu_inwon+0}">
					<c:set var="es_status_str" value="대기자"></c:set>
					<c:set var="wait_count" value="${(edusat.count_value+0) - (edusat.edu_inwon+0) + 1}" />
					<c:set var="es_status_str" value="대기자(${wait_count }번째)"></c:set>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:set var="es_status_str" value="신청완료"></c:set>
				<c:if test="${edusat.es_status eq '1'}">
					<c:set var="es_status_str" value="당첨"></c:set>
				</c:if>
				<c:if test="${edusat.es_status eq '2'}">
					<c:set var="es_status_str" value="낙첨"></c:set>
				</c:if>
				<c:if test="${edusat.es_status eq '3'}">
					<c:set var="es_status_str" value="선정완료"></c:set>
					<c:set var="es_status_col" value="#5C7DF8" />
				</c:if>
				<c:if test="${edusat.es_status eq '4'}">
					<c:set var="es_status_str" value="선정미완료"></c:set>
					<c:set var="es_status_col" value="#f00" />
				</c:if>
			</c:otherwise>
		</c:choose>
		<c:if test="${edusat.es_status eq '9' }">
			<c:set var="es_status_str" value="취소"></c:set>
			<c:set var="es_status_col" value="#f00" />
		</c:if>
		
		<c:set var="view_url" value="/edusat/view.do?edu_idx=${edusat.edu_idx}" />
		<c:if test="${!empty BUILDER_DIR }">
			<c:set var="view_url" value="/${BUILDER_DIR }/edusat/view.do?edu_idx=${edusat.edu_idx}" />
		</c:if>
			
		<tr>
			<td class="eng">${recordcount - ((v_page-1) * pagesize + no.index) }</td>
			<td class="title">
				<a href="${view_url }" target="_blank">${edusat.edu_subject}</a>
				<br/>접수기간 : <span class="eng">${edusat.edu_resdate}~${edusat.edu_reedate}${edusat.es_status }</span>
			</td>
			<%--<td>
				<span class="eng"><c:out value="${edusat.es_ptcp_cnt} " /></span>명
				<c:if test="${edusat.edu_ptcp_yn eq 'Y' }">
					<br/>${ edusat.es_ptcp_name }
				</c:if>
			</td> --%>
			<%--
			<td>
			<c:choose>
			
				<c:when test="${requestChk eq 'true' }">
					신청중
				</c:when>
				<c:otherwise>
					${str2}
				</c:otherwise>
			</c:choose>
			</td>
			 --%>
			<td style="color:${es_status_col}">${es_status_str}</td>
			<td>
				
				<input type="button" class="con_sbtn green" value="정보확인" onclick="location.href='request.do?edu_idx=${edusat.edu_idx}&amp;es_idx=${edusat.es_idx }&prepage=${nowPage}'" />
			</td>
			<td>
				
				<c:if test="${edusat.es_status ne '9'}">
				
					<% // 개강 3일전까지 수정가능 %>
					<c:set var="updateChk" value="N" />
					
					<fmt:parseDate value="${edusat.edu_reedate }" var="strPlanDate" pattern="yyyy-MM-dd"/>
					<fmt:parseNumber value="${strPlanDate.time / (1000*60*60*24)}" integerOnly="true" var="strDate"></fmt:parseNumber>
					<fmt:parseNumber value="${now.time / (1000*60*60*24)}" integerOnly="true" var="endDate"></fmt:parseNumber>
					
					<c:if test="${strDate - endDate >=2}">
						<c:set var="updateChk" value="Y"></c:set>
					</c:if>
					
					<c:choose>
						<c:when test="${edusat.es_status eq '3'}">
							<input type="button" value="수정하기" onclick="javascript:alert('접수완료건은 수정 불가능합니다.\n\n수정을 원할경우 투명우산나눔활동 담당자에게 연락해주세요.');" class="con_sbtn orange" />
						</c:when>
						<c:when test="${updateChk eq 'Y'}">
							<input type="button" value="수정하기" onclick="modifyOk('${edusat.edu_idx}', '${edusat.es_idx}');" class="con_sbtn orange" />
							
						</c:when>
						<c:when test="${updateChk eq 'N'}">
							<input type="button" value="수정하기" onclick="javascript:alert('신청 마감 3일전까지 수정이 가능합니다.\n\n이후 수정을 원할경우 투명우산나눔활동 담당자에게 연락해주세요.');" class="con_sbtn orange" />
						</c:when>
					</c:choose>
				</c:if>
				
			</td>
			<td>
				
				<c:if test="${edusat.es_status ne '9'}">
				
					<% // 개강 3일전까지 취소가능 %>
					<c:set var="cancelChk" value="N" />
					
					<fmt:parseDate value="${edusat.edu_reedate }" var="strPlanDate" pattern="yyyy-MM-dd"/>
					<fmt:parseNumber value="${strPlanDate.time / (1000*60*60*24)}" integerOnly="true" var="strDate"></fmt:parseNumber>
					<fmt:parseNumber value="${now.time / (1000*60*60*24)}" integerOnly="true" var="endDate"></fmt:parseNumber>
					
					<c:if test="${strDate - endDate >=2}">
						<c:set var="cancelChk" value="Y"></c:set>
					</c:if>
					
					<fmt:parseDate value="${edusat.edu_reedate }" var="strPlanDate2" pattern="yyyy-MM-dd"/>
					<fmt:parseNumber value="${strPlanDate2.time / (1000*60*60*24)}" integerOnly="true" var="strDate2"></fmt:parseNumber>
					<c:if test="${strDate2 < endDate}">
						<c:set var="cancelChk" value="X"></c:set>
					</c:if>
					
					<c:choose>
						<c:when test="${edusat.es_status eq '3'}">
							<input type="button" value="취소하기" onclick="javascript:alert('접수완료건은 취소 불가능합니다.\n\n취소를 원할경우 투명우산나눔활동 담당자에게 연락해주세요.');" class="con_sbtn blue" />
						</c:when>
						<c:when test="${cancelChk eq 'Y'}">
							<input type="button" value="취소하기" class="con_sbtn blue" onclick="deleteOk('${edusat.edu_idx}', '${edusat.es_idx}');"/>
						</c:when>
						<c:when test="${cancelChk eq 'N'}">
							<input type="button" value="취소하기" class="con_sbtn blue" onclick="javascript:alert('신청마감 3일전까지 취소가 가능합니다.\n\n이후 취소를 원할경우 투명우산나눔활동 담당자에게 연락해주세요.');"/>
						</c:when>
					</c:choose>
				</c:if>
				
			</td>
		</tr>
		</c:forEach>
		<c:if test="${fn:length(edusatList) == 0}">
		<tr>
			<td class="center" colspan="6">등록된 내용이 없습니다.</td>
		</tr>
		</c:if>
		</tbody>
	</table>
	</div>
		

	<!-- 페이징 -->
	<div class="board_paginate">
		${pagingtag }
	</div>
	<!-- //페이징 -->
	
	<form action="/main/edusat/regist.do" id="reform" method="post">
		<input type="hidden" name="prepage" value="${nowPage }" >
		<input type="hidden" name="a" > <!-- es_idx -->
		<input type="hidden" name="b" > <!-- edu_idx -->
	</form>

</div>
</div>