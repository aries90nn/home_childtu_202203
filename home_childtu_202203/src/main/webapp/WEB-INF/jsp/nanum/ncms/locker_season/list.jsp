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


<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>
<script type="text/javascript" src="/nanum/site/locker/js/common.js"></script>
<script type="text/javascript">

$(function(){
	//$('#ls_req_date').attr("readonly", true).helloCalendar({'selectBox':true});
	$("input[name^='ls_req_date']").each(function(){
		$(this).attr("readonly", true).helloCalendar({'selectBox':true});
	});
	
});

//저장
function w_chk(){

	if (CheckSpaces(document.getElementById('frm').ls_req_date, '신청일자')) { return false; }

	loading_st(1);

}

function deleteOk(ls_num){
	if(confirm("사물함신청차수를 삭제하시겠습니까?")){
		location.href="deleteOk.do?ls_num="+ls_num+"&prepage=${nowPageEncode}";
	}
}
</script>
	<h1 class="tit"><span>사물함신청차수</span> 관리</h1>
	
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
	
	<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk();">
	<input type="hidden" name="prepage" value="${nowPage }" />
		<!-- 검색 -->
		<div class="top_search_area mt10">
			<ul>
				<li class="tit"><label for="ls_lib"><h3 class="tit">사물함신청차수등록 :</h3></label></li>
				<li class="sel">
					<select id="ls_libcode" name="ls_libcode" title="도서관 선택" class="t_search" style="width:150px;">
<c:forEach items="${libList }" var="lib">
						<option value="${lib.ct_idx }">${lib.ct_name }</option>
</c:forEach>
					</select>	
				</li>
				<li class="sel">
					<select id="ls_year" name="ls_year" title="연도 선택" class="t_search" style="width:80px;">
<c:forEach begin="2019" end="${nowyear+1 }" var="item">
						<option value="${item }" ${item eq nowyear ? 'selected="selected"' : '' }>${item }년</option>
</c:forEach>
					</select>	
				</li>
				<li class="sel">
					<select id="ls_season" name="ls_season" title="차수 선택" class="t_search" style="width:80px;">
						<option value="1" >1~6월</option>
						<option value="7" >7~12월</option>
					</select>
				</li>

				<li class="search"><input title="신청시작일자를 입력" name="ls_req_date" id="ls_req_date" type="text" value="" class="search_input" placeholder="신청시작일자" style="width:100px;" /></li>

				<li class="sel">
					<select id="ls_req_time" name="ls_req_time" title="신청시간 선택" class="t_search" style="width:50px;">
<c:forEach begin="0" end="23" var="item">
						<option value="${item }" ${item eq 9 ? 'selected="selected"' : '' }>${item }시</option>
</c:forEach>
					</select>	
				</li>
				<li class="btn"><input type="submit" value="등록" class="btn_bl_default" /></li>

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
				<!-- 전체 <strong>${recordcount }</strong>건 (페이지 <strong class="orange">${v_page }</strong>/${totalpage }) -->
			</div>
	
	
			<fieldset>
				<legend>사물함신청차수관리 작성/수정</legend>
				<table class="bbs_common bbs_default" summary="사이트의 사물함신청차수를 관리합니다." style="width:800px;">
				<caption>사물함신청차수관리 서식</caption>
				<colgroup>
				<col width="40" />
				
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
					
					<th scope="col">도서관</th>
					<th scope="col">연도</th>
					<th scope="col">차수</th>
					<th scope="col">신청시작일자</th>
					<th scope="col">신청시작시간</th>
					
					<th scope="col">수정</th>
					<th scope="col">삭제</th>
				</tr>
				</thead>
	
				<tbody>
<c:forEach items="${seasonList }" var="season" varStatus="no">

				<tr>
					<td class="center"><input type="checkbox" name="chk" value="${season.ls_num }" title="해당 사물함신청차수 선택" /></td>				
					
					<td class="center">
						<select id="ls_lib${no.count }" name="ls_lib${no.count }" title="도서관 선택" class="t_search" style="width:150px;">
<c:forEach items="${libList }" var="lib">
						<option value="${lib.ct_idx }" ${season.ls_libcode eq lib.ct_idx}>${lib.ct_name }</option>
</c:forEach>
						</select>
					</td>
					<td class="center">
						<select id="ls_year${no.count }" name="ls_year${no.count }" title="연도 선택" class="t_search" style="width:80px;">
<c:forEach begin="2019" end="${nowyear+1 }" var="item">
							<option value="${item }" ${season.ls_year eq item ? 'selected="selected"' : '' }>${item }년</option>
</c:forEach>
						</select>
					</td>
	
					<td class="center">
						<select id="ls_season${no.count }" name="ls_season${no.count }" title="차수 선택" class="t_search" style="width:80px;">
							<option value="1" ${season.ls_season eq '1' ? 'selected="selected"' : '' }>1~6월</option>
							<option value="7" ${season.ls_season eq '7' ? 'selected="selected"' : '' }>7~12월</option>
						</select>	
					</td>
	
					<td class="center">
						<input title="신청일자를 입력" name="ls_req_date${no.count }" id="ls_req_date${no.count }" type="text" value="${season.ls_req_date }" class="ta_input" style="width:100px;" /></li>
					</td>
	
					<td class="center">
						<select id="ls_req_time${no.count }" name="ls_req_time${no.count }" title="신청시간 선택" class="t_search" style="width:50px;">
<c:forEach begin="0" end="23" var="item">
							<option value="${item }" ${season.ls_req_time eq item ? 'selected="selected"' : '' }>${item }시</option>
</c:forEach>						
						</select>	
					</td>
	
					<td class="center"><a href="#modify" onclick="frm_modify${no.count }(${season.ls_num });"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a>
	
						
						<script type="text/javascript">
							function frm_modify${no.count }(ls_num){
								var ls_num;
	
								if (CheckSpaces(document.getElementById('frm_list').ls_req_date1, '신청일자')) { return false; }
								//else if (CheckSpaces(document.getElementById('frm').sc_sitename_en, '영문명')) { return false; }
	
								else {
	
									loading_st(1);
									document.getElementById('frm_m').ls_lib.value	= document.getElementById('frm_list').ls_lib${no.count }.value;
									document.getElementById('frm_m').ls_year.value	= document.getElementById('frm_list').ls_year${no.count }.value;
									document.getElementById('frm_m').ls_season.value	= document.getElementById('frm_list').ls_season${no.count }.value;
									document.getElementById('frm_m').ls_req_date.value	= document.getElementById('frm_list').ls_req_date${no.count }.value;
									document.getElementById('frm_m').ls_req_time.value	= document.getElementById('frm_list').ls_req_time${no.count }.value;
									document.getElementById('frm_m').ls_num.value	= ls_num;
	
									document.getElementById('frm_m').action			= "writeOk.do";
									document.getElementById('frm_m').submit();
								
								}
	
	
								
							}
						</script>
					</td>
					<td class="center"><a href="#delete" onclick="deleteOk('${season.ls_num}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
					
				</tr>
</c:forEach>


				</tbody>
				</table>
			</fieldset>
	
			<!-- 하단버튼 -->
			<div id="contoll_area">
				<ul>
					<li class="btn_le">
						<p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 신청차수 삭제</a></p>
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
	

<form id= "frm_m" method='post' action="">
<div>
	<input type="hidden" name="ls_lib" />
	<input type="hidden" name="ls_year" />
	<input type="hidden" name="ls_season" />
	<input type="hidden" name="ls_req_date" />
	<input type="hidden" name="ls_req_time" />
	<input type="hidden" name="ls_num" />
	<input type="hidden" name="prepage" value="${nowPage }" />
</div>
</form>