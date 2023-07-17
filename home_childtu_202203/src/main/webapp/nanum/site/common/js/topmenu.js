function top2menuView(a)
{
	if(this.id){
		eidStr = this.id;
		eidNum=eidStr.substring(eidStr.lastIndexOf("m",eidStr.length)+1,eidStr.length);
		a = parseInt(eidNum);
	}
	top2menuHideAll();
	top1Menu = document.getElementById("top1m"+a);
	top2Menu = document.getElementById("top2m"+a);
	ann = (a<10)? "0"+a : ""+a;
	if(a==0){ 
	}else{
		if (top1Menu){ //top1Menu.parentNode.className="on";
			if(top1Menu.childNodes[0].src) top1Menu.childNodes[0].src="/kor/image/top/top1m"+ann+"on.gif";
			if (top2Menu) { top2Menu.style.display = "inline"; }
		}
	}
}
function top2menuHide(a) 
{
	if(this.id){
		eidStr = this.id;
		eidNum=eidStr.substring(eidStr.lastIndexOf("m",eidStr.length)+1,eidStr.length);
		a = parseInt(eidNum);
	}
	//top2menuHideAll();
	top1Menu = document.getElementById("top1m"+a);
	top2Menu = document.getElementById("top2m"+a);
	top1MenuCurr = document.getElementById("top1m"+d1n);
	top2MenuCurr = document.getElementById("top2m"+d1n);
	ann = (a<10)? "0"+a : ""+a;
	if (top1Menu) { 	//top1Menu.parentNode.className=""	;
		if(top1Menu.childNodes[0].src) top1Menu.childNodes[0].src="/kor/image/top/top1m"+ann+".gif";
		if(top2Menu){ 
			//top2Menu.style.display = "none";
		}
		if(top1MenuCurr){ //top1MenuCurr.parentNode.className="on";
			if(top1MenuCurr.childNodes[0].src) top1MenuCurr.childNodes[0].src="/kor/image/top/top1m"+d1nn+"on.gif";
		}
		if (top2MenuCurr) { top2MenuCurr.style.display = "inline"; }
	}
}
function top2menuHideAll() 
{
	top1menuEl = document.getElementById("top1menu").childNodes;
	for (i=1;i<=11;i++)
	{
		top1Menu = document.getElementById("top1m"+i);
		top2Menu = document.getElementById("top2m"+i);
		inn = (i<10)? "0"+i : ""+i;
		if(top1Menu){ //top1Menu.parentNode.className="";
			if(top1Menu.childNodes[0].src) top1Menu.childNodes[0].src="/kor/image/top/top1m"+inn+".gif";
			//if (top2Menu) { top2Menu.style.display = "none"; }
		}
	}
}
function initTopMenu(d1,d2) {
	d1n=d1; d2n=d2; 
	d1nn = (d1n<10)? "0"+d1n : ""+d1n;
	d2nn = (d2n<10)? "0"+d2n : ""+d2n;
 	top1menuEl = document.getElementById("top1menu").childNodes;
	for (i=1;i<=11;i++) 
	{
		top1Menu = document.getElementById("top1m"+i);
		top2Menu = document.getElementById("top2m"+i);
		if (top1Menu) {
			//var spanEl = document.createElement("span");
			//top1Menu.insertBefore(spanEl,top1Menu.childNodes[0]);
			inn = (i<10)? "0"+i : ""+i;
			
			if(top1Menu.firstChild.tagName != "IMG"){
				top1Menu.innerHTML = '<img src="/kor/image/top/top1m'+inn+'.gif" alt="'+top1Menu.innerHTML+'" />';
			}
			
			top1Menu.style.textIndent = "0";
			top1Menu.onmouseover = top1Menu.onfocus = top2menuView;
			top1Menu.onmouseout = top2menuHide;
			if (top2Menu) {
				//top2Menu.style.display = "none";
				var top2MenuLastChild = top2Menu.lastChild;
				if(top2MenuLastChild){
					while(top2MenuLastChild.nodeName!="LI") top2MenuLastChild = top2MenuLastChild.previousSibling;
					top2MenuLastChild.className = "last";
				}
				var top2MenuFirstChild = top2Menu.firstChild;
				if(top2MenuFirstChild){
					while(top2MenuFirstChild.nodeName!="LI") top2MenuFirstChild = top2MenuFirstChild.nextSibling;
					top2MenuFirstChild.className = "first";
				}
				
				/* 1차 메뉴의 깜빡꺼림 문제 아래두줄을 주석으로 처리하거나 1차 메뉴를 0이 아닌 다른것으로 처리 20121017*/
				//top2Menu.onmouseover = top2Menu.onfocus = top2menuView;
				//top2Menu.onmouseout = top2Menu.onblur = top2menuHide;
				top2MenuAs = top2Menu.getElementsByTagName("a");
				
				/*
				if(top2MenuAs){
					for(j=0;j<top2MenuAs.length;j++){
					top2MenuOns = document.getElementById("top2m"+i+"m"+(j+1));
					innn = (j<10)? "0"+(j+1) : ""+(j+1);
					top2MenuAs[j].innerHTML = '<span>'+top2MenuAs[j].innerHTML+'</span>';
					top2MenuAs[j].innerHTML = '<img src="/kor/image/top/top2m'+inn+innn+'.gif" alt="'+top2MenuAs[j].innerHTML+'" />';
					
					}
				}
				*/
				if(top2MenuAs){
					for(j=0;j<top2MenuAs.length;j++){
						top2MenuOns = document.getElementById("top2m"+i+"m"+(j+1));
						

						innn = (j<10)? "0"+(j+1) : ""+(j+1);
						top2MenuAs[j].innerHTML = '<span>'+top2MenuAs[j].innerHTML+'</span>';
						top2MenuAs[j].innerHTML = '<img src="/kor/image/top/top2m'+inn+innn+'.gif" alt="'+top2MenuAs[j].innerHTML+'" />';
						
						top2MenuOns_ = top2MenuOns.getElementsByTagName("img")[0];
						
						if(d2==(j+1) && d1==i) { 
						}else{
						top2MenuOns_.onmouseover = menuOver;
						top2MenuOns_.onmouseout = menuOut;
						}
					}
				}
				
				
			}
		}
	}

	top2MenuCurrAct = document.getElementById("top2m"+d1+"m"+d2);
	if (top2MenuCurrAct) { top2MenuCurrAct.getElementsByTagName("a")[0].className="on"; 
		//alert(top2MenuCurrAct.getElementsByTagName("img")[0].src );
		if(top2MenuCurrAct.getElementsByTagName("img")[0].src) top2MenuCurrAct.getElementsByTagName("img")[0].src="/kor/image/top/top2m"+d1nn+d2nn+"on.gif";
	}
	top2menuHide(d1);
}


function initD2MenuImg(){
	for (var i=1;i<=5;i++){
		for(var j=1;j<=10;j++){
			d2Menu = document.getElementById("top2m"+i+"m"+j);
			if(d2Menu){ 
				d2MenuImg = d2Menu.getElementsByTagName("img")[0];
				if(d2MenuImg){
					d2MenuImg.onmouseover = menuOver;
					d2MenuImg.onmouseout = menuOut;
				}
			}
		}
	}
}
/***************************************************************************************************/




function over(obj,type){
	//alert(obj+"//"+type);
    if(type) obj.src = obj.src.replace(".gif","_on.gif");
    else  obj.src = obj.src.replace("_on.gif",".gif");
}

function overPng(obj,type){
    if(type) obj.src = obj.src.replace(".png","_on.png");
    else  obj.src = obj.src.replace("_on.png",".png");
}

$(document).ready(function(){
	//Remove outline from links

	$("#menu_area").bind('mouseover focusin', function(){
		//if($("#topmenu_box").is(':animated')) return false;
		$("#menu_navi").stop().animate({opacity:'1',height:'470px'},{queue:false, duration:200, easing: 'easeInSine'});
		$("#rebon").stop().animate({top:'470px'},{queue:false, duration:400, easing: 'easeInSine'});
	});

	$("#menu_area").bind('mouseout focusout', function(){
		//if($("#topmenu_box").is(':animated')) return false;
		$("#menu_navi").stop().animate({height:'61px',opacity:'1'},{duration:200, easing: 'easeInSine'});
		$("#rebon").stop().animate({top:'61px'},{queue:false, duration:400, easing: 'easeInSine'});
	});
	

});