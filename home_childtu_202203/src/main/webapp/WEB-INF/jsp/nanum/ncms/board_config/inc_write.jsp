<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="prepage" value="${empty param.prepage ? 'list.do' : param.prepage }" />

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_board_config.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	//숫자만 입력
	$('.only_number').keyup(function () { 
		this.value = this.value.replace(/[^0-9]/g,'');
	});
});
</script>
<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk()">
<div>
	<input type="hidden" name="a_tablename" value="${board.a_tablename}" />
	<input type="hidden" name="a_replyOpt" value="1" /><!-- 답변(목록/내용) => 사용안함..무조건 목록 -->
	<input type="hidden" name="prc_type" value="${prc_type}" />			<!-- 등록(write) / 수정(edit) -->
	<input type="hidden" name="a_rss" value="N" />
	<input type="hidden" name="a_date_change" value="N" />
	<input type="hidden" name="a_date_list" value="N" />
	<input type="hidden" name="prepage" value="${prepage }" />
</div>

<div id="div_1">

	<h2 class="tit">게시판기본정보<span>기본정보를 입력해주세요. ( <img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다. )</span></h2>
	<fieldset>
	<legend>신규 게시판 생성 서식 작성</legend>
	<table class="bbs_common bbs_default" summary="신규 게시판 생성을 위한 입력 양식입니다.">
	<caption>신규 게시판 생성 서식</caption>
	<colgroup>
		<col width="15%" />
		<col />
	</colgroup>
	<tr>
		<th scope="row"><label for="a_level">게시판종류</label></th>
		<td class="left">
			<select id="a_level" name="a_level" title="게시판종류 선택" class="ta_select" style="width:190px;" onchange="javascript: alevel_change(this.value);">
				<c:forEach var="dir" items="${dir_list}">
					<c:choose><%--제외시킬 스킨 --%>
						<c:when test="${dir.dir eq 'mara_diary' or dir.dir eq 'mara_request' }"></c:when><%--독서마라톤 --%>
						<c:when test="${dir.dir eq 'myposts' }"></c:when><%--내가쓴글 --%>
						<c:when test="${dir.dir eq 'movie' }"></c:when><%--영화상영 --%>
						<c:otherwise>
						
						<option value="${dir.dir}" ${dir.dir eq board.a_level or empty board.a_level ? 'selected="selected"' : '' }>${dir.skin_name}</option>
						
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<span class="point fs11">* 포토게시판 사용시 하단에서 [<strong>파일업로드</strong>] 기능을 꼭 체크하세요</span>
		</td>
	</tr>
	<tr>
		<th scope="row"><label for="a_level"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 게시판명</label></th>
		<td class="left">
			<input type="text" title="게시판명 입력" id="a_bbsname" name="a_bbsname" class="ta_input w5" value="${board.a_bbsname}" maxlength="100" />
			<span class="point fs11">* 게시판명은 100자 이하로 입력해주세요.</span>
		</td>
	</tr>
	<tr>
		<th scope="row"><label for="a_num">고유값 (a_num)</label></th>
		<td class="left">
			<c:choose>
				<c:when test="${board.a_num eq ''}">
					<input type="text" id="a_num" name="a_num" class="ta_input eng only_number" value="${a_num_str}" maxlength="8" /> <span class="point fs11">* 숫자로 이루어진 8자리이하 고유값 <strong>[ 고유값이 없을시 자동생성 됩니다. ]</strong></span>
				</c:when>
				<c:otherwise>
					${board.a_num}
					<input type="hidden" name="a_num" id="a_num" value="${board.a_num}" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th scope="row">회원제</th>
		<td class="left">
			<label for="a_member_Y"><input type="radio" id="a_member_Y" name="a_member" value="Y" title="사용 선택" <c:if test='${board.a_member == "Y"}'> checked</c:if> />사용</label>
			<label for="a_member_N"><input type="radio" id="a_member_N" name="a_member" value="N" title="사용안함 선택" <c:if test='${board.a_member == "N"}'> checked</c:if> />사용안함</label>
			<span class="point fs11">* 글목록에서 본인이 작성한 글만 보이게 하는 기능입니다.</span>
		</td>
	</tr>

	<tr>
		<th scope="row"><label for="a_ad_cms_id">관리자 아이디</label></th>
		<td class="left">
			<input type="text" size="24" title="관리자 아이디 입력" id="a_ad_cms_id" name="a_ad_cms_id" class="ta_input" value="${board.a_ad_cms_id}" /><span class="point fs11">* 사용 예) test1, test2, test3</span>
			<input type="button" value="검색열기" id="schBtn" class="btn_gr_zipcode" onclick="viewSchDiv();" />

<script type="text/javascript">
var schFlag = false;
function viewSchDiv(){
	if(schFlag){
		document.getElementById("schBtn").value="검색열기";
		document.getElementById('Addr_search2').style.display = 'none';
		schFlag = false;
	}else{
		document.getElementById("schBtn").value="검색닫기";
		document.getElementById('Addr_search2').style.display = 'block';
		schFlag = true;
	}
}
</script>
			<!-- 우편번호 찾기 부분 -->
			<div id="Addr_search2" style="display:none; clear:both; margin-top:5px;">
				<div class="post_write">
					<select id="sch_field" name="sch_field" title="검색형태 선택" class="ta_select" style="width:80px;" >

					<option value="m_name" >이름</option>
					<option value="m_id" >아이디</option>
					<option value="lvlname">그룹명</option>
					</select>
					:

					<input type="text" id="sch_member" name="sch_member" class="ta_input" onkeypress="if(event.keyCode==13){return false;}" value="" />

					<input type="button" value="검색" class="btn_wh_zipcode" onclick="wdSearch_Addr()" />

					<span class="point fs11">예) 홍길동, 길동</span>
				</div>
				<div class="post_search_wrap">
					<strong>↓ 회원목록 </strong> - 회원명 검색 결과 목록입니다.

					<!-- 우편번호 나열 -->
					<div id="Search_Form" class="post_search"></div>

				</div>
			</div>



			<script type="text/javascript">
			function wdSearch_Addr(){
				if(!document.getElementById("frm").sch_member.value) {
					alert("검색어를 적어주세요");
					document.getElementById("frm").sch_member.focus();
				}
				else{
					document.getElementById("frm_member").sch_member.value = document.getElementById("frm").sch_member.value;
					document.getElementById("frm_member").sch_field.value = document.getElementById("frm").sch_field.value;
					document.getElementById("frm_member").action = "/ncms/board_config/member_search.do";
					document.getElementById("frm_member").target = "Search_Iframe";
					document.getElementById("Search_Form").innerHTML="<iframe name='Search_Iframe' width='100%' height='100%' frameborder='0'></iframe>";
					document.getElementById("frm_member").submit();
				}
			}
			</script>

		</td>
	</tr>
	<!-- <tr>
		<th scope="row">비밀번호 인증</th>
		<td class="left">
			<label for="a_password_Y"><input type="radio" id="a_password_Y" name="a_password" value="Y" title="사용 선택" <c:if test='${board.a_password == "Y"}'> checked</c:if> />사용</label>
			<label for="a_password_N"><input type="radio" id="a_password_N" name="a_password" value="N" title="사용안함 선택" <c:if test='${board.a_password == "N"}'> checked</c:if>/>사용안함</label>
			&nbsp;&nbsp;&nbsp;<strong>비밀번호</strong> :
			<input type="text" title="네번째 필드명 입력" id="a_password_str" name="a_password_str" class="ta_input" value="${board.a_password_str}" maxlength="30" />
			
			<span class="point fs11">* 30자 이하로 입력해주세요.</span>
		</td>
	</tr> -->
	</table>
	</fieldset>

</div>


<div id="div_2">
	<h2 class="tit h2_mt">필드관리<span>필드정보를 입력해주세요.</span></h2>
	<fieldset>
	<legend>필드정보 서식 작성</legend>
	<table class="bbs_common bbs_default" summary="신규 게시판 필드정보를 위한 입력 양식입니다.">
	<caption>필드정보 서식</caption>
	<colgroup>
		<col width="15%" />
		<col width="35%" />
		<col width="15%" />
		<col width="35%" />
	</colgroup>
	<tr>
		<th scope="row">관리자버튼</th>
		<td class="left">
			<label for="a_ad_cms_Y"><input type="radio" id="a_ad_cms_Y" name="a_ad_cms" value="Y" title="사용 선택" <c:if test='${board.a_ad_cms == "Y"}'> checked</c:if> />사용</label>
			<label for="a_ad_cms_N"><input type="radio" id="a_ad_cms_N" name="a_ad_cms" value="N" title="사용안함 선택" <c:if test='${board.a_ad_cms == "N"}'> checked</c:if> />사용안함</label>
			<span class="point fs11">* 게시판 목록에 자물쇠 모양으로 로그인할수있는 버튼이 생성됩니다.</span>
		</td>
		<th scope="row">분류</th>
		<td class="left">
			<label for="a_cate_Y"><input type="radio" id="a_cate_Y" name="a_cate" value="Y" title="사용 선택" <c:if test='${board.a_cate == "Y"}'> checked</c:if> />사용</label>
			<label for="a_cate_N"><input type="radio" id="a_cate_N" name="a_cate" value="N" title="사용안함 선택" <c:if test='${board.a_cate == "N"}'> checked</c:if> />사용안함</label>
		</td>
	</tr>
	<tr>
		<th scope="row">이메일</th>
		<td class="left">
			<label for="a_email_Y"><input type="radio" id="a_email_Y" name="a_email" value="Y" title="사용 선택" <c:if test='${board.a_email == "Y"}'> checked</c:if> />사용</label>
			<label for="a_email_N"><input type="radio" id="a_email_N" name="a_email" value="N" title="사용안함 선택" <c:if test='${board.a_email == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;<input type="checkbox" name="a_email_req" id="a_email_req" value="Y" <c:if test='${board.a_email_req == "Y"}'> checked</c:if> /><label for="a_email_req">필수</label>
		</td>
		<th scope="row">전화번호</th>
		<td class="left">
			<label for="a_phone_Y"><input type="radio" id="a_phone_Y" name="a_phone" value="Y" title="사용 선택" <c:if test='${board.a_phone == "Y"}'> checked</c:if> />사용</label>
			<label for="a_phone_N"><input type="radio" id="a_phone_N" name="a_phone" value="N" title="사용안함 선택" <c:if test='${board.a_phone == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;<input type="checkbox" name="a_phone_req" id="a_phone_req" value="Y" <c:if test='${board.a_phone_req == "Y"}'> checked</c:if> /><label for="a_phone_req">필수</label>
		</td>
	</tr>
	<tr>
		<th scope="row">주소</th>
		<td class="left">
			<label for="a_home_Y"><input type="radio" id="a_home_Y" name="a_home" value="Y" title="사용 선택" <c:if test='${board.a_home == "Y"}'> checked</c:if> />사용</label>
			<label for="a_home_N"><input type="radio" id="a_home_N" name="a_home" value="N" title="사용안함 선택" <c:if test='${board.a_home == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;<input type="checkbox" name="a_home_req" id="a_home_req" value="Y" <c:if test='${board.a_home_req == "Y"}'> checked</c:if> /><label for="a_home_req">필수</label>
		</td>
		<th scope="row">본문</th>
		<td class="left">
			<label for="a_content_Y"><input type="radio" id="a_content_Y" name="a_content" value="Y" title="사용 선택" <c:if test='${board.a_content == "Y"}'> checked</c:if> />사용</label>
			<label for="a_content_N"><input type="radio" id="a_content_N" name="a_content" value="N" title="사용안함 선택"  <c:if test='${board.a_content == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;<input type="checkbox" name="a_content_req" id="a_content_req" value="Y" <c:if test='${board.a_content_req == "Y"}'> checked</c:if> /><label for="a_content_req">필수</label>
		</td>
	</tr>

	</table>
	</fieldset>

</div>


<div id="div_3">

	<h2 class="tit h2_mt">기능관리<span>기능정보를 입력해주세요.</span></h2>
	<fieldset>
	<legend>기능정보 서식 작성</legend>
	<table class="bbs_common bbs_default" summary="신규 게시판 기능정보를 위한 입력 양식입니다.">
	<caption>기능정보 서식</caption>
	<colgroup>
	<col width="15%" />
	<col width="*" />
	</colgroup>
	<tr>
		<th scope="row"><label for="a_new">NEW 아이콘 출력</label></th>
		<td class="left"><input type="text" size="4" title="NEW 아이콘 출력기간 입력" id="a_new" name="a_new" class="ta_input eng" value="${board.a_new}" maxlength="2" /> 일</td>
	</tr>
	<tr>
		<th scope="row">파일업로드</th>
		<td class="left">
			<label for="a_upload_Y"><input type="radio" id="a_upload_Y" name="a_upload" value="Y" title="사용 선택" onclick="document.getElementById('nofile').style.display='block'" <c:if test='${board.a_upload == "Y"}'> checked</c:if> />사용</label>
			<label for="a_upload_N"><input type="radio" id="a_upload_N" name="a_upload" value="N" title="사용안함 선택" onclick="document.getElementById('nofile').style.display='none'" <c:if test='${board.a_upload == "N"}'> checked</c:if> />사용안함</label>

			<div id="nofile" style="display:block;" class="pt4">

				<table class="bbs_nor" summary="파일업로드 기능정보를 위한 입력 양식입니다.">
				<caption>파일업로드 기능정보 서식</caption>
				<colgroup>
				<col width="13%" />
				<col width="*" />
				</colgroup>
				<tr>
					<th scope="row"><label for="a_upload_len">업로드파일갯수</label></th>
					<td class="left">
						<select id="a_upload_len" name="a_upload_len" title="업로드파일갯수 선택" class="ta_select" style="width:50px;" >
<c:forEach begin="1" end="10" var="no">
						
						<option value="${no }" ${board.a_upload_len eq no ? 'selected="selected"' : '' }>${no }</option>

</c:forEach>
						</select>
						개
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="a_nofilesize">업로드제한용량</label></th>
					<td class="left"><input type="text" size="4" title="업로드제한용량 입력" id="a_nofilesize" name="a_nofilesize" class="ta_input_nor eng only_number" value="${board.a_nofilesize}" maxlength="3" /> <span class="eng">MByte</span>이하</td>
				</tr>
				<tr>
					<th scope="row"><label for="a_nofile">업로드허용파일</label></th>
					<td class="left"><textarea rows="1" id="a_nofile" name="a_nofile" title="업로드제한파일 입력" class="ta_textarea_nor eng w9" >${board.a_nofile}</textarea> <br /><span class="point fs11">* 사용 예) hwp,doc,ppt</span><br /><span class="point fs11">* 별도제작된 스킨(포토갤러리등)에는 적용되지 않을 수 있습니다.</span></td>
				</tr>

				</table>
			</div>
			<c:if test="${board.a_upload eq 'N'}">
			<script type='text/javascript'>
			document.getElementById('nofile').style.display='none';
			</script>
			</c:if>
		</td>
	</tr>
	<tr>
		<th scope="row">답변</th>
		<td class="left">
			<c:set var="disabled" value="${board.a_level eq 'nninc_simple' or board.a_level eq 'nninc_qna' ? '' : 'disabled'}" />
			
			<label for="a_reply_Y"><input type="radio" id="a_reply_Y" name="a_reply" value="Y" title="사용 선택" ${disabled} <c:if test='${board.a_reply == "Y"}'> checked</c:if> />사용</label>
			<label for="a_reply_N"><input type="radio" id="a_reply_N" name="a_reply" value="N" title="사용안함 선택" ${disabled} <c:if test='${board.a_reply == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;<span class="point fs11">* 답변 사용여부를 선택할수있습니다. <span style="font-weight:600">(기본게시판 및 묻고답하기 게시판만 사용가능)</span></span>
		</td>
	</tr>
	<tr>
		<th scope="row">한줄답글(댓글)</th>
		<td class="left">
			<label for="a_command_Y"><input type="radio" id="a_command_Y" name="a_command" value="Y" title="사용 선택" <c:if test='${board.a_command == "Y"}'> checked</c:if>  />사용</label>
			<label for="a_command_N"><input type="radio" id="a_command_N" name="a_command" value="N" title="사용안함 선택" <c:if test='${board.a_command == "N"}'> checked</c:if>  />사용안함</label>
			&nbsp;<span class="point fs11">* 댓글 사용여부를 선택할수있습니다.</span>
		</td>
	</tr>
	<tr>
		<th scope="row">비공개</th>
		<td class="left">
			<label for="a_type_T"><input type="radio" id="a_type_T" name="a_type" value="T" title="비공개 선택" <c:if test='${board.a_type == "T"}'> checked</c:if> />비공개</label>
			<label for="a_type_Y"><input type="radio" id="a_type_Y" name="a_type" value="Y" title="공개/비공개 선택" <c:if test='${board.a_type == "Y"}'> checked</c:if> />공개/비공개 선택</label>
			<label for="a_type_N"><input type="radio" id="a_type_N" name="a_type" value="N" title="사용안함 선택" <c:if test='${board.a_type == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;<span class="point fs11">* 비공개, 공개/비공개 선택 사용여부를 선택할수있습니다.</span>
		</td>
	</tr>
	<tr>
		<th scope="row">본인인증</th>
		<td class="left">
			<label for="a_jumin_Y"><input type="radio" id="a_jumin_Y" name="a_jumin" value="Y" title="사용 선택" <c:if test='${board.a_jumin == "Y"}'> checked</c:if>  />사용</label>
			<label for="a_jumin_N"><input type="radio" id="a_jumin_N" name="a_jumin" value="N" title="사용안함 선택" <c:if test='${board.a_jumin == "N"}'> checked</c:if>  />사용안함</label>
			&nbsp;<span class="point fs11">* 본인인증 사용여부를 선택할수있습니다. 단, 본인인증서비스를 사용중일때만 가능합니다.</span>
		</td>
	</tr>

	<tr>
		<th scope="row">에디터</th>
		<td class="left">
			<label for="a_edit_Y"><input type="radio" id="a_edit_Y" name="a_edit" value="Y" title="사용 선택" <c:if test='${board.a_edit == "Y"}'> checked</c:if> />사용</label>
			<label for="a_edit_N"><input type="radio" id="a_edit_N" name="a_edit" value="N" title="사용안함 선택" <c:if test='${board.a_edit == "N"}'> checked</c:if>  />사용안함</label>
			&nbsp;<span class="point fs11">* 에디터 사용여부를 선택할수있습니다.</span>
		</td>
	</tr>

	<tr>
		<th scope="row">첨부사진 바로보이기</th>
		<td class="left">
			<label for="a_photoview_Y"><input type="radio" id="a_photoview_Y" name="a_photoview" value="Y" title="사용 선택" <c:if test='${board.a_photoview == "Y"}'> checked</c:if> />사용</label>
			<label for="a_photoview_N"><input type="radio" id="a_photoview_N" name="a_photoview" value="N" title="사용안함 선택" <c:if test='${board.a_photoview == "N"}'> checked</c:if> />사용안함</label>
			<span class="point fs11">* 첨부된 사진을 글내용보기에서 바로 보이게 할지를 설정합니다. (포토게시판은 무관)</span>
		</td>
	</tr>



	<tr>
		<th scope="row"><label for="a_noword">금지단어</label></th>
		<td class="left"><input type="text" title="금지단어 입력" id="a_noword" name="a_noword" class="ta_input w5" value="${board.a_noword}" /><span class="point fs11">* 사용 예) 욕설, 비방, 광고,</span></td>
	</tr>
	<tr>
		<th scope="row"><label for="a_garbage_Y">휴지통</label></th>
		<td class="left"><label for="a_garbage_Y"><input type="radio" id="a_garbage_Y" name="a_garbage" value="Y" title="사용 선택" <c:if test='${board.a_garbage == "Y"}'> checked</c:if> />사용</label>
			<label for="a_garbage_N"><input type="radio" id="a_garbage_N" name="a_garbage" value="N" title="사용안함 선택" <c:if test='${board.a_garbage == "N"}'> checked</c:if> />사용안함</label>
			<span class="point fs11">* 삭제한 게시물을 휴지통에 임시보관하는 기능입니다.</span>
		</td>
	</tr>
	<!-- <tr>
		<th scope="row"><label for="a_show_Y">게시기간 설정</label></th>
		<td class="left"><label for="a_show_Y"><input type="radio" id="a_show_Y" name="a_show" value="Y" title="사용 선택" <c:if test='${board.a_show == "Y"}'> checked</c:if> />사용</label>
			<label for="a_show_N"><input type="radio" id="a_show_N" name="a_show" value="N" title="사용안함 선택" <c:if test='${board.a_show == "N"}'> checked</c:if> />사용안함</label>
			&nbsp;&nbsp;
			기본기간 :
				<select id="a_show_term" name="a_show_term" class="ta_select">
					<option value="0" ${'0' == board.a_show_term ? 'selected="selected"' : '' }>설정안함</option>
					<option value="1" ${'1' == board.a_show_term ? 'selected="selected"' : '' }>1개월</option>
					<option value="3" ${'3' == board.a_show_term ? 'selected="selected"' : '' }>3개월</option>
					<option value="6" ${'6' == board.a_show_term ? 'selected="selected"' : '' }>6개월</option>
					<option value="9" ${'9' == board.a_show_term ? 'selected="selected"' : '' }>9개월</option>
					<option value="12" ${'12' == board.a_show_term ? 'selected="selected"' : '' }>1년</option>
					<option value="24" ${'24' == board.a_show_term ? 'selected="selected"' : '' }>2년</option>
					<option value="36" ${'36' == board.a_show_term ? 'selected="selected"' : '' }>3년</option>
					<option value="48" ${'48' == board.a_show_term ? 'selected="selected"' : '' }>4년</option>
					<option value="60" ${'60' == board.a_show_term ? 'selected="selected"' : '' }>5년</option>
				</select>
			<span class="point fs11">* 게시물 작성시 게시기간을 설정해 일정기간만 게시물이 보이게 하는 기능입니다. 
			<span style="font-weight:600">(게시기간이 지났을 경우 일반인들은 글이 안보이게 되며, 관리자나 본인이 작성한 글일경우에만 보여지게 됩니다.)</span></span>
		</td>
	</tr> -->

	<!-- tr>
		<th scope="row">등록일자 수정</th>
		<td class="left">
			<label for="a_date_change_Y"><input type="radio" id="a_date_change_Y" name="a_date_change" value="Y" title="사용 선택" <c:if test='${board.a_date_change == "Y"}'> checked</c:if> />사용</label>
			<label for="a_date_change_N"><input type="radio" id="a_date_change_N" name="a_date_change" value="N" title="사용안함 선택" <c:if test='${board.a_date_change == "N"}'> checked</c:if> />사용안함</label>
			 <span class="point fs11">* 관리권한이 있는경우만 수정가능합니다.</span>
		</td>
	</tr -->

	</table>
	</fieldset>

</div>



<div id="div_4">


	<h2 class="tit h2_mt">디자인관리<span>게시판 디자인정보를 입력해주세요.</span></h2>
	<fieldset>
	<legend>디자인정보 서식 작성</legend>
	<table class="bbs_common bbs_default" summary="신규 게시판 디자인정보를 위한 입력 양식입니다.">
	<caption>기능정보 서식</caption>
	<colgroup>
	<col width="15%" />
	<col width="*" />
	</colgroup>
	<tr>
		<th scope="row"><label for="a_width">게시판 폭(너비)</label></th>
		<td class="left"><input type="text" size="7" title="게시판 폭(너비) 입력" id="a_width" name="a_width" class="ta_input eng" value="${board.a_width}" maxlength="5" /> <span class="point fs11"> * 퍼센트(%)단위 설정은 꼭 <strong>%</strong>를 넣어주세요. 숫자만 입력하시면 픽셀(pixel)단위로 설정됩니다.</span></td>
	</tr>

	<tr>
		<th scope="row"><label for="a_displaysu">페이지당 게시물수</label></th>
		<td class="left">
			<input type="hidden" name="a_displaysu_org" value="${board.a_displaysu}" />
			<input type="text" size="4" title="페이지별 게시물출력수 입력" id="a_displaysu" name="a_displaysu" class="ta_input eng" value="${board.a_displaysu}" maxlength="2" /> 개
		</td>
	</tr>


	<tr>
		<th scope="row"><label for="a_writecontent">글쓰기기본내용</label></th>
		<td class="left">
			<textarea name="a_writecontent" id="a_writecontent" style="width:100%; height:200px;">${board.a_writecontent}</textarea>
			<script type="text/javascript">
			var oEditors = [];
			nhn.husky.EZCreator.createInIFrame({
				oAppRef: oEditors,
				elPlaceHolder: "a_writecontent",
				sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
				fCreator: "createSEditor2"
			});							
			</script>
		</td>
	</tr>

	</table>
	</fieldset>


</div>


	<div class="contoll_box">
		<span><input type="submit" value="등록" class="btn_bl_default" /></span>
		<c:choose>
			<c:when test="${param.page_type eq 'popup'}">
				<span><input type="button" value="취소" onclick="javascript:window.close();" class="btn_wh_default" /></span>
			</c:when>
			<c:otherwise>
				<span><input type="button" value="취소" onclick="location.href='${prepage}';" class="btn_wh_default" /></span>
			</c:otherwise>
		</c:choose>
	</div>





</form>

<form id= "frm_member" method='post' action="">
<div>
	<input type="hidden" name="sch_member" />
	<input type="hidden" name="sch_field" />
</div>
</form>

<script type="text/javascript">
	function click_ftemp(num, val){
		var obj = eval("document.getElementById(\"div_ftemp"+num+"\")");
		if (val){
			obj.style.display = "block";
		}else{
			obj.style.display = "none";
		}
	}
</script>