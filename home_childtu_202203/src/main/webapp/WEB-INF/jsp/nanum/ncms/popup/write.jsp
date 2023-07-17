<%@page import="kr.co.nninc.ncms.common.Func"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<%
String nowy = Func.date("Y");
request.setAttribute("nowy", nowy);
%>

<c:set var="prepage" value="${empty param.prepage ? '/ncms/popup/list.do' : param.prepage }" />
<c:if test="${!empty BUILDER_DIR and empty param.prepage }">
	<c:set var="prepage" value="/${BUILDER_DIR }${prepage }" />
</c:if>


<c:if test="${empty param.idx }">
	<c:set target="${popdto }" property="pos" value="A" />
</c:if>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_popup.js"></script>

<h1 class="tit"><span>${popdto.idx == null or popdto.idx eq ''  ? "신규 팝업 생성" : "팝업 수정"}</span> </h1>
<script>
$(document).ready(function(){
	//숫자만 입력
	$('.only_number').keyup(function () { 
		this.value = this.value.replace(/[^0-9]/g,'');
	});
});
</script>
<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<h2 class="tit">팝업 기본정보 <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>
	
	<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk2();">
	<input type="hidden" name="idx" value="${popdto.idx}" />
	<input type="hidden" name="prepage" value="${prepage }" />
		<fieldset>
			<legend>팝업생성 서식 작성</legend>
			<table class="bbs_common bbs_default" summary="신규 팝업 생성을 위한 입력 양식입니다.">
			<caption>팝업생성 서식</caption>
			<colgroup>
			<col width="15%" />
			<col width="*" />
			</colgroup>
			<tr>
				<th scope="row"><label for="subject"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 제목</label></th>
				<td class="left">
					<input type="text" title="제목 입력" id="subject" name="subject" class="ta_input w5" value="${popdto.subject}" maxlength="50" />
					<span class="point fs11">* 50자 이하로 입력해주세요.</span>
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="sdate_y">사용기간</label></th>
				<td class="left">
					
					<select id="sdate_y" name="sdate_y" title="시작 사용기간(년)을 선택" class="ta_select" style="width:75px;" >
						<c:forEach var="i" begin="2015" end="${nowy+1}">
						<option value="${i}" ${sdate_y == i ? 'selected="selected"' : '' }>${i}년</option>
						</c:forEach>
					</select>
					<select id="sdate_m" name="sdate_m" title="시작 사용기간(월)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="1" end="12">
						<option value="${i}" ${sdate_m == i ? 'selected="selected"' : '' }>${i}월</option>
						</c:forEach>
					</select>
					<select id="sdate_d" name="sdate_d" title="시작 사용기간(일)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="1" end="31">
						<option value="${i}" ${sdate_d == i ? 'selected="selected"' : '' }>${i}일</option>
						</c:forEach>
					</select>&nbsp;&nbsp;
					
					<select id="sdate_h" name="sdate_h" title="시작 사용기간(시간)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="0" end="24">
						<option value="${i}" ${sdate_h == i ? 'selected="selected"' : '' }>${i}시</option>
						</c:forEach>
					</select>
					<select id="sdate_n" name="sdate_n" title="시작 사용기간(분)을 선택" class="ta_select" style="width:60px;" >
						<c:forEach var="i" begin="0" end="59">
						<option value="${i}" ${sdate_n == i ? 'selected="selected"' : '' }>${i}분</option>
						</c:forEach>
					</select>
					<br/>								
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~
					<select id="edate_y" name="edate_y" title="종료 사용기간(년)을 선택" class="ta_select limit" style="width:75px;" >
						<c:forEach var="i" begin="2015" end="${nowy+1}">
						<option value="${i}" ${edate_y == i ? 'selected="selected"' : '' }>${i}년</option>
						</c:forEach>
					</select>
					<select id="edate_m" name="edate_m" title="종료 사용기간(월)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="1" end="12">
						<option value="${i}" ${edate_m == i ? 'selected="selected"' : '' }>${i}월</option>
						</c:forEach>
					</select>
					<select id="edate_d" name="edate_d" title="종료 사용기간(일)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="1" end="31">
						<option value="${i}" ${edate_d == i ? 'selected="selected"' : '' }>${i}일</option>
						</c:forEach>
					</select>&nbsp;&nbsp;
					<select id="edate_h" name="edate_h" title="종료 사용기간(시간)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="0" end="24">
						<option value="${i}" ${edate_h == i ? 'selected="selected"' : '' }>${i}시</option>
						</c:forEach>
					</select>
					<select id="edate_n" name="edate_n" title="종료 사용기간(분)을 선택" class="ta_select limit" style="width:60px;" >
						<c:forEach var="i" begin="0" end="59">
						<option value="${i}" ${edate_n == i ? 'selected="selected"' : '' }>${i}분</option>
						</c:forEach>
					</select>
					&nbsp;&nbsp;
					<input type="checkbox" name="unlimited" id="unlimited" value="Y"  onclick="javascript: click_limit();"  <c:if test= "${popdto.unlimited == 'Y'}"> checked="checked"</c:if> /><label for="unlimited">무제한</label>

				</td>
			</tr>
			<tr>
				<th scope="row"><label for="w_width">팝업창 크기</label></th>
				<td class="left">
					가로 <input type="text" size="4" title="팝업창 가로사이즈 입력" id="w_width" name="w_width" class="ta_input eng only_number" maxlength="4" value="${popdto.w_width}" /> <span class="eng">pixel</span>&nbsp;&nbsp;
					세로 <input type="text" size="4" title="팝업창 세로사이즈 입력" id="w_height" name="w_height" class="ta_input eng only_number" maxlength="4" value="${popdto.w_height}" /> <span class="eng">pixel</span> 
					<span class="point fs11">* 픽셀(pixel) 단위만 사용하실 수 있습니다. </span>
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="w_top">위치지정</label></th>
				<td class="left">
					상단 <input type="text" size="4" title="팝업창 가로위치 입력" id="w_top" name="w_top" class="ta_input eng only_number" maxlength="4" value="${popdto.w_top}" /> <span class="eng">pixel</span>&nbsp;&nbsp;
					좌측 <input type="text" size="4" title="팝업창 세로위치 입력" id="w_left" name="w_left" class="ta_input eng only_number" maxlength="4" value="${popdto.w_left}" /> <span class="eng">pixel</span> 
					<span class="point fs11">* 픽셀(pixel) 단위만 사용하실 수 있습니다. </span>
				</td>
			</tr>
			<tr>
				<th scope="row">환경설정</th>
				<td class="left">
					<input type="checkbox" name="scrollbars" id="scrollbars" title="스크롤바사용" value="Yes" <c:if test= "${popdto.scrollbars == 'Yes'}"> checked="checked"</c:if>  /><label for="scrollbars">스크롤바사용</label>&nbsp;
					<!-- input type="checkbox" name="toolbar" id="toolbar" title="툴바사용" value="Yes" <c:if test= "${popdto.toolbar == 'Yes'}"> checked="checked"</c:if>  /><label for="toolbar">툴바사용</label>&nbsp;
					<input type="checkbox" name="menubar" id="menubar" title="메뉴바사용" value="Yes" <c:if test= "${popdto.menubar == 'Yes'}"> checked="checked"</c:if>   /><label for="menubar">메뉴바사용</label>&nbsp;
					<input type="checkbox" name="locations" id="locations" title="주소창사용" value="Yes"  <c:if test= "${popdto.locations == 'Yes'}"> checked="checked"</c:if>  /><label for="locations">주소창사용</label>&nbsp; -->
				</td>
			</tr>
			<tr>
				<th scope="row">쿠키값설정</th>
				<td class="left">
					[다음에 창열지 않기] 사용
					<label for="ck_chk_Y"><input type="radio" id="ck_chk_Y" name="ck_chk" value="Y" title="사용 선택" onclick="document.getElementById('ck').style.display='block'" <c:if test= "${popdto.ck_chk eq 'Y' or popdto.ck_chk == null}"> checked="checked"</c:if> />사용</label>
					<label for="ck_chk_N"><input type="radio" id="ck_chk_N" name="ck_chk" value="N" title="사용안함 선택" onclick="document.getElementById('ck').style.display='none'" <c:if test= "${popdto.ck_chk eq 'N'}"> checked="checked"</c:if> />사용안함</label>
					
					<div id="ck" <c:if test= "${popdto.ck_chk eq 'N'}">style="display:none;"</c:if> class="pt4">
						<table class="bbs_nor mt5" summary="팝업창 쿠키값 기간(일)설정">
						<colgroup>
						<col width="15%" />
						<col width="*" />
						</colgroup>
						<tr>
							<td class="le"><label for="ck_val"><strong>팝업창을 며칠동안 열지 않겠습니까?</strong></label> <input type="text" size="3" title="창을 열지않을 기간(일) 입력" id="ck_val" name="ck_val" class="ta_input_nor eng" maxlength="2" value="${popdto.ck_val == null ? '1' : popdto.ck_val}" /> 일</td>
						</tr>
						</table>
					</div>	
				</td>
			</tr>
			
			
			<tr>
				<th scope="row"><label for="w_chk">팝업상태</label></th>
				<td class="left">
					<select id="w_chk" name="w_chk" title="사용여부 선택" class="ta_select" style="width:70px;" >
						<option value="Y"  ${popdto.w_chk eq 'Y' ? 'selected="selected"' : '' }>새창</option>
						<option value="L"  ${popdto.w_chk eq 'L' ? 'selected="selected"' : '' }>레이어</option>
						<option value="N" ${popdto.w_chk eq 'N' or popdto.w_chk eq null ? 'selected="selected"' : '' }>중지</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<th scope="row"><label for="pos">팝업적용기종</label></th>
				<td class="left">
					<input type="radio" id="pos_A" name="pos" value="A" ${popdto.pos eq 'A' ? 'checked="checked"' : '' } /><label for="pos_A">전체</label>
					<input type="radio" id="pos_P" name="pos" value="P" ${popdto.pos eq 'P' ? 'checked="checked"' : '' } /><label for="pos_P">PC</label>
					<input type="radio" id="pos_M" name="pos" value="M" ${popdto.pos eq 'M' ? 'checked="checked"' : '' } /><label for="pos_M">MOBILE</label>
				</td>
			</tr>

			<tr>
				<td colspan="2" class="textarea">
					<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
					<textarea name="content" id="content" style="width:100%; height:350px;">${popdto.content}</textarea>
					<script type="text/javascript">
					var oEditors = [];
					nhn.husky.EZCreator.createInIFrame({
						oAppRef: oEditors,
						elPlaceHolder: "content",
						sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
						fCreator: "createSEditor2"
					});							
					</script>
				</td>
			</tr>
			</table>
		</fieldset>

		<div class="contoll_box">
			<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="목록" onclick="page_go1('list.do?${pageInfo}');" class="btn_wh_default" /></span>
		</div>

	</form>

</div>

<script type="text/javascript">
$(document).ready(function(){ 
	click_limit();
});
</script>