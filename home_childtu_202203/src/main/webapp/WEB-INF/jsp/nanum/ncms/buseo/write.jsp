<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<c:set var="builder_url" value="" />
<c:if test="${!empty BUILDER_DIR}">
	<c:set var="builder_url" value="/${BUILDER_DIR}" />
</c:if>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_banner.js"></script>
<link rel="stylesheet" href="/nanum/ncms/common/treeview/jquery.treeview.css" />
<script src="/nanum/ncms/common/treeview/jquery.treeview.js" type="text/javascript"></script>
<script>
$(document).ready(function(){ 
	$.ajax({
		type: "POST",
		url: "${builder_url}/ncms/buseo/listAjax.do", 
		data: "sel_type=s",
		dataType:"html",
		async:false,
		success: function(msg){	
			$("#jsBuseo").html(msg);
		}
	});
});
//삭제
function d_buseo_chk(ct_idx){
	
	if (confirm('정말 삭제하시겠습니까?'))	{
		
		document.getElementById('frm_list').action = "${builder_url}/ncms/buseo/deleteOk.do";
		document.getElementById('frm_list').ct_idx.value=ct_idx;
		document.getElementById('frm_list').submit();

	}else{
		
		return ;
	
	}

}

</script>
	<h1 class="tit"><span>부서</span>관리</h1>
<style>
._bottom{border-bottom:1px solid #888 !important;}
</style>
<!-- 내용들어가는곳 -->
<div id="contents_area">
	<table style="width:100%;">
		<tr>
			<td width="180" valign="top">
				<div id="jsBuseo"></div>	
			</td>
			<td valign="top">
		
		
		
<!-- 부서등록 -->


<h2 class="tit">현재부서경로<span>: <a href="write.do">부서관리홈</a> 

<c:if test="${refList != null and fn:length(refList) > 0}">
	<c:forEach items="${refList}" var="ref" varStatus="no">
		<c:if test="${fn:length(refList) == no.count}">
				&gt; <a href="${builder_url}/ncms/buseo/write.do?ct_idx=${ref.ct_idx}"><strong class="point_default">${ref.ct_name}</strong></a> 
		</c:if>
		<c:if test="${fn:length(refList) != no.count}">
			&gt; <a href="${builder_url}/ncms/buseo/write.do?ct_idx=${ref.ct_idx}">${ref.ct_name}</a> 
		</c:if>
	</c:forEach>
</c:if>
	</span></h2>
		
<form id="frm" method="post" action="${builder_url}/ncms/buseo/writeOk.do" onsubmit="return w_chk();">
<div>
	<input type="hidden" name="ct_ref" value="${ct_ref}" />
	<input type="hidden" name="ct_depth" value="${ct_depth}" />
	<input type="hidden" name="ct_codeno_ref" value="${ct_codeno_ref}" />
	<input type="hidden" name="ct_icon" id="ct_icon_top" value=""/>
</div>

	<!-- 검색 -->
	<div class="top_search_area mt10">
		<ul>
			<li class="tit"><label for="ct_name_i"><h3 class="tit">부서등록 :</h3></label></li>
			<li class="search"><label for="ct_name_i">부서명을 입력하세요</label><input title="부서 입력" name="ct_name" id="ct_name_i" type="text" value="" class="search_input autoInput" /></li>

			<li class="sel">
				<select id="ct_chk_i" name="ct_chk" title="부서 사용여부 선택" class="t_search" style="width:80px;">
					<option value="Y" selected="selected">사용</option>
					<option value="N" >중지</option>
				</select>	
			</li>
			<li class="btn"><input type="submit" value="등록" class="btn_bl_default" /></li>
			<c:if test="${ct_depth ne '1'  and ct_depth+0 < max_depth_option+0}">
			<li class="btn"><input type="button" value="상위부서 이동" class="btn_gr_default" onclick="location.href='write.do?ct_idx=${ref_ct_idx_str}';" /></li>
			</c:if>
		</ul>
	</div>
	<!-- //검색 -->

</form>

			
<form id= "frm_list" action="" method='post'>
<div>
	<input type="hidden" id="status" name="status" />
	<input type="hidden" id="ct_chk" name="ct_chk" />
	<input type="hidden" id="ct_idx" name="ct_idx" />
	<input type="hidden" id="ct_ref" name="ct_ref" value="${ct_ref}" />

	<input type="hidden" id="chk_all" name="chk_all" />
</div>
			
	<fieldset>
		<legend>조직도관리 작성/수정</legend>
		<table class="bbs_common bbs_default" summary="사이트에 사용하는 조직도를 관리합니다.">
		<caption>조직도관리 서식</caption>
		<colgroup>
			<col width="6%" />
			<col />
			<c:if test="${ct_depth+0 <= 2}">
			<!-- col width="40%" / -->
			</c:if>
			
			<c:if test="${ct_depth+0 < max_depth_option+0}">
			<!-- col width="6%" / -->
			</c:if>
			<col width="6%" />
			<col width="8%" />
			<col width="6%" />
			<col width="6%" />
		</colgroup>

		<thead>
		<tr>
			<th scope="col">선택</th>
			<th scope="col">부서</th>
			<c:if test="${ct_depth+0 <= 2}">
			<!-- th scope="col">소개글</th -->
			</c:if>

			<c:if test="${ct_depth+0 < max_depth_option+0}">
			<th scope="col">하위부서</th>
			</c:if>

			<th scope="col">사용여부</th>
			<th scope="col">수정</th>
			<th scope="col">삭제</th>
		</tr>
		</thead>

	<tbody>
		<c:forEach items="${buseoList}" var="buseo" varStatus="no">
		<c:set var="zz" value="${no.count}" />
			<tr>
				<td class="center"><input type="checkbox" name="chk" value="${buseo.ct_idx}" title="해당 부서 선택" /></td>
				<td style="padding:5px 5px">
					<input type="text" title="부서 입력" id="ct_name${zz}" name="ct_name${zz}" class="ta_input w9 editmode" onfocus="focus_on1_default(this);" onblur="focus_off1(this);" value="${buseo.ct_name}" maxlength="100" />
				</td>
				
				<c:if test="${ct_depth+0 <= 2}">
				<!-- td class="center" style="padding:10px 10px">
					<textarea id="ct_memo${zz}" name="ct_memo${zz}" class="ta_textarea w99" style="height:70px">${buseo.ct_memo}</textarea>
				</td -->
				</c:if>
				
				<c:if test="${ct_depth+0 < max_depth_option+0}">
				<td class="center"><a href="javascript:;" onclick="page_go1('${builder_url}/ncms/buseo/write.do?ct_idx=${buseo.ct_idx}');"><img alt="하위부서보기" src="/nanum/ncms/img/common/view_icon.gif" /></a></td>
				</c:if>
				<td class="center">${buseo.ct_chk == "Y" ? "<strong>사용</strong>" : "중지"}</td>
				<td class="center"><a href="javascript:;" onclick="frm_modify${zz}(${buseo.ct_idx},${buseo.ct_ref});"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a>

					<script type="text/javascript">
						function frm_modify${zz}(ct_idx,ct_ref){
							var ct_idx, ct_ref;

							if (CheckSpaces(document.getElementById('frm_list').ct_name${zz}, '부서명')) { return false; }

							else {
								document.getElementById('frm_m').ct_name.value	= document.getElementById('frm_list').ct_name${zz}.value;
								<c:if test="${ct_depth+0 <= 2}">
								//document.getElementById('frm_m').ct_memo.value	= document.getElementById('frm_list').ct_memo${zz}.value;
								</c:if>
								document.getElementById('frm_m').ct_idx.value	= ct_idx;
								document.getElementById('frm_m').ct_ref.value	= ct_ref;
								document.getElementById('frm_m').action			= "${builder_url}/ncms/buseo/writeOk.do";
								document.getElementById('frm_m').submit();
							}
						}
					</script>
				</td>
				<td class="center"><a href="javascript:;" onclick="return d_buseo_chk('${buseo.ct_idx}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
			</tr>
			</c:forEach>
			<c:if test="${fn:length(buseoList) == 0}">
			<tr>
				<td class="center" colspan="7">데이터가 없습니다.</td>
			</tr>
			</c:if>


		</tbody>
		</table>
	</fieldset>

	<!-- 하단버튼 -->
	<div id="contoll_area">
		<ul>
			<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 삭제</a></p>
			<p><a onclick="javascript:window.open('listMove.do?ct_idx=${ct_idx}','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p>
			</li> 
			<li class="btn_ri">
				<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 부서를</p>
				<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 부서 사용여부 선택" class="t_search" style="width:70px;">
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



<form id= "frm_m" method='post' action="">
<div>
	<input type="hidden" name="ct_name" />
	<input type="hidden" name="ct_memo" />
	<input type="hidden" name="ct_chk" />
	<input type="hidden" name="ct_idx" />
	<input type="hidden" name="ct_ref" />
	<input type="hidden" name="ct_icon" />
</div>
</form>

<!--// 부서등록 -->

			</td>
		</tr>
	</table>



</div>