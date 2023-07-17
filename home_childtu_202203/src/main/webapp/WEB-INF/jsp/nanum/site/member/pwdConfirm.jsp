<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<link rel="stylesheet" type="text/css" href="/nanum/site/member/css/common.css" />
<script type="text/javascript" src="/nanum/site/member/js/common.js"></script>
<script type="text/javascript">

function w_chk(eForm){
	if( CheckSpaces(eForm.ps_value, '비밀번호') ) { return false; }
}
</script>

<div id="member_service">
	<p class="tar mb10">사용중인 비밀번호를 입력하세요.</p>


	<form id="frm" method="post" action="${DOMAIN_HTTPS}${action_url }" onsubmit="return w_chk(this)">
	<div>
	<input type="hidden" name="prepage" value="${nowPage }" />
	</div>
	
	<div class="bwrite join_write">
		<div class="box w100">
			<ul>

				<li>
					<dl>
						<dt><span class="red">*</span> <label for="user_id">아이디</label></dt>
						<dd class="pw_wrap">
							${sessionScope.ss_m_id }
						</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt><span class="red">*</span> <label for="ps_value">비밀번호</label></dt>
						<dd class="pw_wrap">
							<input type="password" id="ps_value" name="ps_value" maxlength="20" placeholder="사용중인 비밀번호를 입력하세요." class="member_input" />
						</dd>
					</dl>
				</li>

			</ul>
		</div>
	</div>
	
	<p class="btn_w">
		<input type="submit" value="확인" class="member_btn orange" />
		<a href="/${BUILDER_DIR }/" class="member_btn gray">취소</a>
	</p>
	</form>
</div>