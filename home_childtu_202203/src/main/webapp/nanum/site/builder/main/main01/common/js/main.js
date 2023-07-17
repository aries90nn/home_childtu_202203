$(function(){

	closeListMain();
	$("#cl_category").change(function(){
		closeListMain();
	});
	
	$("#event_lib_anum").change(function(){
		eventListMain();
	});

	$("#notice_lib_anum").change(function(){
		boardListMain();
	});
	

	//searchKeywordMain();
	
	//bestBookListMain();
	//newBookListMain();

});

//인기검색어
function searchKeywordMain(){
	var randkey = makeRandomKey(5);
	$("div.search_btn_wrap>div.tags").load("/main/main/ajaxSearchKeyword.do?sh_days=365&ver="+randkey);
}


//휴관일조회
function closeListMain(){
	var randkey = makeRandomKey(5);
	$("#div_close").load("/main/main/ajaxCloseList.do?ver="+randkey+"&cl_category="+$("#cl_category").val());
}


//새소식조회
function boardListMain(){
	var randkey = makeRandomKey(5);
	$("#ul_noticeList").load("/main/main/ajaxBoardList.do?ver="+randkey+"&a_num="+$("#notice_lib_anum").val());
}

//이달의 행사
function eventListMain(){
	var randkey = makeRandomKey(5);
	$("#ul_eventList").load("/main/main/ajaxEventList.do?ver="+randkey+"&a_num="+$("#event_lib_anum").val());
}




//팝업존
function Popupzone(objid){
	this.move = true;
	this.status = true;
	this.delay = 5000;
	
	var obj = $("#"+objid);	//팝업존목록(li)를 가지고 있는 객체(ul)
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
		obj.parent().find(".pz_play").hide();
	};

	this.stop = function(){
		this.move = false;
		obj.parent().find(".pz_stop").hide();
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


//배너
function rotatecontents(objectid){
    
    this.pagingobject=null;
    this.pagingstart='';
    this.pagingdelimiter='/';
    this.pagingend='';
    this.rotatemethod=null;
    this.maxzindex=1;
    this.speed=5;
    this.autoplay=true;
    this.autoplaytime=10;
 
    this.buttonobject=null;
    this.buttontype='text';
    this.buttononclassname='on';
    this.buttononimagename='';

    
    var object=document.getElementById(objectid);
    var items=new Array(),itemsnum,titles=new Array();
    var btns=new Array(),btnsmover=new Array(),btnsmout=new Array();
    var speed,timer,nowno=0,canimove=true;
    var isie=(navigator.userAgent.toLowerCase().indexOf('msie')!=-1)? true : false;
 
    this.initialize=function(){
        var isfirst=true,removeobjs=new Array(),childs=object.childNodes;
        for(var i=0,j=-1,max=childs.length; i<max; i++){
            if(childs[i].nodeType==1){
                j++;
                if(!isfirst) childs[i].style.display='none';
                items.push(childs[i]);
                if(this.rotatemethod=='overlap' || this.rotatemethod=='slide' || this.rotatemethod=='fadein' || this.rotatemethod=='vertical'){
                    speed=this.speed;
                    object.style.position='relative';
                    with(childs[i].style){
                        position='absolute';
                        left='0px';
                        top='0px';
                        width=object.offsetWidth+'px';
						//2009-08-25
                        height=object.offsetHeight+'px';
                        
                    }
                    if(this.rotatemethod=='fadein'){
                        setopacity(childs[i],(isfirst)? 100 : 0);
                    }
                }
                if(isfirst) isfirst=false;
            }
        }
        itemsnum=items.length;
        if(removeobjs[0]){
            for(var i=0,max=removeobjs.length; i<max; i++) object.removeChild(removeobjs[i]);
        }
 
        if(this.pagingobject) this.pagingobject=document.getElementById(this.pagingobject);
        this.settitlenpaging(nowno);
        object.style.width=object.offsetWidth+'px';
        //alert(object.style.width);
		object.style.height=object.offsetHeight+'px';
        object.style.overflow='hidden';
        object.onmouseover =function(){
            clearTimeout(eval(objectid).autoplaytimer);
        }
        object.onmouseout=function(){
            eval(objectid).autoplayaction();
        }
        if(this.buttonobject){
            this.buttonobject=document.getElementById(this.buttonobject);
            var childs=this.buttonobject.childNodes;
            for(var i=0,j=0,max=childs.length; i<max; i++){
                if(childs[i].nodeType==1){
                    childs[i].myno=j;
                    if(childs[i].onmouseover) btnsmover.push(childs[i].onmouseover);
                    if(childs[i].onmouseout) btnsmout.push(childs[i].onmouseout);
                    btns.push(childs[i]);
                    j++;
                }
            }
            if(items.length!=btns.length) this.buttonobject=null;
            else this.setbtns();
        }
        this.autoplayaction();
    }
 
    this.settitlenpaging=function(no){
       
        if(this.pagingobject) this.pagingobject.innerHTML=this.pagingstart+(no+1)+this.pagingdelimiter+itemsnum+this.pagingend;
    }
 
    this.setzindex=function(no){
        for(var i=0; i<itemsnum; i++){
            if(i==no){
                items[i].style.zIndex=this.maxzindex+3;
                items[i].style.display='';
            }else if(i==nowno){
                items[i].style.zIndex=this.maxzindex+2;
            }else{
                items[i].style.zIndex=this.maxzindex+1;
                items[i].style.display='none';
            }
        }
    }
 
    this.setbtns=function(no){
        if(!no && no!==0) no=nowno;
        for(var i=0; i<itemsnum; i++){
            if(i==no){
                this.setbtnon(btns[i]);
                btns[i].onmouseover=null;
                btns[i].onmouseout=null;
            }else{
                this.setbtnoff(btns[i]);
                btns[i].onmouseover=function(){
                    eval(objectid).setbtnon(this);
                    if(btnsmover[this.myno]) btnsmover[this.myno]();
                }
                btns[i].onmouseout=function(){
                    eval(objectid).setbtnoff(this);
                    if(btnsmout[this.myno]) btnsmout[this.myno]();
                }
            }
        }
    }
 
    this.setbtnon=function(target){
        var classes,exist=false;
        if(this.buttontype=='image'){
            var btnimg=target.getElementsByTagName('img')[0];
            var btnimgexp=btnimg.src.substring(btnimg.src.lastIndexOf('.'));
            if(btnimg.src.indexOf(this.buttononimagename+btnimgexp)==-1) btnimg.src=btnimg.src.replace(btnimgexp,this.buttononimagename+btnimgexp);
        }
        classes=target.className.split(' ');
        for(var i=0; i<classes.length; i++) if(classes[i]==this.buttononclassname) exist=true;
        if(!exist) target.className=target.className+' '+this.buttononclassname;
    }
 
    this.setbtnoff=function(target){
        var classes;
        if(this.buttontype=='image'){
            var btnimg=target.getElementsByTagName('img')[0];
            var btnimgexp=btnimg.src.substring(btnimg.src.lastIndexOf('.'));
            if(btnimg.src.indexOf(this.buttononimagename+btnimgexp)!=-1) btnimg.src=btnimg.src.replace(this.buttononimagename+btnimgexp,btnimgexp);
        }
        if(target.className.indexOf(this.buttononclassname)!=-1){
            if(target.className.indexOf(' ')==-1) target.className=target.className.replace(this.buttononclassname,'');
            else{
                classes=target.className.split(' ');
                for(var i=0; i<classes.length; i++) if(classes[i]==this.buttononclassname) classes[i]='';
                target.className=classes.join(' ');
            }
        }
    }
 
    this.change=function(no){
        no=no-1;
        if(no!=nowno){
            if(this.rotatemethod=='overlap') this.moveaction('overlap',no,(no<nowno)? 'prev' : 'next');
            else if(this.rotatemethod=='slide') this.moveaction('slide',no,(no<nowno)? 'prev' : 'next');
            else if(this.rotatemethod=='vertical') this.moveaction('vertical',no,(no<nowno)? 'prev' : 'next');
            else if(this.rotatemethod=='fadein') this.fadeinaction(no);
            else this.hideitem(no);
            this.settitlenpaging(no);
            if(this.buttonobject) this.setbtns(no);
        }
    }

	this.stop = function() {
		clearTimeout(eval(objectid).autoplaytimer);
		this.hideitem;
	};

	this.play = function() {
		if(!this.autoplay) return;
        this.autoplaytimer=setTimeout(objectid+'.next()',this.autoplaytime*1000);
	};

	this.viewall = function() {
		
//		var viewwrap = document.getElementById('banner_bot');
		var banbtn = document.getElementById('lbtn'); 
//		var banclose = document.getElementById('rbtn');
//		var bbtn = document.getElementById('bbtn');
		if (viewwrap.style.height == '' )
		{	clearTimeout(eval(objectid).autoplaytimer);
//			viewwrap.style.height = '175px';
//			viewwrap.style.backgroundImage = 'url(/open_content/images/main/bg_banner_02.gif)';
			var objList = document.getElementById(objectid);
			var objListul = objList.getElementsByTagName('ul');
//			objList.style.height = '75px';
//			objList.style.paddingTop = '25px';
			for (var i=0; i < objListul.length ; i++)
			{
				objListul[i].style.position = 'static';
				objListul[i].style.display = 'block';
			}	
			banbtn.style.display = 'none';
			banclose.style.display = 'none';
//			bbtn.style.display = 'block';
			object.onmouseover = "null";
			object.onmouseout = "null";

		}else {
			
//			viewwrap.style.height = '';
//			viewwrap.style.backgroundImage = 'url(/open_content/images/main/bg_banner_01.gif)';
			var objList = document.getElementById(objectid);
			var objListul = objList.getElementsByTagName('ul');
//			objList.style.height = '36px';
//			objList.style.paddingTop = '0';
			for (var i=0; i < objListul.length ; i++)
			{
				objListul[i].style.position = 'absolute';
				objListul[i].style.display = '';
			}
			if(!this.autoplay) return;
			this.autoplaytimer=setTimeout(objectid+'.next()',this.autoplaytime*1000);
			banbtn.style.display = '';
			banclose.style.display = 'block'
//			bbtn.style.display = 'none';
		}
	};

    this.prev=function(){
        var prevno=(nowno==0)? itemsnum-1 : nowno-1;
        if(this.rotatemethod=='overlap') this.moveaction('overlap',prevno,'prev');
        else if(this.rotatemethod=='slide') this.moveaction('slide',prevno,'prev');
        else if(this.rotatemethod=='vertical') this.moveaction('vertical',prevno,'prev');
        else if(this.rotatemethod=='fadein') this.fadeinaction(prevno);
        else this.hideitem(prevno);
        this.settitlenpaging(prevno);
    }
 
    this.next=function(){
        var nextno=(nowno==(itemsnum-1))? 0 : nowno+1;
        if(this.rotatemethod=='overlap') this.moveaction('overlap',nextno,'next');
        else if(this.rotatemethod=='slide') this.moveaction('slide',nextno,'next');
        else if(this.rotatemethod=='vertical') this.moveaction('vertical',nextno,'next');
        else if(this.rotatemethod=='fadein') this.fadeinaction(nextno);
        else this.hideitem(nextno);
        this.settitlenpaging(nextno);
        if(this.buttonobject) this.setbtns(nextno);
    }
 
    this.autoplaytimer=null;
    this.autoplayaction=function(){
        if(!this.autoplay) return;
        this.autoplaytimer=setTimeout(objectid+'.next()',this.autoplaytime*1000);
    }
 
    this.hideitem=function(no){
        clearTimeout(this.autoplaytimer);
        for(var i=0; i<itemsnum; i++) items[i].style.display=(i==no)? '' : 'none';
        this.autoplayaction();
        nowno=no;
    }
 
    var getleft=function(no){
        return parseInt(items[no].style.left);
    }
    var setleft=function(no,value){
        items[no].style.left=value+'px';
    }

	var gettop=function(no){
        return parseInt(items[no].style.top);
    }
	var settop=function(no,value){
        items[no].style.top=value+'px';
    }
 
    this.moveaction=function(method,no,direction){
        clearTimeout(this.autoplaytimer);
        if(canimove){
            canimove=false;

            if(method=='slide') setleft(no,(direction=='prev')? -(object.offsetWidth) : object.offsetWidth);
            if(method=='vertical') settop(no,(direction=='prev')? -(object.offsetHeight) : object.offsetHeight);
            
            this.setzindex(no);
            var nowv,now,snow,to=0,mv=0;
            var action=function(){
                clearTimeout(timer);
                now=getleft(no);
                nowv=gettop(no);

                if(method=='slide') snow=getleft(nowno);
				if(method=='vertical') snow=gettop(nowno);
                
                if(now!=to){
                    mv=(now-to)/speed;
                    setleft(no,(direction=='prev')? Math.ceil(now-mv) : Math.floor(now-mv));                  
                    if(method=='slide') setleft(nowno,(direction=='prev')? Math.ceil(snow-mv) : Math.floor(snow-mv));                   
                    setTimeout(action,10);
                }else if (nowv!=to)
                {
					mv=(nowv-to)/speed;
                    settop(no,(direction=='prev')? Math.ceil(nowv-mv) : Math.floor(nowv-mv));           
                    if(method=='vertical') settop(nowno,(direction=='prev')? Math.ceil(snow-mv) : Math.floor(snow-mv));
                    setTimeout(action,10);
                }
				else{
                    eval(objectid+'.autoplayaction()');
                    clearTimeout(timer);
                    canimove=true;
                    nowno=no;
                }
            }
            action();
        }
    }
 
    var getopacity=function(target){
        return (isie)? parseInt(target.style.filter.match(/alpha\(opacity=([0-9]+)\)/)[1]) : target.style.opacity*100;
    }
 
    var setopacity=function(target,value){
        if(isie) target.style.filter='alpha(opacity='+value+')';
        else target.style.opacity=value/100;
    }
 
    this.fadeinaction=function(no){
        clearTimeout(this.autoplaytimer);
        if(canimove){
            canimove=false;
            setopacity(items[no],0);
            this.setzindex(no);
            var nowo,to=0;
            var action=function(){
                clearTimeout(timer);
                nowo=getopacity(items[no]);
                if(nowo!=100){
                    setopacity(items[no],Math.ceil(nowo+(100-nowo)/speed));
                    setTimeout(action,10);
                }else{
                    eval(objectid+'.autoplayaction()');
                    clearTimeout(timer);
                    canimove=true;
                    nowno=no;
                }
            }
            action();
        }
    }
 
}


function setBookImage(obj, key, query){
	$.ajax({
		url :"http://zipcode.nninc.co.kr/book/nv/"
		,type:"get"
		,data:"keyword="+query
		,dataType:"jsonp"
		,timeout:"3000"
		,crossDomain:true
		,success:function(xmlStr){
			//alert(xmlStr);
			try{
				var xmlData = new ActiveXObject("Microsoft.XMLDOM");
				xmlData.loadXML(xmlStr.returnXml);
			}catch(e){
				var xmlData = xmlStr.returnXml;
			}

			var errCode = "";
			var errDesc = "";
			try{
				errCode = $(xmlData).find("errorCode").text();
				errDesc = $(xmlData).find("errorMessage").text();
			}catch(e){}

			if(errCode != ""){
				alert(errCode+"="+errDesc);
			}else{
				try{
					//obj.attr("src", xmlStr.channel.item[0].thumbnail[0].content );
					var img_small = xmlStr.items[0]["image"];
					var img_big = img_small.replace("type=m1", "type=m5");
					var description = xmlStr.items[0]["description"];
					if( img_small != ""){
						obj.attr("src", img_big );
						obj.parent().parent().parent().find(".book_desc").html(description);
					}
				}catch(e){}
			}
		}
		,error: function(xhr,status, error){
			//alert("에러발생"+xhr);
		}
	});
}
