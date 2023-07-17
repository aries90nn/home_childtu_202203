<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<select id="edu_season" name="edu_season" title="기수 선택" class="ta_select">
	<option value="" > -- 기수선택 --</option>

<c:if test="${!empty param.sh_es_libcode }">
	<c:forEach items="${seasonList}" var="season" varStatus="no">
		<option value="${season.es_num}" ${param.edu_season eq season.es_num ? 'selected="selected"' : '' }>${season.es_name}</option>
	</c:forEach>
</c:if>
</select>
<span class="point fs11">* 기수를 선택하면 해당기수의 모든강좌에 대해 중복신청이 제한됩니다.</span>


<script>
$(function(){
	$("#edu_season").customStyle2();
});
</script>