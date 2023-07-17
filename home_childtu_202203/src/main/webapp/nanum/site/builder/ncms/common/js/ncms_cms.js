function click_pagetype(val){
	document.getElementById("tr_T").style.display = "none";
	document.getElementById("tr_B").style.display = "none";
	document.getElementById("tr_B2").style.display = "none";
	document.getElementById("tr_B3").style.display = "none";
	document.getElementById("tr_L").style.display = "none";
	document.getElementById("tr_O").style.display = "none";

	if (val!="X"){
		document.getElementById("tr_"+val+"").style.display = "";
		if(document.getElementById("tr_"+val+"2")!=null) 	document.getElementById("tr_"+val+"2").style.display = "";
		if(document.getElementById("tr_"+val+"3")!=null) 	document.getElementById("tr_"+val+"3").style.display = "";
	}

	if(val=="T"){	
		SEditorCreate();
	}else{
		try{
			oEditors.getById["ct_content"].exec("UPDATE_CONTENTS_FIELD", []);
			document.getElementById("ct_content_org").innerHTML = document.getElementById("frm").ct_content.value;
			document.getElementById("editorArea").innerHTML = "";
			oEditors = [];
		}catch(e){}
	}

}


//수정
function m_chk(num){	
	document.getElementById('frm_list').num.value		= num;
	document.getElementById('frm_list').action	  = "imgOk";
	document.getElementById('frm_list').submit();
}

function SEditorCreate(){

	var objContent = "ct_content";
	str = $("#"+objContent+"_org").html();

	var obj = document.createElement("textarea");
	obj.name="ct_content";
	obj.id="ct_content";
	obj.style.width="100%";
	obj.style.height="700px";
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



//저장
function w_chk2(){
	
	if (CheckSpaces(document.getElementById('frm').ct_name, '메뉴명')) { return false; }
	try{oEditors.getById["ct_content"].exec("UPDATE_CONTENTS_FIELD", []);}catch(e){}

}


function w_chk(){
	

	

	if (CheckSpaces(document.getElementById('frm').ct_name_i, '메뉴명')) { return false; }


	//else if (CheckSpaces(document.getElementById('frm').sc_sitename_en, '영문명')) { return false; }

	//else {document.getElementById('frm').submit(); }

	loading_st(1);

}


//삭제
function d_chk(url){
	
	if (confirm('정말 삭제하시겠습니까?(하위코드포함)'))	{
		
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


function delete_img(){

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
			document.getElementById('frm_list').action = "imgDeleteOk.do";
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


function moveAll(){
	if(confirm("순서를 변경하시겠습니까?")){
		document.getElementById("frm_list").action="moveAll.do";
		document.getElementById("frm_list").submit();
	}
}


function imgResize(img, width, height){//이미지객체, 가로, 세로
		
		var obj = new Image();
		obj.src = img.src;
		//인자로 받는 이미지객체의 가로세로값을 구하면 가끔식 0을 반환하는 버그가 난다.
		//그래서 경로를 받아서 함수내에서 별개의 이미지 객체를 생성해서 가로세로값을 구한다.
		
		var w = obj.width;
		var h = obj.height;
		
		if(width == ''){width = w;}
		if(height == ''){height = h;}
		
		var maxWidth = width;
		var maxHeight = height;
		
		if(w > maxWidth || h > maxHeight){
			if(w > maxWidth){
				nw = maxWidth;
				if(Math.round((h*nw)/w) > maxHeight){
					nh = maxHeight;
					nw = Math.round((w*nh)/h);
				}else{
					nh = Math.round((h*nw)/w);
				}
			}else if(h > maxHeight){
				nh = maxHeight;
				if(Math.round((w*nh)/h) > maxWidth){
					nw = maxWidth;
					nh = Math.round((h*nw)/w);
				}else{
					nw = Math.round((w*nh)/h);
				}
			}
		}
		else{
			nw = w;
			nh = h;
		}
		img.width = nw;
		img.height = nh;
	}




function popup_go(url, w, h, s){

	window.open(url,'', 'width='+w+', height='+h+',left=0,top=0,toolbar=no, location=no, directories=no, status=no, menubar=no, resizable=yes, scrollbars='+s+', copyhistory=no');

}
