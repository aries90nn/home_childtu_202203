<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ko" lang="ko" xml:lang="ko" xmlns="http://www.w3.org/1999/xhtml"><head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="language" content="ko">

	<meta name="robots" content="noindex, nofollow">
	<title>사물함</title>
	
	<link href="/nanum/site/locker/css/common.css?v=asdf" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/nanum/site/common/js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
	
	<style>
		table.tb_locker tbody tr td.on span{cursor:pointer;}
	</style>
	<script type="text/javascript">
	//<![CDATA[
		$(function(){
		
			$("table.tb_locker tbody tr td.on span").click(function(){
				var num = $(this).text();
				selectNum(num);
			});
		});

		onload = function(){
			document.getElementById("close").focus();
		}

		function selectNum(num){
			opener.document.getElementById("lc_locker_num").value = num;
			window.close();
		}
	//]]>
	</script>
</head>


<body class="popup">

<div class="popup_top">
	<div class="popup_tit">${libname } 사물함 선택</div>
	<div class="popup_close"><a id="close" onkeypress="window.close();" onclick="window.close();" href="#close"><img alt="팝업창닫기" src="/nanum/site/locker/img/btn_popup_cls2.png"></a></div>
</div>

<div class="popup_con">
	<table class="tb_locker" summary="${libname } 사물함 선택">
		<caption>${libname } 사물함 선택</caption>
		<colgroup>
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
		</colgroup>
		<tbody>
			<tr>
<c:forEach begin="1" end="${locker_count }" var="i">
	<c:set var="i_str">${i }</c:set>
	<c:set var="user_data" value="${user_list[i_str] }" />
	<c:choose>
		<c:when test="${empty user_data }">
				<td class="on">
					<span class="locker_date">${i }</span>
					<ul>
						<li class="poss">신청가능</li>
						<li>&nbsp;</li>
					</ul>
				</td>
		</c:when>
		<c:otherwise>
				<td class="">
					<span class="locker_date">${i }</span>
					<ul>
						<li class="poss">신청불가</li>
						<li>
			<c:if test="${sessionScope.ss_security_ad_cms eq 'ok' }">
						${user_data.lc_m_name }(${user_data.lc_m_num })
			</c:if>
						</li>
					</ul>
				</td>
		</c:otherwise>
	</c:choose>
				
	<c:if test="${i ne locker_count and i mod 10 eq 0 }">
			</tr><tr>
	</c:if>
</c:forEach>
			</tr>
		</tbody>
	</table>
</div>






</body></html>