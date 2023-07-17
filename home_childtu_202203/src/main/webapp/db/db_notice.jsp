<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
page import ="kr.co.nninc.ncms.common.Func" %><%@
include file="./dbclass.jsp" %><%

response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);


//기존디비
String src_table = "BB_NOTICE_MF";
String src_columns = "SEQ, BOARDLEVEL, BOARDSTEP, MEMOSEQ, REFERSEQ, SIGNATUREID, NOTICETP, HTMLTP, SECRETTP, CATEGORYID, PERSONID";
src_columns +=", MOVEID, MOVEUSERID, MOVEDT, MOVETM, MOVEIP, READCOUNT, USERID, PASSWD, USERNM, EMAIL, HOMEPAGE, REGNO1, REGNO2, TELNO";
src_columns +=", MOBILENO, ZIP1, ZIP2, ADDR1, ADDR2, OTHER1, OTHER2, OTHER3, TITLEDS, MEMODS, INSERTID, INSERTDT, INSERTTM, INSERTIP";
src_columns +=", UPDATEID, UPDATEDT, UPDATETM, UPDATEIP, DELETEID, DELETEDT, DELETETM, DELETEIP, NOTICESDT, NOTICEEDT";

//대상디비
String trg_table = "board";
String trg_file_dir = "board_39";
String trg_a_num = "65499793";

//기존디비 조회
SelectTable ST2 = new SelectTable();
ST2.connectReset("jdbc/old");
ArrayList<HashMap<String, String>> boardList = ST2.selectQueryTable("select "+src_columns+" from "+src_table+" order by seq");


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
	if(!"".equals( nvl( board.get("DELETEID") ) )){
		b_look = "N";
		b_keyword = nvl( board.get("DELETEDT") ).replace("/", "-")+" "+nvl( board.get("DELETETM") );
		b_keyword += "^"+nvl( board.get("DELETEID") );
		b_keyword += "^"+nvl( board.get("DELETEIP") );
	}else if("I".equals( nvl( board.get("SECRETTP") ) )){
		b_look = "N";
		b_keyword = nvl( board.get("UPDATEDT") ).replace("/", "-")+" "+nvl( board.get("UPDATETM") );
		b_keyword += "^"+nvl( board.get("UPDATEID") );
		b_keyword += "^"+nvl( board.get("UPDATEIP") );
	}

	String b_noticechk = "";
	if("1".equals(board.get("NOTICETP"))){
		b_noticechk = "Y";
	}
	
	String sql = "insert into "+trg_table+"(";
	sql += "a_num, b_num, b_type, b_id, b_name, b_pwd, b_subject, b_email, b_jumin, b_phone1, b_phone2, b_phone3, b_cate, b_catename, b_noticechk, b_content, b_ip, b_count, b_look, b_open, b_ref, b_step, b_level, b_regdate, b_chuchun, b_sbjclr, b_sdate, b_edate, b_temp1, b_temp2, b_temp3, b_temp4, b_temp5, b_temp6, b_temp7, b_temp8, b_temp9, b_temp10, b_re_buseo, b_re_date, b_re_state, b_re_content, b_file1, b_file2, b_file3, b_file4, b_file5, b_file6, b_file7, b_file8, b_file9, b_file10, b_keyword, b_c_count, b_zip1, b_zip2, b_addr1, b_addr2, b_show_sdate, b_show_edate, b_notice_sdate, b_notice_edate";
	sql += ") values('"+trg_a_num;
	sql += "', '"+b_num;
	sql += "', 'N";
	sql += "', '"+nvl(board.get("USERID"));
	sql += "', '"+nvl(board.get("USERNM"));
	sql += "', '"+nvl(board.get("PASSWD"));
	sql += "', '"+inputValue(board.get("TITLEDS"));
	sql += "', '"+nvl(board.get("EMAIL"));
	sql += "', '";	//b_email
	sql += "', '";	//b_phone1
	sql += "', '";	//b_phone2
	sql += "', '";	//b_phone3
	sql += "', '0";	//b_cate
	sql += "', '";	//b_catename
	sql += "', '"+b_noticechk;		//b_noticechk
	sql += "', '"+inputValue( board.get("MEMODS") );
	sql += "', '"+nvl( board.get("INSERTIP") );
	sql += "', '"+nvl( board.get("READCOUNT") ).replace("-", "");
	sql += "', '"+b_look;
	sql += "', 'Y";	//b_open
	sql += "', '"+b_num;	//b_ref
	sql += "', '0";		//b_step
	sql += "', '0";		//b_level
	sql += "', '"+nvl( board.get("INSERTDT") ).replace("/", "-")+" "+nvl( board.get("INSERTTM") );
	sql += "', '0";	//b_chuchun
	sql += "', '";	//b_sbjclr
	sql += "', '";	//b_sdate
	sql += "', '";	//b_edate
	sql += "', '";	//b_temp1
	sql += "', '";	//b_temp2
	sql += "', '";	//b_temp3
	sql += "', '";	//b_temp4
	sql += "', '";	//b_temp5
	sql += "', '";	//b_temp6
	sql += "', '";	//b_temp7
	sql += "', '";	//b_temp8
	sql += "', '";	//b_temp9
	sql += "', '"+nvl( board.get("SEQ") );	//b_temp10 //임시로 기존게시물 번호를 넣자
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
	sql += "', '1900-01-01 00:00";	//b_notice_sdate
	sql += "', '2099-12-31 23:59";	//b_notice_edate
	sql += "')";
	//out.print("<br />sql = <pre>"+sql+"</pre>");
	//out.print("<br />"+b_num+". "+board.get("TITLEDS")+" - ");
	executeQuery( sql );
	//out.print(" ok.");
}
out.print("<br />"+trg_table+" a_num="+trg_a_num+" "+boardList.size()+" record insert ok - "+Func.date("Y-m-d H:i:s"));


//첨부파일조회
ArrayList<HashMap<String, String>> fileList = ST2.selectQueryTable("select seq, filelinkseq, filelinknm from "+src_table+"_FILELINK where FILELINKNM is not null order by seq");
for(int i=0;i<=fileList.size()-1;i++){
	HashMap<String,String>file = fileList.get(i);
	String seq = nvl( file.get("SEQ") );
	String filelinkseq = nvl( file.get("FILELINKSEQ") );
	String filelinknm = inputValue( file.get("FILELINKNM") );
			
	if(cInt(filelinkseq) <= 10){
		
		String sql = "update "+trg_table+" set b_file"+filelinkseq+" = '"+filelinknm+"' where a_num = '"+trg_a_num+"' and b_temp10 = '"+seq+"'";
		
		//out.print("<br />"+seq+"- "+filelinkseq+". "+filelinknm+" - ");
		executeQuery( sql );
		//out.print(" ok.");
	}
}

//임시seq 삭제
executeQuery( "update "+trg_table+" set b_temp10 = '' where a_num = '"+trg_a_num+"'" );

out.print("<br />"+trg_table+" a_num="+trg_a_num+" file "+fileList.size()+" record update ok - "+Func.date("Y-m-d H:i:s"));
out.print("<br />c<span>p</span> -R /home/gjlib/www/boardData/"+src_table.replace("BB_", "")+"/* /websource/www/lib.gen.go.kr_2019/ROOT/data/board/"+trg_file_dir+"/");


%>