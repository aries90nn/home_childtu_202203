<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="${empty ncms_decorator ? 'ncms_content' : ncms_decorator }" />
</head>
<link rel="stylesheet" type="text/css" href="/nanum/site/common/css/helloCalendar1.css" />
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_ip.js"></script>
<script type="text/javascript" src="/nanum/site/common/js/helloCalendar.js"></script>
		
<script type="text/javascript">
//<![CDATA[
$(function(){

	$("input[id^='mi_sdate']").each(function(){
		$(this).attr("readonly", true);
		$(this).helloCalendar({'selectBox':true});
	});
	$("input[id^='mi_edate']").each(function(){
		$(this).attr("readonly", true);
		$(this).helloCalendar({'selectBox':true});
	});

});

function dateReset(objid){
	$("#"+objid).val("");
}

//]]>
</script>
	<h1 class="tit">관리자페이지 접근관리</h1>

	<!-- 내용들어가는곳 -->
	<div id="contents_area">
		
	<h2 class="tit">관리자페이지 접근제한 설정</h2>
	<span class="point_default fs11">* 사이트관리에서 생성된 사이트는 해당 관리페이지에서 관리됩니다.</span>
	<fieldset>
		<legend>기본정보 서식 작성/수정</legend>
		<table class="bbs_common bbs_default" summary="사이트 기본정보를 위한 입력/수정 양식입니다.">
		<caption>기본정보 서식</caption>
		<colgroup>
		<col width="150px" />
		<col />
		</colgroup>
		<tr>
			<th scope="row">IP 접근제한 사용</th>
			<td class="left">			
		
				<p class="app_view_onoff jsOnOff" data-field="sc_manager_ip">
					<a href="#tmp" class="disnone" data-update="N" data-now="Y"><span class="on">사용</span></a>
					<a href="#tmp" class="disnone" data-update="Y" data-now="N"><span class="off">중지</span></a>
				</p>
				
				
				${IpCount ==0 ? '접근 허용된 아이피가 없습니다.' : '' }
				
			</td>
		</tr>	
		<tr>
			<th scope="row">모바일기기에서 접속</th>
			<td class="left">
				<p class="app_view_onoff jsOnOff" data-field="sc_manager_mobile">
					<a href="#tmp" class="disnone" data-update="N" data-now="Y"><span class="on">허용</span></a>
					<a href="#tmp" class="disnone" data-update="Y" data-now="N"><span class="off">금지</span></a>
				</p>
			</td>
		</tr>
		</table>
	</fieldset>	

	<p style="clear:both; height:40px;"/>


	<h2 class="tit">관리자페이지 접근IP 관리<span class="ti">IP 접근제한 사용중일때 적용됩니다.</span></h2>
	
	<span class="point_default fs11">* IP와 메모는 100자 내외로 입력해주세요.</span>

	<form id="frm" method="post" action="writeOk.do" onsubmit="return w_chk();">
		<!-- 검색 -->
		<div class="top_search_area mt10">
			<ul>
				<li class="tit"><label for="mi_ip_i"><h3 class="tit">IP 등록 :</h3></label></li>
				<li class="search"><label for="mi_ip_i">IP를 입력하세요</label><input title="IP 입력" name="mi_ip" id="mi_ip_i" type="text" value="" class="search_input autoInput" maxlength="20" /></li>
				<li class="search"><label for="mi_memo_i">메모를 입력하세요</label><input title="IP 입력" name="mi_memo" id="mi_memo_i" type="text" value="" class="search_input autoInput" maxlength="100" /></li>
				<li class="search">
					<input type="text" title="허용 시작일" id="mi_sdate" name="mi_sdate" class="ta_input" style="width:100px;" value="" maxlength="10" /><a href="#reset" onclick="dateReset('mi_sdate')" title="초기화" style="font-weight:bold;">X</a> ~
					<input type="text" title="허용 종료일" id="mi_edate" name="mi_edate" class="ta_input" style="width:100px;" value="" maxlength="10" /><a href="#reset" onclick="dateReset('mi_edate')" title="초기화" style="font-weight:bold;">X</a>
				</li>
				<li class="sel">
					<select id="ct_chk_i" name="mi_chk" title="IP 사용여부 선택" class="t_search" style="width:80px;">
						<option value="Y" selected="selected">사용</option>
						<option value="N" >중지</option>
					</select>	
				</li>
				<li class="btn"><input type="submit" value="등록" class="btn_bl_default" /></li>
			</ul>
		</div>
		<!-- //검색 -->
	</form>

			
	<form id= "frm_list" action="" method='post'>
	<div>
		<input type="hidden" id="status" name="status" />
		<input type="hidden" id="mi_chk" name="mi_chk" />
		<input type="hidden" id="mi_num" name="mi_num" />
		<input type="hidden" id="chk_all" name="chk_all" />
	</div>
			
		<fieldset>
			<legend>IP관리 작성/수정</legend>
			<table class="bbs_common bbs_default" summary="사이트에 사용하는 IP를 관리합니다.">
			<caption>IP관리 서식</caption>
			<colgroup>
				<col width="40" />
				<col width="45" />
				<col width="200"/>
				<col />
				<col width="300"/>
				<col width="65"/>
				<col width="50" />
				<col width="50" />
			</colgroup>

			<thead>
			<tr>
				<th scope="col">선택</th>
				<th scope="col">번호</th>
				<th scope="col">허용IP</th>
				<th scope="col">메모</th>
				<th scope="col">허용기간</th>
				<th scope="col">사용여부</th>
				<th scope="col">수정</th>
				<th scope="col">삭제</th>
			</tr>
			</thead>

			<tbody>
			<c:forEach items="${ipList}" var="ip" varStatus="no">
			<tr>
				<td class="center"><input type="checkbox" name="chk" value="${ip.mi_num}" title="해당 IP 선택" /></td>
				<td class="center">${no.count}</td>
				<td>
					<input type="text" title="허용IP 입력" id="mi_ip${no.count}" name="mi_ip${no.count}" class="ta_input w8" value="${ip.mi_ip}" maxlength="100" />
				</td>
				<td>
					<input type="text" title="메모 입력" id="mi_memo${no.count}" name="mi_memo${no.count}" class="ta_input w8" value="${ip.mi_memo}" maxlength="100" />
				</td>
				<td class="center">
					<input type="text" title="허용 시작일" id="mi_sdate${no.count}" name="mi_sdate${no.count}" class="ta_input" style="width:100px;" value="${ip.mi_sdate}" maxlength="10" /><a href="#reset" onclick="dateReset('mi_sdate${no.count}')" title="초기화" style="font-weight:bold;">X</a> ~
					<input type="text" title="허용 종료일" id="mi_edate${no.count}" name="mi_edate${no.count}" class="ta_input" style="width:100px;" value="${ip.mi_edate}" maxlength="10" /><a href="#reset" onclick="dateReset('mi_edate${no.count}')" title="초기화" style="font-weight:bold;">X</a>
				</td>
				<td class="center">${ip.mi_chk == "Y" ? '<strong class="point">사용</strong>' : '중지' }</td>
				<td class="center"><a href="#modify" onclick="frm_modify${no.count}(${ip.mi_num});"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif" /></a>
				<script type="text/javascript">
					function frm_modify${no.count}(mi_num){
						var mi_num;
	
						if (CheckSpaces(document.getElementById('frm_list').mi_ip${no.count}, '허용IP')) { return false; }
						else if (CheckSpaces(document.getElementById('frm_list').mi_sdate${no.count}, '허용기간 시작일')) { return false; }
						else {
	
							loading_st(1);
							document.getElementById('frm_m').mi_ip.value	= document.getElementById('frm_list').mi_ip${no.count}.value;
							document.getElementById('frm_m').mi_memo.value	= document.getElementById('frm_list').mi_memo${no.count}.value;
							document.getElementById('frm_m').mi_num.value	= mi_num;
							document.getElementById('frm_m').mi_sdate.value	= document.getElementById('frm_list').mi_sdate${no.count}.value;
							document.getElementById('frm_m').mi_edate.value	= document.getElementById('frm_list').mi_edate${no.count}.value;
							document.getElementById('frm_m').action			= "writeOk.do";
							document.getElementById('frm_m').submit();
						
						}
					}
				</script>
			</td>
			<td class="center"><a href="#delete" onclick="return d_chk('deleteOk.do?mi_num=${ip.mi_num}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
			</tr>
		</c:forEach>
		
			</tbody>
			</table>
		</fieldset>

		<!-- 하단버튼 -->
		<div id="contoll_area">
			<ul>
				<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 IP삭제</a></p></li>
				<li class="btn_ri">
					<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif">&nbsp;선택한 IP를</p>
					<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 IP 사용여부 선택" class="t_search" style="width:70px;">
						<option value="Y" selected="selected">사용</option>
						<option value="N" >중지</option>
					</select></p>
					<p>(으)로</p>
					<p><a href="javascript:tot_levelchage();" class="btn_gr">변경</a></p>
				</li>
			</ul>						
		</div>
		<!-- //하단버튼 -->


	</form>
		
		</div>
		<!-- 내용들어가는곳 -->

<form id= "frm_m" method='post' action="">
<div>
	<input type="hidden" name="mi_ip" />
	<input type="hidden" name="mi_memo" />
	<input type="hidden" name="mi_num" />
	<input type="hidden" name="mi_sdate" />
	<input type="hidden" name="mi_edate" />
</div>
</form>

<c:set var="site_dir" value="" />
<c:if test="${!empty BUILDER_DIR }">
	<c:set var="site_dir" value="/${BUILDER_DIR }" />
</c:if>
<script type="text/javascript">

$(document).ready(function(){ 
	getConfig("");
//sc_manager_ip
	// 설정변경
	$(".jsOnOff").each(function(){
		var obj = $(this);
		var fname = obj.data("field");
		
		obj.children("a").click(function(){
			$.post("${site_dir}/ncms/info/site_config_ok.do", {
					proc_type:"update",
					fname:fname,
					val:$(this).data("update")
				}, function(data,status){
					if(data.flag_proc){
						data = data.field_val;
						data = data.replace(/\n/g,'');//행바꿈제거
						data = data.replace(/\r/g, '');//엔터제거
						
						var tobj = $(".jsOnOff[data-field='"+fname+"']");
						tobj.children("a").hide();
						tobj.children("a").css("display", "none");
						tobj.children("a[data-now='"+data+"']").show();
						alert("설정되었습니다.");
					}else{
						if(data.msg != ""){
							alert(data.msg);
						}else{
							alert("처리할 수 없습니다.");
						}
					}
			});
		});
	});

});

// 현재설정 가져오기
function getConfig(fn){
	var tobj = $(".jsOnOff");
	if (fn!=""){
		tobj = $(".jsOnOff[data-field='"+fn+"']");
	}

	tobj.each(function(){
		var obj = $(this);
		var fname = obj.data("field");

		obj.children("a").hide();

		$.post("${site_dir}/ncms/info/site_config_ok.do?proc_type=check&fname="+fname,function(data,status){
			//alert("Data: " + data + "\nStatus: " + status);
			result_val = data.result_value;
			obj.children("a[data-now='"+result_val+"']").show();
		});

	});
}

</script>