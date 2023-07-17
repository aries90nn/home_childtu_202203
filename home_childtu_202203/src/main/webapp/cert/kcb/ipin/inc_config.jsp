<%@
page  contentType = "text/html;charset=utf-8" %><%@
page import ="java.util.*,java.text.SimpleDateFormat" %><%

response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);

String cpg_site_name = "광주광역시교육청통합도서관"; 		// 요청사이트명 
String cfg_site_url = "lib.gen.go.kr";

String cfg_cp_cd = "HBEFOR039041";
String cfg_target = "PROD"; // 테스트="TEST", 운영="PROD"
String cfg_license = pageContext.getServletContext().getRealPath("/cert/kcb/ipin/license/")+ "/" + cfg_cp_cd + "_TIS_01_" + cfg_target + "_AES_license.dat";

%>