<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<%-- 본인인증 연동할 경우 활성화 --%>
<c:if test="${empty sessionScope.ss_m_pwd and empty sessionScope.ss_m_name }">
	<c:url var="auth_url" value="/${BUILDER_DIR }/site/member/ipin.do">
		<c:param name="prepage" value="${nowPage }" />
	</c:url>
	<script>
		alert("회원가입을 위해 본인인증 페이지로 이동합니다.");
		location.href="${auth_url}";
	</script>

</c:if>
<%-- //본인인증 연동할 경우 활성화 --%>

<link rel="stylesheet" type="text/css" href="/nanum/site/member/css/common.css" />
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar_birth.js"></script>
<script type="text/javascript" src="/nanum/site/member/js/common.js"></script>
<script type="text/javascript">
$(function(){
	$('#m_birth, #m_marrydate').attr("readonly", true);
	$('#m_birth').helloCalendar({'selectBox':true});
	$('#m_marrydate').helloCalendar({'selectBox':true});
	
	$("#m_zipcode").attr("readonly", true);
	$("#m_addr1").attr("readonly", true);
});


function w_chk(eForm){
	
	
	<c:if test='${empty member.m_num}'>
		<c:if test='${memberconf.mc_agree eq "Y"}'>
	
		if(!$("#chk_agree").prop("checked")){
			alert("이용약관에 동의하셔야 서비스이용이 가능합니다.");
			$("#chk_agree").focus();
			return false;
		}
		
		</c:if>
		<c:if test='${memberconf.mc_info eq "Y"}'>
		
		if(!$("#chk_pinfo").prop("checked")){
			alert("개인정보취급방침에 동의하셔야 서비스이용이 가능합니다.");
			$("#chk_pinfo").focus();
			return false;
		}
		
		</c:if>
	</c:if>
	
	
	<c:if test="${empty member.m_num}">
	if( CheckSpaces(eForm.m_name, '이름') ) { return false; }

	if (CheckSpaces(eForm.m_id, '아이디')) { return false; }
	if( !textRule1(eForm.m_id, "아이디") ){return false;}
	if (CheckLen2(eForm.m_id, '8', '15', '아이디')) { return false; }
	if(eForm.idchk.value != "Y"){
		alert("아이디 중복확인을 하시기 바랍니다.");
		$("#a_idcheck").focus();
		return false;
	}
	</c:if>

	<c:if test='${empty member.m_num}'>
		if( CheckSpaces(eForm.m_pwd, '비밀번호') ) { return false; }
		if (document.getElementById('frm').m_pwd.value!=""){
			if(!passCheck() ) {return false; }
			if(CheckSpaces(eForm.m_pwdchk, '비밀번호확인') ) { return false; }
			if(CheckEqual(eForm.m_pwd,eForm.m_pwdchk,'비밀번호')) { return false; }
		}
	</c:if>
	
	<c:if test='${memberconf.mc_email eq "Y"}'>
	if (CheckSpaces(eForm.m_email, '이메일')) { return false; }
	if( !email_chk(eForm.m_email, "이메일") ){return false;}
	if(eForm.emailchk.value != "Y"){
		alert("이메일 중복확인을 하시기 바랍니다.");
		$("#a_emailcheck").focus();
		return false;
	}
	</c:if>
		
	<c:if test='${memberconf.mc_nickname eq "Y" and memberconf.mc_nickname_req eq "Y"}'>
		if(CheckSpaces(eForm.m_nickname, "닉네임")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_sex eq "Y" and memberconf.mc_sex_req eq "Y"}'>
		<c:if test="${empty member.m_sex or (empty sessionScope.ss_m_id and empty certMap.sex) }">
		if(CheckRadio(eForm.m_sex, "성별")){return false;}
		</c:if>
	</c:if>
	
	<c:if test='${memberconf.mc_birth eq "Y" and memberconf.mc_birth_req eq "Y"}'>
		if(CheckSpaces(eForm.m_birth, "생년월일")){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_email eq "Y" and memberconf.mc_email_req eq "Y"}'>
		if(!isCorrectEmail(eForm.m_email)){ return false; }
	</c:if>

	<c:if test='${memberconf.mc_mailing eq "Y"}'>
		if(CheckRadio(eForm.m_mailing, "메일링 서비스신청")){return false;}
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

	<c:if test='${memberconf.mc_sms eq "Y"}'>
		if(CheckRadio(eForm.m_sms, "SMS수신여부")){return false;}
	</c:if>
	
	<c:if test='${memberconf.mc_fax eq "Y" and memberconf.mc_fax_req eq "Y"}'>
		if(CheckSpaces(eForm.m_fax1, "팩스번호")){ return false; }
		if(CheckSpaces(eForm.m_fax2, "팩스번호")){ return false; }
		if(CheckSpaces(eForm.m_fax3, "팩스번호")){ return false; }
	</c:if>
	
	<c:if test='${memberconf.mc_addr eq "Y" and memberconf.mc_addr_req eq "Y"}'>
		if(CheckSpaces(eForm.m_zipcode, "주소")){ return false; }
		if(CheckSpaces(eForm.m_addr1, "주소")){ return false; }
		if(CheckSpaces(eForm.m_addr2, "상세주소")){ return false; }
	</c:if>
	
	<c:if test='${memberconf.mc_marry eq "Y"}'>
	if(CheckRadio(eForm.m_marry, "결혼여부")){return false;}
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

	<c:if test='${empty member.m_num}'> //등록일대만 보임
		if( CheckSpaces(eForm.m_autoimg_str,'자동가입방지')) { return false; }
	</c:if>
	

}


function agreeShow(id){
	if( $("#"+id).css("display") == "none"){
		$("#"+id).css("display", "");
	}else{
		$("#"+id).css("display", "none");
	}
}
</script>

<div id="member_service">
	<p class="tar mb10"><span class="red">*</span> 표시가 된 곳은 필수 항목입니다.</p>

	<form id="frm" method="post" action="${DOMAIN_HTTPS}/${BUILDER_DIR }/member/registOk.do" onsubmit="return w_chk(this)">
	<div>
	<input type="hidden" name="idchk" value="N" />
	<input type="hidden" name="emailchk" value="N" />
	<input type="hidden" name="prepage" value="${empty sessionScope.ss_m_num ? param.prepage : nowPage }" />
	<input type="hidden" name="autoimg_str" value="${member.autoimg_str }" />
	<input type="hidden" name="m_dupinfo" value="${certMap.di }" />
	<%--<input type="hidden" name="m_coinfo" value="${certMap.ci }" /> //CI가 필수정보가 아니면 굳이 저장하지말자--%>
	</div>
	
	<div class="bwrite join_write">
		<div class="box w100">
			<ul>
<c:if test="${empty sessionScope.ss_m_num }">
	<c:if test='${memberconf.mc_agree eq "Y"}'>
				<li>
					<dl>
						<dt><span class="red">*</span> <label for="chk_agree">이용약관</label></dt>
						<dd>
							<a href="#agree_show" class="member_btn2 gray" style="text-decoration:none;" onclick="agreeShow('text_agree');">이용약관 보기</a>
							<br /><textarea id="text_agree" style="height:100px;display:none;" readonly="readonly">${memberconf.mc_agree_str}</textarea>
							<br /><input type="checkbox" id="chk_agree" value="Y" /><label for="chk_agree">이용약관에 동의합니다.</label>
						</dd>
					</dl>
				</li>
	</c:if>
	<c:if test='${memberconf.mc_info eq "Y"}'>
				<li>
					<dl>
						<dt><span class="red">*</span> <label for="chk_pinfo">개인정보취급방침</label></dt>
						<dd>
							<a href="#pinfo_show" class="member_btn2 gray" style="text-decoration:none;" onclick="agreeShow('text_pinfo');">개인정보취급방침 보기</a>
							<br /><textarea id="text_pinfo" style="height:100px;display:none;" readonly="readonly">${memberconf.mc_info_str}</textarea>
							<br /><input type="checkbox" id="chk_pinfo" value="Y" /><label for="chk_pinfo">개인정보취급방침에 동의합니다.</label>
						</dd>
					</dl>
				</li>
	</c:if>
</c:if>

				<li>
					<dl>
						<dt><span class="red">*</span> <label for="m_name">이름</label></dt>
						<dd>
<c:if test="${empty sessionScope.ss_m_name }">
							<input type="text" id="m_name" name="m_name" maxlength="20" class="member_input" value="${member.m_name }" />
</c:if>
<c:if test="${!empty sessionScope.ss_m_name }">
							${sessionScope.ss_m_name }
							<input type="hidden" name="m_name" value="${sessionScope.ss_m_name }" />
</c:if>
						</dd>
					</dl>
				</li>


				<li>
					<dl>
						<dt><span class="red">*</span> <label for="user_id">아이디</label></dt>
						<dd class="pw_wrap">
<c:if test="${empty sessionScope.ss_m_id }">
							<input type="text" id="m_id" name="m_id" onchange="idCheckReset();" onkeyup="checkNumber();hanCancel(this);" maxlength="20" class="member_input mr5" />
							<a id="a_idcheck" href="#idcheck" onclick="ajaxIdCheck();"  class="member_btn2 gray" style="text-decoration:none;">아이디중복확인</a>
							<span class="text1">※ 영문, 숫자 혼용 8자리이상</span>
</c:if>
<c:if test="${!empty sessionScope.ss_m_id }">
							${sessionScope.ss_m_id }
</c:if>
						</dd>
					</dl>
				</li>

<c:if test="${empty sessionScope.ss_m_num }">
				<li>
					<dl>
						<dt><span class="red">*</span> <label for="m_pwd">비밀번호</label></dt>
						<dd class="pw_wrap">
							<input type="password" id="m_pwd" name="m_pwd" maxlength="20" class="member_input" onkeyup="checkNumber();" /><span class="text1">※ 영문, 숫자, 특수문자 혼용 10자리이상</span>
						</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt><span class="red">*</span> <label for="m_pwdchk">비밀번호확인</label></dt>
						<dd>
							<input type="password" id="m_pwdchk" name="m_pwdchk" maxlength="20" class="member_input" />
						</dd>
					</dl>
				</li>
</c:if>
<c:if test='${memberconf.mc_nickname eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_nickname_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_nickname">닉네임</label></dt>
						<dd>
							<input type="text" id="m_nickname" name="m_nickname" maxlength="20" class="member_input" value="${member.m_nickname }" />
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_sex eq "Y"}'>
	<c:if test="${!empty certMap.sex }">
		<c:choose>
			<c:when test="${certMap.sex eq '0' }">
				<c:set target="${member }" property="m_sex" value="1" />
			</c:when>
			<c:otherwise>
				<c:set target="${member }" property="m_sex" value="2" />
			</c:otherwise>
		</c:choose>
	</c:if>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_sex_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_sex_1">성별</label></dt>
						<dd>
	<c:choose>
		<c:when test="${empty member.m_sex or (empty sessionScope.ss_m_id and empty certMap.sex) }">
							<input type="radio" id="m_sex_1" name="m_sex" value="1" ${member.m_sex eq '1' ? 'checked="checked"' : '' } /><label for="m_sex_1">남</label>
							<input type="radio" id="m_sex_2" name="m_sex" value="2" ${member.m_sex eq '2' ? 'checked="checked"' : '' } /><label for="m_sex_2">여</label>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${member.m_sex eq '1' }">
								남<input type="hidden" name="m_sex" value="1" />
				</c:when>
				<c:otherwise>
								여<input type="hidden" name="m_sex" value="2" />
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_birth eq "Y"}'>
	<c:if test="${!empty certMap.birth }">
		<c:set target="${member }" property="m_birth" value="${fn:substring(certMap.birth,0,4) }" />
		<c:set target="${member }" property="m_birth" value="${member.m_birth }-${fn:substring(certMap.birth,4,6) }" />
		<c:set target="${member }" property="m_birth" value="${member.m_birth }-${fn:substring(certMap.birth,6,8) }" />
	</c:if>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_birth_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_birth">생년월일</label></dt>
						<dd>
	<c:if test="${empty member.m_birth }">
							<input type="text" id="m_birth" name="m_birth" maxlength="10" class="member_input" value="${member.m_birth }" />
	</c:if>
	<c:if test="${!empty member.m_birth }">
							${member.m_birth }
							<input type="hidden" name="m_birth" value="${member.m_birth }" />
	</c:if>
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_email eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_email_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_email">이메일</label></dt>
						<dd class="pw_wrap">
<c:if test="${empty sessionScope.ss_m_id }">
							<input type="text" id="m_email" name="m_email" maxlength="100" class="member_input" value="${member.m_email }" />
							<a id="a_emailcheck" href="#idcheck" onclick="ajaxEmailCheck();"  class="member_btn2 gray" style="text-decoration:none;">이메일중복확인</a>
							<span class="text1">※ 예시 : email@OOO.com</span>
</c:if>
<c:if test="${!empty sessionScope.ss_m_id }">
							${member.m_email }
</c:if>
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_mailing eq "Y"}'>
				<li>
					<dl>
						<dt><span class="red">*</span> <label for="m_mailing_Y">메일링 서비스</label></dt>
						<dd>
							<input type="radio" id="m_mailing_Y" name="m_mailing" value="Y" ${member.m_mailing eq 'Y' ? 'checked="checked"' : '' } /><label for="m_mailing_Y">신청합니다</label>
							<input type="radio" id="m_mailing_N" name="m_mailing" value="N" ${member.m_mailing eq 'N' ? 'checked="checked"' : '' } /><label for="m_mailing_N">신청하지 않습니다</label>
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_phone eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_phone_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_phone1">전화번호</label></dt>
						<dd>
							<%
							String m_phone1 = "053";
							String areacode = "02,051,053,032,062,042,052,044,031,033,043,041,063,061,054,055,064,070,010,011,016,017,018,019";
							String[] areacode_arr = areacode.split(",");
							pageContext.setAttribute("areacode_arr", areacode_arr);
							pageContext.setAttribute("m_phone1", m_phone1);
							%>
					
							
							<c:if test="${empty member.m_phone1 }">
								<c:set target="${member }" property="m_phone1" value="${m_phone1 }" />
							</c:if>
							<span class="select_style join_select">
							<select id="m_phone1" name="m_phone1">
							<c:forEach items="${areacode_arr}" var="areacode" varStatus="no">
								<option value="${areacode }" ${member.m_phone1 eq areacode ? 'selected="selected"' : '' }>${areacode }</option>
							</c:forEach>
							</select>
							</span>
							- <input type="text" size="5" id="m_phone2" name="m_phone2" value="${member.m_phone2 }" style="width:50px;" onblur="SetNum(this);" class="member_input join_input" maxlength="4" />
							- <input type="text" size="5" id="m_phone3" name="m_phone3" value="${member.m_phone3 }" style="width:50px;" onblur="SetNum(this);" class="member_input join_input" maxlength="4" />
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_mobile eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_mobile_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_mobile1">휴대폰</label></dt>
						<dd>
							<span class="select_style join_select">
							<select id="m_mobile1" name="m_mobile1">
								<option value="010" ${member.m_mobile1 eq '010' ? 'selected="selected"' : '' }>010</option>
								<option value="011" ${member.m_mobile1 eq '011' ? 'selected="selected"' : '' }>011</option>
								<option value="016" ${member.m_mobile1 eq '016' ? 'selected="selected"' : '' }>016</option>
								<option value="017" ${member.m_mobile1 eq '017' ? 'selected="selected"' : '' }>017</option>
								<option value="018" ${member.m_mobile1 eq '018' ? 'selected="selected"' : '' }>018</option>
								<option value="019" ${member.m_mobile1 eq '019' ? 'selected="selected"' : '' }>019</option>
							</select>
							</span>
							- <input type="text" size="5" id="m_mobile2" name="m_mobile2" value="${member.m_mobile2 }" style="width:50px;" onblur="SetNum(this);" maxlength="4" class="member_input join_input" />
							- <input type="text" size="5" id="m_mobile3" name="m_mobile3" value="${member.m_mobile3 }" style="width:50px;" onblur="SetNum(this);" maxlength="4" class="member_input join_input" />
						</dd>
					</dl>
				</li>
</c:if>
<c:if test='${memberconf.mc_sms eq "Y"}'>
				<li>
					<dl>
						<dt><span class="red">*</span> <label for="sms_use_y">SMS수신여부</label></dt>
						<dd>
							<input type="radio" id="m_sms_Y" name="m_sms" value="Y" ${member.m_sms eq 'Y' ? 'checked="checked"' : '' } /><label for="m_sms_Y">허용</label>
							<input type="radio" id="m_sms_N" name="m_sms" value="N" ${member.m_sms eq 'N' ? 'checked="checked"' : '' } /><label for="m_sms_N">거부</label>
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_fax eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_fax_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_fax1">팩스</label></dt>
						<dd>
							<input type="text" size="5" id="m_fax1" name="m_fax1" value="${member.m_fax1 }" style="width:50px;" onblur="SetNum(this);" maxlength="4" class="member_input join_input" />
							- <input type="text" size="5" id="m_fax2" name="m_fax2" value="${member.m_fax2 }" style="width:50px;" onblur="SetNum(this);" maxlength="4" class="member_input join_input" />
							- <input type="text" size="5" id="m_fax3" name="m_fax3" value="${member.m_fax3 }" style="width:50px;" onblur="SetNum(this);" maxlength="4" class="member_input join_input" />
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_addr eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_addr_req eq "Y"}'><span class="red">*</span></c:if> <label for="zipcode">주소</label></dt>
						<dd class="col3">
							<input type="text" class="member_input mb5" id="m_zipcode" name="m_zipcode" value="${member.m_zipcode }" style="width:40%;" maxlength="5" placeholder="우편번호" style="vertical-align:middle;" />
							<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="member_btn2 gray" style="" /><br />
							<input type="text" class="member_input" id="m_addr1" name="m_addr1" value="${member.m_addr1 }" style="width:80%; margin-bottom:5px;" placeholder="주소" class="member_input" />
							<input type="text" class="member_input" id="m_addr2" name="m_addr2" value="${member.m_addr2 }" style="width:80%;" placeholder="상세주소" class="member_input mb20" />
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_marry eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_marry_req eq "Y"}'><span class="red">*</span></c:if> <label for="sms_use_y">결혼여부</label></dt>
						<dd>
							<input type="radio" id="m_marry_Y" name="m_marry" value="Y" ${member.m_marry eq 'Y' ? 'checked="checked"' : '' } /><label for="m_marry_Y">예</label>
							<input type="radio" id="m_marry_N" name="m_marry" value="N" ${member.m_marry eq 'N' ? 'checked="checked"' : '' } /><label for="m_marry_N">아니오</label>
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_marrydate eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_marrydate_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_marrydate">결혼기념일</label></dt>
						<dd>
							<input type="text" id="m_marrydate" name="m_marrydate" maxlength="10" class="member_input join_input" value="${member.m_marrydate }" />
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_homepage eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_homepage_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_homepage">홈페이지</label></dt>
						<dd>
							<input type="text" class="member_input" id="m_homepage" name="m_homepage" value="${member.m_homepage }" style="width:80%;" class="member_input mb20" />
						</dd>
					</dl>
				</li>
</c:if>

<c:if test='${memberconf.mc_job eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_job_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_job">직업</label></dt>
						<dd>
							<input type="text" class="member_input" id="m_job" name="m_job" value="${member.m_job }" class="member_input" />
						</dd>
					</dl>
				</li>
</c:if>
<c:if test='${memberconf.mc_text eq "Y"}'>
				<li>
					<dl>
						<dt><c:if test='${memberconf.mc_text_req eq "Y"}'><span class="red">*</span></c:if> <label for="m_text">남기는 말씀</label></dt>
						<dd>
							<textarea name="m_text" id="m_text" >${member.m_text }</textarea>
						</dd>
					</dl>
				</li>
</c:if>

<c:if test="${empty sessionScope.ss_m_num }">
				<li>
					<dl>
						<dt><span class="red">*</span> <label for="m_autoimg_str">자동가입방지</label></dt>
						<dd>
							<img src="/nanum/ncms/img/0${member.autoimg_str}.png" width="156" height="62" alt="자동가입방지" class="img_center" />
							<input type="text" class="member_input mb5" id="m_autoimg_str" placeholder="자동가입방지 문자를 모두 입력" name="m_autoimg_str" value="" class="member_input" />
						</dd>
					</dl>
				</li>
</c:if>
			</ul>
		</div>
	</div>
	
	<p class="btn_w">
		<input type="submit" value="${empty sessionScope.ss_m_num ? '회원가입' : '정보수정' }" class="member_btn orange" />
		<c:if test="${not empty sessionScope.ss_m_num }">
			<a href="/${BUILDER_DIR }/member/secedeOk.do" onclick="if(!confirm('회원탈퇴하시겠습니까?')){return false;}" class="member_btn mint">회원탈퇴</a>
		</c:if>
		<a href="/${BUILDER_DIR }/" class="member_btn gray">취소</a>
	</p>
	</form>
</div>