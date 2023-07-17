<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
${siteinfo.sc_header_meta}
<script type="text/javascript" src="/nanum/site/common/js/jquery-3.1.1.min.js"></script>
</head>
<body>

<script type="text/javascript">
	if(confirm("${msg}")){
		location.href = "${url}";
	}else{
		location.href = "${url2}";
	}
</script>

</body>
</html>