<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.lang.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.math.*"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="java.util.Enumeration, java.net.URLEncoder" %>
<%@ page import="kr.co.nninc.ncms.common.CommonConfig" %>
<%@ page import="kr.co.nninc.ncms.common.Func" %>
<%@ page import="kr.co.nninc.ncms.common.FuncThumb" %>
<%@ page import="java.security.*"%>
<%@ page import="java.net.*"%>
<%@ page import="java.util.regex.Matcher"%>
<%@ page import="java.util.regex.Pattern"%>
<%!
JspWriter OUT;
HttpServletRequest REQUEST;
%>
<%
OUT = out;
REQUEST = request;
%>
<%!
public class DBConnect{

	private String strContext = "jdbc/nninc";

	private Context env = null;
	private DataSource ds = null;

	protected Connection con = null;
	protected Statement dbcon = null;

	public DBConnect() throws NamingException{
		this.env = new InitialContext();
		this.ds = (DataSource)this.env.lookup("java:comp/env/"+this.strContext);
	}

	protected void connect() throws SQLException{
		this.con = this.ds.getConnection();
		this.dbcon = this.con.createStatement();
	}

	protected void disConnect(){
		if ( this.dbcon != null ){ try{this.dbcon.close();this.dbcon=null;}catch(Exception e){} }
		if ( this.con != null ){ try{this.con.close();this.con=null;}catch(Exception e){} }
	}

	public void connectReset(String strContext) throws NamingException{
		this.strContext = strContext;
		this.ds = (DataSource)this.env.lookup("java:comp/env/"+this.strContext);
	}

}

//select
public class SelectTable extends DBConnect{

	private ResultSet rs = null;

	public SelectTable() throws NamingException{
	}

	//단일필드
	public String selectQueryColumn( String sql ) throws SQLException, NamingException, Exception{
		String ret_value = null;

		try{
			this.selectQuery( sql );
			ResultSetMetaData rsmd	= this.rs.getMetaData();

			if( this.rs.next() ){
				switch( rsmd.getColumnType(1) ){
					case 2005 :	//clob 형태 가져오기
						ret_value = this.getClobValue( this.rs.getClob(1) );
						break;
					default :
						ret_value = this.rs.getString(1);
				}
			}
		}catch(SQLException ex) {
			OUT.print("<br />"+ex);
		}finally{
			this.dbClose();
		}

		return ret_value;
	}


	//단일행
	public HashMap selectQueryRecord( String sql ) throws SQLException, NamingException, Exception{
		HashMap<String, String> ret_value = new HashMap<String, String>();

		try{
			int col_cnt = 0;
			this.selectQuery( sql );
			ResultSetMetaData rsmd	= this.rs.getMetaData();
			col_cnt					= rsmd.getColumnCount();

			if(this.rs.next()){
				for(int i=1;i<=col_cnt;i++){
					switch( rsmd.getColumnType(i) ){
						case 2005 :	//clob 형태 가져오기
							ret_value.put(rsmd.getColumnName(i), this.getClobValue( this.rs.getClob(i) ));
							break;
						default :
							ret_value.put(rsmd.getColumnName(i), this.rs.getString(i));
					}

				}
			}

		}catch(SQLException ex) {
			OUT.print("<br />"+ex);
		}finally{
			this.dbClose();
		}

		return ret_value;
	}

	//2차원 배열
	public ArrayList selectQueryTable( String sql ) throws SQLException, NamingException, Exception{
		
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		try{
			int col_cnt = 0;
			this.selectQuery( sql );
			ResultSetMetaData rsmd	= this.rs.getMetaData();
			col_cnt					= rsmd.getColumnCount();

			while(this.rs.next()){
				HashMap<String, String> ret_value = new HashMap<String, String>();

				for(int i=1;i<=col_cnt;i++){

					switch( rsmd.getColumnType(i) ){
						case 2005 :	//clob 형태 가져오기
							ret_value.put(rsmd.getColumnName(i), this.getClobValue( this.rs.getClob(i) ));
							break;
						default :
							ret_value.put(rsmd.getColumnName(i), this.rs.getString(i));
					}

				}
				list.add(ret_value);
			}

		}catch(SQLException ex) {
			OUT.print("<br />"+ex);
		}finally{
			this.dbClose();
		}

		return list;
	}

	private void selectQuery( String sql ) throws SQLException, NamingException{
		super.connect();
		this.rs = super.dbcon.executeQuery(sql);
	}

	private void dbClose(){
		if(this.rs != null){ try{this.rs.close();this.rs = null;}catch(Exception e){}  }
		super.disConnect();
	}

	private String getClobValue( Clob value ) throws SQLException{

		String temp = null;							//임시 버퍼
		String return_value = "";

		try{
			BufferedReader br = new BufferedReader( value.getCharacterStream() );
			int i = 0;
			while( (temp = br.readLine()) != null){
				if(i>0){return_value = return_value + "\n";}
				return_value = return_value + temp;
				i++;
			}
		}catch( Exception e ){
		}
		return return_value;
	}


}

public class InsertTable extends DBConnect{
	public String tableName = "";

	private HashMap<String, String> insertValues = new HashMap<String, String>();
	private HashMap<String, String> addValues = new HashMap<String, String>();

	public InsertTable() throws NamingException{}

	public String execute() throws Exception{

		String sql = "insert into "+this.tableName+"(";
		String i_fields = "";

		Set<String> keys;
		Iterator<String> it;

		if(this.addValues.size() > 0){
			keys = this.addValues.keySet();
			it = keys.iterator();

			while( it.hasNext() ){
				String key = it.next();
				if(!i_fields.equals("")){ i_fields += ","; }
				i_fields += key;
			}
		}

		keys = this.insertValues.keySet();
		it = keys.iterator();
		while( it.hasNext() ){
			String key = it.next();
			if(!i_fields.equals("")){ i_fields += ","; }
			i_fields += key;
		}
		sql += i_fields+") values(";

		String i_values = "";
		if(this.addValues.size() > 0){
			keys = this.addValues.keySet();
			it = keys.iterator();

			while( it.hasNext() ){
				String key = it.next();
				String value = addValues.get( key );
				if(!i_values.equals("")){ i_values += ","; }
				i_values += value;
			}
		}

		keys = this.insertValues.keySet();
		it = keys.iterator();
		while( it.hasNext() ){
			String key = it.next();
			String value = insertValues.get( key );
			if(!i_values.equals("")){ i_values += ","; }
			i_values += "'"+value+"'";
		}

		sql += i_values+")";
		//OUT.print(sql );
		try{
			this.insertQuery( sql );
		}catch(SQLException ex) {
		}finally{
			//연관배열 초기화
			this.insertValues.clear();
			this.addValues.clear();
			return sql;
		}


	}


	private void insertQuery( String sql ) throws SQLException, NamingException, Exception{
		super.connect();
		try{
			super.dbcon.executeUpdate( sql );
		}catch(SQLException ex) {
			OUT.print("<br />"+ex);
		}finally{
			super.disConnect();
		}
	}

	public void setValues(String key, String value){
		if(value == null){value = "";}
		this.insertValues.put(key, value);
	}

	public void setAddValues(String key, String value){
		if(value == null){value = "";}
		this.addValues.put(key, value);
	}

	//필드명으로 연관배열 추가
	public void setFieldsValues( String str_fileds ){

		str_fileds = str_fileds.replaceAll(" ", "");
		String[] str_fileds_arr = str_fileds.split(",");

		for(int z=0;z<=str_fileds_arr.length-1;z++){
			this.setValues( str_fileds_arr[z], this.inputValue( REQUEST.getParameter(str_fileds_arr[z]) ) );
		}
	}

	//필드명으로 연관배열 추가 MultipartRequest
	public void setFieldsValues( String str_fileds , com.oreilly.servlet.MultipartRequest multi){

		str_fileds = str_fileds.replaceAll(" ", "");
		String[] str_fileds_arr = str_fileds.split(",");

		for(int z=0;z<=str_fileds_arr.length-1;z++){
			this.setValues( str_fileds_arr[z], this.inputValue( multi.getParameter(str_fileds_arr[z]) ) );
		}
	}

	//필드명으로 연관배열 추가 HashMap
	public void setFieldsValues( String str_fileds , HashMap<String, String>map){
		str_fileds = str_fileds.replaceAll(" ", "");
		String[] str_fileds_arr = str_fileds.split(",");

		for(int z=0;z<=str_fileds_arr.length-1;z++){
			this.setValues( str_fileds_arr[z], map.get( str_fileds_arr[z] ) );
		}
	}

	private String inputValue(String strvalue){
		if(strvalue != null){
			strvalue = strvalue.trim();
			strvalue = strvalue.replaceAll("'", "&#39;");
			strvalue = strvalue.replaceAll("\"", "&#34;");
			strvalue = strvalue.replaceAll("<", "&lt;");
			strvalue = strvalue.replaceAll(">", "&gt;");
			return strvalue;
		}else{
			return "";
		}
	}

}

public class UpdateTable extends DBConnect{
	public String tableName = "";
	public String whereQuery = "";

	private HashMap<String, String> updateValues = new HashMap<String, String>();
	private HashMap<String, String> addValues = new HashMap<String, String>();

	public UpdateTable() throws NamingException{}

	public String execute() throws Exception{

		String sql = "update "+this.tableName+" set ";

		Set<String> keys;
		Iterator<String> it;


		String u_values = "";

		if(this.addValues.size() > 0){
			keys = this.addValues.keySet();
			it = keys.iterator();

			while( it.hasNext() ){
				String key = it.next();
				String value = addValues.get( key );
				if(!u_values.equals("")){ u_values += ","; }
				u_values += key + " = " + value;
			}
		}

		keys = this.updateValues.keySet();
		it = keys.iterator();
		while( it.hasNext() ){
			String key = it.next();
			String value = updateValues.get( key );
			if(!u_values.equals("")){ u_values += ","; }
			u_values += key + " = '" + value + "'";
		}

		sql += u_values+" "+this.whereQuery;
		this.updateQuery( sql );

		try{
			this.updateQuery( sql );
		}catch(SQLException ex) {
		}finally{
			//연관배열 초기화
			this.updateValues.clear();
			this.addValues.clear();
			return sql;
		}
	}


	private void updateQuery( String sql ) throws SQLException, NamingException, Exception{
		super.connect();
		try{
			super.dbcon.executeUpdate( sql );
		}catch(SQLException ex) {
			OUT.print("<br />"+ex);
		}finally{
			super.disConnect();
		}
	}

	public void setValues(String key, String value){
		if(value == null){value = "";}
		this.updateValues.put(key, value);
	}

	public void setAddValues(String key, String value){
		if(value == null){value = "";}
		this.addValues.put(key, value);
	}

	//필드명으로 연관배열 추가
	public void setFieldsValues( String str_fileds ){

		str_fileds = str_fileds.replaceAll(" ", "");
		String[] str_fileds_arr = str_fileds.split(",");

		for(int z=0;z<=str_fileds_arr.length-1;z++){
			this.setValues( str_fileds_arr[z], this.inputValue( REQUEST.getParameter(str_fileds_arr[z]) ) );
		}
	}

	//필드명으로 연관배열 추가 MultipartRequest
	public void setFieldsValues( String str_fileds, com.oreilly.servlet.MultipartRequest multi ){

		str_fileds = str_fileds.replaceAll(" ", "");
		String[] str_fileds_arr = str_fileds.split(",");

		for(int z=0;z<=str_fileds_arr.length-1;z++){
			this.setValues( str_fileds_arr[z], this.inputValue( multi.getParameter(str_fileds_arr[z]) ) );
		}
	}

	//필드명으로 연관배열 추가 HashMap
	public void setFieldsValues( String str_fileds , HashMap<String, String>map){

		str_fileds = str_fileds.replaceAll(" ", "");
		String[] str_fileds_arr = str_fileds.split(",");

		for(int z=0;z<=str_fileds_arr.length-1;z++){
			this.setValues( str_fileds_arr[z], map.get( str_fileds_arr[z] ) );
		}
	}

	private String inputValue(String strvalue){
		if(strvalue != null){
			strvalue = strvalue.trim();
			strvalue = strvalue.replaceAll("'", "&#39;");
			strvalue = strvalue.replaceAll("\"", "&#34;");
			strvalue = strvalue.replaceAll("<", "&lt;");
			strvalue = strvalue.replaceAll(">", "&gt;");
			return strvalue;
		}else{
			return "";
		}
	}
}


//clob 등록
public class UpdateClob extends DBConnect{
	private ResultSet rs = null;

	public String field = null;
	public String tableName = null;
	public String whereQuery = null;
	public String value = null;

	public UpdateClob() throws NamingException{}

	public void execute() throws IOException, SQLException, NamingException, Exception{

		try{
			super.connect();
			super.con.setAutoCommit(false);
			
			String sql = "";

			sql = "update "+this.tableName+" set "+this.field+" = empty_clob() "+this.whereQuery;
			super.dbcon.executeUpdate( sql );

			sql = "select "+field+" from "+tableName+" "+whereQuery+" for update";
			this.rs = super.dbcon.executeQuery( sql );
		//	if(rs.next()){
			while(this.rs.next()){
				Clob c1 = this.rs.getClob(field);
				BufferedWriter writer = new BufferedWriter( c1.setCharacterStream(0) );

		//		CLOB cl = ((OracleResultSet)rs).getCLOB(field);
		//		BufferedWriter writer = new BufferedWriter(cl.getCharacterOutputStream());

				writer.write( value.toString() );
				writer.close();
			}
			super.con.commit();
		}catch(SQLException ex) {
			OUT.print("<br />"+ex);
		}finally{
			if(this.rs != null){ try{this.rs.close();this.rs = null;}catch(Exception e){}  }
			super.disConnect();
		}
	}

}





//쿼리실행
public void executeQuery(String sql) throws SQLException, NamingException, Exception{
	DBConnect DB = new DBConnect();
	DB.connect();
	try{
		DB.dbcon.executeUpdate(sql);
	}catch(SQLException ex) {
		OUT.print("<br />"+ex);
	}finally{
		DB.disConnect();
	}
}

//쿼리실행 리소스네임변경
public void executeQuery(String sql, String strContext) throws SQLException, NamingException, Exception{
	DBConnect DB = new DBConnect();
	DB.connectReset( strContext );
	DB.connect();
	try{
		DB.dbcon.executeUpdate(sql);
	}catch(SQLException ex) {
		OUT.print("<br />"+ex);
	}finally{
		DB.disConnect();
	}
}


//형변환
public int cInt(String strValue){

	try{
		return Integer.parseInt(strValue);
	}catch(Exception e){
		return 0;
	}
}


//'**************  Information  ****************************************
//'	Program Title	:	문자열필터링
//'	Company		    :	나눔아이앤씨 
//'*********************************************************************
public String inputValue(String strvalue) {
	if(strvalue != null){
		strvalue = strvalue.trim();
		strvalue = strvalue.replaceAll("'", "''");
		strvalue = strvalue.replaceAll("\"", "&#34;");
		return strvalue;
	}else{
		return "";
	}
}

public String inputValueText(String strvalue) {
	if(strvalue != null){
		strvalue = strvalue.trim();
		strvalue = strvalue.replaceAll("'", "''");
		strvalue = strvalue.replaceAll("\"", "&#34;");
		strvalue = strvalue.replaceAll(" ", "&nbsp;");
		strvalue = strvalue.replaceAll("\n", "<br />");
		return strvalue;
	}else{
		return "";
	}
}


public String nvl(String strvalue) {
	if(strvalue == null){
		return "";
	}else{
		return strvalue;
	}
}


%>