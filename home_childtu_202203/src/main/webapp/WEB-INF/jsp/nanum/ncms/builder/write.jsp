<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/ncms/builder/list.do' : param.prepage }" />
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_builder.js"></script>
<script type="text/javascript">
$(function(){
	
	$("input[name='bs_main']:radio").each(function(){
		if($(this).prop("checked")){
			var idx = $(this).attr("id").replace("bs_main_", "");
			mainImageView(idx);
		}
	});
	$("input[name='bs_skin']:radio").each(function(){
		if($(this).prop("checked")){
			var idx = $(this).attr("id").replace("bs_skin_", "");
			skinImageView(idx);
		}
	});
	
});

</script>

<h1 class="tit"><span>${empty builder.bs_num ? "신규 사이트 생성" : "사이트 수정"}</span> </h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<h2 class="tit">사이트 기본정보 <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>
	
	<form id="frm" method="post" action="writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk();">
	<input type="hidden" name="bs_num" value="${builder.bs_num}" />
	<input type="hidden" name="prepage" value="${prepage }" />
		<fieldset>
			<legend>사이트생성 서식 작성</legend>
			<table class="bbs_common bbs_default" summary="신규 사이트 생성을 위한 입력 양식입니다.">
			<caption>사이트생성 서식</caption>
			<colgroup>
			<col width="15%" />
			<col width="*" />
			</colgroup>
			
<c:if test="${empty builder.bs_num and fn:length(builderList) > 0 }">
			<tr>
				<th scope="row"><label for="cp_site">기존사이트 메뉴복사</label></th>
				<td class="left">
					<select id="cp_bs_num" name="cp_bs_num" class="ta_select">
						<option value="">선택</option>
	<c:forEach items="${builderList}" var="builder" varStatus="no">
						<option value="${builder.bs_num }" ${param.cp_bs_num eq builder.bs_num ? 'selected="selected"' : ''}>${builder.bs_sitename }</option>
	</c:forEach>
					</select>
					<span class="point fs11">* 선택한 사이트의 하위메뉴가 동일하게 생성됩니다.</span>
				</td>
			</tr>


</c:if>
			<tr>
				<th scope="row"><label for="bs_sitename"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 사이트명</label></th>
				<td class="left">
					<input type="text" title="사이트명 입력" id="bs_sitename" name="bs_sitename" class="ta_input" value="${builder.bs_sitename}" maxlength="50" />
					<span class="point fs11">* 50자 이하로 입력해주세요.</span>
				</td>
			</tr>


			<tr>
				<th scope="row"><label for="bs_directory"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 웹디렉토리명</label></th>
				<td class="left">
<c:if test="${empty builder.bs_num }">
					<input type="text" title="웹디렉토리명 입력" id="bs_directory" name="bs_directory" class="ta_input" value="${builder.bs_directory}" maxlength="100" />
					<span class="point fs11">* 접속할 웹디렉토리명을 입력하세요. ex) a.com/dir 로 접속할 경우 dir이 웹디렉토리명</span>
</c:if>
<c:if test="${!empty builder.bs_num }">
					${builder.bs_directory}
</c:if>
				</td>
			</tr>
			
			
			
			<tr>
				<th scope="row"><label for="bs_main"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 메인타입</label></th>
				<td class="left">
					<c:forEach items="${main_list}" var="main" varStatus="no">
						<p><input type="radio" id="bs_main_${no.index }" name="bs_main" value="${main.type }" onclick="mainImageView('${no.index }');" ${builder.bs_main eq main.type ? 'checked="checked"' : '' } /><label for="bs_main_${no.index }">${main.name }</label></p>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th scope="row">메인형태 미리보기</th>
				<td class="left">
					<c:forEach items="${main_list}" var="main" varStatus="no">
						<p><img id="img_main_${no.index }" src="/nanum/site/builder/main/${main.type }/thumb.jpg" style="max-width:500px;display:none;" /></p>
					</c:forEach>
				</td>
			</tr>
			
			<tr>
				<th scope="row"><label for="bs_skin"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 스킨선택</label></th>
				<td class="left">
					<c:forEach items="${skin_list}" var="skin" varStatus="no">
						<p><input type="radio" id="bs_skin_${no.index }" name="bs_skin" value="${skin.type }" onclick="skinImageView('${no.index }');" ${builder.bs_skin eq skin.type ? 'checked="checked"' : '' } /><label for="bs_skin_${no.index }">${skin.name }</label></p>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th scope="row">스킨형태 미리보기</th>
				<td class="left">
					<c:forEach items="${skin_list}" var="skin" varStatus="no">
						<p><img id="img_skin_${no.index }" src="/nanum/site/builder/skin/${skin.type }/thumb.jpg" style="max-width:550px;display:none;" /></p>
					</c:forEach>
				</td>
			</tr>
			
			<tr>
				<th scope="row"><label for="bs_logo_file">로고 이미지</label></th>
				<td class="left">
					<input type="file" size="70" id="bs_logo_file" name="bs_logo_file" title="로고 이미지 찾아보기" class="ta_input_file" value="" />
					
					<c:if test="${!empty builder.bs_logo}">
						<br/><img src = "/data/builder/${builder.bs_logo}" class="vam pt5" style="width:245px;height:41px"/>
						&nbsp;<input type='checkbox' id='bs_logo_del' name='bs_logo_del' value='${builder.bs_logo}' /><label for="bs_logo_del">삭제</label>
						<input type="hidden" name="bs_logo2" value="${builder.bs_logo}" />
					</c:if>
					<br/><br/>
					<span class="point fs11">* 5MB의 jpg,png,gif 이미지 파일만 허용 가능합니다. 이미지가 주어진 사이즈보다 작거나 크더라도 고정 사이즈로 보여지게됩니다.  <strong>[고정 사이즈 가로 :  245px, 세로 : 41px]</strong></span>
				</td>
			</tr>
			<tr>
				<th scope="row">메타태그</th>
				<td class="left"><textarea name="bs_header_meta" id="bs_header_meta" class="ta_textarea w99" style="height:120px">${builder.bs_header_meta}</textarea></td>
			</tr>
			<tr>
				<th scope="row" colspan="2" style="height:30px;">카피라이터</th>
			</tr>
			<tr>
				<td class="left" colspan="2">
					<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
					<textarea name="bs_copyright" id="bs_copyright" style="width:100%; height:250px;"><link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/${builder.bs_skin }/common/css/all.css" /><link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/${builder.bs_skin }/common/css/head_layout.css" /><link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/${builder.bs_skin }/common/css/foot_layout.css" />${builder.bs_copyright}</textarea>
					<script type="text/javascript">
					var oEditors = [];
					nhn.husky.EZCreator.createInIFrame({
						oAppRef: oEditors,
						elPlaceHolder: "bs_copyright",
						sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
						fCreator: "createSEditor2"
					});
					</script>
				</td>
			</tr>
			
			
			<tr>
				<th scope="row"><label for="bs_chk"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 사용여부</label></th>
				<td class="left">
					<select id="bs_chk" name="bs_chk" title="사용여부 선택" class="ta_select" style="width:70px;" >
						<option value="Y"  ${builder.bs_chk eq 'Y' ? 'selected="selected"' : '' }>사용</option>
						<option value="N" ${builder.bs_chk eq 'N' or builder.bs_chk eq null ? 'selected="selected"' : '' }>중지</option>
					</select>
					<span class="point fs11">* [중지]일 경우 총관리자만 확인 및 관리할 수 있습니다.</span>
				</td>
			</tr>

			</table>
		</fieldset>

		<div class="contoll_box">
			<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="취소" onclick="location.href='${prepage}';" class="btn_wh_default" /></span>
		</div>

	</form>

</div>