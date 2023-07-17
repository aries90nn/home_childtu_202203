<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_board_config.js"></script>


<h1 class="tit"><span>게시판</span> 리스트</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

		<form id="frm_sch" action="list.do" method="get">
		<!-- 검색 -->
		<div class="top_search_area mt10">
			<ul>
				<li class="tit"><label for="v_search"><h3 class="tit">게시판검색 :</h3></label></li>
				<li class="sel">
					<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
						<option value="a_bbsname" ${v_search == 'a_bbsname' ? 'selected="selected"' : '' }>게시판명 </option>
						<option value="a_tablename" ${v_search == 'a_tablename' ? 'selected="selected"' : '' }>테이블명</option>
						<option value="a_num" ${v_search == 'a_num' ? 'selected="selected"' : '' }>일련번호</option>
					</select>	
				</li>
				<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="검색어 입력" name="v_keyword" id="v_keyword" type="text" value="${v_keyword}" class="search_input autoInput" maxlength="30" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
				<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('list.do');" /></li>
			</ul>
		</div>
		<!-- //검색 -->	
		</form>


	<form id= "frm_list" action="" method='post'>
		<div>
			<input type="hidden" name="status" />
			<input type="hidden" id="chk_all" name="chk_all" />
		</div>

		<div class="list_count" style="height:20px">
			전체 <strong>${fn:length(boardList)}</strong>개
		</div>

	<fieldset>
		<legend>게시판목록보기</legend>
		<table class="bbs_common bbs_default" summary="사이트의 팝업존를 관리합니다.">
		<caption>게시판관리 서식</caption>
		<colgroup>
		<col />
		<col width="65" />
		<col width="40" />
		<col width="70" />
		<col width="45" />
		<col width="45" />
		</colgroup>

		<thead>
		<tr>
			<th scope="col">게시판명</th>
			<th scope="col">테이블명</th>
			<th scope="col">파일</th>
			<th scope="col">전체/오늘</th>
			<th scope="col">수정</th>
			<th scope="col">분류</th>
		</tr>
		</thead>

		<tbody>
		
		<c:forEach items="${boardList}" var="board" varStatus="no">
		<tr>
			<td class="left"><a href="javascript:void(window.open('./board.do?a_num=${board.a_num}'));" >${board.a_bbsname}</a></td>
			<td class="center eng">${board.a_tablename}<br/>(${board.a_num})</td>
			<td class="center">
					<c:if test="${board.a_upload eq 'Y'}">
						<img src='/nanum/ncms/img/file_ic.gif' width='16' height='16' alt='파일업로드 기능 사용' title='파일업로드 기능 사용' />
					</c:if>
			</td>
			<td class="center eng">${board.tot_count}/${board.today_cnt}</td>
			<td class="center"><a href="write.do?a_num=${board.a_num}&prepage=${nowPageEncode}"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a></td>
			<td class="center">
				<c:if test="${board.a_cate eq 'Y'}">
					<c:set var="code_url" value="/ncms/board_code/write.do?a_num=${board.a_num}" />
					<c:if test="${!empty BUILDER_DIR}">
						<c:set var="code_url" value="/${BUILDER_DIR }/ncms/board_code/write.do?a_num=${board.a_num}" />
					</c:if>
				<a href="${code_url }"><img alt="분류보기" src="/nanum/ncms/img/common/view_icon.gif" /></a>
				</c:if>
			</td>
		</tr>
		</c:forEach>
		<c:if test="${fn:length(boardList) == 0}">
		<tr>
			<td scope="row" class="center"></td>
			<td scope="row" class="center"></td>
			<td scope="row" class="center"></td>
			<td scope="row" class="center"></td>
			<td scope="row" class="center"></td>
			<td scope="row" class="center"></td>
			<td scope="row" class="center"></td>
			<td scope="row" class="center"></td>
		</tr>
		</c:if>


		</tbody>
		</table>
	</fieldset>

	<!-- 하단버튼 -->
<div id="contoll_area">
	<ul>
		<li class="btn_le"><p><a onclick="javascript:window.open('listMove.do','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
	</ul>						
</div>
<!-- //하단버튼 -->

	</form>

</div>
<!-- 내용들어가는곳 -->