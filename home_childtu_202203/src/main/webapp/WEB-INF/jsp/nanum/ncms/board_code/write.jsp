<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_board_code.js"></script>

	<h1 class="tit"><span>게시판 분류</span> 관리</h1>
	
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
<c:set var="board_list_url" value="/ncms/board_config/list.do" />
<c:if test="${!empty BUILDER_DIR }">
	<c:set var="board_list_url" value="/${BUILDER_DIR }/ncms/board_config/list.do" />
</c:if>
<h2 class="tit">현재분류경로<span class="loc">: <a href="${board_list_url }" >게시판리스트</a> > <strong class="point_default">${a_bbsname}</strong></span></h2>

	<form id="frm" method="post" enctype="multipart/form-data" action="writeOk.do?a_num=${a_num}" onsubmit="return w_chk();">
	<div>
		<input type="hidden" name="ct_ref" value="0" />
		<input type="hidden" name="ct_depth" value="1" />
		<input type="hidden" name="ct_codeno_ref" value="C0;" />
		<input type="hidden" id="a_num" name="a_num" value="${a_num}" />
	</div>
	<!-- 검색 -->
	<div class="top_search_area mt10">
		<ul>
			<li class="tit"><label for="ct_name_i"><h3 class="tit">분류등록 :</h3></label></li>
			<li class="search"><label for="ct_name_i">분류명을 입력하세요</label><input title="분류 입력" name="ct_name" id="ct_name_i" type="text" value="" class="search_input autoInput" maxlength="100" /></li>
			<li class="search"><label for="ct_file">이미지 등록</label><input title="이미지 등록" name="ct_file_file" id="ct_file" type="file" value="" class="ta_input_file" style="width:200px;" /></li>
			<li class="sel">
				<select id="ct_chk_i" name="ct_chk" title="분류 사용여부 선택" class="t_search" style="width:80px;">
					<option value="Y" selected="selected">사용</option>
					<option value="N" >중지</option>
				</select>	
			</li>
			<li class="btn"><input type="submit" value="등록" class="btn_bl_default"/></li>
		</ul>
	</div>
	<!-- //검색 -->

	</form>
	<span class="point_default fs11">* 분류명은 100자 이하로 입력해주세요.</span><br/>
	<span class="point_default fs11">* 이미지는 5MB의 jpg,png,gif 이미지 파일만 허용 가능합니다.</span>
<form id= "frm_list" action="" enctype="multipart/form-data" method='post' >
<div>
	<input type="hidden" id="status" name="status" />
	<input type="hidden" id="ct_chk" name="ct_chk" />
	<input type="hidden" id="ct_idx" name="ct_idx" />
	<input type="hidden" id="ct_ref" name="ct_ref" value="${boardcode.ct_ref}" />
	<input type="hidden" id="a_num" name="a_num" value="${a_num}" />

	<input type="hidden" id="chk_all" name="chk_all" />


	
</div>

<fieldset>
	<legend>분류관리 작성/수정</legend>
	<table class="bbs_common bbs_default" summary="사이트에 사용하는 분류를 관리합니다.">
	<caption>분류관리 서식</caption>
	<colgroup>
	<col width="40" />
	<col />
	<col width="250" />
	<col width="70" />
	<col width="50" />
	<col width="50" />
	</colgroup>

	<thead>
	<tr>
		<th scope="col">선택</th>
		<th scope="col">분류</th>
		<th scope="col">이미지</th>
		<th scope="col">사용여부</th>
		<th scope="col">수정</th>
		<th scope="col">삭제</th>
	</tr>
	</thead>

	<tbody>

	<c:forEach items="${boardcodeList}" var="boardcode" varStatus="no">
	<tr>
		<td class="center"><input type="checkbox" name="chk" value="${boardcode.ct_idx}" title="해당 분류 선택" /></td>

		<td class="center">
			<input type="text" title="분류 입력" id="ct_name_${boardcode.ct_idx}" name="ct_name_${boardcode.ct_idx}" class="ta_input w9 editmode" value="${boardcode.ct_name}" maxlength="100" />
		</td>

		<td class="left">

			<input type="file" size="20" title="이미지 등록" id="ct_file_${boardcode.ct_idx}" name="ct_file_file_${boardcode.ct_idx}" class="ta_input_file" value="${boardcode.ct_file}" maxlength="100" />
			<c:if test="${boardcode.ct_file != null and boardcode.ct_file ne ''}">
				<br/><div class="vam pt5"><img src = "/data/board_code/${boardcode.ct_file}" alt="${boardcode.ct_name}" />&nbsp;<input type='checkbox' name='ct_file_del_${boardcode.ct_idx}' value='${boardcode.ct_file}' />삭제</div>
			</c:if>
			<input type="hidden" id="ct_file_org_${boardcode.ct_idx}" name="ct_file_org_${boardcode.ct_idx}" value="${boardcode.ct_file}" />
		</td>
		<td class="center">${boardcode.ct_chk == "Y" ? "<strong>사용</strong>" : "중지"}</td>
		<td class="center">
			<a href="javascript:m_chk(${boardcode.ct_idx});"><img src="/nanum/ncms/img/common/modify_icon.gif" alt="수정" /></a>
		</td>
		<td class="center">
			<a href="javascript: d_chk('deleteOk.do?a_num=${boardcode.a_num}&ct_idx=${boardcode.ct_idx}&ct_ref=${boardcode.ct_ref}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a>
		</td>
		
	</tr>
	</c:forEach>
	<c:if test="${fn:length(boardcodeList) == 0}">
	<tr>
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
		<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 분류삭제</a></p>
		<p><a onclick="javascript:window.open('listMove.do?a_num=${a_num}','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
		<li class="btn_ri">
			<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 분류를</p>
			<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 분류 사용여부 선택" class="t_search" style="width:70px;">
				<option value="Y" selected="selected">사용</option>
				<option value="N" >중지</option>
			</select></p>
			<p>(으)로</p>
			<p><a href="javascript:tot_levelchage();" class="btn_gr">변경</a></p>
		</li>
	</ul>						
</div>
<!-- //하단버튼 -->
	
	
	</form>

<form id= "frm_m" method='post' enctype="multipart/form-data" action="">
<div>
	<input type="hidden" name="ct_name" />
	<input type="hidden" name="ct_file" />
	<input type="hidden" name="ct_file2" />
	<input type="hidden" name="ct_chk" />
	<input type="hidden" name="ct_idx" />
	<input type="hidden" name="ct_ref" />
</div>
</form>

			
		</div>
		<!-- 내용들어가는곳 -->