<%
/**************************************************************************************************************************
* Program Name  : ����Ȯ�� �߰���Ȱ ��������� 
* File Name     : pcc_V3_popup_seed
* Comment       :  �ش� �������� �߰���Ȱ �������̱�� ��û���������� �����������  pcc_V3_result_seed �� �����ؼ� retInfo �� �޾Ƶ� �������.
* History       : 
**************************************************************************************************************************/
%>

<%@ page  contentType = "text/html;charset=ksc5601"%>
<%
	response.setHeader("Cache-Control","no-cache");     
	response.setHeader("Pragma","no-cache");
%>

<%
	String prepage = request.getParameter("prepage");
	if(prepage == null){prepage = "/";}

	String param = "";
	//�ݵ�� get�� post ��� �� �� �������ְ� ����س�����.
	String enc_retInfo   = request.getParameter("retInfo").trim(); //  �ȹ޾����� ȸ���簡 ������ ���ɼ��������� ��Ʈ��ũ in,out�� ����ʿ�. ���߹�������

	param= "?retInfo="+enc_retInfo;
	param+= "&prepage="+java.net.URLEncoder.encode(prepage,"UTF-8");
%>
<html>
<head>
<script language="JavaScript">
	function end() {
		window.opener.location.href = './pcc_V3_result_seed_v2.jsp<%=param%>';
		self.close();
	}
</script>
</head>
<body onload="javascript:end()">
</body>
</html>