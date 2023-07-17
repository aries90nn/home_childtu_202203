<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_poll.js"></script>
<script type="text/javascript">
function deleteOk(po_idx){
	if(confirm("설문조사를 삭제하시겠습니까?")){
		location.href="./deleteOk.do?po_idx="+po_idx+"&prepage=${nowPageEncode}";
	}
	
}
</script>

<h1 class="tit"><span>설문조사</span> 리스트</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

<form id="frm_sch" action="list.do" method="get">
<!-- 검색 -->
<div class="top_search_area mt10">
	<ul>
		<li class="tit"><label for="ct_name_i"><h3 class="tit">설문조사검색 :</h3></label></li>
		<li class="sel">
			<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
			<option value="po_subject"  ${"po_subject" == v_search ? 'selected="selected"' : '' }>제목</option>
		</select>
	</li>
	<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="메뉴 입력" name="v_keyword" id="v_keyword" type="text" value="${v_keyword}" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
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
		<input type="hidden" name="po_idx" />
		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" name="v_search" value="${v_search}" />
		<input type="hidden" name="v_keyword" value="${v_keyword}" />
		<input type="hidden" name="prepage" value="${nowPage}" />
	</div>


	<fieldset>
		<legend>설문조사 수정/삭제/보기</legend>
		<table class="bbs_common bbs_default" summary="사이트의 회원을 관리합니다.">
		<caption>설문조사 서식</caption>
	<colgroup>
	<col width="50" />
	<!-- <col width="50" /> -->
	<col />
	<col width="18%" />
	<col width="70" />
	<col width="50" />
	<col width="50" />
	<col width="60" />
	<col width="60" />
	</colgroup>

	<thead>
	<tr>
		<th scope="col">선택</th>
		<th scope="col">제목</th>
		<th scope="col">설문기간</th>
		<th scope="col">사용여부</th>
		<th scope="col">수정</th>
		<th scope="col">삭제</th>
		<th scope="col">설문문항</th>
		<th scope="col">엑셀출력</th>
	</tr>
	</thead>

	<tbody>
	
	<c:forEach items="${pollList}" var="poll" varStatus="no">
		<c:set var="user_list_url" value="/poll/list.do" />
		<c:set var="question_url" value="/ncms/poll_question/write.do?po_pk=${poll.po_pk}&prepage=${nowPageEncode}" />
		<c:if test="${!empty poll.site_dir }">
			<c:set var="user_list_url" value="/${poll.site_dir }/poll/list.do" />
			<c:set var="question_url" value="/${poll.site_dir }${question_url }" />
		</c:if>
	<tr>
		<td class="center"><input type="checkbox" name="chk" value="${poll.po_idx}" title="해당 회원 선택" /></td>
		<td class="left"><a href="#poll" onclick="window.open('${user_list_url}');"><strong>${poll.po_subject}</strong></a></td>
		<td class="center eng"><span class="point">${poll.po_sdate} ~ ${poll.po_edate}</span></td>
		<td class="center">${poll.po_chk == "Y" ? "<strong>사용</strong>" : "중지"}</td>
		<td class="center"><a href="write.do?po_idx=${poll.po_idx}&prepage=${nowPageEncode}"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a></td>
		<td class="center"><a href="#del" onclick="deleteOk('${poll.po_idx}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
		<td class="center"><a href="${question_url }"><img alt="설정" src="/nanum/ncms/img/common/setup_icon.gif" /></a></td>
		<td class="center"><a href="./resultExcel.do?po_pk=${poll.po_pk }"><img alt="엑셀출력" src="/nanum/ncms/img/common/view_icon.gif" /></a></td>
	</tr>
	</c:forEach>
	<c:if test="${fn:length(pollList) == 0}">
		<tr>
			<td scope="row" class="center" colspan="7">데이터가 없습니다.</td>
		</tr>
	</c:if>

	</tbody>
	</table>
</fieldset>

<!-- 하단버튼 -->
<div id="contoll_area">
	<ul>
		<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 설문삭제</a></p></li>
		<li class="btn_ri">
			<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 설문조사를</p>
			<p><select id="tot_m_level" name="tot_level_chk" title="선택한 설문조사 사용여부 선택" class="t_search" style="width:70px;">
				<option value="Y" selected="selected">사용</option>
				<option value="N" >중지</option>
			</select></p>
			<p>(으)로</p>
			<p><a href="javascript:tot_levelchage();" class="btn_gr">변경</a></p>
		</li>
	</ul>
</div>
<!-- //하단버튼 -->


</form>


<!-- 페이징 -->
<div class="paginate">
	${pagingtag }
</div>
<!-- //페이징 -->

</div>
<!-- 내용들어가는곳 -->