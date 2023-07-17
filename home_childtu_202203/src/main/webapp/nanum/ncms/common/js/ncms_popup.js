//저장
function w_chk2(){
	
	var eForm = document.getElementById('frm');
	
	if( CheckSpaces(document.getElementById('frm').subject, '제목') ) { return false; }
	
	try{oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);}catch(e){}
	
	loading_st(1);
}


//삭제
function d_chk(idx){
	
	if (confirm('정말 삭제하시겠습니까?'))	{
		
		document.getElementById('frm_list').action = "deleteOk.do";
		document.getElementById('frm_list').idx.value=idx;
		document.getElementById('frm_list').submit();
		
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



function delete2(page){

	var flag;
	
	page = encodeURI(page);


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
			document.getElementById('frm_list').action = "deleteOk.do?"+page;
			document.getElementById('frm_list').status.value = "totdel";
			document.getElementById('frm_list').submit();

	}else{
		
		return ;
	
	}
	
	

	
}




function tot_levelchage(page){

	var flag;
	
	page = encodeURI(page);
	
	//alert(page);
	
	

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

	if (document.getElementById('frm_list').tot_w_chk.value=="")	{
		alert ("사용여부를 선택해주세요");
		document.getElementById('frm_list').tot_w_chk.focus();
		return ;
	}

		loading_st(1);
		document.getElementById('frm_list').action = "levelOk.do?"+page;
		document.getElementById('frm_list').status.value = "totlevel";
		document.getElementById('frm_list').submit();
}


function win_popup(idx,vscrollbars,vtoolbar,vmenubar,vlocation,vwidth,vheight){		
	window.open('/popup/view.do?idx='+idx,'','scrollbars='+vscrollbars+',toolbar='+vtoolbar+',menubar='+vmenubar+',location='+vlocation+',width='+vwidth+',height='+vheight+',location=no');
}

function click_limit(){
	if(document.getElementById("unlimited").checked){
		$("select.limit").attr("disabled", true).customStyle2_disabled();
	}else{
		$("select.limit").attr("disabled", false).customStyle2_org();
	}
}

