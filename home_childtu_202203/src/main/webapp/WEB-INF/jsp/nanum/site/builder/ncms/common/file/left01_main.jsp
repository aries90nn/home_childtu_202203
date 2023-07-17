<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<%--통합사이트(main) --%>
			<%--
			<ul>
				<li id="menu1" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />투명우산 소식지</a>
						<ul class="depth2">
							<li id="menu1-1" class="none"><a href="/${BUILDER_DIR }/ncms/ebookpdf/list.do"  class="category dot">소식지 리스트</a></li>
							<li id="menu1-2" class="none"><a href="/${BUILDER_DIR }/ncms/ebookpdf/write.do"  class="category dot">소식지 등록</a></li>
						</ul>
				</li>
			</ul>
 			--%>
			<ul>
				<li id="menu1" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />프로그램 신청관리</a>
						<ul class="depth2">
							<li id="menu1-1" class="none"><a href="/${BUILDER_DIR }/ncms/edusat/list.do"  class="category dot">프로그램/신청자 리스트</a></li>
							<li id="menu1-2" class="none"><a href="/${BUILDER_DIR }/ncms/edusat/write.do"  class="category dot">신규프로그램생성</a></li>
							<li id="menu1-2" class="none"><a href="/${BUILDER_DIR }/ncms/edusat_code/write.do"  class="category dot">프로그램분류관리</a></li>
							<!-- <li id="menu1-3" class="none"><a href="/${BUILDER_DIR }/ncms/edusat_blacklist/list.do"  class="category dot">신청자경고 리스트</a></li> -->
						</ul>
				</li>
			</ul>

	<!--
			<ul>
				<li id="menu1_1" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />강좌기수관리</a>
						<ul class="depth2">
							<li id="menu1_1-1" class="none"><a href="/${BUILDER_DIR }/ncms/edusat_season/list.do"  class="category dot">강좌기수 리스트</a></li>
							<li id="menu1_1-2" class="none"><a href="/${BUILDER_DIR }/ncms/edusat_season/write.do"  class="category dot">강좌기수 생성</a></li>
						</ul>
				</li>
			</ul>
	-->
	<!--
	<ul>
		<li id="menu18" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />조직도관리</a>
			<ul class="depth2">
				<li id="menu1-1173" class="none"><a href="/${BUILDER_DIR}/ncms/buseo_member/write.do"  class="category dot">부서구성원관리</a></li>
				<li id="menu1-1172" class="none"><a href="/${BUILDER_DIR}/ncms/buseo/write.do"  class="category dot">부서관리</a></li>
			</ul>
		</li>
	</ul>
	-->
	
			<ul>
				<li id="menu2" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />설문조사</a>
						<ul class="depth2">
							<li id="menu5-1" class="none"><a href="/${BUILDER_DIR }/ncms/poll/list.do"  class="category dot">설문조사 리스트</a></li>
							<li id="menu5-2" class="none"><a href="/${BUILDER_DIR }/ncms/poll/write.do"  class="category dot">신규 설문조사 생성</a></li>
						</ul>
				</li>
			</ul>
			
			<ul>
				<li id="menu3" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />투명우산 재고관리</a>
						<ul class="depth2">
							<li id="menu5-1" class="none"><a href="/${BUILDER_DIR }/ncms/stock/write.do"  class="category dot">투명우산 재고관리</a></li>
						</ul>
				</li>
			</ul>

			<ul>
				<li id="menu4" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />커뮤니티</a>
						<ul class="depth2">
							<li id="menu4-1" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/notice.do?a_num=69394427"  class="category dot">공지사항</a></li>
							<li id="menu4-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/qna.do?a_num=75543687"  class="category dot">활동갤러리</a></li>
							<li id="menu4-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/event.do?a_num=19211496"  class="category dot">일정표</a></li>
							<li id="menu4-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/faq.do?a_num=45932661"  class="category dot">자유게시판</a></li>
							<li id="menu4-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/bongsa.do?a_num=51268418"  class="category dot">참여후기</a></li>
							<li id="menu4-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/sotong.do?a_num=59907913"  class="category dot">홍보영상</a></li>
						</ul>
				</li>
			</ul>