<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>
			
<link href="/nanum/ndls/common/css/content.css" rel="stylesheet" type="text/css">
			<div class="drop_out">
				<p class="btxt">유의사항</p>
				<p class="stxt">준회원(우리 도서관 회원증 미발급 이용자)만 탈퇴 가능합니다.<br />
				정회원(우리 도서관 회원증 소지자)은 도서관을 방문하여 미반납자료 내역 등을 확인한 후 탈퇴 가능합니다.</p>
				
				<div class="btn">
					<a href="/ndls/memberService/memberDropProc.do" onclick="if(!confirm('회원탈퇴 하시겠습니까?')){return false;}" class="hvr-fade con_btn">회원탈퇴</a>
				</div>
			</div>