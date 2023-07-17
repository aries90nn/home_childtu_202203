<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/ncms/edusat_season/list.do' : param.prepage }" />
<c:if test="${empty param.prepage and !empty BUILDER_DIR }">
	<c:set var="prepage" value="/${BUILDER_DIR }${prepage }" />
</c:if>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_edusat_season.js"></script>

<h1 class="tit"><span>${season.es_num == null or season.es_num eq '' ? "신규 기수 생성" : "기수 수정"}</span></h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<h2 class="tit">기수 기본정보 <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>
	
	<form id="frm" method="post" action="writeOk.do"  onsubmit="return w_chk();">
	<div>
	<input type="hidden" name="es_num" value="${season.es_num}" /><!-- (수정일때사용) -->
	<input type="hidden" name="prepage" value="${prepage}" />
	</div>
		<fieldset>
			<legend>기수생성 서식 작성</legend>
			<table class="bbs_common bbs_default" summary="신규 기수 생성을 위한 입력 양식입니다.">
			<caption>기수생성 서식</caption>
			<colgroup>
			<col width="15%" />
			<col width="*" />
			</colgroup>
			
			<tr>
				<th scope="row"><label for="es_libcode"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 도서관선택</label></th>
				<td class="left">
					<select id="es_libcode" name="es_libcode" title="도서관 선택" class="t_search" style="width:150px;">
<c:forEach items="${libList }" var="lib" varStatus="no">
							<option value="${lib.ct_idx }" ${season.es_libcode eq lib.ct_idx ? 'selected="selected"' : '' }>${lib.ct_name }</option>
</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<th scope="row"><label for="es_name"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 기수명</label></th>
				<td class="left">
					<input type="text" title="기수명 입력" id="es_name" name="es_name" class="ta_input" size="60" value="${season.es_name}" maxlength="50" />
					<span class="point fs11">* 50자 이하로 입력해주세요.</span>
				</td>
			</tr>

			<tr>
				<th scope="row"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> <label for="es_chk">사용여부</label></th>
				<td class="left">
					<select id="es_chk" name="es_chk" title="기수 사용여부 선택" class="ta_select" style="width:60px;" >
						<option value="Y"  ${season.es_chk eq 'Y' ? 'selected="selected"' : '' }>사용</option>
						<option value="N" ${season.es_chk eq 'N' or season.es_chk eq '' ? 'selected="selected"' : '' }>중지</option>
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
