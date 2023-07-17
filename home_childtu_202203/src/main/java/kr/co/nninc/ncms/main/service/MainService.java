package kr.co.nninc.ncms.main.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.ui.Model;

public abstract interface MainService
{
  public abstract void procSkinMethod(Model paramModel)
    throws Exception;

  public abstract void procSkinMethod(Model paramModel, String methodName)
		    throws Exception;
  
  public abstract void popupList(Model paramModel)
    throws Exception;

  public abstract void bannerList(Model paramModel)
    throws Exception;

  public abstract void banner2List(Model paramModel)
    throws Exception;

  public abstract void banner3List(Model paramModel)
    throws Exception;

  public abstract List<HashMap<String, String>> boardList(Model paramModel)
    throws Exception;

  public abstract void closeList(Model paramModel)
    throws Exception;

  public abstract String closeListMain(Model paramModel)
    throws Exception;
  
  public void monthEvent(Model model) throws Exception;
  
  public List<HashMap<String, String>> mainBoardList(Model model, String a_num, int pagecount) throws Exception;
  public List<HashMap<String, String>> mainPollList(Model model) throws Exception;
  public void banner4List(Model model) throws Exception;
}