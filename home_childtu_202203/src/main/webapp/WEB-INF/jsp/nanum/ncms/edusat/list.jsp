<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_edusat.js"></script>


<h1 class="tit">프로그램 리스트</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

<form id="frm_sch" action="list.do" method="get">
<!-- 검색 -->
<div class="top_search_area mt10">
	<ul>
		<li class="tit"><label for="ct_name_i"><h3 class="tit">프로그램검색 :</h3></label></li>
		<li class="sel">
		<c:if test="${empty BUILDER_DIR }">
			<select id="sh_ct_idx" name="sh_ct_idx" title="도서관 선택" class="t_search" style="width:180px;">
				<option value="" > -- 도서관선택 --</option>
				<c:forEach items="${libList}" var="lib" varStatus="no">
					<option value="${lib.ct_idx}" ${param.sh_ct_idx eq lib.ct_idx ? 'selected="selected"' : '' }>${lib.ct_name}</option>
				</c:forEach>
				</select>
		</c:if>
		<c:if test="${cateList != null}">
			<select id="sh_ct_idx2" name="sh_ct_idx2" title="분류 선택" class="t_search" style="width:180px;">
				<option value="" > -- 분류선택 --</option>
				<c:forEach items="${cateList}" var="cate" varStatus="no">
					<option value="${cate.ct_idx}" ${param.sh_ct_idx2 eq cate.ct_idx ? 'selected="selected"' : '' }>${cate.ct_name}</option>
				</c:forEach>
				</select>
		</c:if>
			<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
				<option value="edu_subject" ${param.v_search eq 'edu_subject' ? 'selected="selected"' : '' }>제목</option>
				<option value="edu_content" ${param.v_search eq 'edu_content' ? 'selected="selected"' : '' }>내용</option> 
			</select>
		</li>
		<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="메뉴 입력" name="v_keyword" id="v_keyword" type="text" value="${param.v_keyword }" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
		<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('list.do');" /></li>
	</ul>
</div>
<!-- //검색 -->
</form>



<div class="list_count" style="height:20px;">
	전체 <strong>${recordcount }</strong>개 (페이지 <strong class="point_default">${v_page}</strong>/${totalpage})
</div>
<div class="point_default pb5">* 신청인원을 클릭하면 신청자를 확인할수 있습니다.</div>


<form id= "frm_list" action="" method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" name="edu_chk" />

		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" id="prepage" name="prepage" value="${nowPage }" />
	</div>


	<fieldset>
		<legend>회원관리 수정/삭제/보기</legend>
		<table class="bbs_common bbs_default" summary="사이트의 회원을 관리합니다.">
		<caption>회원관리 서식</caption>
		<colgroup>
		<col width="4%" />
		<col  />
		<col width="8%" />
		<!-- <col width="10%" /> -->
		<col width="8%" />
		<col width="8%" />
		<col width="5%" />
		<col width="5%" />
		</colgroup>

		<thead>
		<tr>
			<th scope="col">선택</th>
			<th scope="col">제목(신청기간)</th>
			<th scope="col">신청인원</th>
			<!-- <th scope="col">신청방식</th> -->
			<th scope="col">사용여부</th>
			<th scope="col">진행상태</th>
			<th scope="col">수정</th>
			<th scope="col">삭제</th>
			<!-- <th scope="col">보기</th> -->
		</tr>
		</thead>

		<tbody>
		
		<c:forEach items="${edusatList}" var="edusat" varStatus="no">
		
			<c:set var='str1' value='사용중지'/>
			<c:choose>
				<c:when test="${edusat.end_chk eq 'Y'}">
					<c:set var='str1' value='사용중'/>
				</c:when>
				<c:when test="${edusat.end_chk eq 'T'}">
					<c:set var='str1' value='신청마감'/>
				</c:when>
			</c:choose>

			<jsp:useBean id="now" class="java.util.Date" />
			<c:set var="nowdate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" /></c:set>

			<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}${edusat.edu_resdate_h}00' />
			<c:if test="${fn:length(edusat.edu_resdate_h) == 1}">
				<c:set var='resdate' value='${fn:replace(edusat.edu_resdate,"-","")}0${edusat.edu_resdate_h}00' />
			</c:if>
			
			<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}${edusat.edu_reedate_h}59' />
			<c:if test="${fn:length(edusat.edu_reedate_h) == 1}">
				<c:set var='reedate' value='${fn:replace(edusat.edu_reedate,"-","")}0${edusat.edu_reedate_h}59' />
			</c:if>
			
			<c:choose>
				<c:when test="${reedate < nowdate}">
					<c:set var="str2" value="기간종료" />
				</c:when>
				<c:when test="${resdate > nowdate}">
					<c:set var="str2" value="준비중" />
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${edusat.edu_inwon < edusat.req_count}">
							<!-- <c:set var="str2" value="마감" /> -->
							<c:set var="str2" value="신청중" />
						</c:when>
						<c:otherwise>
							<c:set var="str2" value="신청중" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			
			<c:set var="receive_str" value=""></c:set>
			<c:set var="br" value=""></c:set>
			<c:choose>
				<c:when test="${fn:contains(edusat.edu_receive_type, '0')}">
					<c:set var="receive_str" value="${receive_str}${br}방문신청"></c:set>
					<c:set var="br" value="<br />"></c:set>
				</c:when>
				<c:when test="${fn:contains(edusat.edu_receive_type, '1')}">
					<c:set var="receive_str" value="${receive_str}${br}전화신청"></c:set>
					<c:set var="br" value="<br />"></c:set>
				</c:when>
				<c:when test="${fn:contains(edusat.edu_receive_type, '2')}">
					<c:set var="receive_str" value="${receive_str}${br}인터넷신청"></c:set>
					<c:set var="br" value="<br />"></c:set>
					<c:choose>
						<c:when test="${edusat.edu_lot_type eq '1'}">
							<c:set var="receive_str" value="${receive_str}<br/>(추첨식 / 대기:${edusat.edu_awaiter})"></c:set>
						</c:when>
						<c:when test="${edusat.edu_lot_type eq '2'}">
							<c:set var="receive_str" value="${receive_str}<br/>(선착순 / 대기:${edusat.edu_awaiter})"></c:set>
						</c:when>
						<c:when test="${edusat.edu_login eq 'Y'}">
							<c:set var="receive_str" value="${receive_str}${br}회원신청"></c:set>
						</c:when>
						<c:when test="${edusat.edu_login ne 'Y'}">
							<c:set var="receive_str" value="${receive_str}${br}비회원신청"></c:set>
						</c:when>
					</c:choose>
				</c:when>
			</c:choose>
			
			<c:set var="view_url" value="/edusat/list.do?edu_idx=${edusat.edu_idx }" />
			<c:if test="${!empty BUILDER_DIR }">
				<c:set var="view_url" value="/${BUILDER_DIR }${view_url }" />
			</c:if>
			
		<tr>
			<td class="center"><input type="checkbox" name="chk" value="${edusat.edu_idx}" title="해당 프로그램 선택" /></td>
			<td class="left" style="line-height:160%;">
				<a href="${view_url }" target="_blank">
					<strong><c:if test="${edusat.edu_gubun ne ''}">[${edusat.edu_gubun}]</c:if> ${edusat.edu_subject }</strong>
				</a>
				<br/><span style="color:red;"><strong>신청기간 : </strong></span>${edusat.edu_resdate}(${edusat.edu_resdate_h}시:00분) ~ ${edusat.edu_reedate }(${edusat.edu_reedate_h}시:00분)
				<!-- <br/><span class="orange"><strong>프로그기간 : </strong></span> ${edusat.edu_sdate } ~ ${edusat.edu_edate } -->
			</td>
			<td class="center">
				<fmt:parseNumber value="${edusat.req_count}" pattern="0,000.00" var="req_count"/>
				<fmt:parseNumber value="${edusat.edu_inwon}" pattern="0,000.00" var="edu_inwon"/>

				<a href="./requestList.do?edu_idx=${edusat.edu_idx }&prepage=${nowPageEncode}">
				<span style='font-weight:bold; color:red;'>${edusat.req_count} </span>명
				<!-- 
				<c:if test="${req_count >= edu_inwon}">
					<span style='font-weight:bold;color:red;'>${edusat.req_count}</span>
				</c:if>
				<c:if test="${req_count < edu_inwon}">
					<span class="sky_blue" style='font-weight:bold;'>${edusat.req_count}</span>
				</c:if> / <strong>${edusat.edu_inwon} 명</strong>
				 -->
				</a>
			</td>
			<!-- <td class="center" style="line-height:150%;">${receive_str}</td> -->
			<td class="center"><c:out value="${str1}"></c:out></td>
			<td class="center"><c:out value="${str2}"></c:out></td>
			<td class="center"><a href="write.do?edu_idx=${edusat.edu_idx }&prepage=${nowPageEncode}"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a></td>
			<td class="center"><a href="#del" onclick="d_chk('deleteOk.do?edu_idx=${edusat.edu_idx}&prepage=${nowPageEncode}');" ><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif"/></a></td>

		</tr>
		</c:forEach>
		
		
		
		</tbody>
		</table>
	</fieldset>

	<!-- 하단버튼 -->
	<div id="contoll_area">
		<ul>
			<li class="btn_le">
				<p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 프로그램삭제</a></p>
			</li>
			<li class="btn_ri">
				<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 프로그램을</p>
				<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 프로그램사용여부 선택" class="t_search" style="width:90px;">
					
								<option value="Y" >사용중</option>
								<option value="T" >신청마감</option>
								<option value="N" >사용중지</option>

					</select></p>
					<p>(으)로</p>
					<p><a href="javascript:tot_levelchage();" class="btn_gr">변경</a></p>
				</li>
			</ul>
		</div>
		<!-- //하단버튼 -->
		

		<!-- 페이징 -->
		<div class="paginate">
			${pagingtag }
		
		</div>
		<!-- //페이징 -->


	</form>

	
</div>
<!-- 내용들어가는곳 -->