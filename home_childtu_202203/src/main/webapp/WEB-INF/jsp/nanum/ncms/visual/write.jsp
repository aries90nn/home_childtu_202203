<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/ncms/visual/list.do' : param.prepage }" />
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_visual.js"></script>

<h1 class="tit"><span>${visual.v_num == null or visual.v_num eq '' ? "신규 메인비쥬얼 생성" : "메인비쥬얼 수정"}</span></h1>

<script>
var textCountLimit = 50;

$(document).ready(function() {
	$('textarea[name=v_memo]').keyup(function() {
		
		var rows = $('#v_memo').val().split('\n').length;
		var maxRows = 2;
		if( rows > maxRows){
			alert('2줄 까지만 가능합니다');
			modifiedText = $('#v_memo').val().split("\n").slice(0, maxRows);
			$('#v_memo').val(modifiedText.join("\n"));
		}
		// 텍스트영역의 길이를 체크
		var textLength = $(this).val().length;
			// 입력된 텍스트 길이를 #textCount 에 업데이트 해줌
			 
			// 제한된 길이보다 입력된 길이가 큰 경우 제한 길이만큼만 자르고 텍스트영역에 넣음
			if (textLength > textCountLimit) {
			    $(this).val($(this).val().substr(0, textCountLimit));
			}
	});
});
</script>
<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<h2 class="tit">메인비쥬얼 기본정보 <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>
	
	<form id="frm" method="post" action="writeOk.do?${pageInfo}" enctype="multipart/form-data" onsubmit="return w_chk();">
	<div>
	<input type="hidden" name="v_num" value="${visual.v_num}" /><!-- (수정일때사용) -->
	<input type="hidden" name="v_img2" value="${visual.v_img}" />
	<input type="hidden" name="prepage" value="${prepage}" />
	</div>
		<fieldset>
			<legend>메인비쥬얼생성 서식 작성</legend>
			<table class="bbs_common bbs_default" summary="신규 메인비쥬얼 생성을 위한 입력 양식입니다.">
			<caption>메인비쥬얼생성 서식</caption>
			<colgroup>
			<col width="15%" />
			<col width="*" />
			</colgroup>
			<tr>
				<th scope="row"><label for="v_subject"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 제목</label></th>
				<td class="left">
					<input type="text" title="제목 입력" id="v_subject" name="v_subject" class="ta_input w7" value="${visual.v_subject}" maxlength="100" />
					<span class="point fs11">* 제목은 100자 이하로 입력해주세요.</span>
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="v_sdate_y">사용기간</label></th>
				<td class="left">
				<jsp:useBean id="date" class="java.util.Date" />
					<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
					
					<select id="v_sdate_y" name="v_sdate_y" title="시작 사용기간(년)을 선택" class="ta_select" style="width:75px;" >
						<c:forEach var="i" begin="2017" end="${currentYear+5}">
						<option value="${i}" ${v_sdate_y == i ? 'selected="selected"' : '' }>${i}년</option>
						</c:forEach>
					</select>
					
					<select id="v_sdate_m" name="v_sdate_m" title="시작 사용기간(월)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="1" end="12">
						<option value="${i}" ${v_sdate_m == i ? 'selected="selected"' : '' }>${i}월</option>
						</c:forEach>
					</select>
					
					<select id="v_sdate_d" name="v_sdate_d" title="시작 사용기간(일)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="1" end="31">
						<option value="${i}" ${v_sdate_d == i ? 'selected="selected"' : '' }>${i}일</option>
						</c:forEach>
					</select> 
					
					<select id="v_sdate_h" name="v_sdate_h" title="시작 사용기간(시간)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="0" end="23">
						<option value="${i}" ${v_sdate_h == i ? 'selected="selected"' : '' }>${i}시</option>
						</c:forEach>
					</select>
					
					<select id="v_sdate_n" name="v_sdate_n" title="시작 사용기간(분)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="0" end="59">
						<option value="${i}" ${v_sdate_n == i ? 'selected="selected"' : '' }>${i}분</option>
						</c:forEach>
					</select>
					<br/>								
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~
					<select id="v_edate_y" name="v_edate_y" title="종료 사용기간(년)을 선택" class="ta_select limit" style="width:75px;" >
						<c:forEach var="i" begin="2017" end="${currentYear+5}">
						<option value="${i}" ${v_edate_y == i ? 'selected="selected"' : '' }>${i}년</option>
						</c:forEach>
					</select>
					<select id="v_edate_m" name="v_edate_m" title="종료 사용기간(월)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="1" end="12">
						<option value="${i}" ${v_edate_m == i ? 'selected="selected"' : '' }>${i}월</option>
						</c:forEach>
					</select>
					<select id="v_edate_d" name="v_edate_d" title="종료 사용기간(일)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="1" end="31">
						<option value="${i}" ${v_edate_d == i ? 'selected="selected"' : '' }>${i}일</option>
						</c:forEach>
					</select>
					
					<select id="v_edate_h" name="v_edate_h" title="시작 사용기간(시간)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="0" end="23">
						<option value="${i}" ${v_edate_h == i ? 'selected="selected"' : '' }>${i}시</option>
						</c:forEach>
					</select>
					
					<select id="v_edate_n" name="v_edate_n" title="시작 사용기간(분)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="0" end="59">
						<option value="${i}" ${v_edate_n == i ? 'selected="selected"' : '' }>${i}분</option>
						</c:forEach>
					</select>&nbsp;&nbsp;
					&nbsp;&nbsp;
					<input type="checkbox" name="unlimited" id="unlimited" value="Y"  onclick="javascript: click_limit();" <c:if test='${visual.unlimited == "Y"}'> checked</c:if> /><label for="unlimited">무제한</label>
					
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="v_memo">비쥬얼문구</th>
				<td class="left"><textarea name="v_memo" id="v_memo" class="ta_textarea" style="width:600px;height:35px" >${visual.v_memo}</textarea>
				<span class="point fs11">* 50자 이내로 입력해주세요. 두줄이상으로 엔터키를 쓰실수 없습니다.</span>
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="v_img"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 비쥬얼 이미지 파일</label></th>
				<td class="left">
					<input type="file" size="70" id="v_img_file" name="v_img_file" title="메인비쥬얼 이미지 찾아보기" class="ta_input_file" value="" />
					
					<c:if test="${!empty visual.v_img}">
						<br/><a href="down.do?v_num=${visual.v_num}" ><img src = "/data/visual/${visual.v_img}" alt="${visual.v_subject}" class="vam pt5"  style="width:520px;height:196px" /></a>
						&nbsp;<input type='checkbox' name='v_img_del' value='${visual.v_img}' />삭제
					</c:if>
					<br/><br/>
					<span class="point fs11">* 10MB의 jpg,png,gif 이미지 파일만 허용 가능합니다. 이미지가 주어진 사이즈보다 작거나 크더라도 고정 사이즈로 보여지게됩니다.  <strong>[고정 사이즈 가로 : 1590px, 세로 : 600px]</strong></span>
				</td>
			</tr>
			<!-- tr>
				<th scope="row"><label for="v_url">연결주소</label></th>
				<td class="left"><input type="text" size="50" title="연결주소 입력" id="v_url" name="v_url" class="ta_input w9" value="${visual.v_url}" maxlength="300" /><br/><span class="point fs11">* 사용 예) http://www.nninc.co.kr</span></td>
			</tr -->
			<tr>
				<th scope="row"><label for="v_chk">사용여부</label></th>
				<td class="left">
					<select id="v_chk" name="v_chk" title="메인비쥬얼 사용여부 선택" class="ta_select" style="width:60px;" >
						<option value="Y"  ${visual.v_chk eq 'Y' ? 'selected="selected"' : '' }>사용</option>
						<option value="N" ${visual.v_chk eq 'N' or visual.v_chk eq '' ? 'selected="selected"' : '' }>중지</option>
					</select>
				</td>
			</tr>
			</table>
		</fieldset>

		<div class="contoll_box">
			<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="취소" onclick="location.href='${prepage}';" class="btn_wh_default" /></span>
		</div>

	</form>

</div>
<!-- 내용들어가는곳 -->

<script type="text/javascript">
$(document).ready(function(){ 
	click_limit();
});
</script>