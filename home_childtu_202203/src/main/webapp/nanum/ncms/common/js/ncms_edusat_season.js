
//저장
function w_chk(){
	var eForm = document.getElementById('frm');
	if (CheckSpaces(eForm.es_name, "기수명") ){ return false; }
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