<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:set var="del_rtn_page" value="${nowPage }"/>
<c:set var="a_upload_len" value="${config.a_upload_len}"/>
<c:set var="file_count" value="0"/>

<c:forEach var="t" begin="1" end="${a_upload_len}" step="1">
	<c:set var="file_name">b_file${t}</c:set>
	<c:set var="b_file_name">${board[file_name]}</c:set>
	<input type="hidden" id="b_file${t}" name="b_file${t}" value="${b_file_name}" />
	
	<c:if test="${b_file_name ne ''}">
		${b_file_name} <a href="#delete" onclick="delete_ok('${config.a_num}','${board.b_num}','${t}','${del_rtn_page}');" onkeyup="delete_ok('${config.a_num}','${board.b_num}','${t}','${del_rtn_page}');" title="첨부파일 삭제하기">
		<img src="/nanum/site/board/${config.a_level}/img/dr_del_bt.gif" alt="첨부파일 삭제하기" /></a><br />
		
		<c:set var= "file_count" value="${file_count + 1}"/>
	</c:if>
</c:forEach>

<div id="NFU_file_control" style="width:98%;height:120px;"></div>
	<div id="div_multi_btn" style="padding-top:5px;">
		<input type="button" value="파일찾기" onclick="NFileUpload.searchFile();" class="NFU_cbtn NFU_cbtn_point"/>
		<input type="button" value="선택삭제" onclick="NFileUpload.removeFiles('check');" class="NFU_cbtn NFU_cbtn_g"/>
		<input type="button" value="전체삭제" onclick="NFileUpload.removeFiles();" class="NFU_cbtn NFU_cbtn_g"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="일반업로드전환" onclick="uploadSelect('normal');" class="NFU_cbtn"/>
	</div>
	<div id="div_normal_btn" style="padding-top:5px;display:none;">
		<input type="button" value="멀티업로드전환" onclick="uploadSelect('multi');" class="NFU_cbtn" />
		<span>※ HTML5 미지원 브라우저일 경우 일반업로드만 사용할 수 있습니다.</span>
	</div>
	<div>
		<span style="font-size:12px;color:#DF0101">※ 허용파일가능갯수 :${config.a_upload_len-file_count}개 <br/>※ 제한용량 : ${config.a_nofilesize}MB <br/>※ 허용파일은 ${config.a_nofile}</span>
	</div>

	<script type="text/javascript">
				//<![CDATA[
				var nfu_upload_type = "multi";
				NFileUpload.nfuPath = "/nfu/";						//config, css 파일위치
				NFileUpload.uploadURL = "/board/nfu_upload.do?a_tablename=${config.a_tablename}&a_num=${config.a_num}";	//파일업로드 처리URL
				NFileUploadNormal.uploadURL = "/board/nfu_normal_upload.do?a_tablename=${config.a_tablename}&a_num=${config.a_num}";	//일반파일업로드 처리URL
				NFileUpload.useImageView = true;				//이미지보기

				NFileUpload.maxFileCount = <c:out value="${config.a_upload_len-file_count}"/>;					//파일개수
				NFileUpload.maxFileSize = ${config.a_nofilesize} * 1024 * 1024;		//파일크기
				NFileUpload.fileFilter = "${config.a_nofile}";			//파일종류
				NFileUpload.fileFilterType = true;				//파일종류 허용여부
				//NFileUpload.setAddValue("a_tablename", "${config.a_tablename}");

				if(NFileUpload.maxFileCount == 0){
					document.getElementById("NFU_file_control").style.display = "none";
					document.getElementById("div_multi_btn").style.display = "none";
				}else{
					NFileUpload.load();
				}

				//개별파일전송완료이벤트
				function fileUploadResultEvent(result){
					var b_file;
					var i;
					//alert("전송결과 : \n"+result);
					for(i=1;i<=${config.a_upload_len};i++){
						b_file = document.getElementById("b_file"+i);
						if(b_file.value == ""){
							b_file.value = result;
							break;
						}
					}
				}

				//전체파일전송완료이벤트
				function fileUploadCompleteEvent(){
					//alert('전체파일 전송완료');
					<c:choose>
						<c:when test="${config.a_level eq 'nninc_photo_mobile'}">
						
						var b_file_count = 0;
						for(var i=1;i<=${config.a_upload_len};i++){
							b_file = document.getElementById("b_file"+i);
							if(b_file.value != ""){
								b_file_count++;
								break;
							}
						}
						
						if(b_file_count == 0){
							alert("이미지 파일을 선택하세요.");
						}else{
							document.getElementById("frm").submit();
						}
						
						</c:when>
						<c:otherwise>
						document.getElementById("frm").submit();
						</c:otherwise>
					</c:choose>

				}

				//객체생성실패 이벤트
				function fileControlCreateFailEvent(e){
					//alert("파일컨트롤 생성 실패. 일반업로드로 전환됩니다.\n\n"+e);
					uploadSelect( 'normal' );
				}

				function delete_ok(a, b, z, p){
					if(confirm("첨부파일을 삭제하시겠습니까?")){
						location.href="/board/imgDeleteOk.do?a_num="+a+"&b_num="+b+"&b_file="+z+"&prepage="+encodeURIComponent(p)+"&board_token="+encodeURIComponent('${board_token}');
					}else{
						return;
					}
				}

				function uploadSelect( utype ){
					switch(utype){
						case "multi" :
							document.getElementById("div_multi_btn").style.display = "block";
							document.getElementById("div_normal_btn").style.display = "none";
							NFileUpload.load();
							break;
						default :
							document.getElementById("div_multi_btn").style.display = "none";
							document.getElementById("div_normal_btn").style.display = "block";
							NFileUploadNormal.load();
							break;
					}
					nfu_upload_type = utype;
				}

				function nfu_upload(){
					if(nfu_upload_type == "multi"){
						NFileUpload.fileUpload();
					}else{
						NFileUploadNormal.fileUpload();
					}
				}
				//]]>
				</script>