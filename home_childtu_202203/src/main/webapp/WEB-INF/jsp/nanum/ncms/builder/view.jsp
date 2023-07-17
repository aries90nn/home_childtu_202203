<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>${popup.subject}</title>
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/sub_layout.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/all.css" />

	<script type="text/javascript" src="/nanum/site/common/js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_design.js"></script>

	<c:set var="ck_val" value="${popup.ck_val eq '' or popup.ck_val == null ? '1':popup.ck_val}" />
	<script type="text/javascript">
		function setCookie(){
			var expire = new Date();
			expire.setDate(expire.getDate() + ${ck_val});
			document.cookie = "pop${popup.idx}=${ck_val}; expires=" + expire.toGMTString()+ "; path=/";
		}

		function closeWin(){

			if (document.getElementById('next_close').checked == true ){
				setCookie();
			}

			self.close();
		}

		function resizePop(){
			imgW = document.documentElement.scrollWidth + 10;
			imgH = document.documentElement.scrollHeight + 60;
			
		<c:if test= "${popup.scrollbars eq 'Yes'}">
			imgW = imgW + 20;
		</c:if>
		
			window.resizeTo(imgW , imgH);
		}

		
	</script>

</head>


<body>

<div>

	<!-- -->
	<div>
		${popup.content}
	</div>
	
	<c:if test="${popup.ck_chk eq 'Y'}">
	<div class="popup">
		<div class="popup_right">
			<ul>
			<li><input type="checkbox" id="next_close" value="Y" /></li>
			<li class="pt5 pr7">다음에 창 열지 않기</li>
			<li class="button pr5"><input type="button" value="닫기" onclick="closeWin();" class="ct_bt01" /></li>
			</ul>
		</div>
	</div>
	</c:if>


</div>





</body>
</html>

<c:if test="${popup.w_width eq '0' or popup.w_height eq '0'}">
<script type="text/javascript">
	window.onload = function(){
	 resizePop();
	}
</script>
</c:if>