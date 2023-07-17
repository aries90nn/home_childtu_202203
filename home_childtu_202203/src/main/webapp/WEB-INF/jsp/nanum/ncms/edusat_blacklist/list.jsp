<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_edusat.js"></script>
<script type="text/javascript">
function deleteOk(eb_num){
	if(confirm("경고를 삭제하시겠습니까?")){
		location.href="deleteOk.do?eb_num="+eb_num+"&prepage=${nowPageEncode}";
	}
}
</script>

<h1 class="tit">신청자경고 리스트</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

<form id="frm_sch" action="list.do" method="get">
<!-- 검색 -->
<div class="top_search_area mt10">
	<ul>
		<li class="tit"><label for="ct_name_i"><h3 class="tit">검색 :</h3></label></li>
		<li class="sel">
			<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
				<option value="es_name" ${param.v_search eq 'es_name' ? 'selected="selected"' : '' }>신청자명</option>
				<option value="es_id" ${param.v_search eq 'es_id' ? 'selected="selected"' : '' }>신청자아이디</option>
				<option value="edu_subject" ${param.v_search eq 'edu_subject' ? 'selected="selected"' : '' }>강좌명</option>
				<option value="eb_comment" ${param.v_search eq 'eb_comment' ? 'selected="selected"' : '' }>경고내용</option>
			</select>
		</li>
		<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="메뉴 입력" name="v_keyword" id="v_keyword" type="text" value="${param.v_keyword}" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
		<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('list.do');" /></li>
	</ul>
</div>
<!-- //검색 -->
</form>

<div class="list_count" style="height:20px;">
	전체 <strong>${recordcount }</strong>개 (페이지 <strong class="point_default">${v_page}</strong>/${totalpage})
</div>


<form id= "frm_list" action="" method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" name="edu_chk" />

		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" id="prepage" name="prepage" value="${nowPage}" />
	</div>


	<fieldset>
		<legend>경고관리 수정/삭제/보기</legend>
		<table class="bbs_common bbs_default" summary="사이트의 회원을 관리합니다.">
		<caption>경고관리 서식</caption>
		<colgroup>
		<col width="40" />
		<col width="150" />
		<col width="150" />
		<col width="300" />
		<col />
		<col width="100" />
		<col width="50" />
		</colgroup>

		<thead>
		<tr>
			<th scope="col">선택</th>
			<th scope="col">신청자명</th>
			<th scope="col">연락처</th>
			<th scope="col">신청강좌</th>
			<th scope="col">경고사유</th>
			<th scope="col">경고일자</th>
			<th scope="col">삭제</th>
		</tr>
		</thead>

		<tbody>
		
		<c:set var="view_url" value="/edusat/view.do" />
		<c:if test="${!empty BUILDER_DIR }">
			<c:set var="view_url" value="/${BUILDER_DIR }${view_url }" />
		</c:if>
		<c:forEach items="${edusatBlackList}" var="edusatBlackList" varStatus="no">
		<tr>
			<td class="center"><input type="checkbox" name="chk" value="${edusatBlackList.eb_num}" title="해당 경고 선택"  /></td>
			<td class="center">${edusatBlackList.es_name}<c:if test='${edusatBlackList.es_id ne ""}'>(${edusatBlackList.es_id})</c:if></td>
			<td class="center">${edusatBlackList.es_bphone1}-${edusatBlackList.es_bphone2}-${edusatBlackList.es_bphone3}</td>
			<td class="left" style="line-height:160%;">
				<span style="color:gray;">${edusatBlackList.edu_lib}<c:if test="${edusatBlackList.edu_gubun ne  '' and edusatBlackList.edu_gubun != null}">(${edusatBlackList.edu_gubun})</c:if></span>
				<br/>
				<a href="${view_url }?edu_idx=${edusatBlackList.edu_idx}" target="_blank"><strong>${edusatBlackList.edu_subject}</strong></a>
				<br/><span style="color:red;"><strong>접수기간 : </strong></span>${edusatBlackList.edu_resdate} ~ ${edusatBlackList.edu_reedate}
				<br/><span class="orange"><strong>강좌기간 : </strong></span> ${edusatBlackList.edu_sdate} ~ ${edusatBlackList.edu_edate}
			</td>
			<td class="center">${edusatBlackList.eb_comment}</td>
			<td class="center">${fn:substring(edusatBlackList.eb_wdate, 0, 10)}</td>
			<td class="center"><a href="javascript: deleteOk('${edusatBlackList.eb_num}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
		</tr>
		</c:forEach>
		<c:if test="${fn:length(edusatBlackList) == 0 }">
		<tr>
			<td class="center" colspan="7">데이타가 없습니다.</td>
		</tr>
		</c:if>
		
		
		</tbody>
		</table>
	</fieldset>

	<!-- 하단버튼 -->
	<div id="contoll_area">
		<ul>
			<li class="btn_le">
				<p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 경고삭제</a></p>
			</li>
			<li class="btn_ri"></li>
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