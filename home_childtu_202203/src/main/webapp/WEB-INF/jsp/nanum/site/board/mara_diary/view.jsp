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
		<thead>
			<tr>
				<th scope="col"><span class='category_color'>${b_cate_str[board.b_cate]}</span> ${board.b_subject}</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<dl class="info">
						<dt>작성일</dt>
						<dd>${board.b_regdate}</dd>
					</dl>
					<dl class="info">
						<dt>작성자</dt>
						<dd>${board.b_name}</dd>
					</dl>
					<dl class="info">
						<dt>조회</dt>
						<dd>${board.b_count}</dd>
					</dl>
				</td>
			</tr>
			<c:if test="${config.a_phone eq 'Y' and is_ad_cms eq 'Y'}">
			<tr>
				<td>
					<dl class="info">
						<dt>연락처</dt>
						<dd>${board.b_phone1} - ${board.b_phone2} - ${board.b_phone3}</dd>
					</dl>
				</td>
			</tr>
			</c:if>
			<c:if test="${config.a_email eq 'Y' and is_ad_cms eq 'Y'}">
			<tr>
				<td>
					<dl class="info">
						<dt>이메일</dt>
						<dd>${board.b_email}</dd>
					</dl>
				</td>
			</tr>
			</c:if>
			<c:if test="${config.a_home eq 'Y' and is_ad_cms eq 'Y'}">
			<tr>
				<td>
					<dl class="info">
						<dt>주소</dt>
						<dd>[${board.b_zip1}${board.b_zip2}] ${board.b_addr1} ${board.b_addr2} </dd>
					</dl>
				</td>
			</tr>
			</c:if>
			

			<c:if test="${config.a_upload eq 'Y' and (board.b_file1 ne '' or board.b_file2 ne '' or board.b_file3 ne '' or board.b_file4 ne '' or board.b_file5 ne  '')}">
			<tr>
				<td>
					<dl class="info">
						<dt>첨부파일</dt>
						<dd>
							<c:forEach var="t" begin="1" end="${config.a_upload_len }" step="1">
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
				</td>
			</tr>
			</c:if>

			<tr>
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
									<a href = "${tot_file}" rel="lightbox"><img src="${tot_file}"  alt="${file_name} 이미지를 클릭하시면 원본크기를 보실 수 있습니다." class="msimg"/></a><br/><br/>
								</c:if>
							</c:if>
						</c:forEach>
					</c:if>
					
					${board.b_content}

					<c:if test="${config.a_sns eq 'Y'}">
					<jsp:include page="../inc_view_sns.jsp" />
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
				<span><a href="?proc_type=write&a_num=${config.a_num}" class="cbtn cbtn_point">글쓰기</a></span>
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
				<span><a href="${gourl }" onclick="if(!confirm('정말 삭제하시겠습니까?')){return false;}" class="cbtn cbtn_g">삭제</a></span>
			</c:if>

			<span><a href="${prepage}" class="cbtn cbtn_g">목록</a></span>
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