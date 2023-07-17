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

<c:set var="prepage" value="${empty param.prepage ? 'list.do' : param.prepage}" />
<link rel="Stylesheet" type="text/css" href="/nanum/site/edusat/css/common.css" />
<script type="text/javascript" src="/nanum/site/edusat/js/common.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/common_dev.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="/nfu//NFU_conf.js"></script>
<link type="text/css" rel="stylesheet" href="/nfu//NFU_css.css">
<script type="text/javascript" src="/nfu/NFU_class.js" charset="utf-8"></script>
<style>
.text1.text2 {
    width: 70px;
    display: inline-block;
}
</style>


<jsp:include page="./config.jsp" />
<jsp:include page="./tophtml.jsp" />


<c:set var="es_name" value="${ss_m_name}" />
<c:if test="${edusatreq.es_name !=null and edusatreq.es_name ne ''}">
	<c:set var="es_name" value="${edusatreq.es_name}" />
</c:if>

<c:set var="es_jumin" value="${empty edusatreq.es_jumin ? fn:replace(sessionScope.ss_user_birthday,'/','') : edusatreq.es_jumin }" />
<c:if test="${empty es_jumin }">
	<c:set var="es_jumin" value="${sessionScope.ss_m_birth }" />
</c:if>
<c:set var="es_jumin_y" value="${fn:substring(es_jumin,0,4)}" />
<c:set var="es_jumin_m" value="${fn:substring(es_jumin,4,6)}" />
<c:set var="es_jumin_d" value="${fn:substring(es_jumin,6,8)}" />

<c:set var="es_sex" value="${edusatreq.es_sex }" />
<c:if test="${empty es_sex }">
	<c:if test="${sessionScope.ss_m_sex eq '0' }">
		<c:set var="es_sex" value="1" />
	</c:if>
	<c:if test="${sessionScope.ss_m_sex eq '1' }">
		<c:set var="es_sex" value="2" />
	</c:if>
</c:if>
<c:if test="${empty es_sex }">
	<c:if test="${sessionScope.ss_user_sex eq '0' }">
		<c:set var="es_sex" value="1" />
	</c:if>
	<c:if test="${sessionScope.ss_user_sex eq '1' }">
		<c:set var="es_sex" value="2" />
	</c:if>
</c:if>


<c:if test="${edusat.edu_temp4 eq 'Y'}">
	<c:if test="${!empty edusat.edu_temp5 and edusat.edu_temp5 ne '|' }">
		<c:set var="edu_temp5_arr" value="${fn:split(edusat.edu_temp5, '[|]') }" />
		<c:set var="chk_birth_s" value="${edu_temp5_arr[0]}" />
		<c:set var="chk_birth_e" value="${edu_temp5_arr[1]}" />
	</c:if>
</c:if>

<div id="board" style="width:100%;">
 
<!-- 쓰기 테이블 -->
	<div class="table_bwrite">
 
			<table cellspacing="0" summary="의 이름, 비밀번호, 내용을 입력">
			<caption>프로그램 신청</caption>
			<colgroup>
					<col width="20%" />
					<col width="10%"/>
					<col width="60%" />
			</colgroup>
			<tbody>
			
				<tr>
					<th scope="row">프로그램명</th>
					<td  colspan="2">${edusat.edu_subject }</td>
				</tr>
				
				<tr>
					<th scope="row">성명</th>
					<td colspan="2">
						${edusatreq.es_name }
					</td>
				</tr>
				
				<c:if test="${gubun_num ne 2 }">
				<tr>
					<th scope="row">신청자 구분</th>
					<td colspan="2">
						<c:choose>
							<c:when test="${edusatreq.es_temp1 eq 'i' }"> 유치원</c:when>
							<c:when test="${edusatreq.es_temp1 eq 'a' }"> 어린이집</c:when>
							<c:when test="${edusatreq.es_temp1 eq 's' }"> 기타</c:when>
							<c:when test="${edusatreq.es_temp1 eq 'c' }"> 지역아동센터</c:when>
						</c:choose>
					</td>
				</tr>
				</c:if>
				
				<c:if test="${gubun_num eq 2 }">
				<tr>
					<th scope="row">사번</th>
					<td colspan="2">${edusatreq.es_temp3 }</td>
				</tr>
				<tr>
					<th scope="row">직급</th>
					<td colspan="2">${edusatreq.es_temp4 }</td>
				</tr>
				<tr>
					<th scope="row">팀명</th>
					<td colspan="2">${edusatreq.es_temp5 }</td>
				</tr>
				</c:if>
				
				<%//<c:if test="${gubun_num ne '1' and edu_field1_yn ne 'R' and edusat.edu_field1_yn ne 'Y'}"> %>
				<c:if test="${gubun_num ne '1' and gubun_num ne '2' and edusat.edu_field1_yn ne 'R' and edusat.edu_field1_yn ne 'Y'}">
				<!-- <tr style="display: none;" id="es_name2_wrab"> -->
				<tr id="es_name2_wrab">
					<th scope="row">기관명</th>
					<td colspan="2">
						${edusatreq.es_name2}
						
					</td>
				</tr>
				</c:if>
				
				<c:if test="${edusat.edu_field1_yn eq 'R' or edusat.edu_field1_yn eq 'Y'}">
				<tr>
					<th scope="row">기관명</th>
					<td colspan="2">
						${edusatreq.es_name2}
						
					</td>
				</tr>
				</c:if>
				
				<c:if test="${(edusat.edu_field2_yn eq 'R' or edusat.edu_field2_yn eq 'Y') and gubun_num eq '1'}">
				<tr>
					<th scope="row">기관주소</th>
					<td colspan="2">
						<style>
							#Search_Form {overflow:auto; height:150px; border:1px solid #cdcdcd; margin-top:3px; margin-right:10px; padding:6px;}
							#Addr_search2 span.text1 {font-size:0.9em; color:#ad6767;}
						</style>

					<div id="Addr_search1" style="display: block;">
						${edusatreq.es_zipcode }
						<div class="pt3">
						${edusatreq.es_addr1 }<br/>
						${edusatreq.es_addr2 }
						</div>
					</div>
					</td>
				</tr>
				</c:if>
				<tr>
					<th scope="row">이메일</th>
					<td colspan="2">${edusatreq.es_email }</td>
				</tr>
				<tr>
					<th rowspan="2" scope="col">연락처</th>
					<td scope="row">유선전화</td>
					
					<td>
						${edusatreq.es_bphone1 } - ${edusatreq.es_bphone2 } - ${edusatreq.es_bphone3 } 
					</td>
				</tr>
				
				<tr>
					<td scope="row">휴대전화</td>
					<td>
						${edusatreq.es_phone1 } - ${edusatreq.es_phone2 } - ${edusatreq.es_phone3 }
					</td>
				</tr>
				<tr>
					<th scope="row">${gubun_num ne '2' ? '수령자명' : '신청학교명' }</th>
					<td colspan="2" >
						${edusatreq.es_temp2 }
					</td>
				</tr>
				<c:if test="${(edusat.edu_field2_yn eq 'R' or edusat.edu_field2_yn eq 'Y') and gubun_num ne '1'}">
				<tr>
					<th scope="row">주소</th>
					<td colspan="2">
						<style>
							#Search_Form {overflow:auto; height:150px; border:1px solid #cdcdcd; margin-top:3px; margin-right:10px; padding:6px;}
							#Addr_search2 span.text1 {font-size:0.9em; color:#ad6767;}
						</style>

					<div id="Addr_search1" style="display: block;">
						${edusatreq.es_zipcode }		
						<div class="pt3">
							${edusatreq.es_addr1 }<br> 
							${edusatreq.es_addr2 }
						</div>
					</div>
					</td>
				</tr>
				</c:if>
				<tr>
					<th scope="row">신청수량</th>
					<td colspan="2" >
						${edusatreq.es_etc } 개
					</td>
				</tr>
				
				<c:if test="${edusat.edu_field4_yn eq 'R' or edusat.edu_field4_yn eq 'Y'}">
				<tr>
					<th scope="row">교육대상 및 인원</span></th>
					<td>1 ~ 6학년</td>
					<td>
						<c:choose>
							<c:when test="${edusatreq.es_video eq 'Y' }">
								신청
							</c:when>
							<c:otherwise>
								미신청
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<%/*
				<tr>
					<th scope="row" rowspan="2">교육대상 및 인원<br/><span style="font-size:0.8rem">(교통안전교육의 대상은<br/> 6세 ~ 초등저학년까지입니다)</span></th>
					<td>1, 2학년</td>
					<td>
						<div class="mb10">
							<span class="text1 text2">1학년</span>
							${edusatreq.es_fgrade ne '' ?  edusatreq.es_fgrade : '0' } 명
							${edusatreq.es_fgrade_class ne '' ? edusatreq.es_fgrade_class : '0' } 개반
						</div>
						<div>
							<span class="text1 text2">2학년</span>
							${edusatreq.es_sgrade ne '' ? edusatreq.es_sgrade : '0' } 명
							${edusatreq.es_sgrade_class ne '' ? edusatreq.es_sgrade_class : '0' } 개반
						</div>						
					</td>
				</tr>
				
				<tr>
					<td>3 ~ 6학년<br/>(교육영상 시청)</td>
					<td>
						<c:choose>
							<c:when test="${edusatreq.es_video eq 'Y' }">
								신청
							</c:when>
							<c:otherwise>
								미신청
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				*/%>
				<%--
				<tr>
					<td>유치원</td>
					<td>
						<div class="mb10">
							<span class="text1 text2">6세</span>
							${edusatreq.es_kgrade ne '' ? edusatreq.es_kgrade : '0' } 명
							${edusatreq.es_kgrade_class ne '' ? edusatreq.es_kgrade_class : '0' } 개반
						</div>
						<div class="mb10">
							<span class="text1 text2">7세</span>
							${edusatreq.es_cgrade ne '' ? edusatreq.es_cgrade : '0' } 명
							${edusatreq.es_cgrade_class ne '' ? edusatreq.es_cgrade_class : '0' } 개반
						</div>				
					</td>
				</tr>
				</c:if>
				
				<c:if test="${(edusat.edu_field5_yn eq 'R' or edusat.edu_field5_yn eq 'Y')}">
				<tr>
					<th scope="row">귀 기관에서 최근 1년간 어린이 교통사고가 발생한 적이 있습니까?</th>
					<td colspan="2">
						<c:choose>
							<c:when test="${edusatreq.es_problem_yn eq 'Y' }">
								예 (총 ${edusatreq.es_problem } 건)
							</c:when>
							<c:otherwise>
								아니요
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				 --%>
				</c:if>
				
				<c:if test="${gubun_num eq '2' }">
				<tr>
					<th scope="row">학교와의 관계를<br/>선택해주세요.</th>
					<td colspan="2">
						<c:choose>
							<c:when test="${edusatreq.es_temp6 eq '1'}">
								자녀의 학교
							</c:when>
							<c:when test="${edusatreq.es_temp6 eq '2'}">
								친척자녀의 학교
							</c:when>
							<c:when test="${edusatreq.es_temp6 eq '3'}">
								본인 및 자녀의 모교
							</c:when>
							<c:when test="${edusatreq.es_temp6 eq '4'}">
								기타
							</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th scope="row" rowspan="3">자녀 또는 친척자녀가<br/>신청학교의 재학생일 경우<br/>작성해주세요</th>
					<td>자녀의 성명</td>
					<td>
						${edusatreq.es_temp7 }					
					</td>
				</tr>
				
				<tr>
					<td>담임교사의<br/>성명</td>
					<td>
						${edusatreq.es_temp8 }					
					</td>
				</tr>
				
				<tr>
					<td>담임교사의<br/>연락처</td>
					<td>
						${edusatreq.es_temp9 }					
					</td>
				</tr>
				
				<tr>
					<th scope="row">나눔식 가능여부 선택해주세요.</th>
					<td colspan="2">
					<c:choose>
							<c:when test="${edusatreq.es_temp10 eq '1'}">
								나눔식 가능
							</c:when>
							<c:when test="${edusatreq.es_temp10 eq '2'}">
								나눔식 불가능
							</c:when>
						</c:choose>
					</td>
				</tr>
				
				</c:if>
				
				<tr>
					<th scope="row">신청사연</th>
					<td colspan="2" style="max-width: 100px;overflow: scroll;">
						${edusatreq.es_memo }
					</td>
				</tr>
				
				<c:if test="${edusat.edu_field3_yn eq 'R' or edusat.edu_field3_yn eq 'Y'}">
				<tr>
					<th scope="row">첨부파일</th>
					<td style="padding:5px;" colspan="2">
						<c:forEach var="t" begin="1" end="5" step="1">
							<c:set var="file_name">es_file${t}</c:set>
							<c:set var="es_file_name">${edusatreq[file_name]}</c:set>
							<input type="hidden" id="es_file${t}" name="es_file${t}" value="${es_file_name}" />
							<c:if test="${es_file_name ne ''}">
								<img src="/data/edusat/${edusatreq[file_name]}" alt="${edusatreq[file_name]}" style="width:300px;"/>
								${es_file_name} 
								<!-- 
								<a href="#delete" onclick="delete_ok('${edusatreq.es_idx}','${edusatreq.edu_idx}','${t}','${del_rtn_page}');" onkeyup="delete_ok('${edusatreq.es_idx}','${edusatreq.edu_idx}','${t}','${del_rtn_page}');" title="첨부파일 삭제하기">
								<img src="/nanum/site/board/nninc_simple/img/dr_del_bt.gif" alt="첨부파일 삭제하기" /></a><br />
								 -->
									
								<c:set var= "file_count" value="${file_count + 1}"/>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				</c:if>

			<tr>
				<th scope="row">신청일</th>
				<td class="left" colspan="3">${edusatreq.es_wdate}</td>
			</tr>

				
			</tbody>
			</table>
	
	</div>
	<!-- //쓰기 테이블 -->
	
	
	
	
 
	<!-- 버튼 -->
	<div class="btn_w">
		<a href="${prepage }" class="con_btn blue" >돌아가기</a>
	</div>
	<!-- //버튼 -->
 
	
</div>

<jsp:include page="./bthtml.jsp" />
