<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_member.js"></script>
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>
<style>
.text1{color:#5C7DF8;font-size:11px}
</style>
<script type="text/javascript">
$(function(){
	$('#m_birth, #m_marrydate').attr("readonly", true);
	$('#m_birth').helloCalendar({'selectBox':true});
	$('#m_marrydate').helloCalendar({'selectBox':true});
	setM_site();
});

function w_chk(){
	var eForm = document.getElementById('frm');
	
	<c:if test="${member.m_num eq ''}">
	if( CheckSpaces(eForm.m_name, '이름') ) { return false; }

	if (CheckSpaces(eForm.m_id, '아이디')) { return false; }
	if (CheckLen2(eForm.m_id, '4', '10', '아이디')) { return false; }
	</c:if>

	<c:if test='${member.m_num eq ""}'>
		if( CheckSpaces(eForm.m_pwd, '비밀번호') ) { return false; }
	</c:if>
		if (document.getElementById('frm').m_pwd.value!=""){
			if(!passCheck() ) {return false; }
			if(CheckSpaces(eForm.m_pwdchk, '비밀번호확인') ) { return false; }
			if(CheckEqual(eForm.m_pwd,eForm.m_pwdchk,'비밀번호')) { return false; }
		}

<c:if test="${empty BUILDER_DIR or sessionScope.ss_g_num eq '1'}">
		if(eForm.m_level.value == "4"){	//사이트관리자
			if(CheckRadio(eForm.m_site, '관리사이트')){return false;}
		}
</c:if>
		
		
	<c:if test='${memberconf.mc_nickname eq "Y" and memberconf.mc_nickname_req eq "Y"}'>
		if(CheckSpaces(eForm.m_nickname, "닉네임")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_birth eq "Y" and memberconf.mc_birth_req eq "Y"}'>
		if(CheckSpaces(eForm.m_birth, "생년월일")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_email eq "Y" and memberconf.mc_email_req eq "Y"}'>
		if(!isCorrectEmail(eForm.m_email)){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_phone eq "Y" and memberconf.mc_phone_req eq "Y"}'>
		if(CheckSpaces(eForm.m_phone1, "전화번호")){ return false; }
		if(CheckSpaces(eForm.m_phone2, "전화번호")){ return false; }
		if(CheckSpaces(eForm.m_phone3, "전화번호")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_mobile eq "Y" and memberconf.mc_mobile_req eq "Y"}'>
		if(CheckSpaces(eForm.m_mobile1, "휴대폰번호")){ return false; }
		if(CheckSpaces(eForm.m_mobile2, "휴대폰번호")){ return false; }
		if(CheckSpaces(eForm.m_mobile3, "휴대폰번호")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_fax eq "Y" and memberconf.mc_fax_req eq "Y"}'>
		if(CheckSpaces(eForm.m_fax1, "팩스번호")){ return false; }
		if(CheckSpaces(eForm.m_fax2, "팩스번호")){ return false; }
		if(CheckSpaces(eForm.m_fax3, "팩스번호")){ return false; }
	</c:if>
	
	<c:if test='${memberconf.mc_addr eq "Y" and memberconf.mc_addr_req eq "Y"}'>
		if(CheckSpaces(eForm.b_zip1, "주소")){ return false; }
		if(CheckSpaces(eForm.b_zip1, "주소")){ return false; }
		if(CheckSpaces(eForm.b_addr1, "주소")){ return false; }
		if(CheckSpaces(eForm.b_addr2, "상세주소")){ return false; }
	</c:if>
	
	<c:if test='${memberconf.mc_marrydate eq "Y" and memberconf.mc_marrydate_req eq "Y"}'>
		if(document.getElementById("m_marry_Y").checked){
			if(CheckSpaces(eForm.m_marrydate, "결혼기념일")){ return false; }
		}
	</c:if>

	<c:if test='${memberconf.mc_homepage eq "Y" and memberconf.mc_homepage_req eq "Y"}'>
		if(CheckSpaces(eForm.m_homepage, "홈페이지")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_job eq "Y" and memberconf.mc_job_req eq "Y"}'>
		if(CheckSpaces(eForm.m_job, "직업")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_text eq "Y" and memberconf.mc_text_req eq "Y"}'>
		if(CheckSpaces(eForm.m_text, "남기는말씀")){ return false; }
	</c:if>

	<c:if test='${member.m_num eq ""}'> //등록일대만 보임
		if( CheckSpaces(eForm.m_autoimg_str,'자동가입방지')) { return false; }
	</c:if>
}

function disabled_chk(){
	alert("수정모드일때는 변경할수 없습니다.");
}

$(document).ready(function(){
	//영문,숫자만 허용
	$("#m_id").keyup(function(event){ 
		if (!(event.keyCode >=37 && event.keyCode<=40)) {
			var inputVal = $(this).val();
			$(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
		}
	});
	//숫자만 입력
	$('.only_number').keyup(function () { 
		this.value = this.value.replace(/[^0-9]/g,'');
	});
	//한글만 입력
	$(".kor").keyup(function(event){
		regexp = /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
		v = $(this).val();
		if( regexp.test(v) ) {
		 alert("한글만입력하세요");
		 $(this).val(v.replace(regexp,''));
		}
	});
});


function setM_site(){
	switch($("#m_level").val()){	//사이트관리자(달서구립은 도서관관리자)
		case "4" :
			$("#tr_m_site").css("display", "");
			break;
		default :
			$("#tr_m_site").css("display", "none");
	}
}
</script>

		<h1 class="tit"><span>${member.m_num == "" ? "신규 회원 생성" : "회원 수정"}</span></h1>

		<!-- 내용들어가는곳 -->
		<div id="contents_area">
<c:set var="action_url" value="${DOMAIN_HTTPS}/ncms/member/writeOk.do" />
<c:set var="prepage" value="/ncms/member/write.do?m_num=${member.m_num}" />
<c:if test="${!empty BUILDER_DIR}">
	<c:set var="action_url" value="${DOMAIN_HTTPS}/${BUILDER_DIR }/ncms/member/writeOk.do" />
	<c:set var="prepage" value="/${BUILDER_DIR }/ncms/member/write.do?m_num=${member.m_num}" />
</c:if>
			<form id="frm" name="frm" method="post" action="${action_url }" onsubmit="return w_chk();">
			<div>
			<input type="hidden" name="m_num" value="${member.m_num}" /><!-- (수정일때사용) -->
			<input type="hidden" name="autoimg_str" value="${member.autoimg_str}" />
			<input type="hidden" name="m_id_chk" value="" />
			<c:if test="${!empty member.m_num}">
			<input type="hidden" name="prepage" value="${prepage }" />
			</c:if>
			</div>
			
			<h2 class="tit">회원 기본정보 <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>
		<fieldset>
		<legend>회원 기본정보 서식 작성</legend>
		<table class="bbs_common bbs_default" summary="신규 회원 기본정보를 위한 입력 양식입니다.">
		<caption>기능정보 서식</caption>
		<colgroup>
		<col width="15%" />
		<col width="*" />
		</colgroup>
		<tr>
			<th scope="row"><label for="m_name"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 이름</label></th>
			<td class="left">
			<c:if test="${member.m_num eq ''}">
				<input type="text" title="이름 입력" id="m_name" name="m_name" class="ta_input kor"   value="${member.m_name}" maxlength="15"  ${member.m_num == "" ? "" : "disabled"} style="width:150px" /> <span class="point fs11">* 15자이내의 한글만 사용하실 수 있습니다. </span>
			</c:if>
			<c:if test="${member.m_num ne ''}">
				${member.m_name}
			</c:if>
			</td>
		</tr>
	
		<tr>
			<th scope="row"><label for="m_id"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 아이디</label></th>
			<td class="left">
				<c:if test="${member.m_num eq ''}">
					<input type="text" title="아이디 입력" id="m_id" name="m_id" class="ta_input eng"   value="${member.m_id}" maxlength="10"  ${member.m_num == "" ? "" : "disabled"} /> <span class="point fs11">* 4자 이상 10자 이내의 영문/숫자만 사용하실 수 있습니다.  </span>
				</c:if>
				<c:if test="${member.m_num ne ''}">
					${member.m_id}
				</c:if>
			</td>
		</tr>
		<tr>
			<th scope="row"><label for="m_pwd"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 비밀번호</label></th>
			<td class="left"><input type="password" title="비밀번호 입력" id="m_pwd" name="m_pwd" class="ta_input"    maxlength="30"  style="width:150px" />
			
			<c:choose>
				<c:when test="${member.m_num == ''}">
					<span class="point fs11">* 숫자, 영문, 특수기호 포함 10자리 이상 20자 이내로 입력해주세요.</span>
				</c:when>
				<c:otherwise>
					<span class="point fs11">* 비밀번호 수정시에만 입력하세요. (숫자, 영문, 특수기호 포함 10자리 이상 20자 이내)</span>
				</c:otherwise>
			</c:choose>
			</td>
		</tr>
	
		<tr>
			<th scope="row"><label for="m_pwdchk"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 비밀번호확인</label></th>
			<td class="left"><input type="password" title="비밀번호 한번더 입력" id="m_pwdchk" name="m_pwdchk" class="ta_input"   value="" maxlength="30"  style="width:150px" /> <span class="point fs11">* 비밀번호를 한번 더 입력하세요.</span></td>
		</tr>
	
		<c:if test='${memberconf.mc_nickname == "Y"}'>
		<tr>
			<th scope="row"><label for="m_nickname"><c:if test='${memberconf.mc_nickname_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 닉네임</label></th>
			<td class="left"><input type="text" id="m_nickname" name="m_nickname" class="ta_input kor"   value="${member.m_nickname}" maxlength="10"  /> <span class="point fs11">* 10자이내의 한글만 사용하실 수 있습니다.</span></td>
		</tr>
		</c:if>
	
		<tr>
			<th scope="row"><label for="m_level"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 그룹</label></th>
			<td class="left">
				<select id="m_level" name="m_level" title="회원가입시 등급 선택" class="ta_select" style="width:200px;" onchange="setM_site();" >
				<c:forEach items="${membergroupList}" var="mgList" varStatus="no">
				<option value="${mgList.g_num}" ${mgList.g_num == member.m_level ? 'selected="selected"' : '' }>${mgList.g_menuname}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
<c:if test="${empty BUILDER_DIR or sessionScope.ss_g_num eq '1'}">
		<tr id="tr_m_site" style="display:none;">
			<th scope="row"><label for="m_site"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 관리사이트</label></th>
			<td class="left">
				<c:forEach items="${builderList}" var="builder" varStatus="no">
					<c:set var="m_site_str" value="(${builder.bs_num })" />
				<p><input type="checkbox" id="m_site_${no.index }" name="m_site" value="${builder.bs_num }" ${builder.checked eq 'checked' ? 'checked="checked"' : '' } />
				<label for="m_site_${no.index }">${builder.bs_sitename }</label></p>
				</c:forEach>
			</td>
		</tr>
</c:if>
		<!-- tr>
			<th scope="row"><label for="allow_ip">관리자페이지 접근IP</label></th>
			<td class="left"><input type="text" title="이름 입력" id="allow_ip" name="allow_ip" class="ta_input"   value="${member.allow_ip}" maxlength="300" style="width:80%;" /><br/><span class="point fs11">* 여러개 등록시 세미콜론(;)으로 구분하세요.<br/>* 관리자페이지 접근가능한 회원만 적용됩니다.<br/>* 미등록시 모든 아이피에서 해당 아이디로 로그인이 가능합니다.</span></td>
		</tr -->
	
		<c:if test='${memberconf.mc_sex == "Y"}'>
		<tr>
			<th scope="row"><c:if test='${memberconf.mc_sex_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 성별</th>
			<td class="left">				
				<label for="m_sex_1">남</label>
				<input name="m_sex" id="m_sex_1" type="radio" value="1" <c:if test='${member.m_sex == "1"}'> checked</c:if> />
				<label for="m_sex_2">여</label>
				<input name="m_sex" id="m_sex_2" type="radio" value="2" <c:if test='${member.m_sex == "2"}'> checked</c:if> />
			</td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_birth == "Y"}'>
		<tr>
			<th scope="row"><label for="m_birth_Y"><c:if test='${memberconf.mc_birth_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 생년월일</label></th>
			<td class="left">
				<input type="text" size="15" id="m_birth" name="m_birth" class="ta_input"   value="${member.m_birth}" maxlength="10" /> <span class="point fs11">* 생년월일을 선택해주세요.</span>
			</td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_email == "Y"}'>
		<tr>
			<th scope="row"><label for="m_email"><c:if test='${memberconf.mc_email_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 이메일</label></th>
			<td class="left">				
				<input type="text" size="30" id="m_email" name="m_email" class="ta_input"   value="${member.m_email}" maxlength="100" />
				<span class="point fs11"> * <strong>@</strong>포함한 이메일주소를 정확히 입력하세요.</span>
			</td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_mailing == "Y"}'>
		<tr>
			<th scope="row"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 메일링 서비스</th>
			<td class="left">				
				<label for="m_mailing_Y">신청합니다</label>
				<input name="m_mailing" id="m_mailing_Y" type="radio" value="Y" <c:if test='${member.m_mailing == "Y"}'> checked</c:if> />
				<label for="m_mailing_N">신청하지않습니다</label>
				<input name="m_mailing" id="m_mailing_N" type="radio" value="N" <c:if test='${member.m_mailing == "N"}'> checked</c:if> />
			</td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_phone == "Y"}'>
		<tr>
			<th scope="row"><label for="m_phone1"><c:if test='${memberconf.mc_phone_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 전화번호</label></th>
			<td class="left"><input type="text" size="4" title="연락처 첫번째 입력" id="m_phone1" name="m_phone1" class="ta_input only_number"   value="${member.m_phone1}" maxlength="4"  /> - <input type="text" size="4" title="연락처 두번째 입력" id="m_phone2" name="m_phone2" class="ta_input only_number"   value="${member.m_phone2}" maxlength="4"  /> - <input type="text" size="4" title="연락처 세번째 입력" id="m_phone3" name="m_phone3" class="ta_input only_number"   value="${member.m_phone3}" maxlength="4"  /> <span class="point fs11">* 숫자만 입력해주세요. </span></td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_mobile == "Y"}'>
		<tr>
			<th scope="row"><label for="m_mobile1"><c:if test='${memberconf.mc_mobile_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 휴대폰</label></th>
			<td class="left"><input type="text" size="4" title="휴대폰 첫번째 입력" id="m_mobile1" name="m_mobile1" class="ta_input only_number"   value="${member.m_mobile1}" maxlength="4"  /> - <input type="text" size="4" title="휴대폰 두번째 입력" id="m_mobile2" name="m_mobile2" class="ta_input only_number"   value="${member.m_mobile2}" maxlength="4"  /> - <input type="text" size="4" title="휴대폰 세번째 입력" id="m_mobile3" name="m_mobile3" class="ta_input only_number"   value="${member.m_mobile3}" maxlength="4"  /> <span class="point fs11">* 숫자만 입력해주세요. </span></td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_sms == "Y"}'>
		<tr>
			<th scope="row"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> SMS 서비스</th>
			<td class="left">				
				<label for="m_sms_Y">신청합니다</label>
				<input name="m_sms" id="m_sms_Y" type="radio" value="Y" <c:if test='${member.m_sms == "Y"}'> checked</c:if> />
				<label for="m_sms_N">신청하지않습니다</label>
				<input name="m_sms" id="m_sms_N" type="radio" value="N" <c:if test='${member.m_sms == "N"}'> checked</c:if> />
			</td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_fax == "Y"}'>
		<tr>
			<th scope="row"><label for="m_fax1"><c:if test='${memberconf.mc_fax_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 팩스번호</label></th>
			<td class="left"><input type="text" size="4" id="m_fax1" name="m_fax1" class="ta_input only_number"   value="${member.m_fax1}" maxlength="4"  /> - <input type="text" size="4" id="m_fax2" name="m_fax2" class="ta_input only_number"   value="${member.m_fax2}" maxlength="4"  /> - <input type="text" size="4" id="m_fax3" name="m_fax3" class="ta_input only_number"   value="${member.m_fax3}" maxlength="4"  /> <span class="point fs11">* 숫자만 입력해주세요. </span></td>
		</tr>
		</c:if>
	
	
		<c:if test='${memberconf.mc_addr == "Y"}'>
		<tr>
			<th scope="row"><label for="m_zip1"><c:if test='${memberconf.mc_addr_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 주소</label></th>
			<td class="left">
				<!-- 변수 선언 -->
				
				<jsp:include page="../../site/common/file/inc_address.jsp">
					<jsp:param name="b_zip1" value="${member.m_zipcode}"/>
					<jsp:param name="b_addr1" value="${member.m_addr1}"/>
					<jsp:param name="b_addr2" value="${member.m_addr2}"/>
				</jsp:include>
			</td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_marry == "Y"}'>
		<tr>
			<th scope="row"><c:if test='${memberconf.mc_marry_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 결혼여부</th>
			<td class="left">
				<label for="m_marry_Y">예</label>
				<input name="m_marry" id="m_marry_Y" type="radio" value="Y" <c:if test='${member.m_marry == "Y"}'> checked</c:if> />
				<label for="m_marry_N">아니오</label>
				<input name="m_marry" id="m_marry_N" type="radio" value="N" <c:if test='${member.m_marry == "N"}'> checked</c:if> />
			</td>
		</tr>
		</c:if>
	
	
		<c:if test='${memberconf.mc_marrydate == "Y"}'>
		<tr>
			<th scope="row"><label for="m_marrydate"><c:if test='${memberconf.mc_marrydate_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 결혼기념일</label></th>
			<td class="left">				
				<input type="text" size="15" id="m_marrydate" name="m_marrydate" class="ta_input"   value="${member.m_marrydate}" maxlength="10" /> <span class="point fs11">* 결혼기념일을 선택해주세요.</span>
			</td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_homepage == "Y"}'>
		<tr>
			<th scope="row"><label for="m_homepage"><c:if test='${memberconf.mc_homepage_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 홈페이지</label></th>
			<td class="left"><input type="text" size="50" id="m_homepage" name="m_homepage" maxlength="100" class="ta_input"   value="${member.m_homepage}"  /> <span class="point fs11">* 홈페이지 주소를 입력해주세요.</span></td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_job == "Y"}'>
		<tr>
			<th scope="row"><label for="m_job"><c:if test='${memberconf.mc_job_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 직업</label></th>
			<td class="left"><input type="text" size="23" id="m_job" name="m_job" maxlength="50" class="ta_input"   value="${member.m_job}" /> <span class="point fs11">* 50자 이내로 입력해주세요.</span></td>
		</tr>
		</c:if>
	
		<c:if test='${memberconf.mc_text == "Y"}'>
		<tr>
			<th scope="row"><label for="m_job"><c:if test='${memberconf.mc_text_req == "Y"}'><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /></c:if> 남기는말씀</label></th>
			<td class="left"><textarea name="m_text" id="m_text" class="ta_textarea w99" >${member.m_text}</textarea></td>
		</tr>
		</c:if>
	
	
		<c:if test='${member.m_num == ""}'><!--등록일대만 보임-->
		<tr>
			<th scope="row"><label for="m_autoimg_str"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 자동가입방지</label></th>
			<td class="left"><img src="/nanum/ncms/img/0${member.autoimg_str}.png" width="156" height="62" alt="자동가입방지" class="img_center" /> &nbsp;<input type="text" size="20" title=" 자동가입방지 입력" id="m_autoimg_str" name="m_autoimg_str" class="ta_input eng"   value="" maxlength="6"  /><span class="point fs11"> * 왼쪽에 보이는 숫자 및 문자를 모두 입력하세요.</span></td>
		</tr>
		</c:if>
		</table>
		</fieldset>
		
				<div class="contoll_box">
					<span><input type="submit" value="등록" class="btn_bl_default" /></span><span><input type="button" value="취소" onclick="page_go1('list.do?${pageInfo}');" class="btn_wh_default" /></span>
				</div>
		
			</form>
</div>
<!-- 내용들어가는곳 -->