<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="robots" content="noindex, nofollow" />
	<title>관리자페이지 : 경고처리 </title>
	<!-- css, script-->
	<jsp:include page="../common/file/head_script.jsp" />
	<link rel="Stylesheet" type="text/css" href="/nanum/ncms/common/css/sortable.css" />
	
</head>

<body>

<!-- 중간 영역 -->
<div>
	<h1 class="tit">강좌신청자 경고처리</h1>

	<!-- 내용들어가는곳 -->
	<div id="contents_area">
		<form id= "frm_list" action="writeOk.do" method='post'>
			<div>
				<input type="hidden" name="eb_num" value="${edublacklist.eb_num}" />
				<input type="hidden" name="edu_idx" value="${edublacklist.edu_idx}" />
				<input type="hidden" name="ct_idx" value="${edublacklist.ct_idx}" />
				<input type="hidden" name="ct_idx2" value="${edublacklist.ct_idx2}" />
				<input type="hidden" name="edu_lib" value="${edublacklist.edu_lib}" />
				<input type="hidden" name="edu_gubun" value="${edublacklist.edu_gubun}" />
				<input type="hidden" name="edu_subject" value="${edublacklist.edu_subject}" />
				<input type="hidden" name="edu_resdate" value="${edublacklist.edu_resdate}" />
				<input type="hidden" name="edu_reedate" value="${edublacklist.edu_reedate}" />
				<input type="hidden" name="edu_sdate" value="${edublacklist.edu_sdate}" />
				<input type="hidden" name="edu_edate" value="${edublacklist.edu_edate}" />
				<input type="hidden" name="es_name" value="${edublacklist.es_name}" />
				<input type="hidden" name="es_id" value="${edublacklist.es_id}" />
				<input type="hidden" name="es_bphone1" value="${edublacklist.es_bphone1}" />
				<input type="hidden" name="es_bphone2" value="${edublacklist.es_bphone2}" />
				<input type="hidden" name="es_bphone3" value="${edublacklist.es_bphone3}" />
				<input type="hidden" name="es_wdate" value="${edublacklist.es_wdate}" />
				<input type="hidden" name="edu_ci" value="${edublacklist.edu_ci}" />
			</div>

			<fieldset>
				<table cellspacing="0" class="bbs_common bbs_default" style="width: 550px;">
				<caption>관리 서식</caption>
				<colgroup>
				<col width="100" />
				<col />	
				<col width="100" />
				<col />	
				</colgroup>

				<tbody>
				<tr>
					<th scope="row">강좌</th>
					<td class="left" colspan="3">${edublacklist.edu_subject}</td>
				</tr>
				<tr>
					<th scope="row">신청자명</th>
					<td class="left">${edublacklist.es_name} / 경고누적 : ${alert_count}회</td>
					<th scope="row">연락처</th>
					<td class="left">
						${edublacklist.es_bphone1}-${edublacklist.es_bphone2}-${edublacklist.es_bphone3}
					</td>
				</tr>
				<tr>

				<tr>
					<th scope="row"><label for="es_etc">경고사유</label></th>
					<td colspan="3" class="left"><input type="text" name="eb_comment" id="eb_comment" style="width:400px" class="input_box" maxlength="70" /></td>
				</tr>



				</tbody>
				</table>
			</fieldset>

			<div class="contoll_box">
				<span><input type="submit" value="등록" class="btn_bl_default" /></span> <span><input type="button" value="창닫기" onclick="self.close();" class="btn_wh_default" /></span>
			</div>
		</form>
	</div>
	<!-- 내용들어가는곳 -->

</div>

</body>
</html>