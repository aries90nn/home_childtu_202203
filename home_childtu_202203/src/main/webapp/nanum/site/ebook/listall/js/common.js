function focus_on1(str) {
	(str).style.border='1px solid #7D7D7D';
	(str).style.background='#ffffff';
}

function focus_off1(str) {
	(str).style.border='1px solid #cdcdcd';
	(str).style.background='#ffffff';
}



function add() {
	var change = false;
	for (var i = 1; i < 5; i++) {
		var obj = document.getElementById("filediv" + i);
		if (obj != null) {
			if (obj.style.display == "none") {
				obj.style.display = "block";
				change = true;
				break;
			}
		}
	}
	if (change == false) {
		alert("파일을 더 이상 추가 할 수 없습니다.");
	}
}
function del(index) {
	var fileobj = document.getElementById("upload" + index);
	var divobj = document.getElementById("filediv" + index);
	fileobj.outerHTML = fileobj.outerHTML;
	divobj.style.display = "none";
}



//새창
function win_popup2(sun,idx,vscrollbars,vtoolbar,vmenubar,vlocation,vwidth,vheight){	
	window.open('/content/poll/view.do?sun='+sun+'&poq_idx='+idx,'','scrollbars='+vscrollbars+',toolbar='+vtoolbar+',menubar='+vmenubar+',location='+vlocation+',width='+vwidth+',height='+vheight+',location=no');
}
