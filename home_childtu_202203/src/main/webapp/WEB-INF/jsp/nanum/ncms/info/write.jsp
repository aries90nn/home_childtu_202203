<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_banner.js"></script>

<h1 class="tit"><span>기본 설정</span></h1>

<script type="text/javascript">
//<![CDATA[
	
	function w_chk(){
		oEditors.getById["sc_copyright"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용된다.
		return true;
	}
//]]>
</script>
<!-- 내용들어가는곳 -->
<div id="contents_area">
		
			<form id="frm" method="post" action="/ncms/info/writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk()">
			<div><input type="hidden" name="sc_idx" value="${info.sc_idx}" /><!-- (수정일때사용) -->
				<input type="hidden" name="sc_logo2" value="${info.sc_logo}" />
				<input type="hidden" name="sc_editor " value="${info.sc_editor}" />
			</div>
			
				<h2 class="tit">기본정보<span class="ri">기본정보를 입력해주세요.</span></h2>
					<fieldset>
						<legend>기본정보 서식 작성/수정</legend>
						<table class="bbs_common bbs_default" summary="사이트 기본정보를 위한 입력/수정 양식입니다.">
						<caption>기본정보 서식</caption>
						<colgroup>
						<col width="15%" />
						<col />
						</colgroup>
						<tr>
							<th scope="row"><label for="sc_sitename">사이트명</label></th>
							<td class="left"><input type="text" title="사이트명 입력" id="sc_sitename" name="sc_sitename" class="ta_input w7" value="${info.sc_sitename}" maxlength="100" />
							<span class="point fs11">* 사이트명은 100자 이하로 입력해주세요.</span>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="sc_logo_file">로고 이미지</label></th>
							<td class="left">
								<input type="file" size="70" id="sc_logo_file" name="sc_logo_file" title="배너 이미지 찾아보기" class="ta_input_file" value="" />
								
								<c:if test="${!empty info.sc_logo}">
									<br/><img src = "/data/info/${info.sc_logo}" class="vam pt5" style="width:160px;height:82px"/>
									&nbsp;<input type='checkbox' name='sc_logo_del' value='${info.sc_logo}' />삭제
								</c:if>
								<br/><br/>
								<span class="point fs11">* 5MB의 jpg,png,gif 이미지 파일만 허용 가능합니다. 이미지가 주어진 사이즈보다 작거나 크더라도 고정 사이즈로 보여지게됩니다.  <strong>[고정 사이즈 가로 :  160px, 세로 : 82px]</strong></span>
							</td>
						</tr>
						<tr>
							<th scope="row">메타태그</th>
							<td class="left"><textarea name="sc_header_meta" id="sc_header_meta" class="ta_textarea w99" style="height:120px">${info.sc_header_meta}</textarea></td>
						</tr>
						<tr>
							<th scope="row" colspan="2" style="height:30px;">카피라이터</th>
						</tr>
						<tr>
							<td class="left" colspan="2">
								<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
								<textarea name="sc_copyright" id="sc_copyright" style="width:100%; height:250px;"><link type="text/css" rel="stylesheet" href="/nanum/site/common/css/all.css" /><link type="text/css" rel="stylesheet" href="/nanum/site/common/css/head_layout.css" /><link type="text/css" rel="stylesheet" href="/nanum/site/common/css/foot_layout.css" />
${info.sc_copyright}</textarea>
								<script type="text/javascript">
								var oEditors = [];
								nhn.husky.EZCreator.createInIFrame({
									oAppRef: oEditors,
									elPlaceHolder: "sc_copyright",
									sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
									fCreator: "createSEditor2"
								});							
								</script>
							</td>
						</tr>
						</table>
					</fieldset>
					
					<div class="contoll_box">
						<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="취소" onclick="page_go1('write.do');" class="btn_wh_default" /></span>
					</div>

				</form>
		
		</div>
		<!-- 내용들어가는곳 -->


	</div>
	<!-- //내용영역 -->