<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="book_list" value="${requestScope['bestbook_data'].LIST_DATA }"></c:set>
<%//검색카운트항목이 있으면 1부터 없으면 0부터%>
<c:set var="begin_num" value="${empty resultSearchCount or resultSearchCount eq 'null' ? 0 : 1 }"></c:set>
<c:set var="end_num" value="${fn:length(book_list) > 3 ? 1 : fn:length(book_list)-2 }" />
<c:set var="end_num" value="${end_num < 0 ? 0 : end_num }" />
<c:forEach items="${book_list}" var="book" varStatus="no" begin="${begin_num }" end="${end_num }">
	<c:choose>
		<c:when test="${book.MANAGE_CODE eq 'MA' }">
			<c:set var="lib_class" value="lib1" />
			<c:set var="lib_name" value="독립" />
		</c:when>
		<c:when test="${book.MANAGE_CODE eq 'MB' }">
			<c:set var="lib_class" value="lib2" />
			<c:set var="lib_name" value="금호" />
		</c:when>
		<c:when test="${book.MANAGE_CODE eq 'MC' }">
			<c:set var="lib_class" value="lib3" />
			<c:set var="lib_name" value="교육" />
		</c:when>
		<c:when test="${book.MANAGE_CODE eq 'MD' }">
			<c:set var="lib_class" value="lib4" />
			<c:set var="lib_name" value="중앙" />
		</c:when>
		<c:when test="${book.MANAGE_CODE eq 'ME' }">
			<c:set var="lib_class" value="lib5" />
			<c:set var="lib_name" value="송정" />
		</c:when>
		<c:when test="${book.MANAGE_CODE eq 'MF' }">
			<c:set var="lib_class" value="lib6" />
			<c:set var="lib_name" value="석봉" />
		</c:when>
		<c:otherwise>
			<c:set var="lib_class" value="lib5" />
			<c:set var="lib_name" value="${book.MANAGE_CODE }" />
		</c:otherwise>
	</c:choose>
	
	<%-- 필드명 통일 --%>
	<c:set var="MEC" value="${book.MEDIA_CODE }" />
	<c:set var="RK" value="${book.SPECIES_KEY }" />
	<c:set var="KDC" value="${book.SUBJECT_CODE}" />
	<c:set var="BRN" value="${book.REG_NO}" />
	<c:set var="SIB" value="${book.ST_CODE}" />
	<c:set var="SIB2" value="${book.ISBN}" />
	<c:set var="AI" value="${book.AUTHOR}" />
	<c:set var="PI" value="${book.PUBLISHER}" />
	<c:set var="YR" value="${empty book.PUB_YEAR ? book.PUBLISH_YEAR : book.PUB_YEAR}" />
	<c:set var="MGC" value="${book.MANAGE_CODE}" />
	<c:set var="place_str" value="${book.SHELF_LOC_NAME}" />
	<c:set var="callinfo_str" value="${book.CALL_NO}" />

	
	<c:set var="TI" value="${empty book.TITLE_INFO ? book.TITLE : book.TITLE_INFO}" />
	<c:set var="TI2" value="${TI}" />
	<c:if test="${!empty TI }">
		<c:set var="TI_arr" value="${fn:split(TI,'=') }" />
		<c:set var="TI2" value="${TI_arr[0] }" />
		<c:set var="TI_arr" value="${fn:split(TI2,':') }" />
		<c:set var="TI2" value="${TI_arr[0] }" />
		<c:set var="TI_arr" value="${fn:split(TI2,'/') }" />
		<c:set var="TI2" value="${TI_arr[0] }" />
	</c:if>
	
	<c:url var="view_url" value="/${BUILDER_DIR }/site/search/bestBook.do">
		<c:param name="manage_code" value="${MGC }" />
		<c:param name="book_type" value="BOOK" />
		<c:param name="species_key" value="${RK }" />
		<c:param name="reckey" value="${book.BOOK_KEY }" />
		<c:param name="prepage" value="/${BUILDER_DIR }/site/search/bestBook.do?sh_days=${sh_days }" />
	</c:url>


								<div class="plist_box">
									<div class="plist_text data_book" data-mec="${MEC }" data-rk="${RK }" data-brn="${BRN }" data-isbn="${SIB2 }" data-ti="${TI2 }" data-yr="${YR }" data-mgc="${MGC }">
										<p class="book_img"><a href="${view_url }"><img class="book_image" src="/nanum/ndls/imgs/common/noimg3.gif" style="width:185px;260px;" alt="${TI2} 표지이미지" /></a></p>
										<div class="txttit_wrap">
											<p class="${lib_class }"><span><a href="${view_url }">${lib_name }</a></span></p>
											<dl>
												<dt>${book.TITLE }</dt>
												<dd class="author">${book.AUTHOR }<span>${book.PUBLISHER }</span></dd>
												<dd class="stxt book_desc">&nbsp;</dd>
											</dl>
										</div>
									</div>
								</div>
</c:forEach>


<script type="text/javascript">
//<![CDATA[

$(function(){

	$("div.data_book").each(function(){
		var book_info = $(this);
		var rk = book_info.data("rk");
		var brn = book_info.data("brn");
		var isbn = book_info.data("isbn");
		var mec = book_info.data("mec");
		var ti = book_info.data("ti");
		var yr = book_info.data("yr");
		var mgc = book_info.data("mgc");
		var book_img = book_info.find("img.book_image");
		
		setBookImage(book_img, isbn);
		
	});

});

//]]>
</script>