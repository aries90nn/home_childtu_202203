<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>


<link rel="Stylesheet" type="text/css" href="/nanum/site/edusat/css/common.css" />
<script type="text/javascript" src="/nanum/site/edusat/js/common.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>

<c:if test="${ec_tophtml ne '' }">
${ec_tophtml }
</c:if>

<script type="text/javascript">
//<![CDATA[

function cancelGo(eForm){
// 	var jumin = eForm.es_jumin_y.value+eForm.es_jumin_m.value+eForm.es_jumin_d.value;
// 	eForm.es_jumin.value = jumin;

	if(!valueChk(eForm.es_name, "성명")){return false;}
// 	if(!valueChk(eForm.es_jumin_y, "생년월일(년)")){ return false; }
// 	if(!valueChk(eForm.es_jumin_m, "생년월일(월)")){ return false; }
// 	if(!valueChk(eForm.es_jumin_d, "생년월일(일)")){ return false; }
	if(!valueChk(eForm.es_bphone1, "연락처 첫번째")){return false;}
	if(!valueChk(eForm.es_bphone2, "연락처 두번째")){return false;}
	if(!valueChk(eForm.es_bphone3, "연락처 세번째")){return false;}
}

function valueChk(obj, objName){	//text필드
	if(obj.value.split(" ").join("") == ""){
		alert(objName+"을(를) 입력하세요");
		try{
			obj.focus();
			return false;
		}catch(e){
			return false;
		}
	}else{
		return true;
	}
}


function SetNum(obj){			//숫자만 입력
	val=obj.value;
	re=/[^0-9]/gi;
	obj.value=val.replace(re,"");
}
//]]>
</script>


<div class="guide">
	<span><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif"/> 강좌신청정보를 입력하여 신청을 취소하세요.</span>
</div>



<div id="board" style="width:100%;">

<!-- 신청자 확인 -->
<form name="frm" method="post" action="cancelOk.do" onsubmit="return cancelGo(this);">
<input type="hidden" name="edu_idx" value="${param.edu_idx}">
<input type="hidden" name="es_idx" value="${param.es_idx}">
<input type="hidden" name="es_jumin" value="">
<input type="hidden" name="prepage" value="${param.prepage}">


	<!-- 쓰기 테이블 -->
	<div class="table_bwrite">

		<table cellspacing="0" summary="의 이름, 비밀번호, 내용을 입력">
		<caption>온라인 수강신청 (신청자 확인)</caption>
			<colgroup>
			<col width="20%" />
			<col width="*" />
			</colgroup>
		<tbody>
			<tr>
				<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif"/> <label for="es_name">성명</label></th>
				<td><input type="text" name="es_name" id="es_name" style="width:100px" class="txt_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${param.es_name}" maxlength="20" /></td>
			</tr>
			<!-- <tr>
				<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif"/> <label for="es_jumin">생년월일</label></th>
				<td>
					<jsp:useBean id="date" class="java.util.Date" />
						<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
						<c:set var="es_jumin_y" value="${fn:substring(param.es_jumin,0,4)}" />
						<c:set var="es_jumin_m" value="${fn:substring(param.es_jumin,4,2)}" />
						<c:set var="es_jumin_d" value="${fn:substring(param.es_jumin,6,2)}" />
						
						<select id="es_jumin_y" name="es_jumin_y" title="시작 사용기간(년)을 선택" class="ta_select" style="width:60px;height:21px" >
							<option value="" ${es_jumin_y eq '' ? 'selected="selected"' : '' }>년</option>
							<c:forEach var="i" begin="1900" end="${currentYear}">
							<option value="${i}" ${es_jumin_y eq i ? 'selected="selected"' : '' }>${i}</option>
							</c:forEach>
						</select>
						<select id="es_jumin_m" name="es_jumin_m" title="시작 사용기간(월)을 선택" class="ta_select" style="width:60px;height:21px" >
							<option value="" ${es_jumin_m eq '' ? 'selected="selected"' : '' }>월</option>
							<c:forEach var="i" begin="1" end="12">
							<fmt:formatNumber var="i_str" minIntegerDigits="2" value="${i}" type="number"/>
							<option value="${i_str}" ${es_jumin_m eq i_str ? 'selected="selected"' : '' }>${i_str}</option>
							</c:forEach>
						</select>
						<select id="es_jumin_d" name="es_jumin_d" title="시작 사용기간(일)을 선택" class="ta_select" style="width:60px;height:21px" >
							<option value="" ${es_jumin_d eq '' ? 'selected="selected"' : '' }>일</option>
							<c:forEach var="i" begin="1" end="31">
							<fmt:formatNumber var="i_str" minIntegerDigits="2" value="${i}" type="number"/>
							<option value="${i_str}" ${es_jumin_d eq i_str ? 'selected="selected"' : '' }>${i_str}</option>
							</c:forEach>
						</select> 
				</td>
			</tr>
			 -->
			<tr>
				<th scope="row"><img height="10" width="7" alt="*" src="/nanum/site/edusat/img/ic_vcheck.gif"/> <label for="es_jumin">연락처</label></th>
				<td>
					<input type="text" size="7" id="es_bphone1" name="es_bphone1" class="txt_input" onfocus="focus_on1(this);" onblur="SetNum(this);focus_off1(this);" value="${param.es_bphone1}" maxlength="4" />
					- <input type="text" size="7" id="es_bphone2" name="es_bphone2" class="txt_input" onfocus="focus_on1(this);" onblur="SetNum(this);focus_off1(this);" value="${param.es_bphone2}" maxlength="4" />
					- <input type="text" size="7" id="es_bphone3" name="es_bphone3" class="txt_input" onfocus="focus_on1(this);" onblur="SetNum(this);focus_off1(this);" value="${param.es_bphone3}" maxlength="4" />
				</td>
			</tr>
		</tbody>
		</table>

		<!-- 버튼 -->
		<div class="btn_w">
			<span><input type="submit" value="확인" class="con_btn green" /></span>
			<span><input type="button" value="되돌아가기" class="con_btn gray" onclick="location.href='${param.prepage}';" /></span>
		</div>
		<!-- //버튼 -->
	</div>
</form>
</div>



<c:if test="${ec_btmhtml ne '' }">
${ec_btmhtml }
</c:if>
