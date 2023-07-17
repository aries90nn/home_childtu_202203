<%
/**************************************************************************************************************************
* Program Name  : ���νǸ�Ȯ�� ��û Sample JSP (Real)  
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
	//��¥ ����
	Calendar today = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	String day = sdf.format(today.getTime());
	
	String prepage = request.getParameter("prepage");
	if(prepage == null){prepage = "/";}
	
	String url = request.getRequestURL().toString();
	String domain = url.substring(0,url.indexOf("/",8));
	
    String id       = "SGPK001";                               // ���νǸ�Ȯ�� ȸ���� ���̵�
	/*
	�� srvNo ���ǻ���
	submit�ϱ� �ٷ� ������ ��û������( EX : http:localhost:8080/pcc_V3_sample_seed_v2.jsp )��
	Bizsiren����Ʈ�� ��� �� ������ ���񽺹�ȣ ����6�ڸ��� �������ֽʽÿ�(��������� �ƴ�!)
	*/
    String srvNo    = "010002";                            // ���νǸ�Ȯ�� ���񽺹�ȣ
    String reqNum   = "123456789";                           // ���νǸ�Ȯ�� ��û��ȣ (sample �������� result ��������  �������� ������ ��������� ��ȣȭ �� ����)
	String exVar    = "0000000000000000";                                       // ��ȣȭ�� �ӽ��ʵ�
    String retUrl   = "32"+domain+"/cert/sci/mobile/pcc_V3_popup_seed_v2.jsp?prepage="+java.net.URLEncoder.encode(prepage,"UTF-8");    // ���νǸ�Ȯ�� ������� URL
	String certDate	= day;                         // ���νǸ�Ȯ�� ��û�ð�
	String certGb	= "H";                           // ���νǸ�Ȯ�� ����Ȯ�� ��������
	String addVar	= "";                           // ���νǸ�Ȯ�� �߰� �Ķ����

    //01. ��ȣȭ ��� ����
	com.sci.v2.pccv2.secu.SciSecuManager seed  = new com.sci.v2.pccv2.secu.SciSecuManager();

	//02. 1�� ��ȣȭ
	String encStr = "";
	String reqInfo      = id+"^"+srvNo+"^"+reqNum+"^"+certDate+"^"+certGb+"^"+addVar+"^"+exVar;  // ������ ��ȣȭ

	seed.setInfoPublic(id,"5608C6CA30810FDECAD35144C695B41E");  //bizsiren.com > ȸ�������� �α����� Ȯ��. 

	encStr               = seed.getEncPublic(reqInfo);

	//03. ������ ���� �� ����
	com.sci.v2.pccv2.secu.hmac.SciHmac hmac = new com.sci.v2.pccv2.secu.hmac.SciHmac();
	String hmacMsg  = seed.getEncReq(encStr,"HMAC");

	//03. 2�� ��ȣȭ
	reqInfo  = seed.getEncPublic(encStr + "^" + hmacMsg + "^" + "0000000000000000");  //2����ȣȭ

	//04. ȸ���� ID ó���� ���� ��ȣȭ
	reqInfo = seed.EncPublic(reqInfo + "^" + id + "^"  + "00000000");

%>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<html>
<head>
<title>���νǸ�Ȯ�� ���� Sample ȭ��</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta name="robots" content="noindex,nofollow" />

<script language=javascript>
	onload = function(){
		document.getElementById("reqPCCForm").submit();
	}


    var PCC_window; 

    function openPCCWindow(){ 
        var PCC_window = window.open('', 'PCCV3Window', 'width=400, height=630, resizable=1, scrollbars=no, status=0, titlebar=0, toolbar=0, left=300, top=200' );
       
	   // iframe�������� �����Ͻ��� �����ֽʽÿ�. iframe���� ���� �� ������ ������ ���������ص帮�� �ʽ��ϴ�.
       if(PCC_window == null){ 
			 alert(" �� ������ XP SP2 �Ǵ� ���ͳ� �ͽ��÷η� 7 ������� ��쿡�� \n    ȭ�� ��ܿ� �ִ� �˾� ���� �˸����� Ŭ���Ͽ� �˾��� ����� �ֽñ� �ٶ��ϴ�. \n\n�� MSN,����,���� �˾� ���� ���ٰ� ��ġ�� ��� �˾������ ���ֽñ� �ٶ��ϴ�.");
        }

        //â�� �����Ҷ� ũ�� �� �ͽ��÷ξ� ���� �� �׽�Ʈ �Ͻñ� �ٶ��ϴ�.
        document.reqPCCForm.action = 'https://pcc.siren24.com/pcc_V3/jsp/pcc_V3_j10_v2.jsp';
        document.reqPCCForm.target = 'PCCV3Window';

		return true;
    }
    
    
</script>
</head>

<body bgcolor="#FFFFFF" topmargin=0 leftmargin=0 marginheight=0 marginwidth=0  background="/images/mem_back.gif" >

<!-- ���νǸ�Ȯ�μ��� ��û form --------------------------->
<form id="reqPCCForm" name="reqPCCForm" method="post" action = "https://pcc.siren24.com/pcc_V3/jsp/pcc_V3_j10_v2.jsp">
    <input type="hidden" name="reqInfo"    value = "<%=reqInfo%>">
    <input type="hidden" name="retUrl"      value = "<%=retUrl%>">
    <input type="hidden" name="verSion"	value = "2">				 <!--��� ��������-->
    <input type="submit" value="����Ȯ�μ���V3 ��û" style="display:none;">	
</form>


</BODY>
</HTML>
