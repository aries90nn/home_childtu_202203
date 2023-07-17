var le_timer = null;
var le_intrval = 5500;


$(document).ready(function(){ 
	$(":input.autoInput").each(function(){
		if ($(this).val()==""){
			$(this).prev("label").show();
		}else{
			$(this).prev("label").hide();
		}
	});
	$(":input.autoInput").blur(function(){
		if($(this).attr("id")!="mm_content"){	// 관리자메인 메모영역
			$(this).css({background:"url(/nanum/ncms/img/common/search_line.gif) no-repeat bottom left"});
		}
		if ($(this).val()==""){	
			$(this).prev("label").show();
		}
	});
	$(":input.autoInput").focus(function(){
		if($(this).attr("id")!="mm_content"){	// 관리자메인 메모영역
			$(this).css({background:"url(/nanum/ncms/img/common/search_line_on.gif) no-repeat bottom left"});
		}
		if ($(this).val()==""){
			$(this).prev("label").hide();
		}
	});

	$(":input.autoInput2").each(function(){
		if ($(this).val()==""){
			$(this).prev("label").show();
		}else{
			$(this).prev("label").hide();
		}
	});
	$(":input.autoInput2").blur(function(){
		$(this).css({background:"url(/nanum/ncms/img/common/search_line2.gif) no-repeat bottom left"});
		if ($(this).val()==""){	
			$(this).prev("label").show();
		}
	});
	$(":input.autoInput2").focus(function(){
		$(this).css({background:"url(/nanum/ncms/img/common/search_line2_on.gif) no-repeat bottom left"});
		if ($(this).val()==""){
			$(this).prev("label").hide();
		}
	});


	// 포커스된 input 이 있는 tr>td 전체
//	$(".editmode").focus(function(){
//		$(this).parents("tr").find("td").css("background-color","#feffe6");	
//	}).blur(function(){
//		$(this).parents("tr").find("td").css("background-color","");	
//	});


	// 왼쪽 메뉴 펼치기 영역
	$("div.side_open").find("img").mouseover(function(){
		clearInterval(le_timer);
		$("#side_alert").stop().fadeTo(100,1);
	}).mouseout(function(){
		$("#side_alert").stop().fadeTo(400,0);
		if($("#side_wrap:hidden").length>0){
			le_timer = setInterval(show_hide_alert, le_intrval);
		}
	});

	// 왼쪽 메뉴 가려진 상태면..
	if($("#side_wrap:hidden").length>0){
		le_timer = setInterval(show_hide_alert, le_intrval);
	}


	// Input박스 포커스 스타일
	$("input.ta_input, input.ta_input_nor, input.ta_input_file, .ta_textarea, .ta_textarea_nor").focus(function(){
	//$("input.ta_input, input.ta_input_nor, input.ta_input_file, .ta_textarea, .ta_textarea_nor").live("focus",function(){
		$(this).addClass("ta_input_on");
	}).blur(function(){
	//}).live("blur",function(){
		$(this).removeClass("ta_input_on");	
	});


	$('select.t_search').customStyle();
	$('select.ta_select').customStyle2();

});

function show_hide_alert(){
//	$("#side_alert").fadeTo(1500,1).fadeTo(800,0);
}

// 관리자 왼쪽메뉴 숨기기/보이기
function SetMngLeft(view_flag){ 
	setCookie( "mngleft_ck", view_flag , 1000); 	
	if(view_flag=="Y"){
		$("#side_wrap").show();
		$("body").attr("id","wrap");
		$("div.side_close").show();
		$("div.side_open").hide();
		clearInterval(le_timer);
	}else{
		$("#side_wrap").hide();
		$("body").attr("id","wide");
		$("div.side_close").hide();
		$("div.side_open").show();
		le_timer = setInterval(show_hide_alert,le_intrval);
	}
}




//검색어입력
//$(function(){
//	$(".search_input").focus(function(){
//		$(this).css({background:"url(/nanum/ncms/img/common/search_line_on.gif) no-repeat bottom left"});
//		$(this).prev("label").hide();
//	}).blur(function(){
//		if($(this).val() == ""){
//			$(this).css({background:"url(/nanum/ncms/img/common/search_line.gif) no-repeat bottom left"});
//			$(this).prev("label").show();
//		}
//	});
//});

jQuery.browser = {};
(function () {
    jQuery.browser.msie = false;
    jQuery.browser.version = 0;
    if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
        jQuery.browser.msie = true;
        jQuery.browser.version = RegExp.$1;
    }
})();


//select box
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

					var int_height = parseInt(selectBoxSpan.height());
					if(int_height==0)	int_height = 16;

					var selectBoxHeight = int_height + parseInt(selectBoxSpan.css('padding-top')) + parseInt(selectBoxSpan.css('padding-bottom'));
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

					var int_height = parseInt(selectBoxSpan.height());	// 가려진 컨텐츠는 0 으로 인식
					if(int_height==0)	int_height = 16;

					var selectBoxHeight = int_height + parseInt(selectBoxSpan.css('padding-top')) + parseInt(selectBoxSpan.css('padding-bottom'));
					$(this).height(selectBoxHeight).change(function(){
						selectBoxSpanInner.text($(this).find(':selected').text()).parent().addClass('changed');
					});
				});
			}
		}, 

		customStyle2_disabled : function() {
			this.next("span.customStyleSelectBox2").css({
				"border":"1px solid #e9e9e9"
				, "color":"#d1d1d1"
			});
		},

		customStyle2_org : function() {
			this.next("span.customStyleSelectBox2").css({
				"border":"1px solid #D7D6D6"
				, "color":"#3b3b3b"
			});
		}


	});
})(jQuery);
$(function(){
//	 $('select.t_search').customStyle();
//	 $('select.ta_select').customStyle2();
});	



//스킨설정 보이기
function nationBlock(objid){
	try{obj = document.getElementById(objid);}catch(err){obj=null;}
	if(obj){obj.style.display = "block";}	
}

function nationNone(objid){
	try{obj = document.getElementById(objid);}catch(err){obj=null;}
	if(obj){obj.style.display = "none";}	
}

function setPng24(obj) {
    obj.width=obj.height=1;
    obj.className=obj.className.replace(/\bpng24\b/i,'');
    obj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+ obj.src +"',sizingMethod='image');"
    obj.src='';
    return '';
}
