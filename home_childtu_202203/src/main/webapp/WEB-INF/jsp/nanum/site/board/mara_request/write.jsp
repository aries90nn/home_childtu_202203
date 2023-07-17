<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="list_url_tmp" value="/${BUILDER_DIR }/" />
<c:set var="prepage" value="${empty param.prepage ? list_url_tmp : param.prepage}" />
<c:if test="${!empty board.b_name and is_ad_cms ne 'Y' }">
	<c:set var="readonly" value="readonly='readonly'" />
</c:if>

<jsp:useBean id="now" class="java.util.Date" />
<c:set var="nowyear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
<c:set var="nowmonth"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
<c:set var="nowday"><fmt:formatDate value="${now}" pattern="dd" /></c:set>

<c:if test="${!empty board.b_num }">
	<c:set var="b_temp3_1" value="${fn:split(board.b_temp3, '-')[0]}" />
	<c:set var="b_temp3_2" value="${fn:split(board.b_temp3, '-')[1]}" />
	<%--코스별 쪽수 문자열 --%>
	<c:if test="${!empty board.b_temp7 }">
		<fmt:formatNumber var="pagecnt" value="${board.b_temp7 }" pattern="#,###" />
	</c:if>
	<c:set var="b_temp7_str" value="${courseList[board.b_temp7] } (${pagecnt }쪽)" />
	<c:if test="${empty courseList[board.b_temp7] }">
		<c:set var="b_temp7_str" value="${board.b_temp7 }" />
	</c:if>
</c:if>

<c:if test="${!empty board.b_temp4 }">
	<c:set var="b_temp4_1" value="${fn:split(board.b_temp4, '-')[0]}" />
	<c:set var="b_temp4_2" value="${fn:split(board.b_temp4, '-')[1]}" />
	<c:set var="b_temp4_3" value="${fn:split(board.b_temp4, '-')[2]}" />
</c:if>
<c:if test="${!empty board.b_temp6 }">
	<c:set var="b_temp6_1" value="${fn:split(board.b_temp6, '-')[0]}" />
	<c:set var="b_temp6_2" value="${fn:split(board.b_temp6, '-')[1]}" />
	<c:set var="b_temp6_3" value="${fn:split(board.b_temp6, '-')[2]}" />
</c:if>

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>
<script type="text/javascript" src="/nfu/NFU_class.js" charset="utf-8"></script>


<div>

<script type="text/javascript">
//<![CDATA[
	
	function w_chk(){
		var eForm = document.getElementById('frm');

		var nowDateCompare = new Date();
		
		<c:if test="${is_ad_cms eq 'Y'}">
		var noticeChk = $(":input:radio[name='b_noticechk']:checked").val();
		var notice_period = "Y";
		
		if(noticeChk == "Y"){
			var no_sDate = $( "input[name='b_notice_sdate']" ).val(); //2017-12-10
			var no_sDateArr = no_sDate.split('-');
			var no_eDate = $( "input[name='b_notice_edate']" ).val(); //2017-12-09
			var no_eDateArr = no_eDate.split('-');

			var no_sTime = $( "input[name='b_notice_sdate_time']" ).val(); //00:00
			var no_sTimeArr = no_sTime.split(':');

			var no_eTime = $( "input[name='b_notice_edate_time']" ).val(); //23:59
			var no_eTimeArr = no_eTime.split(':');

			var no_sDateCompare = new Date(no_sDateArr[0], parseInt(no_sDateArr[1])-1, no_sDateArr[2], no_sTimeArr[0], no_sTimeArr[1]);
			var no_eDateCompare = new Date(no_eDateArr[0], parseInt(no_eDateArr[1])-1, no_eDateArr[2], no_eTimeArr[0], no_eTimeArr[1]);
			
			if(no_sDateCompare.getTime() > no_eDateCompare.getTime()) {
				notice_period = "N";
			}
			<c:if test='${board.b_num eq ""}'>
			if(nowDateCompare.getTime() > no_eDateCompare.getTime()) {
				notice_period = "N2";
			}
			</c:if>
		}
		</c:if>

		try{
			if(!document.getElementById("agree").checked){
				alert("개인정보 수집 및 이용에 동의하셔야 서비스 이용이 가능합니다.");
				document.getElementById("agree").focus();
				return false;
			}
		}catch(e){console.log(e);}
		
		
		
		
		if (CheckSpaces(document.getElementById('frm').b_name, '이름')) {return false;}
		<c:if test='${board.b_pwd eq ""}'>
		else if (CheckSpaces(document.getElementById('frm').b_pwd, '비밀번호')) {return false;}			
		</c:if>

		else if (CheckSpaces(eForm.b_temp1, "분류")) {return false;}
		else if (eForm.b_temp1.value != "4") {
			if (CheckSpaces(eForm.b_temp2, "학교")) {return false;}
			if (CheckSpaces(eForm.b_temp3_1, "학년")) {return false;}
			if (CheckSpaces(eForm.b_temp3_2, "반")) {return false;}
		}
		
		<c:if test='${config.a_home eq "Y" and config.a_home_req eq "Y"}'>
		if (CheckSpaces(eForm.b_zip1, "주소")) {return false;}
		if (CheckSpaces(eForm.b_addr1, "주소")) {return false;}
		</c:if>
		
		<c:if test='${config.a_phone eq "Y" and config.a_phone_req eq "Y"}'>
		if (CheckSpaces(eForm.b_phone1, "전화번호 첫번째")) {return false;}
		if (CheckSpaces(eForm.b_phone2, "전화번호 두번째")) {return false;}
		if (CheckSpaces(eForm.b_phone3, "전화번호 세번째")) {return false;}
		</c:if>
		
		if (CheckSpaces(eForm.b_temp4_1, "휴대전화 첫번째")) {return false;}
		if (CheckSpaces(eForm.b_temp4_2, "휴대전화 두번째")) {return false;}
		if (CheckSpaces(eForm.b_temp4_3, "휴대전화 세번째")) {return false;}
		
		<c:if test='${config.a_email eq "Y" and config.a_email_req eq "Y"}'>
		if (!isCorrectEmail(eForm.b_email)) {return false;}
		</c:if>
		
		if (CheckRadio(eForm.b_temp5, "성별")) {	return false;}
		
		if (CheckSpaces(eForm.b_temp6_1, "생년월일 연도")) {return false;}
		if (CheckSpaces(eForm.b_temp6_2, "생년월일 월")) {return false;}
		if (CheckSpaces(eForm.b_temp6_3, "생년월일 일")) {return false;}
		
		if (CheckSpaces(eForm.b_temp7, "참가부문")) {return false;}
		if (CheckRadio(eForm.b_temp8, "완주기념품")) {return false;}

		<c:if test='${config.a_cate eq "Y" and board.b_type ne "R"}'>
		if (CheckSpaces(document.getElementById('frm').b_cate_tot, '분류')) {return false;}
		</c:if>
		
		<c:if test='${config.a_content eq "Y" and config.a_edit eq "Y"}'>
			oEditors.getById["b_content"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용된다.
		</c:if>

		<c:if test='${config.a_content eq "Y" and config.a_content_req eq "Y"}'>
			<c:if test='${config.a_edit eq "Y"}'>
					var rcontent = document.getElementById('frm').b_content.value.replace(/&nbsp;/g,"").replace(/(\r\n)/gi,"").replace(/\s/g,"");
					if( rcontent=="" || rcontent=="<P></P>" || rcontent=="<br>"){ 
						alert("내용을 입력하세요."); 
						oEditors.getById["b_content"].exec("FOCUS", []);
						return false; 
					}
			</c:if>
			<c:if test='${config.a_edit ne "Y"}'>
				if (document.getElementById('frm').b_content.value=="<br />" || document.getElementById('frm').b_content.value=="") {
					alert("각오한마디 입력하세요.");
					return false;
				}
			</c:if>
		
		</c:if>
		try{
			nfu_upload();
			return false;
		}catch(e){return true;}
		
		return false;

	}
	

	$(document).ready(function(){
		//숫자만 입력
		$('.only_number').keyup(function () { 
			this.value = this.value.replace(/[^0-9]/g,'');
		});
		
		//분류선택초기화
		typeChange();
		//코스선택초기화
		courseChange();
		$("#b_temp1").change(function(){
			typeChange();
			courseChange();
		});
	});

//]]>
</script>


</div>

<c:if test="${empty board.b_num }">
<!--개인정보수집 및 이용에 대한 안내-->
							<h3 class="tit">개인정보 수집 및 이용에 대한 안내</h3>
							<ul class="list">
									<li>수집하는 개인정보의 항목
										<ul class="list2">
											<li>독서마라톤 신청을 위하여 아래와 같이 최소한의 개인정보를 필수항목으로 수집하고 있습니다.<br />
											필수항목 : 성명, 주소, 전화번호, 휴대전화, 성별, 이메일, 학교(해당시), 학년(해당시), 반(해당시)</li>
										</ul>
									</li>
									<li>개인정보의 수집 및 이용 목적
										<ul class="list2">
											<li>신청 시 수집되는 개인정보는 독서마라톤의 신청 및 운영, 통계 목적으로만 사용되고 다른 용도로 활용되지 않습니다.</li>
										</ul>
									</li>
									<li>개인정보의 보유 및 이용기간
										<ul class="list2">
											<li>개인정보는 대회 접수 마감일로부터 1년간 보유하며 이후 지체 없이 파기됩니다.</li>
											<li>회원가입시 등록된 정보는 회원 탈퇴 시 지체 없이 파기됩니다.</li>
										</ul>
									</li>
									<li>동의거부권 및 동의 거부에 따른 불이익
										<ul class="list2">
											<li>신청자는 개인정보 수집·이용에 대하여 거부할 수 있는 권리가 있습니다. 단, 이에 대한 동의를 거부할 경우에는 독서마라톤 신청이 불가능합니다.</li>
										</ul>
									</li>
							</ul>

							<div class="check">
								<input name="agree" id="agree" type="checkbox" value="1">
								<label for="agree">개인정보 수집 및 이용에 동의합니다.</label>
							</div>
<!--개인정보수집 및 이용에 대한 안내-->
</c:if>


<!-- 쓰기 -->
<div id="board" style="width:${config.a_width};">
	
	<form id="frm" method="post" action="/board/writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk()">
	<div>
	<input type="hidden" name="a_num" value="${config.a_num}" />
	<input type="hidden" name="b_num" value="${board.b_num}" />
	<input type="hidden" name="b_type" value="${board.b_type}" />
	
	<c:if test="${config.a_type ne 'Y'}"><%-- 공개/비공개선택이 아니면 무조건 공개  --%>
	<input type="hidden" name="b_open" value="Y" />
	</c:if>
	<input type="hidden" name="b_ref" value="${board.b_ref}" />
	<input type="hidden" name="b_step" value="${board.b_step}" />
	<input type="hidden" name="b_level" value="${board.b_level}" />
	<input type="hidden" name="prepage" value="${prepage}" />
	<input type="hidden" name="board_token" value="${board_token}" />
	<input type="hidden" name="b_subject" value="${board.b_subject}" />
	</div>


	<div class="guide">
		<span><img src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif" alt="*(필수항목)" /> 표시가 된 곳은 필수 항목입니다.</span>
	</div>

	<!-- 쓰기 테이블 -->
	<div class="table_bwrite">

			<table cellspacing="0" summary="${title}의 이름, 비밀번호, 내용을 입력">
			<caption>${title}</caption>
				<colgroup>
				<col width="130" />
				<col width="" />
				</colgroup>
			<thead>
				<tr>
					<th scope="col" class="th_end" colspan="2">신청정보 입력</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row">신청일</label></th>
					<td>${nowyear }년 ${nowmonth }월 ${nowday }일</td>
				</tr>
				<tr>
					<th scope="row">아이디</label></th>
					<td>${sessionScope.ss_m_id }</td>
				</tr>
				<tr>
					<th scope="row"><label for="b_name"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 이름</label></th>
					<td><input type="text" size="20" id="b_name" name="b_name" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_name}" maxlength="30" ${readonly} /> <span class="text1">* 30자 이내로 입력해주세요. </span></td>
				</tr>

				<c:if test="${board.b_pwd eq ''}">
				<tr>
					<th scope="row"><label for="b_pwd"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 비밀번호</label></th>
					<td><input type="password" size="22" id="b_pwd" name="b_pwd" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_pwd}" maxlength="30" /> <span class="text1">* 30자 이내로 입력해주세요. </span></td>
				</tr>
				</c:if>

				<c:if test="${config.a_type eq 'Y'}">
				<%--<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 공개여부</th>
					<td>
						<input type="radio" id="b_open_Y" name="b_open" value="Y" ${board.b_open == "Y" ? "checked" : ""} ${config.a_blind == "Y" ? "disabled='disabled'" : ""} /><label for="b_open_Y">공개</label>
						<input type="radio" id="b_open_N" name="b_open" value="N" ${board.b_open == "N" ? "checked" : ""}  /><label for="b_open_N">비공개</label>
					</td>
				</tr> --%>
				</c:if>
<c:choose>
	<c:when test="${empty board.b_num }">
				<tr>
					<th scope="row"><img height='10' width='7' alt='*(필수항목)' src='/nanum/site/board/nninc_simple/img/ic_vcheck.gif'/> <label for="b_temp1">분류</label></th>
					<td>
						<select id="b_temp1" name="b_temp1" title="분류 선택" style="vertical-align:middle;height:30px;border:1px solid #dbdbdb;padding-left:8px;">
							<option value="1" ${board.b_temp1 eq '1' ? 'selected="selected"' : '' }>초등저학년</option>
							<option value="5" ${board.b_temp1 eq '5' ? 'selected="selected"' : '' }>초등고학년</option>
							<option value="2" ${board.b_temp1 eq '2' ? 'selected="selected"' : '' }>중학생</option>
							<option value="3" ${board.b_temp1 eq '3' ? 'selected="selected"' : '' }>고등학생</option>
							<option value="4" ${board.b_temp1 eq '4' ? 'selected="selected"' : '' }>일반인</option>
						</select>
					</td>
				</tr>
	</c:when>
	<c:otherwise>
				<input type="hidden" name="b_temp1" value="${board.b_temp1 }" />
	</c:otherwise>
</c:choose>
				<tr>
					<th scope="row"><label for="b_temp2"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 학교</label></th>
					<td><input type="text" size="20" id="b_temp2" name="b_temp2" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp2}" maxlength="30" /> <span class="text1">* (예: 00 초등학교 )</span></td>
				</tr>
				<tr>
					<th scope="row"><label for="b_temp3_1"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 학년</label></th>
					<td>
						<input type="text" size="5" id="b_temp3_1" name="b_temp3_1" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${b_temp3_1}" maxlength="2" /> 학년
						<input type="text" size="5" id="b_temp3_2" name="b_temp3_2" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${b_temp3_2}" maxlength="2" /> 반 <span class="text1">* 일반인의 경우 학교 학년 기입하지 않으셔도 됩니다.</span>
					</td>
				</tr>
				<!-- 주소 -->
				<c:if test="${config.a_home eq 'Y'}">
				<tr>
					<th scope="row"><c:if test="${config.a_home_req eq 'Y'}"><img height='10' width='7' alt='*(필수항목)' src='/nanum/site/board/nninc_simple/img/ic_vcheck.gif'/> </c:if><label for="b_zip1">주소</label></th>
					<td>
						<input type="button" value="우편번호찾기" class="ct_bt01" onclick="daumPostcode();" />
						<input type="text" size="5" title="우편번호 다섯자리" id="b_zip1" name="b_zip1" class="input_box board_input only_number"  value="${board.b_zip1}" maxlength="5" />	
						<span class="text1">* 우편번호 (숫자 5자리)</span><br /> 
					
						<div class="pt3">
						<input type="text" size="40" title="주소" id="b_addr1" name="b_addr1" class="input_box board_input" value="${board.b_addr1}" maxlength="50"  />
						<span class="text1">* 시·도 + 시·군·구 + 읍·면 + 도로명 (50자리 이내로 입력해주세요.)</span><br /> 
						<input type="text" size="40" title="상세주소" id="b_addr2" name="b_addr2" class="input_box board_input" value="${board.b_addr2}" maxlength="50" />
						<span class="text1">* 건물번호 + 동·층·호 + (법정동, 공동주택명) (50자리 이내로 입력해주세요.)</span><br />
						</div>
					</td>
				</tr>
				</c:if>
				<c:if test="${config.a_phone eq 'Y'}">
				<tr>
					<th scope="row"><c:if test="${config.a_phone_req eq 'Y'}"><img height='10' width='7' alt='*(필수항목)' src='/nanum/site/board/nninc_simple/img/ic_vcheck.gif'/> </c:if><label for="b_phone1">전화번호</label></th>
					<td><input type="text" size="4" title="전화번호 첫번째" id="b_phone1" name="b_phone1" class="board_input only_number" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_phone1}" maxlength="4" /> - <input type="text" size="4" title="전화번호 두번째" id="b_phone2" name="b_phone2" class="board_input only_number" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_phone2}" maxlength="4" /> - <input type="text" size="4" title="전화번호 세번째" id="b_phone3" name="b_phone3" class="board_input only_number" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_phone3}" maxlength="4" /> <span class="text1">* 숫자만 입력해주세요. </span></td>
				</tr>
				</c:if>
				<tr>
					<th scope="row"><img height='10' width='7' alt='*(필수항목)' src='/nanum/site/board/nninc_simple/img/ic_vcheck.gif'/><label for="b_temp4_1">휴대전화</label></th>
					<td><input type="text" size="4" title="휴대전화 첫번째" id="b_temp4_1" name="b_temp4_1" class="board_input only_number" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${b_temp4_1}" maxlength="4" /> - <input type="text" size="4" title="휴대전화 두번째" id="b_temp4_2" name="b_temp4_2" class="board_input only_number" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${b_temp4_2}" maxlength="4" /> - <input type="text" size="4" title="휴대전화 세번째" id="b_temp4_3" name="b_temp4_3" class="board_input only_number" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${b_temp4_3}" maxlength="4" /> <span class="text1">* 숫자만 입력해주세요. </span></td>
				</tr>

				<c:if test="${config.a_email eq 'Y'}">
				<tr>
					<th scope="row"><c:if test="${config.a_email_req eq 'Y'}"><img height='10' width='7' alt='*(필수항목)' src='/nanum/site/board/nninc_simple/img/ic_vcheck.gif'/> </c:if><label for="b_email">이메일</label></th>
					<td><input type="text" size="50" id="b_email" name="b_email" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_email}" maxlength="100" /> <span class="text1">* 100자 이내로 <strong>@</strong>포함한 이메일주소를 입력하세요.</span></td>
				</tr>
				</c:if>
				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> <label for="b_email">성별</label></th>
					<td>
						<input type="radio" id="b_temp5_0" name="b_temp5" value="남" ${board.b_temp5 eq "남" ? "checked" : ""} /><label for="b_temp5_0">남</label>
						<input type="radio" id="b_temp5_1" name="b_temp5" value="여" ${board.b_temp5 eq "여" ? "checked" : ""} /><label for="b_temp5_1">여</label>
					</td>
				</tr>
				
				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> <label for="b_temp6_1">생년월일</label></th>
					<td>
						<select id="b_temp6_1" name="b_temp6_1" style="vertical-align:middle;height:30px;border:1px solid #dbdbdb;padding-left:8px;">
							<option value="">년도</option>
<c:forEach var="i" begin="0" end="99">
	<c:set var="yyyy" value="${nowyear-i }" />
							<option value="${yyyy }" ${yyyy eq b_temp6_1 ? 'selected' : ''}>${yyyy }</option>
</c:forEach>
						</select>년
						<select id="b_temp6_2" name="b_temp6_2" style="vertical-align:middle;height:30px;border:1px solid #dbdbdb;padding-left:8px;">
							<option value="">월</option>
<c:forEach var="i" begin="1" end="12">
							<option value="${i}" ${i eq b_temp6_2 ? 'selected' : ''}>${i }</option>
</c:forEach>
						</select>월
						<select id="b_temp6_3" name="b_temp6_3" style="vertical-align:middle;height:30px;border:1px solid #dbdbdb;padding-left:8px;">
							<option value="">일</option>
<c:forEach var="i" begin="1" end="31">
							<option value="${i}" ${i eq b_temp6_3 ? 'selected' : ''}>${i }</option>
</c:forEach>
						</select>일
					</td>
				</tr>
				

				

				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> <label for="b_temp7">참가부문</label></th>
					<td>
<c:choose>
	<c:when test="${empty board.b_temp7 }">
						<select id="b_temp7" name="b_temp7" style="vertical-align:middle;height:30px;border:1px solid #dbdbdb;padding-left:8px;">
							<option value="">코스선택</option>
		<c:forEach items="${courseList}" var="course" varStatus="no">
							<option value="${course.key }" ${board.b_temp7 eq course.key ? 'selected="selected"' : '' }>${course.value } (<fmt:formatNumber value="${course.key }" pattern="#,###" />쪽)</option>
		</c:forEach>							
						</select>
	</c:when>
	<c:otherwise>
						${b_temp7_str } <span class="text1" style="color:#CC3300;">* 개인정보는 독서마라톤 마감전 까지 수정이 가능하나 종목 변경은 불가합니다.</span>
						<input type="hidden" name="b_temp7" value="${board.b_temp7 }" />
	</c:otherwise>
</c:choose>
					</td>
				</tr>
				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> <label for="b_temp8_1">완주기념품</label></th>
					<td>
						<input type="radio" id="b_temp8_1" name="b_temp8" value="완주증서" ${board.b_temp8 eq "완주증서" ? "checked" : ""} /><label for="b_temp8_1">완주증서</label>
						<input type="radio" id="b_temp8_2" name="b_temp8" value="완주메달" ${board.b_temp8 eq "완주메달" ? "checked" : ""} /><label for="b_temp8_2">완주메달</label>
					</td>
				</tr>
				
				<c:if test="${config.a_content eq 'Y'}">
				<tr>
					<th scope="row" colspan="2">각오한마디</th>
				</tr>
				<tr>
					<td colspan="2" class="content">
						<c:if test="${config.a_edit eq 'Y'}">
							<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
							<textarea name="b_content" id="b_content" style="width:100%; height:250px;">${board.b_content}</textarea>
							<script type="text/javascript">
							var oEditors = [];
							nhn.husky.EZCreator.createInIFrame({
								oAppRef: oEditors,
								elPlaceHolder: "b_content",
								sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
								fCreator: "createSEditor2"
							});							
							</script>
						</c:if>
						<c:if test="${config.a_edit ne 'Y'}">
						<textarea cols="50" rows="7" id="b_content" name="b_content" title="게시글 내용 입력" style="width:98%;" onfocus="focus_on1(this);" onblur="focus_off1(this);" >${board.b_content}</textarea>
						</c:if>
					</td>
				</tr>
				</c:if>
				
				<c:if test="${config.a_upload eq 'Y'}">
				<tr>
					<th scope="row">첨부파일</th>
					<td style="padding:5px;">
						<jsp:include page="../inc_write_fileupload.jsp" />
					</td>
				</tr>
				</c:if>
				
				
				<c:if test="${config.a_cate eq 'Y' and board.b_type ne 'R'}">
				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> <label for="b_cate_tot">분류</label></th>
					<td>
						<select id="b_cate_tot" name="b_cate_tot" style="vertical-align:middle;height:30px;border:1px solid #dbdbdb;padding-left:8px;">
							<option value="" selected="selected" >분류선택</option>
							<c:forEach items="${codeList}" var="code" varStatus="no">
								<option value="${code.ct_idx},${code.ct_name}" ${code.ct_idx == board.b_cate ? "selected" : ""}>${code.ct_name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				</c:if>
			</tbody>
			</table>
	
	</div>
	<!-- //쓰기 테이블 -->

	<!-- 버튼 -->
	<div class="board_button2">
		<span><input id="submitbtn" class="cbtn cbtn_point" type="submit" value="저장" /></span>
		<span><a href="${prepage}" class="cbtn cbtn_g">취소</a></span>
	</div>
	<!-- //버튼 -->

	</form>

</div>
<!-- //쓰기 -->

<!-- 스크립트 -->
<jsp:include page="../foot_board_script.jsp" />
<!-- //스크립트 -->
