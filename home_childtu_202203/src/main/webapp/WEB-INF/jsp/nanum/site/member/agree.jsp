<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<script type="text/javascript">
$(function(){
	$("#agree_all").click(function(){
		var checked = $(this).prop("checked");
		$("div.join_agree input[type='checkbox']").each(function(){
			$(this).prop("checked", checked);
		});
	});
	
});
function agreeOk(){
	if(!$("#agree1").prop("checked")){
		alert("투명우산 나눔활동 이용약관에 동의하셔야 서비스이용이 가능합니다.");
		$("#agree1").focus();
		return false;
	}
	if(!$("#agree2").prop("checked")){
		alert("개인정보의 수집 및 이용에 동의하셔야 서비스이용이 가능합니다.");
		$("#agree2").focus();
		return false;
	}
	/*
	if(!$("#agree3").prop("checked")){
		alert("개인정보의 공동이용에 동의하셔야 서비스이용이 가능합니다.");
		$("#agree3").focus();
		return false;
	}
	*/
}
</script>

							<div class="join_agree">
								<!-- 이용약관 동의-->
								<div class="area">
									<h3 class="tit">이용약관 동의</h3>
									<p class="check"><input type="checkbox" name="agree1" id="agree1" /> <label for="agree1">동의합니다.</label></p>
									<textarea class="txt_box"><jsp:include page="./inc_agree1.jsp" /></textarea>
								</div>
								<!-- //이용약관 동의-->
								<!--개인정보 수집 및 이용에 대한 안내-->
								<div class="area">
									<h3 class="tit">개인정보 수집 및 이용에 대한 안내</h3>
									<p class="check"><input type="checkbox" name="agree2" id="agree2" /> <label for="agree2">동의합니다.</label></p>
									<textarea class="txt_box"><jsp:include page="./inc_agree2.jsp" /></textarea>
								</div>
								<!-- //개인정보 수집 및 이용에 대한 안내-->
								

								<div class="allagree"><input type="checkbox" name="agree_all" id="agree_all" /> <label for="agree_all">전체 동의합니다.</label></div>
								<div class="btn_w"><a href="/${BUILDER_DIR }/site/member/regist.do" onclick="return agreeOk();" class="ndls_btn">회원가입</a>
								<a href="/main/contents.do?idx=745" class="cancel_btn">이전으로</a></div>

							</div>