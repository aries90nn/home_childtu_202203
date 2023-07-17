<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<style type="text/css">
	.basic_m_wrap {font-size:0; line-height:1; }
	.basic_m_wrap .box {border:1px solid #ddd;  margin-top:20px;}
	.basic_m_wrap .box:first-child {margin-top:0;}
	.basic_m_wrap .box:hover {border-color:#555; box-shadow:0px 2px 2px 2px rgba(0,0,0,0.05);}
	.basic_m_wrap p {font-size:16px; padding:20px; background:#f8f8f8; border-bottom:1px dashed #ddd;}
	.basic_m_wrap p.tit {font-family:'NotoKrM',sans-serif;}
	.basic_m_wrap .btn_warp {text-align:center;}
	.btn_warp {margin:20px 0;}
	.btn_warp button,
	.btn_warp button::after {-webkit-transition: all 0.3s; -moz-transition: all 0.3s;  -o-transition: all 0.3s;	transition: all 0.3s;}
	.btn_warp button { background: none;  border: 2px solid #555;  border-radius: 5px;  color: #000;  display: inline-block; font-size: 16px;  
	font-weight: bold;  margin: 2px;  padding: 6px 20px;  position: relative;  text-transform: uppercase; min-width:100px;}
	.btn_warp button::before,
	.btn_warp button::after { background: #555; content: ''; position: absolute; z-index: -1;}
	.btn_warp button:hover {color: #fff;}

	/* BUTTON 1 */
	.btn_warp .btn-1::after { height: 0; left: 0; top: 0; width: 100%;}
	.btn_warp .btn-1:hover:after {  height: 100%;}

</style>

<div class="basic_m_wrap">
	<div class="box">
		<p class="tit">${confirm }</p>
		<div class="btn_warp">
			<button class="btn-1" onclick="location.href='${yes}';">네</button>
			<button class="btn-1" onclick="location.href='${no }';">아니오</button>
		</div>
	</div>

</div>

