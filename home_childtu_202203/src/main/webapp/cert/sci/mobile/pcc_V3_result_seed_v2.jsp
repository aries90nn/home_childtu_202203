<%
/**************************************************************************************************************************
* Program Name  : 본인확인 결과 수신 Sample JSP 
* File Name     : pcc_result_seed2.jsp
* Comment       : 
* History       :  
*
**************************************************************************************************************************/
%>

<%@ page  contentType = "text/html;charset=ksc5601"%>
<%@ page import = "java.util.*" %> 
<%
    // 변수 --------------------------------------------------------------------------------
    String retInfo		= "";																// 결과정보

	String id			= "";                                                               //회원사 비즈사이렌아이디
	String name			= "";                                                               //성명
	String sex			= "";																//성별
	String birYMD		= "";																//생년월일
	String fgnGbn		= "";																//내외국인 구분값
	String scCode		= "";																//가상식별번호
    String di			= "";																//DI
    String ci1			= "";																//CI
    String ci2			= "";																//CI
    String civersion    = "";                                                               //CI Version
    
    String reqNum		= "";                                                               // 본인확인 요청번호
    String result		= "";                                                               // 본인확인결과 (Y/N)
    String certDate		= "";                                                               // 검증시간
    String certGb		= "";                                                               // 인증수단
	String cellNo		= "";																// 핸드폰 번호
	String cellCorp		= "";																// 이동통신사
	String addVar		= "";

	//예약 필드
	String ext1			= "";
	String ext2			= "";
	String ext3			= "";
	String ext4			= "";
	String ext5			= "";

	//복화화용 변수
	String encPara		= "";
	String encMsg		= "";
	String msgChk       = "N";  
	
    //-----------------------------------------------------------------------------------------------------------------
    
	reqNum = "123456789"; //sample 페이지의 reqNum과 동일하지 않으면 결과페이지 복호화 시 에러

    try{

        // Parameter 수신 --------------------------------------------------------------------
        retInfo  = request.getParameter("retInfo").trim(); //반드시 get과 post 방식 둘 다 받을수있게 허용해놔야함.


        // 1. 암호화 모듈 (jar) Loading
        com.sci.v2.pccv2.secu.SciSecuManager sciSecuMg = new com.sci.v2.pccv2.secu.SciSecuManager();
		sciSecuMg.setInfoPublic(id,"5608C6CA30810FDECAD35144C695B41E");  //bizsiren.com > 회원사전용 로그인후 확인. 

        // 3. 1차 파싱---------------------------------------------------------------

		retInfo  = sciSecuMg.getDec(retInfo, reqNum);

		// 4. 요청결과 복호화
        String[] aRetInfo1 = retInfo.split("\\^");

		encPara  = aRetInfo1[0];         //암호화된 통합 파라미터
        encMsg   = aRetInfo1[1];    //암호화된 통합 파라미터의 Hash값
		
		String encMsg2   = sciSecuMg.getMsg(encPara);
		
		// 5. 위/변조 검증 ---------------------------------------------------------------

        if(encMsg2.equals(encMsg)){
            msgChk="Y";
        }

		if(msgChk.equals("N")){
%>
		    <script language=javascript>
            alert("HMAC 확인이 필요합니다.");
		    </script>
<%
			return;
		}

        // 복호화 및 위/변조 검증 ---------------------------------------------------------------
		retInfo  = sciSecuMg.getDec(encPara, reqNum);

        String[] aRetInfo = retInfo.split("\\^");
		
        name		= aRetInfo[0];
		birYMD		= aRetInfo[1];
        sex			= aRetInfo[2];        
        fgnGbn		= aRetInfo[3];
        di			= aRetInfo[4];
        ci1			= aRetInfo[5];
        ci2			= aRetInfo[6];
        civersion	= aRetInfo[7];
        reqNum		= aRetInfo[8];
        result		= aRetInfo[9];
        certGb		= aRetInfo[10];
		cellNo		= aRetInfo[11];
		cellCorp	= aRetInfo[12];
        certDate	= aRetInfo[13];
		addVar		= aRetInfo[14];

		//예약 필드
		ext1		= aRetInfo[15];
		ext2		= aRetInfo[16];
		ext3		= aRetInfo[17];
		ext4		= aRetInfo[18];
		ext5		= aRetInfo[19];
		
		
		session = request.getSession(true);
		session.setAttribute("ss_m_name", name);
		session.setAttribute("ss_m_dupinfo", di);
		session.setAttribute("ss_m_coinfo", ci1);
		
		if("F".equals(sex)){
			session.setAttribute("ss_m_sex", "1");	//남:0, 여:1 로 변환 (코라스기준, 자체응용프로그램에서는 개별적으로 수정해서 쓴다)
		}else if("M".equals(sex)){
			session.setAttribute("ss_m_sex", "0");
		}
		
		Calendar cal = Calendar.getInstance();
		int birth_year = Integer.parseInt( birYMD.substring(0, 4) );
		int nowyear = cal.get(Calendar.YEAR);
		String age = Integer.toString( nowyear - birth_year );
		session.setAttribute("ss_m_age", age);
		
		session.setAttribute("ss_m_birth", birYMD);
		
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
	location.href="<%=prepage%>";
}
//]]>
</script>
<body>
<div class="ipin_complete">
	<p class="tit">본인인증 완료</p>
	<p class="btn_wrap">
		<a href="javascript:;" onclick="location.href="<%=prepage%>";" class="btn">확인</a>
	</p>
</div>
</body>
</html>
<%
        // ----------------------------------------------------------------------------------

    }catch(Exception ex){
          System.out.println("[pcc] Receive Error -"+ex.getMessage());
    }
%>