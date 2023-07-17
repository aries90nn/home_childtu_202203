(function($){
	$.fn.extend({
		customStyle : function(options) {
			if(!$.browser.msie || ($.browser.msie&&$.browser.version>6)){
				return this.each(function() {
					var currentSelected = $(this).find(':selected');
					$(this).after('<span class="customStyleSelectBox"><span class="customStyleSelectBoxInner">'+currentSelected.text()+'</span></span>').css({
						position:'absolute', opacity:0, fontSize:$(this).next().css('font-size')
					});
					var selectBoxSpan = $(this).next();
					var selectBoxWidth = parseInt($(this).width()) - parseInt(selectBoxSpan.css('padding-left')) -parseInt(selectBoxSpan.css('padding-right'));   
					var selectBoxSpanInner = selectBoxSpan.find(':first-child');
					selectBoxSpan.css({display:'inline-block'});
					selectBoxSpanInner.css({width:selectBoxWidth, display:'inline-block'});
					var selectBoxHeight = parseInt(selectBoxSpan.height()) + parseInt(selectBoxSpan.css('padding-top')) + parseInt(selectBoxSpan.css('padding-bottom'));
					$(this).height(selectBoxHeight).change(function(){
						selectBoxSpanInner.text($(this).find(':selected').text()).parent().addClass('changed');
					});
				});
			}
		},

		customStyle2 : function(options) {
			if(!$.browser.msie || ($.browser.msie&&$.browser.version>6)){
				return this.each(function() {
					var currentSelected = $(this).find(':selected');
					$(this).after('<span class="customStyleSelectBox2"><span class="customStyleSelectBoxInner2">'+currentSelected.text()+'</span></span>').css({
						position:'absolute', opacity:0, fontSize:$(this).next().css('font-size')
					});
					var selectBoxSpan = $(this).next();
					var selectBoxWidth = parseInt($(this).width()) - parseInt(selectBoxSpan.css('padding-left')) -parseInt(selectBoxSpan.css('padding-right'));   
					var selectBoxSpanInner = selectBoxSpan.find(':first-child');
					selectBoxSpan.css({display:'inline-block'});
					selectBoxSpanInner.css({width:selectBoxWidth, display:'inline-block'});
					var selectBoxHeight = parseInt(selectBoxSpan.height()) + parseInt(selectBoxSpan.css('padding-top')) + parseInt(selectBoxSpan.css('padding-bottom'));
					$(this).height(selectBoxHeight).change(function(){
						selectBoxSpanInner.text($(this).find(':selected').text()).parent().addClass('changed');
					});
				});
			}
		}


	});
})(jQuery);
$(function(){
	 $('select.nor').customStyle();
	 $('select.foot').customStyle2();
});	


function ViewCss(css) {
	document.write("\<link rel=\"stylesheet\" type=\"text/css\" href=\"" + css + "\">");
}

function SetSkin(Skin_Color){ 	
	$.ajax({
		type: "POST",
		url: "/common/file/setskin.php", 
		data: "sc="+Skin_Color,
		dataType:"html",
		async:true,
		success: function(msg){	
		},complete: function (xhr, textStatus){
			self.location.reload()
		}
	});
}

//인풋박스 클릭시보더값 변경
function focus_on1_default(str) {
	(str).style.border='1px solid #dedede';
	(str).style.borderTop='1px solid #9f9f9f';
	(str).style.borderLeft='1px solid #9f9f9f';
	(str).style.background='#e6e6e6'
//	(str).style.border='1px solid #394466';
//	(str).style.background='#394466';
//	(str).style.color='#fff';
}

function focus_on1_casual(str) {
	(str).style.border='1px solid #dedede';
	(str).style.borderTop='1px solid #9f9f9f';
	(str).style.borderLeft='1px solid #9f9f9f';
	(str).style.background='#e6e6e6'
//	(str).style.border='1px solid #383838';
//	(str).style.background='none';
}

function focus_on1_chic(str) {
	(str).style.border='1px solid #dedede';
	(str).style.borderTop='1px solid #9f9f9f';
	(str).style.borderLeft='1px solid #9f9f9f';
	(str).style.background='#e6e6e6'
//	(str).style.border='1px solid #f1d1bd';
//	(str).style.background='#f6ded0';
//	(str).style.color='#312c2a';
}

function focus_on1_dandy(str) {
	(str).style.border='1px solid #dedede';
	(str).style.borderTop='1px solid #9f9f9f';
	(str).style.borderLeft='1px solid #9f9f9f';
	(str).style.background='#e6e6e6'
//	(str).style.border='1px solid #d5b95e';
//	(str).style.background='#d5b95e';
}

function focus_on1_marine(str) {
	(str).style.border='1px solid #dedede';
	(str).style.borderTop='1px solid #9f9f9f';
	(str).style.borderLeft='1px solid #9f9f9f';
	(str).style.background='#e6e6e6'
}

function focus_off1(str) {
	(str).style.border='1px solid #f3f3f3';
	(str).style.borderTop='1px solid #e1e1e1';
	(str).style.borderLeft='1px solid #e1e1e1';
	(str).style.background='#f8f8f8';
	(str).style.color='#000';
}

function focus_on2(str) {
	(str).style.border='1px solid #dedede';
	(str).style.borderTop='1px solid #9f9f9f';
	(str).style.borderLeft='1px solid #9f9f9f';
	(str).style.background='#e6e6e6'
}

function focus_off2(str) {
	(str).style.border='1px solid #f3f3f3';
	(str).style.borderTop='1px solid #d7d6d6';
	(str).style.borderLeft='1px solid #d7d6d6';
	(str).style.background='#fff';
}


//2단계메뉴 펼침
function leftMenu(menuIdx){
	for(i=1 ; i<=12 ; i++){
		try{
			objLayer = document.getElementById("leftSubMenu"+i);
			// objImg = document.getElementById("leftMenuImg"+i);

			if(i == menuIdx){
				// objImg.src = objImg.src.replace(".gif", "_on.gif");
				objLayer.style.display = "block";
			}else{
				// objImg.src = objImg.src.replace("_on.gif", ".gif");
				objLayer.style.display = "none";
			}
		}catch(e){ }
	}
}



//퀵 메뉴
QuickMenu = function(element, startPoint, endPoint) {
	var STATICMENU = element;
	var stmnScrollSpeed = 1;
	var stmnTimer;

	RefreshStaticMenu = function ()
	{
		var stmnStartPoint = parseInt(STATICMENU.style.top, 10);
		var stmnEndPoint = parseInt(document.documentElement.scrollTop, 10)+endPoint;
		var stmnRefreshTimer = 1;

		if ( stmnStartPoint != stmnEndPoint ) {
			stmnScrollAmount = Math.ceil( Math.abs( stmnEndPoint - stmnStartPoint ) / 17 );
			STATICMENU.style.top = parseInt(STATICMENU.style.top, 10) + ( ( stmnEndPoint<stmnStartPoint ) ? -stmnScrollAmount : stmnScrollAmount ) + "px";
			stmnRefreshTimer = stmnScrollSpeed;
		}
		stmnTimer = setTimeout ("RefreshStaticMenu();", stmnRefreshTimer);
	}

	this.InitializeStaticMenu = function ()
	{
		STATICMENU.style.top = startPoint + "px";
		RefreshStaticMenu();
	}
}


//글자크기
function wdSetFontSize(a) {
	var defaultFontSize = 1;//em
	var minFontSize = 1;//em
	var maxFontSize = 2;//em
	
	try{
		obj = document.getElementById("sub");
		var objFontSize = obj.style.fontSize;
	}catch(e){
		obj = document.getElementById("wrapper_main");
		var objFontSize = obj.style.fontSize;
	}

	if (!objFontSize) { objFontSize = parseFloat(defaultFontSize)+"em"; }
	var checkFontSize = (Math.round(12*parseFloat(objFontSize))+(a*2))/12;

	if (checkFontSize >= maxFontSize) { checkFontSize = maxFontSize; obj.style.fontSize = checkFontSize+"em"; alert("더이상 늘릴 수 없습니다."); }
	else if (checkFontSize <= minFontSize) { checkFontSize = minFontSize; obj.style.fontSize = checkFontSize+"em"; alert("더이상 줄일 수 없습니다."); }
	else { obj.style.fontSize = checkFontSize+"em"; }
	// alert(checkFontSize);
}

function wdSetFontSize_main(a) {
	var defaultFontSize = 1;//em
	var minFontSize = 1;//em
	var maxFontSize = 2;//em
	obj = document.getElementById("wrapper_main");
	var objFontSize = obj.style.fontSize;
	if (!objFontSize) { objFontSize = parseFloat(defaultFontSize)+"em"; }
	var checkFontSize = (Math.round(12*parseFloat(objFontSize))+(a*2))/12;
	if (checkFontSize >= maxFontSize) { checkFontSize = maxFontSize; obj.style.fontSize = checkFontSize+"em"; alert("더이상 늘릴 수 없습니다."); }
	else if (checkFontSize <= minFontSize) { checkFontSize = minFontSize; obj.style.fontSize = checkFontSize+"em"; alert("더이상 줄일 수 없습니다."); }
	else { obj.style.fontSize = checkFontSize+"em"; }
	// alert(checkFontSize);
}

//인쇄
var initBody;
function BeforePrint() {
	initBody = document.body.innerHTML;
	document.body.innerHTML = print_area.innerHTML;
}

function AfterPrint() {
	document.body.innerHTML = initBody;
}

function ReportPrint() {
	window.onbeforeprint = BeforePrint;
	window.onafterprint = AfterPrint;
	window.print();
} 



//주메뉴 펼침
function menuMouseOver(idx){
//	alert(idx);
	var objImage;
	var objLayer;

	for(i=1 ; i<=8 ; i++){
	try{objImage = document.getElementById("menuimg"+i);}catch(err){objImage=null;}
	try{objLayer = document.getElementById("menu"+i+"01");}catch(err){objLayer=null;}

		if(i == idx){
			if(objImage){objImage.src = objImage.src.replace(i+".gif", i+"_on.gif");}
			if(objLayer){
				if(!smenu_status){
					$(objLayer).slideDown();
					smenu_status = true;
				}else{
					$(objLayer).show();
				}
			}
			if(objLayer){objLayer.style.display = "block";}
			$("#menuimg"+i).parent("a").removeClass("sbmenu").addClass("sbmenu_on");
		}else{
			if(objImage){objImage.src = objImage.src.replace(i+"_on.gif", i+".gif");}
			if(objLayer){objLayer.style.display = "none";}
			$("#menuimg"+i).parent("a").removeClass("sbmenu_on").addClass("sbmenu");

		}
	}
}
function submenuimgMouseOver(idx, subIdx){
	try{objImage = document.getElementById("submenuimg"+idx+""+subIdx);}catch(err){objImage=null;}
	if(objImage) objImage.src = objImage.src.replace("menu0"+idx+"0"+subIdx+".", "menu0"+idx+"0"+subIdx+"_on.");
}

function submenuimgMouseOut(idx, subIdx){
	try{objImage = document.getElementById("submenuimg"+idx+""+subIdx);}catch(err){objImage=null;}	
	if(objImage) objImage.src = objImage.src.replace("menu0"+idx+"0"+subIdx+"_on.", "menu0"+idx+"0"+subIdx+".");
}




//전체메뉴펼침
function if_showhidden(zz) {
	var obj = document.getElementById(zz);
	if(obj.style.display == "none" || obj.style.display == "") {
		obj.style.display = "block";
	} else {
		obj.style.display = "none";
	}
}




//배너
var mtickerEl = new Array();
function initmTicker(mtickerContainer, mtickerContent, delay) {
	mtickerEl[mtickerEl.length] = mtickerContainer;
	var speed = 10; 
	var mtickerElsum = 1; 
	mtickerContainer.delay = delay/(speed/mtickerElsum); 
	mtickerContainer.moveOffset = mtickerContainer.offsetHeight;
	mtickerContainer.count = 0;
	mtickerContainer.mtickerOver = false;
	mtickerContainer.cont = mtickerContent;
	mtickerContainer.cont.currentHeight = 0;
	mtickerContainer.move = setInterval("movemTicker()", speed);
	for (i=0; i<mtickerEl.length; i++) {
		mtickerEl[i].onmouseover = function() { this.mtickerOver=true; }
		mtickerEl[i].onmouseout = function() { this.mtickerOver=false; }
	}
}
function movemTicker() {
	for (i=0; i<mtickerEl.length; i++) {
		if (mtickerEl[i].cont.currentHeight % mtickerEl[i].moveOffset == 0 && mtickerEl[i].count < mtickerEl[i].delay) {
			if(!mtickerEl[i].mtickerOver) mtickerEl[i].count++;
		} else {
			mtickerEl[i].count = 0;
			mtickerEl[i].cont.currentHeight -= mtickerEl[i].moveOffset;
			if (mtickerEl[i].cont.currentHeight % (mtickerEl[i].cont.offsetHeight) == 0) {
				mtickerEl[i].cont.currentHeight = 0;
			}
			mtickerEl[i].cont.style.top = mtickerEl[i].cont.currentHeight + "px";
		}
	}
}
function prevmTicker(mtickerElnum) {
	var mtickerElnum = mtickerElnum-1;
	mtickerEl[mtickerElnum].count = 0;
	mtickerEl[mtickerElnum].cont.currentHeight+= mtickerEl[mtickerElnum].moveOffset;
	if (-mtickerEl[mtickerElnum].cont.currentHeight < 0) {
		mtickerEl[mtickerElnum].cont.currentHeight = mtickerEl[mtickerElnum].moveOffset-mtickerEl[mtickerElnum].cont.offsetHeight;
	}
	mtickerEl[mtickerElnum].cont.style.top = mtickerEl[mtickerElnum].cont.currentHeight + "px";
}
function nextmTicker(mtickerElnum) {
	var mtickerElnum = mtickerElnum-1;
	mtickerEl[mtickerElnum].count = 0;
	mtickerEl[mtickerElnum].cont.currentHeight-= mtickerEl[mtickerElnum].moveOffset;
	if (-mtickerEl[mtickerElnum].cont.currentHeight >= mtickerEl[mtickerElnum].cont.offsetHeight) {
		mtickerEl[mtickerElnum].cont.currentHeight = 0;
	}
	mtickerEl[mtickerElnum].cont.style.top = mtickerEl[mtickerElnum].cont.currentHeight + "px";
}

//메인게시판
function viewNews(a){
	for(var i=1;i<5;i++){
		try{obj = document.getElementById("news_list"+i);}catch(err){obj=null;}
		try{obj2 = document.getElementById("ntab"+i);}catch(err){obj2=null;}

		if(a==i){
			if (obj)	obj.style.display = "block";
			if (obj2)	obj2.className = "on"
		}else{
			if (obj)	obj.style.display = "none";
			if (obj2)	obj2.className = "";
		}
	}
}

//메인 공지사항
function viewList(a){
	for(var i=1;i<4;i++){
		obj = document.getElementById("hlist"+i);
		obj2 = document.getElementById("tab"+i);
		if (i>1){
			obj3 = document.getElementById("tab"+(i-1));
		}
		if(a==i){
			obj.style.display = "block";
			obj2.setAttribute("src","/img/main/notice_tit"+i+"_on.gif");
		}else{
			obj.style.display = "none";
			obj2.setAttribute("src","/img/main/notice_tit"+i+".gif");
		}
	}
}


//메인_문화행사리스트
function viewBook(a){
	for(var i=1;i<4;i++){
		try{obj = document.getElementById("mbook_list"+i);}catch(err){obj=null;}
		try{obj2 = document.getElementById("ctab"+i);}catch(err){obj2=null;}

		if(a==i){
			if (obj)	obj.style.display = "block";
			if (obj2)	obj2.className = "on"
		}else{
			if (obj)	obj.style.display = "none";
			if (obj2)	obj2.className = "";
		}
	}
}


//메인 추천,신간도서
function bookList(a){
	for(var i=1;i<3;i++){
		obj = document.getElementById("blist"+i);
		obj2 = document.getElementById("btab"+i);
		if(a==i){
			if (obj)	obj.style.display = "block";
			if (obj2)	obj2.className = "tab on"
		}else{
			if (obj)	obj.style.display = "none";
			if (obj2)	obj2.className = "tab";
		}
	}
}


//이미지롤링

// 해당 타겟의 이미지 변경
function oversubimg(id) {
	document.getElementById(id).src = document.getElementById(id).src.replace("_off.gif", "_on.gif");
}
// 해당 타겟의 이미지 변경
function outsubimg(id) {
	document.getElementById(id).src = document.getElementById(id).src.replace("_on.gif", "_off.gif");
}





//서브비주얼
$(function(){
	$(".svisual .ico").css({"left":"140px"}).delay(100).animate({"opacity":"1","left":"180px"},700);
});	


//검색창펼쳐보기
function search_w(){		 
	var objImage;
	var objLayer;

	try{objImage = document.getElementById("allsearch");}catch(err){objImage=null;}
	try{objLayer = document.getElementById("search_w");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		$(objLayer).slideDown();
	}else{
		$(objLayer).slideUp();
	}		
}


//input 클릭시 라벨 삭제
$(function(){
	$(".txt_input").each(function(){
		if($(this).val() != "")	$("label[for='"+$(this).attr("id")+"']").hide();
	}).focus(function(){
		$("label[for='"+$(this).attr("id")+"']").hide();
	}).blur(function(){
		if($(this).val() == "")	$("label[for='"+$(this).attr("id")+"']").show();
	});
});




//전체메뉴에서 화면픽스되게끔하는 부분
$(document).ready(function(){
	$('.allbtn').click(function() {
		$('#wrapper').css('height','1000px');
		$('#wrapper').css('overflow','hidden');
	});
	$('.allmenu_close').click(function() {
		$('#wrapper').css('height','auto');
		$('#wrapper').css('overflow','auto');
	});
});


//모바일전체메뉴에서 화면픽스되게끔하는 부분
$(document).ready(function(){
	$('#rightMenuOpenHeader').click(function() {
		$('#wrapper').css('height','700px');
		$('#wrapper').css('overflow','hidden');
	});
	$('.total_close').click(function() {
		$('#wrapper').css('height','auto');
		$('#wrapper').css('overflow','auto');
	});
});


//처음오셨나요 레이어 새창시 마스크 노출
$(document).ready(function(){
	var jbHeight = $( '#wrapper' ).height();
	$('.menu-button').click(function() {
		$('.show_mask').css('display','block');
		$('#map_look').css('opacity','0');
		$('.show_mask').height(jbHeight);
	});
	$('#close-button').click(function() {
		$('.show_mask').css('display','none');
		$('#map_look').css('opacity','1');
	});
});


//메뉴 내려올때 효과
$(document).ready(function(){
	$("#menu_navi #menu_area > ul > li").mouseenter(function(){
		$(".smenu_area2 .tit_box .btxt").css("opacity","1");
		$(".smenu_area2 .tit_box .btxt").css("transform","translateY(0)");
		$(".smenu_area2 .tit_box .btxt").css("-webkit-transform","translateY(0)");

		$(".smenu_area2 .tit_box .smtxt").css("opacity","1");
		$(".smenu_area2 .tit_box .smtxt").css("transform","translateY(0)");
		$(".smenu_area2 .tit_box .smtxt").css("-webkit-transform","translateY(0)");

		$(".smenu_area2 .smenu_cont").css("opacity","1");
		$(".smenu_area2 .smenu_cont").css("transform","translateY(0)");
		$(".smenu_area2 .smenu_cont").css("-webkit-transform","translateY(0)");

		$(".smenu_area").css("background","#fff url(/nanum/site/img/common/menu_s_bg.gif) no-repeat center bottom");

	})
	$("#menu_navi #menu_area > ul > li").keydown(function(){
		$(".smenu_area2 .tit_box .btxt").css("opacity","1");
		$(".smenu_area2 .tit_box .btxt").css("transform","translateY(0)");
		$(".smenu_area2 .tit_box .btxt").css("-webkit-transform","translateY(0)");

		$(".smenu_area2 .tit_box .smtxt").css("opacity","1");
		$(".smenu_area2 .tit_box .smtxt").css("transform","translateY(0)");
		$(".smenu_area2 .tit_box .smtxt").css("-webkit-transform","translateY(0)");

		$(".smenu_area2 .smenu_cont").css("opacity","1");
		$(".smenu_area2 .smenu_cont").css("transform","translateY(0)");
		$(".smenu_area2 .smenu_cont").css("-webkit-transform","translateY(0)");

		$(".smenu_area").css("background","#fff url(/nanum/site/img/common/menu_s_bg.gif) no-repeat center bottom");

	})
		$("#menu_navi #menu_area > ul > li").mouseleave(function(){
		$(".smenu_area2 .tit_box .btxt").css("opacity","0");
		$(".smenu_area2 .tit_box .btxt").css("transform","translateY(-5px)");
		$(".smenu_area2 .tit_box .btxt").css("-webkit-transform","translateY(-5px)");

		$(".smenu_area2 .tit_box .smtxt").css("opacity","0");
		$(".smenu_area2 .tit_box .smtxt").css("transform","translateY(-10px)");
		$(".smenu_area2 .tit_box .smtxt").css("-webkit-transform","translateY(-5px)");

		$(".smenu_area2 .smenu_cont").css("opacity","0");
		$(".smenu_area2 .smenu_cont").css("transform","translateY(-10px)");
		$(".smenu_area2 .smenu_cont").css("-webkit-transform","translateY(-5px)");

		$(".smenu_area").css("background","#fff");

	})
})


// 상단메뉴 스크립트
function chk(){
if(cc == 1){
	$(".gnb_bg1").slideDown(0);
	$("#menu_navi ul li ul").css("display","block").slideDown(0);
	$(".two_depth").slideDown(0);
	$(".two_depth").css("opacity","1");
	$(".two_depth").css("height","auto");
	$(".two_depth").css("transition","all .1s .1s ease-in-out");
	$(".two_depth").css("-webkit-transition","all .1s .1s ease-in-out");
	$(".two_depth").css("transform","translateY(0)");
	$(".two_depth").css("-webkit-transform","translateY(0)");
}else{
	$(".gnb_bg1").slideUp(0);
	$(".two_depth").slideUp(0);
	$(".two_depth").css("opacity","0");
	$(".two_depth").css("height","1px");
	$(".two_depth").css("transition","all .1s .1s ease-in-out");
	$(".two_depth").css("-webkit-transition","all .1s .1s ease-in-out");
	$(".two_depth").css("transform","translateY(5px)");
	$(".two_depth").css("-webkit-transform","translateY(5px)");
}
}
$(function(){
$('#menu_area').mouseover(function(){
	setTimeout(chk, 100);
	cc = 1;
	$(this).addClass('active');
});
$('#menu_area').mouseout(function(){
	setTimeout(chk, 400);
	cc = 0;
	$('#menu_area ul li').removeClass('active');
});
$('#menu_area ul li a').focus(function(){
	setTimeout(chk, 100);
	cc = 1;
	$(this).parent().addClass('active');
});
$('#menu_area ul li a').blur(function(){
	setTimeout(chk, 100);
	cc = 0;
	$('#menu_area ul li').removeClass('active');
});
});
$(function(){
$("#menu_area > ul > li").mouseenter(function(){
	$(this).find(" > a").addClass("active");
});
$("#menu_area > ul > li").mouseleave(function(){
	$(this).find(" > a").removeClass("active");
});
});




// 추천도서 탭메뉴
function bookList(a){
	for(var i=1;i<5;i++){
		obj = document.getElementById("blist"+i);
		obj2 = document.getElementById("btab"+i);
		obj3 = document.getElementById("txt_bg"+i);
		if(a==i){
			if (obj)	obj.style.display = "block";
			if (obj2)	obj2.className = "tab on";
			if (obj3)	obj3.style.backgroundImage = "url(/nanum/site/builder/main/main01/img/tab_menu_line.gif)";
		}else{
			if (obj)	obj.style.display = "none";
			if (obj2)	obj2.className = "tab";
			if (obj3)	obj3.style.backgroundImage = "url('')";
		}
	}
}

// 라벨없애기
$(function(){
	$(".label_put").each(function(){
		if($(this).val() != "")	$("label[for='"+$(this).attr("id")+"']").hide();
	}).focus(function(){
		$("label[for='"+$(this).attr("id")+"']").hide();
	}).blur(function(){
		if($(this).val() == "")	$("label[for='"+$(this).attr("id")+"']").show();
	});
});

// 메인 검색바
$(function(){
	$(".search_text").each(function(){
		if($(this).val() != "")	$("label[for='"+$(this).attr("id")+"']").hide();
	}).focus(function(){
		$("label[for='"+$(this).attr("id")+"']").hide();
	}).blur(function(){
		if($(this).val() == "")	$("label[for='"+$(this).attr("id")+"']").show();
	});
});