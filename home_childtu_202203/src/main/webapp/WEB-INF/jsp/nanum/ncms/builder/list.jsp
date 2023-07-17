<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_popup.js"></script>
<script type="text/javascript">
function deleteOk(bs_num){
	if(confirm("사이트를 삭제하면 하위메뉴, 게시판이 모두 삭제됩니다.\n\n사이트을 삭제하시겠습니까?")){
		location.href="./deleteOk.do?bs_num="+bs_num+"&prepage=${nowPageEncode}";
	}
}
</script>

<h1 class="tit"><span>사이트</span> 리스트</h1>
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
	
	<form id="frm_sch" action="list.do"  method="get">
	<!-- 검색 -->
	<div class="top_search_area mt10">
		<ul>
			<li class="tit"><label for="v_search"><h3 class="tit">사이트검색 :</h3></label></li>
			<li class="sel">
				<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:100px;">
					<option value="bs_sitename" ${v_search == 'bs_sitename' ? 'selected="selected"' : '' }>사이트명</option>
					<option value="bs_directory" ${v_search == 'bs_directory' ? 'selected="selected"' : '' }>웹디렉토리명</option>
				</select>	
			</li>
			<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="검색어 입력" name="v_keyword" id="v_keyword" type="text" value="${param.v_keyword}" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
			<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('./list.do');" /></li>
		</ul>
	</div>
	<!-- //검색 -->	
	</form>

	<div class="list_count" style="height:20px">
		전체 <strong>${recordcount}</strong>개 <!-- (페이지 <strong class="point_default">${v_page}</strong>/${totalpage}) -->
	</div>

<form id= "frm_list" action=""  method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" name="bs_num" />

		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" name="v_search" value="${v_search}" />
		<input type="hidden" name="v_keyword" value="${v_keyword}" />
		<input type="hidden" name="prepage" value="${nowPage}" />
	</div>

	<fieldset>
		<legend>사이트관리 수정/삭제/보기</legend>
		<table class="bbs_common bbs_default" summary="사이트을 관리합니다.">
		<caption>사이트관리 서식</caption>
		<colgroup>
		<col width="40" />
		<col />
		<col width="10%" />
		<col width="10%" />
		<col width="10%" />
		<col width="8%" />
		<col width="50" />
		<col width="50" />
		<col width="50" />
		</colgroup>

		<thead>
		<tr>
			<th scope="col">선택</th>
			<th scope="col">사이트명</th>
			<th scope="col">웹디렉토리명</th>
			<th scope="col">메인</th>
			<th scope="col">스킨</th>
			<th scope="col">사용여부</th>
			<th scope="col">관리</th>
			<th scope="col">수정</th>
			<th scope="col">삭제</th>
		</tr>
		</thead>

		<tbody>

		
		<c:forEach items="${builderList}" var="builder" varStatus="no">
		<c:set var="bs_chk_str" value='' />
		<c:set var="bs_chk_str" value='${builder.bs_chk eq "Y" ? "<strong>사용</strong>":builder.bs_chk}' />
		<c:set var="bs_chk_str" value='${bs_chk_str eq "N" ? "중지":bs_chk_str}' />
		
		<tr>
			<td class="center"><input type="checkbox" name="chk" value="${builder.bs_num}" title="해당 사이트 선택"  /></td>
			<td class="left"><a href="/${builder.bs_directory }/" target="_blank"><strong>${builder.bs_sitename}</strong></a></td>
			<td class="center">${builder.bs_directory}</td>
			<td class="center">${builder.bs_main}</td>
			<td class="center">${builder.bs_skin}</td>
			<td class="center">${bs_chk_str}</td>
			<td class="center"><a href="/${builder.bs_directory}/ncms/cms/write.do" target="_blank" onclick="if(confirm('해당사이트의 관리자페이지로 접속하시겠습니까?')){return true;}else{return false;}"><img src="/nanum/ncms/img/common/setup_icon.gif" /></a></td>
			<td class="center"><a href="write.do?bs_num=${builder.bs_num}&prepage=${nowPageEncode}"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></td>
			<td class="center"><a href="#del" onclick="deleteOk('${builder.bs_num}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
		</tr>
		</c:forEach>
		<c:if test="${fn:length(builderList) == 0}">
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
				<p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 사이트삭제</a></p><p><a onclick="javascript:window.open('listMove.do','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p>
			</li>
			<li class="btn_ri">
				<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 사이트을</p>
				<p><select id="tot_w_chk" name="tot_w_chk" title="선택한 사이트 사용여부 선택" class="t_search" style="width:80px;">
					<!-- <option value="T" >사용(모듬사이트)</option> -->
					<option value="Y">사용</option>
					<!-- <option value="L" selected="selected">사용(레이어사이트)</option>-->
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
	
	<!-- //페이징 -->

</div>