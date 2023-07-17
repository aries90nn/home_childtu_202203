<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="sns">
	<a href="#facebook" onclick="facebook('${board.b_subject}','http://${SERVER_NAME}/board/sns.do?a=${config.a_num}&b=${board.b_num}&u=${NOWPAGE}')" onkeypress=""><img src="/nanum/site/board/nninc_simple/img/sns_facebook.gif" alt="페이스북 보내기"/></a>
	
	<a href="#twitter" onclick="twitter('${board.b_subject}','http://${SERVER_NAME}${uri}?proc_type=view&b_num=${board.b_num}')" onkeypress=""><img src="/nanum/site/board/nninc_simple/img/sns_twitter.gif" alt="트윗하기"/></a>

</div>