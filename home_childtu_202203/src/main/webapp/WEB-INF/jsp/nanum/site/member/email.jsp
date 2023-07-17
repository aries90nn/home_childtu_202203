<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import = "kr.co.nninc.ncms.common.Func" %> 
<html lang="ko">
<!-- 

CREATE TABLE AUTH_EMAIL (
	A_NAME VARCHAR(50),
	A_CHK VARCHAR(2),
	A_EMAIL VARCHAR(200),
	A_RANDOM VARCHAR(50),
	A_WDATE VARCHAR(30)
);
 -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/' : param.prepage }" />
<%
    response.setHeader("Pragma", "no-cache" );
    response.setDateHeader("Expires", 0);
    response.setHeader("Pragma", "no-store");
    response.setHeader("Cache-Control", "no-cache" );
%>
<%
	String prepage = request.getParameter("prepage") == null ? "/" : request.getParameter("prepage");
	
	//xss 검증추가(210702)
	prepage = Func.InputValueXSS(prepage);
	prepage = Func.InputValue(prepage);
%>
<c:set var="prepage" value="${empty param.prepage ? '/' : param.prepage }" />
<script type="text/javascript">
$(document).ready(function(){
  $("#btn_submit").on("click", function(){
    window.open("", "popup_window", "width=800, height=600, scrollbars=no");
    $("#reqForm").submit();
  });
});
</script>

							
							<div class="memberbox ipin">
								<form id="reqForm" method="post" action="email_popup.do" target="popup_window">
									<input type="hidden" name="prepage"  value="<%=prepage%>">
									<div class="certify_w">
										<div class="box">
											<p class="tit icon1">이메일 인증</p>
											<div class="con">
												건전한 인터넷문화 및 상호신뢰 증진 등 본인 확인을 위한 인증이 반드시 필요합니다.<br/>
												이메일 본인 확인을 진행하려면 '이메일 인증'을 클릭해 주세요.
											</div>
											<div class="btn_w">
												<a href="javascript:;" id="btn_submit" class="con_btn orange" title="새창 열림">이메일 인증</a>
											</div>
										</div>
									</div>
								</form>
							</div>


