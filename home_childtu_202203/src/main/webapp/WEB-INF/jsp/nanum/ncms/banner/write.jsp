<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/ncms/banner/list.do' : param.prepage }" />
<c:if test="${empty param.prepage and !empty BUILDER_DIR }">
	<c:set var="prepage" value="/${BUILDER_DIR }${prepage }" />
</c:if>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_banner.js"></script>

<h1 class="tit"><span>${banner.b_l_num == null or banner.b_l_num eq '' ? "신규 배너 생성" : "배너 수정"}</span></h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<h2 class="tit">배너 기본정보 <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>
	
	<form id="frm" method="post" action="writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk();">
	<div>
	<input type="hidden" name="b_l_num" value="${banner.b_l_num}" /><!-- (수정일때사용) -->
	<input type="hidden" name="b_l_img2" value="${banner.b_l_img}" />
	<input type="hidden" name="prepage" value="${prepage}" />
	</div>
		<fieldset>
			<legend>배너생성 서식 작성</legend>
			<table class="bbs_common bbs_default" summary="신규 배너 생성을 위한 입력 양식입니다.">
			<caption>배너생성 서식</caption>
			<colgroup>
			<col width="15%" />
			<col width="*" />
			</colgroup>
			<tr>
				<th scope="row"><label for="b_l_subject"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 제목</label></th>
				<td class="left">
					<input type="text" title="제목 입력" id="b_l_subject" name="b_l_subject" class="ta_input w5" value="${banner.b_l_subject}" maxlength="50" />
					<span class="point fs11">* 50자 이하로 입력해주세요.</span>
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="sc_hdate1_y">사용기간</label></th>
				<td class="left">
				<jsp:useBean id="date" class="java.util.Date" />
					<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
					
					<select id="b_l_sdate_y" name="b_l_sdate_y" title="시작 사용기간(년)을 선택" class="ta_select" style="width:75px;" >
						<c:forEach var="i" begin="2017" end="${currentYear+5}">
						<option value="${i}" ${b_l_sdate_y == i ? 'selected="selected"' : '' }>${i}년</option>
						</c:forEach>
					</select>
					
					<select id="b_l_sdate_m" name="b_l_sdate_m" title="시작 사용기간(월)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="1" end="12">
						<option value="${i}" ${b_l_sdate_m == i ? 'selected="selected"' : '' }>${i}월</option>
						</c:forEach>
					</select>
					
					<select id="b_l_sdate_d" name="b_l_sdate_d" title="시작 사용기간(일)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="1" end="31">
						<option value="${i}" ${b_l_sdate_d == i ? 'selected="selected"' : '' }>${i}일</option>
						</c:forEach>
					</select> 
					
					<select id="b_l_sdate_h" name="b_l_sdate_h" title="시작 사용기간(시간)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="0" end="23">
						<option value="${i}" ${b_l_sdate_h == i ? 'selected="selected"' : '' }>${i}시</option>
						</c:forEach>
					</select>
					
					<select id="b_l_sdate_n" name="b_l_sdate_n" title="시작 사용기간(분)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="0" end="59">
						<option value="${i}" ${b_l_sdate_n == i ? 'selected="selected"' : '' }>${i}분</option>
						</c:forEach>
					</select>
					<br/>								
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~
					<select id="b_l_edate_y" name="b_l_edate_y" title="종료 사용기간(년)을 선택" class="ta_select limit" style="width:75px;" >
						<c:forEach var="i" begin="2017" end="${currentYear+5}">
						<option value="${i}" ${b_l_edate_y == i ? 'selected="selected"' : '' }>${i}년</option>
						</c:forEach>
					</select>
					<select id="b_l_edate_m" name="b_l_edate_m" title="종료 사용기간(월)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="1" end="12">
						<option value="${i}" ${b_l_edate_m == i ? 'selected="selected"' : '' }>${i}월</option>
						</c:forEach>
					</select>
					<select id="b_l_edate_d" name="b_l_edate_d" title="종료 사용기간(일)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="1" end="31">
						<option value="${i}" ${b_l_edate_d == i ? 'selected="selected"' : '' }>${i}일</option>
						</c:forEach>
					</select>
					
					<select id="b_l_edate_h" name="b_l_edate_h" title="시작 사용기간(시간)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="0" end="23">
						<option value="${i}" ${b_l_edate_h == i ? 'selected="selected"' : '' }>${i}시</option>
						</c:forEach>
					</select>
					
					<select id="b_l_edate_n" name="b_l_edate_n" title="시작 사용기간(분)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="0" end="59">
						<option value="${i}" ${b_l_edate_n == i ? 'selected="selected"' : '' }>${i}분</option>
						</c:forEach>
					</select>&nbsp;&nbsp;
					&nbsp;&nbsp;
					<input type="checkbox" name="unlimited" id="unlimited" value="Y"  onclick="javascript: click_limit();" <c:if test='${banner.unlimited == "Y"}'> checked</c:if> /><label for="unlimited">무제한</label>
					
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="b_l_img_file"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 배너 이미지 파일</label></th>
				<td class="left">
					<input type="file" size="70" id="b_l_img_file" name="b_l_img_file" title="배너 이미지 찾아보기" class="ta_input_file" value="" />
					
					<c:if test="${!empty banner.b_l_img}">
						<br/><img src = "/data/banner/${banner.b_l_img}" alt="${banner.b_l_subject}" class="vam pt5" />
						&nbsp;<input type='checkbox' name='b_l_img_del' value='${banner.b_l_img}' />삭제
					</c:if>
					
					<span class="point fs11">* 5MB의 jpg,png,gif 이미지 파일만 허용 가능합니다. (가로 : 200px, 세로 : 60px 를 추천합니다.)</span>
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="b_l_url"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 연결주소</label></th>
				<td class="left"><input type="text" size="50" title="연결주소 입력" id="b_l_url" name="b_l_url" class="ta_input w9" value="${banner.b_l_url}" maxlength="300" />
				<br/><span class="point fs11">* 300자 이하로 입력해주세요. (예시: http://www.nninc.co.kr)</span></td>
			</tr>
			<tr>
				<th scope="row"><label for="b_l_chk">사용여부</label></th>
				<td class="left">
					<select id="b_l_chk" name="b_l_chk" title="배너 사용여부 선택" class="ta_select" style="width:60px;" >
						<option value="Y"  ${banner.b_l_chk eq 'Y' ? 'selected="selected"' : '' }>사용</option>
						<option value="N" ${banner.b_l_chk eq 'N' or banner.b_l_chk eq '' ? 'selected="selected"' : '' }>중지</option>
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