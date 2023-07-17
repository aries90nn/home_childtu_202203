<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<script type="text/javascript">
$(function(){
	$("#preview").html(window.opener.document.frm.ct_content.value);
});
</script>

<p id="preview"></p>