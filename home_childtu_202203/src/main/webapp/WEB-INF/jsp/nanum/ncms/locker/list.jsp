<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>


<jsp:useBean id="now" class="java.util.Date" />
<c:set var="nowyear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>



<script type="text/javascript" src="/nanum/site/locker/js/common.js"></script>
<script type="text/javascript">
function deleteOk(lc_num){
	if(confirm("사물함신청을 삭제하시겠습니까?")){
		location.href="deleteOk.do?lc_num="+lc_num+"&prepage=${nowPageEncode}";
	}
}
</script>
	<h1 class="tit"><span>사물함신청</span> 리스트</h1>
	
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
	
			<form id="frm_sch" action="list.do" method="get">
			<!-- 검색 -->
			<div class="top_search_area mt10">
				<ul>
					<li class="tit"><label for="ct_name_i"><h3 class="tit">신청자검색 :</h3></label></li>
					<li class="sel">
		
						<select id="sh_lc_libcode" name="sh_lc_libcode" title="도서관 선택" class="t_search" style="width:150px;">
							<option value="">도서관선택</option>
<c:forEach items="${libList }" var="lib">
							<option value="${lib.ct_idx }" ${param.sh_lc_libcode eq lib.ct_idx ? 'selected="selected"' : '' }>${lib.ct_name }</option>
</c:forEach>
						</select>
		
						<select id="sh_lc_year" name="sh_lc_year" title="연도 선택" class="t_search" style="width:80px;">
							<option value="">연도</option>
<c:forEach begin="2019" end="${nowyear+1 }" var="item">
							<option value="${item }" ${param.sh_lc_year eq item ? 'selected="selected"' : '' }>${item }년</option>
</c:forEach> 
						</select>
						<select id="sh_lc_season" name="sh_lc_season" title="차수 선택" class="t_search" style="width:100px;">
							<option value="">차수</option>
							<option value="1" ${param.sh_lc_season eq '1' ? 'selected="selected"' : '' }>1~6월</option>
							<option value="7" ${param.sh_lc_season eq '7' ? 'selected="selected"' : '' }>7~12월</option>
						</select>
		
						<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:100px;">
							<option value="lc_m_name" ${param.v_search eq 'lc_m_name' ? 'selected="selected"' : '' }>이름</option>
							<option value="lc_m_id" ${param.v_search eq 'lc_m_id' ? 'selected="selected"' : '' }>아이디</option>
							<option value="lc_locker_num" ${param.v_search eq 'lc_locker_num' ? 'selected="selected"' : '' }>사물함번호</option>
						</select>
					</li>
					<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="메뉴 입력" name="v_keyword" id="v_keyword" type="text" value="${param.v_keyword }" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
					<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('list.do');" /></li>
<c:url var="excel_url" value="excel.do">
	<c:param name="sh_lc_libcode" value="${param.sh_lc_libcode }" />
	<c:param name="sh_lc_year" value="${param.sh_lc_year }" />
	<c:param name="sh_lc_season" value="${param.sh_lc_season }" />
	<c:param name="v_search" value="${param.v_search }" />
	<c:param name="v_keyword" value="${param.v_keyword }" />
</c:url>
					<li class="btn"><input type="button" value="엑셀출력" class="btn_green" onclick="location.href='${excel_url}';" /></li>
				</ul>
			</div>
			<!-- //검색 -->	
			</form>
	
	
		<form id= "frm_list" action="" method='post'>
			<div>
				<input type="hidden" name="status" />
	
				<input type="hidden" id="chk_all" name="chk_all" />
				<input type="hidden" name="v_search" value="${v_search}" />
				<input type="hidden" name="v_keyword" value="${v_keyword}" />
				<input type="hidden" name="prepage" value="${nowPage}" />
			</div>
	
	
			<div class="list_count" style="height:20px">			
				전체 <strong>${recordcount }</strong>건 (페이지 <strong class="orange">${v_page }</strong>/${totalpage })
			</div>
	
	
			<fieldset>
				<legend>사물함신청목록보기</legend>
				<table class="bbs_common bbs_default" summary="사이트의 사물함신청를 관리합니다.">
				<caption>사물함신청관리 서식</caption>
				<colgroup>
				<col width="40" />
				<col width="40" />
				
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
	
				<col width="50" />
				<col width="50" />
				</colgroup>
	
				<thead>
				<tr>
					<th scope="col">선택</th>
					<th scope="col">번호</th>
					
					<th scope="col">도서관</th>
					<th scope="col">신청기간</th>
					<th scope="col">사물함번호</th>
					<th scope="col">신청자명</th>
					<th scope="col">이용자번호</th>
					<th scope="col">연락처</th>
					<th scope="col">신청일자</th>
					
					<th scope="col">수정</th>
					<th scope="col">삭제</th>
				</tr>
				</thead>
	
				<tbody>
				
<c:forEach items="${lockerList }" var="locker" varStatus="no">
	<c:choose>
		<c:when test="${locker.lc_season eq '1' }">
			<c:set var="lc_season_str" value="1월  ~ 6월" />
		</c:when>
		<c:when test="${locker.lc_season eq '7' }">
			<c:set var="lc_season_str" value="7월  ~ 12월" />
		</c:when>
		<c:otherwise>
			<c:set var="lc_season_str" value="${locker.lc_season }" />
		</c:otherwise>
	</c:choose>
				<tr>
					<td class="center"><input type="checkbox" name="chk" value="${locker.lc_num }" title="해당 신청자 선택" /></td>
					<td class="center">${recordcount - ((v_page-1) * pagesize + no.index) }</td>
					<td class="center">${locker.lc_lib }</td>
					<td class="center">${locker.lc_year}년 ${lc_season_str }</td>
					<td class="center">${locker.lc_locker_num }</td>
					<td class="center" style="line-height:150%;">
						${locker.lc_m_name }
						<c:if test="${!empty locerk.lc_m_id }">
							<br />${locker.lc_m_name }
						</c:if>
					</td>
					<td class="center">${locker.lc_m_num }</td>
					<td class="center">${locker.lc_phone }</td>
					<td class="center">${locker.lc_regdate }</td>
					<td class="center"><a href="write.do?lc_num=${locker.lc_num }&prepage=${nowPageEncode}"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a></td>
					<td class="center"><a href="#delete" onclick="deleteOk('${locker.lc_num }');" ><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif"/></a></td>
				</tr>
</c:forEach>
<c:if test="${fn:length(lockerList) == 0 }">
				<tr>
					<td class="center" colspan="11">내용이 없습니다.</td>
				</tr>
</c:if>



				</tbody>
				</table>
			</fieldset>
	
			<!-- 하단버튼 -->
			<div id="contoll_area">
				<ul>
					<li class="btn_le">
						<p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 신청자 삭제</a></p>
					</li>
					<li class="btn_ri">
						&nbsp;
					</li>
				</ul>
			</div>
			<!-- //하단버튼 -->
	
	
			<!-- 페이징 -->
			<div class="paginate">
				${pagingtag }
			<!-- //페이징 -->
		
		</form>
	
	</div>
	<!-- 내용들어가는곳 -->