<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
				<col width="100" />
				<col width="100" />
				<col width="" />
				</colgroup>
		<thead>
				<tr>
					<th scope="col" colspan="3" class="th_end">
					${fn:substring(board.b_sdate, 0, 4)}년 ${fn:substring(board.b_sdate, 5, 7)}월 ${fn:substring(board.b_sdate, 8,10)}일 견학신청서</th>
				</tr>
		</thead>
		<tbody>
<c:if test="${is_ad_cms ne 'Y'}">
				<tr>
					<th scope="row" colspan="2"><label for="b_subject">신청기관</label></th>
					<td style="padding-left:5px">${board.b_subject}</td>
				</tr>

</c:if>
<c:if test="${is_ad_cms eq 'Y'}">

				<tr>
					<th scope="row" rowspan="${config.a_home eq 'Y' ? '3' : '2'}"><label for="b_subject">신청기관</label></th>
					<th scope="row">기관명</th>
					<td style="padding-left:5px">${board.b_subject}</td>
				</tr>

				<tr>
					<th scope="row">연락처</th>
					<td style="padding-left:5px">
					<c:set var="b_temp1_arr" value="${fn:split(board.b_temp1,'-')}" />
					<c:if test="${board.b_temp1 ne ''}">${b_temp1_arr[0]} - ${b_temp1_arr[1]} - ${b_temp1_arr[2]}</c:if>
					</td>
				</tr>
				<c:if test="${config.a_home eq 'Y'}">
				<tr>
					<th scope="row">주소</th>
					<td style="padding-left:5px">
						${board.b_zip1}
						<br />${board.b_addr1}&nbsp;${board.b_addr2}
					</td>
				</tr>
				</c:if>

				<tr>
					<th scope="row" rowspan="3">신청자</th>
					<th scope="row">성명</th>
					<td style="padding-left:5px">${board.b_name}</td>
				</tr>

				<tr>
					<th scope="row">연락처</th>
					<td style="padding-left:5px">${board.b_phone1} - ${board.b_phone2} - ${board.b_phone3}</td>
				</tr>

				<tr>
					<th scope="row">이메일</th>
					<td style="padding-left:5px">${board.b_email}</td>
				</tr>
</c:if>
				<tr>
					<th scope="row" colspan="2">방문시간대</th>
					<td style="padding-left:5px">
						${board.b_sdate}&nbsp;${board.b_temp2}

<c:choose>
	<c:when test="${config.a_num eq '22834449'}">

						<br />예상방문시간 : ${board.b_temp7}
	</c:when>
</c:choose>
					</td>
				</tr>

				<tr>
					<th scope="row" colspan="2">방문자 연령대</th>
					<td style="padding-left:5px">${board.b_temp3}</td>
				</tr>

				<tr>
					<th scope="row" colspan="2">방문인원</th>
					<td style="padding-left:5px">
						${board.b_temp4} 명
					</td>
				</tr>
<c:if test="${is_ad_cms eq 'Y'}">
				<tr>
					<th scope="row" rowspan="2">인솔자</th>
					<th scope="row">성명</th>
					<td style="padding-left:5px">${board.b_temp5}</td>
				</tr>

				<tr>
					<th scope="row">연락처</th>
					<td style="padding-left:5px">
					<c:set var="b_temp6_arr" value="${fn:split(board.b_temp6,'-')}" />
					${b_temp6_arr[0]} - ${b_temp6_arr[1]} - ${b_temp6_arr[2]}</td>
				</tr>
</c:if>

				<tr>
					<th scope="row" colspan="2"><label for="b_temp8">접수상태</label></th>
					<td style="padding-left:5px">
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
				
				<c:if test="${config.a_upload eq 'Y' and (board.b_file1 ne '' or board.b_file2 ne '' or board.b_file3 ne '' or board.b_file4 ne '' or board.b_file5 ne  '')}">
			<tr>
				<td colspan="2"></td>
				<td style="padding-left:5px">
					<dl class="info">
						<dt>첨부파일</dt>
						<dd>
							<c:forEach var="t" begin="1" end="10" step="1">
							<!-- 가변변수 담기.. -->
							<c:set var="b_file">b_file${t}</c:set>
							<c:set var="b_file_size">b_file${t}_size</c:set>
							<c:set var="b_file_icon">b_file${t}_icon</c:set>
							<c:set var="file_name">${board[b_file]}</c:set>
							<c:set var="file_size">${board[b_file_size]}</c:set>
							<c:set var="file_icon">${board[b_file_icon]}</c:set>
							
							<c:if test="${file_name ne ''}">
								<img src="/nanum/site/board/${config.a_level}/img/file/${file_icon}" alt="" /><a href="/board/down.do?a_num=${config.a_num}&b_num=${board.b_num}&f_num=${t}" target="_blank">${file_name}</a>
								
								<span class="size">(${file_size} byte)</span>
								&nbsp;	
								<c:if test="${t%3==0}">
									<br/>
								</c:if>
							</c:if>
							</c:forEach>
						</dd>
					</dl>
				</td>
				<td></td>
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