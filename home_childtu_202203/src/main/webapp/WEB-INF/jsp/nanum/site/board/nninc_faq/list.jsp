<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<c:if test="${!empty param.b_num }">
<script>
$(function(){
	showAnswer(${param.b_num});
});
</script>
</c:if>
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
	
	<c:set var="colCnt" value="1" />
	<!-- 리스트 테이블 -->
	<div class="table_blist" id="faq_area">

	<table cellspacing="0" summary="${title} 의 번호, 제목, 글쓴이, 등록일, 조회수를 확인">
	<caption>${title}</caption>
		<colgroup>
		<c:if test="${is_ad_cms eq 'Y'}">
		<col width="60" />
		<c:set var= "colCnt" value="${colCnt+1}"/>
		</c:if>
		<col width="" />
		<c:if test="${is_write eq 'Y' or is_delete eq 'Y'}">
		<col width="50" />
		<c:set var= "colCnt" value="${colCnt+1}"/>
		</c:if>
		</colgroup>

		<thead>
			<tr>
				<th class="disnone" colspan="${colCnt}">자주하는 질문</th>
			</tr>
		</thead>
		<tbody>
		<!-- 공지기능 -->
		<jsp:useBean id="now" class="java.util.Date" />
		<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="now_date" />
		<!-- 공지기능 -->
		<c:forEach items="${noticeList}" var="no_board" varStatus="no">
		<tr>
			<c:if test="${is_ad_cms eq 'Y'}">
			<td  class="notice"><input type="checkbox" name="chk" value="${no_board.b_num}" title="해당 게시글 선택" /></td>
			</c:if>
			<td id="question_title${no_board.b_num}" class="notice title_notice" style="${no_board.txt_title_style}">
					${no_board.txt_show}
					<c:if test="${no_board.b_type eq 'N'}"><span class='category_color'>${b_cate_str[no_board.b_cate]}</span></c:if>

					<a href="#${no_board.b_num}" onclick="showAnswer(${no_board.b_num});" title="${no_board.b_subject}">${no_board.b_subject}</a>
					<c:if test="${config.a_command eq 'Y' and no_board.b_c_count ne '0'}"><span class="reply_count">(${no_board.b_c_count})</span></c:if>
					<c:if test="${no_board.newIcon}"><img src='/nanum/site/board/nninc_simple/img/icon_new.gif' alt='NEW(새글)' /></c:if>
			</td>

			<c:if test="${is_write eq 'Y' and is_delete eq 'Y'}">
			<td>
				<c:if test="${is_write eq 'Y'}">
				<span><a href="?proc_type=write&a_num=${config.a_num}&b_num=${no_board.b_num}&prepage=${nowPageEncode}"><img src="/nanum/site/board/nninc_faq/img/modify_bt2.gif" alt="수정" /></a></span>
				</c:if>
				<c:if test="${is_delete eq 'Y'}">
					<c:url var="gourl" value="/board/deleteOk.do">
					<c:param name="a_num" value="${config.a_num }" />
					<c:param name="b_num" value="${no_board.b_num }" />
					<c:param name="prepage" value="${nowPage }" />
					<c:param name="d_page" value="${path_info }" />
				</c:url>
				<span><a href="${gourl}" onclick="if(!confirm('정말 삭제하시겠습니까?')){return false;}"><img src="/nanum/site/board/nninc_faq/img/delete_bt2.gif" alt="삭제 " /></a></span>
				</c:if>
			</td>
			</c:if>

			</tr>
			<tr id="answer${no_board.b_num}" style="display:none;">
				<c:if test="${is_ad_cms eq 'Y'}">
				<td class="content2">&nbsp;</td>
				</c:if>
				<td class="content">
					${no_board.b_content}

					<c:if test="${config.a_upload eq 'Y' and (no_board.b_file1 ne '' or no_board.b_file2 ne '' or no_board.b_file3 ne '' or no_board.b_file4 ne '' or no_board.b_file5 ne  '')}">
						<dl class="info">
							<dd>
								<c:forEach var="t" begin="1" end="5" step="1">
								<!-- 가변변수 담기.. -->
								<c:set var="b_file">b_file${t}</c:set>
								<c:set var="b_file_size">b_file${t}_size</c:set>
								<c:set var="b_file_icon">b_file${t}_icon</c:set>
								<c:set var="file_name">${no_board[b_file]}</c:set>
								<c:set var="file_size">${no_board[b_file_size]}</c:set>
								<c:set var="file_icon">${no_board[b_file_icon]}</c:set>
								
								<c:if test="${file_name ne ''}">
									<img src="/nanum/site/board/${config.a_level}/img/file/${file_icon}" alt="" /><a href="/board/down.do?a_num=${config.a_num}&b_num=${no_board.b_num}&f_num=${t}" target="_blank">${file_name}</a>
									
									<c:if test="${file_size ne ''}"><span class="size">(${file_size} byte)</span></c:if>
									&nbsp;		
								</c:if>
								</c:forEach>
							</dd>
						</dl>
					</c:if>
				</td>
				<c:if test="${is_write eq 'Y' or is_delete eq 'Y'}">
				<td class="content2">&nbsp;</td>
				</c:if>
			</tr>
			</c:forEach>
			<!-- //공지기능 -->
				
			<c:forEach items="${boardList}" var="board" varStatus="no">
			<tr id="question${board.b_num}" >
				<c:if test="${is_ad_cms eq 'Y'}">
				<td><input type="checkbox" name="chk" value="${board.b_num}" title="해당 게시글 선택" /></td>
				</c:if>
				<td id="question_title${board.b_num}" class="title" style="${board.txt_title_style}">
						${board.txt_show}
						<c:if test="${board.b_type eq 'R'}">
							<c:forEach var="xx" begin="1" end="${board.b_level}" step="1">&nbsp;&nbsp;&nbsp;</c:forEach>
								<img src='/nanum/site/board/nninc_simple/img/reply_ic.gif' alt='답변글' />
						</c:if>
						<c:if test="${board.b_type eq 'N'}"><span class='category_color'>${b_cate_str[board.b_cate]}</span></c:if>
						<a href="#${board.b_num}" onclick="showAnswer(${board.b_num});" onkeypress="showAnswer(${board.b_num});">${board.b_subject}</a>
						<c:if test="${config.a_command eq 'Y' and board.b_c_count ne '0'}"><span class="reply_count">(${board.b_c_count})</span></c:if>
						<c:if test="${board.newIcon}"><img src='/nanum/site/board/nninc_simple/img/icon_new.gif' alt='NEW(새글)' /></c:if>

						<c:if test="${(config.a_type eq 'Y' and board.b_open eq 'N') or config.a_type eq 'T'}">
							<img src='/nanum/site/board/nninc_simple/img/icon_key.gif' alt='비밀글' />
						</c:if>

				</td>

				<c:if test="${is_write eq 'Y' or is_delete eq 'Y'}">
				<td class="btn_modify">
					<c:if test="${is_write eq 'Y'}">
					<span><a href="?proc_type=write&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}"><img src="/nanum/site/board/nninc_faq/img/modify_bt2.gif" alt="ìì " /></a></span>
					</c:if>
					<c:if test="${is_delete eq 'Y'}">
						
						<c:url var="gourl" value="/board/deleteOk.do">
							<c:param name="a_num" value="${config.a_num }" />
							<c:param name="b_num" value="${board.b_num }" />
							<c:param name="prepage" value="${nowPage }" />
							<c:param name="d_page" value="${path_info }" />
							<c:param name="board_token" value="${board_token }" />
						</c:url>
					<span><a href="${gourl}" onclick="if(!confirm('정말 삭제하시겠습니까?')){return false;}" title="삭제"><img src="/nanum/site/board/nninc_faq/img/delete_bt2.gif" alt="삭제" /></a></span>
					</c:if>
				</td>
				</c:if>

			</tr>
			<tr id="answer${board.b_num}" >
				<c:if test="${is_ad_cms eq 'Y'}">
				<td class="content2">&nbsp;</td>
				</c:if>
				<td class="content">
					<c:if test="${config.a_photoview eq 'Y'}">
						<c:forEach var="t" begin="1" end="5" step="1">
							<c:set var="b_file">b_file${t}</c:set>
							<c:set var="file_name">${board[b_file]}</c:set>
							
							<c:if test="${file_name ne ''}">
								<c:set var="ext_arr" value="${fn:split(file_name,'.')}" />
								<c:set var="ext" value="${ext_arr[fn:length(ext_arr)-1]}" />
								<c:if test="${ext eq 'gif' or ext eq 'jpg'}">
									<c:set var="tot_file" value="/board/get_img.do?a_num=${config.a_num}&b_num=${board.b_num}&f_num=${t}&type=img"/>
									<a href = "${tot_file}" rel="lightbox"><img src="${tot_file}"  alt="${file_name} 이미지를 클릭하시면 원본크기를 보실 수 있습니다." onload="imgResize(this, 600, 0);" width = "500"/></a><br/><br/>
								</c:if>
							</c:if>
						</c:forEach>
					</c:if>

					${board.b_content}

					<c:if test="${config.a_upload eq 'Y' and (board.b_file1 ne '' or board.b_file2 ne '' or board.b_file3 ne '' or board.b_file4 ne '' or board.b_file5 ne  '')}">
					<dl class="info">
						<dd>
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
						</dd>
					</dl>
					</c:if>
					
				</td>
				<c:if test="${is_write eq 'Y' or is_delete eq 'Y'}">
				<td class="content2">&nbsp;</td>
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
					<li><select id="v_search" name="v_search" title="검색형태 선택" style="width:70px;" >
						<option value="b_subject" ${"b_subject" == param.v_search ? 'selected="selected"' : '' }>제목</option>
						<option value="b_content" ${"b_content" == param.v_search ? 'selected="selected"' : '' }>내용</option>
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

<script type="text/javascript">
<!--
	showAnswer2();
//-->
</script>