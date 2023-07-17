<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>프로그램 신청정보</title>
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/all.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/sub_layout.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/design_default.css" />
<link rel="Stylesheet" type="text/css" href="/nanum/nninc/common/js/jquery-ui.css" />
<link href="http://fonts.googleapis.com/css?family=Armata" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="/nanum/ncms/common/js/common_design.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-cookie.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-rightClick.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_design.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>

<script type="text/javascript" src="/nanum/ncms/common/js/design_default.js"></script>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="/nfu//NFU_conf.js"></script>
<link type="text/css" rel="stylesheet" href="/nfu//NFU_css.css">
<script type="text/javascript" src="/nfu/NFU_class.js" charset="utf-8"></script>
<style>
.text1.text2 {
    width: 70px;
    display: inline-block;
}
th label {
	font-weight: bold;
	color : #000;
}
span.text1.text3 {
	font-size : 11px;
	color : red;
}
span.text1 {
	font-size : 12px;
}
</style>

<script>

function sample6_execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var fullAddr = ''; // 최종 주소 변수
			var extraAddr = ''; // 조합형 주소 변수

			// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				fullAddr = data.roadAddress;

			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				fullAddr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 조합한다.
			if(data.userSelectedType === 'R'){
				//법정동명이 있을 경우 추가한다.
				if(data.bname !== ''){
					extraAddr += data.bname;
				}
				// 건물명이 있을 경우 추가한다.
				if(data.buildingName !== ''){
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
				//fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
			}
			

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('es_zipcode').value = data.zonecode; //5자리 새우편번호 사용
			document.getElementById('es_addr1').value = fullAddr;
			if(extraAddr != ""){
				document.getElementById('es_addr2').value = "("+extraAddr+")";
			}

			// 커서를 상세주소 필드로 이동한다.
			document.getElementById('es_addr2').focus();
		}
	}).open();
}

function setNum(obj) {
	var inputVal = obj.value;
	var regex = /[^0-9]/g;
	obj.value = inputVal.replace(regex, "");
}
</script>
	
</head>

<body>
<script type="text/javascript">
//<![CDATA[
// https://newtoki131.com/webtoon/13999484?toon=%EC%9D%BC%EB%B0%98%EC%9B%B9%ED%88%B0
function registGo(eForm){
	
	//var jumin = eForm.es_jumin_y.value+eForm.es_jumin_m.value+eForm.es_jumin_d.value;
	//eForm.es_jumin.value = jumin;
	
	if(!valueChk(eForm.es_name, "성명")){return false;}
	<c:if test="${gubun_num ne '2'}">
	if(!valueChk(eForm.es_bphone1, "유선전화 첫번째")){return false;}
	if(!valueChk(eForm.es_bphone2, "유선전화 두번째")){return false;}
	if(!valueChk(eForm.es_bphone3, "유선전화 세번째")){return false;}
	</c:if>
	if(!valueChk(eForm.es_phone1, "휴대전화 첫번째")){return false;}
	if(!valueChk(eForm.es_phone2, "휴대전화 두번째")){return false;}
	if(!valueChk(eForm.es_phone3, "휴대전화 세번째")){return false;}
	if(!valueChk(eForm.es_etc, "신청수량")){return false;}
	if(eForm.es_etc.value > ${edusat.edu_temp1}){ alert('최대 갯수는 ${edusat.edu_temp1}개 입니다!');return false;}
	
	<c:if test="${edusat.edu_field1_yn eq 'R'}">
		if(!valueChk(eForm.es_name2, "기관명")){return false;}
	</c:if>
	<c:if test="${edusat.edu_field2_yn eq 'R'}">
		if(!valueChk(eForm.es_zipcode, "우편번호")){return false;}
		if(!valueChk(eForm.es_addr1, "주소")){return false;}
		if(!valueChk(eForm.es_addr2, "상세주소")){return false;}
	</c:if>
	
	if(!valueChk(eForm.es_memo, "신청사연")){return false;}
	
	try{
		nfu_upload();
		return false;
	}catch(e){return true;}
	
	return false;
}

function valueChk(obj, objName){	//text필드
	if(obj.value.split(" ").join("") == ""){
		alert(objName+"을(를) 입력하세요");
		try{
			obj.focus();
			return false;
		}catch(e){
			return false;
		}
	}else{
		return true;
	}
}


function SetNum(obj){			//숫자만 입력
	val=obj.value;
	re=/[^0-9]/gi;
	obj.value=val.replace(re,"");
}
//]]>
</script>

<c:set var="es_status_str" value="신청완료"></c:set>
<c:choose>
	<c:when test="${edusatreq.es_status eq '9' }">
		<c:set var="es_status_str" value="취소"></c:set>
	</c:when>
	<c:when test="${edusatreq.es_status eq '1' }">
		<c:set var="es_status_str" value="<font color='#0000FF'>당첨</font>"></c:set>
	</c:when>
	<c:when test="${edusatreq.es_status eq '2' }">
		<c:set var="es_status_str" value="낙첨"></c:set>
	</c:when>
</c:choose>

<!-- 전체싸는 박스 -->
<div style="padding-left:15px;padding-top:15px;">

	<!-- 중간 영역 -->
	<div>

		<!-- 내용영역 -->
		<div  style="width: 650px;">

			<div id="contents_head"  style="width: 650px;"> 
				<div id="contents_title">
					<h1 class="tit"><span>신청자정보</span></h1>
				</div>
			</div>
			
			<form id= "frm_list" action="requestOk.do"  method='post' onsubmit="return registGo(this);">
				<div>
					<input type="hidden" name="edu_idx" value="${edusatreq.edu_idx }" />
					<input type="hidden" name="es_idx" value="${edusatreq.es_idx }" />
					<input type="hidden" name="es_jumin" value="">
				</div>

				<fieldset>
					<table cellspacing="0" class="bbs_common bbs_default" style="width: 650px;">
					<caption>관리 서식</caption>
					<colgroup>
					<col width="100" />
					<col width="100" />
					<col />
					<col />
					</colgroup>
					<tbody>
					<tr>
						<th scope="row"><label for="es_name"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 성명</label></th>
						<td class="left" colspan="3"><input type="text" name="es_name" id="es_name" style="width:100px" class="ta_input" maxlength="20" value="${edusatreq.es_name }" /></td>
					</tr>
					<c:if test="${gubun_num ne '2' }">
					<tr>
						<th scope="row"><label for="es_name"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 신청자구분</label></th>
						<td class="left" colspan="3">
							<%/*
							<input type="radio" name="es_temp1" id="es_temp1_i" value="i" checked="checked" /><label for="es_temp1_i" onclick="nonetoblock(1)">개인</label>
							<input type="radio" name="es_temp1" id="es_temp1_a" value="a" ${edusatreq.es_temp1 eq 'a' ? 'checked' : '' } onclick="nonetoblock(2)"/><label for="es_temp1_a">기관</label>
							<input type="radio" name="es_temp1" id="es_temp1_s" value="s" ${edusatreq.es_temp1 eq 's' ? 'checked' : '' } onclick="nonetoblock(2)" /><label for="es_temp1_s">학교</label>
							*/ %>
							<%/*
							<input type="radio" name="es_temp1" id="es_temp1_i" value="i" checked="checked" /><label for="es_temp1_i" >유치원</label>
							<input type="radio" name="es_temp1" id="es_temp1_a" value="a" ${edusatreq.es_temp1 eq 'a' ? 'checked' : '' } /><label for="es_temp1_a">어린이집</label>
							<input type="radio" name="es_temp1" id="es_temp1_s" value="s" ${edusatreq.es_temp1 eq 's' ? 'checked' : '' }  /><label for="es_temp1_s">기타</label>
							*/%>
							<%//<input type="radio" name="es_temp1" id="es_temp1_i" value="i" checked="checked" /><label for="es_temp1_i" >초등학교</label> %>
						
							<input type="radio" name="es_temp1" id="es_temp1_i" value="i" checked="checked" /><label for="es_temp1_i" >유치원</label>
							<input type="radio" name="es_temp1" id="es_temp1_a" value="a" ${edusatreq.es_temp1 eq 'a' ? 'checked' : '' } /><label for="es_temp1_a">어린이집</label>
							<input type="radio" name="es_temp1" id="es_temp1_c" value="c" ${edusatreq.es_temp1 eq 'c' ? 'checked' : '' } /><label for="es_temp1_c">지역아동센터</label>
							<input type="radio" name="es_temp1" id="es_temp1_s" value="s" ${edusatreq.es_temp1 eq 's' ? 'checked' : '' }  /><label for="es_temp1_s">기타</label>
						</td>
					</tr>
					</c:if>
					<c:if test="${gubun_num eq '2' }">
					<tr>
						<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /><label for="es_temp3">사번</label></th>
						<td class="left" colspan="3">
							<input type="text" name="es_temp3" id="es_temp3" class="ta_input" size="40" maxlength="100" value="${edusatreq.es_temp3 }" />
						</td>
					</tr>
					<tr>
						<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /><label for="es_temp4">직급</label></th>
						<td class="left" colspan="3">
							<input type="text" name="es_temp4" id="es_temp4" class="ta_input" size="40" maxlength="100" value="${edusatreq.es_temp4 }" />
						</td>
					</tr>
					<tr>
						<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /><label for="es_temp5">팀명</label></th>
						<td class="left" colspan="3">
							<input type="text" name="es_temp5" id="es_temp5" class="ta_input" size="40" maxlength="100" value="${edusatreq.es_temp5 }" />
						</td>
					</tr>
					</c:if>
					
					<c:if test="${gubun_num ne '1' and gubun_num ne '2' and edusat.edu_field1_yn ne 'R' and edusat.edu_field1_yn ne 'Y'}">
					<tr id="es_name2_wrab">
						<th scope="row"><label for="es_name2"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> 기관명 </label></th>
						<td class="left" colspan="2">
							<input type="text" size="20" id="es_name2" name="es_name2" class="ta_input middle" value="${edusatreq.es_name2}" maxlength="20" />
							
						</td>
					</tr>
					</c:if>
					
					<c:if test="${gubun_num ne '2' }">
					<tr>
						<th scope="row"><label for="es_bphone1"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 유선전화</label></th>
						<td class="left" colspan="3">
							<input type="text" name="es_bphone1" id="es_bphone1" style="width:45px" class="ta_input" maxlength="4" value="${edusatreq.es_bphone1 }" /> - 
							<input type="text" name="es_bphone2" id="es_bphone2" style="width:45px" class="ta_input" maxlength="4" value="${edusatreq.es_bphone2 }" /> - 
							<input type="text" name="es_bphone3" id="es_bphone3" style="width:45px" class="ta_input" maxlength="4" value="${edusatreq.es_bphone3 }" />
						</td>
					</tr>
					</c:if>
					<tr>
						<th scope="row"><label for="es_phone1"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 휴대전화</label></th>
						<td class="left" colspan="3">
							<input type="text" name="es_phone1" id="es_phone1" style="width:45px" class="ta_input" maxlength="4" value="${edusatreq.es_phone1 }" /> - 
							<input type="text" name="es_phone2" id="es_phone2" style="width:45px" class="ta_input" maxlength="4" value="${edusatreq.es_phone2 }" /> - 
							<input type="text" name="es_phone3" id="es_phone3" style="width:45px" class="ta_input" maxlength="4" value="${edusatreq.es_phone3 }" />
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="es_email"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 이메일</label></th>
						<td class="left" colspan="3">
							<input type="text" name="es_email" id="es_email" class="ta_input" size="40" maxlength="100" value="${edusatreq.es_email }" /> 
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="es_temp2"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 수령자명</label></th>
						<td class="left" colspan="3">
							<input type="text" name="es_temp2" id="es_temp2" class="ta_input" size="40" maxlength="100" value="${edusatreq.es_temp2 }" /> 
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="es_etc"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> ${gubun_num ne '2' ? '신청수량' : '전교생 인원' }</label></th>
						<td class="left" colspan="3">
							<input type="text" name="es_etc" id="es_etc" style="width:45px" class="ta_input" maxlength="4" value="${edusatreq.es_etc }" />
							${gubun_num eq '2' ? '명<span class="text1 text3"> * 전교생인원을 모르실경우 학교알리미 사이트에 접속해서 확인해주세요.</span>' : '개' } 
						</td>
					</tr>
					
					<c:if test="${edusat.edu_field1_yn eq 'R' or edusat.edu_field1_yn eq 'Y'}">
					<tr>
						<th scope="row">${edusat.edu_field1_yn eq 'R' ? '<img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> ' : '' }<label for="es_name2">기관명</label></th>
						<td colspan="2" class="left">
							<input type="text" size="20" id="es_name2" name="es_name2" class="ta_input middle" value="${edusatreq.es_name2}" maxlength="20" />
							
						</td>
					</tr>
					</c:if>
					<tr>
						<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /><label for="es_addr1">주소</label></th>
						<td colspan="2"class="left">
							<style>
								#Search_Form {overflow:auto; height:150px; border:1px solid #cdcdcd; margin-top:3px; margin-right:10px; padding:6px;}
								#Addr_search2 span.text1 {font-size:0.9em; color:#ad6767;}
							</style>
	
						<div id="Addr_search1" style="display: block;">
							<input type="text" size="7" title="우편번호 다섯자리" id="es_zipcode" name="es_zipcode" class="input_box board_input" value="${edusatreq.es_zipcode }" maxlength="5" >
							<input type="button" value="우편번호찾기" class="ct_bt01" onclick="sample6_execDaumPostcode();">
							<span class="text1">* 우편번호</span>
								
							<div class="pt3">
							<input type="text" size="40" title="주소" id="es_addr1" name="es_addr1" class="input_box board_input" value="${edusatreq.es_addr1 }" />
							<span class="text1">* 시·도 + 시·군·구 + 읍·면 + 도로명</span><br> 
							<input type="text" size="40" title="상세주소" id="es_addr2" name="es_addr2" class="input_box board_input" value="${edusatreq.es_addr2 }" maxlength="100"  />
							<span class="text1">* 상세주소</span><br>
							</div>
						</div>
						</td>
					</tr>
					
				<c:if test="${edusat.edu_field4_yn eq 'R' or edusat.edu_field4_yn eq 'Y'}">
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_ptcp_cnt">교육대상 및 인원<br/>(해당사항만 입력)</label></th>
					<td class="left">1 ~ 6학년</td>
					<td colspan="2" class="left">
						<input type="radio" name="es_video" id="es_video_Y" ${(edusatreq.es_video eq 'Y') ? 'checked' : '' } value="Y" /> <label for="es_video_Y">신청</label>
						<input type="radio" name="es_video" id="es_video_N" ${(edusatreq.es_video ne 'Y') ? 'checked' : '' } value="N" /> <label for="es_video_N">미신청</label>
						<span class="text1">* 일부학년만 신청도 가능합니다.</span><br>					
					</td>
				</tr>
				<%/*
				<tr>
					<th scope="row" rowspan="2"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_ptcp_cnt">교육대상 및 인원<br/>(해당사항만 입력)</label></th>
					<td>1, 2학년</td>
					<td colspan="2">
						<div class="mb10">
							<span class="text1 text2">1학년</span>
							<input type="text" size="20" id="es_fgrade" name="es_fgrade" class="ta_input middle" value="${edusatreq.es_fgrade }" maxlength="4" onkeyup="setNum(this)" /> 명
							<input type="text" size="20" id="es_fgrade_class" name="es_fgrade_class" class="ta_input middle" value="${edusatreq.es_fgrade_class }" maxlength="4" onkeyup="setNum(this)" /> 개반
						</div>
						<div>
							<span class="text1 text2">2학년</span>
							<input type="text" size="20" id="es_sgrade" name="es_sgrade" class="ta_input middle" value="${edusatreq.es_sgrade }" maxlength="4" onkeyup="setNum(this)" /> 명
							<input type="text" size="20" id="es_sgrade_class" name="es_sgrade_class" class="ta_input middle" value="${edusatreq.es_sgrade_class }" maxlength="4" onkeyup="setNum(this)" /> 개반
						</div>						
					</td>
				</tr>
				
				<tr>
					<td>3, 6학년<br/>(교육영상 시청)</td>
					<td colspan="2">
						<input type="radio" name="es_video" id="es_video_Y" ${(edusatreq.es_video eq 'Y') ? 'checked' : '' } value="Y" /> <label for="es_video_Y">신청</label>
						<input type="radio" name="es_video" id="es_video_N" ${(edusatreq.es_video ne 'Y') ? 'checked' : '' } value="N" /> <label for="es_video_N">미신청</label>
						<span class="text1">* 교육영상 시청은 학교 전체 학생 기준으로 진행됩니다.</span><br>					
					</td>
				</tr>
				*/%>
				<%--
				<tr>
					<td>유치원</td>
					<td colspan="2">
						<div class="mb10">
							<span class="text1 text2">6세</span>
							<input type="text" size="20" id="es_kgrade" name="es_kgrade" class="ta_input middle" value="${edusatreq.es_kgrade }" maxlength="2" onkeyup="setNum(this)" /> 명
							<input type="text" size="20" id="es_kgrade_class" name="es_kgrade_class" class="ta_input middle" value="${edusatreq.es_kgrade_class }" maxlength="2" onkeyup="setNum(this)" /> 개반
						</div>
						<div class="mb10">
							<span class="text1 text2">7세학년</span>
							<input type="text" size="20" id="es_cgrade" name="es_cgrade" class="ta_input middle" value="${edusatreq.es_cgrade }" maxlength="2" onkeyup="setNum(this)" /> 명
							<input type="text" size="20" id="es_cgrade_class" name="es_cgrade_class" class="ta_input middle" value="${edusatreq.es_cgrade_class }" maxlength="2" onkeyup="setNum(this)" /> 개반
						</div>
						<!-- 
						<div>
							<span class="text1 text2">기타<br/>(5세 미만)</span>
							<input type="text" size="20" id="es_ograde" name="es_ograde" class="ta_input middle" value="${edusatreq.es_ograde }" maxlength="2" onkeyup="setNum(this)" /> 명
						</div>					
						 -->
					</td>
				</tr>
				 --%>
				</c:if>
				<c:if test="${edusat.edu_field5_yn eq 'R' or edusat.edu_field5_yn eq 'Y'}">
				<tr>
					<th scope="row">${edusat.edu_field5_yn eq 'R' ? '<img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> ' : '' }교통사고<br/>(1년이내)</th>
					<td colspan="2">
						<label><input type="radio" value="Y" name="es_problem_yn" onclick="document.getElementById('es_problem_wrap').style.display='inline'" id="es_problem_y" ${edusatreq.es_problem_yn eq 'Y' ? 'checked' : '' } /> 예</label>
						<span style="display: ${edusatreq.es_problem_yn eq 'Y' ? 'inline' : 'none' }; margin:0px 5px" id="es_problem_wrap">총 &nbsp;<input type="text" value="${edusatreq.es_problem }" name="es_problem" id="es_problem" class="board_input" /> 건</span>
						<label><input type="radio" value="N" name="es_problem_yn" onclick="document.getElementById('es_problem_wrap').style.display='none'" id="es_problem_n" ${edusatreq.es_problem_yn ne 'Y' ? 'checked' : '' } /> 아니오</label>
					</td>
				</tr>
				</c:if>
				
				<c:if test="${gubun_num eq '2' }">
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_etc">학교와의 관계를<br/> 선택해주세요</label></th>
					<td colspan="2" >
						<input type="radio" name="es_temp6" id="es_temp6_1" ${(edusatreq.es_temp6 eq '1') ? 'checked' : '' } value="1" /> <label for="es_temp6_1">자녀의 학교</label>
						<input type="radio" name="es_temp6" id="es_temp6_2" ${(edusatreq.es_temp6 eq '2') ? 'checked' : '' } value="2" /> <label for="es_temp6_2">친척 및 지인자녀의 학교</label>
						<input type="radio" name="es_temp6" id="es_temp6_3" ${(edusatreq.es_temp6 eq '3') ? 'checked' : '' } value="3" /> <label for="es_temp6_3">본인의 모교</label>
						<input type="radio" name="es_temp6" id="es_temp6_4" ${(edusatreq.es_temp6 eq '4') ? 'checked' : '' } value="4" /> <label for="es_temp6_4">기타</label>
					</td>
				</tr>
				
				<tr>
					<th scope="row"><label for="es_temp7">자녀의 성명</label></th>
					<td class="left" colspan="2" >
						 <input type="text" size="40" title="자녀의성명" id="es_temp7" name="es_temp7" class="ta_input middle"  maxlength="100" value="${edusatreq.es_temp7 }" />
					</td>
				</tr>
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_temp8">담당교사의<br/>성명</label></th>
					<td class="left" colspan="2" >
						<input type="text" size="40" title="담임교사의성명" id="es_temp8" name="es_temp8" class="ta_input middle"  maxlength="100" value="${edusatreq.es_temp8 }" />
					</td>
				</tr>
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_temp9_1">담당교사의<br/>연락처</label></th>
					<td class="left" colspan="2" >
						<input type="text" title="담임교사의연락처" name="es_temp9_1" id="es_temp9_1" style="width:45px" class="ta_input" maxlength="4" value="${edusatreq.es_temp9_1 }" /> - 
						<input type="text" title="담임교사의연락처" name="es_temp9_2" id="es_temp9_2" style="width:45px" class="ta_input" maxlength="4" value="${edusatreq.es_temp9_2 }" /> - 
						<input type="text" title="담임교사의연락처" name="es_temp9_3" id="es_temp9_3" style="width:45px" class="ta_input" maxlength="4" value="${edusatreq.es_temp9_3 }" />
					</td>
				</tr>
				
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_etc">나눔식 <br/>가능여부를<br/>선택해주세요</label></th>
					<td colspan="2" >
						<input type="radio" name="es_temp10" id="es_temp10_1" ${(edusatreq.es_temp10 eq '1') ? 'checked' : '' } value="1" /> <label for="es_temp10_1">나눔식 가능</label>
						<input type="radio" name="es_temp10" id="es_temp10_2" ${(edusatreq.es_temp10 eq '2') ? 'checked' : '' } value="2" /> <label for="es_temp10_2">나눔식 불가능</label>
						<span class="text1 text3"> * 나눔식이 불가능할 경우 우선순위에서 제외될 수 있습니다.</span>
					</td>
				</tr>
				</c:if>
					
					<tr>
						<th scope="row"> <label for="es_memo">신청사연</label></th>
						<td colspan="3" class="left">
							<textarea name="es_memo" style="width:95%;height:80px">${edusatreq.es_memo}</textarea>
						</td>
					</tr>
					
					<c:if test="${edusat.edu_field3_yn eq 'R' or edusat.edu_field3_yn eq 'Y'}">
					<tr>
						<th scope="row">첨부파일</th>
						<td style="padding:5px;" colspan="2">
							<c:forEach var="t" begin="1" end="5" step="1">
								<c:set var="file_name">es_file${t}</c:set>
								<c:set var="es_file_name">${edusatreq[file_name]}</c:set>
								<input type="hidden" id="es_file${t}" name="es_file${t}" value="${es_file_name}" />
								<c:if test="${es_file_name ne ''}">
									<a href='/data/edusat/${edusatreq[file_name]}' download>
										<img src="/data/edusat/${edusatreq[file_name]}" alt="${edusatreq[file_name]}" style="max-width:300px;"/>
										${es_file_name}
									<a/>
									<!-- 
									<a href="#delete" onclick="delete_ok('${edusatreq.es_idx}','${edusatreq.edu_idx}','${t}','${del_rtn_page}');" onkeyup="delete_ok('${edusatreq.es_idx}','${edusatreq.edu_idx}','${t}','${del_rtn_page}');" title="첨부파일 삭제하기">
									<img src="/nanum/site/board/nninc_simple/img/dr_del_bt.gif" alt="첨부파일 삭제하기" /></a><br />
									 -->
									
									<c:set var= "file_count" value="${file_count + 1}"/>
								</c:if>
							</c:forEach>
			
						</td>
					</tr>
					</c:if>
				
					
					<tr>
						<th scope="row"><label>신청상태</label></th>
						<td class="left" colspan="3">
						<select id="es_status" name="es_status" title="신청상태변경" class="ta_select">
								<option value="0" ${edusatreq.es_status eq '0' ? 'selected="selected"' : '' }>신청완료</option>
								<option value="9" ${edusatreq.es_status eq '9' ? 'selected="selected"' : '' }>신청취소</option>
								<option value="3" ${edusatreq.es_status eq '3' ? 'selected="selected"' : '' }>선정완료</option>
								<option value="4" ${edusatreq.es_status eq '4' ? 'selected="selected"' : '' }>선정미완료</option>
<c:if test="${edusat.edu_lot_type eq '1' }">
								<option value="1" ${edusatreq.es_status eq '1' ? 'selected="selected"' : '' }>당첨(추첨식)</option>
								<option value="2" ${edusatreq.es_status eq '2' ? 'selected="selected"' : '' }>낙첨(추첨식)</option>
</c:if>
						</select>
						</td>
					</tr>
					<tr>
						<th scope="row"><label>신청일</label></th>
						<td class="left" colspan="3">${edusatreq.es_wdate}</td>
					</tr>
					
					</tbody>
					</table>
				</fieldset>

				<div class="contoll_box">
					<span><input type="submit" value="수정" class="btn_bl_default" /></span> <span><input type="button" value="창닫기" onclick="javascript: self.close();" class="btn_wh_default" /></span>
				</div>
			</form>

		</div>
		<!-- 내용들어가는곳 -->

		</div>
		<!-- //내용영역 -->


</div>
</div>
<script type="text/javascript">
function nonetoblock(param) {
	<c:if test="${gubun_num ne '1' and edu_field1_yn ne 'R' and edusat.edu_field1_yn ne 'Y'}">
	if(param == 1) {
		document.getElementById('es_name2_wrab').style.display = 'none';
	}else{
		document.getElementById('es_name2_wrab').style.display = 'table-row';
	}
	</c:if>
}
</script>
</body>
</html>