<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>독서문화프로그램 신청정보</title>
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/all.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/sub_layout.css" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/design_default.css" />
<link rel="Stylesheet" type="text/css" href="/nanum/nninc/common/js/jquery-ui.css" />
<link href="http://fonts.googleapis.com/css?family=Armata" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="/nanum/ncms/common/js/common_design.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-cookie.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/jquery-rightClick.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_design.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>

<script type="text/javascript" src="/nanum/ncms/common/js/design_default.js"></script>
	
</head>

<body>

<!-- 전체싸는 박스 -->
<div style="padding:15px;">

	<!-- 중간 영역 -->
	<div>

		<!-- 내용영역 -->
		<div  style="width: 100%;">

			<div id="contents_head"  style="width: 100%;"> 
				<div id="contents_title">
					<h1 class="tit"><span>${edusat.edu_subject} 신청정보 </span></h1>
				</div>
			</div>
			
							
					<fieldset>
						<legend>회원관리 수정/삭제/보기</legend>
						<table cellspacing="0" class="bbs_common bbs_default" summary="사이트의 회원을 관리합니다.">
						<caption>회원관리 서식</caption>
						<colgroup>
						<col width="40" />
						<col />
						<col />
<%-- 						<col width="8%" /> --%>
						<col />
						<col />
						<col />
<%-- 						<col width="10%" /> --%>
						<col width="140" />
						</colgroup>

						<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">상태</th>
							<th scope="col">성명</th>
<!-- 							<th scope="col">성별</th> -->
							<th scope="col">핸드폰번호</th>
							<th scope="col">참가인원</th>
							<th scope="col">참가자명</th>
<!-- 							<th scope="col">생년월일</th> -->
							<th scope="col">신청일</th>
						</tr>
						</thead>

						<tbody>
						
	<c:set var="accnum" value="0"></c:set>
	<c:forEach items="${edusatRequest}" var="edusatRequest" varStatus="no">
		<c:if test="${edusatRequest.es_status ne '9' }">
			<c:set var="accnum" value="${accnum+ edusatRequest.es_ptcp_cnt}" />
		</c:if>	
		<c:choose>
			<c:when test="${edusat.edu_lot_type eq '2' }">
				<c:set var="es_status_str" value="<span style='font-weight:bold;color:#6e90a8;'>신청완료</span>"></c:set>
				<c:if test="${accnum > edusat.edu_inwon }">
					<c:set var="es_status_str" value="대기자"></c:set>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:set var="es_status_str" value="<span style='font-weight:bold;color:#6e90a8;'>신청완료</span>"></c:set>
				<c:if test="${edusatRequest.es_status eq '1'}">
					<c:set var="es_status_str" value="<font color='#0000FF'>당첨</font>"></c:set>
				</c:if>
				<c:if test="${edusatRequest.es_status eq '2'}">
					<c:set var="es_status_str" value="낙첨"></c:set>
				</c:if>
			</c:otherwise>
		</c:choose>
		<c:if test="${edusatRequest.es_status eq '9' }">
			<c:set var="es_status_str" value="취소"></c:set>
		</c:if>
						<tr>
							<td scope="row" class="center eng">${no.count}</td>
							<td scope="row" class="center">${es_status_str}</td>
							<td scope="row" class="center"><span class="orange">${edusatRequest.es_name}</span></td>
							<!-- <td scope="row" class="center">
								<c:if test="${edusatRequest.es_sex eq '1'}">남</c:if>
								<c:if test="${edusatRequest.es_sex eq '2'}">여</c:if>
							</td>-->
							<td scope="row" class="center eng">${edusatRequest.es_bphone1}-${edusatRequest.es_bphone2}-${edusatRequest.es_bphone3}</td>
							<td scope="row" class="center">${edusatRequest.es_ptcp_cnt} 명</td>
							<td scope="row" class="center">${edusatRequest.es_ptcp_name}</td>							
							<!-- <td scope="row" class="center">${edusatRequest.es_jumin}</td>-->
							<td scope="row" class="center eng">${edusatRequest.es_wdate}</td>
						</tr>
	</c:forEach>
	<c:if test="${fn:length(edusatRequest)==0 }">
												
						<tr>
							<td scope="row" class="center" colspan="7"> 등록된 신청자 없습니다.</td>
							
						</tr>
	</c:if>

						</tbody>
						</table>
					</fieldset>

				<div class="contoll_box">
					<span><input type="button" value="창닫기" onclick="javascript: self.close();" class="btn_wh_default" /></span>
				</div>

		</div>
		<!-- 내용들어가는곳 -->

		</div>
		<!-- //내용영역 -->


</div>
</div>

</body>
</html>