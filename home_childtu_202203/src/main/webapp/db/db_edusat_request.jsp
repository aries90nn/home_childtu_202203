<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@
page import ="kr.co.nninc.ncms.common.Func" %><%@
include file="./dbclass.jsp" %><%

response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);


//기존디비
String src_table = "PJ_CL_PROGRAM_APPLY";
String src_sitecode = "MA";
String src_columns = "CL_APPLY_SEQ, USERID, CL_SEQ, CL_APPLY_NAME, CL_APPLY_AGE, CL_APPLY_GENDER, CL_APPLY_ORG, CL_APPLY_TEL, CL_APPLY_MOBILE, CL_APPLY_EMAIL";
src_columns +=", CL_APPLY_STATE, CL_APPLY_COMMENT, CL_APPLY_DELETE_YN, TO_CHAR(CL_APPLY_REG_DATE,'YYYY-MM-DD HH24:MI:SS') as CL_APPLY_REG_DATE, CL_APPLY_REG_CLIENT";
src_columns +=", CL_APPLY_UPD_DATE, CL_APPLY_UPD_ID, CL_APPLY_UPD_CLIENT";
src_columns +=", CL_APPLY_DEL_DATE, CL_APPLY_DEL_ID, CL_APPLY_DEL_CLIENT, CL_APPLY_CANCEL_DATE, CL_APPLY_ORDER, CL_APPLY_ZIP, CL_APPLY_ADDRESS, CL_APPLY_BIRTH, CL_APPLY_TP";

//대상디비
String trg_table = "edusat_request";
//4:학생독립기념회관, 32:금호평생교육관, 33:학생교육문화회관, 34:중앙도서관, 35:송정도서관, 36:석봉도서관
String trg_ct_idx = "4";



//기존디비 조회
SelectTable ST2 = new SelectTable();
ST2.connectReset("jdbc/old");
ArrayList<HashMap<String, String>> applyList = ST2.selectQueryTable("select "+src_columns+" from "+src_table+" where CL_SEQ in (select CL_SEQ from PJ_CL_PROGRAM where SITE_CODE = '"+src_sitecode+"' and CL_DELETE_YN = 'N') order by CL_APPLY_SEQ");


//대상디비 초기화
executeQuery("delete from "+trg_table+" where edu_idx in (select edu_idx from edusat where ct_idx = '"+trg_ct_idx+"' )");
InsertTable IT = new InsertTable();
IT.tableName = trg_table;
out.print("<br />"+trg_table+" ct_idx="+trg_ct_idx+" delete ok - "+Func.date("Y-m-d H:i:s"));

int edu_code = applyList.size()+1;
for(int i=0;i<=applyList.size()-1;i++){
	HashMap<String,String>apply = applyList.get(i);
	edu_code = edu_code - 1;
	//es_idx, edu_idx, es_br, es_name, es_zipcode, es_addr1, es_addr2, es_phone1, es_phone2, es_phone3, es_name2, es_bphone1, es_bphone2, es_bphone3, es_school, es_hak, es_wdate, es_sex, es_jumin, es_jumin_bak, es_status, es_ban, es_etc, es_pwd, edu_m_id, edu_ci, es_email, es_temp1, es_temp2, es_memo, es_ptcp_cnt, es_ptcp_name


	//집전화
	String es_phone1 = "";
	String es_phone2 = "";
	String es_phone3 = "";

	//모바일
	String es_bphone1 = "";
	String es_bphone2 = "";
	String es_bphone3 = "";
	String CL_APPLY_MOBILE = nvl(apply.get("CL_APPLY_MOBILE")).trim();
	CL_APPLY_MOBILE = CL_APPLY_MOBILE.replaceAll("-", "");
	CL_APPLY_MOBILE = CL_APPLY_MOBILE.replaceAll(" ", "");
	if(CL_APPLY_MOBILE.length() == 11){
		es_bphone1 = CL_APPLY_MOBILE.substring(0,3);
		es_bphone2 = CL_APPLY_MOBILE.substring(3,7);
		es_bphone3 = CL_APPLY_MOBILE.substring(7,11);
	}else if(CL_APPLY_MOBILE.length() == 10){
		es_bphone1 = CL_APPLY_MOBILE.substring(0,3);
		es_bphone2 = CL_APPLY_MOBILE.substring(3,6);
		es_bphone3 = CL_APPLY_MOBILE.substring(6,10);
	}

	//성별
	String es_sex = "";
	String CL_APPLY_GENDER = nvl(apply.get("CL_APPLY_GENDER")).trim();
	if("M".equals(CL_APPLY_GENDER)){
		es_sex = "1";
	}else if("F".equals(CL_APPLY_GENDER)){
		es_sex = "2";
	}

	//생년월일
	String es_jumin ="";
	String CL_APPLY_BIRTH = nvl(apply.get("CL_APPLY_BIRTH")).trim();
	if(!"".equals(CL_APPLY_BIRTH)){
		es_jumin = CL_APPLY_BIRTH.substring(0, 10).replaceAll("-", "");
	}

	//신청상태 0:신청완료, 1:당첨(추첨), 2:낙첨(추첨), 9:취소
	String es_status = "0";
	String CL_APPLY_STATE = nvl(apply.get("CL_APPLY_STATE")).trim();
	if("1".equals(CL_APPLY_STATE) || "3".equals(CL_APPLY_STATE)){
		es_status = "0";
	}else if( "2".equals(CL_APPLY_STATE) || "4".equals(CL_APPLY_STATE) ){
		es_status = "9";
	}



	String sql = "insert into "+trg_table+"(";
	sql += "edu_idx, es_br, es_name, es_zipcode, es_addr1, es_addr2, es_phone1, es_phone2, es_phone3, es_name2, es_bphone1, es_bphone2, es_bphone3, es_school, es_hak, es_wdate, es_sex, es_jumin, es_jumin_bak, es_status, es_ban, es_etc, es_pwd, edu_m_id, edu_ci, es_email, es_temp1, es_temp2, es_memo, es_ptcp_cnt, es_ptcp_name";
	sql += ") values('" + nvl(apply.get("CL_SEQ"));	//edu_idx
	sql += "', '";					//es_br
	sql += "', '" + inputValue(apply.get("CL_APPLY_NAME"));	//es_name
	sql += "', '" + inputValue(apply.get("CL_APPLY_ZIP"));	//es_zipcode
	sql += "', '" + inputValue(apply.get("CL_APPLY_ADDRESS"));	//es_addr1
	sql += "', '";					//es_addr2
	sql += "', '"+es_phone1;		//es_phone1
	sql += "', '"+es_phone2;		//es_phone2
	sql += "', '"+es_phone3;		//es_phone3
	sql += "', '";					//es_name2
	sql += "', '"+es_bphone1;		//es_bphone1
	sql += "', '"+es_bphone2;		//es_bphone2
	sql += "', '"+es_bphone3;		//es_bphone3
	sql += "', '"+inputValue(apply.get("CL_APPLY_ORG"));	//es_school
	sql += "', '";					//es_hak
	sql += "', '"+nvl(apply.get("CL_APPLY_REG_DATE"));	//es_wdate
	sql += "', '"+es_sex;			//es_sex
	sql += "', '"+es_jumin;			//es_jumin
	sql += "', '";					//es_jumin_bak
	sql += "', '"+es_status;		//es_status
	sql += "', '";					//es_ban
	sql += "', '";					//es_etc
	sql += "', '";					//es_pwd
	sql += "', '"+nvl(apply.get("USERID"));	//edu_m_id
	sql += "', '";				//edu_ci
	sql += "', '"+nvl(apply.get("CL_APPLY_EMAIL"));		//es_email
	sql += "', '";					//es_temp1
	sql += "', '";					//es_temp2
	sql += "', '";					//es_memo
	sql += "', '1";					//es_ptcp_cnt
	sql += "', '";					//es_ptcp_name
	sql += "')";
	//out.print("<br />sql = <pre>"+sql+"</pre>");
	//out.print("<br />"+b_num+". "+board.get("TITLEDS")+" - ");
	executeQuery( sql );
	//out.print(" ok.");
}
out.print("<br />"+trg_table+" trg_ct_idx="+trg_ct_idx+" "+applyList.size()+" record insert ok - "+Func.date("Y-m-d H:i:s"));


%>