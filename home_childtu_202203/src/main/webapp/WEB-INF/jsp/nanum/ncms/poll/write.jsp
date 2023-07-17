<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<c:set var="prepage" value="${empty param.prepage ? './list.do' : param.prepage }" />
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_poll.js"></script>

<h1 class="tit"><span></span> ${po_idx == "" ? "신규 설문조사 생성" : "설문조사 수정"}</h1>

<!-- 내용들어가는곳 -->
	<div id="contents_area">

<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk();">
<div>
<input type="hidden" name="po_idx" value="${po_idx}" />
<input type="hidden" name="prepage" value="${prepage}" />
</div>

	<h2 class="tit">설문조사 기본정보 <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>
	
	<fieldset>
		<legend>설문조사생성 서식 작성</legend>
		<table class="bbs_common bbs_default" summary="신규 설문조사 생성을 위한 입력 양식입니다.">
		<caption>설문조사생성 서식</caption>
	<colgroup>
	<col width="15%" />
	<col width="*" />
	</colgroup>
	<tr>
		<th scope="row"><label for="po_subject"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 제목</label></th>
		<td class="left"><input type="text" title="제목 입력" id="po_subject" name="po_subject" class="ta_input w5" value="${poll.po_subject}" maxlength="50" />
		<span class="point fs11">* 제목은 50자 이하로 입력해주세요.</span>
		</td>
	</tr>
<tr>
	<th scope="row">설문 대상자 선택</th>
	<td class="left">
		<fieldset>
			<div class="ta_dl">
				<dl>
					<dt>회원그룹</dt>
						<c:forEach items="${membergroupList}" var="membergroup" varStatus="no">
						<c:set var="group_str" value="[${membergroup.g_num}]" />
						<dd><input type="checkbox" name="po_group" id="po_group${membergroup.g_num}" value="${membergroup.g_num}" <c:if test = "${fn:contains(poll.po_group, group_str)}">checked</c:if> /><label for="po_group${membergroup.g_num}">${membergroup.g_menuname}</label></dd>
						</c:forEach>
				</dl>
				
				<span class="point fs11">* 투표를 할 수있는 그룹에 권한을 체크해주세요.</span>
			</div>
			

		</fieldset>
	</td>
</tr>
<tr>
	<th scope="row">설문 결과 확인 대상자 선택</th>
	<td class="left">
		<fieldset>
			<div class="ta_dl">
				<dl>
					<dt>회원그룹</dt>
						<c:forEach items="${membergroupList}" var="membergroup" varStatus="no">
						<c:set var="group_str" value="${membergroup.g_num}" />
						<dd><input type="checkbox" name="po_addid" id="po_addid${membergroup.g_num}" value="${membergroup.g_num}" <c:if test = "${fn:contains(poll.po_addid, group_str)}">checked</c:if> /><label for="po_addid${membergroup.g_num}">${membergroup.g_menuname}</label></dd>
						</c:forEach>
				</dl>
				
				<span class="point fs11">* 결과 확인을 할 수있는 그룹에 권한을 체크해주세요.</span>
			</div>
			

		</fieldset>
	</td>
</tr>
	<tr>
		<th scope="row"><label for="po_sdate_y">설문기간</label></th>
		<td class="left">
			<jsp:useBean id="date" class="java.util.Date" />
				<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
				
			<select id="po_sdate_y" name="po_sdate_y" title="시작 사용기간(년)을 선택" class="ta_select" style="width:85px;" >
				<c:forEach var="i" begin="2017" end="${currentYear+5}">
				<option value="${i}" ${poll.po_sdate_y == i ? 'selected="selected"' : '' }>${i}년</option>
				</c:forEach>
			</select>
			<select id="po_sdate_m" name="po_sdate_m" title="시작 사용기간(월)을 선택" class="ta_select" style="width:70px;" >
				<c:forEach var="i" begin="1" end="12">
				<option value="${i}" ${poll.po_sdate_m == i ? 'selected="selected"' : '' }>${i}월</option>
				</c:forEach>
			</select>
			<select id="po_sdate_d" name="po_sdate_d" title="시작 사용기간(일)을 선택" class="ta_select" style="width:70px;" >
				<c:forEach var="i" begin="1" end="31">
				<option value="${i}" ${poll.po_sdate_d == i ? 'selected="selected"' : '' }>${i}일</option>
				</c:forEach>
			</select>

			~

			<select id="po_edate_y" name="po_edate_y" title="시작 사용기간(년)을 선택" class="ta_select" style="width:85px;" >
				<c:forEach var="i" begin="2017" end="${currentYear+5}">
				<option value="${i}" ${poll.po_edate_y == i ? 'selected="selected"' : '' }>${i}년</option>
				</c:forEach>
			</select>
			<select id="po_edate_m" name="po_edate_m" title="시작 사용기간(월)을 선택" class="ta_select" style="width:70px;" >
				<c:forEach var="i" begin="1" end="12">
				<option value="${i}" ${poll.po_edate_m == i ? 'selected="selected"' : '' }>${i}월</option>
				</c:forEach>
			</select>
			<select id="po_edate_d" name="po_edate_d" title="시작 사용기간(일)을 선택" class="ta_select" style="width:70px;" >
				<c:forEach var="i" begin="1" end="31">
				<option value="${i}" ${poll.po_edate_d == i ? 'selected="selected"' : '' }>${i}일</option>
				</c:forEach>
			</select>
		</td>
	</tr>

	<tr>
		<th scope="row"><label for="po_chk">사용여부</label></th>
		<td class="left">
			<select id="po_chk" name="po_chk" title="설문 사용여부 선택" class="ta_select" style="width:70px;" >
				<option value="Y" ${poll.po_chk == "Y" ? 'selected="selected"' : '' }>사용</option>
				<option value="N" ${poll.po_chk == "N" ? 'selected="selected"' : '' }>중지</option>
			</select>

			<span class="point fs11">* 사용선택시 등록과 동시에 바로 적용되므로 주의 하세요. </span>
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

</div>
<!-- 내용들어가는곳 -->