<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<%--통합(main)은 개별 강좌를 등록하지 않음 --%>
<c:if test="${BUILDER_DIR ne 'main' }">
			<ul>
				<li id="menu1" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />강좌신청관리</a>
						<ul class="depth2">
							<li id="menu1-1" class="none"><a href="/${BUILDER_DIR }/ncms/edusat/list.do"  class="category dot">강좌/신청자 리스트</a></li>
							<li id="menu1-2" class="none"><a href="/${BUILDER_DIR }/ncms/edusat/write.do"  class="category dot">신규강좌생성</a></li>
							<li id="menu1-2" class="none"><a href="/${BUILDER_DIR }/ncms/edusat_code/write.do"  class="category dot">강좌분류관리</a></li>
							<li id="menu1-3" class="none"><a href="/${BUILDER_DIR }/ncms/edusat_blacklist/list.do"  class="category dot">신청자경고 리스트</a></li>
						</ul>
				</li>
			</ul>

			<ul>
				<li id="menu1_1" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />강좌기수관리</a>
						<ul class="depth2">
							<li id="menu1_1-1" class="none"><a href="/${BUILDER_DIR }/ncms/edusat_season/list.do"  class="category dot">강좌기수 리스트</a></li>
							<li id="menu1_1-2" class="none"><a href="/${BUILDER_DIR }/ncms/edusat_season/write.do"  class="category dot">강좌기수 생성</a></li>
						</ul>
				</li>
			</ul>

</c:if>

<%-- 광주에서 사용하지 않음
			<ul>
				<li id="menu2" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />추천도서</a>
						<ul class="depth2">
							<li id="menu2-1" class="none"><a href="/${BUILDER_DIR }/ncms/site/recommbook/board.do?a_num=78492892"  class="category dot">추천도서 리스트</a></li>
						</ul>
				</li>
			</ul>

			<ul>
				<li id="menu3" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />사물함신청관리</a>
						<ul class="depth2">
							<li id="menu3-1" class="none"><a href="/${BUILDER_DIR }/ncms/locker/list.do"  class="category dot">사물함신청목록</a></li>
							<li id="menu3-2" class="none"><a href="/${BUILDER_DIR }/ncms/locker/write.do"  class="category dot">사물함신청등록</a></li>
							<li id="menu3-3" class="none"><a href="/${BUILDER_DIR }/ncms/locker_season/list.do"  class="category dot">사물함신청차수관리</a></li>
						</ul>
				</li>
			</ul>
 --%>
<c:if test="${BUILDER_DIR eq 'main' }">
			<%--통합 --%>
			<ul>
				<li id="menu4" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />열린공간</a>
						<ul class="depth2">
							<li id="menu4-1" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/notice.do?a_num=83166347"  class="category dot">공지사항</a></li>
							<li id="menu4-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/qna.do?a_num=60787136"  class="category dot">묻고답하기</a></li>
							<li id="menu4-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/faq.do?a_num=59703381"  class="category dot">FAQ</a></li>
						</ul>
				</li>
			</ul>
</c:if>

<c:if test="${BUILDER_DIR eq 'student' }">
			<%--학생독립기념회관 --%>
			<ul>
				<li id="menu4" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />학생독립기념회관 업무</a>
						<ul class="depth2">
							<li id="menu4-1" class="none"><a href="/${BUILDER_DIR }/ncms/close/close.do?cl_category=1"  class="category dot">휴관일관리</a></li>
							<li id="menu4-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/event.do?a_num=53201418"  class="category dot">일정관리</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu5" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />열린공간</a>
						<ul class="depth2">
							<li id="menu5-1" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/notice.do?a_num=47471516"  class="category dot">공지사항</a></li>
							<li id="menu5-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/qna.do?a_num=78796316"  class="category dot">묻고답하기</a></li>
							<li id="menu5-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/faq.do?a_num=69728338"  class="category dot">FAQ</a></li>
							<li id="menu5-4" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/bongsa.do?a_num=83166347"  class="category dot">봉사활동</a></li>
							<li id="menu5-5" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/clean.do?a_num=10139739"  class="category dot">클린신고센터</a></li>
							<li id="menu5-6" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/upright.do?a_num=80255365"  class="category dot">청렴자료실</a></li>
							<li id="menu5-7" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/sotong.do?a_num=65524169"  class="category dot">소통마루</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu6" class="on"><a class="category on_default"  href="#stats" onclick="goLibStats();"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />학생독립기념회관 근무일지(이동)</a>
				</li>
			</ul>
</c:if>
<c:if test="${BUILDER_DIR eq 'geumho'  }">
			<%--금호평생교육관  --%>
			<ul>
				<li id="menu4" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />금호평생교육관 업무</a>
						<ul class="depth2">
							<li id="menu4-1" class="none"><a href="/${BUILDER_DIR }/ncms/close/close.do?cl_category=2"  class="category dot">휴관일관리</a></li>
							<li id="menu4-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/event.do?a_num=42138545"  class="category dot">일정관리</a></li>
							<li id="menu4-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/bongsa.do?a_num=41732388"  class="category dot">봉사활동</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu5" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />열린공간</a>
						<ul class="depth2">
							<li id="menu5-1" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/notice.do?a_num=72205222"  class="category dot">공지사항</a></li>
							<li id="menu5-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/faq.do?a_num=70516094"  class="category dot">FAQ</a></li>
							<li id="menu5-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/qna.do?a_num=11302928"  class="category dot">묻고답하기</a></li>
							<li id="menu5-5" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/clean.do?a_num=99669219"  class="category dot">클린신고센터</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu6" class="on"><a class="category on_default"  href="#stats" onclick="goLibStats();"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />금호평생교육관 근무일지(이동)</a>
				</li>
			</ul>
</c:if>
<c:if test="${BUILDER_DIR eq 'gecs'  }">
			<%--학생교육문화회관   --%>
			<ul>
				<li id="menu4" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />학생교육문화회관 업무</a>
						<ul class="depth2">
							<li id="menu4-1" class="none"><a href="/${BUILDER_DIR }/ncms/close/close.do?cl_category=3"  class="category dot">휴관일관리</a></li>
							<li id="menu4-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/event.do?a_num=17554091"  class="category dot">일정관리</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu5" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />열린공간</a>
						<ul class="depth2">
							<li id="menu5-1" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/notice.do?a_num=76634026"  class="category dot">공지사항</a></li>
							<li id="menu5-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/qna.do?a_num=56324498"  class="category dot">묻고답하기</a></li>
							<li id="menu5-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/faq.do?a_num=22606284"  class="category dot">FAQ</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu6" class="on"><a class="category on_default"   href="#stats" onclick="goLibStats();"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />학생교육문화회관 근무일지(이동)</a>
				</li>
			</ul>
</c:if>
<c:if test="${BUILDER_DIR eq 'jungang'  }">
			<%--중앙도서관   --%>
			<ul>
				<li id="menu4" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />중앙도서관 업무</a>
						<ul class="depth2">
							<li id="menu4-1" class="none"><a href="/${BUILDER_DIR }/ncms/close/close.do?cl_category=4"  class="category dot">휴관일관리</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu5" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />열린공간</a>
						<ul class="depth2">
							<li id="menu5-1" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/notice.do?a_num=11547698"  class="category dot">공지사항</a></li>
							<li id="menu5-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/event.do?a_num=39806457"  class="category dot">일정관리</a></li>
							<li id="menu5-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/qna.do?a_num=29582567"  class="category dot">묻고답하기</a></li>
							<li id="menu5-4" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/faq.do?a_num=69179419"  class="category dot">FAQ</a></li>
							<li id="menu5-5" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/bongsa.do?a_num=85409470"  class="category dot">봉사활동</a></li>
							<li id="menu5-6" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/clean.do?a_num=93113241"  class="category dot">클린신고센터</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu6" class="on"><a class="category on_default"  href="#stats" onclick="goLibStats();"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />중앙도서관 근무일지(이동)</a>
				</li>
			</ul>
</c:if>
<c:if test="${BUILDER_DIR eq 'songjung'  }">
			<%--송정도서관   --%>
			<ul>
				<li id="menu4" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />송정도서관 업무</a>
						<ul class="depth2">
							<li id="menu4-1" class="none"><a href="/${BUILDER_DIR }/ncms/close/close.do?cl_category=5"  class="category dot">휴관일관리</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu5" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />열린공간</a>
						<ul class="depth2">
							<li id="menu5-1" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/notice.do?a_num=19948620"  class="category dot">공지사항</a></li>
							<li id="menu5-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/qna.do?a_num=96676158"  class="category dot">묻고답하기</a></li>
							<li id="menu5-4" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/faq.do?a_num=36338663"  class="category dot">FAQ</a></li>
							<li id="menu5-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/event.do?a_num=91625434"  class="category dot">일정관리</a></li>
							<li id="menu5-6" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/clean.do?a_num=83557898"  class="category dot">클린신고센터</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu4" class="on"><a class="category on_default"  href="#stats" onclick="goLibStats();"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />송정도서관 근무일지(이동)</a>
				</li>
			</ul>
</c:if>

<c:if test="${BUILDER_DIR eq 'seokbong'  }">
			<%--석봉도서관   --%>
			<ul>
				<li id="menu4" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />석봉도서관 업무</a>
						<ul class="depth2">
							<li id="menu4-1" class="none"><a href="/${BUILDER_DIR }/ncms/close/close.do?cl_category=6"  class="category dot">휴관일관리</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu5" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />열린마당</a>
						<ul class="depth2">
							<li id="menu5-1" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/notice.do?a_num=65499793"  class="category dot">공지사항</a></li>
							<li id="menu5-2" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/event.do?a_num=70508447"  class="category dot">일정관리</a></li>
							<li id="menu5-3" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/qna.do?a_num=83697151"  class="category dot">묻고답하기</a></li>
							<li id="menu5-4" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/faq.do?a_num=36317459"  class="category dot">FAQ</a></li>
							<%-- <li id="menu5-5" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/faq.do?a_num=68273365"  class="category dot">봉사활동</a></li> --%>
							<li id="menu5-6" class="none"><a href="/${BUILDER_DIR }/ncms/site/board/clean.do?a_num=23242587"  class="category dot">클린신고센터</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu4" class="on"><a class="category on_default" href="#stats" onclick="goLibStats();"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />석봉도서관 근무일지(이동)</a>
				</li>
			</ul>
</c:if>


<script type="text/javascript">

function goLibStats(){
	alert("근무일지 사이트로 이동합니다.\n\n기존데이타는 이전완료되었습니다.\n\nhttp://lib.gen.go.kr/stats/ 로 바로 접속하실수 있습니다.");
	location.href="/stats/";
}

</script>