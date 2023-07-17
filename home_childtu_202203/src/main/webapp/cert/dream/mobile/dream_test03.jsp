<%@
page contentType="text/html;charset=utf-8"%><%@
page import="com.dreamsecurity.crypt.*"%><%@
page import ="kr.co.nninc.ncms.common.Func"%><%@
include file="./inc_config.jsp" %><%

	String encPriInfo = request.getParameter("priinfo");
	String gender = ""; // 성별 정보
	String nation = ""; // 내국, 외국인 정보

	//out.println("priinfo=" + encPriInfo);
	MsgCrypto mscr = new MsgCrypto();
	
	String rstInfo = mscr.msgDecrypt(encPriInfo, cfg_prikeypath,cfg_password,"EUC-KR");
	String[] rstInfoArray = rstInfo.split("\\$");

	//System.out.println("rstInfo : " + rstInfo);
	//System.out.println("rstInfoArray Size ::: " + rstInfoArray.length);
	
	if (rstInfoArray.length > 3) {
		if (rstInfoArray[6].equals("1"))
			gender = "남자";
		else
			gender = "여자";

		if (rstInfoArray[7].equals("0"))
			nation = "내국인";
		else
			nation = "외국인";

		/*
		if (rstInfoArray.length > 0) {
			out.println("결과코드 : " + rstInfoArray[0]);
			out.println("<p>ci : " + rstInfoArray[1]);
			out.println("<p>di : " + rstInfoArray[2]);
			out.println("<p>전화번호 : " + rstInfoArray[3]);
			out.println("<p>통신사 : " + rstInfoArray[4]);
			out.println("<p>생년월일 : " + rstInfoArray[5]);
			out.println("<p>성별 : " + gender+"("+rstInfoArray[6]+")");
			out.println("<p>내외국인 : " + nation);
			out.println("<p>이름 : " + rstInfoArray[8]);
			out.println("<p>요청번호 : " + rstInfoArray[9]);
			out.println("<p>요청시간 : "+rstInfoArray[10]);
		}
		*/
		
		//세션 생성
		session = request.getSession(true);
		session.setAttribute("ss_m_name", rstInfoArray[8]);
		session.setAttribute("ss_m_dupinfo", rstInfoArray[2]);
		session.setAttribute("ss_m_coinfo", rstInfoArray[1]);
		
		if("1".equals(rstInfoArray[6])){
			session.setAttribute("ss_m_sex", "0");	//남:0, 여:1 로 변환
		}else {
			session.setAttribute("ss_m_sex", "1");
		}
		
		Calendar cal = Calendar.getInstance();
		int birth_year = Integer.parseInt( rstInfoArray[5].substring(0, 4) );
		int nowyear = cal.get(Calendar.YEAR);
		String age = Integer.toString( nowyear - birth_year );
		session.setAttribute("ss_m_age", age);
		
		session.setAttribute("ss_m_birth", rstInfoArray[5]);
		
		
		String prepage = (String)session.getAttribute("ss_prepage");
		if(prepage == null){
			prepage = request.getParameter("prepage");
		}
		if(prepage == null){prepage = "/";}
		String v = Func.get_idx_add();
		if( prepage.indexOf( "?" ) >= 0 ){
			prepage += "&v="+v;
		}else{
			prepage += "?v="+v;
		}
		//out.print("<br />prepage="+prepage);
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ko" />
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>ipin</title>
	<style>
		.ipin_complete{width:100%;min-width:320px;}
		.tit{margin-bottom:40px;font-size:24px;font-family:'NotoKrB', sans-serif;color:#333;letter-spacing:-0.05em;text-align:center;}
		.btn_wrap{text-align:center;}
		.btn_wrap .btn{display:inline-block;margin-bottom:20px;padding:0 20px;height:35px;line-height:35px;font-family:'NotoKrM', sans-serif;color:#fff;letter-spacing:-0.05em;background:#644444;}
		
	</style>
</head>
<script type="text/javascript">
//<![CDATA[
onload = function(){
	opener.location.href="<%=prepage%>";
	window.close();
}
//]]>
</script>
<body>
<div class="ipin_complete">
	<p class="tit">본인인증 완료</p>
	<p class="btn_wrap">
		<a href="javascript:;" onclick="javascript:self.close();" class="btn">닫기</a>
	</p>
</div>
</body>
</html>
<%
		
		
	} else {
		out.println("결과코드 : " + rstInfoArray[0]);
		out.println("오류내용 : " + rstInfoArray[1]);
	}
%>