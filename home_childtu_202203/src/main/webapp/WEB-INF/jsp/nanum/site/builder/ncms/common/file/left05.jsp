<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

			<ul>
				<li id="menu1" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />방문자통계</a>
						<ul class="depth2">
							<li id="menu1-1" class="none"><a href="/${BUILDER_DIR }/ncms/stats/h.do"  class="category dot">시간별 통계</a></li>
							<li id="menu1-2" class="none"><a href="/${BUILDER_DIR }/ncms/stats/d.do"  class="category dot">일별 통계</a></li>
							<li id="menu1-3" class="none"><a href="/${BUILDER_DIR }/ncms/stats/w.do"  class="category dot">요일별 통계</a></li>
							<li id="menu1-4" class="none"><a href="/${BUILDER_DIR }/ncms/stats/m.do"  class="category dot">월별 통계</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu2" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />방문자 접속위치정보</a>
						<ul class="depth2">
							<li id="menu2-1" class="none"><a href="/${BUILDER_DIR }/ncms/stats/log.do"  class="category dot">방문자 접속위치정보</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu3" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />메뉴접속통계</a>
						<ul class="depth2">
							<li id="menu3-1" class="none"><a href="/${BUILDER_DIR }/ncms/stats_menu/stat.do"  class="category dot">메뉴접속통계</a></li>
						</ul>
				</li>
			</ul>
			<ul>
				<li id="menu4" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />관리자접속기록</a>
						<ul class="depth2">
							<li id="menu4-1" class="none"><a href="/${BUILDER_DIR }/ncms/log/list.do"  class="category dot">관리자 접속기록</a></li>
						</ul>
				</li>
			</ul>
