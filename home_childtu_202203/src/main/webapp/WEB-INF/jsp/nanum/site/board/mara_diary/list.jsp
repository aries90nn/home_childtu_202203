<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<!-- 리스트 -->
<div id="board" style="width:${config.a_width};">
	<div class="board_total">
		<div class="board_total_left">
		<c:choose>
			<c:when test="${config.a_ad_cms eq 'Y'}">
				<c:choose>
					<c:when test="${ss_m_id == null}">
						<a href="?proc_type=login&a_num=${config.a_num}&prepage=${nowPageEncode}"><img src="/nanum/site/board/nninc_simple/img/key.gif" alt="" /></a> 
					</c:when>
					<c:otherwise>
						<a href="/board/logout.do?a_num=${config.a_num}&prepage=${nowPageEncode}"><img src="/nanum/site/board/nninc_simple/img/unkey.gif" alt="" /></a> 
					</c:otherwise>
				</c:choose>
				전체 <strong>${recordcount}</strong>개 (페이지 <strong class="board_orange">${v_page}</strong>/${totalpage})
			</c:when>
			<c:otherwise>
				<img src="/nanum/site/board/nninc_simple/img/total_ic.gif" alt="" /> 전체 <strong>${recordcount}</strong>개 (페이지 <strong class="board_orange">${v_page}</strong>/${totalpage})
			</c:otherwise>
		</c:choose>

		</div>
		<div class="board_total_right">
			<jsp:include page="../code.jsp" />
		</div>
	</div>
	
	<c:if test="${is_ad_cms eq 'Y'}">
	<form id= "frm_list" action="${path_info }" method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" name="a_num" value="${config.a_num}" />
		<input type="hidden" name="chk_all" id="chk_all" />		<!-- 전체체크 -->
		<input type="hidden" name="prepage" value="${nowPage}" />
		<input type="hidden" name="board_token" value="${board_token}" />
	</div>
	</c:if>
	
	<c:set var="colCnt" value="6" />
	
	<!-- 리스트 테이블 -->
	<div class="mscroll_guide"><span>모바일로 확인하실 경우</span> 표를 좌우로 움직여 내용을 확인 하실 수 있습니다.</div>
	<div class="mscroll">
	
	<div class="table_blist">
	<table cellspacing="0" summary="${title} 의 번호, 제목, 글쓴이, 등록일, 조회수를 확인">
	<caption>${title}</caption>
		<colgroup>
		<c:if test="${is_ad_cms eq 'Y'}">
		<col width="" />
		<c:set var= "colCnt" value="${colCnt+1}"/>
		</c:if>
		<col width="" />
		<col width="" />
		<col width="" />
		<col width="" />
		<%//PC일때만 %>
		<c:if test="${pc_version eq 'Y'}">
			<col width="" />
			<c:set var= "colCnt" value="${colCnt+1}"/>
		</c:if>
		<col width="" />
		<col width="" />
		<%//PC일때만 %>
		<c:if test="${pc_version eq 'Y'}">
			<col width="" />
			<c:set var= "colCnt" value="${colCnt+1}"/>
		</c:if>
		
		</colgroup>
	<thead>
		<tr>
			<c:if test="${is_ad_cms eq 'Y'}">
			<th scope="col">선택</th>
			</c:if>
			<th scope="col">번호</th>
			<th scope="col">이름</th>
			<th scope="col">도서명</th>
			<th scope="col">날짜</th>
			<%//PC일때만 %>
			<c:if test="${pc_version eq 'Y'}">
				<th scope="col">분류번호</th>
			</c:if>
			<th scope="col">읽은쪽수</th>
			<th scope="col">누적쪽수</th>

			<%//PC일때만 %>
			<c:if test="${pc_version eq 'Y'}">
				<th scope="col" class="th_none">도서관명</th>
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
		<c:if test="${is_ad_cms eq 'Y'}">
		<td><input type="checkbox" name="chk" value="${board.b_num}" title="해당 게시글 선택" /></td>
		</c:if>
		<td>${recordcount - ((v_page-1) * pagesize + no.index) }</td>
		<td>${board.b_name }</td>
		<td style="${board.txt_title_style}">
				${board.txt_show}
				<c:if test="${board.b_type eq 'R'}">
					<c:forEach var="xx" begin="1" end="${board.b_level}" step="1">&nbsp;&nbsp;&nbsp;</c:forEach>
						<img src='/nanum/site/board/nninc_simple/img/reply_ic.gif' alt='답변글' />
				</c:if>
				<c:if test="${board.b_type eq 'N'}"><span class='category_color'>${b_cate_str[board.b_cate]}</span></c:if>
				
				<a href="${view_url }">${board.b_subject}</a>
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
		<td>${fn:replace(fn:substring(board.b_regdate,0,10),"-", ".")}</td>
		<%//PC일때만 %>
		<c:if test="${pc_version eq 'Y'}">
		<td>${board.b_temp3}<br/>(${b_temp3_str })</td>
		</c:if>

		<td>${board.b_temp1}</td>
		<td><fmt:parseNumber integerOnly="true" value="${board.sumpage }" /></td>

		<%//PC일때만 %>
		<c:if test="${pc_version eq 'Y'}">
		<td>${board.b_temp2 }</td>
		</c:if>
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
	
	</div>
	<!-- //리스트 테이블 -->
	
	<c:if test="${is_ad_cms eq 'Y'}">
	</form>
	</c:if>

	<!-- 버튼 -->
	<div class="board_button">
		<c:if test="${is_ad_cms eq 'Y'}">
		<div class="fl">
			<ul>
				<li class="pt"><a href="javascript:checkAll();" class="board_lbtn"><span>전체 선택/해제</span></a></li>
				<li class="pt"><a href="javascript:delete2();" class="board_lbtn"><span>선택 게시글삭제</span></a></li>
			</ul>
		</div>
		</c:if>
		<div class="fr">
			<c:if test="${is_write eq 'Y'}">
			<span class="bt"><a href="?proc_type=write&a_num=${config.a_num}&prepage=${nowPageEncode}" class="cbtn cbtn_point">글쓰기</a></span>
			</c:if>
			<span class="bt"><a href="?proc_type=list&a_num=${config.a_num}" class="cbtn cbtn_g">목록</a></span>
		</div>

	</div>
	<!-- //버튼 -->

	<!-- 페이징 -->
	<div class="board_paginate">
		${pagingtag }
	</div>
	<!-- //페이징 -->

	<!-- 게시물 검색 -->
	<div style="margin:0 auto;text-align:center;">
		<div class="board_search">
			<form id="frm_sch" action="${path_info }" method="get">
			<input type="hidden" name="a_num" value="${config.a_num}" />
			<input type="hidden" name="v_cate" value="${param.v_cate}" />
				<fieldset>
				<legend>게시물 검색</legend>
				<ul>
					<li><select id="v_search" name="v_search" title="검색형태 선택" style="width:100px;" >
						<option value="b_subject" ${"b_subject" == param.v_search ? 'selected="selected"' : '' }>도서명</option>
						<option value="b_content" ${"b_content" == param.v_search ? 'selected="selected"' : '' }>감상문내용</option>
					</select></li>
					<li><input type="text" size="25" title="검색어를 입력하세요" id="p_keyword" name="v_keyword" class="search_input" value="${param.v_keyword}" /></li>
					<li><input type="image" src="/nanum/site/board/nninc_simple/img/search_bt.gif" id="search_bt" name="search_bt" class="search_bt" alt="검색" /></li>
				</ul>
				</fieldset>
			</form>
		</div>
	</div>
	<!-- //게시물 검색 -->


</div>
<!-- //리스트 -->