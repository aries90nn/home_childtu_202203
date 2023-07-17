<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_stock.js"></script>
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>

	<h1 class="tit"><span>투명우산</span> 재고관리</h1>
	
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
		<!-- 재고등록 -->
		<h2 class="default">재고 관리</h2>
		
		<table class="bbs_common bbs_default" summary="재고, 입고, 출고 수량 표." style="margin-bottom : 15px;">
			<caption>재고 수량 서식</caption>
			<colgroup>
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="" />
			</colgroup>
			
			<thead>
				<tr>
					<th scope="col">전년도 재고</th>
					<th scope="col">입고 수량</th>
					<th scope="col">출고 수량</th>
					<th scope="col">총 재고수량</th>
				</tr>
			</thead>
					
			<tbody>
				<tr>
					<td class="center"><fmt:formatNumber value="${sc_last }" pattern="#,###" /></td>
					<td class="center"><fmt:formatNumber value="${sc_receiv }" pattern="#,###" /></td>
					<td class="center"><fmt:formatNumber value="${sc_forward }" pattern="#,###" /></td>
					<td class="center"><fmt:formatNumber value="${sc_count }" pattern="#,###" /></td>
				</tr>
			</tbody>
		</table>

		<form name="frm" action="/${BUILDER_DIR}/ncms/stock/writeOk.do" method='post' id="form_write" onsubmit="return w_chk();">
			<input type="hidden" name="prepage" value="${nowPage}" />
			<input type="hidden" name="stock_cnt" value="${sc_count}" />
			<fieldset>
					<legend>재고 관리</legend>
					<table class="bbs_common bbs_default" summary="사이트의 회원을 관리합니다.">
					<caption>재고 관리 서식</caption>
					<colgroup>
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="" />
						<col width="10%" />
					</colgroup>
					<thead>
					<tr>
						<th scope="col"><label for="st_gubun">날짜</label></th>
						<th scope="col"><label for="st_work">작업</label></th>
						<th scope="col"><label for="st_cnt">수량</label></th>
						<th scope="col"><label for="st_program">프로그램 구분</label></th>
						<th scope="col"><label for="st_delivery">배송정보</label></th>
						<th scope="col"><label for="st_ext">발송처</label></th>
						<th scope="col"><label for="st_ext">비고</label></th>
						<th scope="col">등록</th>
					</tr>
					</thead>
		
					<tbody>
		
		
					<tr>
						<td class="center">
							<%--<select id="st_gubun" name="st_gubun" title="구분 선택" class="t_search" style="width:80px;">
								<option value="m">모비스 </option>
								<option value="f">재단 </option>
							</select> --%>
							<input type="text" size="100" id="st_date" name="st_date" class="ta_input jsCalendar" onfocus="focus_on1(this);" onblur="focus_off1(this);" maxlength="10" style="width:80px;" title="작업 날짜 선택" />
						</td>
						<td class="center">
							<select id="st_work" name="st_work" title="작업 선택" class="t_search" style="width:80px;">
								<option value="입고">입고 </option>
								<option value="출고">출고 </option>
							</select>
						</td>
						<td class="center">
							<input type="text" title="등록갯수 입력"  id="st_cnt" name="st_cnt" value="" class="ta_input" maxlength="9" onkeyup="setNum(this)" />
						</td>
						<td class="center">
							<select id="st_program" name="st_program" title="프로그램 선택" class="t_search" >
								<option value="">프로그램 선택</option>
								<option value="사연신청">사연신청</option>
								<option value="학교신청">학교신청</option>
								<option value="임직원나눔">임직원나눔</option>
								<option value="대리점/협력사나눔">대리점/협력사나눔</option>
								<option value="기타나눔">기타나눔</option>
							</select>
						</td>
						<td class="center">
							<select id="st_delivery" name="st_delivery" title="작업 선택" class="t_search" >
								<option value="">배송 선택 </option>
								<option value="택배배송">택배배송 </option>
								<option value="화물배송">화물배송 </option>
							</select>
						</td>
						<td class="center">
							<input type="text" title="등록자 입력"  id="st_history" name="st_history" value="" class="ta_input" maxlength="200" style="width:200px;" />
						</td>
						<td class="center">
							<input type="text" title="등록자 입력"  id="st_ext" name="st_ext" value="" class="ta_input" maxlength="200" style="width:200px;" />
						</td>
						<td class="center"><input type="submit" value="등록" class="btn_bl_default" /></td>
					</tr>
					</tbody>
					</table>
				</fieldset>
			</form>
	
			<!-- 검색 -->
			<form id="frm_sch" action="write.do" method="get">
				<div class="top_search_area mt10">
					<ul>
						<li class="tit"><label for="v_search"><h3 class="tit">내역 검색 :</h3></label></li>
						<li class="sel">
							<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
								<option value="st_date" ${v_search == 'st_date' ? 'selected="selected"' : '' }>날짜 </option>
								<option value="st_work" ${v_search == 'st_work' ? 'selected="selected"' : '' }>작업 </option>
								<option value="st_program" ${v_search == 'st_program' ? 'selected="selected"' : '' }>프로그램</option>
								<option value="st_delivery" ${v_search == 'st_delivery' ? 'selected="selected"' : '' }>배송 </option>
								<option value="st_history" ${v_search == 'st_history' ? 'selected="selected"' : '' }>발송처 </option>
								<option value="st_ext" ${v_search == 'st_ext' ? 'selected="selected"' : '' }>비고 </option>
							</select>	
						</li>
						<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="검색어 입력" name="v_keyword" id="v_keyword" type="text" value="${param.v_keyword}" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
						<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('./write.do');" /></li>
						<li class="btn"><input type="button" value="엑셀다운로드" class="btn_bl_default" onclick="page_go1('./excel.do?v_search=${v_search}&v_keyword=${v_keyword }');" /></li>
					</ul>
				</div>
			</form>
			<!-- //검색 -->
	
	
			<form id= "frm_list" action="" method='post'>
				<div>
					<input type="hidden" name="status" />
		
					<input type="hidden" id="chk_all" name="chk_all" />
					<input type="hidden" name="v_search" value="${v_search}" />
					<input type="hidden" name="v_keyword" value="${v_keyword}" />
					<input type="hidden" name="prepage" value="${nowPage}" />
				</div>
		
		
				<div class="list_count" style="height:20px">			
					전체 <strong>${recordcount}</strong>건 (페이지 <strong class="point_default">${v_page}</strong>/${totalpage})
				</div>
		
		
				<fieldset>
					<legend>투명우산 재고관리 로그</legend>
					<table class="bbs_common bbs_default" summary="투명우산 재고관리 로그를 관리합니다.">
					<caption>투명우산 재고관리 로그 서식</caption>
					<colgroup>
					<col width="50" />
					<col width="50" />
					
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
					<col width="" />
					
					<col width="20%" />
					</colgroup>
		
					<thead>
					<tr>
						<th scope="col">선택</th>
						<th scope="col">번호</th>
						
						<th scope="col">날짜</th>
						<th scope="col">작업</th>
						<th scope="col">수량</th>
						<th scope="col">프로그램 구분</th>
						<th scope="col">배송정보</th>
						<th scope="col">발송처</th>
						<th scope="col">비고</th>
						
						<th scope="col">등록날짜</th>
					</tr>
					</thead>
		
					<tbody>
					
	
	
					<c:forEach items="${stockList}" var="stock" varStatus="no">
					<tr>
						<td class="center"><input type="checkbox" name="chk" value="${stock.st_idx}" title="해당 로그 선택" /></td>
						<td class="center">${no.count }</td>
						
						<td class="center">${stock.st_date }</td>
						<td class="center p0">${stock.st_work }</td>
						<td class="center eng"><fmt:formatNumber value="${stock.st_cnt }" pattern="#,###" /></td>
						<td class="center">${stock.st_program }</td>
						<td class="center">${stock.st_delivery }</td>
						<td class="center">${stock.st_history }</td>
						<td class="center">${stock.st_ext }</td>
						
						<td class="center eng">${stock.st_regdate }</td>
					</tr>
					</c:forEach>
					<c:if test="${fn:length(stockList) == 0}">
						<tr>
							<td scope="row" class="center" colspan="11">데이터가 없습니다.</td>
						</tr>
					</c:if>
	
	
					</tbody>
					</table>
				</fieldset>
		
				<!-- 하단버튼 -->
				<div id="contoll_area">
					<ul>
						<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 내역 삭제</a></p></li>
						<!-- 
						<p><a onclick="javascript:window.open('listMove.do','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p>
						<li class="btn_ri">
							<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif" />&nbsp;선택한 통합팝업을</p>
							<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 통합팝업 사용여부 선택" class="t_search" style="width:70px;">
								<option value="Y" selected="selected">사용</option>
								<option value="N" >중지</option>
							</select></p>
							<p>(으)로</p>
							<p><a href="javascript:tot_levelchage();" class="btn_gr">변경</a></p>
						</li>
						 -->
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
	<script type="text/javascript">
//<![CDATA[

var org_sdate = $("#st_date").val();

$(function(){
	$("#st_date").attr('readonly', true);

	$('#st_date').helloCalendar({'selectBox':true});

	$('.jsCalendar').attr("readonly", true);

});
//]]>
</script>