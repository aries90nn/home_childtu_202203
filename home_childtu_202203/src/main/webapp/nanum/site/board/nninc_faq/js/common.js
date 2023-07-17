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



function showAnswer(answerId){
	var tableBody = document.getElementById("faq_area").getElementsByTagName("table")[0];
	var tBody = tableBody.getElementsByTagName("tbody")[0];
	var trTags = tBody.getElementsByTagName("tr");
	var answerTagId = "answer" + answerId;
	for(var i = 0; i < trTags.length; i++ ) {
		var tagId = "" + trTags[i].id;

		// show & hide
		if(trTags[i].id == answerTagId && trTags[i].style.display != ""){
			trTags[i].style.display = "";
		}else if(tagId.substring(0, 6) == "answer"){
			trTags[i].style.display = "none";
		}

		// question bold
//		var tdTags = trTags[i].getElementsByTagName("td");
//		var questionTitleTagId = "question_title" + answerId;
//		for(var j = 0; j < tdTags.length; j++){
//			var strongTag = tdTags[j].getElementsByTagName("strong");
//			if(tdTags[j].id == questionTitleTagId && (strongTag.length == 0 || strongTag[0].className != "ud")){
//				tdTags[j].innerHTML = "<strong class=\"ud\">" + tdTags[j].innerHTML + "</strong>";
//			}else if(tdTags[j].id.substring(0, 14) == "question_title"){
//				if(strongTag.length > 0 && strongTag[0].className == "ud"){
//					var html = strongTag[0].innerHTML;
//					tdTags[j].innerHTML = html;
//				}
//			}
//		}
	}
}

function showAnswer2(){
	var tableBody = document.getElementById("faq_area").getElementsByTagName("table")[0];
	var tBody = tableBody.getElementsByTagName("tbody")[0];
	var trTags = tBody.getElementsByTagName("tr");

	for(var i = 0; i < trTags.length; i++ ) {
		var tagId = "" + trTags[i].id;

		if(tagId.substring(0, 6) == "answer"){
			trTags[i].style.display = "none";
		}
	}
}
