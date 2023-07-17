<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="Stylesheet" type="text/css" href="/nanum/site/builder/skin/common/css/popup.css" />
<style>
div.popup {clear:both; position:relative; float:left; margin:0; padding:0;}
div.popup_right {float:right;}
div.popup_right ul li {float:left; font-size:0.9em; letter-spacing:-0.1em; padding-bottom:10px; }
div.popup_right ul li.button {float:left; font-size:1em; letter-spacing:-0.1em;}
</style>

<c:forEach items="${popupList}" var="popup" varStatus="no">

	<c:set var="w_width" value="${popup.w_width eq '0' ? '100':popup.w_width}" />
	<c:set var="w_height" value="${popup.w_height eq '0' ? '100':popup.w_height}" />
	<c:set var="ck_val" value="${popup.ck_val eq '' ? '1':popup.ck_val}" />
	<%//레이어팝업 %>
	<c:if test="${popup.w_chk eq 'L'}">
	<script type="text/javascript">
		function setCookie${popup.idx}(){
			var expire = new Date();
			expire.setDate(expire.getDate() + ${ck_val});
			document.cookie = "pop${popup.idx}=${ck_val}; expires=" + expire.toGMTString()+ "; path=/";
		}
		function closeWin${popup.idx}(){
			if (document.getElementById('next_close${popup.idx}').checked == true ){
				setCookie${popup.idx}();
			}
			window.parent.document.getElementById("layer_${popup.idx}").style.display = "none";
		}
	</script>
	<style type="text/css">
	/********************모바일********************/
	@media(max-width:736px){
	
	.mmimg_w{top:0 !important; left:0 !important;}
	.mmimg_w,
	.mmimg_in,
	.mmmimg,
	.mmmimg p,
	.mmmimg img{width:100% !important;height:auto !important;}
	
	}
	</style>
	<div id='layer_${popup.idx}' class="mmimg_w" style='position:absolute; left:${popup.w_left}px; top:${popup.w_top}px; z-index:999;background-color:#FFFFFF;white-space:normal;border:1px solid #CCCCCC;display:none' class="mobile_pop_style${no.count}">
		<div class="mmimg_in" style="width:${w_width}px;height:${w_height}px">
			<!-- -->
			<div class="mmmimg">
				${popup.content}
			</div>
		</div>
		<c:if test="${popup.ck_chk eq 'Y'}">
		<div class="popup_btm">
			<div class="btn_chk"><input type="checkbox" id="next_close${popup.idx}" value="Y" /><label for="next_close${popup.idx}">다음에 창 열지 않기</label></div>
			<input type="button" value="닫기" onclick="closeWin${popup.idx}();" class="btn_cls" />
		</div>
		</c:if>
	</div>
	</c:if>
	<SCRIPT language="JavaScript">
	
	<c:if test="${popup.ck_chk eq 'Y'}">

			// 팝업창 띄우지 않게 하기 시작
			function ReadCookie(name){
				var nameOfCookie=name+"=";
							//alert(nameOfCookie);
				var x=0;
				while(x<=document.cookie.length)
				{
					var y=(x+nameOfCookie.length)
					if(document.cookie.substring(x,y)==nameOfCookie){
						if((endOfCookie=document.cookie.indexOf(";",y))==-1)
							endOfCookie=document.cookie.length;
						return unescape(document.cookie.substring(y,endOfCookie));
					}
					x=document.cookie.indexOf(" ",x) +1;
					if(x==0)
						break;
				}
				return "";
			}


			function bd_popup_win1(){
				var ck = ReadCookie( "pop${popup.idx}" );
				if ( ck == "${ck_val}" ){
					return;
				}else{
					<c:if test="${popup.w_chk eq 'L'}">
					document.getElementById('layer_${popup.idx}').style.display='block';
					</c:if>
					<c:if test="${popup.w_chk eq 'Y'}">
						var height_size = "${w_height}";
						<c:if test="${popup.ck_chk eq 'Y'}">
							 height_size = ${w_height}+30;
						</c:if>
					window.open('/popup/view.do?idx=${popup.idx}','bd_popup${popup.idx}','scrollbars=${popup.scrollbars},toolbar=${popup.toolbar},menubar=${popup.menubar},location=${popup.locations},width=${w_width},height='+height_size+',left=${popup.w_left}, top=${popup.w_top}');
					</c:if>
				}
			}

			bd_popup_win1();

	</c:if>	//쿠키사용을 하지 않는다면	
	<c:if test="${popup.ck_chk ne 'Y'}">
		<c:if test="${popup.w_chk eq 'L'}">
			document.getElementById('layer_${popup.idx}').style.display='block';
		</c:if>
		<c:if test="${popup.w_chk eq 'Y'}">
			var height_size = "${w_height}";
			<c:if test="${popup.ck_chk eq 'Y'}">
				 height_size = ${w_height}+30;
			</c:if>
			window.open('/popup/view.do?idx=${popup.idx}','bd_popup${popup.idx}','scrollbars=${popup.scrollbars},toolbar=${popup.toolbar},menubar=${popup.menubar},location=${popup.locations},width=${w_width},height='+height_size+',left=${popup.w_left}, top=${popup.w_top}');
		</c:if>

	</c:if>

	</SCRIPT>
</c:forEach>