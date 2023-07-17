<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_ebookpdf.js"></script>
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>


<c:set var="prepage" value="${empty param.prepage ? 'list.do' : param.prepage }" />
<h1 class="tit"><span>소식지 ${empty ebook.eb_idx ? "생성" : "수정"}</span> </h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

	<form id="frm" method="post" action="writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk();">
	<div>
		<input type="hidden" name="eb_idx" value="${ebook.eb_idx}" />
		<input type="hidden" name="prepage" value="${prepage}" />
	</div>

	<fieldset>
		<legend>소식지생성 서식 작성</legend>
		<table class="bbs_common bbs_default" summary="신규 소식지 생성을 위한 입력 양식입니다.">
		<caption>소식지생성 서식</caption>
		<colgroup>
		<col width="15%" />
		<col width="*" />
		</colgroup>
		<tr>
			<th scope="row"><label for="eb_subject"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 제목</label></th>
			<td class="left"><input type="text" title="제목 입력" id="eb_subject" name="eb_subject" class="ta_input w9" value="${ebook.eb_subject}" maxlength="100" /></td>
		</tr>
		
		<tr>
			<th scope="row"><label for="eb_pdf_file"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 소식지 pdf 파일</label></th>
			<td class="left">
				<input type="file" size="70" id="eb_pdf_file" name="eb_pdf_file" class="ta_input_file" value="" />
				<span class="point fs11">* pdf파일만 등록가능합니다.</span>
				
				<c:if test="${!empty ebook.eb_pdf}"><br/>
				<input type="hidden" id="eb_pdf_org" name="eb_pdf_org" value="${ebook.eb_pdf}" />
					<br/>${ebook.eb_pdf}
					&nbsp;<input type='checkbox' name='eb_pdf_del' id='eb_pdf_del' value='Y' /><label for="eb_pdf_del">삭제</label>
				</c:if>
			</td>
		</tr>
		
		<tr>
			<th scope="row"><label for="eb_img_file">메인 이미지</label></th>
			<td class="left">
				<input type="file" size="70" id="eb_img_file" name="eb_img_file" class="ta_input_file" value="" />
				<span class="point fs11">* 등록가능한 파일(jpg, gif, png)</span>
				<c:if test="${!empty ebook.eb_img}"><br/>
				<input type="hidden" id="eb_img_org" name="eb_img_org" value="${ebook.eb_img}" />
					<br/>${ebook.eb_img}
					&nbsp;<input type='checkbox' name='eb_img_del' id='eb_img_del' value='Y' /><label for="eb_eb_img_del">삭제</label>
				</c:if>
			</td>
		</tr>
		
		
		<tr>
		<th scope="row"><label for="eb_chk"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 사용여부</label></th>
		<td class="left">
			<select id="eb_chk" name="eb_chk" title="사용여부 선택" class="ta_select" style="width:100px;">
				<option value="Y" ${ebook.eb_chk eq 'Y' ? 'selected="selected"' : '' }>사용</option>
				<option value="N" ${ebook.eb_chk eq 'N' ? 'selected="selected"' : '' }>중지</option>
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