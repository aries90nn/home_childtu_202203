<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="a_upload_len" value="20" />
<c:set var="a_nofile" value="gif,jpg,png" />
<c:set var="a_nofilesize" value="10" />
<c:set var="eb_pk" value="${ebookdto.eb_pk}" />

<script type="text/javascript" src="/nfu/NFU_class.js" charset="utf-8"></script>
<script type="text/javascript">
//<![CDATA[
var nfu_upload_count = ${a_upload_len};

//파일추가이벤트
function fileAddEvent(name, size, type){
	//alert(name+","+size+","+type);
	return true;
}


//파일전송이벤트
function fileUploadStartEvent(){
	return true;
}


//개별파일전송완료이벤트
function fileUploadResultEvent(result){
	var ebp_pageimg;
	for(var i=1;i<=nfu_upload_count;i++){
		ebp_pageimg =  document.getElementById("ebp_pageimg_"+i);
		if(ebp_pageimg.value == ""){
			ebp_pageimg.value = result;
			break;
		}
	}
}


//전체파일전송완료이벤트
function fileUploadCompleteEvent(){
	//alert('전체파일 전송완료');
	document.getElementById("frm").submit();
}

//객체생성실패 이벤트
function fileControlCreateFailEvent(e){
	alert("파일컨트롤 생성 실패. 일반업로드로 전환됩니다.\n\n"+e);
	uploadSelect('normal');
}

function eBookPageUpload(){
	nfu_upload();
	return false;
}

function uploadSelect( utype ){
	switch(utype){
		case "multi" :
			document.getElementById("div_multi_btn").style.display = "block";
			document.getElementById("div_normal_btn").style.display = "none";
			NFileUpload.load();
			break;
		default :
			document.getElementById("div_multi_btn").style.display = "none";
			document.getElementById("div_normal_btn").style.display = "block";
			NFileUploadNormal.load();
			break;
	}
	nfu_upload_type = utype;
}

function nfu_upload(){
	if(nfu_upload_type == "multi"){
		NFileUpload.fileUpload();
	}else{
		NFileUploadNormal.fileUpload();
	}
}

//]]>
</script>
※ 파일명 순서대로 페이지가 생성됩니다. (가나다, ABC 순)<br />

<c:forEach var="i" begin="1" end="${a_upload_len}" step="1">
	<input type="hidden" id="ebp_pageimg_${i}" name="ebp_pageimg_data" value="" />
</c:forEach>

<div id="NFU_file_control" style="width:98%;height:120px;"></div>
<div id="div_multi_btn" style="padding-top:5px;">
	<input type="button" value="파일찾기" onclick="NFileUpload.searchFile();" class="NFU_cbtn NFU_cbtn_point" />
	<input type="button" value="선택삭제" onclick="NFileUpload.removeFiles('check');" class="NFU_cbtn NFU_cbtn_g" />
	<input type="button" value="전체삭제" onclick="NFileUpload.removeFiles();" class="NFU_cbtn NFU_cbtn_g" />&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="일반업로드전환" onclick="uploadSelect('normal');" class="NFU_cbtn" />
</div>
<div id="div_normal_btn" style="padding-top:5px;display:none;">
	<input type="button" value="멀티업로드전환" onclick="uploadSelect('multi');" class="NFU_cbtn" />
	<span>※ HTML5 미지원 브라우저일 경우 일반업로드만 사용할 수 있습니다.</span>
</div>
<script type="text/javascript">
//<![CDATA[
var nfu_upload_type = "multi";
NFileUpload.nfuPath = "/nfu";						//config, css 파일위치
NFileUpload.uploadURL = "./nfu_upload.do?eb_pk=${eb_pk}";			//파일업로드 처리URL
NFileUploadNormal.uploadURL = "./nfu_normal_upload.do?eb_pk=${eb_pk}";	//일반파일업로드 처리URL
NFileUpload.useImageView = true;				//이미지보기

NFileUpload.maxFileCount = ${a_upload_len};					//파일개수
NFileUpload.maxFileSize = ${a_nofilesize* 1024 * 1024};		//파일크기
NFileUpload.fileFilter = "${a_nofile}";			//파일종류
NFileUpload.fileFilterType = true;				//파일종류 허용여부

NFileUpload.load();
//]]>
</script>