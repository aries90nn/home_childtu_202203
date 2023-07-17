<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="decorator" content="user_content" />
</head>

<script type="text/javascript">
function viewer(eb_idx){
	var scr_width = screen.availWidth;
	var scr_height = screen.availHeight;
	window.open('/ebookpdf/viewer/index.do?eb_idx='+eb_idx,'ebook_pdf','width='+scr_width+',height='+scr_height+',scrollbars=yes');
}

</script>

<div class="book_list_wrap">
	<div class="book_list">
		<ul>
<c:forEach items="${ebookpdfList }" var="ebookpdf" varStatus="no">

			<li>
				<a href="/main/ebookpdf/viewer/index.do?eb_idx=${ebookpdf.eb_idx }" target="_blank"  class="btn_viewer">
					<div class="poster">
						<img src="/data/ebookpdf/${ebookpdf.eb_img }" onerror="this.src='/nanum/site/ebookpdf/img/book_poster_no.jpg';" style="width:253px;height:372px;" alt="${ebook.ebookpdf }" />
					</div>
				</a>							
				<a href="./down.do?eb_idx=${ebookpdf.eb_idx }" class="cbtn cbtn_g">다운로드</a>
				<div class="info">
					<p class="tit">${ebookpdf.eb_subject }</p>
				</div>
			</li>


</c:forEach>		
			
		</ul>
	</div>
	
	<!-- 페이징 -->
	<div class="paginate">
		${pagingtag }
	</div>
	<!-- //페이징 -->
</div>