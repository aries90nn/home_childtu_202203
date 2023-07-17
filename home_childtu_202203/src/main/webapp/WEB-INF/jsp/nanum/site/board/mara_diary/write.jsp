<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="list_url_tmp" value="${nowURI }?proc_type=list&a_num=${param.a_num }" />
<c:set var="prepage" value="${empty param.prepage ? list_url_tmp : param.prepage}" />
<c:if test="${!empty board.b_name and is_ad_cms ne 'Y' }">
	<c:set var="readonly" value="readonly='readonly'" />
</c:if>


<jsp:useBean id="now" class="java.util.Date" />
<c:set var="nowyear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
<c:set var="nowmonth"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
<c:set var="nowday"><fmt:formatDate value="${now}" pattern="dd" /></c:set>

<c:if test="${!empty board.b_num }">
	<c:set var="nowyear" value="${fn:substring(board.b_regdate, 0, 4) }" />
	<c:set var="nowmonth" value="${fn:substring(board.b_regdate, 5, 7) }" />
	<c:set var="nowday" value="${fn:substring(board.b_regdate, 8, 10) }" />
</c:if>

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>
<script type="text/javascript" src="/nfu/NFU_class.js" charset="utf-8"></script>

<%-- --%>
<c:choose>
	<c:when test="${mara_rq_board.b_temp7 eq '3000' }">
		<c:set var="min_txt" value="30" />
	</c:when>
	<c:otherwise>
		<c:set var="min_txt" value="50" />
	</c:otherwise>
</c:choose>

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

		if (CheckSpaces(document.getElementById('frm').b_name, '이름')) {return false;}
		<c:if test='${board.b_pwd eq ""}'>
		else if (CheckSpaces(document.getElementById('frm').b_pwd, '비밀번호')) {return false;}			
		</c:if>

		<c:if test='${config.a_phone eq "Y" and config.a_phone_req eq "Y"}'>
		else if (CheckSpaces(eForm.b_phone1, "연락처 첫번째")) {return false;}
		else if (CheckSpaces(eForm.b_phone2, "연락처 두번째")) {return false;}
		else if (CheckSpaces(eForm.b_phone3, "연락처 세번째")) {return false;}
		</c:if>

		<c:if test='${config.a_email eq "Y" and config.a_email_req eq "Y"}'>
		else if (!isCorrectEmail(eForm.b_email)) {return false;}
		</c:if>

		<c:if test='${config.a_cate eq "Y" and board.b_type ne "R"}'>
		else if (CheckSpaces(document.getElementById('frm').b_cate_tot, '분류')) {return false;}
		</c:if>

		<c:if test='${config.a_home eq "Y" and config.a_home_req eq "Y"}'>
		else if (CheckSpaces(eForm.b_zip1, "주소")) {return false;}
		else if (CheckSpaces(eForm.b_addr1, "주소")) {return false;}
		</c:if>

		
		<c:if test="${is_ad_cms eq 'Y'}">
		else if(notice_period == "N") {
			alert("공지기간 시작일자와 종료일자를 확인해 주세요.");
			return false;
		}
		else if(notice_period == "N2") {
			alert("공지기간 종료일자를 현재시간보다 크게 잡아주세요.");
			return false;
		}
		else if(noticeChk == "Y" && CheckSpaces(eForm.b_notice_edate, '공지기간(종료일자)') ){return false;}
		else if(noticeChk == "Y" && CheckSpaces(eForm.b_notice_edate_time, '공지기간(종료시간)') ){return false;}
		</c:if>

		else if (CheckSpaces(eForm.b_subject, '도서명')) {return false;}
		else if (CheckSpaces(eForm.b_temp1, '읽은 쪽수')) {return false;}
		else if (CheckSpaces(eForm.b_temp2, '대출/구입 도서관')) {return false;}
		
		if(eForm.b_temp2.value != "구입도서"){
			if (CheckSpaces(eForm.b_temp6, '도서관명')) {return false;}
		}
		
		if (CheckSpaces(eForm.b_sdate, '대출/구입 일자')) {return false;}
		else if (CheckSpaces(eForm.b_temp4, '저자')) {return false;}
		else if (CheckSpaces(eForm.b_temp5, '출판사')) {return false;}
		if (eForm.b_content_len.value < ${min_txt}){alert("독서감상문을 ${min_txt}자 이상 입력하세요.");eForm.b_content.focus();return false;}
		
		

		
		<c:if test='${config.a_content eq "Y" and config.a_content_req eq "Y"}'>
				if (document.getElementById('frm').b_content.value=="<br />" || document.getElementById('frm').b_content.value=="") {
					alert("내용을 입력하세요.");
					return false;
				}
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
		
		$('#b_sdate').attr("readonly", true).helloCalendar({'selectBox':true});
		
		calcMsg($('#b_content')[0]);
		
		libraryCheck();
		$("#b_temp2").change(function(){
			libraryCheck();
		});
	});

	function isNull( text ) { 
		if( text == null ) return true; 
		var result = text.replace(/(^\s*)|(\s*$)/g, ""); 

		if( result ) 
			return false; 
		else 
			return true; 
	}
	
	

//]]>
</script>


</div>

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
	<input type="hidden" id="min_txt" name="min_txt" value="${min_txt }" />
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
					<th scope="col" class="th_end" colspan="2">일지 작성</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row">참가종목</th>
					<td>
						${courseList[mara_rq_board.b_temp7] } (<fmt:formatNumber value="${mara_rq_board.b_temp7 }" pattern="#,###" />)쪽
						/ 누적 : <c:set var="total_page" value="${empty mara_rq_board.b_temp9 ? '0' : mara_rq_board.b_temp9 }" />
						<span style="font-weight:bold;"><fmt:formatNumber value="${total_page }" pattern="#,###" /></span>쪽
						<c:if test="${marathon_close eq 'Y' }">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<span class="text1" style="color:#FF0000;">* 독서마라톤 대회가 종료되었습니다.</span>
						</c:if>
					</td>
				</tr>
				<tr>
					<th scope="row">이름</th>
					<td>${board.b_name}<input type="hidden" id="b_name" name="b_name" value="${board.b_name}" /></td>
				</tr>
				<c:if test="${board.b_pwd eq ''}">
				<tr>
					<th scope="row"><label for="b_pwd"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 비밀번호</label></th>
					<td><input type="password" size="22" id="b_pwd" name="b_pwd" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_pwd}" maxlength="30" /> <span class="text1">* 30자 이내로 입력해주세요. </span></td>
				</tr>
				</c:if>
				
				<tr>
					<th scope="row">일지 작성일</th>
					<td>${nowyear }년 ${nowmonth }월 ${nowday }일</td>
				</tr>

				<c:if test="${config.a_type eq 'Y'}">
				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 공개여부</th>
					<td>
						<input type="radio" id="b_open_Y" name="b_open" value="Y" ${board.b_open == "Y" ? "checked" : ""} ${config.a_blind == "Y" ? "disabled='disabled'" : ""} /><label for="b_open_Y">공개</label>
						<input type="radio" id="b_open_N" name="b_open" value="N" ${board.b_open == "N" ? "checked" : ""}  /><label for="b_open_N">비공개</label>
					</td>
				</tr>
				</c:if>

				<c:if test="${config.a_phone eq 'Y'}">
				<tr>
					<th scope="row"><c:if test="${config.a_phone_req eq 'Y'}"><img height='10' width='7' alt='*(필수항목)' src='/nanum/site/board/nninc_simple/img/ic_vcheck.gif'/> </c:if><label for="b_phone1">연락처</label></th>
					<td><input type="text" size="4" title="연락처 첫번째" id="b_phone1" name="b_phone1" class="board_input only_number" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_phone1}" maxlength="4" /> - <input type="text" size="4" title="연락처 두번째" id="b_phone2" name="b_phone2" class="board_input only_number" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_phone2}" maxlength="4" /> - <input type="text" size="4" title="연락처 세번째" id="b_phone3" name="b_phone3" class="board_input only_number" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_phone3}" maxlength="4" /> <span class="text1">* 숫자만 입력해주세요. </span></td>
				</tr>
				</c:if>

				<c:if test="${config.a_email eq 'Y'}">
				<tr>
					<th scope="row"><c:if test="${config.a_email_req eq 'Y'}"><img height='10' width='7' alt='*(필수항목)' src='/nanum/site/board/nninc_simple/img/ic_vcheck.gif'/> </c:if><label for="b_email">이메일</label></th>
					<td><input type="text" size="50" id="b_email" name="b_email" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_email}" maxlength="100" /> <span class="text1">* 100자 이내로 <strong>@</strong>포함한 이메일주소를 입력하세요.</span></td>
				</tr>
				</c:if>
				
				<c:if test="${is_ad_cms eq 'Y'}">
				<%-- <tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 공지여부</th>
					<td>
						<input type="radio" id="b_noticechk_Y" name="b_noticechk" value="Y" ${board.b_noticechk == "Y" ? "checked" : ""} /><label for="b_noticechk_Y">사용</label>
						<input type="radio" id="b_noticechk_N" name="b_noticechk" value="" ${board.b_noticechk ne "Y" ? "checked" : ""} /><label for="b_noticechk_N">사용안함</label>
					</td>
				</tr> --%>
				</c:if>

				<!-- 공지사항 게시기간 설정 -->
				<jsp:include page="../inc_write_b_show_date.jsp" />

				<c:if test="${config.a_cate eq 'Y' and board.b_type ne 'R'}">
				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> <label for="b_cate_tot">분류</label></th>
					<td>
						<select id="b_cate_tot" name="b_cate_tot" >
							<option value="" selected="selected" >분류선택</option>
							<c:forEach items="${codeList}" var="code" varStatus="no">
								<option value="${code.ct_idx},${code.ct_name}" ${code.ct_idx == board.b_cate ? "selected" : ""}>${code.ct_name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				</c:if>

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
				
				
				
				<tr>
					<th scope="row"><label for="b_subject"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 도서명</label></th>
					<td><input type="text" size="60" id="b_subject" name="b_subject" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_subject}" maxlength="100" />
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="b_temp1"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 읽은 쪽수</label></th>
					<td><input type="text" size="10" id="b_temp1" name="b_temp1" class="board_input only_number" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp1}" maxlength="4" />
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="b_sdate"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 대출/구입 날짜</label></th>
					<td>
						<select id="b_temp2" name="b_temp2" style="vertical-align:middle;height:30px;border:1px solid #dbdbdb;padding-left:8px;">
							<option value="">-- 도서관 선택 --</option>
							<option value="공공도서관" ${board.b_temp2 eq '공공도서관' ? 'selected="selected"' : ''}>공공도서관</option>
							<option value="대학도서관" ${board.b_temp2 eq '대학도서관' ? 'selected="selected"' : ''}>대학도서관</option>
							<option value="학교도서관" ${board.b_temp2 eq '학교도서관' ? 'selected="selected"' : ''}>학교도서관</option>
							<option value="공립작은도서관" ${board.b_temp2 eq '공립작은도서관' ? 'selected="selected"' : ''}>공립작은도서관</option>
							<option value="사립작은도서관" ${board.b_temp2 eq '사립작은도서관' ? 'selected="selected"' : ''}>사립작은도서관</option>
							<option value="구입도서" ${board.b_temp2 eq '구입도서' ? 'selected="selected"' : ''}>구입도서</option>
						</select>
						<input type="text" size="15" id="b_temp6" name="b_temp6" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp6}" maxlength="100" placeholder="대출도서관명" title="대출도서관명" style="display:none;" /> 
						<input type="text" size="15" id="b_sdate" name="b_sdate" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_sdate}" maxlength="10" />
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="b_temp3"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 분류번호</label></th>
					<td>
						<select id="b_temp3" name="b_temp3" style="vertical-align:middle;height:30px;border:1px solid #dbdbdb;padding-left:8px;">
							<option value="000" ${board.b_temp3 eq '000' ? 'selected="selected"' : '' }>000(총류)</option>
							<option value="100" ${board.b_temp3 eq '100' ? 'selected="selected"' : '' }>100(철학)</option>
							<option value="200" ${board.b_temp3 eq '200' ? 'selected="selected"' : '' }>200(종교)</option>
							<option value="300" ${board.b_temp3 eq '300' ? 'selected="selected"' : '' }>300(사회과학)</option>
							<option value="400" ${board.b_temp3 eq '400' ? 'selected="selected"' : '' }>400(순수과학)</option>
							<option value="500" ${board.b_temp3 eq '500' ? 'selected="selected"' : '' }>500(기술과학)</option>
							<option value="600" ${board.b_temp3 eq '600' ? 'selected="selected"' : '' }>600(예술)</option>
							<option value="700" ${board.b_temp3 eq '700' ? 'selected="selected"' : '' }>700(언어)</option>
							<option value="800" ${board.b_temp3 eq '800' ? 'selected="selected"' : '' }>800(문학)</option>
							<option value="900" ${board.b_temp3 eq '900' ? 'selected="selected"' : '' }>900(역사)</option>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="b_temp4"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 저자</label></th>
					<td><input type="text" size="40" id="b_temp4" name="b_temp4" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp4}" maxlength="100" />
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="b_temp5"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 출판사</label></th>
					<td><input type="text" size="60" id="b_temp5" name="b_temp5" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp5}" maxlength="100" />
					</td>
				</tr>
				
				
				<c:if test="${config.a_content eq 'Y'}">
				<tr>
					<th scope="row">
						<img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> <label for="b_content">독서감상문</label>
					</th>
					<td colspan="2">
						<span id="span_b_content_len" class="text1">0 / ${min_txt }자</span><br />
						<input type="hidden" id="b_content_len" name="b_content_len" value="0" />
						<textarea cols="50" rows="10" id="b_content" name="b_content" title="게시글 내용 입력" style="width:98%;" onfocus="focus_on1(this);" onblur="focus_off1(this);" onkeyup="calcMsg(this);">${board.b_content}</textarea>
						<br /><span class="text1">* 독서감상문은 띄어쓰기 빈칸을 포함하여 50자(3km 30자) 이상 기록하여야 합니다.</span>
						
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
			</tbody>
			</table>
	
	</div>
	<!-- //쓰기 테이블 -->

	<!-- 버튼 -->
	<div class="board_button2">
	<c:if test="${marathon_close ne 'Y' }">
		<span><input id="submitbtn" class="cbtn cbtn_point" type="submit" value="저장" /></span>
	</c:if>
		<span><a href="${prepage}" class="cbtn cbtn_g">취소</a></span>
		
	<c:if test="${is_delete eq 'Y' and !empty board.b_num}">
		<c:url var="gourl" value="/board/deleteOk.do">
			<c:param name="a_num" value="${config.a_num }" />
			<c:param name="b_num" value="${board.b_num }" />
			<c:param name="prepage" value="${nowPage }" />
			<c:param name="d_page" value="${path_info }" />
			<c:param name="board_token" value="${board_token }" />
		</c:url>
		<span><a href="${gourl }" onclick="if(!confirm('정말 삭제하시겠습니까?')){return false;}" class="cbtn cbtn_g">삭제</a></span>
	</c:if>
	</div>
	<!-- //버튼 -->

	</form>

</div>
<!-- //쓰기 -->

<!-- 스크립트 -->
<jsp:include page="../foot_board_script.jsp" />
<!-- //스크립트 -->
