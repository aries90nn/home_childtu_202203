<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<!-- top -->
	<div class="top_wrap">
		<div class="top_tab ">
			<ul>
				<li class="first ${BUILDER_DIR eq 'main' ? 'on' : ''}"><a href="/main/"><p>통합<span>도서관</span></p></a></li>
				<li class="${BUILDER_DIR eq 'student' ? 'on' : ''}"><a href="/student/"><p><span>광주학생</span>독립<span>운동기념회관</span></p></a></li>
				<li class="${BUILDER_DIR eq 'geumho' ? 'on' : ''}"><a href="/geumho/"><p>금호<span>평생교육관</span></p></a></li>
				<li class="${BUILDER_DIR eq 'gecs' ? 'on' : ''}"><a href="/gecs/"><p><span>광주학생</span>교육<span>문화회관</span></p></a></li>
				<li class="first ${BUILDER_DIR eq 'jungang' ? 'on' : ''}"><a href="/jungang/"><p><span>광주</span>중앙<span>도서관</span></p></a></li>
				<li class="${BUILDER_DIR eq 'songjung' ? 'on' : ''}"><a href="/songjung/"><p><span>광주</span>송정<span>도서관</span></p></a></li>
				<li class="${BUILDER_DIR eq 'seokbong' ? 'on' : ''}"><a href="/seokbong/"><p>석봉<span>도서관</span></p></a></li>
			</ul>
		</div>
	</div>
	<!-- //top -->
	