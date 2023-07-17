<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_menu.js"></script>
<script type="text/javascript">
function deleteOk(ct_idx){
	if(confirm("메뉴를 삭제하시겠습니까?")){
		location.href="deleteOk.do?ct_idx="+ct_idx+"&prepage=${nowPageEncode}";
	}
}
</script>

<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<h2 class="tit">현재메뉴경로<span>: <a href="write.do">메뉴관리홈</a> 

	<c:forEach items="${refMenuList}" var="refMenuList" varStatus="ref_no">
		<c:choose>
			<c:when test="${ref_no.last}">
					&gt; <a href="write.do?ct_idx=${refMenuList.ct_idx}" ><strong class="point_default">${refMenuList.ct_name}</strong></a>
			</c:when>
			<c:otherwise>
					&gt; <a href="write.do?ct_idx=${refMenuList.ct_idx}" >${refMenuList.ct_name}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>

	</span></h2>
	
	<!-- 등록폼-->
	<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk();">
	<div>
		<input type="hidden" name="ct_ref" value="${ct_ref}" />
		<input type="hidden" name="ct_depth" value="${ct_depth}" />
		<input type="hidden" name="ct_codeno_ref" value="${ct_codeno_ref}" />
		<input type="hidden" name="ct_icon" id="ct_icon_top" value=""/>
		<input type="hidden" name="prepage" id="prepage" value="${nowPage }"/>
	</div>
	<div class="top_search_area mt10">
		<ul>
			<li class="tit"><label for="ct_name_i"><h3 class="tit">메뉴등록 :</h3></label></li>
			<li class="search"><label for="ct_name_i">메뉴명을 입력하세요</label><input title="메뉴 입력" name="ct_name" id="ct_name_i" type="text" value="" class="search_input autoInput" /></li>
	
			<c:if test="${ct_depth == 1}">
				<li class="icon_sample"><a href="#view_icon_box"><img onclick="view_icon_box('ct_icon_top','icon_img_top',event);" id="icon_img_top" src="/nanum/ncms/img/casual/top_icon01.png" alt="메뉴아이콘선택"></a></li>
			</c:if>
			
			<li class="sel">
				<select id="ct_chk_i" name="ct_chk" title="메뉴 사용여부 선택" class="t_search" style="width:80px;">
					<option value="Y" selected="selected">사용</option>
					<option value="N" >중지</option>
				</select>
			</li>
			<li class="btn"><input type="submit" value="등록" class="btn_bl_default" /></li>
			<c:if test="${ct_depth != 1 and ct_depth<= max_depth_option}">
			<li class="btn"><input type="button" value="상위메뉴 이동" class="btn_gr_default" onclick="location.href='write.do?ct_idx=${ref_ct_idx_str}';" /></li>
			</c:if>
		</ul>
	</div>
	</form>
	<!-- //등록폼 -->
		
	<form id= "frm_list" action="" method='post'>
	<div>
		<input type="hidden" id="status" name="status" />
		<input type="hidden" id="ct_chk" name="ct_chk" />
		<input type="hidden" id="ct_idx" name="ct_idx" />
		<input type="hidden" id="ct_ref" name="ct_ref" value="${ct_ref}" />

		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" name="prepage" id="prepage" value="${nowPage }"/>
	</div>
			
		<fieldset>
			<legend>메뉴관리 작성/수정</legend>
			<table class="bbs_common bbs_default" summary="사이트에 사용하는 메뉴를 관리합니다.">
			<caption>메뉴관리 서식</caption>
			<colgroup>
				<col width="40" />
				<col />
				
				<c:if test="${ct_depth == 1}">
				<col width="50" />
				</c:if>
				<c:if test="${ct_depth == 2}">
				<col width="350" />
				</c:if>

				<c:if test="${ct_depth< max_depth_option}">
				<col width="67" />
				</c:if>
				<c:if test="${ct_depth == 3}">
				<col />
				</c:if>
				<col width="70" />
				<col width="50" />
				<col width="50" />
			</colgroup>

			<thead>
			<tr>
				<th scope="col">선택</th>
				<th scope="col">메뉴</th>
				<c:if test="${ct_depth == 1}">
				<th scope="col">아이콘</th>
				</c:if>
				<c:if test="${ct_depth == 2}">
				<th scope="col">폴더명</th>
				</c:if>

				<c:if test="${ct_depth< max_depth_option}">
				<th scope="col">하위메뉴</th>
				</c:if>

				<c:if test="${ct_depth == 3}">
				<th scope="col">경로</th>
				</c:if>
				<th scope="col">사용여부</th>
				<th scope="col">수정</th>
				<th scope="col">삭제</th>
			</tr>
			</thead>

			<tbody>
			<c:forEach items="${menuList}" var="menuList" varStatus="no">
			<tr>
				<td class="center"><input type="checkbox" name="chk" value="${menuList.ct_idx}" title="해당 팝업존 선택" /></td>
				<td>
					<input type="text" title="메뉴 입력" id="ct_name${no.count}" name="ct_name${no.count}" class="ta_input w9 editmode" value="${menuList.ct_name}" maxlength="100" />
				</td>
				
				<c:if test="${ct_depth == 1}">
				<td class="center">
					<div class="icon_sample"><a href="#view_icon_box"><img onclick="javascript: view_icon_box('ct_icon${no.count}','icon_img${no.count}',event);" id="icon_img${no.count}" src="/nanum/ncms/img/casual/${menuList.ct_icon}.png" alt="메뉴아이콘선택"></a></div>
					<input type="hidden" name="ct_icon${no.count}" id="ct_icon${no.count}" value="${menuList.ct_icon}"/>
				</td>
				</c:if>
				
				<c:if test="${ct_depth == 2}">
				<td class="center"><input type="text" size="33" title="경로 입력" id="ct_folder${no.count}" name="ct_folder${no.count}" class="ta_input w8" value="${menuList.ct_folder}" maxlength="150" /></td>
				</c:if>

				<c:if test="${ct_depth< max_depth_option}">
				<td class="center"><a href="#view" onclick="page_go1('write.do?ct_idx=${menuList.ct_idx}');"><img alt="하위메뉴보기" src="/nanum/ncms/img/common/view_icon.gif" /></a></td>
				</c:if>
				<c:if test="${ct_depth == 3}">
				<td class="center"><input type="text" size="33" title="경로 입력" id="ct_url${no.count}" name="ct_url${no.count}" class="ta_input w8" value="${menuList.ct_url}" maxlength="150" /></td>
				</c:if>
				<td class="center">${menuList.ct_chk == "Y" ? "<strong>사용</strong>" : "중지"}</td>
				<td class="center"><a href="#modify" onclick="frm_modify${no.count}(${menuList.ct_idx},${menuList.ct_ref});"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a>
				
				<script type="text/javascript">
					function frm_modify${no.count}(ct_idx,ct_ref){
						var ct_idx, ct_ref;

						if (CheckSpaces(document.getElementById('frm_list').ct_name${no.count}, '메뉴명')) { return false; }
						else {
							loading_st(1);
							document.getElementById('frm_m').ct_name.value	= document.getElementById('frm_list').ct_name${no.count}.value;

							<c:if test="${ct_depth == 3}">
							document.getElementById('frm_m').ct_url.value	= document.getElementById('frm_list').ct_url${no.count}.value;
							</c:if>
							<c:if test="${ct_depth == 1}">
							document.getElementById('frm_m').ct_icon.value	= document.getElementById('frm_list').ct_icon${no.count}.value;
							</c:if>
							<c:if test="${ct_depth == 2}">
							document.getElementById('frm_m').ct_folder.value	= document.getElementById('frm_list').ct_folder${no.count}.value;
							</c:if>
							document.getElementById('frm_m').ct_idx.value	= ct_idx;
							document.getElementById('frm_m').ct_ref.value	= ct_ref;
							document.getElementById('frm_m').action			= "writeOk.do";
							document.getElementById('frm_m').submit();
						}
					}
				</script>
				</td>
				<td class="center"><a href="#delete" onclick="deleteOk('${menuList.ct_idx}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
				
			</tr>
			</tr>
			</c:forEach>
			</tbody>
			</table>
		</fieldset>

		<!-- 하단버튼 -->
		<div id="contoll_area">
			<ul>
				<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 메뉴삭제</a></p><!--p><a href="javascript:moveAll();" class="btn_gr">순서일괄수정</a></p-->
				<p><a onclick="javascript:window.open('listMove.do?ct_idx=${ct_ref}&prepage=${nowPageEncode }','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
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
	<input type="hidden" name="ct_url" />
	<input type="hidden" name="ct_folder" />
	<input type="hidden" name="ct_chk" />
	<input type="hidden" name="ct_idx" />
	<input type="hidden" name="ct_ref" />
	<input type="hidden" name="ct_icon" />
	<input type="hidden" name="prepage" value="${nowPage }" />
</div>
</form>


<div id="icon_sample" class="disnone">
	<img src="/nanum/ncms/img/casual/top_icon01.png" data-icon="top_icon01">
	<img src="/nanum/ncms/img/casual/top_icon02.png" data-icon="top_icon02">
	<img src="/nanum/ncms/img/casual/top_icon03.png" data-icon="top_icon03">
	<img src="/nanum/ncms/img/casual/top_icon04.png" data-icon="top_icon04">
	<img src="/nanum/ncms/img/casual/top_icon05.png" data-icon="top_icon05">
	<img src="/nanum/ncms/img/casual/top_icon06.png" data-icon="top_icon06">
</div>

<!-- 하단영역-->
<jsp:include page="../common/file/bottom.jsp" />

<script type="text/javascript">
var flag_icon_view = false;
var textfield = "";
var imgfield = "";
var img_path = "/nanum/ncms/img/casual/";

function view_icon_box(tf, imgf, event){
	var xx = document.body.scrollTop;
	if (xx == 0) xx = document.documentElement.scrollTop;	
	xx = xx + event.clientX-120;
	var yy = document.body.scrollLeft;
	if (yy == 0) yy = document.documentElement.scrollLeft;
	yy = yy + event.clientY+20;

	if (flag_icon_view && textfield==tf){
		$("#icon_sample").hide();
		flag_icon_view = false;
	}else{
		$("#icon_sample").css({"left":xx+"px", "top":yy+"px"}).show();
		flag_icon_view = true;
	}
	textfield = tf;	
	imgfield = imgf;
}

$(document).ready(function(){ 
	$("#icon_sample").find("img").css("cursor","pointer");

	var firstimg = $("#icon_sample").find("img:first").data("icon");
	$("#ct_icon_top").val( firstimg);
	$("#icon_img_top").attr("src", img_path+firstimg+".png");

	$("#icon_sample").find("img").click(function(){
		var imgsrc = $(this).data("icon");
		$("#"+textfield).val(imgsrc);
		$("#"+imgfield).attr("src", img_path+imgsrc+".png");

		$("#icon_sample").hide();
		flag_icon_view = false;
	});

});

</script>