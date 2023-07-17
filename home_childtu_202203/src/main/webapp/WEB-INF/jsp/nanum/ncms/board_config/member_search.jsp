<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/all.css" />


<script type="text/javascript">
function return_value(m_id) {
	var a_ad_cms_id = window.parent.document.getElementById("frm").a_ad_cms_id.value

	if(a_ad_cms_id == ""){
		window.parent.document.getElementById("frm").a_ad_cms_id.value	=  m_id + ",";
	}else{
		window.parent.document.getElementById("frm").a_ad_cms_id.value	= a_ad_cms_id + m_id + ",";
	}
}
</script>


<body>
	
	<c:forEach items="${memberList}" var="member" varStatus="no">
		<a href="javascript:return_value('${member.m_id}')">${member.m_name} (${member.m_id})</a><br><img width="0" height="6"><br>
	</c:forEach>
	<c:if test="${fn:length(memberList) == 0}">
	<font color=#008E8B>등록된 데이터가 없습니다.</font>
	</c:if>
	
	
</body>
</html>
