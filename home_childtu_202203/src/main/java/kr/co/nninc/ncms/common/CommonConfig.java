package kr.co.nninc.ncms.common;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component("CommonConfig")
public class CommonConfig {
	/**
	 * config.properties 파일명을 저장하는 상수 필드
	 */
	public static String CONFIG_FILE = "kr/co/nninc/spring/nninc-config.properties";
	/**
	 * board.properties 파일명을 저장하는 상수 필드
	 */
	public static String BOARD_FILE = "kr/co/nninc/spring/board-config.properties";
	/**
	 * config.proeprties의 내용을 저장하는 프로퍼티 필드
	 */
	private static LinkedProperties config;
	/**
	 * board.proeprties의 내용을 저장하는 프로퍼티 필드
	 */
	private static LinkedProperties board;

	/**
	 * CommonConfig 클래스의 기본생성자.
	 * 중요 : private 접근자이므로 생성자를 생성할 수 없다.
	 */
	private CommonConfig() {
	}

	/**
	 * properties 폴더에 있는 config.properties파일의 내용을 로딩한다.
	 * @return void
	 * @throws Exception
	 */
	private static void loadConfig() throws Exception {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream in = null;
			if (loader != null) in = loader.getResourceAsStream(CONFIG_FILE);
			if (in == null) in = ClassLoader.getSystemResourceAsStream(CONFIG_FILE);
			LinkedProperties props = new LinkedProperties();
			props.load(in);
			in.close();
			config = props;
		} catch (Exception e) {
			throw new Exception("Can't load config file"+e.getMessage());
		}
	}

	/**
	 * properties 폴더에 있는 config.properties파일의 내용을 로딩한다.
	 * @return void
	 * @throws Exception
	 */
	private static void loadConfig2() throws Exception {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream in = null;
			if (loader != null) in = loader.getResourceAsStream(BOARD_FILE);
			if (in == null) in = ClassLoader.getSystemResourceAsStream(BOARD_FILE);
			LinkedProperties props = new LinkedProperties();
			props.load(in);
			in.close();
			board = props;
		} catch (Exception e) {
			throw new Exception("Can't load config file"+e.getMessage());
		}
	}

	/**
	 * config.properties 파일을 로딩하여 Properties 객체를 생성하여 리턴한다.
	 * @return Properties config.properties 파일을 로딩한 Properties 객체
	 * @throws Exception
	 */
	public static Properties getConfig() throws Exception {
		if (config == null) {
			loadConfig();
		}
		return config;
	}

	/**
	 * config.properties의 메타데이타를 지정된 키로 config 프로퍼티 필드에서
	 * 찾아 리턴한다. 이 메소드를 최초 호출시 config.properties 파일을 로딩하여 config
	 * 프로퍼티 필드에 저장한다.
	 *
	 * @param key String 프로퍼티의 키
	 * @return String 프로퍼티 키의 값
	 * @throws Exception
	 */
	public static String get(String key) throws Exception {
		if (config == null) {
			loadConfig();
		}
		if (key == null) {
			throw new Exception("Config Exception : key can not be null");
		}
		String value = config.getProperty(key);
		if (value != null) {
			value.trim();
		}
		return value;
	}

	/**
	 * config.properties의 메타데이타를 지정된 키로 config 프로퍼티 필드에서
	 * 찾아 리턴한다. 이 메소드를 최초 호출시 config.properties 파일을 로딩하여 config
	 * 프로퍼티 필드에 저장한다.
	 *
	 * @param key String 프로퍼티의 키
	 * @return String 프로퍼티 키의 값
	 * @throws Exception
	 */
	public static String getBoard(String key) throws Exception {
		if (board == null) {
			loadConfig2();
		}
		if (key == null) {
			throw new Exception("Config Exception : key can not be null");
		}
		String value = board.getProperty(key);
		if (value != null) {
			value.trim();
		}
		return value;
	}
	
	public static LinkedHashMap<String, String> getBoardMap(String search_name) throws Exception {
		if (board == null) {
			loadConfig2();
		}
		if (search_name == null) {
			throw new Exception("Config Exception : key can not be null");
		}
		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
		for (Map.Entry e : (Set<Map.Entry>) board.entrySet()) {
			String key = (String) e.getKey();
			String val = (String) e.getValue();
			if (key.indexOf(search_name) > -1) {
				// 정규식 괄호안의 문자열 찾기
				Pattern p = Pattern.compile("\\[(.*?)\\]");
				Matcher m = p.matcher(key);
				if (m.find()) {
					hm.put(m.group(1), val);
				}
			}
		}
		return hm;
	}

	public static void main(String[] args) throws Exception {
		if (board == null) {
			loadConfig2();
		}
		//정렬때문에 HashMap 대신에 LinkedHashMap 씀
		LinkedHashMap<String, String> hm = getBoardMap("service_request.time_config");
		for (String key: hm.keySet()) {
			System.out.println(key+"="+hm.get(key));
		}
	}

	/**
	 * config.properties에 값을 저장한다.
	 *
	 * @param key String 프로퍼티의 키
	 * @param String 프로퍼티 키의 값
	 * @return void
	 * @throws Exception
	 */
	public static void set(String key, String value) throws Exception {
		if (config == null) {
			loadConfig();
		}
		if (key == null || value == null) {
			throw new Exception("Config Exception : key and value can not be null");
		}
		config.setProperty(key, value);
	}
}
