<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="list_url_tmp" value="${nowURI }?proc_type=list&a_num=${param.a_num }" />
<c:set var="prepage" value="${empty param.prepage ? list_url_tmp : param.prepage}" />

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->


<c:set var="b_sdate_str" value="" />
<c:if test='${board.b_sdate ne ""}'>
	<c:set var="b_sdate_str" value="${fn:substring(board.b_sdate, 0, 4)}년 ${fn:substring(board.b_sdate, 5, 7)}월 ${fn:substring(board.b_sdate, 8, 10)}일" />
</c:if>
<c:set var="img_url" value="${board.b_keyword}" />
<c:if test='${board.b_file1 ne ""}'>
	<c:set var="img_url" value="/board/get_img.do?a_num=${config.a_num}&b_num=${board.b_num}&f_num=1&type=img" />
</c:if>

<fmt:parseDate value="${board.b_sdate}" pattern="yyyy-MM-dd" var="sdate" />
<fmt:formatDate value="${sdate}" pattern="E" var="week"/>

<!-- 보기 -->
<div id="board" style="width:${config.a_width};">
	
	<!-- 보기 테이블 -->
	<div class="table_bview">
		<div class="movieinfo">
			<span class="img"><img src="${img_url}" onerror="this.src='/nanum/site/board/movie/img/no.gif'" /></span>
			<div class="info">
				<p class="tit"><span class='category_color'>${b_cate_str[board.b_cate]}</span> ${board.b_subject}</p>
				<div class="txt">
					<ul>
						<li>
							<dl>
								<dt>상영일시</dt>
								<dd>${b_sdate_str}(${week}) ${board.b_temp1}</dd>
							</dl>
						</li>
						<c:if test="${board.b_temp2 ne ''}">
						<li>
							<dl>
								<dt>상영장소</dt>
								<dd>${board.b_temp2}</dd>
							</dl>
						</li>
						</c:if>
					</ul>
					<c:if test="${board.b_temp3 ne ''}">
					<ul>

						<li>							
							<dl>
								<dt>감독</dt>
								<dd>${board.b_temp3}</dd>
							</dl>							
						</li>
					</ul>
					</c:if>
					<c:if test="${board.b_temp4 ne ''}">
					<ul>
						<li>
							<dl>
								<dt>배우</dt>
								<dd>${board.b_temp4}</dd>
							</dl>
						</li>
					</ul>
					</c:if>
					<ul>
						<c:if test="${board.b_temp5 ne ''}">
						<li>							
							<dl>
								<dt>장르</dt>
								<dd>${board.b_temp5}</dd>
							</dl>							
						</li>
						</c:if>
						<c:if test="${board.b_temp6 ne ''}">
						<li>
							<dl>
								<dt>관람등급</dt>
								<dd>${board.b_temp6}</dd>
							</dl>
						</li>
						</c:if>
						<c:if test="${board.b_temp7 ne ''}">
						<li>
							<dl>
								<dt>상영시간</dt>
								<dd>${board.b_temp7}</dd>
							</dl>
						</li>
						</c:if>		
						<c:if test="${board.b_temp8 ne ''}">
						<li>
							<dl>
								<dt>제작년도</dt>
								<dd>${board.b_temp8}</dd>
							</dl>
						</li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
		<div class="moviecont">${board.b_content}</div>


	</div>
	<!-- //보기 테이블 -->


	<!-- 버튼 -->
	<div class="board_button">
		<div class="fl pt10">
			<c:if test="${!empty next_b_num}">
			<a href="?proc_type=view&a_num=${config.a_num}&b_num=${next_b_num}" ><img src="/nanum/site/board/nninc_simple/img/listup_bt.gif" alt="다음글" /></a>
			</c:if>
			<c:if test="${!empty prev_b_num}">
			<a href="?proc_type=view&a_num=${config.a_num}&b_num=${prev_b_num}" ><img src="/nanum/site/board/nninc_simple/img/listdown_bt.gif" alt="이전글" /></a>
			</c:if>
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
				<span><a href="?proc_type=write&a_num=${config.a_num}" class="ndls_btn blue">글쓰기</a></span>

				<span><a href="?proc_type=write&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}" class="ndls_btn gray">수정</a></span>
			</c:if>

			<c:if test="${is_delete eq 'Y'}">
				<c:url var="gourl" value="/board/deleteOk.do">
					<c:param name="a_num" value="${config.a_num }" />
					<c:param name="b_num" value="${board.b_num }" />
					<c:param name="prepage" value="${nowPage }" />
					<c:param name="d_page" value="${path_info }" />
					<c:param name="board_token" value="${board_token }" />
				</c:url>
				<span><a href="${gourl}" class="ndls_btn gray">삭제</a></span>
			</c:if>

			<span><a href="${prepage}" class="ndls_btn gray">목록</a></span>
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