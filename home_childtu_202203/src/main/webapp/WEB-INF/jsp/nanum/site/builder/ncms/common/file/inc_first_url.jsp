<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--사이트별 첫번째 업무메뉴링크 선언 --%>
<c:set var="firstMenuMap" value="<%=new java.util.HashMap()%>" />
<c:set target="${firstMenuMap }" property="main" value="/main/ncms/edusat/list.do" />
<c:set var="first_menu_url" value="${firstMenuMap[BUILDER_DIR] }" />