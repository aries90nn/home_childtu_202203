<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="member_content" />
</head>

<c:if test="${mode eq '' or mode == null}">
<!-- input 클릭시 label 사라짐 -->
<script type="text/javascript">
	$(function(){
		$(".label_put").each(function(){
			if($(this).val() != "")	$("label[for='"+$(this).attr("id")+"']").hide();
		}).focus(function(){
			$("label[for='"+$(this).attr("id")+"']").hide();
		}).blur(function(){
			if($(this).val() == "")	$("label[for='"+$(this).attr("id")+"']").show();
		});
	});
</script>
<!--내용영역-->
<div id="cont_wrap">
	<div class="member_top">
		<p class="stxt">아이디 및 비밀번호를 분실했을 때<br /> 이름과 이메일로 빠르게 찾으실 수 있습니다.</p>
	</div>
	<div class="member_area" style="background:none;">
		<div class="area">
			<form id="idSearchFrm" name="idSearchFrm" method="post" action="idpw.do" onsubmit="return sendit(this);">
			<div>
				<input type="hidden" name="mode" value="proc">
			</div>
				<div class="area">
						<p><input type="text" id="m_name" name="m_name" class="id_put label_put"><label for="m_name">이름</label></p>
						<p><input type="text" id="m_email" name="m_email" class="pw_put label_put"><label for="m_email">이메일</label></p>
					</div>
					<div class="login_btn">
						<input type="submit" value="찾기" />
					</div>
			</form>
		</div>
	</div>
</div>
<!--//내용영역-->
<script type="text/javascript">
function sendit(){
	var eForm = document.getElementById("idSearchFrm");
	if(!valueChk(eForm.m_name, "이름")){return false;}
	if(!valueChk(eForm.m_email, "회원가입시 입력하신 이메일")){return false;}
	
}
function valueChk(obj, objName){//text필드
	if(obj.value.split(" ").join("") == ""){
		alert(objName+"을(를) 입력하세요");
		try{
			obj.focus();
		}catch(e){
		}
		return false;
	}else{
		return true;
	}
}
</script>
</c:if>
<c:if test="${mode eq 'proc'}">
	<c:set var="urlvalue" value="/member/idpw.do" />
	<div style="padding:25px;width:380px;border:6px solid #efefef;text-align:center;margin:0 auto;">
	<c:if test="${search_id eq ''}">
		${m_name}님의 회원 정보가 없습니다. 다시 확인하세요.<br/>
	</c:if>
	<c:if test="${search_id ne ''}">
		<c:set var="urlvalue" value="/member/login.do" />
		<strong style="color:#000;">${m_name}</strong>님의 아이디는 <strong>${search_id}</strong>
		<br/>비밀번호는 [<strong>${random_pwd}</strong>] 로 초기화 되었습니다.
		<br />로그인 후 반드시 변경하시기 바랍니다.<br/>
	</c:if>
	<p style="margin-top:20px;"><a href="${urlvalue}" class="con_sbtn green">확인</a></p>
	</div>
</c:if>