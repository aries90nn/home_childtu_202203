
$(function(){
	
	$("#mbook_list div.book_list div.book_img>img").each(function(){
	//$("div.thum img.book_image").each(function(){
		var obj = $(this);
		if(obj.attr("src") == "" || obj.attr("src") == "/nanum/site/img/main/book_noimg.jpg"){
			setBookImage(obj, obj.data("mgc"), obj.data("booktype"), obj.data("regno"));
		}
	});

});

function bookPrev(type){
	var objs = $("li[id^='li_"+type+"']");
	var maxcount = objs.size();
	var nowcount = 0;
	objs.each(function(index, item){
		if($(item).css("display") != "none"){
			nowcount = index+1;
			return false;
		}
	});
	if(nowcount <= 2){
		alert('처음입니다.');
	}else{
		objs.css("display", "none");
		objs.eq(nowcount-2).css("display", "block");
		objs.eq(nowcount-3).css("display", "block");
	}
}

function bookNext(type){
	var objs = $("li[id^='li_"+type+"']");
	var maxcount = objs.size();
	var nowcount = 0;
	objs.each(function(index, item){
		if($(item).css("display") != "none"){
			nowcount = index+1;
			return false;
		}
	});
	if(nowcount >= maxcount-1){
		alert('마지막입니다.');
	}else{
		objs.css("display", "none");
		objs.eq(nowcount+1).css("display", "block");
		objs.eq(nowcount+2).css("display", "block");
	}
}


//KSEARCH API
function setBookImage(obj, manage_code, book_type, reg_no){
	if(manage_code != "" && book_type != "" && reg_no != ""){
		//window.open("/ndls/bookSearch/getBookInfo.do?manage_code="+manage_code+"&book_type="+book_type+"&reckey="+reckey+"&get_field=IMAGE");
		$.ajax({
			url :"/ndls/bookSearch/getBookInfo.do"
			,type:"get"
			,data:"manage_code="+manage_code+"&book_type="+book_type+"&reg_no="+reg_no+"&get_field=IMAGE"
			,dataType:"text"
			,timeout:"3000"
			,success:function(data){
				if(trim(data) == ""){
					data = "/nanum/site/img/main/book_noimg.jpg";
				}else{
					if(data != "http://static.naver.net/book/image/noimg3.gif"){
						obj.attr("src", data);
					}
				}
			}
			,error: function(xhr, status, error){
				//alert("에러발생:"+status+","+error);
				obj.attr("src", "/nanum/site/img/main/book_noimg.jpg");
			}
		});
	}
}

function trim(str){
	return str.replace(/^\s+|\s+$/g, "");
}

