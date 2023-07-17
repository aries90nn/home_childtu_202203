<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="list_url_tmp" value="${nowURI }?proc_type=list&a_num=${param.a_num }" />
<c:set var="prepage" value="${empty param.prepage ? list_url_tmp : param.prepage}" />
<c:if test="${!empty board.b_name and is_ad_cms ne 'Y' }">
	<c:set var="readonly" value="readonly='readonly'" />
</c:if>


<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>
<script type="text/javascript" src="/nfu/NFU_class.js" charset="utf-8"></script>

<script type="text/javascript">//<![CDATA[

	function w_chk(){
		var eForm = document.getElementById('frm');

		if(eForm.b_temp8.value == "예약취소"){
			if(!confirm("예약을 취소하시겠습니까?")){
				eForm.b_temp8.focus();
				return false;
			}
		}
		eForm.b_subject.value = eForm.b_name.value;

		if (CheckSpaces(document.getElementById('frm').b_name, '신청자 성명')) {return false;}
		<c:if test='${board.b_pwd eq ""}'>
		else if (CheckSpaces(document.getElementById('frm').b_pwd, '비밀번호')) {return false;}
		</c:if>		
		else if (CheckSpaces(eForm.b_phone1, "신청자 연락처 첫번째")) {return false;}
		else if (CheckSpaces(eForm.b_phone2, "신청자 연락처 두번째")) {return false;}
		else if (CheckSpaces(eForm.b_phone3, "신청자 연락처 세번째")) {return false;}

		<c:if test='${config.a_home eq "Y" and config.a_home_req eq "Y"}'>
		else if (CheckSpaces(eForm.b_zip1, "주소")) {return false;}
		else if (CheckSpaces(eForm.b_addr1, "주소")) {return false;}
		</c:if>
		
		else if (CheckRadio(eForm.b_temp1, "성별")) {return false;}
		
		//else if (CheckSpaces(eForm.b_temp3, "소속")) {return false;}
		else if (CheckSpaces(eForm.b_temp4, "1365아이디")) {return false;}
		
		<c:if test='${config.a_email eq "Y" and config.a_email_req eq "Y"}'>
		else if (!isCorrectEmail(eForm.b_email)) {return false;}
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
			else if (document.getElementById('frm').b_content.value=="<br />" || document.getElementById('frm').b_content.value=="") {
				alert("내용을 입력하세요.");
				return false;
			}
		</c:if>
		</c:if>
		try{
			nfu_upload();
			return false;
		}catch(e){return true;}
		//loading_st(1);

	}

	function isNull( text ) { 
		if( text == null ) return true; 
		var result = text.replace(/(^\s*)|(\s*$)/g, ""); 

		if( result ) 
			return false; 
		else 
			return true; 
	}


	function colorPicker(val){
		if(val==1){
			document.getElementById('colorPicker_view').style.visibility='visible';

		}else if(val==2){
			document.getElementById('colorPicker_view').style.visibility='hidden'; 
		}
	}

	function colorselect(color) {
		document.getElementById('colorsel').style.backgroundColor= color;
		document.getElementById('b_subject').style.backgroundColor= color;
		document.getElementById('b_subject').style.color= "#FFFFFF";

		document.getElementsByName('b_sbjclr')[0].value = color;
	}

	function colorselect_white(color) {
		document.getElementById('colorsel').style.backgroundColor= color;
		document.getElementById('b_subject').style.backgroundColor= color;
		document.getElementById('b_subject').style.color= "#000000";

		document.getElementsByName('b_sbjclr')[0].value = color;
	}



//]]>
</script>
<c:set var="b_temp8" value="${board.b_temp8}" />
<c:if test="${empty board.b_num}">
	<c:set var="b_temp8" value="승인" />
</c:if>
<!-- 쓰기 -->
<div id="board" style="width:${config.a_width};">
	
	<form id="frm" method="post" action="${DOMAIN_HTTPS}/board/writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk();">
	<div>

	<input type="hidden" name="a_num" value="${config.a_num}" />
	<input type="hidden" name="b_num" value="${board.b_num}" /><!-- (수정일때사용) -->
	<input type="hidden" name="b_type" value="${board.b_type}" />
	<input type="hidden" name="w_mode" value="${board.w_mode}" />
	<input type="hidden" name="b_ref" value="${board.b_ref}" />
	<input type="hidden" name="b_step" value="${board.b_step}" />
	<input type="hidden" name="b_level" value="${board.b_level}" />
	<input type="hidden" name="b_sbjclr" value="${board.b_sbjclr}" />
	<input type="hidden" name="b_open" value="N" />

	<input type="hidden" name="prepage" value="${prepage}" />
	<input type="hidden" name="board_token" value="${board_token}" />
	<input type="hidden" name="b_subject" />
	</div>

	<div class="guide">
		<span><img src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif" alt="*(필수항목)" /> 표시가 된 곳은 필수 항목입니다.</span>
	</div>

	<!-- 쓰기 테이블 -->
	<div class="table_bwrite">	

			<table cellspacing="0" summary="${title}의 이름, 비밀번호, 일시, 내용을 입력">
			<caption>${title}</caption>
				<colgroup>
				<col width="130" />
				<col width="" />
				</colgroup>
			<thead>
				<tr>
					<th scope="col" colspan="2" class="th_end">
					${fn:substring(board.b_sdate, 0, 4)}년 ${fn:substring(board.b_sdate, 5, 7)}월 ${fn:substring(board.b_sdate, 8,10)}일 자원봉사신청서 작성</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row"><label for="b_name"><img alt="*(필수항목)" src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif"/> 성명</label></th>
					<td><input type="text" size="20" id="b_name" name="b_name" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_name}" maxlength="30" ${readonly}/></td>
				</tr>
				<c:if test="${board.b_pwd eq ''}">
				<tr>
					<th scope="row"><label for="b_pwd"><img alt="*(필수항목)" src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif"/> 비밀번호</label></th>
					<td><input type="password" size="22" id="b_pwd" name="b_pwd" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_pwd}" maxlength="30" /> <span class="text1">* 10자 이내의 영문/숫자만 사용하실 수 있습니다. </span></td>
				</tr>
				</c:if>
				
				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif"/> <label for="b_phone1">연락처</label></th>
					<td><input type="text" size="4" title="연락처 첫번째" id="b_phone1" name="b_phone1" class="board_input" onfocus="focus_on1(this);" onblur="SetNum(this);focus_off1(this);" value="${board.b_phone1}" maxlength="4" /> - <input type="text" size="4" title="연락처 두번째" id="b_phone2" name="b_phone2" class="board_input" onfocus="focus_on1(this);" onblur="SetNum(this);focus_off1(this);" value="${board.b_phone2}" maxlength="4" /> - <input type="text" size="4" title="연락처 세번째" id="b_phone3" name="b_phone3" class="board_input" onfocus="focus_on1(this);" onblur="SetNum(this);focus_off1(this);" value="${board.b_phone3}" maxlength="4" /></td>
				</tr>
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
			
				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif"/> <label for="b_temp1_0">성별</label></th>
					<td>
						<input type="radio" id="b_temp1_0" name="b_temp1" value="0" ${board.b_temp1 eq '0' ? 'checked="checked"' : '' } /><label for="b_temp1_0">남</label>
						<input type="radio" id="b_temp1_1" name="b_temp1" value="1" ${board.b_temp1 eq '1' ? 'checked="checked"' : '' } /><label for="b_temp1_1">여</label>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="b_temp2"><img alt="*(필수항목)" src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif"/> 희망시간</label></th>
					<td>
						<input type="hidden" id="b_sdate" name="b_sdate" value="${board.b_sdate}" maxlength="10" />

<c:set var="sel" value="N"/>
<c:choose>
	<c:when test="${fn:length(time_config) == 1 or !empty board.b_num}">
		<c:if test="${cnt_array[0] < max_count[0]}">
			<c:set var="sel" value="Y"/>
		</c:if>
		<input type="hidden" name="b_temp2" value="${board.b_temp2}^${max_count[0]}" />
		${board.b_temp2}
	</c:when>
	<c:otherwise>
		<span class="select_style">
		<select id="b_temp2" name="b_temp2">
			<c:forEach items="${time_config}" var="time_config" varStatus="no">
			<c:if test="${cnt_array[no.index] < max_count[no.index]}">
				<c:set var="sel" value="Y"/>
				<option value="${time_config}^${max_count[no.index]}">${time_config}</option>
			</c:if>
			</c:forEach>
		</select>
		</span>
	</c:otherwise>
</c:choose>

<c:if test="${sel eq 'N' and empty board.b_num}">
<script>
alert("해당일자는 신청이 마감되었습니다.");
window.history.back();
</script>
</c:if>

					</td>
				</tr>
				<!-- <tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif"/> <label for="b_temp3">소속</label></th>
					<td>
						<input type="text" size="40" id="b_temp3" name="b_temp3" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp3}" maxlength="100" />
						<br /><span class="text1">* 학교, 학년, 반, 번호 필수입력</span>
					</td>
				</tr> -->
				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif"/> <label for="b_temp4">1365아이디</label></th>
					<td>
						<input type="text" size="40" id="b_temp4" name="b_temp4" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp4}" maxlength="100" />
					</td>
				</tr>
<c:choose>
	<c:when test="${empty board.b_num }">
				<input type="hidden" name="b_temp8" id="b_temp8" value="대기" />
	</c:when>
	<c:otherwise>
	
				<tr>
					<th scope="row"><label for="b_temp8">접수상태</label></th>
					<td>
						<span class="select_style">
						<select id="b_temp8" name="b_temp8">
							<option value="대기" ${b_temp8 eq '대기' ? "selected" : ""}>대기</option>
						<c:if test="${is_ad_cms eq 'Y'}">
							<option value="승인" ${b_temp8 eq '승인' ? "selected" : ""}>승인</option>
							<option value="보류" ${b_temp8 eq '보류' ? "selected" : ""}>보류</option>
						</c:if>
							<option value="예약취소" ${b_temp8 eq '예약취소' ? "selected" : ""}>예약취소</option>
						</select>
						</span>
					</td>
				</tr>
		
	</c:otherwise>
</c:choose>
				
			</tbody>
			</table>
	
	</div>
	<!-- //쓰기 테이블 -->

	<!-- 버튼 -->
	<div class="board_button2">
		<span><input id="submitbtn" class="con_btn blue" type="submit" value="저장" /></span>
		<span><a href="${prepage}" class="con_btn dgray">취소</a></span>
	</div>
	<!-- //버튼 -->

	</form>

</div>
<!-- //쓰기 -->


<!-- 스크립트 -->
<jsp:include page="../foot_board_script.jsp" />
<!-- //스크립트 -->