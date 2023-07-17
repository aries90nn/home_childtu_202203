<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<link href="/nanum/site/ebook/skin4/css/common.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/nanum/site/ebook/skin4/js/liquid.js"></script>
<script type="text/javascript" src="/nanum/site/ebook/skin4/js/swfobject.js"></script>
<script type="text/javascript" src="/nanum/site/ebook/skin4/js/ebook.js"></script>


<script type="text/javascript">

	flippingBook.pages = [
		${flippingBook_pages}
	];



	flippingBook.contents = [
		${flippingBook_contents}
	];



	flippingBook.settings.bookWidth = ${ebook.eb_width2*2};
	flippingBook.settings.bookHeight = ${ebook.eb_height2};
	flippingBook.settings.pageBackgroundColor = 0xffffff;	//페이지배경색상
	flippingBook.settings.backgroundColor = 0xE6F5E7;		//배경색상
	flippingBook.settings.zoomUIColor = 0xC7A097;			//스크롤색상
	flippingBook.settings.useCustomCursors = true;
	flippingBook.settings.dropShadowEnabled = false,
	flippingBook.settings.zoomImageWidth = ${ebook.eb_width};
	flippingBook.settings.zoomImageHeight = ${ebook.eb_height};
	flippingBook.settings.downloadURL = "";
	flippingBook.settings.zoomPath = "/data/ebook/${ebook.eb_pk}/";
	flippingBook.settings.backgroundImage = "/nanum/site/ebook/skin4/img/bg.gif";
	flippingBook.settings.flipSound = "/nanum/site/ebook/skin4/sounds/01.mp3";
	flippingBook.settings.staticShadowsDepth = "${ebook.eb_viewtype}";


	flippingBook.create();

	function ebook_size() {
		
		is_size = document.getElementById('frm_sch').is_size.value;

		if(is_size == "max"){//1024
			min();
		}else if(is_size == "min"){
			max();
		}
		
	}


	function max() {
		document.getElementById('frm_sch').is_size.value = "max";
		self.moveTo(0,0);
		self.resizeTo(screen.availWidth,screen.availHeight);
	}

	function min() {
		

		document.getElementById('frm_sch').is_size.value = "min";

		self.moveTo(0,0);
		self.resizeTo(1024,768);
	}

	function go_ebook_url(eb_pk){
		document.location.href="/ebook/index?eb_pk="+eb_pk;
	}

	function ebook_go1(url){
	location.href = url;
}
</script>



</head>
<body>
<c:set var="eb_size" value="max" />
<form id="frm_sch" action="/ebook/index.do?eb_pk=${ebook.eb_pk}"  method="post">
<div>
	<input type="hidden" name="is_size" value="${is_size}" />
</div>

	<div id="fbFooter">

		<div id="logo">
			<c:if test="${ebook.eb_logoimg ne ''}">
			<img src="/data/ebook/logo/${ebook.eb_logoimg}" alt="" />
			</c:if>
		</div>

		<div id="content_move">
			<select onchange = "go_ebook_url(this.value);">
				<c:forEach items="${ebookList}" var="ebookList" varStatus="no">
				<option value="${ebookList.eb_pk}" ${ebookList.eb_pk == ebook.eb_pk ? 'selected="selected"' : '' }>${ebookList.eb_subject}</option>
				</c:forEach>
			
			</select>
		</div>
		

		<div id="fbMenu">
			<img src="/nanum/site/ebook/skin4/img/btnPrevious.png" width="31" height="33" border="0" id="fbBackButton" alt="이전페이지" /> <img src="/nanum/site/ebook/skin4/img/btnNext.png" width="31" height="33" border="0" id="fbForwardButton" alt="다음페이지" />
			<img src="/nanum/site/ebook/skin4/img/btnDiv.gif" width="6" height="32" border="0" class="div" alt="" />
			<img src="/nanum/site/ebook/skin4/img/btnZoom.png" width="31" height="33" border="0" id="fbZoomButton" alt="확대/축소" /> <img src="/nanum/site/ebook/skin4/img/btnPrint.png" width="31" height="33" border="0" id="fbPrintButton" alt="인쇄" /> <img src="/nanum/site/ebook/skin4/img/btnHelp.png" width="31" height="33" border="0" id="fbHelpButton" alt="이용방법" onclick="window.open('/nanum/site/ebook/skin4/help.html','help','width=392,height=593');" style="cursor:pointer;" />

			<img src="/nanum/site/ebook/skin4/img/btnDiv.gif" width="6" height="32" border="0" class="div" alt="" />
			<img src="/nanum/site/ebook/skin4/img/btnMax.gif" width="21" height="33" border="0" alt="최대화" onclick="ebook_size();" style="cursor:pointer;" /><img src="/nanum/site/ebook/skin4/img/btnClose.gif" width="21" height="33" border="0" alt="닫기" onclick="window.close();" style="cursor:pointer;" />
		</div>

		<div id="fbContents">
			<ul>
				<li style="padding-top:3px;"><span class="fbPaginationMinor">페이지</span></li>
				<li style="padding-top:3px;"><span id="fbCurrentPages">1</span></li>
				<li style="padding-top:3px;"><span id="fbTotalPages" class="fbPaginationMinor2"></span></li>
				<li>
					<select id="fbContentsMenu" name="fbContentsMenu" title="페이지이동">
						<option></option>
					</select>
				</li>
			</ul>
		</div>

		<div id="fbContents2">
			<ul>
				<li><input type="text" size="10" title="검색어를 입력하세요" id="v_keyword" name="v_keyword"  value="${v_keyword}" style="height:14px;font-size:11px;"></li>
				<li style="padding-left:3px;"><input type="image" src="/nanum/site/ebook/skin4/img/btnSearch.gif" alt="검색" /></li>
				<li style="padding-left:4px;"><a href="javascript:;" onclick="ebook_go1('/content/ebook/index.do?eb_pk=${ebook.eb_pk}');"><img src="/nanum/site/ebook/skin4/img/btnTotalview.gif" alt="전체보기" /></a></li>
				<li style="padding-left:5px;"><img src="/nanum/site/ebook/skin4/img/btnDiv2.gif" width="7" height="22" border="0" class="div" alt="" /></li>
			</ul>
		</div>

	</div>

	<div id="fbContainer">
		<div id="altmsg"><a class="altlink" href="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash">Download Adobe Flash Player.</a></div>
	</div>
	
	<c:if test="${eb_size eq 'max'}">
	<script LANGUAGE="JavaScript">
		max();
	</script>
	</c:if>
	
</form>
</body>
</html>
