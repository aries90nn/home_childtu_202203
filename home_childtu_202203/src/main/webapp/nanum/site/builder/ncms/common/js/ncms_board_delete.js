function pwd_chk(){
			
			if (CheckSpaces(document.getElementById('frm').b_pwd_chk, '비밀번호')) {return false;}

			loading_st(1);

		}

//삭제
function d_chk(url){
	
	if (confirm('삭제시 데이타베이스에서 영구삭제됩니다.\n\n정말 삭제하시겠습니까?'))	{
		
		loading_st(1);
		page_go1(url);

	}else{
		
		return ;
	
	}

}

//복원
function restore_chk(url){
	
	if (confirm('복원하시겠습니까?'))	{
		
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
			document.getElementById('frm_list').action = "deleteOk.do";
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
	document.getElementById('frm_list').action = "level_ok.do";
	document.getElementById('frm_list').submit();
}


function tot_levelchage(){

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
		document.getElementById('frm_list').action = "/content/board/level_ok.do";
		document.getElementById('frm_list').status.value = "totlevel";
		document.getElementById('frm_list').submit();
}


function win_popup(idx,vscrollbars,vtoolbar,vmenubar,vlocation,vwidth,vheight){		
	window.open('view.asp?idx='+idx,'','scrollbars='+vscrollbars+',toolbar='+vtoolbar+',menubar='+vmenubar+',location='+vlocation+',width='+vwidth+',height='+vheight+',location=no');
}



function bmove(){

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


	document.getElementById('frm_list').action = "?proc_type=move";//"/content/board/move.asp";
//	document.getElementById('frm_list').status.value = "totdel";
	document.getElementById('frm_list').submit();

}

function delete3(){
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

	if (confirm('삭제시 데이타베이스에서 영구삭제됩니다.\n\n정말 삭제하시겠습니까?'))	{
		loading_st(1);
		document.getElementById('frm_list').action = "deleteOk.do";
		document.getElementById('frm_list').status.value = "totdel";
		document.getElementById('frm_list').submit();
	}else{		
		return ;	
	}	
}



function restore(){
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

	if (confirm('정말 복원하시겠습니까?'))	{
		loading_st(1);
		document.getElementById('frm_list').action = "restoreOk.do";
		document.getElementById('frm_list').status.value = "totdel";
		document.getElementById('frm_list').submit();

	}else{
		return ;
	}
}

