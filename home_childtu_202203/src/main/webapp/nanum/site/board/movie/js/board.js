

//삭제
function d_chk(url){
	
	if (confirm('정말 삭제하시겠습니까?'))	{
		
		loading_st(1);
		page_go1(url);

	}else{
		
		return ;
	
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
			document.getElementById('frm_list').action = "delete_ok.php";
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
	document.getElementById('frm_list').action = "level_ok.php";
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
		document.getElementById('frm_list').action = "level_ok.php?"+page;
		document.getElementById('frm_list').status.value = "totlevel";
		document.getElementById('frm_list').submit();
}


function win_popup(idx,vscrollbars,vtoolbar,vmenubar,vlocation,vwidth,vheight){		
	window.open('view.php?idx='+idx,'','scrollbars='+vscrollbars+',toolbar='+vtoolbar+',menubar='+vmenubar+',location='+vlocation+',width='+vwidth+',height='+vheight+',location=no');
}


function copyRssFeed(){

	var obj = document.getElementById("FeedRssUrl");
	if (obj.innerText !=undefined){
		str = obj.innerText;
	}else{
		str = obj.textContent;
	}

	var IE=(document.all)?true:false;
	if (IE) {
		if(confirm("이 게시판의 RSS주소를 클립보드에 복사하시겠습니까?")){
			window.clipboardData.setData("Text", str);
			alert("RSS 주소가 복사되었습니다. \n\n다운받은 프로그램을 열고 붙여넣기 하시면 완료됩니다.");
		}
	} else {
		temp = prompt("이 게시판의 RSS주소입니다. Ctrl+C를 눌러 클립보드로 복사하세요", str);
	}	
}

function parseXML(str) {
    if (str == null) return null;
    if ($.trim(str) == "") return createDocument();
 
    var dom = str;
    if ($.browser.msie) {
        dom = new ActiveXObject("Microsoft.XMLDOM");
        dom.async = "false";
        dom.loadXML(str);
    }
    else if (typeof (DOMParser) != "undefined") {
        var parser = new DOMParser();
        try {
 
            dom = parser.parseFromString(str, "text/xml");
        } catch (e) { };
 
    }
}

function searchMovie(query, callback){

	$.ajax({
		url :"/ndls/api/movie.do"
		,type:"get"
		,data:"query="+encodeURIComponent(query)+"&display=20"
		,dataType:"json"
		,timeout:"3000"
		,crossDomain:true
		,success:function(jsonData){
			var errCode = "";
			var errDesc = "";
			try{
				errDesc = jsonData.errorMessage;
				errCode = jsonData.errorCode;
				if (errCode==undefined){
					errCode = "";
					errDesc = "";
				}
			}catch(e){}

			if(errCode != ""){
				console.log(errCode+"="+errDesc);
			}else{
				eval( callback+"(jsonData)" );
			}
		}
		,error: function(xhr,status, error){
			//alert("에러발생"+xhr+">>"+error);
		}
	});
}

function movieSearchResult(obj){
	var html = "";
	
	try{
		for(var i=0;i<obj.total;i++){
			var movie = obj.movieList[i];
			
			var img_url = movie.image;
			var title = movie.title;
			title = title.replace(/(<([^>]+)>)/ig,"");
			var director = "";
			if (movie.director!=""){
				arr = movie.director.split("|");
				for(var z=0;z<arr.length;z++){
					if (arr[z]!=""){
						if(director != ""){director += ", ";}
						director += arr[z];
					}					
				}
			}
			var actor = "";	
			if (movie.actor!=""){
				arr = movie.actor.split("|");
				for(var z=0;z<arr.length;z++){
					if (arr[z]!=""){
						if(actor != ""){actor += ", ";}
						actor += arr[z];
					}					
				}
			}	
			var genre = "";	
			var year = movie.pubdate;
			var story  = "";

			if(html != ""){html +="<br />";}
			var atag = "<a href='#movie' onclick='selectMovie("+i+");'>";
			html += "<img src='"+img_url+"' style='max-width:110px;' onerror=\"this.src='/nanum/site/board/movie/img/no.gif'\" />"+atag+title+" | 감독 : "+director+"</a>";
			html += "<input type='hidden' id='movie_"+i+"_img' value='"+img_url+"' />";
			html += "<input type='hidden' id='movie_"+i+"_title' value='"+title+"' />";
			html += "<input type='hidden' id='movie_"+i+"_director' value='"+director+"' />";
			html += "<input type='hidden' id='movie_"+i+"_actor' value='"+actor+"' />";
			html += "<input type='hidden' id='movie_"+i+"_genre' value='"+genre+"' />";
			html += "<input type='hidden' id='movie_"+i+"_year' value='"+year+"' />";
			html += "<textarea id='movie_"+i+"_story' style='display:none'>"+story+"</textarea>";
		}
	}catch(e){
//		alert(e);
	}
	if(html == ""){html = "검색결과가 없습니다.";}
	$("#movie_list").show().html( html );
}



function selectMovie(num){

	var eForm = document.getElementById("frm");

	eForm.b_keyword.value = $("#movie_"+num+"_img").val();
	eForm.b_subject.value = $("#movie_"+num+"_title").val();
	eForm.b_temp3.value = $("#movie_"+num+"_director").val();
	eForm.b_temp4.value = $("#movie_"+num+"_actor").val();
	eForm.b_temp5.value = $("#movie_"+num+"_genre").val();
	eForm.b_temp8.value = $("#movie_"+num+"_year").val();
	eForm.b_content.value = $("#movie_"+num+"_story").val();
	$("#movie_list").hide().text("");


	//$("td.content iframe").remove();
	$("td.content #div_editor").html("<textarea name='b_content' id='b_content' style='width:100%; height:250px;'>"+eForm.b_content.value+"</textarea>");
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "b_content",
		sSkinURI: "/smarteditor2/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});

}