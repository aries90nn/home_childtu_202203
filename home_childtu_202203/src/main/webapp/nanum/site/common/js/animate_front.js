var UI = (function(){
	var initHandle = function(){

        /**
         * oldBrowser Check
         * */

        //toggleMenu
		//var first = false;
        //$(document).on('click', '.movie-icon .more', function(){
			
            //iconShowMore
        //    if ($('html').hasClass('ie7')) {
        //        if($(this).find('span').hasClass('close')){
        //            $('.movie-icon .more span').removeClass('close');
        //            $('.movie-icon .more span').text('더보기');
        //            $('.movie-icon .second > li').hide();
        //            $('.movie-icon .second').slideUp(400);
        //        }else{
        //            $('.movie-icon .more span').addClass('close');
        //            $('.movie-icon .more span').text('접기');
        //            $('.movie-icon .second').slideDown(400, function(){
        //                $('.movie-icon .second > li').show();
        //            });
        //        }
        //    }else{
        //        if($(this).find('span').hasClass('close')){
        //            $('.movie-icon .more span').removeClass('close');
        //            $('.movie-icon .more span').text('더보기');
        //            $('.movie-icon .second').slideUp(400);
        //        }else{
        //            $('.movie-icon .more span').addClass('close');
        //            $('.movie-icon .more span').text('접기');
        //            $('.movie-icon .second').slideDown(400, function(){
        //                $('.movie-icon .second > li').show();
        //            });
        //            if(!first){
        //                first = true;
        //                TweenMax.from('.ico7', 0.5, {y:-40, opacity:0, delay:0,ease:Back.easeOut.config(1.7)});
        //                TweenMax.from('.ico8', 0.5, {y:-40, opacity:0, delay:0.2,ease:Back.easeOut.config(1.7)});
        //                TweenMax.from('.ico9', 0.5, {y:-40, opacity:0, delay:0.4,ease:Back.easeOut.config(1.7)});
        //                TweenMax.from('.ico10', 0.5, {y:-40, opacity:0, delay:0.6,ease:Back.easeOut.config(1.7)});
        //                TweenMax.from('.ico11', 0.5, {y:-40, opacity:0, delay:0.8,ease:Back.easeOut.config(1.7)});
        //                TweenMax.from('.ico12', 0.5, {y:-40, opacity:0, delay:1,ease:Back.easeOut.config(1.7)});
        //            }
        //        }
        //    }

		//});
	}
	/**
	* UI.Slider
	* */
	var Slider = function(){}

    /**
     * Icon.Move
     * */
    var iconMotion = function(){

        var ico1 = $('.ico1 .m1'), ico2_1 = $('.ico2 .m1'), ico2_2 = $('.ico2 .m2'), ico3 = $('.ico3 .m1'), ico4 = $('.ico4 .m1'),
            ico5_1 = $('.ico5 .m1'), ico5_2 = $('.ico5 .m2'), ico6 = $('.ico6 .m1'), ico7_1 = $('.ico7 .m1'), ico7_2 = $('.ico7 .m2'),
            ico8 = $('.ico8 .m1'), ico9_1 = $('.ico9 .m1'), ico9_2 = $('.ico9 .m2'), ico9_3 = $('.ico9 .m3'), ico10 = $('.ico10 .m1'),
            ico11 = $('.ico11 .m1'), ico12 = $('.ico12 .m1');

        $('.ico1').mouseenter(function(){
            TweenMax.to(ico1, 0.5, {bezier:{type:'quadratic',  values:[/*p1*/{x:0, y:0},{x:5, y:0},{x:5, y:5},  /*p2*/{x:5, y:10},{x:0, y:10},  /*p3*/{x:-5, y:10},{x:-5, y:5},  /*p4*/{x:-5, y:0},{x:0, y:0}]}/*bezier end*/, ease:Linear.easeNone});
        });
        $('.ico2').mouseenter(function(){
            TweenMax.from(ico2_1, 0.5, {x:-20, ease:Back.easeOut.config(1.7)});
            TweenMax.from(ico2_2, 0.5, {x:20, ease:Back.easeOut.config(1.7)});
        });
        $('.ico3').mouseenter(function(){
            TweenMax.from(ico3, 0.7,  {y:15, ease: Elastic.easeOut.config(1, 0.3)});
        });
        $('.ico4').mouseenter(function(){
            TweenMax.from(ico4, 1, { x:-20,ease: Power2.easeOut});
        });

        ico5_2.show();
        TweenMax.to(ico5_2, 0, { rotation:0, opacity:0,  ease: Power2.easeOut});

        var reset = function(){
            TweenMax.to(ico5_1, 0.5, { rotation:0, opacity: 1, ease: Expo.easeOut});
            TweenMax.to(ico5_2, 0.5, { rotation:0, opacity: 0, ease: Expo.easeOut});
        };

        $('.ico5').mouseenter(function(){
            TweenMax.to(ico5_1, 0.5, { rotation:180, opacity:0, ease: Expo.easeOut});
            TweenMax.to(ico5_2, 0.5, { rotation:180, opacity:1,  ease: Expo.easeOut, onComplete:reset});
        });

        $('.ico6').mouseenter(function(){
            TweenMax.from(ico6, 1, { x:-15, ease: Elastic.easeOut.config(1, 0.4)});
        });

        $('.ico7').mouseenter(function(){
            TweenMax.from(ico7_1, 0.5, {x:-20, ease:Back.easeOut.config(1.7)});
            TweenMax.from(ico7_2, 0.5, {x:20, ease:Back.easeOut.config(1.7)});
        });

        $('.ico8').mouseenter(function(){
            //TweenMax.from(ico8, 0.5, {y:-15, ease: Bounce.easeOut});
            TweenMax.from(ico8, 0.5, {clip:"rect(0px 0px 15px 0px)"});
        });


        $('.ico9').mouseenter(function() {
            TweenMax.from(ico9_1, 0.3, {y:-10, ease: Back.easeOut.config(1.7)});
            TweenMax.from(ico9_2, 0.3, {y:-10,opacity:0, delay:0.2, ease: Back.easeOut.config(1.7)});
            TweenMax.from(ico9_3, 0.3, {y:-10,opacity:0, delay:0.4, ease: Back.easeOut.config(1.7)});
        });

        $('.ico10').mouseenter(function(){
            TweenMax.to(ico10, 0.5, {bezier:{type:'quadratic',  values:[/*p1*/{x:0, y:0},{x:5, y:0},{x:5, y:5},  /*p2*/{x:5, y:10},{x:0, y:10},  /*p3*/{x:-5, y:10},{x:-5, y:5},  /*p4*/{x:-5, y:0},{x:0, y:0}]}/*bezier end*/, ease:Linear.easeNone});
        });

        $('.ico11').mouseenter(function(){
            TweenMax.from(ico11, 0.7, { y:-15, ease: Bounce.easeOut});
        });

        $('.ico12').mouseenter(function(){
            TweenMax.from(ico12, 0.7, { y:-15, ease: Circ.easeOut});
        });
    }

    var icoSet =function(){
        //TweenMax.from('.ico1', 0.5, {y:40, opacity:0, delay:0.5,ease:Back.easeOut.config(1.7)});
        //TweenMax.from('.ico2', 0.5, {y:40, opacity:0, delay:0.7,ease:Back.easeOut.config(1.7)});
        //TweenMax.from('.ico3', 0.5, {y:40, opacity:0, delay:0.9,ease:Back.easeOut.config(1.7)});
        //TweenMax.from('.ico4', 0.5, {y:40, opacity:0, delay:1.1,ease:Back.easeOut.config(1.7)});
        //TweenMax.from('.ico5', 0.5, {y:40, opacity:0, delay:1.3,ease:Back.easeOut.config(1.7)});
        //TweenMax.from('.ico6', 0.5, {y:40, opacity:0, delay:1.5,ease:Back.easeOut.config(1.7)});
    };

    var fontSize = function(){
        var section ;
        var factor = 0.95;
        var sizeUp = 0;

        section = $('.mainContent').find('.fontSize');
        function getFontSize(el)
        {
            var fs = parseFloat($(el).css('font-size'), 10);
            if(!el.originalFontSize)el.originalFontSize =fs;
            return fs;
        }
        function setFontSize(fact){
            section.each(function(){
                var newsize = fact ? getFontSize(this) * fact : this.originalFontSize;
                if(newsize) $(this).css('font-size', newsize);
            });
        }

        function resetFont(){
            setFontSize();
        }
        

       
    };

    return {
		initHandle : initHandle,
		Slider : Slider,
		icoSet : icoSet,
		iconMotion : iconMotion,
        fontSize : fontSize
	}
})();
$(function(){   

	$(document).ready(function(){
        if (!$('html').hasClass('ie7')) {
			UI.iconMotion();
			UI.icoSet();
			UI.fontSize();
			

			$('.ico8 .m1').css({
				'clip': 'rect(0px 15px 15px 0px)',
				'width': '18px',
				'height': '15px',
				'position': 'absolute',
				'top': '26px',
				'left': '28px'
			})
		}        
    });	
});


