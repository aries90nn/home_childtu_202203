/**
 * 
 */
//저장
function w_chk(){
	
	

	if (CheckSpaces(document.getElementById('frm').sc_sitename, '사이트명')) { return ; }

	else if (CheckSpaces(document.getElementById('frm').sc_sitename_en, '영문명')) { return ; }
	//else if (CheckSpaces(document.getElementById('frm').b_l_chk, '사용유무')) { return; }

	//else {document.getElementById('frm').submit(); }
	

	else {
		loading_st(1);
		document.getElementById('frm').submit();
	}
	
}



function date_chk(){
	

	//alert(document.getElementById('frm').sc_date_chk.checked);


	if ( document.getElementById('frm').sc_date_chk.checked == true) {  //체크되면
		document.getElementById('frm').sc_hdate1_y.disabled="disabled";
		document.getElementById('frm').sc_hdate1_m.disabled="disabled";
		document.getElementById('frm').sc_hdate1_d.disabled="disabled";

		document.getElementById('frm').sc_hdate2_y.disabled="disabled";
		document.getElementById('frm').sc_hdate2_m.disabled="disabled";
		document.getElementById('frm').sc_hdate2_d.disabled="disabled";

	}else{

		document.getElementById('frm').sc_hdate1_y.disabled="";
		document.getElementById('frm').sc_hdate1_m.disabled="";
		document.getElementById('frm').sc_hdate1_d.disabled="";

		document.getElementById('frm').sc_hdate2_y.disabled="";
		document.getElementById('frm').sc_hdate2_m.disabled="";
		document.getElementById('frm').sc_hdate2_d.disabled="";

	}
	
}

function hdd_chk(){

	if ( document.getElementById('frm').sc_hdd_chk.checked == true) {  //체크되면
		document.getElementById('frm').sc_hdd.disabled = "disabled";

	}else{

		document.getElementById('frm').sc_hdd.disabled = "";

	}
	
}

