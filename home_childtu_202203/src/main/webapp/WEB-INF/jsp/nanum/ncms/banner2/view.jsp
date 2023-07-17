<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>${banner2.b_l_subject}</title>
	<link rel="Stylesheet" type="text/css" href="/ncms/common/css/sub_layout.css" />
	<link rel="Stylesheet" type="text/css" href="/ncms/common/css/all.css" />
	<script type="text/javascript" src="/common/js/common_design.js"></script>

	<script type="text/javascript" src="/common/js/fusioncharts.js"></script>
	<script type="text/javascript" src="/common/js/common_dev.js"></script>
	<script type="text/javascript">
		function resizePop(){
			imgW = document.documentElement.scrollWidth + 10;
			imgH = document.documentElement.scrollHeight + 60;
			<c:if test="${banner2.scrollbars eq 'Yes'}">
			imgW = imgW + 20;
			</c:if>
			window.resizeTo(imgW , imgH);
		}

		
	</script>

</head>


<body>

<div>


	<div>
		${banner2.content}
	</div>
	
	
	<div class="popup">
		<div class="popup_right">
			<ul>
			<li class="button pr5"><input type="button" value="닫기" onclick="self.close();" class="ct_bt01" /></li>
			</ul>
		</div>
	</div>

</div>





</body>
</html>
<c:if test="${banner2.w_width eq '0' and banner2.w_height eq '0'}">
<script type="text/javascript">
	window.onload = function(){
	 resizePop();
	}
</script>
</c:if>