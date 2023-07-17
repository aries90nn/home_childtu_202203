<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_cms.js"></script>
<script type="text/javascript">

//삭제
function deleteOk(ct_idx){
	
	if (confirm('정말 삭제하시겠습니까?(하위코드포함)'))	{
		loading_st(1);
		location.href="deleteOk.do?ct_idx="+ct_idx+"&prepage=${nowPageEncode}";
	}
}
</script>


<h1 class="tit"><span>홈페이지 메뉴</span> 리스트</h1>

		
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
		
		<h2 class="default">현재메뉴경로<span>: <a href="write.do" >홈페이지 메뉴관리홈</a> 

		<c:forEach items="${refMenuList}" var="refMenu" varStatus="ref_no">
			<c:choose>
				<c:when test="${ref_no.last}">
						&gt; <a href="write.do?ct_idx=${refMenu.ct_idx}" ><strong class="point_default">${refMenu.ct_name}</strong></a>
				</c:when>
				<c:otherwise>
						&gt; <a href="write.do?ct_idx=${refMenu.ct_idx}" >${refMenu.ct_name}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		</span></h2>
		
		
		<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk();">
		<div>
			<input type="hidden" name="ct_ref" value="${ct_ref}" />
			<input type="hidden" name="ct_depth" value="${ct_depth}" />
			<input type="hidden" name="ct_codeno_ref" value="${ct_codeno_ref}" />
			<input type="hidden" name="ct_group" value="${sessionScope.ss_ct_group}" />
			<input type="hidden" name="prepage" value="${nowPage}" />
		</div>

		<!-- 검색 -->
		<div class="top_search_area mt10">
			<ul>
				<li class="tit"><label for="ct_name_i"><h3 class="tit">메뉴등록 :</h3></label></li>
				<li class="search"><label for="ct_name_i">메뉴명을 입력하세요</label><input title="메뉴 입력" name="ct_name" id="ct_name_i" type="text" class="search_input autoInput" maxlength="50" /></li>
				<li class="sel">
					<select id="ct_chk_i" name="ct_chk" title="메뉴표시 여부선택" class="t_search" style="width:80px;">
					<option value="Y" selected="selected">메뉴표시</option>
					<option value="N">감추기</option>
					</select>	
				</li>
				<li class="btn"><input type="submit" value="등록" class="btn_bl_default" /></li>		
				<c:if test="${ct_depth != 1 and ct_depth<= max_depth_option}">
				<li class="btn"><input type="button" value="상위메뉴 이동" class="btn_gr_default" onclick="location.href='write.do?ct_idx=${ref_ct_idx_str}';" /></li>
				</c:if>
			</ul>
		</div>
		<!-- //검색 -->	

		</form>

		<div class="point_default pb5">* 메뉴표시여부 : 홈페이지 상단/좌측 영역에 해당메뉴를 표시할지의 여부</div>
		<div class="point_default pb5">* 메뉴명은 50자 이내로 입력해주세요.</div>

		
		<form id= "frm_list" action="" method='post'>
		<div>
			<input type="hidden" id="status" name="status" />
			<input type="hidden" id="ct_chk" name="ct_chk" />
			<input type="hidden" id="ct_idx" name="ct_idx" />
			<input type="hidden" id="ct_ref" name="ct_ref" value="${ct_ref}" />
			<input type="hidden" id="chk_all" name="chk_all" />
			<input type="hidden" name="prepage" value="${nowPage}" />
		</div>
		
		<fieldset>
			<legend>홈페이지 메뉴관리 작성/수정</legend>
			<table class="bbs_common bbs_default" summary="사이트에 사용하는 메뉴를 관리합니다.">
			<caption>홈페이지 메뉴관리 서식</caption>
			<colgroup>
			<col width="50" />
			<col />
			<col width="70" />
			<col width="80" />
			<col width="80" />
			<col width="50" />
			</colgroup>

			<thead>
			<tr>
				<th scope="col">선택</th>
				<th scope="col">메뉴</th>
				<th scope="col">페이지확인</th>
				<th scope="col">메뉴표시여부</th>
				<th scope="col">메뉴명수정</th>
				<th scope="col">삭제</th>
			</tr>
			</thead>

			<tbody>
		
		<c:forEach items="${menuList}" var="menu" varStatus="no">
			<tr>
				<td class="center"><input type="checkbox" name="chk" value="${menu.ct_idx}" title="해당 메뉴 선택" /></td>
				<td class="center">
					<input type="text" title="메뉴 입력" id="ct_name${no.count}" name="ct_name${no.count}" class="ta_input" value="${menu.ct_name}" maxlength="50" style="width:65%" />
				</td>
				<td class="center"><a href="${menu.ct_menu_url}" target="_blank"><img src="/nanum/ncms/img/common/view_icon.gif" alt="페이지확인" /></a></td>
				<td class="center">${menu.ct_chk eq "Y" ? "<strong>표시</strong>" : "감추기"}</td>
				<td class="center">
					<a href="javascript: frm_modify${no.count}(${menu.ct_idx},${menu.ct_ref});"><img src="/nanum/ncms/img/common/modify_icon.gif" alt="수정" /></a>
					
					<script type="text/javascript">
						function frm_modify${no.count}(ct_idx,ct_ref){
							var ct_idx, ct_ref;

							if (CheckSpaces(document.getElementById('frm_list').ct_name${no.count}, '메뉴명')) { return false; }

							else {

								loading_st(1);
								document.getElementById('frm_m').ct_name.value	= document.getElementById('frm_list').ct_name${no.count}.value;
								//document.getElementById('frm_m').ct_chk.value	= document.getElementById('frm_list').ct_chk${no.count}.value;

								document.getElementById('frm_m').ct_idx.value	= ct_idx;
								document.getElementById('frm_m').ct_ref.value	= ct_ref;
								document.getElementById('frm_m').action			= "writeOk.do";
								document.getElementById('frm_m').submit();
							}
						}
					</script>
				</td>
				<td class="center">
					<a href="#delete" onclick="deleteOk('${menu.ct_idx}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a>
				</td>
				
			</tr>
			</c:forEach>
			<c:if test="${fn:length(menuList) == 0}">
			<tr>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<!-- <td class="center"></td> -->
			</tr>
			</c:if>



			</tbody>
			</table>
		</fieldset>

		<!-- 하단버튼 -->
		<div id="contoll_area">
			<ul>
				<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 메뉴삭제</a></p>
				<p><a onclick="javascript:window.open('listMove.do?ct_idx=${ct_ref}','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
				<li class="btn_ri">
					<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 메뉴를</p>
					<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 메뉴표시여부 선택" class="t_search" style="width:70px;">
						<option value="Y" selected="selected">표시</option>
						<option value="N" >감추기</option>
					</select></p>
					<p>로</p>
					<p><a href="javascript:tot_levelchage();" class="btn_gr">변경</a></p>
				</li>
			</ul>						
		</div>
		<!-- //하단버튼 -->
</form>


<form id= "frm_m" method='post' action="">
<div>
	<input type="hidden" name="prepage" value="${nowPage }" />
	<input type="hidden" name="ct_name" />
	<input type="hidden" name="ct_chk" />
	<input type="hidden" name="ct_idx" />
	<input type="hidden" name="ct_ref" />
</div>
</form>

		</div>
		<!-- 내용들어가는곳 -->

