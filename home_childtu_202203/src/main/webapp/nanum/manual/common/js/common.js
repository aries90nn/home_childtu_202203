var es_step = "Expo.ease";
$(window).load(function(){

    setTimeout(function(){
        // GNB HOVER 관련
		var $menu_navi_list = $("#menu > ul > li > a");
		var $header = $("#head");

        $menu_navi_list.mouseenter(function(){
            $("#head").addClass("active");
            TweenMax.to($header, .4, {height:250, ease: es_step});
            dimmed(".header_dimmed", 0.3, "block");
        });
        $header.mouseleave(function(){
            TweenMax.to($(this), .4, {height: 76, onComplete:function(){
                $("#head").removeClass("active");
            }});
            dimmed(".header_dimmed", 0, "none");
        });
		
		function dimmed(dimmedName, num, display) {
            TweenMax.to($(dimmedName), 0.3, {opacity: num, display: display, ease: es_step});
        }

    }, 300);
    
})


//메뉴이미지
function menuMouseOver(idx){
	var objImage;
	var objLayer;

	for(i=1 ; i<=9 ; i++){
	try{objImage = document.getElementById("menuimg"+i);}catch(err){objImage=null;}
	try{objLayer = document.getElementById("menu"+i+"01");}catch(err){objLayer=null;}

		if(i == idx){			
			if(objImage){objImage.src = objImage.src.replace(i+".png", i+"_on.png");}
		}else{
			if(objImage){objImage.src = objImage.src.replace(i+"_on.png", i+".png");}
		}
	}
}



function submenuimgMouseOver(idx, subIdx){
	objImage = document.getElementById("submenuimg"+idx+""+subIdx);
	objImage.src = objImage.src.replace("menu"+idx+"0"+subIdx+".", "menu"+idx+"0"+subIdx+"_on.");
}

function submenuimgMouseOut(idx, subIdx){
	objImage = document.getElementById("submenuimg"+idx+""+subIdx);
	objImage.src = objImage.src.replace("menu"+idx+"0"+subIdx+"_on.", "menu"+idx+"0"+subIdx+".");
}



