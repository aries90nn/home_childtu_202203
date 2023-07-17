<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />

</head>


<c:set var="prepage" value="${param.prepage }" />
<c:if test="${empty prepage }">
	<c:set var="prepage" value="/" />
	<c:if test="${!empty BUILDER_DIR }">
		<c:set var="prepage" value="/${BUILDER_DIR }${prepage }" />
	</c:if>
</c:if>
<c:if test="${!empty sessionScope.ss_m_id}">
	<c:set var="back_url" value="/" />
	<c:if test="${!empty BUILDER_DIR }">
		<c:set var="back_url" value="/${BUILDER_DIR }${back_url }" />
	</c:if>
	<script>
		location.href = '${back_url}';
	</script>
</c:if>

<script type="text/javascript">
					//<![CDATA[
					
					function loginGo(eForm){
						if(!valueCheck(eForm.m_id, "아이디")){ return false;}
						if(!valueCheck(eForm.m_pwd, "비밀번호")){ return false;}
					}
					
					
					function valueCheck(obj, objName){	//text필드
						var value = obj.value.replace(/_/g, " ");
						if(value.split(" ").join("") == ""){
							alert(objName+"을(를) 입력하세요");
							try{
								obj.focus();
								return false;
							}catch(e){
								return false;
							}
						}else{
							return true;
						}
					}
					//]]>
					</script>
					
					
			<div class="cash_box">
				<p><a href="/${BUILDER_DIR }/site/member/cash_delete_popup.do" onclick="window.open(this.href, '', 'width=425px,height=475px,top=0,left=0, scrollbars=no'); return false;">모바일에서 로그인이 안될 경우 [해결방법]</a></p>
			</div>

			<div class="book_tab_wrap">
				<ul>
					<li>
						<!--<span class="tabwrap onwrap" id="ptabwrap1"><a href="javascript:;" class="tab on" id="ptab1" onmouseover="programList(1);firTab();return false;" onfocus="programList(1);firTab();return false;">일반회원</a></span> -->
					    <div class="plist_wrap" id="plistwrap1" style="display:block;">
							<div class="plist_box_wrap">
								<div class="plist_wrap" id="plistwrap1" style="display:block;">
									<form id="frm_login" method="post" action="${DOMAIN_HTTPS}/${BUILDER_DIR }/member/loginOk.do" onsubmit="return loginGo(this);">
									<input type="hidden" name="prepage" value="${prepage}" />
										<div class="member_box">
											<div class="area">
												<p><input type="text" title="아이디 입력" id="m_id" name="m_id" class="label_put" value="${empty cookie.ck_m_id.value ? 'SP1000JEEE' : cookie.ck_m_id.value }" /><label for="m_id">아이디를 입력해주세요</label></p>
												<p><input type="password" id="m_pwd"  title="비밀번호 입력" name="m_pwd" class="label_put" value="${empty cookie.ck_m_id.value ? '!sksna1234' : '' }" /><label for="m_pwd">비밀번호를 입력해주세요</label></p>
												<!-- <input type="checkbox" id="idsave" name="idsave" value="Y" ${cookie.ck_idsave.value eq 'Y' ? 'checked="checked"' : '' } /><label for="idsave">아이디저장</label> -->
												<div class="log_btn">
													<input type="submit" id="" name="" class="inp_btn" title="로그인" value="로그인" />
													<ul>
														<li><a class="find" href="/${BUILDER_DIR }/site/member/agree.do">회원가입</a></li>
														<li><a class="find" href="/${BUILDER_DIR }/site/member/idFind.do">아이디찾기</a></li>
														<li><a class="find" href="/${BUILDER_DIR }/site/member/pwdFind.do">비밀번호찾기</a></li>
													</ul>
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>

					
	
					

</div><script type="text/javascript">
		function programList(a){
			for(var i=1;i<4;i++){
				obj = document.getElementById("plistwrap"+i);
				obj2 = document.getElementById("ptab"+i);
				
				if(a==i){
					if (obj)	obj.style.display = "block";
					if (obj2)	obj2.className = "tab on"
				}else{
					if (obj)	obj.style.display = "none";
					if (obj2)	obj2.className = "tab";	
				}
			}
		}
	</script>	
