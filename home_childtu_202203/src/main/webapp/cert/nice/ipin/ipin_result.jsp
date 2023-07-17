<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" import="Kisinfo.Check.IPIN2Client" %>

<%

	/********************************************************************************************************************************************
		NICE������ Copyright(c) KOREA INFOMATION SERVICE INC. ALL RIGHTS RESERVED
		
		���񽺸� : �����ֹι�ȣ���� (IPIN) ����
		�������� : �����ֹι�ȣ���� (IPIN) ��� ������
	*********************************************************************************************************************************************/
	//String sSiteCode				= "I896";			// IPIN ���� ����Ʈ �ڵ�		(NICE���������� �߱��� ����Ʈ�ڵ�)
	//String sSitePw					= "Tjdtj896";			// IPIN ���� ����Ʈ �н�����	(NICE���������� �߱��� ����Ʈ�н�����)
	//String sSiteCode				= "EB43";			// IPIN ���� ����Ʈ �ڵ�		(NICE���������� �߱��� ����Ʈ�ڵ�)
	//String sSitePw					= "wjdqhrhksfl3194*";			// IPIN ���� ����Ʈ �н�����	(NICE���������� �߱��� ����Ʈ�н�����)
	String sSiteCode				= "M306";			// IPIN ���� ����Ʈ �ڵ�		(NICE���������� �߱��� ����Ʈ�ڵ�)
	String sSitePw					= "39371889";			// IPIN ���� ����Ʈ �н�����	(NICE���������� �߱��� ����Ʈ�н�����)
	
	
	// ����� ���� �� CP ��û��ȣ�� ��ȣȭ�� ����Ÿ�Դϴ�.
    String sResponseData = requestReplace(request.getParameter("enc_data"), "encodeData");
    
    // CP ��û��ȣ : ipin_main.jsp ���� ���� ó���� ����Ÿ
    String sCPRequest = (String)session.getAttribute("CPREQUEST");
    
    
    // ��ü ����
    IPIN2Client pClient = new IPIN2Client();
	
	
	/*
	�� ��ȣȭ �Լ� ����  ��������������������������������������������������������������������������������������������������������������������
		Method �����(iRtn)�� ����, ���μ��� ���࿩�θ� �ľ��մϴ�.
		
		fnResponse �Լ��� ��� ����Ÿ�� ��ȣȭ �ϴ� �Լ��̸�,
		'sCPRequest'���� �߰��� �����ø� CP��û��ȣ ��ġ���ε� Ȯ���ϴ� �Լ��Դϴ�. (���ǿ� ���� sCPRequest ����Ÿ�� ����)
		
		���� �ͻ翡�� ���ϴ� �Լ��� �̿��Ͻñ� �ٶ��ϴ�.
	������������������������������������������������������������������������������������������������������������������������������������������
	*/
	int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData);
	//int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData, sCPRequest);
	
	String sRtnMsg				= "";			// ó����� �޼���
	String sVNumber				= pClient.getVNumber();			// �����ֹι�ȣ (13�ڸ��̸�, ���� �Ǵ� ���� ����)
	String sName				= pClient.getName();			// �̸�
	String sDupInfo				= pClient.getDupInfo();			// �ߺ����� Ȯ�ΰ� (DI - 64 byte ������)
	String sAgeCode				= pClient.getAgeCode();			// ���ɴ� �ڵ� (���� ���̵� ����)
	String sGenderCode			= pClient.getGenderCode();		// ���� �ڵ� (���� ���̵� ����)
	String sBirthDate			= pClient.getBirthDate();		// ������� (YYYYMMDD)
	String sNationalInfo		= pClient.getNationalInfo();	// ��/�ܱ��� ���� (���� ���̵� ����)
	String sCPRequestNum		= pClient.getCPRequestNO();		// CP ��û��ȣ
	
	String sCoInfo1				= pClient.getCoInfo1();			// �������� Ȯ�ΰ� (CI - 88 byte ������)
	String sCIUpdate			= pClient.getCIUpdate();		// CI ��������
	
	// Method ������� ���� ó������
	if (iRtn == 1)
	{
	
		/*
			������ ���� ����� ������ ������ �� �ֽ��ϴ�.
			����ڿ��� �����ִ� ������, '�̸�' ����Ÿ�� ���� �����մϴ�.
		
			����� ������ �ٸ� ���������� �̿��Ͻ� ��쿡��
			������ ���Ͽ� ��ȣȭ ����Ÿ(sResponseData)�� ����Ͽ� ��ȣȭ �� �̿��Ͻǰ��� �����մϴ�. (���� �������� ���� ó�����)
			
			����, ��ȣȭ�� ������ ����ؾ� �ϴ� ��쿣 ����Ÿ�� ������� �ʵ��� ������ �ּ���. (����ó�� ����)
			form �±��� hidden ó���� ����Ÿ ���� ������ �����Ƿ� �������� �ʽ��ϴ�.
		*/
		/*
		out.println("�����ֹι�ȣ : " + sVNumber + "<BR>");
		out.println("�̸� : " + sName + "<BR>");
		out.println("�ߺ����� Ȯ�ΰ� (DI) : " + sDupInfo + "<BR>");
		out.println("���ɴ� �ڵ� : " + sAgeCode + "<BR>");
		out.println("���� �ڵ� : " + sGenderCode + "<BR>");
		out.println("������� : " + sBirthDate + "<BR>");
		out.println("��/�ܱ��� ���� : " + sNationalInfo + "<BR>");
		out.println("CP ��û��ȣ : " + sCPRequestNum + "<BR>");
		//out.println("����Ȯ�� ���� : " + sAuthInfo + "<BR>");
		out.println("�������� Ȯ�ΰ� (CI) : " + sCoInfo1 + "<BR>");
		out.println("CI �������� : " + sCIUpdate + "<BR>");
		out.println("***** ��ȣȭ �� ������ �������� Ȯ���� �ֽñ� �ٶ��ϴ�.<BR><BR><BR><BR>");
		*/
		sRtnMsg = "���� ó���Ǿ����ϴ�.";
		
	}
	else if (iRtn == -1 || iRtn == -4)
	{
		sRtnMsg =	"iRtn ��, ���� ȯ�������� ��Ȯ�� Ȯ���Ͽ� �ֽñ� �ٶ��ϴ�.";
	}
	else if (iRtn == -6)
	{
		sRtnMsg =	"���� �ѱ� charset ������ euc-kr �� ó���ϰ� ������, euc-kr �� ���ؼ� ����� �ֽñ� �ٶ��ϴ�.<BR>" +
					"�ѱ� charset ������ ��Ȯ�ϴٸ� ..<BR><B>iRtn ��, ���� ȯ�������� ��Ȯ�� Ȯ���Ͽ� ���Ϸ� ��û�� �ֽñ� �ٶ��ϴ�.</B>";
	}
	else if (iRtn == -9)
	{
		sRtnMsg = "�Է°� ���� : fnResponse �Լ� ó����, �ʿ��� �Ķ���Ͱ��� ������ ��Ȯ�ϰ� �Է��� �ֽñ� �ٶ��ϴ�.";
	}
	else if (iRtn == -12)
	{
		sRtnMsg = "CP ��й�ȣ ����ġ : IPIN ���� ����Ʈ �н����带 Ȯ���� �ֽñ� �ٶ��ϴ�.";
	}
	else if (iRtn == -13)
	{
		sRtnMsg = "CP ��û��ȣ ����ġ : ���ǿ� ���� sCPRequest ����Ÿ�� Ȯ���� �ֽñ� �ٶ��ϴ�.";
	}
	else
	{
		sRtnMsg = "iRtn �� Ȯ�� ��, NICE������ ���� ����ڿ��� ������ �ּ���.";
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
if(iRtn == 1){
	
	session = request.getSession(true);
	session.setAttribute("ss_m_name", sName);
	session.setAttribute("ss_m_dupinfo", sDupInfo);
	session.setAttribute("ss_m_coinfo", sCoInfo1);
	
	if("0".equals(sGenderCode)){
		session.setAttribute("ss_m_sex", "1");
	}else if("1".equals(sGenderCode)){
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
	<title>��������</title>
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
	<p class="tit">�������� �Ϸ�</p>
	<p class="btn_wrap">
		<a href="javascript:;" onclick="javascript:self.close();" class="btn">�ݱ�</a>
	</p>
</div>
</body>
</html>
<%
}else{
	out.println("<script>alert('������������ : " + sRtnMsg + "');</script>");
}
%>