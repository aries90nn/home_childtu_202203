package kr.co.nninc.ncms.mail_auth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.nninc.ncms.common.CommonConfig;
import kr.co.nninc.ncms.common.Func;
import kr.co.nninc.ncms.common.SMTPAuthenticator;
import kr.co.nninc.ncms.common.service.impl.ExtendDAO;
import kr.co.nninc.ncms.mail_auth.service.MailAuthDTO;
import kr.co.nninc.ncms.mail_auth.service.MailAuthService;

/**
 * 메일인증 서비스 구현 클래스
 * @author 나눔
 * @since 2019.04.07
 * @version 1.1
 */
@Service("mailAuthService")
public class MailAuthServiceImpl implements MailAuthService {
	
	@Resource(name = "extendDAO")
	private ExtendDAO exdao;

	@Override
	public Map<String, String> send(Model model, HttpServletRequest request) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		//받는사람
		String a_name = Func.nvl(request.getParameter("a_name"));
		String a_email = Func.nvl(request.getParameter("a_email"));
		
		//전송했는지
		String send = "N";
		if("".equals(a_name) || "".equals(a_email)){ //파라미터없음
			resultMap.put("send", "M");
			return resultMap;
		}
		
		//랜덤문자열
		Random rnd =new Random();
		rnd.setSeed(new Date().getTime());
		
		StringBuffer buf =new StringBuffer();
		buf.append((char)((int)(rnd.nextDouble()*26)+65));
		buf.append((char)((int)(rnd.nextDouble()*26)+65));
		
		for(int i=0;i<5;i++){
			buf.append(rnd.nextInt(10));
		}
		String a_random = buf.toString();
		
		try {
			String from = CommonConfig.get("mail.id");
			String to = a_email;
			String subject = "[투명우산 나눔활동] 인증번호가 발송되었습니다.";
			String content = "";
			content += "		<p style=\"padding:20px 0;margin:0 15px;border-bottom:1px dashed #bad1dc;text-align:center;font-size:16px;\"><span style=\"color:#424242;font-weight:600\">인증번호가 발송되었습니다.</span></p>";
			content += "	<div style=\"padding:25px 0;margin:0 15px;background:#f2f7fa;\">";
			content += "		<table style=\"width:850px;margin:0 auto;background:#fff;border-collapse:collapse;\">";
			content += "			<colgroup>";
			content += "				<col width=\"30%\" />";
			content += "				<col />";
			content += "			</colgroup>";
			content += "			<tr>";
			content += "				<th style=\"padding:10px 0;text-align:center;font-size:13px;border:1px solid #d9e5eb;font-weight:bold;color:#18688f;border-top:2px solid #007fbe;\">이름</th>";
			content += "				<td style=\"padding:10px 0;text-align:center;font-size:13px;border:1px solid #d9e5eb;padding:10px;text-align:left;color:#777;border-top:2px solid #b2bbbf;\">"+a_name+"</td>";
			content += "			</tr>";
			content += "			<tr>";
			content += "				<th style=\"padding:10px 0;text-align:center;font-size:13px;border:1px solid #d9e5eb;font-weight:bold;color:#18688f;\" >인증번호</span></th>";
			content += "				<td style=\"padding:10px 0;text-align:center;font-size:13px;border:1px solid #d9e5eb;padding:10px;text-align:left;color:#777;\">"+a_random+"</td>";
			content += "			</tr>";
			content += "		</table>";
			content += "</div>";
			content += "<div style='margin:10px 10px;border-bottom:1px dashed #bad1dc;text-align:center;font-size:15px;text-align:left;'>";
			content += "		<table style='color:#424242;width:850px;margin:0 auto;font-size:13px'>";
			content += "		<tr><td>발급된 번호를 인증번호 입력란에 정확히 기재한 후 확인 버튼을 눌러 본인인증을 계속 해 주십시오.</td></tr>";
			content += "		</table><br/>";
			content += "	</div>";
			 
			// 프로퍼티 값 인스턴스 생성과 기본세션(SMTP 서버 호스트 지정)
			Properties p = new Properties();

			// SMTP 서버의 계정 설정
			// Naver와 연결할 경우 네이버 아이디 지정
			// Google과 연결할 경우 본인의 Gmail 주소
			p.put("mail.smtp.user", CommonConfig.get("mail.id"));

			// SMTP 서버 정보 설정
			// 네이버일 경우 smtp.naver.com
			// Google일 경우 smtp.gmail.com
			p.put("mail.smtp.host", "smtp.gmail.com");
			p.put("mail.smtp.port", "465");
			p.put("mail.smtp.starttls.enable", "true");
			p.put("mail.smtp.debug", "true");
			p.put("mail.smtp.auth", "true");
			
			if("dev".equals(CommonConfig.get("mail.type"))){
				//TLS 사용일때 
				p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				p.put("mail.smtp.socketFactory.port", "465");
				p.put("mail.smtp.socketFactory.fallback", "false");
			}else{
				//SSL 사용일때 
				p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				p.put("mail.smtp.socketFactory.port", "587");
				p.put("mail.smtp.ssl.enable", "true"); 
			}
			// 아래 정보는 네이버와 구글이 동일하므로 수정하지 마세요.
			/*p.put("mail.smtp.port", "465");
			p.put("mail.smtp.starttls.enable", "true");
			p.put("mail.smtp.auth", "true");
			p.put("mail.smtp.debug", "true");
			p.put("mail.smtp.socketFactory.port", "465");
			p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			p.put("mail.smtp.socketFactory.fallback", "false");*/
			
			Authenticator auth = new SMTPAuthenticator();
			Session ses = Session.getInstance(p, auth);

			// 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
			ses.setDebug(true);
				
			// 메일의 내용을 담기 위한 객체
			MimeMessage msg = new MimeMessage(ses);

			// 제목 설정
			msg.setSubject(subject);
				
			// 보내는 사람의 메일주소
			Address fromAddr = new InternetAddress(from, "Seed Vault Center");
			msg.setFrom(fromAddr);
				
			// 받는 사람의 메일주소
			Address toAddr = new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
				
			// 메시지 본문의 내용과 형식, 캐릭터 셋 설정
			msg.setContent(content, "text/html;charset=UTF-8");
				
			// 발송하기
			Transport.send(msg);
			send = "Y";
				
		} catch (Exception mex) {
			send = "N";
			//mex.printStackTrace();
			System.out.println("ERROR 1 - MailAuthServiceImpl.send");
		}
		//데이터담기
		if("Y".equals(send)){
			//삭제후 등록(3시간전꺼 삭제)
			//exdao.executeQuery("DELETE FROM auth_email WHERE a_email='"+a_email+"' OR TO_DATE(a_wdate,'YYYY-MM-DD HH24:MI:SS') < TO_DATE(TO_CHAR(SYSDATE-3/24, 'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') ");
			exdao.executeQuery("DELETE FROM auth_email WHERE a_email='"+a_email+"' OR STR_TO_DATE(a_wdate,'%Y-%m-%d %H:%i:%s') < STR_TO_DATE(DATE_ADD(NOW(), INTERVAL -3 HOUR),'%Y-%m-%d %H:%i:%s') ");
			
			//휴대폰 암호화
			//String email = "!noquot;SCP.ENC_STR('"+CommonConfig.get("db_param1")+"', '"+CommonConfig.get("db_param2")+"', '"+CommonConfig.get("db_param3")+"', '"+a_email+"')";
			
			MailAuthDTO mailauth = new MailAuthDTO();
			mailauth.setA_name(a_name);
			//mailauth.setA_email(email);
			mailauth.setA_email(a_email);
			mailauth.setA_chk("N");
			mailauth.setA_random(a_random);
			mailauth.setA_wdate(Func.date("Y-m-d H:i:s"));
			mailauth.setExTableName("auth_email");
			exdao.insert(mailauth);
		}
		resultMap.put("send", send);
		return resultMap;
	}

	@Override
	public Map<String, String> end(Model model, HttpServletRequest request) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String result = "N";

		String a_random		= Func.nvl(request.getParameter("a_random"));
		String a_name			= Func.nvl(request.getParameter("a_name"));
		String a_email			= Func.nvl(request.getParameter("a_email"));
		
		if("".equals(a_random) || "".equals(a_email)  || "".equals(a_email)){ //파라미터없음
			resultMap.put("result", "M");
			return resultMap;
		}

		//3시간전꺼 삭제
		//exdao.executeQuery("DELETE FROM auth_email WHERE TO_DATE(a_wdate,'YYYY-MM-DD HH24:MI:SS') < TO_DATE(TO_CHAR(SYSDATE-3/24, 'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') ");
		exdao.executeQuery("DELETE FROM auth_email WHERE STR_TO_DATE(a_wdate,'%Y-%m-%d %H:%i:%s') < STR_TO_DATE(DATE_ADD(NOW(), INTERVAL -3 HOUR),'%Y-%m-%d %H:%i:%s') ");
		
		String where = "WHERE a_random='" + a_random + "' AND a_name='" + a_name +"' ";
		//where += " AND SCP.DEC_STR('"+CommonConfig.get("db_param1")+"','"+CommonConfig.get("db_param2")+"', '"+CommonConfig.get("db_param3")+"', a_email)='" + a_email+"' ";
		where += " AND a_email='" + a_email+"' ";
		//
		MailAuthDTO selectdto = new MailAuthDTO();
		selectdto.setExTableName("auth_email");
		selectdto.setExWhereQuery(where);
		selectdto.setExColumn("a_email");
		HashMap<String, String> map = exdao.selectQueryRecord(selectdto);
		if(!"".equals(Func.nvl(map.get("a_email")))){
			String db_email = Func.nvl(map.get("a_email"));
			if(!"".equals(db_email)){
				result = "Y";
				//세션생성
				Func.setSession(request, "ss_m_pwd", a_email);
				Func.setSession(request, "ss_m_name", a_name);
				Func.setSession(request, "ss_m_jumin", db_email);
			}
		}
		resultMap.put("result", result);
		return resultMap;
	}
}
