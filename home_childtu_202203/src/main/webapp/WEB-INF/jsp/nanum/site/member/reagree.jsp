<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="prepage" value="${param.prepage }" />
<c:if test="${empty prepage }">
	<c:if test="${!empty BUILDER_DIR}">
		<c:set var="prepage" value="/${BUILDER_DIR }/" />
	</c:if>
</c:if>


<script type="text/javascript">
$(function(){
	$("#agree_all").click(function(){
		var checked = $(this).prop("checked");
		$("div.join_agree input[type='checkbox']").each(function(){
			$(this).attr("checked", checked);
		});
	});
	
});
function agreeOk(){
	if(!$("#agree1").prop("checked")){
		alert("광주광역시 교육청 통합도서관 이용약관에 동의하셔야 서비스이용이 가능합니다.");
		$("#agree1").focus();
		return false;
	}
	if(!$("#agree2").prop("checked")){
		alert("개인정보의 수집 및 이용에 동의하셔야 서비스이용이 가능합니다.");
		$("#agree2").focus();
		return false;
	}
	if(!$("#agree3").prop("checked")){
		alert("개인정보의 공동이용에 동의하셔야 서비스이용이 가능합니다.");
		$("#agree3").focus();
		return false;
	}
}
</script>

							<div class="join_agree">
								<!-- . -->
								<div class="area">
									<h3 class="tit">홈페이지이용약관</h3>
									<p class="check"><input type="checkbox" name="agree1" id="agree1" /> <label for="agree1">동의합니다.</label></p>
									<textarea class="txt_box"><jsp:include page="./inc_agree1.jsp" /></textarea>
								</div>
								<!-- //. -->
								<!-- . -->
								<div class="area">
									<h3 class="tit">통합회원가입약관</h3>
									<p class="check"><input type="checkbox" name="agree2" id="agree2" /> <label for="agree2">동의합니다.</label></p>
									<textarea class="txt_box"><jsp:include page="./inc_agree2.jsp" /></textarea>
								</div>
								<!-- //. -->
								<!-- . -->
								<div class="area">
									<h3 class="tit">개인정보 수집 및 이용</h3>
									<p class="check"><input type="checkbox" name="agree3" id="agree3" /> <label for="agree3">동의합니다.</label></p>
									<textarea class="txt_box"><jsp:include page="./inc_agree3.jsp" /></textarea>
								</div>
								<!-- //. -->
								<!-- . -->
								<div class="area">
									<h3 class="tit">개인정보 제3자 제공</h3>
									<p class="check"><input type="checkbox" name="agree4" id="agree4" /> <label for="agree4">동의합니다.</label></p>
									<textarea class="txt_box"><jsp:include page="./inc_agree4.jsp" /></textarea>
								</div>
								<!-- //. -->

								<div class="allagree"><input type="checkbox" name="agree_all" id="agree_all" /> <label for="agree_all">전체 동의합니다.</label></div>
								
								<c:url var="reagree_url" value="/ndls/memberService/reAgreeProc.do">
									<c:param name="prepage" value="${prepage }" />
								</c:url>
								<div class="btn_w"><a href="${reagree_url }" onclick="return agreeOk();" class="ndls_btn">재동의</a>
								<a href="${prepage }" class="cancel_btn">취소</a></div>

							</div>