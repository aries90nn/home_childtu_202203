(function($, win, doc) {
    'use strict';
    win.commonJs = {};
    var commonJs = win.commonJs;
    var $win = $(win);
    var $doc = $(document);

    $.easing.easeOutQuad = function(a, b, c, d, e) {
        return -d * (b /= e) * (b - 2) + c
    };
    commonJs.tabOut = {
        set: function($wrap, name, callBack) {
            var iframe = $wrap.find('iframe'); 
            $doc.off('keydown.tabOut' + name).on('keydown.tabOut' + name, function(e) {
            });
        },
        off: function(name) {
            $doc.off('keydown.tabOut' + name);
        }
    }

    /* 전체메뉴 열었을 때 전체화면 스크롤 안생기게 */
    commonJs.smoothWheel = {
        scrollBlock: false,
        dimmSet: function(ck) {
            if (ck) {
                var scrollTop = parseInt($(document).scrollTop());
                $('body').addClass("hiddenBody");
                $('body').css({
                })
                this.scrollBlock = true;
				$('#wrapper').css('height','auto');
				$('#wrapper').css('overflow','hidden');
				$('#wrapper').css('position','fixed');
            } else {
                var scrollTop = -parseInt($('body').css('marginTop'));
                $('body').removeClass("hiddenBody");
                $('body').css({
                });
                this.scrollBlock = false;
				$('#wrapper').css('height','auto');
				$('#wrapper').css('overflow','auto');
				$('#wrapper').css('position','relative');
            }
        },
    }
    commonJs.allMenu = function() {
        $('.btnViewAll').off('click').on('click', function() {    
            if($('.gnb ul.open').length > 0){
                $('.gnb ul.open').siblings('a').trigger('click');
            }
            $('.viewAllMenu').addClass('open');
            $('.viewAllMenu').next('.dimmed').stop().fadeIn();
            commonJs.smoothWheel.dimmSet(true);            
            commonJs.tabOut.set($('.viewAllMenu'),'gnbMenu',function(){
                $('.btnAllClose').click();
            });
            //전체메뉴 효과
            var obj = {};
            obj.x = 100;
            $(obj).stop().animate({
                x: 0
            }, {
                duration: 500,
                easing: 'easeOutQuart',
                step: function(now) {
                    var xPos = now
                    $('.viewAllMenu .con').css('transform', 'translate(' + xPos + '%, 0px)')
                    $('.viewAllMenu').addClass('open');
                },
                complete: function(){
                    $('.logo').focus();
                }
            });
            //전체메뉴 열기
            $('.viewAllMenu .con nav ul >li').each(function(idx) {
                var txt = {};
                txt.x = 20 * (idx + 1);
                $(txt).stop().delay(100 + (idx * 20)).animate({
                    x: 0
                }, {
                    duration: 500,
                    easing: 'easeOutQuart',
                    step: function(now, fx) {
                        var xPos = now;
                        $('.viewAllMenu  nav li span').eq(idx).css('transform', 'translate(' + xPos + '%, 0px)')
                    }
                });
            });
        })
        //전체메뉴 닫기
        $('.btnAllClose, .dimmed').off('click').on('click', function() {
            $('.viewAllMenu').next('.dimmed').stop().fadeOut();
            commonJs.tabOut.off('gnbMenu');
            var obj = {};
            obj.x = 0;
            $(obj).stop().animate({
                x: 100
            }, {
                duration: 500,
                easing: 'easeOutQuart',
                step: function(now) {
                    var xPos = now
                    $('.viewAllMenu .con').css('transform', 'translate(' + xPos + '%, 0px)')
                    $('.viewAllMenu .btnAllClose').css('transform', 'translate(' + xPos + '%, 0px)')
                },
                complete: function() {
                    $('.viewAllMenu').stop().removeClass('open')
                    $('.viewAllMenu').css('transform', '');
                    $('.viewAllMenu .btnAllClose').css('transform', '');
                    $('.viewAllMenu  nav li span').each(function(idx) {
                        $(this).css('transform', 'translate(' + idx * 20 + '%, 0)');
                    })
                    commonJs.smoothWheel.dimmSet(false);
                }
            })
        });
    };
 
    $win.one('load', function() {
        commonJs.allMenu();
    });
})(jQuery, window, document);

/******************************************************************************/
/*********************************** EASING ***********************************/
/******************************************************************************/

(function() {

    // Based on easing equations from Robert Penner (http://www.robertpenner.com/easing)

    var baseEasings = {};

    $.each(["Quad", "Cubic", "Quart", "Quint", "Expo"], function(i, name) {
        baseEasings[name] = function(p) {
            return Math.pow(p, i + 2);
        };
    });

    $.each(baseEasings, function(name, easeIn) {
        $.easing["easeIn" + name] = easeIn;
        $.easing["easeOut" + name] = function(p) {
            return 1 - easeIn(1 - p);
        };
        $.easing["easeInOut" + name] = function(p) {
            return p < 0.5 ?
                easeIn(p * 2) / 2 :
                1 - easeIn(p * -2 + 2) / 2;
        };
    });

})();

