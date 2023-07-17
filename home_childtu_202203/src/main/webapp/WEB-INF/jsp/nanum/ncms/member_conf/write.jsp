<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>

<h1 class="tit"><span>회원가입</span> 환경설정</h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">

	<form id="frm" method="post" action="/ncms/member_conf/writeOk.do">
	<div>
		<input type="hidden" name="mc_idx" value="${memberconf.mc_idx}" /><!-- (수정일때사용) -->
		<input type="hidden"  name="mc_email" value="Y"  />
		<input type="hidden"  name="mc_email_req" value="Y"  />
		<input type="hidden"  name="mc_join" value="Y"  />
	</div>
	<%--
	<h2 class="default">회원제 사용여부</h2>
	<fieldset>
		<legend>회원가입 사용여부</legend>
		<table class="bbs_common bbs_default" summary="회원가입 약관정보를 위한 입력 양식입니다.">
		<caption>회원가입 사용여부</caption>
		<colgroup>
		<col width="120" />
		<col />
		</colgroup>
		<tr>
			<th scope="row">회원제 사용여부</th>
			<td class="left">
				<label for="mc_join_Y"><input type="radio" id="mc_join_Y" name="mc_join" value="Y" title="사용 선택" <c:if test='${memberconf.mc_join == "Y"}'> checked</c:if> />사용</label>
				<label for="mc_join_N"><input type="radio" id="mc_join_N" name="mc_join" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_join == "N"}'> checked</c:if> />사용안함</label>
			</td>
		</tr>
		</table>
	</fieldset>
	<br/><br/>
	 --%>
		
	<h2 class="default">약관 정보 <span class="ri">약관정보를 입력해주세요.</span></h2>
	<fieldset>
		<legend>약관정보 서식 작성</legend>
		<table class="bbs_common bbs_default" summary="회원가입 약관정보를 위한 입력 양식입니다.">
		<caption>약관정보 서식</caption>
		<colgroup>
		<col width="120" />
		<col />
		</colgroup>
		<tr>
			<th scope="row">이용약관</th>
			<td class="left">
				<label for="mc_agree_Y"><input type="radio" id="mc_agree_Y" name="mc_agree" value="Y" title="사용 선택" onclick="document.getElementById('agree').style.display='block'" <c:if test='${memberconf.mc_agree == "Y"}'> checked</c:if> />사용</label>
				<label for="mc_agree_N"><input type="radio" id="mc_agree_N" name="mc_agree" value="N" title="사용안함 선택" onclick="document.getElementById('agree').style.display='none'" <c:if test='${memberconf.mc_agree == "N"}'> checked</c:if> />사용안함</label>

				<div id="agree" style="display:block;" class="pt4">
					<textarea class="ta_textarea w99" style="height:180px; line-height:160%;" id="mc_agree_str" name="mc_agree_str" title="약관 내용 입력" onfocus="focus_on1_default(this);" onblur="focus_off1(this);" >${memberconf.mc_agree_str}</textarea>
				</div>

				<c:if test='${memberconf.mc_agree == "N"}'>
				<script type='text/javascript'>
				<!--
					document.getElementById('agree').style.display='none';
				//-->
				</script>
				</c:if>
			</td>
		</tr>
		<tr>
			<th scope="row">개인정보취급방침</th>
			<td class="left">
				<label for="mc_info_Y"><input type="radio" id="mc_info_Y" name="mc_info" value="Y" title="사용 선택" onclick="document.getElementById('info').style.display='block'" <c:if test='${memberconf.mc_info == "Y"}'> checked</c:if> />사용</label>
				<label for="mc_info_N"><input type="radio" id="mc_info_N" name="mc_info" value="N" title="사용안함 선택" onclick="document.getElementById('info').style.display='none'" <c:if test='${memberconf.mc_info == "N"}'> checked</c:if> />사용안함</label>

				<div id="info" style="display:block;" class="pt4">
					<textarea class="ta_textarea w99" style="height:180px; line-height:160%;" id="mc_info_str" name="mc_info_str" title="약관 내용 입력" onfocus="focus_on1_default(this);" onblur="focus_off1(this);" >${memberconf.mc_info_str}</textarea>
				</div>

				<c:if test='${memberconf.mc_info == "N"}'>
				<script type='text/javascript'>
				<!--
					document.getElementById('info').style.display='none';
				//-->
				</script>
				</c:if>
			</td>
		</tr>
		</table>
	</fieldset>



	<h2 class="default h2_mt">필드관리 <span class="ri">필드정보를 선택해주세요.</span></h2>
	<fieldset>
	<legend>필드정보 서식 선택/수정</legend>
	<table class="bbs_common bbs_default" summary="회원가입 필드정보를 위한 선택 양식입니다.">
	<caption>필드정보 서식</caption>
	<colgroup>
	<col width="120" />
	<col width="235" />
	<col width="120" />
	<col width="235" />
	</colgroup>

	<tr>
		<th scope="row">닉네임</th>
		<td class="left">
			<label for="mc_nickname_Y"><input type="radio" id="mc_nickname_Y" name="mc_nickname" value="Y" title="사용 선택" <c:if test='${memberconf.mc_nickname == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_nickname_N"><input type="radio" id="mc_nickname_N" name="mc_nickname" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_nickname == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_nickname_req"><input type="checkbox" id="mc_nickname_req" name="mc_nickname_req" value="Y" <c:if test='${memberconf.mc_nickname_req == "Y"}'> checked</c:if> />필수</label>
		</td>
		<th scope="row">성별</th>
		<td class="left">
			<label for="mc_sex_Y"><input type="radio" id="mc_sex_Y" name="mc_sex" value="Y" title="사용 선택" <c:if test='${memberconf.mc_sex == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_sex_N"><input type="radio" id="mc_sex_N" name="mc_sex" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_sex == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_sex_req"><input type="checkbox" id="mc_sex_req" name="mc_sex_req" value="Y" <c:if test='${memberconf.mc_sex_req == "Y"}'> checked</c:if> />필수</label>
		</td>
	</tr>

	<tr>
		<th scope="row">생년월일</th>
		<td class="left">
			<label for="mc_birth_Y"><input type="radio" id="mc_birth_Y" name="mc_birth" value="Y" title="사용 선택" <c:if test='${memberconf.mc_birth == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_birth_N"><input type="radio" id="mc_birth_N" name="mc_birth" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_birth == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_birth_req"><input type="checkbox" id="mc_birth_req" name="mc_birth_req" value="Y" <c:if test='${memberconf.mc_birth_req == "Y"}'> checked</c:if> />필수</label>
		</td>
		<th scope="row">연락처</th>
		<td class="left">
			<label for="mc_phone_Y"><input type="radio" id="mc_phone_Y" name="mc_phone" value="Y" title="사용 선택" <c:if test='${memberconf.mc_phone == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_phone_N"><input type="radio" id="mc_phone_N" name="mc_phone" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_phone == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_phone_req"><input type="checkbox" id="mc_phone_req" name="mc_phone_req" value="Y" <c:if test='${memberconf.mc_phone_req == "Y"}'> checked</c:if> />필수</label>
		</td>
	</tr>

	<tr>
		<th scope="row">팩스번호</th>
		<td class="left">
			<label for="mc_fax_Y"><input type="radio" id="mc_fax_Y" name="mc_fax" value="Y" title="사용 선택" <c:if test='${memberconf.mc_fax == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_fax_N"><input type="radio" id="mc_fax_N" name="mc_fax" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_fax == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_fax_req"><input type="checkbox" id="mc_fax_req" name="mc_fax_req" value="Y" <c:if test='${memberconf.mc_fax_req == "Y"}'> checked</c:if> />필수</label>
		</td>
		<th scope="row">휴대폰</th>
		<td class="left">
			<label for="mc_mobile_Y"><input type="radio" id="mc_mobile_Y" name="mc_mobile" value="Y" title="사용 선택" <c:if test='${memberconf.mc_mobile == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_mobile_N"><input type="radio" id="mc_mobile_N" name="mc_mobile" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_mobile == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_mobile_req"><input type="checkbox" id="mc_mobile_req" name="mc_mobile_req" value="Y" <c:if test='${memberconf.mc_mobile_req == "Y"}'> checked</c:if> />필수</label>
		</td>
	</tr>

	<tr>
		<th scope="row">SMS수신동의</th>
		<td class="left">
			<label for="mc_sms_Y"><input type="radio" id="mc_sms_Y" name="mc_sms" value="Y" title="사용 선택" <c:if test='${memberconf.mc_sms == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_sms_N"><input type="radio" id="mc_sms_N" name="mc_sms" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_sms == "N"}'> checked</c:if> />사용안함</label>
		</td>
		<th scope="row">메일수신동의</th>
		<td class="left">
			<label for="mc_mailing_Y"><input type="radio" id="mc_mailing_Y" name="mc_mailing" value="Y" title="사용 선택" <c:if test='${memberconf.mc_mailing == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_mailing_N"><input type="radio" id="mc_mailing_N" name="mc_mailing" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_mailing == "N"}'> checked</c:if> />사용안함</label>				
		</td>
	</tr>

	<tr>
		<th scope="row">주소</th>
		<td class="left">
			<label for="mc_addr_Y"><input type="radio" id="mc_addr_Y" name="mc_addr" value="Y" title="사용 선택" <c:if test='${memberconf.mc_addr == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_addr_N"><input type="radio" id="mc_addr_N" name="mc_addr" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_addr == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_addr_req"><input type="checkbox" id="mc_addr_req" name="mc_addr_req" value="Y" <c:if test='${memberconf.mc_addr_req == "Y"}'> checked</c:if> />필수</label>
		</td>
		<th scope="row">홈페이지</th>
		<td class="left">
			<label for="mc_homepage_Y"><input type="radio" id="mc_homepage_Y" name="mc_homepage" value="Y" title="사용 선택" <c:if test='${memberconf.mc_homepage == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_homepage_N"><input type="radio" id="mc_homepage_N" name="mc_homepage" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_homepage == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_homepage_req"><input type="checkbox" id="mc_homepage_req" name="mc_homepage_req" value="Y" <c:if test='${memberconf.mc_homepage_req == "Y"}'> checked</c:if> />필수</label>
		</td>
	</tr>

	<tr>
		<th scope="row">결혼여부</th>
		<td class="left">
			<label for="mc_marry_Y"><input type="radio" id="mc_marry_Y" name="mc_marry" value="Y" title="사용 선택" <c:if test='${memberconf.mc_marry == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_marry_N"><input type="radio" id="mc_marry_N" name="mc_marry" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_marry == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_marry_req"><input type="checkbox" id="mc_marry_req" name="mc_marry_req" value="Y" <c:if test='${memberconf.mc_marry_req == "Y"}'> checked</c:if> />필수</label>
		</td>
		<th scope="row">결혼기념일</th>
		<td class="left">
			<label for="mc_marrydate_Y"><input type="radio" id="mc_marrydate_Y" name="mc_marrydate" value="Y" title="사용 선택" <c:if test='${memberconf.mc_marrydate == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_marrydate_N"><input type="radio" id="mc_marrydate_N" name="mc_marrydate" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_marrydate == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_marrydate_req"><input type="checkbox" id="mc_marrydate_req" name="mc_marrydate_req" value="Y" <c:if test='${memberconf.mc_marrydate_req == "Y"}'> checked</c:if> />필수</label>
		</td>
	</tr>
	
	
	<tr>
		<th scope="row">직업</th>
		<td class="left">
			<label for="mc_job_Y"><input type="radio" id="mc_job_Y" name="mc_job" value="Y" title="사용 선택" <c:if test='${memberconf.mc_job == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_job_N"><input type="radio" id="mc_job_N" name="mc_job" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_job == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_job_req"><input type="checkbox" id="mc_job_req" name="mc_job_req" value="Y" <c:if test='${memberconf.mc_job_req == "Y"}'> checked</c:if> />필수</label>
		</td>
		<th scope="row">남기는말씀</th>
		<td class="left">
			<label for="mc_text_Y"><input type="radio" id="mc_text_Y" name="mc_text" value="Y" title="사용 선택" <c:if test='${memberconf.mc_text == "Y"}'> checked</c:if> />사용</label>
			<label for="mc_text_N"><input type="radio" id="mc_text_N" name="mc_text" value="N" title="사용안함 선택" <c:if test='${memberconf.mc_text == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			<label for="mc_text_req"><input type="checkbox" id="mc_text_req" name="mc_text_req" value="Y" <c:if test='${memberconf.mc_text_req == "Y"}'> checked</c:if> />필수</label>
		</td>
	</tr>


	<tr>
		<th scope="row"><label for="mc_joinlevel">회원가입시 등급</label></th>
		<td class="left" colspan="3">
			<select id="mc_joinlevel" name="mc_joinlevel" title="회원가입시 등급 선택" class="ta_select" style="width:90px;" >
				<c:forEach items="${membergroupList}" var="mgList" varStatus="no">
				<option value="${mgList.g_num}" ${mgList.g_num == memberconf.mc_joinlevel ? 'selected="selected"' : '' }>${mgList.g_menuname}</option>
				</c:forEach>
			</select>
		</td>
	</tr>

	</table>
	</fieldset>



	<div class="contoll_box">
		<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="취소" class="btn_wh_default" onclick="page_go1('write.do');" /></span>
	</div>
	
		</form>
	
	</div>
	<!-- 내용들어가는곳 -->