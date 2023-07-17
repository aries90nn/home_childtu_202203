<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 오늘날짜 --%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy" var="nowYear" />

<c:set var="list_url_tmp" value="${nowURI }?proc_type=list&a_num=${param.a_num }" />
<c:set var="prepage" value="${empty param.prepage ? list_url_tmp : param.prepage}" />

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<!-- 보기 -->
<div id="board" style="width:${config.a_width};">
	
	<!-- 보기 테이블 -->
	<div class="table_bview">
		<table cellspacing="0" summary="${title} 의 작성일, 작성자, 조회수 및 내용" >
		<caption>${title}</caption>
			<colgroup>
				<col width="130" />
				<col width="" />
				</colgroup>
		<thead>
				<tr>
					<th scope="col" colspan="2" class="th_end">
					${fn:substring(board.b_sdate, 0, 4)}년 ${fn:substring(board.b_sdate, 5, 7)}월 ${fn:substring(board.b_sdate, 8,10)}일 신청정보</th>
				</tr>
		</thead>
		<tbody>
		
				<tr>
					<th scope="row">신청기관명</th>
					<td>${board.b_subject}</td>
				</tr>
		
		
<c:set var="b_name" value="${board.b_name}" />
<c:if test="${is_ad_cms ne 'Y'}">
	<c:set var="b_name" value="${fn:substring(b_name,0,1)}*${fn:substring(b_name,2, fn:length(b_name))}" />
</c:if>
				<tr>
					<th scope="row">성명</th>
					<td>${b_name}</td>
				</tr>
<c:if test="${is_ad_cms eq 'Y'}">
				<tr>
					<th scope="row">연락처</th>
					<td>${board.b_phone1}-${board.b_phone2}-${board.b_phone3}</td>
				</tr>
</c:if>
				<tr>
					<th scope="row">방문인원</th>
					<td>
						${board.b_temp1} 명
					</td>
				</tr>

				<tr>
					<th scope="row">방문시간대</th>
					<td>
						${board.b_temp2}
					</td>
				</tr>

				<tr>
					<th scope="row">접수상태</th>
					<td>
						<c:if test="${board.b_temp8 eq '대기'}">
							<span style="color:#1C1C1C">대기</span>
						</c:if>
						<c:if test="${board.b_temp8 eq '승인'}">
							<span style="color:#0000FF">승인</span>
						</c:if>
						<c:if test="${board.b_temp8 eq '보류'}">
							<span style="color:#DF0101">보류</span>
						</c:if>
						<c:if test="${board.b_temp8 eq '예약취소'}">
							<span style="color:#FF0000">예약취소</span>
						</c:if>
					</td>
				</tr>
		
		</tbody>
		</table>
	</div>
	<!-- //보기 테이블 -->

		<!-- 버튼 -->
	<div class="board_button">
		<div class="fl pt10">
			&nbsp;
		</div>
		<div class="fr pt10">

			<c:if test="${is_ad_cms eq 'Y' or (is_write eq 'Y' and empty board.b_temp8 or board.b_temp8 eq '대기')}">
				<span><a href="?proc_type=write&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}" class="con_btn dgray">수정</a></span>
			</c:if>

			<c:if test="${is_delete eq 'Y'}">
				<c:url var="gourl" value="/board/deleteOk.do">
					<c:param name="a_num" value="${config.a_num }" />
					<c:param name="b_num" value="${board.b_num }" />
					<c:param name="prepage" value="${nowPage }" />
					<c:param name="d_page" value="${path_info }" />
					<c:param name="board_token" value="${board_token }" />
				</c:url>
				<span><a href="${gourl}" onclick="if(!confirm('정말 삭제하시겠습니까?')){return false;}" class="con_btn dgray">삭제</a></span>
			</c:if>

			<span><a href="${prepage}" class="con_btn dgray">목록</a></span>
		</div>
	</div>
	<!-- //버튼 -->


	<!-- 댓글 -->
	<c:if test="${config.a_command eq 'Y'}">
		<jsp:include page="../command.jsp" />
	</c:if>
	<!-- //댓글 -->


</div>
<!-- //보기 -->