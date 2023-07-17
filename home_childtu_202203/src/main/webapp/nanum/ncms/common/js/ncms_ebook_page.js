
//저장
function w_chk(){
	/*
	if (document.getElementById('frm').upload_type.value=="multi"){
		UploadClick();
		return false;
	}else{
		if (CheckSpaces(document.getElementById('frm').b_file1, '이미지')) {return false;}
	}
	*/

	if (CheckSpaces(document.getElementById('frm').b_file1, '이미지')) {return false;}

}



//수정
function m_chk(ebp_idx,ebp_chk){
	
	document.getElementById('frm_list').ebp_chk.value		= ebp_chk;
	document.getElementById('frm_list').ebp_idx.value		= ebp_idx;
	
	//document.getElementById('frm_list').encoding = "multipart/form-data";
	document.getElementById('frm_list').action	  = "modifyOk.do";
	document.getElementById('frm_list').submit();


	loading_st(1);

}


//삭제
function d_chk(url){
	
	if (confirm('정말 삭제하시겠습니까?'))	{
		
		loading_st(1);
		page_go1(url);

	}else{
		
//		return false;
	
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
			document.getElementById('frm_list').action = "deleteOk.do";
			document.getElementById('frm_list').status.value = "totdel";
			document.getElementById('frm_list').submit();

	}else{
		
		return ;
	
	}
	
	

	
}


function levelchange(form, idx){
	
	loading_st(1);
	document.getElementById('frm_list').eb_chk.value = form;
	document.getElementById('frm_list').eb_idx.value = idx;
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



function moveAll(){
	if(confirm("순서를 변경하시겠습니까?")){
		document.getElementById("frm_list").action="moveAll.do";
		document.getElementById("frm_list").submit();
	}
}
