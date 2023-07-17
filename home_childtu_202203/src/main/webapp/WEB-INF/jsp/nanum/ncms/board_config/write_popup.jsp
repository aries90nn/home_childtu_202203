<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>관리자페이지 : 게시판수정 </title>
	<jsp:include page="../common/file/head_script.jsp" />
	<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
	<script type="text/javascript" src="/nanum/ncms/common/js/ncms_board_config.js"></script>
</head>

<body id="popup">

<!-- 중간 영역 -->
<div id="content_wrap" style="min-width:0px;">

	<!-- 내용영역 -->
	<div id="contents">

		<h1 class="tit"><span>게시판</span> 수정</h1>
		
		<!-- 내용들어가는곳 -->
		<div id="contents_area">
		
			<c:import url="./inc_write.jsp" charEncoding="UTF-8">
				<c:param name="page_type" value="popup"></c:param>
			</c:import>
			
		</div>
		<!-- 내용들어가는곳 -->

	</div>
	<!-- //내용영역 -->

</div>


</body>
</html>
