<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? 'ncms/ebook/list.do' : param.prepage }" />

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_ebook.js"></script>
<c:set var="prepage" value="${empty param.prepage ? '/ncms/ebook/list.do' : param.prepage }" />
<h1 class="tit"><span>신규 이북 ${empty ebook.eb_idx ? "생성" : "수정"}</span> </h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

	<form id="frm" method="post" action="/ncms/ebook/writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk();">
	<div>
	<input type="hidden" name="eb_idx" value="${ebook.eb_idx}" />
	<input type="hidden" name="eb_logoimg_org" value="${ebook.eb_logoimg}" />
	<input type="hidden" name="eb_chk" value="${ebook.eb_chk}" />
	<input type="hidden" name="eb_pk" value="${ebook.eb_pk}" />
	<input type="hidden" name="prepage" value="${prepage}" />
	</div>

<fieldset>
	<legend>이북생성 서식 작성</legend>
	<table class="bbs_common bbs_default" summary="신규 이북 생성을 위한 입력 양식입니다.">
	<caption>이북생성 서식</caption>
	<colgroup>
	<col width="15%" />
	<col width="*" />
	</colgroup>
	<tr>
		<th scope="row"><label for="eb_subject">제목</label></th>
		<td class="left"><input type="text" title="제목 입력" id="eb_subject" name="eb_subject" class="ta_input w9" value="${ebook.eb_subject}" maxlength="50" /></td>
	</tr>

	<tr>
		<th scope="row">원본 이미지사이즈</th>
		<td class="left">
			<label for="eb_width">가로</label> : <input type="text" size="5" id="eb_width" name="eb_width" class="ta_input eng" value="${ebook.eb_width}" maxlength="4" /> px&nbsp;&nbsp;&nbsp;
			<label for="eb_height">세로</label> : <input type="text" size="5" id="eb_height" name="eb_height" class="ta_input eng" value="${ebook.eb_height}" maxlength="4" /> px&nbsp;&nbsp;&nbsp;
			<span class="point fs11">* 이미지 한장 사이즈입니다.</span>
		</td>
	</tr>

	<tr>
		<th scope="row">축소 이미지사이즈</th>
		<td class="left">
			<label for="eb_width2">가로</label> : <input type="text" size="5" id="eb_width2" name="eb_width2" class="ta_input eng" value="${ebook.eb_width2}" maxlength="4" /> px&nbsp;&nbsp;&nbsp;
			<label for="eb_height2">세로</label> : <input type="text" size="5" id="eb_height2" name="eb_height2" class="ta_input eng" value="${ebook.eb_height2}" maxlength="4" /> px&nbsp;&nbsp;&nbsp;
			<span class="point fs11">* 이미지 한장 사이즈입니다.</span>
		</td>
	</tr>


	<tr>
		<th scope="row">스킨설정</th>
		<td class="left" style="padding-top:14px;padding-bottom:14px;">
			<label for="eb_skin_1"><input type="radio" id="eb_skin_1" name="eb_skin" value="1" title="1번 스킨 선택" ${ebook.eb_skin == "1" ? "checked" : ""}/><img src="/nanum/ncms/img/ebook_skin1.gif" width="158" height="125" class="img_center" alt="1번 스킨 선택" /></label>&nbsp;
			<label for="eb_skin_2"><input type="radio" id="eb_skin_2" name="eb_skin" value="2" title="2번 스킨 선택" ${ebook.eb_skin == "2" ? "checked" : ""}/><img src="/nanum/ncms/img/ebook_skin2.gif" width="158" height="125" class="img_center" alt="2번 스킨 선택" /></label>&nbsp;
			<label for="eb_skin_3"><input type="radio" id="eb_skin_3" name="eb_skin" value="3" title="3번 스킨 선택" ${ebook.eb_skin == "3" ? "checked" : ""}/><img src="/nanum/ncms/img/ebook_skin3.gif" width="158" height="125" class="img_center" alt="3번 스킨 선택" /></label>&nbsp;
			<br /><br />
			<label for="eb_skin_4"><input type="radio" id="eb_skin_4" name="eb_skin" value="4" title="4번 스킨 선택" ${ebook.eb_skin == "4" ? "checked" : ""}/><img src="/nanum/ncms/img/ebook_skin4.gif" width="158" height="125" class="img_center" alt="4번 스킨 선택" /></label>&nbsp;
			<label for="eb_skin_5"><input type="radio" id="eb_skin_5" name="eb_skin" value="5" title="5번 스킨 선택" ${ebook.eb_skin == "5" ? "checked" : ""}/><img src="/nanum/ncms/img/ebook_skin5.gif" width="158" height="125" class="img_center" alt="5번 스킨 선택" /></label>&nbsp;
			<label for="eb_skin_6"><input type="radio" id="eb_skin_6" name="eb_skin" value="6" title="6번 스킨 선택" ${ebook.eb_skin == "6" ? "checked" : ""}/><img src="/nanum/ncms/img/ebook_skin6.gif" width="158" height="125" class="img_center" alt="6번 스킨 선택" /></label>&nbsp;
		</td>
	</tr>
	<tr>
		<th scope="row">페이지 형태</th>
		<td class="left">
			<label for="eb_viewtype_1"><input type="radio" id="eb_viewtype_1" name="eb_viewtype" value="1" title="일반형 선택" ${ebook.eb_viewtype == "1" ? "checked" : ""} /> 일반형</label>&nbsp;
			<label for="eb_viewtype_2"><input type="radio" id="eb_viewtype_2" name="eb_viewtype" value="0" title="PT형선택" ${ebook.eb_viewtype == "0" ? "checked" : ""} /> PT형</label>
		</td>
	</tr>
	
	<tr>
		<th scope="row"><label for="eb_logoimg_file">로고 이미지 파일</label></th>
		<td class="left">
			<input type="file" size="42" id="eb_logoimg_file" name="eb_logoimg_file" title="로고 이미지 찾아보기" class="ta_input_file" value="" /> <span class="point fs11">* 권장 사이즈 : 150 픽셀 x 48 픽셀 이하</span>

			<c:if test="${!empty ebook.eb_logoimg}"><br/>
				<br/><img src = "/data/ebook/logo/${ebook.eb_logoimg}" alt="${ebook.eb_logoimg}" /></a>
				&nbsp;<input type='checkbox' name='eb_logoimg_del' value='${ebook.eb_logoimg}' />삭제
			</c:if>
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