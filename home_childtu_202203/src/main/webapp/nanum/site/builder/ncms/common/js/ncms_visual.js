
//저장
function w_chk(){
	
	if (CheckSpaces(document.getElementById('frm').v_subject, '제목')) { return false; }

}

function m_chk(){
	if (CheckSpaces(document.getElementById('frm').v_subject, '제목')) { return false; }
	else if (CheckSpaces(document.getElementById('frm').v_url, '연결주소')) { return false; }
}



//삭제
function d_chk(v_num){
	
	if (confirm('정말 삭제하시겠습니까?'))	{
		
		document.getElementById('frm_list').action = "deleteOk.do";
		document.getElementById('frm_list').v_num.value=v_num;
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
			document.getElementById('frm_list').action = "/ncms/visual/deleteOk.do?"+page;
			document.getElementById('frm_list').status.value = "totdel";
			document.getElementById('frm_list').submit();

	}else{
		
		return ;
	
	}
	
	

	
}


function levelchange(form, idx){
	
	loading_st(1);
	document.getElementById('frm_list').v_chk.value = form;
	document.getElementById('frm_list').v_num.value = idx;
	document.getElementById('frm_list').action = "level_ok.do";
	document.getElementById('frm_list').submit();
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

	if (document.getElementById('frm_list').tot_level_chk.value=="")	{
		alert ("사용여부를 선택해주세요");
		document.getElementById('frm_list').tot_level_chk.focus();
		return ;
	}

		loading_st(1);
		document.getElementById('frm_list').action = "/ncms/visual/levelOk.do?"+page;
		document.getElementById('frm_list').status.value = "totlevel";
		document.getElementById('frm_list').submit();
}


function click_limit(){
	if(document.getElementById("unlimited").checked){
		$("select.limit").attr("disabled", true).customStyle2_disabled();
	}else{
		$("select.limit").attr("disabled", false).customStyle2_org();
	}
}
