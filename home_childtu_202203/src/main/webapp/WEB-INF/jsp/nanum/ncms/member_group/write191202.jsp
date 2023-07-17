<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_member_group.js"></script>


<h1 class="tit">회원그룹 권한설정</h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">

<c:if test="${empty BUILDER_DIR }">

	<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk();">
	<!-- 검색 -->
	<div class="top_search_area mt10">
		<ul>
			<li class="tit"><label for="g_menuname"><h3 class="tit">회원그룹등록 :</h3></label></li>
			<li class="search"><label for="g_menuname">회원그룹명을 입력하세요</label><input title="메뉴 입력" name="g_menuname" id="g_menuname" type="text" value="" maxlength="25" class="search_input autoInput" /></li>
			<li class="sel">
				<select id="g_chk" name="g_chk" title="회원그룹 사용여부 선택" class="t_search" style="width:80px;">
					<option value="Y" selected="selected">사용</option>
					<option value="N" >중지</option>
				</select>	
			</li>
			<li class="btn"><input type="submit" value="등록" class="btn_bl_default"/></li>
		</ul>
	</div>
	<!-- //검색 -->
	</form>
	
	<span class="point_default fs11">* 그룹명은 25자 내외로 입력해주세요.</span><br/><br/>
</c:if>

	<form id= "frm_list" action="" method='post'>
		<div>
			<input type="hidden" name="status" />
			<input type="hidden" name="g_chk" />
			<input type="hidden" name="g_num" />
			<input type="hidden" id="chk_all" name="chk_all" />
		</div>


		<fieldset>
			<legend>회원그룹명 수정/삭제/권한 설정</legend>
			<table class="bbs_common bbs_default" summary="사이트에 사용하는 회원그룹을 관리합니다.">
			<caption>회원그룹 및 권한설정 서식</caption>
			<colgroup>
<c:if test="${empty BUILDER_DIR }">
			<col width="40" />
</c:if>
			<col />
			<col width="70" />
			<col width="70" />
<c:if test="${empty BUILDER_DIR }">
			<col width="70" />
			<col width="70" />
</c:if>
			<col width="70" />
			</colgroup>

			<thead>
			<tr>
<c:if test="${empty BUILDER_DIR }">
				<th scope="col">선택</th>
</c:if>
				<th scope="col">회원그룹</th>
				<th scope="col">접속 권한</th>
				<th scope="col">사용여부</th>
<c:if test="${empty BUILDER_DIR }">
				<th scope="col">수정</th>
				<th scope="col">삭제</th>
</c:if>
				<th scope="col">권한</th>
			</tr>
			</thead>

			<tbody>
			
<c:forEach items="${membergroupList}" var="membergroup" varStatus="no">
			<tr>
	<c:if test="${empty BUILDER_DIR }">
				<td class="center">
					<c:choose>
						<c:when test="${membergroup.g_num eq '1' or membergroup.g_num eq '2' or membergroup.g_num eq '3' or membergroup.g_num eq '4'}">
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="chk" value="${membergroup.g_num}" title="해당 그룹 선택" />
						</c:otherwise>
					</c:choose>
				</td>
	</c:if>
				<td class="center">
	<c:choose>
		<c:when test="${empty BUILDER_DIR }">
					<input type="text" title="회원그룹명 수정" id="g_menuname${no.count}" name="g_menuname${no.count}" class="ta_input w9" value="${membergroup.g_menuname}" maxlength="25" />
		</c:when>
		<c:otherwise>
					${membergroup.g_menuname}
		</c:otherwise>
	</c:choose>
				</td>
				<td class="center">
					<c:choose>
						<c:when test="${membergroup.g_manager eq 'Y'}">
							<strong class='point'>사용</strong>
						</c:when>
						<c:otherwise>
							중지
						</c:otherwise>
					</c:choose>
				</td>
				<td class="center">
					<c:choose>
						<c:when test="${membergroup.g_chk eq 'Y'}">
							<strong class='point'>사용</strong>
						</c:when>
						<c:otherwise>
							중지
						</c:otherwise>
					</c:choose>
				</td>
<c:if test="${empty BUILDER_DIR }">
				<td class="center"><a href="javascript: frm_modify${no.count}(${membergroup.g_num});"><img value="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a>
					<script type="text/javascript">
						function frm_modify${no.count}(g_num){

							document.getElementById('frm_m').g_menuname.value	= document.getElementById('frm_list').g_menuname${no.count}.value;
							//document.getElementById('frm_m').g_chk.value		= document.getElementById('frm_list').g_chk${no.count}.value;

							document.getElementById('frm_m').g_num.value		= g_num;
							document.getElementById('frm_m').action				= "writeOk.do";
							document.getElementById('frm_m').submit();
						}
					</script>
				</td>
				<td class="center">
					<c:choose>
						<c:when test="${membergroup.g_num eq '1' or membergroup.g_num eq '2' or membergroup.g_num eq '3' or membergroup.g_num eq '4'}">
						</c:when>
						<c:otherwise>
							<a href="javascript: d_chk('deleteOk.do?g_num=${membergroup.g_num}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a>
						</c:otherwise>
					</c:choose>
				</td>
</c:if>
				<td class="center">
					<c:if test="${membergroup.g_num != '1'}">
					<a href="awrite.do?g_num=${membergroup.g_num}"><img alt="권한" src="/nanum/ncms/img/common/power_icon.gif" /></a>
					</c:if>

				</td>
			</tr>
			</c:forEach>

			<!-- <tr>
				<td scope="row" class="center" colspan="2"></td>
				<td scope="row" class="left">&nbsp;&nbsp;&nbsp;<strong>비회원</strong></td>
				<td scope="row" class="center"></td>
				<td scope="row" class="center"><input type="button" value="권한" class="ct_bt01" onclick="" /></td>
				<td scope="row" class="center"></td>
				<td scope="row" class="center"></td>
			</tr> -->


			</tbody>
			</table>
		</fieldset>

<c:if test="${empty BUILDER_DIR }">
		<!-- 하단버튼 -->
		<div id="contoll_area">
			<ul>
				<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 그룹삭제</a></p><p><a onclick="javascript:window.open('listMove.do','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
				<li class="btn_ri">
					<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 회원그룹을</p>
					<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 회원그룹 사용여부 선택" class="t_search" style="width:70px;">
						<option value="Y" selected="selected">사용</option>
						<option value="N" >중지</option>
					</select></p>
					<p>(으)로</p>
					<p><a href="javascript:tot_levelchage();" class="btn_gr">변경</a></p>
				</li>
			</ul>						
		</div>
		<!-- //하단버튼 -->
</c:if>

		</form>
		
		</div>
		<!-- 내용들어가는곳 -->

<form id= "frm_m" method='post' action="">
	<div>
	<input type="hidden" name="g_menuname" />
	<input type="hidden" name="g_chk" />
	<input type="hidden" name="g_num" />
	
	</div>
</form>