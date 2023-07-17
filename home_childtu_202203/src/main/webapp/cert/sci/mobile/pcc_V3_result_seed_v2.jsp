<%
/**************************************************************************************************************************
* Program Name  : ����Ȯ�� ��� ���� Sample JSP 
* File Name     : pcc_result_seed2.jsp
* Comment       : 
* History       :  
*
**************************************************************************************************************************/
%>

<%@ page  contentType = "text/html;charset=ksc5601"%>
<%@ page import = "java.util.*" %> 
<%
    // ���� --------------------------------------------------------------------------------
    String retInfo		= "";																// �������

	String id			= "";                                                               //ȸ���� ������̷����̵�
	String name			= "";                                                               //����
	String sex			= "";																//����
	String birYMD		= "";																//�������
	String fgnGbn		= "";																//���ܱ��� ���а�
	String scCode		= "";																//����ĺ���ȣ
    String di			= "";																//DI
    String ci1			= "";																//CI
    String ci2			= "";																//CI
    String civersion    = "";                                                               //CI Version
    
    String reqNum		= "";                                                               // ����Ȯ�� ��û��ȣ
    String result		= "";                                                               // ����Ȯ�ΰ�� (Y/N)
    String certDate		= "";                                                               // �����ð�
    String certGb		= "";                                                               // ��������
	String cellNo		= "";																// �ڵ��� ��ȣ
	String cellCorp		= "";																// �̵���Ż�
	String addVar		= "";

	//���� �ʵ�
	String ext1			= "";
	String ext2			= "";
	String ext3			= "";
	String ext4			= "";
	String ext5			= "";

	//��ȭȭ�� ����
	String encPara		= "";
	String encMsg		= "";
	String msgChk       = "N";  
	
    //-----------------------------------------------------------------------------------------------------------------
    
	reqNum = "123456789"; //sample �������� reqNum�� �������� ������ ��������� ��ȣȭ �� ����

    try{

        // Parameter ���� --------------------------------------------------------------------
        retInfo  = request.getParameter("retInfo").trim(); //�ݵ�� get�� post ��� �� �� �������ְ� ����س�����.


        // 1. ��ȣȭ ��� (jar) Loading
        com.sci.v2.pccv2.secu.SciSecuManager sciSecuMg = new com.sci.v2.pccv2.secu.SciSecuManager();
		sciSecuMg.setInfoPublic(id,"5608C6CA30810FDECAD35144C695B41E");  //bizsiren.com > ȸ�������� �α����� Ȯ��. 

        // 3. 1�� �Ľ�---------------------------------------------------------------

		retInfo  = sciSecuMg.getDec(retInfo, reqNum);

		// 4. ��û��� ��ȣȭ
        String[] aRetInfo1 = retInfo.split("\\^");

		encPara  = aRetInfo1[0];         //��ȣȭ�� ���� �Ķ����
        encMsg   = aRetInfo1[1];    //��ȣȭ�� ���� �Ķ������ Hash��
		
		String encMsg2   = sciSecuMg.getMsg(encPara);
		
		// 5. ��/���� ���� ---------------------------------------------------------------

        if(encMsg2.equals(encMsg)){
            msgChk="Y";
        }

		if(msgChk.equals("N")){
%>
		    <script language=javascript>
            alert("HMAC Ȯ���� �ʿ��մϴ�.");
		    </script>
<%
			return;
		}

        // ��ȣȭ �� ��/���� ���� ---------------------------------------------------------------
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

		//���� �ʵ�
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
			session.setAttribute("ss_m_sex", "1");	//��:0, ��:1 �� ��ȯ (�ڶ󽺱���, ��ü�������α׷������� ���������� �����ؼ� ����)
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
	location.href="<%=prepage%>";
}
//]]>
</script>
<body>
<div class="ipin_complete">
	<p class="tit">�������� �Ϸ�</p>
	<p class="btn_wrap">
		<a href="javascript:;" onclick="location.href="<%=prepage%>";" class="btn">Ȯ��</a>
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