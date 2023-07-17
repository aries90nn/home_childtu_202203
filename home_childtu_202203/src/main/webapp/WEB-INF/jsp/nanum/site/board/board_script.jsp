<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<jsp:useBean id="now" class="java.util.Date" />
<c:set var="mmss"><fmt:formatDate value="${now}" pattern="mmss" /></c:set>

<link rel="stylesheet" type="text/css" href="/nanum/site/board/nninc_photo_mobile/css/lightbox.css" />
<c:if test="${config.a_level ne ''}">
	<link rel="stylesheet" type="text/css" href="/nanum/site/board/${config.a_level}/css/common.css?ver=${mmss}" />
	<script type="text/javascript" src="/nanum/site/board/${config.a_level}/js/common.js?ver=${mmss}"></script>
	<script type="text/javascript" src="/nanum/site/board/${config.a_level}/js/board.js?ver=${mmss}"></script>
</c:if>
<script type="text/javascript" src="/nanum/site/board/nninc_photo_mobile/js/spica.js"></script>
<script type="text/javascript" src="/nanum/site/board/nninc_photo_mobile/js/lightbox.js"></script>

<style type="text/css">
.msimg{max-width:855px;margin:0 auto;}
embed,
video{width:855px !important;text-align:center;}

@media(max-width:736px){
	.msimg{max-width:320px;height:auto;}
	embed,
	video{width:100% !important;text-align:center;}
#board .table_bview tbody td.content p,
#board .table_bview tbody td.content span,
#board .table_bview tbody td.content strong{font-size:13px !important;/*line-height:150% !important;*/}
#board .table_bview tbody td.content table{width:100%;}
}
</style>

<script type="text/javascript">
//<![CDATA[
$(function(){
	$("#b_zip1").attr("readonly", true);
	$("#b_addr1").attr("readonly", true);
});
//]]>
</script>

