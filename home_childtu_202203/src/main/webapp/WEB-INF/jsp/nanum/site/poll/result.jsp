<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/poll/list.do' : param.prepage}" />
<link rel="Stylesheet" type="text/css" href="/nanum/site/poll/css/common.css" />
<script type="text/javascript" src="/nanum/site/poll/js/common.js"></script>

<!-- 상단내용 --> 
${pollconf.poc_tophtml }

<!-- 리스트 -->
<div id="poll">

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
	
		<c:if test="${question.poq_type eq '1'}">
		<p class="mscroll_guide mt20"><span>모바일로 확인하실 경우</span> 좌우로 움직여 내용을 확인 하실 수 있습니다.</p>
		<div class="mscroll">
		<div class="table_bview">
			<fieldset>
			<legend>게시물 리스트</legend>
				<table cellspacing="0">
				<caption>리스트</caption>
					<colgroup>
					<col width="" />
					<col width="" />
					</colgroup>
				<thead>
					<tr>
						<th scope="col" class="count"><span>${no.count}</span></th>
						<th scope="col" class="subject" style="text-align:left;">${question.poq_question}</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2" class="content">
							<c:forEach var="i" begin="1" end="10" step="1">
								<!-- 보기문항(poq_bogi1, poq_bogi2, poq_bogi3...) -->
								<c:set var="bogi" value="poq_bogi${i}" />
								<c:set var="poq_bogi" value="${question[bogi]}" />
								<!-- 퍼센티지 변수초기화 -->
								<c:set var="v_percent_str" value="1" />
								<c:set var="v_percent" value="0" />
								
								<!-- 보기가있다면 -->
								<c:if test="${poq_bogi ne ''}">
								
									<!-- HashMap에 있는거 꺼내기 -->
									<c:set var="mp" value="${question.por_mp}" />
									<c:set var="por" value="por_cnt${i}" />
									<c:set var="percent" value="v_percent${i}" />
									<c:set var="percent_str" value="v_percent_str${i}" />
									<!--// HashMap에 있는거 꺼내기 -->
									<c:set var="por_cnt">${mp[por]}</c:set>
									
									<div class="total_result">
										<div class="result01">${poq_bogi}</div>
										<div class="result02">${por_cnt}명</div>
										<div class="result03">${mp[percent]}%</div>
										<div class="result04">
											<div class="graph0${i}" style="width:${mp[percent_str]}%;">
												<span/>
											</div>
										</div>
										<div class="clear"/>
									</div>
								</c:if>
							</c:forEach>
						</td>
					</tr>
				</tbody>
				</table>
			</fieldset>
		</div>
		</div>
		</c:if>
		<c:if test="${question.poq_type ne '1'}">
			<div class="table_bwrite">
				<fieldset>
				<legend>게시물 리스트</legend>
					<table cellspacing="0">
					<caption>리스트</caption>
						<colgroup>
						<col width="38" />
						<col width="" />
						<col width="70" />
						</colgroup>
					<thead>
						<tr>
							<th scope="col" class="count"><span>${no.count}</span></th>
							<th scope="col" class="subject" style="text-align:left;">${question.poq_question}</th>
							<th scope="col" class="subject"><a href="javascript:win_popup3(${no.count},${question.poq_idx},'yes','no','no','no',600,500);" class="all_look">전체보기</a></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="content" colspan="3" style="height:15px;"></td>
						</tr>
					</tbody>
					</table>
				</fieldset>
			</div>
		</c:if>
	</c:forEach>

	
	<!-- //리스트 테이블 -->
	


	<!-- 버튼 -->
	<div class="poll_button2">
		<span><a href="${prepage }" title="목록" class="con_btn blue" style="color:#fff;">목록</a></span>
	</div>
	<!-- //버튼 -->


<script type="text/javascript">//<![CDATA[
//새창
function win_popup3(sun,idx,vscrollbars,vtoolbar,vmenubar,vlocation,vwidth,vheight){	
	window.open('/poll/view.do?sun='+sun+'&poq_idx='+idx,'','scrollbars='+vscrollbars+',toolbar='+vtoolbar+',menubar='+vmenubar+',location='+vlocation+',width='+vwidth+',height='+vheight+',location=no');
}
//]]>
</script>

</div>
<!-- //리스트 -->
	

<!-- 하단내용 --> 
${pollconf.poc_btmhtml }