<%@
page contentType = "text/html;charset=utf-8"%><%@
page import="kcb.org.json.*" %><%@
page import ="kr.co.nninc.ncms.common.Func"%><%@
include file="./inc_config.jsp" %><%

    //**************************************************************************
	// 파일명 : ipin_popup3.jsp
	// - 팝업페이지
	// 아이핀 서비스 인증 결과 화면(return url)
	// 암호화된 인증결과정보를 복호화한다.
	//**************************************************************************
	
	//' 처리결과 모듈 토큰 정보
	String MDL_TKN = request.getParameter("MDL_TKN");

	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' KCB로부터 부여받은 회원사코드(아이디) 설정 (12자리) 
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String CP_CD = cfg_cp_cd;	// 회원사코드
	//String CP_CD = (String)session.getAttribute("IPIN_CP_CD");
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 타겟 : 운영/테스트 전환시 변경 필요
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String target = cfg_target; // 테스트="TEST", 운영="PROD"
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 라이센스 파일
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	//String license = "C:\\okcert3_license\\" + CP_CD + "_TIS_01_" + target + "_AES_license.dat";
	String license = cfg_license;
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 서비스명 (고정값)
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String svcName = "TIS_IPIN_POPUP_RESULT";
	
	/**************************************************************************
	okcert3 요청 정보
	**************************************************************************/
	JSONObject reqJson = new JSONObject();
	
	reqJson.put("MDL_TKN", MDL_TKN);
	String reqStr = reqJson.toString();
	
	/**************************************************************************
	okcert3 실행
	**************************************************************************/
	kcb.module.v3.OkCert okcert = new kcb.module.v3.OkCert();
	
	// '************ IBM JDK 사용 시, 주석 해제하여 호출 ************
	// okcert.setProtocol2type("22");
	// '객체 내 license를 리로드해야 될 경우에만 주석 해제하여 호출. (v1.1.7 이후 라이센스는 파일위치를 key로 하여 static HashMap으로 사용됨)
	// okcert.delLicense(license);
	
	//' callOkCert 메소드호출 : String license 파일 path로 라이센스 로드
	String resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr);
	/*
	// 'OkCert3 내부에서 String license 파일 path로 라이센스를 못 읽어올 경우(Executable Jar 환경 등에서 발생),
	// '메소드 마지막 파라미터에 InputStream를 사용하여 라이센스 로드
	String resultStr = null;
	if ( ! okcert.containsLicense(license) ) {			// 로드된 라이센스 정보가 HashMap에 없는 경우
		java.io.InputStream is = new java.io.FileInputStream(license);	// 환경에 맞게 InputStream 로드
		resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr, is);
	} else {											// 로드된 라이센스 정보가 HashMap에 있는 경우
		resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr);
	}
	*/
	
	JSONObject resJson = new JSONObject(resultStr);
	
    String RSLT_CD =  resJson.getString("RSLT_CD");
    String RSLT_MSG =  resJson.getString("RSLT_MSG");
	String TX_SEQ_NO = resJson.getString("TX_SEQ_NO");

	String RSLT_NAME = "";
	String RSLT_BIRTHDAY = "";
	String RSLT_SEX_CD = "";
	String RSLT_NTV_FRNR_CD = "";
	
	String DI = "";
	String CI = "";
	String CI2 = "";
	String CI_UPDATE = "";
	String VSSN = "";
	
	String RETURN_MSG = "";
	if(resJson.has("RETURN_MSG")) RETURN_MSG =  resJson.getString("RETURN_MSG");
	
	if ("T000".equals(RSLT_CD)){
		RSLT_NAME = resJson.getString("RSLT_NAME");
		RSLT_BIRTHDAY = resJson.getString("RSLT_BIRTHDAY");
		RSLT_SEX_CD = resJson.getString("RSLT_SEX_CD");
		RSLT_NTV_FRNR_CD = resJson.getString("RSLT_NTV_FRNR_CD");
		
		DI = resJson.getString("DI");
		CI = resJson.getString("CI");
		CI2 = resJson.getString("CI2");
		CI_UPDATE = resJson.getString("CI_UPDATE");
		VSSN = resJson.getString("VSSN");
		
		
		//세션 생성
		session = request.getSession(true);
		session.setAttribute("ss_m_name", RSLT_NAME);
		session.setAttribute("ss_m_dupinfo", DI);
		session.setAttribute("ss_m_coinfo", CI);
		
		if("M".equals(RSLT_SEX_CD)){
			session.setAttribute("ss_m_sex", "0");	//남:0, 여:1 로 변환
		}else {
			session.setAttribute("ss_m_sex", "1");
		}
		
		Calendar cal = Calendar.getInstance();
		int birth_year = Integer.parseInt( RSLT_BIRTHDAY.substring(0, 4) );
		int nowyear = cal.get(Calendar.YEAR);
		String age = Integer.toString( nowyear - birth_year );
		session.setAttribute("ss_m_age", age);
		
		session.setAttribute("ss_m_birth", RSLT_BIRTHDAY);
		
		String prepage = request.getParameter("prepage");
		if(prepage == null){prepage = "/";}
		String v = Func.get_idx_add();
		if( prepage.indexOf( "?" ) >= 0 ){
			prepage += "&v="+v;
		}else{
			prepage += "?v="+v;
		}
		//out.print("prepage="+prepage);
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
		out.println("<script>alert('본인인증실패 : " + RSLT_CD + " : " + RSLT_MSG + "');</script>");
	}
%>