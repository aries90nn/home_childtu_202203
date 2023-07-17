
//저장
function w_chk(){
	
	if (CheckSpaces(document.getElementById('frm').poq_question, '문항 제목')) { return false; }
	
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


//수정
function m_chk(poq_idx,poq_chk){
	
	if ($("#poq_question_"+poq_idx).val() == "") { 
		alert("문항 제목을 입력해주세요.");
		$("#poq_question_"+poq_idx).focus();
	}else{
		document.getElementById('frm_list').poq_chk.value		= poq_chk;
		document.getElementById('frm_list').poq_idx.value		= poq_idx;
		
		//document.getElementById('frm_list').encoding = "multipart/form-data";
		document.getElementById('frm_list').action	  = "writeOk.do";
		document.getElementById('frm_list').submit();
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


//문항 보이기/감추기
function a_level_chg(val,id_name){
	var val;
	
	if (val =="1")//주관식
	{
		document.getElementById(id_name).style.display = "";
	}else{//객관식
		document.getElementById(id_name).style.display = "none";
	}
}
