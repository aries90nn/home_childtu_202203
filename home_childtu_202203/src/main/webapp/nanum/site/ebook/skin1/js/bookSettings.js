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
	[ "4 페이지", 4 ],
	[ "5 페이지", 5 ]
];



// define custom book settings here
flippingBook.settings.bookWidth = 826;
flippingBook.settings.bookHeight = 584;
flippingBook.settings.pageBackgroundColor = 0xffffff;	//페이지배경색상
flippingBook.settings.backgroundColor = 0x4D4D4D;		//배경색상
flippingBook.settings.zoomUIColor = 0xffffff;			//스크롤색상
flippingBook.settings.useCustomCursors = true;
flippingBook.settings.dropShadowEnabled = false,
flippingBook.settings.zoomImageWidth = 992;
flippingBook.settings.zoomImageHeight = 1403;
flippingBook.settings.downloadURL = "";
flippingBook.settings.zoomPath = "/data/ebook/ebook_1/large/";
flippingBook.settings.flipSound = "sounds/01.mp3";


// default settings can be found in the flippingbook.js file
flippingBook.create();




function max() {
	self.moveTo(0,0);
	self.resizeTo(screen.availWidth,screen.availHeight);
}
