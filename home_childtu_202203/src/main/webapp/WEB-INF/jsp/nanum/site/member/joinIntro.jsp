<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>
<jsp:forward page="/main/site/member/agree.do" />
<div class="memberbox">
	<!-- -->
	<div class="box">
		<p class="tit icon01"><strong>회원증이 있는</strong>분</p>
		<div class="con">
			<ul class="list">
				<li>기존 도서회원의 홈페이지 아이디 발급</li>
				<li>본인인증후 홈페이지에 사용할 아이디와 비밀번호를 발급합니다.</li>
				<li>미인증회원일 경우 회원증에 기재된 회원번호를 입력해야 합니다. 회원번호를 모를 경우 신분증 지참 후 도서관으로 방문 바랍니다.</li>
			</ul>
		</div>
		<p class="btn"><a href="/main/site/member/createID.do" class="con_btn">기존회원가입 바로가기</a></p>
	</div>
	<!-- -->
	<div class="box">
		<p class="tit icon02"><strong>회원증이 없는</strong>분</p>
		<div class="con">
			<ul class="list">
				<li>회원으로 가입한 적이 없는 이용자 [기존 회원가입 여부를 모를 경우 도서관으로 전화문의 바랍니다.]</li>
				<li>신규회원가입후 도서관에 방문하여 회원증을 발급하셔야 홈페이지내 모든 서비스를 이용할 수 있습니다.</li>
				<li>신분증 지참하여 자료실 방문 [14세 미만의 어린이는 법정대리인이 가족관계증명서 및 사진을 지참하여 대리신청 가능]</li>
				
			</ul>
		</div>
		<p class="btn"><a href="/main/site/member/agree.do" class="con_btn">신규회원가입 바로가기</a></p>
	</div>
	<!-- -->
</div>