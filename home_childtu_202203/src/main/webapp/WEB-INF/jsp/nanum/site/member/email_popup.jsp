<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import = "kr.co.nninc.ncms.common.Func" %> 
<%
	String prepage = request.getParameter("prepage") == null ? "/" : request.getParameter("prepage");

	//xss 검증추가(210702)
	prepage = Func.InputValueXSS(prepage);
	prepage = Func.InputValue(prepage);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="language" content="ko" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="format-detection" content="telephone=no" />
<title>투명우산 나눔활동</title>
<script type="text/javascript" src="/nanum/site/common/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_design.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/head_menu.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/swiper.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery.easings.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/TweenMax.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery.loading.js"></script>

<link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/skin01/common/css/all.css" />
<link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/skin01/common/css/sub_design.css" />
<link type="text/css" rel="stylesheet" href="/nanum/site/builder/skin/skin01/common/css/sub_layout.css" />
<link type="text/css" rel="stylesheet" href="/nanum/site/common/css/member.css" />

</head>
<body>
<script type="text/javascript">
$(document).ready(function() {
	$('#btn1').click(function() {
		$("#a_email").val($("#a_email01").val()+"@"+$("#a_email02").val());
		
		if($("#a_name").val() == ""){
			alert("이름을 입력해주세요.");
			$("#a_name").focus();
			return;
		}
		else if($("#a_email01").val() == ""){
			alert("이메일을 입력해주세요.");
			$("#a_email01").focus();
			return;
		}
		else if($("#a_email02").val() == ""){
			alert("이메일을 입력해주세요.");
			$("#a_email02").focus();
			return;
		}
		else if(!$("#email_agree").is(":checked")){
			alert("이메일 정보수집에 동의해주세요.");
			$("#email_agreel").focus();
			return;
		}
		else{
			var params = jQuery("#frm").serialize();
			jQuery.ajax({
				type: 'POST',
				data:params,
				url:"/${BUILDER_DIR}/mail_auth/send.do",
				dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
				beforeSend : function(xhr, opts) {
					//$('body').loading('start');
				},
				success : function(data) {
					if(data.send == "Y"){
						alert("인증번호가 발송되었습니다. \n\n메일을 확인해주세요.");
					}
					$('body').loading('stop');
				},
				error : function(xhr, status, error) {
					$('body').loading('stop');
					alert("에러발생");
				}
			});
		}
	});

	$('#btn2').click(function() {
		$("#a_email").val($("#a_email01").val()+"@"+$("#a_email02").val());
		
		if($("#a_name").val() == ""){
			alert("이름을 입력해주세요.");
			$("#a_name").focus();
			return;
		}
		else if($("#a_email01").val() == ""){
			alert("이메일을 입력해주세요.");
			$("#a_email01").focus();
			return;
		}
		else if($("#a_email02").val() == ""){
			alert("이메일을 입력해주세요.");
			$("#a_email02").focus();
			return;
		}
		else if(!$("#email_agree").is(":checked")){
			alert("이메일 정보수집에 동의해주세요.");
			$("#email_agreel").focus();
			return;
		}
		else if($("#a_random").val() == ""){
			alert("인증번호를 입력해주세요.");
			$("#a_random").focus();
			return;
		}
		else{
			var params = jQuery("#frm").serialize();
			jQuery.ajax({
				type: 'POST',
				data:params,
				url:"/${BUILDER_DIR}/mail_auth/end.do",
				dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
				beforeSend : function(xhr, opts) {
					//$('body').loading('start');
				},
				success : function(data) {
					//$('body').loading('stop');
					if(data.result == "Y"){
						alert("인증이 완료되었습니다.");
						opener.location.href = "<%=prepage%>";
						self.close();
					}else{
						alert("다시 확인해주세요.");
						$("#a_random").val("");
					}
				},
				error : function(xhr, status, error) {
					$('body').loading('stop');
					alert("에러발생");
				}
			});
		}
	});

	$('#btn_close').click(function() {
		opener.location.href = "<%=prepage%>";
		self.close();
	});
	//이메일 입력방식 선택
	$('#selectEmail').change(function(){
		$("#selectEmail option:selected").each(function () {
			if($(this).val()== '1'){ //직접입력일 경우
				 $("#a_email02").val('');                        //값 초기화
				 $("#a_email02").attr("disabled",false); //활성화
			}else{ //직접입력이 아닐경우
				 $("#a_email02").val($(this).text());      //선택값 입력
				 $("#a_email02").attr("disabled",true); //비활성화
			}
		});
	});
});
</script>
<form id="frm" method="post">
	<div class="join_popup">
		<h3 class="tit">이메일 본인인증 받기</h3>
	<table class="table1" style="margin-top:0;">
		<colgroup>
			<col width="20%" />
			<col width="80%" />
		</colgroup>
		<tr>
			<th>이름</th>
			<td class="left"><input type="text" name="a_name" id="a_name" value="" style="height:30px;border:1px solid #d5d5d5;padding:0 5px;" /></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td class="left"><input type="hidden" name="a_email" id="a_email" value="" />
				<input type="text" name="a_email01" id="a_email01" style="width:100px;height:30px;border:1px solid #d5d5d5;padding:0 5px;" /> @
				<input type="text" name="a_email02" id="a_email02" style="width:100px;height:30px;border:1px solid #d5d5d5;padding:0 5px;" />
				<select style="width:100px;height:30px;" name="selectEmail" id="selectEmail">
					<option value="1" selected>직접입력</option>
					<option value="naver.com">naver.com</option>
					<option value="hanmail.net">hanmail.net</option>
					<option value="gmail.com">gmail.com</option>
					<option value="nate.com">nate.com</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2"><input type="checkbox" name="email_agree" id="email_agree" value="1" /><label for="email_agree">이메일 정보 수집 및 제공에 동의 합니다.</label></td>
		</tr>
	</table>
	
	<div class="btn_w" style="padding-top:20px;">
		<input type="button" value="인증번호 전송" id="btn1" class="con_btn green" />
	</div>
	<table class="table1" style="margin-top:25px;">
		<tr>
			<th>인증번호</th>
			<td class="left"><input type="text" name="a_random" id="a_random" value="" style="height:30px;border:1px solid #d5d5d5;padding:0 5px;" /></td>
		</tr>
	</table>
	
	<div class="btn_w">
		<input type="button" value="확인" id="btn2" class="con_btn orange" /> 
		<input type="button" value="닫기" id="btn_close" class="con_btn blue" /> 
	</div>
	</div>
</form>

</body>
</html>