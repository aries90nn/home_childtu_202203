<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>


<jsp:useBean id="now" class="java.util.Date" />
<c:set var="nowyear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>

<c:set var="prepage" value="${empty param.prepage ? '/ncms/locker/list.do' : param.prepage }" />
<c:if test="${empty param.prepage and !empty BUILDER_DIR }">
	<c:set var="prepage" value="/${BUILDER_DIR }${prepage }" />
</c:if>
<script type="text/javascript" src="/nanum/site/locker/js/common.js"></script>
<script type="text/javascript">
//<![CDATA[
function w_chk(){
	var eForm = document.getElementById("frm");
	try{
		if( CheckSpaces(eForm.lc_libcode, "도서관") ){ return false; }
	}catch(e){}
	if( CheckSpaces(eForm.lc_m_num, "회원번호") ){ return false; }
	if( CheckSpaces(eForm.lc_m_name, "이름") ){ return false; }
	if( CheckSpaces(eForm.lc_phone, "연락처") ){ return false; }
	if( CheckSpaces(eForm.lc_birth_y, "생년월일 연도") ){ return false; }
	if( CheckSpaces(eForm.lc_birth_m, "생년월일 월") ){ return false; }
	if( CheckSpaces(eForm.lc_birth_d, "생년월일 일") ){ return false; }
	//if( CheckSpaces(eForm.lc_addr, "주소") ){ return false; }
	try{
		if( CheckSpaces(eForm.lc_locker_num, "사물함번호") ){ return false; }
	}catch(e){}
}

function popupLocker(){
	var eForm = document.getElementById("frm");
	if( CheckSpaces(eForm.lc_libcode, "도서관") ){ return; }
	var url = "/${BUILDER_DIR}/locker/locker.do";
	url += "?lc_year="+eForm.lc_year.value;
	url += "&lc_season="+eForm.lc_season.value;
	url += "&lc_libcode="+eForm.lc_libcode.value;
	window.open(url, 'locker', 'width=1200,height=1000,scrollbars=yes');
}
//]]>
</script>


<h1 class="tit"><span>${locker.lc_num == null or locker.lc_num eq '' ? "신규 사물함신청 생성" : "사물함신청 수정"}</span></h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<h2 class="tit">사물함신청 기본정보 <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>
	
	<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk();">
	<div>
	<input type="hidden" name="lc_num" value="${locker.lc_num}" /><!-- (수정일때사용) -->
	<input type="hidden" name="prepage" value="${prepage}" />
	</div>
		<fieldset>
						<legend>신청자정보생성 서식 작성</legend>
						<table class="bbs_common bbs_default" cellspacing="0" summary="신규 신청자정보 생성을 위한 입력 양식입니다.">
						<caption>신청자정보생성 서식</caption>
						<colgroup>
						<col width="140">
						<col width="580">
						</colgroup>

						<tbody><tr>
							<th scope="row"><img width="7" height="10" alt="*" src="/nanum/ncms/img/ic_vcheck.gif"> <label for="lc_lib">도서관</label></th>
							<td class="left">
								<select name="lc_libcode" title="도서관 선택" class="ta_select" id="lc_libcode" style="height: 25px; font-size: 13px; position: absolute; opacity: 0;">
									<option value=""> -- 도서관선택 --</option>
<c:forEach items="${libList }" var="lib">
									<option value="${lib.ct_idx }" ${locker.lc_libcode eq lib.ct_idx ? 'selected="selected"' : '' }>${lib.ct_name }</option>
</c:forEach>
								</select>
							</td>
						</tr>

						<tr>
							<th scope="row"><img width="7" height="10" alt="*" src="/nanum/ncms/img/ic_vcheck.gif"> 기간</th>
							<td class="left">
								<select name="lc_year" id="lc_year" class="ta_select">
<c:forEach begin="2019" end="${nowyear+1 }" var="item">
									<option value="${item }" ${locker.lc_year eq item ? 'selected="selected"' : '' }>${item }년</option>
</c:forEach>
								</select>
								<select name="lc_season" id="lc_season" class="ta_select">
									<option value="1" ${locker.lc_season eq '1' ? 'selected="selected"' : '' }>1월 ~ 6월</option>
									<option value="7" ${locker.lc_season eq '7' ? 'selected="selected"' : '' }>7월 ~ 12월</option>
								</select>
							</td>
						</tr>

						<tr>
							<th scope="row"><img width="7" height="10" alt="*" src="/nanum/ncms/img/ic_vcheck.gif"> <label for="lc_m_num">회원번호</label></th>
							<td class="left"><input name="lc_m_num" title="회원번호 입력" class="input_box" id="lc_m_num" onfocus="" onblur="" type="text" size="40" maxlength="50" value="${locker.lc_m_num }">
							</td>
						</tr>

						<tr>
							<th scope="row"><img width="7" height="10" alt="*" src="/nanum/ncms/img/ic_vcheck.gif"> <label for="lc_m_name">이름</label></th>
							<td class="left"><input name="lc_m_name" title="이름 입력" class="input_box" id="lc_m_name" onfocus="" onblur="" type="text" size="20" maxlength="20" value="${locker.lc_m_name }">
							</td>
						</tr>

						<tr>
							<th scope="row"><img width="7" height="10" alt="*" src="/nanum/ncms/img/ic_vcheck.gif"> <label for="lc_phone">연락처</label></th>
							<td class="left"><input name="lc_phone" title="연락처 입력" class="input_box" id="lc_phone" onfocus="" onblur="" type="text" size="20" maxlength="20" value="${locker.lc_phone }">
							</td>
						</tr>

						<tr>
							<th scope="row"><img width="7" height="10" alt="*" src="/nanum/ncms/img/ic_vcheck.gif"> <label for="lc_birth_y">생년월일</label></th>
							<td class="left">
							<c:set var="lc_birth_arr" value="${fn:split(locker.lc_birth, '-') }" />
								<input name="lc_birth_y" title="생년월일 연도 입력" class="input_box" id="lc_birth_y" onfocus="" onblur="SetNum(this);" type="text" size="4" maxlength="4" value="${lc_birth_arr[0] }">
								<label for="lc_birth_y">년</label>
								<input name="lc_birth_m" title="생년월일 월 입력" class="input_box" id="lc_birth_m" onfocus="" onblur="SetNum(this);" type="text" size="2" maxlength="2" value="${lc_birth_arr[1] }">
								<label for="lc_birth_y">월</label>
								<input name="lc_birth_d" title="생년월일 일 입력" class="input_box" id="lc_birth_d" onfocus="" onblur="SetNum(this);" type="text" size="2" maxlength="2" value="${lc_birth_arr[2] }">
								<label for="lc_birth_y">일</label>
							</td>
						</tr>

						<!-- <tr>
							<th scope="row"><img width="7" height="10" alt="*" src="/nanum/ncms/img/ic_vcheck.gif"> <label for="lc_addr">주소</label></th>
							<td class="left"><input name="lc_addr" title="주소 입력" class="input_box" id="lc_addr" onfocus="" onblur="" type="text" size="60" maxlength="100" value="${locker.lc_addr }">
							</td>
						</tr> -->

						<tr>
							<th scope="row"><img width="7" height="10" alt="*" src="/nanum/ncms/img/ic_vcheck.gif"> <label for="lc_locker_num">사물함번호</label></th>
							<td class="left">
<c:choose>
	<c:when test="${empty locker.lc_num }">
								<input name="lc_locker_num" title="사물함번호 입력" class="input_box" id="lc_locker_num" onfocus="" onblur="SetNum(this);" type="text" size="5" maxlength="3" readonly="readonly">
								<input class="btn_wh_default" onclick="popupLocker();" type="button" value="사물함보기">
	</c:when>
	<c:otherwise>
								${locker.lc_locker_num }
	</c:otherwise>
</c:choose>
								
							</td>
						</tr>

						</tbody></table>
					</fieldset>

		<div class="contoll_box">
			<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="취소" onclick="location.href='${prepage}';" class="btn_wh_default" /></span>
		</div>

	</form>

</div>
<!-- 내용들어가는곳 -->
