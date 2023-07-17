<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<c:forEach var="t" begin="1" end="8" step="1">
		<c:set var="bb_temp">b_temp${t}</c:set>
		<c:set var="aa_ftemp">a_ftemp${t}</c:set>
		<c:set var="aa_ftemp_req">a_ftemp${t}_req</c:set>
		<c:set var="aa_ftemp_str">a_ftemp${t}_str</c:set>
		<c:set var="aa_ftemp_type">a_ftemp${t}_type</c:set>
		<c:set var="aa_ftemp_val">a_ftemp${t}_val</c:set>
		
		<c:set var="b_temp">${board[bb_temp]}</c:set>
		<c:set var="a_ftemp">${config[aa_ftemp]}</c:set>
		<c:set var="a_ftemp_req">${config[aa_ftemp_req]}</c:set>
		<c:set var="a_ftemp_str">${config[aa_ftemp_str]}</c:set>
		<c:set var="a_ftemp_type">${config[aa_ftemp_type]}</c:set>
		<c:set var="a_ftemp_val">${config[aa_ftemp_val]}</c:set>
			
			<c:if test='${a_ftemp eq "Y"}'>
				<tr>
					<th scope="row"><c:if test="${a_ftemp_req eq 'Y'}"><img height='10' width='7' alt='*(필수항목)' src='/nanum/site/board/nninc_simple/img/ic_vcheck.gif'/> </c:if><label for="b_temp${t}">${a_ftemp_str}</label></th>
					<td>
				<c:choose>
					<c:when test="${a_ftemp_type eq 'T'}">
						<input type="text" size="50" id="b_temp${t}" name="b_temp${t}" class="board_input" onfocus="focus_on1(this);" onblur="focus_off1(this);" value="${b_temp}" maxlength="100" />
						<span class="text1">* 100자리 이내로 입력해주세요.</span>
					</c:when>
					<c:when test="${a_ftemp_type eq 'R'}">
						<c:set var="i" value="1" />
						<c:forEach var="a_ftemp_val_arr" items="${fn:split(a_ftemp_val,',')}">
							<input type="radio" name="b_temp${t}" id="b_temp${t}_${i}" value="${a_ftemp_val_arr}" ${b_temp eq a_ftemp_val_arr ? "checked" : ""} style="vertical-align:middle" /><label for="b_temp${t}_${i}">${a_ftemp_val_arr}</label>
							<c:set var="i" value="${i+1}" />
						</c:forEach>
					</c:when>
					<c:when test="${a_ftemp_type eq 'C'}">
						<c:set var="i" value="1" />
						<c:forEach var="a_ftemp_val_arr" items="${fn:split(a_ftemp_val,',')}" varStatus="status">
							<!--  -->
							<c:set var="checked" value="" />
							<c:forEach var="b_temp_arr" items="${fn:split(b_temp,',')}">
								<c:if test='${b_temp_arr eq a_ftemp_val_arr}'>
									<c:set var="checked" value="checked" />
								</c:if>
							</c:forEach>
							<!--  -->
							<input type="checkbox" name="b_temp${t}" id="b_temp${t}_${i}" value="${a_ftemp_val_arr}" ${checked} style="vertical-align:middle" /><label for="b_temp${t}_${i}">${a_ftemp_val_arr}</label>
							<c:set var="i" value="${i+1}" />
							
						</c:forEach>
					</c:when>
					<c:when test="${a_ftemp_type eq 'S'}">
						<c:set var="i" value="1" />
						<select id="b_temp${t}" name="b_temp${t}" title="${a_ftemp_str} 선택">
						<c:forEach var="a_ftemp_val_arr" items="${fn:split(a_ftemp_val,',')}">
							<option value="${a_ftemp_val_arr}" ${b_temp eq a_ftemp_val_arr ? "selected" : ""}>${a_ftemp_val_arr}</option>
							<c:set var="i" value="${i+1}" />
						</c:forEach>
						</select>
					</c:when>
					</c:choose>
					</td>
				</tr>

				</c:if>
			</c:forEach>