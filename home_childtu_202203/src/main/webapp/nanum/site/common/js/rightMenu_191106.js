var rightMenuOpenFlag = false;
var isTablet = (document.documentElement.clientWidth >= 768) ? true : false;
var isApple = navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i);
var iOS = /(iPad|iPhone|iPod)/g.test(navigator.userAgent);
var documentWidth = document.documentElement.clientWidth;
var documentHeight = document.documentElement.clientHeight;
var rightMenuPosArr = [];
var agent = window.navigator.userAgent;
var fixed = true,
isApple = false,
istouch = false,
rightMenuiScroll,
vers;
function submenuInit() {
}
window.addEventListener('pagehide', function () {
    if (rightMenuOpenFlag != false) {
        $('#wrap').css({
            height: '100%',
            overflow: ''
        });
        TweenMax.to($('.total'), 0, {
            x: documentWidth
        });
        $('#rightMenu').css({
           // display: 'none'
        });
        $('body').css({
            overflow: ''
        });
	   
        $('.total a').attr('tabIndex', - 1);
        rightMenuOpenFlag = false;
    }
}, false);
$(function () {
    setTimeout(function () {
        window.scrollTo(0, 0);
    }, 300);
});
$(document).ready(function () {
    rightMenuInit();
});
function rightMenuInit() {
    RightMenu.init();
    $('.totladim').css({
        display: 'none',
        opacity: 0.8
    });
    $('.total_submn ul').css('display','none');
    $(window).resize(rightMenuResize);
    rightMenuResize();
    $('.total_top').css({
        '-webkit-backface-visibility': 'hidden',
        '-webkit-perspective': '1000'
    });
    $('.total_close').css({
        '-webkit-backface-visibility': 'hidden',
        '-webkit-perspective': '1000'
    });
    $('#rightMenu').css({
        display: 'block',
        'z-index': 9000,
        width: 0,
        overflow: 'hidden'
    });
    $('.total').css({
        transition: 'all 0s ease-in-out',
        transform: 'translateX(' +documentWidth + 'px)'
    });
    $('.totalwrap').css({
        width: documentWidth
    });
    $('.total').css('visibility', 'hidden');
}
var tapFocus = false;
function rightMenuOpen() {
    if (rightMenuOpenFlag == false) {
        documentHeight = document.documentElement.clientHeight;
        documentWidth = document.documentElement.clientWidth;
        $('.total').css('visibility', 'visible');
        $('.util_right').css('display', 'none');
        $('#rightMenu').css({
            display: 'block',
            'z-index': 9000,
            width: documentWidth,
            overflow: 'hidden'
        });
        $('.totalwrap').css({
            width: documentWidth
        });
        $('#wrap').css({
            height: documentHeight - 1,
            overflow: 'hidden'
        });
        $('.totladim').fadeIn(100, function () {
            $('.total').css({
                transition: 'all 0.3s ease-in-out',
                transform: 'translateX(0px)'
            });
            $('.total').bind('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function () {
                a();
            });
            $('.total_close').css('display', 'block');
        });
        if (istouch == false) {
            if (vers != undefined) {
                if (tapFocus) {
                    rightMenuiScroll = new IScroll('.total_submn', {
                        bounce: false
                    });
                } else {
                    rightMenuiScroll = new IScroll('.total_submn', {
                        bounce: false,
                        click: true,
                        disableMouse: true
                    });
                }
            } else {
                if (tapFocus) {
                    if (iOS == true) {
                        rightMenuiScroll = new IScroll('.total_submn', {
                            bounce: false,
                            click: true
                        });
                    } else {
                        rightMenuiScroll = new IScroll('.total_submn', {
                            bounce: false
                        });
                    }
                } else {
                    rightMenuiScroll = new IScroll('.total_submn', {
                        bounce: false,
                        click: true
                    });
                }
            }
            istouch = true;
        }
        rightMenuPosReset();
        rightMenuScroll();
        function a(f) {
            $('.util_right').css('display', 'block');
            $('#wrap').css({
                overflow: 'hidden'
            });
            $('.total').unbind('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend');
            $('.total').off();
            rightMenuOpenFlag = true;
            if (tapFocus && vers == undefined) {
                $('#wrap').css('visibility', 'hidden');
                $('.totalwrap').find('*').filter(focusableElementsString).filter(':visible').first().focus();
            }
        }
    } else {
        if (tapFocus && vers == undefined) {
            $('#wrap').css('visibility', 'visible');
            if (iOS == true) {
                $('.util_right').css('display', 'none');
            }
        }
        $('#wrap').css({
            height: '100%',
            overflow: ''
        });
        $('.totladim').fadeOut(100, function () {
            $('.total').css({
                transition: 'all 0.3s ease-in-out',
                transform: 'translateX(' + documentWidth + 'px)'
            });
            $('.total').bind('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function () {
                c();
            });
        });
    }
    function c() {
        $('.total').unbind('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend');
        $('.total').off();
        $('.total').css('visibility', 'hidden');
        $('#rightMenu').css({
            display: 'block',
            'z-index': 10,
            width: 0,
            overflow: 'hidden'
        });
        rightMenuOpenFlag = false;
        if (tapFocus) {
            if (iOS == true) {
                $('.util_right').delay(300).show(0);
            }
            focusedElementBeforeModal.focus();
            focusedElementBeforeModal = null;
            tapFocus = null;
        }
    }
}
function rightMenuResize() {
    documentWidth = document.documentElement.clientWidth;
    documentHeight = document.documentElement.clientHeight;
    if (rightMenuOpenFlag != false) {
        $('#wrap').css({
            height: documentHeight
        });
        $('#rightMenu').css({
            width: documentWidth
        });
        $('.totalwrap').css({
            width: documentWidth
        });
        if (rightMenuiScroll) {
            rightMenuiScroll.refresh();
        }
    } else {
        $('#wrap').css({
            height: '100%'
        });
        $('#rightMenu').css({
            width: 0
        });
    }
}
function rightMenuScroll() {
    if (over1 !== undefined && over1 !== 0) {
        if (over1 == 1) {
            rightMenuiScroll.scrollTo(0, - (over2 * 41), 0);
        } else {
            rightMenuiScroll.scrollTo(0, - (rightMenuPosArr[over1 - 2] + 30 + (over2 * 41)), 0);
        }
        rightMenuiScroll.refresh();
    }
    function a() {
        var d = rightMenuiScroll.y * - 1;
        $('ul.total_mn > li').removeClass('on');
        for (var c = 0; c < rightMenuPosArr.length;
        c++) {
            if (c == 0) {
                if (b(d, 0, rightMenuPosArr[c])) {
                    $('ul.total_mn > li:eq(' + (c) + ')').addClass('on');
                }
            } else {
                if (b(d, rightMenuPosArr[c - 1], rightMenuPosArr[c])) {
                    $('ul.total_mn > li:eq(' + (c) + ')').addClass('on');
                }
            }
        }
    }
    function b(d, e, c) {
        return d >= e && d < c;
    }
    a();
    if (rightMenuiScroll._events.scroll == null || rightMenuiScroll._events.scroll == undefined) {
        rightMenuiScroll.on('scrollEnd', function (c) {
            a();
        });
    }
}
function rightMenuPosReset() {
    var a = $('.total_submn .subcon dl');
    rightMenuPosArr = [
    ];
    $.each(a, function (c) {
        if (c == 0) {
            rightMenuPosArr.push($(a[c]).height());
        } else {
            rightMenuPosArr.push(rightMenuPosArr[c - 1] + $(a[c]).height());
        }
    });
    var b = a.length - 2;
    $('.lastdl').height(documentHeight - ($('.subcon dl:eq(' + b + ')').height() - 20));
    if (rightMenuiScroll) {
        rightMenuiScroll.refresh();
    }
}
