<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">

	//삭제
	function cd_chk(f){
		if (CheckSpaces(f.c_pwd, '비밀번호')) { return false;}
	}

	//저장
	function cw_chk(){

	<c:if test="${ss_m_id == null or ss_m_id eq ''}">
		if (CheckSpaces(document.getElementById('frm_cm').c_name, '이름')) { return false; }
		else if (CheckSpaces(document.getElementById('frm_cm').c_pwd, '비밀번호')) { return false; }
		else if (CheckSpaces(document.getElementById('frm_cm').c_codechk, '자동등록방지문자')) { return false; }
	</c:if>
		if (CheckSpaces(document.getElementById('frm_cm').c_content, '내용')) { return false; }
		//loading_st(1);		

	}

	function cm_pwd_st(val, c_num, ff){

		if (ff=="Y"){
			if(confirm("댓글을 삭제하시겠습니까?")){
				document.getElementById('frm_cmdel'+c_num).submit();
			}else{
				return;
			}
		}else{

			if(val==1){ //보여라
				document.getElementById('cm_pwd_'+c_num).style.visibility='visible';
			}else if(val==2){
				document.getElementById('cm_pwd_'+c_num).style.visibility='hidden'; 
			}
		}
	}

</script>


<div class="board_comment">

<form id= "frm_cm" action="/board/command/writeOk.do"  method='post' onsubmit="return cw_chk();">
<div>
	<input type="hidden" name="a_num" value="${config.a_num}" />
	<input type="hidden" name="b_num" value="${board.b_num}" />
	<input type="hidden" name="prepage" value="${nowPage}" />
	<input type="hidden" name="autoimg_str" value="${autoimg_str}" />
</div>

	<table cellspacing="0" summary="게시물 댓글 작성을 위한 이름, 비밀번호, 내용을 입력" >
	<caption>게시물 댓글</caption>
		<colgroup>
		<col width="5" />
		<col width="" />
		<col width="5" />
		</colgroup>
	<tbody>
		<tr>
			<td class="dr01"></td>
			<td class="dr02"></td>
			<td class="dr03"></td>
		</tr>
	<c:if test="${ss_m_id == null or ss_m_id eq ''}">
		<tr>
			<td class="dr04"></td>
			<td class="text">
				<dl class="info">
					<dt><label for="c_name">이름</label> :</dt>
					<dd><input type="text" size="10" id="c_name" name="c_name" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${ss_m_name}" /></dd>
					<dt><label for="c_pwd">비밀번호</label> :</dt>
					<dd><input type="password" size="10" id="c_pwd" name="c_pwd" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" /></dd>
					<dt><label for="c_codechk">자동등록방지문자</label> :</dt>
					<dd>
					<img src="/nanum/ncms/img/0${autoimg_str}.png" height="30" alt="자동가입방지" class="img_center" align="absmiddle" />
					<input type="text" size="10" id="c_codechk" name="c_codechk" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" maxlength="6" title="왼쪽에 보이는 숫자 및 문자를 모두 입력하세요" />
					</dd>
				</dl>
			</td>
			<td class="dr06"></td>
		</tr>
	</c:if>
		<tr>
			<td class="dr04"></td>
			<td class="dr05">
				<textarea cols="50" rows="3" id="c_content" name="c_content" style="vertical-align:top;" title="댓글 내용 입력" onfocus="focus_on1(this);" onblur="focus_off1(this);"></textarea>
				<input type="image" src="/nanum/site/board/nninc_simple/img/dr_bt.gif" alt="댓글 쓰기" />
			</td>
			<td class="dr06"></td>
		</tr>
		<tr>
			<td class="dr07"></td>
			<td class="dr08"></td>
			<td class="dr09"></td>
		</tr>
	</tbody>
	</table>

</form>

<c:forEach items="${commandList}" var="command" varStatus="no">

<ul class="cmt_list">
<li>
<form id="frm_cmdel${command.c_num}" method='post' action="/board/command/deleteOk.do" onsubmit="return cd_chk(this);">
	<div>
		<input type="hidden" name="a_num" value="${config.a_num}" />
		<input type="hidden" name="b_num" value="${board.b_num}" />
		<input type="hidden" name="prepage" value="${nowPage}" />
		<input type="hidden" name="c_num" value="${command.c_num}" />
	</div>
	
	<c:set var="flag_del" value="N" />
	<c:if test="${is_ad_cms eq 'Y' or (ss_m_id ne '' and ss_m_id eq command.c_id)}">
		<c:set var="flag_del" value="Y" />
	</c:if>
		
	<div class="h"><strong>${command.c_name}</strong> [${command.c_regdate}] <a href="javascript:;" onclick="cm_pwd_st(1,${command.c_num},'${flag_del}');"><img src="/nanum/site/board/nninc_simple/img/dr_del_bt.gif" alt="댓글삭제" /></a>
		<div id="cm_pwd_${command.c_num}" style="position:absolute;visibility:hidden;" class="command_pwd">
			<ul>
				<li class="text"><label for="c_pwd${command.c_num}">비밀번호</label> :</li>
				<li><input type="password" size="10" id="c_pwd${command.c_num}" name="c_pwd" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" /></li>
				<li><input type="image" src="/nanum/site/board/nninc_simple/img/command_ok.gif" width="34" height="20" alt="확인" /></li>
				<li><a  href="#del" onclick="cm_pwd_st(2,${command.c_num},'${flag_del}');"><img src="/nanum/site/board/nninc_simple/img/command_cancel.gif" width="34" height="20" alt="취소" /></a></li>
			</ul>
		</div>
	</div>
		<p>${command.c_content}</p>
</form>

	</li>
	<li class="line" />
	</ul>

</c:forEach>
</div>