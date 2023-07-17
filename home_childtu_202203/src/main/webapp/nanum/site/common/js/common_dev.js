//###공용모듈###
//----------------------------------------------------------------------------------
function ViewCss2(css) {
	document.write("\<link rel=\"stylesheet\" type=\"text/css\" href=\"" + css + "\">");
}



//공백체크
function CheckSpaces(str,m) {
    var flag=true;
    try{
	    var strValue = str.value;
	
	    if (strValue!=" ") {
	       for (var i=0; i < strValue.length; i++) {
	          if (strValue.charAt(i) != " ") {
	             flag=false;
	             break;
	          }
	       }
	    }
		if(flag == true) {
	       alert( m + "을(를) 입력하십시요.");
	       str.focus();
	    }
    }catch(e){
    	flag = false;
    }
    return flag;
}

function CheckRadio(obj, objName){	//체크박스, 라디오버튼 체크
	
	var check = false;
	$(obj).each(function(){
		if($(this).prop("checked")){
			check = true;
			return false;
		}
	});
	if(!check){
		alert(objName+"을(를) 체크하세요");
		$(obj).eq(0).focus();
		return true;
	}else{
		return false;
	}
	
	
	/*
	var trueCnt = 0;
	
	for(i=0;i < obj.length;i++){
		if(obj[i].checked == true){
			trueCnt = trueCnt + 1;
		}
	}
	if(trueCnt == 0){
		alert(objName+"을(를) 체크하세요");
		obj[0].focus();
		return true;
	}else{
		trueCnt = 0;
		return false;
	}
	*/
}

function CheckRadio2(objId, cnt, objName){	//체크박스, 라디오버튼 체크, 사용필드전용
	var trueCnt = 0;
	cnt = eval(cnt);
	for(i=1;i <= cnt;i++){
		obj = document.getElementById(objId+"_"+i);
		if(obj.checked == true){
			trueCnt = trueCnt + 1;
		}
	}
	if(trueCnt == 0){
		obj = document.getElementById(objId+"_1");
		alert(objName+"을(를) 체크하세요");
		obj.focus();
		return true;
	}else{
		trueCnt = 0;
		return false;
	}
}



function Digit(str) {
	var flag = false;

	if (str.value == "" ) {
		alert("금액을 입력하여주세요");
		str.focus();
		flag = true;

	}else {
		for(i=0; i < str.value.length; i++){
			var tmp_chr = str.value.charAt(i);

			if(/^[0-9]/.test(tmp_chr) == false) {
				alert("금액에는 숫자만 입력이 가능합니다");
				str.value="";
				str.focus();
				flag = true;
				break;
			}
		}
	}
	return flag;
}

//숫자 체크  << 함수명 바꿔서 사용하기
//function Number( str, m ) {
//   var flag = false;
//   var Digit= "1234567890";
//  
//   if ( CheckSpaces(str,m) == false) {
//	   for(i=0; i<str.value.length;i++) {
//
//		 if(Digit.indexOf(str.value.substring(i, i+1)) == -1){	
//			alert(m + "은(는) 숫자만 사용하실 수 있습니다.");
//			str.value = "";
//			str.focus();	
//			flag = true;
//			break;
//		  }
//		  
//		}
//   } else {
//	 flag = true; 
//   }
//	return flag;
//   
//}   

function RateDigit(str) {
	var flag = false;

	for(i=0; i < str.value.length; i++){
			var tmp_chr = str.value.charAt(i);
			if(/^[0-9]/.test(tmp_chr) == false) {
				alert("완료율 입력은 숫자만 가능합니다");
				str.value = "";
				str.focus();
				flag = true;
				break;
			}
	}
	return flag;
}

//영문,숫자 체크
function alphaDigit( str, m ) {
   var flag = false;
   var alphaDigit= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
  
   for(i=0; i<str.value.length;i++) {

	 if(alphaDigit.indexOf(str.value.substring(i, i+1)) == -1){	
		alert(m + "은(는) 영문/숫자만 사용하실 수 있습니다.");
		str.focus();	
		flag = true;
		break;
	  }
	  
    }
    return flag;
   
}

function alphaDigitChk( str, m ) {
	var flag = false;
	var flag2 = false;
	var flag3 = false;
	var alphaDigit1= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var alphaDigit2= "1234567890";
	var alphaDigit3= "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";

	for(i=0; i<str.value.length;i++) {
		if(alphaDigit1.indexOf(str.value.substring(i, i+1)) != -1){
			flag = true;
			break;
		}
	}
	if(flag == true){
		for(i=0; i<str.value.length;i++) {
			if(alphaDigit2.indexOf(str.value.substring(i, i+1)) != -1){
				flag2 = true;
				break;
			}
		}

		if(flag2 == true){
			for(i=0; i<str.value.length;i++) {
				if(alphaDigit3.indexOf(str.value.substring(i, i+1)) != -1){
					flag3 = true;
					break;
				}
			}
			if( flag3 == true ){
				return flag3;
			}
		}
	}
	alert(m+"은(는) 영문/숫자/특수문자 조합만 가능합니다.");
	str.focus();
	return false;

}

//길이 체크
function CheckLen( str, start, end ) {
	var flag = false;

	if ( str.value.length < start && str.value.length < end ) {
			alert(start + "~" + end + "자 이내로 입력하여 주십시오");
			str.focus();	
			flag = true;
   }

   return flag;
}
//길이 체크
function CheckLen2( str, start, end, txt ) {
	var flag = false;

	if ( str.value.length < start && str.value.length < end ) {
			alert(txt + "을(를) " + start + "~" + end + "자 이내로 입력하여 주십시오");
			str.focus();	
			flag = true;
   }

   return flag;
}




function jumin_chk(jumin1,jumin2){

	var jstr=(jumin1.value) + (jumin2.value)+"";
	var dummy = new Array("2","3","4","5","6","7","8","9","2","3","4","5");
	var tot;
	var tmp, chksum;


	tmp = jstr.charAt(2);
	tmp = tmp + jstr.charAt(3);
	if(tmp > 12){
			alert("잘못된 주민등록번호입니다.");  
			return true;
	}

	tmp = jstr.charAt(4);
	tmp = tmp + jstr.charAt(5);
	if(tmp > 31){
			alert("잘못된 주민등록번호입니다.");
			return true;
	}

	  tmp = jstr.charAt(6);
	if(tmp > 4){
			alert("잘못된 주민등록번호입니다.");
			return true;
	}

   if(jstr.length != 13){
			alert("주민등록번호 자릿수가 맞지 않습니다.");
	jumin1.focus();
			return true;
	}

	tot = 0;
	tmp = 0;
	chksum = 0;

	for(var i=0; i<12; i++){
			tot = tot + (jstr.charAt(i) * dummy[i]);
	}

	tmp = tot%11;
	chksum = 11 - tmp;
	tmp = chksum%10;

	if( jstr.charAt(12) != tmp ){
			alert("잘못된 주민등록번호입니다.");
			return true;
	}

	return false;

} 




function CheckEqual( str1, str2, m ) {
	var flag = false;

	if ( str1.value !=  str2.value ) {
		alert(m + "가 같지 않습니다");
		str2.value="";
		str2.focus();
		flag = true;
	}
	 return flag;
}


//----------------------------------------------------------------------------------


function number_chk1(){

	var sMoney = event.srcElement.value.replace(/,/g,"");	
		if(isNaN(sMoney)){
		alert("숫자로 입력하세요");        
		event.srcElement.value = "";
		event.srcElement.focus();
		return;
	}

}

function SetNum(obj){			//숫자만 입력
	val=obj.value;
	re=/[^0-9]/gi;
	obj.value=val.replace(re,"");
}



function imgResize(img, width, height){//이미지객체, 가로, 세로

	var obj = new Image();
	obj.src = img.src;
	//인자로 받는 이미지객체의 가로세로값을 구하면 가끔씩 0을 반환하는 버그가 난다.
	//그래서 경로를 받아서 함수내에서 별개의 이미지 객체를 생성해서 가로세로값을 구한다.

	var w = obj.width;
	var h = obj.height;

	if(width == ''){width = w;}
	if(height == ''){height = h;}

	var maxWidth = width;
	var maxHeight = height;

	if(w > maxWidth || h > maxHeight){
		if(w > maxWidth){
			nw = maxWidth;
			if(Math.round((h*nw)/w) > maxHeight){
				nh = maxHeight;
				nw = Math.round((w*nh)/h);
			}else{
				nh = Math.round((h*nw)/w);
			}
		}else if(h > maxHeight){
			nh = maxHeight;
			if(Math.round((w*nh)/h) > maxWidth){
				nw = maxWidth;
				nh = Math.round((h*nw)/w);
			}else{
				nw = Math.round((w*nh)/h);
			}
		}
	}else{
		nw = w;
		nh = h;
	}
	img.width = nw;
	img.height = nh;
}




function zipcode(){
	window.open("/nninc/content/01info/zipcode_search.php?","zipcode","scrollbars=yes,toolbar=no,directories=no,menubar=no,resizable=no,status=no,width=468,height=300'");
}


function page_go1(url){
	location.href = url;
}


function page_go2(url){
	window.open(url);
}


//자주쓰는 메뉴에 추가
function mf_chk(mf_ct_idx){
	
	var url = "/nninc/content/01favorites/write_ok2.php?mm_ct_idx=";

	if (confirm('현재 메뉴를 자주쓰는 메뉴에 추가 하시겠습니까?'))	{
		
		page_go1(url+mf_ct_idx);

	}else{		
		return ;	
	}

}


function loading_st(val){

	if(val==1){		
		//document.getElementById('loading_layer').style.display='block'; 
	}else{
		//document.getElementById('loading_layer').style.display='none'; 
	}
}



//E-mail 체크
function isCorrectEmail(obj) {
	var i;
	var check=0;
	var dot=0;
	var before = "";
	var after = "";
	
	if(obj.value.length == 0){
		alert("이메일을 입력하세요");
		obj.focus();
		return(false);
	}
	for(i=0; i<obj.value.length; i++) {
		if(obj.value.charAt(i) == '@') { check = check + 1; }
		else if(check == 0) { before = before + obj.value.charAt(i); }
	else if(check == 1) { after = after + obj.value.charAt(i); }    }
	if( check >= 2 || check == 0 ) {
		alert("잘못된 전자우편 주소입니다.");
		obj.focus();
		obj.select()
		return(false);
	}
	
	for(i=0; i<before.length; i++) {
		if(!((before.charAt(i) >= 'A' && before.charAt(i) <= 'z') ||
		(before.charAt(i) >= '0' && before.charAt(i) <= '9') ||
		(before.charAt(i) == '_') || (before.charAt(i) == '-'))) {
			alert("잘못된 전자우편 주소입니다.");
			obj.focus();
			obj.select()
			return(false);
		}
	}
	
	for(i=0; i<after.length; i++) {
		if(!((after.charAt(i) >= 'A' && after.charAt(i) <= 'z') ||
		(after.charAt(i) >= '0' && after.charAt(i) <= '9') ||
		(after.charAt(i) == '_') || (after.charAt(i) == '.') ||
		(after.charAt(i) == '-'))) {
			alert("잘못된 전자우편 주소입니다.");
			obj.focus();
			obj.select()
			return(false);
		}
	}
	
	for(i=0; i<after.length; i++) {
		if(after.charAt(i) == '.') {
			dot = dot + 1;
		}
	}
	
	if( dot < 1 ) {
		alert("잘못된 전자우편 주소입니다.");
		obj.focus();
		obj.select()
		return(false);
	}
	return(true);
}



//관리자UI
function dialog_open(subject, action, formdata){
	$("#dialog").dialog({
		autoOpen: false,
		width: 850,
		height: 700,
		modal: true,
		title: subject
	});

	$("#dialogContent").html("<img style='padding-top:200px' src='/nanum/site/common/js/jquery_ui_images/ajax_loding1_fbisk.gif'>");

/*
	$.ajax({
		url: action,
		cashe: false,
		type: "POST",
		data: formdata,
		success:function(html){
			setTimeout(function(){ $("#dialogContent").html(html); }, 500);
		}
	});
	$('#dialog').dialog('open');
*/


	$.ajax({
		type: "POST",
		url: action,
		//data: ({ b_l_num:num }),
		data: formdata,
		dataType:"html",
		async:false,
		beforeSend:function(){//통신시작할때 처리
			$('#dialog').dialog('open');
		},
		complete:function(){//통신완료후 처리
		},
		error:function(request, status, error){
			alert("code:"+status+"\nmessage:"+request.responseText);
		},
		success: function(html){
			setTimeout(function(){ $("#dialogContent").html(html); }, 500);
		}
	});

}

function dialogFrame_open(subject, action, formdata){
	var url = "";
	$("#dialog").dialog({
		autoOpen: false,
		width: 850,
		height: 700,
		modal: true,
		title: subject
	});
	url = action;
	if(formdata != ""){
		url = url+"?"+formdata;
	}
	$("#dialogContent").html("<iframe id='dialogFrame' style='width:100%;height:650px' frameborder='0' src='"+url+"'></iframe>");

	$('#dialog').dialog('open');
}


//모달창 닫기
function dialog_close()
{
	$("#dialogContent").html("");
	$("#dialog").dialog('close');
}




function fn_sns_share(url, sns){
	msg = encodeURIComponent(document.title);
	//url = location.href;
	url = encodeURIComponent(url);

	$.ajax({
		type: "POST",
		url: "/common/file/get_shorturl.jsp", 
		data: "long_url="+url,
		dataType:"json",
		async:true,
		success: function(jData){	
			surl = jData.data.url;
			if (sns=="facebook"){
				window.open("http://www.facebook.com/sharer.php?u=" + surl + "&t=" + msg, 'facebook', '');
			}else if(sns=="twitter"){
				window.open("http://twitter.com/home?status=" + msg + " " + surl , 'facebook', '');
			}
		},error: function( jqXHR, textStatus, errorThrown ) {
			alert( jqXHR.statusText );
		}
	});
}

//개인정보 체크
function infoCheck(obj, objname){
	var tmp_content = "";
	tmp_content = obj.value;
	tmp_content = tmp_content.replace(/\s/g,''); 
	tmp_content = tmp_content.replace(/&nbsp;/g,''); 
	tmp_content = tmp_content.replace(/-/g,''); 
	tmp_content = tmp_content.replace(/\\/g,''); 
	tmp_content = tmp_content.replace(/\//g,''); 
	
	if(tmp_content.search(/\d{6}\-\d{7}/) != -1 || tmp_content.search(/\d{13}/) != -1 || tmp_content.search(/\d{4}\-\d{4}\-\d{4}\-\d{4}/) != -1 || tmp_content.search(/\d{16}/) != -1 || tmp_content.search(/\d{11}/) != -1 || tmp_content.search(/\d{3}\-\d{4}\-\d{4}/) != -1){
		alert(objname+'에 개인정보 노출 위험이 있습니다.\n\n개인정보(주민등록번호,휴대전화번호,신용카드번호)는 등록이 불가합니다.');
		try{obj.focus();}catch(e){}
		return false;
	}else{
		return true;
	}
}



function passCheck() {
	var obj = document.getElementById("m_pwd");
	var m_pwd = obj.value;

	// 길이
	if(!/^[a-zA-Z0-9!@#$%^&*()?_~]{10,30}$/.test(m_pwd))
	{
		alert("비밀번호는 숫자, 영문, 특수문자 조합으로 10자리이상 사용해야 합니다.");
		return false;
	}
	 
	// 영문, 숫자, 특수문자 혼용
	var chk = 0; 
	var eng = false;
	var num = false;
	var emo = false;
	if(m_pwd.search(/[0-9]/g) != -1 ){
		chk ++;
		num = true;
	}
	if(m_pwd.search(/[a-z]/ig)  != -1 ){
		chk ++;
		eng = true;
	}
	if(m_pwd.search(/[!@#$%^&*()?_~]/g)  != -1  ){
		chk ++;
		emo = true;
	}
	if(chk < 3)
	{
		alert("비밀번호는 숫자, 영문, 특수문자 조합으로 10자리이상 사용해야 합니다.");
		obj.focus();
		return false;
	}

	if(!/^[a-zA-Z0-9!@#$%^&*()?_~]{10,30}$/.test(m_pwd)){
		alert("비밀번호는 숫자, 영문, 특수문자 조합으로 10자리이상 사용해야 합니다.");
		obj.focus();
		return false;
	}
	 
	// 동일한 문자/숫자 4이상, 연속된 문자
	if(/(\w)\1\1\1/.test(m_pwd) || isContinuedValue(m_pwd))
	{
		alert("비밀번호에 4자 이상의 연속 또는 반복 문자 및 숫자를 사용하실 수 없습니다.");
		obj.focus();
		return false;
	}
	return true;
}


function passCheckById(obj_name) {
	var obj = document.getElementById(obj_name);
	var m_pwd = obj.value;

	// 길이
	if(!/^[a-zA-Z0-9!@#$%^&*()?_~]{10,30}$/.test(m_pwd))
	{
		alert("비밀번호는 숫자, 영문, 특수문자 조합으로 10자리이상 사용해야 합니다.");
		return false;
	}
	 
	// 영문, 숫자, 특수문자 혼용
	var chk = 0; 
	var eng = false;
	var num = false;
	var emo = false;
	if(m_pwd.search(/[0-9]/g) != -1 ){
		chk ++;
		num = true;
	}
	if(m_pwd.search(/[a-z]/ig)  != -1 ){
		chk ++;
		eng = true;
	}
	if(m_pwd.search(/[!@#$%^&*()?_~]/g)  != -1  ){
		chk ++;
		emo = true;
	}
	if(chk < 3)
	{
		alert("비밀번호는 숫자, 영문, 특수문자 조합으로 10자리이상 사용해야 합니다.");
		obj.focus();
		return false;
	}

	if(!/^[a-zA-Z0-9!@#$%^&*()?_~]{10,30}$/.test(m_pwd)){
		alert("비밀번호는 숫자, 영문, 특수문자 조합으로 10자리이상 사용해야 합니다.");
		obj.focus();
		return false;
	}
	 
	// 동일한 문자/숫자 4이상, 연속된 문자
	if(/(\w)\1\1\1/.test(m_pwd) || isContinuedValue(m_pwd))
	{
		alert("비밀번호에 4자 이상의 연속 또는 반복 문자 및 숫자를 사용하실 수 없습니다.");
		obj.focus();
		return false;
	}
	return true;
}


function isContinuedValue(value) {
	//console.log("value = " + value);
	var intCnt1 = 0;
	var intCnt2 = 0;
	var temp0 = "";
	var temp1 = "";
	var temp2 = "";
	var temp3 = "";

	for (var i = 0; i < value.length-3; i++) {
		//console.log("=========================");
		temp0 = value.charAt(i);
		temp1 = value.charAt(i + 1);
		temp2 = value.charAt(i + 2);
		temp3 = value.charAt(i + 3);

		//console.log(temp0)
		//console.log(temp1)
		//console.log(temp2)
		//console.log(temp3)

		if (temp0.charCodeAt(0) - temp1.charCodeAt(0) == 1
				&& temp1.charCodeAt(0) - temp2.charCodeAt(0) == 1
				&& temp2.charCodeAt(0) - temp3.charCodeAt(0) == 1) {
			intCnt1 = intCnt1 + 1;
		}

		if (temp0.charCodeAt(0) - temp1.charCodeAt(0) == -1
				&& temp1.charCodeAt(0) - temp2.charCodeAt(0) == -1
				&& temp2.charCodeAt(0) - temp3.charCodeAt(0) == -1) {
			intCnt2 = intCnt2 + 1;
		}
		//console.log("=========================");
	}

	//console.log(intCnt1 > 0 || intCnt2 > 0);
	return (intCnt1 > 0 || intCnt2 > 0);
}


function ViewCss(css) {
	document.write("\<link rel=\"stylesheet\" type=\"text/css\" href=\"" + css + "\">");
}

function SetSkin(Skin_Color){ 
	setCookie( "skin_ck", Skin_Color , 1000); 	
	self.location.reload()
}

function setCookie( name, value, expiredays ){
	var todayDate = new Date(); 
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

function getCookie( name ){
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length )
	{
		var y = (x+nameOfCookie.length);
		if ( document.cookie.substring( x, y ) == nameOfCookie ) {
			if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
			endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
		}
		
		x = document.cookie.indexOf( " ", x ) + 1;
		
		if ( x == 0 ) break;
	}
	return "";
}

function facebook(msg,url) {
	var href = "http://www.facebook.com/sharer.php?u=" + encodeURIComponent(url) + "&t=" + encodeURIComponent(msg);
	window.open(href, 'facebook', '');
}
function twitter(msg,url) {
	var href = "http://twitter.com/home?status=" + encodeURIComponent(msg) + " " + encodeURIComponent(url);
	window.open(href, 'twitter', '');
}
function naverBlog(url) {
	var href = "https://blog.naver.com/openapi/share?url=" + encodeURIComponent(url);
	window.open(href, 'naver', 'width=600,height=600');
}


function trim(str){
	if(str == null){str = "";}
	return str.replace(/^\s+|\s+$/g, "");
}

function ReadCookie(name){
	return getCookie(name);
}

function makeRandomKey(count){
	var text = "";
	var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	for( var i=0; i < count; i++ )
		text += possible.charAt(Math.floor(Math.random() * possible.length));
	return text;
}


var defaultzoom = 1;
function resizewindow(type) {
	var v = 1.01;
	var d = document;
	
	if(d.body.style.zoom == 0 && type == "in"){
		d.body.style.zoom = v;
		defaultzoom = defaultzoom + 1;
	}else{
		if(type == "in"){
			d.body.style.zoom *= v;
			defaultzoom = defaultzoom + 1;
			
		}else if(type == "out" && defaultzoom != 1){
			d.body.style.zoom /= v;
			defaultzoom = defaultzoom - 1;
			
		}
	}
}
