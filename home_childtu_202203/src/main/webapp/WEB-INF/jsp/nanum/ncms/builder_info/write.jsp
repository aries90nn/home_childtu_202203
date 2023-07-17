<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_builder_info.js"></script>

<h1 class="tit"><span>기본 설정</span></h1>

<script type="text/javascript">
//<![CDATA[

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

//]]>
</script>

<!-- 내용들어가는곳 -->
<div id="contents_area">
		
			<form id="frm" method="post" action="./writeOk.do" enctype="multipart/form-data" onsubmit="return w_chk()">
			<div><input type="hidden" name="bs_num" value="${BUILDER.bs_num}" /><!-- (수정일때사용) -->
				<input type="hidden" name="bs_logo2" value="${BUILDER.bs_logo}" />
			</div>
			
				<h2 class="tit">사이트 기본정보<span class="ri">사이트 기본정보를 입력해주세요.</span></h2>
					<fieldset>
						<legend>기본정보 서식 작성/수정</legend>
						<table class="bbs_common bbs_default" summary="사이트 기본정보를 위한 입력/수정 양식입니다.">
						<caption>기본정보 서식</caption>
						<colgroup>
						<col width="15%" />
						<col />
						</colgroup>
						<tr>
							<th scope="row"><label for="bs_sitename"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 사이트명</label></th>
							<td class="left">
								<input type="text" title="사이트명 입력" id="bs_sitename" name="bs_sitename" class="ta_input" value="${BUILDER.bs_sitename}" maxlength="50" />
								<span class="point fs11">* 50자 이하로 입력해주세요.</span>
							</td>
						</tr>
						
						<tr>
							<th scope="row"><label for="bs_main"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 메인타입</label></th>
							<td class="left">
								<c:forEach items="${main_list}" var="main" varStatus="no">
									<p><input type="radio" id="bs_main_${no.index }" name="bs_main" value="${main.type }" onclick="mainImageView('${no.index }');" ${BUILDER.bs_main eq main.type ? 'checked="checked"' : '' } /><label for="bs_main_${no.index }">${main.name }</label></p>
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
									<p><input type="radio" id="bs_skin_${no.index }" name="bs_skin" value="${skin.type }" onclick="skinImageView('${no.index }');" ${BUILDER.bs_skin eq skin.type ? 'checked="checked"' : '' } /><label for="bs_skin_${no.index }">${skin.name }</label></p>
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
								
								<c:if test="${!empty BUILDER.bs_logo}">
									<br/><img src = "/data/builder/${BUILDER.bs_logo}" class="vam pt5" style="width:160px;height:82px"/>
									&nbsp;<input type='checkbox' id='bs_logo_del' name='bs_logo_del' value='${BUILDER.bs_logo}' /><label for="bs_logo_del">삭제</label>
								</c:if>
								<br/><br/>
								<span class="point fs11">* 5MB의 jpg,png,gif 이미지 파일만 허용 가능합니다. 이미지가 주어진 사이즈보다 작거나 크더라도 고정 사이즈로 보여지게됩니다.  <strong>[고정 사이즈 가로 :  160px, 세로 : 82px]</strong></span>
							</td>
						</tr>
						<tr>
							<th scope="row">메타태그</th>
							<td class="left"><textarea name="bs_header_meta" id="bs_header_meta" class="ta_textarea w99" style="height:120px">${BUILDER.bs_header_meta}</textarea></td>
						</tr>
						<tr>
							<th scope="row" colspan="2" style="height:30px;">카피라이터</th>
						</tr>
						<tr>
							<td class="left" colspan="2">
								<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
								<textarea name="bs_copyright" id="bs_copyright" style="width:100%; height:250px;"><link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/${BUILDER.bs_skin }/common/css/all.css" /><link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/${BUILDER.bs_skin }/common/css/head_layout.css" /><link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/${BUILDER.bs_skin }/common/css/foot_layout.css" />${BUILDER.bs_copyright}</textarea>
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