<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String[] cmsFolderIcon = new String[7];
cmsFolderIcon[0] = "/nanum/ncms/img/common/side_icon04.png";	// 초기
cmsFolderIcon[1] = "/nanum/ncms/img/common/side_icon01.png";	// 트리의 마지막 (하위없음)
cmsFolderIcon[2] = "/nanum/ncms/img/common/side_icon02.png";	//(사용 중지)닫긴 폴더
cmsFolderIcon[3] = "/nanum/ncms/img/common/side_icon03.png";	//(사용 중지)열린 폴더
cmsFolderIcon[4] = "/nanum/ncms/img/common/side_icon05.png";//(사용 중)닫긴 폴더
cmsFolderIcon[5] = "/nanum/ncms/img/common/side_icon06.png";//(사용 중)열린 폴더
cmsFolderIcon[6] = "/nanum/ncms/img/common/side_icon07.png";
%>

<script type="text/javascript" src="/nanum/ncms/common/js/jquery.yakutree.js"></script>
<script type="text/javascript" >

var cmsFolderIcon = new Array();
cmsFolderIcon[1] = "<%=cmsFolderIcon[1]%>";
cmsFolderIcon[2] = "<%=cmsFolderIcon[2]%>";
cmsFolderIcon[3] = "<%=cmsFolderIcon[3]%>";
cmsFolderIcon[4] = "<%=cmsFolderIcon[4]%>";
cmsFolderIcon[5] = "<%=cmsFolderIcon[5]%>";
cmsFolderIcon[6] = "<%=cmsFolderIcon[6]%>";

var nowCtidx = "${param.ct_idx}";
var nowMenuIdx = "";



$(function(){
	//트리메뉴 적용
    $("#ul_menu_tree").yakutree({
        unique: false,
        animate: false,
        duration: 100 ,
        aTagClick : true,
        dragndrop : ${sessionScope.ss_m_id eq 'nninc' ? 'true' : 'false'},
        btnCallback : function ( type, $source, $target) {
            switch( type ) {
                case 'moveUp':
                    //this.moveAnimation(  $source, $target, 'revert' );
                    break;
            }
        }
    });
	

  	//메뉴별 폴더아이콘 설정
    $("#sidemenu a.category").each(function(){
    	var obj = $(this).parent("li").children("ul.sub");
    	
    	if (obj.length>0){
    		if($(this).parent("li").data("chk") == "Y"){
    			$(this).children("img.group").attr("src",cmsFolderIcon[4]);
    		}else{
    			$(this).children("img.group").attr("src",cmsFolderIcon[2]);
    		}
    	}else{
    		if($(this).parent("li").data("chk") == "Y"){
    			$(this).children("img.group").attr("src",cmsFolderIcon[6]);
    		}else{
    			$(this).children("img.group").attr("src",cmsFolderIcon[1]);
    		}
    	}
    });
	

 	// 초기 펼쳐질 메뉴
	nmenu = $.cookie('cmsOpenmenu');
	if (nmenu==null)	nmenu = "";
	if (nmenu!=""){
		arr_nmenu = nmenu.split(",");
		for (i=0; i<arr_nmenu.length; i++){
			arr_nmenu[i] = arr_nmenu[i].replace("[","").replace("]","");
			if( arr_nmenu[i]!="" ){
				$("#sidemenu li[data-idx='"+arr_nmenu[i]+"']>a.category:eq(0)").click();
				/*
				obj = $("#sidemenu li[data-idx='"+arr_nmenu[i]+"']").children("ul.sub");
				if (obj.length>0)	obj.show();
				*/
			}
		}
	}

	// 현재 선택 메뉴에 on
	$("#sidemenu li[data-idx='"+nowCtidx+"']").addClass("on");
	$("#sidemenu li[data-idx='"+nowCtidx+"']").children("a.category").addClass("on_default");

	// --- 이벤트설정 ---- //
    $("img.side_menu_list").mouseover(function(){
		$(this).attr("src","/nanum/ncms/img/common/side_menu_list_over.gif");
	}).mouseout(function(){
		$(this).attr("src","/nanum/ncms/img/common/side_menu_list.gif");
	});
	
 	// 메뉴설정(이미지) 클릭
	$("#sidemenu a.setup").click(function(){
		fn_menuInfoView($(this));
	});
	$("#sidemenu a.category").each(function(){
		if(!$(this).hasClass("no_control")){
			$(this).rightClick( function(e) {
				fn_menuInfoView($(this));
			});
		}
	});
	
	// 메뉴명(텍스트) 클릭
	$("#sidemenu a.category").click(function(){
		fn_menuInfoClose();
		
		var obj = $(this).parent("li").children("ul.sub");
		
		var sltidx = $(this).parent("li").data("idx");
		var nmenu = $.cookie('cmsOpenmenu');
		if (nmenu==null)	nmenu = "";
		
		if (obj.length>0){	// 하위 메뉴있으면
			// 확장된 메뉴 쿠키에 담기
			$.cookie('cmsOpenmenu', nmenu+',['+sltidx+']');
		}else{
			// 축소된 메뉴 쿠키에 빼기
			nmenu = nmenu.replace(",["+sltidx+"]", "");
			$.cookie('cmsOpenmenu', nmenu);
		}
	});

});









// 메뉴설정 보이기
function fn_menuInfoView(thisobj){

	var obj = thisobj.parent("li");
	nowMenuIdx = obj.data("idx");

	var objmenu = $("#menuInfo").clone();
	if (obj.data("nocon")=="Y"){
		objmenu.find("li.edit>a").addClass("nocon").removeAttr("href");
	}
	if (obj.data("depth")=="1"){
		objmenu.find("li.img").css( "display", "block" );
		objmenu.find("li.menu>a").html("1차 메뉴관리");
	}
	if (obj.data("depth")=="2"){
		objmenu.find("li.menu>a").html("2차 메뉴관리");
	}
	if (obj.data("depth")=="3"){
		objmenu.find("li.menu>a").html("3차 메뉴관리");
	}
	if (obj.data("depth")=="4"){
		objmenu.find("li.child").css( "display", "none" );
		objmenu.find("li.menu>a").html("4차 메뉴관리");
	}
	fn_menuInfoClose();	// 다른거 다 지우고,
	obj.append( objmenu.html() );	// 메뉴 붙임
}

// 메뉴설정 가리기
function fn_menuInfoClose(){
	$("#sidemenu li").find(".lsetup_area").remove();
}

function cms_menu1(){
	location.href = "write.do?ct_idx="+nowMenuIdx+"&ok_url=";
}
function cms_menu2(){
	location.href="contents.do?ct_idx="+nowMenuIdx;
}
function cms_menu3(){
	window.open($("li[data-idx='"+nowMenuIdx+"']").attr("data-url"));
}
function cms_menu4(){
	if (confirm('정말 삭제하시겠습니까?')) {
		if (confirm('포함된 하위메뉴도 일괄 삭제 됩니다.\n삭제된 메뉴는 복구 할 수 없습니다.\n\n정말 삭제하시겠습니까?')) {
			location.href = "deleteOk.do?ct_idx="+nowMenuIdx+"&ok_url=";
		}
	}
}
//이미지관리
function cms_menu5(){
	location.href="img.do?ct_idx="+nowMenuIdx
}
//css관리
function cms_menu6(){
	location.href="css.do?ct_idx="+nowMenuIdx
}


</script>

<!-- 메뉴설정리스트 -->
<div id="menuInfo" class="disnone">
<div class="lsetup_area">
	<div class="area"><a href="javascript:fn_menuInfoClose();" class="btn"><img src="/nanum/ncms/img/common/side_setup_arrow.gif" alt="메뉴설정리스트 닫기" /></a></div>
	<ul>
		<li class="child"><a href="javascript: cms_menu1();">하위메뉴관리</a></li>
		<li class="menu"><a href="javascript: cms_menu2();">메뉴관리</a></li>
		<li class="edit"><a href="javascript: cms_menu3();">메뉴보기</a></li>
		<li class="img" style="display:none;"><a href="javascript: cms_menu5();" class="btn2">이미지관리</a></li>
		<li class="img" style="display:none;"><a href="javascript: cms_menu6();" class="btn2">CSS관리</a></li>
		<li><a href="javascript: cms_menu4();">메뉴삭제</a></li>
	</ul>
</div>
</div>
<!-- //메뉴설정리스트 -->

<!-- 메뉴동기화 -->
			<ul>
				<li id="menu1" data-chk="Y"><a class="category no_control" href="./syncOk.do?prepage=${nowPageEncode }" onclick="if(!confirm('데이타베이스와 동기화합니다.\n\n작업시간이 몇 분이상 걸릴 수 있습니다.\n\n동기화하시겠습니까?')){return false;}"><img src="/nanum/ncms/img/common/side_icon07.png" class="png24 group" alt="그룹 아이콘" />메뉴 동기화</a>
				</li>
			</ul>



<!-- 코딩부분 : 홈 없어지고 2depth부터 시작 -->
	<ul id="ul_menu_tree">
	<c:forEach items="${leftList}" var="menu1" varStatus="no">
	
		<!-- 안쓰는메뉴 & 탭메뉴 표시 -->
		<c:set var="colorStyle" value="${menu1.ct_chk eq 'N' ? 'style=color:#8a8d94;' : 'style=#e7eaef;'}" />
		<c:set var="tabImg" value="${menu1.ct_tab eq 'Y' ? '<img src=\"/nanum/ncms/img/tab_icon3.gif\">' : ''}" />
		<c:set var="folderImg" value="${menu1.ct_chk eq 'Y' ? '/nanum/ncms/img/common/side_icon05.png' : '/nanum/ncms/img/common/side_icon02.png'}" />
		<c:set var="chk" value="${menu1.ct_chk}" />
		<!-- //안쓰는메뉴 & 탭메뉴 표시 -->
		
		<li data-idx="${menu1.ct_idx}" data-nocon="${menu1.nocon}" data-depth="1" data-url="${menu1.ct_menu_url}" data-chk="${chk}"><a class="category" ${colorStyle}><img src="${folderImg}" class="group" />${menu1.ct_name}&nbsp;${tabImg}</a><a class="setup"><img src="/nanum/ncms/img/common/side_menu_list.gif" alt="메뉴설정" class="side_menu_list" /></a>
		
		<!-- 2차메뉴 -->
		${fn:length(menu1.menuList) > 0 ? '<ul class="sub depth2">' : ''}
		<c:forEach items="${menu1.menuList}" var="menu2" varStatus="no2"> 
		
			<!-- 안쓰는메뉴 & 탭메뉴 표시 -->
			<c:set var="colorStyle" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' ? 'style=color:#8a8d94;' : 'style=#e7eaef;'}" />
			<c:set var="tabImg" value="${menu2.ct_tab eq 'Y' ? '<img src=\"/nanum/ncms/img/tab_icon3.gif\">' : ''}" />
			<c:set var="folderImg" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' ? '/nanum/ncms/img/common/side_icon05.png' : '/nanum/ncms/img/common/side_icon02.png'}" />
			<c:set var="chk" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' ? 'N' : 'Y'}" />
			<!-- //안쓰는메뉴 & 탭메뉴 표시 -->
			
			<li data-idx="${menu2.ct_idx}" data-nocon="${menu2.nocon}" data-depth="2" data-url="${menu2.ct_menu_url}" data-chk="${chk}"><a class="category" ${colorStyle}><img src="${folderImg}" class="group" />${menu2.ct_name}&nbsp;&nbsp;${tabImg}</a><a class="setup"><img src="/nanum/ncms/img/common/side_menu_list.gif" alt="메뉴설정" class="side_menu_list" /></a>
			
			<!-- 3차메뉴 -->
			${fn:length(menu2.menuList) > 0 ? '<ul class="sub depth3">' : ''}
			<c:forEach items="${menu2.menuList}" var="menu3" varStatus="no3"> 
			
				<!-- 안쓰는메뉴 & 탭메뉴 표시 -->
				<c:set var="colorStyle" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' or menu3.ct_chk eq 'N' ? 'style=color:#8a8d94;' : 'style=#e7eaef;'}" />
				<c:set var="tabImg" value="${menu3.ct_tab eq 'Y' ? '<img src=\"/nanum/ncms/img/tab_icon3.gif\">' : ''}" />
				<c:set var="folderImg" value="${menu3.ct_tab eq 'Y' ? '/nanum/ncms/img/common/side_icon05.png' : '/nanum/ncms/img/common/side_icon02.png'}" />
				<c:set var="chk" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' or menu3.ct_chk eq 'N' ? 'N' : 'Y'}" />
				<!-- //안쓰는메뉴 & 탭메뉴 표시 -->
				
				<li data-idx="${menu3.ct_idx}" data-nocon="${menu3.nocon}" data-depth="3" data-url="${menu3.ct_menu_url}" data-chk="${chk}"><a class="category" ${colorStyle}><img src="${folderImg}" class="group" />${menu3.ct_name}&nbsp;${tabImg}</a><a class="setup"><img src="/nanum/ncms/img/common/side_menu_list.gif" alt="메뉴설정" class="side_menu_list" /></a>
					<!-- 4차메뉴 -->
					${fn:length(menu3.menuList) > 0 ? '<ul class="sub depth4">' : ''}
					<c:forEach items="${menu3.menuList}" var="menu4" varStatus="no4"> 
					
						<!-- 안쓰는메뉴 & 탭메뉴 표시 -->
						<c:set var="colorStyle" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' or menu3.ct_chk eq 'N' or menu4.ct_chk eq 'N' ? 'style=color:#8a8d94;' : 'style=#e7eaef;'}" />
						<c:set var="tabImg" value="${menu4.ct_tab eq 'Y' ? '<img src=\"/nanum/ncms/img/tab_icon3.gif\">' : ''}" />
						<c:set var="folderImg" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' or menu3.ct_chk eq 'N' or menu4.ct_chk eq 'N' ? '/nanum/ncms/img/common/side_icon05.png' : '/nanum/ncms/img/common/side_icon02.png'}" />
						<c:set var="chk" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' or menu3.ct_chk eq 'N' or menu4.ct_chk eq 'N' ? 'N' : 'Y'}" />
						<!-- //안쓰는메뉴 & 탭메뉴 표시 -->
						
						<li data-idx="${menu4.ct_idx}" data-nocon="${menu4.nocon}" data-depth="4" data-url="${menu4.ct_menu_url}" data-chk="${chk}"><a class="category" ${colorStyle}><img src="${folderImg}" class="group" />${menu4.ct_name}&nbsp;${tabImg}</a><a class="setup"><img src="/nanum/ncms/img/common/side_menu_list.gif" alt="메뉴설정" class="side_menu_list" /></a>
								
								<!-- 5차메뉴 -->
								${fn:length(menu4.menuList) > 0 ? '<ul class="sub depth5">' : ''}
								<c:forEach items="${menu4.menuList}" var="menu5" varStatus="no5">
								
									<!-- 안쓰는메뉴 & 탭메뉴 표시 -->
									<c:set var="colorStyle" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' or menu3.ct_chk eq 'N' or menu4.ct_chk eq 'N' or menu5.ct_chk eq 'N' ? 'style=color:#c97d7d;' : 'style=color:#fff;'}" />
									<c:set var="tabImg" value="${menu5.ct_tab eq 'Y' ? '<img src=\"/nanum/ncms/img/tab_icon3.gif\">' : ''}" />
									<c:set var="folderImg" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' or menu3.ct_chk eq 'N' or menu4.ct_chk eq 'N' or menu5.ct_chk eq 'N' ? '/nanum/ncms/img/common/side_icon05.png' : '/nanum/ncms/img/common/side_icon02.png'}" />
									<c:set var="chk" value="${menu1.ct_chk eq 'N' or menu2.ct_chk eq 'N' or menu3.ct_chk eq 'N' or menu4.ct_chk eq 'N' or menu5.ct_chk eq 'N' ? 'N' : 'Y'}" />
									<!-- //안쓰는메뉴 & 탭메뉴 표시 -->
									
									<li data-idx="${menu5.ct_idx}" data-nocon="${menu5.nocon}" data-depth="5" data-url="${menu5.ct_menu_url}" data-chk="${chk}"><a class="category" ${colorStyle}><img src="${folderImg}" class="group" />${menu5.ct_name}&nbsp;${tabImg}</a><a class="setup"><img src="/nanum/ncms/img/common/side_menu_list.gif" alt="메뉴설정" class="side_menu_list" /></a></li>
								</c:forEach>
								${fn:length(menu4.menuList) > 0 ? '</ul>' : ''}
								<!--// 5차메뉴 -->
						</li>
					</c:forEach>
					${fn:length(menu3.menuList) > 0 ? '</ul>' : ''}
				</li>
				<!--// 4차메뉴 -->
			</c:forEach>
			${fn:length(menu2.menuList) > 0 ? '</ul>' : ''}
			</li>
			<!--// 3차메뉴 -->
		</c:forEach>
		${fn:length(menu1.menuList) > 0 ? '</ul>' : ''}
		</li>
		<!--// 2차메뉴 -->
	</c:forEach>
	</ul>
	<!-- //코딩부분 -->
