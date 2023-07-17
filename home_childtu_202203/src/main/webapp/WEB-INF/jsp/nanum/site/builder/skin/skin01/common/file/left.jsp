<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<!-- 중간영역 -->
		<div id="container">
			<div class="wsize">
				<!-- style="background-image : url('/nanum/site/builder/skin/skin01/img/common/side_bg02.gif');" -->
				<c:choose>
					<c:when test="${param.idx eq '2588' or param.idx eq '2589'  }">
						<c:set var="imgUrl" value="side_bg01.gif" />
					</c:when>
										<c:when test="${param.idx eq '2590' or param.idx eq '2591' or param.idx eq '2592' or fn:contains(pageContext.request.requestURL, '/edusat/') }">
						<c:set var="imgUrl" value="side_bg02.gif" />
					</c:when>
					<c:when test="${param.a_num eq '69394427' or param.a_num eq '75543687' or param.idx eq '2595' or param.a_num eq '19211496'}">
						<c:set var="imgUrl" value="side_bg03.gif" />
					</c:when>
					<c:when test="${param.a_num eq '45932661' or param.a_num eq '51268418' or fn:contains(pageContext.request.requestURL, '/poll/') or param.a_num eq '59907913' }">
						<c:set var="imgUrl" value="side_bg04.gif" />
					</c:when>
					<c:when test="${fn:contains(pageContext.request.requestURL, '/member/') or fn:contains(pageContext.request.requestURL, '/mylib/') }">
						<c:set var="imgUrl" value="side_bg05.gif" />
					</c:when>
				</c:choose>
				<!--사이드영역-->
				<div id="sidebar">
					<div class="stit" style="background-image : url('/nanum/site/builder/skin/skin01/img/common/${imgUrl}');">
						<p class="eng">${empty first_menu_name_eng ? '' : first_menu_name_eng}</p>
						<h2>${first_menu_name}</h2>
					</div>
					<div id="sidemenu">
						<p class="tit jsMMenuText"><a href="#empty" class="jsMMenuBtn">${first_menu_name }</a></p>
						<ul id="jsMMenu">
							<%-- 공통으로 사용되는 레프트메뉴 --%>
							<jsp:include page="../../../common/file/inc_left.jsp" />
						</ul>
					</div>
					<div id="jsFacetLeft">
					<%-- 자료검색 패싯데이타영역 --%>
					</div>
					
					<!-- 바로가기 메뉴 -->
					<div class="left_gobtn">
						<ul>
							<li class="icon01"><a href="/main/edusat/list.do?gubun=47">프로그램 신청</a></li>
							<li class="icon02"><a href="/main/contents.do?a_num=19211496">일정표</a></li>
							<li class="icon05"><a href="/main/contents.do?a_num=51268418">참여후기</a></li>
							<li class="icon03"><a href="/main/contents.do?a_num=59907913">홍보영상</a></li>
							<li class="icon04"><a href="/main/poll/list.do">설문조사</a></li>
						</ul>
					</div>
					<!-- //바로가기 메뉴 -->
				</div>
				<!--//사이드영역-->
				<!-- 컨텐츠영역 -->
				<div id="contents">
					<!-- 인쇄영역 -->
					<div id="print_wrap">