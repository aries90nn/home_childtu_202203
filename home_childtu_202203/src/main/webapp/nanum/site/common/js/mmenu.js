function depth_layer(num){		 
	var objImage;
	var objLayer;

	for(i=1; i<=11; i++){

		if(num != i){
			try{objImage = document.getElementById("dep"+i);}catch(err){objImage=null;}
			try{objLayer = document.getElementById("dep"+i+"_viwe");}catch(err){objLayer=null;}

			$(objLayer).slideUp();
			$(objImage).removeClass("on");
		}
	}

	try{objImage = document.getElementById("dep"+num);}catch(err){objImage=null;}
	try{objLayer = document.getElementById("dep"+num+"_viwe");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		//if(objLayer){objLayer.style.display = "block";}	
		$(objLayer).slideDown();
		$(objImage).addClass("on");
	}else{
		//if(objLayer){objLayer.style.display = "none";}
		$(objLayer).slideUp();
		$(objImage).removeClass("on");
	}		
}
/*
function depth2(){		 
	var objImage;
	var objLayer;

	try{objImage = document.getElementById("dep2");}catch(err){objImage=null;}
	try{objLayer = document.getElementById("dep2_viwe");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		//if(objLayer){objLayer.style.display = "block";}	
		$(objLayer).slideDown();
		$(objImage).addClass("on");
	}else{
		//if(objLayer){objLayer.style.display = "none";}
		$(objLayer).slideUp();
		$(objImage).removeClass("on");
	}		
}


function depth3(){		 
	var objImage;
	var objLayer;

	try{objImage = document.getElementById("dep3");}catch(err){objImage=null;}
	try{objLayer = document.getElementById("dep3_viwe");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		//if(objLayer){objLayer.style.display = "block";}	
		$(objLayer).slideDown();
		$(objImage).addClass("on");
	}else{
		//if(objLayer){objLayer.style.display = "none";}
		$(objLayer).slideUp();
		$(objImage).removeClass("on");
	}		
}


function depth4(){		 
	var objImage;
	var objLayer;

	try{objImage = document.getElementById("dep4");}catch(err){objImage=null;}
	try{objLayer = document.getElementById("dep4_viwe");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		//if(objLayer){objLayer.style.display = "block";}	
		$(objLayer).slideDown();
		$(objImage).addClass("on");
	}else{
		//if(objLayer){objLayer.style.display = "none";}
		$(objLayer).slideUp();
		$(objImage).removeClass("on");
	}		
}


function depth5(){		 
	var objImage;
	var objLayer;

	try{objImage = document.getElementById("dep5");}catch(err){objImage=null;}
	try{objLayer = document.getElementById("dep5_viwe");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		//if(objLayer){objLayer.style.display = "block";}	
		$(objLayer).slideDown();
		$(objImage).addClass("on");
	}else{
		//if(objLayer){objLayer.style.display = "none";}
		$(objLayer).slideUp();
		$(objImage).removeClass("on");
	}	
}


function depth6(){		 
	var objImage;
	var objLayer;

	try{objImage = document.getElementById("dep6");}catch(err){objImage=null;}
	try{objLayer = document.getElementById("dep6_viwe");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		//if(objLayer){objLayer.style.display = "block";}	
		$(objLayer).slideDown();
		$(objImage).addClass("on");
	}else{
		//if(objLayer){objLayer.style.display = "none";}
		$(objLayer).slideUp();
		$(objImage).removeClass("on");
	}		
}



function depth7(){		 
	var objImage;
	var objLayer;

	try{objImage = document.getElementById("dep7");}catch(err){objImage=null;}
	try{objLayer = document.getElementById("dep7_viwe");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		//if(objLayer){objLayer.style.display = "block";}	
		$(objLayer).slideDown();
		$(objImage).addClass("on");
	}else{
		//if(objLayer){objLayer.style.display = "none";}
		$(objLayer).slideUp();
		$(objImage).removeClass("on");
	}		
}


function depth8(){		 
	var objImage;
	var objLayer;

	try{objImage = document.getElementById("dep8");}catch(err){objImage=null;}
	try{objLayer = document.getElementById("dep8_viwe");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		//if(objLayer){objLayer.style.display = "block";}	
		$(objLayer).slideDown();
		$(objImage).addClass("on");
	}else{
		//if(objLayer){objLayer.style.display = "none";}
		$(objLayer).slideUp();
		$(objImage).removeClass("on");
	}		
}


function depth9(){		 
	var objImage;
	var objLayer;

	try{objImage = document.getElementById("dep9");}catch(err){objImage=null;}
	try{objLayer = document.getElementById("dep9_viwe");}catch(err){objLayer=null;}

	if (objLayer.style.display=="none" || objLayer.style.display=="inline-block"){
		//if(objLayer){objLayer.style.display = "block";}	
		$(objLayer).slideDown();
		$(objImage).addClass("on");
	}else{
		//if(objLayer){objLayer.style.display = "none";}
		$(objLayer).slideUp();
		$(objImage).removeClass("on");
	}		
}
*/