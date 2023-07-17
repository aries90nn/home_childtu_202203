/* **********************************************************************
	nameSpace �앹꽦
********************************************************************** */
;(function(window, $){
	'use strict';

	var global = "$utils", nameSpace = "NHLIFE.utils", nameSpaceRoot = null;

	function createNameSpace(identifier, module){
		var win = window, name = identifier.split('.'), p, i = 0;

		if(!!identifier){
			for (i = 0; i < name.length; i += 1){
				if(!win[ name[ i ] ]){
					if(i === 0){
						win[ name[ i ] ] = {};
						nameSpaceRoot = win[ name[ i ] ];
					} else {
						win[ name[ i ] ] = {};
					}
				}
				win = win[ name[ i ] ];
			}
		}
		if(!!module){
			for (p in module){
				if(!win[ p ]){
					win[ p ] = module[ p ];
				} else {
					throw new Error("module already exists! >> " + p);
				}
			}
		}
		return win;
	}

	if(!!window[ global ]){
		throw new Error("already exists global!> " + global);
	}

	window[ global ] = createNameSpace(nameSpace, {
		namespace : function(identifier, module){
			return createNameSpace(identifier, module);
		}
	});
})(window, jQuery);

var ui;

/* **********************************************************************
	NHLIFE.common
	* ui 珥덇린��
	* @memberof NHLIFE.common
********************************************************************** */
;(function(window, $) {
	'use strict';
	ui = $utils.namespace('NHLIFE.common', {

		/* ---------------------------------------------------------------------------
			�덉씠�꾩썐 : �ㅻ퉬寃뚯씠��(�꾩껜硫붾돱/GNB)
		--------------------------------------------------------------------------- */
		uiNav : function(){
			/*  GNB **********************************************/
			var $gnb = $('#gnb');
			var $gnb_menu = $gnb.find('> ul > li');
			var $gnb_menu_el = $gnb.find('> ul > li > a');
			var $gnb_menu_sub = $gnb.find('> ul > li ol a');
			var $gnb_timer = null;

			//�꾩옱移댄뀒怨좊━�ㅼ젙
			if($('#container').attr('class')){
				var menu_cate = $('#container').attr('class').split(' ')[0];
				if(menu_cate=="ig") $gnb_menu.eq(0).addClass('active');
				if(menu_cate=="cc") $gnb_menu.eq(1).addClass('active');
			}

			//1李⑤찓�� �대깽�몄꽕��
			$gnb_menu_el
				.on("focus mouseover", function() {
					if(!$('#asideMyMenu').hasClass('open') && !$('#asideTotalMenu').hasClass('open')){
						clearTimeout($gnb_timer);
						$gnb_menu.removeClass('on');
						$(this).parent('li').addClass('on');
					}
				})
				.on("blur mouseout", function() {
					setGnbOff();
				});

			//2李⑤찓�� �댄븯 �대깽�� �ㅼ젙
			$gnb_menu_sub
				.on("focus mouseover", function() {
					clearTimeout($gnb_timer);
				})
				.on("blur mouseout", function() {
					setGnbOff();
				});

			//GNB �リ린湲곕뒫
			function setGnbOff(){
				$gnb_timer = setTimeout(function(){
					$gnb_menu.removeClass('on');
				},200);
			}

			//硫붿씤�붿옄�몃�寃�(PC��)
			$('#container').hasClass('main') ? $('#header').addClass('main') : $('#header').removeClass('main');


		},

		/* ---------------------------------------------------------------------------
			�덉씠�꾩썐 : �⑦꽩�앹꽦
		--------------------------------------------------------------------------- */
		uiPattern : function(){
			var pattern = new Array();
			pattern["main"] = [["pattern1" , "load"] , ["pattern3" , "scroll"] , ["pattern2", "scroll"]];
			pattern["ig"] = [["pattern1", "scroll"], ["pattern2", "scroll"] , ["pattern3", "scroll"] , ["pattern1", "scroll"] , ["pattern2", "scroll"] , ["pattern3","scroll"]];
			pattern["ig_sub"] = [["pattern1", "load"], ["pattern3", "load"]];
			pattern["cc"] = [["pattern4", "scroll"], ["pattern5", "scroll"] , ["pattern6", "scroll"]];
			pattern["cc_submain"] = [["pattern1", "load"]];
			pattern["zz"] = [["pattern4", "scroll"], ["pattern5", "scroll"] , ["pattern6", "scroll"]];
			pattern["sc"] = [["pattern1" , "scroll"] , ["pattern2" , "scroll"] , ["pattern3", "scroll"], ["pattern1" , "scroll"] , ["pattern2" , "scroll"] , ["pattern3", "scroll"]];

			if($('#container').attr('class')){
				var menu_cate = $('#container').attr('class').split(' ')[0];
				if(menu_cate=="cc" && $('#container').hasClass('submain')) menu_cate = "cc_submain";

				for(var i=0;i<pattern[menu_cate].length;i++){
					var el_div = $('<div>').addClass(pattern[menu_cate][i][0]).addClass("num"+(i*1+1)).attr('data-motion',pattern[menu_cate][i][1]);
					for(var k=0;k<3;k++){
						el_div.append($('<span>'));
					}

					if(menu_cate!="ig") $('#wrapper').append(el_div);
					else $('#container .wrap-detail').eq(0).append(el_div);
				}

				if(menu_cate=="ig"){
					for(var a=0;a<pattern["ig_sub"].length;a++){
						var el_div = $('<div>').addClass(pattern["ig_sub"][a][0]).addClass("num"+(a*1+1)).attr('data-motion',pattern["ig_sub"][a][1]);
						for(var b=0;b<3;b++){
							el_div.append($('<span>'));
						}
						$('#container .wrap-result').eq(0).prepend(el_div);
					}
				}
			}
		},

		/* ---------------------------------------------------------------------------
			init : onload
		--------------------------------------------------------------------------- */
		init: function() {
			ui.uiPattern();
			ui.uiNav();
			ui.uiFixed();
			ui.setTableSummary();
			ui.scrollBar();
			ui.uiAccessibilityForm();

			$('a[href^="tel"]').on("click",function(ev){
				if(!$('html').hasClass('mb')) ev.preventDefault();
			});
		}
	});
	$(document).ready(ui.init);
})(window, jQuery);