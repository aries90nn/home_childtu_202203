<%@ page  contentType = "text/html;charset=utf-8"%>
<%@ page import ="java.util.*,java.text.SimpleDateFormat"%>
<%@ include file="./inc_config.jsp" %>
<%


        //날짜 생성
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String day = sdf.format(today.getTime());

        java.util.Random ran = new Random();
        //랜덤 문자 길이
        int numLength = 6;
        String randomStr = "";

        for (int i = 0; i < numLength; i++) {
            //0 ~ 9 랜덤 숫자 생성
            randomStr += ran.nextInt(10);
        }
		//reqNum은 최대 40byte 까지 사용 가능
        String reqNum = day + randomStr;
		
        String url = request.getRequestURL().toString();
    	String domain = url.substring(7,url.indexOf("/",8));
%>
<html>
    <head>
        <title>본인인증서비스  테스트</title>
        <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
        <style>
            <!--
            body,p,ol,ul,td
            {
                font-family: 굴림;
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
            -->
        </style>

    </head>
    <body bgcolor="#FFFFFF" topmargin=0 leftmargin=0 marginheight=0 marginwidth=0>

        <center>
            <br><br><br>
            <span class="style1">본인인증서비스 테스트</span><br>

            <form name="reqForm" method="post" action="./dream_test02.jsp">
           
                <table cellpadding=1 cellspacing=1>
                    <tr>
                        <td align=center>회원사ID</td>
                        <td align=left><input type="text" name="cpid" size='41' maxlength ='10' value = "<%=cfg_cpid%>"></td>
                    </tr>
                    <tr>
                        <td align=center>URL코드</td>
                        <td align=left><input type="text" name="serviceCode" size='41' maxlength ='6' value="<%=cfg_servicecode%>"></td>
                    </tr>
                    <tr>
                        <td align=center>요청번호</td>
                        <td align=left><input type="text" name="clntReqNum" size='41' maxlength ='40' value='<%=reqNum%>'></td>
                    </tr>
                    <tr>
                        <td align=center>요청일시</td>
						<!-- 현재시각 세팅(YYYYMMDDHI24MISS) -->
                        <td align=left><input type="text" name="reqdate" size="41" maxlength ='14' value="<%=day%>"></td>
					</tr>
  
                    
                        <td align=center>결과수신URL</td>
                        <td align=left><input type="text" name="retUrl" size="41" value="http://<%=domain %>/cert/dream/mobile/dream_test03.jsp"></td>
                    </tr>
                </table>
                <br><br>
                <input type="submit" value="본인인증 테스트" >
            </form>
            <br>
            <br>

            <br>
        </center>
    </body>
</html>