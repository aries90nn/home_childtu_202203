<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import ="java.util.*" %>
<%@ page import="org.springframework.web.context.*,org.springframework.web.context.support.*"%>
<%@page import="org.apache.ibatis.session.SqlSessionFactory"%>
<%@page import="kr.co.nninc.ncms.common.service.impl.ExtendDAO"%>
<%
//ExtendDAO 생성
ServletContext ctx = pageContext.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
SqlSessionFactory sqlSession = (SqlSessionFactory)wac.getBean("sqlSession");
ExtendDAO exdao = new ExtendDAO();
exdao.setSqlSessionFactory(sqlSession);

String a_num = exdao.filter(request.getParameter("a_num"));
String a_bbsname = exdao.selectQueryColumn("a_bbsname", "board_config", "where a_num = '"+a_num+"'");
request.setAttribute("a_bbsname", a_bbsname);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<style>
.board_total_right input.search_input {height:15px !important;width: auto;}

</style>

<h1 class="tit">${a_bbsname}</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">
	<jsp:include flush='false' page='/board/board.do'></jsp:include>
</div>
<!-- 내용들어가는곳 -->