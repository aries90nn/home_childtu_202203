<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import ="kr.co.nninc.ncms.common.Func" %>
<%@page import ="kr.co.nninc.ncms.crypto.service.CryptoARIAService" %>
<%@include file="./dbclass.jsp" %>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);

//String src_table = "dbo.BBS_DATA";
//String src_table = "dbo.BBS_DATA";
//String src_table = "dbo.BBS_DATA as a left outer join .ATTACH_FILES as b on a.IDX = b.FKIdx where a.IDX = 943";
String src_table = "dbo.BBS_DATA where CateID = '64' and status != '6' order by REGDATE DESC";
/*
select *
from dbo.BBS_DATA as a left outer join .ATTACH_FILES as b on a.IDX = b.FKIdx where a.IDX = 943;
*/

String src_columns = "*";
String sql_src = "select "+src_columns+" from "+src_table;
//String sql_src = "select "+src_columns+" from "+src_table+" where use_at = 'Y' and ( upper_dept_id is null or upper_dept_id = 'DEPT_000000000000031')  order by dept_seq ";
//String sql_src = "select "+src_columns+" from "+src_table+" where dept_id != 'DEPT_000000000000031' and use_at = 'Y' and ( upper_dept_id is null or upper_dept_id = 'DEPT_000000000000031')  order by dept_seq ";
//String sql_src = "select "+src_columns+" from "+src_table+" where use_at = 'Y' and ( upper_dept_id = 'DEPT_000000000000170')  order by dept_seq ";
sql_src = "select ADS_RECEIVER as es_name, ADS_ZIPCODE as es_zipcode, ADS_ADDR1 as es_addr1, ADS_ADDR2 as es_addr2, convert(varchar(1000),decryptbypassphrase('qjqqncj13!#@#', ADS_TEL)) as es_phone,ADS_SELECTGUBUN as es_gubun, ADS_RECEIVE_NAME as es_name2, convert(varchar(1000),decryptbypassphrase('qjqqncj13!#@#', ADS_HOMETEL)) as es_bphone, REG_DTM as es_wdate, ADS_QTY as es_etc, convert(varchar(1000),decryptbypassphrase('qjqqncj13!#@#', AM_EMAIL)) as es_email, ADS_CONTENTS as es_memo from dbo.APPLICATION_DTL_STORY a LEFT OUTER JOIN APPLICATION_MST b on a.ADS_MST_IDX = b.AM_IDX where REG_DTM >= '2022-04-01 00:00:00.00' order by ADS_IDX DESC;";
//기존디비 조회
SelectTable ST2 = new SelectTable();
ST2.connectReset("jdbc/chid");
ArrayList<HashMap<String, String>> deptList = ST2.selectQueryTable( sql_src );
//out.print(sql_src);
CryptoARIAService ariaService = new CryptoARIAService();

//대상디비
String trg_table = "board";
String trg_file_dir = "main";
String regdate = Func.date("Y-m-d H:i:s");

InsertTable IT = new InsertTable();
IT.tableName = trg_table;

//최대 ct_code 조회
//SelectTable ST = new SelectTable();
//ST.connectReset("jdbc/nninc");
//int ct_code = 1;
//out.print(ct_code+"<br/>");
int z = 1;
for(int i=0;i<=deptList.size()-1;i++){
	//if(i == 4) break;
	HashMap<String,String>dept = deptList.get(i);
	
	//ct_code = ct_code + 1;	
	// 출력
	// es_name, es_zipcode, es_addr1, es_addr2, es_phone1, es_phone2, es_phone3, es_name2, es_bphone1, es_bphone2, es_bphone3, es_email
	String es_name = ariaService.encryptData(dept.get("es_name"));
	String es_zipcode = ariaService.encryptData(dept.get("es_zipcode"));
	String es_addr1 = ariaService.encryptData(dept.get("es_addr1"));
	String es_addr2 = ariaService.encryptData(dept.get("es_addr2"));
	
	String es_phone = dept.get("es_phone");
	String[] es_phone_arr = es_phone.replaceAll(" ", "").split("-");
	String es_phone1 = ariaService.encryptData(es_phone_arr[0]);
	String es_phone2 = ariaService.encryptData(es_phone_arr[1]);
	String es_phone3 = ariaService.encryptData(es_phone_arr[2]);
	
	// es_temp1 - 개인 (i), 기관(a), 학교(s)
	String es_temp1 = "i";
	String es_gubun = dept.get("es_gubun");
	String es_name2 = "";
	if(es_gubun.equals("C")) {// P, C
		es_temp1 = "s";
		es_name2 = ariaService.encryptData(dept.get("es_name2"));	
	}
	
	
	
	String es_bphone = dept.get("es_bphone");
	String[] es_bphone_arr = es_bphone.replaceAll(" ", "").split("-");
	String es_bphone1 = ariaService.encryptData(es_bphone_arr[0]);
	String es_bphone2 = ariaService.encryptData(es_bphone_arr[1]);
	String es_bphone3 = ariaService.encryptData(es_bphone_arr[2]);
	String es_email = dept.get("es_email").replaceAll(" ", "").trim();
	if(!"".equals(es_email)) es_email = ariaService.encryptData(dept.get("es_email"));
	
	
	String es_wdate = dept.get("es_wdate");
	String es_etc = dept.get("es_etc");
	String es_memo = dept.get("es_memo");
	/*
	out.print("<br />"+z+".  es_name :"+ariaService.decryptData(es_name));
	out.print("<br />"+z+".  es_zipcode :"+ariaService.decryptData(es_zipcode));
	out.print("<br />"+z+".  es_addr1 :"+ariaService.decryptData(es_addr1));
	out.print("<br />"+z+".  es_addr2 :"+ariaService.decryptData(es_addr2));
	out.print("<br />"+z+".  es_phone :"+es_phone);
	out.print("<br />"+z+".  es_phone1 :"+es_phone_arr[0]);
	out.print("<br />"+z+".  es_phone2 :"+es_phone_arr[1]);
	out.print("<br />"+z+".  es_phone3 :"+es_phone_arr[2]);
	out.print("<br />"+z+".  es_gubun :"+es_temp1);
	out.print("<br />"+z+".  es_name2 :"+es_name2);
	out.print("<br />"+z+".  es_bphone :"+es_bphone);
	out.print("<br />"+z+".  es_bphone1 :"+es_bphone_arr[0]);
	out.print("<br />"+z+".  es_bphone2 :"+es_bphone_arr[1]);
	out.print("<br />"+z+".  es_bphone3 :"+es_bphone_arr[2]);
	out.print("<br />"+z+".  es_email :"+ariaService.decryptData(es_email));
	out.print("<br />"+z+".  es_wdate :"+es_wdate);
	out.print("<br />"+z+".  es_etc :"+es_etc);
	out.print("<br />"+z+".  es_memo :"+es_memo);
	*/
	z++;	
	//out.print("<br /> BODY :"+dept.get("BODY"));
	//out.print("<br /> REGDATE :"+dept.get("REGDATE"));
	//out.print("<br /> CNT_READ :"+dept.get("CNT_READ"));
	
	//String sql = "insert into board(a_num, b_type, b_id, b_name, b_pwd, b_subject, b_cate, b_content, b_ip, b_count, b_look, b_open,  b_step, b_level, b_regdate, b_chuchun, b_file1) values('75543687', 'N', 'nninc', '관리자', '522c628e61da85356b8ec1a58a210ed35a5a5082465131cabf1cdce845d211be', '"+dept.get("TITLE")+"', '0', '"+dept.get("BODY")+"', '112.216.151.210', '"+dept.get("CNT_READ")+"', 'Y', 'Y', '0', '0', '"+dept.get("REGDATE")+"', '0', '"+z+"-min.jpg')";
	//String sql = "INSERT INTO buseo( ct_code, ct_name, ct_ref, ct_depth, ct_chk, ct_wdate, ct_memo, site_dir ) VALUES ( "+ct_code+", '"+dept.get("dept_nm")+"', 82, 3, 'Y', '"+regdate+"' , '', '"+trg_file_dir+"' )";
	// edu_idx = 1937, ct_idx = 47
	// es_name, es_zipcode, es_addr1, es_addr2, es_phone1, es_phone2, es_phone3, es_name2, es_bphone1, es_bphone2, es_bphone3, es_email																								(edu_idx, es_name, 	es_zipcode, es_addr1, es_addr2, es_phone1, es_phone2, es_phone3, es_name2, es_bphone1, es_bphone2, es_bphone3, es_wdate, es_etc, es_email, es_temp1, es_memo)
	//String sql = "INSERT INTO edusat_request(edu_idx, es_name, es_zipcode, es_addr1, es_addr2, es_phone1, es_phone2, es_phone3, es_name2, es_bphone1, es_bphone2, es_bphone3, es_wdate, es_etc, es_email, es_temp1, es_memo, es_status) values('1938', '"+es_name+"', '"+es_zipcode+"', '"+es_addr1+"', '"+es_addr2+"', '"+es_phone1+"', '"+es_phone2+"', '"+es_phone3+"', '"+es_name2+"', '"+es_bphone1+"', '"+es_bphone2+"', '"+es_bphone3+"', '"+es_wdate+"', '"+es_etc+"', '"+es_email+"', '"+es_temp1+"', '"+es_memo+"', '0')";
	//ST.connectReset("jdbc/nninc");
	//String ct_idx = ST.selectQueryColumn("SELECT LAST_INSERT_ID() AS reviewIdx");
	
	//String sql2 = "UPDATE buseo SET ct_codeno = 'C0;C11;C"+ct_idx+";' WHERE ct_idx ='"+ct_idx+"'";
	//String sql2 = "UPDATE buseo SET ct_codeno = 'C0;C11;C82;C"+ct_idx+";' WHERE ct_idx ='"+ct_idx+"'";
	
	try{
		//executeQuery( sql );
		//executeQuery( sql2 );
	}catch(Exception e){
		out.print("<br />"+dept.get("TITLE")+" - error");
		//out.print("<br />sql = <pre>"+sql+"</pre>");
	}
}

%>