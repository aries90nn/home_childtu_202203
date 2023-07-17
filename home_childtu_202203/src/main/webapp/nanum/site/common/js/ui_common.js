var mainReviewSlide, mainVisual, eventBanner, pSummarySlide; //
var counter1; //
$(function () {
	$.fn.sorting = function(){
		var $option = $('.main_p_option li a'), $product = $('.main_p_list li');
		$option.on('click', function(e) {
			var $this = $(this), key = $this.data('type'), arrType = [];
			key += '';
			if (key) {
				arrType = key.split (',');
				shuffle.apply (this, arrType);
			}
		});
		var shuffle = function(n1,n2,n3,n4,n5,n6){// 
			var $showItem = new Array(n1,n2,n3,n4,n5,n6);// 
			var $showTotal = 0;
			for(s=0;s<=6;s++){// 
				if($showItem[s] == null){
					$showTotal = s;
					break;
				}
			}
			var $widthNum = new Array(0,409,816);
			var $heightNum = new Array(0,0,0);
			var $max = $heightNum[0];
			var $minNum =0;
			var $leftNum = 0;
			var $topNum = 0;
			var $feedArea = $('.main_p_list ul');
			var $item = $('.main_p_list li');
			var $itemTotal = $item.length;
			
			$product.removeClass('active');
			for(i=0;i<=6;i++){// 
				$('.main_p_list li[data-type="' + $showItem[i] + '"]').addClass('active');
			}
			$('.main_p_list li:not(.active)').fadeOut();
			$('.main_p_list li.active').fadeIn();
			function pOffset(SN,LN){
				for(var n=SN; n<=LN; n++){
					var $min = $heightNum[0];
					for(var a=0; a<$heightNum.length; a++){
						if($min > $heightNum[a]){
							$min = $heightNum[a];
						};
					};
					for(var b=0; b<$heightNum.length; b++){
						if($min == $heightNum[b]){
							$minNum = b;
							break;
						};
					};
					$('.main_p_list li[data-type="' + $showItem[n] + '"]').css({top: $heightNum[$minNum], left: $widthNum[$minNum]});
					$feedArea.css({height : $max + 'px'});
					$heightNum[$minNum] = $heightNum[$minNum] + $('.main_p_list li[data-type="' + $showItem[n] + '"]').height()+24;
					for(var i=0; i<$heightNum.length; i++){
						if($max < $heightNum[i]){
							$max = $heightNum[i];
						};
					};
				};
			};
			var fNum = 0;
			var lNum = $showTotal;
			pOffset(fNum,lNum);
		}
	}
	$.fn.sorting();
	
	
	// Banner Close
	$('.close_banner').on('click', function(e) {
		$('.top_banner').slideUp(300);
	});
	// Event Popup Close
	$('.dialog_event_close').on('click', function(e) {
		$.fn.eventClose();
	});
	$.fn.eventClose = function(){
		var $eventPopup = $('#dialogMainEvent'), $dimmed = $('.dimmed');
		$eventPopup.remove();
		$dimmed.remove();
	}
	// a11y nav
	$.fn.nav = function(){
		$('#head').append('<div class="gnb_bg"></div>');
		var $nav = {
				gnb: $('.gnb'),
				menuWrap: $('.gnb>ul>li'),
				menu: $('.gnb>ul>li>a'),
				subMenu: $('.gnb .sub_menu'),
				current: $('.gnb>ul>li.current'),
				bg: $('#head').find(".gnb_bg"),
				bar: $('.gnb .bar')
			}, 
			visibility = null;
		$nav.menu.attr('aria-expanded','false');
		$nav.gnb.on({
			"mouseover": navOver,
			"focusin": navOver,
			"mouseout": navHide,
			"focusout": navHide
		})
		$nav.bg.on({
			"mouseover": navShow,
			"mouseout": navHide
		})
		function navHide() {
			if (visibility) {
				clearTimeout(visibility);
				visibility = null;
			}
			visibility = setTimeout(function() {
				navClose();
			}, 100)
		}
		function navShow() {
			if (visibility) {
				clearTimeout(visibility);
				visibility = null;
			}
		}
		function navOver() {
			navShow();
			navOpen();
		}
		function navOpen() {
			$nav.menu.attr('aria-expanded','true');
			$nav.subMenu.show(0,function() {
				var subMenuHeight = 0;
				$nav.subMenu.each(function() {
					var currentHeight = $(this).outerHeight();
					if(subMenuHeight < currentHeight){
						subMenuHeight = currentHeight;
					} else {
						subMenuHeight = subMenuHeight;
					}
				});
				$nav.bg.css('height',subMenuHeight);
				setTimeout(function() {
					$nav.subMenu.css({'opacity':'1'});
				}, 100)
			});
			if($('.dimmed').length === 0){
				$('body').append('<div class="dimmed" tabindex="-1"></div>');
			}
		}
		function navClose() {
			$nav.menu.attr('aria-expanded','false');
			$nav.bg.css({'height':'0','transition-duration':'0.3s'});
			$nav.subMenu.css({'opacity':'0','transition-duration':'0.3s'},
				setTimeout(function() {
					$nav.subMenu.hide();
				}, 100)
			);
			$('.dimmed').remove();
		}
		if ($nav.menuWrap.hasClass('current')) {
			$nav.bar.css({left: $nav.current.position().left + "px"}).addClass('active');
		}
		$nav.menuWrap.on('mouseover focusin', function(e) {
			var xValue = $(this).closest('li').position().left;
			$nav.bar.addClass('active').css({left: xValue + "px"});
		});
		$nav.menuWrap.on('mouseout focusout', function(e) {
			if ($nav.menuWrap.hasClass('current')) {
				$nav.bar.css({left: $nav.current.position().left + "px"});
			} else {
				$nav.bar.removeClass('active');
			}
		});
	}
	if($('#head').length > 0) {
		$.fn.nav();
	}
	// Floating
	$.fn.floating = function(){
		$('.quick_menu .toggle').on('click', function(e) {
			var $this = $(this), $txt = $this.find('.offscreen'), $quickMenu = $('.quick_menu'), $menu = $('.quick_menu').find('ul');
			e.preventDefault();
			$quickMenu.toggleClass('active');
			if(!$quickMenu.hasClass('active')){
				setTimeout(function() {
					$menu.removeClass('animated').attr('aria-hidden','true');
				}, 100)
				$txt.text('text');
			} else {
				setTimeout(function() {
					$menu.addClass('animated').attr('aria-hidden','false');
				}, 100)
				$txt.text('text');
			}
		});
		var $window = $(window),$target = $('.btn_top');
			$window.scroll(function() {
				($window.scrollTop() > 100) ? $target.addClass('show') : $target.removeClass('show');
			});
		$('.btn_top').on('click', function(e) {
			e.preventDefault();
			$('html, body').animate({scrollTop:0}, 300);
		});
	}
	$.fn.floating();
	// 諛붾줈媛��� Toggle
	$.fn.menu_toggle = function(){
		var $openJoin = $('.join_now_open'), $closeJoin = $('.join_now_close'), $target = $('#joinNow'), $close = $('.btn_close_join'), $outer = $('.join .btn_join');
		var $focus ='a[href], area[href], input:not([disabled]), input:not([readonly]), select:not([disabled]), textarea:not([disabled]), button:not([disabled]), iframe, object, embed, *[tabindex], *[contenteditable]';
		$openJoin.on('click', function(e) {
			e.preventDefault();
			$(this).hide();
			$closeJoin.css('display','block');
		});
		$closeJoin.on('click', function(e) {
			e.preventDefault();
			$(this).hide();
			$openJoin.css('display','block');
			$close.click();
		});
		$close.on('click', function(e) {
			e.preventDefault();
			$closeJoin.hide();
			$openJoin.css('display','block');
		});
	}
	$.fn.menu_toggle();
	// a11y Tab
	$.fn.tab = function(){
		var $tabWidget = $('.tab_section');
		$tabWidget.each(function () {
			var $this = $(this),
				$tab = $this.find('.tab_nav'),
				$tabListItems = $tab.find('li a'),
				$tabListItemActive = $tab.find('li.active a'),
				$tabListItemID = $tabListItemActive.attr("href");
				$tabPanels = $this.find('.panel');
				$tabPanelActive = $($tabListItemID);
			$tabPanels.attr({
				'aria-hidden':'true'
			});
			$tabPanelActive.attr('aria-hidden','false').addClass('active');
		});
		$(".tab_nav .tab a").on('click', function(e) {
			e.preventDefault();
			$(this).closest('.tab_nav').find('li').removeClass('active');
			$(this).closest('li').addClass('active');
			var tabpanid = $(this).attr("href");
			var tabpan = $(tabpanid);
			tabpan.attr('aria-hidden','false').addClass('active').siblings().attr('aria-hidden', 'true').removeClass('active');
			if($(this).closest('#productTab').length > 0){
				var $this = $(this), $target = $($this.attr('href')), tabHeight = $('#productTab').outerHeight();
				$('html,body').animate({'scrollTop':$target.offset().top-tabHeight},500, 'easeInOutCubic');
				setTimeout(function() {
					$('.motion').removeClass('animated');
					clearTimeout(counter1);
					$('.counter_s4 span').text(0);
					$('.pie_area .counter span').text(0);
					$.fn.scrollAni();
				}, 600)
			}
		});
		//$('[role="tab"]').keyup(function(e) {
//			var keyCode = e.keyCode || e.which,
//				lastPanel = $(this).closest('.tab_nav').find('li:last-child').attr('aria-controls'),
//				firstPanel = $(this).closest('.tab_nav').find('li:first-child').attr('aria-controls'),
//				firstTab = $(this).closest('.tab_nav').find('li:first-child').attr('id');
//				lastTab = $(this).closest('.tab_nav').find('li:last-child').attr('id');
//			if(keyCode == 39 || keyCode == 40) { // �ㅻⅨ履쎈갑�ν궎 �닿굅�� �꾨옒 諛⑺뼢��
//			  e.preventDefault();
//			  $(this).removeClass('active').next().addClass('active').attr('aria-selected', true).siblings().attr('aria-selected', false);
//			  var selectedId = "#" + $(this).next().attr('aria-controls');
//			  $(selectedId).addClass('active').siblings().removeClass('active');
//			  $(this).next().focus();
//			  
//			  if($(this).next().prevObject.attr('aria-controls') == lastPanel) {
//				$('#' + firstTab).focus().addClass('active').attr('aria-selected', true).siblings().removeClass('active').attr('aria-selected', false);
//				$('#' + firstPanel).addClass('active').siblings().removeClass('active');
//			  }
//			}
//			if(keyCode == 37 || keyCode == 38) { // �쇱そ諛⑺뼢�� �닿굅�� �꾩そ 諛⑺뼢��
//			  e.preventDefault();
//			  $(this).removeClass('active').prev().addClass('active').attr('aria-selected', true).siblings().attr('aria-selected', false);
//			  var selectedId = "#" + $(this).prev().attr('aria-controls');
//			  $(selectedId).addClass('active').siblings().removeClass('active');
//			  $(this).prev().focus();
//			  if($(this).prev().prevObject.attr('aria-controls') == firstPanel) {
//				$('#' + lastTab).focus().addClass('active').attr('aria-selected', true).siblings().removeClass('active').attr('aria-selected', false);
//				$('#' + lastPanel).addClass('active').siblings().removeClass('active');
//			  }
//			}
//			if(keyCode == 35) { //end �ㅻ� �뚮��� ��
//			  e.preventDefault();
//			  $('#' + lastTab).focus().addClass('active').attr('aria-selected', true).siblings().removeClass('active').attr('aria-selected', false);
//			  $('#' + lastPanel).addClass('active').siblings().removeClass('active');
//			}
//			if(keyCode == 36) { //home �ㅻ� �뚮��� ��
//			  e.preventDefault();
//			  $('#' + firstTab).focus().addClass('active').attr('aria-selected', true).siblings().removeClass('active').attr('aria-selected', false);
//			  $('#' + firstPanel).addClass('active').siblings().removeClass('active');
//			}
//			if(keyCode == 13) { //Enter �ㅻ� �뚮��� ��
//			  e.preventDefault();
//			  $(this).click();
//			}
//		});
	};
	$.fn.tab();
	// Target Scroll Move
	$('.target_move').on('click', function() {
		var $target = $($(this).attr('href'));
		$('html,body').animate({'scrollTop':$target.offset().top},800, 'easeInOutCubic');
	});
	// a11y Combobox
	$.fn.combobox = function(){
		var $comboboxBtn = $(".combobox button"), $comboboxList = $(".combobox ul"), $comboboxListOption = $(".combobox ul li"), $options, $combobox, $listbox, selectedIndex = -1;
		$comboboxBtn.attr({'role':'combobox', 'aria-autocomplete':'none', 'tabindex':'0', 'aria-owns':'listbox'});
		$comboboxList.attr('role','listbox');
		$comboboxListOption.attr('role','option');
		$comboboxBtn.on('keydown', handleKeyDown);
		$comboboxBtn.on('click', function() {
			$combobox = $(this);
			$listbox = $combobox.closest(".combobox").find("ul");
			$listbox.toggle();
		});
		$comboboxListOption.on('click', function() { 
			$combobox = $(this).closest(".combobox").find("button");
			$listbox = $(this).closest(".combobox").find("ul");
			$options = $(this).closest(".combobox").find("li");
			selectedIndex = $options.index($(this));
			selectOption();
			$options.removeClass('selected');
			$(this).addClass('selected');
			close();
		});
		function open() {
			$listbox.show();
		}
		function close() {
			var $option = $($options[selectedIndex]);
			$listbox.hide();
			$combobox.attr('aria-activedescendant', $option.attr('id'));
			selectedIndex = -1;
		}
		function highlightOption() {
			var $option = $($options[selectedIndex]);
			$options.removeClass('selected');
			$option.addClass('selected');
			$combobox.attr('aria-activedescendant', $option.attr('id'));
		}
		function selectOption() {
		  $combobox.html($($options[selectedIndex]).html());
		}
		function handleKeyDown(event) {
		  var keyCode = event.keyCode;
          if([38,40].indexOf(event.keyCode) > -1) {
		  	event.preventDefault();
		  }
		  $combobox = $(this);
		  $listbox = $(this).closest(".combobox").find("ul");
		  $options = $(this).closest(".combobox").find("li");
		  $listbox.toggle();
		  switch(keyCode) {
			case 9: // tab, esc
			case 27:
			  close();
			  break;
			case 13: // enter
			  if ($listbox.is(':visible')) {
				selectOption();
				close();
			  } else {
				open();
			  }
			  break;
			case 40: // down
			  open();
			  if (selectedIndex < $options.length-1) {
				selectedIndex++;
				highlightOption();
				selectOption();
			  }
			  break;
			case 38: // up
			  open();
			  if (selectedIndex > 0) {
				selectedIndex--;
				highlightOption();
				selectOption();
			  }
			  break;
		  }
		}
	};
	$.fn.combobox();
	// a11y popup
	$.fn.modalDialog = function(){
		var $modals = this,
			$focus ='a[href], area[href], input:not([disabled]), input:not([readonly]), select:not([disabled]), textarea:not([disabled]), button:not([disabled]), iframe, object, embed, *[contenteditable]',
			$body = $('body'),
			$wrap = $('#wrap'),
			$dialog = $('.dialog'),
			$gnb = $('#gnb');
		$dialog.attr('aria-hidden','true');
		$gnb.attr('aria-hidden','true');
		$modals.on('click', function(e){
			var $this = $(this);
			var $select_id = $($(this).attr('href'));
			var $sel_id_focus = $select_id.find($focus);
			var $focus_num = $select_id.find($focus).length;
			var $closBtn = $select_id.find('.dialog_close, .btn_close_join');
			var clickAnchor = $this.attr('href');
			var hrefFocus = this;
			e.preventDefault();
			if(clickAnchor == '#joinNow' && $('.dimmed').length === 0){
				$body.append('<div class="dimmed" tabindex="-1"></div>');
			} else if($('.dimmed').length === 0) {
				$body.addClass('no_scroll');
				$body.append('<div class="dimmed" tabindex="-1" style="z-index:9999"></div>');
			}
			$(clickAnchor).siblings().find($focus).attr('tabindex','-1');
			$select_id.attr('tabindex', '0').attr({'aria-hidden':'false','aria-live':'polit'}).fadeIn(100).addClass('show').focus();
			$select_id.on('blur', function(){ $(this).removeAttr('tabindex'); });
			$.fn.dialogHeight(); // �앹뾽而⑦뀗痢� �믪씠 怨꾩궛
			$($select_id).find($focus).last().on("keydown", function(e){
				if (e.which == 9) {
					if(e.shiftKey) {
						$($select_id).find($focus).eq($focus_num - 1).focus();
						e.stopPropagation();
					} else {
						$($select_id).find($focus).eq(0).focus();
						e.preventDefault();
					};
				};
			});
			$($select_id).find($focus).first().on("keydown", function(e){
				if(e.keyCode == 9) {
					if(e.shiftKey) {
						$($select_id).find($focus).eq($focus_num - 1).focus();
						e.preventDefault();
					};
				};
			});
			$($select_id).on("keydown", {msg:clickAnchor,msg2:hrefFocus}, function(e){
				if ( e.which == 27 ) {
					e.preventDefault();
					$.fn.hide_modal (e.data.msg,e.data.msg2 );
				};
				if( $(this).is(":focus") ){
					if(e.keyCode == 9) {
						if(e.shiftKey) {
							$($select_id).find($focus).eq($focus_num - 1).focus();
							e.preventDefault();
						};
					};
				};
			});
			$closBtn.on("click", {msg:clickAnchor,msg2:hrefFocus},function(e){
				e.preventDefault();
				$.fn.hide_modal (e.data.msg,e.data.msg2 );
			});		
		});
		$.fn.hide_modal = function (info, hrefFocus){
			$body.removeClass('no_scroll');
			$(info).attr('aria-hidden','true').removeClass('show').fadeOut(300);
			$(info).siblings().find($focus).removeAttr('tabindex');
			$('.dimmed').remove();
			setTimeout(function() { $(hrefFocus).focus(); }, 100);
			
			if (typeof dialogCloseAfter === 'function') {
				dialogCloseAfter(info);
			}
		};
	};
	$('.dialog_open').modalDialog();
	$.fn.dialogHeight = function(){
		var contentHeight = $(window).outerHeight() - $('.dialog_header').outerHeight() - $('.dialog_footer').outerHeight() - 200,
			$dialogContent = $('.dialog_content .scroller');
		$dialogContent.css('max-height',contentHeight);
	};
	$.fn.dialogHeight();
	// a11y Tooltip
	var tooltipFlag;
	$.fn.tooltip = function(){
		var $panel = $('.tooltip_panel'), $btn = $('.tooltip>button'), $btnClose = $('.tooltip .tooltip_close'), offset, tooltipTop, windowHeight, tooltipHeight, windowTop, currentY, scrollTop;
		$panel.attr('aria-hidden','true');
		$btn.attr({'aria-haspopup':'true', 'aria-expanded':'false'}).append('<span class="txt">�댁슜�닿린</span>')
		$(window).on('scroll',function(){
			windowHeight = $(window).height();
			windowTop = $(window).scrollTop();
			scrollTop = $(this).scrollTop();
		});
		$(document).on('mouseenter mouseleave focusin focusout click', '.tooltip button', function(e){
			var $this = $(this);
			tooltipFlag = 0;
			if(e.type == 'mouseenter'){
				if($this.closest('.tooltip').hasClass('hover')){
					$this.attr('aria-expanded','true').closest('.tooltip').find('.tooltip_panel').attr({'aria-hidden':'false'}).show();
					$this.find('.txt').text('�댁슜�リ린');
				}
			} else if(e.type == 'click' || e.type == 'focusin'){
				$btn.find('.txt').text('�댁슜�닿린');
				$panel.attr('aria-hidden','true').hide();
				offset = $(this).offset();
				tooltipTop = offset.top;
				tooltipHeight = $(this).outerHeight();
				currentY = tooltipTop-windowTop+tooltipHeight;
				if(tooltipFlag == 0){
					$this.attr('aria-expanded','true').closest('.tooltip').find('.tooltip_panel').attr({'aria-hidden':'false'}).show();
					$this.find('.txt').text('�댁슜�リ린');
					tooltipFlag = 1;
				} else {
					$this.attr('aria-expanded','false').closest('.tooltip').find('.tooltip_panel').attr('aria-hidden','true').hide();
					$this.find('.txt').text('�댁슜�닿린');
					tooltipFlag = 0;
				}
				var panelHeight = $(this).closest('.tooltip').find('.tooltip_panel').outerHeight(),
					sTop = panelHeight + scrollTop;
				if(windowHeight < currentY+panelHeight){
					$('html,body').animate({'scrollTop':sTop}) 
				}
				$btnClose.on('click focusout', function(){
					var $close = $(this);
					$close.closest('.tooltip').find('button').focus().attr('aria-expanded','false').find('.txt').text('�댁슜�닿린');
					$close.closest('.tooltip').find('.tooltip_panel').attr('aria-hidden','true').hide();
					$this.attr('aria-expanded','false').closest('.tooltip').find('.tooltip_panel').attr('aria-hidden','true').hide();
					$this.find('.txt').text('�댁슜�닿린');
					tooltipFlag = 0;
				});
			} else if(e.type == 'mouseleave' || e.type == 'focusout'){
				if($this.closest('.tooltip').hasClass('hover')){
					$this.attr('aria-expanded','false').closest('.tooltip').find('.tooltip_panel').attr('aria-hidden','true').hide();
					$this.find('.txt').text('�댁슜�닿린');
				}
			}
		});
	};
	$.fn.tooltip();
	// Tooltip Toggle
	$.fn.tooltipToggle = function() {
		var $tooltip = $('.tooltip_toggle'), $panel = $('.tooltip_toggle_panel');
		$tooltip.on('click', function(e) {
			e.preventDefault();
			var $this = $(this), $target = $this.attr('href');
			if(!$this.hasClass('active')){
				$tooltip.removeClass('active').find('span').text('�덈궡臾멸뎄蹂닿린');
				$panel.slideUp(300, 'easeInOutCubic').attr('aria-hidden','true');
				$($target).slideDown(500, 'easeInOutCubic').attr('aria-hidden','false');
				$this.addClass('active').find('span').text('�덈궡臾멸뎄�リ린');
			} else {
				$($target).slideUp(300, 'easeInOutCubic').attr('aria-hidden','true');
				$this.removeClass('active').find('span').text('�덈궡臾멸뎄蹂닿린');
			}
			
		});
	}
	$.fn.tooltipToggle();
	// a11y Accordion
	$.fn.accordion = function(){
		var $accordion = $('.accordion'),
			$title = $('.accordion .title'),
			$panel = $('.accordion .panel');
		$panel.hide();
		$accordion.attr({
			role: 'tablist',
			multiselectable: 'true'
		});
		$panel.attr('id', function(IDcount) { 
			return 'panel-' + IDcount; 
		});
		$panel.attr('aria-labelledby', function(IDcount) { 
			return 'control-panel-' + IDcount; 
		});
		$panel.attr('aria-hidden','true');
		$panel.attr('role','tabpanel');
		$title.each(function(i){
			var $this = $(this), $toggle = $(this).find('.toggle');
			$target = $this.next('.panel')[0].id;
			$link = $('<a>', {
			  'href':'#'+$target,
			  'aria-expanded':'false',
			  'aria-controls':$target,
			  'id':'control-'+ $target
			});
			$toggle.wrapInner($link);
			if($this.hasClass('open')){
				$this.find('a').attr('aria-expanded',true).addClass('active').append('<span class="ico">�댁슜�リ린</span>').parent().next('.panel').attr('aria-hidden','false').slideDown(300, 'easeInOutCubic');
			} else {
				$this.find('a').append('<span class="ico">�댁슜�닿린</span>');
			}
		});
		$('.accordion .title a').on('click', function (e) {
			e.preventDefault();
			var $this = $(this);
			if ($this.attr('aria-expanded') == 'false'){
				if(!$this.closest('.accordion').hasClass('toggle')){
					$this.parents('.accordion').find('[aria-expanded=true]').attr('aria-expanded',false).removeClass('active').parent().parent().next('.panel').attr('aria-hidden','true').slideUp(300, 'easeInOutCubic');
				}
			  	$this.attr('aria-expanded',true).addClass('active').find('.ico').text('�댁슜�リ린').parents('.title').next('.panel').attr('aria-hidden',false).slideDown(300, 'easeInOutCubic');
				$this.parent().parent().siblings().find('.ico').text('�댁슜�닿린');
			} else {
			  	$this.attr('aria-expanded',false).removeClass('active').find('.ico').text('�댁슜�닿린').parents('.title').next('.panel').attr('aria-hidden',true).slideUp(300, 'easeInOutCubic');
			}
		});
		$('.accordion .title a').keyup(function(e) {
			e.preventDefault();
			var keyCode = e.keyCode || e.which,
				titleNum = $(this).closest('.accordion').find('.title').length,
				$firstItem = $(this).closest('.accordion').find('.title').first().find('a'),
				$lastItem = $(this).closest('.accordion').find('.title').last().find('a');
			if(keyCode == 39 || keyCode == 40) { // �ㅻⅨ履쎈갑�ν궎 �닿굅�� �꾨옒 諛⑺뼢��
			  e.preventDefault();
			  $(this).parents('.title').next('.panel').next('.title').find('a').focus();
			  if($(this).parents('.title').next('.panel').next('.title').length == 0) {
				  $firstItem.focus();
			  }
			}
			if(keyCode == 37 || keyCode == 38) { // �쇱そ諛⑺뼢�� �닿굅�� �꾩そ 諛⑺뼢��
			  e.preventDefault();
			  $(this).parents('.title').prev('.panel').prev('.title').find('a').focus();
			  if($(this).parents('.title').prev('.panel').length == 0) {
				  $lastItem.focus();
			  }
			}
			if(keyCode == 35) { //end �ㅻ� �뚮��� ��
			  e.preventDefault();
			  $lastItem.focus();
			}
			if(keyCode == 36) { //home�ㅻ� �뚮��� ��
			  e.preventDefault();
			  $firstItem.focus();
			}
		});
	}
	$.fn.accordion();
	// All Check
    $.fn.allCheck = function() {
        var $this = this,
            $chkItem = $this.closest('.agree_list').find('.chk_item'),
			$subAll = $('.sub_all'),
			$subItem = $subAll.closest('li').find('ul').find('.chk_item'),
			isAllChecked;
		function roofCheck() {
			if($chkItem.length == $chkItem.filter(":checked").length){
				$this.prop("checked", true);
			} else {
				$this.prop("checked", false);
			}
		}
		$this.on('change', function() {
            if (this.checked) {
                $chkItem.each(function() {
                    this.checked = true;
                })
            } else {
                $chkItem.each(function() {
                    this.checked = false;
                })
            }
        });
		$chkItem.on('click', function() {
            if ($(this).is(":checked")) {
                isAllChecked = 0;
                roofCheck();
            } else {
                $this.prop("checked", false);
            }
        });
		$subAll.on('change', function() {
            if (this.checked) {
				$subItem.each(function() {
                    this.checked = true;
                })
            } else {
				$subItem.each(function() {
                    this.checked = false;
                })
            }
			roofCheck()
        });
		$subItem.on('click', function() {
            if ($(this).is(":checked")) {
                var isSubAllChecked = 0;
                $subItem.each(function() {
                    if (!this.checked)
                        isSubAllChecked = 1;
                })
                if (isSubAllChecked == 0) {
                    $subAll.prop("checked", true);
                }
            } else {
                $subAll.prop("checked", false);
            }
			roofCheck()
        });
    };
	$('#allChk').allCheck();
	$('#allMedia').allCheck();
	// datepicker
	//$('.datepicker').mkdatepicker();
	// Radio Tab
	$.fn.radioTab = function(panel) {
		var $target = this, $panel = panel;
		$target.on('change', function() {
			var $this = $(this), radioValue = $this.attr('id');
			$this.closest('.btn_toggle').find("input[type='radio']").attr('aria-selected','false');
			$this.attr('aria-selected','true');
			$('.'+ radioValue).show().attr('aria-hidden','false').siblings($panel).hide().attr('aria-hidden','true');
		});
	}
	$(".radio_tab input[type='radio']").radioTab('.ins_option_list');
	$(".children_tab input[type='radio']").radioTab('.child_form');
	// Select Change
	$.fn.selectChange = function(panel) {
		var $target = this, $panel = panel;
		$target.change(function(){
			$(this).find("option:selected").each(function(){
				var optionValue = $(this).attr("value");
				if(optionValue){
					$($panel).not("." + optionValue).hide();
					$("." + optionValue).show();
				} else{
					$($panel).hide();
				}
			});
		}).change();
	}
	$("select#insProducts").selectChange('.children_tab');
	// Option Toggle
	$.fn.optionToggle = function(panel) {
		var $this = $('input[type="radio"]');
		$this.on('change', function() {
			var $this = $(this),
				radioValue = $this.attr('rel'),
				yesNum = $this.closest('.question_section').find("input[type='radio'].yes:checked").length;
			if(yesNum === 0){
				$this.closest('.question_section').find('.app_options').hide().attr('aria-hidden','true');
			} else {
				$('.'+ radioValue).show().attr('aria-hidden','false');
			}
		});
	}
	$.fn.optionToggle();
	// Datepicker Toggle
	$('.date_item .datepicker').on('click', function() {
		var $this = $(this), $txt = $this.find('.offscreen');
		$this.toggleClass('active');
		if(!$this.hasClass('active')){
			$txt.text('�좎쭨�좏깮 �щ젰 �닿린');
		} else {
			$txt.text('�좎쭨�좏깮 �щ젰 �リ린');
		}
	});
	// Chart Animation Scroll Check
	$.fn.chartAni = function(){
		var $window = $(window), $el = $('.ins_design_section');
		if($el.length > 0){
			if($window.scrollTop() >= $el.offset().top - 200) {
				$el.addClass('active');
			}
		}
	};
	$.fn.chartAni();
	// Elements Animation Scroll
	$.fn.scrollAni = function(){
		var offset = $(window).scrollTop() + $(window).height(), $animatables = $('.motion');
		if ($animatables.length == 0) {
			$(window).off('scroll', $.fn.scrollAni);
		}
		$animatables.each(function() {
			var $animatable = $(this);
			if (($animatable.offset().top + 100) < offset && $animatable.closest('.panel').hasClass('active')) {
				$animatable.removeClass('animated').addClass('animated');
				if($animatable.find('.counter').length > 0){
					$('.pie_area .counter span').counter(0);// �レ옄 移댁슫�� �좊땲硫붿씠�� �몄텧
				} 
				if($animatable.find('.counter_s4').length > 0){
					counter1 = setTimeout(function() {
						$('.counter_s4 span').counter();// �レ옄 移댁슫�� �좊땲硫붿씠�� �몄텧
					}, 4000)
				}
			}
		});
	};
	// ui counter animation
	$.fn.counter = function(format){
		this.each(function () {
			var countTo = $(this).attr('data-count');
			$(this).prop('Counter',$(this).text()).animate({
				Counter: countTo
			}, {
				duration: 1000,
				easing: 'easeOutQuart',
				step: function (now) {
					if(format == 0){
						$(this).text(commaSeparateNumber(now));
					} else {
						$(this).text(commaSeparateNumber(Math.ceil(now)));
					}
				}
			});
		});
		function commaSeparateNumber(val){
			while (/(\d+)(\d{3})/.test(val.toString())){
			  val = val.toString().replace(/(\d+)(\d{3})/, '$1'+','+'$2');
			}
			return val;
		}
	}
	// Window Scroll Event
	$(window).scroll(function() {
		$.fn.chartAni();
		$.fn.scrollAni();
		$.fn.calculatorFixed();
		$.fn.tabFixed();
	});
	// Window Resize Event
	$(window).resize(function() {
		var $visual = $('.main_visual .slide .video'), visualTop = $visual.outerHeight()/2;
		$visual.css('margin-top','-'+visualTop+'px');
	});
	// swiper
    $.fn.slide = function() {
		if ($('.main_review_slide').length > 0) {
			mainReviewSlide = $('.main_review_slide').slick({
				slidesToShow:3,
				centerMode: true,
				variableWidth: true,
				arrows:true,
				dots:true,
				speed:600,
				customPaging: function(slider, i) {
					return $('<button type="button" data-role="none" role="button" tabindex="0" />').text(i + 1 + '踰덉㎏ 媛��낇썑湲�');
				},
				appendDots: $('.main_review .pager'),
				nextArrow:'.review_control.next',
				prevArrow:'.review_control.prev'
			});
        }
		if ($('.main_visual_slide').length > 0) {
			mainVisual = $('.main_visual_slide').slick({
				slidesToShow:1,
				slidesToScroll:1,
				arrows:true,
				fade:true,
				dots:true,
				speed:500,
				customPaging: function(slider, i) {
					return $('<button type="button" data-role="none" role="button" tabindex="0" />').text(i + 1 + '踰덉㎏ 諛곕꼫');
				},
				appendDots: $('.main_visual .pager'),
				nextArrow:'.main_visual .next',
				prevArrow:'.main_visual .prev',
				autoplay:true,
				autoplaySpeed:6000
			});
			$('.main_visual .pager .slick-dots').append('<li class="slick_play"><a href="#none"><span class="offscreen">�쒖옉</span></a></li>')
			$('.main_visual .pager .slick-dots').append('<li class="slick_pause"><a href="#none"><span class="offscreen">以묒�</span></a></li>')
			$('.main_visual .slick_pause').on('click',function(){
				$('.main_visual_slide').slick('slickPause');
				$('.main_visual .slick_play').show();
				$('.main_visual .slick_play a').focus();
				$('.main_visual .slick_pause').hide();
			});
			$('.main_visual .slick_play').on('click',function(){
				$('.main_visual_slide').slick('slickPlay');
				$('.main_visual .slick_play').hide();
				$('.main_visual .slick_pause').show();
				$('.main_visual .slick_pause a').focus();
			});
        }
		if ($('.event_banner_slide').length > 0) {
			eventBanner = $('.event_banner_slide').slick({
				slidesToShow:1,
				slidesToScroll:1,
				arrows:false,
				dots:true,
				speed:500,
				customPaging: function(slider, i) {
					return $('<button type="button" data-role="none" role="button" tabindex="0" />').text(i + 1 + '踰덉㎏ 諛곕꼫');
				},
				appendDots: $('.event_banner .pager'),
				autoplay:true,
				autoplaySpeed:6000
			});
			$('.event_banner .pager .slick-dots').append('<li class="slick_play"><a href="#none"><span class="offscreen">�쒖옉</span></a></li>')
			$('.event_banner .pager .slick-dots').append('<li class="slick_pause"><a href="#none"><span class="offscreen">以묒�</span></a></li>')
			$('.event_banner .slick_pause').on('click',function(){
				$('.event_banner_slide').slick('slickPause');
				$('.event_banner .slick_play').show();
				$('.event_banner .slick_play a').focus();
				$('.event_banner .slick_pause').hide();
			});
			$('.event_banner .slick_play').on('click',function(){
				$('.main_visual_slide').slick('slickPlay');
				$('.event_banner .slick_play').hide();
				$('.event_banner .slick_pause').show();
				$('.event_banner .slick_pause a').focus();
			});
        }
		if ($('.p_summary_visual').length > 0) {
            pSummarySlide = $('.p_summary_slide').slick({
				slidesToShow:1,
				slidesToScroll:1,
				arrows:false,
				dots:true,
				speed:500,
				customPaging: function(slider, i) {
					return $('<button type="button" data-role="none" role="button" tabindex="0" />').text(i + 1 + '踰덉㎏ 鍮꾩＜��');
				},
				appendDots: $('.p_summary_visual .pager'),
				autoplay:true,
				autoplaySpeed:6000
			});
			$('.p_summary_visual .pager .slick-dots').append('<li class="slick_play"><a href="#none"><span class="offscreen">�쒖옉</span></a></li>')
			$('.p_summary_visual .pager .slick-dots').append('<li class="slick_pause"><a href="#none"><span class="offscreen">以묒�</span></a></li>')
			$('.p_summary_visual .slick_pause').on('click',function(){
				$('.p_summary_slide').slick('slickPause');
				$('.p_summary_visual .slick_play').show();
				$('.p_summary_visual .slick_play a').focus();
				$('.p_summary_visual .slick_pause').hide();
			});
			$('.p_summary_visual .slick_play').on('click',function(){
				$('.p_summary_slide').slick('slickPlay');
				$('.p_summary_visual .slick_play').hide();
				$('.p_summary_visual .slick_pause').show();
				$('.p_summary_visual .slick_pause a').focus();
			});
        }
    };
    $.fn.slide();
	// Menu Toggle
	$.fn.menuToggle = function(){
		this.on('click', function(e) {
			e.preventDefault();
			var $this = $(this);
			$this.addClass('active').parents('li').siblings().find('a').removeClass('active');
		});
	}
	$('.index_list a').menuToggle();
	$('.tab_link a').menuToggle();
	$('.p_list a').menuToggle();
	$('.main_p_option a').menuToggle();
	// ui close
	$.fn.ui_close = function(){
		$(document).click(function(e) {
			var a = e.target;
			if($(a).closest('.combobox, .tooltip, .date_picker').length === 0) {
				$('.calendar_wrap').attr('aria-hidden', 'true');
				$('.combobox ul').hide();
				$('.tooltip>button').attr('aria-expanded','false').closest('.tooltip').find('.tooltip_panel').attr('aria-hidden','true').hide();
				$('.tooltip>button').find('.txt').text('�댁슜�닿린');
			}
		});
	}
	$.fn.ui_close();
	// UI Tab Fixed
	$.fn.tabFixed = function(){
		if($('.ins_product_info').length > 0) {
			var scrollPos = $(window).scrollTop(), $productInfo = $('.ins_product_info'), offsetTop = $productInfo.offset().top;
			(offsetTop < scrollPos) ? $productInfo.addClass('fixed') : $productInfo.removeClass('fixed');
		}
	}
	// UI Calculator Fixed
	$.fn.calculatorFixed = function(){
		if($('.main_quick_calculator').length > 0) {
			var scrollPos = $(window).scrollTop()+$(window).height(), offsetTop = $('#footer').offset().top, $calculator = $('.main_quick_calculator');
			(offsetTop < scrollPos) ? $calculator.removeClass('fixed') : $calculator.addClass('fixed');
		}
	}
	// Window Popup
	$.fn.myWindow = function(myURL, title, myWidth, myHeight) {
		var left = (screen.width - myWidth) / 2;
		var top = (screen.height - myHeight) / 4;
		var myWindow = window.open(myURL, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=' + myWidth + ', height=' + myHeight + ', top=' + top + ', left=' + left);
	}
});