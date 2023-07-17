<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="kr.co.nninc.ncms.common.CommonConfig" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<!-- 스크립트 -->
<jsp:include page="./board_script.jsp" />
<!-- //스크립트 -->

<!-- 쓰기 -->
	<form id="frm" method="post" action="/board/a_pwdOk.do?a_num=${config.a_num}&rtn_url=${param.rtn_url}" onsubmit="return pwd_chk();">
	<div>
	</div>

	<div class="pwd_check">
		비밀번호를 입력해주세요.<br /><br />
		<ul>
			<li><span class="text">비밀번호 :</span> <input type="password" size="15" title="비밀번호를 입력하세요" id="c_pwd" name="b_pwd_chk" class="board_input" style="vertical-align:middle;" onfocus="focus_on1(this);" onblur="focus_off1(this);" />
					<input type="image" src="/nanum/site/board/nninc_simple/img/command_ok.gif" width="34" height="20" alt="확인" style="vertical-align:middle;padding-left:3px;" />
			</li>
		</ul>
	</div>


	<div class="pwd_check_button">
		<span><a href="list.do?a_num=${config.a_num}&${page_info}"><img src="/nanum/site/board/nninc_simple/img/back_bt.gif" width="82" height="23" alt="돌아가기" /></a></span>
	</div>

	</form>
<!-- //쓰기 -->