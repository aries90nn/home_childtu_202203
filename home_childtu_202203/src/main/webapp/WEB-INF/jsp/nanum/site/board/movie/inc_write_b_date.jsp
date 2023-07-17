<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<?
if($b_sdate == ""){$b_sdate = date("Y-m-d");}
if($b_edate == ""){$b_edate = date("Y-m-d");}
?>

<script type="text/javascript">
//<![CDATA[
$(function(){
	$('#b_sdate').attr("readonly", true);
	//시작, 종료일 연동
	$('#b_sdate').helloCalendar({'selectBox':true});
});

//]]>
</script>
					<input type="text" size="100" id="b_sdate" name="b_sdate" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_sdate}" maxlength="10" style="width:80px;" />