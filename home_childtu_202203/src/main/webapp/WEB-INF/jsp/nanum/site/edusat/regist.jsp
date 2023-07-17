<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? 'list.do' : param.prepage}" />
<link rel="Stylesheet" type="text/css" href="/nanum/site/edusat/css/common.css" />
<script type="text/javascript" src="/nanum/site/edusat/js/common.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
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
}
span.text1.text3 {
	color : red;
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

<jsp:include page="./config.jsp" />
<jsp:include page="./tophtml.jsp" />


<c:set var="es_name" value="${ss_m_name}" />
<c:if test="${edusatreq.es_name !=null and edusatreq.es_name ne ''}">
	<c:set var="es_name" value="${edusatreq.es_name}" />
</c:if>

<c:set var="es_jumin" value="${empty edusatreq.es_jumin ? fn:replace(sessionScope.ss_user_birthday,'/','') : edusatreq.es_jumin }" />
<c:if test="${empty es_jumin }">
	<c:set var="es_jumin" value="${sessionScope.ss_m_birth }" />
</c:if>
<c:set var="es_jumin_y" value="${fn:substring(es_jumin,0,4)}" />
<c:set var="es_jumin_m" value="${fn:substring(es_jumin,4,6)}" />
<c:set var="es_jumin_d" value="${fn:substring(es_jumin,6,8)}" />

<c:set var="es_sex" value="${edusatreq.es_sex }" />
<c:if test="${empty es_sex }">
	<c:if test="${sessionScope.ss_m_sex eq '0' }">
		<c:set var="es_sex" value="1" />
	</c:if>
	<c:if test="${sessionScope.ss_m_sex eq '1' }">
		<c:set var="es_sex" value="2" />
	</c:if>
</c:if>
<c:if test="${empty es_sex }">
	<c:if test="${sessionScope.ss_user_sex eq '0' }">
		<c:set var="es_sex" value="1" />
	</c:if>
	<c:if test="${sessionScope.ss_user_sex eq '1' }">
		<c:set var="es_sex" value="2" />
	</c:if>
</c:if>


<c:if test="${edusat.edu_temp4 eq 'Y'}">
	<c:if test="${!empty edusat.edu_temp5 and edusat.edu_temp5 ne '|' }">
		<c:set var="edu_temp5_arr" value="${fn:split(edusat.edu_temp5, '[|]') }" />
		<c:set var="chk_birth_s" value="${edu_temp5_arr[0]}" />
		<c:set var="chk_birth_e" value="${edu_temp5_arr[1]}" />
	</c:if>
</c:if>


<script type="text/javascript">
//<![CDATA[
function date_chk(element, title) {
	if(!/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/.test(element.value)) {
		alert(title+'을(를) 형식에 맞게 작성해주세요.');
		element.focus();
		return false;
	}
	return true;
}
function registGo(){
	var eForm = document.getElementById('frm');
	<c:if test="${es_idx eq '' }">
	try{
		var agree = document.getElementById("chk_agree");
		if(agree.checked == false){
			alert("개인정보 수집에 동의하셔야 본 서비스를 이용하실 수 있습니다.");
			agree.focus();
			return false;
		}
		var agree2 = document.getElementById("chk_agree2");
		if(agree2.checked == false){
			alert("개인정보 제3자 제공에 동의하셔야 본 서비스를 이용하실 수 있습니다.");
			agree2.focus();
			return false;
		}
		var agree3 = document.getElementById("chk_agree3");
		if(agree3.checked == false){
			alert("김영란법에 동의하셔야 본 서비스를 이용하실 수 있습니다.");
			agree3.focus();
			return false;
		}
	}catch(e){
	}
	</c:if>
	if(!valueChk(eForm.es_name, "성명")){return false;}
	<c:if test="${gubun_num ne '2' and edusat.edu_field1_yn eq 'N'}">
	if(!valueChk(eForm.es_temp1, "신청자 구분")){return false;}
	<%//if((eForm.es_temp1.value == 'a' || eForm.es_temp1.value == 's') && eForm.es_name2.value.trim() == '') {%>
	if(eForm.es_name2.value.trim() == '') {
		alert('기관명을(를) 입력해주세요.');
		eForm.es_name2.focus();
		return false;
	}
	</c:if>
	
	<c:if test="${(edusat.edu_field6_yn eq 'R' or edusat.edu_field6_yn eq 'Y') and (sessionScope.ss_m_id eq '' or sessionScope.ss_m_id eq null)}">
	if(!valueChk(eForm.es_pwd, "비밀번호")){return false;}
	</c:if>
	
	<c:if test="${gubun_num eq '2'}">
	if(!valueChk(eForm.es_temp3, "사번")){return false;}
	if(!valueChk(eForm.es_temp4, "직급")){return false;}
	if(!valueChk(eForm.es_temp5, "팀명")){return false;}
	</c:if>
	
	if(!date_chk(eForm.es_email, "이메일")){return false;}
	<c:if test="${gubun_num ne '2'}">
	if(!valueChk(eForm.es_bphone1, "유선전화 첫번째")){return false;}
	if(!valueChk(eForm.es_bphone2, "유선전화 두번째")){return false;}
	if(!valueChk(eForm.es_bphone3, "유선전화 세번째")){return false;}
	</c:if>
	if(!valueChk(eForm.es_phone1, "휴대전화 첫번째")){return false;}
	if(!valueChk(eForm.es_phone2, "휴대전화 두번째")){return false;}
	if(!valueChk(eForm.es_phone3, "휴대전화 세번째")){return false;}
	if(!valueChk(eForm.es_temp2, "수령자(신청학교명)")) {return false;}
	if(!valueChk(eForm.es_etc, "신청수량(전교생인원)")){return false;}

	<c:if test="${gubun_num eq '999'}" >
		// gubun_num eq '0'
		//if(eForm.es_etc.value > '50'){ alert('최대 갯수는 50개 입니다!');return false;}
	</c:if>
	
	<c:if test="${edusat.edu_field1_yn eq 'R'}">
		if(!valueChk(eForm.es_name2, "기관명")){return false;}
	</c:if>
	<c:if test="${edusat.edu_field2_yn eq 'R'}">
		if(!valueChk(eForm.es_zipcode, "우편번호")){return false;}
		if(!valueChk(eForm.es_addr1, "주소")){return false;}
		if(!valueChk(eForm.es_addr2, "상세주소")){return false;}
	</c:if>
	<c:if test="${edusat.edu_field4_yn eq 'Y'}">
		/*if((eForm.es_fgrade.value.trim() == "" || eForm.es_fgrade_class.value.trim() == "") && (eForm.es_sgrade.value.trim() == "" || eForm.es_sgrade_class.value.trim() == "")){
			alert('1, 2학년 중 한 학년은 필수 입력입니다.');
			eForm.es_fgrade.focus();
			return false;
		}*/
	</c:if>
	<c:if test="${edusat.edu_field5_yn eq 'R'}">
		if(document.getElementById('es_problem_y').checked && document.getElementById('es_problem').value.trim() == "") {
			alert("교통사고가 발생한 적이 있으시다면 1년간의 총 건수를 작성해주세요.");
			document.getElementById('es_problem').focus();
			return false;
		}
	</c:if>
	
	<c:if test="${gubun_num eq '2'}">
	if(!valueChk(eForm.es_temp8, "담당교사의 성명")){return false;}
	if(!valueChk(eForm.es_temp9_1, "담당교사의 연락처")||!valueChk(eForm.es_temp9_2, "담당교사의 연락처")||!valueChk(eForm.es_temp9_3, "담당교사의 연락처")){return false;}
	
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

function valueChkbox(obj, objName){	//체크박스, 라디오버튼 체크
	var trueCnt = 0;
	for(i=0;i < obj.length;i++){
		if(obj[i].checked == true){
			trueCnt = trueCnt + 1;
		}
	}
	if(trueCnt == 0){
		alert(objName+"을(를) 체크하세요");
		obj[0].focus();
		return false;
	}else{
		trueCnt = 0;
		return true;
	}
}


function SetNum(obj){			//숫자만 입력
	val=obj.value;
	re=/[^0-9]/gi;
	obj.value=val.replace(re,"");
}


function nonetoblock(param) {
	<c:if test="${gubun_num ne '1' and edu_field1_yn ne 'R' and edusat.edu_field1_yn ne 'Y'}">
	if(param == 1) {
		document.getElementById('es_name2_wrab').style.display = 'none';
	}else{
		document.getElementById('es_name2_wrab').style.display = 'table-row';
	}
	</c:if>
}
//]]>
</script>

<c:set var="action_url" value="${DOMAIN_HTTPS}/edusat/registOk.do" />
<c:if test="${!empty BUILDER_DIR }">
	<c:set var="action_url" value="${DOMAIN_HTTPS}/${BUILDER_DIR }/edusat/registOk.do" />
</c:if>

<div id="board" style="width:100%;">
	
	<form id="frm" method="post" action="${action_url}" onsubmit="return registGo();">
	<input type="hidden" name="edu_idx" value="${edu_idx }">
	<input type="hidden" name="es_idx" value="${es_idx }">
	<input type="hidden" name="es_jumin" value="">
	<input type="hidden" name="prepage" value="${DOMAIN_HTTP}${prepage}">
	<c:if test="${es_idx eq '' }">
 	<div class="agreeBox">
		<!-- . -->
		<div class="area">
			<h3 class="tit">개인정보의 수집 및 이용</h3>
			<p class="check"><input type="checkbox" name="chk_agree" id="chk_agree" /> <label for="chk_agree">본 프로그램 신청을 위한 개인정보 수집 및 이용에 동의합니다.</label></p>
			<textarea class="txt_box">온라인 수강신청을 위하여 아래와 같이 개인정보를 수집 및 이용하고자 합니다.
			
1. 개인정보의 수집 및 이용목적 : 해당 프로그램의 신청자 확인 및 운영.(투명우산 나눔활동 신청시 수집되는 개인정보는 목적 외 용도로 이용하지 않습니다.)
2. 수집하는 개인정보의 항목 : 신청자 성명, 생년월 일, 성별, 휴대폰 번호, 주소
3. 개인정보의 보유 및 이용기간 : 1년
4. 신청자는 개인정보 수집·이용에 대하여 동의를 거부할 수 있는 권리가 있습니다. 단, 이에 대한 동의를 거부할 경우에는 투명우산 나눔활동 신청이 불가합니다.</textarea>
		</div>
		<!-- //. -->
		<!-- . -->
		<div class="area">
			<h3 class="tit">개인정보 제3자 제공 동의</h3>
			<p class="check"><input type="checkbox" name="chk_agree2" id="chk_agree2" /> <label for="chk_agree2">본 프로그램 신청을 위한 개인정보 제3자 제공에 동의합니다.</label></p>
			<textarea class="txt_box">온라인 수강신청 시 수집된 개인정보는 원활한 프로그램 운영을 위하여 아래 내용에 따라 제공하게 됩니다. 
			
1. 개인정보를 제공받는 자 : 투명우산 나눔활동의 담당자
2. 개인정보를 제공받는 자의 개인정보 이용 목적 : 해당 활동 운영
3. 제공하는 개인정보의 항목 : 신청자 성명, 연락처, 주소
4. 개인정보를 제공받는 자의 개인정보 보유 및 이용 기간 : 신청 당 해의 투명우산 나눔활동이 종료되면 지체없이 파기
5. 투명우산 나눔활동 신청자는 개인정보 제3자의 제공에 대하여 동의를 거부할 수 있는 권리가 있습니다.
단, 이에 대한 동의를 거부할 경우에는 투명우산 나눔활동 신청이 불가합니다.</textarea>
		</div>
		<!-- //. -->
	</div>
	
	<div class="area mt20">
		<p class="check"><input type="checkbox" name="chk_agree3" id="chk_agree3" /> <label for="chk_agree3">본 사업은 '부정 청탁 및 금품 등 수수의 금지에 관한 법률(김영란법)'과는 무관한 '사회공헌사업'임을 확인하였습니다.</label></p>
		<p class="check" style="color: #999; font-size: 14px;">프로그램에 선정되어 투명우산을 받은 수령자는 반드시 어린이들에게 배포하며, 판매 혹은 정치적 목적으로 사용할 수 없습니다.</p>
	</div>
	</c:if>
 
	<div class="guide">
		<span><img src="/nanum/site/edusat/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span>
	</div>
 
<!-- 쓰기 테이블 -->
	<div class="table_bwrite">
 
			<table cellspacing="0" summary="의 이름, 비밀번호, 내용을 입력">
			<caption>프로그램 신청</caption>
			<colgroup>
					<col width="20%" />
					<col width="10%"/>
					<col width="60%" />
			</colgroup>
			<tbody>
			
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif"/> <label for="name">프로그램명</label></th>
					<td  colspan="2">${edu_subject }</td>
				</tr>
				
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_name">성명</label></th>
					<td colspan="2">
						<c:choose>
							<c:when test="${empty es_name }">
								<input type="text" size="20" id="es_name" name="es_name" class="board_input"   value="${edurequest.es_name}" maxlength="20" ${!empty sessionScope.ss_m_name ? 'readonly=readonly' : ''} /><!-- <span class="text1">* 한글만 사용하실 수 있습니다. </span>-->
							</c:when>
							<c:otherwise>
								${es_name }
								<input type="hidden" name="es_name" value="${es_name }" />
							</c:otherwise>
						</c:choose>
						
						
					</td>
				</tr>
				
				<c:if test="${gubun_num ne '2' }">
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_temp1_i">신청자 구분</label></th>
					<td colspan="2">
						<%/*
						<input type="radio" name="es_temp1" id="es_temp1_i" value="i" checked="checked" /><label for="es_temp1_i" onclick="nonetoblock(1)">개인</label>
						<input type="radio" name="es_temp1" id="es_temp1_a" value="a" ${edurequest.es_temp1 eq 'a' ? 'checked' : '' } onclick="nonetoblock(2)"/><label for="es_temp1_a">기관</label>
						<input type="radio" name="es_temp1" id="es_temp1_s" value="s" ${edurequest.es_temp1 eq 's' ? 'checked' : '' } onclick="nonetoblock(2)" /><label for="es_temp1_s">학교</label>
						*/%>
						<%/*
						<input type="radio" name="es_temp1" id="es_temp1_i" value="i" checked="checked" /><label for="es_temp1_i" >유치원</label>
						<input type="radio" name="es_temp1" id="es_temp1_a" value="a" ${edurequest.es_temp1 eq 'a' ? 'checked' : '' } /><label for="es_temp1_a">어린이집</label>
						<input type="radio" name="es_temp1" id="es_temp1_s" value="s" ${edurequest.es_temp1 eq 's' ? 'checked' : '' }  /><label for="es_temp1_s">기타</label>
						*/%>
						<%//<input type="radio" name="es_temp1" id="es_temp1_i" value="i" checked="checked" /><label for="es_temp1_i" >초등학교</label> %>
						
						<input type="radio" name="es_temp1" id="es_temp1_i" value="i" checked="checked" /><label for="es_temp1_i" >유치원</label>
						<input type="radio" name="es_temp1" id="es_temp1_a" value="a" ${edurequest.es_temp1 eq 'a' ? 'checked' : '' } /><label for="es_temp1_a">어린이집</label>
						<input type="radio" name="es_temp1" id="es_temp1_c" value="c" ${edurequest.es_temp1 eq 'c' ? 'checked' : '' } /><label for="es_temp1_c">지역아동센터</label>
						<input type="radio" name="es_temp1" id="es_temp1_s" value="s" ${edurequest.es_temp1 eq 's' ? 'checked' : '' }  /><label for="es_temp1_s">기타</label>
					</td>
				</tr>
				</c:if>
				
				<c:if test="${gubun_num eq '2' }">
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /><label for="es_temp3">사번</label></th>
					<td colspan="2"><input type="text" size="40" title="사번" id="es_temp3" name="es_temp3" class="board_input middle"  maxlength="100" value="${edurequest.es_temp3 }" /></td>
				</tr>
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /><label for="es_temp4">직급</label></th>
					<td colspan="2"><input type="text" size="40" title="직급" id="es_temp4" name="es_temp4" class="board_input middle"  maxlength="100" value="${edurequest.es_temp4 }" /></td>
				</tr>
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /><label for="es_temp5">팀명</label></th>
					<td colspan="2"><input type="text" size="40" title="팀명" id="es_temp5" name="es_temp5" class="board_input middle"  maxlength="100" value="${edurequest.es_temp5 }" /></td>
				</tr>
				</c:if>
				
				<c:if test="${gubun_num ne '1' and gubun_num ne '2' and edusat.edu_field1_yn ne 'R' and edusat.edu_field1_yn ne 'Y'}">
				<%//<tr style="display: none;" id="es_name2_wrab"> %>
				<tr id="es_name2_wrab">
					<th scope="row"><label for="es_name2"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> 기관명 </label></th>
					<td colspan="2">
						<input type="text" size="20" id="es_name2" name="es_name2" class="board_input middle" value="${edurequest.es_name2}" maxlength="20" />
						
					</td>
				</tr>
				</c:if>
				
				<c:if test="${(edusat.edu_field6_yn eq 'R' or edusat.edu_field6_yn eq 'Y') and (sessionScope.ss_m_id eq '' or sessionScope.ss_m_id eq null)}">
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_name">비밀번호</label></th>
					<td colspan="2">
						<input type="password" name="es_pwd" id="es_pwd" value="" class="board_input middle"  maxlength="20" size="20"/>
						<span class="text1">* 신청확인 시 사용할 비밀번호를 입력해주세요.</span><br>
					</td>
				</tr>
				</c:if>
				
				<c:if test="${edusat.edu_field1_yn eq 'R' or edusat.edu_field1_yn eq 'Y'}">
				<tr>
					<th scope="row">${edusat.edu_field1_yn eq 'R' ? '<img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> ' : '' }<label for="es_name2">기관명</label></th>
					<td colspan="2">
						<input type="text" size="20" id="es_name2" name="es_name2" class="board_input middle" value="${edurequest.es_name2}" maxlength="20" />
						
					</td>
				</tr>
				</c:if>
				
				<c:if test="${(edusat.edu_field2_yn eq 'R' or edusat.edu_field2_yn eq 'Y') and gubun_num eq '1'}">
				<tr>
					<th scope="row">${edusat.edu_field2_yn eq 'R' ? '<img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> ' : '' }<label for="es_addr1">기관주소</label></th>
					<td colspan="2">
						<style>
							#Search_Form {overflow:auto; height:150px; border:1px solid #cdcdcd; margin-top:3px; margin-right:10px; padding:6px;}
							#Addr_search2 span.text1 {font-size:0.9em; color:#ad6767;}
						</style>

					<div id="Addr_search1" style="display: block;">
						<input type="text" size="7" title="우편번호 다섯자리" id="es_zipcode" name="es_zipcode" class="input_box board_input" value="${edurequest.es_zipcode }" maxlength="5" readonly="readonly">
						<input type="button" value="우편번호찾기" class="ct_bt01" onclick="sample6_execDaumPostcode();">
						<span class="text1">* 우편번호</span>
							
						<div class="pt3">
						<input type="text" size="40" title="주소" id="es_addr1" name="es_addr1" class="input_box board_input" maxlength="100" value="${edurequest.es_addr1 }" />
						<span class="text1">* 시·도 + 시·군·구 + 읍·면 + 도로명</span><br> 
						<input type="text" size="40" title="상세주소" id="es_addr2" name="es_addr2" class="input_box board_input"  maxlength="100" value="${edurequest.es_addr2 }" />
						<span class="text1">* 상세주소</span><br>
						</div>
					</div>
					</td>
				</tr>
				</c:if>
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /><label for="es_email">이메일</label></th>
					<td colspan="2">
						<input type="text" size="40" title="이메일" id="es_email" name="es_email" class="board_input middle"  maxlength="100" value="${edurequest.es_email }" />
					</td>
				</tr>
				<c:if test="${gubun_num ne '2' }">
				<tr>
					<th rowspan="2" scope="col"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_bphone1">연락처</label></th>
					<td scope="row">유선전화</td>
					
					<td>
						<c:if test="${!empty sessionScope.ss_user_mobile}">
							<c:choose>
								<c:when test="${fn:contains(sessionScope.ss_user_mobile, '-')}">
									<c:set var="arr" value="${fn:split(sessionScope.ss_user_mobile, '-') }" />
									<c:set var="es_bphone1" value="${arr[0]}" />
									<c:set var="es_bphone2" value="${arr[1]}" />
									<c:set var="es_bphone3" value="${arr[2]}" />
								</c:when>
								<c:otherwise>
									<c:set var="es_bphone1" value="${fn:substring(sessionScope.ss_user_mobile,0,3)}" />
									<c:set var="es_bphone2" value="${fn:substring(sessionScope.ss_user_mobile,3,7)}" />
									<c:set var="es_bphone3" value="${fn:substring(sessionScope.ss_user_mobile,7,11)}" />
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${!empty edusatreq.es_bphone1 }">
							<c:set var="es_bphone1" value="${edusatreq.es_bphone1}" />
							<c:set var="es_bphone2" value="${edusatreq.es_bphone2}" />
							<c:set var="es_bphone3" value="${edusatreq.es_bphone3}" />
						</c:if>	
						<c:if test="${!empty edurequest.es_bphone1 }">
							<c:set var="es_bphone1" value="${edurequest.es_bphone1}" />
							<c:set var="es_bphone2" value="${edurequest.es_bphone2}" />
							<c:set var="es_bphone3" value="${edurequest.es_bphone3}" />
						</c:if>
				
						<input type="text" size="7" id="es_bphone1" name="es_bphone1" class="board_input mini"  onblur="SetNum(this);" value="${es_bphone1 }" maxlength="4" ${!empty sessionScope.ss_user_handphone ? 'readonly=readonly' : ''} />
						- <input type="text" size="7" id="es_bphone2" name="es_bphone2" class="board_input mini"  onblur="SetNum(this);" value="${es_bphone2 }" maxlength="4" ${!empty sessionScope.ss_user_handphone ? 'readonly=readonly' : ''} />
						- <input type="text" size="7" id="es_bphone3" name="es_bphone3" class="board_input mini"  onblur="SetNum(this);" value="${es_bphone3 }" maxlength="4"  ${!empty sessionScope.ss_user_handphone ? 'readonly=readonly' : ''}/>
					</td>
				</tr>
				
				<tr>
					<td scope="row">휴대전화</td>
					<td>
						<c:if test="${!empty sessionScope.ss_user_mobile}">
							<c:choose>
								<c:when test="${fn:contains(sessionScope.ss_user_mobile, '-')}">
									<c:set var="arr" value="${fn:split(sessionScope.ss_user_mobile, '-') }" />
									<c:set var="es_phone1" value="${arr[0]}" />
									<c:set var="es_phone2" value="${arr[1]}" />
									<c:set var="es_phone3" value="${arr[2]}" />
								</c:when>
								<c:otherwise>
									<c:set var="es_phone1" value="${fn:substring(sessionScope.ss_user_mobile,0,3)}" />
									<c:set var="es_phone2" value="${fn:substring(sessionScope.ss_user_mobile,3,7)}" />
									<c:set var="es_phone3" value="${fn:substring(sessionScope.ss_user_mobile,7,11)}" />
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${!empty edusatreq.es_phone1 }">
							<c:set var="es_phone1" value="${edusatreq.es_phone1}" />
							<c:set var="es_phone2" value="${edusatreq.es_phone2}" />
							<c:set var="es_phone3" value="${edusatreq.es_phone3}" />
						</c:if>	
						<c:if test="${!empty edurequest.es_phone1 }">
							<c:set var="es_phone1" value="${edurequest.es_phone1}" />
							<c:set var="es_phone2" value="${edurequest.es_phone2}" />
							<c:set var="es_phone3" value="${edurequest.es_phone3}" />
						</c:if>	
				
						<input type="text" size="7" id="es_phone1" name="es_phone1" class="board_input mini"  onblur="SetNum(this);" value="${es_phone1 }" maxlength="4" ${!empty sessionScope.ss_user_handphone ? 'readonly=readonly' : ''} />
						- <input type="text" size="7" id="es_phone2" name="es_phone2" class="board_input mini"  onblur="SetNum(this);" value="${es_phone2 }" maxlength="4" ${!empty sessionScope.ss_user_handphone ? 'readonly=readonly' : ''} />
						- <input type="text" size="7" id="es_phone3" name="es_phone3" class="board_input mini"  onblur="SetNum(this);" value="${es_phone3 }" maxlength="4"  ${!empty sessionScope.ss_user_handphone ? 'readonly=readonly' : ''}/>
					</td>
				</tr>
				</c:if>
				<c:if test="${gubun_num eq '2' }">
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_phone1">휴대전화</label></th>
					<td colspan="2" >
						<c:if test="${!empty sessionScope.ss_user_mobile}">
							<c:choose>
								<c:when test="${fn:contains(sessionScope.ss_user_mobile, '-')}">
									<c:set var="arr" value="${fn:split(sessionScope.ss_user_mobile, '-') }" />
									<c:set var="es_phone1" value="${arr[0]}" />
									<c:set var="es_phone2" value="${arr[1]}" />
									<c:set var="es_phone3" value="${arr[2]}" />
								</c:when>
								<c:otherwise>
									<c:set var="es_phone1" value="${fn:substring(sessionScope.ss_user_mobile,0,3)}" />
									<c:set var="es_phone2" value="${fn:substring(sessionScope.ss_user_mobile,3,7)}" />
									<c:set var="es_phone3" value="${fn:substring(sessionScope.ss_user_mobile,7,11)}" />
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${!empty edusatreq.es_phone1 }">
							<c:set var="es_phone1" value="${edusatreq.es_phone1}" />
							<c:set var="es_phone2" value="${edusatreq.es_phone2}" />
							<c:set var="es_phone3" value="${edusatreq.es_phone3}" />
						</c:if>	
						<c:if test="${!empty edurequest.es_phone1 }">
							<c:set var="es_phone1" value="${edurequest.es_phone1}" />
							<c:set var="es_phone2" value="${edurequest.es_phone2}" />
							<c:set var="es_phone3" value="${edurequest.es_phone3}" />
						</c:if>	
				
						<input type="text" size="7" id="es_phone1" name="es_phone1" class="board_input mini"  onblur="SetNum(this);" value="${es_phone1 }" maxlength="4" ${!empty sessionScope.ss_user_handphone ? 'readonly=readonly' : ''} />
						- <input type="text" size="7" id="es_phone2" name="es_phone2" class="board_input mini"  onblur="SetNum(this);" value="${es_phone2 }" maxlength="4" ${!empty sessionScope.ss_user_handphone ? 'readonly=readonly' : ''} />
						- <input type="text" size="7" id="es_phone3" name="es_phone3" class="board_input mini"  onblur="SetNum(this);" value="${es_phone3 }" maxlength="4"  ${!empty sessionScope.ss_user_handphone ? 'readonly=readonly' : ''}/>
					</td>
				</tr>
				</c:if>
				
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_temp2">${gubun_num ne '2' ? '수령자명' : '신청학교명' }</label></th>
					<td colspan="2" >
						<input type="text" size="40" title="수령자명" id="es_temp2" name="es_temp2" class="board_input middle"  maxlength="100" value="${edurequest.es_temp2 }" />
						<span class="text1">${gubun_num ne '2' ? '* 수령자의 성명을 입력하세요.' : '' }</span><br>
					</td>
				</tr>
				
				<c:if test="${(edusat.edu_field2_yn eq 'R' or edusat.edu_field2_yn eq 'Y') and gubun_num ne '1'}">
				<tr>
					<th scope="row">${edusat.edu_field2_yn eq 'R' ? '<img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> ' : '' }<label for="es_addr1">${gubun_num ne '2' ? '주소' : '신청학교 주소' }</label></th>
					<td colspan="2">
						<style>
							#Search_Form {overflow:auto; height:150px; border:1px solid #cdcdcd; margin-top:3px; margin-right:10px; padding:6px;}
							#Addr_search2 span.text1 {font-size:0.9em; color:#ad6767;}
						</style>

					<div id="Addr_search1" style="display: block;">
						<input type="text" size="7" title="우편번호 다섯자리" id="es_zipcode" name="es_zipcode" class="input_box board_input" value="${edurequest.es_zipcode }" maxlength="5"  readonly="readonly">
						<input type="button" value="우편번호찾기" class="ct_bt01" onclick="sample6_execDaumPostcode();">
						<span class="text1">* 우편번호</span>
							
						<div class="pt3">
						<input type="text" size="40" title="주소" id="es_addr1" name="es_addr1" class="board_input middle" maxlength="100" value="${edurequest.es_addr1 }" />
						<span class="text1">* 시·도 + 시·군·구 + 읍·면 + 도로명</span><br> 
						<input type="text" size="40" title="상세주소" id="es_addr2" name="es_addr2" class="board_input middle"  maxlength="100" value="${edurequest.es_addr2 }" />
						<span class="text1">* 상세주소</span><br>
						</div>
					</div>
					</td>
				</tr>
				</c:if>
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_etc">${gubun_num eq '2' ? '전교생 인원<br/><span style="font-size:12px;">(전교생 수를 기입해주세요)</span>' : '신청수량'}</label></th>
					<td colspan="2" >
						<%// <c:when test="${gubun_num eq '0'}"> %>
						<c:choose>
							<c:when test="${gubun_num eq '999'}">
								<select id="es_etc" name="es_etc" style="vertical-align:middle;height:30px;border:1px solid #dbdbdb;padding-left:8px; font-size:14px;">
									<c:forEach begin="5" end="50" step="5" var="etc_count">
									<option value="${etc_count }" ${etc_count == edurequest.es_etc ? 'selected' : '' }>${etc_count } 개</option>
									</c:forEach>
								</select>
								<span class="text1">* 최대 50개 가능</span><br>
							</c:when>
							<c:otherwise>
								<input type="text" size="20" id="es_etc" name="es_etc" class="board_input middle" value="${edurequest.es_etc}" maxlength="4" onkeyup="setNum(this)"/>
								 ${gubun_num ne '2' ? '개 <span class="text1">* 투명우산을 수령하고자하는 기관의 어린이 수에 맞게 신청해주세요.</span><br>' : '명 <span class="text1 text3">* 전교생 인원을 모르실 경우 ‘학교알리미’ 사이트에 접속해서 확인해주세요.</span><br>' }
							</c:otherwise>
						</c:choose>
						
					</td>
				</tr>
				
				<c:if test="${gubun_num eq '2' }">
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_etc">학교와의 관계를<br/> 선택해주세요</label></th>
					<td colspan="2">
						<input type="radio" name="es_temp6" id="es_temp6_1" ${(edurequest.es_temp6 eq '1') ? 'checked' : '' } value="1" /> <label for="es_temp6_1">자녀의 학교</label>
						<input type="radio" name="es_temp6" id="es_temp6_2" ${(edurequest.es_temp6 eq '2') ? 'checked' : '' } value="2" /> <label for="es_temp6_2">친척 및 지인자녀의 학교</label>
						<input type="radio" name="es_temp6" id="es_temp6_3" ${(edurequest.es_temp6 eq '3') ? 'checked' : '' } value="3" /> <label for="es_temp6_3">본인의 모교</label>
						<input type="radio" name="es_temp6" id="es_temp6_4" ${(edurequest.es_temp6 eq '4' || edurequest.es_temp6 eq null) ? 'checked' : '' } value="4" /> <label for="es_temp6_4">기타</label>
					</td>
				</tr>
				
				<tr>
					<th scope="row"><label for="es_temp7">자녀의 성명 <span style="font-size:12px;"><br/>(자녀 또는 지인의 자녀가<br/>재학생일 경우 작성해주세요)</span></label></th>
					<td colspan="2">
						 <input type="text" size="40" title="자녀의성명" id="es_temp7" name="es_temp7" class="board_input middle"  maxlength="100" value="${edurequest.es_temp7 }" />
					</td>
				</tr>
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_temp8">담당교사의 성명</label></th>
					<td colspan="2">
						<input type="text" size="40" title="담임교사의성명" id="es_temp8" name="es_temp8" class="board_input middle"  maxlength="100" value="${edurequest.es_temp8 }" />
					</td>
				</tr>
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_temp9_1">담당교사의 연락처</label></th>
					<td colspan="2">
						<input type="text" size="7" id="es_temp9_1" name="es_temp9_1" class="board_input mini"  onblur="SetNum(this);" value="${edurequest.es_temp9_1 }" maxlength="4"  />
						- <input type="text" size="7" id="es_temp9_2" name="es_temp9_2" class="board_input mini"  onblur="SetNum(this);" value="${edurequest.es_temp9_2 }" maxlength="5"/>
						- <input type="text" size="7" id="es_temp9_3" name="es_temp9_3" class="board_input mini"  onblur="SetNum(this);" value="${edurequest.es_temp9_3 }" maxlength="4"/>
					</td>
				</tr>
				
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_etc">나눔식 가능여부를<br/>선택해주세요</label></th>
					<td colspan="2" >
						<input type="radio" name="es_temp10" id="es_temp10_1" ${(edurequest.es_temp10 eq '1') ? 'checked' : '' } value="1" /> <label for="es_temp10_1">나눔식 가능</label>
						<input type="radio" name="es_temp10" id="es_temp10_2" ${(edurequest.es_temp10 eq '2' || edurequest.es_temp10 eq null) ? 'checked' : '' } value="2" /> <label for="es_temp10_2">나눔식 불가능</label>
						<span class="text1 text3">* 나눔식이 불가능할 경우 우선순위에서 제외될 수 있습니다</span><br>
					</td>
				</tr>
				</c:if>
				
				<c:if test="${edusat.edu_field4_yn eq 'R' or edusat.edu_field4_yn eq 'Y'}">
				<tr>
					<th scope="row" ><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_ptcp_cnt">교육대상 및 인원</label></th>
					<td>1 ~ 6학년</td>
					<td>
						<input type="radio" name="es_video" id="es_video_Y" ${(edurequest.es_video eq 'Y') ? 'checked' : '' } value="Y" /> <label for="es_video_Y">신청</label>
						<input type="radio" name="es_video" id="es_video_N" ${(edurequest.es_video ne 'Y') ? 'checked' : '' } value="N" /> <label for="es_video_N">미신청</label>
						<span class="text1">* 일부학년만 신청도 가능합니다.</span><br>
					</td>
				</tr>
				<%--
				<tr>
					<th scope="row" rowspan="2"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_ptcp_cnt">교육대상 및 인원</label></th>
					<td>1, 2학년</td>
					<td>
						<div class="mb10">
							<span class="text1 text2">1학년</span>
							<input type="text" size="20" id="es_fgrade" name="es_fgrade" class="board_input middle" value="${edurequest.es_fgrade }" maxlength="4" onkeyup="setNum(this)" /> 명
							<input type="text" size="20" id="es_fgrade_class" name="es_fgrade_class" class="board_input middle" value="${edurequest.es_fgrade_class }" maxlength="4" onkeyup="setNum(this)" /> 개반
						</div>
						<div>
							<span class="text1 text2">2학년</span>
							<input type="text" size="20" id="es_sgrade" name="es_sgrade" class="board_input middle" value="${edurequest.es_sgrade }" maxlength="4" onkeyup="setNum(this)" /> 명
							<input type="text" size="20" id="es_sgrade_class" name="es_sgrade_class" class="board_input middle" value="${edurequest.es_sgrade_class }" maxlength="4" onkeyup="setNum(this)" /> 개반
						</div>						
					</td>
				</tr>
				
				<tr>
					<td>3 ~ 6학년<br/>(교육영상 시청)</td>
					<td>
						<input type="radio" name="es_video" id="es_video_Y" ${(edurequest.es_video eq 'Y') ? 'checked' : '' } value="Y" /> <label for="es_video_Y">신청</label>
						<input type="radio" name="es_video" id="es_video_N" ${(edurequest.es_video ne 'Y') ? 'checked' : '' } value="N" /> <label for="es_video_N">미신청</label>
						<span class="text1">* 교육영상 시청은 학교 전체 학생 기준으로 진행됩니다.</span><br>					
					</td>
				</tr>
				--%>
				
				<%--<tr>
					<td>유치원</td>
					<td>
						<div class="mb10">
							<span class="text1 text2">6세</span>
							<input type="text" size="20" id="es_kgrade" name="es_kgrade" class="board_input middle" value="${edurequest.es_kgrade }" maxlength="2" onkeyup="setNum(this)" /> 명
							<input type="text" size="20" id="es_kgrade_class" name="es_kgrade_class" class="board_input middle" value="${edurequest.es_kgrade_class }" maxlength="2" onkeyup="setNum(this)" /> 개반
						</div>
						<div class="mb10">
							<span class="text1 text2">7세</span>
							<input type="text" size="20" id="es_cgrade" name="es_cgrade" class="board_input middle" value="${edurequest.es_cgrade }" maxlength="2" onkeyup="setNum(this)" /> 명
							<input type="text" size="20" id="es_cgrade_class" name="es_cgrade_class" class="board_input middle" value="${edurequest.es_cgrade_class }" maxlength="2" onkeyup="setNum(this)" /> 개반
						</div>
						<!-- 
						<div>
							<span class="text1 text2">기타(5세 미만)</span>
							<input type="text" size="20" id="es_ograde" name="es_ograde" class="board_input middle" value="${edurequest.es_ograde }" maxlength="2" onkeyup="setNum(this)" /> 명
						</div>
						 -->					
					</td>
				</tr>
				--%>
				</c:if>
				
				<c:if test="${(edusat.edu_field5_yn eq 'R' or edusat.edu_field5_yn eq 'Y')}">
				<tr>
					<th scope="row">${edusat.edu_field5_yn eq 'R' ? '<img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> ' : '' }귀 기관에서 최근 1년간 어린이 교통사고가 발생한 
적이 있습니까?</th>
					<td colspan="2">
						<label><input type="radio" value="Y" name="es_problem_yn" onclick="document.getElementById('es_problem_wrap').style.display='inline'" id="es_problem_y" ${edurequest.es_problem_yn eq 'Y' ? 'checked' : '' } /> 예</label>
						<span style="display: ${edurequest.es_problem_yn eq 'Y' ? 'inline' : 'none' }; margin:0px 5px" id="es_problem_wrap">총 &nbsp;<input type="text" value="${edurequest.es_problem }" name="es_problem" id="es_problem" class="board_input" maxlength="10" onkeyup="setNum(this)" /> 건</span>
						<label><input type="radio" value="N" name="es_problem_yn" onclick="document.getElementById('es_problem_wrap').style.display='none'" id="es_problem_n" ${edurequest.es_problem_yn ne 'Y' ? 'checked' : '' } /> 아니오</label>
					</td>
				</tr>
				</c:if>
				
				
				<tr>
					<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif" /> <label for="es_memo">${(gubun_num eq '1') ? '투명우산 및 <br/>교통안전교육 필요성' : '신청사연' }<br/>(최소 300글자 이상)</label></th>
					<td colspan="2">
						<textarea name="es_memo" style="width:90%;height:150px;" class="textarea" minlength="300">${edurequest.es_memo }</textarea>
						<br/>
						<span class="text1">* 사연 신청 시 이모티콘 (문자 + 한자, 이모지) 등은 입력이 안되도록 부탁드립니다.</span>
					</td>
				</tr>
				
				<c:if test="${edusat.edu_field3_yn eq 'R' or edusat.edu_field3_yn eq 'Y'}">
				<tr>
					<th scope="row">첨부파일</th>
					<td style="padding:5px;" colspan="2">
						<jsp:include page="./inc_write_fileupload.jsp" />
					</td>
				</tr>
				</c:if>

			<c:if test="${edusat.edu_ptcp_yn ne 'Y' }">
				<input type="hidden" name="es_ptcp_cnt" value="1" />
			</c:if>

				
			</tbody>
			</table>
	
	</div>
	<!-- //쓰기 테이블 -->
	
	
	
	
 
	<!-- 버튼 -->
	<div class="btn_w">
		<input type="submit" value="${(es_idx eq null or es_idx eq '') ? '신청' : '수정' }" class="con_btn orange" />
		<a href="${prepage }" class="con_btn blue" >취소</a>
	</div>
	<!-- //버튼 -->
 
	</form>
 
	
</div>

<jsp:include page="./bthtml.jsp" />
