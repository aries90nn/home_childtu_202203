<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.net.URLEncoder"%>

<link rel="Stylesheet" type="text/css" href="/nanum/site/edusat/css/common.css" />
<link rel="Stylesheet" type="text/css" href="/nanum/site/common/css/sub_design.css" />
<script type="text/javascript">
//<![CDATA[

function deleteOk(edu_idx, es_idx){
	if(confirm("신청을 취소하시겠습니까?")){
		location.href="/edusat/cancelOk.do?edu_idx="+edu_idx+"&es_idx="+es_idx+"&prepage=${prepage}";
	}
}
//]]>
</script>

<!-- 리스트 -->
<div id="board" style="width:${config.a_width};">

	<div class="base_search mb35">
		<div class="base_search_on">
			<form id="frm_sch" action="" method="get">
			<!-- 셀렉트박스 -->
			<div class="select_box">
				<span class="select_style">
				<select id="sh_ct_idx" name="sh_ct_idx" title="도서관선택">
					<option value="">도서관선택</option>
					<c:forEach items="${libList}" var="lib" varStatus="no">
					<option value="${lib.ct_idx}" ${param.sh_ct_idx eq lib.ct_idx ? 'selected="selected"' : '' }>${lib.ct_name}</option>
					</c:forEach>
				</select>
				</span>	
				
				<span class="select_style">
				<select id="v_search" name="v_search" title="검색형태 선택">
					<option value="edu_subject" ${v_search eq 'edu_subject' ? 'selected="selected"' : '' }>프로그램명</option>
					<option value="edu_teacher" ${v_search eq 'edu_teacher' ? 'selected="selected"' : '' }>강사명</option>
				</select>
				</span>	
			</div>
			<!-- //셀렉트박스 -->
			
			
			<!-- input 클릭시 label 숨기기-->
			<script type="text/javascript">
			$(function(){
				$(".search_input").each(function(){
					if($(this).val() != "")	$("label[for='"+$(this).attr("id")+"']").hide();
				}).focus(function(){
					$("label[for='"+$(this).attr("id")+"']").hide();
				}).blur(function(){
					if($(this).val() == "")	$("label[for='"+$(this).attr("id")+"']").show();
				});
			});
			</script>
			
			
			<!-- 검색바 -->
			<div class="search_style">		
				<div class="search">
					<div class="input">
						<input type="text" size="25" title="검색어를 입력하세요" id="p_keyword" name="v_keyword" placeholder="검색어를 입력하세요" class="search_txt" value="${param.v_keyword}" />
					</div>					
					<div class="input_bt"><input type="submit" class="btn-search" value="검색" /></div>
				</div>
			</div>
			<!-- //검색바 -->
			</form>
		</div>
	</div>
	
	<div class="list_count" style="margin-bottom:8px;">
		전체 <strong class="eng" style="color:#0070bb;">${recordcount }</strong>개 (페이지 <strong class="point_default eng black">${v_page}</strong><span class="eng">/${recordcount}</span>)
	</div>

	<!--  -->
	<div class="table_blist">
		<table cellspacing="0" summary="${title} 의 프로그램명, 운영기간, 신청현황, 신청상태를 확인">
		<caption>프로그램 신청내역</caption>
		<colgroup>
			<col width="50px" />
			<col />
			<col  />						
			<col width="70px"/>
			<col width="70px"/>
			<col width="90px"/>
		</colgroup>
		<thead>
		<tr>
			<th scope="col">번호</th>
			<th scope="col">프로그램정보</th>
			<th scope="col">참가인원</th>
			<th scope="col">신청현황</th>
			<th scope="col">신청상태</th>
			<th scope="col" class="th_none">취소</th>
		</tr>
		</thead>
		<tbody>
		<jsp:useBean id="now" class="java.util.Date" />
		<c:set var="nowdate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" /></c:set>
		
		<c:forEach items="${edusatList}" var="edusat" varStatus="no">
		
		<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}${edusat.edu_resdate_h}00' />
		<c:if test="${fn:length(edusat.edu_resdate_h) == 1}">
			<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}0${edusat.edu_resdate_h}00' />
		</c:if>
		
		<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}${edusat.edu_reedate_h}59' />
		<c:if test="${fn:length(edusat.edu_reedate_h) == 1}">
			<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}0${edusat.edu_reedate_h}59' />
		</c:if>
		<c:set var="requestChk" value="false"></c:set>
		<c:choose>
			<c:when test="${reedate < nowdate}">
				<c:set var="str2" value='기간종료' />
			</c:when>
			<c:when test="${resdate > nowdate}">
				<c:set var="str2" value='신청준비' />
			</c:when>
			<c:otherwise>
				<c:choose>				
					<c:when test="${edusat.edu_inwon <= edusat.req_count}">
						<c:set var="str2" value='신청마감' />
						<c:if test="${(edusat.edu_inwon + edusat.edu_awaiter) > edusat.req_count }">
							<c:set var="str2" value='신청중' />
							<c:set var="requestChk" value="true"></c:set>
						</c:if>
					</c:when>
					<c:otherwise>
						<c:set var="requestChk" value="true"></c:set>
						<c:set var="str2" value='<a href="#javascript:;" class="btn_sm btn_prepare">신청<br />준비</a>' />
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
				
		<%//신청상태 %>
		<c:set var="es_status_str" value="" />
		<c:choose>
			<c:when test="${edusat.edu_lot_type eq '2' }">
				<c:set var="es_status_str" value="신청완료"></c:set>
				<c:if test="${edusat.count_value+0 > edusat.edu_inwon+0}">
					<c:set var="es_status_str" value="대기자"></c:set>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:set var="es_status_str" value="신청완료"></c:set>
				<c:if test="${edusat.es_status eq '1'}">
					<c:set var="es_status_str" value="당첨"></c:set>
				</c:if>
				<c:if test="${edusat.es_status eq '2'}">
					<c:set var="es_status_str" value="낙첨"></c:set>
				</c:if>
			</c:otherwise>
		</c:choose>
		<c:if test="${edusat.es_status eq '9' }">
			<c:set var="es_status_str" value="취소"></c:set>
		</c:if>
		
		<c:set var="view_url" value="/edusat/view.do?edu_idx=${edusat.edu_idx}" />
		<c:if test="${!empty BUILDER_DIR }">
			<c:set var="view_url" value="/${BUILDER_DIR }/edusat/view.do?edu_idx=${edusat.edu_idx}" />
		</c:if>
			
		<tr>
			<td class="eng">${recordcount - ((v_page-1) * pagesize + no.index) }</td>
			<td class="title">
				<a href="${view_url }" target="_blank">${edusat.edu_subject}</a>
				<br/>운영기간 : <span class="eng">${edusat.edu_sdate}~${edusat.edu_edate}</span>
			</td>
			<td>
				<span class="eng"><c:out value="${edusat.es_ptcp_cnt} " /></span>명
				<c:if test="${edusat.edu_ptcp_yn eq 'Y' }">
					<br/>${ edusat.es_ptcp_name }
				</c:if>
			</td>
			<td>
			<c:choose>
				<c:when test="${requestChk eq 'true' }">
					신청중
				</c:when>
				<c:otherwise>
					${str2}
				</c:otherwise>
			</c:choose>
			</td>
			<td>${es_status_str}</td>
			<td>
				
				<c:if test="${edusat.es_status ne '9'}">
				
					<% // 개강 3일전까지 취소가능 %>
					<c:set var="cancelChk" value="N" />
					
					<fmt:parseDate value="${edusat.edu_sdate }" var="strPlanDate" pattern="yyyy-MM-dd"/>
					<fmt:parseNumber value="${strPlanDate.time / (1000*60*60*24)}" integerOnly="true" var="strDate"></fmt:parseNumber>
					<fmt:parseNumber value="${now.time / (1000*60*60*24)}" integerOnly="true" var="endDate"></fmt:parseNumber>
					
					<c:if test="${strDate - endDate >=2}">
						<c:set var="cancelChk" value="Y"></c:set>
					</c:if>
					
					<fmt:parseDate value="${edusat.edu_edate }" var="strPlanDate2" pattern="yyyy-MM-dd"/>
					<fmt:parseNumber value="${strPlanDate2.time / (1000*60*60*24)}" integerOnly="true" var="strDate2"></fmt:parseNumber>
					<c:if test="${strDate2 < endDate}">
						<c:set var="cancelChk" value="X"></c:set>
					</c:if>
					
					<c:choose>
						<c:when test="${cancelChk eq 'Y'}">
							<input type="button" value="취소하기" class="con_sbtn white" onclick="deleteOk('${edusat.edu_idx}', '${edusat.es_idx}');"/>
						</c:when>
						<c:when test="${cancelChk eq 'N'}">
							<input type="button" value="취소하기" class="con_sbtn white" onclick="javascript:alert('개강 3일전까지 취소가 가능합니다.\n\n이후 취소를 원할경우 도서관으로 연락해주세요.');"/>
						</c:when>
					</c:choose>
				</c:if>
				
			</td>
		</tr>
		</c:forEach>
		<c:if test="${fn:length(edusatList) == 0}">
		<tr>
			<td class="center" colspan="6">등록된 내용이 없습니다.</td>
		</tr>
		</c:if>
		</tbody>
	</table>
		

	<!-- 페이징 -->
	<div class="paginate">
		${pagingtag }
		
	</div>
	<!-- //페이징 -->

</div>
</div>