jQuery(function($) {
	//viusal
	var $visual_box = $('.popupzone'),
	$visual = $visual_box.find('.slide_box'),
	$visual_02 = $visual_box.find('.slide_box02'),
	slideCount = null,
	visualLength = $visual.find('.cont_box').length;

	if(visualLength>1){
		$visual_box.find('.auto').addClass('pause').text('정지');
	} else{
		$visual_box.find('.auto').addClass('play').text('재생');
	};
	$visual.on('init', function(event, slick){
		slideCount = slick.slideCount;
		setSlideCount();
		setCurrentSlideNumber(slick.currentSlide);
	});

	$visual.on('beforeChange', function(event, slick, currentSlide, nextSlide){
		setCurrentSlideNumber(nextSlide);
	});

	function setSlideCount() {
		var $el = $('.count_box').find('.total');
		$el.text(slideCount);
	};

	function setCurrentSlideNumber(currentSlide) {
		var $el = $('.count_box').find('.current');
		$el.text(currentSlide + 1);
	};
	$visual_02.slick({
	  arrows: true,
		speed: 980,
	  asNavFor: '.slide_box',
		prevArrow : $visual_box.find('.prev'),
		nextArrow : $visual_box.find('.next')
	});
	$visual.slick({
		swipe : true,
		draggable : true,
		slidesToShow : 1,
		slidesToScroll: 1,
		speed: 1000,
		infinite: true,
		autoplay : true,
		variableWidth: true,
		centerMode: true,
		dots : false,
		asNavFor: '.slide_box02',
		responsive : [
			{
				breakpoint : 800,
				settings: {
					centerMode: false
				}
			},
			{
				breakpoint : 600,
				settings: {
					centerMode: true
				}
			},
			{
				breakpoint : 400,
				settings: {
					centerMode: false,
						slidesToShow : 1,
						variableWidth: false
				}
			}
		]
	});

	$visual_box.find('.auto').click(function(){
		var NowPlaying = $(this).is('.pause');
		if(visualLength>1){
			if(NowPlaying==true){
				$visual.slick('slickPause');
				$(this).removeClass('pause').addClass('play').text('재생');
			} else if(NowPlaying==false){
				$visual.slick('slickPlay');
				$(this).removeClass('play').addClass('pause').text('정지');
			};
		};
	});

	$visual_box.find('.slick-arrow').on('click', function(){
		$visual.slick('slickPause');
		$visual_box.find('.auto').addClass('pause').text('재생');
	});

});

