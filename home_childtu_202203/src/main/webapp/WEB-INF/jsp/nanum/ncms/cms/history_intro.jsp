<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>


${history.ct_board_header}

	<c:if test="${history.ct_pagetype == 'T'}">
		${history.ct_content}
	</c:if>
	
	<c:if test="${history.ct_pagetype == 'L'}">
		${history.ct_url}
	</c:if>
	
	<c:if test="${history.ct_pagetype == 'B'}">
		<iframe src="/history${history.ct_menu_url}" style="width:98%;height:480px;padding: 10px 10px"></iframe>
	</c:if>

${history.ct_board_footer}