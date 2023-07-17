<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@
taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
					<ul>
						<li><p>휴관일</p>
							<div>
<c:forEach items="${closeList }" var="close" varStatus="no">
	<c:if test="${close.cl_name ne '법정공휴일' }">
						<span>${fn:substring(close.cl_date,8,10) }</span>
	</c:if>
</c:forEach>
							</div>
						</li>
						
						<li><p>법정공휴일</p>
							<div>
<c:forEach items="${closeList }" var="close" varStatus="no">
	<c:if test="${close.cl_name eq '법정공휴일' }">
						<span>${fn:substring(close.cl_date,8,10) }</span>
	</c:if>
</c:forEach>
							</div>
						</li>
<c:if test="${param.cl_category ne '3' }">
						<li class="p_txt">※ 법정공휴일은 <p>열람실만개방</p></li>
</c:if>
						
						<!-- <li><a href="#"><img src="/nanum/site/builder/main/sample01/img/over_txt.gif" alt="독립 휴관일 더보기" /></a></li> -->
					</ul>