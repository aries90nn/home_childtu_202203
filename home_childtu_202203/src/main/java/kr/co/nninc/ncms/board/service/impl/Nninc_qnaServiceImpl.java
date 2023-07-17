package kr.co.nninc.ncms.board.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.board.service.SkinDTO;
import kr.co.nninc.ncms.board.service.SkinService;

@Service("nninc_qnaService")
public class Nninc_qnaServiceImpl extends EgovAbstractServiceImpl implements SkinService {

	@Override
	public void list(Model model) throws Exception {
		Map<String, Object> map		= model.asMap();
		
		SkinDTO skinDTO = (SkinDTO) map.get("skinDTO");
		
		skinDTO.setWhere_query("and b_type = 'N'");
		skinDTO.setOrderby_query("order by b_regdate desc, b_num desc");

	}

	@Override
	public void view(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeOk(Model model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void modify(Model model) throws Exception {
		// TODO Auto-generated method stub

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
