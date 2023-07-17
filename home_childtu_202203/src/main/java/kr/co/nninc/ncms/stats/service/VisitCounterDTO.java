package kr.co.nninc.ncms.stats.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("visitcounterdto")
public class VisitCounterDTO extends ExtendDTO implements Serializable {

	private static final long serialVersionUID = 3322221739214619419L;
	
	private String vNum;
	private String vIP;
	private String vDD;
	private String vHH;
	private String vMT;
	private String vSeason;
	private String vDW;
	private String vBrowser;
	private String vOS;
	private String vURL;
	private String vDomain;
	private String fulldate;
	
	
	public String getvNum() {
		return vNum;
	}
	public void setvNum(String vNum) {
		this.vNum = vNum;
	}
	public String getvIP() {
		return vIP;
	}
	public void setvIP(String vIP) {
		this.vIP = vIP;
	}
	public String getvDD() {
		return vDD;
	}
	public void setvDD(String vDD) {
		this.vDD = vDD;
	}
	public String getvHH() {
		return vHH;
	}
	public void setvHH(String vHH) {
		this.vHH = vHH;
	}
	public String getvMT() {
		return vMT;
	}
	public void setvMT(String vMT) {
		this.vMT = vMT;
	}
	public String getvSeason() {
		return vSeason;
	}
	public void setvSeason(String vSeason) {
		this.vSeason = vSeason;
	}
	public String getvDW() {
		return vDW;
	}
	public void setvDW(String vDW) {
		this.vDW = vDW;
	}
	public String getvBrowser() {
		return vBrowser;
	}
	public void setvBrowser(String vBrowser) {
		this.vBrowser = vBrowser;
	}
	public String getvOS() {
		return vOS;
	}
	public void setvOS(String vOS) {
		this.vOS = vOS;
	}
	public String getvURL() {
		return vURL;
	}
	public void setvURL(String vURL) {
		this.vURL = vURL;
	}
	public String getvDomain() {
		return vDomain;
	}
	public void setvDomain(String vDomain) {
		this.vDomain = vDomain;
	}
	public String getFulldate() {
		return fulldate;
	}
	public void setFulldate(String fulldate) {
		this.fulldate = fulldate;
	}
	
	

}
