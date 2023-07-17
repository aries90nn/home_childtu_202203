<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>iframe upload</title>
<style>
/*파일객체 css 클래스*/
.inputfile{width:100%;margin-bottom:5px;}
</style>
<script type="text/javascript">
//<![CDATA[
var uploadchk = "${uploadchk}";
var file_names = "${file_names}";
NFileUploadNormal = parent.NFileUploadNormal;

onload = function(){
	if(uploadchk == "Y"){
		NFileUploadNormal.uploadResult( file_names );		//업로드결과 처리
	}
	NFileUploadNormal.reset();				//파일객체 생성
}

//]]>
</script>
<body>
<form id="form_1" method="post" action="nfu_normal_upload_ok.do" enctype="multipart/form-data">
<!-- 아래 파라미터값은 NFileUpload와 동기화됨 -->
<input type="hidden" name="uploadchk" value="Y" />
<input type="hidden" name="filecount" />
<input type="hidden" name="maxFileSize" />
<input type="hidden" name="fileFilter" />
<input type="hidden" name="fileFilterType" />
<input type="hidden" name="ct_menu_folder" value="${ct_menu_folder}" />
</form>


</body>
</html>