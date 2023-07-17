var over1 = 0,
over2 = 0,
over3 = 0;
var date = new Date();
var clickTime = date.getTime();
var RightMenu = {
    init: function () {
        a();
        b();
        function b() {
            $('.total_submn').find('.open').click(e);
            $('.total_submn').find('.open').find('ul').click(c);
            function c(f) {
                f.stopPropagation();
            }
            //3 depth //
			function e(f) {
				var date = new Date();
				if (date.getTime() -clickTime  < 400) {
					return;
				}
				f.preventDefault();
				f.stopPropagation();
				clickTime = date.getTime();
               if ($(this).attr('isOpen') == 'false') {
                   $(this).attr('isOpen', 'true');
                   $(this).find('>a').addClass('on');
                   $(this).find('.conts_hidden').html('확장메뉴 닫기');
                   $(this).find('ul').css({
                       display: 'block'
                   });
               } else {
                  $(this).attr('isOpen', 'false');
                   $(this).find('>a').removeClass('on');
                   $(this).find('.conts_hidden').html('확장메뉴 펼치기');
                   $(this).find('ul').css({
                       display: 'none'
                   });
               }
			   clickTime = date.getTime()
               rightMenuPosReset();
			   return;
            }
            $('.total_mn').find('li>a').click(d);

			 
			$(".total_submn dl dd > a").click(function () {
				if (!$(this).parent().hasClass("open")) {
					$(".total_submn dl dd a").removeClass("on");
					$(this).addClass("on");
				}
           });
 
 
			
            function d(g) {
                var f = $(this).parent().index();
				
                if ( f == 6) { //메뉴갯수
                    return;
                }
                g.stopPropagation();
                g.preventDefault();
                if (f == 0) {
                    rightMenuiScroll.scrollTo(0, 0, 300);
                } else {
                    rightMenuiScroll.scrollTo(0, - (rightMenuPosArr[f-1]), 300);
                }
            }
        }
        function a() {
            $('.lastdl').height(documentHeight - 107);
        }
    }
};