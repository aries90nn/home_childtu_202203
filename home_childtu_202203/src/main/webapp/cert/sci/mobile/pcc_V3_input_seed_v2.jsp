<%@ page  contentType = "text/html;charset=ksc5601"%>
<%@ page import ="java.util.*,java.text.SimpleDateFormat"%>
<%
        //��¥ ����
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String day = sdf.format(today.getTime());

        java.util.Random ran = new Random();
        //���� ���� ����
        int numLength = 6;
        String randomStr = "";

        for (int i = 0; i < numLength; i++) {
            //0 ~ 9 ���� ���� ����
            randomStr += ran.nextInt(10);
        }

		//reqNum�� �ִ� 40byte ���� ��� ����
        String reqNum = "123456789";   //sample �������� result ��������  �������� ������ ��������� ��ȣȭ �� ����
		String certDate=day;

%>

<html>
    <head>
        <title>����ſ������� ���νǸ�Ȯ�μ���  �׽�Ʈ</title>
        <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
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
    </head>
    <body onload="document.reqCBAForm.id.focus();" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 marginheight=0 marginwidth=0>
        <center>
            <br><br><br>
            <span class="style1">����ſ������� ���νǸ�Ȯ�μ��� �׽�Ʈ</span><br>

            <form name="reqCBAForm" method="post" action="./pcc_V3_sample_seed_v2.jsp">
                <table cellpadding=1 cellspacing=1>
                    <tr>
                        <td align=center>ȸ������̵�</td>
                        <td align=left><input type="text" name="id" size='41' maxlength ='8' value = "SGPK001"></td>
                    </tr>
                    <tr>
                        <td align=center>���񽺹�ȣ</td>  <!-- siren������ submit�ϱ� �ٷ� ������ ��ûurl�� ���񽺹�ȣ�� ���� -->
                        <td align=left><input type="text" name="srvNo" size='41' maxlength ='6' value="010002"></td>
                    </tr>
                    <tr>
                        <td align=center>��û��ȣ</td>
                        <td align=left><input type="text" name="reqNum" size='41' maxlength ='40' value='<%=reqNum%>'></td>
                    </tr>
					<tr>
                        <td align=center>��������</td>
                        <td align=left>
                            <select name="certGb" style="width:300">
                                
                                <option value="H">�޴���</option>
                            </select>
                        </td>
                    </tr>
					<tr>
                        <td align=center>��û�ð�</td>
                        <td align=left><input type="text" name="certDate" size='41' maxlength ='40' value='<%=certDate%>'></td>
                    </tr>
					<tr>
                        <td align=center>�߰��Ķ����</td>
                        <td align=left><input type="text" name="addVar"  size="41" maxlength ='320' value=""></td>
                    </tr>
                    <tr>
                        <td align=center>�������URL</td><!-- ������� URL(32http:// ������ �ּ�) -->
								  	     				  <!-- 32http:// �� ��� - ���� �����ӿ��� ��� �����ϴ� ���
															   31http:// �� ��� - �θ�â���� ��� �����ϴ� ��� -->
                        <td align=left><input type="text" name="retUrl" size="41" value="32http://reserve.kiam.or.kr/cert/sci/mobile/pcc_V3_popup_seed_v2.jsp"></td>
                    </tr>
                </table>
                <br><br>
                <input type="submit" value="����Ȯ�μ���V3 �׽�Ʈ">
            </form>
            <br>
            <br>
            �� Sampleȭ���� ����ſ��� ���νǸ�Ȯ�μ��� �׽�Ʈ�� ���� �����ϰ� �ִ� ȭ���Դϴ�.<br>
			<p style="color:red"><b> Sample�������� �׽�Ʈ�θ� �����Ű�� �� ���� �����Ʈ�� �ݿ��ϽǶ��� ���÷� �����ǰ��ִ� ���ϸ����δ� ����� ���� �����ֽʽÿ�. </b></p>
            <br>
        </center>
    </body>
</html>