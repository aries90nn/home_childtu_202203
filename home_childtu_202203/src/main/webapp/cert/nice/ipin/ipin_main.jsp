<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page language="java" import="Kisinfo.Check.IPIN2Client" %>

<%

	/********************************************************************************************************************************************
		NICE������ Copyright(c) KOREA INFOMATION SERVICE INC. ALL RIGHTS RESERVED
		
		���񽺸� : �����ֹι�ȣ���� (IPIN) ����
		�������� : �����ֹι�ȣ���� (IPIN) ȣ�� ������
	*********************************************************************************************************************************************/
	String prepage = request.getParameter("prepage");
    if(prepage == null){prepage = "/";}
    
    //String sSiteCode				= "I896";			// IPIN ���� ����Ʈ �ڵ�		(NICE���������� �߱��� ����Ʈ�ڵ�)
	//String sSitePw					= "Tjdtj896";			// IPIN ���� ����Ʈ �н�����	(NICE���������� �߱��� ����Ʈ�н�����)
	//String sSiteCode				= "CK60";			// IPIN ���� ����Ʈ �ڵ�		(NICE���������� �߱��� ����Ʈ�ڵ�)
	//String sSitePw					= "2486252asqw!@";			// IPIN ���� ����Ʈ �н�����	(NICE���������� �߱��� ����Ʈ�н�����)
	//String sSiteCode				= "FM91";			// IPIN ���� ����Ʈ �ڵ�		(NICE���������� �߱��� ����Ʈ�ڵ�)
	//String sSitePw					= "yangcheon!23";			// IPIN ���� ����Ʈ �н�����	(NICE���������� �߱��� ����Ʈ�н�����)
	//String sSiteCode				= "EB43";			// IPIN ���� ����Ʈ �ڵ�		(NICE���������� �߱��� ����Ʈ�ڵ�)
	//String sSitePw					= "wjdqhrhksfl3194*";			// IPIN ���� ����Ʈ �н�����	(NICE���������� �߱��� ����Ʈ�н�����)
	String sSiteCode				= "M306";			// IPIN ���� ����Ʈ �ڵ�		(NICE���������� �߱��� ����Ʈ�ڵ�)
	String sSitePw					= "39371889";			// IPIN ���� ����Ʈ �н�����	(NICE���������� �߱��� ����Ʈ�н�����)
	
		
	/*
	�� sReturnURL ������ ���� ����  ����������������������������������������������������������������������������������������������������������
		NICE������ �˾����� �������� ����� ������ ��ȣȭ�Ͽ� �ͻ�� �����մϴ�.
		���� ��ȣȭ�� ��� ����Ÿ�� ���Ϲ����� URL ������ �ּ���.
		
		* URL �� http ���� �Է��� �ּž��ϸ�, �ܺο����� ������ ��ȿ�� �������� �մϴ�.
		* ��翡�� �����ص帰 ���������� ��, ipin_process.jsp �������� ����� ������ ���Ϲ޴� ���� �������Դϴ�.
		
		�Ʒ��� URL �����̸�, �ͻ��� ���� �����ΰ� ������ ���ε� �� ���������� ��ġ�� ���� ��θ� �����Ͻñ� �ٶ��ϴ�.
		�� - http://www.test.co.kr/ipin_process.jsp, https://www.test.co.kr/ipin_process.jsp, https://test.co.kr/ipin_process.jsp
	������������������������������������������������������������������������������������������������������������������������������������������
	*/
	String url = request.getRequestURL().toString();
	String domain = url.substring(0,url.indexOf("/",8));
	//String domain = "localhost:8080";
	String sReturnURL				= domain+"/cert/nice/ipin/ipin_process.jsp?prepage="+java.net.URLEncoder.encode(prepage,"UTF-8");
	//out.print("sReturnURL="+sReturnURL);
	
	/*
	�� sCPRequest ������ ���� ����  ����������������������������������������������������������������������������������������������������������
		[CP ��û��ȣ]�� �ͻ翡�� ����Ÿ�� ���Ƿ� �����ϰų�, ��翡�� ������ ���� ����Ÿ�� ������ �� �ֽ��ϴ�.
		
		CP ��û��ȣ�� ���� �Ϸ� ��, ��ȣȭ�� ��� ����Ÿ�� �Բ� �����Ǹ�
		����Ÿ ������ ���� �� Ư�� ����ڰ� ��û�� ������ Ȯ���ϱ� ���� �������� �̿��Ͻ� �� �ֽ��ϴ�.
		
		���� �ͻ��� ���μ����� �����Ͽ� �̿��� �� �ִ� ����Ÿ�̱⿡, �ʼ����� �ƴմϴ�.
	������������������������������������������������������������������������������������������������������������������������������������������
	*/
	String sCPRequest				= "";
	
	
	
	// ��ü ����
	IPIN2Client pClient = new IPIN2Client();
	
	
	// �ռ� ����帰 �ٿͰ���, CP ��û��ȣ�� ������ ����� ���� �Ʒ��� ���� ������ �� �ֽ��ϴ�.
	sCPRequest = pClient.getRequestNO(sSiteCode);
	
	// CP ��û��ȣ�� ���ǿ� �����մϴ�.
	// ���� ������ ������ ������ ipin_result.jsp ���������� ����Ÿ ������ ������ ���� Ȯ���ϱ� �����Դϴ�.
	// �ʼ������� �ƴϸ�, ������ ���� �ǰ�����Դϴ�.
	session.setAttribute("CPREQUEST" , sCPRequest);
	
	
	// Method �����(iRtn)�� ����, ���μ��� ���࿩�θ� �ľ��մϴ�.
	int iRtn = pClient.fnRequest(sSiteCode, sSitePw, sCPRequest, sReturnURL);
	
	String sEncData					= "";			// ��ȣȭ �� ����Ÿ
	String sRtnMsg					= "";			// ó����� �޼���
	
	// Method ������� ���� ó������
	if (iRtn == 0)
	{
	
		// fnRequest �Լ� ó���� ��ü������ ��ȣȭ�� �����͸� �����մϴ�.
		// ����� ��ȣȭ�� ����Ÿ�� ��� �˾� ��û��, �Բ� �����ּž� �մϴ�.
		sEncData = pClient.getCipherData();
		
		sRtnMsg = "���� ó���Ǿ����ϴ�.";
	
	}
	else if (iRtn == -1 || iRtn == -2)
	{
		sRtnMsg =	"������ �帰 ���� ��� ��, �ͻ� ����ȯ�濡 �´� ����� �̿��� �ֽñ� �ٶ��ϴ�.<BR>" +
					"�ͻ� ����ȯ�濡 �´� ����� ���ٸ� ..<BR><B>iRtn ��, ���� ȯ�������� ��Ȯ�� Ȯ���Ͽ� ���Ϸ� ��û�� �ֽñ� �ٶ��ϴ�.</B>";
	}
	else if (iRtn == -9)
	{
		sRtnMsg = "�Է°� ���� : fnRequest �Լ� ó����, �ʿ��� 4���� �Ķ���Ͱ��� ������ ��Ȯ�ϰ� �Է��� �ֽñ� �ٶ��ϴ�.";
	}
	else
	{
		sRtnMsg = "iRtn �� Ȯ�� ��, NICE������ ���� ����ڿ��� ������ �ּ���.";
	}

%>

<html>
<head>
	<title>NICE������ �����ֹι�ȣ ����</title>
	
	<script language='javascript'>
	window.name ="Parent_window";
	
	function fnPopup(){
		window.open('', 'popupIPIN2', 'width=450, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
		document.form_ipin.target = "popupIPIN2";
		document.form_ipin.action = "https://cert.vno.co.kr/ipin.cb";
		document.form_ipin.submit();
	}
	
	onload = function(){
		document.getElementById("form_ipin").submit();
	}
	</script>
</head>

<body>

<form id="form_ipin" name="form_ipin" method="post" action="https://cert.vno.co.kr/ipin.cb">
	<input type="hidden" name="m" value="pubmain">						<!-- �ʼ� ����Ÿ��, �����Ͻø� �ȵ˴ϴ�. -->
    <input type="hidden" name="enc_data" value="<%= sEncData %>">		<!-- ������ ��ü������ ��ȣȭ �� ����Ÿ�Դϴ�. -->
    
    <!-- ��ü���� ����ޱ� ���ϴ� ����Ÿ�� �����ϱ� ���� ����� �� ������, ������� ����� �ش� ���� �״�� �۽��մϴ�.
    	 �ش� �Ķ���ʹ� �߰��Ͻ� �� �����ϴ�. -->
    <input type="hidden" name="param_r1" value="">
    <input type="hidden" name="param_r2" value="">
    <input type="hidden" name="param_r3" value="">
    
</form>



</body>
</html>