<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>도서관 상/하단 HTML 정보</title>
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/all.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/sub_layout.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/design_default.css" />
<link rel="Stylesheet" type="text/css" href="/nanum/nninc/common/js/jquery-ui.css" />
<link href="http://fonts.googleapis.com/css?family=Armata" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="/nanum/ncms/common/js/common_design.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-cookie.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-rightClick.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_design.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>

<script type="text/javascript" src="/nanum/ncms/common/js/design_default.js"></script>
	
</head>

<body>

<!-- 전체싸는 박스 -->
<div style="padding-left:15px;padding-top:15px;">

	<!-- 중간 영역 -->
	<div>

		<!-- 내용영역 -->
		<div  style="width: 650px;">

			<div id="contents_head"  style="width:650px;"> 
				<div id="contents_title">
					<h1 class="tit"><span>[독서문화프로그램] 도서관 상/하단 HTML 정보</span></h1>
				</div>
			</div>
			
	<form id="frm" method="post" action="libConfOk.do">
		<div>
		<input type="hidden" name="ec_idx" value="${edusatConf.ec_idx }" />
		<input type="hidden" name="prepage" value="${NOWPAGE}" />
		
		</div>
			<fieldset>
				<legend>문화행사 환경설정 작성</legend>
				<table cellspacing="0" class="bbs_common bbs_default" summary="문화행사 환경설정을 위한 입력 양식입니다.">
				<caption>문화행사 환경설정 </caption>
				<colgroup>
				<col width="15%" />
				<col width="*" />
				</colgroup>
				<tr>
					<th scope="row"><label for="ec_lib">도서관구분</label></th>
					<td class="left">
						<select id="ec_lib" name="ec_lib" title="도서관 선택" class="t_search" style="width:150px;" onchange="javascript:location.href='libConf.do?ec_lib='+encodeURI(this.value);">
							<option value="" > -- 도서관선택 --</option>
						<c:forEach items="${ec_lib_arr}" var="item">
							<option value="${item}" ${libConf.ec_lib eq item ? 'selected="selected"' : '' }>${item}</option>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="ec_tophtml">상단 HTML 정보</label></th>
					<td class="left"><textarea cols="100" style="height:300px;" id="ec_tophtml" name="ec_tophtml" title="상단 HTML 입력"  class="ta_textarea w9" tabindex="5004">${libConf.ec_tophtml }</textarea></td>
				</tr>
				<tr>
					<th scope="row"><label for="ec_btmhtml">하단 HTML 정보</label></th>
					<td class="left"><textarea cols="100" style="height:300px;" id="ec_btmhtml" name="ec_btmhtml" title="하단 HTML 입력"  class="ta_textarea w9"  tabindex="5006">${libConf.ec_btmhtml }</textarea></td>
				</tr>
				</table>
			</fieldset>
			<br/>
			<div style="text-align:center;">
				<span><input type="submit" value="등록" class="btn_bl_default" /></span>
				<span><input type="button" onclick="javaascript:self.close();" value="창닫기" class="btn_wh_default" /></span>
			</div>
			<br/>
		</form>
		
		<br/><br/>

		</div>
		<!-- 내용들어가는곳 -->

		</div>
		<!-- //내용영역 -->


</div>
</div>

</body>
</html>