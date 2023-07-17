<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<h1 class="tit">회원그룹 권한설정</h1>
<!-- 내용들어가는곳 -->
<div id="contents_area">


<%-- 각 사이트에서만 게시판 권한 관리 --%>
<c:if test="${!empty BUILDER_DIR }">
	<form id="frm_cp" method="post" action="awrite.do?g_num=${g_num}" >

	<div class="contoll">
		<h2 class="tit">회원그룹 <strong class="point_default">[${g_num_str}]</strong> 게시판 권한</h2>
	
	<!-- 검색-->
	<div class="top_search_area mt10">
		<ul>
			<li class="tit"><label for="g_menuname"><h3 class="tit">권한복사 :</h3></label></li>
			<li class="sel">
				<select id="cp_g_num" name="cp_g_num" title="선택한 회원그룹 선택" class="t_search" style="width:80px;" >
				<c:forEach items="${membergroupList}" var="mgList" varStatus="no">
				<option value="${mgList.g_num}" ${mgList.g_num == cp_g_num ? 'selected="selected"' : '' }>${mgList.g_menuname}</option>
				</c:forEach>
				</select>	
			</li>
			<li class="txt">권한으로</li>
			<li class="btn"><a href="javascript:document.getElementById('frm_cp').submit();" class="btn_bl" ><span>복사</span></a></li>
		</ul>
	</div>
	<!-- //검색 -->
		</div>
	</form>

	
</c:if>


	<form id="frm" method="post" action="awriteOk.do" >


<%-- 각 사이트에서만 게시판 권한 관리 --%>
<c:if test="${!empty BUILDER_DIR }">
		<span class="point_default">* 게시판마다 권한을 부여하실수 있습니다.</span><br/>
		<span class="point_default">* 관리 권한 : 목록, 읽기, 쓰기, 삭제, 답변을 포함한 해당 게시판의 게시글을 관리합니다. </span><br/>
		<span class="point_default">* 목록 권한 : 해당게시판의 목록을 볼수있는 권한을 부여합니다.</span><br/>
		<span class="point_default">* 읽기 권한 : 해당게시판의 게시글을 볼수있는 권한을 부여합니다.</span><br/>
		<span class="point_default">* 쓰기 권한 : 해당게시판에 글을 쓸수있는 권한을 부여합니다.</span><br/>
		<span class="point_default">* 삭제 권한 : 해당게시판에 글을 삭제할수있는 권한을 부여합니다.</span><br/>
		<span class="point_default">* 답변 권한 : 해당게시판에 게시물에 답변을 쓸수있는 권한을 부여합니다.</span><br/>
		<fieldset>
			<legend>회원그룹 권한 설정</legend>
			<table class="bbs_common bbs_default" summary="사이트에 사용하는 회원그룹 권한 설정을 관리합니다." style="margin-top:10px">
			<caption>권한설정 서식</caption>
			<colgroup>
			<col width="40" />
			<col />
			<col width="50" />
			<col width="50" />
			<col width="50" />
			<col width="50" />
			<col width="50" />
			<col width="50" />
			</colgroup>

			<thead>
			<tr>
				<th scope="col">번호</th>
				<th scope="col">게시판명 (등록일순)</th>
				<th scope="col"><input type="checkbox" title="관리권한 전체선택/해제" onclick="javascript: selectYn(this, 'ad', 1);" <c:if test='${g_num == "2"}'>disabled='disabled'</c:if> />관리</th>
				<th scope="col"><input type="checkbox" title="목록권한 전체선택/해제" onclick="javascript: selectYn(this, 'list', 1);" />목록</th>
				<th scope="col"><input type="checkbox" title="읽기권한 전체선택/해제" onclick="javascript: selectYn(this, 'read', 1);" />읽기</th>
				<th scope="col"><input type="checkbox" title="쓰기권한 전체선택/해제" onclick="javascript: selectYn(this, 'write', 1);" />쓰기</th>
				<th scope="col"><input type="checkbox" title="삭제권한 전체선택/해제" onclick="javascript: selectYn(this, 'delete', 1);" />삭제</th>
				<th scope="col"><input type="checkbox" title="답변권한 전체선택/해제" onclick="javascript: selectYn(this, 'reply', 1);" />답변</th>
			</tr>
			</thead>

			<tbody>
			<c:set var="bbs_cnt" value="1" />
			<c:forEach items="${boardList}" var="board" varStatus="no">
			<tr>
				<td scope="row" class="center eng">${no.count}</td>
				<td scope="row" class="left bold"><input type="checkbox" onclick="javascript: selectYn(this, '${board.a_num}', 0);" />
				<c:if test="${empty BUILDER_DIR }">[${board.ct_site_dir }]</c:if>
				&nbsp;${board.a_bbsname}<!--게시판PK--><input type="hidden" name="a_num${no.count}" value="${board.a_num}" /> </td>
				<td scope="row" class="center"><input type="checkbox" id="${board.a_num}_ad" name="bl_ad_cms${no.count}" value="Y" title="관리 권한 선택" <c:if test='${board.bl_ad_cms == "Y"}'>checked</c:if> <c:if test='${g_num == "2"}'>disabled='disabled'</c:if> /></td>
				<td scope="row" class="center"><input type="checkbox" id="${board.a_num}_list" name="bl_list${no.count}" value="Y" title="목록 권한 선택" <c:if test='${board.bl_list == "Y"}'>checked</c:if> /></td>
				<td scope="row" class="center"><input type="checkbox" id="${board.a_num}_read" name="bl_read${no.count}" value="Y" title="읽기 권한 선택" <c:if test='${board.bl_read == "Y"}'>checked</c:if> /></td>
				<td scope="row" class="center"><input type="checkbox" id="${board.a_num}_write" name="bl_write${no.count}" value="Y" title="쓰기 권한 선택" <c:if test='${board.bl_write == "Y"}'>checked</c:if> /></td>
				<td scope="row" class="center"><input type="checkbox" id="${board.a_num}_delete" name="bl_delete${no.count}" value="Y" title="삭제 권한 선택" <c:if test='${board.bl_delete == "Y"}'>checked</c:if> /></td>
				<td scope="row" class="center"><input type="checkbox" id="${board.a_num}_reply" name="bl_reply${no.count}" value="Y" title="답변 권한 선택" <c:if test='${board.bl_reply == "Y"}'>checked</c:if> /></td>
			</tr>
				<c:set var="bbs_cnt" value="${bbs_cnt + 1}"/>
			</c:forEach>
			<c:if test="${bbs_cnt == 1 }">
			<tr>
				<td scope="row" class="center" colspan="8">등록된 게시판이 없습니다.</td>
			</tr>
			</c:if>
			</tbody>
			</table>
		</fieldset>
</c:if>




		<h2 class="tit h2_mt">회원그룹 <strong class="point_default">[${g_num_str}]</strong> NCMS 접속권한 및 사용메뉴 권한</h2>
		<span class="point_default">* 관리자에 접근할수있는 접속권한을 부여합니다.</span><br/>
		<fieldset>
			<legend>사용메뉴 대메뉴 권한 설정</legend>
			<table class="bbs_common bbs_default" summary="사용메뉴 대메뉴 권한설정을 위한 입력 양식입니다."style="margin-top:10px">
			<caption>사용메뉴 대메뉴 서식</caption>
			<colgroup>
			<col width="15%" />
			<col width="*" />
			</colgroup>
			<tr>
				<th scope="row"><label for="g_manager">NCMS 접속 권한</label></th>
				<td class="left">
					<select id="g_manager" name="g_manager" title="NCMS 접속 권한 선택" class="ta_select" style="width:60px;" >
						<option value="Y" ${g_manager == "Y" ? 'selected="selected"' : '' }>사용</option>
						<option value="N" ${g_manager == "N" ? 'selected="selected"' : '' }>중지</option>
					</select>
					<span class="point fs11">* 사용으로 설정하시면 NCMS(관리페이지)로 접속을 하실 수 있습니다.</span>
				</td>
			</tr>

<%--사이트관리자일 경우 감춤 --%>
<c:if test="${empty BUILDER_DIR }">
			<!-- 관리메뉴 부분(대메뉴) -->
			<c:set var="menu_cnt" value="1" />
			<c:forEach items="${menuList}" var="menu1" varStatus="no">
			<tr>
				<th scope="row">${menu1.ct_name}</th>
				<td class="left">
					<fieldset>
						<table class="bbs_nor" summary="사용메뉴 2단계메뉴 권한설정을 위한 입력 양식입니다.">
						<colgroup>
						<col width="27%" />
						<col width="*" />
						</colgroup>
						<!-- 관리메뉴 부분(소메뉴) -->
						<c:forEach items="${menu1.menuList}" var="menu2" varStatus="no2">
							<tr>
								<th scope="row" style="text-align:left;padding-left:70px;">
									<input type="checkbox" id="ct_idx${menu_cnt}" name="ct_idx${menu_cnt}" value="${menu2.ct_idx}" <c:if test='${menu2.checked == "1"}'> checked</c:if> />
									<input type="hidden" name="ct_codeno${menu_cnt}" value="${menu2.ct_codeno}" />
									
									<label for="ct_idx${menu_cnt}">${menu2.ct_name}</label>
									<c:set var="menu_cnt" value="${menu_cnt + 1}"/>
								</th>
								<td class="le">
									<fieldset>
									<!-- 관리메뉴 부분(소소메뉴) -->
									<c:forEach items="${menu2.menuList}" var="menu3" varStatus="no3">
										<c:set var="zz" value="${no3.count}"/>
										<span style="display:inline-block; width:150px;">
											${menu3.ct_name}
										</span>
										<!-- 줄바꿈 -->
										<c:if test='${zz == 4}'>
											<br/>
											<c:set var="zz" value="1"/>
										</c:if>
										
									</c:forEach>
									<!-- // 관리메뉴 부분(소소메뉴) -->
									</fieldset>
								</td>
							</tr>
						</c:forEach>
						<!-- // 관리메뉴 부분(소메뉴) -->
						</table>
						</fieldset>
					</td>
				</tr>
				</c:forEach>
				<!-- // 관리메뉴 부분(대메뉴) -->
</c:if>

				</table>
				</fieldset>



				<div class="contoll_box">
					<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="취소" onclick="page_go1('write.do');" class="btn_wh_default" /></span>
				</div>

				<div>
					<input type="hidden" name="menu_cnt" value="${menu_cnt-1}" />
					<input type="hidden" name="bbs_cnt" value="${bbs_cnt-1}" />
					<input type="hidden" name="g_num" value="${g_num}" /><!-- (수정일때사용) -->
				</div>
				</form>
		</div>
	<!-- 내용들어가는곳 -->

<script type="text/javascript">
function selectYn(con, fname, c){
	var f = document.getElementById("frm");
	pBool = con.checked;

	for (var i=0;i<f.elements.length;i++){
		if (f.elements[i].type=="checkbox"){
			
			con_id = f.elements[i].id;
			arr_con_id = con_id.split("_");
			comp_str = arr_con_id[c];	// c=1 이면 comp_str는 a_num값 , c=0 이면 comp_str 는 권한구분값 (ad, list, write 등..)

			if (comp_str==fname){
				f.elements[i].checked = pBool;
			}
		}
	}
}
</script>