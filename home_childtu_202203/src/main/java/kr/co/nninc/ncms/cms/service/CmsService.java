package kr.co.nninc.ncms.cms.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.ui.Model;

/**
 * cms를 관리하기 위한 서비스 인터페이스 클래스
 * @author 나눔
 * @since 2019.02.15
 * @version 1.1
 */
public interface CmsService {

	public void write(Model model) throws Exception;

	public void writeOk(Model model) throws Exception;

	public void modifyOk(Model model) throws Exception;

	public String user_contents(Model model) throws Exception;

	public void contents(Model model) throws Exception;

	public void preview(Model model) throws Exception;
	
	public String contentsOk(Model model) throws Exception;

	public void history(Model model) throws Exception;

	public void historyOk(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;

	public void listMove(Model model) throws Exception;

	public void listMoveOk(Model model) throws Exception;

	public void move(Model model) throws Exception;

	public void levelOk(Model model) throws Exception;

	public void mngLeft(Model model) throws Exception;

	public void initMenuUrl() throws Exception;

	public String getLwprtMenuUrl(CmsDTO cms) throws Exception;

	public void img(Model model) throws Exception;

	public void imgOk(Model model) throws Exception;

	public void imgDeleteOk(Model model) throws Exception;

	public void css(Model model) throws Exception;

	public void cssOk(Model model) throws Exception;
	
	public void nfuUpload(Model model) throws Exception;

	public void nfuNormalUpload(Model model) throws Exception;
	
	public JSONArray getMenuJson(HttpServletRequest request) throws Exception;
	
	public String getMenuJsonByIdx(HttpServletRequest request, String idx, String field) throws Exception;
	
	public String getMenuJsonByIdx(HttpServletRequest request, String idx, String field, JSONArray menuList) throws Exception;
	
	public JSONObject getMenuJsonByIdx(HttpServletRequest request, String idx) throws Exception;
	
	public JSONObject getMenuJsonByIdx(HttpServletRequest request, String idx, JSONArray menuList) throws Exception;
	
	public void createMenuJson(HttpServletRequest request) throws Exception;
	
	public JSONArray mapToJson(List<HashMap<String,Object>> menuList) throws Exception;
	
	public List<HashMap<String,Object>> getMenuList(String ct_ref, int ct_depth, int max_depth_option) throws Exception;
	
	public List<HashMap<String,Object>> getMenuList(String ct_ref, int ct_depth, int max_depth_option, String site_dir) throws Exception;
	
	public String dragMoveOk(Model model) throws Exception;
}
