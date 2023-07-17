<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
#Search_Form {overflow:auto; height:150px; border:1px solid #cdcdcd; margin-top:3px; margin-right:10px; padding:6px;}
#Addr_search2 span.text1 {font-size:0.9em; color:#ad6767;}
</style>

<div id="Addr_search1">

	<input type="button" value="우편번호찾기" class="ct_bt01" onclick="zipCodeSearch();" />
	<input type="text" size="7" title="우편번호 다섯자리" id="b_zip1" name="b_zip1" class="input_box board_input"  value="${param.b_zip1}" maxlength="5" />	
	<span class="text1">* 우편번호</span><br /> 

	<div class="pt3">
	<input type="text" size="40" title="주소" id="b_addr1" name="b_addr1" class="input_box board_input" value="${param.b_addr1}" />
	<span class="text1">* 시·도 + 시·군·구 + 읍·면 + 도로명</span><br /> 
	<input type="text" size="40" title="상세주소" id="b_addr2" name="b_addr2" class="input_box board_input" value="${param.b_addr2}" maxlength="100" />
	<span class="text1">* 건물번호 + 동·층·호 + (법정동, 공동주택명)</span><br />
	</div>

</div>

<!-- 우편번호 찾기 부분 -->
<div id="Addr_search2" class="disnone" >
	<div class="post_write">	

		<input type="text" id="sh_keyword" name="sh_keyword" size="20" class="input_box board_input" onkeydown="if(event.keyCode==13) {wdSearch_Addr();return false;}" value="" title="도로명주소 또는 지번검색" />
		<input type="button" value=" 검색 " class="ct_bt01" onclick="wdSearch_Addr()"  />
		<input type="button" value=" 취소 " class="ct_bt01" onclick="zipCodeSearchClose();"  />
		<span class="text1">검색 예) 잠원로4길 35 또는 잠원동 61-12</span>
	</div>
	<div class="post_search_wrap" style="padding-top:10px;">
		<strong>↓ 검색 결과 </strong> - <span id="jsAddrSearchResult"></span>

		<!-- 우편번호 나열 -->
		<div id="Search_Form" class="post_search2"></div>

	</div>
</div>
<script type="text/javascript">
//<![CDATA[

var currentPage = "1";
var countPerPage = "100";

$(function(){
	$("#jusopage").on("change",function(){
	//$("#jusopage").live("change",function(){
		currentPage = $(this).val();
		getAddr();
	});

});

function makeList(xmlStr){
	var htmlStr = "";
	htmlStr += "<table style='width:90%;'>";
	$(xmlStr).find("juso").each(function(){
		htmlStr += "<tr>";
		htmlStr += "<td style='text-align:left; font-size:11px;border:none; border-bottom:1px solid #e4e4e4; height:10px; padding:4px 0 3px 8px;'><a href=\"javascript:wdZipcode('"+$(this).find('zipNo').text()+"','"+$(this).find('roadAddrPart1').text()+"', '"+$(this).find('roadAddrPart2').text()+"')\">";
		htmlStr += "도로명주소 : "+$(this).find('roadAddr').text();
		htmlStr += "<br/><span style='color:#939393;'>(지번주소 : "+$(this).find('jibunAddr').text()+")</span>";
		htmlStr += "</a></td>";


//		htmlStr += "<tr>";
//		htmlStr += "<td style='font-size:11px;border:none; height:10px; padding:3px 30px 3px 3px;'><a href=\"javascript:wdZipcode('"+$(this).find('zipNo').text()+"','"+$(this).find('roadAddr').text()+"', '')\">";
//		htmlStr += $(this).find('roadAddr').text()+"</a></td>";
//		htmlStr += "<td style='font-size:11px;border:none; height:10px; padding:3px 0 3px 0; color:#8d8d8d; '>지번주소)&nbsp;"+$(this).find('jibunAddr').text()+"</td>";
		
//		htmlStr += "<td>"+$(this).find('roadAddr').text() +"</td>";
//		htmlStr += "<td>"+$(this).find('roadAddrPart1').text() +"</td>";
//		htmlStr += "<td>"+$(this).find('roadAddrPart2').text() +"</td>";
//		htmlStr += "<td>"+$(this).find('jibunAddr').text() +"</td>";
//		htmlStr += "<td>"+$(this).find('engAddr').text() +"</td>";
//		htmlStr += "<td>"+$(this).find('zipNo').text() +"</td>";
//		htmlStr += "<td>"+$(this).find('admCd').text() +"</td>";
//		htmlStr += "<td>"+$(this).find('rnMgtSn').text() +"</td>";
//		htmlStr += "<td>"+$(this).find('bdMgtSn').text() +"</td>";
		
		htmlStr += "</tr>";
	});
	htmlStr += "</table>";
	$("#Search_Form").html(htmlStr);
}


function makeResult(xmlStr){
	var htmlStr = "";

	var totalCount = $(xmlStr).find('totalCount').text();
	var currentPage = $(xmlStr).find('currentPage').text();
	var countPerPage = $(xmlStr).find('countPerPage').text();

	var totalPage = Math.ceil( totalCount / countPerPage);

	
	htmlStr += "총 "+totalCount+"건&nbsp;&nbsp;";
	htmlStr += "<select id='jusopage'></select>";
	htmlStr += "/"+totalPage+" 페이지";

	$("#jsAddrSearchResult").html(htmlStr);

	for (i=1;i<=totalPage ;i++ ){
		$("#jusopage").append("<option value='"+i+"'>"+i+" 페이지</option>");
	}
	$("#jusopage").val(currentPage);
}



function zipCodeSearch(){
	document.getElementById('Addr_search1').style.display='none';
	document.getElementById('Addr_search2').style.display='block';
	document.getElementById('sh_keyword').focus();
}
function zipCodeSearchClose(){
	document.getElementById('Addr_search1').style.display='block';
	document.getElementById('Addr_search2').style.display='none';
}
function wdSearch_Addr(){
	
	if(!document.getElementById("sh_keyword").value) {
		alert("검색어를 적어주세요");
		document.getElementById("sh_keyword").focus();
	}else{

		currentPage = 1;
		getAddr();		
	}
}


function getAddr(){

	$("#Search_Form").html("");

	$.ajax({
		url :"http://zipcode.nninc.co.kr/addr_result.php"
		,type:"post"
		,data:"currentPage="+currentPage+"&countPerPage="+countPerPage+"&keyword="+$("#sh_keyword").val()
		,dataType:"jsonp"
		,crossDomain:true
		,success:function(xmlStr){
			
			try{
				var xmlData = new ActiveXObject("Microsoft.XMLDOM");
				xmlData.loadXML(xmlStr.returnXml)
			}catch(e){
				var xmlData = xmlStr.returnXml;			
			}
			/*
			if(navigator.appName.indexOf("Microsoft") > -1){  //ie경우에만
				var xmlData = new ActiveXObject("Microsoft.XMLDOM");
				xmlData.loadXML(xmlStr.returnXml)
			}else{
				var xmlData = xmlStr.returnXml;
			}
			*/

			
			var errCode = $(xmlData).find("errorCode").text();
			var errDesc = $(xmlData).find("errorMessage").text();
			if(errCode != "0"){
				alert(errCode+"="+errDesc);
			}else{
				if(xmlStr != null){									

					makeResult(xmlData);
					makeList(xmlData);
				}
			}
		}
		,error: function(xhr,status, error){
			alert("에러발생");
		}
	});

}


function wdZipcode(post,addr, addr2) {

	with(document.getElementById("frm")){
		b_zip1.value=post;
		b_addr1.value=addr;
		b_addr2.value=addr2;
	}


	document.getElementById("Addr_search1").style.display = "block";
	document.getElementById("Addr_search2").style.display = "none";


}


//]]>
</script>
