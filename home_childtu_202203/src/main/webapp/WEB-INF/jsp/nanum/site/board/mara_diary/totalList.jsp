<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ko" />
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>독서마라톤 신청자정보</title>
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/sub_layout.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/all.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/design_default.css" />
	<script type="text/javascript" src="/nanum/site/common/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>

</head>
<body>

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<script>
$(function(){
	$("#board").attr("tabindex", -1).focus();
	$("#board").focus();
});

</script>

<!-- 리스트 -->
<div id="board" style="width:${config.a_width};">
	<!-- 쓰기 테이블 -->
	<div class="table_bwrite">

			<table cellspacing="0" summary="${title}의 이름, 비밀번호, 내용을 입력">
			<caption>${title}</caption>
				<colgroup>
				<col width="200" />
				<col width="" />
				</colgroup>
			<thead>
				<tr>
					<th scope="col" class="th_end" colspan="2">신청자정보</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row">목표페이지 / 누적페이지</th>
					<td>
<fmt:formatNumber var="b_temp7_number" value="${mara_rq_board.b_temp7}" pattern="#,###"/>
<fmt:formatNumber var="b_temp9_number" value="${mara_rq_board.b_temp9}" pattern="#,###"/>
<fmt:formatNumber var="calc_page" value="${mara_rq_board.b_temp7 - mara_rq_board.b_temp9}" pattern="#,###"/>
<fmt:formatNumber var="avg" value="${(mara_rq_board.b_temp9*1.0 / mara_rq_board.b_temp7) * 100}" pattern="#.##"/>
<c:set var="avg" value="${avg}%" />
<c:if test="${mara_rq_board.b_temp10 eq '1' }">
	<c:set var="avg" value="<span style='font-weight:bold;color:#FF0000;'>${avg }</span>" />
</c:if>
					
						${b_temp9_number } / ${b_temp7_number } ( 남은 페이지 : ${calc_page } )
					</td>
				</tr>
				<tr>
					<th scope="row">달성률</th>
					<td>
						${avg }
					</td>
				</tr>
				<tr>
					<th scope="row">아이디</th>
					<td>
						${mara_rq_board.b_id }
					</td>
				</tr>
				<tr>
					<th scope="row">이름</th>
					<td>
						${mara_rq_board.b_name }
					</td>
				</tr>
				<tr>
					<th scope="row">참가종목</th>
					<td>
<c:set var="b_temp7_str" value="${courseList[mara_rq_board.b_temp7] } (${b_temp7_number }쪽)" />
<c:if test="${empty courseList[mara_rq_board.b_temp7] }">
	<c:set var="b_temp7_str" value="${mara_rq_board.b_temp7 }" />
</c:if>
						${b_temp7_str }
					</td>
				</tr>
				<tr>
					<th scope="row">휴대전화</th>
					<td>
						${mara_rq_board.b_temp4 }
					</td>
				</tr>
				<tr>
					<th scope="row">전화번호</th>
					<td>
						${mara_rq_board.b_phone1 }-${mara_rq_board.b_phone2 }-${mara_rq_board.b_phone3 }
					</td>
				</tr>
				<tr>
					<th scope="row">이메일</th>
					<td>
						${mara_rq_board.b_email }
					</td>
				</tr>
				<tr>
					<th scope="row">참가신청일</th>
					<td>
						${mara_rq_board.b_regdate }
					</td>
				</tr>
				<tr>
					<th scope="row">각오한마디</th>
					<td>
						${mara_rq_board.b_content }
					</td>
				</tr>
 			</tbody>
			</table>
	</div>




	<div class="board_total">
		<div class="board_total_left">
		전체 <strong>${recordcount}</strong>개
		</div>
		<div class="board_total_right">
			&nbsp;
		</div>
	</div>
	
	<c:set var="colCnt" value="8" />
	
	<!-- 리스트 테이블 -->
	<div class="table_blist">
	<table cellspacing="0" summary="${title} 의 번호, 제목, 글쓴이, 등록일, 조회수를 확인">
	<caption>${title}</caption>
		<colgroup>
		<col width="50" />
		<col width="" />
		<col width="" />
		<col width="80" />
		<%//PC일때만 %>
		<c:if test="${pc_version eq 'Y'}">
			<col width="" />
			<c:set var= "colCnt" value="${colCnt+1}"/>
		</c:if>
		<col width="60" />
		<col width="" />
		<%//PC일때만 %>
		<c:if test="${pc_version eq 'Y'}">
			<col width="" />
			<c:set var= "colCnt" value="${colCnt+1}"/>
		</c:if>
		
		</colgroup>
	<thead>
		<tr>
			<th scope="col">번호</th>
			<th scope="col">이름</th>
			<th scope="col">도서명</th>
			<th scope="col">저자</th>
			<th scope="col">출판사</th>
			<th scope="col">날짜</th>
			<%//PC일때만 %>
			<c:if test="${pc_version eq 'Y'}">
				<th scope="col">분류번호</th>
			</c:if>
			<th scope="col">읽은쪽수</th>
			<th scope="col">누적쪽수</th>

			<%//PC일때만 %>
			<c:if test="${pc_version eq 'Y'}">
				<th scope="col">도서관구분</th>
			</c:if>
		</tr>
	</thead>
	<tbody>
		<jsp:useBean id="now" class="java.util.Date" />
		<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="now_date" />
	
	<c:forEach items="${boardList}" var="board" varStatus="no">
		<c:set var="b_name" value="${board.b_name }" />
		<c:if test="${(config.a_type eq 'Y' and board.b_open eq 'N') or config.a_type eq 'T'}">
			<c:if test="${is_ad_cms ne 'Y' }">
				<c:set var="b_name" value="비공개" />
			</c:if>
		</c:if>
		<c:set var="view_url" value="?proc_type=write&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}" />
	<tr>
		<td>${no.count }</td>
		<td>${board.b_name }</td>
		<td style="${board.txt_title_style}">
				${board.txt_show}
				<c:if test="${board.b_type eq 'R'}">
					<c:forEach var="xx" begin="1" end="${board.b_level}" step="1">&nbsp;&nbsp;&nbsp;</c:forEach>
						<img src='/nanum/site/board/nninc_simple/img/reply_ic.gif' alt='답변글' />
				</c:if>
				<c:if test="${board.b_type eq 'N'}"><span class='category_color'>${b_cate_str[board.b_cate]}</span></c:if>
				
				${board.b_subject}
				<c:if test="${config.a_command eq 'Y' and board.b_c_count ne '0'}"><span class="reply_count">(${board.b_c_count})</span></c:if>
				
				<c:if test="${board.newIcon}"><img src='/nanum/site/board/nninc_simple/img/icon_new.gif' alt='NEW(새글)' /></c:if>
				<c:if test="${config.a_upload eq 'Y'}">
					<c:if test="${!empty board.b_file1 or !empty board.b_file2 or !empty board.b_file3 or !empty board.b_file4 or !empty board.b_file5}">
						<img src='/nanum/site/board/nninc_simple/img/icon_file.gif' alt='첨부파일' />
					</c:if>
				</c:if>
				
				<c:if test="${(config.a_type eq 'Y' and board.b_open eq 'N') or config.a_type eq 'T'}">
					<img src='/nanum/site/board/${config.a_level }/img/icon_key.gif' alt='비밀글' />
				</c:if>
				
				<c:choose>
					<c:when test="${board.b_temp3 eq '000' }"><c:set var="b_temp3_str" value="총류" /></c:when>
					<c:when test="${board.b_temp3 eq '100' }"><c:set var="b_temp3_str" value="철학" /></c:when>
					<c:when test="${board.b_temp3 eq '200' }"><c:set var="b_temp3_str" value="종교" /></c:when>
					<c:when test="${board.b_temp3 eq '300' }"><c:set var="b_temp3_str" value="사회과학" /></c:when>
					<c:when test="${board.b_temp3 eq '400' }"><c:set var="b_temp3_str" value="순수과학" /></c:when>
					<c:when test="${board.b_temp3 eq '500' }"><c:set var="b_temp3_str" value="기술과학" /></c:when>
					<c:when test="${board.b_temp3 eq '600' }"><c:set var="b_temp3_str" value="예술" /></c:when>
					<c:when test="${board.b_temp3 eq '700' }"><c:set var="b_temp3_str" value="언어" /></c:when>
					<c:when test="${board.b_temp3 eq '800' }"><c:set var="b_temp3_str" value="문학" /></c:when>
					<c:when test="${board.b_temp3 eq '900' }"><c:set var="b_temp3_str" value="역사" /></c:when>
				</c:choose>
				
		</td>
		<td>${board.b_temp4 }</td>
		<td>${board.b_temp5 }</td>
		<td>${fn:replace(fn:substring(board.b_regdate,0,10),"-", ".")}</td>
		<%//PC일때만 %>
		<c:if test="${pc_version eq 'Y'}">
		<td>${board.b_temp3}(${b_temp3_str })</td>
		</c:if>

		<td><fmt:formatNumber value="${board.b_temp1}" pattern="#,###"/></td>
		<td><fmt:formatNumber value="${board.sumpage }" pattern="#,###" /></td>

		<%//PC일때만 %>
		<c:if test="${pc_version eq 'Y'}">
		<td>${board.b_temp2 }</td>
		</c:if>
	</tr>
	<tr>
		<td colspan="${colCnt}" style="text-align:left;padding:5px 5px 5px 5px;background-color:#EEEEEE">${board.b_content }</td>
	</tr>
	</c:forEach>
	<c:if test="${fn:length(boardList) == 0}">
		<tr>
			<td class="center" colspan="${colCnt}">등록된 내용이 없습니다.</td>
		</tr>
	</c:if>

			</tbody>
			</table>
	</div>
	<!-- //리스트 테이블 -->
	
	<!-- 버튼 -->
	<div class="board_button">
		<c:if test="${is_ad_cms eq 'Y'}">
		<div class="fl">
			&nbsp;
		</div>
		</c:if>
		<div class="fr">
			<span class="bt"><a href="#close" onclick="window.close();" style="color:#FFFFFF;" class="board_button3">닫기</a></span>
		</div>

	</div>
	<!-- //버튼 -->

	<!-- 페이징 -->
	<div class="board_paginate">
		&nbsp;
	</div>
	<!-- //페이징 -->

</div>
<!-- //리스트 -->

</body>
</html>