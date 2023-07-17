function ajaxIdCheck(){
	var eForm = document.getElementById('frm');
	
	if (CheckSpaces(eForm.m_id, '아이디')) { return false; }
	if( !textRule1(eForm.m_id, "아이디") ){return false;}
	if (CheckLen2(eForm.m_id, '8', '15', '아이디')) { return false; }
	$.get("/site/member/ajaxIdCheck.do?v="+makeRandomKey(5)+"&m_id="+eForm.m_id.value,function(data,status){
		//alert("Data: " + data + "\nStatus: " + status);
		//eval(callback+"("+data+")");
		
		if(data == "0"){
			alert("["+eForm.m_id.value+"]는 사용할 수 있는 아이디 입니다.");
			eForm.idchk.value = "Y";
		}else if(data == "1"){
			alert("["+eForm.m_id.value+"]는 이미 사용중인 아이디 입니다.");
			idCheckReset();
		}else{
			alert("아이디조회 실패.\n\n증상이 지속되면 담당자에게 문의하시기 바랍니다.");
			idCheckReset();
		}
	});
}

function ajaxEmailCheck(){
	var eForm = document.getElementById('frm');
	
	if (CheckSpaces(eForm.m_email, '이메일')) { return false; }
	if(!email_chk(eForm.m_email, "이메일") ){return false;}
	
	$.get("/site/member/ajaxEmailCheck.do?v="+makeRandomKey(5)+"&m_email="+eForm.m_email.value,function(data,status){
		//alert("Data: " + data + "\nStatus: " + status);
		//eval(callback+"("+data+")");
		
		if(data == "0"){
			alert("["+eForm.m_email.value+"]는 사용할 수 있는 이메일 입니다.");
			eForm.emailchk.value = "Y";
		}else if(data == "1"){
			alert("["+eForm.m_email.value+"]는 이미 사용중인 이메일 입니다.");
			emailCheckReset();
		}else{
			alert("이메일조회 실패.\n\n증상이 지속되면 담당자에게 문의하시기 바랍니다.");
			emailCheckReset();
		}
	});
	
}

function email_chk(element, title) {
	if(!/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i.test(element.value)) {
		alert(title+'을(를) 정확하게 작성해주세요.');
		element.focus();
		return false;
	}
	return true;
}


function idCheckReset(){
	var eForm = document.getElementById("frm");
	eForm.idchk.value = "N";
}
function emailCheckReset(){
	var eForm = document.getElementById("frm");
	eForm.emailchk.value = "N";
}


//영문,숫자 체크	ex) pwd입력
function textRule1( str, m ) {
	var flag = false;
	var flag2 = false;
	var alphaDigit1= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var alphaDigit2= "1234567890";

	for(i=0; i<str.value.length;i++) {
		if(alphaDigit1.indexOf(str.value.substring(i, i+1)) == -1){
			flag = true;
			break;
		}
	}
	if(flag == true){
		for(i=0; i<str.value.length;i++) {
			if(alphaDigit2.indexOf(str.value.substring(i, i+1)) == -1){
				flag2 = true;
				break;
			}
		}
		if(flag2 == false){
			alert(m+"은(는) 영문/숫자 조합만 가능합니다.");
			str.focus();
		}
		return flag2;
	}else{
		alert(m+"은(는) 영문/숫자 조합만 가능합니다.");
		str.focus();
		return flag;
	}
}

//영문,숫자, 특수문자 체크	ex) pwd입력
function textRule2( str, m ) {
	var flag = false;
	var flag2 = false;
	var flag3 = false;
	var alphaDigit1= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var alphaDigit2= "1234567890";
	//var alphaDigit3= "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
	var alphaDigit3= "~`!@#$%^*()-_=+\\|[{]},./?";	// &';:<> 사용불가

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


//특정문자 입력방지
function checkNumber(){
	var objEv = event.srcElement;
	var num ="&';:<>";    //입력을 막을 특수문자 기재.
	event.returnValue = true;

	for (var i=0;i<objEv.value.length;i++){
		if(-1 != num.indexOf(objEv.value.charAt(i)))
			event.returnValue = false;
	}

	if (!event.returnValue) {
		alert(num+" 는 입력하실 수 없습니다.");
		objEv.value="";
	}
}


//한글방지
function hanCancel(obj){
	//좌우 방향키, 백스페이스, 딜리트, 탭키에 대한 예외
	if(event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39
	|| event.keyCode == 46 ) return;
	//obj.value = obj.value.replace(/[\a-zㄱ-ㅎㅏ-ㅣ가-힣]/g, '');
	obj.value = obj.value.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, '');
}



function sample6_execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var fullAddr = ''; // 최종 주소 변수
			var extraAddr = ''; // 조합형 주소 변수

			// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				fullAddr = data.roadAddress;

			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				fullAddr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 조합한다.
			if(data.userSelectedType === 'R'){
				//법정동명이 있을 경우 추가한다.
				if(data.bname !== ''){
					extraAddr += data.bname;
				}
				// 건물명이 있을 경우 추가한다.
				if(data.buildingName !== ''){
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
				//fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
			}
			

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('m_zipcode').value = data.zonecode; //5자리 새우편번호 사용
			document.getElementById('m_addr1').value = fullAddr;
			if(extraAddr != ""){
				document.getElementById('m_addr2').value = "("+extraAddr+")";
			}

			// 커서를 상세주소 필드로 이동한다.
			document.getElementById('m_addr2').focus();
		}
	}).open();
}