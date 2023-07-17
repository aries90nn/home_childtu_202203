package kr.co.nninc.ncms.common;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {

	private String username;
	private String password;
	
	public SMTPAuthenticator(String id , String pwd){
		this.username = id;
		this.password = pwd;
	}
	
	public SMTPAuthenticator(){
		try {
			username = CommonConfig.get("mail.id");
			password = CommonConfig.get("mail.pwd");
		} catch (Exception e) {
			System.out.println("ERROR 1 - SMTPAuthenticator.SMTPAuthenticator");
		} // gmail 사용자;
	}
	
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}
