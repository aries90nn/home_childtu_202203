<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/ncms/banner2/list.do' : param.prepage }" />
<c:if test="${empty param.prepage and !empty BUILDER_DIR }">
	<c:set var="prepage" value="/${BUILDER_DIR }${prepage }" />
</c:if>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_banner2.js"></script>
<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>

	<h1 class="tit"><span>팝업존 ${empty banner2.b_l_num ? "생성" : "수정"}</span></h1>

	<!-- 내용들어가는곳 -->
	<div id="contents_area">
	
		<form id="frm" method="post" action="./writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk();">
		<div>
		<input type="hidden" name="b_l_num" value="${banner2.b_l_num}" /><!-- (수정일때사용) -->
		<input type="hidden" name="b_main_img2" value="${banner2.b_main_img}" />
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
					<th scope="row"><label for="b_l_subject">제목</label></th>
					<td class="left">
						<input type="text" title="제목 입력" id="b_l_subject" name="b_l_subject" class="ta_input w9" value="${banner2.b_l_subject}" maxlength="100" />
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
						<input type="checkbox" name="unlimited" id="unlimited" value="Y"  <c:if test= "${banner2.unlimited == 'Y'}"> checked</c:if>  onclick="javascript: click_limit();" /><label for="unlimited">무제한</label>
						
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="b_main_img_file">팝업존 이미지(메인) </label></th>
					<td class="left">
						<input type="file" size="70" id="b_main_img_file" name="b_main_img_file" title="팝업존 이미지 찾아보기" class="ta_input_file" value="" />
						
						<c:if test="${!empty banner2.b_main_img}">
						    <br/><img src = "/data/banner2/${banner2.b_main_img}" alt="${banner2.b_l_subject}" class="vam pt5" />
							&nbsp;<input type='checkbox' name='b_main_img_del' value='${banner2.b_main_img}' />삭제
						</c:if>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="b_l_chk">사용여부</label></th>
					<td class="left">
						<select id="b_l_chk" name="b_l_chk" title="배너 사용여부 선택" class="ta_select" style="width:60px;" >
							<option value="Y"  ${banner2.b_l_chk eq 'Y' ? 'selected="selected"' : '' }>사용</option>
							<option value="N" ${banner2.b_l_chk eq 'N' or banner2.b_l_chk eq '' ? 'selected="selected"' : '' }>중지</option>
						</select>
					</td>
				</tr>
				<tr>
			<th scope="row"><label for="b_l_page">연결창</label></th>
			<td class="left">
				<input type="radio" name="b_l_page" id="b_l_page1" title="페이지로 연결 선택" value="1" onclick="javascript: click_page(1);" <c:if test= "${banner2.b_l_page == '1'}"> checked</c:if>/> <label for="b_l_page1">페이지</label>
				<input type="radio" name="b_l_page" id="b_l_page2" title="팝업으로 연결 선택" value="2" onclick="javascript: click_page(2);" <c:if test= "${banner2.b_l_page == '2'}"> checked</c:if>/> <label for="b_l_page2">팝업</label>

				<div id="page_1" <c:if test= "${banner2.b_l_page == '2'}"> style="display:none;"</c:if> class="pt4">
					<table class="bbs_nor" summary="페이지로 연결 설정">
					<colgroup>
					<col width="70" />
					<col />
					</colgroup>
					<tr>
						<th scope="row"><label for="b_l_url">연결주소</label></th>
						<td class="left">
							<input type="text" size="50" title="연결주소 입력" id="b_l_url" name="b_l_url" class="ta_input_nor w9" value="${banner2.b_l_url}" maxlength="300" /><br/><span class="point fs11">* 사용 예) http://www.nninc.co.kr</span>
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="b_l_win">새창여부</label></th>
						<td class="left">
							<input type="checkbox" name="b_l_win" id="b_l_win" title="새창링크 선택" value="1" <c:if test= "${banner2.b_l_win == '1'}"> checked</c:if> />
							<label for="b_l_win">새창으로 열기</label>
						</td>
					</tr>
					</table>
				</div>

				<div id="page_2" <c:if test= "${banner2.b_l_win == '2'}"> style="display:none;"</c:if> class="pt4">
					<table class="bbs_nor" summary="팝업 연결 설정">
					<colgroup>
					<col width="80" />
					<col />
					</colgroup>
					<tr>
						<th scope="row"><label for="w_width">팝업창 크기</label></th>
						<td class="left">
							가로 <input type="text" size="4" title="팝업창 가로사이즈 입력" id="w_width" name="w_width" class="ta_input_nor eng" maxlength="4" value="${banner2.w_width}" /> <span class="eng">pixel</span>&nbsp;&nbsp;
							세로 <input type="text" size="4" title="팝업창 세로사이즈 입력" id="w_height" name="w_height" class="ta_input_nor eng" maxlength="4" value="${banner2.w_height}" /> <span class="eng">pixel</span> 
							<span class="point fs11">* 픽셀(pixel) 단위만 사용하실 수 있습니다. </span>
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="w_top">위치지정</label></th>
						<td class="left">
							상단 <input type="text" size="4" title="팝업창 가로위치 입력" id="w_top" name="w_top" class="ta_input_nor eng" maxlength="4" value="${banner2.w_top}" /> <span class="eng">pixel</span>&nbsp;&nbsp;
							좌측 <input type="text" size="4" title="팝업창 세로위치 입력" id="w_left" name="w_left" class="ta_input_nor eng" maxlength="4" value="${banner2.w_left}" /> <span class="eng">pixel</span> 
							<span class="point fs11">* 픽셀(pixel) 단위만 사용하실 수 있습니다. </span>
						</td>
					</tr>
					<tr>
						<th scope="row">환경설정</th>
						<td class="left">
							<input type="checkbox" name="scrollbars" id="scrollbars" title="스크롤바사용" value="Yes" <c:if test= "${banner2.scrollbars == 'Yes'}"> checked</c:if> /><label for="scrollbars">스크롤바사용</label>&nbsp;
							<input type="checkbox" name="toolbar" id="toolbar" title="툴바사용" value="Yes" <c:if test= "${banner2.toolbar == 'Yes'}"> checked</c:if> /><label for="toolbar">툴바사용</label>&nbsp;
							<input type="checkbox" name="menubar" id="menubar" title="메뉴바사용" value="Yes" <c:if test= "${banner2.menubar == 'Yes'}"> checked</c:if> /><label for="menubar">메뉴바사용</label>&nbsp;
							<input type="checkbox" name="locations" id="locations" title="주소창사용" value="Yes" <c:if test= "${banner2.locations == 'Yes'}"> checked</c:if> /><label for="locations">주소창사용</label>&nbsp;							
						</td>
					</tr>
					<tr>
						<td colspan="2" class="textarea">

								<div id="editorArea"></div>
								<div id="content_org" style="display:none;">${banner2.content}</div>
						</td>
					</tr>
					</table>
				</div>


			</td>
		</tr>
				</table>
			</fieldset>
	
			<div class="contoll_box">
				<span><input type="submit" value="등록" class="btn_bl_default" /></span><span><input type="button" value="취소" onclick="page_go1('${prepage}');" class="btn_wh_default" /></span>
			</div>
	
		</form>
	
	</div>
	<!-- 내용들어가는곳 -->

<script type="text/javascript">
$(document).ready(function(){ 
	click_limit();
	click_page('${banner2.b_l_page}');
});

var oEditors = [];

</script>