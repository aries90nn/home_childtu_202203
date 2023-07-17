package kr.co.nninc.ncms.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;

import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.homepage_log_work.service.HomepageLogWorkDTO;

/**
 * 함수모음
 * 
 * @author 나눔
 * @since 2017.09.04
 * @version 1.0
 */
@Component("Func")
public class Func {
	
	/** 동적dao */
	private static ExtendDAO exdao;

	@Autowired
	private Func(ExtendDAO exdao2) {
		this.exdao = exdao2;
	}

	/**
	 * @title : 암호화
	 * @method : hash(String hashtype, String str)
	 * @see :  MD5, MD4, SHA-1, SHA-256, SHA-512
	 */
	public String hash(String hashtype, String str){
		String SHA = "";
		try{
			MessageDigest sh = MessageDigest.getInstance(hashtype); 
			sh.update(str.getBytes()); 

			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 

			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();

		}catch(Exception e){
			SHA = e.toString();
		}
		return SHA;
	}
	
	public static String base64Encode(String str)  throws java.io.IOException {
		if ( str == null || str.equals("") ) {
			return "";
		} else {
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			byte[] b1 = str.getBytes();
			String result = encoder.encode(b1);
			return result;
		}
	}

	public static String base64Decode(String str)  throws java.io.IOException {
		if ( str == null || str.equals("") ) {
			return "";
		} else {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			byte[] b1 = decoder.decodeBuffer(str);
			String result = new String(b1);
			return result;
		}
	}
	
	
	
	/**
	 * @title : 변수필터
	 * @method : InputValue(String)
	 */
	public static String InputValue(String strvalue) {
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
	
	/**
	 * @title : 변수 null > 공백처리
	 * @method : requestAll(Object dto)
	 */
	public static Object requestAll(Object dto) throws Exception {
		Class dtoClass = dto.getClass();
		Field[] fields = dtoClass.getDeclaredFields();
		
		// 파라미터 초기데이터 집어넣기
		if (dto == null) dto = dtoClass.newInstance();
		try {
			Object obj = dto;
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				
				Object value = field.get(obj);
				if(value == null) {
					if (!"serialVersionUID".equals(field.getName())) {
						//초기화할값넣기
						field.set(obj, "");
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return dto;
	}
	
	
	/**
	 * @title : 변수 null > 공백처리
	 * @method : requestAllMap(HashMap<String,String> map)
	 */
	public static HashMap<String,String> requestAllMap(HashMap<String,String> map) throws Exception {
		
		for (Map.Entry<String, String> entry: map.entrySet()) {
			//System.out.println(entry.getKey()+"="+entry.getValue());
			if(entry.getValue() == null){
				map.put(entry.getKey(), "");
			}
			//System.out.println(entry.getKey()+"="+map.get(entry.getKey()));
		}
		
		return map;
	}
	
	/**
	 * @title : 변수필터
	 * @method : InputValue2(String)
	 */
	public static String InputValue2(String strvalue) {
		if(strvalue != null){
			strvalue = strvalue.trim();
			strvalue = strvalue.replaceAll("'", "&#39;");
			return strvalue;
		}else{
			return "";
		}
	}
	
	/**
	 * @title : 변수필터
	 * @method : InputValue3(String)
	 */
	public int InputValue3(String strvalue) {
		if(strvalue != null){
			
			return Integer.parseInt(strvalue);
			
		}else{
			
			return 0;
		}
	}
	
	/**
	 * @title : 변수필터
	 * @method : InputValue4(String)
	 */
	public static int InputValue4(String strvalue) {
		if(strvalue != null){
			return Integer.parseInt(strvalue);
		}else{
			return 1;
		}
	}
	
	/**
	 * @title : 변수필터(XSS처리)
	 * @method : InputValueXSS(String)
	 */
	public static String InputValueXSS(String strvalue) {
		if(strvalue != null){
			strvalue = strvalue.trim();
			strvalue = strvalue.replaceAll("<(no)?script[^>]*>.*?</(no)?script>","");
			strvalue = strvalue.replaceAll("(?i)<script([^'\"]|\"[^\"]*\"|'[^']*')*?</script>","");
			strvalue = strvalue.replaceAll("(?i)alert", "");
			strvalue = strvalue.replaceAll("(?i)confirm", "");
			strvalue = strvalue.replaceAll("(?i)onerror", "");
			strvalue = strvalue.replaceAll("(?i)onclick", "");
			strvalue = strvalue.replaceAll("(?i)ondblclick", "");
			strvalue = strvalue.replaceAll("(?i)onmouseover", "");
			strvalue = strvalue.replaceAll("(?i)onmouseout", "");
			strvalue = strvalue.replaceAll("(?i)onmousedown", "");
			strvalue = strvalue.replaceAll("(?i)onmouseup", "");
			strvalue = strvalue.replaceAll("(?i)onmousemove", "");
			strvalue = strvalue.replaceAll("(?i)oncontextmenu", "");
			strvalue = strvalue.replaceAll("(?i)onkeydown", "");
			strvalue = strvalue.replaceAll("(?i)onkeyup", "");
			strvalue = strvalue.replaceAll("(?i)onkeypress", "");
			strvalue = strvalue.replaceAll("(?i)onblur", "");
			strvalue = strvalue.replaceAll("(?i)onfocus", "");
			strvalue = strvalue.replaceAll("(?i)onchange", "");
			strvalue = strvalue.replaceAll("(?i)onreset", "");
			strvalue = strvalue.replaceAll("(?i)onselect", "");
			strvalue = strvalue.replaceAll("(?i)onload", "");
			strvalue = strvalue.replaceAll("(?i)onabort", "");
			strvalue = strvalue.replaceAll("(?i)onunload", "");
			strvalue = strvalue.replaceAll("(?i)onresize", "");
			strvalue = strvalue.replaceAll("(?i)onscroll", "");

			// 이모지 제거 정규식인데 '-'까지 치환해버리는 문제가 있음
			strvalue = strvalue.replaceAll("-", "!hyp!");
			Pattern emoticons = Pattern.compile("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+");
			Matcher emoticonsMather = emoticons.matcher(strvalue);
			strvalue = emoticonsMather.replaceAll(" ");
			strvalue = strvalue.replaceAll("!hyp!", "-");

			return strvalue;
		}else{
			return "";
		}
	}
	
	/**
	 * @title : 변수필터
	 * @method : OutputValue(String)
	 */
	public String OutputValue(String strvalue) {
		if(strvalue != null){		
			strvalue = strvalue.trim();
			strvalue = strvalue.replaceAll("&#39;", "'");
			strvalue = strvalue.replaceAll("&#34;", "\"");
			strvalue = strvalue.replaceAll("&lt;", "<");
			strvalue = strvalue.replaceAll("&gt;", ">");
			return strvalue;
		}else{
			return "";
		}
	}
	
	/**
	 * @title : 텍스트모드로 변환
	 * @method : getTextmode(String)
	 */
	public static String getTextmode(String strvalue) {
		if(strvalue != null){

			strvalue = strvalue.trim();
			strvalue = strvalue.replaceAll("<", "&lt;");
			strvalue = strvalue.replaceAll(">", "&gt;");
			strvalue = strvalue.replaceAll("  ", "&nbsp;&nbsp;");
			strvalue = strvalue.replaceAll("\t", "&nbsp;&nbsp;&nbsp;");
			strvalue = strvalue.replaceAll("\r\n", "<br />");
			strvalue = strvalue.replaceAll("\r", "<br />");
			strvalue = strvalue.replaceAll("\n", "<br />");

			return strvalue;
		}else{
			return "";
		}
	}
	
	/**
	 * @title : 대상 String이 null일 경우 ""을, null이 아닐 경우 대상 String을 return
	 * @method : nvl(String)
	 */
	public static String nvl(String str) {
		try {
			if (str == null){ return "";}
			else{ return str.trim();}
		}catch(Exception e){
			return "";
		}
	}

	/**
	 * @title : 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
	 * @method : hash(String)
	 */
	public static String hash(String password) throws Exception {
		String SHA = "";
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(password.getBytes());
	
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
	
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
		}catch(Exception e){
			SHA = e.toString();
		}
		
		return new String(SHA);
	}
	
	/**
	 * @title : 숫자형>문자열변환
	 * @method : cStr(int)
	 */
	public static String cStr(int intValue){

		try{
			return Integer.toString(intValue);
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * @title : 숫자형>문자열변환
	 * @method : cStr(long)
	 */
	public static String cStr(long lngValue){

		try{
			return Long.toString(lngValue);
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * @title : 숫자형>문자열변환
	 * @method : cStr(double)
	 */
	public static String cStr(double dblValue){

		try{
			return Double.toString(dblValue);
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * @title : 숫자형>문자열변환
	 * @method : cStr(float)
	 */
	public static String cStr(float floatValue){

		try{
			return Float.toString(floatValue);
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * @title : 숫자형>문자열변환
	 * @method : cStr(BigDecimal)
	 */
	public static String cStr(BigDecimal bdValue){

		try{
			return bdValue.toString();
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * @title : 문자열>문자열변환
	 * @method : cStr(String)
	 */
	public static String cStr(String strValue){
		if(strValue == null){
			return "";
		}else{
			return strValue;
		}
	}
	
	/**
	 * @title : 객체>문자열변환
	 * @method : cStr(Object)
	 */
	public static String cStr(Object objValue){
		try{
			return String.valueOf(objValue);
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * @title : 문자열>숫자형변환
	 * @method : cInt(String)
	 */
	public static int cInt(String strValue){
		try{
			return Integer.parseInt(strValue);
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * @title : 문자열>숫자형변환
	 * @method : cInt(String)
	 */
	public static int cInt(Object objValue){
		try{
			return Integer.parseInt( Func.cStr(objValue) );
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * @title : 문자열>Long변환
	 * @method : cLng(String)
	 */
	public static long cLng(String strValue){
		try{
			return Long.parseLong(strValue);
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * @title : BigDecimal>숫자형변환
	 * @method : cInt(String)
	 */
	public static int cInt(BigDecimal strValue){

		try{
			return strValue.intValue();
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * @title : String>float변환
	 * @method : cflt(String)
	 */
	public static float cflt(String strValue){
	
		try{
			return Float.parseFloat(strValue);
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * @title : BigDecimal>float변환
	 * @method : cflt(BigDecimal)
	 */
	public static float cflt(BigDecimal strValue){
	
		try{
			return strValue.floatValue();
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * @title : double>String변환
	 * @method : cDbl(String)
	 */
	public static double cDbl(String strValue){
	
		try{
			return Double.parseDouble(strValue);
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * @title : double>BigDecimal변환
	 * @method : cDbl(BigDecimal)
	 */
	public static double cDbl(BigDecimal strValue){
	
		try{
			return strValue.doubleValue();
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * @title : 태그제거
	 * @method : strip_tags(String)
	 */
	public static String strip_tags(String word) throws Exception {
		boolean flag=false;
		StringBuffer sb=new StringBuffer();
		String tmp="";
		for (int i=0; i<word.length(); i++) {
			tmp=word.substring(i,i+1);
			flag=flag||"<".equals(tmp);
			if (!flag) sb.append(tmp);
			if (">".equals(tmp)) flag=false;
		}
		return sb.toString();
	}
	
	/**
	 * @title : 태그제거
	 * @method : remove_tags(String)
	 */
	public static String remove_tags(String word) throws Exception {
		
		return strip_tags(word);
	}
	
	/**
	 * @title : 날짜
	 * @method : date(String)
	 */
	public static String date(String strValue){
		Calendar cal2 = Calendar.getInstance();
		String strYear;
		String strMonth;
		String strDay;
		String strWeek;

		int yy = cal2.get(Calendar.YEAR);						//현재연도
		int mm = cal2.get(Calendar.MONTH)+1;					//현재월
		int dd = cal2.get(Calendar.DATE);						//현재일
		int ww = cal2.get(Calendar.DAY_OF_WEEK);				//현재요일

		strYear = Integer.toString(yy);
		strMonth = Integer.toString(mm);
		strDay = Integer.toString(dd);
		strWeek = Integer.toString(ww);
		String strWeek_han = "";

		if(strMonth.length() == 1){strMonth = "0"+strMonth;}
		if(strDay.length() == 1){strDay = "0"+strDay;}

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		String TODAY = formatter.format(new java.util.Date());		//현재일시(yyyyMMddHHmmss)
		String strHour = TODAY.substring(8,14).substring(0,2);		//현재 시간(0시~24시까지 표현)
		
		String apm = "AM";
		String strHour2 = strHour;		//현재 시간(0시~12시까지 표현)
		if(cInt(strHour2) > 12){
			apm = "PM";
			strHour2 = cStr( cInt(strHour2) - 12 );
		}
		if(strHour2.length() == 1){strHour2 = "0"+strHour2;}

		String strMinute = TODAY.substring(8,14).substring(2,4);	//현재 분
		String strSecond = TODAY.substring(8,14).substring(4,6);	//현재 초
		
		switch(cInt(strWeek)) {
			case 1 :	strWeek_han = "일"; break;
			case 2 :	strWeek_han = "월"; break;
			case 3 :	strWeek_han = "화"; break;
			case 4 :	strWeek_han = "수"; break;
			case 5 :	strWeek_han = "목"; break;
			case 6 :	strWeek_han = "금"; break;
			case 7 :	strWeek_han = "토"; break;
		}

		strValue = strValue.replaceAll("Y", strYear);
		strValue = strValue.replaceAll("y", strYear.substring(2,4));
		strValue = strValue.replaceAll("m", strMonth);
		strValue = strValue.replaceAll("n", cStr(mm));
		strValue = strValue.replaceAll("d", strDay);
		strValue = strValue.replaceAll("j", cStr(dd));
		strValue = strValue.replaceAll("H", strHour);
		strValue = strValue.replaceAll("h", strHour2);
		strValue = strValue.replaceAll("i", strMinute);
		strValue = strValue.replaceAll("s", strSecond);
		strValue = strValue.replaceAll("w", strWeek);
		strValue = strValue.replaceAll("q", strWeek_han);
		strValue = strValue.replaceAll("a", apm);

		return strValue;
	}
	
	/**
	 * @title : 날짜
	 * @method : date(String "Ymd", String "YYYY-mm-dd HH:ii:ss")
	 */
	public static String date(String strValue, String strDate){
		try{
			String[] strDate_arr = strDate.split(" ");
			String[] strDate_arr_1 = strDate_arr[0].split("-");
			
			String strYear = strDate_arr_1[0];
			String strMonth = strDate_arr_1[1];
			String strDay = strDate_arr_1[2];
			String strHour = "00";
			String strMinute = "00";
			String strSecond = "00";
			String strWeek = "";
			String strWeek_han = "";

			if(strDate_arr.length > 1){
				String[] strDate_arr_2 = strDate_arr[1].split(":");
				strHour = strDate_arr_2[0];
				strMinute = strDate_arr_2[1];
				if(strDate_arr_2.length > 2){
					strSecond = strDate_arr_2[2];
				}
			}

			String strHour2 = strHour;		//현재 시간(0시~12시까지 표현)
			if(cInt(strHour2) > 12){strHour2 = cStr( cInt(strHour2) - 12 );}
			
			//----------요일을 뽑기 위해---------
			Calendar cal2 = Calendar.getInstance();
			cal2.set(Calendar.YEAR, cInt(strYear));		//년도
			cal2.set(Calendar.MONTH, cInt(strMonth)-1);	//월
			cal2.set(Calendar.DATE, cInt(strDay));		//일
			strWeek = cStr( cal2.get(Calendar.DAY_OF_WEEK) );

			switch(cInt(strWeek)) {
				case 1 :	strWeek_han = "일"; break;
				case 2 :	strWeek_han = "월"; break;
				case 3 :	strWeek_han = "화"; break;
				case 4 :	strWeek_han = "수"; break;
				case 5 :	strWeek_han = "목"; break;
				case 6 :	strWeek_han = "금"; break;
				case 7 :	strWeek_han = "토"; break;
			}
			//----------요일을 뽑기 위해---------

			strValue = strValue.replaceAll("Y", strYear);
			strValue = strValue.replaceAll("y", strYear.substring(2,4));
			strValue = strValue.replaceAll("m", strMonth);
			strValue = strValue.replaceAll("n", cStr( cInt(strMonth) ) );
			strValue = strValue.replaceAll("d", strDay);
			strValue = strValue.replaceAll("j", cStr( cInt(strDay) ) );
			strValue = strValue.replaceAll("H", strHour);
			strValue = strValue.replaceAll("h", strHour2);
			strValue = strValue.replaceAll("i", strMinute);
			strValue = strValue.replaceAll("s", strSecond);
			strValue = strValue.replaceAll("w", strWeek);
			strValue = strValue.replaceAll("q", strWeek_han);

		}catch(Exception e){
			strValue = "";
		}
		return strValue;
	}
	
	/**
	 * @title : 날짜더하기
	 * @method : addGetDate(int)
	 */
	public final String addGetDate(int addMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, addMonth);
		 
		// 특정 형태의 날짜로 값을 뽑기
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = df.format(cal.getTime());
		return strDate;
	}

	/**
	 * @title : 날짜더하기2(포맷에 맞추기)
	 * @method : addGetDate2(int, String)
	 */
	public static final String addGetDate2(int addMonth, String Format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, addMonth);

		// 특정 형태의 날짜로 값을 뽑기
		DateFormat df = new SimpleDateFormat(Format);
		String strDate = df.format(cal.getTime());
		return strDate;
	}
	
	
	/**
	 * @title : 날짜더하기2(포맷에 맞추기)
	 * @method : addGetDate(String, int)
	 */
	public final String addGetDate(String isdate, int addMonth){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String strDate = null;
		Date date;
		try {
			date = df.parse(isdate);
			
			// 날짜 더하기
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, addMonth);
			strDate = df.format(cal.getTime());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	
	/**
	 * @title : 날짜더하기(포맷에 맞추기)
	 * @method : addGetDay(String, int)
	 */
	public static String addGetDay(String isdate, int addDay){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String strDate = null;
		Date date;
		try {
			date = df.parse(isdate);
			
			// 날짜 더하기
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, addDay);
			strDate = df.format(cal.getTime());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	/**
	 * @title : 분더하기(포맷에 맞추기)
	 * @method : addGetMinute(String, int)
	 */
	public static String addGetMinute(String isdate, int addMinute){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String strDate = null;
		Date date;
		try {
			date = df.parse(isdate);
			
			// 날짜 더하기
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE, addMinute);
			strDate = df.format(cal.getTime());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}	
	
	/**
	 * @title : 현재페이지 정보
	 * @method : getNowPage(HttpServletRequest)
	 */
	public static String getNowPage(HttpServletRequest request) {
		String nowpage = (String) request.getAttribute("javax.servlet.forward.request_uri");
		if(nowpage == null){
			nowpage = (String) request.getRequestURI();
		}
		if(nowpage == null){nowpage = "";}
		String path_query		= request.getQueryString();
		if(path_query != null){
			nowpage += "?"+path_query;
		}
		request.setAttribute("nowPage", nowpage);
		request.setAttribute("nowPageEncode", Func.urlEncode(nowpage));
		
		String path_info =nowpage.split("[?]")[0];
		request.setAttribute("path_info", path_info);
		
		return nowpage;
	}
	
	/**
	 * @title : 전에 페이지 체크
	 * @method : getRefererChk(String header)
	 */
	public static String getRefererChk(String header) {
		try {
			String cururl = header.toString(); 
			String fileName = cururl.substring(cururl.lastIndexOf("/") + 1, cururl.length());
			String lastFileName = "";
			
			int i = fileName.lastIndexOf('?');
			
			if(i > 0 ){
				lastFileName = fileName.substring(0, fileName.lastIndexOf('?'));
			}else{
				lastFileName = fileName;
			}
			return lastFileName;
		}catch(Exception e) {
			return "";
		}
	}

	/**
	 * @title : 파일삭제
	 * @method : fileDelete(String)
	 */
	public static void fileDelete(String path) {
		File file = new File(path);
		if( file.exists() ){
			if(file.delete()){
					System.out.println("파일삭제 성공");
			}else{
				System.out.println("파일삭제 실패");
			}
		}else{
			System.out.println("파일이 존재하지 않습니다.");
		}
	}
	
	/**
	 * @title : 폴더체크후 생성
	 * @method : FolderCreate(String)
	 */
	public static void folderCreate(String strDir) {
		File file = new File(strDir);
		if (!file.exists()) {
			boolean c = file.mkdirs();
		}
	}
	
	/**
	 * @title : 폴더삭제(하위폴더/파일포함)
	 * @method : folderDelete(String)
	 */
	public static void folderDelete(String strDir) {
		try {
			File file = new File(strDir);
			//폴더내 파일을 배열로 가져온다.
			File[] tempFile = file.listFiles();
	
			if(tempFile.length >0){
				for (int i = 0; i < tempFile.length; i++) {
					if(tempFile[i].isFile()){
						tempFile[i].delete();
					}else{
						//재귀함수
						folderDelete(tempFile[i].getPath());
					}
					tempFile[i].delete();
				}
				file.delete();
			}
			//System.out.println("strDir="+strDir);
		}catch(Exception e) {
			System.out.println("Exception : "+e.getMessage());
		}
	}

	/**
	 * @title : 페이지 url 체크
	 * @method : urlChk(Model model)
	 */
	public static String urlChk(Model model, String allowUrl) throws Exception{
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpServletResponse response = (HttpServletResponse) map.get("response");
		//
		String[] allowUrl_arr	= allowUrl.split(",");
		//현재페이지 정보값
		String referer_fileName = Func.getRefererChk(request.getHeader("referer"));
		System.out.println("referer_fileName="+referer_fileName);
		//
		String urlResult = "N";
		for(int z=0;z <= allowUrl_arr.length-1;z++){
			if(referer_fileName.trim().equals(allowUrl_arr[z].trim())){//값은값이 있으면 Y
				urlResult = "Y";
			}
		}
		return urlResult;
	}
	
	/**
	 * @title : fill문자를 len의 길이만큼 앞에 붙여 돌려줌
	 * @method : zerofill(int num, int len, String fill)
	 */
	public static String zerofill(int num, int len, String fill) {
		return zerofill(String.valueOf(num), len, fill);
	}
	
	/**
	 * @title : fill문자를 len의 길이만큼 앞에 붙여 돌려줌
	 * @method : zerofill(String str, int len, String fill)
	 */
	public static String zerofill(String str, int len, String fill) {
		if(str == null) str = "";
		int strLen = str.length();
		StringBuffer tmp = new StringBuffer();
		for( int LoopI=0; LoopI<len-strLen; LoopI++ ) {
			tmp.append(fill);
		}
		tmp.append(str);
		return tmp.toString();
	}
	
	/**
	 * @title : 데이터가 비어있는지 유무를 판단하여 돌려줌
	 * @method : isEmpty(String)
	 */
	public static boolean isEmpty(String str) {
		if( str == null ) return true;
		return str.trim().length() > 0 ? false : true;
	}
	
	/**
	 * @title : 데이터가 비어있는지 유무를 판단하여 돌려줌
	 * - isZeroCheck ( true : 0일때 return true )
	 * @method : isEmpty(Integer, boolean)
	 */
	public static boolean isEmpty(Integer num, boolean isZeroCheck) {
		if( isZeroCheck ) {
			if( num == null ) {
				return true;
			} else {
				return num == 0 ? true : isEmpty(num);
			}
		} else {
			return isEmpty(num);
		}
	}
	
	/**
	 * @title : 데이터가 비어있는지 유무를 판단하여 돌려줌
	 * @method : isEmpty(Integer)
	 */
	public static boolean isEmpty(Integer num) {
		return isEmpty(String.valueOf(num));
	}
	
	/**
	 * @title : 난수 생성
	 * @method : get_idx_add()
	 */
	public static String get_idx_add() {
		
		int jw1					= 0;
		int jw2					= 0;
		int jw3					= 0;
		int jw4					= 0;
		String jw01				= "";
		String jw02				= "";
		String jw03				= "";
		String jw04				= "";

		SimpleDateFormat formatter	= new SimpleDateFormat("yyyyMMddHHmmss");
		String TODAY							= formatter.format(new java.util.Date());	//현재일시(yyyyMMddHHmmss)
		int HOUR								= Integer.parseInt( TODAY.substring(8,14).substring(0,2) );		//현재 시간(0시~24시까지 표현)
		int MINUTE								= Integer.parseInt( TODAY.substring(8,14).substring(2,4) );	//현재 분
		int SECOND								= Integer.parseInt( TODAY.substring(8,14).substring(4,6) );	//현재 초

		Random rand = new Random();
		
		//10 ~ 99 사이의 랜덤 수를 생성
		int start = 10;
		int end = 99;
		double range = end - start + 1;
		
		jw1		= (int)(rand.nextDouble() * range + start);
		jw01	= Integer.toString(jw1);
		jw2		= (int)(rand.nextDouble() * range + start);
		jw02		= Integer.toString(jw2);
		jw3		= (int)(rand.nextDouble() * range + start);
		jw03	= Integer.toString(jw3);
		jw4		= (int)(rand.nextDouble() * range + start);
		jw04= Integer.toString(jw4);

		return cStr( jw01+jw02+jw03+jw04 );
	}
	
	/**
	 * @title : 랜덤숫자 만들기
	 * @method : randomNumber(int)
	 */
	public static int randomNumber(int length) {
		String numStr = "1";
		String plusNumStr = "1";
		
		for (int i = 0; i < length; i++) {
			numStr += "0";
			if (i != length - 1) {
				plusNumStr += "0";
			}
		}
		Random random = new Random();
		int result = random.nextInt(Integer.parseInt(numStr)) + Integer.parseInt(plusNumStr);

		if (result > Integer.parseInt(numStr)) {
			result = result - Integer.parseInt(plusNumStr);
		}
		return result;
	}
	
	/**
	 * @title : 비밀번호 랜덤 문자열 가져오기
	 * @method : randomPasswd()
	 */
	public static String randomPasswd() {
		String  pswd = "";
		StringBuffer sb = new StringBuffer();
		StringBuffer sc = new StringBuffer("!@#$%&*");  // 특수문자 모음, {}[] 같은 비호감문자는 뺌
	
		// 소문자 4개를 임의발생
		for( int i = 0; i<6; i++) {
			sb.append((char)((Math.random() * 26)+97)); // 아스키번호 97(a) 부터 26글자 중에서 택일
		}
		
		// 숫자 2개를 임의 발생
		for( int i = 0; i<4; i++) {
			sb.append((char)((Math.random() * 10)+48)); //아스키번호 48(1) 부터 10글자 중에서 택일
		}
		
		// 특수문자를 두개  발생시켜 랜덤하게 중간에 끼워 넣는다 
		sb.setCharAt(((int)(Math.random()*3)+1), sc.charAt((int)(Math.random()*sc.length()-1))); //대문자3개중 하나   
		sb.setCharAt(((int)(Math.random()*4)+4), sc.charAt((int)(Math.random()*sc.length()-1))); //소문자4개중 하나

		pswd = sb.toString();
		return pswd;
	}
	
	/**
	 * @title : 두날짜의 차이 구하기
	 * @method : doDiffOfDate(String, String)
	 */
	public static long doDiffOfDate(String start, String end){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date beginDate = formatter.parse(start);
			java.util.Date endDate = formatter.parse(end);
			 
			// 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
			long diff = endDate.getTime() - beginDate.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);

			return diffDays;
			 
		} catch (ParseException e) {
			return 0;
		}
	}
	
	/**
	 * @title : 두날짜의 차이 구하기(시간포함)
	 * @method : doDiffOfTime(String, String)
	 */
	public static long doDiffOfTime(String start, String end){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date beginDate = formatter.parse(start);
			java.util.Date endDate = formatter.parse(end);
			 
			// 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
			long diff = endDate.getTime() - beginDate.getTime();
			return diff;
			 
		} catch (ParseException e) {
			return 0;
		}
	}	
	
	/**
	 * @title : 남은일 계산하기
	 * @method : doDiffOfDate(String, String, String)
	 */
	public static long diffOfDate(String begin, String end, String type) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat(type);
		
		Date beginDate = formatter.parse(begin);
		Date endDate = formatter.parse(end);
		
		long diff = endDate.getTime() - beginDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		return diffDays;
	}
	
	/**
	 * @title : 금지파일 체크
	 * @method : fileChk(String, String)
	 */
	public static boolean fileChk(String filename, String ext){
		boolean extchk = false;
		String fileExt = filename.substring(filename.lastIndexOf(".")+1, filename.length());
		String[] ext_arr = ext.split(",");	
		String noext = "asp,aspx,asa,vbs,php,jsp,jar,java,class,js,dll,exe,com,bat";
		String[] noext_arr = noext.split(",");
		for(int i=0; i <= ext_arr.length-1;i++){

			if(ext_arr[i].toLowerCase().equals(fileExt.toLowerCase())){
				extchk = true;
			}
		}
		return extchk;
	}
	
	/**
	 * @title : 인코딩
	 * @method : urlEncode(String)
	 */
	public static String urlEncode(String strUrl){
		try{
			String strEncode = java.net.URLEncoder.encode(strUrl,"UTF-8");
			return strEncode;
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * @title : 실제IP 추출
	 * @method : remoteIp(HttpServletRequest)
	 */
	public static String remoteIp(HttpServletRequest request){
		String ip = null;
		ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");   
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");   
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip == null){ip = "";}
		return ip;
	}
	
	/**
	 * @title : 소수점자르기
	 * @method : formatDecimal2(double, int)
	 */
	public static float formatDecimal2(double test, int count ) throws Exception {
		String pattern = "#.";	
		for(int i=0; i<count;i++){
			pattern += "0";
		}
		if(count == 0){pattern = "#";}
		DecimalFormat dformat = new DecimalFormat(pattern);
		float ss = Double.valueOf(dformat.format(test)).floatValue();
		
		return ss;
	}
	
	/**
	 * @title : 마지막날구하기
	 * @method : lastDay(int, int, int)
	 */
	public static int lastDay(int yy, int mm, int dd) throws Exception {
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(yy, mm-1, dd); //월은 -1해줘야 해당월로 인식
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			return lastDay;
		}catch(Exception e) {
			return 31;
		}
	}
	
	/**
	 * @title : 비율 구하기
	 * @method : getPer(int, int)
	 */
	public static String getPer(int totVal, int val ) throws Exception {
		DecimalFormat format = new DecimalFormat("0.00");
	
		float f_totVal= totVal;
		float f_val = val;
	
		String per = "0";
		if (f_totVal!=0 && f_val!=0){
			per = format.format((f_val/f_totVal)*100);
		}
		return per;
	}
	
	/**
	 * @title : 주일
	 * @method : SetWeek(int)
	 */
	public static String SetWeek(int theNum){
		String week = null;
		switch(theNum) {
			case 0 :	week = "SUN"; break;
			case 1 :	week = "MON"; break;
			case 2 :	week = "TUE"; break;
			case 3 :	week = "WED"; break;
			case 4 :	week = "THU"; break;
			case 5 :	week = "FRI"; break;
			case 6 :	week = "SAT"; break;
		}
		return week;
	}
	
	/**
	 * @title : os명
	 * @method : SetOS(String)
	 */
	public static String SetOS(String strvalue) {
		String vOS;
		if(strvalue.equals("")){
			vOS = "unKnown";
		}else if(strvalue.equals("Windows NT 5.0") || strvalue.equals("Windows NT 5.0)")){
			vOS = "Windows 2000";
		}else if(strvalue.equals("Windows NT 5.1") || strvalue.equals("Windows NT 5.1)")){
			vOS = "Windows XP";
		}else{
			vOS = strvalue;
		}
		return vOS;
	}
	
	/**
	 * @title : 브라우저명
	 * @method : SetBrowser(String)
	 */
	public static String SetBrowser(String strvalue){
		String vBrowser;
		if(strvalue.trim() == ""){
			vBrowser = "unKnown";
		}else{
			vBrowser = strvalue;
		}
		return vBrowser;
	}
	
	public static String check_browser(HttpServletRequest request) {
		String browser = "";
		String header = request.getHeader("User-Agent");
		// 신규추가된 indexof : Trident(IE11) 일반 MSIE로는 체크 안됨
		if (header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1) {
			browser = "ie";
		}
		// 크롬일 경우
		else if (header.indexOf("Chrome") > -1) {
			browser = "chrome";
		}
		// 오페라일경우
		else if (header.indexOf("Opera") > -1) {
			browser = "opera";
		}
		// 사파리일 경우
		else if (header.indexOf("Apple") > -1) {
			browser = "sarari";
		} else {
			browser = "firfox";
		}
		return browser;
	}
	

	public static String cutOff(String p_str, int p_len) {
		if (p_str!=null && !"".equals(p_str)) {
			boolean chkFlag = false;
			String strName = p_str.trim();
			byte[] arName = strName.getBytes();
			String p_tailstr = "...";
			if (arName.length > p_len) {
				for (int idx = 0; idx < p_len; idx++) {
					if (arName[idx] < 0) {
						chkFlag = !chkFlag;
					} else {
						chkFlag = false;
					}
				}
				if (chkFlag) {
					strName = new String(arName, 0, p_len + 1) + p_tailstr;
				} else {
					strName = new String(arName, 0, p_len) + p_tailstr;
				}
			} else {
				strName = new String(arName, 0, arName.length);
			}
			return strName;
		}
		return p_str;
	}
	
	/**
	 * @title : 태그제거
	 * @method : StringToHTML
	 */
	public static String StringToHTML (String str, int cnt, boolean tmp){
		return cutOff(str, cnt);
	}

	/**
	 * @title : 날짜계산
	 * @method : dateSerial
	 */
	public static String dateSerial(int year, int month, int day){
		Calendar cal2 = Calendar.getInstance();
		String strYear;
		String strMonth;
		String strDay;

		month = month - 1;

		cal2.set(Calendar.YEAR, year);		//년도
		cal2.set(Calendar.MONTH, month);	//월
		cal2.set(Calendar.DATE, day);		//일

		strYear = Integer.toString(cal2.get(Calendar.YEAR));
		strMonth = Integer.toString(cal2.get(Calendar.MONTH)+1);
		strDay = Integer.toString(cal2.get(Calendar.DATE));

		if(strMonth == "0"){strMonth = "12";}
		if(strMonth.length() == 1){strMonth = "0"+strMonth;}
		if(strDay.length() == 1){strDay = "0"+strDay;}

		return strYear+"-"+strMonth+"-"+strDay;
	}
	
	/**
	 * @title : new 아이콘 출력
	 * @method : get_newimg
	 */
	public static boolean get_newimg(String date, int a_new){
		Calendar CAL	= Calendar.getInstance();
		int YEAR		= CAL.get(Calendar.YEAR);						//현재연도
		int MONTH		= CAL.get(Calendar.MONTH)+1;					//현재월
		int DAY			= CAL.get(Calendar.DATE);						//현재일
		
		int year	= YEAR;
		int month	= MONTH;
		int day		= DAY;

		boolean newIcon = false;
		date = nvl(date);
		if(!"".equals(date)){
			String aa_regdate	= date.substring(0,10);
			String Yesterday	= dateSerial(year, month, day - a_new);
			
	
			int aa_regdate2		= Integer.parseInt(aa_regdate.replaceAll("-", ""));
			int Yesterda2		= Integer.parseInt(Yesterday.replaceAll("-", ""));
			
			if (aa_regdate2 >= Yesterda2) {
				newIcon = true;
			}
		}
		return newIcon;
	}
	
	/**
	 * @title : 태그자르기
	 * @method : strCut
	 */
	public static String strCut(String szText, String szKey, int nLength, int nPrev, boolean isNotag, boolean isAdddot) { // 문자열
		//자르기
		String r_val = nvl(szText);
		int oF = 0, oL = 0, rF = 0, rL = 0;
		int nLengthPrev = 0;
		Pattern p = Pattern.compile("<(/?)([^<>]*)?>", Pattern.CASE_INSENSITIVE); // 태그제거 패턴

		if (isNotag) {
			r_val = p.matcher(r_val).replaceAll("");
		} // 태그 제거
		r_val = r_val.replaceAll("&amp;", "&");
		r_val = r_val.replaceAll("(!/|\r|\n|&nbsp;)", ""); // 공백제거

		try {
			byte[] bytes = r_val.getBytes("UTF-8"); // 바이트로 보관
			if (szKey != null && !szKey.equals("")) {
				nLengthPrev = (r_val.indexOf(szKey) == -1) ? 0 : r_val.indexOf(szKey); // 일단 위치찾고
				nLengthPrev = r_val.substring(0, nLengthPrev).getBytes("MS949").length; // 위치까지길이를 byte로 다시 구한다
				nLengthPrev = (nLengthPrev - nPrev >= 0) ? nLengthPrev - nPrev : 0; // 좀 앞부분부터 가져오도록한다.
			}

			// x부터 y길이만큼 잘라낸다. 한글안깨지게.
			int j = 0;
			if (nLengthPrev > 0)
				while (j < bytes.length) {
					if ((bytes[j] & 0x80) != 0) {
						oF += 2;
						rF += 3;
						if (oF + 2 > nLengthPrev) {
							break;
						}
						j += 3;
					} else {
						if (oF + 1 > nLengthPrev) {
							break;
						}
						++oF;
						++rF;
						++j;
					}
				}
			j = rF;
			while (j < bytes.length) {
				if ((bytes[j] & 0x80) != 0) {
					if (oL + 2 > nLength) {
						break;
					}
					oL += 2;
					rL += 3;
					j += 3;
				} else {
					if (oL + 1 > nLength) {
						break;
					}
					++oL;
					++rL;
					++j;
				}
			}
			r_val = new String(bytes, rF, rL, "UTF-8"); // charset 옵션
			if (isAdddot && rF + rL + 3 <= bytes.length) {
				r_val += "...";
			} // ...을 붙일지말지 옵션
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return r_val;
	}

	/**
	 * @title : 쿠키가져오기
	 * @method : getCookie
	 */
	public static String getCookie(HttpServletRequest request, String cookieName) throws Exception {
		Cookie[] COOKIES = request.getCookies();
		String cookieValue = "";
		try{
			for(int i = 0; i < COOKIES.length;i++){
				Cookie thisCookie = COOKIES[i];
				if(cookieName.equals(thisCookie.getName())){
					cookieValue = thisCookie.getValue();
					break;
				}
			}
			return  java.net.URLDecoder.decode( cookieValue ) ;
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * @title : 쿠키저장
	 * @method : setCookie
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue) throws Exception {
		setCookie(request, response, cookieName, cookieValue, -1);
	}
	
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int maxage) throws Exception {
		String domain = (String) request.getServerName().toString();
		//
		Cookie cookie = new Cookie(cookieName, java.net.URLEncoder.encode( cookieValue  ) );
		//cookie.setDomain(domain);
		cookie.setPath("/");
		cookie.setMaxAge(maxage);
		response.addCookie(cookie);
	}
	
	/**
	 * @title : 소수점자르기
	 * @method : formatDecimal
	 */
	public static String formatDecimal(double test, int count ) throws Exception {
		String pattern = "#.";	
		for(int i=0; i<count;i++){
			pattern += "0";
		}
		if(count == 0){pattern = "#";}
		DecimalFormat dformat = new DecimalFormat(pattern);
		
		String ss = dformat.format(test);
		return ss;
	}
	
	/**
	 * @title : 배열을 문자열로 변환
	 * @method : formatDecimal
	 */
	public static String implode(String txt, String[] arr){
		String ret_value = "";
		for(int i=0;i<=arr.length-1;i++){
			if(i > 0){ret_value += txt;}
			ret_value += arr[i];
		}
		return ret_value;
	}
	
	/**
	 * @title : 세션생성
	 * @method : setSession
	 */
	public static void setSession(HttpServletRequest request, String sessionName, String sessionValue){
		HttpSession session = request.getSession();
		session.setAttribute(sessionName, sessionValue);
	}
	
	/**
	 * @title : 세션조회
	 * @method : setSession
	 */
	public static String getSession(HttpServletRequest request, String sessionName){
		try {
			HttpSession session = request.getSession(false);
			if(session != null){
				return session.getAttribute(sessionName) != null ? (String)session.getAttribute(sessionName) : "";
			}else{
				return "";
			}
		}catch(Exception e) {
			return "";
		}
	}
	
	/**
	 * @title : 파일크기계산
	 * @method : byteConvert
	 */
	public static String byteConvert(String bytes) throws Exception {
		String[] s = {"Byte", "KB", "MB", "GB", "TB", "PB"};
		String e = cStr( Math.floor(Math.log( Func.cDbl(bytes) ) / Math.log( 1024 )) );
		e = e.replaceAll(".0", "");
		double ret_temp = Func.cDbl(bytes) / Math.pow(1024, Math.floor(cInt(e)));
		String ret_temp2 = formatDecimal(ret_temp, 1);
		return cStr(ret_temp2) + s[cInt(e)];
	}
	
	/**
	 * @title : 디렉토리목록 가져오기
	 * @method : openDir
	 */
	public static String[] openDir(String filePath) throws Exception{
		String[] dir = null;

		try{
			File path = new File( filePath );
			if( path.exists() == true ){

				File[] fileList = path.listFiles();

				int count = 0;
				for( int i = 0 ; i < fileList.length ; i++ ){
					if( fileList[i].isDirectory() ){	//디렉토리
						count++;
					}
				}
				dir = new String[count];

				count = 0;
				for( int i = 0 ; i < fileList.length ; i++ ){
					if( fileList[i].isDirectory() ){	//디렉토리
						dir[count] = fileList[i].getName();
						count++;
					}
				}
			}
		}catch(Exception e){
		}finally{
			return dir;
		}
	}
	
	/**
	 * @title : 파일내용 읽기
	 * @method : fileGetContents
	 */
	public static String fileGetContents( String filePath ) throws Exception{
		
		String ret_value = null;

		try{
			FileReader fr = new FileReader(filePath); //파일읽기객체생성
			BufferedReader br = new BufferedReader(fr); //버퍼리더객체생성
			String line = null;

			ret_value = "";
			int i = 0;
			while((line=br.readLine())!=null){ //라인단위 읽기
				if(i > 0){ret_value += "\n";}
				ret_value += line;
				i++;
			}
		}catch(IOException e){
		}finally{
			return ret_value;
		}
	}

	/**
	 * @title : 파일이미지
	 * @method : get_FileName
	 */
	public static String get_FileName(String filename) throws Exception{

		String file_str = null;
		filename = filename.toLowerCase();
		String[] filename_arr = filename.split("\\.");
		filename = filename_arr[filename_arr.length-1].trim();

		if(filename != null){
		
			if(filename.equals("")){ 		file_str = "icon_no.png"; }
			else if(filename.equals("avi")){ 	file_str = "icon_avi.png"; }
			else if(filename.equals("doc")){ 	file_str = "icon_doc.png"; }
			else if(filename.equals("docx")){ 	file_str = "icon_doc.png"; }
			else if(filename.equals("gif")){	 	file_str = "icon_gif.png"; }
			else if(filename.equals("hwp")){ 	file_str = "icon_hwp.png"; }
			else if(filename.equals("jpg")){ 	file_str = "icon_jpg.png"; }
			else if(filename.equals("pdf")){ 	file_str = "icon_pdf.png"; }
			else if(filename.equals("ppt")){ 	file_str = "icon_ppt.png"; }
			else if(filename.equals("pptx")){ 	file_str = "icon_ppt.png"; }
			else if(filename.equals("txt")){		file_str = "icon_txt.png"; }
			else if(filename.equals("wmv")){ 	file_str = "icon_wmv.png"; }
			else if(filename.equals("xls")){	 	file_str = "icon_xls.png"; }
			else if(filename.equals("xlsx")){ 	file_str = "icon_xls.png"; }
			else if(filename.equals("zip")){ 	file_str = "icon_zip.png"; }
			else{ file_str = "icon_no.png"; }
			
			return file_str;

		}else{

			return "icon_no.png";
			
		}
	}
	
	/**
	 * @title : 가변변수 가져오기
	 * @method : getValue
	 */
	public static String getValue(Object obj, String name){
		try{
			Field field = ReflectionUtils.findField(obj.getClass(), name);
			field.setAccessible(true);
			String value = "";
			value = (String) field.get(obj);
			return value;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	
	/**
	 * @title : 가변변수 세팅하기
	 * @method : setValue
	 */
	public static void setValue(Object obj, String name, String value){
		try{
			Field field = ReflectionUtils.findField(obj.getClass(), name);
			field.setAccessible(true);
			ReflectionUtils.setField(field, obj, value);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * @title : 그래프 그리기
	 * @method : drawGraph
	 */
	public static String drawGraph(long val1, long val2, int depth) throws Exception{
		DecimalFormat format = new DecimalFormat("0.00");

		float fval1 = (new Long(val1)).floatValue();
		float fval2 = (new Long(val2)).floatValue();

		//float percent = (fval1/fval2)*100;

		String str_percent = "0";
		if (val1>0){
			str_percent = format.format((fval1/fval2)*100);
		}

		String color = "#b09ac0";
		if (depth==1){
			color = "#72C0EF";
		}else if (depth==2){
			color = "#69ba74";	
		}else if (depth==3){
			color = "#bfbfbf";
		}else if (depth==4){
			color = "#f1d033";
		}

		String graph = "";
		graph += "<div style=\"height:20px; width:88%; position:relative;border:0px solid #EFEFEF; float:left;\">";
		graph += "     <div class=\"jsGraph\" data-percent=\""+str_percent+"\" style=\"height:100%; width:0; position:absolute;left:0; background-color:"+color+";\"></div>";
		graph += "</div>";
		graph += "<div class=\"eng\" style=\"float:right; padding-right:12px;\">"+str_percent+"%</div>";
		return graph;
	}
	
	//입력값 체크함수
	public static String valueChk( String value, String val_name){
		String ret_value = "";
		if(value.trim().equals("")){

			ret_value = val_name+"을(를) 입력하세요.\\n";

		}
		return ret_value;
	}

	//입력값 체크함수
	public static String valueChk( String value, String val_name, String lang){
		String ret_value = "";
		if("".equals(lang)) lang = "eng";
		if(value.trim().equals("")){

			ret_value = "Please enter " +val_name+"\\n";

		}
		return ret_value;
	}
	
	/**
	 * @title : 홈페이지 작업로그
	 * @method : writeWorkLog
	 */
	public static void writeWorkLog (String hl_job, String hl_type, HttpServletRequest request) throws Exception{
		HomepageLogWorkDTO log = new HomepageLogWorkDTO();
		log.setHl_type(hl_type);
		log.setHl_job(hl_job);
		log.setHl_id(Func.getSession(request,"ss_m_id"));
		log.setHl_name(Func.getSession(request,"ss_m_name"));
		log.setHl_ci(Func.getSession(request,"ss_m_jumin"));
		log.setHl_ip(Func.remoteIp(request));
		log.setHl_ymd(Func.date("Ymd"));
		log.setHl_regdate(Func.date("Y-m-d H:i:s"));
		
		//사이트정보
		String builder_dir = (String)request.getAttribute("BUILDER_DIR");
		if(builder_dir == null){ builder_dir = "ncms"; }
		log.setSite_dir(builder_dir);
		
		log.setExTableName("homepage_log_work");
		exdao.insert(log);
	}
	
	 /**
	  * 숫자에 천단위마다 콤마 넣기
	  * @param int
	  * @return String
	  * */
	 public static String comma(long num) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}


	//관리자 작업
	public static void writeManagerLog (String hl_job, HttpServletRequest request) throws Exception{
		writeWorkLog (hl_job, "1", request);
	}

	//게시판 작업
	public static void writeBoardLog (String hl_job, HttpServletRequest request) throws Exception{
		writeWorkLog (hl_job, "2", request);
	}

	//회원작업
	public static void writeMemberLog (String hl_job, HttpServletRequest request) throws Exception{
		writeWorkLog (hl_job, "3", request);
	}

	//관리자 작업
	public static void writeManagerLoginLog (String hl_job, HttpServletRequest request) throws Exception{
		writeWorkLog (hl_job, "4", request);
	}
	
	public static void alert(JspWriter OUT, String msg) throws Exception{
		String move = null;
		move = "<script language='javascript'>";
		move = move+"alert('"+msg+"');";
		move = move+"</script>";
		OUT.print(move);
		return;
	}
	
	
	public static void Back_back(JspWriter OUT, String msg) throws Exception{
		String move = null;
		move = "<script language='javascript'>";
		move = move+"alert('"+msg+"');";
		move = move+"history.go(-1);";
		move = move+"</script>";
		OUT.print(move);
		return;
	}
	
	public static void Back_URL(JspWriter OUT, String msg, String url) throws Exception{
		String move = null;
		move = "<script language='javascript'>";
		move = move+"alert('"+msg+"');";
		move = move+"location.href='"+url+"';";
		move = move+"</script>";
		OUT.print(move);
		return;
	}
	
	public static void goUrl(JspWriter OUT, String url) throws Exception{
		String move = null;
		move = "<script language='javascript'>";
		move = move+"location.href='"+url+"';";
		move = move+"</script>";
		OUT.print(move);
		return;
	}

	/**
	 * @title : map에 데이타 체크후 null일경우 만 입력
	 * @method : mapCheckPut(HashMap<String,String>, String value)
	 */
	public static void mapCheckPut(HashMap<String,String>map, String key, String value){
		if(map.get(key) == null){
			map.put(key, value);
		}
	}
	
	/**
	 * @title : 본인인증정보 맵생성
	 * @method : createCertMap(HttpServletRequest)
	 */
	public static HashMap<String, String> createCertMap(HttpServletRequest request ){
		HashMap<String, String> certMap = new HashMap<String, String>();
		certMap.put("name", Func.getSession(request, "ss_m_name") );
		certMap.put("ci", Func.getSession(request, "ss_m_coinfo") );
		certMap.put("di", Func.getSession(request, "ss_m_dupinfo") );
		certMap.put("sex", Func.getSession(request, "ss_m_sex") );
		certMap.put("age", Func.getSession(request, "ss_m_age") );
		certMap.put("birth", Func.getSession(request, "ss_m_birth") );
		return certMap;
	}
	
	public static String url_encoding(java.util.Hashtable hash) {
		if ( hash == null ) throw new IllegalArgumentException("argument is null");
		java.util.Enumeration enum1 = hash.keys();
		StringBuffer buf = new StringBuffer();
		boolean isFirst = true;
		while(enum1.hasMoreElements()){
			if (isFirst) isFirst = false;
			else buf.append('&');
			String key = (String)enum1.nextElement();
			String value = (String)hash.get(key);
			buf.append(key);
			buf.append('=');
			buf.append(value);
		}
		return buf.toString();
	}
	
	public static void sendSms(String smsmsg, String smstel) throws Exception{

		String strJsFile = "";
		try{
			
			String fileUrl = "http://sms.nninc.co.kr/hostsms_new_board_utf.asp";
			URL url = null;
			URLConnection httpConn = null;
			BufferedReader in = null;
			PrintWriter outS = null;
			url = new URL(fileUrl);
			httpConn = url.openConnection();
			httpConn.setConnectTimeout(60000);
			httpConn.setReadTimeout(60000);
			httpConn.setDoOutput(true);
			httpConn.setUseCaches(false);
			outS = new PrintWriter (httpConn.getOutputStream());
			java.util.Hashtable data = new java.util.Hashtable();
			data.put("sms_m_id", "childtu");
			data.put("sms_msg", urlEncode(smsmsg));
			data.put("sms_tran_callback", "070-4418-5953");
			data.put("sms_tran_phone", smstel);
			outS.print(url_encoding(data));
			outS.flush();

			InputStream is = httpConn.getInputStream();
			in = new BufferedReader(new InputStreamReader(is),8*1024);
			String line = null;
			int readLineCnt = 0;
			while( (line=in.readLine())!= null ) {
			if(readLineCnt > 0) strJsFile += "\n";
				strJsFile += line;
			}
			outS.close();
			in.close();

			

		}catch(Exception e){
			//OUT.print("ERROR!!!!!!");
			e.printStackTrace();
		}
		//OUT.print(strJsFile);

	}
	
	public static String getSms() throws Exception{

		String strJsFile = "";
		try{
			
			String fileUrl = "http://sms.nninc.co.kr/ifr_restnum.asp";
			URL url = null;
			URLConnection httpConn = null;
			BufferedReader in = null;
			PrintWriter outS = null;
			url = new URL(fileUrl);
			httpConn = url.openConnection();
			httpConn.setConnectTimeout(60000);
			httpConn.setReadTimeout(60000);
			httpConn.setDoOutput(true);
			httpConn.setUseCaches(false);
			outS = new PrintWriter (httpConn.getOutputStream());
			java.util.Hashtable data = new java.util.Hashtable();
			data.put("sms_m_id", "childtu");
			outS.print(url_encoding(data));
			outS.flush();

			InputStream is = httpConn.getInputStream();
			in = new BufferedReader(new InputStreamReader(is),8*1024);
			String line = null;
			int readLineCnt = 0;
			while( (line=in.readLine())!= null ) {
			if(readLineCnt > 0) strJsFile += "\n";
				strJsFile += line;
			}
			outS.close();
			in.close();

		}catch(Exception e){
			//OUT.print("ERROR!!!!!!");
			e.printStackTrace();
		}
		//OUT.print(strJsFile);
		return strJsFile;
	}

}
