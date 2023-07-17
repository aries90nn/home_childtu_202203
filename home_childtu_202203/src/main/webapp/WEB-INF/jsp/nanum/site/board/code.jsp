<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 분류 -->
<c:if test="${config.a_cate eq 'Y'}">
<fieldset>
<legend>분류검색</legend>

<form id= "frm_code" method='get' action="${path_info }">
<input type="hidden" name="a_num" value="${config.a_num}" />

<ul class="bunru">
<li class="mr5">
	<select id="b_cate_tot" name="v_cate" title="분류 선택" style="vertical-align:middle;height:30px;border:1px solid #dbdbdb;padding-left:8px;">
		<option value="" selected="selected">전체</option>
		<c:forEach items="${codeList}" var="code" varStatus="no">
			<option value="${code.ct_idx}" ${code.ct_idx == param.v_cate ? "selected" : ""}>${code.ct_name}</option>
		</c:forEach>
</select>
<input type="submit" value="확인" style="border:0 none;vertical-align:middle;background:#888;color:#fff;font-size:13px;width:60px;height:30px;cursor:pointer;" onclick="javascript:$('#frm_code').submit();" /></li>
</ul>

</form>
</fieldset>

</c:if>
<!-- //분류 -->