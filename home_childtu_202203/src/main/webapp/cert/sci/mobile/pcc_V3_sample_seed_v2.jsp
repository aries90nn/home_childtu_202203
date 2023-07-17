<%
/**************************************************************************************************************************
* Program Name  : 본인실명확인 요청 Sample JSP (Real)  
* File Name     : pccName_sample_seed.jsp
* Comment       : 
* History       : 
*
**************************************************************************************************************************/
%>
<%
    response.setHeader("Pragma", "no-cache" );
    response.setDateHeader("Expires", 0);
    response.setHeader("Pragma", "no-store");
    response.setHeader("Cache-Control", "no-cache" );
%>
<%@ page  contentType = "text/html;charset=ksc5601"%>
<%@ page import ="java.util.*,java.text.SimpleDateFormat"%>
<%
	//날짜 생성
	Calendar today = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	String day = sdf.format(today.getTime());
	
	String prepage = request.getParameter("prepage");
	if(prepage == null){prepage = "/";}
	
	String url = request.getRequestURL().toString();
	String domain = url.substring(0,url.indexOf("/",8));
	
    String id       = "SGPK001";                               // 본인실명확인 회원사 아이디
	/*
	※ srvNo 주의사항
	submit하기 바로 직전의 요청페이지( EX : http:localhost:8080/pcc_V3_sample_seed_v2.jsp )를
	Bizsiren사이트에 등록 후 나오는 서비스번호 숫자6자리를 셋팅해주십시오(결과페이지 아님!)
	*/
    String srvNo    = "010002";                            // 본인실명확인 서비스번호
    String reqNum   = "123456789";                           // 본인실명확인 요청번호 (sample 페이지와 result 페이지가  동일하지 않으면 결과페이지 복호화 시 에러)
	String exVar    = "0000000000000000";                                       // 복호화용 임시필드
    String retUrl   = "32"+domain+"/cert/sci/mobile/pcc_V3_popup_seed_v2.jsp?prepage="+java.net.URLEncoder.encode(prepage,"UTF-8");    // 본인실명확인 결과수신 URL
	String certDate	= day;                         // 본인실명확인 요청시간
	String certGb	= "H";                           // 본인실명확인 본인확인 인증수단
	String addVar	= "";                           // 본인실명확인 추가 파라메터

    //01. 암호화 모듈 선언
	com.sci.v2.pccv2.secu.SciSecuManager seed  = new com.sci.v2.pccv2.secu.SciSecuManager();

	//02. 1차 암호화
	String encStr = "";
	String reqInfo      = id+"^"+srvNo+"^"+reqNum+"^"+certDate+"^"+certGb+"^"+addVar+"^"+exVar;  // 데이터 암호화

	seed.setInfoPublic(id,"5608C6CA30810FDECAD35144C695B41E");  //bizsiren.com > 회원사전용 로그인후 확인. 

	encStr               = seed.getEncPublic(reqInfo);

	//03. 위변조 검증 값 생성
	com.sci.v2.pccv2.secu.hmac.SciHmac hmac = new com.sci.v2.pccv2.secu.hmac.SciHmac();
	String hmacMsg  = seed.getEncReq(encStr,"HMAC");

	//03. 2차 암호화
	reqInfo  = seed.getEncPublic(encStr + "^" + hmacMsg + "^" + "0000000000000000");  //2차암호화

	//04. 회원사 ID 처리를 위한 암호화
	reqInfo = seed.EncPublic(reqInfo + "^" + id + "^"  + "00000000");

%>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<html>
<head>
<title>본인실명확인 서비스 Sample 화면</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta name="robots" content="noindex,nofollow" />

<script language=javascript>
	onload = function(){
		document.getElementById("reqPCCForm").submit();
	}


    var PCC_window; 

    function openPCCWindow(){ 
        var PCC_window = window.open('', 'PCCV3Window', 'width=400, height=630, resizable=1, scrollbars=no, status=0, titlebar=0, toolbar=0, left=300, top=200' );
       
	   // iframe형식으로 개발하시지 말아주십시오. iframe으로 개발 시 나오는 문제는 개발지원해드리지 않습니다.
       if(PCC_window == null){ 
			 alert(" ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.");
        }

        //창을 오픈할때 크롬 및 익스플로어 양쪽 다 테스트 하시길 바랍니다.
        document.reqPCCForm.action = 'https://pcc.siren24.com/pcc_V3/jsp/pcc_V3_j10_v2.jsp';
        document.reqPCCForm.target = 'PCCV3Window';

		return true;
    }
    
    
</script>
</head>

<body bgcolor="#FFFFFF" topmargin=0 leftmargin=0 marginheight=0 marginwidth=0  background="/images/mem_back.gif" >

<!-- 본인실명확인서비스 요청 form --------------------------->
<form id="reqPCCForm" name="reqPCCForm" method="post" action = "https://pcc.siren24.com/pcc_V3/jsp/pcc_V3_j10_v2.jsp">
    <input type="hidden" name="reqInfo"    value = "<%=reqInfo%>">
    <input type="hidden" name="retUrl"      value = "<%=retUrl%>">
    <input type="hidden" name="verSion"	value = "2">				 <!--모듈 버전정보-->
    <input type="submit" value="본인확인서비스V3 요청" style="display:none;">	
</form>


</BODY>
</HTML>
