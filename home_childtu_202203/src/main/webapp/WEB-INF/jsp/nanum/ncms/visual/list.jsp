<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="ncms_content" />
</head>
<script type="text/javascript" src="/nanum/ncms/common/js/ncms_visual.js"></script>
<script>
function deleteOk(v_num){
	if(confirm("비주얼을 삭제하시겠습니까?")){
		location.href="/ncms/visual/deleteOk.do?v_num="+v_num+"&prepage=${nowPageEncode}";
	}
}
</script>
	<h1 class="tit"><span>메인비쥬얼</span> 리스트</h1>
	
	<!-- 내용들어가는곳 -->
	<div id="contents_area">
	
			<form id="frm_sch" action="list.do" method="get">
			<!-- 검색 -->
			<div class="top_search_area mt10">
				<ul>
					<li class="tit"><label for="v_search"><h3 class="tit">메인비쥬얼검색 :</h3></label></li>
					<li class="sel">
						<select id="v_search" name="v_search" title="검색형태 선택" class="t_search" style="width:80px;">
							<option value="v_subject" ${v_search eq 'v_subject' ? 'selected="selected"' : '' }>제목 </option>
						</select>	
					</li>
					<li class="search"><label for="v_keyword">검색어를 입력하세요</label><input title="검색어 입력" name="v_keyword" id="v_keyword" type="text" value="${param.v_keyword}" class="search_input autoInput" /><input class="search_icon" type="image" alt="검색" src="/nanum/ncms/img/common/search_btn.gif" /></li>
					<li class="btn"><input type="button" value="전체보기" class="btn_gr_default" onclick="page_go1('/ncms/visual/list.do');" /></li>
				</ul>
			</div>
			<!-- //검색 -->	
			</form>
	
	
		<form id= "frm_list" action="" method='post'>
			<div>
				<input type="hidden" name="status" />
				<input type="hidden" name="v_chk" />
				<input type="hidden" name="v_num" />
	
				<input type="hidden" id="chk_all" name="chk_all" />
				<input type="hidden" name="v_search" value="${v_search}" />
				<input type="hidden" name="v_keyword" value="${v_keyword}" />
				<input type="hidden" name="prepage" value="${nowPage}" />
			</div>
	
	
			<div class="list_count" style="height:20px">			
				<!-- 전체 <strong>14</strong>개 (페이지 <strong class="orange">1</strong>/2) -->
			</div>
	
	
			<fieldset>
				<legend>메인비쥬얼목록보기</legend>
				<table class="bbs_common bbs_default" summary="사이트의 메인비쥬얼를 관리합니다.">
				<caption>메인비쥬얼관리 서식</caption>
				<colgroup>
				<col width="50" />
				<col />
				<col width="20%" />
				<col width="20%" />
				<col width="80" />
				<col width="50" />
				<col width="50" />
				</colgroup>
	
				<thead>
				<tr>
					<th scope="col">선택</th>
					<th scope="col">메인비쥬얼</th>
					<th scope="col">제목</th>
					<th scope="col">사용기간</th>
					<th scope="col">사용여부</th>
					<th scope="col">수정</th>
					<th scope="col">삭제</th>
				</tr>
				</thead>
	
				<tbody>
				


				<c:forEach items="${visualList}" var="visual" varStatus="no">
				<tr>
					<td class="center"><input type="checkbox" name="chk" value="${visual.v_num}" title="해당 메인비쥬얼 선택" /></td>
					<td class="center p0">
						<a href="down.do?v_num=${visual.v_num}" >
							<img src = "/data/visual/${visual.v_img}" alt="${visual.v_subject}" style="width:520px;height:196px" />
						</a>
					</td>
					<td class="center">${visual.v_subject}</td>
					<td class="center eng"><span class="point">${fn:substring(visual.v_sdate,0,10)} ~ 
						<c:choose>
							<c:when test="${visual.unlimited eq 'Y'}">
								무제한
							</c:when>
							<c:otherwise>
								${fn:substring(visual.v_edate,0,10)}
							</c:otherwise>
						</c:choose>
					</span></td>
					<td class="center">${visual.v_chk == "Y" ? "<strong>사용</strong>" : "중지"}</td>
					<td class="center"><a href="/ncms/visual/write.do?v_num=${visual.v_num}&prepage=${nowPageEncode}"><img alt="수정" src="/nanum/ncms/img/common/modify_icon.gif"/></a></td>
					<td class="center"><a href="#del" onclick="deleteOk('${visual.v_num}');"><img alt="삭제" src="/nanum/ncms/img/common/delete_icon.gif" /></a></td>
				</tr>
				</c:forEach>
				<c:if test="${fn:length(visualList) == 0}">
					<tr>
						<td class="center"></td>
						<td class="center"></td>
						<td class="center"></td>
						<td class="center"></td>
						<td class="center"></td>
						<td class="center"></td>
						<td class="center"></td>
					</tr>
				</c:if>


				</tbody>
				</table>
			</fieldset>
	
			<!-- 하단버튼 -->
			<div id="contoll_area">
				<ul>
					<li class="btn_le"><p><a href="javascript:checkAll();" class="btn_bl">전체 선택/해제</a></p><p><a href="javascript:delete2();" class="btn_gr">선택 메인비쥬얼삭제</a></p>
					<p><a onclick="javascript:window.open('/ncms/visual/listMove.do?prepage=${nowPageEncode}','','width=350, height=750,scrollbars=no');" class="btn_gr">순서일괄수정</a></p></li>
					<li class="btn_ri">
						<p><img alt="" src="/nanum/ncms/img/common/checkbox_on.gif" />&nbsp;선택한 메인비쥬얼를</p>
						<p><select id="tot_level_chk" name="tot_level_chk" title="선택한 메인비쥬얼 사용여부 선택" class="t_search" style="width:70px;">
							<option value="Y" selected="selected">사용</option>
							<option value="N" >중지</option>
						</select></p>
						<p>(으)로</p>
						<p><a href="javascript:tot_levelchage();" class="btn_gr">변경</a></p>
					</li>
				</ul>						
			</div>
			<!-- //하단버튼 -->
	
	
		</form>
	
	</div>
	<!-- 내용들어가는곳 -->