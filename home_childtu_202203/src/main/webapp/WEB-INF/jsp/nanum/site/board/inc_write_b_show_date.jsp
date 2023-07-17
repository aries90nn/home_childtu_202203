<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${is_ad_cms eq 'Y'}">
<tr id="tr_notice_date" style="display:none;">
	<th scope="row"><img alt="*(필수항목)" src="/nanum/site/board/nninc_simple/img/ic_vcheck.gif"/> <label for="b_notice_sdate">공지기간</label></th>
	<td>
		<input type="text" size="100" id="b_notice_sdate" name="b_notice_sdate" class="board_input jsCalendar" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_notice_sdate}" maxlength="10" style="width:80px;" title="공지시작 날짜 선택" />

		<input type="text" size="100" id="b_notice_sdate_time" name="b_notice_sdate_time" class="board_input jsTimePicker" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_notice_sdate_time}" maxlength="5" style="width:60px;"  title="공지시작 시간 선택" />
		~
		<input type="text" size="100" id="b_notice_edate" name="b_notice_edate" class="board_input jsCalendar" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_notice_edate}" maxlength="10" style="width:80px;"  title="공지종료 날짜 선택" />

		<input type="text" size="100" id="b_notice_edate_time" name="b_notice_edate_time" class="board_input jsTimePicker" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_notice_edate_time}" maxlength="5" style="width:60px;" title="공지종료 시간 선택" />

		<input type="checkbox" name="notice_nolimit" id="notice_nolimit" value="Y"  ${board.b_notice_edate == "2099-12-31" ? "checked" : ""} /><label for="notice_nolimit">공지기간 제한없음</label>
	</td>
</tr>
</c:if>



<script type="text/javascript" src="/nanum/site/common/js/jquery-timepicker.js"></script>
<link rel="stylesheet" type="text/css" href="/nanum/site/common/js/jquery-timepicker.css" />

<script type="text/javascript">
//<![CDATA[

var org_sdate = $("#b_notice_sdate").val();
var org_edate = $("#b_notice_edate").val();

$(function(){
	$("#b_notice_sdate").attr('readonly', true);
	//$("#b_notice_sdate_time").attr('readonly', true);
	$("#b_notice_edate").attr('readonly', true);
	//$("#b_notice_edate_time").attr('readonly', true);

	$("input[name='b_noticechk']").click(function(){	//공지체크
		showNoticeDate();
	});
	showNoticeDate();

	$("input[name='notice_nolimit']").click(function(){
		showNoticeLimit();
	});
	showNoticeLimit();

	
	//시작, 종료일 연동

	$('#b_notice_sdate').helloCalendar({'selectBox':true, 'endLink':'b_notice_edate'});
	$('#b_notice_edate').helloCalendar({'selectBox':true, 'startLink':'b_notice_sdate'});

	$('.jsCalendar').attr("readonly", true);
	$(".jsTimePicker").timepicker({ 'timeFormat': 'H:i' });

});

function showNoticeLimit(){
	if($("input[name='notice_nolimit']").is(":checked")){
		org_sdate = $("#b_notice_sdate").val();
		org_edate = $("#b_notice_edate").val();
		$("#b_notice_sdate").val("1900-01-01");
		$("#b_notice_edate").val("2099-12-31");
		$("#b_notice_sdate_time").val("00:00");
		$("#b_notice_edate_time").val("23:59");
		$("#tr_notice_date").find("input[type='text']").attr("disabled",true);
	}else{
		$("#b_notice_sdate").val(org_sdate);
		$("#b_notice_edate").val(org_edate);
		//$("#b_notice_edate_time").val("");
		$("#tr_notice_date").find("input[type='text']").attr("disabled",false);
	}
}
function showNoticeDate(){
	if($("input[name='b_noticechk']:checked").val()=="Y"){
		$("#tr_notice_date").show();
	}else{
		$("#tr_notice_date").hide();
	}
}
//]]>
</script>
