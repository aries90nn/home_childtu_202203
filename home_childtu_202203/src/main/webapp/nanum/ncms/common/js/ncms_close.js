
function wCheck(obj){
	var w = obj.value;
	$("input.w_"+w).prop("checked", obj.checked);
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
	
	$("input[name='chk']:checkbox").each(function(){
		if(!$(this).prop("disabled")){
			$(this).prop("checked", true);
		}
	});
	
}


function totoff(){
	$("input[name='chk']:checkbox").each(function(){
		if(!$(this).prop("disabled")){
			$(this).prop("checked", false);
		}
	});
}


function requestType(){

	var flag;


	if (confirm('정말 변경하시겠습니까?'))	{
		
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
		document.getElementById('frm_list').submit();

	}else{
		
		return ;
	
	}
	
	

	
}