<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_member.js"></script>
<script type="text/javascript">
function deleteOk(m_num){
	if(confirm("회원을 삭제하시겠습니까?")){
		location.href="deleteOk.do?m_num="+m_num+"&prepage=${nowPageEncode}";
	}
}
</script>

<h1 class="tit"><span>회원</span> 리스트</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

<form id="frm_sch" action="list.do" method="get">
<!-- 검색 -->
<div class="top_search_area mt10">
	<ul>
		<li class="tit"><label for="ct_name_i"><h3 class="tit">회원검색 :</h3></label></li>
		<li class="sel">
			<select id="v_gnum" name="v_gnum" title="그룹 선택" class="t_search" style="width:200px;">
				<option value="">그룹전체</option>
				<c:forEach items="${membergroupList}" var="mgList" varStatus="no">
				<option value="${mgList.g_num}" ${mgList.g_num == v_gnum ? 'selected="selected"' : '' }>${mgList.g_menuname}</option>
				</c:forEach>
			</select>
			<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
				<option value="m_name"  ${"m_name" == v_search ? 'selected="selected"' : '' }>이름</option>
				<option value="m_id"  ${"m_id" == v_search ? 'selected="selected"' : '' }>아이디</option>
				<option value="m_date" ${"m_date" == v_search ? 'selected="selected"' : '' }>가입일</option>
			</select>
		</li>
		<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="메뉴 입력" name="v_keyword" id="v_keyword" type="text" value="${v_keyword}" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
		<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('list.do');" /></li>
	</ul>
</div>
<!-- //검색 -->
</form>


<form id= "frm_list" action="" method='post'>
	<div>			
		<input type="hidden" name="status" />
		<input type="hidden" name="m_level" />
		<input type="hidden" name="m_num" />
		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" name="v_gnum" value="${v_gnum}" />
		<input type="hidden" name="v_search" value="${v_search}" />
		<input type="hidden" name="v_keyword" value="${v_keyword}" />
		<input type="hidden" name="prepage" value="${nowPage}" />
	</div>


	<fieldset>
		<legend>회원관리 수정/삭제/보기</legend>
		<table class="bbs_common bbs_default" summary="사이트의 회원을 관리합니다.">
		<caption>회원관리 서식</caption>
		<colgroup>
		<col width="40" />
		<col width="50" />
		<col width="" />
		<col width="" />
		<col width="" />
		<col width="12%" />
		<col width="12%" />
		<col width="12%" />
		<col width="50" />
		<col width="50" />
		</colgroup>

		<thead>
		<tr>
			<th scope="col">선택</th>
			<th scope="col">번호</th>
			<th scope="col">아이디</th>
			<th scope="col">이름</th>
			<th scope="col">그룹</th>
			<th scope="col">최근접속일</th>
			<th scope="col">가입일</th>
			<th scope="col">탈퇴일</th>
			<th scope="col">수정</th>
			<th scope="col">삭제</th>
		</tr>
		</thead>

		<tbody>
		
		<c:forEach items="${memberList}" var="member" varStatus="no">
		<tr>
		<td class="center"><input type="checkbox" name="chk" value="${member.m_num}" title="해당 회원 선택" /></td>
			<td class="center">${recordcount - ((v_page-1) * pagesize + no.index) }</td>
			<td class="center eng">${member.m_id}</td>
			<td class="center">${member.m_name}</td>
			<td class="center">${member.lvlname}</td>
			<td class="center eng">${member.m_lastdate}</td>
			<td class="center eng">${fn:substring(member.m_date, 0, 10)}</td>
			<td class="center eng">${fn:substring(member.m_secede_date, 0, 10)}</td>
			<td class="center"><a href="write.do?m_num=${member.m_num}&prepage=${nowPageEncode}"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a></td>
			<td class="center"><a href="javascript: deleteOk('${member.m_num}');" ><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif"/></a></td>
		</tr>
		</c:forEach>
		<c:if test="${fn:length(memberList) == 0}">
			<tr>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
				<td class="center"></td>
			</tr>
		</c:if>

		</tbody>
		</table>
	</fieldset>

	<!-- 하단버튼 -->
	<div id="contoll_area">
		<ul>
			<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 회원삭제</a></p></li>
			<li class="btn_ri">
				<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 회원을</p>
				<p><select id="tot_level_chk" name="tot_m_level" title="선택한 회원그룹 선택" class="t_search" style="width:90px;">
					<c:forEach items="${membergroupList}" var="mgList" varStatus="no">
					<option value="${mgList.g_num}" >${mgList.g_menuname}</option>
					</c:forEach>
				</select></p>
				<p>(으)로</p>
				<p><a href="javascript:tot_levelchage();" class="btn_gr">변경</a></p>
			</li>
		</ul>
	</div>
	<!-- //하단버튼 -->

</form>
	

	<!-- 페이징 -->
	<div class="paginate">
		${pagingtag }
	</div>
	<!-- //페이징 -->

</div>
<!-- 내용들어가는곳 -->