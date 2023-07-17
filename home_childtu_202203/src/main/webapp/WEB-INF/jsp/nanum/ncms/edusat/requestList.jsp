<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator"
	content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<c:set var="prepage"
	value="${empty param.prepage ? 'list.do' : param.prepage }" />
<c:set var="queryString"
	value="${empty pageContext.request.queryString ? '' : pageContext.request.queryString }" />

<script type="text/javascript"
	src="/nanum/ncms/common/js/ncms_edusat.js"></script>
<script type="text/javascript">
	//<![CDATA[

	function deleteRequest(edu_idx, es_idx) {
		if (confirm("신청자정보를 삭제하시겠습니까?")) {
			location.href = "delete2_ok.php?edu_idx="
					+ edu_idx
					+ "&es_idx="
					+ es_idx
					+ "&prepage=%2Fnninc%2Fcontent%2F04edusat%2Frequest_list.php%3Fedu_idx%3D29%26es_idx%3D%26prepage%3D%252Fnninc%252Fcontent%252F04edusat%252Flist.php";
		}
	}

	function SetNum(obj) { //숫자만 입력
		val = obj.value;
		re = /[^0-9]/gi;
		obj.value = val.replace(re, "");
	}

	function lotTypeShow() {
		var obj = document.getElementById("edu_receive_type_2");
		var tr = document.getElementById("tr_lot_type");
		if (obj.checked == true) {
			tr.style.display = "";
		} else {
			tr.style.display = "none";
		}
	}

	function chkAll(obj) {
		$("#frm_request input[name^='es_idx']").each(function() {
			if (!$(this).prop("disabled")) {
				$(this).attr("checked", obj.checked);
			}
		});
	}

	function userRequest(edu_idx, es_idx) {
		window.open('request.php?edu_idx=' + edu_idx + '&es_idx=' + es_idx,
				'request', 'width=600, height=500');
	}
	//]]>
</script>


<h1 class="tit">신청자 리스트</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

	<h2 class="tit">프로그램 정보</h2>

	<fieldset>
		<legend>프로그램생성 서식 작성</legend>
		<table cellspacing="0" class="bbs_common bbs_default"
			summary="신규 프로그램 생성을 위한 입력 양식입니다.">
			<caption>프로그램생성 서식</caption>
			<colgroup>
				<col width="140" />
				<col width="580" />
			</colgroup>

			<tr>
				<th scope="row">프로그램분류</th>
				<td class="left">${edusat.edu_gubun }</td>
			</tr>
			<tr>
				<th scope="row">프로그램명</th>
				<td class="left">${edusat.edu_subject }</td>
			</tr>
			<c:set var="edu_resdate_h" value="${edusat.edu_resdate_h }" />
			<c:if test="${10 > edu_resdate_h }">
				<c:set var="edu_resdate_h" value="0${edusat.edu_resdate_h }" />
			</c:if>
			<c:set var="edu_reedate_h" value="${edusat.edu_reedate_h }" />
			<c:if test="${10 > edu_reedate_h }">
				<c:set var="edu_reedate_h" value="0${edusat.edu_reedate_h }" />
			</c:if>
			<fmt:parseNumber value="${edusat.req_count}" pattern="0,000.00"
				var="req_count" />
			<fmt:parseNumber value="${edusat.edu_inwon}" pattern="0,000.00"
				var="edu_inwon" />
			<tr>
				<th scope="row">접수기간</th>
				<td class="left">${edusat.edu_resdate }${edu_resdate_h }:00 ~
					${edusat.edu_reedate } ${edu_reedate_h }:59</td>
			</tr>
			<!-- 
						<tr>
							<th scope="row">교육기간</th>
							<td class="left">${edusat.edu_sdate } ~ ${edusat.edu_edate  }</td>
						</tr>
						 -->
			<tr>
				<th scope="row">신청인원</th>
				<td class="left"><c:if test="${req_count >= edu_inwon}">
						<span style='font-weight: bold; color: red;'>${edusat.req_count}</span> 명
								</c:if> <!-- 
								<c:if test="${req_count < edu_inwon}">
									<span class="sky_blue" style='font-weight:bold;'>${edusat.req_count}</span>
								</c:if> / <strong>${edusat.edu_inwon } 명</strong>
								 --></td>
			</tr>
		</table>


		<div class="contoll_box">

			<span><input type="button" value="돌아가기"
				onclick="location.href='${prepage}';" class="btn_wh_default" /></span>
		</div>

		<br />
		<br />
		<c:url var="reset_url" value="requestList.do">
			<c:param name="edu_idx" value="${edusat.edu_idx }" />
			<c:param name="prepage" value="${prepage }" />
		</c:url>
		<c:url var="excel_url" value="excel.do">
			<c:param name="edu_idx" value="${edusat.edu_idx }" />
			<c:param name="v_search2" value="${param.v_search2 }" />
			<c:param name="v_keyword2" value="${param.v_keyword2 }" />
			<c:param name="v_status" value="${param.v_status }" />
		</c:url>
		<form id="frm_search" method="get" action="requestList.do">
			<input type="hidden" name="edu_idx" value="${edusat.edu_idx }" /> <input
				type="hidden" name="prepage" value="${prepage }" />
			<div class="top_search_area mt10">
				<ul>
					<li class="tit"><label for="ct_name_i"><h3 class="tit">신청자검색
								:</h3></label></li>
					<li class="sel"><select id="v_status" name="v_status"
						title="프로그램 선택" tabindex="5001" class="t_search">
							<option value=""
								${param.v_status eq '' ? 'selected="selected"' : '' }>신청상태</option>
							<option value="0"
								${param.v_status eq '0' ? 'selected="selected"' : '' }>신청완료</option>
							<option value="9"
								${param.v_status eq '9' ? 'selected="selected"' : '' }>신청취소</option>
							<option value="3"
								${param.v_status eq '3' ? 'selected="selected"' : '' }>선정완료</option>
							<option value="4"
								${param.v_status eq '4' ? 'selected="selected"' : '' }>선정미완료</option>
					</select></li>
					<li class="sel"><select id="v_search2" name="v_search2"
						title="프로그램 선택" tabindex="5001" class="t_search">
							<option value="es_name"
								${param.v_search2 eq 'es_name' ? 'selected="selected"' : '' }>신청자명</option>
					</select></li>
					<li class="search"><label for="v_keyword2">검색어를 입력하세요</label><input
						title="메뉴 입력" name="v_keyword2" id="v_keyword2" type="text"
						value="${param.v_keyword2 }" class="search_input autoInput" /><input
						class="search_icon" type="image" alt="검색"
						src="/nanum/ncms/img/common/search_btn.gif" /></li>
					<li class="btn"><input type="button" value="전체보기"
						class="btn_gr_default" onclick="location.href='${reset_url}';" /></li>

					<li class="btn"><input type="button" value="현재인원엑셀저장"
						class="btn_bl_default" onclick="location.href='${excel_url}';" /></li>
					<c:if test="${edusat.edu_lot_type eq '1'}">
						<li class="btn"><input type="button" value="자동추첨"
							class="btn_gr_default"
							onclick="location.href='winOk.do?p_type=win&edu_idx=${edusat.edu_idx}&prepage=${nowPageEncode}';" /></li>
						<li class="btn"><input type="button" value="초기화"
							style="color: #FF0000;" class="btn_wh_default"
							onclick="location.href='winOk.do?edu_idx=${edusat.edu_idx}&prepage=${nowPageEncode}';" /></li>
					</c:if>
				</ul>
			</div>
		</form>


		<div class="list_count">
			<!--전체 <strong>14</strong>명 신청  (페이지 <strong class="orange">1</strong>/2) -->
		</div>



		<form id="frm_list" method="get" action="requestList.do">
			<input type="hidden" name="status" /> <input type="hidden"
				name="edu_idx" value="${edusat.edu_idx }" /> <input type="hidden"
				id="prepage" name="prepage" value="${nowPage }" /> <input
				type="hidden" id="chk_all" name="chk_all" />
			<fieldset>
				<legend>회원관리 수정/삭제/보기</legend>

				<table cellspacing="0" class="bbs_common bbs_default"
					summary="사이트의 회원을 관리합니다.">
					<caption>회원관리 서식</caption>
					<colgroup>
						<col width="50" />
						<col width="50" />
						<col width="90" />
						<col />
						<col />
						<col />
						<col />
						<col />
						<col width="180" />
						<col width="65" />
						<col width="65" />
						<!-- <col width="55" /> -->
					</colgroup>

					<thead>
						<tr>
							<th scope="col">선택</th>
							<th scope="col">번호</th>
							<th scope="col">상태</th>
							<th scope="col">성명</th>
							<th scope="col">수령자명(신청학교)</th>
							<th scope="col">기관명</th>
							<th scope="col">휴대전화</th>
							<th scope="col">신청수량</th>
							<th scope="col">신청일</th>
							<th scope="col">수정</th>
							<th scope="col">삭제</th>
							<!-- <th scope="col">경고</th> -->
						</tr>
					</thead>

					<tbody>

						<c:set var="accnum" value="0"></c:set>
						<c:forEach items="${edusatRequestList}" var="edusatRequest"
							varStatus="no">
							<c:if test="${edusatRequest.es_status ne '9' }">
								<c:set var="accnum" value="${accnum+ edusatRequest.es_ptcp_cnt}" />
							</c:if>

							<c:choose>
								<c:when test="${edusat.edu_lot_type eq '2' }">
									<c:set var="es_status_str"
										value="<span style='color:#7c7c7c;'>신청완료</span>" />
									<c:if test="${accnum > edusat.edu_inwon }">
										<c:set var="es_status_str" value="대기자"></c:set>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:set var="es_status_str"
										value="<span style='color:#7c7c7c;'>신청완료</span>" />
									<c:if test="${edusatRequest.es_status eq '3'}">
										<c:set var="es_status_str"
											value="<span style='color:#5C7DF8;'>선정완료</span>" />
									</c:if>
									<c:if test="${edusatRequest.es_status eq '4'}">
										<c:set var="es_status_str"
											value="<span style='color:#f00;'>선정미완료</span>" />
									</c:if>
									<c:if test="${edusatRequest.es_status eq '2'}">
										<c:set var="es_status_str" value="낙첨"></c:set>
									</c:if>
								</c:otherwise>
							</c:choose>
							<c:if test="${edusatRequest.es_status eq '9' }">
								<c:set var="es_status_str"
									value="<span style='color:#f00;'>신청취소</span>" />
							</c:if>

							<c:set var="blacklist_url"
								value="/ncms/edusat_blacklist/writePop.do?es_idx=${edusatRequest.es_idx}" />
							<c:if test="${!empty BUILDER_DIR }">
								<c:set var="blacklist_url"
									value="/${BUILDER_DIR }${blacklist_url }" />
							</c:if>

							<tr>
								<td scope="row" class="center eng"><input type="checkbox"
									name="chk" value="${edusatRequest.es_idx}" /></td>
								<td scope="row" class="center eng">${no.count}</td>
								<td scope="row" class="center">${es_status_str}</td>
								<td scope="row" class="center"><a href="#reqview"
									onclick="window.open('request.do?edu_idx=${edusat.edu_idx}&es_idx=${edusatRequest.es_idx}','request', 'width=600, height=640, scrollbars=yes');"><span
										class="orange">${edusatRequest.es_name}</span> <c:if
											test="${!empty edusatRequest.edu_m_id }">
									(ID : ${edusatRequest.edu_m_id })
								</c:if> </a></td>
								<td scope="row" class="center eng">${edusatRequest.es_temp2}</td>
								<td scope="row" class="center eng">${edusatRequest.es_name2}</td>
								<td scope="row" class="center eng">${edusatRequest.es_phone1}-${edusatRequest.es_phone2}-${edusatRequest.es_phone3}</td>
								<td scope="row" class="center">${edusatRequest.es_etc}개</td>
								<td scope="row" class="center eng">${edusatRequest.es_wdate}</td>
								<td scope="row" class="center"><input type="button"
									value="수정" class="btn_bl_default"
									onclick="window.open('request.do?edu_idx=${edusat.edu_idx}&es_idx=${edusatRequest.es_idx}','request', 'width=700, height=640, scrollbars=yes');" /></td>
								<td scope="row" class="center"><input type="button"
									value="삭제" class="btn_gr_default "
									onclick="d_chk('delete2Ok.do?edu_idx=${edusat.edu_idx}&es_idx=${edusatRequest.es_idx}&prepage=${nowPageEncode}');" /></td>
								<!-- <td scope="row" class="center"><input type="button" value="경고" class="ct_bt01" onclick="window.open('${blacklist_url}','request', 'width=600, height=500');" /></td> -->
							</tr>
						</c:forEach>
						<c:if test="${fn:length(edusatRequestList)==0 }">

							<tr>
								<td scope="row" class="center" colspan="9">등록된 신청자 없습니다.</td>
							</tr>
						</c:if>
					</tbody>
				</table>

				<div id="contoll_area">
					<ul>
						<li class="btn_le">
							<p>
								<a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a>
							</p>
							<p>
								<a href="javascript:delete2();" class="btn_gr">선택 신청자삭제</a>
							</p>
						</li>
						<li class="btn_ri">
							<p>
								<img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한
								신청자를
							</p>
							<p>
								<select id="tot_level_chk" name="tot_level_chk"
									title="선택한 신청자 상태 변경" class="t_search"
									style="width: 90px; position: absolute; opacity: 0; font-size: 12px; height: 30px;">
									<option value="0">신청완료</option>
									<option value="9">신청취소</option>
									<option value="3">선정완료</option>
									<option value="4">선정미완료</option>
								</select>
							</p>
							<p>(으)로</p>
							<p>
								<a href="javascript:tot_levelchage2();" class="btn_gr">변경</a>
							</p>
						</li>
					</ul>
				</div>
			</fieldset>
		</form>
</div>
<script>
function delete2(){
	var flag;

	if (confirm('정말 삭제하시겠습니까?'))	{
		flag = "Empty";
		for (var j = 0; j < document.getElementById('frm_list').elements.length; j++)	{

			if(document.getElementById('frm_list').elements[j].type=='checkbox'){ 
				if (document.getElementById('frm_list').elements[j].checked == true){
					flag = "Not Empty";
				}
			}
		}
		if (flag == "Empty")	{
			alert ("데이터를 선택해 주세요.");
			return ;
		}			
			loading_st(1);
			document.getElementById('frm_list').action = "delete2Ok.do";
			document.getElementById('frm_list').status.value = "totdel";
			document.getElementById('frm_list').submit();
	}else{
		return ;
	}	
}
</script>
<!-- 내용들어가는곳 -->