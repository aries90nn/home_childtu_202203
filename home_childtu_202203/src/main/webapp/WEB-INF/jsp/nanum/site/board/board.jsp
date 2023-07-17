<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="a_num" value="${empty param.a_num ? a_num : param.a_num }" />
<c:if test="${!empty a_num }">
	<c:set var="proc_type" value="${empty param.proc_type ? 'list' : param.proc_type }" />
	<c:choose>
		<c:when test="${proc_type eq 'name' }">
			<%--본인인증 페이지로 이동 --%>
			<c:url var="url" value="/site/member/ipin.do">
				<c:param name="prepage" value="${empty param.prepage ? '/' : param.prepage }" />
			</c:url>
			<c:if test="${!empty BUILDER_DIR }">
				<c:set var="url" value="/${BUILDER_DIR }${url }" />
			</c:if>
			<script>location.href="${url}";</script>
		</c:when>
		<c:when test="${proc_type eq 'list'
		 or proc_type eq 'view'
		 or proc_type eq 'write'
		 or proc_type eq 'pwd'
		 or proc_type eq 'login'
		 or proc_type eq 'reply'
		 }">
			<c:set var="url" value="/board/${proc_type }.do" />
			
			<c:catch var="exc">
				<jsp:include flush="false" page="${url }">
					<jsp:param name='proc_type' value='${proc_type }' />
					<jsp:param name='a_num' value='${a_num }' />
				</jsp:include>
			</c:catch>
			<c:if test="${!empty exc }">
				요청하신 페이지 정보가 없습니다.
			</c:if>
		</c:when>
		<c:otherwise>
			<c:set var="url" value="/" />
			<c:if test="${!empty BUILDER_DIR }">
				<c:set var="url" value="/${BUILDER_DIR }${url }" />
			</c:if>
			<script type="text/javascript">
				alert("잘못된 페이지 요청입니다.");
				location.href="${url}";
			</script>
			
		</c:otherwise>
	</c:choose>

</c:if>