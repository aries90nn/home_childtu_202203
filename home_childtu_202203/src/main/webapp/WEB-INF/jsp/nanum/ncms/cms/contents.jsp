<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>


<c:set var="site_dir" value="" />
<c:if test="${!empty BUILDER_DIR }"><c:set var="site_dir" value="/${BUILDER_DIR }" /></c:if>
<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_cms.js"></script>
<script src="/nanum/site/common/js/jquery-linenumbers.min.js"></script>


<h1 class="tit"><span>홈페이지</span> ${cms.ct_depth}차 메뉴관리</h1>
		
<!-- 내용들어가는곳 -->
<div id="contents_area">
		
	<form id="frm" name="frm" method="post" action="contentsOk.do" enctype="multipart/form-data" onsubmit="return w_chk2();">
	<div>
	<input type="hidden" name="ct_idx" value="${cms.ct_idx}" /><!-- (수정일때사용) -->
	<input type="hidden" name="ct_ref" value="${cms.ct_ref}" />
	<input type="hidden" name="ct_group" value="${sessionScope.ss_ct_group}" />
	<input type="hidden" name="ct_url_target" id="ct_url_target" value="${cms.ct_url_target}" />
	</div>
		<h2 class="tit">${cms.ct_depth}차 메뉴관리 <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>

		<fieldset>
			<legend>홈페이지 메뉴별 내용입력 서식 작성</legend>
			<table class="bbs_common bbs_default" summary="신규 QR코드 생성을 위한 입력 양식입니다.">
			<caption>홈페이지 메뉴별 내용입력 서식</caption>
			<colgroup>
			<col width="15%" />
			<col width="*" />
			</colgroup>

			<tr>
				<th scope="row" class="left"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 메뉴명</th>
				<td class="left">
					<input type="text" name="ct_name" id="ct_name" value="${cms.ct_name}" class="ta_input w5" maxlength="50" />
					&nbsp;<span class="point fs11">* 메뉴명은 50자 이내로 입력해주세요.</span>
				</td>
			</tr>
	<c:if test='${cms.ct_depth eq 1}'>		
			<tr>
				<th scope="row" class="left"> 메뉴명(영문)</th>
				<td class="left">
					<input type="text" name="ct_name_eng" id="ct_name_eng" value="${cms.ct_name_eng}" class="ta_input w5" maxlength="30" />
					&nbsp;<span class="point fs11">* 메뉴명은 30자 이내로 입력해주세요.</span>
				</td>
			</tr>
	
		<tr>
			<th scope="row" class="left" style="height:55px">상단 이미지메뉴 사용여부</th>
			<td class="left">
				<input type="checkbox" name="ct_img_status"  id="jsImgUse" value="Y" style="vertical-align:middle;" <c:if test='${cms.ct_img_status == "Y"}'> checked</c:if> /> <label for="ct_img_status">사용</label>
			</td>
		</tr>
			
		<tr class="jsImgArea disnone">
			<th scope="row">상단메뉴 이미지</th>
			<td class="left">
				<table class="bbs_nor">
				<!-- tr>
					<th scope="row">마우스오버</th>
					<td class="left">
						<c:if test='${cms.ct_img_on != "" and cms.ct_img_on != null}'>
						<input type="hidden" name="ct_img_on_org" value="${cms.ct_img_on}" />
						<img src="/data/cms/${cms.ct_img_on}" onload="imgResize(this, 200, 40);" /> <input type="checkbox" name="ct_img_on_del" id="ct_img_on_del" value="Y" style="vertical-align:middle;" /><label for="ct_img_on_del">삭제 </label><br/>
						</c:if>
						<input type="file" name="ct_img_on_file" id="ct_img_on_file" class="ta_input_file_nor" style="width:350px" />
						<span class="point fs11">* 5MB의 jpg,png,gif 이미지 파일만 허용 가능합니다. (가로 : 63px, 세로 : 17px 를 추천합니다.)</span>
					</td>
				</tr -->
				<tr>
					<td class="left">
						<c:if test='${cms.ct_img_off != "" and cms.ct_img_off != null}'>
						<input type="hidden" name="ct_img_off_org" value="${cms.ct_img_off}" />
						<img src="/data/cms/${cms.ct_img_off}" style="max-height:27px" /> <input type="checkbox" name="ct_img_off_del" id="ct_img_off_del" value="Y" style="vertical-align:middle;" /><label for="ct_img_off_del">삭제 </label><br/>
						</c:if>
						<input type="file" name="ct_img_off_file" id="ct_img_off_file" class="ta_input_file_nor" style="width:350px" />
						<br/>
						<span class="point fs11">* 5MB의 jpg,png,gif 이미지 파일만 허용 가능합니다. 세로사이즈가 최대 27px이 넘어갈 시에는 이미지가 안맞게 보일수 있습니다. (가로 : 234px, 세로 : 17px 을 추천합니다.)</span>
					</td>
				</tr>
				</table>
			</td>
		</tr>
	</c:if>
	
	<tr>
		<th scope="row" class="left">입력유형</th>
		<td class="left">
			<input type="radio" name="ct_pagetype" id="ct_pagetype_T" value="T" <c:if test='${cms.ct_pagetype == "T"}'> checked</c:if> class="jsType" /><label for="ct_pagetype_T">직접입력</label>&nbsp;&nbsp;
			<input type="radio" name="ct_pagetype" id="ct_pagetype_B" value="B" <c:if test='${cms.ct_pagetype == "B"}'> checked</c:if> class="jsType" /><label for="ct_pagetype_B">게시판</label>&nbsp;&nbsp;
			<input type="radio" name="ct_pagetype" id="ct_pagetype_L" value="L" <c:if test='${cms.ct_pagetype == "L"}'> checked</c:if> class="jsType" /><label for="ct_pagetype_L">링크</label>&nbsp;&nbsp;
			<input type="radio" name="ct_pagetype" id="ct_pagetype_X" value="X" <c:if test='${cms.ct_pagetype == "X"}'> checked</c:if> class="jsType" /><label for="ct_pagetype_X">유형없음</label>&nbsp;&nbsp;
		</td>
	</tr>
		
		<c:if test="${fn:length(historyList) > 0}">
		<tr>
			<th scope="row" class="left">작업내역</th>
			<td class="left">
				<select name="cth_idx" id="cth_idx" class="ta_select" style="width:300px">
					<option value="">선택</option>
					<c:forEach items="${historyList}" var="history" varStatus="no">
					<c:set var="page_type" value="${history.ct_pagetype eq'T' ? '직접입력' : page_type }" />
					<c:set var="page_type" value="${history.ct_pagetype eq 'L' ? '링크' : page_type }" />
					<c:set var="page_type" value="${history.ct_pagetype eq 'B' ? '게시판' : page_type }" />${history.ct_pagetype }
					<option value="${history.cth_idx}" ct_pagetype="${history.ct_pagetype}">${no.count}. ${history.ct_wdate} (입력유형 : ${page_type})</option>
					</c:forEach>
				</select>
				<input type="button" value="보기" class="btn_gr_zipcode" onclick="javascript:view_history();"/>&nbsp;
			</td>
		</tr>
		</c:if>
		
		<c:if test='${cms.ct_depth >= 2}'>
		<tr>
			<th scope="row" class="left" style="height:55px">하위메뉴 탭 사용여부</th>
			<td class="left">
				<select id="ct_tab" name="ct_tab" title="메뉴표시여부 선택" class="ta_select" style="width:70px;" >
					<option value="Y" ${cms.ct_tab =='Y' ? 'selected' : '' }>사용</option>
					<option value="N" ${cms.ct_tab =='N' or cms.ct_tab == '' ? 'selected' : '' }>미사용</option>
				</select>
				<span class="point fs11">* 하위메뉴들을 컨텐츠 안의 탭으로 구분할지 여부</span>
			</td>
		</tr>
		</c:if>
		
		<tr>
			<th scope="row" class="left">메뉴표시여부</th>
			<td class="left">
				<select id="ct_chk" name="ct_chk" title="메뉴표시여부 선택" class="ta_select" style="width:70px;" >
					<option value="Y" ${cms.ct_chk =='Y' ? 'selected' : '' }>표시</option>
					<option value="N" ${cms.ct_chk =='N' || cms.ct_chk ==null ? 'selected' : '' }>감추기</option>
				</select>
				<span class="point fs11">* 홈페이지 상단/좌측 영역에 해당메뉴를 표시할지의 여부</span>
			</td>
		</tr>
		
		<tr class="disnone jsTypeView" data-type="T">
			<th scope="row" class="left" style="line-height:23px;">접근제한그룹</th>
			<td class="left">
				<c:set var="groupArr" value="${fn:split(cms.ct_group,',')}" />
				<c:forEach items="${membergroupList}" var="membergroup" varStatus="no">
					<c:if test="${membergroup.g_num != '1'}">
					
						<c:set var="checked" value="" />
						<c:forEach var="ct_group" items="${groupArr}" varStatus="j">
							<c:if test="${ct_group eq membergroup.g_num}">
								<c:set var="checked" value="checked" />
							</c:if>
						</c:forEach>
						<input type="checkbox" name="gChk" id="gChk${membergroup.g_num}" value="${membergroup.g_num}" ${checked} />
						<label for="gChk${membergroup.g_num}">${membergroup.g_menuname}</label>
					</c:if>
				</c:forEach>
			</td>
		</tr>
		
		

		<tr class="disnone jsTypeView" data-type="T">
			<td colspan="2">
				<div style="float:right">
					<input type="button" value="미리보기" class="btn_bl_dandy" onclick="javascript:;" id="preview"/>&nbsp;
					<input type="submit" value="등록" class="btn_bl_default" /> <input type="button" value="취소" onclick="page_go1('write.do?ct_idx=${cms.ct_ref}');" class="btn_wh_default" />
				</div>
				<br/>
				<br/>
<c:if test="${ct_tab_ref eq 'Y' }"><%--부모메뉴가 탭메뉴 사용중이라면 --%>
				<h3 class="tit">탭메뉴상단 내용</h3>
				<textarea name="ct_header" id="ct_header" style="width:100%;height:200px;" class="lined">${cms.ct_header}</textarea>
				<br />
</c:if>
				<h3 class="tit">본문 내용</h3>
				<textarea name="ct_content" id="ct_content" style="width:100%;height:600px;" class="lined">${cms.ct_content}</textarea>
				
			</td>
		</tr>

		<tr class="disnone jsTypeView" data-type="B">
			<th scope="row" class="left"><label for="ct_anum">게시판선택</label></th>
			<td class="left">
				
				<p style="padding-bottom:10px;">
					<input type="radio" name="board_type" id="board_type_new" value="new"  ${cms.ct_anum eq '' ? 'checked=checked' : '' } /><label for="board_type_new">신규게시판 생성</label></p>

					<input type="radio" name="board_type" id="board_type_org" value="org" ${cms.ct_anum ne '' ? 'checked=checked' : '' } /><label for="board_type_org">기존게시판 연결</label>
					&nbsp;&nbsp;
					<select name="ct_anum" id="jsCtAnum" class="ta_select" style="width:300px">
						<option value="">:: 선택하세요 ::</option>
						<c:forEach items="${boardList}" var="board" varStatus="no">
							<c:set var="tmp_color" value="${!empty board.ct_name and cms.ct_anum ne board.a_num ? 'class=\"jsFontGray\"' : '' }" />
							<c:set var="tmp_str" value="${!empty board.ct_name and cms.ct_anum ne board.a_num ? ' - 연결됨' : '' }" />
							<option value="${board.a_num}" ${cms.ct_anum eq board.a_num ? 'selected="selected"' : '' } ${tmp_color}>${board.a_bbsname} (${board.a_tablename}) ${tmp_str}</option>
						</c:forEach>
					</select>
					&nbsp;
					<c:if test='${cms.ct_pagetype == "B" and cms.ct_anum != ""}'>
						<!-- input type="button" value="게시판수정" class="btn_bl_default" onclick="javascript: popup_go('/ncms/board_config/write_popup.do?a_num=${cms.ct_anum}&page_type=popup',900,800,1);" / -->
						<input type="button" value="게시판수정" class="btn_bl_default" onclick="javascript: configWrite();" />&nbsp;
					</c:if>
				</td>
			</tr>

<c:if test="${ct_tab_ref eq 'Y' }"><%--부모메뉴가 탭메뉴 사용중이라면 --%>
		<tr class="disnone jsTypeView" data-type="B">
			<th scope="row" class="left"><label for="ct_header">탭메뉴 상단 HTML 정보</label></th>
			<td class="left">
				<textarea rows="6" id="ct_header_b" name="ct_header_b" title="탭메뉴 상단 HTML 정보" class="ta_textarea w9" >${cms.ct_header}</textarea>
			</td>
		</tr>
</c:if>


		<tr class="disnone jsTypeView" data-type="B">
			<th scope="row" class="left"><label for="ct_board_header">상단 HTML 정보</label></th>
			<td class="left">
				<textarea rows="6" id="ct_board_header" name="ct_board_header" title="상단 HTML 입력" class="ta_textarea w9" >${cms.ct_board_header}</textarea>
			</td>
		</tr>
		<tr class="disnone jsTypeView" data-type="B">
			<th scope="row" class="left"><label for="ct_board_footer">하단 HTML 정보</label></th>
			<td class="left">
				<textarea rows="6" id="ct_board_footer" name="ct_board_footer" title="상단 HTML 입력" class="ta_textarea w9" >${cms.ct_board_footer}</textarea>
			</td>
		</tr>

		<tr class="disnone jsTypeView" data-type="L">
			<th scope="row" class="left"><label for="ct_url">링크 URL</label></th>
			<td class="left">
				<input type="text" name="ct_url" id="ct_url" value="${cms.ct_url}" class="ta_input" maxlength="100" style="width:60%;" />
				<input type="checkbox" name="ct_url_target_tmp" id="ct_url_target_tmp" value="Y" <c:if test='${cms.ct_url_target eq "Y"}'> checked</c:if> /><label for="ct_url_target_tmp">새창열기</label>
				<br/><span class="point fs11">* 100자 이하로 입력해주시고, http:// 를 포함한 전체경로를 입력하세요.</span>
				
			</td>
		</tr>
		
		</table>
	</fieldset>
	
	<div class="contoll_box">
		<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="취소" onclick="page_go1('write.do?ct_idx=${cms.ct_ref}');" class="btn_wh_default" /></span>
	</div>
	
	<br/><br/>
	
	</form>

	<form name="frm_history" id="frm_history" action="" method="post">
		<input type="hidden" name="ct_idx" value="${cms.ct_idx}" />
		<input type="hidden" name="cth_idx" value="" />
	</form>
	

		</div>
		<!-- 내용들어가는곳 -->




<script type="text/javascript">
//<![CDATA[
	
function configWrite(){
	if(confirm("게시판 수정을 하러 가시겠습니까?(새창으로 띄워집니다.)")){
		window.open('${site_dir}/ncms/board_config/write.do?a_num=${cms.ct_anum}')
	}else{
		return false;
	}
}
	

function proc_imgArea(){
	if($("#jsImgUse").is(":checked")){
		$("tr.jsImgArea").show();
	}else{
		$("tr.jsImgArea").hide();
	}
}

function proc_type(){
	var str = $(".jsType:checked").val();
	if (str!=""){	
		$(".jsTypeView").each(function(){
			if($(this).data("type")==str){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	}
}

function sendit(){
	if (CheckSpaces(document.getElementById('frm').ct_name, '메뉴명')) { return ; }

	var val1 = $("input[name='ct_pagetype']:checked").val();
	var val2 = $("input[name='board_type']:checked").val();
	var val3 = $("#jsCtAnum").val();

	if(val1=="B" && val2=="org" && val3==""){
		alert("게시판을 선택하세요");
		return false;
	}
}


$(function(){
	
	$("#ct_url_target_tmp").click(function(){
		var chk = $(this).is(":checked");//.attr('checked');
        if(chk){
			$("#ct_url_target").val("Y");
		}else{
			$("#ct_url_target").val("");
		}
	});

	$("#jsImgUse").click(function(){
		proc_imgArea();
	});
	proc_imgArea();

	$(".jsType").click(function(){
		proc_type();
	});
	proc_type();

	$(".jsFontGray").css("color","#afafaf");

	$("#jsCtAnum").change(function(){
		if($(this).find("option:selected").hasClass("jsFontGray")){ // 타메뉴에 연결된 게시판
			alert("다른 메뉴에 연결된 게시판입니다. 다른 게시판을 선택하세요.");
			$(this).val("").change();
		}
	});

	//미리보기
	$("#preview").click(function(){
		var gsWin = window.open("${site_dir}/cms/preview.do?idx=${cms.ct_idx}", "winName");
	});

});



function view_history(){
	var cth_idx = document.getElementById("frm").cth_idx.value;

	if(cth_idx == ""){
		alert("작업내역을 선택해주세요.");
		document.getElementById("frm").cth_idx.focus();
		return false;
	}else{
		if($("#cth_idx option:selected").attr("ct_pagetype") == "L"){
			var popUrl = "./history.do?cth_idx="+cth_idx+"&ct_idx="+${cms.ct_idx};	//팝업창에 출력될 페이지 URL
			var popOption = "width=470, height=200, resizable=yes, scrollbars=yes, status=no;";    //팝업창 옵션(optoin)
			window.open(popUrl,"",popOption);
		}else{
			var popUrl = "./history.do?cth_idx="+cth_idx+"&ct_idx="+${cms.ct_idx};	//팝업창에 출력될 페이지 URL
			var popOption = "width=1270, height=760, resizable=yes, scrollbars=yes, status=no;";    //팝업창 옵션(optoin)
			window.open(popUrl,"",popOption);
		}
	}
}

//복원하기
function submit_history(){
	var cth_idx = document.getElementById("frm").cth_idx.value;

	if(cth_idx == ""){
		alert("작업내역을 선택해주세요.");
		document.getElementById("frm").cth_idx.focus();
		return false;
	}else{
		if (confirm('정말 적용하시겠습니까?'))	{
			document.getElementById("frm_history").cth_idx.value = cth_idx;
			document.getElementById("frm_history").action = "historyOk.do";
			document.getElementById("frm_history").submit();
		}
	}
}


//]]>
</script>

<c:if test='${cms.ct_pagetype eq "T"}'>
<script>
$(function() {
	$('#ct_header,#ct_content').linenumbers({col_width:'55px'});
	//$('#ct_content').linenumbers({col_width:'55px'});
});
</script>
</c:if>