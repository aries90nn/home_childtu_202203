package kr.co.nninc.ncms.stats.service;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import kr.co.nninc.ncms.common.service.ExtendDTO;

@Repository("visitcountersitedto")
public class VisitCounterSiteDTO extends ExtendDTO implements Serializable{

	private static final long serialVersionUID = -7854183217907478172L;

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
	private String vSite_dir;
	
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
	public String getvSite_dir() {
		return vSite_dir;
	}
	public void setvSite_dir(String vSite_dir) {
		this.vSite_dir = vSite_dir;
	}
	
	
}
