package kr.co.nninc.ncms.stock.service;

import org.springframework.ui.Model;

public interface StockService {
	
	public String write(Model model) throws Exception ;
	
	public String writeOk(Model model) throws Exception;
	
	public void excel(Model model) throws Exception;

	public void deleteOk(Model model) throws Exception;
}
