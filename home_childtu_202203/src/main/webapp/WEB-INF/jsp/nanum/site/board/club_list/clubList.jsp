<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
							
							<div class="bookclub">
								<div class="bookclub_top">
									<p class="txt">독서회 게시판은 도서관 동아리가 온라인상에서 자유롭게 의견을 나누는 곳입니다.<br />
									기타 문의사항은 <strong class="eng">063) 320-5622</strong>로 연락주시기 바랍니다.</p>
									<a href="?a_num=${config.a_num }&page_type=write&prepage=${nowPageEncode}" class="con_btn blue">동아리 만들기</a>
								</div>
								<div class="bookclub_list">
<c:forEach items="${club_list }" var="club" varStatus="no">
	<c:set var="img_url" value="/board/get_img.do?a_num=${config.a_num}&b_num=${club.b_num}&thum=Y&f_num=1&type=img" />
	<c:if test="${empty club.b_file1 }">
		<c:set var="img_url" value="/nanum/site/board/${config.a_level }/img/no.gif" />
	</c:if>
	
	<c:set var="board_url" value="href='#noservice'" />
	<c:set var="club_status" value="승인<br />대기중" />
	<c:set var="club_status_class" value=" noservice" />
	<c:if test="${club.b_temp8 eq '2' }">
		<c:set var="board_url" value="href='/${BUILDER_DIR }/contents.do?a_num=${board_a_num}&v_cate=${club.b_temp4 }' target='_blank'" />
		<c:set var="club_status" value="생각<br />나누기" />
		<c:set var="club_status_class" value="" />
	</c:if>
									<!-- -->
									<div class="clubBox${club_status_class}">
										<a ${board_url }>
											<p class="img"><img src="${img_url }" style="max-width:230px;" alt="리좀 문학회 이미지" /></p>
											<div class="info">
												<p class="tit">${club.b_subject }</p>
												<dl>
													<dt>대표자</dt>
													<dd>${club.b_temp1 }</dd>
												</dl>
												<dl>
													<dt>정기모임</dt>
													<dd>${club.b_temp2 }</dd>
												</dl>
												<dl>
													<dt>지원자격</dt>
													<dd>${club.b_temp3 }</dd>
												</dl>
											</div>
											<span class="circle">${club_status }</span>
										</a>
									</div>
</c:forEach>								
								</div>
							</div>