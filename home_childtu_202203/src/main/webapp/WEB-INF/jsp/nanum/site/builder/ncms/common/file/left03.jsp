<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

			<ul>
				<li id="menu1" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />회원관리</a>
						<ul class="depth2">
							<li id="menu1-1" class="none"><a href="/${BUILDER_DIR }/ncms/member/list.do"  class="category dot">회원리스트</a></li>
							<li id="menu1-2" class="none"><a href="/${BUILDER_DIR }/ncms/member/write.do"  class="category dot">신규회원생성</a></li>
						</ul>
				</li>
				<li id="menu2" class="on"><a class="category on_default"><img src="/nanum/ncms/img/common/side_icon03.png" class="png24 group" alt="그룹 아이콘" />권한관리</a>
						<ul class="depth2">
							<li id="menu1-1" class="none"><a href="/${BUILDER_DIR }/ncms/member_group/write.do"  class="category dot">회원그룹 권한설정</a></li>
						</ul>
				</li>
			</ul>
