<%@ page contentType="text/html;charset=UTF-8" %><%@
page import="kr.co.nninc.ncms.edusat.service.EdusatDTO"%><%@
page language="java" import="java.util.*" %><%@
page import="java.io.*"%><%

request.setCharacterEncoding("UTF-8");
String filePath = "";

HashMap<String,String> edusatdto = (HashMap<String,String>) request.getAttribute("edusat");
String fileName		= edusatdto.get("edu_temp3"); // 파일명 받기

fileName = fileName.replaceAll("../", "/");
fileName = fileName.replaceAll("/", "");

String a_tablename	= (String) request.getAttribute("a_tablename");
String fileName2 = new String(fileName.getBytes("EUC-KR"),"8859_1");


ServletContext context = getServletContext(); // 서블릿 컨텍스트 얻기
String sSavePath = context.getRealPath("data/edusat/"); // 상대경로(저장할 폴더)

String strClient  = request.getHeader("User-Agent");
response.reset();
if(strClient.indexOf("MSIE 5.5") != -1) {
	response.setHeader("Content-Disposition", "filename=" + fileName2);
	response.setContentType("doesn/matter;");
}
else{
	response.setHeader("Content-Disposition", "attachment;filename=" + fileName2);
	response.setContentType("application/octet-stream;");
}

filePath = sSavePath +"/"+ fileName;


File file = new File(filePath);
int fileSize = (int)file.length();
response.setContentLength(fileSize);

byte buff[] = new byte[1024];
if (file.isFile()){
	BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
	BufferedOutputStream fout = new BufferedOutputStream(response.getOutputStream());
	int read = 0;
	try{
		while ((read = fin.read(buff)) != -1){
			fout.write(buff, 0, read);
			fout.flush();
		}
		fin.close();
		fout.close();
	}catch (Exception e) {
		System.out.println(e);
	}finally {
		if(fin != null) fin.close();
	}
}

%>