<%@ page contentType="text/html;charset=UTF-8" %><%@ page language="java" import="java.util.*" %><%@ page import="java.io.*"%><%

request.setCharacterEncoding("UTF-8");
String filePath = "";
String fileName		= (String) request.getAttribute("filename");	// 파일명 받기

fileName = fileName.replaceAll("../", "/");
fileName = fileName.replaceAll("/", "");

String a_tablename	= (String) request.getAttribute("a_tablename");
String thum	= (String) request.getAttribute("thum");
String fileName2 = new String(fileName.getBytes("EUC-KR"),"8859_1");


ServletContext context = pageContext.getServletContext(); // 서블릿 컨텍스트 얻기
String sSavePath = context.getRealPath("data/board/"+a_tablename); // 상대경로(저장할 폴더)


if(!"".equals(fileName)){ //파일있으면

	if("Y".equals(thum)){

		String fileend = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()).toLowerCase();

		if(fileend.equals("jpg") || fileend.equals("png")){ //이미지 파일이면..									
			filePath = sSavePath +"/thum/" + fileName;
		}else{
			filePath = sSavePath +"/"+ fileName;
		}
		
	}else{
		filePath = sSavePath +"/"+ fileName;
	}

	File file_tmp = new File(filePath);
	// 파일 존재 여부 판단(없으면 기본이미지)
	if (!file_tmp.exists()) {
		fileName = "no.gif";
		filePath = context.getRealPath("nanum/site/board/nninc_photo_mobile/img")+"/"+fileName;
	}

}else{ //파일없음
	fileName = "no.gif";
	filePath = context.getRealPath("nanum/site/board/nninc_photo_mobile/img")+"/"+fileName;
}


File file = new File(filePath);
 
response.setHeader("Content-Type","image/jpeg");
//response.setHeader("Content-Disposition", "attachment;fileName=" + fileName2);
response.setHeader("Content-Transfer-Encoding", "binary;");
response.setHeader("Pragma", "no-cache;");
response.setHeader("Expires", "-1;");

if( file.exists() ) {

	byte b[] = new byte[ ( int )file.length() ];

	BufferedInputStream fin = new BufferedInputStream( new FileInputStream( file ) );

	out.clear();
	pageContext.pushBody();

	BufferedOutputStream outs = new BufferedOutputStream( response.getOutputStream() );

	try{
		int read = 0;
		while( ( read = fin.read( b ) ) != -1 ) {
			outs.write( b, 0, read );
		}
		outs.close();
	}catch (Exception e) {
		out.println(e);
	}finally {
		if(outs != null) outs.close();
		if(fin != null) fin.close();
	}
}
%>