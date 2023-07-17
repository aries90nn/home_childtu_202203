<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>관리자페이지 : 팝업관리 > 수정 </title>
</head>

<body id="wrap">
<!-- 상단 영역-->
<jsp:include page="../common/file/top.jsp" />

<!-- 사이드영역 -->
<jsp:include page="../common/file/left.jsp" />
<!-- 중간 영역 -->
<div id="content_wrap">

	<!-- 내용영역 -->
	<div id="contents">
	
		<h1 class="tit"><span>팝업수정</span> </h1>
		
		<!-- 내용들어가는곳 -->
		<div id="contents_area">
		
			<form id="frm" method="post" action="modifyOk.do?${pageInfo}" onsubmit="return w_chk2();">
			<input type="hidden" name="idx" value="${popdto.idx}" />
				<fieldset>
					<legend>팝업생성 서식 작성</legend>
					<table class="bbs_common bbs_default" summary="신규 팝업 생성을 위한 입력 양식입니다.">
					<caption>팝업생성 서식</caption>
					<colgroup>
					<col width="15%" />
					<col width="*" />
					</colgroup>
					<tr>
						<th scope="row"><label for="subject">제목</label></th>
						<td class="left">
							<input type="text" title="제목 입력" id="subject" name="subject" class="ta_input w9" value="${popdto.subject}" maxlength="200" />
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="sdate_y">사용기간</label></th>
						<td class="left">
							
							<select id="sdate_y" name="sdate_y" title="시작 사용기간(년)을 선택" class="ta_select" style="width:75px;" >
								<c:forEach var="i" begin="${sdate_y_begin}" end="${sdate_y_end}">
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
								<c:forEach var="i" begin="1" end="24">
								<option value="${i}" ${sdate_h == i ? 'selected="selected"' : '' }>${i}시</option>
								</c:forEach>
							</select>
							<select id="sdate_n" name="sdate_n" title="시작 사용기간(분)을 선택" class="ta_select" style="width:60px;" >
								<option value="00" ${sdate_n == "00" ? 'selected="selected"' : '' }>00분</option>
								<option value="30" ${sdate_n == "30" ? 'selected="selected"' : '' }>30분</option>
							</select>
							<br/>								
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~
							<select id="edate_y" name="edate_y" title="종료 사용기간(년)을 선택" class="ta_select limit" style="width:75px;" >
								<c:forEach var="i" begin="${edate_y_begin}" end="${edate_y_end}">
								<option value="${i}" ${edate_y == i ? 'selected="selected"' : '' }>${i}년</option>
								</c:forEach>
							</select>
							<select id="edate_m" name="edate_m" title="종료 사용기간(월)을 선택" class="ta_select limit" style="width:60px;" >
								<c:forEach var="i" begin="1" end="12">
								<option value="${i}" ${edate_y == m ? 'selected="selected"' : '' }>${i}월</option>
								</c:forEach>
							</select>
							<select id="edate_d" name="edate_d" title="종료 사용기간(일)을 선택" class="ta_select limit" style="width:60px;" >
								<c:forEach var="i" begin="1" end="31">
								<option value="${i}" ${edate_d == i ? 'selected="selected"' : '' }>${i}일</option>
								</c:forEach>
							</select>&nbsp;&nbsp;
							<select id="edate_h" name="edate_h" title="종료 사용기간(시간)을 선택" class="ta_select limit" style="width:60px;" >
								<c:forEach var="i" begin="1" end="24">
								<option value="${i}" ${edate_h == i ? 'selected="selected"' : '' }>${i}시</option>
								</c:forEach>
							</select>
							<select id="edate_n" name="edate_n" title="종료 사용기간(분)을 선택" class="ta_select limit" style="width:60px;" >
								<option value="00" ${edate_n == "00" ? 'selected="selected"' : '' }>00분</option>
								<option value="30" ${edate_n == "30" ? 'selected="selected"' : '' }>30분</option>
							</select>
							&nbsp;&nbsp;
							<input type="checkbox" name="unlimited" id="unlimited" value="Y"  onclick="javascript: click_limit();"  <c:if test= "${popdto.unlimited == 'Y'}"> checked="checked"</c:if> /><label for="unlimited">무제한</label>
		
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="w_width">팝업창 크기</label></th>
						<td class="left">
							가로 <input type="text" size="4" title="팝업창 가로사이즈 입력" id="w_width" name="w_width" class="ta_input eng" maxlength="4" value="${popdto.w_width}" /> <span class="eng">pixel</span>&nbsp;&nbsp;
							세로 <input type="text" size="4" title="팝업창 세로사이즈 입력" id="w_height" name="w_height" class="ta_input eng" maxlength="4" value="${popdto.w_height}" /> <span class="eng">pixel</span> 
							<span class="point fs11">* 픽셀(pixel) 단위만 사용하실 수 있습니다. </span>
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="w_top">위치지정</label></th>
						<td class="left">
							상단 <input type="text" size="4" title="팝업창 가로위치 입력" id="w_top" name="w_top" class="ta_input eng" maxlength="4" value="${popdto.w_top}" /> <span class="eng">pixel</span>&nbsp;&nbsp;
							좌측 <input type="text" size="4" title="팝업창 세로위치 입력" id="w_left" name="w_left" class="ta_input eng" maxlength="4" value="${popdto.w_left}" /> <span class="eng">pixel</span> 
							<span class="point fs11">* 픽셀(pixel) 단위만 사용하실 수 있습니다. </span>
						</td>
					</tr>
					<tr>
						<th scope="row">환경설정</th>
						<td class="left">
							<input type="checkbox" name="scrollbars" id="scrollbars" title="스크롤바사용" value="Yes" <c:if test= "${popdto.scrollbars == 'Yes'}"> checked="checked"</c:if>  /><label for="scrollbars">스크롤바사용</label>&nbsp;
							<input type="checkbox" name="toolbar" id="toolbar" title="툴바사용" value="Yes" <c:if test= "${popdto.toolbar == 'Yes'}"> checked="checked"</c:if>  /><label for="toolbar">툴바사용</label>&nbsp;
							<input type="checkbox" name="menubar" id="menubar" title="메뉴바사용" value="Yes" <c:if test= "${popdto.menubar == 'Yes'}"> checked="checked"</c:if>   /><label for="menubar">메뉴바사용</label>&nbsp;
							<input type="checkbox" name="locations" id="locations" title="주소창사용" value="Yes"  <c:if test= "${popdto.locations == 'Yes'}"> checked="checked"</c:if>  /><label for="locations">주소창사용</label>&nbsp;
						</td>
					</tr>
					<tr>
						<th scope="row">쿠키값설정</th>
						<td class="left">
							[다음에 창열지 않기] 사용
							<label for="ck_chk_Y"><input type="radio" id="ck_chk_Y" name="ck_chk" value="Y" title="사용 선택" onclick="document.getElementById('ck').style.display='block'" <c:if test= "${popdto.ck_chk == 'Y'}"> checked="checked"</c:if> />사용</label>
							<label for="ck_chk_N"><input type="radio" id="ck_chk_N" name="ck_chk" value="N" title="사용안함 선택" onclick="document.getElementById('ck').style.display='none'" <c:if test= "${popdto.ck_chk == 'N'}"> checked="checked"</c:if> />사용안함</label>
							
							<div id="ck" style="display:block;" class="pt4">
								<table class="bbs_nor mt5" summary="팝업창 쿠키값 기간(일)설정">
								<colgroup>
								<col width="15%" />
								<col width="*" />
								</colgroup>
								<tr>
									<td class="le"><label for="ck_val"><strong>팝업창을 며칠동안 열지 않겠습니까?</strong></label> <input type="text" size="3" title="창을 열지않을 기간(일) 입력" id="ck_val" name="ck_val" class="ta_input_nor eng" maxlength="3" value="${popdto.ck_val}" /> 일</td>
								</tr>
								</table>
							</div>	
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
		<!-- 내용들어가는곳 -->

	</div>
	<!-- //내용영역 -->

</div>

 
<script type="text/javascript">
<c:if test= "${popdto.ck_chk == 'Y'}">
	document.getElementById('ck').style.display='block'
</c:if>
<c:if test= "${popdto.ck_chk == 'N'}">
	document.getElementById('ck').style.display='none'
</c:if>
</script>

<!-- 하단영역-->
<jsp:include page="../common/file/bottom.jsp" />

</body>
</html>