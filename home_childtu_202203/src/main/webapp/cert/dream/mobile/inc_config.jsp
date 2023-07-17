<%@ page  contentType = "text/html;charset=utf-8" %><%@
page import ="java.util.*,java.text.SimpleDateFormat" %><%

response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);


String cfg_cpid = "lib00";
String cfg_password = "wjdqhxla494";
String cfg_servicecode = "01001";
String cfg_certpath = pageContext.getServletContext().getRealPath("/cert/dream/mobile/cert/")+"/kjedu001Cert.der";
String cfg_prikeypath = pageContext.getServletContext().getRealPath("/cert/dream/mobile/cert/")+"/kjedu001Pri.key";


%>