<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_popup.js"></script>
<script type="text/javascript">
function deleteOk(idx){
	if(confirm("팝업을 삭제하시겠습니까?")){
		location.href="deleteOk.do?idx="+idx+"&prepage=${nowPageEncode}";
	}
}
</script>

<h1 class="tit"><span>팝업</span> 리스트</h1>
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
	
	<form id="frm_sch" action="list.do"  method="get">
	<!-- 검색 -->
	<div class="top_search_area mt10">
		<ul>
			<li class="tit"><label for="v_search"><h3 class="tit">팝업검색 :</h3></label></li>
			<li class="sel">
				<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
					<option value="subject" ${v_search == 'subject' ? 'selected="selected"' : '' }>제목 </option>
					<option value="content" ${v_search == 'content' ? 'selected="selected"' : '' }>내용</option>
				</select>	
			</li>
			<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="검색어 입력" name="v_keyword" id="v_keyword" type="text" value="${param.v_keyword}" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
			<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('./list.do');" /></li>
		</ul>
	</div>
	<!-- //검색 -->	
	</form>

	<div class="list_count" style="height:20px">
		전체 <strong>${recordcount}</strong>개 (페이지 <strong class="point_default">${v_page}</strong>/${totalpage})
	</div>

<form id= "frm_list" action=""  method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" name="idx" />

		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" name="v_search" value="${v_search}" />
		<input type="hidden" name="v_keyword" value="${v_keyword}" />
		<input type="hidden" name="prepage" value="${nowPage}" />
	</div>

	<fieldset>
		<legend>팝업관리 수정/삭제/보기</legend>
		<table class="bbs_common bbs_default" summary="팝업을 관리합니다.">
		<caption>팝업관리 서식</caption>
		<colgroup>
		<col width="40" />
		<col />
		<col width="28%" />
		<col width="8%" />
		<col width="50" />
		<col width="50" />
		</colgroup>

		<thead>
		<tr>
			<th scope="col">선택</th>
			<th scope="col">제목</th>
			<th scope="col">사용기간</th>
			<th scope="col">사용여부</th>
			<th scope="col">수정</th>
			<th scope="col">삭제</th>
		</tr>
		</thead>

		<tbody>

		
		<c:forEach items="${popupList}" var="popupList" varStatus="no">
		<c:set var="w_chk_str" value='' />
		<c:set var="w_chk_str" value='${popupList.w_chk eq "Y" ? "<strong>사용</strong>":popupList.w_chk}' />
		<c:set var="w_chk_str" value='${w_chk_str eq "L" ? "<strong>사용(레이어팝업)</strong>":w_chk_str}' />
		<c:set var="w_chk_str" value='${w_chk_str eq "N" ? "중지":w_chk_str}' />
		
		<tr>
			<td class="center"><input type="checkbox" name="chk" value="${popupList.idx}" title="해당 팝업 선택"  /></td>
			<td class="left"><a href="#pop" onclick="win_popup(${popupList.idx},'${popupList.scrollbars}','${popupList.toolbar}','${popupList.menubar}','${popupList.locations}','${popupList.w_width}','${popupList.w_height}');"><strong>${popupList.subject}</strong></a></td>
			<td class="center eng"><span class="point">${popupList.sdate}  ~ 
				<c:choose>
					<c:when test="${popupList.unlimited eq 'Y'}">
						무제한
					</c:when>
					<c:otherwise>
						${popupList.edate}
					</c:otherwise>
				</c:choose>
			</span></td>
			<td class="center">${w_chk_str}</td>
			<td class="center"><a href="write.do?idx=${popupList.idx}&prepage=${nowPageEncode}"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></td>
			<td class="center"><a href="#del" onclick="deleteOk('${popupList.idx}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
		</tr>
		</c:forEach>
		<c:if test="${fn:length(popupList) == 0}">
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
			<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 팝업삭제</a></p></li>
			<li class="btn_ri">
				<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 팝업을</p>
				<p><select id="tot_w_chk" name="tot_w_chk" title="선택한 팝업 사용여부 선택" class="t_search" style="width:120px;">
					<!-- <option value="T" >사용(모듬팝업)</option> -->
					<option value="Y">사용</option>
					<option value="L" selected="selected">사용(레이어팝업)</option>
					<option value="N">중지</option>
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