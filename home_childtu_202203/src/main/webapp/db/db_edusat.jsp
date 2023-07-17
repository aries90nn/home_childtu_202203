<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
page import ="kr.co.nninc.ncms.common.Func" %><%@
include file="./dbclass.jsp" %><%

response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);


//기존디비
String src_table = "PJ_CL_PROGRAM";
String src_sitecode = "MA";
String src_columns = "CL_SEQ, CL_TYPE, CL_TITLE, CL_LEC_SDT, CL_LEC_EDT, CL_PLACE, CL_TARGET, CL_AGE, CL_CYCLE, CL_MAX_PEOPLE, CL_READY_PEOPLE";
src_columns +=", TO_CHAR(CL_CANAPPLY_SDT,'YYYY-MM-DD HH24:MI:SS') as CL_CANAPPLY_SDT, TO_CHAR(CL_CANAPPLY_EDT,'YYYY-MM-DD HH24:MI:SS') as CL_CANAPPLY_EDT";
src_columns +=", CL_APPLY_CASE, CL_LECTURER, CL_LECTURER_INTRO, CL_LEC_CONTENTS, CL_LEC_PAY";
src_columns +=", CL_USE_YN, CL_DELETE_YN, TO_CHAR(CL_REG_DATE,'YYYY-MM-DD HH24:MI:SS') as CL_REG_DATE, CL_REG_ID, CL_REG_CLIENT";
src_columns +=", TO_CHAR(CL_UPD_DATE,'YYYY-MM-DD HH24:MI:SS') as CL_UPD_DATE, CL_UPD_ID, CL_UPD_CLIENT, CL_DEL_DATE, CL_DEL_ID";
src_columns +=", CL_DEL_CLIENT, SITE_CODE, CL_AGE_MAX, CL_TARGET_EXT, CL_LEC_TIME, CL_BIRTH_MIN, CL_BIRTH_MAX, CL_LEC_STM, CL_LEC_ETM, CL_LEC_BIGO";

//대상디비
String trg_table = "edusat";
//4:학생독립기념회관, 32:금호평생교육관, 33:학생교육문화회관, 34:중앙도서관, 35:송정도서관, 36:석봉도서관
String trg_ct_idx = "4";
String trg_edu_lib = "학생독립기념회관";


//기존디비 조회
SelectTable ST2 = new SelectTable();
ST2.connectReset("jdbc/old");
ArrayList<HashMap<String, String>> programList = ST2.selectQueryTable("select "+src_columns+" from "+src_table+" where SITE_CODE = '"+src_sitecode+"' and CL_DELETE_YN = 'N' order by CL_SEQ");


//대상디비 초기화
executeQuery("delete from "+trg_table+" where ct_idx = '"+trg_ct_idx+"' ");
InsertTable IT = new InsertTable();
IT.tableName = trg_table;
out.print("<br />"+trg_table+" ct_idx="+trg_ct_idx+" delete ok - "+Func.date("Y-m-d H:i:s"));

int edu_code = programList.size()+1;
for(int i=0;i<=programList.size()-1;i++){
	HashMap<String,String>program = programList.get(i);
	edu_code = edu_code - 1;
	//edu_idx, ct_idx, ct_idx2, edu_code, edu_lib, edu_gubun, edu_gubun2, edu_season, edu_lot_type, edu_awaiter, edu_subject, edu_teacher, edu_resdate, edu_reedate, edu_sdate, edu_edate, edu_time, edu_week, edu_money, edu_target, edu_inwon, edu_content, edu_chk, edu_wdate, edu_mdate, edu_ip, edu_login, edu_receive_type, edu_temp1, edu_temp2, edu_temp3, edu_temp4, edu_temp5, edu_resdate_h, edu_reedate_h, end_chk, edu_ptcp_yn, edu_ptcp_cnt, edu_field1_yn, edu_field2_yn, edu_field3_yn, edu_field4_yn, edu_field5_yn, edu_field6_yn, edu_field7_yn, edu_field8_yn
	
	//강좌분류
	String CL_TYPE = nvl(program.get("CL_TYPE"));
	String ct_idx2 = "";
	String edu_gubun = "";
	if("1".equals(CL_TYPE)){	//독서프로그램
		ct_idx2 = "10";
		edu_gubun = "독서프로그램";
	}else if("2".equals(CL_TYPE)){	//평생교육프로그램
		ct_idx2 = "11";
		edu_gubun = "평생교육프로그램";
	}else if("4".equals(CL_TYPE)){	//체험활동
		ct_idx2 = "14";
		edu_gubun = "체험활동";
	}else{	//기타
		ct_idx2 = "15";
		edu_gubun = "기타";
	}
	
	
	//접수방법
	String CL_APPLY_CASE = nvl(program.get("CL_APPLY_CASE"));
	String edu_receive_type = "2";
	if("I".equals(CL_APPLY_CASE)){	//인터넷
		edu_receive_type = "2";
	}else if("B".equals(CL_APPLY_CASE)){	//방문
		edu_receive_type = "0";
	}

	//강의기간
	String edu_sdate = nvl(program.get("CL_LEC_SDT")).substring(0,4);
	edu_sdate += "-"+nvl(program.get("CL_LEC_SDT")).substring(4,6);
	edu_sdate += "-"+nvl(program.get("CL_LEC_SDT")).substring(6,8);
	String edu_edate = nvl(program.get("CL_LEC_EDT")).substring(0,4);
	edu_edate += "-"+nvl(program.get("CL_LEC_EDT")).substring(4,6);
	edu_edate += "-"+nvl(program.get("CL_LEC_EDT")).substring(6,8);
	

	//강의요일
	String edu_week = "";
	String CL_CYCLE = nvl(program.get("CL_CYCLE"));
	if( CL_CYCLE.indexOf( "일" ) >= 0 ){ edu_week += "".equals(edu_week) ? "0" : ",0"; }
	if( CL_CYCLE.indexOf( "월" ) >= 0 ){ edu_week += "".equals(edu_week) ? "1" : ",1"; }
	if( CL_CYCLE.indexOf( "화" ) >= 0 ){ edu_week += "".equals(edu_week) ? "2" : ",2"; }
	if( CL_CYCLE.indexOf( "수" ) >= 0 ){ edu_week += "".equals(edu_week) ? "3" : ",3"; }
	if( CL_CYCLE.indexOf( "목" ) >= 0 ){ edu_week += "".equals(edu_week) ? "4" : ",4"; }
	if( CL_CYCLE.indexOf( "금" ) >= 0 ){ edu_week += "".equals(edu_week) ? "5" : ",5"; }
	if( CL_CYCLE.indexOf( "토" ) >= 0 ){ edu_week += "".equals(edu_week) ? "6" : ",6"; }

	//대상
	String edu_target = "";
	String CL_TARGET = nvl( program.get("CL_TARGET") );
	String CL_TARGET_EXT = nvl( program.get("CL_TARGET_EXT") ).trim();
	if("1".equals(CL_TARGET)){
		edu_target = "영유아";
	}else if("2".equals(CL_TARGET)){
		edu_target = "초등학교";
	}else if("3".equals(CL_TARGET)){
		edu_target = "중학교";
	}else if("4".equals(CL_TARGET)){
		edu_target = "고등학교";
	}else if("5".equals(CL_TARGET)){
		edu_target = "일반";
	}
	if(!"".equals(CL_TARGET_EXT)){edu_target += " "+CL_TARGET_EXT;}

	//강의내용
	String CL_LEC_CONTENTS = nvl(program.get("CL_LEC_CONTENTS"));
	String edu_content = inputValueText(CL_LEC_CONTENTS);


	//참가대상제한
	String edu_temp4 = "N";
	String edu_temp5 = "";
	String CL_BIRTH_MIN = nvl(program.get("CL_BIRTH_MIN"));
	String CL_BIRTH_MAX = nvl(program.get("CL_BIRTH_MAX"));
	if(!"".equals(CL_BIRTH_MIN) && !"".equals(CL_BIRTH_MAX)){
		edu_temp4 = "Y";
		String edu_temp5_s = CL_BIRTH_MIN+"01";
		String edu_temp5_e = Func.dateSerial( cInt(CL_BIRTH_MAX.substring(0,4)), cInt(CL_BIRTH_MAX.substring(4,6))+1, 1-1 ).replaceAll("-", "");
		edu_temp5 = edu_temp5_s+"|"+edu_temp5_e;
	}


	//신청시간
	int edu_resdate_h = cInt( nvl(program.get("CL_CANAPPLY_SDT")).substring(11,13) );
	int edu_reedate_h = cInt( nvl(program.get("CL_CANAPPLY_EDT")).substring(11,13) ) - 1;


	//사용,중지,마감
	String end_chk = "Y";
	String CL_USE_YN = nvl(program.get("CL_USE_YN"));
	if("Y".equals(CL_USE_YN)){
		end_chk = "Y";
	}else{
		end_chk = "N";
	}

	String sql = "insert into "+trg_table+"(";
	sql += "edu_idx, ct_idx, ct_idx2, edu_code, edu_lib, edu_gubun, edu_gubun2, edu_season, edu_lot_type, edu_awaiter, edu_subject, edu_teacher, edu_resdate, edu_reedate, edu_sdate, edu_edate, edu_time, edu_week, edu_money, edu_target, edu_inwon, edu_content, edu_chk, edu_wdate, edu_mdate, edu_ip, edu_login, edu_receive_type, edu_temp1, edu_temp2, edu_temp3, edu_temp4, edu_temp5, edu_resdate_h, edu_reedate_h, end_chk, edu_ptcp_yn, edu_ptcp_cnt, edu_field1_yn, edu_field2_yn, edu_field3_yn, edu_field4_yn, edu_field5_yn, edu_field6_yn, edu_field7_yn, edu_field8_yn";
	sql += ") values('" +nvl( program.get("CL_SEQ") );	//edu_idx;
	sql += "', '" +trg_ct_idx;
	sql += "', '" +ct_idx2;
	sql += "', '" +edu_code;
	sql += "', '" +trg_edu_lib;		//edu_lib;
	sql += "', '" +edu_gubun;
	sql += "', '";					//edu_gubun2;
	sql += "', '0";					//edu_season;
	sql += "', '2";					//edu_lot_type;
	sql += "', '"+nvl(program.get("CL_READY_PEOPLE")); 	//edu_awaiter;
	sql += "', '"+inputValue(program.get("CL_TITLE"));			//edu_subject;
	sql += "', '"+nvl(program.get("CL_LECTURER"));			//edu_teacher;
	sql += "', '"+nvl(program.get("CL_CANAPPLY_SDT")).substring(0,10);	//edu_resdate;
	sql += "', '"+nvl(program.get("CL_CANAPPLY_EDT")).substring(0,10);	//edu_reedate;
	sql += "', '" +edu_sdate;
	sql += "', '" +edu_edate;
	sql += "', '"+nvl(program.get("CL_LEC_TIME"));		//edu_time;
	sql += "', '" +edu_week;
	sql += "', '"+nvl(program.get("CL_LEC_PAY"));		//edu_money;
	sql += "', '" +edu_target;
	sql += "', '"+nvl(program.get("CL_MAX_PEOPLE"));	//edu_inwon;
	sql += "', '" +edu_content;
	sql += "', 'Y";					//edu_chk;
	sql += "', '"+nvl(program.get("CL_REG_DATE"));		//edu_wdate;
	sql += "', '"+nvl(program.get("CL_UPD_DATE"));		//edu_mdate;
	sql += "', '"+nvl(program.get("CL_REG_CLIENT"));		//edu_ip;
	sql += "', 'Y";					//edu_login;
	sql += "', '" +edu_receive_type;
	sql += "', '";					//edu_temp1;
	sql += "', '"+nvl(program.get("CL_PLACE"));		//edu_temp2;
	sql += "', '";					//edu_temp3;
	sql += "', '" +edu_temp4;
	sql += "', '" +edu_temp5;
	sql += "', '" +edu_resdate_h;		//edu_resdate_h;
	sql += "', '" +edu_reedate_h;		//edu_reedate_h;
	sql += "', '" +end_chk;
	sql += "', 'N";					//edu_ptcp_yn;
	sql += "', '1";					//edu_ptcp_cnt;
	sql += "', 'N";					//edu_field1_yn	(보호자명)
	sql += "', 'R";					//edu_field2_yn (생년월일)
	sql += "', 'R";					//edu_field3_yn (연락처)
	sql += "', 'R";					//edu_field4_yn (성별)
	sql += "', 'R";					//edu_field5_yn (학교)
	sql += "', 'N";					//edu_field6_yn; (이메일)
	sql += "', 'R";					//edu_field7_yn; (주소)
	sql += "', 'N";					//edu_field8_yn; (기타)
	sql += "')";
	//out.print("<br />sql = <pre>"+sql+"</pre>");
	//out.print("<br />"+b_num+". "+board.get("TITLEDS")+" - ");
	executeQuery( sql );
	//out.print(" ok.");
}
out.print("<br />"+trg_table+" trg_ct_idx="+trg_ct_idx+" "+programList.size()+" record insert ok - "+Func.date("Y-m-d H:i:s"));


//첨부파일조회
ArrayList<HashMap<String, String>> fileList = ST2.selectQueryTable("select CL_SEQ, CL_FILELINKSEQ, CL_FILELINKNM from "+src_table+"_FILELINK where CL_FILELINKNM is not null order by CL_SEQ");
for(int i=0;i<=fileList.size()-1;i++){
	HashMap<String,String>file = fileList.get(i);
	String CL_SEQ = nvl( file.get("CL_SEQ") );
	String CL_FILELINKSEQ = nvl( file.get("CL_FILELINKSEQ") );
	String CL_FILELINKNM = inputValue( file.get("CL_FILELINKNM") );
			
	if(cInt(CL_FILELINKSEQ) == 1){
		
		String sql = "update "+trg_table+" set edu_temp3 = '"+CL_FILELINKNM+"' where edu_idx = '"+CL_SEQ+"'";
		
		//out.print("<br />"+seq+"- "+filelinkseq+". "+filelinknm+" - ");
		executeQuery( sql );
		//out.print(" ok.");
	}
}
out.print("<br />"+trg_table+" trg_ct_idx="+trg_ct_idx+" file "+fileList.size()+" record update ok - "+Func.date("Y-m-d H:i:s"));
String cp_exec = " -rf /home/gjlib/www/boardData/"+src_table+"/* /websource/www/lib.gen.go.kr_2019/ROOT/data/edusat/";
cp_exec = cp_exec.replaceAll("/", "&#47;");
out.print("<br />c<span>p</span>"+cp_exec);


%>