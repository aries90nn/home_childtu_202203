<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ page import = "java.util.Calendar" %>
<%@ page import = "kr.co.nninc.ncms.common.Func" %>

<%!
public String getWeatherIcon(String wfKor, String wfEn ) throws Exception{
	String wIcon = wfEn.replaceAll(" ","_").replaceAll("/","");
	return "<img src=\"/img/common/t_weather_"+wIcon+".gif\" alt=\""+wfKor+"\" />";
}
public String getDayWeekEng(Calendar cal ) throws Exception{
	String strWeek_eng = "";
	switch( cal.get(Calendar.DAY_OF_WEEK) ) {
		case 1 :	strWeek_eng = "Sun"; break;
		case 2 :	strWeek_eng = "Mon"; break;
		case 3 :	strWeek_eng = "Tue"; break;
		case 4 :	strWeek_eng = "Wed"; break;
		case 5 :	strWeek_eng = "Thu"; break;
		case 6 :	strWeek_eng = "Fri"; break;
		case 7 :	strWeek_eng = "Sat"; break;
	}
	return strWeek_eng;
}
%>
<%
boolean flag_xml = false;	// xml 읽어올지 여부

String ck_weather_tm =  Func.getCookie(request, "ck_weather_tm");
String ck_weather_str =  Func.getCookie(request, "ck_weather_str");

if (!"".equals(ck_weather_str)){
	String[] arr_str = ck_weather_str.split("##");
	if (arr_str.length>2){
		String[] arr_day1 = arr_str[0].split("@");
		String[] arr_day2 = arr_str[1].split("@");
		String[] arr_day3 = arr_str[2].split("@");
		
		arr_day1[1] = arr_day1[1].replaceAll(" ","_").replaceAll("/","");
		
		String icon = "icon07";
%>
<!-- 날씨 -->
<div class="tweather" id="jsTopWeather">
	<%
		if("Partly_Cloudy".equals(arr_day1[1])){
			icon = "icon01";
		}else if("Mostly_Cloudy".equals(arr_day1[1])){
			icon = "icon02";
		}else if("Cloudy".equals(arr_day1[1])){
			icon = "icon03";
		}else if("Rain".equals(arr_day1[1])){
			icon = "icon04";
		}else if("Snow".equals(arr_day1[1])){
			icon = "icon05";
		}else if("SnowRain".equals(arr_day1[1])){
			icon = "icon06";
		}
	%>
	<div class="box">
		<p class="weater_icon <%=icon%>"></p>
		<p class="cate">백두대간</p>
		<p class="number"><%=arr_day1[3]%></p>
	</div>
</div>
<!--날씨-->
<%
	}
}%>