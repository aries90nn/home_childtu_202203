<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/ncms/ebook/list.do' : param.prepage }" />
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_ebook_page.js"></script>
<script type="text/javascript" src="/nfu/NFU_class.js" charset="utf-8"></script>

<script type="text/javascript">
function eBookPageUpload(){
	try{
		nfu_upload();
		return false;
	}catch(e){return true;}
}

function deleteImg(eb_pk, eb_idx){
	if(confirm("이미지를 삭제하시겠습니까?")){
		location.href="./delete2Ok.do?eb_pk="+eb_pk+"&ebp_idx="+eb_idx+"&prepage=${nowPageEncode}";
	}
}

function deletePage(eb_pk, eb_idx){
	if(confirm("이북 페이지를 삭제하시겠습니까?")){
		location.href="./deleteOk.do?eb_pk="+eb_pk+"&ebp_idx="+eb_idx+"&prepage=${nowPageEncode}";
	}
}
</script>
	
<h1 class="tit"><span>이북</span> 페이지 설정</h1>
	
<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<h2 class="tit">현재페이지경로<span class="loc">: <a href='${prepage }'>이북리스트</a> > <strong class="point_default">${ebookdto.eb_subject}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;size: ${ebookdto.eb_width}px * ${ebookdto.eb_height}px</strong></span></h2>

<form id="frm" method="post" action="/ncms/ebook_page/writeOk.do" enctype="multipart/form-data" onsubmit="return eBookPageUpload()">
	<div>
	<input type="hidden" name="eb_pk" value="${ebookdto.eb_pk}"/>
	</div>
	<div style="position:relative;">
		<jsp:include page="inc_write_fileupload.jsp" />
		
			<div class="contoll_box" >
				<span><input id="submitbtn" type="submit" value="등록" class="btn_bl_default" /></span>
				<span><input id="cancelbtn" type="button" value="취소" class="btn_gr_default" onclick="location.href='${prepage}';" /></span>
			</div>
		</div>
	</form>

<br/>
<br/>
<br/>
<br/>
<br/>

<form id= "frm_list" action="/ncms/ebook_page/modifyOk.do" enctype="multipart/form-data" method='post' >
	<div>
		<input type="hidden" id="status" name="status" />
		<input type="hidden" id="ebp_chk" name="ebp_chk" />
		<input type="hidden" id="ebp_idx" name="ebp_idx" />
		<input type="hidden" id="eb_pk" name="eb_pk" value="${ebookdto.eb_pk}" />
		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" id="prepage" name="prepage" value="${nowPage }" />
	</div>

<fieldset>
<legend>페이지관리 작성/수정</legend>
<table class="bbs_common bbs_default" summary="사이트에 사용하는 페이지를 관리합니다.">
	<caption>페이지관리 서식</caption>
<colgroup>
<col width="50" />
<col width="100" />
<col />
<col width="250" />

<col width="50" />
<col width="50" />
</colgroup>

<thead>
	<tr>
		<th scope="col">선택</th>
		<th scope="col">등록이미지</th>
		<th scope="col">페이지명</th>
		<th scope="col">이미지수정</th>
		<th scope="col">수정</th>
		<th scope="col">삭제</th>
	</tr>
</thead>

<tbody>

<c:forEach items="${ebookpageList}" var="ebookpage" varStatus="no">
<tr>
	<td class="center"><input type="checkbox" name="chk" value="${ebookpage.ebp_idx}" title="해당 페이지 선택" /></td>

	<td class="center">
		<c:if test="${!empty ebookpage.ebp_pageimg}">
		<div class="vam pt5">
			<img src = "/data/ebook/${ebookpage.eb_pk}/small/${ebookpage.ebp_pageimg}" alt="${ebookpage.ebp_subject}" />

			<input type="button" value="이미지삭제" class="btn_wh_" onclick="deleteImg('${ebookpage.eb_pk}','${ebookpage.ebp_idx}');" />
		</div>
		</c:if>
	</td>

	<td>
		<input type="text" title="페이지 입력" id="ebp_subject_${ebookpage.ebp_idx}" name="ebp_subject_${ebookpage.ebp_idx}" class="ta_input w9 editmode" value="${ebookpage.ebp_subject}" maxlength="100" />
	</td>

	<td class="left">
		<input type="file" size="20" title="이미지 등록" id="ebp_pageimg_file_${ebookpage.ebp_idx}" name="ebp_pageimg_file_${ebookpage.ebp_idx}" class="ta_input_file" value="" maxlength="100" />
		<input type="hidden" id="ebp_pageimg_org_${ebookpage.ebp_idx}" name="ebp_pageimg_org_${ebookpage.ebp_idx}" value="${ebookpage.ebp_pageimg}" />
	</td>


	<td class="center">
		<a href="javascript:m_chk('${ebookpage.ebp_idx}');"><img src="/nanum/ncms/img/common/modify_icon.gif" alt="수정" /></a>
	</td>
	<td class="center"><a href="#del" onclick="deletePage('${ebookpage.eb_pk}','${ebookpage.ebp_idx}');"><img src="/nanum/ncms/img/common/delete_icon.gif" alt="삭제" /></a></td>

</tr>
</c:forEach>

</tbody>
</table>
</fieldset>

<!-- 하단버튼 -->
<div id="contoll_area">
	<ul>
		<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 페이지삭제</a></p><!--p><a href="javascript:moveAll();" class="btn_gr">순서일괄수정</a></p--><p><a onclick="javascript:window.open('listMove.do?eb_pk=${ebookdto.eb_pk}&prepage=${nowPageEncode }','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
	</ul>						
</div>
<!-- //하단버튼 -->


</form>
	
</div>
<!-- 내용들어가는곳 -->