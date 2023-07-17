function pwd_chk(){
			
			if (CheckSpaces(document.getElementById('frm').b_pwd_chk, '비밀번호')) {return false;}

			loading_st(1);

		}

//삭제
function d_chk(url){
	
	if (confirm('정말 삭제하시겠습니까?'))	{
		
		loading_st(1);
		page_go1(url);

	}else{
		
		return ;
	
	}

}






function checkAll(){

	if(document.getElementById('frm_list').chk_all.value == "") {
		document.getElementById('frm_list').chk_all.value="Y";
		totchk();
	}else {

		document.getElementById('frm_list').chk_all.value="";
		totoff();
	}
}



function totchk(){
	

	for(var i=0; i < document.getElementById('frm_list').elements.length ; i++) { 
		if(document.getElementById('frm_list').elements[i].type=='checkbox') 
			if (  document.getElementById('frm_list').elements[i].disabled != true ){
			document.getElementById('frm_list').elements[i].checked = true;
		}
	}
}


function totoff(){
	for(var i=0; i < document.getElementById('frm_list').elements.length ; i++) { 
		if(document.getElementById('frm_list').elements[i].type=='checkbox') 
			document.getElementById('frm_list').elements[i].checked = false;
		
	}
}



function delete2(){

	var flag;


	if (confirm('정말 삭제하시겠습니까?'))	{
		
		flag = "Empty";
		for (var j = 0; j < document.getElementById('frm_list').elements.length; j++)	{

			if(document.getElementById('frm_list').elements[j].type=='checkbox'){ 
				if (document.getElementById('frm_list').elements[j].checked == true){
					flag = "Not Empty";
				}
			}

		}

		if (flag == "Empty")	{
			alert ("데이터를 선택해 주세요.");
			return ;
		}

			loading_st(1);
			document.getElementById('frm_list').action = "/board/deleteOk.do";
			document.getElementById('frm_list').status.value = "totdel";
			document.getElementById('frm_list').submit();

	}else{
		
		return ;
	
	}

}


function levelchange(form, idx){
	
	loading_st(1);
	document.getElementById('frm_list').b_l_chk.value = form;
	document.getElementById('frm_list').b_l_num.value = idx;
	document.getElementById('frm_list').action = "levelOk.do";
	document.getElementById('frm_list').submit();
}


function tot_levelchage(page){

	var flag;
	
	

	flag = "Empty";
	for (var j = 0; j < document.getElementById('frm_list').elements.length; j++)	{

		if(document.getElementById('frm_list').elements[j].type=='checkbox'){ 
			if (document.getElementById('frm_list').elements[j].checked == true){
				flag = "Not Empty";
			}
		}

	}

	if (flag == "Empty")	{
		alert ("데이터를 선택해 주세요.");
		return ;
	}

	if (document.getElementById('frm_list').tot_level_chk.value=="")	{
		alert ("변경할 등급을 선택해주세요");
		document.getElementById('frm_list').tot_level_chk.focus();
		return ;
	}

		loading_st(1);
		document.getElementById('frm_list').action = "levelOk.do?"+page;
		document.getElementById('frm_list').status.value = "totlevel";
		document.getElementById('frm_list').submit();
}


function win_popup(idx,vscrollbars,vtoolbar,vmenubar,vlocation,vwidth,vheight){		
	window.open('view.php?idx='+idx,'','scrollbars='+vscrollbars+',toolbar='+vtoolbar+',menubar='+vmenubar+',location='+vlocation+',width='+vwidth+',height='+vheight+',location=no');
}


function copyRssFeed(){

	var obj = document.getElementById("FeedRssUrl");
	if (obj.innerText !=undefined){
		str = obj.innerText;
	}else{
		str = obj.textContent;
	}

	var IE=(document.all)?true:false;
	if (IE) {
		if(confirm("이 게시판의 RSS주소를 클립보드에 복사하시겠습니까?")){
			window.clipboardData.setData("Text", str);
			alert("RSS 주소가 복사되었습니다. \n\n다운받은 프로그램을 열고 붙여넣기 하시면 완료됩니다.");
		}
	} else {
		temp = prompt("이 게시판의 RSS주소입니다. Ctrl+C를 눌러 클립보드로 복사하세요", str);
	}	
}


function calcMsg(obj){
	var min_txt = eval( $("#min_txt").val() );
	if(window.event){
		if(event.ctrlKey && window.event.keyCode == 86){alert('붙여넣기 금지'); event.returnValue=false;  return;}
	}
	var str = obj.value;
	var _byte = 0;
	if(str.length != 0){
		for (var i=0; i < str.length; i++) {
			var str2 = str.charAt(i);
			if(str2.match(/[ㄱ-ㅎㅏ-ㅣ]/) || str2.match(/[\!\@\#\$\%\^\&\*\(\)\'\"\?\.\,\<\>\[\]\{\}\`\~\/\;\:\-\\_\=\+]/) || str2.charCodeAt() == 10 || str2.charCodeAt() == 13) continue;
			//str2.charCodeAt(32)
			/*
			if(escape(str2).length > 4){
				_byte += 2;
			}
			else _byte++;
			*/
			//모든 글자는 모두 1바이트로 취급합
			_byte++;
		}
	}
	$("#b_content_len").val(_byte);
	if(_byte >= min_txt){
		_byte = "<span style='color:#0000FF;'>"+_byte+"</span>";
	}
	$("#span_b_content_len").html(_byte+" / "+min_txt+"자");
	
}

function libraryCheck(){
	if($("#b_temp2").val() == "구입도서" || $("#b_temp2").val() == ""){
		$("#b_temp6").css("display", "none");
	}else{
		$("#b_temp6").css("display", "");
	}
}
