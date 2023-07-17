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
<%@ page import = "java.util.*" %> 
<%
	
    String id       = request.getParameter("id");                               // ���νǸ�Ȯ�� ȸ���� ���̵�
	/*
	�� srvNo ���ǻ���
	submit�ϱ� �ٷ� ������ ��û������( EX : http:localhost:8080/pcc_V3_sample_seed_v2.jsp )��
	Bizsiren����Ʈ�� ��� �� ������ ���񽺹�ȣ ����6�ڸ��� �������ֽʽÿ�(��������� �ƴ�!)
	*/
    String srvNo    = request.getParameter("srvNo");                            // ���νǸ�Ȯ�� ���񽺹�ȣ
    String reqNum   = request.getParameter("reqNum");                           // ���νǸ�Ȯ�� ��û��ȣ (sample �������� result ��������  �������� ������ ��������� ��ȣȭ �� ����)
	String exVar    = "0000000000000000";                                       // ��ȣȭ�� �ӽ��ʵ�
    String retUrl   = request.getParameter("retUrl");                           // ���νǸ�Ȯ�� ������� URL
	String certDate	= request.getParameter("certDate");                         // ���νǸ�Ȯ�� ��û�ð�
	String certGb	= request.getParameter("certGb");                           // ���νǸ�Ȯ�� ����Ȯ�� ��������
	String addVar	= request.getParameter("addVar");                           // ���νǸ�Ȯ�� �߰� �Ķ����

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
<style>
   body,p,ol,ul,td
   {
	 font-family: ����;
	 font-size: 12px;   
   } 
   
   a:link { size:9px;color:#000000;text-decoration: none; line-height: 12px}
   a:visited { size:9px;color:#555555;text-decoration: none; line-height: 12px}
   a:hover { color:#ff9900;text-decoration: none; line-height: 12px}

   .style1 {
		color: #6b902a;
		font-weight: bold;
	}
	.style2 {
	    color: #666666
	}
	.style3 {
		color: #3b5d00;
		font-weight: bold;
	}
</style>

<script language=javascript>  
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

<center>
<br><br><br><br><br><br>
<span class="style1">���νǸ�Ȯ�μ��� ��ûȭ�� Sample�Դϴ�.</span><br>
<br><br>
<table cellpadding=1 cellspacing=1>    
    <tr>
        <td align=center>ȸ������̵�</td>
        <td align=left><%=id%></td>
    </tr>
    <tr>
        <td align=center>���񽺹�ȣ</td>
        <td align=left><%=srvNo%></td>
    </tr>
    <tr>
        <td align=center>��û��ȣ</td>
        <td align=left><%=reqNum%></td>
    </tr>
	<tr>
        <td align=center>��������</td>
        <td align=left><%=certGb%></td>
    </tr>
	<tr>
        <td align=center>��û�ð�</td>
        <td align=left><%=certDate%></td>
    </tr>
	<tr>
        <td align=center>�߰��Ķ����</td>
        <td align=left><%=addVar%></td>
    </tr>    
    <tr>
        <td align=center>&nbsp</td>
        <td align=left>&nbsp</td>
    </tr>
    <tr width=100>
        <td align=center>��û����(��ȣȭ)</td>
        <td align=left>
            <%=reqInfo%>
        </td>
    </tr>
    <tr>
        <td align=center>�������URL</td>
        <td align=left><%=retUrl%></td>
    </tr>
	<tr>
        <td align=center>hmacMsg</td>
        <td align=left><%=hmacMsg%></td>
    </tr>
	<tr>
        <td align=center>encStr</td>
        <td align=left><%=encStr%></td>
    </tr>
</table>

<!-- ���νǸ�Ȯ�μ��� ��û form --------------------------->
<form name="reqPCCForm" method="post" action = "" onsubmit="return openPCCWindow()">
    <input type="hidden" name="reqInfo"    value = "<%=reqInfo%>">
    <input type="hidden" name="retUrl"      value = "<%=retUrl%>">
    <input type="hidden" name="verSion"	value = "2">				 <!--��� ��������-->
    <input type="submit" value="����Ȯ�μ���V3 ��û" >	
</form>





<BR>
<BR>
<!--End ���νǸ�Ȯ�μ��� ��û form ----------------------->


<br>
<br>
	<table width="450" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="130"><a href=http://www.siren24.com/v2alimi/comm/jsp/v2alimiAuth.jsp?id=SIR005&svc_seq=01 target=blank><img src="http://www.siren24.com/mysiren/img/layout/logo.gif" width="122" height="41" border=0></a></td>
        <td width="320"><span class="style2">�� ����Ʈ�� ����ſ�������(��)�� <span class="style3">���ǵ����������</span> �������Ʈ �Դϴ�. Ÿ���� ���Ǹ� �����Ͻ� ��� ���ù��ɿ� ���� ó�� ������ �� �ֽ��ϴ�.</span></td>
      </tr>
    </table>
      <br>
      <br>
    <br>
  �� Sampleȭ���� ���νǸ�Ȯ�μ��� ��ûȭ�� ���߽� ���� �ǵ��� �����ϰ� �ִ� ȭ���Դϴ�.<br>
<p style="color:red"><b> Sample�������� �׽�Ʈ�θ� �����Ű�� �� ���� �����Ʈ�� �ݿ��ϽǶ��� ���÷� �����ǰ��ִ� ���ϸ����δ� ����� ���� �����ֽʽÿ�. </b></p>
  <br>
  <br>
  <br>
</center>
</BODY>
</HTML>
