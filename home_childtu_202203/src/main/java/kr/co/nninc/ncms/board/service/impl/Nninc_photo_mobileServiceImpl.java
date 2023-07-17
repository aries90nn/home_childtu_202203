package kr.co.nninc.ncms.board.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.SkinService;

@Service("nninc_photo_mobileService")
public class Nninc_photo_mobileServiceImpl extends EgovAbstractServiceImpl implements SkinService {

	@Override
	public void list(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void view(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		HashMap<String,String>config = (HashMap<String, String>) request.getAttribute("config");
		config.put("a_nofile", "jpg,gif,png,bmp");

	}

	@Override
	public void writeOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void modify(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		HttpServletRequest request	= (HttpServletRequest) map.get("request");
		HashMap<String,String>config = (HashMap<String, String>) request.getAttribute("config");
		config.put("a_nofile", "jpg,gif,png,bmp");

	}

	@Override
	public void modifyOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteOk(Model model) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
