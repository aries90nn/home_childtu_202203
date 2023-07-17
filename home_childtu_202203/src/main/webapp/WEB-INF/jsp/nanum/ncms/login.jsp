<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
//String location = "/nninc/index.jsp";
//if( !security_mobile() ){ return; }
//if( !security_ip() ){ return; }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />

	<meta name="language" content="ko" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title></title>
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/all.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/login.css" />
	<link href="http://fonts.googleapis.com/css?family=Armata" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="/nanum/site/common/js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/jquery-cookie.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/jquery-rightClick.js"></script>
	
	<script type="text/javascript" src="/nanum/site/common/js/common_design.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
	<script type="text/javascript" src="/nanum/ncms/common/js/design_default.js"></script>

<script type="text/javascript">

window.onload = function () {

	// userid 쿠키에서 id 값을 가져온다.
	var coo_mid = getCookie("userid");

	with(document.getElementById("reg")){

		if(coo_mid!=""){
			m_id.value = coo_mid;
			fn_set_saveid(true);

			$("#m_id").css({background:"url(/nanum/ncms/img/login/idpw_line_on.gif) no-repeat bottom left"});
			$("#m_id").prev("label").hide();

			m_pwd.focus();
		}else{
			m_id.focus();
		}
	}
}



function fn_saveid(){
	if($("#imgSaveid").attr("src")=="/nanum/ncms/img/login/check_box_on.gif"){
		fn_set_saveid(false);
	}else{
		fn_set_saveid(true);
	}
}

function fn_set_saveid(flag){
	if(flag){
		$("#imgSaveid").attr("src","/nanum/ncms/img/login/check_box_on.gif");
		$("#imgSaveid").attr("data-yn","Y");		
	}else{
		$("#imgSaveid").attr("src","/nanum/ncms/img/login/check_box_off.gif");
		$("#imgSaveid").attr("data-yn","N");
	}
}

//저장
function w_chk(){

	if (CheckSpaces(document.getElementById('reg').m_id, '아이디')) { return false; }
	else if (CheckSpaces(document.getElementById('reg').m_pwd, '비밀번호')) { return false; }
	//else {document.getElementById('frm').submit(); }

	// 로그인 정보 저장 체크 확인하여 진행
	if($("#imgSaveid").attr("data-yn")=="Y") saveLogin(document.getElementById('reg').m_id.value);
	else  saveLogin("");
	return true;

}


// 쿠키에 로그인 정보 저장
function saveLogin(id){
	if(id != ""){
		// userid 쿠키에 id 값을 300일간 저장
		setCookie("userid", id, 300);
	}else{
		// userid 쿠키 삭제
		setCookie("userid", id, -1);
	}
}



//검색어입력
$(function(){
	$(".idpw_input").focus(function(){
		$(this).css({background:"url(/nanum/ncms/img/login/idpw_line_on.gif) no-repeat bottom left"});
		$(this).prev("label").hide();
	});
	$(".idpw_input").blur(function(){
		if($(this).val() == ""){
			$(this).css({background:"url(/nanum/ncms/img/login/idpw_line.gif) no-repeat bottom left"});
			$(this).prev("label").show();
		}
	});
});


//아이콘
function viewList(a){
	for(var i=1;i<7;i++){
		obj = document.getElementById("hlist"+i);
		obj2 = document.getElementById("icon"+i);

		if(a==i){
			obj.style.display = "block";
			//obj2.setAttribute("src","/nanum/ncms/img/casual/top_icon0"+i+".png");
		}else{
			obj.style.display = "none";
			//obj2.setAttribute("src","/img/main/notice_tit"+i+".gif");
		}
	}
}

</script>

<!--[if lt IE 9]>
<script src="/nanum/nninc/common/js/html5.js"></script>
<![endif]-->
<!--[if lte IE 7]>
<script src="/nanum/nninc/common/js/IE8.js"></script>
<![endif]-->




</head>
<body>

<div id="login_wrap">
	<h1>NCMS<br/> v1.0</h1>
	
	<div id="login_area">
		<h2>Your best e-service partner nanum i&c<br/><span>NCMS v1.0</span></h2>
		<div class="icons">
			<ul>
				<li><img src="/nanum/ncms/img/casual/top_icon01.png" class="png24" alt="기본환경설정 아이콘" /></li>
				<li><img src="/nanum/ncms/img/casual/top_icon02.png" class="png24" alt="게시판관리 아이콘" /></li>
				<li><img src="/nanum/ncms/img/casual/top_icon03.png" class="png24" alt="회원/권한관리 아이콘" /></li>
				<li><img src="/nanum/ncms/img/casual/top_icon04.png" class="png24" alt="부가기능 아이콘" /></li>
				<li><img src="/nanum/ncms/img/casual/top_icon05.png" class="png24" alt="통계자료 아이콘" /></li>
				<li class="last"><img src="/nanum/ncms/img/casual/top_icon06.png" class="png24" alt="메뉴관리 아이콘" /></li>
			</ul>
		</div>

		<!-- 로그인폼 -->
		<form id="reg" method="post" action="${DOMAIN_HTTPS}/ncms/loginOk.do" onsubmit="return w_chk()">
		<div class="form">
			<ul>
				<li class="idpw"><label for="m_id">아이디</label><input title="아이디 입력" name="m_id" id="m_id" type="text" value="" class="idpw_input" maxlength="30" /></li>
				<li class="idpw"><label for="m_pw">패스워드</label><input title="패스워드 입력" name="m_pwd" id="m_pw" type="password" value="" class="idpw_input" maxlength="30" /></li>
			</ul>
			<p class="idsave"><a href="javascript: fn_saveid();"><img src="/nanum/ncms/img/login/check_box_off.gif" alt="아이디저장" id="imgSaveid" data-yn="N" /></a>아이디저장</p>
			<p class="btn"><input type="submit" value="로그인" class="btn_bl_login" /></p>
		</div>

		</form>
		<!-- //로그인폼 -->

	</div>

</div>

<div id="copyright">
	<cite>Copyright&copy; <span>nanum i&c</span>. All rights reserved.</cite>
	<p>creative by <span><a href="http://www.nninc.co.kr" target="_blank">nanum i&c</a></span></p>
</div>

</body>
</html>
