<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? 'list.do' : param.prepage }" />

<link rel="Stylesheet" type="text/css" href="/nanum/site/edusat/css/common.css" />
<script type="text/javascript" src="/nanum/site/edusat/js/common.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
<script type="text/javascript">
//<![CDATA[

function deleteOk(es_idx){
	if(confirm("신청을 취소하시겠습니까?")){
		location.href="cancelOk.do?edu_idx=${edusatdto.edu_idx}&es_idx="+es_idx+"&prepage=${prepage}";
	}
}

//]]>
</script>
<script type="text/javascript">
function modifyOk() {
	var reform = document.getElementById('reform');
	reform.e.value = "${code}";
	reform.submit();
}
</script>

<jsp:include page="./config.jsp" />
<jsp:include page="./tophtml.jsp" />


<fmt:parseNumber value="${edusatdto.edu_inwon }" pattern="0" var="edu_inwon"></fmt:parseNumber>

<jsp:useBean id="now" class="java.util.Date" />
<c:set var="nowdate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" /></c:set>

<c:set var='resdate' value='${fn:replace(edusatdto.edu_resdate,"-","")}${edusatdto.edu_resdate_h}00' />
<c:if test="${fn:length(edusatdto.edu_resdate_h) == 1}">
	<c:set var='resdate' value='${fn:replace(edusatdto.edu_resdate,"-","")}0${edusatdto.edu_resdate_h}00' />
</c:if>

<c:set var='reedate' value='${fn:replace(edusatdto.edu_reedate,"-","")}${edusatdto.edu_reedate_h}59' />
<c:if test="${fn:length(edusatdto.edu_reedate_h) == 1}">
	<c:set var='reedate' value='${fn:replace(edusatdto.edu_reedate,"-","")}0${edusatdto.edu_reedate_h}59' />
</c:if>


<c:set var="requestChk" value="false" />
<c:choose>
	<c:when test="${reedate < nowdate}">
	</c:when>
	<c:when test="${resdate > nowdate}">
	</c:when>
	<c:otherwise>
	<c:set var="requestChk" value="true" />
	</c:otherwise>
</c:choose>


<div id="board" style="width:100%;">
	<!-- div class="board_total">
		<div class="board_total_left">
			총 <strong>${fn:length(edusatreq) }</strong>명이 신청등록되었습니다.
		</div>
	</div -->

	<!-- 리스트 테이블 -->
	<div class="table_blist">
		<table cellspacing="0"  summary="의 번호, 제목, 글쓴이, 등록일, 조회수를 확인">
		<caption></caption>
			<colgroup>
			<!-- <col width="10%" /> -->
			<col width="15%" />
			<!--<col width="15%" />-->
			<col width="15%" />
			<col width="*" />
			<col width="15%" />
			<col width="15%" />
			<col width="15%" />
			</colgroup>
		<thead>
			<tr>
				<!-- <th scope="col">번호</th> -->
				<th scope="col">이름</th>
				<!-- <th scope="col">참가인원</th> -->
				<th scope="col">휴대전화</th>
				<th scope="col">등록일</th>
				<th scope="col">상태</th>
				<th scope="col">수정</th>
				<th scope="col" class="th_none" >취소</th>
			</tr>
		</thead>
		<tbody>
<c:set var="num" value="1"></c:set>
<c:forEach items="${edusatreq}" var="edusatreq" varStatus="no">
	<c:set var="es_status_str" value="" />
	<c:set var="wait_count" value="" />
	<c:choose>
		<c:when test="${edusatdto.edu_lot_type eq '2' }">
			<c:set var="es_status_str" value="신청완료"></c:set>
			<c:if test="${edusatreq.count_value+0 >= edusatdto.edu_inwon+0}">
				<c:set var="wait_count" value="${(edusatreq.count_value+0) - (edusatdto.edu_inwon+0) + 1}" />
				<c:set var="es_status_str" value="대기자(${wait_count }번째)"></c:set>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:set var="es_status_str" value="신청완료"></c:set>
			
			<c:if test="${edusatreq.es_status eq '3'}">
				<c:set var="es_status_str" value="접수완료"></c:set>
			</c:if>
			<c:if test="${edusatreq.es_status eq '2'}">
				<c:set var="es_status_str" value="낙첨"></c:set>
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:if test="${edusatreq.es_status eq '9' }">
		<c:set var="es_status_str" value="취소"></c:set>
	</c:if>
	
	<c:set var="es_name_str" value="${edusatreq.es_name }"></c:set>
	<c:set var="es_idx_str" value="${edusatreq.es_idx }"></c:set>
	<c:set var="es_tel_str" value="${edusatreq.es_phone1 }-${edusatreq.es_phone2 }-${edusatreq.es_phone3 }"></c:set>
	
	<c:choose>
		<c:when test="${sessionScope.ss_m_id ne '' and sessionScope.ss_m_id ne null }">
			<c:set var="str_es_name" value="${edusatreq.es_name }"></c:set>
			<!-- <c:set var="str_es_jumin" value="${edusatreq.es_jumin }"></c:set> -->
			<c:set var="str_es_jumin" value="${edusatreq.es_phone1 }-${edusatreq.es_phone2 }-${edusatreq.es_phone3 }"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="str_es_name" value="${fn:substring(edusatreq.es_name,0,1)}*${fn:substring(edusatreq.es_name,2,fn:length(edusatreq.es_name))}"></c:set>
			<!-- <c:set var="str_es_jumin" value="${fn:substring(edusatreq.es_jumin,0,4)}**"></c:set> -->
			<c:set var="str_es_jumin" value="${edusatreq.es_phone1 }-****-${edusatreq.es_phone3 }"></c:set>
		</c:otherwise>
	</c:choose>
	
			<tr>
				<!-- <td class="eng">${num}</td> -->
				<td>${str_es_name}</td>
				<!-- <td>${edusatreq.es_ptcp_cnt} 명</td> -->
				<td class="eng">${str_es_jumin}</td>
				<td class="eng">${edusatreq.es_wdate }</td>
				<td>${es_status_str}</td>
				<td><input type="button" value="수정하기" onclick="modifyOk();" class="con_sbtn orange" /></td>
				<td>
				<c:if test="${edusatreq.es_status ne '9' and edusatreq.es_status ne '3'  and edusatreq.es_status eq '0'}">
					<c:choose>
						<c:when test="${requestChk eq 'true'}">
							<input type="button" value="취소하기" class="con_sbtn blue" onclick="deleteOk('${edusatreq.es_idx}');"/>
						</c:when>
						<c:otherwise>
							<input type="button" value="취소하기" class="con_sbtn blue" onclick="alert('지금은 신청 취소가 불가능합니다.');"/>
						</c:otherwise>
					</c:choose>
				</c:if>
				</td>
			</tr>
	<c:set var="num" value="${num+1}"></c:set>
</c:forEach>
<c:if test="${num == 1}">
				<tr>
					<td colspan="6">신청 내역이 없습니다.</td>
				</tr>
</c:if>
		</tbody>
		</table>

	</div>

	<!-- 버튼 -->
	<div class="btn_w">
		<span><a href="${prepage }" class="con_btn gray">돌아가기</a></span>
	</div>
	<!-- //버튼 -->

</div>

<form action="./regist.do" id="reform">
	<input type="hidden" name="a" value="${es_idx_str }">
	<input type="hidden" name="b" value="${edusatdto.edu_idx }">
	<input type="hidden" name="c" value="${es_tel_str }">
	<input type="hidden" name="d" value="${es_name_str }">
	<input type="hidden" name="e" value="">
</form>

<jsp:include page="./bthtml.jsp" />
