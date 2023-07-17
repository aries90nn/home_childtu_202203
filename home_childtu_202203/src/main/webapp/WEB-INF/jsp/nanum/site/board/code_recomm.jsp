<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!-- 분류 -->

<fieldset>
<legend>분류검색</legend>

<jsp:useBean id="now" class="java.util.Date" />
<c:set var="nowyear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
<c:set var="nowmonth"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
<c:set var="sh_b_sdate_y" value="${empty param.sh_b_sdate_y ? nowyear : param.sh_b_sdate_y }" />
<c:set var="sh_b_sdate_m" value="${empty param.sh_b_sdate_m ? nowmonth : param.sh_b_sdate_m }" />

<form id= "frm_code" method='get' action="">
<input type="hidden" name="a_num" value="${config.a_num}" />

<ul class="bunru">
<c:if test="${config.a_cate eq 'Y'}">
<li class="admin_select_style mr5">
	<select id="b_cate_tot" name="v_cate" title="분류 선택">
		<option value="" selected="selected">전체</option>
		<c:forEach items="${codeList}" var="code" varStatus="no">
			<option value="${code.ct_idx}" ${code.ct_idx == v_cate ? "selected" : ""}>${code.ct_name}</option>
		</c:forEach>
</select></li>
</c:if>
<li class="admin_select_style mr5">

	<select id="sh_b_sdate_y" name="sh_b_sdate_y" title="년도 선택">
		<c:forEach var="year" begin="2010" end="${nowyear }">
			<option value="${year}" ${sh_b_sdate_y == year ? "selected" : ""}>${year}</option>
		</c:forEach>
</select></li>
<li class="admin_select_style mr5">
	<select id="sh_b_sdate_m" name="sh_b_sdate_m" title="월 선택">
		<c:forEach var="month" begin="1" end="12">
			<c:set var="month_str" value="${month }" />
			<c:if test="${month < 10 }"><c:set var="month_str" value="0${month }" /></c:if>
			<option value="${month_str}" ${sh_b_sdate_m == month_str ? "selected" : ""}>${month_str}</option>
		</c:forEach>
</select></li>
<li><input type="button" value="확인" class="ndls_btn gray" onclick="javascript:$('#frm_code').submit();" /></li>
</ul>

</form>
</fieldset>

<!-- //분류 -->
