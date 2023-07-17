<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="language" content="ko" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>나눔아이앤씨 관리자가이드</title>
<link rel="stylesheet" type="text/css" href="/nanum/ncms/manual/common/css/manual.css" />
<script type="text/javascript" src="/nanum/ncms/manual/common/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
//메뉴링크시 필요한것
$(function(){
var href_val = location.href;
if(href_val.indexOf("?") != -1){ //?문자열이 포함되어있을경우
	var temp=href_val.split("?"); 
	var data=temp[1].split("&");
	var url=data[0];
	var url2=data[1]; 
	
	//어린이병원인지 체크(170719)
	if(href_val.indexOf("child=Y") != -1){
		url2 = url2 + "?child=Y";
	}

	//document.getElementById('sidemenu').src = url;
	$('#m_side').attr('src', url);
	$('#m_cont').attr('src', url2);
	//document.getElementById('content').src = "http://www.naver.com";
}
});
</script>
</head>
<body>
<div id="wrapper">
	<!--상단영역-->
	<div id="head">
		<div class="area">
			<h1><a href="/ncms/manual/index.do"><img src="/nanum/ncms/manual/img/common/logo.png" alt="NANUM Administrator's Guide" /></a></h1>
			<div id="menu">
				<ul>
					<li><a href="index.do?/ncms/manual/common/file/head_01sub.do&/ncms/manual/content/01sub/01_01.do"><img src="/nanum/ncms/manual/img/common/menu01.png" onmouseover="this.src='/nanum/ncms/manual/img/common/menu01_on.png'" onmouseout="this.src='/nanum/ncms/manual/img/common/menu01.png'" alt="게시판관리" /></a></li>
					<li><a href="index.do?/ncms/manual/common/file/head_02sub.do&/ncms/manual/content/02sub/01_01.do"><img src="/nanum/ncms/manual/img/common/menu02.png" onmouseover="this.src='/nanum/ncms/manual/img/common/menu02_on.png'" onmouseout="this.src='/nanum/ncms/manual/img/common/menu02.png'" alt="회원/권한관리" /></a></li>
					<li><a href="index.do?/ncms/manual/common/file/head_03sub.do&/ncms/manual/content/03sub/01_01.do"><img src="/nanum/ncms/manual/img/common/menu03.png" onmouseover="this.src='/nanum/ncms/manual/img/common/menu03_on.png'" onmouseout="this.src='/nanum/ncms/manual/img/common/menu03.png'" alt="부가기능" /></a></li>
					<li><a href="index.do?/ncms/manual/common/file/head_04sub.do&/ncms/manual/content/04sub/01_01.do"><img src="/nanum/ncms/manual/img/common/menu04.png" onmouseover="this.src='/nanum/ncms/manual/img/common/menu04_on.png'" onmouseout="this.src='/nanum/ncms/manual/img/common/menu04.png'" alt="통계자료" /></a></li>
					<li><a href="index.do?/ncms/manual/common/file/head_05sub.do&/ncms/manual/content/05sub/01_01.do"><img src="/nanum/ncms/manual/img/common/menu05.png" onmouseover="this.src='/nanum/ncms/manual/img/common/menu05_on.png'" onmouseout="this.src='/nanum/ncms/manual/img/common/menu05.png'" alt="메뉴/페이지" /></a></li>
				</ul>
			</div>
		</div>
	</div>
	<!--//상단영역-->
	
	<!--중간영역-->
	<div id="container">
		<!--서브메뉴-->
		<iframe src="/ncms/manual/common/file/head_01sub.do" frameborder="0" id="m_side" name="m_side" width=100% height=59px marginWidth=0 scrolling="no" topmargin="0" leftmargin="0"></iframe>
		<!--//서브메뉴-->
		<!--내용영역-->
		<div id="contents">
			<iframe src="/ncms/manual/content/01sub/01_01.do" frameborder="0" id="m_cont" name="m_cont" width=100% height=100% marginWidth=0 scrolling="yes" topmargin="0" leftmargin="0"></iframe>
		</div>
		<!--//내용영역-->
		<div class="quick">
			<p class="tip"><a href="/ncms/manual/index.do"><img src="/nanum/ncms/manual/img/common/tip.gif" alt="" /></a></p>
			<p class="top"><a href="#">TOP</a></p>
		</div>
	</div>
	<!--//중간영역-->

	<!--하단영역-->
	<div id="foot">
		(c) NANUM I&C. ALL RIGHTS RESERVED.
	</div>
	<!--//하단영역-->
</div>
</body>
</html>
