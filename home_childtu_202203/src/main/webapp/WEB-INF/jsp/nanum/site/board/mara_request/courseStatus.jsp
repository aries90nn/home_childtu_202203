<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!-- 쓰기 -->
<div class="event_all">
	
	<h3 class="tit">3km</h3>
	<div class="event01 course01">
		<div class="bar_bg">
			<span class="pink_bar" style="width:${course_avg1}%"> ${course_avg1}%</span>
		</div>
		<em>${course1}명</em>
	</div>

	<h3 class="tit">5km</h3>
	<div class="event01 course02">
		<div class="bar_bg">
			<span class="pink_bar" style="width:${course_avg2}%"> ${course_avg2}%</span>
		</div>
		<em>${course2}명</em>
	</div>

	<h3 class="tit">10km</h3>
	<div class="event01 course03">
		<div class="bar_bg">
			<span class="pink_bar" style="width:${course_avg3}%"> ${course_avg3}%</span>
		</div>
		<em>${course3}명</em>
	</div>

	<h3 class="tit">하프</h3>
	<div class="event01 course04">
		<div class="bar_bg">
			<span class="pink_bar" style="width:${course_avg4}%"> ${course_avg4}%</span>
		</div>
		<em>${course4}명</em>
	</div>
	
</div>