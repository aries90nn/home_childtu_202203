$(function(){
	var maskHeight = $("#wrapper").height()-$("#foot").height();	 

	$(window).on('load resize', function () {
		//menu_act();
	});

	$(function(){
		//$(".jsMMenuText>a").text($("#jsMMenu>.on>a").text());
		if($("#jsMMenu>.on>a").text() == ""){
			$(".jsMMenuText>a").text($("#cont_head>h2").text());
		}else{
			$(".jsMMenuText>a").text($("#jsMMenu>.on>a").text());
		}
		$(".jsMMenuBtn").click(function(){
			if(document.body.offsetWidth<1025){
				if ($("#sidemenu>ul:visible").length>0){
					$("#sidemenu>ul").stop().slideUp();
				}else{
					$("#sidemenu>ul").stop().slideDown();
				}
			}
		});
	});

});