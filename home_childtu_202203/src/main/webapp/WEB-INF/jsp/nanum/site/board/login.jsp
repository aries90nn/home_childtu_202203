<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="prepage" value="${empty param.prepage ? '?proc_type=list' : param.prepage}" />

<!-- 스크립트 -->
<jsp:include page="./board_script.jsp" />
<!-- //스크립트 -->

<!-- 쓰기 -->
	
	<script type="text/javascript">
	function on_show() {
		document.getElementById('frm_mn').m_id.focus();
		return;
	}

	//저장
	function w_chk(){
		if (CheckSpaces(document.getElementById('frm_mn').m_id, '아이디')) { return false; }
		else if (CheckSpaces(document.getElementById('frm_mn').m_pwd, '비밀번호')) { return false; }
	}

	</script>

	
	<form id="frm_mn" method="post" action="/board/loginOk.do" onsubmit="return w_chk()">
	<div>
	<input type="hidden" id="prepage" name="prepage" value="${prepage }" />
	<input type="hidden" id="a_num" name="a_num" value="${config.a_num}" />
	<input type="hidden" name="board_token" value="${board_token}">
	</div>


	<div class="login_check">
		로그인 정보를 입력해주세요.<br /><br />
		
		<ul>
			<li class="id"><span class="text">아이디 :</span> <input type="text" size="15" title="아이디를 입력하세요" id="m_id" name="m_id" class="board_input" style="width:120px;vertical-align:middle;" onfocus="focus_on1(this);" onblur="focus_off1(this);"  /></li>
		</ul>
		<ul>
			<li><span class="text">비밀번호 :</span> <input type="password" size="15" title="비밀번호를 입력하세요" id="m_pwd" name="m_pwd" class="board_input" style="width:120px;vertical-align:middle;" onfocus="focus_on1(this);" onblur="focus_off1(this);"  /><input type="image" src="/nanum/site/board/nninc_simple/img/command_ok.gif" width="34" height="20" alt="확인" style="vertical-align:middle;padding-left:3px;" /></li>
		</ul>

	</div>


	<div class="login_check_button">
		<span><a href="${prepage }"><img src="/nanum/site/board/nninc_simple/img/back_bt.gif" width="82" height="23" alt="돌아가기" /></a></span>
	</div>

	</form>


<!-- //쓰기 -->