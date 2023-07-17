function Popupzone(objid){
	this.move = true;
	this.status = true;
	this.delay = 5000;
	
	var obj = $("#"+objid);	//�앹뾽議대ぉ濡�(li)瑜� 媛�吏�怨� �덈뒗 媛앹껜(ul)
	var thisObject;
	
	this.start = function(){
		thisObject = this;
		setTimeout(function(){
			thisObject.popupZoneMove();
		}, this.delay);
		
		obj.find("img.pz_numimg").parent().mouseover(function(){
			thisObject.popupZoneSelect( $(this) );
		}).mouseout(function(){
			thisObject.status = true;
		}).focus(function(){
			thisObject.popupZoneSelect( $(this) );
		});

		//obj.find(">li>div").mouseover(function(){
		obj.find("p.popup_list").mouseover(function(){
			thisObject.status = false;
		}).mouseout(function(){
			thisObject.status = true;
		}).focus(function(){
			thisObject.status = false;
		});
	}
	
	this.popupZoneMove = function(){
		var nowcount;
		//var img_arr = obj.find("img.pz_image").toArray();
		//var img_arr = obj.find(">li>div").toArray();
		var img_arr = obj.find("p.popup_list").toArray();
		if(this.move){

			for(var i=0;i<=img_arr.length-1;i++){
				if( img_arr[i].style.display != "none" ){
					nowcount = i;
					break;
				}
			}
			if(img_arr.length == 0){
				nowcount = 0;
			}else if(img_arr.length-1 == nowcount){
				nowcount = 0;
			}else{
				nowcount++;
			}
			if(this.status){

				this.imageEffect( nowcount );
				
			}
		}
		setTimeout(function(){
			thisObject.popupZoneMove();
		}, this.delay);
		
	}
	
	this.imageEffect = function( num ){
		obj.find("p.popup_list").hide();
		obj.find("p.popup_list").eq(num).show();
		/*
		obj.find(">li>div").hide();
		obj.find(">li>div").eq(num).show();
		*/
/*
		if(num == 0){
			$("#div_img_0").show();
			$("[id^='div_img_'][id!='div_img_0']").fadeOut(500);
		}else{
			$("[id^='div_img_'][id!='div_img_"+num+"']").fadeOut(500);
			$("#div_img_"+num).fadeIn(500);
		}
*/
		this.numberImgChange( num );
	};
	
	this.numberImgChange = function( num ){
		
		obj.find("img.pz_numimg").each(function(){
			var imgsrc = $(this).attr("src").replace("_on", "_off");
			$(this).attr("src", imgsrc);
		});
		
		var numimg = obj.find("img.pz_numimg").eq(num);
		if(numimg.length > 0){
			var imgsrc = numimg.attr("src").replace("_off", "_on");
			numimg.attr("src",imgsrc);
		}
	};
	
	this.popupZoneSelect = function(aobj){
		//var num = obj.find(">li>a").index(aobj);
		var num = obj.find("p.pop_list_btn>a").index(aobj);
		this.status = false;
		this.imageEffect( num );
	};
	
	this.play = function(){
		this.move = true;
		//this.start();
		obj.parent().find(".pz_stop").show();
		obj.parent().find(".pz_play").show();
	};

	this.stop = function(){
		this.move = false;
		obj.parent().find(".pz_stop").show();
		obj.parent().find(".pz_play").show();
	};
	
	this.next = function(){
		var nowcount;
		//var img_arr = obj.find("img.pz_image").toArray();
		//var img_arr = obj.find(">li>div").toArray();
		var img_arr = obj.find("p.popup_list").toArray();

		for(var i=0;i<=img_arr.length-1;i++){
			if( img_arr[i].style.display != "none" ){
				nowcount = i;
				break;
			}
		}
		if(img_arr.length == 0){
			nowcount = 0;
		}else if(img_arr.length-1 == nowcount){
			nowcount = 0;
		}else{
			nowcount++;
		}
		this.imageEffect( nowcount );
		
	};
	
	this.prev = function(){
		var nowcount;
		//var img_arr = obj.find("img.pz_image").toArray();
		//var img_arr = obj.find(">li>div").toArray();
		var img_arr = obj.find("p.popup_list").toArray();

		for(var i=0;i<=img_arr.length-1;i++){
			if( img_arr[i].style.display != "none" ){
				nowcount = i;
				break;
			}
		}
		if(img_arr.length == 0){
			nowcount = 0;
		}else if(nowcount == 0){
			nowcount = img_arr.length-1;
		}else{
			nowcount--;
		}
		this.imageEffect( nowcount );
		
	};
}