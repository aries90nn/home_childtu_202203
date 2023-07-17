
//저장
function w_chk(){
	var eForm = document.getElementById('frm');

//	try{
		//eForm.edu_week.value = "";
		/*
		for(var i=0;i<=9;i++){
			if(i < 7 || i == 9){
				var obj = document.getElementById("edu_week_"+i);
				if( obj.checked ){
					//if(eForm.edu_week.value != ""){eForm.edu_week.value += ",";}
					//eForm.edu_week.value += obj.value;
				}
			}
		}
		*/
//	}catch(e){}
	/*
	if( eForm.edu_temp4.checked ){
		try{
			var edu_field2_yn_r = document.getElementById("edu_field2_yn_r");
			if( !edu_field2_yn_r.checked ){
				edu_field2_yn_r.checked = true;
				edu_field2_yn_r.focus();
				alert("참가대상제한기능이 활성화되어 생년월일입력을 필수로 수정하였습니다.");
			}
		}catch(e){}
	}

	try{
		if (CheckSpaces(eForm.ct_idx, '도서관')) { return false; }
	}catch(e){}
	*/
	try{
		if (CheckSpaces(eForm.ct_idx2, '구분')) { return false; }
	}catch(e){}
		if (CheckSpaces(eForm.edu_subject, '프로그램명')) { return false; }
		//if (CheckSpaces(eForm.edu_temp1, '최대신청수량')) { return false; }
		//if (CheckRadio(eForm.edu_week_chk, "강의요일") ){ return false; }
		/*
		if(eForm.edu_temp4.checked){
			if (CheckSpaces(eForm.edu_temp5_s_y, "참가가능생년월일 연도1") ){ return false; }
			if (CheckSpaces(eForm.edu_temp5_s_m, "참가가능생년월일 월1") ){ return false; }
			if (CheckSpaces(eForm.edu_temp5_s_d, "참가가능생년월일 일1") ){ return false; }
			if (CheckSpaces(eForm.edu_temp5_e_y, "참가가능생년월일 연도2") ){ return false; }
			if (CheckSpaces(eForm.edu_temp5_e_m, "참가가능생년월일 월2") ){ return false; }
			if (CheckSpaces(eForm.edu_temp5_e_d, "참가가능생년월일 일2") ){ return false; }
		}
		if (CheckSpaces(eForm.edu_inwon, "참가인원") ){ return false; }
		*/
//	}catch(e){}
	/*
	try{
		var chk = false;
		for(var i=0;i<=2;i++){
			if( document.getElementById('edu_receive_type_'+i).checked == true ){
				chk = true;
				break;
			}
		}
		if(!chk){
			alert("접수유형을 1개 이상 선택하세요.");
			document.getElementById('edu_receive_type_0').focus();
			return false;
		}
	}catch(e){}
	*/
	try{oEditors.getById["edu_content"].exec("UPDATE_CONTENTS_FIELD", []);}catch(e){}
	loading_st(1);
}


//삭제
function d_chk(url){
	
	if (confirm('정말 삭제하시겠습니까?'))	{
		
		loading_st(1);
		page_go1(url);

	}else{
		
		return false;
	
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
		document.getElementById('frm_list').action = "levelOk.do";
		document.getElementById('frm_list').status.value = "totlevel";
		document.getElementById('frm_list').submit();
}

function tot_levelchage2(){

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
		alert ("변경할 상태를 선택해주세요");
		document.getElementById('frm_list').tot_level_chk.focus();
		return ;
	}

		loading_st(1);
		document.getElementById('frm_list').action = "reqlevelOk.do";
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



function moveAll(){
	if(confirm("순서를 변경하시겠습니까?")){
		document.getElementById("frm_list").action="moveAll.do";
		document.getElementById("frm_list").submit();
	}
}


function SEditorCreate(){
	var objContent = "edu_content";
	str = $("#"+objContent+"_org").html();

	var obj = document.createElement("textarea");
	obj.name="edu_content";
	obj.id="edu_content";
	obj.style.width="100%";
	obj.style.height="450px";
	obj.value=str;
	document.getElementById("contentArea").innerHTML="";
	document.getElementById("contentArea").appendChild(obj);

	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: objContent,
		sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});	
}

//팝업창에서 호출
function ParentSEditorCreate(id){
	var objContent = "edu_content";
	str = document.getElementById(id).value;

	var obj = opener.document.createElement("textarea");
	obj.name="edu_content";
	obj.id="edu_content";
	obj.style.width="100%";
	obj.style.height="450px";
	obj.value=str;
	opener.document.getElementById("contentArea").innerHTML="";
	opener.document.getElementById("contentArea").appendChild(obj);
	
	//에디터생성
	opener.editorCreate(objContent);
}

function editorCreate(objContent){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: objContent,
		sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});	
}

function ptcp_yn_chk(val){
	if (val=="N"){
		$("#edu_ptcp_cnt").val("1").prop("disabled",true);
	}else{
		$("#edu_ptcp_cnt").prop("disabled",false);		
	}

}