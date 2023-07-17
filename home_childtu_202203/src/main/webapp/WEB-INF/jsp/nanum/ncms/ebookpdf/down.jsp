<%@ page contentType="text/html;charset=UTF-8" %><%@ page language="java" import="java.util.*" %><%@ page import="java.io.*"%><%@
page import ="kr.co.nninc.ncms.common.Func" %><%@
page import ="kr.co.nninc.ncms.common.FileUtil" %><%

request.setCharacterEncoding("UTF-8");
String filePath = "";
String fileName		= (String) request.getAttribute("filename");	// 파일명 받기

fileName = fileName.replaceAll("../", "/");
fileName = fileName.replaceAll("/", "");

ServletContext context = pageContext.getServletContext(); // 서블릿 컨텍스트 얻기
String sSavePath = context.getRealPath("data/ebookpdf"); // 상대경로(저장할 폴더)

String strClient  = request.getHeader("User-Agent");
response.reset();
response.setHeader("Content-Disposition", FileUtil.getDisposition(fileName, Func.check_browser(request)));
response.setContentType("application/octet-stream; charset=utf-8");

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