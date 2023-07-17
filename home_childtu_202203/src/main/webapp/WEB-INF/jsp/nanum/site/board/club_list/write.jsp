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





<div class="bookclub">
	<div class="bookclub_top">
		<p class="txt">독서회 게시판은 도서관 동아리가 온라인상에서 자유롭게 의견을 나누는 곳입니다.<br />
		기타 문의사항은 <strong class="eng">053)667-4821</strong>로 연락주시기 바랍니다.</p>
	</div>
</div>
<div class="admin_list">
	<ol class="no5">
		<li>
			<div class="box ${empty board.b_temp8 ? 'style1' : ''}">
				<p class="num">STEP01</p>
				가입신청
			</div>
		</li>
		<li>
			<div class="box ${board.b_temp8 eq '0' ? 'style1' : ''}">
				<p class="num">STEP02</p>
				승인대기
			</div>
		</li>
		<li>
			<div class="box ${board.b_temp8 eq '1' ? 'style1' : ''}">
				<p class="num">STEP03</p>
				승인심사
			</div>
		</li>
		<li>
			<div class="box">
				<p class="num">STEP04</p>
				승인
			</div>
		</li>
		<li>
			<div class="box ${board.b_temp8 eq '2' ? 'style1' : ''}">
				<p class="num">STEP05</p>
				승인완료
			</div>
		</li>
	</ol>
</div>


<h3 class="tit h3_t">가입신청</h3>

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

		if (CheckSpaces(document.getElementById('frm').b_subject, '동아리명')) {return false;}
		if (eForm.dual_chk.value != "Y") {
			alert("동아리명 중복체크를 하세요.");
			document.getElementById("btn_dual_chk").focus();
			return false;
		}
		if (CheckSpaces(document.getElementById('frm').b_name, '신청자명')) {return false;}
		<c:if test='${board.b_pwd eq ""}'>
		else if (CheckSpaces(document.getElementById('frm').b_pwd, '비밀번호')) {return false;}			
		</c:if>

		else if (CheckSpaces(document.getElementById('frm').b_temp1, '대표자명')) {return false;}
		else if (CheckSpaces(document.getElementById('frm').b_temp2, '정기모임')) {return false;}
		else if (CheckSpaces(document.getElementById('frm').b_temp3, '회원자격')) {return false;}
		
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

		

		<c:if test='${config.a_content eq "Y" and config.a_edit eq "Y"}'>
			oEditors.getById["b_content"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용된다.
		</c:if>

		<c:if test='${config.a_content eq "Y" and config.a_content_req eq "Y"}'>
			<c:if test='${config.a_edit eq "Y"}'>
					var rcontent = document.getElementById('frm').b_content.value.replace(/&nbsp;/g,"").replace(/(\r\n)/gi,"").replace(/\s/g,"");
					if( rcontent=="" || rcontent=="<P></P>" || rcontent=="<br>"){ 
						alert("조직목적 및 소개를 입력하세요."); 
						oEditors.getById["b_content"].exec("FOCUS", []);
						return false; 
					}
			</c:if>
			<c:if test='${config.a_edit ne "Y"}'>
				else if (document.getElementById('frm').b_content.value=="<br />" || document.getElementById('frm').b_content.value=="") {
					alert("조직목적 및 소개를 입력하세요.");
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
	});

	function isNull( text ) { 
		if( text == null ) return true; 
		var result = text.replace(/(^\s*)|(\s*$)/g, ""); 

		if( result ) 
			return false; 
		else 
			return true; 
	}
	

	function clubNameSearchCount(){
		var eForm = document.getElementById('frm');
		if (CheckSpaces(eForm.b_subject, '동아리명')) {return;}
		var url = "/board/${config.a_level}/clubNameSearchCount.do";
		url += "?a_num=${config.a_num}";
		url += "&sh_b_subject="+encodeURIComponent(eForm.b_subject.value);
		url += "&sh_b_num=${board.b_num}";
		url += "&dumy="+encodeURIComponent(makeRandomKey(5));
		$.get(url,function(data,status){
			if(trim(data) == "0"){
				alert("사용가능한 동아리명입니다.");
				eForm.dual_chk.value = "Y";
			}else{
				alert(eForm.b_subject.value+"은(는) 이미 사용중인 동아리명입니다.");
				eForm.dual_chk.value = "N";
			}
		});
	}
	
	function dualCheckReset(){
		document.getElementById('frm').dual_chk.value = "N";
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
	<input type="hidden" name="dual_chk" value="${empty board.b_num ? 'N' : 'Y'}" />
	<input type="hidden" name="b_temp4" value="${board.b_temp4}" /><%-- 동아리전용 게시판에 적용될 게시판분류 ct_idx값 --%>
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
					<th scope="col" class="th_end" colspan="2">가입정보 입력</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row"><label for="b_subject"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 동아리명</label></th>
					<td>
					<input type="text" size="60" id="b_subject" name="b_subject" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_subject}" maxlength="100" onchange="dualCheckReset();" /><input type="button" id="btn_dual_chk" value="중복검사" onclick="clubNameSearchCount();" class="movie_search" style="border:0;font-size:13px;display:inlne-block;*display:inline;*zoom:1;padding:6px 7px;margin-left:5px;background:#666;color:#fff;font-weight:bold;" />
					<span class="text1">* 100자리 이내로 입력해주세요.</span>
					</td>
				</tr>
			
				<tr>
					<th scope="row"><label for=b_name><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 신청자명</label></th>
					<td><input type="text" size="20" id="b_name" name="b_name" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_name}" maxlength="30" ${readonly} /> <span class="text1">* 30자 이내로 입력해주세요. </span></td>
				</tr>
				
				

				<c:if test="${board.b_pwd eq ''}">
				<tr>
					<th scope="row"><label for="b_pwd"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 비밀번호</label></th>
					<td><input type="password" size="22" id="b_pwd" name="b_pwd" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_pwd}" maxlength="30" /> <span class="text1">* 30자 이내로 입력해주세요. </span></td>
				</tr>
				</c:if>
				
				<tr>
					<th scope="row"><label for="b_temp1"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 대표자명</label></th>
					<td><input type="text" size="20" id="b_temp1" name="b_temp1" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp1}" maxlength="30" />
				</tr>
				
				<tr>
					<th scope="row"><label for="b_temp2"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 정기모임</label></th>
					<td><input type="text" size="60" id="b_temp2" name="b_temp2" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp2}" maxlength="100" />
				</tr>
				<tr>
					<th scope="row"><label for="b_temp3"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 회원자격</label></th>
					<td><input type="text" size="60" id="b_temp3" name="b_temp3" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp3}" maxlength="100" />
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
				<%--공지여부 --%>
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
				
				
				
				<c:if test="${config.a_content eq 'Y'}">
				<tr>
					<th colspan="2">
						<img height='10' width='7' alt='*(필수항목)' src='/nanum/site/board/nninc_simple/img/ic_vcheck.gif'/> <label for="b_contet">조직목적 및 소개</label>
					</th>
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
						<textarea cols="50" rows="15" id="b_content" name="b_content" title="게시글 내용 입력" style="width:98%;" onfocus="focus_on1(this);" onblur="focus_off1(this);" >${board.b_content}</textarea>
						</c:if>
					</td>
				</tr>
				</c:if>
				
				<c:if test="${config.a_upload eq 'Y'}">
				<tr>
					<th scope="row">독서회이미지</th>
					<td style="padding:5px;">
						<jsp:include page="../inc_write_fileupload.jsp" />
					</td>
				</tr>
				</c:if>
				

<c:choose>
	<c:when test="${is_ad_cms eq 'Y' }">
				<tr>
					<th scope="row"><label for="b_temp8_0"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 승인여부</label></th>
					<td>

						<input type="radio" id="b_temp8_0" name="b_temp8" value="0" ${empty board.b_temp8 or board.b_temp8 eq '0' ? 'checked="checked"' : '' } /><label for="b_temp8_0">승인대기</label>
						<input type="radio" id="b_temp8_1" name="b_temp8" value="1" ${board.b_temp8 eq '1' ? 'checked="checked"' : '' } /><label for="b_temp8_1">승인심사</label>
						<input type="radio" id="b_temp8_2" name="b_temp8" value="2" ${board.b_temp8 eq '2' ? 'checked="checked"' : '' } /><label for="b_temp8_2">승인완료</label>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="b_temp7_0"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 출력여부</label></th>
					<td>
						<input type="radio" id="b_temp7_0" name="b_temp7" value="Y" ${empty board.b_temp7 or board.b_temp7 eq 'Y' ? 'checked="checked"' : '' } /><label for="b_temp7_0">출력</label>
						<input type="radio" id="b_temp7_1" name="b_temp7" value="N" ${board.b_temp7 eq 'N' ? 'checked="checked"' : '' } /><label for="b_temp7_1">미출력</label>
					</td>
				</tr>
		<c:if test="${!empty board.b_num }">
				<tr>
					<th scope="row">등록아이피</th>
					<td>
						${board.b_ip }
					</td>
				</tr>
				<tr>
					<th scope="row">등록일시</th>
					<td>
						${board.b_regdate }
					</td>
				</tr>
		</c:if>
	</c:when>
	<c:otherwise>
				<input type="hidden" name="b_temp8" value="${empty board.b_temp8 ? '0' : board.b_temp8 }" />
				<input type="hidden" name="b_temp7" value="${empty board.b_temp7 ? 'Y' : board.b_temp7 }" />
	</c:otherwise>
</c:choose>

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
