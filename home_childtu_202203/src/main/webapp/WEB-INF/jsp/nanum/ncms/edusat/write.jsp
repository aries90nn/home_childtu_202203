<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<c:set var="prepage" value="${empty param.prepage ? 'list.do' : param.prepage}" />

<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_edusat.js"></script>

<style>
#contents_area table tr td * {vertical-align:middle;}
</style>
<script type="text/javascript">
/*
$(document).ready(function () {

	//요일선택시 매일은 나머지 요일 비활성화시키기
	$('input[name="edu_week_chk"]').change(function(){
		var weekVal = $(this).val();
		weekChk($(this).val());
	});
	
	lotTypeChk("${edusat.edu_lot_type}");
	lotTypeShow();
	
	//loginCheck();	//참가대상제한일때 로그인필수체크
	
	loadSeason();
});
*/
/*
function weekChk(value){
	var weekChk = $('input[name="edu_week_chk"]').is(":checked");
	var cnt = $('input[name="edu_week_chk"]:checked').length;
	if(cnt==0){
		$("input[name*='edu_week_chk']").prop("checked", false).prop( "disabled", false );
	}else{
		//매일이라고 체크했으면 나머지요일 비활성화
		if(value == "9" && weekChk){
			for(var i=0; i<=8; i++){
				$("#edu_week_"+i).prop("checked", false).prop( "disabled", true );
			}
		//매일 체크 풀려있으면 나머지요일 활성화, 매일은 비활성화
		}else{
			for(var i=0; i<=8; i++){
				$("#edu_week_"+i).prop( "disabled", false );
			}
		}
	}
}
*/
/*
function lotTypeChk(value){
	var obj = document.getElementById("edu_awaiter");
	if(value == "1"){
		obj.value = "0";
		obj.readOnly = true;
		obj.style.backgroundColor = "#E0E0E0";
	}else if(value == "2"){
		obj.readOnly = false;
		obj.style.backgroundColor = "";
	}
}
*/
function onlyNumber(obj) {
	$(obj).keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/g,""));
	}); 
}

function lotTypeShow(){
	var obj = document.getElementById("edu_receive_type_2");
	var tr = document.getElementById("tr_lot_type");
	if(obj.checked == true){
		tr.style.display = "";
	}else{
		tr.style.display = "none";
	}
}

function loginCheck(){
	if($("#edu_temp4").prop("checked")){
		$("#edu_login").prop("checked", true);
		$("#span_edu_temp5").css("display", "");
	}else{
		$("#span_edu_temp5").css("display", "none");
	}
}


function loadSeason(){
	var sh_es_libcode = "";
	if($("#ct_idx").val() != ""){
		var ct_idx_arr = $("#ct_idx").val().split(";");
		sh_es_libcode = ct_idx_arr[0];
	}
	var rand = Math.floor(Math.random() * 999) + 1;
	$("#div_season").load("/${BUILDER_DIR}/ncms/edusat_season/ajaxListAll.do?edu_season=${edusat.edu_season}&sh_es_libcode="+sh_es_libcode+"&v="+rand);
}


function targetCheck(chkobj){
	
	if( $("#edu_temp4").prop("checked") ){
		if(!$(chkobj).prop("checked")){
			alert("참가대상 제한을 사용할 경우 반드시 로그인 필수를 체크해야합니다.");
			$(chkobj).prop("checked", true);
		}
	}
}

function copyEdusat(){
	var eForm = document.getElementById("frm");
	if (CheckSpaces(eForm.copy_edu_idx, "복사할 프로그램") ){
		return;
	}
	var copy_edu_idx = eForm.copy_edu_idx.value;
	location.href="?copy_edu_idx="+copy_edu_idx;
	
}
</script>

<jsp:useBean id="now" class="java.util.Date" />

<%//기본값 세팅 %>
<c:choose>
	<c:when test="${empty edusat.edu_idx and empty param.copy_edu_idx }">
		<c:set var="edu_resdate_y"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
		<c:set var="edu_resdate_m"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
		<c:set var="edu_resdate_d"><fmt:formatDate value="${now}" pattern="dd" /></c:set>
		<c:set var="edu_reedate_y"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
		<c:set var="edu_reedate_m"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
		<c:set var="edu_reedate_d"><fmt:formatDate value="${now}" pattern="dd" /></c:set>
		<c:set var="edu_sdate_y"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
		<c:set var="edu_sdate_m"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
		<c:set var="edu_sdate_d"><fmt:formatDate value="${now}" pattern="dd" /></c:set>
		<c:set var="edu_edate_y"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
		<c:set var="edu_edate_m"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
		<c:set var="edu_edate_d"><fmt:formatDate value="${now}" pattern="dd" /></c:set>
		
		<c:set var="edu_resdate_h">9</c:set>
		<c:set var="edu_reedate_h">18</c:set>
		
		<c:set var="edu_lot_type">2</c:set>
		<c:set var="edu_ptcp_yn">N</c:set>
		
		<c:set target="${edusat}" property="edu_field1_yn" value="N" /><%-- 기관명 --%>
		<c:set target="${edusat}" property="edu_field2_yn" value="R" /><%-- 주소 --%>
		<c:set target="${edusat}" property="edu_field3_yn" value="N" /><%-- 첨부파일 --%>
		<c:set target="${edusat}" property="edu_field4_yn" value="N" /><%-- 교육대상및인원 --%>
		<c:set target="${edusat}" property="edu_field5_yn" value="N" /><%-- 고통사고확인 --%>
		<c:set target="${edusat}" property="edu_field6_yn" value="N" /><%-- 비밀번호 --%>
		<c:set target="${edusat}" property="edu_field7_yn" value="N" /><%-- 주소 --%>
		<c:set target="${edusat}" property="edu_field8_yn" value="N" /><%-- 기타 --%>
		<c:set target="${edusat}" property="edu_field9_yn" value="N" /><%-- 자녀명 --%>
		<c:set target="${edusat}" property="edu_field10_yn" value="N" /><%-- 사용안함 --%>
	</c:when>
	<c:otherwise>
		<c:set var="edu_resdate_h" value="${edusat.edu_resdate_h }"></c:set>
		<c:set var="edu_reedate_h" value="${edusat.edu_reedate_h }"></c:set>
		
		<c:set var="edu_lot_type" value="${edusat.edu_lot_type }"></c:set>
		<c:set var="edu_ptcp_yn" value="${edusat.edu_ptcp_yn }"></c:set>
	</c:otherwise>
</c:choose>

<c:set var="nowdate_y"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>

	
<h1 class="tit"><span>프로그램</span> 정보</h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">

			<form id="frm" method="post" action="writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk();">
			<div>
			<input type="hidden" name="edu_temp3_2" value="${edusat.edu_temp3 }" />
			<input type="hidden" name="edu_idx" value="${edusat.edu_idx}" />
			<%--<input type="hidden" name="edu_receive_type" value="2" /> 신청방식 인터넷고정 --%>
			<input type="hidden" name="ct_idx2_tmp" id="ct_idx2_tmp" value="" />
			<input type="hidden" name="prepage" value="${prepage}" />
			</div>
			
				<h2 class="tit">프로그램 정보입력<span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>
				<fieldset>
					<legend>프로그램생성 서식 작성</legend>
					<table cellspacing="0" class="bbs_common bbs_default" summary="프로그램정보관리를 위한 입력 양식입니다.">
					<caption>프로그램생성 서식</caption>
					<colgroup>
					<col width="140" />
					<col width="580" />
					</colgroup>
				
				<!-- 
				<c:if test="${empty edusat.edu_idx}">
					<tr>
						<th scope="row"><label for="copy_edu_idx"> 기존프로그램 복사</label></th>
						<td class="left">
							<select id="copy_edu_idx" name="copy_edu_idx" title="기존프로그램 선택" onchange="" class="ta_select">
								<option value="">기존프로그램 선택</option>
								<c:forEach items="${srcEdusatList }" var="srcEdusat">
								<option value="${srcEdusat.edu_idx }" ${param.copy_edu_idx eq srcEdusat.edu_idx ? 'selected="selected"' : '' }>[${srcEdusat.edu_gubun  }]${srcEdusat.edu_subject }</option>
								</c:forEach>						
							</select>
							<input type="button" value="복사" onclick="copyEdusat();" class="btn_bl_default" />
						</td>
					</tr>
				</c:if>
				 -->
					
					<!-- 
					<tr>
						<th scope="row"><label for="edu_season"> 프로그램기수</label></th>
						<td class="left">
							<div id="div_season">
							</div>
						</td>
					</tr>
 					-->
<c:if test="${cateList != null}">
					<tr>
						<th scope="row"><label for="ct_idx2"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 프로그램구분</label></th>
						<td class="left">
							<select id="ct_idx2" name="ct_idx2" title="프로그램구분 선택" class="ta_select">
								<option value="" > -- 프로그램구분선택 --</option>
							<c:forEach items="${cateList}" var="cate" varStatus="no">
								<option value="${cate.ct_idx};${cate.ct_name}" ${cate.ct_idx eq edusat.ct_idx2 ? 'selected="selected"' : '' }>${cate.ct_name}</option>
							</c:forEach>
							</select>

						</td>
					</tr>
</c:if>

					<tr>
						<th scope="row"><label for="edu_subject"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 프로그램명</label></th>
						<td class="left"><input type="text" size="70" title="프로그램명 입력" id="edu_subject" name="edu_subject" class="ta_input" value="${fn:replace(edusat.edu_subject,'"', '&quot;') }" maxlength="100" />
						<c:if test="${edusat.edu_idx eq ''}">
						<!-- <input type="button" value="프로그램복사" onclick="javascript:window.open('/ncms/edu_gisu/listPop.do','gisu_pop', 'width=800, height=770, resizable=no, scrollbars=no, status=no');" class="btn_bl_zipcode" />-->
						<input type="button" value="기존등록된프로그램복사" onclick="javascript:window.open('/ncms/edusat/listPop.do','gisu_pop', 'width=800, height=770, resizable=no, scrollbars=no, status=no');" class="btn_bl_zipcode" />
						</c:if>
						</td>
					</tr>
					<!-- 
					<tr>
						<th scope="row"><label for="edu_subject">강사명</label></th>
						<td class="left"><input type="text" size="30" title="강사명 입력" id="edu_teacher" name="edu_teacher" class="ta_input" value="${edusat.edu_teacher }" maxlength="50" />
						
						<span class="point fs11">* ex) 윤종우 교수 </span>

						</td>
					</tr>
					 -->

					<tr>
						<th scope="row"><label for="edu_resdate_y"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 신청기간</label></th>
						<td class="left">
							<select id="edu_resdate_y" name="edu_resdate_y" title="시작 사용기간(년)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="2010" end="${nowdate_y+1 }" step="1">
								<option value="${item }" ${edu_resdate_y eq item ? 'selected="selected"' : '' }>${item }년</option>
							</c:forEach>
																	
							</select>
							<select id="edu_resdate_m" name="edu_resdate_m" title="시작 사용기간(월)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="1" end="12" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_resdate_m eq item_value ? 'selected="selected"' : '' }>${item_value }월</option>
							</c:forEach>
							</select>
							<select id="edu_resdate_d" name="edu_resdate_d" title="시작 사용기간(일)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="1" end="31" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_resdate_d eq item_value ? 'selected="selected"' : '' }>${item_value }일</option>
							</c:forEach>
							</select>
							&nbsp;
							<select id="edu_resdate_h" name="edu_resdate_h" title="시작 사용기간(시)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="0" end="23" step="1">
								<option value="${item }" ${edu_resdate_h eq item ? 'selected="selected"' : '' }>${item }시</option>
							</c:forEach>
							</select> : 00분
							
							~ 

							<select id="edu_reedate_y" name="edu_reedate_y" title="종료 사용기간(년)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="2010" end="${nowdate_y+1 }" step="1">
								<option value="${item }" ${edu_reedate_y eq item ? 'selected="selected"' : '' }>${item }년</option>
							</c:forEach>
							</select>
							<select id="edu_reedate_m" name="edu_reedate_m" title="종료 사용기간(월)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="1" end="12" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_reedate_m eq item_value ? 'selected="selected"' : '' }>${item_value }월</option>
							</c:forEach>
							</select>
							<select id="edu_reedate_d" name="edu_reedate_d" title="종료 사용기간(일)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="1" end="31" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_reedate_d eq item_value ? 'selected="selected"' : '' }>${item_value }일</option>
							</c:forEach>
							</select>
							&nbsp;
							<select id="edu_reedate_h" name="edu_reedate_h" title="종료 사용기간(시)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="0" end="23" step="1">
								<option value="${item }" ${edu_reedate_h eq item ? 'selected="selected"' : '' }>${item }시</option>
							</c:forEach>
							</select> : 00분
							
						</td>
					</tr>
					
					<!-- 
					<tr>
						<th scope="row"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" />  <label for="edu_temp1">최대 신청 수량</label></th>
						<td class="left"><input type="text" size="30" onkeyup="onlyNumber(this);" title="최대 신청수량 입력" id="edu_temp1" name="edu_temp1" class="ta_input" value="${edusat.edu_temp1 }" maxlength="4" />
						
						<span class="point fs11">* 숫자만 입력해주세요. </span>

						</td>
					</tr>
					 -->

					<!-- 
					<tr>
						<th scope="row"><label for="edu_sdate_y"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 강의기간</label></th>
						<td class="left">
							<select id="edu_sdate_y" name="edu_sdate_y" title="시작 사용기간(년)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="2010" end="${nowdate_y+1 }" step="1">
								<option value="${item }" ${edu_sdate_y eq item ? 'selected="selected"' : '' }>${item }년</option>
							</c:forEach>
							</select>
							<select id="edu_sdate_m" name="edu_sdate_m" title="시작 사용기간(월)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="1" end="12" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_sdate_m eq item_value ? 'selected="selected"' : '' }>${item_value }월</option>
							</c:forEach>
							</select>
							<select id="edu_sdate_d" name="edu_sdate_d" title="시작 사용기간(일)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="1" end="31" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_sdate_d eq item_value ? 'selected="selected"' : '' }>${item_value }일</option>
							</c:forEach>
							</select>
							
							~ 

							<select id="edu_edate_y" name="edu_edate_y" title="종료 사용기간(년)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="2010" end="${nowdate_y+1 }" step="1">
								<option value="${item }" ${edu_edate_y eq item ? 'selected="selected"' : '' }>${item }년</option>
							</c:forEach>
							</select>
							<select id="edu_edate_m" name="edu_edate_m" title="종료 사용기간(월)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="1" end="12" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_edate_m eq item_value ? 'selected="selected"' : '' }>${item_value }월</option>
							</c:forEach>
							</select>
							<select id="edu_edate_d" name="edu_edate_d" title="종료 사용기간(일)을 선택" class="ta_select">
							<c:forEach var="item" varStatus="i" begin="1" end="31" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_edate_d eq item_value ? 'selected="selected"' : '' }>${item_value }일</option>
							</c:forEach>
							</select>

							
						</td>
					</tr>
					 
					<tr>
						<th scope="row"><label for="edu_week_0"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 강의요일</label></th>
						<td class="left">
							<input type="hidden" name="edu_week" />
							
							<%//매일과 요일에 버튼 비활성화시킬건지 %>
							<c:set var="style_week1" value="" />
							<c:set var="style_week2" value="" />
							
							<c:if test="${edusat.edu_idx ne '' and fn:contains(edusat.edu_week, '9')}">
								<c:set var="style_week2" value="disabled" />
							</c:if>
							<input type="checkbox" id="edu_week_9" name="edu_week_chk" value="9" <c:if test="${fn:contains(edusat.edu_week, '9')}">checked='checked'</c:if> /><label for="edu_week_9">매일</label>
							<input type="checkbox" id="edu_week_0" name="edu_week_chk" value="0" ${style_week2} <c:if test="${fn:contains(edusat.edu_week, '0')}">checked='checked'</c:if> /><label for="edu_week_0">일</label>
							<input type="checkbox" id="edu_week_1" name="edu_week_chk" value="1" ${style_week2} <c:if test="${fn:contains(edusat.edu_week, '1')}">checked='checked'</c:if> /><label for="edu_week_1">월</label>
							<input type="checkbox" id="edu_week_2" name="edu_week_chk" value="2" ${style_week2} <c:if test="${fn:contains(edusat.edu_week, '2')}">checked='checked'</c:if> /><label for="edu_week_2">화</label>
							<input type="checkbox" id="edu_week_3" name="edu_week_chk" value="3" ${style_week2} <c:if test="${fn:contains(edusat.edu_week, '3')}">checked='checked'</c:if> /><label for="edu_week_3">수</label>
							<input type="checkbox" id="edu_week_4" name="edu_week_chk" value="4" ${style_week2} <c:if test="${fn:contains(edusat.edu_week, '4')}">checked='checked'</c:if> /><label for="edu_week_4">목</label>
							<input type="checkbox" id="edu_week_5" name="edu_week_chk" value="5" ${style_week2} <c:if test="${fn:contains(edusat.edu_week, '5')}">checked='checked'</c:if> /><label for="edu_week_5">금</label>
							<input type="checkbox" id="edu_week_6" name="edu_week_chk" value="6" ${style_week2} <c:if test="${fn:contains(edusat.edu_week, '6')}">checked='checked'</c:if> /><label for="edu_week_6">토</label>

						</td>
					</tr>

					<tr>
						<th scope="row"><label for="edu_time">강의시간</label></th>
						<td class="left"><input type="text" size="30" title="강의시간 입력" id="edu_time" name="edu_time" class="ta_input" value="${edusat.edu_time }" maxlength="30" />
						
						<span class="point fs11">* ex) 10:00~12:00</span>
						</td>
					</tr> 
					<tr>
						<th scope="row"><label for="edu_money">참가비</label></th>
						<td class="left"><input type="text" size="30" title="참가비 입력" id="edu_money" name="edu_money" class="ta_input" value="${edusat.edu_money }" maxlength="50" />
						<span class="point fs11">* ex) 무료(교재비 :5,000원) </span>
						</td>
					</tr>
					
					<tr>
						<th scope="row"><label for="edu_target">참가대상</label></th>
						<td class="left">
							<input type="text" size="30" title="참가대상 입력" id="edu_target" name="edu_target" class="ta_input" value="${edusat.edu_target }" maxlength="100" />

							<span class="point fs11">* ex) 초등 4-6학년 </span>
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="edu_temp4">참가대상제한</label></th>
						<td class="left">
							<input type="checkbox" id="edu_temp4" name="edu_temp4" value="Y" ${edusat.edu_temp4 eq 'Y' ? 'checked' : '' } /><label for="edu_temp4">사용</label>
							<span class="point fs11">* 로그인 필수 체크</span>
							
							<span id="span_edu_temp5">
							<select id="edu_temp5_s_y" name="edu_temp5_s_y" title="시작 사용기간(년)을 선택" class="ta_select">
								<option value="">년</option>
							<c:forEach var="item" varStatus="i" begin="1930" end="${nowdate_y+1 }" step="1">
								<c:set var="i_value" value="${(nowdate_y+1)-i.count }" />
								<option value="${i_value }" ${edu_temp5_s_y eq i_value ? 'selected="selected"' : '' }>${i_value }년</option>
							</c:forEach>
							</select>
							<select id="edu_temp5_s_m" name="edu_temp5_s_m" title="시작 사용기간(월)을 선택" class="ta_select">
								<option value="">월</option>
							<c:forEach var="item" varStatus="i" begin="1" end="12" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_temp5_s_m eq item_value ? 'selected="selected"' : '' }>${item_value }월</option>
							</c:forEach>
							</select>
							<select id="edu_temp5_s_d" name="edu_temp5_s_d" title="시작 사용기간(일)을 선택" class="ta_select">
								<option value="">일</option>
							<c:forEach var="item" varStatus="i" begin="1" end="31" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_temp5_s_d eq item_value ? 'selected="selected"' : '' }>${item_value }일</option>
							</c:forEach>
							</select>
							
							부터

							<select id="edu_temp5_e_y" name="edu_temp5_e_y" title="종료 사용기간(년)을 선택" class="ta_select">
								<option value="">년</option>
							<c:forEach var="item" varStatus="i" begin="1930" end="${nowdate_y+1 }" step="1">
								<c:set var="i_value" value="${(nowdate_y+1)-i.count }" />
								<option value="${i_value }" ${edu_temp5_e_y eq i_value ? 'selected="selected"' : '' }>${i_value }년</option>
							</c:forEach>
							</select>
							<select id="edu_temp5_e_m" name="edu_temp5_e_m" title="종료 사용기간(월)을 선택" class="ta_select">
								<option value="">월</option>
							<c:forEach var="item" varStatus="i" begin="1" end="12" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_temp5_e_m eq item_value ? 'selected="selected"' : '' }>${item_value }월</option>
							</c:forEach>
							</select>
							<select id="edu_temp5_e_d" name="edu_temp5_e_d" title="종료 사용기간(일)을 선택" class="ta_select">
								<option value="">일</option>
							<c:forEach var="item" varStatus="i" begin="1" end="31" step="1">
								<c:set var="item_value" value="${item }"></c:set>
								<c:if test="${item < 10 }"><c:set var="item_value" value="0${item }"></c:set></c:if>
								<option value="${item_value }" ${edu_temp5_e_d eq item_value ? 'selected="selected"' : '' }>${item_value }일</option>
							</c:forEach>
							</select>
							생까지 신청가능
							
							</span>
						</td>
					</tr>

					<tr>
						<th scope="row"><label for="edu_inwon"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 참가인원</label></th>
						<td class="left">
							<input type="text" size="5" title="온라인신청인원 입력" id="edu_inwon" name="edu_inwon" class="ta_input" onkeydown="onlyNumber(this)" value="${edusat.edu_inwon }" maxlength="3" /> 명
						</td>
					</tr>

					<tr>
						<th scope="row"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> <label for="edu_receive_type">신청유형</label></th>
						<td class="left">
							<input type="checkbox" name="edu_receive_type" id="edu_receive_type_0" value="0" <c:if test="${fn:contains(edusat.edu_receive_type, '0')}">checked='checked'</c:if> /><label for="edu_receive_type_0">방문</label>
							<input type="checkbox" name="edu_receive_type" id="edu_receive_type_1" value="1" <c:if test="${fn:contains(edusat.edu_receive_type, '1')}">checked='checked'</c:if> /><label for="edu_receive_type_1">전화</label>
							<input type="checkbox" name="edu_receive_type" id="edu_receive_type_2" value="2" <c:if test="${fn:contains(edusat.edu_receive_type, '2')}">checked='checked'</c:if> onclick="lotTypeShow();" /><label for="edu_receive_type_2">인터넷</label>
						</td>
					</tr>
					
					<tr id="tr_lot_type" style="disaply:none;">
						<th scope="row"><label for="edu_lot_type_1"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 인터넷 신청방식</label></th>
						<td class="left">
							<input type="radio" name="edu_lot_type" id="edu_lot_type_1" value="1" <c:if test="${edu_lot_type eq '1'}">checked='checked'</c:if> style="vertical-align:middle;" onclick="lotTypeChk(this.value);" /><label for="edu_lot_type_1">추첨식</label>
							<input type="radio" name="edu_lot_type" id="edu_lot_type_2" value="2" <c:if test="${edu_lot_type eq '2'}">checked='checked'</c:if> style="vertical-align:middle;" onclick="lotTypeChk(this.value);" /><label for="edu_lot_type_2">선착순</label>
							| 대기인원수 : <input type="text" size="3" title="대기자인원 입력" id="edu_awaiter" name="edu_awaiter" class="ta_input" onkeydown="onlyNumber(this)" value="${edusat.edu_awaiter }" maxlength="2" /> 명

							| <input type="checkbox" name="edu_login" id="edu_login" value="Y" <c:if test="${edusat.edu_login eq 'Y' or empty edusat.edu_idx}">checked='checked'</c:if> /><label for="edu_login">로그인 필수</label>
						</td>
					</tr>


					<tr>
						<th scope="row"><label for="edu_temp2">프로그램장소</label></th>
						<td class="left">
							<input type="text" size="30" title="프로그램장소 입력" id="edu_temp2" name="edu_temp2" class="ta_input" value="${edusat.edu_temp2 }" maxlength="30" />

							<span class="point fs11">* ex) 시청각실 </span>
						</td>
					</tr>
					-->
					<tr>
							<th scope="row"><label for="edu_temp3">대표이미지</label></th>
							<td class="left">
							
								<input type="file" size="70" id="edu_temp3_file" name="edu_temp3_file" title="삽입할 파일 찾아보기" class="ta_input_file" />
								<c:if test="${!empty edusat.edu_temp3}">
								<br />
								<img alt="${edusat.edu_temp3 }" src="/data/edusat/${edusat.edu_temp3 }">
								${edusat.edu_temp3 }&nbsp;<input type='checkbox' id='edu_temp3_chk' name='edu_temp3_chk' value='Y' title="삭제" value='${edusat.edu_temp3 }' /><label for="edu_temp3_chk">삭제</label>
								</c:if>
							</td>
					</tr>
					
					
					<!-- <tr>
						<th scope="row"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 신청자 입력</th>
						<td class="left">
							<input type="radio" name="edu_ptcp_yn" id="edu_ptcp_yn_N" value="N" <c:if test="${edu_ptcp_yn eq 'N'}">checked='checked'</c:if> style="vertical-align:middle;" onclick="ptcp_yn_chk(this.value);" /><label for="edu_ptcp_yn_N">미적용</label>
							<input type="radio" name="edu_ptcp_yn" id="edu_ptcp_yn_Y" value="Y" <c:if test="${edu_ptcp_yn eq 'Y'}">checked='checked'</c:if> style="vertical-align:middle;" onclick="ptcp_yn_chk(this.value);" /><label for="edu_ptcp_yn_Y">적용</label>
							&nbsp;&nbsp;&nbsp;&nbsp;| 신청가능 인원수 :
							<select name="edu_ptcp_cnt"  id="edu_ptcp_cnt" class="ta_select2" ${edu_ptcp_yn eq 'N' ? 'disabled=disabled' : '' }>
							<c:forEach var="t" begin="1" end="10" step="1">
								<option value="${t }" ${edusat.edu_ptcp_cnt eq t ? 'selected=selected' : '' }>${t } 명</option>					
							</c:forEach>			
							</select>
						</td>
					</tr> -->
					<input type="hidden" name="edu_ptcp_yn" value="N" />
					

					
					
					<tr>
						<th scope="row"><label for="edu_field1_yn">접수항목설정</label></th>
						<td class="left">
							<table style="width:370px;">
							<colgroup>
							<col width="" />
							<col width="25%" />
							<col width="25%" />
							<col width="25%" />
							</colgroup>
							<thead>
							<tr>
								<th>항목</th>
								<th>사용안함</th>
								<th>사용</th>
								<th>사용(필수입력)</th>
							</tr>
							</thead>
							<tbody>
							<tr>
								<th>기관명</th>
								<td class="center">
									<input type="radio" id="edu_field1_yn_n" name="edu_field1_yn" value="N" ${edusat.edu_field1_yn eq 'N' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field1_yn_y" name="edu_field1_yn" value="Y" ${edusat.edu_field1_yn eq 'Y' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field1_yn_r" name="edu_field1_yn" value="R" ${edusat.edu_field1_yn eq 'R' ? 'checked="checked"' : '' } />
								</td>
							</tr>
							<tr>
								<th>주소(기관주소)</th>
								<td class="center">
									<input type="radio" id="edu_field2_yn_n" name="edu_field2_yn" value="N" ${edusat.edu_field2_yn eq 'N' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field2_yn_y" name="edu_field2_yn" value="Y" ${edusat.edu_field2_yn eq 'Y' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field2_yn_r" name="edu_field2_yn" value="R" ${edusat.edu_field2_yn eq 'R' ? 'checked="checked"' : '' } />
								</td>
							</tr>
							<tr>
								<th>첨부파일</th>
								<td class="center">
									<input type="radio" id="edu_field3_yn_n" name="edu_field3_yn" value="N" ${edusat.edu_field3_yn eq 'N' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field3_yn_y" name="edu_field3_yn" value="Y" ${edusat.edu_field3_yn eq 'Y' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
								</td>
							</tr>
							<tr>
								<th>교육대상 및 인원</th>
								<td class="center">
									<input type="radio" id="edu_field4_yn_n" name="edu_field4_yn" value="N" ${edusat.edu_field4_yn eq 'N' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field4_yn_y" name="edu_field4_yn" value="Y" ${edusat.edu_field4_yn eq 'Y' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
								</td>
							</tr>

							<tr>
								<th>교통사고 확인</th>
								<td class="center">
									<input type="radio" id="edu_field5_yn_n" name="edu_field5_yn" value="N" ${edusat.edu_field5_yn eq 'N' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field5_yn_y" name="edu_field5_yn" value="Y" ${edusat.edu_field5_yn eq 'Y' ? 'checked="checked"' : '' } />
								</td>
								
								<td class="center">
								<%--
									<input type="radio" id="edu_field5_yn_r" name="edu_field5_yn" value="R" ${edusat.edu_field5_yn eq 'R' ? 'checked="checked"' : '' } />
									 --%>
								</td>
								
							</tr>
							<tr>
								<th>비밀번호</th>
								<td class="center">
									<input type="radio" id="edu_field6_yn_n" name="edu_field6_yn" value="N" ${edusat.edu_field6_yn eq 'N' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field6_yn_y" name="edu_field6_yn" value="Y" ${edusat.edu_field6_yn eq 'Y' ? 'checked="checked"' : '' } />
								</td>
								
								<td class="center">
								<%--
									<input type="radio" id="edu_field6_yn_r" name="edu_field6_yn" value="R" ${edusat.edu_field6_yn eq 'R' ? 'checked="checked"' : '' } />
								--%>
								</td>
								 
							</tr>
							<!-- 
							<tr>
								<th>주소</th>
								<td class="center">
									<input type="radio" id="edu_field7_yn_n" name="edu_field7_yn" value="N" ${edusat.edu_field7_yn eq 'N' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field7_yn_y" name="edu_field7_yn" value="Y" ${edusat.edu_field7_yn eq 'Y' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field7_yn_r" name="edu_field7_yn" value="R" ${edusat.edu_field7_yn eq 'R' ? 'checked="checked"' : '' } />
								</td>
							</tr>
							<tr>
								<th>기타</th>
								<td class="center">
									<input type="radio" id="edu_field8_yn_n" name="edu_field8_yn" value="N" ${edusat.edu_field8_yn eq 'N' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field8_yn_y" name="edu_field8_yn" value="Y" ${edusat.edu_field8_yn eq 'Y' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field8_yn_r" name="edu_field8_yn" value="R" ${edusat.edu_field8_yn eq 'R' ? 'checked="checked"' : '' } />
								</td>
							</tr>
							<tr>
								<th>자녀명</th>
								<td class="center">
									<input type="radio" id="edu_field9_yn_n" name="edu_field9_yn" value="N" ${edusat.edu_field9_yn eq 'N' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field9_yn_y" name="edu_field9_yn" value="Y" ${edusat.edu_field9_yn eq 'Y' ? 'checked="checked"' : '' } />
								</td>
								<td class="center">
									<input type="radio" id="edu_field9_yn_r" name="edu_field9_yn" value="R" ${edusat.edu_field9_yn eq 'R' ? 'checked="checked"' : '' } />
								</td>
							</tr>
							 -->
							</tbody>
							</table>
						</td>
					</tr>
					
					<tr>
						<th scope="row">선정완료 발송 메시지</th>
						<td><textarea rows="10" cols="100" class="ta_input" name="edu_lib">${edusat.edu_lib }</textarea>
						</td>
					</tr>
					
					<tr>
						<th scope="row" colspan="2" style="height:40px;">프로그램내용</th>
					</tr>
					<tr>
						<td colspan="2">
								<div id="contentArea"></div>
								<div id="edu_content_org" style="display:none;">${edusat.edu_content}</div>
						</td>
					</tr>
					
					
					<tr>
						<th scope="row"><label for="end_chk">사용여부</label></th>
						<td class="left">
							<select id="end_chk" name="end_chk" title="프로그램 사용여부 선택" class="ta_select">
								<option value="Y" ${edusat.end_chk eq 'Y' ? 'selected="selected"' : '' }>사용중</option>
								<option value="T" ${edusat.end_chk eq 'T' ? 'selected="selected"' : '' }>신청마감</option>
								<option value="N" ${edusat.end_chk eq 'N' ? 'selected="selected"' : '' }>사용중지</option>									
							</select>

							<span class="point fs11">* '사용중' 인 프로그램만 사용자 쪽에 노출됩니다. </span>
						</td>
					</tr>

					<!--tr>
						<th scope="row"><label for="edu_chk">사용여부</label></th>
						<td class="left">
							<select id="edu_chk" name="edu_chk" title="설문 사용여부 선택">
								<option value="준비중" >준비중</option>
								<option value="신청중" >신청중</option>
								<option value="온라인마감" >온라인마감</option>
								<option value="방문마감" >방문마감</option>
								<option value="강제마감" >강제마감</option>
								</select>

								<span class="point fs11">* 선택시 사용자페이지에 바로 적용됩니다. </span>
							</td>
						</tr-->

						</table>
					</fieldset>

					<div class="contoll_box">
						<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="취소" onclick="location.href='${prepage}';" class="btn_wh_default" /></span>
					</div>
				
				</form>

	</div>
	<!-- 내용들어가는곳 -->
	
<script type="text/javascript">
$(document).ready(function(){ 
	SEditorCreate();
});

var oEditors = [];

</script>