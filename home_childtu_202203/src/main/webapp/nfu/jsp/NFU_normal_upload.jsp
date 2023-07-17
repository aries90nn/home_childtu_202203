<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.io.*"%><%@ page import="com.oreilly.servlet.MultipartRequest"%><%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%><%

String NOWPAGE = request.getRequestURI();
if( request.getQueryString() != null ){
	NOWPAGE += "?"+request.getQueryString();
}

String init = request.getParameter("init");
String uploadchk = "";
String file_names = "";
if(init == null){
	String uploaddir		= application.getRealPath("/nfu/data/");
	int sizeLimit			= 1000 * 1024 * 1024;

	MultipartRequest multi = new MultipartRequest(request, uploaddir, sizeLimit, "UTF-8", new DefaultFileRenamePolicy());

	uploadchk = multi.getParameter("uploadchk");
	String maxFileSize = multi.getParameter("maxFileSize");
	String fileFilter = multi.getParameter("fileFilter");
	String fileFilterType = multi.getParameter("fileFilterType");

	if(uploadchk==null){uploadchk = "";}

	file_names = "";
	if(uploadchk.equals("Y")){
		
		String filecount	= multi.getParameter("filecount");
		if(filecount == null){ filecount = "0";}


		for(int i=1;i<=cInt(filecount);i++){
			String filename = fileUpload( "NFU_add_file_"+i, uploaddir, maxFileSize, fileFilter, fileFilterType, multi );
			if(!filename.equals("")){
				file_names += filename+"|";
			}
		}
	}
}else{
	NOWPAGE = NOWPAGE.replaceAll("&init=1", "");
	NOWPAGE = NOWPAGE.replaceAll("[?]init=1", "?");
}
%>
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
var uploadchk = "<%=uploadchk%>";
var file_names = "<%=file_names%>";
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

<form id="form_1" method="post" action="<%=NOWPAGE%>" enctype="multipart/form-data">
<!-- 아래 파라미터값은 NFileUpload와 동기화됨 -->
<input type="hidden" name="uploadchk" value="Y" />
<input type="hidden" name="filecount" />
<input type="hidden" name="maxFileSize" />
<input type="hidden" name="fileFilter" />
<input type="hidden" name="fileFilterType" />
</form>


</body>
</html>
<%!
public String fileUpload( String filename, String uploaddir, String maxFileSize, String fileFilter, String fileFilterType, MultipartRequest multi ){

	String source_name	= multi.getFilesystemName( filename );
	if(source_name == null){source_name = "";}

	File fileobj = new File( uploaddir+"/"+source_name ); 
	long size			= fileobj.length();

	boolean upload_status	= true;

	String target_name = "";
	if( !source_name.equals("")){


		//파일사이즈 체크
		if(size > cLng(maxFileSize)){
			target_name = "nfu_err3:"+source_name;
			upload_status = false;
		}

		//파일확장자 체크
		fileFilter = fileFilter.replaceAll(" ", "").toLowerCase();
		String[] ext_arr	= fileFilter.split(",");
		boolean status_tmp	= false;

		String[] filename_arr	= source_name.toLowerCase().split("\\.");
		String extension		= filename_arr[ filename_arr.length-1 ];

		for(int i=0;i <= ext_arr.length-1;i++){
			if(extension.equals( ext_arr[i] )){
				status_tmp = true;
			}
		}
		if(fileFilterType.toLowerCase().equals("true")){
			if(status_tmp == false){
				target_name = "nfu_err1:"+extension;
				upload_status = false;
			}
		}else{
			if(status_tmp == true){
				target_name = "nfu_err2:"+extension;
				upload_status = false;
			}
		}
		//파일확장자 체크끝

		if(upload_status){
			target_name = source_name;
		}else{
			FolderDelete( uploaddir+"/"+source_name );
		}
	}
	return target_name;
}

public int cInt(String strValue){

	try{
		return Integer.parseInt(strValue);
	}catch(Exception e){
		return 0;
	}
}

public long cLng(String strValue){

	try{
		return Long.parseLong(strValue);
	}catch(Exception e){
		return 0;
	}
}

public void FolderDelete(String strDir){
	File file = new File(strDir);
	if(file.exists()){
		boolean c = file.delete();
	}
}

%>