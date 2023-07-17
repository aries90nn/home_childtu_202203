<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->

<c:if test="${is_ad_cms eq 'Y' }">
<div class="board_con_tab board_tab_b">
<c:set var="class_nm" value="no${fn:length(courseList)+1}" />
<c:set var="class_nm" value="${fn:length(courseList)+1 > 7 ? 'no5' : class_nm}" />
<ul class="${class_nm}">
	<li><a href="?proc_type=list&a_num=${config.a_num }" ${empty param.sh_course ? 'class="on"' : '' }>전체</a></li>
	<c:forEach items="${courseList}" var="course" varStatus="no">
	<li><a href="?sh_course=${course.key}&a_num=${config.a_num }" ${param.sh_course eq course.key ? 'class="on"' : '' }>${course.value}</a></option>
	</c:forEach>
</ul>
</div>

<script type="text/javascript">
function viewDiray(b_id){
	window.open('/board/mara_diary/totalList.do?a_level=mara_diary&b_id='+b_id,'view_diary', 'width=1000,height=800,scrollbars=yes');
}
function excelDiray(b_id){
	alert("신청자가 많을경우 파일생성시간이 다소 걸릴 수 있습니다.");
	location.href='/board/mara_diary/totalListExcel.do?a_level=mara_diary&sh_course=${param.sh_course}&b_id='+b_id;
}
function excelDirayComplete(){
	alert("신청자가 많을경우 파일생성시간이 다소 걸릴 수 있습니다.");
	location.href='/board/mara_diary/totalListExcel.do?a_level=mara_diary&sh_course=${param.sh_course}&complete=Y';
}
</script>
</c:if>

<!-- 리스트 -->
<div class="mscroll_guide"><span>모바일로 확인하실 경우</span> 표를 좌우로 움직여 내용을 확인 하실 수 있습니다.</div>
<div class="mscroll">


<div id="board" style="width:${config.a_width};">
	<div class="board_total">
		<div class="board_total_left" style="vertical-align:middle;">
		<c:choose>
			<c:when test="${config.a_ad_cms eq 'Y'}">
				<c:choose>
					<c:when test="${ss_m_id == null}">
						<a href="?proc_type=login&a_num=${config.a_num}&prepage=${nowPageEncode}"><img src="/nanum/site/board/nninc_simple/img/key.gif" alt="" /></a> 
					</c:when>
					<c:otherwise>
						<a href="/board/logout.do?a_num=${config.a_num}&prepage=${nowPageEncode}"><img src="/nanum/site/board/nninc_simple/img/unkey.gif" alt="" /></a> 
					</c:otherwise>
				</c:choose>
				전체 <strong>${recordcount}</strong>개 (페이지 <strong class="board_orange">${v_page}</strong>/${totalpage})
			</c:when>
			<c:otherwise>
				
			</c:otherwise>
		</c:choose>
			&nbsp;<span>※ 참가자정보를 클릭해서 수정할 수 있습니다.</span>
		</div>
		<div class="board_total_right" style="vertical-align:middle;">
<c:if test="${is_ad_cms eq 'Y'}">
	<c:set var="course_name" value="${courseList[param.sh_course] }" />
	<c:if test="${empty course_name }">
		<c:set var="course_name" value="전체" />
	</c:if>
	
			<input type="button" value="${course_name } 엑셀다운로드" onclick="excelDiray('');" class="board_button4" style="padding:3px;font-size:12px;margin:0px;" />
			<input type="button" value="${course_name } 엑셀다운로드(완주자만)" onclick="excelDirayComplete();" class="board_button4" style="padding:3px;font-size:12px;margin:0px;" />
</c:if>
		
			<jsp:include page="../code.jsp" />
		</div>
	</div>
	
	<c:if test="${is_ad_cms eq 'Y'}">
	<form id= "frm_list" action="${path_info }" method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" name="a_num" value="${config.a_num}" />
		<input type="hidden" name="chk_all" id="chk_all" />		<!-- 전체체크 -->
		<input type="hidden" name="prepage" value="${nowPage}" />
		<input type="hidden" name="board_token" value="${board_token}" />
		<input type="hidden" name="a_level" value="${config.a_level}" />
	</div>
	</c:if>
	
	<c:set var="colCnt" value="8" />
	
	<!-- 리스트 테이블 -->
	<div class="table_blist">
	<table cellspacing="0" summary="${title} 의 번호, 제목, 글쓴이, 등록일, 조회수를 확인">
	<caption>${title}</caption>
		<colgroup>
		<c:if test="${is_ad_cms eq 'Y'}">
		<col width="50" />
		<col width="50" />
		<c:set var= "colCnt" value="${colCnt+2}"/>
		</c:if>
		<col width="" />
		<col width="" />
		<col width="" />
		<col width="" />
		<col width="" />
		<col width="" />
		<col width="" />
		<c:if test="${is_ad_cms eq 'Y'}">
		<col width="70" />
		<col width="100" />
		<c:set var= "colCnt" value="${colCnt+2}"/>
		</c:if>
		
		</colgroup>
	<thead>
		<tr>
			<c:if test="${is_ad_cms eq 'Y'}">
			<th scope="col">선택</th>
			<th scope="col">번호</th>
			</c:if>
			<th scope="col">아이디</th>
			<th scope="col">이름</th>
			<th scope="col">연락처</th>
			<th scope="col">휴대전화</th>
			<th scope="col">참가종목</th>
			<th scope="col">달성률</th>
			<th scope="col">등록일</th>
			<c:if test="${is_ad_cms eq 'Y'}">
			<th scope="col">상태</th>
			<th scope="col">일지</th>
			</c:if>

		</tr>
	</thead>
	<tbody>
		<jsp:useBean id="now" class="java.util.Date" />
		<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="now_date" />
	
	<c:forEach items="${boardList}" var="board" varStatus="no">
		<c:set var="b_name" value="${board.b_name }" />
		<c:if test="${(config.a_type eq 'Y' and board.b_open eq 'N') or config.a_type eq 'T'}">
			<c:if test="${is_ad_cms ne 'Y' }">
				<c:set var="b_name" value="비공개" />
			</c:if>
		</c:if>
		
		<%--코스별 쪽수 문자열 --%>
		<c:if test="${!empty board.b_temp7 }">
			<fmt:formatNumber var="pagecnt" value="${board.b_temp7 }" pattern="#,###" />
		</c:if>
		<c:set var="b_temp7_str" value="${courseList[board.b_temp7] } (${pagecnt }쪽)" />
		<c:if test="${empty courseList[board.b_temp7] }">
			<c:set var="b_temp7_str" value="${board.b_temp7 }" />
		</c:if>
		<c:set var="b_temp9_str" value="0" />
		<c:if test="${!empty board.b_temp9 }">
			<fmt:formatNumber var="b_temp9_str" value="${board.b_temp9 }" pattern="#,###" />
		</c:if>
		
		<c:set var="view_url" value="?proc_type=view&a_num=${config.a_num}&b_num=${board.b_num}&prepage=${nowPageEncode}" />
		
		<c:set var="b_temp3_1" value="${fn:split(board.b_temp3, '-')[0]}" />
		<c:set var="b_temp3_2" value="${fn:split(board.b_temp3, '-')[1]}" />
		
		<c:set var="b_temp4_1" value="${fn:split(board.b_temp4, '-')[0]}" />
		<c:set var="b_temp4_2" value="${fn:split(board.b_temp4, '-')[1]}" />
		<c:set var="b_temp4_3" value="${fn:split(board.b_temp4, '-')[2]}" />
		
		<c:set var="b_temp6_1" value="${fn:split(board.b_temp6, '-')[0]}" />
		<c:set var="b_temp6_2" value="${fn:split(board.b_temp6, '-')[1]}" />
		<c:set var="b_temp6_3" value="${fn:split(board.b_temp6, '-')[2]}" />
		
		<fmt:formatNumber var="avg" value="${(board.b_temp9*1.0 / board.b_temp7) * 100}" pattern="#.##"/>
		<c:set var="avg" value="${avg}%" />
		<c:if test="${board.b_temp10 eq '1' }">
			<c:set var="avg" value="<span style='font-weight:bold;color:#FF0000;'>${avg }</span>" />
		</c:if>
		
		<c:set var="b_temp10_str" value="심사대기" />
		<c:if test="${board.b_temp10 eq '1' }">
			<c:set var="b_temp10_str" value="<span style='font-weight:bold;color:#0000FF;'>완주완료</span>" />
		</c:if>
		<c:if test="${board.b_temp10 eq '2' }">
			<c:set var="b_temp10_str" value="<span style='font-weight:bold;color:#FF0000;'>완주실패</span>" />
		</c:if>
		
	<tr>
		<c:if test="${is_ad_cms eq 'Y'}">
		<td><input type="checkbox" name="chk" value="${board.b_num}" title="해당 게시글 선택" /></td>
		<td>${recordcount - ((v_page-1) * pagesize + no.index) }</td>
		</c:if>
		<td><a href="${view_url }">${board.b_id }</a></td>
		<td><a href="${view_url }">${board.b_name }</a></td>
		<td><a href="${view_url }">${board.b_phone1 }-${board.b_phone2 }-${board.b_phone3 }</a></td>
		<td><a href="${view_url }">${b_temp4_1 }-${b_temp4_2 }-${b_temp4_3 }</a></td>
		<td><a href="${view_url }">${b_temp7_str }</a></td>
		<td><a href="${view_url }">${b_temp9_str}/${pagecnt } (${avg })</a></td>
		<td><a href="${view_url }">${fn:replace(fn:substring(board.b_regdate,0,10),"-", ".")}</a></td>
	<c:if test="${is_ad_cms eq 'Y'}">
		<td>
			${b_temp10_str }
		</td>
		<td>
			<input type="button" value="보기" onclick="viewDiray('${board.b_id}');" class="board_button3" style="padding:3px;" />
			<input type="button" value="엑셀" onclick="excelDiray('${board.b_id}');" class="board_button4" style="padding:3px;" />
		</td>
	</c:if>
	</tr>
	</c:forEach>
	<c:if test="${fn:length(boardList) == 0}">
		<tr>
			<td class="center" colspan="${colCnt}">등록된 내용이 없습니다.</td>
		</tr>
	</c:if>

			</tbody>
			</table>
	</div>
	
	</div>
	<!-- //리스트 테이블 -->
	
	

	<!-- 버튼 -->
	<div class="board_button">
		<c:if test="${is_ad_cms eq 'Y'}">
		<div class="fl">
			<ul>
				<li class="pt"><a href="javascript:checkAll();" class="board_lbtn"><span>전체 선택/해제</span></a></li>
				<li class="pt"><a href="javascript:delete2();" class="board_lbtn"><span>선택 게시글삭제</span></a></li>
				<li class="pt">
					선택한 참가자를
					<select name="tot_level_chk" style="vertical-align:middle;height:27px;border:1px solid #dbdbdb;padding-left:8px;font-size:11px;">
						<option value="0">심사대기</option>
						<option value="1">완주완료</option>
						<option value="2">완주실패</option>
					</select>로 <input type="button" value="변경" onclick="statusChange();" class="board_button3" style="padding:3px;height:27px;" />
				</li>
			</ul>
		</div>
		</c:if>
		<div class="fr">
			&nbsp;
		</div>

	</div>
	<!-- //버튼 -->
	
	<c:if test="${is_ad_cms eq 'Y'}">
	</form>
	</c:if>

	<!-- 페이징 -->
	<div class="board_paginate">
		<c:if test="${is_ad_cms eq 'Y' }">
		${pagingtag }
		</c:if>
	</div>
	<!-- //페이징 -->

<c:if test="${is_ad_cms eq 'Y' }">
	<!-- 게시물 검색 -->
	<div style="margin:0 auto;text-align:center;">
		<div class="board_search">
			<form id="frm_sch" action="${path_info }" method="get">
			<input type="hidden" name="a_num" value="${config.a_num}" />
			<input type="hidden" name="v_cate" value="${param.v_cate}" />
			<input type="hidden" name="sh_course" value="${param.sh_course}" />
				<fieldset>
				<legend>게시물 검색</legend>
				<ul>
					<li><select id="v_search" name="v_search" title="검색형태 선택" style="width:70px;" >
						<option value="b_id" ${"b_id" == param.v_search ? 'selected="selected"' : '' }>아이디</option>
						<option value="b_name" ${"b_name" == param.v_search ? 'selected="selected"' : '' }>이름</option>
						<option value="b_phone" ${"b_phone" == param.v_search ? 'selected="selected"' : '' }>전화번호</option>
						<option value="b_mobile" ${"b_mobile" == param.v_search ? 'selected="selected"' : '' }>휴대전화</option>
						<option value="b_email" ${"b_email" == param.v_search ? 'selected="selected"' : '' }>이메일</option>
					</select></li>
					<li><input type="text" size="25" title="검색어를 입력하세요" id="p_keyword" name="v_keyword" class="search_input" value="${param.v_keyword}" /></li>
					<li><input type="image" src="/nanum/site/board/nninc_simple/img/search_bt.gif" id="search_bt" name="search_bt" class="search_bt" alt="검색" /></li>
				</ul>
				</fieldset>
			</form>
		</div>
	</div>
	<!-- //게시물 검색 -->
</c:if>


</div>
<!-- //리스트 -->