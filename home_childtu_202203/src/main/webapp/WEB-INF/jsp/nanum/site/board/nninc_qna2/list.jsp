<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

	<c:set var="colCnt" value="4" />
	<!-- 리스트 테이블 -->
	<p class="mscroll_guide mt20"><span>모바일로 확인하실 경우</span> 좌우로 움직여 내용을 확인 하실 수 있습니다.</p>
	<div class="mscroll">
	<div class="table_blist">

			<table cellspacing="0" summary="${title} 의 번호, 제목, 글쓴이, 등록일, 조회수를 확인">
			<caption>${title}</caption>
				<colgroup>
				<c:if test="${is_ad_cms eq 'Y'}">
				<col width="50" />
				<c:set var= "colCnt" value="${colCnt+1}"/>
				</c:if>
				<col width="10%" />
				<col width="" />
				<col width="10%" />
				<col width="15%" />
				</colgroup>
			<thead>
				<tr>
					<c:if test="${is_ad_cms eq 'Y'}">
					<th scope="col">선택</th>
					</c:if>
					<th scope="col">번호</th>
					<th scope="col">제목</th>
					<th scope="col">처리상황</th>
					<th scope="col">등록일</th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach items="${boardList}" var="board" varStatus="no">
				<!-- 리스트에 뿌릴 필드 정의 -->
				<c:set var="b_name" value="${board.b_name}" />
				<c:set var="b_open_str" value="" />
				<c:if test="${config.a_type eq 'Y'}">
					<c:if test="${board.b_open eq 'N'}">
						<c:set var="b_open_str" value="<span style='color:#1775C4;font-weight:bold;font-size:0.9em'>비공개</span>" />
						<c:set var="b_name" value="***" />
					</c:if>
					<c:if test="${board.b_open ne 'N'}">
						<%-- <c:set var="b_open_str" value="<span style='color:#F66E00;font-weight:bold;font-size:0.9em'>공개</span>" />--%>
						<c:set var="b_open_str" value="" />
					</c:if>
				</c:if>
				<c:if test="${(config.a_type eq 'Y' and board.b_open eq 'N') or config.a_type eq 'T'}">
					<c:set var="b_open_str" value="<img src='/nanum/site/board/${config.a_level }/img/icon_key.gif' alt='비밀글' />" />
				</c:if>
				<tr>
					<c:if test="${is_ad_cms eq 'Y'}">
					<td><input type="checkbox" name="chk" value="${board.b_num}" title="해당 게시글 선택" /></td>
					</c:if>
					<td>${recordcount - ((v_page-1) * pagesize + no.index) }</td>

					<td class="title" style="${board.txt_title_style}">
							${board.txt_show}
							<c:if test="${board.b_type eq 'R'}">
								<c:forEach var="xx" begin="1" end="${board.b_level}" step="1">&nbsp;&nbsp;&nbsp;</c:forEach>
									<img src='/nanum/site/board/nninc_simple/img/reply_ic.gif' alt='답변글' />
							</c:if>
							<c:if test="${board.b_type eq 'N'}"><span class='category_color'>${b_cate_str[board.b_cate]}</span></c:if>
							<a href="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}">${board.b_subject}</a>
							<c:if test="${config.a_command eq 'Y' and board.b_c_count ne '0'}"><span class="reply_count">(${board.b_c_count})</span></c:if>
							<c:if test="${board.newIcon}"><img src='/nanum/site/board/nninc_simple/img/icon_new.gif' alt='NEW(새글)' /></c:if>
							<c:if test="${config.a_upload eq 'Y' and config.a_lt_e eq 'Y'}">
								<c:if test="${board.b_file1 ne '' or board.b_file2 ne '' or board.b_file3 ne '' or board.b_file4 ne '' or board.b_file5 ne ''}">
									<img src='/nanum/site/board/nninc_simple/img/icon_file.gif' alt='첨부파일' />
								</c:if>
							</c:if>
							${b_open_str}
					</td>
					<c:set var="re_state_col" value="qna_ing" />
					<c:choose>
						<c:when test="${board.b_re_state eq '완료' }"><c:set var="re_state_col" value="qna_ing finish" /></c:when>
						<c:when test="${board.b_re_state eq '취하' }"><c:set var="re_state_col" value="qna_ing cancel" /></c:when>
						<c:when test="${board.b_re_state ne '진행중' }"><c:set var="re_state_col" value="qna_ing receipt" /></c:when>
					</c:choose>
					<td><span  class="${re_state_col }">${board.b_re_state == "" ? "접수" : board.b_re_state}</span></td>
					<td>${fn:replace(fn:substring(board.b_regdate,0,10),"-", ".")}</td>
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
			<span class="bt"><a href="?proc_type=write&a_num=${config.a_num}&prepage=${nowPageEncode}" class="con_btn orange">글쓰기</a></span>
			</c:if>
			<span class="bt"><a href="?proc_type=list&a_num=${config.a_num}" class="con_btn blue">목록</a></span>
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
					<li><select id="v_search" name="v_search" title="검색형태 선택" style="width:70px;" >
						<option value="b_subject" ${"b_subject" == param.v_search ? 'selected="selected"' : '' }>제목</option>
						<option value="b_content" ${"b_content" == param.v_search ? 'selected="selected"' : '' }>내용</option>
					</select></li>
					<li><input type="text" size="25" title="검색어를 입력하세요" id="p_keyword" name="v_keyword" class="search_input" value="${param.v_keyword}" /></li>
					<li class="bt"><input type="image" src="/nanum/site/board/nninc_simple/img/search_bt.gif" id="search_bt" name="search_bt" class="search_bt" alt="검색" /></li>
				</ul>
				</fieldset>
			</form>
		</div>
	</div>
	<!-- //게시물 검색 -->


</div>
<!-- //리스트 -->