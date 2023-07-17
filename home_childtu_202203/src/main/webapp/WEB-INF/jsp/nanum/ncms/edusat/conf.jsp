<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_edusat.js"></script>


<h1 class="tit">독서문화프로그램 환경설정</h1>

<!-- 내용들어가는곳 -->
<div id="contents_area">
	
	<form id="frm" method="post" action="confOk.do">
		<div>
		<input type="hidden" name="ec_idx" value="${edusatConf.ec_idx }" />
		<input type="hidden" name="prepage" value="${nowPage }" />
		<input type="hidden" name="ec_lib" value="도담동도서관,아름동도서관,종촌동도서관,한솔동도서관,고운동도서관,보람동도서관,대평동도서관,새롬동도서관,고운남측도서관,작은도서관" />
		
		</div>
			<fieldset>
				<legend>문화행사 환경설정 작성</legend>
				<table cellspacing="0" class="bbs_common bbs_default" summary="문화행사 환경설정을 위한 입력 양식입니다.">
				<caption>문화행사 환경설정 </caption>
				<colgroup>
				<col width="140" />
				<col width="580" />
				</colgroup>
				<tr>
					<th scope="row"><label for="ec_alert_count">경고횟수</label></th>
					<td class="left"><input type="text" size="3" id="ec_alert_count" name="ec_alert_count" title="도서관구분 입력" class="ta_input" value="${edusatConf.ec_alert_count}" maxlength="2"/>회 
					<br /><span class="text1">* 경고횟수이상 누적되면 강좌신청이 제한됩니다.</span>
					<br /><span class="text1">* 0 은 제한 없음. </span>
					</td>
				</tr>
				<tr>
					<th scope="row"><label for="ec_lib">도서관구분</label></th>
					<!-- <td class="left"><input type="text" size="90" id="ec_lib" name="ec_lib" title="도서관구분 입력" class="ta_input" onfocus="focus_on1_default(this);" onblur="focus_off1(this);" value="${edusatConf.ec_lib }" maxlength="100" /><br /><span class="text1">* 도서관별 문화행사가 있을경우만 입력하세요.</span>
					<br /><span class="text1">* ','구분자로 입력하세요. ex)구분1,구분2...</span>
					</td>-->
					
					<td class="left"><input type="button" value="관리" class="btn_bl_default" onclick="javascript:window.open('/ncms/code/write.do?ct_idx=1','htmlOpen2'); " /></td>
					
				</tr>
				<tr>
					<th scope="row"><label for="ec_gubun">문화행사구분</label></th>
					<td class="left"><input type="text" size="90" id="ec_gubun" name="ec_gubun" title="문화행사구분 입력" class="ta_input"  value="${edusatConf.ec_gubun }" maxlength="100"/><br /><span class="text1">* ','구분자로 입력하세요. ex)구분1,구분2...</span></td>
				</tr>
				<tr>
					<th scope="row"><label for="ec_tophtml">상단 HTML 정보</label></th>
					<td class="left"><textarea style="height:250px;" id="ec_tophtml" name="ec_tophtml" title="상단 HTML 입력"  class="ta_textarea w9" >${edusatConf.ec_tophtml }</textarea></td>
				</tr>
				<tr>
					<th scope="row"><label for="ec_btmhtml">하단 HTML 정보</label></th>
					<td class="left"><textarea style="height:250px;" id="ec_btmhtml" name="ec_btmhtml" title="하단 HTML 입력" class="ta_textarea w9" >${edusatConf.ec_btmhtml }</textarea></td>
				</tr>
				
				<tr>
					<th scope="row"><label for="ec_btmhtml">도서관 상/하단 HTML 정보</label></th>
					<td class="left"><input type="button" value="관리" class="btn_bl_default" onclick="javascript:window.open('/ncms/edusat/libConf.do', 'htmlOpen','width=700, height=840, scrollbars=yes', ''); " /></td>
				</tr>
			
				
				</table>
			</fieldset>
			<div class="contoll_box">
				<span><input type="submit" value="등록" class="btn_bl_default" /></span>
			</div>

		</form>
</div>
<!-- 내용들어가는곳 -->