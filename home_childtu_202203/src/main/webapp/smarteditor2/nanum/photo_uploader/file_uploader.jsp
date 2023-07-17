<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>

<%!
public String urlEncode(String strUrl){
	try{
		String strEncode = java.net.URLEncoder.encode(strUrl,"UTF-8");
		return strEncode;
	}catch(Exception e){
		return "";
	}
}
public String hangul_img(String strvalue) {
	if(strvalue != null){
		strvalue = urlEncode(strvalue);
		strvalue = strvalue.replaceAll("\\+"," ");
		return strvalue;
	}else{
		return "";
	}
}
public void FolderCreate(String strDir){
	File file = new File(strDir);
	if(!file.exists()){
		boolean c = file.mkdirs();
	}
}
%>

<%
String	savePath_dir	= "/data/popup"; 
int		sizeLimit		= 10 * 1024 * 1024 ; 
String	savePath		= application.getRealPath("") + savePath_dir; 

FolderCreate(savePath);

MultipartRequest multi=new MultipartRequest(request, savePath, sizeLimit,"utf-8", new DefaultFileRenamePolicy());

String url = multi.getParameter("callback")+"?callback_func="+multi.getParameter("callback_func");

String filename = multi.getFilesystemName("Filedata");




boolean extchk = false;
String fileExt = filename.substring(filename.lastIndexOf(".")+1, filename.length());
String[] ext_arr = {"jpg","gif","png","bmp"};	
for(int i=0; i <= ext_arr.length-1;i++){
	if(ext_arr[i].toLowerCase().equals(fileExt.toLowerCase())){
		extchk = true;
		break;
	}
}

if(!extchk){	
	if (!filename.equals("")) { 
		File file = new File(savePath + filename);
		if(file.exists())	file.delete();
	}
	url = url + "&errstr=error";

}else{
	
	filename = hangul_img(filename);
//	filename = new String (filename.getBytes("UTF-8"),"8859_1");
	
	url = url + "&bNewLine=true";
	url = url + "&sFileName="+filename;
	url = url + "&sFileURL=/data/popup/"+filename;
}

//out.print(url);
//if(true)return;
response.sendRedirect( url);

%>