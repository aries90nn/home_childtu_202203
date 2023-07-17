<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ko" />
	<meta name="robots" content="noindex, nofollow" />
	<title>도서관견학설정 | ${BUILDER.bs_sitename }</title>
	<link rel="stylesheet" type="text/css" href="/nanum/site/board/${config.a_level}/css/common.css" />
	<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/sub_layout.css" />
	<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/all.css" />
	<style>
	
		.cust{min-width:40px;padding:0 6px 0 6px;}
	
	</style>	
	<script type="text/javascript" src="/nanum/site/common/js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
	<!--[if ie 6]><link href="/common/css/ie6_png.css" rel="stylesheet" type="text/css" /><![endif]-->
	
	<script type="text/javascript">
	//<![CDATA[
	
	onload = function(){
		document.getElementById("be_time1").focus();
	}

	function sendit(eForm){
		
		//var eForm = document.frm;
		//SetNum(eForm.be_count);
		//if(!valueChk(eForm.be_count, "신청팀")){return false;}
		//alert("OK");
		//eForm.submit();
		
		if(!valueChk(eForm.be_rtype, "신청가능상태")){return false;}
		
		for(var i=1;i<=4;i++){
			var be_time_sh = $("#be_time_sh"+i).val();
			var be_time_sm = $("#be_time_sm"+i).val();
			var be_time_eh = $("#be_time_eh"+i).val();
			var be_time_em = $("#be_time_em"+i).val();
			if(be_time_sh !="" && be_time_sm != "" && be_time_eh != "" && be_time_em != ""){
				$("#be_time"+i).val( be_time_sh+":"+be_time_sm+"~"+be_time_eh+":"+be_time_em );
			}
		}
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
	
	function onlyNumber(obj) {
		$(obj).keyup(function(){
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		}); 
	}
	//]]>
	</script>
 
</head>

<body>

<!-- 쓰기 -->
<div id="board" style="width:100%;">
	<div style="padding:20px 10px;">
	
	<form id="frm" method="post" action="./dateConfOk.do" onsubmit="return sendit(this);">
	<div>
	<input type="hidden" name="a_num" value="${config.a_num}" />
	<input type="hidden" name="be_date" value="${excursconf.be_date}" />
	<input type="hidden" name="be_num" value="${excursconf.be_num}" />
	</div>
 
 
	<div class="guide" style="font-size:13px;">
		<span><img src="/nanum/site/board/${config.a_level}/img/ic_vcheck.gif" width="7" height="10" alt="*" /> 해당날짜에 신청받을 팀수를 입력하세요.</span>
	</div>

	<div  class="table_bwrite"> 
		<!-- 게시판상단e -->
		<table class="table4" summary="도서관견학신청하기">
			<caption>
				도서관견학신청하기
			</caption>
			<colgroup>
				<col width="100px">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>분류 </th>
					<td class="end">도서관견학신청일자 설정</td>
				</tr>
				<tr>
					<th>신청일자 </th>
					<td class="end">${excursconf.be_date}</td>
				</tr>
				<tr>
					<th>신청가능상태</th>
					<td class="end">
						<span class="select_style2">
							<select id="be_rtype" name="be_rtype">
								<option value="">선택</option>
								<option value="0" ${excursconf.be_rtype eq '0' ? 'selected="selected"' : '' }>신청불가</option>
								<option value="1" ${excursconf.be_rtype eq '1' ? 'selected="selected"' : '' }>신청가능</option>
								<option value="2" ${excursconf.be_rtype eq '2' ? 'selected="selected"' : '' }>신청마감</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<th>시간</th>
					<td style="padding:5px 5px" class="time_cont">
<%//기본값 지정%>
<c:set var="be_time1" value="${empty excursconf.be_time1 ? '09:00~13:00' : excursconf.be_time1}" />
<c:set var="be_count1" value="${empty excursconf.be_count1 ? '1' : excursconf.be_count1}" />

<c:forEach begin="1" end="4" var="i">
	
	<c:set var="be_time_key" value="be_time${i }" />
	<c:set var="be_count_key" value="be_count${i }" />
	<c:set var="be_time" value="${excursconf[be_time_key]}" />
	<c:set var="be_count" value="${excursconf[be_count_key]}" />
	<c:if test="${i == 1 }">
		<c:set var="be_time" value="${empty be_time ? '09:00~13:00' : be_time}" />
		<c:set var="be_count" value="${empty be_count ? '1' : be_count}" />
	</c:if>
	
	<c:set var="be_time_sh" value="${empty be_time ? '' : fn:substring(be_time, 0, 2) }" />
	<c:set var="be_time_sm" value="${empty be_time ? '' : fn:substring(be_time, 3, 5) }" />
	<c:set var="be_time_eh" value="${empty be_time ? '' : fn:substring(be_time, 6, 8) }" />
	<c:set var="be_time_em" value="${empty be_time ? '' : fn:substring(be_time, 9, 11) }" />


					
					<p>
					<input type="hidden" id="be_time${i}" name="be_time${i}" />
					<span class="select_style2 cust">
					<select id="be_time_sh${i }" name="be_time_sh${i }">
						<option value="">--</option>
	<c:forEach begin="0" end="23" var="j">
		<c:set var="j_value" value="${j }" />
		<c:if test="${j_value < 10 }"><c:set var="j_value" value="0${j_value }" /></c:if>
						<option value="${j_value }" ${be_time_sh eq j_value ? 'selected="selected"' : '' }>${j_value }</option>
	</c:forEach>
					</select>
					</span>:
					<span class="select_style2 cust">
					<select id="be_time_sm${i }" name="be_time_sm${i }">
						<option value="">--</option>
						<option value="00" ${be_time_sm eq '00' ? 'selected="selected"' : '' }>00</option>
						<option value="30" ${be_time_sm eq '30' ? 'selected="selected"' : '' }>30</option>
					</select>
					</span>
					~
					<span class="select_style2 cust">
					<select id="be_time_eh${i }" name="be_time_eh${i }">
						<option value="">--</option>
	<c:forEach begin="0" end="23" var="j">
		<c:set var="j_value" value="${j }" />
		<c:if test="${j_value < 10 }"><c:set var="j_value" value="0${j_value }" /></c:if>
						<option value="${j_value }" ${be_time_eh eq j_value ? 'selected="selected"' : '' }>${j_value }</option>
	</c:forEach>
					</select>
					</span>
					:
					<span class="select_style2 cust">
					<select id="be_time_em${i }" name="be_time_em${i }">
						<option value="">--</option>
						<option value="00" ${be_time_sm eq '00' ? 'selected="selected"' : '' }>00</option>
						<option value="30" ${be_time_sm eq '30' ? 'selected="selected"' : '' }>30</option>
					</select>
					</span>
					<input type="text" name="be_count${i }" value="${be_count}" class="input_box" style="width:30px;vertical-align:middle;" maxlength="5" onkeydown="onlyNumber(this)" /> 명</p>

</c:forEach>
					</td>
				</tr>

<c:if test="${!empty excursconf.be_num}">
				<tr>
					<th>일자별 설정삭제</th>
					<td class="end">
						<a href="./dateConfReset.do?a_num=${config.a_num}&be_num=${excursconf.be_num}" onclick="if(!confirm('일자별 별도설정을 삭제 하시겠습니까?')){return false;}">[삭제]</a>
					</td>
				</tr>
</c:if>
			</tbody>
		</table>

		<!-- 버튼s -->
		<div class="board_button2"><input type="submit" class="con_btn dgray" value="저장" /></div>
		<!-- 버튼e -->

	</div>
	</form>
	</div>
</div>
</body>
</html>