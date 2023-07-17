<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_cms.js"></script>
<script type="text/javascript" src="/nfu/NFU_class.js" charset="utf-8"></script>

<script type="text/javascript">
function pageUpload(){
	try{
		nfu_upload();
		return false;
	}catch(e){return true;}
}

function deteletOk(img_file, ct_menu_folder){
	if(confirm("삭제하시겠습니까?")){
		var url = "./imgDeleteOk.do?img_file="+encodeURIComponent(img_file);
		url += "&ct_menu_folder="+encodeURIComponent(ct_menu_folder);
		url += "&prepage=${nowPageEncode}";
		location.href=url;
	}
}
</script>

	<h1 class="tit"><span>이미지 파일</span> 관리</h1>
		
		<!-- 내용들어가는곳 -->
	<div id="contents_area">
		
	<h2 class="tit">이미지경로<span class="loc" style="font-size:13px">: ${ct_name} ( <strong class="point_default">/nanum/site/builder/dir/${BUILDER_DIR}/img/${ct_menu_folder}</strong> )</span></h2>
		
	<form id="frm" method="post" action="./img.do?ct_idx=${ct_idx}" enctype="multipart/form-data" onsubmit="return pageUpload()">
	<input type="hidden" name="prepage" value='${nowPage }"' />
		<div>
		<input type="hidden" name="ct_menu_folder" value="${ct_menu_folder}"/>
		</div>
		<div style="position:relative;">
			<jsp:include page="inc_write_fileupload.jsp" />
		
			<div class="contoll_box" >
				<span><input id="submitbtn" type="submit" value="등록" class="btn_bl_default" /></span>
			</div>
		</div>
	</form>
	<br/><br/>
	
	<form id= "frm_list" action="./imgOk.do" enctype="multipart/form-data" method='post' >
		<div>
			<input type="hidden" name="ct_menu_folder" value="${ct_menu_folder}"/>
			<input type="hidden" name="ct_idx" value="${ct_idx}"/>
			<input type="hidden" name="chk_all" value=""/>
			<input type="hidden" name="status" value=""/>
			<input type="hidden" id="num" name="num" />
			<input type="hidden" name="prepage" value='${nowPage }"' />
		</div>
		<h3 class="tit">이미지파일</h3>
		<fieldset>
			<legend>홈페이지 메뉴별 내용입력 서식 작성</legend>
			<table class="bbs_common bbs_default jsColorTable" summary="신규 QR코드 생성을 위한 입력 양식입니다.">
			<caption>홈페이지 메뉴별 내용입력 서식</caption>
			<colgroup>
			<col width="50" />
			<col width="11%" />
			<col width="*" />
			<!-- col width="10%" />
			<col width="7%" / -->
			<col width="7%" />
			</colgroup>
	
		<thead>
			<tr>
				<th scope="col">선택</th>
				<th scope="col">등록이미지</th>
				<th scope="col">이미지명</th>
				<!-- th scope="col">수정파일</th>
				<th scope="col">수정</th -->
				<th scope="col">삭제</th>
			</tr>
		</thead>
		
		<tbody>
		<c:forEach items="${files}" var="files" varStatus="no">
		<tr>
			<td class="center"><input type="checkbox" name="chk" value="${files.name}" title="해당 페이지 선택" /></td>
			<td class="center rborder">
				<c:if test="${files.name != null}">
				<div class="vam pt5">
					<img src = "/nanum/site/builder/dir/${BUILDER_DIR }/img/${ct_menu_folder}/${files.name}" alt="${files.name}" style="max-height:80px;max-width:300px" />
				</div>
				</c:if>
			</td>
			<td class="left rborder">${files.name}<br/><strong>실제경로:  </strong><strong class="point_default">/nanum/site/builder/dir/${BUILDER_DIR }/img/${ct_menu_folder}/${files.name}</strong></td>
			<!-- td class="left">
				<input type="file" size="20" title="이미지 등록" id="img_file_${no.count}" name="img_file_${no.count}" class="ta_input_file" value="" maxlength="100" />
				<input type="hidden" id="img_org_${no.count}" name="img_org_${no.count}" value="${files.name}" />
			</td>
			<td class="center">
				<a href="javascript:m_chk('${no.count}');"><img src="/nanum/ncms/img/common/modify_icon.gif" alt="수정" /></a>
			</td -->
			<td class="center"><a href="javascript:deteletOk('${files.name}','${ct_menu_folder}');"><img src="/nanum/ncms/img/common/delete_icon.gif" alt="삭제" /></a></td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
	</fieldset>
	
	<!-- 하단버튼 -->
		<div id="contoll_area">
			<ul>
				<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete_img();" class="btn_gr">선택 이미지삭제</a></p></li>
			</ul>						
		</div>
		<!-- //하단버튼 -->
	
	<br/><br/>
	
	</form>

	</div>
	<!-- 내용들어가는곳 -->

<script type="text/javascript">
$(function(){

	$(".jsColorTable").find("td, img").mouseenter(function(){
		$(this).parents("tr:first").find("td").css("background-color","#ffffe6");
	}).mouseout(function(){
		$(this).parents("tr:first").find("td").css("background-color","#ffffff");
	});
});
</script>
