<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>


<script type="text/javascript" src="/nanum/ncms/common/js/ncms_edusat_code.js"></script>
<script>
function deleteOk(ct_idx){
	if(confirm("코드를 삭제하시겠습니까?")){
		location.href="./deleteOk.do?ct_idx="+ct_idx+"&prepage=${nowPageEncode}";
	}
}
</script>

<h1 class="tit"><span>프로그램분류</span>관리</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<!-- 등록폼-->
	<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk();">
	<div>
		<input type="hidden" name="ct_ref" value="${ct_ref}" />
		<input type="hidden" name="ct_depth" value="${ct_depth}" />
		<input type="hidden" name="ct_codeno_ref" value="${ct_codeno_ref}" />
		<input type="hidden" name="ct_icon" id="ct_icon_top" value=""/>
		<input type="hidden" id="prepage" name="prepage" value="${nowPage }" />
	</div>
	<!-- 검색 -->
	<div class="top_search_area mt10">
		<ul>
			<li class="tit"><label for="ct_name_i"><h3 class="tit">분류등록 :</h3></label></li>
			<li class="search"><label for="ct_name_i">코드명을 입력하세요</label><input title="메뉴 입력" name="ct_name" id="ct_name_i" type="text" value="" class="search_input autoInput" /></li>				
			<li class="sel">
				<select id="ct_chk_i" name="ct_chk" title="메뉴 사용여부 선택" class="t_search" style="width:80px;">
					<option value="Y" selected="selected">사용</option>
					<option value="N" >중지</option>
				</select>	
			</li>
			<li class="btn"><input type="submit" value="등록" class="btn_bl_default" /></li>
			<c:if test="${ct_depth != 1 and ct_depth<= max_depth_option}">
			</c:if>
		</ul>
	</div>
	<!-- //검색 -->
	</form>
	<!-- //등록폼 -->
		
	<form id= "frm_list" action="" method='post'>
	<div>
		<input type="hidden" id="status" name="status" />
		<input type="hidden" id="ct_chk" name="ct_chk" />
		<input type="hidden" id="ct_idx" name="ct_idx" />
		<input type="hidden" id="ct_ref" name="ct_ref" value="${ct_ref}" />

		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" id="prepage" name="prepage" value="${nowPage }" />
	</div>
			
		<fieldset>
			<legend>코드관리 작성/수정</legend>
			<table class="bbs_common bbs_default" summary="사이트에 사용하는 코드를 관리합니다.">
			<caption>코드관리 서식</caption>
			<colgroup>
			<col width="40" />
			<col />
			<col width="70" />
			<col width="70" />
<c:if test="${param.ct_idx eq '1'}">
			<col width="70" />
</c:if>
			<col width="70" />
			<col width="50" />
			<col width="50" />
			</colgroup>

			<thead>
			<tr>
				<th scope="col">선택</th>
				<th scope="col">코드</th>
				<th scope="col">고유키</th>
				<th scope="col">코드번호</th>
<c:if test="${param.ct_idx eq '1'}">
				<th scope="col">회원그룹코드</th>
</c:if>
				<th scope="col">사용여부</th>
				<th scope="col">수정</th>
				<th scope="col">삭제</th>
			</tr>
			</thead>

			<tbody>
			<c:forEach items="${codeList}" var="codeList" varStatus="no">
			<tr>
				<td class="center"><input type="checkbox" name="chk" value="${codeList.ct_idx}" title="해당 팝업존 선택" /></td>
				<td>
					<input type="text" title="코드 입력" id="ct_name${no.count}" name="ct_name${no.count}" class="ta_input w9 editmode" value="${codeList.ct_name}" maxlength="100" />
				</td>
				<td class="center">
					${codeList.ct_idx}
				</td>
				<td>${codeList.ct_codeno}</td>
				
				<c:if test="${param.ct_idx eq '1'}">
				<td>
					<input type="text" title="코드 입력" id="ct_g_num${no.count}" name="ct_g_num${no.count}" class="ta_input w9 editmode" value="${codeList.ct_g_num}" maxlength="100" />
				</td>
				</c:if>
				
				<td class="center">${codeList.ct_chk == "Y" ? "<strong>사용</strong>" : "중지"}</td>
				<td class="center"><a href="#modify" onclick="frm_modify${no.count}(${codeList.ct_idx},${codeList.ct_ref});"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a>
				
				<script type="text/javascript">
				function frm_modify${no.count}(ct_idx,ct_ref){
						var ct_idx, ct_ref;

						if (CheckSpaces(document.getElementById('frm_list').ct_name${no.count}, '메뉴명')) { return false; }
						else {
							loading_st(1);
							document.getElementById('frm_m').ct_name.value	= document.getElementById('frm_list').ct_name${no.count}.value;
							document.getElementById('frm_m').ct_idx.value	= ct_idx;
							document.getElementById('frm_m').ct_ref.value	= ct_ref;
				<c:if test="${param.ct_idx eq '1'}">
							document.getElementById('frm_m').ct_g_num.value	= document.getElementById('frm_list').ct_g_num${no.count}.value;
				</c:if>
							document.getElementById('frm_m').action			= "./writeOk.do";
							document.getElementById('frm_m').submit();
						}
					}
				</script>
				</td>
				<td class="center"><a href="#delete" onclick="deleteOk('${codeList.ct_idx}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
				
			</tr>
			</c:forEach>
			</tbody>
			</table>
		</fieldset>

		<!-- 하단버튼 -->
		<div id="contoll_area">
			<ul>
				<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 분류삭제</a></p><!--p><a href="javascript:moveAll();" class="btn_gr">순서일괄수정</a></p-->
				<p><a onclick="javascript:window.open('./listMove.do?ct_idx=${ct_ref}&prepage=${nowPageEncode }','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
				<li class="btn_ri">
					<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 메뉴를</p>
					<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 메뉴 사용여부 선택" class="t_search" style="width:70px;">
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


<form id= "frm_m" method='post' action="">
<div>
	<input type="hidden" name="ct_name" />
	<input type="hidden" name="ct_chk" />
	<input type="hidden" name="ct_idx" />
	<input type="hidden" name="ct_ref" />
	<input type="hidden" name="ct_g_num" />
	<input type="hidden" name="prepage" value="${nowPage }" />
</div>
</form>
