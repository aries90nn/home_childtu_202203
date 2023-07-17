package kr.co.nninc.ncms.test.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.test.service.TestService;

@Service("testService")
public class TestServiceImpl extends EgovAbstractServiceImpl implements TestService{

	/** 동적 dao **/
	@Resource(name="extendDAO")
	private ExtendDAO exdao;
	
	@Override
	public void test() throws Exception{
		System.out.println("test service");
		
		List<HashMap<String,String>>boardList = new ArrayList<HashMap<String,String>>();
		for(int i=1;i<=4;i++){
			HashMap<String,String>board = new HashMap<String,String>();
			board.put("a_num", Func.cStr(i));
			board.put("a_tablename", "board_"+i);
			board.put("a_bbsname", "게시판 "+i);
			board.put("a_pageurl", "/board/list.do?a="+i);
			
			boardList.add(board);
		}
		
		exdao.createBoardView(boardList);
	}
}
