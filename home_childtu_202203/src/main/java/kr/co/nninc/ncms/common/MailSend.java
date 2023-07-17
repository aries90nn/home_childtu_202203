package kr.co.nninc.ncms.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSend {
	
	private String host;
	private String port;
	private String id;
	private String pwd;
	private String subject;
	private String content;
	private String fromAddr;
	private String fromName;
	private List<HashMap<String,String>> toAddr;
	
	
	public MailSend() throws Exception{
		this.setHost( CommonConfig.get("mail.host") );
		this.setPort( CommonConfig.get("mail.port") );
		this.setId( CommonConfig.get("mail.id") );
		this.setPwd( CommonConfig.get("mail.pwd") );
		this.setSubject("제목없음");
	}


	public void send() throws Exception{
		// 프로퍼티 값 인스턴스 생성과 기본세션(SMTP 서버 호스트 지정)
		Properties p = new Properties();

		// SMTP 서버의 계정 설정
		// Naver와 연결할 경우 네이버 아이디 지정
		// Google과 연결할 경우 본인의 Gmail 주소
		p.put("mail.smtp.user", this.getId());

		// SMTP 서버 정보 설정
		// 네이버일 경우 smtp.naver.com
		// Google일 경우 smtp.gmail.com
		p.put("mail.smtp.host", this.getHost());
		p.put("mail.smtp.port", this.getPort());
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.port", this.getPort());
		p.put("mail.smtp.socketFactory.fallback", "false");
		
		
		//Session 생성
		Session session = Session.getDefaultInstance(p, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(getId(), getPwd());
			}
		});
		session.setDebug(true);	//for debug
		
		// 메일의 내용을 담기 위한 객체
		Message msg = new MimeMessage(session);
		
		// 제목 설정
		msg.setSubject(this.getSubject());
			
		// 보내는 사람의 메일주소
		Address fromAddr = null;
		if(Func.nvl( this.getFromName() ).equals("")){
			fromAddr = new InternetAddress(this.getFromAddr());
		}else{
			fromAddr = new InternetAddress(this.getFromAddr(), this.getFromName());
		}
		msg.setFrom(fromAddr);
			
		// 받는 사람의 메일주소
		/*
		Address toAddr = new InternetAddress("sp1000je@naver.com");
		msg.addRecipient(Message.RecipientType.TO, toAddr);
		*/
		/*
		InternetAddress[] toAddr = new InternetAddress[2];
		toAddr[0] = new InternetAddress("sp1000je@nate.com");
		toAddr[1] = new InternetAddress("sp1000je@naver.com");
		*/
		
		List<HashMap<String,String>>toAddrList = this.getToAddr();
		InternetAddress[] toAddr = new InternetAddress[ toAddrList.size() ];
		for(int i=0;i<=toAddrList.size()-1;i++){
			HashMap<String,String>toAddrMap = toAddrList.get(i);
			for (Entry<String, String> entry: toAddrMap.entrySet()) {
				System.out.println( entry.getKey() + " : " + entry.getValue() );
				toAddr[i] = new InternetAddress(entry.getValue());
			}
		}
		
		msg.setRecipients(Message.RecipientType.TO, toAddr);
		
			
		// 메시지 본문의 내용과 형식, 캐릭터 셋 설정
		msg.setContent(this.getContent(), "text/html;charset=UTF-8");
		
		// 발송하기
		Transport.send(msg);
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFromAddr() {
		return fromAddr;
	}
	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public List<HashMap<String, String>> getToAddr() {
		return toAddr;
	}
	public void setToAddr(List<HashMap<String, String>> toAddr) {
		this.toAddr = toAddr;
	}
	public void setToAddr(String toAddr) {
		List<HashMap<String,String>>toAddrList = new ArrayList<HashMap<String,String>>();
		HashMap<String,String>toAddrMap = new HashMap<String,String>();
		toAddrMap.put("mail", toAddr);
		toAddrList.add(toAddrMap);
		setToAddr(toAddrList);
	}

	
}
