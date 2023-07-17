<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script type="text/javascript">
$(function() {
	$("#tree").treeview({
		collapsed: false,
		animated: "medium",
		control:"#sidetreecontrol",
		persist: "location"
	});
})

function memberGo(dcode){
	location.href="write.do?codeno="+dcode;
}

function settingGo(dcode){
	location.href="write.do?ct_idx="+dcode;
}
</script>
<div id="sidetree">
	<!-- <div class="treeheader">&nbsp;</div> -->

	<div id="sidetreecontrol" style="padding:5px 0 8px; 0;"><a href="?#">전체 닫기</a> | <a href="?#">전체 열기</a></div>
	<ul id="tree">
		<ul>
<c:forEach items="${buseoList}" var="buseo1" varStatus="no">
	<c:if test="${buseo1.ct_depth eq '1'}">
		<li>
		<c:set var="urlGo" value="javascript:memberGo('${buseo1.ct_codeno}')" />
		<c:if test="${sel_type eq 's'}">
			<c:set var="urlGo" value="javascript:settingGo('${buseo1.ct_idx}')" />
		</c:if>
		<a href="${urlGo}"><strong>${buseo1.ct_name}</strong></a>
		
		<c:forEach items="${buseo1.list}" var="buseo2" varStatus="no2">
			<c:set var="urlGo" value="javascript:memberGo('${buseo2.ct_codeno}')" />
			<c:if test="${sel_type eq 's'}">
				<c:set var="urlGo" value="javascript:settingGo('${buseo2.ct_idx}')" />
			</c:if>
		<ul>
			<li>
				<a href="${urlGo}">${buseo2.ct_name}</a>
				<c:forEach items="${buseo2.list}" var="buseo3" varStatus="no3">
					<c:set var="urlGo" value="javascript:memberGo('${buseo3.ct_codeno}')" />
					<c:if test="${sel_type eq 's'}">
						<c:set var="urlGo" value="javascript:settingGo('${buseo3.ct_idx}')" />
					</c:if>
					
					<ul>
						<li><a href="${urlGo}">${buseo3.ct_name}</a></li>
					</ul>
				</c:forEach>
				</li>
			</ul>
		</c:forEach>
		</li>
	</c:if>
</c:forEach>
	</ul>
</div>











