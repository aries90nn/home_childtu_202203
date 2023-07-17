<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>

<c:set var="prepage" value="${empty param.prepage ? '/ncms/poll/list.do' : param.prepage }" />
<c:if test="${empty param.prepage and !empty BUILDER_DIR }">
	<c:set var="prepage" value="/${BUILDER_DIR }${prepage }" />
</c:if>

<script type="text/javascript" src="/nanum/ncms/common/js/ncms_poll_question.js"></script>
<script type="text/javascript">
function deleteOk(poq_idx, po_pk){
	if(confirm("설문문항을 삭제하시겠습니까?")){
		location.href="./deleteOk.do?poq_idx="+poq_idx+"&po_pk="+po_pk+"&prepage=${nowPageEncode}";
	}
}
</script>

<h1 class="tit"><span>설문조사 설문문항설정</span></h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">

<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk();">
<div>
	<input type="hidden" name="poq_idx" value="${question.poq_idx}" />
	<input type="hidden" name="po_pk" value="${param.po_pk}" />
	<input type="hidden" name="prepage" value="${nowPage}" />
</div>

<h2 class="tit">현재경로<span class="loc">: <a href="${prepage }" >설문조사 리스트</a> > <strong class="point_default">${poll.po_subject}</strong></span> <span><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 표시가 된 곳은 필수 항목입니다.</span></h2>



<fieldset>
	<legend>설문문항생성 서식 작성</legend>
	<table class="bbs_common bbs_default" summary="신규 설문문항 생성을 위한 입력 양식입니다.">
	<caption>설문조사문항생성 서식</caption>
<colgroup>
<col width="15%" />
<col width="*" />
</colgroup>
<tr>
	<th scope="row"><label for="poq_topmemo">머릿말</label></th>
	<td class="left"><textarea rows="2" id="poq_topmemo" name="poq_topmemo" title="머릿말 내용 입력" class="ta_textarea w9" ></textarea></td>
</tr>
<tr>
	<th scope="row"><label for="poq_question"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 문항 제목</label></th>
	<td class="left"><input type="text" title="문항제목 입력" id="poq_question" name="poq_question" class="ta_input w5" value="" maxlength="150" />
	<span class="point fs11">* 제목은 150자 이하로 입력해주세요.</span>
	</td>
</tr>
<tr>
	<th scope="row"><label for="poq_type">형식</label></th>
	<td class="left">
		<select id="poq_type" name="poq_type" title="문항 형식을 선택" class="ta_select" style="width:80px;" onchange="javascript:a_level_chg(this.value,'mode_view');">
			<option value="1">객관식</option>
			<option value="2" >주관식</option>
		</select>
		<span class="point fs11">* 항목은 150자 이하로 입력해주세요.</span>

	<div id="mode_view" style="display:block;" class="mt10">
		
		<table class="bbs_nor" summary="설문항목 입력 양식입니다.">
		<caption>설문항목 서식</caption>
		<colgroup>
		<col width="10%" />
		<col width="*" />
		</colgroup>
			<tr>
				<th scope="row" rowspan="10"><label for="poq_bogi1">항목</label></th>
				<td class="le">
					<strong class="eng">1</strong> <input type="text" title="1번 항목 내용 입력" id="poq_bogi1" name="poq_bogi1" class="ta_input_nor w85" value="" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le">
					<strong class="eng">2</strong> <input type="text" title="2번 항목 내용 입력" id="poq_bogi2" name="poq_bogi2" class="ta_input_nor w85" value="" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le">
					<strong class="eng">3</strong> <input type="text" title="3번 항목 내용 입력" id="poq_bogi3" name="poq_bogi3" class="ta_input_nor w85" value="" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le">
					<strong class="eng">4</strong> <input type="text" title="4번 항목 내용 입력" id="poq_bogi4" name="poq_bogi4" class="ta_input_nor w85" value="" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le">
					<strong class="eng">5</strong> <input type="text" title="5번 항목 내용 입력" id="poq_bogi5" name="poq_bogi5" class="ta_input_nor w85" value="" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le">
					<strong class="eng">6</strong> <input type="text" title="5번 항목 내용 입력" id="poq_bogi6" name="poq_bogi6" class="ta_input_nor w85" value="" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le">
					<strong class="eng">7</strong> <input type="text" title="5번 항목 내용 입력" id="poq_bogi7" name="poq_bogi7" class="ta_input_nor w85" value="" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le">
					<strong class="eng">8</strong> <input type="text" title="5번 항목 내용 입력" id="poq_bogi8" name="poq_bogi8" class="ta_input_nor w85" value="" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le">
					<strong class="eng">9</strong> <input type="text" title="5번 항목 내용 입력" id="poq_bogi9" name="poq_bogi9" class="ta_input_nor w85" value="" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le">
					<strong class="eng">10</strong> <input type="text" title="10번 항목 내용 입력" id="poq_bogi10" name="poq_bogi10" class="ta_input_nor w85" value="" maxlength="150" />
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="poq_multi_Y">다중선택</label></th>
				<td class="left">
					<label for="poq_multi_Y"><input type="radio" id="poq_multi_Y" name="poq_multi" value="Y" title="사용 선택" />사용</label>
					<label for="poq_multi_N"><input type="radio" id="poq_multi_N" name="poq_multi" value="N" title="사용안함 선택" checked='checked' />사용안함</label>
				</td>
			</tr>
			</table>

		</div>

	</td>
</tr>

<tr>
	<th scope="row"><label for="poq_chk">사용여부</label></th>
	<td class="left">
		<select id="poq_chk" name="poq_chk" title="문제 사용여부 선택" class="ta_select" style="width:60px;" >
		<option value="Y">사용</option>
		<option value="N">중지</option>
			</select></td>
	</tr>
		</table>
	</fieldset>


	<div class="contoll_box">
		<span><input type="submit" value="문항등록" class="btn_bl_default" /></span>
	</div>

</form>


<div style="text-align:center; display:block; height:50px; width:100%; margin-top:50px;">
	<img src="/nanum/ncms/img/poll_arrow.gif" alt="다음단계" />
</div>

<!-- 수정.. -->

<form id= "frm_list" action="" method='post'>
	<div>
		<input type="hidden" name="status" />
		<input type="hidden" id="poq_chk" name="poq_chk" />
		<input type="hidden" id="chk_all" name="chk_all" />
		
		<input type="hidden" id="po_pk" name="po_pk" value="${param.po_pk}" />
		<input type="hidden" id="poq_idx" name="poq_idx" />
		<input type="hidden" name="prepage" value="${nowPage}" />
	</div>


<fieldset>
	<legend>설문조사관리 수정/삭제/보기</legend>
	<table class="bbs_common bbs_default" summary="설문조사을 관리합니다.">
	<caption>설문조사관리 서식</caption>
<colgroup>
<col width="40" />
<col />
<col width="70" />
<col width="50" />
<col width="50" />
</colgroup>

<thead>
<tr>
	<th scope="col">선택</th>
	<th scope="col">제목</th>
	<th scope="col">사용여부</th>
	<th scope="col">수정</th>
	<th scope="col">삭제</th>
</tr>
</thead>

<tbody>

<c:forEach items="${questionList}" var="question" varStatus="no">
<tr>
	<td scope="row" class="center"><input type="checkbox" name="chk" value="${question.poq_idx}" title="해당 설문조사 선택" /></td>
	<td scope="row" class="left">

		<table class="bbs_nor">
		<colgroup>
		<col width="10%" />
		<col width="" />
		</colgroup>
		<tr>
			<th scope="row"><label for="poq_topmemo_${question.poq_idx}">머릿말</label></th>
			<td class="le"><textarea rows="2" id="poq_topmemo_${question.poq_idx}" name="poq_topmemo_${question.poq_idx}" title="머릿말 내용 입력" class="ta_textarea_nor w9" >${question.poq_topmemo}</textarea></td>
		</tr>
		<tr>
			<th scope="row"><label for="poq_question_${question.poq_idx}"> <strong class="point_default"><img src="/nanum/ncms/img/ic_vcheck.gif" width="7" height="10" alt="*" /> [${no.count}] 문항 제목</strong></label></th>
			<td class="le"><input type="text" title="문항제목 입력" id="poq_question_${question.poq_idx}" name="poq_question_${question.poq_idx}" class="ta_input_nor w5" value="${question.poq_question}" maxlength="150" />
			<span class="point fs11">* 제목은 150자 이하로 입력해주세요.</span>
			</td>
		</tr>
		<tr>
			<th scope="row"><label for="poq_type_${question.poq_idx}">형식</label></th>
			<td class="le item">
				<select id="poq_type_${question.poq_idx}" name="poq_type_${question.poq_idx}" title="문항 형식을 선택" class="ta_select" style="width:80px;" onchange="javascript:a_level_chg(this.value,'mode_view${question.poq_idx}');">
					<option value="1" ${question.poq_type == "1" ? 'selected="selected"' : '' }>객관식</option>
					<option value="2" ${question.poq_type == "2" ? 'selected="selected"' : '' }>주관식</option>
				</select>
				
				<span class="point fs11">* 객관식 항목은 150자 이하로 입력해주세요.</span>
			</td>
		</tr>
		</table>


		<div id="mode_view${question.poq_idx}" style="display:block;" class="mt10">
			
			<table class="bbs_nor">
			<colgroup>
			<col width="10%" />
			<col width="" />
			</colgroup>
			<tr>
				<th scope="row" rowspan="10"><label for="poq_bogi1_${question.poq_idx}">항목</label></th>
				<td class="le item">
					<strong class="eng">1</strong> <input type="text" title="1번 항목 내용 입력" id="poq_bogi1_${question.poq_idx}" name="poq_bogi1_${question.poq_idx}" class="ta_input_nor w85" value="${question.poq_bogi1}" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le item">
					<strong class="eng">2</strong> <input type="text" title="2번 항목 내용 입력" id="poq_bogi2_${question.poq_idx}" name="poq_bogi2_${question.poq_idx}" class="ta_input_nor w85" value="${question.poq_bogi2}" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le item">
					<strong class="eng">3</strong> <input type="text" title="3번 항목 내용 입력" id="poq_bogi3_${question.poq_idx}" name="poq_bogi3_${question.poq_idx}" class="ta_input_nor w85" value="${question.poq_bogi3}" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le item">
					<strong class="eng">4</strong> <input type="text" title="4번 항목 내용 입력" id="poq_bogi4_${question.poq_idx}" name="poq_bogi4_${question.poq_idx}" class="ta_input_nor w85" value="${question.poq_bogi4}" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le item">
					<strong class="eng">5</strong> <input type="text" title="5번 항목 내용 입력" id="poq_bogi5_${question.poq_idx}" name="poq_bogi5_${question.poq_idx}" class="ta_input_nor w85" value="${question.poq_bogi5}" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le item">
					<strong class="eng">6</strong> <input type="text" title="6번 항목 내용 입력" id="poq_bogi6_${question.poq_idx}" name="poq_bogi6_${question.poq_idx}" class="ta_input_nor w85" value="${question.poq_bogi6}" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le item">
					<strong class="eng">7</strong> <input type="text" title="7번 항목 내용 입력" id="poq_bogi7_${question.poq_idx}" name="poq_bogi7_${question.poq_idx}" class="ta_input_nor w85" value="${question.poq_bogi7}" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le item">
					<strong class="eng">8</strong> <input type="text" title="8번 항목 내용 입력" id="poq_bogi8_${question.poq_idx}" name="poq_bogi8_${question.poq_idx}" class="ta_input_nor w85" value="${question.poq_bogi8}" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le item">
					<strong class="eng">9</strong> <input type="text" title="9번 항목 내용 입력" id="poq_bogi9_${question.poq_idx}" name="poq_bogi9_${question.poq_idx}" class="ta_input_nor w85" value="${question.poq_bogi9}" maxlength="150" />
				</td>
			</tr>
			<tr>
				<td class="le item">
					<strong class="eng">10</strong> <input type="text" title="10번 항목 내용 입력" id="poq_bogi10_${question.poq_idx}" name="poq_bogi10_${question.poq_idx}" class="ta_input_nor w85" value="${question.poq_bogi10}" maxlength="150" />
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="poq_multi_${question.poq_idx}_Y">다중선택</label></th>
				<td class="le">
					<label for="poq_multi_${question.poq_idx}_Y"><input type="radio" id="poq_multi_${question.poq_idx}_Y" name="poq_multi_${question.poq_idx}" value="Y" title="사용 선택" <c:if test='${question.poq_multi == "Y"}'> checked</c:if> />사용</label>
					<label for="poq_multi_${question.poq_idx}_N"><input type="radio" id="poq_multi_${question.poq_idx}_N" name="poq_multi_${question.poq_idx}" value="N" title="사용안함 선택" <c:if test='${question.poq_multi == "N"}'> checked</c:if> />사용안함</label>

				</td>
			</tr>
			</table>

			<script type="text/javascript">
			a_level_chg(${question.poq_type},'mode_view${question.poq_idx}')
			</script>

		</div>

	</td>

	<td scope="row" class="center">${question.poq_chk == "Y" ? "<strong>사용</strong>" : "중지"}</td>
	<td scope="row" class="center"><a href="javascript: m_chk('${question.poq_idx}','${question.poq_chk}');"><img src="/nanum/ncms/img/common/modify_icon.gif" alt="수정" /></a></td>
	<td scope="row" class="center"><a href="#del" onclick="deleteOk('${question.poq_idx}','${question.po_pk}');"><img src="/nanum/ncms/img/common/delete_icon.gif" alt="삭제" /></a></td>
</tr>
</c:forEach>
<c:if test="${fn:length(questionList) == 0}">
<tr>
	<td scope="row" class="center"></td>
	<td scope="row" class="left"></td>
	<td scope="row" class="center"></td>
	<td scope="row" class="center"></td>
	<td scope="row" class="center"></td>
	<td scope="row" class="center"></td>
</tr>
</c:if>
	</tbody>
	</table>
</fieldset>

<!-- 하단버튼 -->
<div id="contoll_area">
	<ul>
		<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 문항삭제</a></p>
		<p><a onclick="javascript:window.open('./listMove.do?po_pk=${param.po_pk}&prepage=${nowPageEncode }','','width=360, height=780,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
	<li class="btn_ri">
		<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 문항을</p>
		<p><select id="tot_m_level" name="tot_level_chk" title="선택한 문항 사용여부 선택" class="t_search" style="width:70px;">
			<option value="Y" selected="selected">사용</option>
			<option value="N" >중지</option>
		</select></p>
		<p>(으)로</p>
		<p><a href="javascript:tot_levelchage('${page_info}');" class="btn_gr">변경</a></p>
		</li>
	</ul>
</div>
<!-- //하단버튼 -->


</form>
	
</div>
<!-- 내용들어가는곳 -->