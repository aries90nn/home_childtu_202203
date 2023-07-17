<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.Calendar" %>
<%	//인증 후 결과값이 null로 나오는 부분은 관리담당자에게 문의 바랍니다.
    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

    String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");

    String sSiteCode = "H0168";				// NICE로부터 부여받은 사이트 코드
    String sSitePassword = "TLZTMW3RRI9R";			// NICE로부터 부여받은 사이트 패스워드

    String sCipherTime = "";			// 복호화한 시간
    String sRequestNumber = "";			// 요청 번호
    String sResponseNumber = "";		// 인증 고유번호
    String sAuthType = "";				// 인증 수단
    String sName = "";					// 성명
    String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
    String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
    String sBirthDate = "";				// 생년월일(YYYYMMDD)
    String sGender = "";				// 성별
    String sNationalInfo = "";			// 내/외국인정보 (개발가이드 참조)
	String sMobileNo = "";				// 휴대폰번호
	String sMobileCo = "";				// 통신사
    String sMessage = "";
    String sPlainData = "";
    
    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

    if( iReturn == 0 )
    {
        sPlainData = niceCheck.getPlainData();
        sCipherTime = niceCheck.getCipherDateTime();
        
        // 데이타를 추출합니다.
        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
        
        sRequestNumber  = (String)mapresult.get("REQ_SEQ");
        sResponseNumber = (String)mapresult.get("RES_SEQ");
        sAuthType		= (String)mapresult.get("AUTH_TYPE");
        sName			= (String)mapresult.get("NAME");
		//sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
        sBirthDate		= (String)mapresult.get("BIRTHDATE");
        sGender			= (String)mapresult.get("GENDER");
        sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
        sDupInfo		= (String)mapresult.get("DI");
        sConnInfo		= (String)mapresult.get("CI");
        sMobileNo		= (String)mapresult.get("MOBILE_NO");
        sMobileCo		= (String)mapresult.get("MOBILE_CO");
        
        String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
        if(!sRequestNumber.equals(session_sRequestNumber))
        {
            sMessage = "세션값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
            sResponseNumber = "";
            sAuthType = "";
        }
    }
    else if( iReturn == -1)
    {
        sMessage = "복호화 시스템 에러입니다.";
    }    
    else if( iReturn == -4)
    {
        sMessage = "복호화 처리오류입니다.";
    }    
    else if( iReturn == -5)
    {
        sMessage = "복호화 해쉬 오류입니다.";
    }    
    else if( iReturn == -6)
    {
        sMessage = "복호화 데이터 오류입니다.";
    }    
    else if( iReturn == -9)
    {
        sMessage = "입력 데이터 오류입니다.";
    }    
    else if( iReturn == -12)
    {
        sMessage = "사이트 패스워드 오류입니다.";
    }    
    else
    {
        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
    }

%>
<%!

	public String requestReplace (String paramValue, String gubun) {

        String result = "";
        
        if (paramValue != null) {
        	
        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        	paramValue = paramValue.replaceAll("\\*", "");
        	paramValue = paramValue.replaceAll("\\?", "");
        	paramValue = paramValue.replaceAll("\\[", "");
        	paramValue = paramValue.replaceAll("\\{", "");
        	paramValue = paramValue.replaceAll("\\(", "");
        	paramValue = paramValue.replaceAll("\\)", "");
        	paramValue = paramValue.replaceAll("\\^", "");
        	paramValue = paramValue.replaceAll("\\$", "");
        	paramValue = paramValue.replaceAll("'", "");
        	paramValue = paramValue.replaceAll("@", "");
        	paramValue = paramValue.replaceAll("%", "");
        	paramValue = paramValue.replaceAll(";", "");
        	paramValue = paramValue.replaceAll(":", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll("#", "");
        	paramValue = paramValue.replaceAll("--", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll(",", "");
        	
        	if(gubun != "encodeData"){
        		paramValue = paramValue.replaceAll("\\+", "");
        		paramValue = paramValue.replaceAll("/", "");
            paramValue = paramValue.replaceAll("=", "");
        	}
        	
        	result = paramValue;
            
        }
        return result;
  }
%>
<%
if("".equals(sMessage)){
	session = request.getSession(true);
	session.setAttribute("ss_m_name", sName);
	session.setAttribute("ss_m_dupinfo", sDupInfo);
	session.setAttribute("ss_m_coinfo", sConnInfo);
	
	if("0".equals(sGender)){
		session.setAttribute("ss_m_sex", "1");	//남:0, 여:1 로 변환 (코라스기준, 자체응용프로그램에서는 개별적으로 수정해서 쓴다)
	}else if("1".equals(sGender)){
		session.setAttribute("ss_m_sex", "0");
	}
	
	Calendar cal = Calendar.getInstance();
	int birth_year = Integer.parseInt( sBirthDate.substring(0, 4) );
	int nowyear = cal.get(Calendar.YEAR);
	String age = Integer.toString( nowyear - birth_year );
	session.setAttribute("ss_m_age", age);
	
	session.setAttribute("ss_m_birth", sBirthDate);
	
	String prepage = request.getParameter("prepage");
	if(prepage == null){prepage = "/";}
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ko" />
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>본인인증</title>
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
}else{
	
	out.println("<script>alert('본인인증실패 : " + sMessage + "');</script>");
}
%>