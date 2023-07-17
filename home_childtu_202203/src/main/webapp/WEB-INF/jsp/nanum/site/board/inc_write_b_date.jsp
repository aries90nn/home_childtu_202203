<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
//<![CDATA[
$(function(){
	$('#b_sdate, #b_edate').attr("readonly", true);
	//시작, 종료일 연동
	$('#b_sdate').helloCalendar({'selectBox':true});
	$('#b_edate').helloCalendar({'selectBox':true});
});

//]]>
</script>
	<input type="text" size="100" id="b_sdate" name="b_sdate" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_sdate}" maxlength="10" style="width:80px;" />
	~
	<input type="text" size="100" id="b_edate" name="b_edate" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_edate}" maxlength="10" style="width:80px;" />
