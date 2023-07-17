var vInterval, vDuration = 6000;
var vCurNum, vMaxNum;
$(function(){
	$(".visualbg.img02").hide(); //start

	//메인 비주얼
	vMaxNum = jQuery(".m_visual>.visualbg").size()-1;
	vCurNum = 0;
	if(vMaxNum != 0) {
		vInterval = setInterval("visual()", vDuration);	
	} else {
		jQuery(".controller").css("display", "none");
		jQuery(".controller2").css("display", "none");
	}
	temp_btn_view();


	jQuery(".m_visual>.controller>.btn>a").each(function(q){
		jQuery(this).hover(function(){
			clearInterval(vInterval);
			jQuery(this).find("img").attr("src", jQuery(this).find("img").attr("src").replace(".png", "_on.png"));
		}, function(){
			clearInterval(vInterval);
			vInterval = setInterval("visual()", vDuration);
			jQuery(this).find("img").attr("src", jQuery(this).find("img").attr("src").replace("_on.png", ".png"));
		}).click(function(){
			if(q) {	// 다음
				jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src", jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src").replace("_on.png", ".png"));
				jQuery(".visualbg").eq(vCurNum).stop().fadeOut(1000);
				vCurNum++;
				if(vCurNum > vMaxNum) vCurNum = 0;
				jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src", jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src").replace(".png", "_on.png"));
				jQuery(".visualbg").eq(vCurNum).stop().fadeOut(1000).fadeIn(800);
			} else {	// 이전
				jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src", jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src").replace("_on.png", ".png"));
				jQuery(".visualbg").eq(vCurNum).stop().fadeOut(1000);
				vCurNum--;
				if(vCurNum < 0) vCurNum = vMaxNum;
				jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src", jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src").replace(".png", "_on.png"));
				jQuery(".visualbg").eq(vCurNum).stop().fadeOut(1000).fadeIn(800);
			}

		})
	})
	
	//메인비주얼 동그라미 버튼
	jQuery(".m_visual>.controller2 a").each(function(q){
		jQuery(this).click(function(){
			if(q != vCurNum) {
				jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src", jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src").replace("_on.png", ".png"));
				jQuery(".visualbg").eq(vCurNum).stop().fadeOut(1000);
				vCurNum = q;
				jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src", jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src").replace(".png", "_on.png"));
				jQuery(".visualbg").eq(vCurNum).stop().fadeOut(1000).fadeIn(800);
			}
		}).hover(function(){
			clearInterval(vInterval);
		}, function(){
			clearInterval(vInterval);
			vInterval = setInterval("visual()", vDuration);
		})
	})
})

function visual()
{
	jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src", jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src").replace("_on.png", ".png"));
	jQuery(".visualbg").eq(vCurNum).stop().fadeOut(1000);
	vCurNum++;
	if(vCurNum > vMaxNum) vCurNum = 0;
	jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src", jQuery(".m_visual>.controller2 a").eq(vCurNum).find("img").attr("src").replace(".png", "_on.png"));
	jQuery(".visualbg").eq(vCurNum).stop().fadeOut(1000).fadeIn(800);
	//visual_text_effect(vCurNum);

}

// 첫번째 비주얼에서는 버튼 이미지 다른거 나오게 해달라고...
function temp_btn_view(){
	if (vCurNum==0){
		$(".m_visual>.controller>.btn>a:eq(0)").find("img").attr("src","/nanum/site/img/main/btn_mvisual_prev.png");
		$(".m_visual>.controller>.btn>a:eq(1)").find("img").attr("src","/nanum/site/img/main/btn_mvisual_next.png");
	}else{
		$(".m_visual>.controller>.btn>a:eq(0)").find("img").attr("src","/nanum/site/img/main/btn_mvisual_prev.png");
		$(".m_visual>.controller>.btn>a:eq(1)").find("img").attr("src","/nanum/site/img/main/btn_mvisual_next.png");
	}	
}