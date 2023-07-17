/**
 * 
 */
//저장
function w_chk(){
	
	if (CheckSpaces(document.getElementById('frm').b_l_subject, '제목')) { return false; }
	//loading_st(1);

	try{oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);}catch(e){}
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


function moveAll(page){
	if(confirm("순서를 변경하시겠습니까?")){
		document.getElementById("frm_list").action="moveAll.do?"+page;
		document.getElementById("frm_list").submit();
	}
}


function click_page(num){
	if(num==1){		
		try{
			oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
			document.getElementById("content_org").innerHTML = document.getElementById("frm").content.value;
			document.getElementById("editorArea").innerHTML = "";
			oEditors = [];
		}catch(e){}

		document.getElementById("page_1").style.display = "block";
		document.getElementById("page_2").style.display = "none";
	}else{
		document.getElementById("page_1").style.display = "none";
		document.getElementById("page_2").style.display = "block";

		SEditorCreate();

	}

}


function SEditorCreate(){
	var objContent = "content";
	str = $("#"+objContent+"_org").html();

	var obj = document.createElement("textarea");
	obj.name="content";
	obj.id="content";
	obj.style.width="100%";
	obj.style.height="350px";
	obj.value=str;
	document.getElementById("editorArea").innerHTML="";
	document.getElementById("editorArea").appendChild(obj);

	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: objContent,
		sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});	
}



function click_limit(){
	if(document.getElementById("unlimited").checked){
		$("select.limit").attr("disabled", true).customStyle2_disabled();
	}else{
		$("select.limit").attr("disabled", false).customStyle2_org();
	}
}
