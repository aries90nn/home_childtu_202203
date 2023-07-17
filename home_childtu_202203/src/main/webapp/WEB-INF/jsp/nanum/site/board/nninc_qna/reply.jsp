<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="list_url_tmp" value="${nowURI }?proc_type=list&a_num=${param.a_num }" />
<c:set var="prepage" value="${empty param.prepage ? list_url_tmp : param.prepage}" />


<!-- 스크립트 -->
<jsp:include page="../board_script.jsp" />
<!-- //스크립트 -->
	
<script type="text/javascript">//<![CDATA[
	function w_chk2(){
		
		if (CheckSpaces(document.getElementById('frm').b_re_buseo, '답변부서')) {return false;}
		//else if (CheckSpaces(document.getElementById('frm').b_re_content, '답변내용')) {return false;}
		
		<c:if test='${config.a_content eq "Y"}'>
			oEditors.getById["b_re_content"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용된다.
		</c:if>
		
		<c:if test='${config.a_content eq "Y"}'>
			<c:if test='${config.a_edit eq "Y"}'>
					var rcontent = document.getElementById('frm').b_re_content.value.replace(/&nbsp;/g,"").replace(/(\r\n)/gi,"").replace(/\s/g,"");
					if( rcontent=="" || rcontent=="<P></P>" || rcontent=="<br>"){ 
						alert("내용을 입력하세요."); 
						oEditors.getById["b_re_content"].exec("FOCUS", []);
						return false; 
					}
			</c:if>
			<c:if test='${config.a_edit ne "Y"}'>
				else if (document.getElementById('frm').b_re_content.value=="<br />" || document.getElementById('frm').b_re_content.value=="") {
					alert("내용을 입력하세요.");
					return false;
				}
			</c:if>
		
		</c:if>
		
		
		loading_st(1);
	}
	
	function isNull( text ) { 
		if( text == null ) return true; 
		var result = text.replace(/(^\s*)|(\s*$)/g, ""); 

		if( result ) 
			return false; 
		else 
			return true; 
	}
</script>

<!-- 보기 -->
<div id="board" style="width:${config.a_width};">
	
	<form id="frm" method="post" action="/board/replyOk.do" onsubmit="return w_chk2();">
	<div>
	<input type="hidden" name="a_num" value="${config.a_num}" />
	<input type="hidden" name="b_num" value="${board.b_num}" /><!-- (수정일때사용) -->
	<input type="hidden" name="prepage" value="${prepage}" />
	<input type="hidden" name="board_token" value="${board_token}" />
	</div>

	<div class="guide">
		<span><img src="/nanum/site/board/nninc_qna/img/ic_vcheck.gif" alt="*(필수항목)" /> 표시가 된 곳은 필수 항목입니다.</span>
	</div>

	<!-- 쓰기 테이블 -->
	<div class="table_bwrite">
		<table cellspacing="0" summary="${title}의 문의에 대한 답변, 답변부서, 처리상황을 입력">
		<caption>${title}</caption>
			<colgroup>
			<col width="130" />
			<col width="" />
			</colgroup>
		<thead>
			<tr>
				<th scope="col" colspan="2" class="th_end">답변쓰기</th>
			</tr>
		</thead>
		<tbody>
			
		<tr>
			<th scope="row"><img src="/nanum/site/board/nninc_qna/img/ic_vcheck.gif" alt="*(필수항목)" /> <label for="b_temp5">답변부서</label></th>
			<td>
				<input type="text" size="50" id="b_re_buseo" name="b_re_buseo" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${board.b_re_buseo}" maxlength="50"  />
			</td>
		</tr>

		<tr>
			<th scope="row"><img src="/nanum/site/board/nninc_qna/img/ic_vcheck.gif" alt="*(필수항목)" />  처리기한</th>
			<td>
				<jsp:useBean id="date" class="java.util.Date" />
				<fmt:formatDate value="${date }" pattern="yyyy" var="currentYear"/>
				<select id="b_re_date_y" name="b_re_date_y" title="처리기한(년도) 선택">
					<c:forEach var="i" begin="2017" end="${currentYear+5 }">
						<option value="${i }" ${board.b_re_date_y == i ? 'selected="selected"' : '' }> ${i }</option>
					</c:forEach>
				</select>
				년
				
				<select id="b_re_date_m" name="b_re_date_m" title="처리기한(월) 선택">
					<c:forEach var="i" begin="1" end="12">
						<option value="${i }" ${board.b_re_date_m == i ? 'selected="selected"' : '' }> ${i }</option>
					</c:forEach>
				</select>
				월
				
				<select id="b_re_date_d" name="b_re_date_d" title="처리기한(일) 선택">
					<c:forEach var="i" begin="1" end="31">
						<option value="${i }" ${board.b_re_date_d == i ? 'selected="selected"' : '' }> ${i }</option>
					</c:forEach>
				</select>
				일
			</td>
		</tr>

		<tr>
			<th scope="row"><img src="/nanum/site/board/nninc_qna/img/ic_vcheck.gif" alt="*(필수항목)" /> <label for="b_re_state">처리상황</label></th>
			<td>
				<select id="b_re_state" name="b_re_state">
					<option value="접수" ${board.b_re_state == "접수" ? 'selected="selected"' : '' }>접수</option>
					<option value="진행중" ${board.b_re_state == "진행중" ? 'selected="selected"' : '' }>진행중</option>
					<option value="완료" ${board.b_re_state == "완료" ? 'selected="selected"' : '' }>완료</option>
					<option value="취하" ${board.b_re_state == "취하" ? 'selected="selected"' : '' }>취하</option>
				</select>
			</td>
		</tr>


		<tr>
			<td colspan="2" class="content">
				<c:if test="${config.a_edit eq 'Y'}">
					<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
					<textarea name="b_re_content" id="b_re_content" style="width:100%; height:250px;">${board.b_re_content}</textarea>
					<script type="text/javascript">
					var oEditors = [];
					nhn.husky.EZCreator.createInIFrame({
						oAppRef: oEditors,
						elPlaceHolder: "b_re_content",
						sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
						fCreator: "createSEditor2"
					});							
					</script>
				</c:if>
				<c:if test="${config.a_edit ne 'Y'}">
					<textarea cols="50" rows="15" id="b_re_content" name="b_re_content" title="답변 내용 입력" style="width:98%;" onfocus="focus_on1(this);" onblur="focus_off1(this);" >${board.b_re_content}</textarea>
				</c:if>
			</td>
		</tr>

	</tbody>
	</table>

	
	</div>
	<!-- //쓰기 테이블 -->

	<!-- 버튼 -->
	<div class="board_button2">
		<span><input id="submitbtn" class="cbtn cbtn_point" type="submit" value="저장" /></span>
		<span><a href="${prepage }" class="cbtn cbtn_g">취소</a></span>
	</div>
	<!-- //버튼 -->

	</form>

</div>
<!-- //쓰기 -->