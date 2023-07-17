<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? 'list.do' : param.prepage }" />

<!-- 상단내용 --> 
${pollconf.poc_tophtml }

<link rel="Stylesheet" type="text/css" href="/nanum/site/poll/css/common.css" />
<script type="text/javascript" src="/nanum/site/poll/js/common.js"></script>

<!-- 리스트 -->
<div id="poll">

	<form id= "frm_list" action="pollOk.do" method='post'>
	<div>
		<input type="hidden" name="po_pk" value="${poll.po_pk}" />
		<input type="hidden" name="por_mid" value="${ss_m_id}" />
		<input type="hidden" name="prepage" value="${prepage}" />
	</div>

	<!-- 설문주제 타이틀 -->
	<div id="poll_tit">
		<div class="title_bg">
			<div class="title_bg_02">
				<div class="title">
					<span>설문주제</span>
					${poll.po_subject}
				</div>
				<div class="title_content">
					<ul>
					<li>
						<strong>기&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;간</strong>
						 ${po_sdate_y}년 ${po_sdate_m}월 ${po_sdate_d}일 ~ ${po_edate_y}년 ${po_edate_m}월 ${po_edate_d}일
					</li>
					<li>
						<strong>총문항수</strong>
						 ${fn:length(questionList)} 문항
					</li>
					<li>
						<strong>참여자수</strong>
						 ${poll.po_count} 명
					</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- //설문주제 타이틀 -->


	<!-- 리스트 테이블 -->
	<c:forEach items="${questionList}" var="question" varStatus="no">
	<div class="table_bwrite">

	<input type="hidden" name="poq_type_${question.poq_idx}" value="${question.poq_type}" />
	<input type="hidden" name="poq_multi_${question.poq_idx}" value="${question.poq_multi}" />

		<fieldset>
		<legend>설문문항 리스트</legend>
			<table cellspacing="0">
			<caption>리스트</caption>
			<thead>
			<c:if test="${!empty question.poq_topmemo}">
				<tr>
					<th colspan="2" class="poll_head" style="text-align:left;">${question.poq_topmemo}</th>
				</tr>
			</c:if>
				<tr>
					<th scope="col" class="count"><span>${no.count}</span></th>
					<th scope="col" class="subject" style="text-align:left;">${question.poq_question}</th>
				</tr>
			</thead>

	<c:if test="${question.poq_type eq '1'}">
	<tbody>
		<tr>
			<td colspan="2" class="content">
				<c:forEach var="i" begin="1" end="10" step="1">
				<!-- 보기문항 가변적변수 -->
				<c:set var="bogi">poq_bogi${i}</c:set>
				<c:set var="poq_bogi">${question[bogi]}</c:set>
				
				<c:if test="${poq_bogi ne ''}">
					<c:choose>
						<c:when test="${question.poq_multi eq 'Y'}">
							<div><label for="q_${question.poq_idx}_${i}"><input type="checkbox" id="q_${question.poq_idx}_${i}" name="q_${question.poq_idx}" value="${i}" title="${poq_bogi}" />${poq_bogi}</label></div>
						</c:when>
						<c:otherwise>
							<div><label for="q_${question.poq_idx}_${i}"><input type="radio" id="q_${question.poq_idx}_${i}" name="q_${question.poq_idx}" value="${i}" title="${poq_bogi}" />${poq_bogi}</label></div>
						</c:otherwise>
					</c:choose>
				</c:if>
				</c:forEach>
			</td>
		</tr>
	</tbody>
	</c:if>
	<c:if test="${question.poq_type ne '1'}">
	<tbody>
		<tr>
			<td class="content" colspan="2">
				<div><textarea style="width:98%;" cols="90" rows="5" id="q_${question.poq_idx}" name="q_${question.poq_idx}" title="내용 입력" ></textarea></div>
			</td>
		</tr>
	</tbody>
	</c:if>
			</table>
		</fieldset>
	</div>

	<div>
	<input type="hidden" name="chk" value="${question.poq_idx}" />
	</div>
	</c:forEach>

	<!-- //리스트 테이블 -->
	
	


	<!-- 버튼 -->
	<div class="poll_button2">
		<span><input type="submit" value="설문참여" class="con_btn orange" /></span>
		<span><a href="${prepage }" title="목록" class="con_btn blue" style="color:#fff;">목록</a></span>
	</div>
	<!-- //버튼 -->
	</form>
</div>
<!-- //리스트 -->
	

<!-- 하단내용 --> 
${pollconf.poc_btmhtml }