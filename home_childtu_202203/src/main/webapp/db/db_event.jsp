<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
page import ="kr.co.nninc.ncms.common.Func" %><%@
include file="./dbclass.jsp" %><%

response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);


//기존디비
String src_table = "PJ_SCH_PROGRAM";
String src_sitecode = "MC";
String src_sch_type = "P";	//S:공연, D:전시, P:체육관
String src_columns = "SCH_SEQ, SCH_TITLE, TO_CHAR(SCH_SDATE,'YYYY-MM-DD') as SCH_SDATE, TO_CHAR(SCH_EDATE,'YYYY-MM-DD') as SCH_EDATE, SCH_CONTENTS, SCH_USE_YN, TO_CHAR(SCH_REG_DT,'YYYY-MM-DD HH24:MI:SS') as SCH_REG_DT, SCH_REG_ID, SCH_REG_IP, SCH_UPD_DT, SCH_UPD_ID, SCH_UPD_IP, SCH_DEL_DT, SCH_DEL_ID, SCH_DEL_IP, SITE_CODE, SCH_TYPE, SCH_DEL_YN";

//대상디비
String trg_table = "board";
String trg_file_dir = "board_97";
String trg_a_num = "54237091";

//기존디비 조회
SelectTable ST2 = new SelectTable();
ST2.connectReset("jdbc/old");
ArrayList<HashMap<String, String>> boardList = ST2.selectQueryTable("select "+src_columns+" from "+src_table+" where SITE_CODE = '"+src_sitecode+"' and SCH_TYPE = '"+src_sch_type+"' order by SCH_SEQ");


//대상디비 초기화
executeQuery("delete from "+trg_table+" where a_num = '"+trg_a_num+"' ");
InsertTable IT = new InsertTable();
IT.tableName = trg_table;
out.print("<br />"+trg_table+" a_num="+trg_a_num+" delete ok - "+Func.date("Y-m-d H:i:s"));

//최대 b_num 조회
SelectTable ST = new SelectTable();
int b_num = cInt( ST.selectQueryColumn("select max(b_num) as b_num from "+trg_table) );

for(int i=0;i<=boardList.size()-1;i++){
	HashMap<String,String>board = boardList.get(i);
	b_num = b_num + 1;
	//a_num, b_num, b_type, b_id, b_name, b_pwd, b_subject, b_email, b_jumin, b_phone1, b_phone2, b_phone3, b_cate, b_catename, b_noticechk, b_content, b_ip, b_count, b_look, b_open, b_ref, b_step, b_level, b_regdate, b_chuchun, b_sbjclr, b_sdate, b_edate, b_temp1, b_temp2, b_temp3, b_temp4, b_temp5, b_temp6, b_temp7, b_temp8, b_temp9, b_temp10, b_re_buseo, b_re_date, b_re_state, b_re_content, b_file1, b_file2, b_file3, b_file4, b_file5, b_file6, b_file7, b_file8, b_file9, b_file10, b_keyword, b_c_count, b_zip1, b_zip2, b_addr1, b_addr2, b_show_sdate, b_show_edate, b_notice_sdate, b_notice_edate
	
	String b_look = "Y";
	String b_keyword = "";
	if("Y".equals( nvl( board.get("SCH_DEL_YN") ) )){
		b_look = "N";
		b_keyword = nvl( board.get("SCH_DEL_DT") ).replace("/", "-");
		b_keyword += "^"+nvl( board.get("SCH_DEL_ID") );
		b_keyword += "^"+nvl( board.get("SCH_DEL_IP") );
	}

	String b_noticechk = "";
	String b_notice_sdate = "";
	String b_notice_edate = "";
	if("1".equals(board.get("NOTICETP"))){
		b_noticechk = "Y";
		b_notice_sdate = "1900-01-01 00:00";
		b_notice_edate = "2099-12-31 23:59";
	}

	String b_content = inputValue( board.get("SCH_CONTENTS") );
	b_content = inputValueText( board.get("SCH_CONTENTS") );
	

	String b_open = "Y";

	String b_sdate = nvl( board.get("SCH_SDATE") );
	String b_edate = nvl( board.get("SCH_EDATE") );

	String b_sbjclr = "red_day";
	
	String sql = "insert into "+trg_table+"(";
	sql += "a_num, b_num, b_type, b_id, b_name, b_pwd, b_subject, b_email, b_jumin, b_phone1, b_phone2, b_phone3, b_cate, b_catename, b_noticechk, b_content, b_ip, b_count, b_look, b_open, b_ref, b_step, b_level, b_regdate, b_chuchun, b_sbjclr, b_sdate, b_edate, b_temp1, b_temp2, b_temp3, b_temp4, b_temp5, b_temp6, b_temp7, b_temp8, b_temp9, b_temp10, b_re_buseo, b_re_date, b_re_state, b_re_content, b_file1, b_file2, b_file3, b_file4, b_file5, b_file6, b_file7, b_file8, b_file9, b_file10, b_keyword, b_c_count, b_zip1, b_zip2, b_addr1, b_addr2, b_show_sdate, b_show_edate, b_notice_sdate, b_notice_edate";
	sql += ") values('"+trg_a_num;
	sql += "', '"+b_num;
	sql += "', 'N";
	sql += "', '"+nvl(board.get("SCH_REG_ID"));
	sql += "', '관리자";	//b_name
	sql += "', '"+nvl(board.get("SCH_REG_ID"));
	sql += "', '"+inputValue(board.get("SCH_TITLE"));
	sql += "', '";	//b_email
	sql += "', '";	//b_jumin
	sql += "', '";	//b_phone1
	sql += "', '";	//b_phone2
	sql += "', '";	//b_phone3
	sql += "', '0";	//b_cate
	sql += "', '";	//b_catename
	sql += "', '"+b_noticechk;		//b_noticechk
	sql += "', '"+b_content;
	sql += "', '"+nvl( board.get("SCH_REG_IP") );	//b_ip
	sql += "', '0";
	sql += "', '"+b_look;
	sql += "', '"+b_open;	//b_open
	sql += "', '"+b_num;	//b_ref
	sql += "', '0";		//b_step
	sql += "', '0";		//b_level
	sql += "', '"+nvl( board.get("SCH_REG_DT") );	//b_regdate
	sql += "', '0";	//b_chuchun
	sql += "', '"+b_sbjclr;	//b_sbjclr
	sql += "', '"+b_sdate;	//b_sdate
	sql += "', '"+b_edate;	//b_edate
	sql += "', '";	//b_temp1
	sql += "', '";	//b_temp2
	sql += "', '";	//b_temp3
	sql += "', '";	//b_temp4
	sql += "', '";	//b_temp5
	sql += "', '";	//b_temp6
	sql += "', '";	//b_temp7
	sql += "', '";	//b_temp8
	sql += "', '";	//b_temp9
	sql += "', '";	//b_temp10 //임시로 기존게시물 번호를 넣자
	sql += "', '";	//b_re_buseo
	sql += "', '";	//b_re_date
	sql += "', '";	//b_re_state
	sql += "', '";	//b_re_content
	sql += "', '";	//b_file1
	sql += "', '";	//b_file2
	sql += "', '";	//b_file3
	sql += "', '";	//b_file4
	sql += "', '";	//b_file5
	sql += "', '";	//b_file6
	sql += "', '";	//b_file7
	sql += "', '";	//b_file8
	sql += "', '";	//b_file9
	sql += "', '";	//b_file10	
	sql += "', '"+b_keyword;
	sql += "', '0";	//b_c_count
	sql += "', '";	//b_zip1
	sql += "', '";	//b_zip2
	sql += "', '";	//b_addr1
	sql += "', '";	//b_addr2
	sql += "', '";	//b_show_sdate
	sql += "', '";	//b_show_edate
	sql += "', '"+b_notice_sdate;	//b_notice_sdate
	sql += "', '"+b_notice_edate;	//b_notice_edate
	sql += "')";
	//out.print("<br />sql = <pre>"+sql+"</pre>");
	//out.print("<br />"+b_num+". "+board.get("TITLEDS")+" - ");
	executeQuery( sql );
	//out.print(" ok.");
}
out.print("<br />"+trg_table+" a_num="+trg_a_num+" "+boardList.size()+" record insert ok - "+Func.date("Y-m-d H:i:s"));



//임시seq 삭제
executeQuery( "update "+trg_table+" set b_temp10 = '' where a_num = '"+trg_a_num+"'" );


%>