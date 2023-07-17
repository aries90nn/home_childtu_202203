<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_board_delete.js"></script>

<script type="text/javascript">
function deleteOk(a_num, b_num){
	if(confirm("삭제시 데이타베이스에서 영구삭제됩니다.\n\n정말 삭제하시겠습니까?")){
		location.href="deleteOk.do?a_num="+a_num+"&b_num="+b_num+"&prepage=${nowPageEncode}";
	}
}

function restoreOk(a_num, b_num){
	if(confirm("복원하시겠습니까?")){
		location.href="restoreOk.do?a_num="+a_num+"&b_num="+b_num+"&prepage=${nowPageEncode}";
	}
}
</script>

	<h1 class="tit"><span>삭제 게시물</span> (휴지통)</h1>
	
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
	
			<form id="frm_sch" action="list.do" method="get">
			<!-- 검색 -->
			<div class="top_search_area mt10">
				<ul>
					<li class="tit"><label for="v_search"><h3 class="tit">게시판검색 :</h3></label></li>
					<li class="sel">
						<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
							<option value="b_name" ${v_search == 'b_name' ? 'selected="selected"' : '' }>작성자 </option>
							<option value="a_bbsname" ${v_search == 'a_bbsname' ? 'selected="selected"' : '' }>게시판명</option>
							<option value="b_subject" ${v_search == 'b_subject' ? 'selected="selected"' : '' }>제목</option>
						</select>	
					</li>
					<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="검색어 입력" name="v_keyword" id="v_keyword" type="text" value="${v_keyword}" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
					<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('list.do');" /></li>
				</ul>
			</div>
			<!-- //검색 -->
			</form>
	
			<div class="list_count" style="height:20px">
				전체 <strong>${recordcount}</strong>개 (페이지 <strong class="point_default">${v_page}</strong>/${totalpage})
			</div>
	
	
		<form id= "frm_list" action="" method='post'>
			<div>
				<input type="hidden" name="status" />
				<input type="hidden" id="chk_all" name="chk_all" />
				<input type="hidden" id="prepage" name="prepage" value="${nowPage }" />
			</div>
			
		<fieldset>
			<legend>삭제글관리 수정/삭제/보기</legend>
		<table class="bbs_common bbs_default" summary="삭제글을 관리합니다.">
			<caption>삭제글관리 서식</caption>
			<colgroup>
			<col width="40" />
			<col width="250"/>
			<col />
			<col width="100"/>
			<col width="100"/>
			<col width="100"/>
			<col width="100"/>
			<col width="50" />
			<col width="50" />
			</colgroup>

			<thead>
			<tr>
				<th scope="col">선택</th>
				<th scope="col">게시판명</th>
				<th scope="col">제목</th>
				<th scope="col">작성자</th>
				<th scope="col">작성일시</th>
				<th scope="col">삭제자</th>
				<th scope="col">삭제일시</th>
				<th scope="col">복원</th>
				<th scope="col">삭제</th>
			</tr>
			</thead>

			<tbody>
			
			<c:forEach items="${boardDeleteList}" var="boardDelete" varStatus="no">
				<c:set var="b_delete_date" value="" />
				<c:set var="b_delete_name" value="" />
				<c:set var="b_delete_ip" value="" />
				<c:if test="${boardDelete.b_keyword ne '' and boardDelete.b_keyword != null}">
					<c:set var="b_keyword_arr" value="${fn:split(boardDelete.b_keyword,'^')}" />
					<c:set var="b_delete_date" value="${b_keyword_arr[0]}" />
					<c:set var="b_delete_name" value="${b_keyword_arr[1]}" />
					<c:set var="b_delete_ip" value="${b_keyword_arr[2]}" />
				</c:if>
			<tr>
				<td class="center"><input type="checkbox" name="chk" value="${boardDelete.a_num}^${boardDelete.b_num}" title="해당 삭제글 선택" /></td>
				<td class="left">${boardDelete.a_bbsname}</td>
				<td class="left"><b><a href="javascript:page_go2('/${BUILDER_DIR }/ncms/board_config/board.do?proc_type=view&a_num=${boardDelete.a_num}&b_num=${boardDelete.b_num}');">${boardDelete.b_subject}<b/></a></td>
				<td class="center">${boardDelete.b_name}</td>
				<td class="left">${boardDelete.b_regdate}</td>
				<td class="center">${b_delete_name}<br/>(${b_delete_ip})</td>
				<td class="center">${b_delete_date}</td>
				<td class="center"><a href="javascript: restoreOk('${boardDelete.a_num}','${boardDelete.b_num}');"><img alt="복원" src="/nanum/ncms/img/common/view_icon.gif"/></a></td>
				<td class="center"><a href="javascript: deleteOk('${boardDelete.a_num}','${boardDelete.b_num}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
			</tr>
			</c:forEach>
			<c:if test="${fn:length(boardDeleteList) == 0}">
			<tr>
				<td scope="row" class="center" colspan="9">데이터가 없습니다.</td>
			</tr>
			</c:if>
			</tbody>
			</table>
		</fieldset>

	<!-- 하단버튼 -->
	<div id="contoll_area">
		<ul>
			<li class="btn_le">
				<p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p>
				<p><a href="javascript:restore();" class="btn_gr">선택 게시글복원</a></p>
				<p><a href="javascript:delete3();" class="btn_gr">선택 게시글삭제</a></p>
			</li>
		</ul>
	</div>
	<!-- //하단버튼 -->
	
	<!-- 페이징 -->
	<div class="paginate">
		${pagingtag }

	</div>
	<!-- //페이징 -->
	
		</form>
	
	</div>
	<!-- 내용들어가는곳 -->