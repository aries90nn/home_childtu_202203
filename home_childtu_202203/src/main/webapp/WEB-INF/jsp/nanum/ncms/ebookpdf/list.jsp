<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_ebookpdf.js"></script>
<script type="text/javascript">
function viewer(eb_idx){
	var scr_width = screen.availWidth;
	var scr_height = screen.availHeight;
	window.open('/ebookpdf/viewer/index.do?eb_idx='+eb_idx,'ebook_pdf','width='+scr_width+',height='+scr_height+',scrollbars=yes');
}

</script>

<h1 class="tit"><span>소식지</span> 리스트</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

	<form id="frm_sch" action="list.do" method="get">
	<!-- 검색 -->
	<div class="top_search_area mt10">
		<ul>
			<li class="tit"><label for="v_search"><h3 class="tit">소식지 검색 :</h3></label></li>
			
			<li class="sel">
				<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
					<option value="eb_subject" ${v_search == 'eb_subject' ? 'selected="selected"' : '' }>제목 </option>
				</select>	
			</li>
			<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="검색어 입력" name="v_keyword" id="v_keyword" type="text" value="${v_keyword}" class="search_input autoInput" /><!-- <input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /> --></li>
			<li class="btn"><input type="submit" value="검색" class="btn_bl_default" /></li>
			<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('list.do');" /></li>
		</ul>
	</div>
	<!-- //검색 -->	
	</form>

	<div class="list_count mt15">
		전체 <strong>${recordcount }</strong>개 (페이지 <strong class="point_default">${v_page }</strong>/${totalpage })
	</div>
	

	<form id= "frm_list" action="" method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" id="prepage" name="prepage" value="${nowPage }" />
	</div>

	<fieldset>
	<legend>단행본관리 수정/삭제/보기</legend>
	<table class="bbs_common bbs_default" summary="단행본을 관리합니다.">
	<caption>단행본관리 서식</caption>
	<colgroup>
	<col width="50" />
	<col width="100" />
	<col />
	<col width="70" />
	<col width="60" />
	<col width="60" />
	</colgroup>

	<thead>
	<tr>
		<th scope="col">선택</th>
		<th scope="col" colspan="2">제목</th>
		<th scope="col">사용여부</th>
		<th scope="col">수정</th>
		<th scope="col">삭제</th>
	</tr>
	</thead>

	<tbody>
		<c:forEach items="${ebookList}" var="ebook" varStatus="no">
		<tr>
			<td class="center"><input type="checkbox" name="chk" value="${ebook.eb_idx}" title="해당 단행본 선택" /></td>
			<td class="center"><img src="/data/ebookpdf/${ebook.eb_img }" onerror="this.src='/nanum/site/board/nninc_photo_mobile/img/no.gif';" style="width:80px;" /></td>
			<!-- <td class="left"><a href="javascript:viewer('${ebook.eb_idx}');"><strong>${ebook.eb_subject}</strong></a></td>-->
			<td class="left"><a href="/main/ebookpdf/viewer/index.do?eb_idx=${ebook.eb_idx }" target="_blank"><strong>${ebook.eb_subject}</strong></a></td>
			<td class="center">${ebook.eb_chk == "Y" ? "<strong>사용</strong>" : "중지"}</td>
			<td class="center"><a href="write.do?eb_idx=${ebook.eb_idx}&prepage=${nowPageEncode}"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif"/></a></td>
			<td class="center"><a href="javascript:;" onclick="javascript:deleteOk('${ebook.eb_idx}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif"/></a></td>
		</tr>
		</c:forEach>
		<c:if test="${fn:length(ebookList) == 0}">
			<tr>
				<td class="center" colspan="6"></td>
			</tr>
		</c:if>

	</tbody>
	</table>
	</fieldset>

	<!-- 하단버튼 -->
	<div id="contoll_area">
		<ul>
			<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 삭제</a></p>
			<p><a onclick="javascript:window.open('listMove.do?${querystring }','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
			<li class="btn_ri">
				<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif" />&nbsp;선택한 단행본을</p>
				<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 단행본 사용여부 선택" class="t_search" style="width:70px;">
					<option value="Y" selected="selected">사용</option>
					<option value="N" >중지</option>
				</select></p>
				<p>(으)로</p>
				<p><a href="javascript:tot_levelchage();" class="btn_gr">변경</a></p>
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