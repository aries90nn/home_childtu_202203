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



function setBookImage(obj, query){

	var api_url = "/ndls/api/book.do";
	
	if (query!=""){
		$.ajax({
			url : api_url
			,type:"get"
			,data:"query="+query
			,dataType:"json"
			,timeout:"3000"
			,crossDomain:true
			,success:function(jsonData){
				var errCode = "";
				var errDesc = "";
				try{
					errDesc = jsonData.errorMessage;
					errCode = jsonData.errorCode;
					if (errCode==undefined){
						errCode = "";
						errDesc = "";
					}
				}catch(e){}
				
				if(errCode != ""){
					console.log(errCode+"="+errDesc);
				}else{

					try{					
						setBookInfo( obj, jsonData );
					}catch(e){}
				}
			}
			,error: function(xhr,status, error){
				console.log("에러발생 : "+xhr);
			}
		});

	}
}



function selectBook(num){

	var eForm = document.getElementById("frm");

	eForm.b_keyword.value = $("#book_"+num+"_img").val();
	eForm.b_subject.value = removeTag( $("#book_"+num+"_ti").val() );
	eForm.b_temp2.value = $("#book_"+num+"_ai").val();
	eForm.b_temp3.value = $("#book_"+num+"_pi").val();
	eForm.b_temp4.value = $("#book_"+num+"_yr").val();

	eForm.b_temp5.value = $("#book_"+num+"_callinfo").val();
	eForm.b_temp6.value = $("#book_"+num+"_bsl").val();
	eForm.b_temp7.value = $("#book_"+num+"_sib").val();

	eForm.b_content.value = $("#book_"+num+"_story").val();
	$("#book_list").hide().text("");


	//$("td.content iframe").remove();
	$("td.content #div_editor").html("<textarea name='b_content' id='b_content' style='width:100%; height:250px;'>"+eForm.b_content.value+"</textarea>");
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "b_content",
		sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});

}


function removeTag( str ) {
	return str.replace(/(<([^>]+)>)/gi, "");
}

