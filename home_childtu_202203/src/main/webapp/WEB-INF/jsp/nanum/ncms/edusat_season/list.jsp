<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_edusat_season.js"></script>
<script type="text/javascript">
function deleteOk(es_num){
	if(confirm("기수를 삭제하시겠습니까?")){
		location.href="deleteOk.do?es_num="+es_num+"&prepage=${nowPageEncode}";
	}
}
</script>
	<h1 class="tit"><span>기수</span> 리스트</h1>
	
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
	
			<form id="frm_sch" action="list.do" method="get">
			<!-- 검색 -->
			<div class="top_search_area mt10">
				<ul>
					<li class="tit"><label for="v_search"><h3 class="tit">기수검색 :</h3></label></li>
<c:if test="${empty BUILDER_DIR }">
					<li class="sel">
						<select id="sh_es_libcode" name="sh_es_libcode" title="도서관 선택" class="t_search" style="width:150px;">
							<option value="">도서관전체 </option>
<c:forEach items="${libList }" var="lib" varStatus="no">
							<option value="${lib.ct_idx }" ${param.sh_es_libcode eq lib.ct_idx ? 'selected="selected"' : '' }>${lib.ct_name }</option>
</c:forEach>
						</select>
					</li>
</c:if>
					<li class="sel">
						<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
							<option value="es_name" ${v_search == 'es_name' ? 'selected="selected"' : '' }>기수명 </option>
						</select>
					</li>
					<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="검색어 입력" name="v_keyword" id="v_keyword" type="text" value="${param.v_keyword}" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
					<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('./list.do');" /></li>
				</ul>
			</div>
			<!-- //검색 -->	
			</form>
	
	
		<form id= "frm_list" action="" method='post'>
			<div>
				<input type="hidden" name="status" />
				<input type="hidden" name="es_chk" />
				<input type="hidden" name="es_num" />
	
				<input type="hidden" id="chk_all" name="chk_all" />
				<input type="hidden" name="v_search" value="${v_search}" />
				<input type="hidden" name="v_keyword" value="${v_keyword}" />
				<input type="hidden" name="prepage" value="${nowPage}" />
			</div>
	
	
			<div class="list_count" style="height:20px">			
				<!-- 전체 <strong>14</strong>개 (페이지 <strong class="orange">1</strong>/2) -->
			</div>
	
	
			<fieldset>
				<legend>기수목록보기</legend>
				<table class="bbs_common bbs_default" summary="사이트의 기수를 관리합니다.">
				<caption>기수관리 서식</caption>
				<colgroup>
				<col width="50" />
				<col />
				<col />
				<col />
				<col width="50" />
				<col width="50" />
				</colgroup>
	
				<thead>
				<tr>
					<th scope="col">선택</th>
					<th scope="col">도서관</th>
					<th scope="col">기수명</th>
					<th scope="col">사용여부</th>
					<th scope="col">수정</th>
					<th scope="col">삭제</th>
				</tr>
				</thead>
	
				<tbody>
				


				<c:forEach items="${seasonList}" var="season" varStatus="no">
				<tr>
					<td class="center"><input type="checkbox" name="chk" value="${season.es_num}" title="해당 기수 선택" /></td>
					<td class="center">${season.libname}</td>
					<td class="center">${season.es_name}</td>
					<td class="center">${season.es_chk eq "Y" ? "<strong>사용</strong>" : "중지"}</td>
					<td class="center"><a href="write.do?es_num=${season.es_num}&prepage=${nowPageEncode}"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif"/></a></td>
					<td class="center"><a href="#del" onclick="deleteOk('${season.es_num}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
				</tr>
				</c:forEach>
				<c:if test="${fn:length(seasonList) == 0}">
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
					<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 기수삭제</a></p>
					</li>
					<li class="btn_ri">
						<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif" />&nbsp;선택한 기수를</p>
						<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 기수 사용여부 선택" class="t_search" style="width:70px;">
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
	
	</div>
	<!-- 내용들어가는곳 -->