<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_banner.js"></script>
<link rel="stylesheet" href="/nanum/ncms/common/treeview/jquery.treeview.css" />
<script src="/nanum/ncms/common/treeview/jquery.treeview.js" type="text/javascript"></script>
<script>
$(document).ready(function(){ 
	$.ajax({
		type: "POST",
		url: "/${BUILDER_DIR}/ncms/buseo/listAjax.do", 
		data: "sel_type=m",
		dataType:"html",
		async:false,
		success: function(msg){	
			$("#jsBuseo").html(msg);
		}
	});
});

function modMember(num, submit){

	if(document.getElementById("form_mod"+num).style.display == "none"){

		$("#form_"+num).hide();
		$("#form_"+num).children('td').css('border-bottom', '0px solid #000;');

		$("#form_mod"+num).show();
		$("#form_mod"+num).children('td').css({"border": "1px solid #e5e5e5", "border-left": "none", 
 "border-right":"none", 
 "border-top":"none"});
	}else{
		//서브밋
		//var e = document.getElementById("ct_codeno"+num);
		//var strBuseo = e.options(e.selectedIndex).value;
		
		var strBuseo = $("#ct_codeno"+num+" option:selected").val();
		
		document.getElementById("form_modify").m_num.value			= document.getElementById("m_num"+num).value;
		document.getElementById("form_modify").ct_codeno.value		= strBuseo;
		document.getElementById("form_modify").m_name.value			= document.getElementById("m_name"+num).value;
		document.getElementById("form_modify").m_work.value			= document.getElementById("m_work"+num).value;
		document.getElementById("form_modify").m_tel.value			= document.getElementById("m_tel"+num).value;
		document.getElementById("form_modify").m_fax.value			= document.getElementById("m_fax"+num).value;

		document.getElementById("form_modify").action = "/${BUILDER_DIR}/ncms/buseo_member/writeOk.do";
		document.getElementById("form_modify").submit();
	}
}
//삭제
function d_buseomem_chk(m_num){
	
	if (confirm('정말 삭제하시겠습니까?'))	{
		
		document.getElementById('frm_list').action = "/${BUILDER_DIR}/ncms/buseo_member/deleteOk.do";
		document.getElementById('frm_list').m_num.value=m_num;
		document.getElementById('frm_list').submit();

	}else{
		
		return ;
	
	}

}

</script>
	<h1 class="tit"><span>부서구성원</span>관리</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">
	<table style="width:100%;">
		<tr>
			<td width="180" valign="top">
				<div id="jsBuseo"></div>	
			</td>
			<td valign="top">
			
		
<!-- 부서회원 -->


<h2 class="default">${div_name} 직원 등록</h2>

	<form name="frm" action="/${BUILDER_DIR}/ncms/buseo_member/writeOk.do" method='post'>
	<input type="hidden" name="codeno" value="${codeno}" />
	<fieldset>
			<legend>직원등록</legend>
			<table class="bbs_common bbs_default" summary="사이트의 회원을 관리합니다.">
			<caption>직원관리 서식</caption>
			<colgroup>
			<col />
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="15%" />
			<col width="8%" />
			</colgroup>

			<thead>
			<tr>
				<th scope="col">부서명</th>
				<th scope="col">성명</th>
				<th scope="col">담당업무</th>
				<th scope="col">전화번호</th>
				<th scope="col">팩스번호</th>
				<th scope="col">등록</th>
			</tr>
			</thead>

			<tbody>


			<tr>
				<td>
					<select name="ct_codeno" id="ct_codeno_i" class="ta_select" style="width:200px">
<c:forEach items="${buseoList}" var="buseo1" varStatus="no">
	<option value="${buseo1.ct_codeno}" ${codeno eq buseo1.ct_codeno ? 'selected="selected"' : ''}>${buseo1.ct_name}</option>
	<c:forEach items="${buseo1.list}" var="buseo2" varStatus="no2">
		<option value="${buseo2.ct_codeno}" ${codeno eq buseo2.ct_codeno ? 'selected="selected"' : ''}>　- ${buseo2.ct_name}</option>
		<c:forEach items="${buseo2.list}" var="buseo3" varStatus="no3">
			<option value="${buseo3.ct_codeno}" ${codeno eq buseo3.ct_codeno ? 'selected="selected"' : ''}>　　　- ${buseo3.ct_name}</option>
		</c:forEach>
	</c:forEach>
</c:forEach>
					</select>
				</td>
				<td class="center"><input type="text" id="m_name_i" name="m_name" value="" class="ta_input" /></td>
				<td class="left">
					<textarea id="m_work_i" name="m_work" class="ta_textarea" style="height:50px;padding:5px 5px;width:95%"></textarea>
				</td>
				<td class="center">
					<input type="text" title="연락처 입력" id="m_tel_i" name="m_tel" class="ta_input" value="" maxlength="30" />
				</td>
				<td class="center">
					<input type="text"  title="팩스 입력" id="m_fax_i" name="m_fax" class="ta_input" value="" maxlength="30" />
				</td>
				<td class="center"><input type="submit" value="등록" class="btn_bl_default" /></td>
			</tr>
			</tbody>
			</table>
		</fieldset>
	</form>


<br /><br /><br />
<h2 class="default">${div_name} 직원 리스트</h2>
	<form id= "frm_list" action="" method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" name="m_num" />
		<input type="hidden" id="chk_all" name="chk_all" />
		<input type="hidden" name="codeno" value="${codeno}" />
	</div>


		<fieldset>
			<legend>회원관리 수정/삭제/보기</legend>
			<table class="bbs_common bbs_default" summary="사이트의 회원을 관리합니다.">
			<caption>회원관리 서식</caption>
			<colgroup>
			<col width="6%" />
			<col />
			<col width="15%" />
			<col width="25%" />
			<col width="10%" />
			<col width="10%" />
			<col width="8%" />
			<col width="8%" />
			</colgroup>

			<thead>
			<tr>
				<th scope="col">선택</th>
				<th scope="col">부서명</th>
				<th scope="col">성명</th>
				<th scope="col">담당업무</th>
				<th scope="col">전화번호</th>
				<th scope="col">팩스번호</th>
				<th scope="col">수정</th>
				<th scope="col">삭제</th>
			</tr>
			</thead>

			<tbody>

<c:forEach items="${buseoMemberList}" var="member" varStatus="no">
			<tr id="form_${member.m_num}">
				<td class="center"><input type="checkbox" name="chk" value="${member.m_num}" title="해당 직 선택" /></td>
				<td class="center">${member.ct_name}</td>
				<td class="center">${member.m_name}</td>
				<td class="left">${member.m_work2}</td>
				<td class="center">${member.m_tel}</td>
				<td class="center">${member.m_fax}</td>
				<td class="center"><a href="javascript:modMember('${member.m_num}','N');"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a></td>
				<td class="center"><a href="javascript:d_buseomem_chk('${member.m_num}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif"/></a></td>

			</tr>

			<tr id="form_mod${member.m_num}" style="display:none">
				<td class="center">
					<input type="hidden" id="m_num${member.m_num}" value="${member.m_num}" />
					<input type="checkbox" name="chk" value="${member.m_num}" title="해당 직 선택" /></td>
				<td class="center">

				<select id="ct_codeno${member.m_num}" class="ta_select" style="width:130px">
<c:forEach items="${buseoList}" var="buseo1" varStatus="no">
	<option value="${buseo1.ct_codeno}" ${member.ct_codeno eq buseo1.ct_codeno ? 'selected="selected"' : ''}>${buseo1.ct_name}</option>
	<c:forEach items="${buseo1.list}" var="buseo2" varStatus="no2">
		<option value="${buseo2.ct_codeno}" ${member.ct_codeno eq buseo2.ct_codeno ? 'selected="selected"' : ''}>　- ${buseo2.ct_name}</option>
		<c:forEach items="${buseo2.list}" var="buseo3" varStatus="no3">
			<option value="${buseo3.ct_codeno}" ${member.ct_codeno eq buseo3.ct_codeno ? 'selected="selected"' : ''}>　　　- ${buseo3.ct_name}</option>
		</c:forEach>
	</c:forEach>
</c:forEach>
				</select>
				</td>
				<td class="center"><input type="text" id="m_name${member.m_num}" value="${member.m_name}" class="ta_input" /></td>
				<td class="left">
					<textarea id="m_work${member.m_num}" class="ta_textarea" style="height:50px;padding:5px 5px;width:95%">${member.m_work}</textarea>
				</td>
				<td class="left">
					<input type="text"  title="연락처 입력" id="m_tel${member.m_num}" class="ta_input" value="${member.m_tel}" maxlength="30" />
				</td>
				<td class="left">
					<input type="text" title="팩스 입력" id="m_fax${member.m_num}" class="ta_input" value="${member.m_fax}" maxlength="30" />
				</td>
				<td class="center"><a href="javascript:modMember('${member.m_num}','Y');"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a></td>
				<td class="center"><a href="javascript:d_buseomem_chk('${member.m_num}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif"/></a></td>

			</tr>
</c:forEach>
<c:if test="${fn:length(buseoMemberList) == 0}">
			<tr>
				<td class="center" colspan="13">데이터가 없습니다.</td>
			</tr>
</c:if>
			</tbody>
			</table>
		</fieldset>


	</form>

		<!-- 하단버튼 -->
		<div id="contoll_area">
			<ul>
				<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 삭제</a></p>
				<p><a onclick="javascript:window.open('listMove.do?codeno=${codeno}','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
				</li>
			</ul>
		</div>
		<!-- //하단버튼 -->


<!--// 부서회원 -->

	<form id="form_modify" method='post'>
		<input type="hidden" name="codeno" value="${codeno}" />
		<input type="hidden" name="m_num" value="" />
		<input type="hidden" name="m_name" value="" />
		<input type="hidden" name="m_code" value="" />
		<input type="hidden" name="m_work" value="" />
		<input type="hidden" name="ct_codeno" value="" />
		<input type="hidden" name="m_tel" value="" />
		<input type="hidden" name="m_fax" value="" />
	</form>



</div>