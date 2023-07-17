<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="kr.co.nninc.ncms.common.CommonConfig" %>

<c:set var="prepage" value="${empty param.prepage ? '?proc_type=list' : param.prepage}" />
<c:set var="gopage" value="${empty param.gopage ? '?proc_type=list' : param.gopage}" />

<!-- 스크립트 -->
<jsp:include page="./board_script.jsp" />
<!-- //스크립트 -->

<link rel="stylesheet" type="text/css" href="/nanum/site/board/nninc_simple/css/common.css" />

<!-- 쓰기 -->
<script type="text/javascript">
function pwd_chk(){
	if(document.getElementById("frm").b_pwd_chk.value==""){
		alert('비밀번호를 입력해주세요.');
		document.getElementById("frm").b_pwd_chk.focus();
		return false;		
	}
}
</script>
	
<form id="frm" method="post" action="/board/pwdOk.do" onsubmit="return pwd_chk();">
<div>
<input type="hidden" name="a_num" value="${a_num}">
<input type="hidden" name="b_num" value="${b_num}">
<input type="hidden" name="prepage" value="${prepage}">
<input type="hidden" name="gopage" value="${gopage}">
<input type="hidden" name="board_token" value="${board_token}">
<input type="hidden" name="pwd_type" value="${param.pwd_type}">

</div>

	<div class="pwd_check">
		비밀번호를 입력해주세요.<br/><br/>
		<ul>
			<li><label for="c_pwd" class="text">비밀번호 :</label> <input type="password" size="15" title="비밀번호를 입력하세요" id="c_pwd" name="b_pwd_chk" class="board_input" style="vertical-align:middle;" onfocus="focus_on1(this);" onblur="focus_off1(this);" /><input type="image" src="/nanum/site/board/nninc_simple/img/command_ok.gif" width="34" height="20" alt="확인" style="vertical-align:middle;padding-left:3px;" /></li>
		</ul>
	</div>

	<div class="pwd_check_button">
		<span><a href="${prepage}"class="cbtn cbtn_g">뒤로가기</a></span>
	</div>

</form>


<!-- //쓰기 -->