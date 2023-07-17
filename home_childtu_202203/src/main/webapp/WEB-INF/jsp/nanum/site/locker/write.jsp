<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>




<link rel="Stylesheet" type="text/css" href="/nanum/site/locker/css/common.css" />
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
<script type="text/javascript" src="/nanum/site/locker/js/common.js"></script>

<jsp:include page="./tophtml.jsp" />

<c:choose>
	<c:when test="${fn:length(libList) > 1}">
<%--도서관이 여러개 --%>
<div class="con_tab tab_b">
<c:set var="class_nm" value="no${fn:length(libList)}" />
<c:set var="class_nm" value="${fn:length(libList) > 7 ? 'no7' : class_nm}" />
<ul class="${class_nm}">
	<c:forEach items="${libList}" var="lib" varStatus="no">
	<li><a href="?sh_lc_libcode=${lib.ct_idx}" ${sh_lc_libcode eq lib.ct_idx ? 'class="on"' : '' }>${lib.ct_name}</a></option>
	</c:forEach>
</ul>
</div>
	
	</c:when>
	<c:otherwise>
<%--도서관이 한개--%>
	
	</c:otherwise>
</c:choose>

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
	if( CheckSpaces(eForm.lc_addr, "주소") ){ return false; }
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


function lockerCancel(lc_num){
	if(confirm("사용중인 사물함을 반납하시겠습니까?")){
		location.href="/${BUILDER_DIR}/locker/deleteOk.do?lc_num="+lc_num+"&prepage=${nowPageEncode}";
	}else{
		return;
	}
}

//]]>
</script>

<div id="board" style="width:100%;">
	<c:set var="action_url" value="/locker/writeOk.do" />
	<c:if test="${!empty BUILDER_DIR }">
		<c:set var="action_url" value="/${BUILDER_DIR }/locker/writeOk.do" />
	</c:if>
	<form id="frm" method="post" action="${action_url}" onsubmit="return w_chk(this);">
	<input type="hidden" name="edu_idx" value="${edu_idx }">
	
	<input type="hidden" name="prepage" value="${nowPage}">
	<input type="hidden" name="lc_libcode" value="${season.ls_libcode}">
	<input type="hidden" name="lc_year" value="${season.ls_year }">
	<input type="hidden" name="lc_season" value="${season.ls_season }">
	<input type="hidden" name="lc_m_num" value="${sessionScope.ss_user_num }">
	<input type="hidden" name="lc_m_name" value="${sessionScope.ss_user_name }">
	
 
 
<c:if test="${fn:length(locker_user) == 0 }">
	<div class="guide">
		<span><img src="/nanum/site/edusat/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span>
	</div>
	<!-- 쓰기 테이블 -->
	<div class="table_bwrite">
			<table cellspacing="0" summary="의 이름, 비밀번호, 내용을 입력">
			<caption>사물함신청 신청</caption>
				<colgroup>
					<col />
					<col width="80%" />
				</colgroup>
			<tbody>
				<tr>
					<th scope="row">도서관</th>
					<td>${sh_lc_lib }</td>
				</tr>
				<tr>
					<th scope="row">기간</th>
					<td>
						${season.ls_year } 년
	<c:choose>
		<c:when test="${season.ls_season eq '1' }">
							1월 ~ 6월
		</c:when>
		<c:when test="${season.ls_season eq '7' }">
							7월 ~ 12월
		</c:when>
	</c:choose>
					</td>
				</tr>
				<tr>
					<th scope="row">회원번호</th>
					<td>
						${sessionScope.ss_user_num }
					</td>
				</tr>
				<tr>
					<th scope="row">이름</th>
					<td>
						${sessionScope.ss_user_name }
					</td>
				</tr>
				
				<tr>
					<th scope="row"><img src="/nanum/site/edusat/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 연락처</th>
					<td>
						<input type="text" size="20" id="lc_phone" name="lc_phone" class="board_input" value="${sessionScope.ss_user_mobile}" maxlength="20" />
					</td>
				</tr>
				
				<tr>
					<th scope="row"><img src="/nanum/site/edusat/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 생년월일</th>
					<td>
	<c:if test="${!empty sessionScope.ss_user_birthday }">
		<c:set var="birthday_arr" value="${fn:split(sessionScope.ss_user_birthday, '/') }" />
		<c:set var="lc_birth_y" value="${birthday_arr[0] }" />
		<c:set var="lc_birth_m" value="${birthday_arr[1] }" />
		<c:set var="lc_birth_d" value="${birthday_arr[2] }" />
	</c:if>
						<input type="text" size="5" id="lc_birth_y" name="lc_birth_y" class="board_input" value="${lc_birth_y}" maxlength="4" onblur="SetNum(this);" />년
						<input type="text" size="2" id="lc_birth_m" name="lc_birth_m" class="board_input" value="${lc_birth_m}" maxlength="2" onblur="SetNum(this);" />월
						<input type="text" size="2" id="lc_birth_d" name="lc_birth_d" class="board_input" value="${lc_birth_d}" maxlength="2" onblur="SetNum(this);" />일
					</td>
				</tr>
				
				<tr>
					<th scope="row"><img src="/nanum/site/edusat/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 사물함번호</th>
					<td>
						<input name="lc_locker_num" title="사물함번호 입력" class="board_input" id="lc_locker_num" onfocus="" onblur="SetNum(this);" type="text" size="5" maxlength="3" readonly="readonly">
						<input class="ct_bt01" onclick="popupLocker();" type="button" value="사물함보기">
					</td>
				</tr>
			</tbody>
			</table>
			
	</div>
	
	<!-- 버튼 -->
	<div class="btn_w">
		<input type="submit" value="신청" class="con_btn blue" />
		<a href="${prepage }" class="con_btn gray" >취소</a>
	</div>
	<!-- //버튼 -->
	</form>




</c:if>
<c:if test="${fn:length(locker_user) > 0 }">

	<div class="guide">
		
	</div>
	<!-- 쓰기 테이블 -->
	<div class="table_bwrite">
			<table cellspacing="0" summary="의 이름, 비밀번호, 내용을 입력">
			<caption>사물함신청 신청정보</caption>
				<colgroup>
					<col />
					<col width="80%" />
				</colgroup>
			<tbody>
				<tr>
					<th scope="row">도서관</th>
					<td>${locker_user.lc_lib }</td>
				</tr>
				<tr>
					<th scope="row">기간</th>
					<td>
						${locker_user.lc_year  } 년
	<c:choose>
		<c:when test="${locker_user.lc_season eq '1' }">
							1월 ~ 6월
		</c:when>
		<c:when test="${locker_user.lc_season eq '7' }">
							7월 ~ 12월
		</c:when>
	</c:choose>
					</td>
				</tr>
				<tr>
					<th scope="row">회원번호</th>
					<td>
						${locker_user.lc_m_num  }
					</td>
				</tr>
				<tr>
					<th scope="row">이름</th>
					<td>
						${locker_user.lc_m_name }
					</td>
				</tr>
				
				<tr>
					<th scope="row">연락처</th>
					<td>
						${locker_user.lc_phone }
					</td>
				</tr>
				
				<tr>
					<th scope="row">생년월일</th>
					<td>
	<c:if test="${!empty locker_user.lc_birth }">
		<c:set var="birthday_arr" value="${fn:split(locker_user.lc_birth, '-') }" />
		<c:set var="lc_birth_y" value="${birthday_arr[0] }" />
		<c:set var="lc_birth_m" value="${birthday_arr[1] }" />
		<c:set var="lc_birth_d" value="${birthday_arr[2] }" />
	</c:if>
						${lc_birth_y}년
						${lc_birth_m}월
						${lc_birth_d}일
					</td>
				</tr>
				
				<tr>
					<th scope="row">사물함번호</th>
					<td>
						${locker_user.lc_locker_num }
					</td>
				</tr>
			</tbody>
			</table>
	</div>
	
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate value="${now}" pattern="yyyyMMdd" var="nowDate" />
	<c:set var="lc_regdate_str" value="${fn:replace(fn:substring(locker_user.lc_regdate,0,10),'-', '') }" />
	<fmt:parseDate value="${lc_regdate_str }" pattern="yyyyMMdd" var="regDate" />
	
	
	<!-- 버튼 -->
	<div class="btn_w">
		<a href="#cancel" onclick="lockerCancel(${locker_user.lc_num});" class="con_btn gray" >신청취소</a>
	</div>
	<!-- //버튼 -->
	

</c:if>



</div>


<jsp:include page="./bthtml.jsp" />