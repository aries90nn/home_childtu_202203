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
<script type="text/javascript" src="/nanum/site/common/js/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>
<script type="text/javascript" src="/nfu/NFU_class.js" charset="utf-8"></script>

<script type="text/javascript">
	$(function(){
		$('#b_sdate').attr("readonly", true);
		//시작, 종료일 연동
		$('#b_sdate').helloCalendar({'selectBox':true});
	});

	function w_chk(){
		var eForm = document.getElementById('frm');
		if (CheckSpaces(document.getElementById('frm').b_name, '이름')) {return false;}
		<c:if test='${board.b_pwd eq ""}'>
		else if (CheckSpaces(document.getElementById('frm').b_pwd, '비밀번호')) {return false;}			
		</c:if>

		else if (CheckSpaces(eForm.b_temp1, '시작시간')) {return false;}
		else if (CheckSpaces(eForm.b_subject, '제목')) {return false;}
		else if (CheckSpaces(eForm.b_temp6, '관람등급')) {return false;}

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

		if( !infoCheck(eForm.b_subject, '제목') ){return false;}
		if( !infoCheck(eForm.b_content, '내용') ){return false;}
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

	function isNull( text ) { 
		if( text == null ) return true; 
		var result = text.replace(/(^\s*)|(\s*$)/g, ""); 

		if( result ) 
			return false; 
		else 
			return true; 
	}


	function colorPicker(val){

		if(val==1){ //보여라
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

	function searchMov(){
		var eForm = document.getElementById("frm");
		if (CheckSpaces(eForm.b_subject, '제목')) {return false;}
		searchMovie(eForm.b_subject.value, 'movieSearchResult');
	}
//]]>
</script>

<!-- 쓰기 -->
<div id="board" style="width:${config.a_width};">
	
	<form id="frm" method="post" action="/board/writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk()">
	<div>
	<input type="hidden" name="a_num" value="${config.a_num}" />
	<input type="hidden" name="b_num" value="${board.b_num}" /><!-- (수정일때사용) -->
	<input type="hidden" name="b_type" value="${board.b_type}" />
	<input type="hidden" name="w_mode" value="${board.w_mode}" />
	<c:if test="${config.a_type eq 'N'}">
	<input type="hidden" name="b_open" value="Y" />
	</c:if>
	<input type="hidden" name="b_ref" value="${board.b_ref}" />
	<input type="hidden" name="b_step" value="${board.b_step}" />
	<input type="hidden" name="b_level" value="${board.b_level}" />
	<input type="hidden" name="b_keyword" value="${board.b_keyword}" />
	<input type="hidden" name="prepage" value="${prepage}" />
	<input type="hidden" name="board_token" value="${board_token}" />
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
					<th scope="col" class="th_end" colspan="2">글쓰기</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row"><label for="b_name"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 이름</label></th>
					<td><input type="text" size="20" id="b_name" name="b_name" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_name}" maxlength="30" ${readonly} /> <span class="text1">* 한글만 사용하실 수 있습니다. </span></td>
				</tr>

				<c:if test="${board.b_pwd eq ''}">
				<tr>
					<th scope="row"><label for="b_pwd"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> 비밀번호</label></th>
					<td><input type="password" size="22" id="b_pwd" name="b_pwd" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_pwd}" maxlength="30" /> <span class="text1">* 10자 이내의 영문/숫자만 사용하실 수 있습니다. </span></td>
				</tr>
				</c:if>

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
					<td><input type="text" size="4" title="연락처 첫번째" id="b_phone1" name="b_phone1" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_phone1}" maxlength="4" /> - <input type="text" size="4" title="연락처 두번째" id="b_phone2" name="b_phone2" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_phone2}" maxlength="4" /> - <input type="text" size="4" title="연락처 세번째" id="b_phone3" name="b_phone3" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_phone3}" maxlength="4" /></td>
				</tr>
				</c:if>

				<c:if test="${config.a_email eq 'Y'}">
				<tr>
					<th scope="row"><c:if test="${config.a_email_req eq 'Y'}"><img height='10' width='7' alt='*(필수항목)' src='/nanum/site/board/nninc_simple/img/ic_vcheck.gif'/> </c:if><label for="b_email">이메일</label></th>
					<td><input type="text" size="50" id="b_email" name="b_email" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_email}" maxlength="200" /> <span class="text1">* <strong>@</strong>포함한 이메일주소를 입력하세요.</span></td>
				</tr>
				</c:if>

				<!-- 게시기간 설정 -->
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
						<jsp:include page="../../common/file/inc_address.jsp" />
					</td>
				</tr>
				</c:if>
				
				<!-- 일정 -->
				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif"/> <label for="b_sdate">상영일시</label></th>
					<td>
						<jsp:useBean id="today" class="java.util.Date" />
						<fmt:formatDate value="${toDay}" pattern="yyyy-MM-dd" var="today" /> 
						
						<input type="text" size="100" id="b_sdate" name="b_sdate" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_sdate eq '' ? today : board.b_sdate}" maxlength="10" style="width:80px;" />
						<label for="b_temp1">시작시간</label> : <input type="text" size="10" id="b_temp1" name="b_temp1" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp1}" maxlength="20" />
					</td>
				</tr>

				<tr>
					<th scope="row"><label for="b_subject"><img alt="*(필수항목)" src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif"/> 제목</label></th>
					<td style="padding-top:5px;padding-bottom:5px;">
						<input type="text" size="40" id="b_subject" name="b_subject" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_subject}" maxlength="200" /><input type="button" value="영화검색" onclick="searchMov();" class="movie_search" style="border:0;font-size:12px;display:inlne-block;*display:inline;*zoom:1;padding:12px 10px;margin-left:10px;background:#666;color:#fff;font-weight:bold;" />

							<div id="movie_list" style="display:none;" class="movie_box">
								검색결과가 없습니다.
							</div>

					</td>
				</tr>

				<tr>
					<th scope="row"><label for="b_temp2">상영장소</label></th>
					<td><input type="text" size="20" id="b_temp2" name="b_temp2" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp2}" maxlength="50" /></td>
				</tr>

				<tr>
					<th scope="row"><label for="b_temp3">감독</label></th>
					<td><input type="text" size="20" id="b_temp3" name="b_temp3" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp3}" maxlength="50" /></td>
				</tr>

				<tr>
					<th scope="row"><label for="b_temp4">배우</label></th>
					<td><input type="text" size="50" id="b_temp4" name="b_temp4" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp4}" maxlength="100" /></td>
				</tr>

				<tr>
					<th scope="row"><label for="b_temp5">장르</label></th>
					<td><input type="text" size="50" id="b_temp5" name="b_temp5" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp5}" maxlength="100" /></td>
				</tr>

				<tr>
					<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_calendar/img/ic_vcheck.gif"/> <label for="b_temp6">관람등급</label></th>
					<td>
						<select name="b_temp6" id="b_temp6">
							<option value="">등급선택</option>
							<option value="전체" ${board.b_temp6 eq '전체' ? 'selected':''}>전체</option>
							<option value="만12세이상" ${board.b_temp6 eq '만12세이상' ? 'selected':''}>만12세이상</option>
							<option value="만15세이상" ${board.b_temp6 eq '만15세이상' ? 'selected':''}>만15세이상</option>
							<option value="만18세이상" ${board.b_temp6 eq '만18세이상' ? 'selected':''}>만18세이상</option>
						</select>
					</td>
				</tr>

				<tr>
					<th scope="row"><label for="b_temp7">상영시간</label></th>
					<td><input type="text" size="20" id="b_temp7" name="b_temp7" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp7}" maxlength="10" /></td>
				</tr>

				<tr>
					<th scope="row"><label for="b_temp8">제작년도</label></th>
					<td><input type="text" size="5" id="b_temp8" name="b_temp8" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_temp8}" maxlength="10" /></td>
				</tr>

				<c:if test="${config.a_content eq 'Y'}">
				<tr>
					<td colspan="2" class="content">
						<c:if test="${config.a_edit eq 'Y'}">
							<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
							<div id="div_editor">
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
							</div>
						</c:if>
						<c:if test="${config.a_edit ne 'Y'}">
						<textarea cols="50" rows="15" id="b_content" name="b_content" title="게시글 내용 입력" style="width:98%;" onfocus="focus_on1(this);" onblur="focus_off1(this);" >${board.b_content}</textarea>
						</c:if>
					</td>
				</tr>
				</c:if>
				
				<c:if test="${config.a_upload eq 'Y'}">
				<tr>
					<th scope="row">첨부파일</th>
					<td style="padding:5px;">
						<div>
							<span style="font-size:12px;color:#DF0101">※ 영화검색시 자동으로 영화이미지가 등록됩니다.</span>
						</div>
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
		<span><input id="submitbtn" class="ndls_btn blue" type="submit" value="저장" /></span>
		<span><a href="${prepage}" class="ndls_btn gray">취소</a></span>
	</div>
	<!-- //버튼 -->

	</form>

</div>
<!-- //쓰기 -->