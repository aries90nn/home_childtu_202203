<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="member_content" />
</head>

<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>
<script type="text/javascript">
$(function(){
	$('#m_birth, #m_marrydate').attr("readonly", true);
	$('#m_birth').helloCalendar({'selectBox':true});
	$('#m_marrydate').helloCalendar({'selectBox':true});
	
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
</script>
<style>
.text1{font-size:12px;color:#298A08}
</style>
<form id="frm" name="frm" method="post" action="${DOMAIN_HTTPS}/member/registOk.do" onsubmit="return sendit();">
<div>
	<input type="hidden" id="ok_url" name="ok_url" value="${DOMAIN_HTTP}/member/login.do" />
	<input type="hidden" id="idchk" name="idchk" value="N" />
	<input type="hidden" name="autoimg_str" value="${member.autoimg_str}" />
</div>

<p class="tar mb10"><span class="blue">*</span> 는 필수항목입니다.<p>
<table class="table1 join_table" summary="회원가입을 위한 회원정보 입력">
	<caption>회원가입</caption>
	<colgroup>
		<col width="20%" />
		<col width="" />
	</colgroup>		
	<tbody>	
		<tr>
			<th scope="row" class="bg"><label for="m_id">아이디 <span class="blue">*</span></label></th>
			<td class="left"><input name="m_id" id="m_id" type="text" style="width:150px; ime-mode:disabled;" maxlength="10" class="txt_input wize1" /> <a href="javascript:;" onclick="id_chk();" class="table_btn">중복확인</a> <span class="text1">* 4자 이상 10자 이내의 영문/숫자만 사용하실 수 있습니다. </span></td>
		</tr>

		<tr>
			<th scope="row" class="bg"><label for="m_pwd">비밀번호 <span class="blue">*</span></label></th>
			<td class="left"><input name="m_pwd" id="m_pwd" type="password" style="width:150px;" maxlength="20" class="txt_input wize1" /> <span class="text1">* 숫자, 영문, 특수기호 포함 10자리 이상 20자 이내로 입력해주세요.</span></td>
		</tr>
		<tr>
			<th scope="row" class="bg"><label for="m_pwdchk">비밀번호 확인 <span class="blue">*</span></label></th>
			<td class="left"><input name="m_pwdchk" id="m_pwdchk" type="password" style="width:150px;" maxlength="20" class="txt_input wize1" /> <span class="text1">* 비밀번호를 한번 더 입력하세요. </span></td>
		</tr>
		<tr>
			<th scope="row" class="bg"><label for="m_name">이름 <span class="blue">*</span></label></th>
			<td class="left"><input name="m_name" id="m_name" type="text" style="width:150px;" maxlength="15" class="txt_input wize1 kor" /> <span class="text1">* 15자이내의 한글만 사용하실 수 있습니다. </span></td>
		</tr>

		<c:if test='${memberconf.mc_nickname == "Y"}'>
		<tr>
			<th scope="row" class="bg"><label for="m_nickname">닉네임 <c:if test='${memberconf.mc_nickname_req eq "Y"}'><span class="blue">*</span></c:if></label></th>
			<td class="left"><input name="m_nickname" id="m_nickname" type="text" style="width:150px;" maxlength="10" class="txt_input kor"/> <span class="text1">* 10자이내의 한글만 사용하실 수 있습니다.</span></td>
		</tr>
		</c:if>
		
		<c:if test='${memberconf.mc_sex == "Y"}'>
		<tr>
			<th scope="row" class="bg">성별  <c:if test='${memberconf.mc_sex_req == "Y"}'><span class="blue">*</span></c:if></th>
			<td class="left">
				<input name="m_sex" id="m_sex_1" type="radio" value="1" checked="checked" />
				<label for="m_sex_1">남</label>&nbsp;
				<input name="m_sex" id="m_sex_2" type="radio" value="2" />
				<label for="m_sex_2">여</label>
			</td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_birth == "Y"}'>
		<tr>
			<th scope="row" class="bg">생년월일 <c:if test='${memberconf.mc_birth_req == "Y"}'> <span class="blue">*</span></c:if></th>
			<td class="left">
				<input type="text" size="15" id="m_birth" name="m_birth" class="txt_input" value="${member.m_birth}" maxlength="10" /> <span class="text1">* 생년월일을 선택해주세요.</span>
			</td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_email == "Y"}'>
		<tr>
			<th scope="row" class="bg"><label for="m_email">이메일 <c:if test='${memberconf.mc_email_req == "Y"}'> <span class="blue">*</span></c:if></label></th>
			<td class="left"><input name="m_email" id="m_email" type="text" style="width:180px;" class="txt_input wize1" maxlength="100" /> <span class="text1">* @포함한 이메일주소를 정확히 입력하세요. </span></td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_mailing == "Y"}'>
		<tr>
			<th scope="row" class="bg">메일링 서비스 <span class="blue">*</span></th>
			<td class="left">
				<label for="m_mailing_Y">신청합니다</label>
				<input name="m_mailing" id="m_mailing_Y" type="radio" value="Y" checked="checked" />
				<label for="m_mailing_N">신청하지않습니다</label>
				<input name="m_mailing" id="m_mailing_N" type="radio" value="N" />
			</td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_phone == "Y"}'>
		<tr>
			<th scope="row" class="bg">전화번호 <c:if test='${memberconf.mc_phone_req == "Y"}'> <span class="blue">*</span></c:if></th>
			<td class="left">
				<input name="m_phone1" id="m_phone1" type="text" style="width:50px;" class="txt_input only_number" onblur="SetNum(this)" maxlength="4" title="전화번호 첫번째자리 입력"/>
				-
				<input name="m_phone2" id="m_phone2" type="text" style="width:50px;" class="txt_input only_number" onblur="SetNum(this)" maxlength="4" title="전화번호 두번째자리 입력"/>
				-
				<input name="m_phone3" id="m_phone3" type="text" style="width:50px;" class="txt_input only_number" onblur="SetNum(this)" maxlength="4" title="전화번호 세번째자리 입력"/>
				 <span class="text1">* 숫자만 입력해주세요. </span>
			</td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_mobile == "Y"}'>
		<tr>
			<th scope="row" class="bg">휴대폰번호 <c:if test='${memberconf.mc_mobile_req == "Y"}'> <span class="blue">*</span></c:if></th>
			<td class="left">
				<input name="m_mobile1" id="m_mobile1" type="text" style="width:50px;" class="txt_input only_number" onblur="SetNum(this)" maxlength="4" title="휴대폰번호 첫번째자리 입력"/>
				-
				<input name="m_mobile2" id="m_mobile2" type="text" style="width:50px;" class="txt_input only_number" onblur="SetNum(this)" maxlength="4" title="휴대폰번호 두번째자리 입력"/>
				-
				<input name="m_mobile3" id="m_mobile3" type="text" style="width:50px;" class="txt_input only_number" onblur="SetNum(this)" maxlength="4" title="휴대폰번호 세번째자리 입력"/>
				 <span class="text1">* 숫자만 입력해주세요. </span>
			
			</td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_sms == "Y"}'>
		<tr>
			<th scope="row" class="bg">SMS 서비스 <span class="blue">*</span></th>
			<td class="left">
				<label for="m_sms_Y">신청합니다</label>
				<input name="m_sms" id="m_sms_Y" type="radio" value="Y" checked="checked" />
				<label for="m_sms_N">신청하지않습니다</label>
				<input name="m_sms" id="m_sms_N" type="radio" value="N" />
			</td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_fax == "Y"}'>
		<tr>
			<th scope="row" class="bg">팩스번호 <c:if test='${memberconf.mc_fax_req == "Y"}'> <span class="blue">*</span></c:if></th>
			<td class="left">
				<input name="m_fax1" id="m_fax1" type="text" style="width:50px;" class="txt_input only_number" onblur="SetNum(this)" maxlength="4" title="팩스번호 첫번째자리 입력"/>
				-
				<input name="m_fax2" id="m_fax2" type="text" style="width:50px;" class="txt_input only_number" onblur="SetNum(this)" maxlength="4" title="팩스번호 두번째자리 입력"/>
				-
				<input name="m_fax3" id="m_fax3" type="text" style="width:50px;" class="txt_input only_number" onblur="SetNum(this)" maxlength="4" title="팩스번호 세번째자리 입력"/>
				 <span class="text1">* 숫자만 입력해주세요. </span>

			</td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_addr == "Y"}'>
		<tr>
			<th scope="row" class="bg">주소 <c:if test='${memberconf.mc_addr_req == "Y"}'> <span class="blue">*</span></c:if></th>
			<td class="left">
				
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
			<th scope="row" class="bg">결혼여부 <span class="blue">*</span></th>
			<td class="left">
				<label for="m_marry_Y">예</label>
				<input name="m_marry" id="m_marry_Y" type="radio" value="Y" />
				<label for="m_marry_N">아니오</label>
				<input name="m_marry" id="m_marry_N" type="radio" value="N" checked="checked" />
			</td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_marrydate == "Y"}'>
		<tr>
			<th scope="row" class="bg">결혼기념일<c:if test='${memberconf.mc_marrydate_req == "Y"}'> <span class="blue">*</span></c:if></th>
			<td class="left">
				<input type="text" size="15" id="m_marrydate" name="m_marrydate" class="txt_input" value="" maxlength="10" /> <span class="text1">* 결혼기념일을 선택해주세요.</span>
			</td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_homepage == "Y"}'>
		<tr>
			<th scope="row" class="bg"><label for="m_homepage">홈페이지 <c:if test='${memberconf.mc_homepage_req == "Y"}'> <span class="blue">*</span></c:if></label></th>
			<td class="left"><input name="m_homepage" id="m_homepage" type="text" style="width:300px;" maxlength="100" class="txt_input" /> <span class="text1">* 홈페이지 주소를 입력해주세요.</span></td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_job == "Y"}'>
		<tr>
			<th scope="row" class="bg"><label for="m_job">직업<c:if test='${memberconf.mc_job_req == "Y"}'> <span class="blue">*</span></c:if></label></th>
			<td class="left"><input name="m_job" id="m_job" type="text" style="width:130px;" maxlength="50" class="txt_input" /> <span class="text1">* 50자 이내로 입력해주세요.</span></td>
		</tr>
		</c:if>

		<c:if test='${memberconf.mc_text == "Y"}'>
		<tr>
			<th scope="row" class="bg"><label for="m_text">남기는말씀<c:if test='${memberconf.mc_text_req == "Y"}'> <span class="blue">*</span></c:if></label></th>
			<td class="left"><textarea name="m_text" id="m_text" style="height: 70px; width: 90%;" ></textarea></td>
		</tr>
		</c:if>

		<tr>
			<th scope="row" class="bg"><label for="m_autoimg_str">자동가입방지 <span class="blue">*</span></label></th>
			<td class="left">
				<img src="/nanum/ncms/img/0${member.autoimg_str}.png" width="156" height="62" alt="자동가입방지" class="img_center" align="absmiddle" />
				<input name="m_autoimg_str" type="text" style="width:100px;" class="txt_input" />
				<span class="text1">* 왼쪽에 보이는 숫자 및 문자를 모두 입력하세요.</span>
			</td>
		</tr>

		<c:if test='${memberconf.mc_agree == "Y"}'>
		<tr>			
			<th class="bg">이용약관 <span class="blue">*</span></th>
			<td class="left">
				<div style="float:left;">
					<span class="rad_span"><input name="mc_agree" id="mc_agree_Y" type="radio" value="Y" checked="checked" class="check" />
					<label for="mc_agree_Y">동의합니다</label></span>&nbsp;
					<span class="rad_span"><input name="mc_agree" id="mc_agree_N" type="radio" value="N" class="check" />
					<label for="mc_agree_N">동의하지 않습니다</label></span>
				</div>
				<div class="jsInfoBtn table_btn" style="float:right;">이용약관 닫기</div>
				<div class="jsInfoDetail" style="display:block">
					<textarea>${memberconf.mc_agree_str}</textarea>
				</div>
			</td>
		</tr>		
		</c:if>

		<c:if test='${memberconf.mc_info == "Y"}'>
		<tr>
			
			<th class="bg">개인정보처리방침 <span class="blue">*</span></th>
			<td class="left">
				<div style="float:left;">				
					<span class="rad_span"><input name="mc_info" id="mc_info_Y" type="radio" value="Y" checked="checked" class="check" />
					<label for="mc_info_Y">동의합니다</label></span>&nbsp;
					<span class="rad_span"><input name="mc_info" id="mc_info_N" type="radio" value="N" class="check" />
					<label for="mc_info_N">동의하지 않습니다</label></span>
				</div>

				<div class="jsInfoBtn table_btn" style="float:right;">개인정보처리방침 닫기</div>
				<div class="jsInfoDetail" style="display:block">
					<textarea>${memberconf.mc_info_str}</textarea>
				</div>

			</td>
		</tr>		
		</c:if>



	</tbody>
</table>

<!-- 버튼 -->
<div class="btn_w">
	<input type="submit" value="확인" class="con_btn green" /> <a href="/member/login.do" class="con_btn gray">취소</a>
</div>
<!-- //버튼 -->


</form>



<script type="text/javascript">

$(".jsInfoBtn").click(function(){
	if ($(this).next(".jsInfoDetail:hidden").length>0){
		$(this).next(".jsInfoDetail").show();
		$(this).html($(this).html().replace("보기","닫기"));
	}else{
		$(this).next(".jsInfoDetail").hide();
		$(this).html($(this).html().replace("닫기","보기"));
	}
	
});

function id_chk(){
	var eForm = document.getElementById("frm");
	if(!valueChk(eForm.m_id, "아이디")){
		return;
	}else if (alphaDigit(eForm.m_id,"아이디")){
	}else if (CheckLen(eForm.m_id,4,20)){
	}else{

		var script = document.createElement('script');
		script.type = 'text/javascript';
		script.src = "/member/idchk.do?m_id="+eForm.m_id.value;

		var head = document.getElementsByTagName('head')[0];
		head.appendChild(script);


		/*
		$.ajax({
			type: "POST",
			url: "/content/member/idchk_ajax.php", 
			data: "m_id="+eForm.m_id.value,
			dataType:"html",
			async:false,
			success: function(msg){	
				msg = msg.replace(/(\r\n)/gi,""); //줄바꿈 없애기
				if (msg=="Y"){
					alert("사용할 수 있는 아이디 입니다.");
					eForm.idchk.value = "Y";
					eForm.m_pwd.focus();
				}else{
					alert("["+eForm.m_id.value+"]은(는) 이미 사용중인 아이디 입니다.");
					eForm.idchk.value = "N";
					eForm.m_id.focus();
				}
			}
		});
		*/


	}
}


function sendit(){
	var eForm = document.getElementById("frm");
	if(eForm.idchk.value == "N"){
		alert("아이디 중복검색을 하세요.");
		eForm.m_id.focus();
		return false;
	}

	if (CheckSpaces(eForm.m_id, '아이디')) { return false; }
	if (CheckLen2(eForm.m_id, '4', '10', '아이디')) { return false; }

	if(!valueChk(eForm.m_name, "이름")){ return false; }

	if (document.getElementById('frm').m_pwd.value!=""){
		if(!passCheck() ) {return false; }
		else if(CheckSpaces(eForm.m_pwdchk, '비밀번호확인') ) { return false; }
		else if(CheckEqual(eForm.m_pwd,eForm.m_pwdchk,'비밀번호')) { return false; }
	}
	
	<c:if test='${memberconf.mc_nickname eq "Y" and memberconf.mc_nickname_req eq "Y"}'>
		if(!valueChk(eForm.m_nickname, "닉네임")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_birth eq "Y" and memberconf.mc_birth_req eq "Y"}'>
		if(CheckSpaces(eForm.m_birth, "생년월일")){ return false; }
	</c:if>
	
	<c:if test='${memberconf.mc_email eq "Y" and memberconf.mc_email_req eq "Y"}'>
		if(!isCorrectEmail(eForm.m_email)){ return false; }
	</c:if>
	
	<c:if test='${memberconf.mc_phone eq "Y" and memberconf.mc_phone_req eq "Y"}'>
		if(!valueChk(eForm.m_phone1, "전화번호")){ return false; }
		if(!valueChk(eForm.m_phone2, "전화번호")){ return false; }
		if(!valueChk(eForm.m_phone3, "전화번호")){ return false; }
	</c:if>
	
	<c:if test='${memberconf.mc_mobile eq "Y" and memberconf.mc_mobile_req eq "Y"}'>
		if(!valueChk(eForm.m_mobile1, "휴대폰번호")){ return false; }
		if(!valueChk(eForm.m_mobile2, "휴대폰번호")){ return false; }
		if(!valueChk(eForm.m_mobile3, "휴대폰번호")){ return false; }
	</c:if>
	
	<c:if test='${memberconf.mc_fax eq "Y" and memberconf.mc_fax_req eq "Y"}'>
		if(!valueChk(eForm.m_fax1, "팩스번호")){ return false; }
		if(!valueChk(eForm.m_fax2, "팩스번호")){ return false; }
		if(!valueChk(eForm.m_fax3, "팩스번호")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_addr eq "Y" and memberconf.mc_addr_req eq "Y"}'>
		if(!valueChk(eForm.b_zip1, "주소")){ return false; }
		//if(!valueChk(eForm.b_zip1, "주소")){ return false; }
		if(!valueChk(eForm.b_addr1, "주소")){ return false; }
		//if(!valueChk(eForm.b_addr2, "상세주소")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_marrydate eq "Y" and memberconf.mc_marrydate_req eq "Y"}'>
		if(document.getElementById("m_marry_Y").checked){
			if(CheckSpaces(eForm.m_marrydate, "결혼기념일")){ return false; }
		}
	</c:if>

	<c:if test='${memberconf.mc_homepage eq "Y" and memberconf.mc_homepage_req eq "Y"}'>
		if(!valueChk(eForm.m_homepage, "홈페이지")){ return false; }
	</c:if>
	
	<c:if test='${memberconf.mc_job eq "Y" and memberconf.mc_job_req eq "Y"}'>
		if(!valueChk(eForm.m_job, "직업")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_text eq "Y" and memberconf.mc_text_req eq "Y"}'>
		if(!valueChk(eForm.m_text, "남기는말씀")){ return false; }
	</c:if>

	if(!valueChk(eForm.m_autoimg_str, "자동가입방지")){ return false; }
	
	<c:if test='${memberconf.mc_agree eq "Y"}'>
	if (!eForm.mc_agree[0].checked){
		alert("이용약관에 동의해주세요.");
		return false;
	}
	</c:if>

	<c:if test='${memberconf.mc_info eq "Y"}'>
	if (!eForm.mc_info[0].checked){
		alert("개인정보처리방침에 동의해주세요.");
		return false;
	}
	</c:if>

}

function valueChk(obj, objName){
	//text필드
	if(obj.value.split(" ").join("") == ""){
		alert(objName+"을(를) 입력하세요");
		try{
			obj.focus();
		}
		catch(e){ }
		return false;
	}
	else{
		return true;
	}
}

//E-mail 체크
function isCorrectEmail(obj) {
	var i;
	var check=0;
	var dot=0;
	var before = "";
	var after = "";

	if(obj.value.length == 0){
		alert("이메일을 입력하세요");
		obj.focus();
		return(false);
	}
	for(i=0; i<obj.value.length; i++) {
		if(obj.value.charAt(i) == '@') {
			check = check + 1;
		}
		else if(check == 0) {
			before = before + obj.value.charAt(i);
			}
			else if(check == 1) {
				after = after + obj.value.charAt(i);
		}
	}
	if( check >= 2 || check == 0 ) {
		alert("잘못된 전자우편 주소입니다.");
		obj.focus();
		obj.select()
		return(false);
	}

	for(i=0; i<before.length; i++) {
		if(!((before.charAt(i) >= 'A' && before.charAt(i) <= 'z') ||
			(before.charAt(i) >= '0' && before.charAt(i) <= '9') ||
			(before.charAt(i) == '_') || (before.charAt(i) == '-'))) {
			alert("잘못된 전자우편 주소입니다.");
			obj.focus();
			obj.select()
			return(false);
		}
	}

	for(i=0; i<after.length; i++) {
		if(!((after.charAt(i) >= 'A' && after.charAt(i) <= 'z') ||
			(after.charAt(i) >= '0' && after.charAt(i) <= '9') ||
			(after.charAt(i) == '_') || (after.charAt(i) == '.') ||
			(after.charAt(i) == '-'))) {
			alert("잘못된 전자우편 주소입니다.");
			obj.focus();
			obj.select()
			return(false);
		}
	}

	for(i=0; i<after.length; i++) {
		if(after.charAt(i) == '.') {
			dot = dot + 1;
		}
	}

	if( dot < 1 ) {
		alert("잘못된 전자우편 주소입니다.");
		obj.focus();
		obj.select()
		return(false);
	}
	return(true);
}

function SetNum(obj){
	//숫자만 입력
	val=obj.value;
	re=/[^0-9]/gi;
	obj.value=val.replace(re,"");
}


//주민번호체크
function jumin_chk(jumin1,jumin2){

	var jstr=(jumin1.value) + (jumin2.value)+"";
	var dummy = new Array("2","3","4","5","6","7","8","9","2","3","4","5");
	var tot;
	var tmp, chksum;


	tmp = jstr.charAt(2);
	tmp = tmp + jstr.charAt(3);
	if(tmp > 12){
		alert("잘못된 주민등록번호입니다.");
		return true;
	}

	tmp = jstr.charAt(4);
	tmp = tmp + jstr.charAt(5);
	if(tmp > 31){
		alert("잘못된 주민등록번호입니다.");
		return true;
	}

	tmp = jstr.charAt(6);
	if(tmp > 4){
		alert("잘못된 주민등록번호입니다.");
		return true;
	}

	if(jstr.length != 13){
		alert("주민등록번호 자릿수가 맞지 않습니다.");
		jumin1.focus();
		return true;
	}

	tot = 0;
	tmp = 0;
	chksum = 0;

	for(var i=0; i<12; i++){
		tot = tot + (jstr.charAt(i) * dummy[i]);
	}

	tmp = tot%11;
	chksum = 11 - tmp;
	tmp = chksum%10;

	if( jstr.charAt(12) != tmp ){
		alert("잘못된 주민등록번호입니다.");
		return true;
	}

	return false;
}
</script>