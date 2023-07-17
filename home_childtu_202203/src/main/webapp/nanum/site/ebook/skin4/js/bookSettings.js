flippingBook.pages = [
	"/data/ebook/ebook_1/page-001.jpg",
	"/data/ebook/ebook_1/page-002.jpg",
	"/data/ebook/ebook_1/page-003.jpg",
	"/data/ebook/ebook_1/page-004.jpg"
];


flippingBook.contents = [
	[ "목차", 1 ],
	[ "2 페이지", 2 ],
	[ "3 페이지", 3 ],
	[ "4 페이지", 4 ]
];



// define custom book settings here
flippingBook.settings.bookWidth = 826;
flippingBook.settings.bookHeight = 584;
flippingBook.settings.pageBackgroundColor = 0xffffff;	//페이지배경색상
flippingBook.settings.backgroundColor = 0xE6F5E7;		//배경색상
flippingBook.settings.zoomUIColor = 0xC7A097;			//스크롤색상
flippingBook.settings.useCustomCursors = true;
flippingBook.settings.dropShadowEnabled = false,
flippingBook.settings.zoomImageWidth = 992;
flippingBook.settings.zoomImageHeight = 1403;
flippingBook.settings.downloadURL = "";
flippingBook.settings.zoomPath = "/data/ebook/ebook_1/large/";
flippingBook.settings.backgroundImage = "img/bg.gif";
flippingBook.settings.flipSound = "sounds/01.mp3";


// default settings can be found in the flippingbook.js file
flippingBook.create();




function max() {
	self.moveTo(0,0);
	self.resizeTo(screen.availWidth,screen.availHeight);
}



var clear="img/blank.gif" //path to clear.gif

pngfix=function(){var els=document.getElementsByTagName('*');var ip=/\.png/i;var i=els.length;while(i-- >0){var el=els[i];var es=el.style;if(el.src&&el.src.match(ip)&&!es.filter){es.height=el.height;es.width=el.width;es.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+el.src+"',sizingMethod='crop')";el.src=clear;}else{var elb=el.currentStyle.backgroundImage;if(elb.match(ip)){var path=elb.split('"');var rep=(el.currentStyle.backgroundRepeat=='no-repeat')?'crop':'scale';es.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+path[1]+"',sizingMethod='"+rep+"')";es.height=el.clientHeight+'px';es.backgroundImage='none';var elkids=el.getElementsByTagName('*');if (elkids){var j=elkids.length;if(el.currentStyle.position!="absolute")es.position='static';while (j-- >0)if(!elkids[j].style.position)elkids[j].style.position="relative";}}}}}
try{window.attachEvent('onload',pngfix);}catch(e){}