
//저장
function w_chk(){
		if (CheckSpaces(document.getElementById('frm').a_level, '게시판종류')) { return false; }

		if (CheckSpaces(document.getElementById('frm').a_bbsname, '게시판명')) { return false; }

		if($("input[id='a_ftemp1_Y']:checked").length > 0 && CheckSpaces(document.getElementById('frm').a_ftemp1_str, '사용자 필드명1')) { return false; }
		if($("input[id='a_ftemp2_Y']:checked").length > 0 && CheckSpaces(document.getElementById('frm').a_ftemp2_str, '사용자 필드명2')) { return false; }
		if($("input[id='a_ftemp3_Y']:checked").length > 0 && CheckSpaces(document.getElementById('frm').a_ftemp3_str, '사용자 필드명3')) { return false; }
		if($("input[id='a_ftemp4_Y']:checked").length > 0 && CheckSpaces(document.getElementById('frm').a_ftemp4_str, '사용자 필드명4')) { return false; }
		if($("input[id='a_ftemp5_Y']:checked").length > 0 && CheckSpaces(document.getElementById('frm').a_ftemp5_str, '사용자 필드명5')) { return false; }
		if($("input[id='a_ftemp6_Y']:checked").length > 0 && CheckSpaces(document.getElementById('frm').a_ftemp6_str, '사용자 필드명6')) { return false; }
		if($("input[id='a_ftemp7_Y']:checked").length > 0 && CheckSpaces(document.getElementById('frm').a_ftemp7_str, '사용자 필드명7')) { return false; }
		if($("input[id='a_ftemp8_Y']:checked").length > 0 && CheckSpaces(document.getElementById('frm').a_ftemp8_str, '사용자 필드명8')) { return false; }

		if($("input[id='a_upload_Y']:checked").length > 0 && CheckSpaces(document.getElementById('frm').a_nofilesize, '업로드제한용량')) { return false; }
		if($("input[id='a_upload_Y']:checked").length > 0 && document.getElementById('frm').a_nofilesize.value == "0") { alert("업로드제한용량을 0보다 크게 입력해주세요.");return false; }
		
			
		if (CheckSpaces(document.getElementById('frm').a_new, 'new기간')) { return false; }
		if (CheckSpaces(document.getElementById('frm').a_width, '게시판크기')) { return false; }
		if (CheckSpaces(document.getElementById('frm').a_displaysu, '페이지당게시물수')) { return false; }

	try{oEditors.getById["a_writecontent"].exec("UPDATE_CONTENTS_FIELD", []);}catch(e){}
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


function copyBoardStr(Arg){
	obj = eval("document.getElementById('Board"+Arg+"')");
	str = obj.innerHTML;
	window.clipboardData.setData("text",str);
	alert("복사되었습니다.");
}


function moveAll(page){
	if(confirm("순서를 변경하시겠습니까?")){
		document.getElementById("frm_list").action="moveAll.do?"+page;
		document.getElementById("frm_list").submit();
	}
}


function popup_go(url, w, h, s){

	window.open(url,'', 'width='+w+', height='+h+',left=0,top=0,toolbar=no, location=no, directories=no, status=no, menubar=no, resizable=yes, scrollbars='+s+', copyhistory=no');

}

function alevel_change(val){
	with(document.getElementById("frm")){
		if (val=="nninc_photo" || val=="nninc_photowa"){
			if ((a_displaysu.value)%4>0)	a_displaysu.value = "12";
		}else{
			a_displaysu.value = a_displaysu_org.value;
		}
		//답변 선택가능하게
		if (val=="nninc_simple" || val=="nninc_qna"){
			$("input:radio[name=a_reply]").prop("disabled", false);
		}else{
			$("#a_reply_N").prop('checked', true);
			$("input:radio[name=a_reply]").prop("disabled", true);
		}
		
		//사용자필드 선택가능하게
		if (val=="information" || val=="nninc_faq"){
			$(".a_ftemp").prop('checked', true).click();
			$(".a_ftemp_req").prop("checked", false);
			$(".a_ftemp, .a_ftemp_req").prop("disabled", true);
		}else{
			$(".a_ftemp, .a_ftemp_req").prop("disabled", false);
		}
	}
}