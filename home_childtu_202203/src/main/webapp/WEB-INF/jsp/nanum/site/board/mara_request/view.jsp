<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 오늘날짜 --%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyyMMdd" var="nowDate" />

<c:set var="list_url_tmp" value="${nowURI }?proc_type=list&a_num=${param.a_num }" />
<c:set var="prepage" value="${empty param.prepage ? list_url_tmp : param.prepage}" />


<c:if test="${!empty board.b_num }">
	<c:set var="b_temp3_1" value="${fn:split(board.b_temp3, '-')[0]}" />
	<c:set var="b_temp3_2" value="${fn:split(board.b_temp3, '-')[1]}" />
	
	<c:set var="b_temp4_1" value="${fn:split(board.b_temp4, '-')[0]}" />
	<c:set var="b_temp4_2" value="${fn:split(board.b_temp4, '-')[1]}" />
	<c:set var="b_temp4_3" value="${fn:split(board.b_temp4, '-')[2]}" />
	
	<c:set var="b_temp6_1" value="${fn:split(board.b_temp6, '-')[0]}" />
	<c:set var="b_temp6_2" value="${fn:split(board.b_temp6, '-')[1]}" />
	<c:set var="b_temp6_3" value="${fn:split(board.b_temp6, '-')[2]}" />
</c:if>

<%--코스별 쪽수 문자열 --%>
<c:if test="${!empty board.b_temp7 }">
	<fmt:formatNumber var="pagecnt" value="${board.b_temp7 }" pattern="#,###" />
</c:if>
<c:set var="b_temp7_str" value="${courseList[board.b_temp7] } (${pagecnt }쪽)" />
<c:if test="${empty courseList[board.b_temp7] }">
	<c:set var="b_temp7_str" value="${board.b_temp7 }" />
</c:if>

<c:choose>
	<c:when test="${board.b_temp1 eq '1' }">
		<c:set var="b_temp1_str" value="초등학생" />
	</c:when>
	<c:when test="${board.b_temp1 eq '2' }">
		<c:set var="b_temp1_str" value="중학생" />
	</c:when>
	<c:when test="${board.b_temp1 eq '3' }">
		<c:set var="b_temp1_str" value="고등학생" />
	</c:when>
	<c:when test="${board.b_temp1 eq '4' }">
		<c:set var="b_temp1_str" value="일반인" />
	</c:when>
	<c:otherwise>
		<c:set var="b_temp1_str" value="${board.b_temp1 }" />
	</c:otherwise>
</c:choose>

<c:set var="b_temp10_str" value="심사대기" />
<c:if test="${b_temp10 eq '1' }">
	<c:set var="b_temp10_str" value="<span style='font-weight:bold;color:#0000FF;'>완주완료</span>" />
</c:if>
<c:if test="${b_temp10 eq '2' }">
	<c:set var="b_temp10_str" value="<span style='font-weight:bold;color:#FF0000;'>완주실패</span>" />
</c:if>


<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<!-- 보기 -->
<div id="board" style="width:${config.a_width};">
	
	<!-- 보기 테이블 -->
	<div class="table_bwrite">

			<table cellspacing="0" summary="${title}의 이름, 비밀번호, 내용을 입력">
			<caption>${title}</caption>
				<colgroup>
				<col width="130" />
				<col width="" />
				</colgroup>
			<thead>
				<tr>
					<th scope="col" class="th_end" colspan="2">참가신청 현황</th>
				</tr>
			</thead>
			<tbody>
<c:if test="${board.b_temp10 eq '1' }">
	
	<fmt:parseDate value="20190920" pattern="yyyyMMdd" var="startDate" />
	<fmt:parseDate value="20191231" pattern="yyyyMMdd" var="endDate" />
	<fmt:formatDate value="${startDate}" pattern="yyyyMMdd" var="printStartDate"/>
	<fmt:formatDate value="${endDate}" pattern="yyyyMMdd" var="printEndDate"/>
	


	<c:url var="paper_url" value="/board/${config.a_level}/completePaper.do">
		<c:param name="a_level" value="${config.a_level }" />
		<c:param name="b_id" value="${board.b_id }" />
	</c:url>

	<c:if test="${(printStartDate <= nowDate && printEndDate >= nowDate)}">
		<%--심사끝나면 완주증서 출력오픈--%>
				<tr>
					<th scope="row">완주증서</th>
					<td><input type="button" value="인쇄" onclick="window.open('${paper_url}', 'paper', 'width=1000,height=1000,scrollbars=yes');" class="board_button3" style="padding:3px;" /></td>
				</tr>
	</c:if>
</c:if>
				<tr>
					<th scope="row">신청일</th>
					<td>${board.b_regdate}</td>
				</tr>
				<tr>
					<th scope="row">아이디</th>
					<td>${board.b_id }</td>
				</tr>
				<tr>
					<th scope="row">이름</th>
					<td>${board.b_name}</td>
				</tr>
<c:if test="${board.b_temp1 ne '4' }">
				<tr>
					<th scope="row">학교</th>
					<td>${board.b_temp2} ${b_temp3_1}학년 ${b_temp3_2}반</td>
				</tr>
</c:if>
				
				<!-- 주소 -->
				<c:if test="${config.a_home eq 'Y'}">
				<tr>
					<th scope="row">주소</th>
					<td>
						(${board.b_zip1 })
						${board.b_addr1 }
						${board.b_addr2 }
					</td>
				</tr>
				</c:if>
				<c:if test="${config.a_phone eq 'Y'}">
				<tr>
					<th scope="row">전화번호</th>
					<td>${board.b_phone1} - ${board.b_phone2} - ${board.b_phone3}</td>
				</tr>
				</c:if>
				<tr>
					<th scope="row">휴대전화</th>
					<td>${b_temp4_1} - ${b_temp4_2} - ${b_temp4_3}</td>
				</tr>

				<c:if test="${config.a_email eq 'Y'}">
				<tr>
					<th scope="row">이메일</th>
					<td>${board.b_email}</td>
				</tr>
				</c:if>
				<tr>
					<th scope="row"> <label for="b_email">성별</th>
					<td>${board.b_temp5 }</td>
				</tr>
				
				<tr>
					<th scope="row"> <label for="b_temp6_1">생년월일</th>
					<td>${b_temp6_1 }년 ${b_temp6_2 }월 ${b_temp6_3 }일</td>
				</tr>
				

				

				<tr>
					<th scope="row">참가부문</th>
					<td>${b_temp7_str }</td>
				</tr>
				<tr>
					<th scope="row">완주기념품</th>
					<td>${board.b_temp8 }</td>
				</tr>
				
				<c:if test="${config.a_content eq 'Y'}">
				<tr>
					<th scope="row">각오한마디</th>
					<td>
						${board.b_content }
					</td>
				</tr>
				</c:if>
				
				<c:if test="${config.a_upload eq 'Y'}">
				<tr>
					<th scope="row">첨부파일</th>
					<td style="padding:5px;">
						<jsp:include page="../inc_write_fileupload.jsp" />
					</td>
				</tr>
				</c:if>
				
				
				<c:if test="${config.a_cate eq 'Y' and board.b_type ne 'R'}">
				<tr>
					<th scope="row"> <label for="b_cate_tot">분류</th>
					<td>
						<c:forEach var="t" begin="1" end="5" step="1">
							<!-- 가변변수 담기.. -->
							<c:set var="b_file">b_file${t}</c:set>
							<c:set var="b_file_size">b_file${t}_size</c:set>
							<c:set var="b_file_icon">b_file${t}_icon</c:set>
							<c:set var="file_name">${board[b_file]}</c:set>
							<c:set var="file_size">${board[b_file_size]}</c:set>
							<c:set var="file_icon">${board[b_file_icon]}</c:set>
							
							<c:if test="${file_name ne ''}">
								<img src="/nanum/site/board/${config.a_level}/img/file/${file_icon}" alt="" /><a href="/board/down.do?a_num=${config.a_num}&b_num=${board.b_num}&f_num=${t}" target="_blank">${file_name}</a>
								
								<c:if test="${file_size ne ''}"><span class="size">(${file_size} byte)</span></c:if>
								&nbsp;		
							</c:if>
						</c:forEach>
					</td>
				</tr>
				</c:if>
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
			<c:if test="${is_reply eq 'Y' and config.a_reply eq 'Y'}">
				<c:url var="gourl" value="">
					<c:param name="a_num" value="${config.a_num }" />
					<c:param name="b_num" value="${board.b_num }" />
					<c:param name="prepage" value="${prepage }" />
					<c:param name="proc_type" value="write" />
					<c:param name="b_type" value="R" />
				</c:url>
			<span><a href="${gourl }" class="cbtn cbtn_g">답변</a></span>
			</c:if>
			
			<c:if test="${is_write eq 'Y'}">
				<span><a href="?proc_type=write&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}" class="cbtn cbtn_g">수정</a></span>
			</c:if>

			<c:if test="${is_delete eq 'Y'}">
				<c:url var="gourl" value="/board/deleteOk.do">
					<c:param name="a_num" value="${config.a_num }" />
					<c:param name="b_num" value="${board.b_num }" />
					<c:param name="prepage" value="${nowPage }" />
					<c:param name="d_page" value="${path_info }" />
					<c:param name="board_token" value="${board_token }" />
				</c:url>
			</c:if>

			<span><a href="${prepage}" class="cbtn cbtn_g">돌아가기</a></span>
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