<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_board_config.js"></script>

<h1 class="tit">${param.a_num == null ? '신규 게시판 생성' : '게시판 수정'}</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">
	<c:import url="./inc_write.jsp" charEncoding="UTF-8"/>
</div>
<!-- 내용들어가는곳 -->