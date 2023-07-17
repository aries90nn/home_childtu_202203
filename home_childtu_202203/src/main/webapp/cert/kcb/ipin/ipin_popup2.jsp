<%@
page contentType = "text/html;charset=utf-8"%><%@
page import="kcb.org.json.*" %><%@
include file="./inc_config.jsp" %><%
    //**************************************************************************
	// 파일명 : ipin_popup2.jsp
	// - 팝업페이지
	// 아이핀 서비스 인증페이지 호출 화면
    //
    // ※주의
    // 	실제 운영시에는 화면에 보여지는 데이터를 삭제하여 주시기 바랍니다.
    // 	방문자에게 사이트데이터가 노출될 수 있습니다.
    //**************************************************************************

    //' UTF-8 환경의 경우 주석 해제 + 전체 페이지 상단 charset, pageEncoding 및 파일인코딩 변경 필요
	request.setCharacterEncoding("UTF-8");


	String prepage = request.getParameter("prepage");
	if(prepage == null){prepage = "/";}

	
	/**************************************************************************
	 * OkCert3 아이핀 서비스 파라미터
	 **************************************************************************/
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	//' 회원사 사이트명, URL
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String SITE_NAME = cpg_site_name; 		// 요청사이트명 
	String SITE_URL = cfg_site_url;

	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' KCB로부터 부여받은 회원사코드(아이디) 설정 (12자리) 
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	//String CP_CD = "V06880000000";	// 회원사코드
	String CP_CD = cfg_cp_cd;	// 회원사코드
	//session.setAttribute("IPIN_CP_CD", CP_CD);

	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 리턴 URL 설정 : 최대 1000 바이트.
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	//' opener(popup1)의 도메인과 일치하도록 설정해야 함. 
	//' (http://www.test.co.kr과 http://test.co.kr는 다른 도메인으로 인식하며, http 및 https도 일치해야 함)
	// 인증 완료 후 리턴될 URL (도메인 포함 full path)
	String url = request.getRequestURL().toString();
    String domain = url.substring(0,url.indexOf("/",8));
	
	String RTN_URL = domain+"/cert/kcb/ipin/ipin_popup3.jsp?prepage="+java.net.URLEncoder.encode(prepage,"UTF-8");

	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 인증요청사유코드
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String RQST_CAUS_CD = "00";
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 리턴메시지 (공백가능. returnUrl에서 같이 전달받고자 하는 값이 있다면 설정.)
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String RETURN_MSG = "";
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 타겟 및 팝업URL : 운영/테스트 전환시 변경 필요
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String target = cfg_target; // 테스트="TEST", 운영="PROD"
	//String popupUrl = "https://tmpin.ok-name.co.kr:5443/CommonSvl";// 테스트 URL
	String popupUrl = "https://ipin.ok-name.co.kr/CommonSvl";// 운영 URL
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 라이센스 파일
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	//String license = "C:\\okcert3_license\\" + CP_CD + "_TIS_01_" + target + "_AES_license.dat";
	String license = cfg_license;
		
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 서비스명 (고정값)
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String svcName = "TIS_IPIN_POPUP_START";
	
	/**************************************************************************
	okcert3 요청 정보
	**************************************************************************/
	JSONObject reqJson = new JSONObject();
	reqJson.put("RTN_URL", RTN_URL);
	reqJson.put("SITE_NAME", SITE_NAME);
	reqJson.put("SITE_URL", SITE_URL);
	reqJson.put("RQST_CAUS_CD", RQST_CAUS_CD);
	
	reqJson.put("RETURN_MSG", RETURN_MSG);
	
	//' 거래일련번호는 기본적으로 모듈 내에서 자동 채번되고 채번된 값을 리턴해줌.
	//'	회원사가 직접 채번하길 원하는 경우에만 아래 코드를 주석 해제 후 사용.
	//' 각 거래마다 중복 없는 String 을 생성하여 입력. 최대길이:20
	//reqJson.put("TX_SEQ_NO", "123456789012345"); 
	
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
    String RSLT_MSG = resJson.getString("RSLT_MSG");
    //if(resJson.has("TX_SEQ_NO")) String TX_SEQ_NO = resJson.getString("TX_SEQ_NO"); // 필요 시 거래 일련 번호 에 대하여 DB저장 등의 처리
    String MDL_TKN = "";
    
	boolean succ = false;
	
    if ("T300".equals(RSLT_CD) && resJson.has("MDL_TKN") ) {
            MDL_TKN = resJson.getString("MDL_TKN");
			succ = true;
    }
	
%>

<html>
<title>KCB 아이핀 서비스 샘플 2</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function request(){
		var eForm = document.getElementById("form1");
		eForm.action = "<%=popupUrl%>";
		eForm.method = "post";
	
		eForm.submit();
	}
</script>
</head>

<body>
	<form id="form1" name="form1">
	<!-- 인증 요청 정보 -->
	<!--// 필수 항목 -->
	<input type="hidden" name="tc" value="kcb.tis.ti.cmd.LoginRPCert3Cmd"/>	<!-- 변경불가-->
	<input type="hidden" name="cpCd" value="<%=CP_CD%>">	<!-- 회원사코드 -->
	<input type="hidden" name="mdlTkn" value="<%=MDL_TKN%>">	<!-- 토큰 --> 
	<!-- 필수 항목 //-->	
	</form>
</body>
<%
	if (succ) {
		//인증요청
		out.println("<script>request();</script>");
	} else {
		//요청 실패 페이지로 리턴
		out.println("<script>alert('" + RSLT_CD + " : " + RSLT_MSG + "'); self.close(); </script>");
	}
%>
</html>


