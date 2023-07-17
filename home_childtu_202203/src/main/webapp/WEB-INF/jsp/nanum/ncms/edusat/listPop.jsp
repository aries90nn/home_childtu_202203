<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>등록된 독서문화프로그램 검색</title>

	<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/all.css" />
	<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/sub_layout.css" />
	<link rel="stylesheet" type="text/css" href="/nanum/site/board/nninc_simple/css/common.css" />
	<script type="text/javascript" src="/nanum/site/common/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_design.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
	<script type="text/javascript" src="/nanum/ncms/common/js/ncms_edusat.js"></script>
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/template.css" />
	<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>


</head>
<body>
<style>
/* Input,textarea,select */
.input_box {padding:6px 12px;border:1px solid #eaeaea;color:#666;font-size:13px;}
.input_box2 {padding:4px 8px;border:1px solid #eaeaea;color:#666;font-size:13px;}
textarea {border:1px solid #cdcdcd; font-size: 1em; width:98%;}
select, textarea {vertical-align: middle; font-size:1em; font-family:Dotum,sans-serif;}
input.btn_wh_zipcode {display:inline-block; padding:4px 8px 5px 8px; *padding:4px 2px; font-size:12px; color:#484848; font-family:nanumB; text-decoration:none; background:none; border:1px solid #d4d4d4; vertical-align:top;}

</style>
<p style="padding:16px 0;text-align:center;background:#569fc9;font-weight:bold;font-size:20px;color:#fff;">등록된 강좌 검색</p>
<div style="padding:10px 15px">
<div style="padding:15px 0 20px 5px;">
	<form id="frm" method="get" action="listPop.do" onsubmit="return sendit(this)">
		<strong>검색 : </strong>

		<c:if test="${libList != null}">
			<select id="sh_ct_idx" name="sh_ct_idx" title="도서관 선택" class="t_search" style="width:150px;height:25px;">
				<option value="" > -- 도서관선택 --</option>
				<c:forEach items="${libList}" var="lib" varStatus="no">
					<option value="${lib.ct_idx}" ${sh_ct_idx eq lib.ct_idx ? 'selected="selected"' : '' }>${lib.ct_name}</option>
				</c:forEach>
				</select>
		</c:if>
		<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;height:25px;">
			<option value="all" ${v_search eq 'all' ? 'selected="selected"' : '' }>전체</option>
			<option value="edu_subject" ${v_search eq 'edu_subject' ? 'selected="selected"' : '' }>제목</option>
		</select>
		<input type="text" id="v_keyword" name="v_keyword" style="width:150px" value="${v_keyword}" />
		<input type="submit" value="검색" class="cbtn_mini orange" />
	</form>
</div>

		
<form id= "frm_list" action="" method='post'>
<div>
	<input type="hidden" name="status" />
	<input type="hidden" name="m_level" />
	<input type="hidden" name="m_num" />
	<input type="hidden" id="chk_all" name="chk_all" />
</div>

<table class="table1">
	<caption>회원관리 서식</caption>
	<colgroup>
	<col width="6%" />
	<col />
	<col width="8%" />
	<col width="25%" />
	<col width="8%" />
	<col width="8%" />
	</colgroup>

	<thead>
	<tr>
		<th scope="col">번호</th>
		<th scope="col">제목</th>
		<th scope="col">모집인원</th>
		<th scope="col">접수방식</th>
		<th scope="col">선택</th>
	</tr>
	</thead>

	<c:forEach items="${edusatList}" var="edusat" varStatus="no">
	<c:set var="receive_str" value=""></c:set>
	<c:set var="br" value=""></c:set>
	<c:choose>
		<c:when test="${fn:contains(edusat.edu_receive_type, '0')}">
			<c:set var="receive_str" value="${receive_str}${br}방문접수"></c:set>
			<c:set var="br" value="<br />"></c:set>
		</c:when>
		<c:when test="${fn:contains(edusat.edu_receive_type, '1')}">
			<c:set var="receive_str" value="${receive_str}${br}전화접수"></c:set>
			<c:set var="br" value="<br />"></c:set>
		</c:when>
		<c:when test="${fn:contains(edusat.edu_receive_type, '2')}">
			<c:set var="receive_str" value="${receive_str}${br}인터넷접수"></c:set>
			<c:set var="br" value="<br />"></c:set>
			<c:choose>
				<c:when test="${edusat.edu_lot_type eq '1'}">
					<c:set var="receive_str" value="${receive_str}(추첨식)"></c:set>
				</c:when>
				<c:when test="${edusat.edu_lot_type eq '2'}">
					<c:set var="receive_str" value="${receive_str}(선착순 / 대기:${edusat.edu_awaiter})"></c:set>
				</c:when>
				<c:when test="${edusat.edu_login eq 'Y'}">
					<c:set var="receive_str" value="${receive_str}${br}회원접수"></c:set>
				</c:when>
				<c:when test="${edusat.edu_login ne 'Y'}">
					<c:set var="receive_str" value="${receive_str}${br}비회원접수"></c:set>
				</c:when>
			</c:choose>
		</c:when>
	</c:choose>
	<tr>
		<td style="text-align:center;padding:5px 5px">${recordcount - ((v_page-1) * pagesize + no.index) }</td>
		<td style="text-align:left;padding:7px 7px">
		<span style="color:gray;"><strong>[${edusat.edu_lib}<c:if test="${edusat.edu_gubun2 ne ''}"> > ${edusat.edu_gubun2}</c:if>]</strong></span>
		<br/><c:if test="${edusat.edu_gubun ne ''}">[${edusat.edu_gubun}]</c:if> ${edusat.edu_subject}</td>
		<td style="text-align:center;padding:5px 5px">${edusat.edu_inwon} 명</td>
		<td style="text-align:center;padding:5px 5px">${receive_str}</td>
		<td style="text-align:center;padding:5px 5px"><a href="javascript:;" onclick="frm_reg${no.index}();"><strong style="color:#5a6ab8;">선택</strong></a>
		
		<script type="text/javascript">
			function frm_reg${no.index}(){
				opener.document.getElementById('frm').edu_subject.value			= "${edusat.edu_subject}";
				opener.document.getElementById('frm').edu_teacher.value			= "${edusat.edu_teacher}";
				opener.document.getElementById('frm').ct_idx2_tmp.value			= "${edusat.ct_idx2}";
				opener.$("#ct_idx").val("${edusat.ct_idx}").attr("selected", "selected").change();
				opener.document.getElementById('frm').edu_time.value				= "${edusat.edu_time}";
				opener.document.getElementById('frm').edu_money.value			= "${edusat.edu_money}";
				opener.document.getElementById('frm').edu_target.value				= "${edusat.edu_target}";
				opener.document.getElementById('frm').edu_inwon.value				= "${edusat.edu_inwon}";
				opener.document.getElementById('frm').edu_temp2.value				= "${edusat.edu_temp2}";
				opener.$("#ct_idx").val("${edusat.ct_idx}").attr("selected", "selected").change();
				
				//에디터생성
				ParentSEditorCreate("edu_content${no.index}");
				
				<c:if test="${fn:contains(edusat.edu_lot_type, '1')}">
					opener.$('#edu_lot_type_1').prop('checked',true);
				</c:if>
				<c:if test="${fn:contains(edusat.edu_lot_type, '2')}">
					opener.$('#edu_lot_type_2').prop('checked',true);
				</c:if>
				<c:if test="${edusat.edu_login eq 'Y'}">
					opener.$('#edu_login').prop('checked',true);
				</c:if>
				opener.document.getElementById('frm').edu_awaiter.value		= "${edusat.edu_awaiter}";
				self.close();
			}
		</script>
		<textarea name="edu_content${no.index}" id="edu_content${no.index}" style="display:none;">${edusat.edu_content}</textarea>
		
		</td>
	</tr>
	</c:forEach>
	<c:if test="${fn:length(edusatList) == 0}">
		<tr>
			<td colspan="6" style="text-align:center;">데이터가 없습니다.</td>
		</tr>
	</c:if>
</table>
</form>

<!-- 페이징 -->
<div class="paginate">
	<c:if test="${prevPage > 0 }">
	<a href="?v_page=${prevPage}&${pageInfo}&${query_string}" class='pre'>이전</a>
	</c:if>
	<c:forEach var="i" begin="${firstPage}" end="${lastPage}">
	<c:choose>
		<c:when test="${v_page == i }">
			<strong>${i}</strong>
		</c:when>
		<c:otherwise>
			<a href="?v_page=${i}&${pageInfo}&${query_string}">${i}</a>
		</c:otherwise>
	</c:choose>
	</c:forEach>
	<c:if test="${nextPage > 0 }">
		<a href="?v_page=${nextPage}&${pageInfo}&${query_string}" class='next'>다음</a>
	</c:if>

</div>
<!-- //페이징 -->

<form id= "frm_reg" method='post' action="">
<div>
	<input type="hidden" name="m_id" />
	<input type="hidden" name="m_gubun" />
	<input type="hidden" name="m_gubun2" />
</div>
</form>

</body>
</html>