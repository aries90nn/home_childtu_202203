package kr.co.nninc.ncms.common.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.service.ClobSelectDTO;

/**
 * 범용 DAO 클래스
 * @author 나눔
 * @since 2018.03.29
 * @version 1.0
 */

@Repository("extendDAO")
public class ExtendDAO extends EgovAbstractMapper{
		
	/**
	 * @title : dto를 map으로 변환
	 * @method : createMap
	 */
	private LinkedHashMap<String,String> createMap( Object dto ){
		LinkedHashMap<String, String>map = new LinkedHashMap<String, String>();
		String exKeyColumn = "";
		
		try {
			Object obj = dto;
			for (Field field : obj.getClass().getSuperclass().getDeclaredFields()) {
				field.setAccessible(true);
				Object valuetmp = field.get(obj);
				String value = "";
				if(valuetmp != null){value = valuetmp.toString();}
				
				if("exColumn".equals(field.getName()) && !"".equals(value)){
					String columns = "";
					String[] value_arr = value.split(",");
					for(int i=0;i<=value_arr.length-1;i++){
						if(i > 0){columns += ", ";}
						String value_tmp = value_arr[i].trim();
						String column_name = "";
						String column_name_as = "";
						if(value_tmp.toLowerCase().indexOf(" as ") == -1){
							column_name = value_tmp;
							column_name_as = value_tmp;
						}else{
							String[] value_tmp_arr = value_tmp.split("(?i) as ");
							column_name = value_tmp_arr[0];
							column_name_as = value_tmp_arr[1].trim();
						}
						if(column_name_as.indexOf(".") > -1){
							String[] column_name_as_arr = column_name_as.split("\\.");
							column_name_as = column_name_as_arr[column_name_as_arr.length-1];
						}
						column_name = column_name.replaceAll("!comma;", ",");
						columns += column_name+" as \""+column_name_as+"\"";
					}
					value = columns;
				}else if("exKeyColumn".equals(field.getName()) && !"".equals(value)){
					exKeyColumn = value;
				}else if("exWhereQuery".equals(field.getName()) && !"".equals(value)){
					value = value.replaceAll("^\\s+","");
					String chk = value.substring(0, 4).toLowerCase();
					if(chk.equals("and ")){
						value = "where "+value.substring(4, value.length());
					}
				}
				//System.out.println(field.getName()+"="+value);
				map.put(field.getName(), value);
			}
			
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				Object valuetmp = field.get(obj);
				String value = null;
				if(valuetmp != null){value = valuetmp.toString().trim();}
				map.put(field.getName(), value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!"".equals(exKeyColumn)){
			String exColumn = map.get("exColumn");
			exColumn += ", "+exKeyColumn.toUpperCase()+" as EXKEYCOLUMNNAME";
			map.put("exColumn", exColumn);
		}
		return map;
	}
	
	/**
	 * @title : dto내에 필드를 조회해서 문자열로 반환 "a, b, c"
	 * @method : getFieldNames( Object dto )
	 */
	public String getFieldNames( Object dto ){
		String fields = this.getFieldNames(dto, "");
		return fields;
	}
	
	/**
	 * @title : dto내에 필드를 조회해서 문자열로 반환 "a, b, c"
	 * @method : getFieldNames( Object dto, String removeFields )
	 */
	public String getFieldNames( Object dto, String removeFields ){
		String fields = "";
		String[] removeFields_arr = null;
		if(removeFields == null){removeFields = "";}
		removeFields = removeFields.trim();
		removeFields = removeFields.replaceAll(" ", "");
		if(!"".equals(removeFields)){
			removeFields_arr = removeFields.split(",");
		}
		try {
			Object obj = dto;
			if(removeFields_arr == null){
				for (Field field : obj.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if("serialVersionUID".equals(field.getName())){
						continue;
					}
					if(!"".equals(fields)){fields += ", ";}
					fields += field.getName();
				}
			}else{
				for (Field field : obj.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if("serialVersionUID".equals(field.getName())){
						continue;
					}
					String fieldname = field.getName().toLowerCase();
					boolean fieldNameChk = false;
					for(int i=0;i<=removeFields_arr.length-1;i++){
						String removeField = removeFields_arr[i].toLowerCase();
						if(fieldname.equals(removeField)){
							fieldNameChk = true;
							break;
						}
					}
					if(!fieldNameChk){
						if(!"".equals(fields)){fields += ", ";}
						fields += field.getName();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fields;
	}
	
	
	
	/**
	 * @title : 쿼리삽입방지
	 * @method : filter
	 */
	public String filter(String value){
		if(value == null){value = "";}
		value = value.trim();
		value = value.replaceAll("'", "''");
		return value;
	}


	/**
	 * @title : 단일필드 조회
	 * @method : selectQueryColumn
	 */
	public String selectQueryColumn(Object dto) throws InstantiationException, IllegalAccessException{
		
		LinkedHashMap<String, String>map = this.createMap(dto);
		
		return selectOne("extenddao_mp.getColumn", map);

	}
	
	/**
	 * @title : 단일필드 조회 String 파라미터
	 * @method : selectQueryColumn
	 */
	public String selectQueryColumn(String exColumn, String exTableName) throws InstantiationException, IllegalAccessException{
		
		return selectQueryColumn(exColumn, exTableName, "", "");

	}
	public String selectQueryColumn(String exColumn, String exTableName, String exWhereQuery) throws InstantiationException, IllegalAccessException{
		
		return selectQueryColumn(exColumn, exTableName, exWhereQuery, "");

	}
	public String selectQueryColumn(String exColumn, String exTableName, String exWhereQuery, String exOrderByQuery) throws InstantiationException, IllegalAccessException{
		
		LinkedHashMap<String, String>map = new LinkedHashMap<String, String>();
		map.put("exColumn", exColumn);
		map.put("exTableName", exTableName);
		map.put("exWhereQuery", exWhereQuery);
		map.put("exOrderByQuery", exOrderByQuery);
		return selectOne("extenddao_mp.getColumn", map);

	}
	
	
	/**
	 * @throws SQLException 
	 * @throws IOException 
	 * @title : 단일행 조회 HashMap<String, Object>
	 * @method : selectQueryRecordObject
	 */
	public HashMap<String, Object> selectQueryRecordObject(Object dto) throws InstantiationException, IllegalAccessException, SQLException, IOException{
		
		LinkedHashMap<String, String>map = this.createMap(dto);
		map.put("exRecordCount","1"); //조회 레코드수 강제지정
		HashMap<String, Object>map_1 = selectOne("extenddao_mp.getRecord", map);
		
		if(map_1 == null){
			map_1 = new HashMap<String, Object>();
		}else{
			
			for (Map.Entry<String, Object> entry: map_1.entrySet()) {
				String key = (String) entry.getKey();
				String value = "";
				String keyColumnValue = "";
				//if (map_1.get(key) instanceof TbClob){	//tibero
				String tmp = key+" = ";
				if (map_1.get(key) instanceof Clob){
					tmp += "clob";
					if(!"".equals( map.get("exKeyColumn") )){
						keyColumnValue = map_1.get( "EXKEYCOLUMNNAME" ).toString();
						value = clobToString(map.get("exTableName"), map.get("exKeyColumn"), keyColumnValue, key);
					}
					map_1.put(key, value);
				}
			}

		}
		return map_1;

	}
	
	
	/**
	 * @throws SQLException 
	 * @throws IOException 
	 * @title : 단일행 조회 HashMap<String, String>
	 * @method : selectQueryRecord
	 */
	public HashMap<String, String> selectQueryRecord(Object dto) throws InstantiationException, IllegalAccessException, SQLException, IOException{
		
		HashMap<String, Object>map_1 = selectQueryRecordObject(dto);
		HashMap<String, String>map_2 = new HashMap<String, String>();
		
		if(map_1.size() > 0){
			for (Map.Entry<String, Object> entry: map_1.entrySet()) {
				map_2.put(entry.getKey(), entry.getValue().toString());
			}
		}
		
		return map_2;

	}
	
	
	
	/**
	 * @title : 리스트 조회
	 * @method : selectQueryTableObject
	 */
	public List<HashMap<String, Object>> selectQueryTableObject(Object dto) throws InstantiationException, IllegalAccessException{
		
		LinkedHashMap<String, String>map = this.createMap(dto);

		List<HashMap<String, Object>>list_data = selectList("extenddao_mp.getRecord", map);
		
		//clob 타입 체크
		for(int i=0;i<=list_data.size()-1;i++){
			HashMap<String, Object>map_1 = (HashMap<String, Object>)list_data.get(i);

			for (Map.Entry<String, Object> entry: map_1.entrySet()) {
				String key = (String) entry.getKey();
				String value = "";
				String keyColumnValue = "";
				if (map_1.get(key) instanceof Clob){
					if(!"".equals( map.get("exKeyColumn") )){
						keyColumnValue = map_1.get( "EXKEYCOLUMNNAME" ).toString();
						value = clobToString(map.get("exTableName"), map.get("exKeyColumn"), keyColumnValue, key);
					}
					map_1.put(key, value);
				}
			}
		}
		return list_data;

	}
	
	
	/**
	 * @title : 리스트 조회
	 * @method : selectQueryTable
	 */
	public List<HashMap<String, String>> selectQueryTable(Object dto) throws InstantiationException, IllegalAccessException{
		
		List<HashMap<String, String>>list_data = new ArrayList<HashMap<String, String>>();
		List<HashMap<String, Object>>list_data2 = selectQueryTableObject(dto);
		
		//Object -> String
		for(int i=0;i<=list_data2.size()-1;i++){
			
			HashMap<String, Object>map_1 = (HashMap<String, Object>)list_data2.get(i);
			HashMap<String, String>map_2 = new HashMap<String, String>();
			
			for (Map.Entry<String, Object> entry: map_1.entrySet()) {
				map_2.put(entry.getKey(), entry.getValue().toString());
			}
			
			list_data.add(map_2);
		}
		

		return list_data;

	}
	
	
	
	
	/**
	 * @title : 페이징
	 * @method : selectQueryPageObject
	 */
	public List<HashMap<String, Object>> selectQueryPageObject(Object dto) {
		LinkedHashMap<String, String>map = this.createMap(dto);
		//정렬조건에 랜덤은 제거
		if("random".equals(map.get("exOrderByQuery"))){
			map.remove("exOrderByQuery");
		}
		
		//전체레코드수
		HashMap<String, String>map2 = new HashMap<String, String>();
		map2.putAll(map);
		map2.put("exColumn", "count(*)");
		map2.put("exOrderByQuery", null);	//카운트 낼때 정렬은 하지말자
		String totalcount = selectOne("extenddao_mp.getColumn", map2);
		HashMap<String, Object>map_total = new HashMap<String, Object>();
		map_total.put("totalcount", totalcount);
		//전체레코드수 끝
		List<HashMap<String, Object>>list_data = new ArrayList<HashMap<String, Object>>();
		//list_data.add(map_total);
		
		if(!"0".equals(totalcount)){	//검색결과가 있다면
			int totalcnt = Integer.parseInt( totalcount );
			int pagesize = Integer.parseInt( map.get("exRecordCount") );
			int totalpage = (int)Math.ceil( ((totalcnt-1)/pagesize)+1);
			if("0".equals(map.get("exPage"))){map.put("exPage", "1");}
			int page = Integer.parseInt( map.get("exPage") );
			if(page < 1){
				page = 1;
			}else if(page > totalpage){
				page = totalpage;
			}
			map.put("exPage", Integer.toString(page));	//현재페이지
			//시작 레코드카운트(mysql, oracle)
			String exStartCount = Integer.toString( pagesize * (page-1) );
			map.put("exStartCount", exStartCount);
			//마지막 레코드카운트(orcle)
			String exEndCount = Integer.toString( ( pagesize * (page - 1) ) + pagesize );
			map.put("exEndCount", exEndCount);
			list_data = selectList("extenddao_mp.getPage", map);
			//Object -> String
			for(int i=0;i<=list_data.size()-1;i++){
				HashMap<String, Object>map_1 = (HashMap<String, Object>)list_data.get(i);

				for (Map.Entry<String, Object> entry: map_1.entrySet()) {
					String key = (String) entry.getKey();
					String value = "";
					String keyColumnValue = "";

					if (map_1.get(key) instanceof Clob){
						if(!"".equals( map.get("exKeyColumn") )){
							keyColumnValue = map_1.get( "EXKEYCOLUMNNAME" ).toString();
							value = clobToString(map.get("exTableName"), map.get("exKeyColumn"), keyColumnValue, key);
						}
						map_1.put(key, value);
					}
				}
				
			}
		}
		
		list_data.add(0, map_total);	//총카운트 첫번째 리스트로 추가
		
		return list_data;
	}
	
	
	
	
	
	/**
	 * @title : 페이징
	 * @method : selectQueryPage
	 */
	public List<HashMap<String, String>> selectQueryPage(Object dto) {
		List<HashMap<String, String>>list_data = new ArrayList<HashMap<String, String>>();
		List<HashMap<String, Object>>list_data2 = selectQueryPageObject(dto);
		//Object -> String
		for(int i=0;i<=list_data2.size()-1;i++){
			
			HashMap<String, Object>map_1 = (HashMap<String, Object>)list_data2.get(i);
			HashMap<String, String>map_2 = new HashMap<String, String>();
			
			for (Map.Entry<String, Object> entry: map_1.entrySet()) {
				map_2.put(entry.getKey(), entry.getValue().toString());
			}
			
			list_data.add(map_2);
		}
		
		return list_data;
	}
	
	/**
	 * @title : 데이타저장
	 * @method : insert
	 */
	public void insert(Object dto) {
		
		LinkedHashMap<String, String>map = this.createMap(dto);
		insert("extenddao_mp.insert", map);
		
	}
	
	
	
	
	/**
	 * @title : 데이타수정
	 * @method : update(Object dto)
	 */
	public void update(Object dto) {
		
		LinkedHashMap<String, String>map = this.createMap(dto);
		update("extenddao_mp.update", map);
		
	}
	
	
	/**
	 * @title : 쿼리실행
	 * @method : executeQuery
	 */
	public void executeQuery(String sql) {
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("sql", sql);
		update("extenddao_mp.executeQuery", map);
		
	}
	
	
	
	/**
	 * @title : clob컬럼 값 String반환
	 * @method : createClobColumnName
	 */
	public String clobToString(String tableName, String keyColumn, String keyColumnValue, String columnName){
		HashMap<String, String>selectmap = new HashMap<String, String>();
		
		selectmap.put("exTableName", tableName);
		selectmap.put("exColumn", columnName);
		
		String whereQuery = "";
		if("".equals(keyColumn) || keyColumn == null){
			return tableName+"."+columnName+" 데이타 조회 실패";
		}else{
			if("".equals(whereQuery) || whereQuery == null){
				whereQuery = " where "+keyColumn+" = '"+keyColumnValue+"'";
			}
		}
		selectmap.put("exWhereQuery", whereQuery);
		
		ClobSelectDTO clobSelectDTO = selectOne("extenddao_mp.getClobColumn", selectmap);
		
		String value = clobSelectDTO.getExClobField();
		
		return value;
	}
	
	/**
	 * @title : empty > null
	 * @method : requestAll(Object dto)
	 */
	public Object emptyToNull(Object dto) throws Exception {
		Class dtoClass = dto.getClass();
		Field[] fields = dtoClass.getDeclaredFields();
		
		// 파라미터 초기데이터 집어넣기
		if (dto == null) dto = dtoClass.newInstance();
		try {
			Object obj = dto;
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				
				Object value = field.get(obj);
				if("".equals(value)) {
					if (!"serialVersionUID".equals(field.getName())) {
						//초기화할값넣기
						field.set(obj, null);
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return dto;
	}
	
	/*************** 부가기능 시작 ***************/
	
	/**
	 * @throws Exception 
	 * @title : 테이블명 검색 카운트
	 * @method : searchTableCount
	 */
	public int searchTableCount(String tablename) throws Exception {
		String dbname = CommonConfig.get("jdbc.dbname");
		return searchTableCount(dbname, tablename);
	}
	public int searchTableCount(String dbname, String tablename) {
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("db_name", dbname);
		map.put("table_name", tablename);
		return selectOne("extenddao_mp.searchTableCount", map);
		
	}
	
	/**
	 * @title : 총관리자 통계테이블 생성
	 * @method : createVisitTable
	 */
	public void createVisitTable(String tablename) {
		createVisitTable(tablename, tablename);
	}
	public void createVisitTable(String tablename, String tablename_small) {
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("table_name", tablename);
		map.put("tablename_small", tablename_small);
		update("extenddao_mp.createVisitTable", map);
		
	}
	
	/**
	 * @title : 사이트 접속통계테이블 생성
	 * @method : createBuilderSiteVisitTable
	 */
	public void createBuilderSiteVisitTable(String tablename) {
		createBuilderSiteVisitTable(tablename, tablename);
		
	}
	public void createBuilderSiteVisitTable(String tablename, String tablename_small) {
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("table_name", tablename);
		map.put("tablename_small", tablename_small);
		update("extenddao_mp.createBuilderSiteVisitTable", map);
		
	}
	
	/**
	 * @title : 메뉴통계테이블 생성
	 * @method : createMenuTable
	 */
	public void createMenuTable(String tablename) {
		createMenuTable(tablename, tablename);
		
	}
	public void createMenuTable(String tablename, String tablename_small) {
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("table_name", tablename);
		map.put("tablename_small", tablename_small);
		update("extenddao_mp.createMenuTable", map);
		
	}
	
	/**
	 * @title : 세션
	 * @method : procSessionWait
	 */
	public HashMap<String, ?> procSessionWait(String session_id) {
		return selectOne("extenddao_mp.procSessionWait", session_id);
	}
	
	/**
	 * @title : 테이블 생성
	 * @method : createBoard
	 */
	public void createBoard(String a_tablename_str) throws Exception {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("a_tablename_str", a_tablename_str);
		insert("extenddao_mp.createBoard", hm);
		
	}
	
	/**
	 * @title : 게시판 뷰 생성
	 * @method : createBoardView
	 */
	public void createBoardView(List<HashMap<String,String>> boardList) throws Exception {
		insert("extenddao_mp.createBoardTotal", boardList);
		insert("extenddao_mp.createBoardTotalDelete", boardList);
		insert("extenddao_mp.createBoardTotalMain", boardList);
	}
	
	
	/*************** 부가기능 끝 ***************/
	
}
